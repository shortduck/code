/*
 * Includes dynamic behavior used for the advances tab control
 * 
 * ZH - 04/28/2009
 * 
 */

// predefine form fields and messages

var requestDate;
var fromDate;
var toDate;
var dollarAmount;
var reason;
var manualWarrantDocNum;
var manualWarrantIssdInd;
var manualDepositDocNum;
var manualDepositAmount;

var DATE_FORMAT_INVALID = "Invalid Date Format. Dates must be in MM/DD/YYYY format";
var DATE_INVALID = "Invalid Day, Month, or Year range detected";

// buttons
var buttonSave;
var buttonSubmit;
var buttonModify;
var buttonApprove;
var buttonApproveWithComments;
var buttonApproveWithCommentsClicked;
var buttonApproveNextClicked;
var buttonApproveSkip;
var buttonReject;
var buttonRejectClicked;
var modifyClicked;
var commentsAfterModify;
var fromSubmit = false;
var formValidated = false;

function initializeForm() {

	// attach listener on change
	dojo.connect(dojo.byId('fromDate'),'onchange',matchToDateWithFromDate);
	
	initializeFormFields();
	var status = dojo.byId('status').value;
	if (status == 'SUBM' || status == 'APPR' || status == 'HSNT'
			|| status == 'PROC' || status == 'RJCT' || status == 'XTCT' || status =='DISABLED') {
		// disable all input fields
		toggleAllFields(true);
		// hide images
		toggleImages("hidden");
		// disbale dojo controls
		toggleDojoControls('advance', true);
		dojo.byId('employeeId').disabled = false;
	}
	
	dijit.byId('manualWarrantIssdInd').set("value", dojo.byId('manualWarrantIssd').value, false);

	if (dojo.byId("moduleId").value == "ADVW003") {
		// toggleField(manualWarrantIssdInd, false);
		if (status == ""){
			manualWarrantIssdInd.attr('disabled',false);
			manualWarrantDocNum.disabled = false;
		}
		else{
			manualWarrantIssdInd.attr('disabled',true);
			manualWarrantDocNum.disabled = true;
		}
		// these fields must be disbaled unless form is in PROC
		/*
		 * if (status == 'PROC'){ toggleField(manualDepositDocNum, false);
		 * toggleField(manualDepositAmount, false); }
		 */
	}
		
	// add onchange event handler to keep track if the form has been modified
	addOnChange();
	// add manual warrant field to tie the event to the indicator field
    	dojo.connect(dijit.byId("manualWarrantIssdInd"), 'onChange', setChangedManualWarrant);	
			// manualWarrantDocNum.style.backgroundColor = 'white';
  
    var treqId = dojo.byId(treqEventId).innerHTML;
    if (parseInt(treqId ) > 0)
    	dojo.byId('spanTreqEventId').style.display = "inline";
    
}

// Function to match toDate with fromDate if the toDate is blank.

function matchToDateWithFromDate(){
	var fDate = dojo.byId('fromDate');
	var tDate = dojo.byId('toDate');
	if(tDate.value.length == 0){
			syncDate(fDate);
			tDate.value = fDate.value;
		}	
}


function initializeFormFields() {
	// populate form field variables
	
	requestDate = dojo.byId('requestDate');
	fromDate = dojo.byId('fromDate');
	toDate = dojo.byId('toDate');
	dollarAmount = dojo.byId('dollarAmount');
	reason = dojo.byId('advanceReason');
	manualWarrantIssdInd = dijit.byId('manualWarrantIssdInd');
	manualWarrantDocNum = dojo.byId('manualWarrantDocNum');
	manualDepositDocNum = dojo.byId('manualDepositDocNum');
	manualDepositAmount = dojo.byId('manualDepositAmount');
	
	// images
	buttonSave = dojo.byId('buttonSave');
	buttonSubmit = dojo.byId('buttonSubmit');
	buttonModify = dojo.byId('buttonModify');
	buttonApprove = dojo.byId('buttonApprove');
	buttonApproveWithComments = dojo.byId('buttonApproveWithComments');
	buttonApproveNext = dojo.byId('buttonApproveNext');
	buttonApproveSkip = dojo.byId('buttonApproveSkip');
	buttonReject = dojo.byId('buttonReject');
}

// Handles change to the manual warrant fields
function setChangedManualWarrant() {
	// toggle manual warrant doc number field depending upon the selection in
	// the indicator field
	if (manualWarrantIssdInd.value == "Y")
		manualWarrantDocNum.disabled = false;
	else {
		manualWarrantDocNum.value = "";
		manualWarrantDocNum.disabled = true;
	}
}

// sets form status to modifield if data was eneterd in an input field
function modifyForm() {
	showStatusMessage("");
	// override manual deposit fields
	if ((dojo.byId('status').value == 'PROC' && dojo.byId("moduleId").value == "ADVW003") ||
			((dojo.byId('status').value == 'APPR' && dojo.byId("moduleId").value == "ADVW003") || 
			(dojo.byId('status').value == 'SUBM' && dojo.byId("moduleId").value == "ADVW003") || 
			(dojo.byId('status').value == 'RJCT' && dojo.byId("moduleId").value == "ADVW003") ||
			(dojo.byId('status').value == 'APPR' && dojo.byId("moduleId").value == "APRW004") || 
			(dojo.byId('status').value == 'RJCT' && dojo.byId("moduleId").value == "APRW004") ||
			(dojo.byId('status').value == 'SUBM' && dojo.byId("moduleId").value == "APRW004")) &&
			(dojo.byId('previouslyPROC').value == 'Y')) {
		toggleAllFieldsReadonly(true);
		// toggleDojoControlsReadonly('advance', true);
		toggleFieldReadonly(manualDepositDocNum, false);
		toggleFieldReadonly(manualDepositAmount, false);
		toggleDojoControlsReadOnly('advance', true);
		disableCbDeleteButtons(true);
		dojo.byId('buttonSave').disabled = true;
		
		buttonSave.disabled = true;
		
	} else {
		toggleAllFields(false);
		toggleImages("visible");
		toggleDojoControls('advance', false);
		toggleField(manualDepositDocNum, true);
		toggleField(manualDepositAmount, true);
	
		// override manual warrant field
		if (dojo.byId("moduleId").value == "ADVW003" || dojo.byId("moduleId").value == "APRW004"){
			toggleField(manualWarrantIssdInd, false);
			toggleField(manualWarrantDocNum, false);
		}
		else{
			toggleField(manualWarrantIssdInd, true);
			toggleField(manualWarrantDocNum, true);
		}
		
		/*
		 * if (dojo.byId("moduleId").value.substring(0,2) == "AP"){ // approver
		 * is modifying buttonSave.disabled = true; } else{ buttonSave.disabled =
		 * false; }
		 */
}
	buttonSave.disabled = true;
	buttonModify.disabled = true;
	buttonSave.style.display = "inline";	
	buttonSubmit.style.display = "inline";
	buttonSubmit.disabled = false;
	buttonApprove.style.display = "none";
	buttonApproveWithComments.style.display = "none";
	buttonApproveNext.style.display = "none";
	buttonApproveSkip.style.display = "none";
	buttonReject.style.display = "none";
	modifyClicked = true;
}

// hide or show images
function toggleImages(flag) {
	dojo.byId("requestDateSelector").style.visibility = flag;
	dojo.byId("fromDateSelector").style.visibility = flag;
	dojo.byId("toDateSelector").style.visibility = flag;

}

function validate() {
	var advReason = dojo.byId("advanceReason").value;
	// check for data in all required fields
	if (!validateRequiredFields())
		return false;
	// check date fields format and values
	if (!validateDates())
		return false;
	// check to see if amounts are valid decimal values
	if (!validateAmounts())
		return false;
	// validate length of reason field
	if (advReason.length > 255){
		showErrorMessage("Reason longer than allowed. Please enter a shorter reason");
		return false;
	}
	
	// validates manualDepositAmount and manualDepositDocNum
	var manualDepositAmount = trimStr(dojo.byId("manualDepositAmount").value);
	var manualDepositDocNum = trimStr(dojo.byId("manualDepositDocNum").value);
	
	// validate if manual deposit is entered in case of PROC advances
	if (dojo.byId('status').value == 'PROC'){
		if (manualDepositAmount == '' && manualDepositDocNum ==''){
			showErrorMessage("Manual deposit information is required for adjustment");
			return false;
		}
	}

	if (manualDepositAmount != "" || manualDepositDocNum != "") {
		if (manualDepositAmount == "") {
			showErrorMessage("Manual Deposit Amount required");
			return false;			
		}
		if (manualDepositDocNum == "") {
			showErrorMessage("Manual Deposit document number required");
			return false;
		}
	}
	
	
	
	// validate coding block rows
	if (!validateCodingBlocks()){
		return false;
	}
	
	formValidated = true;

	return true;
}

function validateRequiredFields() {
	if (requestDate.value.length == 0 || dollarAmount.value.length == 0
			|| fromDate.value.length == 0 || toDate.value.length == 0
			|| reason.value.length == 0) {
		showErrorMessage ("Please enter all required fields indicated by *");
		return false;
	} else
		return true;
}

// for valid dates
function validateDates() {

	var checkRequestDate = checkdate(requestDate);
	if (checkRequestDate == -1) {
		showErrorMessage(DATE_FORMAT_INVALID);
		requestDate.select();
		return false;
	} else if (checkRequestDate == -2) {
		showErrorMessage(DATE_INVALID);
		return false;
	}

	var checkFromDate = checkdate(fromDate);
	if (checkFromDate == -1) {
		showErrorMessage(DATE_FORMAT_INVALID);
		fromDate.select();
		return false;
	} else if (checkFromDate == -2) {
		showErrorMessage(DATE_INVALID);
		fromDate.select();
		return false;
	}

	var checkToDate = checkdate(toDate);
	if (checkToDate == -1) {
		showErrorMessage(DATE_FORMAT_INVALID);
		toDate.select();
		return false;
	} else if (checkToDate == -2) {
		showErrorMessage(DATE_INVALID);
		toDate.select();
		return false;
	}

	return true;
}

// check for valid amounts
function validateAmounts() {
	if (dollarAmount.value != '') {
		if (!isDecimal(dollarAmount)) {
			showErrorMessage("Please enter a valid amount(Example 100.00)");
				dollarAmount.select();
			return false;
		}
		
		if (!validateDollarAmount()){
			return false;
		}
		
	}
	// validate manual deposit amount
	if (manualDepositAmount.value != '') {
		if (!isDecimal(manualDepositAmount)) {
			showErrorMessage("Please enter a valid amount(Example 100.00)");
			manualDepositAmount.select();
			return false;
		}
		
		// validate manual deposit amount
		if (!validateManualDepositAmount()){
			return false;
		}
	}
	return true;
}

function saveAdvance(fromSubmit,comments) {
	showStatusMessage("");
	// if coming from Submit then validation is already done
	if (!fromSubmit)
	{
		if (!validate ()){
			return false;
		}
	}
		
		var advanceForm = dojo.byId('advance');
		dojo.xhrPost( {

			url :"AjaxAdvanceCodingBlockSubmit.action",
			form: advanceForm,
			handleAs :"json",
			sync:true,
			load : function(data) {

			if ((data.validationErrors != null || data.errors != null)) {
				if (data.validationErrors.appointments != null && data.validationErrors.appointments.length > 0){
					handleMultipleAppointments(dojo.fromJson(data.validationErrors.appointments[0]), "ADVANCE");
					return false;
				}
				if (!processSaveResponse (data)){
					return false;
				}

				var advance = dojo.byId('adevIdentifier');
				if (parseInt(data.response.revision) >= 0 ){
					// enable previous revision button in case new revision has been created
					if (parseInt(data.response.revision) > parseInt(dojo.byId('revisionNumber').innerHTML)){
						dojo.byId('getPrevious').disabled = false;
					}
					dojo.byId("revisionNumber").innerHTML = data.response.revision;
				}
				processPostSaveResponse (data, comments);
				// reset form status
				formModified = false;
				}
		}, 
				error: function (error){
		        	handleAjaxError(error);
		        }
});
	
}

function processPostSaveResponse (data, comments){
		if ((data.errors.length > 0)){
			// changes to allow user to submit even with cb errors
			// if(data.response.canSubmitWithErrors == "yes" && fromSubmit){
			if(fromSubmit){
				if (data.response.canSubmit == "true"){
				submitAdvanceNew(comments);
				} else {
					showErrorMessage ("Submission Failed -  Please check grid below for errors");
					return false;
				}
			}
			if(!fromSubmit){
				if (dojo.byId("adevIdentifier").innerHTML == "")
					showErrorMessage ("Save Successful with Errors/Warnings");
				else
					showErrorMessage ("Update Successful with Errors/Warnings");
				}
		}
		else {
			updateErrorsGrid("");
			if (fromSubmit){
				if (data.response.canSubmit == "true"){
					submitAdvanceNew(comments);
					} else if (data.response.canSubmit != undefined){
						showErrorMessage ("Submission Failed");
						// return false;
					}
				// submitAdvanceNew(comments);
			} else {
				if (dojo.byId("adevIdentifier").innerHTML == "" && data.response != null)
					showStatusMessage ("Save Successful");
				else if (data.response != null)
					showStatusMessage ("Update Successful");
			}
		}
		
		if (dojo.byId("adevIdentifier").innerHTML == "" && data.response.advanceEventId != undefined) {
			dojo.byId("adevIdentifier").innerHTML = data.response.advanceEventId;
		}
 
}

function submitAdv(){
	showStatusMessage("");
	
	if (modifyClicked){
		if (validate()){
		dijit.byId('dialogCommentsAndSubmit').show();
		}
	} else{

			if (!validate()){
				return false;
			}
			submitAdvance("");
	}
	
}

function showCommentsAndSubmit(dialogFields, e) {
		commentsAfterModify = dojo.byId("commentsAfterModify").value;
		if(commentsAfterModify == null || commentsAfterModify == undefined || commentsAfterModify.replace(/^\s+|\s+$/g,"") == ""){
			showErrorMessage ("You must provide comments for submission");
			return;
		} else if(commentsAfterModify.length > 256){
			showErrorMessage("You entered comments longer than 256 characters. Submission failed.");
			return;
		}else{ 
			submitAdvance(commentsAfterModify);
			dojo.byId("commentsAfterModify").value = "";
		}
		
}


function submitAdvance(comments){
    if (formModified || modifyClicked || dojo.byId('adevIdentifier').innerHTML == ""){
    	fromSubmit = true;
    	saveAdvance(fromSubmit,comments);
    }else{
    	submitAdvanceNew(comments);
    }
    fromSubmit = false;
}
function submitAdvanceNew(comments){
    dojo.xhrPost({
        url: "AdvanceSubmitAction.action",
		content: {'approverComments': comments},
        handleAs: "json",
        sync: true,
        load: function(data){

				if ((data.validationErrors.errors != null)) {
					processSaveResponse (data);
					return false;
				} else if (data.response.status != null && data.response.status != ""){
					// updateErrorsGrid(data.errors);
					formatCodingBlockErrorsAdvanceAjax(data.errors);
					showStatusMessage("Submit Successful");	
					toggleAllFields(true);
					toggleImages("hidden");
					toggleDojoControls('advance', true);
					dojo.byId('employeeId').disabled = false;
					buttonModify.disabled = false;
					buttonSave.disabled = true;
					buttonSubmit.disabled = true;
					
					updateDisplayAmounts(data);
					

					if (!buttonApprove.disabled) {
						enableButtonsApproverModify();
					}			
				}else {
					showErrorMessage ("An error has occurred");
				}
            
        },
        error: function (error){
        	handleAjaxError(error);
        }
});

    
    // clear form status
		formModified = false;
		modifyClicked = false;		
}
function approveAdvance(){
    dojo.xhrPost({
        
        url: "AdvanceApproveAction.action",
        handleAs: "json",
        sync: true,
        load: function(data){
            	performPostApprovalProcessing (data);
        },
		error: function (error){
        	handleAjaxError(error);
        }
});
    
    formModified = false;
}

function approveSkip(action){
	var vext=dojo.byId('adevIdentifier').value;
	
	// fetch and load next approval transaction
    dojo.xhrPost({        
        url: "ApprovalNextTransaction.action?skip=" + action+"&requestId="+dojo.byId("adevIdentifier").innerHTML,
        handleAs: "json",
        sync: true,
        load: function(data){
    		if (data.response.apptIdentifier != undefined && data.response.apptIdentifier != ""){
    			var queryString = dojo.objectToQuery(data.response);
    			// reset form status
    			setUnchanged();
    			window.location = "ApproveReferrer.action?" + queryString;
    		} else {
    			// no more transaction in queue... back to the approval page
    			loadApprovalPage(dojo.byId("moduleId").value);
    		}
        },
		error: function (error){
        	handleAjaxError(error);
        }
});
}

function approveAdvanceWithComments() {

	var approverComments = dojo.byId("approverComments");
	var comments = approverComments.value;
	if(comments == null || comments == undefined || comments.replace(/^\s+|\s+$/g,"") == ""){
		showErrorMessage ("You must provide comments for approval");
		return;
	} else if(comments.length > 256){
		showErrorMessage("You entered comments longer than 256 characters. Approval failed.");
		return;
	}

	dojo.xhrPost( {

		url :"AdvanceApproveAction.action",
		content: {'approverComments': comments},
		handleAs :"json",
		sync: true,
		load : function(data) {
        	 performPostApprovalProcessing (data);      
		},
		error: function (error){
        	handleAjaxError(error);
        }
    });
	
	formModified = false;
}

function performPostApprovalProcessing (data){
	
	//AI 19898 Message display for employee with Invalid status. kp
	if (data.response.status == "EMPLOYEE_STATUS_INVALID"){
		showErrorMessage ("The employee is in a \"No Pay\" status. Please update employee status in HRMN to allow approval.");
		return;
	}
	
	
	if (data.response.status == "ADVANCE_APPROVING_AFTER_END_DATE"){
		showErrorMessage (data.response.msg);
		
		return;
	}		
	
	if (data.response.status == "SUBM" || data.response.status == "APPR"){
		toggleApprovalButtons (true);
		/*if (data.response.outstandingAmount != null &&
				parseFloat(data.response.outstandingAmount) > 0){
			dojo.byId("amountOutstanding").innerHTML = data.response.outstandingAmount.toFixed(2);
		}
		
		updateDisplayManualDepositAmount(data);*/
		updateDisplayAmounts (data);
    	showStatusMessage("Approve Successful");	     
		} else {
			showErrorMessage ("An error has occurred");
		}
	// updateErrorsGrid(data.errors);
	formatCodingBlockErrorsAdvanceAjax(data.errors);
	data == null;
	
	if (buttonApproveNextClicked || buttonApproveWithCommentsClicked){
	
    dojo.xhrPost({
        
        url: "ApprovalNextTransaction.action?requestId="+dojo.byId("adevIdentifier").innerHTML,
        handleAs: "json",
        sync: true,
        load: function(data){
    		if (data.response.apptIdentifier != undefined && data.response.apptIdentifier != ""){
    			var queryString = dojo.objectToQuery(data.response);
    			// reset form status
    			setUnchanged();
    			window.location = "ApproveReferrer.action?" + queryString;
    		} else {
    			loadApprovalPage(dojo.byId("moduleId").value);
    		}
        },
		error: function (error){
        	handleAjaxError(error);
        }
});
} else {
	loadApprovalPage(dojo.byId("moduleId").value);
}
    
	
}

function rejectAdvance() {
	// ZH, Fixed defect # 37
	var approverComments = dojo.byId("rejectComments");
	var comments = approverComments.value;
	if(comments == null || comments == undefined || comments.replace(/^\s+|\s+$/g,"") == ""){
		showErrorMessage ("You must provide comments for rejection");
		return;
	} else if(comments.length > 256){
		showErrorMessage("You entered comments longer than 256 characters. Rejection failed.");
		return;
	}
	dojo.xhrPost( {

		url :"AdvanceRejectAction.action",
		content: {'approverComments': comments},
		handleAs :"json",
		sync: true,
		load : function(data) {
				if (data.response.status == "RJCT"){
						toggleApprovalButtons (true, data.response.allowModification);
						showStatusMessage("Reject Successful");
						setUnchanged();
						approveSkip("reject");
					} else {
						showErrorMessage ("An error has occurred");
					}
		 // updateErrorsGrid(data.errors);
		 formatCodingBlockErrorsAdvanceAjax(data.errors);
		},
		error: function (error){
        	handleAjaxError(error);
        }
	});
	
	formModified = false;
}

function showCommentsAndSubmitApproval(dialogFields, e) {
	var approverComments = dojo.byId("approverComments");
	approverComments.value = dialogFields.comments;
	if (buttonApproveWithCommentsClicked){
	    approveAdvanceWithComments();
		// reset button clicked state
		//buttonApproveWithCommentsClicked = false; //Chandana
	} else if (buttonRejectClicked) {
		rejectAdvance();
		// reset button clicked state
		buttonRejectClicked = false;
	}
}



function processSaveResponse(data){
	// if validation errors exist, display them.
	if(data.validationErrors.errors != undefined && data.validationErrors.errors.length > 0){
		processValidationErrors(data.validationErrors.errors);
		if ((data.errors.length > 0)) {
			// populate error grid - added to handle field errors where submission is not allowed but there are
			// business errors that need to be added to the grid
			formatCodingBlockErrorsAdvanceAjax(data.errors);
		}
		return false;
	}
	
	if ((data.errors.length > 0)) {
		// updateErrorsGrid(data.errors);
		formatCodingBlockErrorsAdvanceAjax(data.errors);
	}
	
	return true;
	
}

/* Shows all errors in the status bar */
function processValidationErrors(errors){
	var errorMsg = '';
	dojo.forEach(errors, function(item){
		errorMsg += item + '<br/>';
	});
	
	displayValidationErrorMsg(errorMsg);
}

function displayValidationErrorMsg(msg){
	// dojo.byId('statusArea').innerHTML = msg;
	showErrorMessage(msg);
}

function showStatusMessage(messageText) {
	dojo.byId('statusArea').style.color = "green";
	dojo.byId('statusArea').innerHTML = messageText;
}

function showErrorMessage(messageText) {
	dojo.byId('statusArea').style.color = "red";
	dojo.byId('statusArea').innerHTML = messageText;
}

function enableButtonsApproverModify() {
	buttonSave.style.display = "none";
	buttonSubmit.style.display = "none";
	buttonApprove.style.display = "inline";
	buttonApproveWithComments.style.display = "inline";
	buttonApproveNext.style.display = "inline";
	buttonApproveSkip.style.display = "inline";
	buttonReject.style.display = "inline";
}

function clearErrorsGrid (){
	errorGridStore = new dojo.data.ItemFileReadStore(
			{data: {identifier: 'code',items: []}}
	);
}

function toggleApprovalButtons (flag, allowModification){
	buttonApprove.disabled = flag;
	buttonApproveWithComments.disabled = flag;
	buttonApproveNext.disabled = flag;
	buttonApproveSkip.disabled = flag;
	buttonReject.disabled = flag;
	if (flag == true && (allowModification == undefined || allowModification =='true'))  
		buttonModify.disabled = false;
		
}

function computeNewOutstandingAmount (){
	// set new manual deposit amount field for calculated display
	dojo.byId("liquidatedByDeposit").value = manualDepositAmount.value;
	// perform new calculation
	var liqExpenseAmount = Math.round(parseFloat(dojo.byId("liquidatedByExpense").innerHTML) *100)/100;
	var newDollarAmount = Math.round(parseFloat(dollarAmount.value)*100)/100;
	var newManualDepositAmount = Math.round(parseFloat(manualDepositAmount.value)*100)/100;
	var newAmountOutstanding = parseFloat(dollarAmount.value) - parseFloat(manualDepositAmount.value) - liqExpenseAmount;
	// set new amount outstanding
	dojo.byId("amountOutstanding").innerHTML = Math.round(newAmountOutstanding*100)/100;
		
}

function validateDollarAmount (){
	if (parseFloat(dojo.byId('dollarAmount').value) <= 0){
		showErrorMessage("Requested advance amount should be greater than 0");
		return false;
	}
	
	if (parseFloat(dojo.byId('dollarAmount').value) >= 100000){
		showErrorMessage("Requested advance amount not allowed. Please try a lesser amount.");
		return false;
	}
	
	var liquidatedByExpense = parseFloat (dojo.byId("liquidatedByExpense").innerHTML);
	var liquidatedByDeposit = parseFloat (dojo.byId("liquidatedByDeposit").innerHTML);
	var sumLiquidations = liquidatedByExpense + liquidatedByDeposit;
	if (sumLiquidations > 0 && parseFloat(dojo.byId('dollarAmount').value) < sumLiquidations){
		showErrorMessage("Requested advance amount may not be less than Liquidated amount");
		return false;
	}
	
	return true;
}

function validateManualDepositAmount (){
	if (parseFloat(dojo.byId("manualDepositAmount").value) > 0){
/*	if (parseFloat(dojo.byId("manualDepositAmount").value) > parseFloat(dojo.byId("amountOutstanding").innerHTML)){
		showErrorMessage("Manual deposit amount may not exceed amount outstanding");
		return false;
	}*/
	var totalLiquidatedAmount = parseFloat(dojo.byId("manualDepositAmount").value) +
			parseFloat(dojo.byId("liquidatedByExpense").innerHTML);
	if (totalLiquidatedAmount > parseFloat(dojo.byId("dollarAmount").value)){
			showErrorMessage("Total amount liquidated may not exceed advance amount");
			return false;
	}
	} else if (parseFloat(dojo.byId("manualDepositAmount").value) <= 0) {
		showErrorMessage("Manual deposit amount should be greater than 0");
		return false;
	}
	
	if (parseFloat(dojo.byId('manualDepositAmount').value) >= 100000){
		showErrorMessage("Manual deposit amount not allowed. Please try a lesser amount.");
		return false;
	}
	
	return true;
}

// Enable or disable form fields. When flag is true, the background color is
// changed to white to override default
// Firefox behavior that turns background color to dark grey
function toggleAllFields(flag){
	   // data input fields
	   var f = document.getElementsByTagName('input');
	   for(var i=0;i<f.length;i++){
	   if(f[i].getAttribute('type')!='submit'){
		   f[i].disabled = flag;
		  /*
			 * if (flag == true) f[i].style.backgroundColor = 'white'; else
			 * f[i].style.backgroundColor = '';
			 */
	   }
	   
	   }
	   // dropdowns
	   var f = document.getElementsByTagName('select');
	   for(var i=0;i<f.length;i++){
		   f[i].disabled = flag;
		/*
		 * if (flag == true) f[i].style.backgroundColor = 'white'; else
		 * f[i].style.backgroundColor = '';
		 */
		   }	
	   // textareas
	   var f = document.getElementsByTagName('textarea');
	   for(var i=0;i<f.length;i++){
		   if(f[i].name != "comments" && f[i].name != "commentAfterModify"){
			   //[mc 6/2/10] - Making fields read only instead of disabled to enable scrolling
			   //f[i].disabled = flag;
			   if(flag){ 
					f[i].readOnly = true;f[i].style.color = 'gray';
			   }else{
					f[i].readOnly = false;f[i].style.color = 'black';
			   }
		   }
	    /*
		 * if (flag == true) f[i].style.backgroundColor = 'white'; else
		 * f[i].style.backgroundColor = '';
		 */
		   }
	   }

// toggles only dojo combo boxes
function toggleDojoControls (form, flag){
	
	var elem = document.getElementById(form).elements;
 // var box = dijit.byId('box');
 for(var i = 0; i < elem.length; i++){
 var dijitElement = dijit.byId(elem[i].id);
 if (dijitElement != undefined)
     if ((dijitElement.declaredClass == 'dijit.form.ComboBox') || (dijitElement.declaredClass == 'dijit.form.FilteringSelect'))
     		dijitElement.attr('disabled',flag);
 }
	
}

// toggles only dojo combo boxes
function toggleDojoControlsReadOnly (form, flag){
	
	var elem = document.getElementById(form).elements;
 // var box = dijit.byId('box');
 for(var i = 0; i < elem.length; i++){
 var dijitElement = dijit.byId(elem[i].id);
 if (dijitElement != undefined)
     if ((dijitElement.declaredClass == 'dijit.form.ComboBox') || (dijitElement.declaredClass == 'dijit.form.FilteringSelect') ||
     		(dijitElement.declaredClass == 'dijit.form.CheckBox'))
     		dijitElement.attr('readOnly',flag);
 }
	
}

// Enable or disable a single field
function toggleField(field, flag){
	// toggle field
	field.disabled = flag;
	// if (flag == true)
		// field.style.backgroundColor = 'white';
}

function toggleAllFieldsReadonly(flag){
	   // data input fields
	   var f = document.getElementsByTagName('input');
	   for(var i=0;i<f.length;i++){
	   if(f[i].getAttribute('type')!='submit'){
		   f[i].disabled = false;
		   f[i].readOnly = flag;
		  /*
			 * if (flag == true) f[i].style.backgroundColor = 'white'; else
			 * f[i].style.backgroundColor = '';
			 */
	   }
	   }
	   
	   // explicitly disable permanent checkbox. Since checkboxes marked readonly may be toggled, this 
	   // will prevent the user from changing permanent status. The permanent flag will not be copied over
	   // in case of PROC advances.
	   dojo.byId('permanentAdvInd').disabled = flag;
	   
	   // dropdowns
	   var f = document.getElementsByTagName('select');
	   for(var i=0;i<f.length;i++){
		   f[i].disabled = false;
		   f[i].readOnly = flag;
		/*
		 * if (flag == true) f[i].style.backgroundColor = 'white'; else
		 * f[i].style.backgroundColor = '';
		 */
		   }	
	   // textareas
	   var f = document.getElementsByTagName('textarea');
	   for(var i=0;i<f.length;i++){
		   if(f[i].name != "commentAfterModify"){
			   f[i].disabled = false;
			   f[i].readOnly = flag;
		   }
	    /*
		 * if (flag == true) f[i].style.backgroundColor = 'white'; else
		 * f[i].style.backgroundColor = '';
		 */
		   }
	   }

// toggles only dojo combo boxes
function toggleDojoControlsReadonly (form, flag){
	
	var elem = document.getElementById(form).elements;
// var box = dijit.byId('box');
for(var i = 0; i < elem.length; i++){
var dijitElement = dijit.byId(elem[i].id);
if (dijitElement != undefined)
  if ((dijitElement.declaredClass == 'dijit.form.ComboBox') || (dijitElement.declaredClass == 'dijit.form.FilteringSelect')){
    if (dijit.byId('commentsAfterModify')){
    dijitElement.setAttribute('disabled',false);
	   dijitElement.setAttribute('readOnly',flag);
    }
  }

}
	
}

// Enable or disable a single field
function toggleFieldReadonly(field, flag){
	// toggle field
	field.disabled = flag;
	field.readOnly = flag;
	// if (flag == true)
		// field.style.backgroundColor = 'white';
}

// Adds to the onchange event of input fields, will be utilized to check if any
// changes have been made to the form
function addOnChange (){
	   // data input fields
	 var f = document.getElementsByTagName('input');
	 for(var i=0;i<f.length;i++){
	 if(f[i].getAttribute('type')!='submit'){
		 addChangeEvent(f[i]);
	   }
	   }
	  // dropdowns
	   var f = document.getElementsByTagName('select');
	   for(var i=0;i<f.length;i++){
		   addChangeEvent(f[i]);
		   }
	  // text areas
	   var f = document.getElementsByTagName('textarea');
	   for(var i=0;i<f.length;i++){
		   addChangeEvent(f[i]);
		   }
	   // dojo dropdowns
	   	var elem = document.getElementById('advance').elements;
	       // var box = dijit.byId('box');
	       for(var i = 0; i < elem.length; i++){
	       var dijitElement = dijit.byId(elem[i].id);
	       if (dijitElement != undefined)
	           if (dijitElement.declaredClass == 'dijit.form.ComboBox' || (dijitElement.declaredClass == 'dijit.form.FilteringSelect'))
	        	   dojo.connect(dijitElement, 'onChange', setChanged);
	       }
	       
	       // Date images, used onclick since onchange won't fire when the
			// field
	       // is modified through a script
	       dojo.connect(dojo.byId("requestDateSelector"), 'onclick', setChanged);
	       dojo.connect(dojo.byId("fromDateSelector"), 'onclick', setChanged);
	       dojo.connect(dojo.byId("toDateSelector"), 'onclick', setChanged);
	   }

function addChangeEvent (field){
	if(window.addEventListener){ // Mozilla, Netscape, Firefox
		field.addEventListener('change', setChanged, false);
	} else { // IE
		field.attachEvent('onchange', setChanged);
	}

}

function removeChangeEvent (field){
	if(window.removeEventListener){ // Mozilla, Netscape, Firefox
		field.removeEventListener('change', setChanged, false);
	} else { // IE
		field.removeEvent('onchange', setChanged);
	}
}

// Updates the status of a form to modified in case a field was changed
function setChanged (){
	formModified = true;
}

//Updates the status of a form to unmodified when resetting 
function setUnchanged(){
	formModified = false;
}


// Validates dates format and value
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

// Validates numbers
function isInteger(s){
		var i;
	    for (i = 0; i < s.length; i++){   
	        // Check that current character is number.
	        var c = s.charAt(i);
	        if (((c < "0") || (c > "9"))) return false;
	    }
	    // All characters are numbers.
	    return true;
	}

// Validates amounts for maximum of 2 decimal values

function isDecimal(element)
{
return (/^\d+(\.\d)?$/.test(element.value) || /^\d+(\.\d\d)?$/.test(element.value)
		 || /^(\.\d\d)?$/.test(element.value) || /^\d+(\.)?$/.test(element.value));
}

function disableCbDeleteButtons (flag){
	noOfCodingBlocks = dojo.byId("cbTable").rows.length;
	for(var i=1;i < noOfCodingBlocks ; i++){
		dojo.byId("del_" + i).disabled = flag;
	}
}
	
	function updateDisplayAmounts (data){
		if (data.response.outstandingAmount != null &&
				parseFloat(data.response.outstandingAmount) >= 0){
			dojo.byId("amountOutstanding").innerHTML = data.response.outstandingAmount.toFixed(2);
		}
		if (data.response.manualDepositAmount != null &&
				parseFloat(data.response.manualDepositAmount) > 0){
			dojo.byId("liquidatedByDeposit").innerHTML = data.response.manualDepositAmount.toFixed(2);
		}
}

//Call this function if an Ajax error occurred
function handleAjaxError(error){
	        showErrorMessage("An error has occured");
	        return true;	        
}




