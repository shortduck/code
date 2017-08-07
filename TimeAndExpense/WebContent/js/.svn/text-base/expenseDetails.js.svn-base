// global variable to refer to the expense details grid.
var expenseDetailsGrid;

// global variable to refer to the expense details grid's store.
var expenseDetailsGridStore;

// variable to track max expense detail line no
var maxExpenseLineNo;

// variable to track current commonMiles for the selected From & To locations
var currentCommonMiles = 0;

// variable to track onClick event handler for calendar 
var expenseDetailDateOnClick;

// temporary variable to store expense type CB key
var _temp_expenseTypeCBKey='';

//temporary variable to store expense type standard rate
var _temp_expenseTypeStandardRate = 0;

// cache to maintain latest common miles
var commonMileageCache;

var pageEventHandlers = [];

var expenseTypesCB;

// A constant representing time in milliseconds for server session refresh call
var SERVER_SESSION_REFRESH_TIME = 25*60*1000;

// variable to track time of most recent user activity
var userLastKeypressTime;

/* 
 * Invoked upon expense detail tab load. Fetches the expense line items from the server
 * and shows them in the grid
 */
function initExpenseDetailsGrid(){
	
	// Only create the calendar control, if field is enabled.
	// Otherwise the calendar control should be created when the
	// fields are first enabled i.e. when MODIFY is clicked.
   
	if(dojo.byId('modifyBtn').disabled) constructDetailsCalendarControl(); 
	
	// populate revision no
	populateRevNoInDetails();
	
	// construct depart time and return time drop down
	constructTimeComponents();
	
	//construct Overnight dropdown
	constructOvernight();
	
	// find all view data except expense types
	serverRequestExpenseDetailsInquiry("FindExpenseDetails.action", null, prepareView);
	
	// attach counter to textfields marked with 'counter' class
	processTextFieldCounters();
}

//populate expense details
function populateDefaultDetailDate(){
	dojo.byId('fromDate').value = dojo.byId('f_fromDateField').value;
	dojo.byId('fromDate').focus();
}

function constructDetailsCalendarControl(){
	initSingleMultiDateSelectionCalendar();	
}

// populate revision no
function populateRevNoInDetails(){
	dojo.byId('ed_revNo').innerHTML = dojo.byId('revNo').innerHTML;
}

//construct Overnight dropdown
function constructOvernight(){
	
	var _overnightData = {};
	_overnightData.identifier = 'overnight';
	_overnightData.items = [{overnight:''}, {overnight:'Yes'}, {overnight:'No'}];
	var overnightStore = new dojo.data.ItemFileWriteStore({data: _overnightData});
	
	var overnight = new dijit.form.FilteringSelect({id: "overnight", name: "overnight", searchAttr: 'overnight',  
		store: overnightStore, style:"width:5em", displayedValue:''}, 'overnight');	
}

// Constructs depart time and return time drop down
function constructTimeComponents(){
	
	var _timeRange = [];
	for(var _mins=0 ; _mins< 60 ; _mins+=5){
		var _minStr = (_mins<10)? '0'+_mins : _mins+'';
		_timeRange.push({'time': 12+':'+_minStr});
	}
	for(var hrs=1 ; hrs<12 ; hrs++){
		for(var mins=0 ; mins< 60 ; mins+=5){
			var minStr = (mins<10)? '0'+mins : mins+'';
			_timeRange.push({'time': hrs+':'+minStr});
		}
	}
	_timeRange.push({'time': '11'+':'+'59'});
	var _data = {};
	_data.identifier = 'time';
	_data.items = _timeRange;
	var timeRangeStore = new dojo.data.ItemFileWriteStore({data: _data});

	departureTimeCB = new dijit.form.ComboBox({id: "departureTime", name: "departureTime", searchAttr: 'time',  
		store: timeRangeStore, value: '', displayedValue:'', style:"width:6em"}, 'departureTime');

	returnTimeCB = new dijit.form.ComboBox({id: "returnTime", name: "returnTime", searchAttr: 'time',  
		store: timeRangeStore, value: '', displayedValue:'', style:"width:6em"}, 'returnTime');

	var _phaseData = {};
	_phaseData.identifier = 'phase';
	_phaseData.items = [{phase:''}, {phase:'AM'}, {phase:'PM'}];
	var timePhaseStore = new dojo.data.ItemFileWriteStore({data: _phaseData});
	
	var departureTimePhase = new dijit.form.FilteringSelect({id: "departureTimePhase", name: "departureTimePhase", searchAttr: 'phase',  
		store: timePhaseStore, value: 'AM', displayedValue:'AM', style:"width:5em"}, 'departureTimePhase');
		
	var returnTimePhase = new dijit.form.FilteringSelect({id: "returnTimePhase", name: "returnTimePhase", searchAttr: 'phase',  
		store: timePhaseStore, value: 'PM', displayedValue:'PM', style:"width:5em"}, 'returnTimePhase');
}

/* Invoked when user selects a row in the grid */
function processExpenseDetailsGridRowSelection(event){
	var selExpDtl = expenseDetailsGrid.selection.getSelected()[0];
	
	refreshExpenseDetailFormData(selExpDtl);
}

/* Clears all selected rows in grid and clears the input form */
function resetExpenseDetailsForm(){
	// clear grid selection
	expenseDetailsGrid.selection.deselectAll();
	
	// reset form fields
	refreshExpenseDetailFormData(null);
}

/* Clears the form information for expense type, miles, amount and comments */
function partialResetExpenseDetailsForm(){
	expenseTypesCB.attr('value', '');
	dijit.byId('miles').attr('value', '0');
	dijit.byId('vicinityMiles').attr('value', '0');
	dojo.byId('commonMilesInd').innerHTML = 'NO';
	dijit.byId('reimbursementAmount').attr('value', '');
	dojo.byId('ed_suggestedAmt').value = '';
	dojo.byId('expenseDetailComments').value = '';

	// trigger 'onchange' event
	fireEvent(dojo.byId('expenseDetailComments'), 'change');	
	dijit.byId('totalMiles').attr('value',0);	
	dojo.byId('hiddenExpenseTypeCode').value = '';
	
}


/* Removes the selected expense detail from the grid */
function deleteExpenseDetail(){
	// this automatically deletes the row from the underlying store 
	// & genereates notifications for grid.
	expenseDetailsGrid.removeSelectedRows();
	
	// reset form fields
	resetExpenseDetailsForm();
	
	// trigger attached event processing
	refreshExpenseDetailAmount();
	
	// set form state to dirty if not marked so already
	if (!getFormModifiedState()){
		setChanged();
	}
	
}

function isNotNull(obj){
	return (obj != null)?true:false;
}

function connectEvents(){
	if(expenseDetailsGrid != null) pageEventHandlers.push(dojo.connect(expenseDetailsGrid, 'onRowClick', 'processExpenseDetailsGridRowSelection'));
	
	var expTypNode = dijit.byId('expenseType_cb');
	if(expTypNode != null) pageEventHandlers.push(dojo.connect(expenseTypesCB, 'onChange', handleExpenseTypeChange));
	
	pageEventHandlers.push(dojo.connect(dojo.byId('fromDate'), 'onchange', handleExpenseDetailDateChange));
	pageEventHandlers.push(dojo.connect(dojo.byId('modifyBtn'), 'onclick', function(){setupDetailsViewUponModification()}));
	pageEventHandlers.push(dojo.connect(dijit.byId('miles'), 'onFocus', function(){formatNumericSeparators('miles')}));
	pageEventHandlers.push(dojo.connect(dijit.byId('vicinityMiles'), 'onFocus', function(){formatNumericSeparators('vicinityMiles')}));
	pageEventHandlers.push(dojo.connect(dijit.byId('reimbursementAmount'), 'onFocus', function(){formatNumericSeparators('reimbursementAmount')}));
	pageEventHandlers.push(dojo.connect(dijit.byId('miles'), 'onChange', function(){handleMileChange('miles')}));
	pageEventHandlers.push(dojo.connect(dijit.byId('vicinityMiles'), 'onChange', function(){handleMileChange('vicinityMiles')}));
	pageEventHandlers.push(dojo.connect(dijit.byId('fromCity_cb'), 'onChange', function(item){handleFromCityChange(item)}));
	pageEventHandlers.push(dojo.connect(dijit.byId('fromState_cb'), 'onChange', function(item){handleFromStateChange(item)}));
	pageEventHandlers.push(dojo.connect(dijit.byId('toCity_cb'), 'onChange', function(item){handleToCityChange(item)}));
	pageEventHandlers.push(dojo.connect(dijit.byId('toState_cb'), 'onChange', function(item){handleToStateChange(item)}));
	pageEventHandlers.push(dojo.connect(dojo.byId('roundTrip'), 'onclick', function(){handleRoundTripChange(dojo.byId('roundTrip').checked)}));
	
	//overnight dropdown change event
	pageEventHandlers.push(dojo.connect(dijit.byId('overnight'), 'onChange', function(item){handleOvernightChange(item)}));
	
	// special event to ensure that a user working with data ONLY on client side (i.e. not performing any interaction with the server)
	// is not kicked out by the server after 30 min server session timeout. So, we keep the time of latest user interaction. 
	//dojo.connect(dojo.byId('ed_form').containerNode, 'keypress', function(){userLastKeypressTime = new Date();});
	window.onkeypress = handleUserKeyPress;
	
}

function handleUserKeyPress(){
	userLastKeypressTime = new Date();
}

function disconnectEvents(){
	dojo.forEach(pageEventHandlers, dojo.disconnect);
	
	// Reinitialize the handler array as all previous entries are now eligible for GC.
	pageEventHandlers = [];
}

function isExpenseTypePresentInStore(expenseTypeDescription){
	
	var returnValue = false;
	var store = dijit.byId('expenseType_cb').store;
	store.fetch({ query: { desc: expenseTypeDescription },  
	               onItem: function(item) {	            	  
	                  returnValue =  true;
	               }
	});
	
	if(returnValue) 
		return true;
		
	return false;
}

/*
 * Updates the form with the data from the provided expense detail.
 * If argument is NULL, then the fields are reset
 */
function refreshExpenseDetailFormData(selExpDtl){
	// disconnect all events
	disconnectEvents();

	// clear out any error notifications
	notifyExpenseDetails('', false);
	
	var validDetail = isNotNull(selExpDtl);
	
	if(validDetail && isNotNull(selExpDtl.expenseDate) && isNotNull(selExpDtl.expenseDate[0]))  dojo.byId('fromDate').value = selExpDtl.expenseDate[0];
	else dojo.byId('fromDate').value='';
	if(validDetail && isNotNull(selExpDtl.fromCity) && isNotNull(selExpDtl.fromCity[0])) dijit.byId('fromCity_cb').attr('value',selExpDtl.fromCity[0]);
	else dijit.byId('fromCity_cb').attr('value','');
	if(validDetail && isNotNull(selExpDtl.fromState) && isNotNull(selExpDtl.fromState[0])) dijit.byId('fromState_cb').attr('value', selExpDtl.fromState[0]);
	else dijit.byId('fromState_cb').attr('value', '');
	if(validDetail && isNotNull(selExpDtl.toCity) && isNotNull(selExpDtl.toCity[0])) dijit.byId('toCity_cb').attr('value',selExpDtl.toCity[0]); 
	else dijit.byId('toCity_cb').attr('value', '');
	if(validDetail && isNotNull(selExpDtl.toState) && isNotNull(selExpDtl.toState[0])) dijit.byId('toState_cb').attr('value', selExpDtl.toState[0]);
	else dijit.byId('toState_cb').attr('value', '');
	if(validDetail && isNotNull(selExpDtl.roundTrip) && isNotNull(selExpDtl.roundTrip[0])) dojo.byId('roundTrip').checked = selExpDtl.roundTrip[0];
	else dojo.byId('roundTrip').checked = false;
	if(validDetail && isNotNull(selExpDtl.departTime) && selExpDtl.departTime.length > 0 && isNotNull(selExpDtl.departTime[0]) ){ 
		dojo.byId('departureTime').value = selExpDtl.departTime[0].substring(0, selExpDtl.departTime[0].length-3);
		dojo.byId('departureTimePhase').value = selExpDtl.departTime[0].substring(selExpDtl.departTime[0].length-2);
	}else{
		 dojo.byId('departureTime').value='';
		 dojo.byId('departureTimePhase').value='AM';
	}
	if(validDetail && isNotNull(selExpDtl.returnTime) && selExpDtl.returnTime.length > 0 && isNotNull(selExpDtl.returnTime[0])){
		 dojo.byId('returnTime').value = selExpDtl.returnTime[0].substring(0, selExpDtl.returnTime[0].length-3);
		 dojo.byId('returnTimePhase').value = selExpDtl.returnTime[0].substring(selExpDtl.returnTime[0].length-2);
	}
	else{
		 dojo.byId('returnTime').value ='';
		 dojo.byId('returnTimePhase').value='PM';
	}
	
	//overnight
	if(validDetail && isNotNull(selExpDtl.overnightInd) && isNotNull(selExpDtl.overnightInd[0])) dojo.byId('overnight').value = selExpDtl.overnightInd[0];
	else dojo.byId('overnight').value='';
	
	//This is for firing overnight onChnage event, to get incidental expenses if the value is "Yes" 
	
	handleOvernightChange(null);
	
	if(dijit.byId('expenseType_cb') != null){
		if(validDetail && isNotNull(selExpDtl.expenseType) && isNotNull(selExpDtl.expenseType[0])){
			
			if (isExpenseTypePresentInStore(selExpDtl.expenseTypeDesc[0]) == true){
				dijit.byId('expenseType_cb').attr('value', selExpDtl.expenseType[0]);
			
				//if the user clicks on the BLD in the grid, the selExpDtl.expenseType[0] will not match-up with the value 
				//in the expense_type drop down. In such case, we will get the value from the categoryCode column (last column) of the grid.
				
				if (dijit.byId('expenseType_cb').value == null || dijit.byId('expenseType_cb').value == ''){
					if(validDetail && isNotNull(selExpDtl.categoryCode) && isNotNull(selExpDtl.categoryCode[0])){
						dijit.byId('expenseType_cb').attr('value', selExpDtl.categoryCode[0]);
					}
				}
			}
			else{
				//If the expense is no longer add the expense back to the drop down temporary
				
				var newItem = {};
				newItem.code = "1" ;
				newItem.desc = selExpDtl.expenseTypeDesc[0];
				newItem.isMileageRelated = false; 				
				newItem.isMealRelated = false;
//				dijit.byId('expenseType_cb').store = prepareReadStore(newItem, 'code');
				dijit.byId('expenseType_cb').set( "store", prepareReadStore(newItem, 'code'));
				
				dojo.byId('expenseType_cb').value = selExpDtl.expenseTypeDesc[0];
			}
		}
		else dijit.byId('expenseType_cb').attr('value', '');
		
		// store value in temp variable, so that CB may be initialized with it upon rendering
		_temp_expenseTypeCBKey = dijit.byId('expenseType_cb').attr('value');
	}else{
		if(validDetail && isNotNull(selExpDtl.expenseType) && isNotNull(selExpDtl.expenseType[0])){ 
			dojo.byId('expenseType_cb').value = selExpDtl.expenseTypeDesc[0];
			
			//if the user clicks on the BLD in the grid, the selExpDtl.expenseType[0] will not match-up with the value 
			//in the expense_type drop down. In such case, we will get the value from the categoryCode column (last column) of the grid.
			
			if (dijit.byId('expenseType_cb').value == null || dijit.byId('expenseType_cb').value == ''){
				if(validDetail && isNotNull(selExpDtl.categoryCode) && isNotNull(selExpDtl.categoryCode[0])){
					dijit.byId('expenseType_cb').attr('value', selExpDtl.categoryCode[0]);
				}
			}
		}
		
		else dojo.byId('expenseType_cb').value = '';
		
		// store value in temp variable, so that CB may be initialized with it upon rendering
		_temp_expenseTypeCBKey = selExpDtl.expenseType[0];
	}
	
	
	if(validDetail && isNotNull(selExpDtl.miles) && isNotNull(selExpDtl.miles[0])) dijit.byId('miles').attr('value', selExpDtl.miles[0]);
	else dijit.byId('miles').attr('value', '0');
	if(validDetail && isNotNull(selExpDtl.vicinityMiles) && isNotNull(selExpDtl.vicinityMiles[0])) dijit.byId('vicinityMiles').attr('value', selExpDtl.vicinityMiles[0]);
	else dijit.byId('vicinityMiles').attr('value', '0');
	// ZH - Fixed defect # 966 - Added a 'true' check for common miles
	if(validDetail && isNotNull(selExpDtl.commonMiles) && isNotNull(selExpDtl.commonMiles[0])
			&& selExpDtl.commonMiles[0] == true){
		 dojo.byId('commonMilesInd').innerHTML = 'YES';
		 // set current common miles for correct reimbursement amt calculation & correct behavior across miles & common miles
		 //PS: 'currentCommonMiles' is always the ONE WAY trip value. If selected line item is a round trip, reduce the common miles accordingly
		 currentCommonMiles = dojo.byId('roundTrip').checked ? dijit.byId('miles').attr('value')/2 : dijit.byId('miles').attr('value');
	}else{ 
		dojo.byId('commonMilesInd').innerHTML = 'NO';
		// reset current common miles for correct reimbursement amt calculation & correct behavior across miles & common miles
		currentCommonMiles = 0;
	}
	if(validDetail && isNotNull(selExpDtl.amount) && isNotNull(selExpDtl.amount[0])) dijit.byId('reimbursementAmount').attr('value', selExpDtl.amount[0]);
	else dijit.byId('reimbursementAmount').attr('value', '');
	if(validDetail && isNotNull(selExpDtl.comments) && isNotNull(selExpDtl.comments[0])) dojo.byId('expenseDetailComments').value = selExpDtl.comments[0];
	else dojo.byId('expenseDetailComments').value = '';
	
	if (!validDetail)
		dojo.byId("SelectCityIndicator").innerHTML = "" ;
	
	// trigger 'onchange' event
	fireEvent(dojo.byId('expenseDetailComments'), 'change');
	
	// total miles is computed field & never comes from grid data source
	var totalMiles = dijit.byId('miles').attr('value') + dijit.byId('vicinityMiles').attr('value');
	dijit.byId('totalMiles').attr('value',totalMiles);

	// execute display logic blocks
	modifyRoundTripIndicatorViewState();

	modifyMilesAndAmtViewState();
	
	// find exp type standard rate
	findCurrentExpenseTypeStandardRate();
		
	// find common miles(no post processing; just the common miles value) & set it to global commonMiles field
	currentCommonMiles = findCommonMilesWithoutEffects();
	
	// update suggested amount
	calculateSuggestedAmount();
	
	// reconnect all events
	connectEvents();

}

function modifyMilesAndAmtViewState(){
	var expTypeNode = dijit.byId('expenseType_cb');
	
	if(expTypeNode == null || expTypeNode.item == null) return;
	
	var miles = dijit.byId('miles');
	var vicinityMiles = dijit.byId('vicinityMiles');
	var reimbursementAmt = dijit.byId('reimbursementAmount');
// ZH - AI 21412, added status check
	if(expTypeNode.item.isMileageRelated[0] && !dojo.byId('saveBtn').disabled){
		if(miles != null){ 
			miles.attr('disabled', false); 
			miles.attr('readOnly', false); 
		}
		if(vicinityMiles != null){ 
			vicinityMiles.attr('disabled', false); 
			vicinityMiles.attr('readOnly', false); 
		}
		if(reimbursementAmt != null){ 
			reimbursementAmt.disabled = false; 
			reimbursementAmt.attr('readOnly', true); 
		}
	}else{
		if(miles != null){ 
			miles.attr('disabled', true); 
			miles.attr('readOnly', true); 
		}
		if(vicinityMiles != null){ 
			vicinityMiles.attr('disabled', true);
			vicinityMiles.attr('readOnly', true); 
		}
		if(reimbursementAmt != null){ 
			reimbursementAmt.disabled = false; 
			reimbursementAmt.attr('readOnly', false); 
		}
	}
}

/* Finds standard reimbursement rate for the current expense type */
function findCurrentExpenseTypeStandardRate(){
	_temp_expenseTypeStandardRate = 0;
	
	var expTypeNode = dijit.byId('expenseType_cb');
	if(expTypeNode == null || expTypeNode.item == null || expTypeNode.item.code == null || expTypeNode.item.code[0] == null
			|| trimStr(expTypeNode.item.code[0]) == "" || trimStr(dojo.byId("fromDate").value).length < 1) return;
	
	var minMaxDate = getMinMaxExpenseDetailDate();

	var requestData = {};
	requestData.expenseTypeCode = expTypeNode.item.code[0];
	requestData.startDate = minMaxDate.minDate;
	requestData.endDate = minMaxDate.maxDate;
	
	//set values of the flags viz. OutOfState, Overnight, etc.
	var chkOutOfStateCheckBox = dojo.byId("outOfStateCheckBox");
	
	if(chkOutOfStateCheckBox != null){
		if (chkOutOfStateCheckBox.checked == true)
			requestData.isOutOfState = "Y";
		else
			requestData.isOutOfState = "N";
	}
	
	requestData.isOvernight = dojo.byId("overnight").value;
	
	requestData.toCity  = dijit.byId('toCity_cb').attr('value') ;
	requestData.toState = dijit.byId('toState_cb').attr('value');
	requestData.expenseDate = dojo.byId("fromDate").value;
	requestData.isMealRelated  = getExpenseMealValue(); //expTypeNode.item.isMealRelated[0];
	
	serverRequestExpenseDetailsInquiry("FindExpenseTypeStandardRate.action", requestData, function(data){
		if(data.response.rate != null && data.response.rate.length == 1){
			_temp_expenseTypeStandardRate = data.response.rate[0].rateAmt;
			   
			//For BLD, we will have the correct expense type after "FindExpenseTypeStandardRate.action"
			//We will assign the value to the  'hiddenExpenseTypeCode'
			//ultimately this value will be inserted to the expense_type column in Grid's 
			var txtHiddenExpenseTypeCode  = dojo.byId('hiddenExpenseTypeCode');
			if (txtHiddenExpenseTypeCode != null){
				txtHiddenExpenseTypeCode.value = data.response.rate[0].expTypeCode;				   
			}
		}
	});
}

/*
 * Prepares the page during load time
 * 
 * @param response
 * @return
 */
function prepareView(data){
	if(data.response.expenseDetails != null && data.response.expenseDetails.items.length > 0){
		updateExpenseDetailsGrid(data.response.expenseDetails.items);
		dojo.byId('totalExpenseAmtId').value = data.response.expenseDetails.totalAmount.toFixed(2).toString();
	}else{
		var emptyItems = [];
		updateExpenseDetailsGrid(emptyItems);
		dojo.byId('totalExpenseAmtId').value = '0.00';
	}
	
	if(data.response.outstandingAdvance != null)
		dojo.byId('advOutstandingAmtId').value = data.response.outstandingAdvance.toFixed(2).toString();
	
	// execute the hooked up code
	processHooks(data.response);
}

/* 
 * Updates the grid with the new data and triggers any attached
 * post processing hooks
 */
function updateExpenseDetailsGrid(details){
	expenseDetailsGrid = dijit.byId('expenseDetailsGrid');
	expenseDetailsGridStore = prepareWriteStore(details);
	
	expenseDetailsGrid.setStore(expenseDetailsGridStore);
	
	// fix any misalignments
	expenseDetailsGrid.resize();
	
	maxExpenseLineNo = getMaxLineItemNo(details);
}

/* Sets up view state of GUI components */
function setupExpenseDetailsViewState(view){
	// SETUP COMPONENTS THAT DO NOT DERIVE VIEW STATE
	if(view.supervisorReview == "DISABLED"){
		dojo.byId('supervisorReceiptsReviewed').disabled = true;
	}else if(view.supervisorReview == "ENABLED"){
		dojo.byId('supervisorReceiptsReviewed').disabled = false;
	}
	
	if(view.audit == "DISABLED"){
		dojo.byId('auditCompleteBtn').disabled = true;
	}else if(view.audit == "ENABLED"){
		dojo.byId('auditCompleteBtn').disabled = false;
	}else if(view.audit == "HIDDEN"){
		dojo.byId('auditCompleteBtn').style.display = 'none';
	}
	
	// SETUP COMPONENTS THAT DERIVE VIEW STATE
	dojo.byId('ed_prevRevBtn').disabled = dojo.byId('prevRevBtn').disabled;
	dojo.byId('ed_nextRevBtn').disabled = dojo.byId('nextRevBtn').disabled;
	
	// if the SAVE button on TripId is disabled, 
	// then all form fields on details must also be disabled.
	var tripIdSaveBtnDisabled = dojo.byId('saveBtn').disabled;
	disableExpenseDetailsFormViewState(tripIdSaveBtnDisabled);
	disableSaveExpenseDetailsButton(tripIdSaveBtnDisabled);
	
	// setup defaults
	setupExpenseDetailDefaults();
	
}

/* Set each component's state in expense details form depending upon the boolean passed.
 * TRUE => fields must be disabled
 * FALSE => fields must be enabled
 */
function disableExpenseDetailsFormViewState(state){
	dojo.byId('ed_add').disabled = state;
	dojo.byId('ed_delete').disabled = state;
	dojo.byId('fromDate').disabled = state;
	
	// disable calendar button
	var calField = dojo.byId('fromDateSelector');
	if(state){
		expenseDetailDateOnClick = calField.onclick;
		calField.onclick = null;
	}else{
		if(expenseDetailDateOnClick != null) calField.onclick = expenseDetailDateOnClick;
	}
	
	dijit.byId('fromCity_cb').attr('disabled', state);
	dijit.byId('fromState_cb').attr('disabled', state);
	dijit.byId('toCity_cb').attr('disabled', state);
	dijit.byId('toState_cb').attr('disabled', state);
	
	dojo.byId('roundTrip').disabled = state;
	dijit.byId('overnight').attr('disabled', state);
	dijit.byId('departureTime').attr('disabled', state);
	dijit.byId('departureTimePhase').attr('disabled', state);
	dijit.byId('returnTime').attr('disabled', state);
	dijit.byId('returnTimePhase').attr('disabled', state);
	dijit.byId('expenseType_cb').attr('disabled', state);
	dijit.byId('miles').attr('disabled', state);
	dijit.byId('vicinityMiles').attr('disabled', state);
	dijit.byId('totalMiles').attr('disabled', state);
	dijit.byId('reimbursementAmount').attr('disabled', state);
	dijit.byId('fromCity_cb').attr('style','font-color: #FF0000;font-weight:bold');
	
    //dojo.byId('expenseDetailComments').disabled = state;
	var ed_comments = dojo.byId('expenseDetailComments');
	if(ed_comments != null){
		if(state){
			ed_comments.readOnly = true;
			ed_comments.style.color = 'gray';
		}else{
			ed_comments.readOnly = false;
			ed_comments.style.color = 'black';
		}
	}

	dojo.byId('applyExpenseDetailBtn').disabled = state;
	
	//overnight
	dijit.byId('overnight').attr('disabled', state);
}

function disableSaveExpenseDetailsButton(state){
	dojo.byId('saveExpenseDetailsBtn').disabled = state;
}

/* Sets sensible defaults to aid faster expense detail entry */
function setupExpenseDetailDefaults(){
	
	
	// If expense type is 	PDF, set From & To location to be NA
	if (dojo.byId('PDFCheckBox').checked){
		//dijit.byId('overnight').attr('disabled', false); 
		dijit.byId('fromCity_cb').attr('value', "N/A");
		dijit.byId('fromState_cb').attr('value', "NA");
		dijit.byId('toCity_cb').attr('value', "N/A");
		dijit.byId('toState_cb').attr('value', "NA");
	}
	// If expense type is non-travel,disable overnight option, set From & To location to be NA
	else if(dojo.byId('expenseTypeN').checked){
		dijit.byId('overnight').attr('disabled', true); 
		dijit.byId('fromCity_cb').attr('value', "N/A");
		dijit.byId('fromState_cb').attr('value', "NA");
		dijit.byId('toCity_cb').attr('value', "N/A");
		dijit.byId('toState_cb').attr('value', "NA");
	}
	
	else{
		//dijit.byId('overnight').attr('disabled', false); 
		dijit.byId('fromCity_cb').attr('value', "");
		dijit.byId('fromState_cb').attr('value', "");
		dijit.byId('toCity_cb').attr('value', "");
		dijit.byId('toState_cb').attr('value', "");
	}
}



/* Constructs the expense types drop down and attaches the required event handlers. */
function setupExpenseTypes(data){
	expenseTypesCB = constructExpenseTypesDropDown(data);
	
	// initialize to the value if already populated
	expenseTypesCB.attr('value', _temp_expenseTypeCBKey);
} 

/* Handler for expense detail date */
function handleExpenseDetailDateChange(){
	var expTypeNode = dijit.byId('expenseType_cb');
	if(expTypeNode == null || expTypeNode.item == null 
		|| expTypeNode.item.code == null || expTypeNode.item.code[0] == null) return;
	
	calculateExpenseTypeStandardRate(expTypeNode.item.code[0]);
		
	// set focus back to date field
	dojo.byId('fromDate').focus();
	
	selectCityLookup();
}

/* Handler for expense type change */
function handleExpenseTypeChange(expenseTypeCode){
	
	console.trace();
	
	// If user enters invalid expense type, 
	// reset reimbursement amount to be ZERO
	//PS: miles changes are handled in subsequent chained calls 
	if(expenseTypeCode == null || expenseTypeCode==""){ 
		dojo.byId('ed_suggestedAmt').value = '';
	}
	
	calculateExpenseTypeStandardRate(expenseTypeCode);
	lookupCommonMiles();
	
	//If the expense type is non-mileage related, disable common miles indicator
	dijit.byId('expenseType_cb').store.fetchItemByIdentity({
		identity:expenseTypeCode, 
		onItem: function(item, request){
			if(!item.isMileageRelated[0]){
				dojo.byId('commonMilesInd').innerHTML = "NO";
			}else{
				// only enable the round trip check box. It's value would already have been set
				// by the "lookupCommonMiles()" above. Same is true for common miles indicator.
				dojo.byId('roundTrip').disabled = false;
			}
		}
	});
	
	// update '_temp_expenseTypeCBKey' with current value
	_temp_expenseTypeCBKey = expenseTypeCode;
}

function calculateExpenseTypeStandardRate(expenseTypeCode){

	// as soon as a change is detected, the previous exp types 
	// standard rate is no longer valid. Therefore, reset it.
	_temp_expenseTypeStandardRate = 0;
	
	if(expenseTypeCode == null || trimStr(expenseTypeCode) == "") return;
	
	//clear out any previous messages
	notifyExpenseDetails('', false);

	var requestData = {};
	
	var minMaxDate = getMinMaxExpenseDetailDate();

	requestData.expenseTypeCode = expenseTypeCode;
	requestData.startDate = (minMaxDate.minDate == null) ? Date.today().toString("MM/dd/yyyy") : minMaxDate.minDate;
	requestData.endDate = (minMaxDate.maxDate == null) ? Date.today().toString("MM/dd/yyyy") : minMaxDate.maxDate;
	
	//set values of the flags viz. OutOfState, Overnight, etc.
	var chkOutOfStateCheckBox = dojo.byId("outOfStateCheckBox");
	
	if(chkOutOfStateCheckBox != null){
		if (chkOutOfStateCheckBox.checked == true)
			requestData.isOutOfState = "Y";
		else
			requestData.isOutOfState = "N";
	}
	
	var isOvernight = dojo.byId("overnight").value;
	if (isOvernight == "Yes"){
		requestData.isOvernight = "Y";
	} 
	else if (isOvernight == "No") {
		requestData.isOvernight = "N";
	}

	requestData.toCity  = dijit.byId('toCity_cb').attr('value') ;
	requestData.toState = dijit.byId('toState_cb').attr('value');
	requestData.expenseDate = dojo.byId("fromDate").value;	
	requestData.isMealRelated = getExpenseMealValue();
	
	serverRequestExpenseDetailsInquiry("FindExpenseTypeStandardRate.action", requestData, function(data){
		if(data.response.rate == null){			
			_temp_expenseTypeStandardRate = 0;
		}else{
			// if multiple rates found			
			if(data.response.rate.length > 1){
				var warnMsg = 'Reimbursement rate changed for the selected expense type on ';
				var firstRun = true;
				dojo.forEach(data.response.rate, function(item){
					if(!firstRun){
						warnMsg += item.startDate + ', ';
					}
					firstRun = false;
				});
	
				warnMsg = warnMsg.substring(0, warnMsg.length-2);
				warnMsg += '. Please submit separate expenses for each expense date.';
				
				notifyExpenseDetails(warnMsg, true);
				
				// reset standard rate
				_temp_expenseTypeStandardRate = 0;
				
				// reset expense type to blank
				dijit.byId('expenseType_cb').attr('value', '');
				return;
		   }			
		   // if exactly one rate found
		   else if(data.response.rate.length == 1){
			   _temp_expenseTypeStandardRate = data.response.rate[0].rateAmt;
			   
			   //For BLD, we will have the correct expense type after "FindExpenseTypeStandardRate.action"
			   //We will assign the value to the  'hiddenExpenseTypeCode'
			   //ultimately this value will be inserted to the expense_type column in Grid's 
			   var txtHiddenExpenseTypeCode  = dojo.byId('hiddenExpenseTypeCode');
			   if (txtHiddenExpenseTypeCode != null){
				   txtHiddenExpenseTypeCode.value = data.response.rate[0].expTypeCode;				
			   }
		   }
		   // no rate found
		   else{
			   _temp_expenseTypeStandardRate = 0;
			   var warnMsg = "Can't find rates for this expense type for the expense date. Please contact OFM help Desk.";
			   notifyExpenseDetails(warnMsg, true);
		   }

		   setupMilesViewState(expenseTypesCB.item); 
		   handleMileChange("miles");
		}
	});
}

function getMinMaxExpenseDetailDate(){
	var minMaxDate = {};
	var minDate;
	var maxDate;

	dojo.forEach(dojo.byId("fromDate").value.split(/,./), function(item){
		var currDate = Date.parse(trimStr(item));
		
		if(currDate == null || currDate.length < 1) return;
		
		if(minDate == null && maxDate == null){
			 minDate = currDate;
			 maxDate = currDate;
		}else if(currDate < minDate) minDate = currDate;
		else if(currDate > maxDate) maxDate = currDate;
	});

	minMaxDate.minDate = (minDate == null) ? null : minDate.toString("MM/dd/yyyy");
	minMaxDate.maxDate = (maxDate == null) ? null : maxDate.toString("MM/dd/yyyy");
	
	return minMaxDate;
}

/* Disables miles fields and sets common miles indicator to false, 
 * if the expense type is non mileage related 
 */
function setupMilesViewState(item){
	var miles = dijit.byId('miles');
	var vicinityMiles = dijit.byId('vicinityMiles');
	var amt = dijit.byId('reimbursementAmount');
	
	// If mileage <-> non-mileage change, then update miles/vicinity if common miles present from earlier user selection
	// But if user had modified the common miles from previous selection, then leave those miles/vicinity as is.
	if(item!= null && item.isMileageRelated[0]){
		if (parseFloat(miles.attr('value')) == 0 || miles.attr('value') == "" 
				|| miles.attr('value') == currentCommonMiles
				|| (dojo.byId('roundTrip').checked && miles.attr('value') == Math.round(currentCommonMiles*2*100)/100)) {
			
			//AI-21691: If total miles(miles + vicinity) > 0, then 
			//do not update; overwrite user's 0 miles, aka miles with default miles from database.
			if (parseInt(miles.attr('value')) + parseInt(vicinityMiles.attr('value')) == 0 ){
				if (dojo.byId('roundTrip').checked) {
					miles.attr('value', currentCommonMiles * 2);
				} else {
					miles.attr('value', currentCommonMiles);
				}
			}
		}
		if (!dojo.byId('saveBtn').disabled){
			vicinityMiles.attr('disabled', false);
			vicinityMiles.attr('readOnly', false);
			
			miles.attr('disabled', false);
			miles.attr('readOnly', false);
			selectField(dojo.byId('miles'));
		}
		
		amt.attr('readOnly', true);
		
	}else{
		dijit.byId('miles').attr('value', 0);
		dijit.byId('vicinityMiles').attr('value', 0);
		dijit.byId('totalMiles').attr('value', 0);
		dojo.byId('commonMilesInd').innerHTML = "NO";
		
		miles.attr('disabled', true);
		miles.attr('readOnly', true);
		vicinityMiles.attr('readOnly', true);
		amt.attr('readOnly', false);

		vicinityMiles.attr('disabled', true);
		dojo.byId('reimbursementAmount').focus();
		dojo.byId('reimbursementAmount').select();
	}
}

/* Event handler for expense type change. All the data related to the selection is passed along. */
function updateReimbursementAmount(selection){
	dijit.byId('expenseType_cb').store.fetchItemByIdentity({
		identity:_temp_expenseTypeCBKey, onItem: function(item, request){
		var isOldExpTypeMileageRelated;		
		if (item != null)
			isOldExpTypeMileageRelated = item.isMileageRelated[0];
		else
			isOldExpTypeMileageRelated = false;
		
		var isNewExpTypeMileageRelated = dijit.byId('expenseType_cb').item.isMileageRelated[0];
		
		var projectedReimbursementRate = 0;
		var standardRate = _temp_expenseTypeStandardRate;
		if(isNewExpTypeMileageRelated){
			var totalMiles = dijit.byId('totalMiles').attr('value');
			totalMiles = (totalMiles == null || isNaN(totalMiles)) ? 0 : totalMiles;
			projectedReimbursementRate = totalMiles * standardRate;
		}else{
			projectedReimbursementRate = standardRate;
		}

		// round of the amount to 2 decimal places.
		// Reimbursement amount should reset while moving between mileage <-> non-mileage exp type.
		var reimbursementAmtNode = dijit.byId('reimbursementAmount');
		if(isOldExpTypeMileageRelated != isNewExpTypeMileageRelated){
			reimbursementAmtNode.attr('value', '0');
			if (isNewExpTypeMileageRelated){
				selectField(dojo.byId('miles'));
			} else {
				selectField(dojo.byId('reimbursementAmount'));
			}
		}
		if(isNewExpTypeMileageRelated){
			reimbursementAmtNode.attr('value', Math.round(projectedReimbursementRate*100)/100);
		}
		
		// update suggested amount
		calculateSuggestedAmount();
		}
	});
}

// Calculates and updates suggested amount
function calculateSuggestedAmount(){
	dojo.byId('ed_suggestedAmt').value = (_temp_expenseTypeStandardRate <=0) ? 'N/A' : _temp_expenseTypeStandardRate;	
}

/* Gets the max linItemNo value from expense details */
function getMaxLineItemNo(details){
	var max = 0;
	
	dojo.forEach(details, function(item){
		if(item.lineItemNo[0] > max)
			max = item.lineItemNo[0];
	});
	
	return max;
}

/* Save the expense details on the server */
function saveExpenseDetails(){
		
	// If TripId information not entered, raise alert. 
	if(dojo.byId('expenseEventId').value.length < 1)
	{
		notifyExpenseDetails('Please save Trip Id information before entering expense details.', true);
		return;
	
	// If save not required, return.
	}else if(!expenseDetailsGridStore.isDirty()){
		notifyExpenseDetails('Expense details unmodified! Skipping save.', false);
		setUnchanged();
		return false;
	}
	
	// save grid store, to make its state consistent
	expenseDetailsGridStore.save();
	setUnchanged();
	// now send the details in the grid to the server
	var expDtlsArray = (dojo.fromJson(expenseDetailsGridStore._getNewFileContentString())).items;
	var data = {expenseDetailsJson : dojo.toJson(expDtlsArray)};
	serverRequestExpenseDetails("SaveExpenseDetails.action", data, expenseDetailsStoreSaveCallback);
		
	return false;
}

/* Callback method invoked whenever expense details grid store is saved */
function expenseDetailsStoreSaveCallback(data){
	var problemDuringSave = false;
	// if validation errors exist, display them.
	if(data.validationErrors.errors != null){
		problemDuringSave = true;
		// switch back to previous tab if applicable
		revertToLastTab (lastTabForAjaxSave);
		notifyExpenseDetails(data.validationErrors.errors[0], true);
		return;
	}
	
	// if error occurred, show msg & stop processing
	if(data.response.expenseDetails == null){	
		notifyExpenseDetails('Save failed', true);
		return true;
	}
	
	// if success, then update grid, show success msg
	updateExpenseDetailsGrid(data.response.expenseDetails.items);

	// show save confirmation
	notifyExpenseDetails('Saved successfully', false);

	// display errors in error grid, if present
	if((data.errors != null)){
		updateErrorsGrid(data.errors);
	}
	// mark the Coding Block tab to be refreshed
	tabsToReload.push('cbTab');
	
	// cleanup popup references
	processPopupPostSave ();

	return problemDuringSave;
};

/* Provides post processing after the page elements are rendered */
function processHooks(response){
	constructCityDropDown(response.cities);
	constructStateDropDown(response.state);

	dojo.byId('supervisorReceiptsReviewed').checked = response.supervisorReceiptsReviewed;
	setupExpenseTypes(response.expenseTypes);
	setupExpenseDetailsViewState(response.viewState);
	
	// if no expense details present, then populate default date
	if(response.expenseDetails != null && response.expenseDetails.items.length < 1) populateDefaultDetailDate();
	
	connectEvents();
	
	// initialize user last keypress time to NOW
	userLastKeypressTime = new Date();
	
	//set up timer for server session refresh after 25 mins
	setInterval("issueServerSessionRefreshCall()", SERVER_SESSION_REFRESH_TIME);
	
}

function issueServerSessionRefreshCall(){
	var timeSinceLastUserKeypress = (new Date()) - userLastKeypressTime;
	
	// if user pressed key within the last 25 mins issue server call
	if(timeSinceLastUserKeypress<SERVER_SESSION_REFRESH_TIME){
		dojo.xhrPost({url: "DoNothing.action"});
	}
}

/* Modifies details view upon MODFY button click on TripId */
function setupDetailsViewUponModification(){
	// enable form fields
	disableExpenseDetailsFormViewState(false);
	
	disableSaveExpenseDetailsButton(false);
	disableAuditCompleteButton(true);
}

function disableAuditCompleteButton(disable){
	var auditBtn = dojo.byId('auditCompleteBtn');
	if(auditBtn != null) auditBtn.disabled = disable;
}

/* Function to handle round trip indicator change */
function handleRoundTripChange(isChecked){
	var milesNode = dijit.byId('miles');

	// only effective for common miles. If not common miles, update common_mile_flag value and return
	if(dojo.byId('commonMilesInd').innerHTML != "YES"){	
		// if round trip clicked and mileage is twice of common miles, then enable Common Mile Flag
		if((isChecked && milesNode.attr('value') == currentCommonMiles*2)
			||(!isChecked && milesNode.attr('value') == currentCommonMiles)) 
			
			// only update common mileage indicator, if miles are greater than ZERO
			if(milesNode.attr('value') > 0) 
				dojo.byId('commonMilesInd').innerHTML = "YES";
		else
			dojo.byId('commonMilesInd').innerHTML = "NO";
		
		//PS: No change to mile field required, so return here after initiating suggested amt updation
		calculateSuggestedAmount();
		return;
	}
	if(isChecked){
		milesNode.attr('value', milesNode.attr('value')*2);
	}else{
		milesNode.attr('value', (milesNode.attr('value')/2).toFixed(0));
	}

	// initiate miles change post processing, to correctly update other fields
	handleMileChange();
}

/* Event handler for mile fields lost focus */
function handleMileChange(fieldName){
	var milesEL = dijit.byId('miles');
	var vicinityMilesEL = dijit.byId('vicinityMiles');
	var miles = milesEL.attr('value');
	var vicinityMiles = vicinityMilesEL.attr('value');
	
	if(miles == null || isNaN(miles)) dijit.byId('miles').attr('value', 0);
	if(vicinityMiles == null || isNaN(vicinityMiles)) dijit.byId('vicinityMiles').attr('value', 0);

	// refresh values as changed above
	miles = milesEL.attr('value');vicinityMiles = vicinityMilesEL.attr('value');
	
	var totalMiles = miles + vicinityMiles;
	dijit.byId('totalMiles').attr('value', totalMiles);
	
	//trigger common mileage indicator update if required
	var isRoundTrip = dojo.byId('roundTrip').checked;
	
	if(isRoundTrip){
		if(currentCommonMiles > 0 && miles == currentCommonMiles*2){ 
			dojo.byId('commonMilesInd').innerHTML = "YES";
			selectField(dojo.byId('miles'));
		}else{
			dojo.byId('commonMilesInd').innerHTML = "NO";
		}
	}else{
		if(currentCommonMiles > 0 && miles == currentCommonMiles){ 
			dojo.byId('commonMilesInd').innerHTML = "YES";
			selectField(dojo.byId('miles'));
		}else{
			dojo.byId('commonMilesInd').innerHTML = "NO";
		}
	}

	expenseTypeCB = dijit.byId('expenseType_cb');
	
	// trigger event to update reimbursement amt
	if(expenseTypeCB != null && expenseTypeCB.item != null){
		updateReimbursementAmount(expenseTypeCB.item);
	}
}

/* Executes logic upon From City change */
function handleFromCityChange(item){
	// Is out of state indicator checked on TripId ?
	var outOfStateTravel = dojo.byId('outOfStateCheckBox').checked;
	
	if(dijit.byId('fromCity_cb').attr('value') == "N/A" && dijit.byId('fromState_cb').attr('value') != "NA"){
		dijit.byId('fromState_cb').attr('value', "NA");
	}else if(!outOfStateTravel && dijit.byId('fromState_cb').attr('value') == ""){
		dijit.byId('fromState_cb').attr('value', "MI");
	}

	// disable round trip, if applicable
	modifyRoundTripIndicatorViewState();
	
	// perform common mileage lookup
	lookupCommonMiles();
}

/* Executes logic upon To City change */
function handleToCityChange(item){
	// Is out of state indicator checked on TripId ?
	var outOfStateTravel = dojo.byId('outOfStateCheckBox').checked;
	
	if(dijit.byId('toCity_cb').attr('value') == "N/A" && dijit.byId('toState_cb').attr('value') != "NA"){
		dijit.byId('toState_cb').attr('value', "NA");
	}else if(!outOfStateTravel && dijit.byId('toState_cb').attr('value') == ""){
		dijit.byId('toState_cb').attr('value', "MI");
	}

	// disable round trip, if applicable
	modifyRoundTripIndicatorViewState();
	
	// perform common mileage lookup
	lookupCommonMiles();
	
	//Action to check if the "To" City/State/Expense Date combination is a select city
	selectCityLookup();
	
	//Action to get meal type rates

	if(isExpenseTypeMealType(_temp_expenseTypeCBKey)){
		calculateExpenseTypeStandardRate(_temp_expenseTypeCBKey);
	}
}

//Validates dates format and value
function checkdate(input){	 
var validformat=/^\d{2}\/\d{2}\/\d{4}$/ // Basic check for format validity
	 var returnval= 0
	 if (!validformat.test(input.value)){
		 input.select();
		 return -1;
	 }
	 else{ // Detailed check for valid date ranges
	 var monthfield=input.value.split("/")[0]
	 var dayfield=input.value.split("/")[1]
	 var yearfield=input.value.split("/")[2]
	 var dayobj = new Date(yearfield, monthfield-1, dayfield)
	 if ((dayobj.getMonth()+1!=monthfield)||(dayobj.getDate()!=dayfield)||(dayobj.getFullYear()!=yearfield))
		 return -2
	 
	 return 0;
}
}


//Action to check if the "To" City/State/Expense Date combination is a select city
function selectCityLookup(){	

	var requestData = {};
	requestData.expenseDate = dojo.byId('fromDate').value;	
	// chandana
	var date=(requestData.expenseDate).split(","); 
	if(date.length == 1)
		requestData.expenseDate = dojo.byId('fromDate').value;
	else
		requestData.expenseDate=date[date.length-1];
	// chandana
	requestData.toCity = dijit.byId('toCity_cb').value;
	requestData.toState = dijit.byId('toState_cb').value;
	
	if (requestData.expenseDate == "" || requestData.toCity.length == 0 || requestData.toState.length == 0 || (date.length == 1 && checkdate(dojo.byId('fromDate')) != 0))
		return;
	
	serverRequestExpenseDetailsInquiry("isSelectCity.action", requestData, function(data){
		if(data.response != null){
			if(data.response.IsSelectCity == "select_city"){
				dojo.byId("SelectCityIndicator").innerHTML = "Select City";
			}
			else{
				dojo.byId("SelectCityIndicator").innerHTML = "";
			}
		}
		
		if (data.validationErrors.errors != null){ 
			notifyExpenseDetails(data.validationErrors.errors, true);
		}
		else 			
			notifyExpenseDetails('', true);
	});	
	
} 

/* Disables round trip indicator, if From City, To City, From State & To State all are NA */
function modifyRoundTripIndicatorViewState(){
	if(isFromToLocationNA()){
		dojo.byId('roundTrip').checked = false;
		dojo.byId('roundTrip').disabled = true;
	}else{
		dojo.byId('roundTrip').disabled = false;
	}

	// If expense type is not mileage related, 
	var expTypeNode = dijit.byId('expenseType_cb');
	if(expTypeNode != null && expTypeNode.item != null){
		if(expTypeNode.item.isMileageRelated[0]){
			dojo.byId('roundTrip').disabled = false;
		}else{
			// For non mileage types, round trip remains in the previous state
			// and is not to be disabled as round trip may be checked to indicate
			// taxability status of non-mileage expense type.
			// So, just reset common miles indicator
			dojo.byId('commonMilesInd').innerHTML = "NO";
		}
	}

}
	
/* Checks whether From City, From State, To City & To State all are N/A */
function isFromToLocationNA(){
	return dijit.byId('fromCity_cb').attr('value') == "N/A" && dijit.byId('fromState_cb').attr('value') == "NA"
		&& dijit.byId('toCity_cb').attr('value') == "N/A" && dijit.byId('toState_cb').attr('value') == "NA";
}

/* Handles events related to "From" State of City change */
function handleFromStateChange(fieldName){
	modifyRoundTripIndicatorViewState();

	// perform common mileage lookup
	lookupCommonMiles();
}

/* Handles events related to "To" State of City change */
function handleToStateChange(fieldName){
	modifyRoundTripIndicatorViewState();

	// perform common mileage lookup
	lookupCommonMiles();
	
	//Action to check if the "To" City/State/Expense Date combination is a select city
	selectCityLookup();
	
	//call server to calculate select city rate 
	//Action to get BLD rates
	
	if(isExpenseTypeMealType(_temp_expenseTypeCBKey)){	
		calculateExpenseTypeStandardRate(_temp_expenseTypeCBKey);	
	}
}
	

/*Handles Overnight dropdown change
 * If Yes:
 * 		 1. Add incidental expenses.
 * 		 2. All meals (BLD) should be non-taxable.
 * if No:
 * 		 1. Remove incidental expenses.
 * 		 2. Meals should be now taxable.
 *  */
function handleOvernightChange(fieldName){
	
	//store the current values of expense types	
	//set overnight id
	//get expense types
	
	var request = {} ;
	var overnight = dojo.byId('overnight');
	
	if (overnight != null)	
		request.isOvernight = overnight.value;

	serverRequestExpenseDetailsInquiry("FindExpenseDetails.action", request, setupExpenseTypesByOvernight);
	
	//set again to the previously selected expense type if available.
	//initialize to the value if already populated
	expenseTypesCB.attr('value', _temp_expenseTypeCBKey);
	
	if (_temp_expenseTypeCBKey.length > 0){
		calculateExpenseTypeStandardRate(getExpenseTypeCode());
	}
}

function setupExpenseTypesByOvernight(data){
	//1. get store
	var expenseTypeCBStore = prepareReadStore(data.response.expenseTypes, 'code');
	
	//2. get expense_cb handle
	var ddExpenseType_cb = dijit.byId('expenseType_cb') ;
	
	//3. assign store to the DD
	
	if (ddExpenseType_cb != null){
//		ddExpenseType_cb.store = expenseTypeCBStore;
		ddExpenseType_cb.set("store", expenseTypeCBStore);
	}
}

function getExpenseTypeCode(){
	var expTypeNode = dijit.byId('expenseType_cb');
	
	if(expTypeNode == null || expTypeNode.item == null 
		|| expTypeNode.item.code == null || expTypeNode.item.code[0] == null) return;
	
	return expTypeNode.item.code[0]; 
}

function getExpenseMealValue(){
	var expTypeNode = dijit.byId('expenseType_cb');
	
	if(expTypeNode == null || expTypeNode.item == null 
		|| expTypeNode.item.isMealRelated == null || expTypeNode.item.isMealRelated[0] == null) return;
	
	return expTypeNode.item.isMealRelated[0]; 
}

/* Lookup common miles between the source and destination location and triggers required post processing */
function lookupCommonMiles(){
	// whenever From or To location changes, 'currentCommonMiles' are rendered invalid.
	// Therefore, reset them.
	
	currentCommonMiles = 0;
	
	// If expense type is not mileage related, no lookup needed.
	var expTypeNode = dijit.byId('expenseType_cb');
	if(expTypeNode!= null && expTypeNode.item!= null && !expTypeNode.item.isMileageRelated[0]){
		return;
	}
	
	var fromCity = dijit.byId('fromCity_cb').attr('value');
	var fromState = dijit.byId('fromState_cb').attr('value');
	var toCity = dijit.byId('toCity_cb').attr('value');
	var toState = dijit.byId('toState_cb').attr('value');

	// if valid location, then lookup common mileage
	if(!(fromCity == null || fromCity == "" || fromCity == "N/A" || fromState == null || fromState == "" || fromState == "NA"
		|| toCity == null || toCity == "" || toCity == "N/A" || toState == null || toState == "" || toState == "NA")){
		
		var data = {};
		data.fromCity = fromCity;
		data.fromState = fromState;
		data.toCity = toCity;
		data.toState = toState;
		
		if(commonMileageCache != null && commonMileageCache.fromCity == fromCity && commonMileageCache.fromState == fromState
				&& commonMileageCache.toCity == toCity && commonMileageCache.toState == toState){
			handleCommonMileageServerResponse(commonMileageCache);
		}else{
			serverRequestExpenseDetailsInquiry("FindCommonMileage.action", data, function(data){
				if(data.response.commonMiles != null && data.response.commonMiles.miles != null) commonMileageCache = data.response.commonMiles;
				handleCommonMileageServerResponse(data.response.commonMiles);
			});
		}
	}else{
		//reset common miles
		currentCommonMiles = 0;
		
		// update miles and common miles
		dijit.byId('miles').attr('value', 0);
		dijit.byId('vicinityMiles').attr('value', 0);
		dojo.byId('commonMilesInd').innerHTML = "NO";
	}
}

// Find common miles values and returns it without triggering any post processing.
function findCommonMilesWithoutEffects(){
	var _commonMiles = 0;
	
	var fromCity = dijit.byId('fromCity_cb').attr('value');
	var fromState = dijit.byId('fromState_cb').attr('value');
	var toCity = dijit.byId('toCity_cb').attr('value');
	var toState = dijit.byId('toState_cb').attr('value');

	// if valid location, then lookup common mileage
	if(!(fromCity == null || fromCity == "" || fromCity == "N/A" || fromState == null || fromState == "" || fromState == "NA"
		|| toCity == null || toCity == "" || toCity == "N/A" || toState == null || toState == "" || toState == "NA")){
		
		var data = {};
		data.fromCity = fromCity;
		data.fromState = fromState;
		data.toCity = toCity;
		data.toState = toState;
		
		if(commonMileageCache != null && commonMileageCache.fromCity == fromCity && commonMileageCache.fromState == fromState
				&& commonMileageCache.toCity == toCity && commonMileageCache.toState == toState){
			_commonMiles = parseInt(commonMileageCache.miles.toFixed(0));
		}else{
			serverRequestExpenseDetailsInquiry("FindCommonMileage.action", data, function(data){
				if(data.response.commonMiles != null && data.response.commonMiles.miles != null)
					_commonMiles = parseInt(data.response.commonMiles.miles.toFixed(0));
			});
		}
	}
	
	return _commonMiles;
}

function handleCommonMileageServerResponse(commonMiles){
	// update global variable with new value
	if(commonMiles != null) currentCommonMiles = parseInt(commonMiles.miles.toFixed(0));
	
	var projectedTotalMiles = 0;
	if(currentCommonMiles > 0){ 
		projectedTotalMiles = dojo.byId('roundTrip').checked ? (currentCommonMiles * 2).toFixed(0) : currentCommonMiles.toFixed(0);
	}
	
	var milesNode = dijit.byId('miles');
	var expenseTypeNode = dijit.byId('expenseType_cb');
	
	// if expense type is mileage related, update miles and common miles.
	if(expenseTypeNode != null && expenseTypeNode.item!= null){
		if(expenseTypeNode.item.isMileageRelated[0]){
			// if common miles OR no miles, ONLY then suggest new mileage
			if(milesNode.attr('value') == 0 ||
					(milesNode.attr('value') > 0 && dojo.byId('commonMilesInd').innerHTML == "YES")){
				if (parseInt(milesNode.attr('value')) + parseInt(dijit.byId('vicinityMiles').attr('value')) == 0)
					milesNode.attr('value', projectedTotalMiles);
			}
		}else{
			dijit.byId('miles').attr('value', 0);
			dijit.byId('vicinityMiles').attr('value', 0);
			dijit.byId('totalMiles').attr('value', 0);
		}
		
	}

	// initiate miles change post processing, to correctly update other fields
	handleMileChange();
}

/* Refreshes the total expense detail amount */
function refreshExpenseDetailAmount(){
	expenseDetailsGridStore.fetch({
		query:{lineItemNo: '*'},
	    onError: function(){},
	    onComplete: function(items, request){
	    	var runningTotal = 0.0;
	    	dojo.forEach(items, function(item){
	    		runningTotal += parseFloat(item.amount[0]);
	    	});
	    	dojo.byId('totalExpenseAmtId').value = runningTotal.toFixed(2);
	    }
	});
}

/* format numeric value to remove seperators */
function formatNumericSeparators(name){
	var value = dijit.byId(name).attr('value');
	if(!isNaN(value))
		dojo.byId(name).value = value;
}

/* Constructs drop down with provided State values */
function constructStateDropDown(states){
	var stateCBStore = prepareReadStore(states, 'code');
	
	fromStateCB = new dijit.form.FilteringSelect({id: "fromState_cb", name: "fromState", store: stateCBStore, autoComplete : true,
		searchAttr: 'name', value: '', displayedValue:'', style:"width:8em"}, 'fromState_dd');
	toStateCB = new dijit.form.FilteringSelect({id: "toState_cb", name: "toState", store: stateCBStore, autoComplete : true,
		searchAttr: 'name', value: '', displayedValue:'', style:"width:8em"}, 'toState_dd');
}

/* Constructs drop down with provided City values */
function constructCityDropDown(cities){
	var cityCBStore = prepareReadStore(cities, 'name');
	
	fromCityCB = new dijit.form.ComboBox({id: "fromCity_cb", name: "fromCity", store: cityCBStore, autoComplete : true, 
		searchAttr: 'name', value: '', displayedValue:'', style:"width:8em"}, 'fromCity_dd');
	toCityCB = new dijit.form.ComboBox({id: "toCity_cb", name: "toCity", store: cityCBStore, autoComplete : true,
		searchAttr: 'name', value: '', displayedValue:'', style:"width:8em"}, 'toCity_dd');
}

/* Constructs drop down with provided ExpenseTypes values */
function constructExpenseTypesDropDown(expenseTypes){
	var isDisabled = dojo.byId('expenseType_cb').disabled;
	
	var expenseTypeCBStore = prepareReadStore(expenseTypes, 'code');
	
	//var expenseTypeCBStore = prepareWriteStoreExpense (expenseTypes, 'code');
	
	expenseTypesCB = new dijit.form.FilteringSelect({id: "expenseType_cb", name: "expenseType", store: expenseTypeCBStore, 
		searchAttr: 'desc', value: '', displayedValue:'', style:"width:10em"}, 'expenseType_cb');
	
	if(isDisabled){
		expenseTypesCB.attr('disabled', true);
	}

	return expenseTypesCB;
}

/* Prepares a Write store */
function prepareWriteStore(jsonStr){
	var _data = {};
	_data.identifier = 'lineItemNo';
	_data.items = jsonStr;
	return new dojo.data.ItemFileWriteStore({data: _data});
}

/* Prepares a Write store */
function prepareWriteStoreExpense(itemArray, idColumnName){
	var _data = {};
	_data.identifier = idColumnName;
	_data.items = itemArray;
	return new dojo.data.ItemFileWriteStore({data: _data});
}

/* Prepares a Read store */
function prepareReadStore(itemArray, idColumnName){
	var stateCBData = {};
	stateCBData.identifier = idColumnName;
	stateCBData.items = itemArray;

	return new dojo.data.ItemFileReadStore({data: stateCBData});
}

/* Validates the expense line item form data */
function validateExpenseDetail(){
	var errorMsg = {msg:''};
	
	var valid = true;
	
	var valid = validateExpenseDate(errorMsg)
				&& validateFromCity(errorMsg)
				&& validateFromState(errorMsg)
				&& validateToCity(errorMsg)
				&& validateToState(errorMsg)
				&& validateCityAndStateForNA(errorMsg)
				&& validateDepartureReturnTime(errorMsg)
				&& validateExpenseType(errorMsg)
				&& validateReimbursementAmount(errorMsg)
				&& validateMilesAsNumbers(errorMsg)
				&& validateComments(errorMsg)
				&& validateSelectCityExpenseAndCity(errorMsg)
				&& validateOvernight(errorMsg);
	
	notifyExpenseDetails(errorMsg.msg, true);
	return valid;
}

/* Validates expense date */
function validateExpenseDate(errorMsg){
	var _valid = true;
	
	var dateNode = dojo.byId("fromDate");
	
	if(dateNode.value.length < 1) _valid =  false;
	else{
		dojo.forEach(dateNode.value.split(/,./), function(item){
			item = trimStr(item);
			if(item.length <1) return;
			
			var validDateRegex = /^(([0][1-9])|([1][0-2]))(\/)(([0][1-9])|([1-2][0-9])|([3][0-1]))(\/)\d{4}$/;
			
			if(!validDateRegex.test(item)) _valid = false;
		});
	}
	
	if(!_valid) errorMsg.msg = "Please enter a valid expense 'Date'";
	
	return _valid;
}

/* Validates from city */
function validateFromCity(errorMsg){
	if(trimStr(dijit.byId('fromCity_cb').attr('value')).length < 1){
		errorMsg.msg = "Please choose an existing 'From' city from the dropdown or enter your own value.";
		return false;
	}
	
	return true;
}

/* Validates from state */
function validateFromState(errorMsg){
	if(trimStr(dijit.byId('fromState_cb').attr('value')).length < 1){
		errorMsg.msg = "Please choose an existing 'From' state from the dropdown.";
		return false;
	}
	
	return true;
}

/* Validates from city */
function validateToCity(errorMsg){
	if(trimStr(dijit.byId('toCity_cb').attr('value')).length < 1){
		errorMsg.msg = "Please choose an existing 'To' city from the dropdown or enter your own value.";
		return false;
	}
	
	return true;
}

/* Validates from state */
function validateToState(errorMsg){
	if(trimStr(dijit.byId('toState_cb').attr('value')).length < 1){
		errorMsg.msg = "Please choose an existing 'To' state from the dropdown.";
		return false;
	}
	
	return true;
}

/* Validates departure/return time */
function validateDepartureReturnTime(errorMsg){
	var dTime = trimStr(dijit.byId('departureTime').attr('displayedValue'));
	var dTimePhase = trimStr(dijit.byId('departureTimePhase').attr('displayedValue'));
	var rTime = trimStr(dijit.byId('returnTime').attr('displayedValue'));
	var rTimePhase = trimStr(dijit.byId('returnTimePhase').attr('displayedValue'));

	var timeFormat = /^(([1-9])|(1[0-2])):[0-5][0-9]/;
	// one is missing, while other is present
	if((dTime.length > 0 && rTime.length ==0) || (rTime.length > 0 && dTime.length ==0)){
		errorMsg.msg = "Please provide both departure and return time.";
		return false;
	}
	// invalid departure time
	if(dTime.length>0 && !timeFormat.exec(dTime)){
		errorMsg.msg = "Please choose a valid departure time.";
		return false;
	}
	// if valid then AM/PM must be specified
	else if(dTimePhase.length == 0 && dTimePhase == ""){
		errorMsg.msg = "Please choose either 'AM' or 'PM' as departure time phase.";
		return false;
	}
		
	// invalid return time
	if(rTime.length>0 && !timeFormat.exec(rTime)){
		errorMsg.msg = "Please choose a valid return time.";
		return false;
	}
	// if valid then AM/PM must be specified
	else if(rTimePhase.length==0 && rTimePhase == "" ){
		errorMsg.msg = "Please choose either 'AM' or 'PM' as return time phase.";
		return false;
	}
	
	
	
	//Times are required if Meal types are selected.
	if(isExpenseTypeMealType(dijit.byId('expenseType_cb').attr('value'))){		
		if(rTime.length <1 || dTime.length <1){
			errorMsg.msg = "Departure and Return time is required for meal type expenses.";
			return false;
		}
	}

	
	// return time must be greater than depart time
	if(dTime.length > 0 && rTime.length > 0){
		var dColonPosition = dTime.indexOf(':');
		var rColonPosition = rTime.indexOf(':');

		var dHrs = parseInt(dTime.substring(0, dColonPosition));
		if(dHrs == 12) dHrs=0;
		var rHrs = parseInt(rTime.substring(0, rColonPosition));
		if(rHrs == 12) rHrs=0;
		
		var dTimeMins = (dTimePhase=="PM") ? 
						(dHrs+12)*60 + parseInt(dTime.substring(dColonPosition+1)) :
						dHrs*60 + parseInt(dTime.substring(dColonPosition+1));
		
		var rTimeMins = (rTimePhase=="PM") ?
						(rHrs+12)*60 + parseInt(rTime.substring(rColonPosition+1)) :
						rHrs*60 + parseInt(rTime.substring(rColonPosition+1));
		
		if(dTimeMins >= rTimeMins){
			errorMsg.msg = "Return time must be greater than departure time.";
			return false;
		}	
	}
	
	return true;
}

/* Validates expense type */
function validateExpenseType(errorMsg){
	var expType_cb = dijit.byId('expenseType_cb');
	
	if(!expType_cb.isValid() || expType_cb.attr('value').length < 1){
		errorMsg.msg = "Please choose an 'Expense Type' from the dropdown.";
		return false;
	}
	
	return true;
}

/* Validates expense type */
function validateReimbursementAmount(errorMsg){
	if(!dijit.byId('reimbursementAmount').isValid() || dojo.byId('reimbursementAmount').value.length < 1){
		errorMsg.msg = "Please enter a valid 'Reimbursement Amount'.";
		return false;
	}
	
	return true;
}

/* validates miles, total miles and vicinity miles to be numeric */
function validateMilesAsNumbers(errorMsg){
	// set miles to '0' if missing
	var miles_tb = dojo.byId('miles');
	var vicinityMiles_tb = dojo.byId('vicinityMiles');
	var totalMiles_tb = dojo.byId('totalMiles');
	
	miles_tb.value = (trimStr(miles_tb.value)=="") ? 0 : miles_tb.value;
	vicinityMiles_tb.value = (trimStr(vicinityMiles_tb.value)=="") ? 0 : vicinityMiles_tb.value;
	totalMiles_tb.value = (trimStr(totalMiles_tb.value)=="") ? 0 : totalMiles_tb.value;
	
	// validate miles
	if(!dijit.byId('miles').isValid() || !dijit.byId('vicinityMiles').isValid() || !dijit.byId('totalMiles').isValid()){
		errorMsg.msg = "Please provide valid miles."
		return false;
	}
	
	return true;
}

/**
 * Added specific error messages to validate that both City and State selected 
 * should be N/A or valid values for each dropdown
 * @param errorMsg
 * @return
 */

function validateCityAndStateForNA (errorMsg){

	var fromCity = trimStr(dijit.byId('fromCity_cb').attr('value'));
	var fromState = trimStr(dijit.byId('fromState_cb').attr('value'));
	var toCity = trimStr(dijit.byId('toCity_cb').attr('value'));
	var toState = trimStr(dijit.byId('toState_cb').attr('value'));
	
	if(!validateCityAndState(fromCity, fromState)){
		errorMsg.msg  = "From State must be N/A if the City is N/A.";
		return false;
	}
	
	if(!validateCityAndState(fromState, fromCity)){
		errorMsg.msg  = "From City must be N/A if the State is N/A.";
		return false;
	}
	
	if(!validateCityAndState(toCity, toState)){
		errorMsg.msg  = "To State must be N/A if the City is N/A.";
		return false;
	}
	
	if(!validateCityAndState(toState, toCity)){
		errorMsg.msg  = "To City must be N/A if the State is N/A.";
		return false;
	}

	return true;
}

function validateCityAndState(value1, value2){
	if(value1 == 'N/A' || value1 == 'NA'){
		if (value2 != 'N/A' && value2 != 'NA'){
			return false;
		}
	}
	
	return true;
}

/* Validates expense detail comments */
function validateComments(errorMsg){
	if(dojo.byId('expenseDetailComments').value.length > 255){
		errorMsg.msg  = "Comments longer than allowed (255 characters). Please enter a shorter value.";
		return false;
	}
	
	return true;
}

//check if the selected expense is of type Select City 
//and the selected city is "Select City", if not then flash error.
//If expense is not a select city but the selected city is a "Select City"
//we do not care.

function validateSelectCityExpenseAndCity(errorMsg){

	var requestData = {};
	requestData.expenseTypeCode = dijit.byId('expenseType_cb').attr('value');
	var returnValue = false;
	
	if(isExpenseTypeMealType(requestData.expenseTypeCode)){
		returnValue =  true;
	}else{		
		serverRequestExpenseDetailsInquiry("isSelectCityExpense.action", requestData, function(data){
		if(data.response.select_city_expense != null && data.response.select_city_expense == 'yes'
			&& dojo.byId("SelectCityIndicator").innerHTML == ""){
				errorMsg.msg  = "Selected expense is a \"select city expense\", however the \"To\" city/state is not a \"select City\".";
				returnValue =  false;
			}
		else
			returnValue =  true;
		});
	}
	
	return returnValue;
}

/*
 * Method if make overnight "required" if the expenseType is BLD.
 * */
function validateOvernight(errorMsg){
	if(isExpenseTypeMealType(dijit.byId('expenseType_cb').attr('value'))){
		if (dojo.byId("overnight").value == ''){
			errorMsg.msg  = "Overnight switch is required if the expense type is a meal type.";
			return false;
		}	
	}
	return true;
}

/* Invoked when the user clicks the 'Apply' button on expense details tab */
function applyExpenseDetail(){
	setChanged();
	// if validation fails, do not proceed!
	if(!validateExpenseDetail()) return;
	
	var selExpDtl;
	
	if(expenseDetailsGrid.selection.getSelected().length > 0){
		selExpDtl = expenseDetailsGrid.selection.getSelected()[0];
	}
	
	var firstRun = true;
	dojo.forEach(dojo.byId("fromDate").value.split(/,./), function(item){		
		var newExpDtl = {};
		newExpDtl.expenseDate = trimStr(item);		
		newExpDtl.expenseType = dijit.byId('expenseType_cb').attr('value');
		
		//add expenseType_cb to the category column.
		newExpDtl.categoryCode = dijit.byId('expenseType_cb').attr('value');
		
		//add expensetypeCode from the hidden textbox.
		newExpDtl.expenseType = dojo.byId('hiddenExpenseTypeCode').value;

		
		newExpDtl.expenseTypeDesc = dojo.byId('expenseType_cb').value;
		newExpDtl.expenseTypeStdRate = _temp_expenseTypeStandardRate;
		newExpDtl.amount = dojo.number.parse(dijit.byId('reimbursementAmount').attr('value').toFixed(2).toString());
		newExpDtl.fromCity = dijit.byId('fromCity_cb').attr('value');
		newExpDtl.toCity = dijit.byId('toCity_cb').attr('value');
		newExpDtl.fromState = dijit.byId('fromState_cb').attr('value');
		newExpDtl.toState = dijit.byId('toState_cb').attr('value');
		newExpDtl.roundTrip = dojo.byId('roundTrip').checked;
		if(dojo.byId('departureTime').value != null && dojo.byId('departureTime').value.length > 0
			&& dijit.byId('departureTimePhase').attr('displayedValue') != null 
			&& dijit.byId('departureTimePhase').attr('displayedValue').length > 0){
			newExpDtl.departTime = trimStr(dojo.byId('departureTime').value) +' '+ trimStr(dijit.byId('departureTimePhase').attr('displayedValue'));
		}else{
			newExpDtl.departTime = '';
		}
		if(dojo.byId('returnTime').value != null && dojo.byId('returnTime').value.length > 0
			&& dijit.byId('returnTimePhase').attr('displayedValue') != null 
			&& dijit.byId('returnTimePhase').attr('displayedValue').length > 0){
			newExpDtl.returnTime = trimStr(dojo.byId('returnTime').value) +' '+ trimStr(dijit.byId('returnTimePhase').attr('displayedValue'));
		}else{
			newExpDtl.returnTime = '';
		}
		newExpDtl.miles = dijit.byId('miles').attr('value');
		newExpDtl.vicinityMiles = dijit.byId('vicinityMiles').attr('value');
		newExpDtl.commonMiles = "YES"==dojo.byId('commonMilesInd').innerHTML;
		newExpDtl.comments = dojo.byId('expenseDetailComments').value;
		
		//overnight
		newExpDtl.overnightInd = dojo.byId('overnight').value;
		
		//isMealRelated
		newExpDtl.isMealRelated = getExpenseMealValue();
		
		
		// existing item update
		if(selExpDtl != null && firstRun){
			newExpDtl.expdId =  selExpDtl.expdId[0];
			newExpDtl.lineItemNo =  selExpDtl.lineItemNo[0];

			// update store
			expenseDetailsGridStore.fetchItemByIdentity({
				identity:selExpDtl.lineItemNo[0], onItem: function(item, request){
					updateStoreItem(expenseDetailsGridStore, item, newExpDtl);
				}
			});
		// new item addition	
		}else{
			newExpDtl.expdId = 0;
			newExpDtl.lineItemNo = ++maxExpenseLineNo;

			// if new add to store
			expenseDetailsGridStore.newItem(newExpDtl);
			expenseDetailsGrid.setStore(expenseDetailsGridStore);
			// fix any misalignments
			expenseDetailsGrid.resize();
		}	

		// set flag
		firstRun = false;
	});

	// clear standard reimbursement amount for the associated expense type
	_temp_expenseTypeStandardRate = 0;
	
	// trigger attached event processing
	refreshExpenseDetailAmount();
	
	// reset all fields except those in the 1st 2 rows.
	partialResetExpenseDetailsForm();
	
	// resize to fix any misalignment
	expenseDetailsGrid.resize();
	
	// clear grid selection
	expenseDetailsGrid.selection.deselectAll();

	// set focus on expense types
	dijit.byId('expenseType_cb').focus();
}

/* 
 * Updates the given item from the store with the new values in 'newExpDtl',
 * except 'expdId' and 'lineItemNo' properties
 */
function updateStoreItem(expenseDetailsGridStore, item, newExpDtl){
	expenseDetailsGridStore.setValue(item, "expenseDate", newExpDtl.expenseDate);
	expenseDetailsGridStore.setValue(item, "expenseType", newExpDtl.expenseType);
	expenseDetailsGridStore.setValue(item, "expenseTypeDesc", newExpDtl.expenseTypeDesc);
	expenseDetailsGridStore.setValue(item, "expenseTypeStdRate", newExpDtl.expenseTypeStdRate);
	expenseDetailsGridStore.setValue(item, "amount", dojo.number.parse(newExpDtl.amount));
	expenseDetailsGridStore.setValue(item, "fromCity", newExpDtl.fromCity);
	expenseDetailsGridStore.setValue(item, "fromState", newExpDtl.fromState);
	expenseDetailsGridStore.setValue(item, "toCity", newExpDtl.toCity);
	expenseDetailsGridStore.setValue(item, "toState", newExpDtl.toState);
	expenseDetailsGridStore.setValue(item, "roundTrip", newExpDtl.roundTrip);
	expenseDetailsGridStore.setValue(item, "departTime", newExpDtl.departTime);
	expenseDetailsGridStore.setValue(item, "returnTime", newExpDtl.returnTime);
	expenseDetailsGridStore.setValue(item, "miles", newExpDtl.miles);
	expenseDetailsGridStore.setValue(item, "vicinityMiles", newExpDtl.vicinityMiles);
	expenseDetailsGridStore.setValue(item, "totalMiles", newExpDtl.miles + newExpDtl.vicinityMiles);//newExpDtl.totalMiles);
	expenseDetailsGridStore.setValue(item, "commonMiles", newExpDtl.commonMiles);
	expenseDetailsGridStore.setValue(item, "comments", newExpDtl.comments);
	
	//adding a new column CategoryId to add the category id from the dropdowm.
	expenseDetailsGridStore.setValue(item, "categoryCode", newExpDtl.categoryCode);
	
	//overnight
	expenseDetailsGridStore.setValue(item, "overnightInd", newExpDtl.overnightInd);
	expenseDetailsGridStore.setValue(item, "isMealRelated", newExpDtl.isMealRelated);
}

/* Marks the current expense as audited */
function markExpenseAsAudited(){
	//disable button to avoid double submits
	dojo.byId('auditCompleteBtn').disabled = true;
	
	serverRequestExpenseDetails("MarkExpenseAudited.action", null, function(data){
		if(data.response.expenseActionId != null){
			notifyExpenseDetails('Expense successfully audited.', false);
			dojo.byId('auditCompleteBtn').disabled = true;
		}else{
			notifyExpenseDetails('Expense audit failed', true);
			dojo.byId('auditCompleteBtn').disabled = false;
		}
	});
}

/* 
 * Utility method to make an Ajax call to the server and 
 * invoke the callback method upon receiving successful response
 */ 
function serverRequestExpenseDetailsInquiry(serviceUrl, _data, callbackMethod){
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

/* 
 * Utility method to make an Ajax call to the server and 
 * invoke the callback method upon receiving successful response
 */ 
function serverRequestExpenseDetails(serviceUrl, _data, callbackMethod){
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
					
					notifyExpenseDetails("Save failed", true);
					var tab = dijit.byId('expense_tab_container');
					if (tab.selectedChildWidget.id != 'expenseDetailsTab'){
						tab.selectChild('expenseDetailsTab');
					}
					return true;
		}
	});
}

function selectField(field){
	field.focus();
	field.select();
}

function isExpenseTypeMealType(expenseTypeCode){
	
var isMealType = false;

	//This iterates the expense type drop down store,
	//finds the expense type and checks the isMealRelated field
	//return value accordling.
	dijit.byId('expenseType_cb').store.fetchItemByIdentity({
		identity:expenseTypeCode, 
		onItem: function(item, request){
			if(item != null && item.isMealRelated[0]){
				isMealType = true;
			}else{
				isMealType = false;
			}
		},
		onError:function(item, request){
			isMealType = false;
		}
	});
	
	return isMealType;
}
