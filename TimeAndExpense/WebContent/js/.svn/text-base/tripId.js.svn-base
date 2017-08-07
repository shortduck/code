  	var calFromOnClick;
  	var calToOnClick;
  	var formModified = false;
  	var modifyBtnClicked = false;
  	var onChangePDFCheckBox_handle = null;
  	
	/* Initialization immediately after page load */
	function initTripId(){

		// sync calendar dates to date fields
		syncDate(dojo.byId('f_fromDateField'));
		syncDate(dojo.byId('f_toDateField'));
		
		// refresh expense event Id
		if(dojo.byId('expenseEventId_hidden') != null)
			dojo.byId('expenseEventId').value = dojo.byId('expenseEventId_hidden').value;
		
		// setup revision no.
		if(dojo.byId('revNo').innerHTML == '')
			dojo.byId('revNo').innerHTML = '0';
		
		// set up view state
		var viewSpec = dojo.byId('viewJsonId').value;
		if(viewSpec != null && viewSpec != ""){
			setupDisplay(dojo.fromJson(viewSpec));
		}	
		
		// attach listener on change
		dojo.connect(dojo.byId('f_fromDateField'), 'onchange', matchToDateWithFromDate);
		
		// attach listener on for Out-of-State checkbox and PDF
		
		if (/MSIE (\d+\.\d+);/.test(navigator.userAgent)){
			// Internet Explorer
			dojo.connect(dojo.byId('outOfStateCheckBox'), 'onpropertychange', toggleAuthCodes);
			onChangePDFCheckBox_handle = dojo.connect(dojo.byId('PDFCheckBox'), 'onpropertychange', onChangePDFCheckBox);
		}
		else{
			dojo.connect(dojo.byId('outOfStateCheckBox'), 'onchange', toggleAuthCodes);
			onChangePDFCheckBox_handle = dojo.connect(dojo.byId('PDFCheckBox'), 'onchange', onChangePDFCheckBox);
		}
		
		addOnChange();
		//onChangePDFCheckBox();
		
	}
	
	
	function validateNonTravel(errorMsg){
		//Travel expense
		if(dojo.byId('expenseTypeN').checked && dojo.byId('outOfStateCheckBox')!= null && dojo.byId('outOfStateCheckBox').checked){
			errorMsg.msg = "For non-travel expenses, Out-of-state travel should be unchecked";
		}
		return (errorMsg.msg.length >0)? false: true;
	}
	
	function onChangePDFCheckBox(){
		
		//disable Travel/Non-Travel
		if (dojo.byId('PDFCheckBox').checked){
			
			dojo.byId('outOfStateCheckBox').checked = false;
			dojo.byId('outOfStateCheckBox').disabled = true;
			dojo.byId('expenseTypeN').disabled = true;
			dojo.byId('expenseTypeY').disabled = true;
		}
		else{
			dojo.byId('outOfStateCheckBox').disabled = false;			
			dojo.byId('expenseTypeN').disabled = false;
			dojo.byId('expenseTypeY').disabled = false;
			
		}			
	}
	
	function setupPDF(data){

		if (data.response.IsPDFRolePResent == "no"){
			//uncheck PDF checkbox before hiding it.
			dojo.byId('PDFCheckBox').checked = false;			
			dojo.byId('PDFCheckBox').style.display = 'none';
			//Firefox
			dojo.byId('PDFCheckBoxLabel1').hidden = true;
			//IE 
			dojo.byId('PDFCheckBoxLabel1').style.display = 'none';
		}
		else if (data.response.IsPDFRolePResent == "readonly"){			
			dojo.byId('PDFCheckBoxLabel1').disabled = true;
			dojo.byId('PDFCheckBox').disabled = true;
		}
		else  if (data.response.IsPDFRolePResent == "yes"){
			dojo.byId('PDFCheckBoxLabel1').disabled = false;
			dojo.byId('PDFCheckBox').disabled = false;
		}
	}
	
	function serverRequest(serviceUrl, _data, callbackMethod){
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
	 * Function to match toDate with fromDate if the toDate is blank.
	 */
	function matchToDateWithFromDate(){
		var fDate = dojo.byId('f_fromDateField');
		var tDate = dojo.byId('f_toDateField');
		if(tDate.value.length == 0){
				syncDate(fDate);
				tDate.value = fDate.value;
			}	
	}
	
	/* 
	 * Controls the display behavior of form fields and 
	 * displays expense errors in errors grid, if present.
	 */
  	function setupDisplay(view){  		
	  		if(!view.editable){
	  			disableFormFields();
	  			dojo.byId('saveBtn').disabled = true;
	  		}else{
	  			enableFormFields();
	  			dojo.byId('saveBtn').disabled = false;
	  		}	
	  		if(view.modify == "DISABLED"){
	  			dojo.byId('modifyBtn').disabled = true;
	  		}else if(view.modify == "ENABLED") {
	  			dojo.byId('modifyBtn').disabled = false;
	  		}
	  		if(view.previousRevision == "DISABLED"){
	  			dojo.byId('prevRevBtn').disabled = true;
	  		}else if(view.previousRevision == "ENABLED"){
	  			dojo.byId('prevRevBtn').disabled = false;
	  		}
	  		if(view.nextRevision == "DISABLED"){
	  			dojo.byId('nextRevBtn').disabled = true;
	  		}else if(view.nextRevision == "ENABLED"){
	  			dojo.byId('nextRevBtn').disabled = false;
	  		}
	  		// disable multi-select options if checkbox not checked
	  		// but page can modified
	  		if (view.editable && !dojo.byId('outOfStateCheckBox').checked){
	  			dojo.byId('outOfStateAuthCodes').disabled = true;
	  		}
	}
	
	/* Enable all form fields */
	function enableFormFields(){
		dojo.byId('f_fromDateField').disabled = false;
		dojo.byId('f_toDateField').disabled = false;

		dojo.byId('exp_desc').disabled = false;
		dojo.byId('bizNature').disabled = false;
		
		var exp_desc = dojo.byId('exp_desc');
		exp_desc.readOnly = false;
		exp_desc.style.color = 'black';
			
		var biz_nature = dojo.byId('bizNature');
		biz_nature.readOnly = false;
		biz_nature.style.color = 'black';
		
		dojo.byId('expenseTypeN').disabled = false;
		dojo.byId('expenseTypeY').disabled = false;
		dojo.byId('outOfStateCheckBox').disabled = false;
		if (dojo.byId('outOfStateCheckBox').checked)
			dojo.byId('outOfStateAuthCodes').disabled = false;
		
		
		if(calFromOnClick != null) dojo.byId('f_fromDateTrigger').onclick = calFromOnClick;
		if(calToOnClick != null) dojo.byId('f_toDateTrigger').onclick = calToOnClick;
	
		// anytime the form fields are enabled, SAVE button must also be enabled.
		dojo.byId('saveBtn').disabled = false;
		
		//call Action to test if the user has PDf roles.		
		serverRequest("isPDFModuleAvailable.action", null, setupPDF);
		
	}
	
	/* Disable all form fields */
	function disableFormFields(){
		
		//call Action to test if the user has PDf roles.		
		serverRequest("isPDFModuleAvailable.action", null, setupPDF);
		if (dojo.byId('PDFCheckBox') != null && dojo.byId('PDFCheckBox').disabled == false && !dojo.byId('PDFCheckBoxLabel1').hidden){
			dojo.byId('PDFCheckBox').disabled = true;
			dojo.byId('PDFCheckBoxLabel1').disabled = true;
		}
		
		dojo.byId('f_fromDateField').disabled = true;
		dojo.byId('f_toDateField').disabled = true;
		
		var exp_desc = dojo.byId('exp_desc');
		exp_desc.readOnly = true;
		exp_desc.style.color = 'gray';

		var biz_nature = dojo.byId('bizNature');
		biz_nature.readOnly = true;
		biz_nature.style.color = 'gray';
		
		dojo.byId('expenseTypeN').disabled = true;
		dojo.byId('expenseTypeY').disabled = true;
		dojo.byId('outOfStateCheckBox').disabled = true;
		
		// ZH 12/01/2010 - The selected options do not highlight in IE8 if the Select is disabled.
		// Commented line below and only disabled unselected options
		//dojo.byId('outOfStateAuthCodes').disabled = true;
		disableSelectOptions(false);
		
		calFromOnClick = dojo.byId('f_fromDateTrigger').onclick;
		calToOnClick = dojo.byId('f_toDateTrigger').onclick;
		dojo.byId('f_fromDateTrigger').onclick = null;
		dojo.byId('f_toDateTrigger').onclick = null;
		
	}

	/* Validate user input upon SAVE */  
	function validateUserInput(errorMsg){
		return validateFromDate(errorMsg) && validateToDate(errorMsg)
			&& validateDateFromLessThanTo(errorMsg) && validateBizNature(errorMsg) 
			&& validateOutOfStateTravel(errorMsg) && validateComments(errorMsg)
			&& validateNonTravel(errorMsg) 
			;
	}

	function validateFromDate(errorMsg){
		var fDate = dojo.byId('f_fromDateField').value;
		
		errorMsg.msg = (fDate == '') ? "Expense 'From' date missing":'';
		return (errorMsg.msg.length >0)? false: true;	
	}
	function validateToDate(errorMsg){
		var tDate = dojo.byId('f_toDateField').value;
		
		errorMsg.msg = (tDate == '') ? "Expense 'To' date missing":'';
		return (errorMsg.msg.length >0)? false: true;	
	}
	function validateDateFromLessThanTo(errorMsg){
		var fDate = Date.parse(dojo.byId('f_fromDateField').value);
		var tDate = Date.parse(dojo.byId('f_toDateField').value);
		
		errorMsg.msg = (fDate.compareTo(tDate)>0)? "Expense 'To' date cannot be less than 'From' date":'';
		return (errorMsg.msg.length >0)? false: true;	
	}
/*	function validateDescription(errorMsg){
		var desc = trimStr(dojo.byId('exp_desc').value);
		
		errorMsg.msg = (desc == '') ? "User event description missing!":'';
		return (errorMsg.msg.length >0)? false: true;	
	}
*/	function validateBizNature(errorMsg){
		var bizNature = trimStr(dojo.byId('bizNature').value);
		if (bizNature == '') {
			errorMsg.msg  = "Nature of official business missing";	
		}
		return (errorMsg.msg.length >0)? false: true;	
	}
	function validateOutOfStateTravel(errorMsg){
		var bizNature = dojo.byId('outOfStateCheckBox').checked;
		var authCodes = dojo.byId('outOfStateAuthCodes');
		
		if(bizNature && authCodes.selectedIndex < 0){
			errorMsg.msg = "Auth code(s) required for Out-of-state travel";
		}
		
		return (errorMsg.msg.length >0)? false: true;	
	}

	function validateComments(errorMsg){
		var expDesc = dojo.byId("exp_desc").value;
		if (expDesc.length > 255){
			errorMsg.msg  = "User event description longer than allowed (255 characters). Please enter a shorter value.";
		}
		return (errorMsg.msg.length >0)? false: true;	
	}
	
	
	
	
	
	/* Invoked upon SAVE. Saves expense if no validation error */  
  	function saveExpense(){
  		
		var errorMsg = {msg:''};
		var problemDuringSave = false;
		// clear any previous error messages
		//displayValidationErrorMsg('');
		notifyTripId('', false);
		
		// validate user input
		if(validateUserInput(errorMsg)){
			setUnchanged();
			// submit save request
			problemDuringSave = submitSaveRequest();
			//dojo.byId('errorMsg').innerHTML = msg;
		}else{
			problemDuringSave = true;
			// show validation error
			//displayValidationErrorMsg(errorMsg.msg);
			notifyTripId(errorMsg.msg, true);
		}
		// cleanup popup references
		processPopupPostSave ();
		
		// mark the Coding Block tab to be refreshed
		tabsToReload.push('cbTab');		
						
		return problemDuringSave;
  		//validateUserInput(errorMsg);
  	}
  	
  	function submitSaveRequest(){
		dojo.xhrPost ({
			url: 'SaveExpense.action',
			form: 'TripIdForm',
			handleAs: "json",
    		sync: true,	   			
			load: function (data) {
					// process server response
					processTripIdSaveResponse(data);
			},
			// Call this function if an error happened
			error: function (error) {
			        //displayValidationErrorMsg("Save failed!");
			        notifyTripId("Save failed", true);
			     // revert to last tab if save was done through the popup
					revertToLastTab(lastTabForAjaxSave);
			        // an error has occured. 
			        return true;
			        
			}
            });
		return false;
	}

	/* Process server response to the Trip Id save call. */
	function processTripIdSaveResponse(data){
		// if validation errors exist, display them.
		if(data.validationErrors.errors != undefined){
			processTripIdValidationErrors(data.validationErrors.errors);
			revertToLastTab(lastTabForAjaxSave);
			return;
		}
		
		// if multiple appointments found for the given date range,
		// prompt user to choose an appointment
		if(data.validationErrors.appointments != null){
			handleMultipleAppointments(dojo.fromJson(data.validationErrors.appointments[0]), "EXPENSE");
			return;
		}
		
		// display errors in error grid
		if((data.errors != null)){
			updateErrorsGrid(data.errors);
		}
		
		// if save successful
		if(data.response.expenseEventId != null){
			// update request Id
			dojo.byId('expenseEventId').value = data.response.expenseEventId;
			
			// update revision no
			dojo.byId('revNo').innerHTML = data.response.revisionNo;
			
			// update prev next button state
			if(data.response.revisionNo > 0){
				dojo.byId('prevRevBtn').disabled = false;
				dojo.byId('nextRevBtn').disabled = true;
			}
			
			// show save confirmation
			notifyTripId('Saved successfully', false);
			// refresh details tab if save was performed through the pop up
			if (dijit.byId('expense_tab_container').selectedChildWidget.id != "idTab"){
				dijit.byId('expense_tab_container').selectedChildWidget.refresh();
			} else {
				setupExpenseDetailDefaults();
			}
			
			// if modify was saved, set the tabs to reload
			if(modifyBtnClicked){ 
				if(findElementPositionInArray(tabsToReload, 'expenseDetailsTab') < 0)
					tabsToReload.push('expenseDetailsTab');
				if(findElementPositionInArray(tabsToReload, 'liquidationsTab') < 0)
					tabsToReload.push('liquidationsTab');

				// reset variable state
				modifyBtnClicked = false;
			}	
		}else{
			// expense event Id is always returned, if operation is successful
			notifyTripId('Save failed!', true);
			// this return will evaluate to problemsDuringSave = true;
			return true;
		}
		
	}
	
	/* Shows all errors in the status bar */
	function processTripIdValidationErrors(errors){
		var errorMsg = '';
		dojo.forEach(errors, function(item){
			errorMsg += item + '<br/>';
		});
		
		//displayValidationErrorMsg(errorMsg);
		notifyTripId(errorMsg, true);
	}
	
	/* Invoked when modify is clicked */
  	function modifyExpense(){
  		setChanged ();
  		
  		// construct details calendar control.
  		// We construct it here, because it's
  		// here that the input fields are being enabled.
  		constructDetailsCalendarControl();
  		
  		// enable form fields
  		enableFormFields();
  		
  		// disable modify button
  		dojo.byId('modifyBtn').disabled = true;
  		
  		// disable prev & next rev options
		dojo.byId('prevRevBtn').disabled = true;
		dojo.byId('nextRevBtn').disabled = true;

  		// inform server that modification has been requested
  		ajaxGet('ModifyExpense.action', null);
  		
  		// store modify btn click flag
  		modifyBtnClicked = true;
  	}
  	
	// Utility method to make an Ajax call to the server and 
	// invoke the callback method upon receiving successful response.
	function ajaxGet(serviceUrl, callbackMethod){
		dojo.xhrPost({
			url: serviceUrl,
			handleAs: "json",
			handle: function(data,args){
				if(data.response == null){
					//if errors, do not pursue the effect of call!
					console.warn("error!",args);
					disableFormFields();
				}else if(callbackMethod != null){
					callbackMethod(data);
				}
			}
		});
	}  	
	
	// Adds to the onchange event of input fields, will be utilized to check if any changes have been made to the form
	function addOnChange (){
		// data input fields
		 var f = document.getElementsByTagName('input');
		 for(var i=0;i<f.length;i++){
			 if(f[i].getAttribute('type')!='submit' && f[i].getAttribute('name') != 'supervisorReceiptsReviewed') 
			 {	
				 addChangeEvent(f[i]);
			 }
		   }
		  // dropdowns
		   var f = document.getElementsByTagName('select');
		   for(var i=0;i<f.length;i++){
			   addChangeEvent(f[i]);
			   }
			  // dropdowns
		   var f = document.getElementsByTagName('text');
		   for(var i=0;i<f.length;i++){
			   addChangeEvent(f[i]);
			   }
		  // text areas
		   var f = document.getElementsByTagName('textarea');
		   for(var i=0;i<f.length;i++){
			   addChangeEvent(f[i]);
			   }
		   //radio 
		   var f = document.getElementsByTagName('radio');
		   for(var i=0;i<f.length;i++){
			   addChangeEvent(f[i]);
			   }
		   //checkbox
		   var f = document.getElementsByTagName('checkbox');
		   for(var i=0;i<f.length;i++){
			   addChangeEvent(f[i]);
			   }
		   
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
	
	//Updates the status of a form to unmodified when resetting 
	function setUnchanged(){
		formModified = false;
	}

	//Updates the status of a form to modified in case a field was changed
	function setChanged (){
		formModified = true;
	}

	//Check to see if a form was modified
	function checkFormState (){
		if (formModified && showUnsavedChangesPopup)
			return 'There are unsaved changes';		
	}
	
	//Enables or disables authorization codes based upon the checkbox value
	function toggleAuthCodes (){
		if (dojo.byId('outOfStateCheckBox').checked){
			dojo.byId('outOfStateAuthCodes').disabled = false;
			for (var i=0; i<dojo.byId('outOfStateAuthCodes').options.length; i++){
					// enable individual options
					document.getElementById('outOfStateAuthCodes').options[i].disabled = false;
			}
		}
		else {
			for (var i=0; i<dojo.byId('outOfStateAuthCodes').options.length; i++){ 
				if (document.getElementById('outOfStateAuthCodes').options[i].selected == true) 
					document.getElementById('outOfStateAuthCodes').options[i].selected = false;
			}
			dojo.byId('outOfStateAuthCodes').disabled = true;
		}
	}
	
	//Check to see if a form was modified
	function getFormModifiedState (){
		return formModified;
	}

	// disable individual options
	function disableSelectOptions (flag){
		for (var i=0; i<dojo.byId('outOfStateAuthCodes').options.length; i++){ 
			var item = document.getElementById('outOfStateAuthCodes').options[i];
			if (flag == false){
				// only disable if item not selected
				if (item.selected == false) {
					item.disabled = true;
				}
			} else {
				// disable all options
				item.disabled = true;
			}
			
		}
	}

	function showAdjustmentDialog(){
		
		dojo.byId("errorMsg").innerHTML = "" ;
		dijit.byId('dialogAdjustmentDialog').show();
		dojo.byId('btnClose').focus();
		window.onbeforeunload = null;
	}
	
	function closeClick(){
		dijit.byId('dialogAdjustmentDialog').hide();		
	}
