/*
 * Common Coding Block code that is used for Advances and Expenses. Both ExpenseCodingBlock.js
 * and AdvanceCodingBlock.js files refer to code in this file. There is commented code in this file
 * that should be removed after a couple of iterations of testing
 * 
 * 08/31/2009
 * 
 */

/**
 * Creates and initializes a new HTML table and creates the header row according to coding block 
 * options. This is the entry point to creating the widget. 
 */

var storeApprYear = "";
//var blankIndexStore = dojo.fromJson("{index:[{'id':0,'code':'','name':'','appr_year':'58','agency':'','display':''}]}");
var blankStore = dojo.fromJson("[{'index':[{'id':0,'code':'','name':'','appr_year':'','agency':'','display':''}]},{'pca':[{'id':0,'code':'','name':'','appr_year':'','agency':'','display':''}]}]");
var apprYearStores = [];
var storeDataLoaded = false;

function initializeTable(cbOptions) {
	var table;
	if (document.all)
		table = document.all["cbTable"];
	else
		table = document.getElementById("cbTable");
	var tBody = document.createElement('TBODY');
	var tHeader = document.createElement('THEAD');
	var tHeadRow = document.createElement('TR');
	var oRow, oCell;
	var heading = new Array();
	heading[0] = "Pct";
	heading[1] = "AY";
	heading[2] = "Index";
	heading[3] = "PCA";
	heading[4] = "Grant";
	heading[5] = "Ph";
	heading[6] = "AG1";
	heading[7] = "Project";
	heading[8] = "Ph";
	heading[9] = "AG2";
	heading[10] = "AG3";
	heading[11] = "Multi";
	heading[12] = "Std";

	table.appendChild(tHeader);
	table.appendChild(tBody);
	oRow = document.createElement("TR");
	oRow.id = "cb_header";
	tHeader.appendChild(oRow);
	cBlockOptions = cbOptions;
	// Create and insert cells into the header row.
	for ( var j = 0; j < cbOptions.length; j++) {
		if (cbOptions[j].show == "Y") {
			oCell = document.createElement("TH");
			var headerValue = toProperCase(cbOptions[j].name.substring(0,
					cbOptions[j].name.length - 1));
			if (headerValue != "Del")
				oCell.innerHTML = headerValue;
			if (headerValue == "Grantphase" || headerValue == "Projectphase")
				oCell.innerHTML = "Ph";
			dojo.byId('cb_header').appendChild(oCell);
		}
	}

	return table;
}

/*
 * Displays a Coding Block row. Data in this row is initialized withe the store and existing CB data if any
 */

function constructCodingBlockCells(newRow, rowIndex, name, isNewRow, pctValue) {

	var displayValue = "";
	// text boxes
	if (name == "pct_" || name == "ay_" || name == "grantPhase_"
			|| name == "projectPhase_") {
		var cellName = name.substring(0, name.length - 1);

		if (name == "pct_" && !isNewRow)
			displayValue = cbSelectedData[newRow.rowIndex - 1].pct;
		else if (name == "pct_" && !rowInitialized)
			displayValue = "100.00";
		else if (name == "pct_" && rowInitialized)
			displayValue = pctValue;
		if (name == "ay_" && !isNewRow)
			displayValue = cbSelectedData[newRow.rowIndex - 1].ay;
		if (name == "grantPhase_" && !isNewRow)
			displayValue = cbSelectedData[newRow.rowIndex - 1].grantPhase;
		if (name == "projectPhase_" && !isNewRow)
			displayValue = cbSelectedData[newRow.rowIndex - 1].projectPhase;
		addTextBox(name + rowIndex, newRow, cellName, displayValue,
				newRow.childNodes.length);
	} else {
		// Dojo combo boxes
		var selectedCode;
		if (name == "index_") {
			if (!isNewRow && cbSelectedData[newRow.rowIndex - 1] != undefined)
				selectedCode = cbSelectedData[newRow.rowIndex - 1].index;
			else
				selectedCode = "";
			addDropDown(newRow, rowIndex, jsonResult.store[0].index,
					selectedCode, name, isNewRow, 0, 5);
		}
		if (name == "pca_") {
			if (!isNewRow && cbSelectedData[newRow.rowIndex - 1] != undefined)
				selectedCode = cbSelectedData[newRow.rowIndex - 1].pca;
			else
				selectedCode = "";
			addDropDown(newRow, rowIndex, jsonResult.store[1].pca,
					selectedCode, name, isNewRow, 0, 5);
		}

		if (name == "grant_") {
			if (!isNewRow &&  cbSelectedData[newRow.rowIndex - 1] != undefined)
				selectedCode = cbSelectedData[newRow.rowIndex - 1].grant;
			else
				selectedCode = "";
			addDropDown(newRow, rowIndex, jsonResult.store[2].grant,
					selectedCode, name, isNewRow, 0, 6)
		}
		if (name == "project_") {
			if (!isNewRow && cbSelectedData[newRow.rowIndex - 1] != undefined)
				selectedCode = cbSelectedData[newRow.rowIndex - 1].project;
			else
				selectedCode = "";
			addDropDown(newRow, rowIndex, jsonResult.store[4].project,
					selectedCode, name, isNewRow, 0, 6)
		}

		if (name == "ag1_") {
			if (!isNewRow && cbSelectedData[newRow.rowIndex - 1] != undefined)
				selectedCode = cbSelectedData[newRow.rowIndex - 1].ag1;
			else
				selectedCode = "";
			addDropDown(newRow, rowIndex, jsonResult.store[3].ag1,
					selectedCode, name, isNewRow, 0, 4);
		}

		if (name == "ag2_") {
			if (!isNewRow && cbSelectedData[newRow.rowIndex - 1] != undefined)
				selectedCode = cbSelectedData[newRow.rowIndex - 1].ag2;
			else
				selectedCode = "";
			addDropDown(newRow, rowIndex, jsonResult.store[5].ag2,
					selectedCode, name, isNewRow, 0, 4);
		}

		if (name == "ag3_") {
			if (!isNewRow && cbSelectedData[newRow.rowIndex - 1] != undefined)
				selectedCode = cbSelectedData[newRow.rowIndex - 1].ag3;
			else
				selectedCode = "";
			addDropDown(newRow, rowIndex, jsonResult.store[6].ag3,
					selectedCode, name, isNewRow, 0, 6);
		}

		if (name == "multi_") {
			if (!isNewRow && cbSelectedData[newRow.rowIndex - 1] != undefined)
				selectedCode = cbSelectedData[newRow.rowIndex - 1].multi;
			else
				selectedCode = "";
			addDropDown(newRow, rowIndex, jsonResult.store[7].multi,
					selectedCode, name, isNewRow, 0, 10);
		}

		if (name == "std_") {
			var stdCell = newRow.insertCell(newRow.childNodes.length);
			var std = "std_" + parseInt(rowIndex);
			var selectedStandard = "N";
			var dispStandard = false;
			if (!isNewRow) {
				selectedStandard = cbSelectedData[newRow.rowIndex - 1].std;
				(selectedStandard == "N") ? dispStandard = false
						: dispStandard = true;
			} else if (!rowInitialized) {
				dispStandard = true;
				rowInitialized = true;
			}
			// alert(selectedStandard);
			stdCell.innerHTML = "<input id='" + std
					+ "' dojotype='dijit.form.CheckBox' checked='"
					+ dispStandard + "' value='off' />";
			std = new dijit.form.CheckBox( {
				id :"std_" + rowIndex,
				name :"std",
				checked :dispStandard
			}, std);
			dojo.connect(dojo.byId("std_" + rowIndex), 'onclick', function() {
				disableControls(dojo.byId("std_" + rowIndex), rowIndex,
						cBlockOptions)
						disconnectTooltip(dijit.byId("std_" + rowIndex)); //kp
			});
		}
		if (name == "del_") {
			var buttonCell = newRow.insertCell(newRow.childNodes.length);
			var del = "del_" + rowIndex;
			buttonCell.innerHTML = "<input id='"
					+ del
					+ "' type='button' value='Del' onclick='rowDelete(this.id)' />";
		}

	}

}

/*
 * Adds and initializes a new text box
 */
function addTextBox(inputId, newRow, cellId, displayValue, colIndex) {
	cellId = newRow.insertCell(colIndex);
	//var inputName = inputId.substring(0, parseInt(inputId.length) - 2);
	var inputName = inputId.substring(0, inputId.indexOf('_'));
	var maxlength;
	var fieldStyle;
	if (inputName == "pct") {
		maxlength = 5;
		fieldStyle = 'width:50px';
	} else {
		maxlength = 2;
		fieldStyle = 'width:25px';
	}
	if (displayValue == "null")
		displayValue = "";

	cellId.innerHTML = "<input id='"
		+ inputId
			+ "' type='text' dojoType='dijit.form.ValidationTextBox' style='"
			+ fieldStyle
			+ "' name = '"
			+ inputName
			+ "' value = '"
			+ displayValue
			+ "' maxlength='"
			+ maxlength
			+ "' required='true' invalidMessage='Appropriation Year is required' promptMessage='Appropriation Year' />";
	if (inputName == "pct")
		dojo.connect(dojo.byId(inputId), 'onchange', function() {
			doPctValidate(inputId);
		});
	dojo.connect(inputId, 'onFocus', function() {
		toggleCheckBox(inputId);
	});
	if (inputName == "ay") {
		// dojo.connect(dojo.byId(inputId),'change',function()
		// {getNewStoreForApprYear(inputId);});
		dojo.connect(dojo.byId(inputId), 'change', function() {
			return checkAyInput(inputId);
		});
	}

}

/*
 * Called to construct a single combo box 
 */

function addDropDown(newRow, rowIndex, store, cbSelected, name, isNewRow,
		pctValue, maxLength) {

	var cbCell = newRow.insertCell(newRow.childNodes.length);
	var newStore = {};
	newStore.identifier = 'id';
	newStore.items = store;
	var selectedCode = "";
	if (!isNewRow)
		selectedCode = cbSelected;
	if (selectedCode == "null")
		selectedCode = "";
	addComboBox(name + rowIndex, name + rowIndex, name.substring(0,
			name.length - 1), newStore, "display", selectedCode, cbCell,
			maxLength);
	
	// Both Grants and Projects require phases. All others are simple Dojo combo boxes
	if (name == "grant_") {
		dojo.connect(dijit.byId(name + rowIndex), 'onChange', function() {
			getGrantPhase(dijit.byId(name + rowIndex), "");
		});
	}
	if (name == "project_") {
		dojo.connect(dijit.byId(name + rowIndex), 'onChange', function() {
			getProjectPhase(dijit.byId(name + rowIndex), "");
		});
	}
}

/*
 * Does the actual combo box construction
 */

function addComboBox(inputId, compId, name, store, displayValue, selectedValue,
		cellId, maxlength) {
		 
	cellId.innerHTML = "<input id='" + inputId
			+ "' size='75px' maxlength = '6' style='width:75px' />";
	rowIndex = inputId.substring(inputId.indexOf('_') + 1, inputId.length);

	datastore = new dojo.data.ItemFileReadStore( {
		data :store
	});
	inputId = new dijit.form.ComboBox( {
		id :compId,
		name :name,
		selectOnClick: true, 
		store :datastore,
        searchAttr :"display",
		value :selectedValue,
		maxLength :maxlength,
	    style:'width:75px;text-align:left'
		}, inputId);

		 
	// Adding the tooltip and substring the value of the selected combobox value. kp   
	 dojo.connect(inputId, 'onChange', function() {
		 addTooltip(inputId, compId, rowIndex);
	 }); 

 
	if (name == "grant" || name == "project")
		dijit.byId(inputId.attr('style', 'width:80px'));
	else
		dijit.byId(inputId.attr('style', 'width:75px'));
	// Needed for left-aligning text value in IE - this solution only works the first time around
	
/*	dojo.connect(inputId, 'onMouseOver', function() {
//		dijit.byId(inputId).comboNode.focus();
		
//		var foo=dijit.form.ValidationTextBox.displayMessage(message: "selected value."); 
				
	});*/
	dojo.connect(inputId, 'onFocus', function() {
		toggleCheckBox(inputId);
	});
	
// blank out stores for Index and PCA if no AY has been defined. The CB 
	// values will be balnk unless an AY has been entered.
	
	if (name == "index" && dojo.byId("ay_" + rowIndex).value != storeApprYear){
		loadStoreForIndex(inputId.id);
	}
	
	if (name == "pca" && dojo.byId("ay_" + rowIndex).value != storeApprYear) {
		loadStoreForPca(inputId.id);
	}

}

/**
 * @input params : inputId, compId, rowIndex. 
 * Generates the tooltip for all the selected combobox values. kp  
 *  
 */

function addTooltip(inputId, compId, rowIndex ){
	if(dijit.byId("std_" + rowIndex)!= undefined && !dijit.byId("std_" + rowIndex).checked ) {
		if (dijit.byId(compId+"_tt") == null && dijit.byId(compId).getDisplayedValue() != ""){
		var newSpan = new dijit.Tooltip({id:compId+"_tt",
	    connectId: [compId],
	    label: dijit.byId(inputId).value
	     });
		} else {
			newSpan = dijit.byId(compId+"_tt");
		}
      		 
		 if (inputId.getDisplayedValue().indexOf(' ') >= 0  ){
		 var temp = inputId.getDisplayedValue();
		 inputId.setDisplayedValue(inputId.getDisplayedValue().substring(0, inputId.getDisplayedValue().indexOf(' ')));
		 newSpan.label = temp;
		 
		}  
}
}


/*
 * Called to initiate row deletion
 */
function rowDelete(id) {
	// gather necessary data in order to delete a row
	var rowNo = id.substring(id.indexOf('_') + 1, id.length);
	var deletedRowId = "cb_" + rowNo;

	// destory all elements and therefore deregister from the DOM
	if (dijit.byId("index_" + rowNo) != undefined)
		dijit.byId("index_" + rowNo).destroy();
	if (dijit.byId("pca_" + rowNo) != undefined)
		dijit.byId("pca_" + rowNo).destroy();
	if (dijit.byId("grant_" + rowNo) != undefined)
		dijit.byId("grant_" + rowNo).destroy();
	if (dijit.byId("ag1_" + rowNo) != undefined)
		dijit.byId("ag1_" + rowNo).destroy();
	if (dijit.byId("ag2_" + rowNo) != undefined)
		dijit.byId("ag2_" + rowNo).destroy();
	if (dijit.byId("ag3_" + rowNo) != undefined)
		dijit.byId("ag3_" + rowNo).destroy();
	if (dijit.byId("project_" + rowNo) != undefined)
		dijit.byId("project_" + rowNo).destroy();
	if (dijit.byId("multi_" + rowNo) != undefined)
		dijit.byId("multi_" + rowNo).destroy();
	if (dijit.byId("std_" + rowNo) != undefined)
		dijit.byId("std_" + rowNo).destroy();

	// perform the actual row deletion
	deleteRowFromTable(deletedRowId);

	// Check the last row if it should be updated or a new row is to be added at the bottom
	if (isLastRowEmpty() && !isCurrentRowLastRow(id)) {
		updatelastRowPctAfterDelete();
	} else {
		var currentSum = addPct(false);
		var difference = (100 - currentSum).toFixed(2);
		if (difference > 0) {
			appendRow(document.getElementById("cbTable"), rowCount, difference);
		}
	}

	toggleFirstRowDeleteButton();
}

/*
 * Performs an actual row deletion from the HTML table
 */

function deleteRowFromTable(deletedRowId) {
	// get deleted row index from table
	var deletedRowNum;
	for ( var i = 0; i < dojo.byId('cbTable').rows.length; i++) {
		if (dojo.byId('cbTable').rows[i].id == deletedRowId) {
			deletedRowNum = i + 1;
			break;
		}
	}
	dojo.byId('cbTable').deleteRow(dojo.byId(deletedRowId).rowIndex);
	deletedRowCount++;
}

/*
 * Checks whether a new line should be added or if the last line is empty that can be updated with the
 * new percentage.
 */

function doPctValidate(elementId) {
	if (!validatePctAmount(elementId))
		return false;
	// do not insert a blank row if one already exists
	var lastRow = dojo.byId('cbTable').rows.length - 1;
	// round to 2 digits
		if (dojo.byId(elementId).value != "" && !isNaN(dojo.byId(elementId).value)){
			dojo.byId(elementId).value = parseFloat(dojo.byId(elementId).value).toFixed(2);
		}
	
	// compute sum and see if a new row is needed
	var sum = addPct(false);

	var pctDifference = (100 - sum).toFixed(2);
	if (isLastRowEmpty() && !isCurrentRowLastRow(elementId) && parseFloat(pctDifference) != 0) {
			updatelastRowPct(pctDifference);
	} else {
		if (pctDifference > 0){
			var pctDifference = 100 - parseFloat(sum)
			appendRow(document.getElementById("cbTable"), rowCount,
					pctDifference.toFixed(2));
		}
	}

	// }
}

/*
Computes the new total percentage 
 */

function addPct(withoutLastRow) {
	var tableId = dojo.byId('cbTable');
	var noOfCodingBlocks = tableId.rows.length - 1;
	if (withoutLastRow) {
		noOfCodingBlocks = noOfCodingBlocks - 1;
	}
	var sumPct = 0;
	var i = 0;

	for (i; i < noOfCodingBlocks; i++) {
		// var suffix = getLastRowIdNumber();
		// var pctElement;
		var cbRow = dojo.byId('cbTable').rows[i + 1];
		// var pctElement = cbRow.getElementsByTagName("pct*");
		var pctElement = cbRow.childNodes[0].childNodes[0];
		if (pctElement.value != "" && !isNaN(pctElement.value))
			sumPct += parseFloat(pctElement.value);
	}

	return sumPct.toFixed(2);
}

/*
 * Filps the Delete button for the first row between enabled and disabled. The delete button for first 
 * row must be disabled if there is only one CB row. The button is enabled if there is more than
 * one CB row
 */

function toggleFirstRowDeleteButton() {
	if (dojo.byId('cbTable').rows.length > 2) {
		dojo.byId('del_' + getFirstRowIdNumber()).disabled = false;
	} else {
		dojo.byId('del_' + getFirstRowIdNumber()).disabled = true;
	}
}

/*
 * User to obtain the index (not the CB index) for the last row in the table
 * when inserting or deleting rows
 * 
 */
function getLastRowIdNumber() {
	var lastRowNumber = dojo.byId('cbTable').rows.length - 1;
	var idCbRow = dojo.byId('cbTable').rows[lastRowNumber].id;
	var rowIdNumber = idCbRow.substring(idCbRow.indexOf('_') + 1,
			idCbRow.length);
	if (rowIdNumber > 0)
		return rowIdNumber;
	else
		return -1;
}

/**
 * User to obtain the index (not the CB index) for the first row in the table
 * when inserting or deleting rows
 */
function getFirstRowIdNumber() {
	var idCbRow = dojo.byId('cbTable').rows[1].id;
	var rowIdNumber = idCbRow.substring(idCbRow.indexOf('_') + 1,
			idCbRow.length);
	if (rowIdNumber > 0)
		return rowIdNumber;
	else
		return -1;
}

/*
 * Checks to see if there are any fields populated other than the pct field.
 */

function isLastRowEmpty() {
	var lastRowId = getLastRowIdNumber();
	var stdElement = dojo.byId("std_" + lastRowId);
	if (stdElement.checked) {
		return false;
	} else {
		var concatString = getConcatString(lastRowId);

		if (concatString == "")
			return true;
		else
			return false;
	}
	return true;
}

function isRowEmpty (rowId){
		var stdElement = dojo.byId("std_" + rowId);
		if (stdElement.checked) {
			return false;
		} else {
			var concatString = getConcatString(rowId);
			if (concatString != ""){
				return false
			}
		}
		return true;
}

function getConcatString (rowId){
	var concatString = "";
	if (dojo.byId("ay_" + rowId) == null)
		return;
	concatString += dojo.byId("ay_" + rowId).value;
	if (dojo.byId("index_" + rowId) != undefined)
		concatString += dojo.byId("index_" + rowId).value.substring(0, 5);
	if (dojo.byId("pca_" + rowId) != undefined)
		concatString += dojo.byId("pca_" + rowId).value.substring(0, 5);
	if (dojo.byId("grant_" + rowId) != undefined)
		concatString += dojo.byId("grant_" + rowId).value.substring(0, 6);
	if (dojo.byId("grantPhase_" + rowId) != undefined)
		concatString += dojo.byId("grantPhase_" + rowId).value;
	if (dojo.byId("ag1_" + rowId) != undefined)
		concatString += dojo.byId("ag1_" + rowId).value.substring(0, 4);
	if (dojo.byId("project_" + rowId) != undefined)
		concatString += dojo.byId("project_" + rowId).value.substring(0, 6);
	if (dojo.byId("projectPhase_" + rowId) != undefined)
		concatString += dojo.byId("projectPhase_" + rowId).value;
	if (dojo.byId("ag2_" + rowId) != undefined)
		concatString += dojo.byId("ag2_" + rowId).value.substring(0, 4);
	if (dojo.byId("ag3_" + rowId) != undefined)
		concatString += dojo.byId("ag3_" + rowId).value.substring(0, 6);
	if (dojo.byId("multi_" + rowId) != undefined)
		concatString += dojo.byId("multi_" + rowId).value.substring(0, 10);
	
	return concatString;
}

/*
 * Used to setup last row
 */

function isCurrentRowLastRow(elementId) {
	var lastRowNumber = getLastRowIdNumber();
	var suffix = elementId.substring(elementId.indexOf('_') + 1,
			elementId.length);
	if (lastRowNumber == suffix) {
		return true;
	} else {
		return false;
	}
}

/*
 * Flips the Std button kp 
 */

function toggleCheckBox(inputId) {
	rowIndex = inputId.id.substring(inputId.id.indexOf('_') + 1, inputId.id.length);
	if (dijit.byId("std_" + rowIndex).checked){
		dijit.byId("std_" + rowIndex).attr('checked', false);
		disconnectTooltip(inputId); 
	}
}


/**
 * @param : inputId.
 * Removes the tooltip once for any empty values. kp 
 */

function disconnectTooltip(inputId){
	rowNo = inputId.id.substring(inputId.id.indexOf('_') + 1, inputId.id.length);
	if(!dijit.byId("std_" + rowNo)!= undefined) { 	
		destroyTooltip("index_",rowNo );
		destroyTooltip("pca_",rowNo );
		destroyTooltip("grant_",rowNo );
		destroyTooltip("ag1_",rowNo );
		destroyTooltip("ag2_",rowNo );
		destroyTooltip("ag3_",rowNo );
		destroyTooltip("project_",rowNo );
		destroyTooltip("multi_",rowNo );
				
	}
}


/**
 * @param : coding block specific id. kp 
 * removes the tooltip for empty coding block values on selection of standard coding block. 
 * 
 */
function destroyTooltip(id, rowNo ){
if (dijit.byId(id + rowNo) != undefined){
if(dijit.byId(id + rowNo+"_tt") != null) {
dijit.byId(id + rowNo+"_tt").destroy();
 }
}
 }

function updatelastRowPct(pctDifference) {
	var sum;

	if (pctDifference == 0){
		dojo.byId("pct_" + getLastRowIdNumber()).value = 0.00;
	} else if (pctDifference < 0){
			sum = addPct(true);
			if (sum <= 100){
			dojo.byId("pct_" + getLastRowIdNumber()).value = 
				(parseFloat(dojo.byId("pct_" + getLastRowIdNumber()).value) + parseFloat(pctDifference)).toFixed(2);
			} else {
				dojo.byId("pct_" + getLastRowIdNumber()).value = 0.00;
			}
	}	else {
	var currentValue = dojo.byId("pct_" + getLastRowIdNumber()).value;
	var newPct = parseFloat(currentValue) + parseFloat(pctDifference);
	if (newPct <= 100 && newPct > 0)
		dojo.byId("pct_" + getLastRowIdNumber()).value = newPct.toFixed(2);
	}
}

function updatelastRowPctAfterDelete() {
	var sum = addPct(true);
	var difference = 100 - sum;
	if (difference > 0 && difference <= 100) {
		dojo.byId("pct_" + getLastRowIdNumber()).value = difference.toFixed(2);
	}
}

function toProperCase(header) {
	if (header == "ay")
		return header.toUpperCase("AY");
	if (header == "pca")
		return header.toUpperCase("PCA");
	if (header == "ag1")
		return header.toUpperCase("AG1");
	if (header == "ag2")
		return header.toUpperCase("AG2");
	if (header == "ag3")
		return header.toUpperCase("AG3");
	return header.charAt(0).toUpperCase()
			+ header.substring(1, header.length).toLowerCase();
}

function getGrantPhase(control, grantStore) {
	// var selectedGrantNo =
	// dijit.byId(control.id).attr('displayedValue').substring(2);
	// var code = grantStore.code;
	selectedGrantNo = dijit.byId(control.id).value.substring(0, dijit
			.byId(control.id).value.indexOf(" "));
	
	// Added the following code after Combo box changes.  kp
	
	if(selectedGrantNo ==  "")
	{ 
	selectedGrantNo = dijit.byId(control.id).value; 
	}
	
		if (selectedGrantNo < 0)
		selectedGrantNo = dijit.byId(control.id).value;
	dojo.xhrPost( {
		url :"CodingBlockGrantPhase.action",
		handleAs :"json",
		load : function(data) {
			loadGrantPhase(control, data);
		},
		content : {
			grantNo :selectedGrantNo
		}
	});
}

function loadGrantPhase(control, jsonData) {

	var indexNo = control.id.substring(control.id.length - 1);

	grantPhase = "grantPhase_" + indexNo;
	grantPhaseName = "grantPhase";
	var newGrantPhaseStore = {};
	newGrantPhaseStore.identifier = 'id';
	newGrantPhaseStore.items = jsonData.response;
	var datastore = new dojo.data.ItemFileReadStore( {
		data :newGrantPhaseStore
	});
	var selectedGrantPhase = "";
	// addComboBox("grantPhase_"+indexNo,"grantPhase_"+indexNo,"chosenGrantPhaseCode",newGrantPhaseStore,"display",selectedGrantPhase,"grantPhase");
	if (dijit.byId(grantPhase) != null && dijit.byId(grantPhase).declaredClass == 'dijit.form.ComboBox')
		dijit.byId(grantPhase).attr('store', datastore);
	else {
	grantPhase = new dijit.form.ComboBox( {
		id :grantPhase,
		name :grantPhaseName,
		store :datastore,
		searchAttr :"grant_phase",
		value :selectedGrantPhase,
		maxLength :2
	}, grantPhase);
	}
	dijit.byId(grantPhase).attr('style', 'width:55px');
}

function getProjectPhase(control, projectStore) {
	// rowProjectIdx = rowIndex;
	var selectedProjectNo = dijit.byId(control.id).value.substring(0, dijit
			.byId(control.id).value.indexOf(" "));
	
	// Getting the project phase. 
	
	if(selectedProjectNo == "")
	{  
	selectedProjectNo = dijit.byId(control.id).value; 	
	}
	if (selectedProjectNo < 0)
		selectedProjectNo = dijit.byId(control.id).value;
	// var code = projectStore.code;
	dojo.xhrPost( {
		url :"CodingBlockProjectPhase.action",
		handleAs :"json",
		load : function(data) {
			loadProjectPhase(control, data);
		},
		content : {
			projectNo :selectedProjectNo
		}
	});
}

function loadProjectPhase(control, jsonData) {
	var indexNo = control.id.substring(control.id.length - 1);
	projectPhase = "projectPhase_" + indexNo;
	projectPhaseName = "projectPhase";
	var newProjectPhaseStore = {};
	newProjectPhaseStore.identifier = 'id';
	newProjectPhaseStore.items = jsonData.response;
	var datastore = new dojo.data.ItemFileReadStore( {
		data :newProjectPhaseStore
	});
	var selectedProjectPhase = "";
	if (dijit.byId(projectPhase) != null && dijit.byId(projectPhase).declaredClass == 'dijit.form.ComboBox')
		dijit.byId(projectPhase).attr('store', datastore);
	else {
	projectPhase = new dijit.form.ComboBox( {
		id :projectPhase,
		name :projectPhaseName,
		store :datastore,
		searchAttr :"project_phase",
		value :selectedProjectPhase,
		maxLength :2
	}, projectPhase);
	}
	dijit.byId(projectPhase).attr('style', 'width:55px');
}

// Method to get the new store for the given year

//Method to get the new store for the given year

function getNewStoreForApprYear(element) {
	// format AY string correctly to form a dijit ID. The formatted id, e.g. ay_1
	// or just the row number may be passed in by
	element = element +'';
	if ((element + '').indexOf("_") < 0)
		element = "ay_" + element;
  
	  var selectedApprYear = dojo.byId(element).value;
	  if (selectedApprYear != storeApprYear){
		  		  // not current AY
		  var dataForStore = getApprYearStoreFromLocalCache(selectedApprYear);
	      if ( dataForStore != null){
	    	  // load from cache
	    	  loadStoreForApprYear(element, dataForStore);
	    	  return;
	      }
	      
	  // AY not current year or store values not available from local cache
	  // This is a synchronous/blocking Ajax call
	  dojo.xhrPost( { url: "CodingBlockApprYear.action",
	  sync: true,
	  handleAs: "json", 
	  load: function(data) {
	  // add new AY values to local cache
	  addApprYearStoreToLocalCache (selectedApprYear, data.response);
	  storeDataLoaded = true;
	  // change stores for Index and PCA
	  loadStoreForApprYear(element,data.response); },
	  content:{ apprYear:
	  selectedApprYear }
	  });
	  } else {
		  // AY is current year. Load index and pca
		  var rowNo = element.substring(element.indexOf('_') + 1, element.length);
		  if (dijit.byId("index_" + rowNo) != undefined){
     		 loadStoreForIndex(element, jsonResult.store[0].index);
	  }
		  
		  if (dijit.byId("pca_" + rowNo) != undefined){
			  loadStoreForPca(element, jsonResult.store[1].pca);
		  }
	  }
	 
}


// Method to load the new store for the given appropriation year

function loadStoreForApprYear(element, data) {
	loadStoreForIndex(element, data[0].index);
	loadStoreForPca(element, data[1].pca);
}

//Method to load the new store for the given appropriation year

function loadStoreForIndex(element, store) {
	if (cBlockOptions[2].show != "Y")
		return;

	// set index dropdown store
	if (store != undefined && store.length > 0){
	var indexData = store;
	} else {
		indexData =  blankStore[0].index;
	}
	var rowIndex = element.substring(element.indexOf('_') + 1, element.length);
	var indexStore = new dojo.data.ItemFileReadStore( {
		data : {
			identifier :"id",
			items :indexData
		}
	});
	dijit.byId('index_' + rowIndex).attr('store', indexStore);
}

//Method to load the new store for the given appropriation year

function loadStoreForPca(element, store) {
	if (cBlockOptions[3].show != "Y")
		return;

	// set index dropdown store
	if (store != undefined && store.length > 0){
	var pcaData = store;
	} else {
		pcaData =  blankStore[1].pca;
	}
	var rowIndex = element.substring(element.indexOf('_') + 1, element.length);
	var pcaStore = new dojo.data.ItemFileReadStore( {
		data : {
			identifier :"id",
			items :pcaData
		}
	});
	dijit.byId('pca_' + rowIndex).attr('store', pcaStore);
}
function disableControls(checkBoxControl, rowIndex, codingBlockOptions) {

	if (checkBoxControl.checked) {
		var rowIndex = checkBoxControl.id.substring(checkBoxControl.id
				.indexOf('_') + 1, checkBoxControl.id.length);

		for ( var i = 0; i < codingBlockOptions.length; i++) {
			if (codingBlockOptions[i].show == "Y" && codingBlockOptions[i].name == "index_")
				dijit.byId("index_" + rowIndex).attr('value', "");
			if (codingBlockOptions[i].show == "Y" && codingBlockOptions[i].name == "pca_")
				dijit.byId("pca_" + rowIndex).attr('value', "");
			if (codingBlockOptions[i].show == "Y"
					&& codingBlockOptions[i].name == "grant_")
				dijit.byId("grant_" + rowIndex).attr('value', "");
			if (codingBlockOptions[i].show == "Y"
					&& codingBlockOptions[i].name == "grantPhase_") {
				if (dijit.byId("grant_" + rowIndex).value == "null"
						|| dijit.byId("grant_" + rowIndex).value == "")
					dojo.byId("grantPhase_" + rowIndex).value = "";
				else
					dijit.byId("grantPhase_" + rowIndex).attr('value', "");
			}
			if (codingBlockOptions[i].show == "Y"
					&& codingBlockOptions[i].name == "project_")
				dijit.byId("project_" + rowIndex).attr('value', "");

			if (codingBlockOptions[i].show == "Y"
					&& codingBlockOptions[i].name == "projectPhase_") {
				if (dijit.byId("project_" + rowIndex).value == "null"
						|| dijit.byId("project_" + rowIndex).value == "")
					dojo.byId("projectPhase_" + rowIndex).value = "";
				else
					dijit.byId("projectPhase_" + rowIndex).attr('value', "");
			}
			if (codingBlockOptions[i].show == "Y"
					&& codingBlockOptions[i].name == "ag1_")
				dijit.byId("ag1_" + rowIndex).attr('value', "");
			if (codingBlockOptions[i].show == "Y"
					&& codingBlockOptions[i].name == "ag2_")
				dijit.byId("ag2_" + rowIndex).attr('value', "");
			if (codingBlockOptions[i].show == "Y"
					&& codingBlockOptions[i].name == "ag3_")
				dijit.byId("ag3_" + rowIndex).attr('value', "");
			if (codingBlockOptions[i].show == "Y"
					&& codingBlockOptions[i].name == "multi_")
				dijit.byId("multi_" + rowIndex).attr('value', "");
			if (codingBlockOptions[i].show == "Y"
					&& codingBlockOptions[i].name == "ay_")
				dojo.byId("ay_" + rowIndex).value = "";
		}
		// reset checkbox to checked, since unchecked by toggleCheckbox when AY
		// or dropdowns change
		dijit.byId("std_" + rowIndex).attr('checked', true);	
	}
}

function resetApprYearStore (){
	// reset store for all rows in the table if the selected appropriation year is different that the current 
	// year
	var numRows = dojo.byId("cbTable").rows.length;
	for (var i = 1; i < numRows ; i++){
		if (dojo.byId("ay_" + i) != null && dojo.byId("ay_" + i).value != null){
			var ayField = dojo.byId("ay_" + i).value;
			if (ayField != "" && ayField != storeApprYear){
					getNewStoreForApprYear(i);
			}
		}
	}
}


/**
 * formats coding block errors to display the correct CB line number. The CB line number is derived
 * 
 * @param cbErrors
 * @return
 */

function formatCodingBlockErrorsAjax (cbErrors){
	var cbTable = dojo.byId("cbTable");
	if (cbTable == null)
		// CB tab not loaded yet
		return;
	var cbFromErrorSource = "";
	// loop though all errors and attempt to a specific CB row with the same concatenated CB as reported in the
	// error source
	
	dojo.forEach(cbErrors, function(item){
		cbFromErrorSource = item.source.substring((item.source.indexOf("-") + 2), 
				item.source.length);
			for (var j = 1; j < noOfCodingBlocks + 1; j++){
				var nextRowNumber = getNextCbRowSuffix(j);
				var concatCB = getConcatString(nextRowNumber);

				if (cbFromErrorSource.split(' ').join('') == concatCB){
					   // Row with error located, format display source 
					// Row with error located, format display source 
						item.source = item.source.substring(0, (item.source.indexOf("-") + 2));
						item.source += " " + j;
						break;
				}	
			}
		
	});	    			
}

/**
 * formats coding block errors to display the correct CB line number. The CB line number is derived
 * 
 * @param cbErrors
 * @return
 */

function formatCodingBlockErrorsAdvanceAjax (cbErrors){
	var cbTable = dojo.byId("cbTable");
	var cbFromErrorSourceAdvance = "";

	dojo.forEach(cbErrors, function(item){
		cbFromErrorSourceAdvance = item.source;
		if (cbFromErrorSourceAdvance != "Advance"){
			for (var j = 1; j < noOfCodingBlocks + 1; j++){
				var nextRowNumber = getNextCbRowSuffix(j);
				var concatCB = getConcatString(nextRowNumber);

				if (cbFromErrorSourceAdvance.split(' ').join('') == concatCB){
					   // Row with error located, format display source 
						item.source = "CB Line " + j;
						break;
				}	
			}
		}
		
	});
	updateErrorsGrid(cbErrors);    				    			    			
}


/**
 * Builds/adds to a local array for store values of any appropriation year other than
 * the current year. These values change for index and PCA only accross different AYs
 * @param apprYear
 * @param dataForStore
 * @return
 */

function addApprYearStoreToLocalCache (apprYear, dataForStore){
	var element = new Object();
	element.apprYear = apprYear;
	element.dataForStore = dojo.toJson(dataForStore);
	apprYearStores.push(element);
}

/**
 * Retrieves store values for a given appropriation year.
 * @param apprYear
 * @return
 */

function getApprYearStoreFromLocalCache (apprYear){
	var numElements = apprYearStores.length;
	for (var i = 0 ; i < numElements; i++){
		var element = apprYearStores[i];
		if (element.apprYear == apprYear){
			return dojo.fromJson(apprYearStores[i].dataForStore);
		}
	}
	return null;
}

function sleep(interval){
	var t1 = new Date ();
	var t1Secs = t1.getTime();
	var timeout = t1Secs + interval;

	while (true){
	var t2 = new Date();
	var t2Secs = t2.getTime();
	if (t2Secs > timeout)
	break;
	}
}

//Validate the appropriation year
function checkAyInput(element){
	// ZH - Fixed defect # 17, blank out stores before attempting to load store for changed AY
	loadStoreForIndex(element);
	loadStoreForPca(element);

	var ayValue = dojo.byId(element).value;
	if(ayValue=="" || ayValue==null){
		dojo.byId(element).focus();
		return false;
	}
	else{
		if(isNaN(ayValue)){
			showErrorMessage("Please enter a valid 2 digit Appropriation Year");
			return false;
		}
		else{
			showErrorMessage("");
			getNewStoreForApprYear(element);
		}
	}
}

function trimCbElements(noOfCodingBlocks){
	for(var i=0;i < noOfCodingBlocks;i++){
		var rowIdSuffix = i + 1;
		trimCbElement("index_", rowIdSuffix);
		trimCbElement("pca_", rowIdSuffix);
		trimCbElement("grant_", rowIdSuffix);
		trimCbElement("grantPhase_", rowIdSuffix);
		trimCbElement("project_", rowIdSuffix);
		trimCbElement("projectPhase_", rowIdSuffix);
		trimCbElement("ag1_", rowIdSuffix);
		trimCbElement("ag2_", rowIdSuffix);
		trimCbElement("ag3_", rowIdSuffix);
		trimCbElement("multi_", rowIdSuffix);
	}
}

function trimCbElement(cbElement, rowIdSuffix){
	var rowIdSuffix = getNextCbRowSuffix(rowIdSuffix);
	var element = dojo.byId(cbElement + rowIdSuffix);
	var stdChecked = dojo.byId("std_" + rowIdSuffix).checked;
	if (element != undefined && !stdChecked && element.value.length > 0 ){
		element.value = element.value.trim();
	}
	return true;
}

function isRowLast(rowId) {
	var lastRowId = getLastRowIdNumber();
	if (rowId == lastRowId)
		return true;
	else
		return false;		
}

/**
 * Determines if any CB options are to be displayed.
 * @return
 */

function checkTkuOptions(){
	for ( var i = 2; i < (cBlockOptions.length - 2); i++) {
			if (cBlockOptions[i].show == "Y"){
				return true;
			}
	}
	return false;
}

/**
 * Changes mode for CB row to read-only if no CB options are enabled
 * @return
 */
function setSingleStdRowReadOnly (){
	if (!checkTkuOptions ()){
			dojo.byId("pct_1").readOnly = true;
		    dojo.byId("ay_1").readOnly = true;
		    dijit.byId("std_1").readOnly = true;
	}
}

