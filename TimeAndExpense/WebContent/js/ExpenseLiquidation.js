var liquidationsGridStore = null;
var liquidationsGrid = null;

function displayLiquidationList(data){
	populateRevNoInLiquidations();
	dijit.byId("expenseLiquidationsGrid").showMessage("Loading ... Please wait");
	serverRequestExpenseLiquidationsInquiry("ViewExpenseLiquidations.action", {}, handleLiquidationServerResponse);
	setupInitialViewState();
}

// populate revision no
function populateRevNoInLiquidations(){
	dojo.byId('liq_revNo').innerHTML = dojo.byId('revNo').innerHTML;
}

function setupInitialViewState(){
	// setup prev, next & save button state
	dojo.byId('saveExpenseLiquidationsBtn').disabled = dojo.byId('saveBtn').disabled;
	if (dojo.byId('PDFCheckBox').checked){
		dojo.byId('saveExpenseLiquidationsBtn').disabled = true;
	}
	dojo.byId('liq_prevRevBtn').disabled = dojo.byId('prevRevBtn').disabled;
	dojo.byId('liq_nextRevBtn').disabled = dojo.byId('nextRevBtn').disabled;
	
	// attach handler to TripId 'Modify' button
	if (!dojo.byId('PDFCheckBox').checked){
		dojo.connect(dojo.byId('modifyBtn'), 'onclick', function(){setupLiquidationsViewUponModification()});
	}
	
	// disable SAVE button, if no record in grid
	if(dijit.byId('expenseLiquidationsGrid').store._arrayOfAllItems.length < 1)
		dojo.byId('saveExpenseLiquidationsBtn').disabled = true;
	
	// disable liquidation cell editing if SAVE if disabled
	if(dojo.byId('saveExpenseLiquidationsBtn').disabled || dojo.byId('PDFCheckBox').checked)
		dijit.byId('expenseLiquidationsGrid').layout.structure[0].cells[0][7].editable = false;
}

/* Setup view upon modification request */
function setupLiquidationsViewUponModification(){
	dojo.byId('saveExpenseLiquidationsBtn').disabled = false;
}

/* Refreshes liquidation components upon tab re-selection */
function refreshLiquidationsAfterLoad(){
	
	serverRequestExpenseLiquidationsInquiry("FindCurrentExpenseAmount.action", {}, function(data){
		if(data.response.amount != null) dojo.byId('liq_totalExpenses').value = data.response.amount.toFixed(2).toString();
		dijit.byId('expenseLiquidationsGrid').resize();
	});
	
}

function handleLiquidationServerResponse(data){
	dojo.byId('liq_totalAdvanceOutstandingAmt').value = data.response.outstandingAdvance;
	dojo.byId('liq_totalExpenses').value = data.response.expense;

	constructLiquidationsGrid(data.response.advances);
	dijit.byId('expenseLiquidationsGrid').resize();
	
	// disable SAVE button if no advances found
	if(data.response.advances==null || data.response.advances.length < 1) 
		dojo.byId('saveExpenseLiquidationsBtn').disabled = true;
}

function constructLiquidationsGrid(liquidations){
	liquidationsGridStore = prepareLiquidationGridStore(formatLiquidationsServerData(liquidations));
	
	liquidationsGrid = dijit.byId('expenseLiquidationsGrid');
	liquidationsGrid.setStore(liquidationsGridStore);

	dojo.connect(liquidationsGrid, "onCellMouseOver", showTooltip);
	dojo.connect(liquidationsGrid, "onCellMouseOut", hideTooltip);
	// attach cell edit handler
	dojo.connect(liquidationsGrid, "doApplyCellEdit", onLiquidationAmountChange);
	dojo.connect(liquidationsGrid, "onKeyDown", onLiquidationAmountChange);
}


//function for displaying tooltip
var showTooltip = function(event) {
var testGrid =  dijit.byId('expenseLiquidationsGrid');
var reason = testGrid._by_idx[event.rowIndex].item.advanceReason; 

if (event.cellIndex == 4){
	if (reason != undefined){
		var msg = reason;			
		dijit.showTooltip(msg, event.cellNode);
	   }
	}
}		

//function for hiding tooltip
var hideTooltip = function(e) {
dijit.hideTooltip(e.cellNode);
}


function prepareLiquidationGridStore(liquidations){
	return new dojo.data.ItemFileWriteStore({ data:{identifier:'advmIdentifier', items:liquidations} });
}

function onLiquidationAmountChange(newValue, rowIndex, colIndex){
	var editedRow = liquidationsGrid.getItem(rowIndex);
	
	liquidationsGridStore.fetchItemByIdentity({identity: editedRow.advmIdentifier[0], onItem : function(item, request) {
		if(newValue== null || newValue=="" || isNaN(newValue)){
			liquidationsGridStore.setValue(item, "amountLiquidated", 0);
			liquidationsGridStore.setValue(item, "newOutstandingAmount", editedRow.adjustedAmountOutstanding[0]);
		}else{
			liquidationsGridStore.setValue(item, "newOutstandingAmount", editedRow.adjustedAmountOutstanding[0] - parseFloat(newValue));
		}
	}});
	setChanged();
	liquidationsGrid.resize();
}

function formatLiquidationsServerData(liquidations){
	dojo.forEach(liquidations, function(item){
		convertLiquidationNullFieldsToEmptyStrings(item);
		item.newOutstandingAmount = item.adjustedAmountOutstanding - item.amountLiquidated;
	});
	
	return liquidations;
}

function convertLiquidationNullFieldsToEmptyStrings(item){
	if(item.dateRequested == null) item.dateRequested='';
	//if(item.paidDate == null) item.paidDate='';
	if(item.shortReason == null) item.shortReason='';
	if(item.advanceFromDate == null) item.advanceFromDate='';
	if(item.advanceToDate == null) item.advanceToDate='';
	if(item.dollarAmount == null) item.dollarAmount=0;
	if(item.newOutstandingAmount == null) item.newOutstandingAmount=0;
	if(item.permanentAdvInd == null) item.permanentAdvInd='';
};

/* Validate user input upon SAVE */  
function validateLiquidations(errorMsg){
	return validateLiquidationAmount(errorMsg);
}

/* Validate liquidation amount NOT greater than total outstanding amount */  
function validateLiquidationAmount(errorMsg){
	for(i = 0; i<liquidationsGrid.rowCount; i++){
		var item = liquidationsGrid.getItem(i);
		if(item.amountLiquidated < 0){
			 errorMsg.msg = 'Row ' + (i+1) +'  -  Liquidation amount cannot be less than 0';
			 return false;
		}
		if(item.newOutstandingAmount < 0){
			errorMsg.msg = 'Row ' + (i+1) +'  -  Liquidation amount ($'+ item.amountLiquidated[0].toFixed(2) +') cannot be more than the total outstanding advance ($'+item.adjustedAmountOutstanding[0].toFixed(2) +') ';
			 return false;
		}
	}
	
	return true;
}


function saveExpenseLiquidations(){
	
	// get grid out of edit mode and apply all changes
	liquidationsGrid.edit.apply();
	var problemDuringSave = false;
	var errorMsg = {msg:''};
	
	// clear any previous error messages
	notifyLiquidations('', false);
	
	// validate user input
	if(validateLiquidations(errorMsg)){
		setUnchanged();
		// submit save request
		submitLiquidationSaveRequest();
	}else{
		problemDuringSave = true;
		// show validation error
		notifyLiquidations(errorMsg.msg, true);
	}
	return problemDuringSave;

}

function submitLiquidationSaveRequest(){
	// save grid store, to make its state consistent
	liquidationsGridStore.save();

	// now send the details in the grid to the server
	var expLiquidationsArray = (dojo.fromJson(liquidationsGridStore._getNewFileContentString())).items;
	var data = {expenseLiquidationsJson : dojo.toJson(expLiquidationsArray)};

	serverRequestExpenseLiquidations("SaveExpenseLiquidations.action", data, expenseLiquidationsSaveCallback);
}

/* Processes server response upon SAVE */
function expenseLiquidationsSaveCallback(data){
	if(validateSaveLiquidationsResponse(data)){
		notifyLiquidations('Saved successfully', false);
	}else{
        if (data.validationErrors.length == 0){
            notifyLiquidations('Save failed!', true);
        }
		revertToLastTab (lastTabForAjaxSave);
	}
	
	// cleanup popup references
	processPopupPostSave ();
}

/* Validates the server response to SAVE operation */
function validateSaveLiquidationsResponse(data){
	
	// check validation errors
	var validationError = '';
	for(var err in data.validationErrors){
		if(err == null) continue;
		validationError += data.validationErrors[err];
		validationError += "<br/>";
	}
	
	if(validationError.length>0){ 
		notifyLiquidations(validationError, true);
		return false;
	}

	// was operation successful on server?
	if(data.response==null || data.response.status==null || data.response.status != 'success') return false;
	
	// display errors in error grid, if present
	if((data.errors != null)){
		updateErrorsGrid(data.errors);
	}
	
	return true;
}

/* 
 * Utility method to make an Ajax call to the server and 
 * invoke the callback method upon receiving successful response
 */ 
function serverRequestExpenseLiquidations(serviceUrl, _data, callbackMethod){
	dojo.xhrPost({
		url: serviceUrl,
		handleAs: "json",
		content: _data,
		sync: true,
		handle: function(data,args){
					callbackMethod(data);
				},
				// Call this function if an error happened
				error: function (error) {
						notifyLiquidations("Save failed", true);
							var tab = dijit.byId('expense_tab_container');
							if (tab.selectedChildWidget.id != 'liquidationsTab'){
								tab.selectChild('liquidationsTab');
							}
							return true;
				}
	});
}

function serverRequestExpenseLiquidationsInquiry(serviceUrl, _data, callbackMethod){
	dojo.xhrPost({
		url: serviceUrl,
		handleAs: "json",
		content: _data,
		sync: true,
		handle: function(data,args){
					callbackMethod(data);
				}
	});
}
