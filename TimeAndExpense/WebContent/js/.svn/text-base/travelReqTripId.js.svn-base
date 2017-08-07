  	var calFromOnClick;
  	var calToOnClick;
  	var formModified = false;
  	var modifyBtnClicked = false;
  	var buttonRejectClicked;
  	var buttonApproveNextClicked;
  	var commentsAfterModify;
  	var buttonApproveWithCommentsClicked;
  	var saveValidationErrors = false;
  	var modifyFromApprovals = false;
  
	/* Initialization immediately after page load */
	function initTripId(){
		// sync calendar dates to date fields
		syncDate(dojo.byId('f_requestDateField'));
		syncDate(dojo.byId('f_fromDateField'));
		syncDate(dojo.byId('f_toDateField'));
		
		// refresh expense event Id
		if(dojo.byId('treqEventId_hidden') != null)
			dojo.byId('treqEventId').value = dojo.byId('treqEventId_hidden').value;
		
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
		
		// attach listener on for Out-of-State checkbox
		if (/MSIE (\d+\.\d+);/.test(navigator.userAgent))
			// Internet Explorer
			dojo.connect(dojo.byId('outOfStateCheckBox'), 'onpropertychange', toggleAuthCodes);
		else
			dojo.connect(dojo.byId('outOfStateCheckBox'), 'onchange', toggleAuthCodes);
			
		
		addOnChange();
		
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
	  			dojo.byId('buttonSave').disabled = true;
	  			dojo.byId('buttonSubmit').disabled = true;
	  		}else{
	  			enableFormFields();
	  			dojo.byId('buttonSave').disabled = false;
	  			dojo.byId('buttonSubmit').disabled = false;
	  		}	
	  		
	  	/*	if(!view.editable){
	  			disableFormFields();
	  			dojo.byId('buttonSubmit').disabled = true;
	  		}else{
	  			enableFormFields();
	  			dojo.byId('buttonSubmit').disabled = false;
	  		}	*/
	  		
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
	  		
	  		alignAmountFields (view.editable);
	  		//hide advance requested label if the checkbox is not visible
	  		if (dojo.byId('requestAdvance').style.display == "none"){
				dojo.byId('labelRequestAdvance').hidden = true;		
				dojo.byId('labelRequestAdvance').style.display = 'none';
	  		}
	  		// disable Print button if expense has not been saved
	  		if (dojo.byId('treqEventId').value == "")
	  			dojo.byId('buttonPrint').disabled = true;
	}
	
	/* Enable all form fields */
	function enableFormFields(){
		dojo.byId('f_requestDateField').disabled = false;
		dojo.byId('f_fromDateField').disabled = false;
		dojo.byId('f_toDateField').disabled = false;
		dojo.byId('treq_desc').disabled = false;
		dojo.byId('exp_desc').disabled = false;
		dojo.byId('transportationVia').disabled = false;
		dojo.byId('requestAdvance').disabled = false;
		
		/*dojo.byId('transportationAmount').disabled = false;
		dojo.byId('lodgingAmount').disabled = false;
		dojo.byId('mealsAmount').disabled = false;
		dojo.byId('registAmount').disabled = false;
		dojo.byId('otherAmount').disabled = false;*/
		
		var transportationAmount = dojo.byId('transportationAmount');
		transportationAmount.readOnly = false;
		transportationAmount.style.color = '';
		
		var airfareAmount = dojo.byId('airfareAmount');
		airfareAmount.readOnly = false;
		airfareAmount.style.color = '';
		
		var lodgingAmount = dojo.byId('lodgingAmount');
		lodgingAmount.readOnly = false;
		lodgingAmount.style.color = '';
		
		var mealsAmount = dojo.byId('mealsAmount');
		mealsAmount.readOnly = false;
		mealsAmount.style.color = '';
		
		var registAmount = dojo.byId('registAmount');
		registAmount.readOnly = false;
		registAmount.style.color = '';
		
		var otherAmount = dojo.byId('otherAmount');
		otherAmount.readOnly = false;
		otherAmount.style.color = '';
		
		var officeProgram = dojo.byId('officeProgram');
		officeProgram.readOnly = false;
		officeProgram.style.color = '';
		
		var destination = dojo.byId('destination');
		destination.readOnly = false;
		destination.style.color = '';
		
		var exp_desc = dojo.byId('exp_desc');
		exp_desc.readOnly = false;
		exp_desc.style.color = '';
			
		var transportationVia = dojo.byId('transportationVia');
		transportationVia.readOnly = false;
		transportationVia.style.color = 'black';
		
		var treq_desc = dojo.byId('treq_desc');
		treq_desc.readOnly = false;
		treq_desc.style.color = 'black';
		
		/*dojo.byId('expenseTypeN').disabled = false;
		dojo.byId('expenseTypeY').disabled = false;*/
		dojo.byId('outOfStateCheckBox').disabled = false;
		if (dojo.byId('outOfStateCheckBox').checked)
			dojo.byId('outOfStateAuthCodes').disabled = false;
		
		if(calFromOnClick != null) dojo.byId('f_fromDateTrigger').onclick = calFromOnClick;
		if(calToOnClick != null) dojo.byId('f_toDateTrigger').onclick = calToOnClick;
		
		// anytime the form fields are enabled, SAVE button must also be enabled.
		dojo.byId('buttonSave').disabled = false;
		dojo.byId('buttonSubmit').disabled = false;
		
	}
	
	/* Disable all form fields */
	function disableFormFields(){
		dojo.byId('f_fromDateField').disabled = true;
		dojo.byId('f_toDateField').disabled = true;
		dojo.byId('f_requestDateField').disabled = true;
		
		var exp_desc = dojo.byId('exp_desc');
		exp_desc.readOnly = true;
		exp_desc.style.color = 'gray';

		var treq_desc = dojo.byId('treq_desc');
		treq_desc.readOnly = false;
		treq_desc.style.color = 'gray';
		
		var biz_nature = dojo.byId('transportationVia');
		biz_nature.readOnly = true;
		biz_nature.style.color = 'gray';
		
		var transportationAmount = dojo.byId('transportationAmount');
		transportationAmount.readOnly = true;
		transportationAmount.style.color = 'gray';
		
		var airfareAmount = dojo.byId('airfareAmount');
		airfareAmount.readOnly = true;
		airfareAmount.style.color = 'gray';
		
		var lodgingAmount = dojo.byId('lodgingAmount');
		lodgingAmount.readOnly = true;
		lodgingAmount.style.color = 'gray';
		
		var mealsAmount = dojo.byId('mealsAmount');
		mealsAmount.readOnly = true;
		mealsAmount.style.color = 'gray';
		
		var registAmount = dojo.byId('registAmount');
		registAmount.readOnly = true;
		registAmount.style.color = 'gray';
		
		var otherAmount = dojo.byId('otherAmount');
		otherAmount.readOnly = true;
		otherAmount.style.color = 'gray';
		
		var officeProgram = dojo.byId('officeProgram');
		officeProgram.readOnly = true;
		officeProgram.style.color = 'gray';
		
		var destination = dojo.byId('destination');
		destination.readOnly = true;
		destination.style.color = 'gray';
		
		dojo.byId('requestAdvance').disabled = true;
		
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
	/*	if (!validateAmounts(errorMsg))
			return false;*/
		return validateRequestDate(errorMsg) && validateFromDate(errorMsg) && validateToDate(errorMsg)
			&& validateDateFromLessThanTo(errorMsg) && validateDestination(errorMsg)
			&& validateTransportationVia(errorMsg) && validateBizNature(errorMsg) 			
			&& validateOutOfStateTravel(errorMsg) && validateComments(errorMsg) 
			&& validateAmounts(errorMsg);
	}

	function validateRequestDate(errorMsg){
		var fDate = dojo.byId('f_requestDateField').value;
		
		errorMsg.msg = (fDate == '') ? "'Request' date missing":'';
		return (errorMsg.msg.length >0)? false: true;	
	}
	
	function validateFromDate(errorMsg){
		var fDate = dojo.byId('f_fromDateField').value;
		
		errorMsg.msg = (fDate == '') ? "'From' date missing":'';
		return (errorMsg.msg.length >0)? false: true;	
	}
	function validateToDate(errorMsg){
		var tDate = dojo.byId('f_toDateField').value;
		
		errorMsg.msg = (tDate == '') ? "'To' date missing":'';
		return (errorMsg.msg.length >0)? false: true;	
	}
	function validateDateFromLessThanTo(errorMsg){
		var fDate = Date.parse(dojo.byId('f_fromDateField').value);
		var tDate = Date.parse(dojo.byId('f_toDateField').value);
		
		errorMsg.msg = (fDate.compareTo(tDate)>0)? "'To' date cannot be less than 'From' date":'';
		return (errorMsg.msg.length >0)? false: true;	
	}
/*	function validateDescription(errorMsg){
		var desc = trimStr(dojo.byId('exp_desc').value);
		
		errorMsg.msg = (desc == '') ? "User event description missing!":'';
		return (errorMsg.msg.length >0)? false: true;	
	}
*/	
	function validateBizNature(errorMsg){
		var exp_desc = trimStr(dojo.byId('exp_desc').value);
		if (exp_desc == '') {
			errorMsg.msg  = "Nature of Official Business missing";	
		} else if (exp_desc.length > 80){
			errorMsg.msg  = "Nature of Official Business longer than allowed (80 characters). Please enter a shorter value.";
		}
		return (errorMsg.msg.length >0)? false: true;	
	}
	
	function validateTransportationVia(errorMsg){
		var transportationVia = trimStr(dojo.byId('transportationVia').value);
		if (transportationVia == '') {
			errorMsg.msg  = "Transportation Via missing";	
		}
		return (errorMsg.msg.length >0)? false: true;	
	}
	
	function validateOutOfStateTravel(errorMsg){
		var bizNature = dojo.byId('outOfStateCheckBox').checked;
		var authCodes = dojo.byId('outOfStateAuthCodes');
		
	/*	if(bizNature && authCodes.selectedIndex < 0){
			errorMsg.msg = "Auth code(s) required for Out-of-state travel";
		}*/
		
		return (errorMsg.msg.length >0)? false: true;	
	}

	function validateComments(errorMsg){
		var expDesc = dojo.byId("treq_desc").value;
		if (expDesc.length > 255){
			errorMsg.msg  = "User event description longer than allowed (255 characters). Please enter a shorter value.";
		}
		return (errorMsg.msg.length >0)? false: true;	
	}
	
	/* ZH - Commented to synch with JSP - Build # 10
	function validateOfficeProgram(errorMsg){
		var officeProgram = trimStr(dojo.byId('officeProgram').value);
		if (officeProgram == '') {
			errorMsg.msg  = "Office/Program missing";	
		}
		return (errorMsg.msg.length >0)? false: true;	
	}*/
	
	function validateDestination(errorMsg){
		var destination = trimStr(dojo.byId('destination').value);
		if (destination == '') {
			errorMsg.msg  = "Destination missing";	
		}
		return (errorMsg.msg.length >0)? false: true;	
	}
	
	
	/* Invoked upon SAVE. Saves expense if no validation error */  
  	function saveTravelRequisition(){
  		
		var errorMsg = {msg:''};
		var problemDuringSave = false;
		// clear any previous error messages
		//displayValidationErrorMsg('');
		//notifyTripId('', false);
		showErrorMessage ("", "ID");
		
		// validate user input
		if(validateUserInput(errorMsg)){
			setUnchanged();
			// submit save request
			problemDuringSave = save();
			// enable Print button after Save
			if (dojo.byId('buttonPrint').disabled)
	  			dojo.byId('buttonPrint').disabled = false;
			return false;
			//dojo.byId('errorMsg').innerHTML = msg;
		}else{
			problemDuringSave = true;
			// show validation error
			//displayValidationErrorMsg(errorMsg.msg);
			if (errorMsg.msg != ""){
				showErrorMessage (errorMsg.msg, "ID");
				
			return true;
		}
		}
		
		
		/*if (problemDuringSave)
			return false;
		else
			return true;*/
		
  	}

	function save(){
		dojo.xhrPost ({
			url: 'SaveTravelRequisition.action',
			form: 'TripIdForm',
			handleAs: "json",
    		sync: true,	   			
			load: function (data) {
					// process server response
					processTripIdSaveResponse(data);
					return false;
			},
			// Call this function if an error happened
			error: function (error) {
			        //displayValidationErrorMsg("Save failed!");
			        //notifyTripId("Save failed", true);
				dojo.byId('errorMsg').innerHTML = "Save failed";
			     // revert to last tab if save was done through the popup
					revertToLastTab(lastTabForAjaxSave);
			        // an error has occured. 
			        return true;
			        
			}
            });
	}

	/* Process server response to the Trip Id save call. */
	function processTripIdSaveResponse(data){
		// if validation errors exist, display them.
		if(data.validationErrors.errors != undefined){
			processTripIdValidationErrors(data.validationErrors.errors);
			saveValidationErrors = true;
			return false;
		}
		
		// if multiple appointments found for the given date range,
		// prompt user to choose an appointment
		if(data.validationErrors.appointments != null){
			handleMultipleAppointments(dojo.fromJson(data.validationErrors.appointments[0]), "Travel Requisitions");
			saveValidationErrors = true;
			return false;
		}
		
		// display errors in error grid
		if((data.errors != null)){
			updateErrorsGrid(data.errors);
		}
		
		// if save successful
		if(data.response.treqEventId != null){
			// update request Id
			dojo.byId('treqEventId').value = data.response.treqEventId;
			
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
			if (dijit.byId('travel_req_tab_container').selectedChildWidget.id != "idTab"){
				dijit.byId('travel_req_tab_container').selectedChildWidget.refresh();
			}
			
			// if modify was saved, set the tabs to reload
			/*if(modifyBtnClicked){ 
				if(findElementPositionInArray(tabsToReload, 'expenseDetailsTab') < 0)
					tabsToReload.push('expenseDetailsTab');
				if(findElementPositionInArray(tabsToReload, 'liquidationsTab') < 0)
					tabsToReload.push('liquidationsTab');

				// reset variable state
				modifyBtnClicked = false;
			}	*/
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
  	function modifyTravelRequisition(){
  		setChanged ();
  		
  		if (!(dojo.byId('buttonApprove').disabled) &&
  				(dojo.byId('buttonApprove').style.display != "none")){
  			modifyFromApprovals = true;
  		}
  		
  		// construct details calendar control.
  		// We construct it here, because it's
  		// here that the input fields are being enabled.
  		//constructDetailsCalendarControl();
  		
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
  		
  		dojo.byId('buttonSave').disabled = false;
  		//dojo.byId('buttonModify').disabled = true;
  		dojo.byId('buttonSave').style.display = "inline";	
  		dojo.byId('buttonSubmit').style.display = "inline";
  		dojo.byId('buttonSubmit').disabled = false;
  		dojo.byId('buttonApprove').style.display = "none";
  		dojo.byId('buttonApproveWithComments').style.display = "none";
  		dojo.byId('buttonApproveNext').style.display = "none";
  		dojo.byId('buttonApproveSkip').style.display = "none";
  		dojo.byId('buttonReject').style.display = "none";
  		if (!dojo.byId('buttonExpense').disabled)
  			dojo.byId('buttonExpense').disabled = true;

  		// left align amount fields
  		alignAmountFields(true);

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
	
	function setDollarAmount (inputAmountField){
		/*dojo.byId('dollarAmount').value = parseFloat(dojo.byId('transportationAmount').value) 
		+ parseFloat(dojo.byId('lodgingAmount').value);*/
		/*+ dojo.byId('mealsAmount').value 
		+ dojo.byId('registAmount').value 
		+ dojo.byId('otherAmount').value;*/		
		
		dojo.byId('dollarAmount').value = getFloatValue('transportationAmount') 
											+ getFloatValue('airfareAmount')
											+ getFloatValue('lodgingAmount') 
											+ getFloatValue('mealsAmount') 
											+ getFloatValue('registAmount')
											+ getFloatValue('otherAmount');
		
		dojo.byId('dollarAmount').value = Math.round((dojo.byId('dollarAmount').value)*100)/100;
		
		if (dojo.byId('dollarAmount').value == 'NaN')
			dojo.byId('dollarAmount').value = '';
	}
	
	function getFloatValue (amountField){
		var amount = 0;
		var value = dojo.byId(amountField).value;
		if (value != "")
			amount = parseFloat(value);
		return amount;
	}
	
	function submitTravelRequisition(){
		showStatusMessage("", "ID");
		var errorMsg = {msg:''};
		showErrorMessage ("", "ID")
		var problemDuringSave = false;
		//	Check for validation
		if(!validateUserInput(errorMsg)){
			problemDuringSave = true;
			if (errorMsg.msg != ""){
				showErrorMessage (errorMsg.msg, "ID");
			 }
		}
		else if (modifyBtnClicked){
			if(validateUserInput(errorMsg)){
				dijit.byId('dialogCommentsAndSubmit').show();
			}
		} else{
			submitTR("");
		}		
	}
		
	function submitTR(comments){
	    if (formModified || modifyBtnClicked){
	    	if (!saveTravelRequisition()){
	    		if (!saveValidationErrors){
	    			submit(comments);
	    		} else {
	    			saveValidationErrors = false;
	    		}
	    	}
	    }else{
	    	submit(comments);
	    }
	    fromSubmit = false;
	}
	
	function submit(comments){
	    dojo.xhrPost({
	        url: "TravelRequisitionSubmitAction.action",
	        content: {'approverComments': comments},
	        handleAs: "json",
	        sync: true,
	        load: function(data){
	        		// display errors in error grid
					if((data.errors != null)){
							updateErrorsGrid(data.errors);
					}

	        	    if ((data.validationErrors.errors != null)) {
						processSaveResponse (data);
						return false;
					} else if ((data.response.submitSuccess != undefined) && !data.response.submitSuccess){
						showErrorMessage (data.response.msg, "ID");
					} else if (data.response.status != null && data.response.status != ""){
						if (dojo.byId('requestAdvance').checked){
							if (dojo.byId('relatedAdvance').innerHTML ==""){
								window.location = "AdvanceCreateAction.action";
							}
						} 
						dojo.byId('modifyBtn').disabled = false;
						dojo.byId('buttonSave').disabled = true;
			  			dojo.byId('buttonSubmit').disabled = true;
						disableFormFields();
						if (modifyFromApprovals) {
							enableButtonsApproverModify();
							modifyFromApprovals = false;
							showStatusMessage ("Submit Successful", "ID"); 
						} else{
							showStatusMessage ("Submit Successful", "ID"); 
				  			if (dojo.byId('buttonExpense').disabled)
				  	  			dojo.byId('buttonExpense').disabled = false;
						}
					} else {
						showErrorMessage ("An error has occurred", "ID");
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
	
	function showStatusMessage(messageText, tab) {
		var errorNode;
		if (tab == "ID")
			errorNode = dojo.byId('errorMsg');
		else 
			errorNode = dojo.byId('errorCodingBlockMsg');
		errorNode.style.color = "green";
		errorNode.innerHTML = messageText;
	}
	
	function showErrorMessage(messageText, tab) {
		var errorNode;
		if (tab == "ID")
			errorNode = dojo.byId('errorMsg');
		else 
			errorNode = dojo.byId('errorCodingBlockMsg');
		errorNode.style.color = "red";
		errorNode.innerHTML = messageText;
	}
	
	function approveTravelRequisition(){
	    dojo.xhrPost({
	        
	        url: "TravelRequisitionApproveAction.action",
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
	
	function performPostApprovalProcessing (data){
		var moduleId = data.response.moduleId;
		//AI 19898 Message display for employee with Invalid status. kp
		if (data.response.status == "EMPLOYEE_STATUS_INVALID"){
			showErrorMessage ("The employee is in a \"No Pay\" status. Please update employee status in HRMN to allow approval.");
			return;
		}	
		
		if (data.response.status == "SUBM" || data.response.status == "APPR"){
			toggleApprovalButtons (true);
			// reset save status - approvals defect fix, when the approval page is reloaded
			formModified = false;
			if (buttonApproveNextClicked || buttonApproveWithCommentsClicked){
				
			    dojo.xhrPost({
			        
			        url: "ApprovalNextTransaction.action?requestId="+dojo.byId('treqEventId').value,
			        handleAs: "json",
			        sync: true,
			        load: function(data){
			    		if (data.response.apptIdentifier != undefined && data.response.apptIdentifier != ""){
			    			var queryString = dojo.objectToQuery(data.response);
			    			// reset form status
			    			setUnchanged();
			    			window.location = "ApproveReferrer.action?" + queryString;
			    		} else {
			    			loadApprovalPage(moduleId); 
			    		}
			        },
					error: function (error){
			        	handleAjaxError(error);
			        }
			});
			} else {
				loadApprovalPage(moduleId);
			}
			
	    	/*showStatusMessage("Approve Successful", "ID");	     

			} else {
				showErrorMessage ("An error has occurred", "ID");*/
			}
		data == null;
	}
	
	function rejectTravelRequisition() {
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

			url :"TravelRequisitionRejectAction.action",
			content: {'approverComments': comments},
			handleAs :"json",
			sync: true,
			load : function(data) {
					if (data.response.status == "RJCT"){
							toggleApprovalButtons (true, data.response.allowModification);
							showStatusMessage("Reject Successful", "ID");
							setUnchanged();
							approveSkip("reject");
						} else {
							showErrorMessage ("An error has occurred", "ID");
						}
			 // updateErrorsGrid(data.errors);
			 //formatCodingBlockErrorsAdvanceAjax(data.errors);
			},
			error: function (error){
	        	handleAjaxError(error);
	        }
		});
		
		formModified = false;
	}

	
	//Call this function if an Ajax error occurred
	function handleAjaxError(error){
		        showErrorMessage("An error has occured", "ID");
		        return true;	        
	}
	
	function toggleApprovalButtons (flag, allowModification){
		dojo.byId("buttonApprove").disabled = flag;
		dojo.byId("buttonApproveWithComments").disabled = flag;
		dojo.byId("buttonApproveNext").disabled = flag;
		dojo.byId("buttonApproveSkip").disabled = flag;
		dojo.byId("buttonReject").disabled = flag;
		if (flag == true && (allowModification == undefined || allowModification =='true'))  
			dojo.byId("modifyBtn").disabled = false;
			
	}
	
	function validateAmounts(errorMsg) {
		var amountField = dojo.byId("transportationAmount");
		if(!validateAmount(amountField, "Transportation")){
			amountField.select();
			return false;
		}	
		
		 var amountField = dojo.byId("airfareAmount");
		if(!validateAmount(amountField, "Airfare")){
			amountField.select();
			return false;
		}
		
		var amountField = dojo.byId("lodgingAmount");
		if(!validateAmount(amountField, "Lodging")){
			amountField.select();
			return false;
		}	
		
		var amountField = dojo.byId("mealsAmount");
		if(!validateAmount(amountField, "Meals")){
			amountField.select();
			return false;
		}	
		
		var amountField = dojo.byId("registAmount");
		if(!validateAmount(amountField, "Registration")){
			amountField.select();
			return false;
		}	
		
		var amountField = dojo.byId("otherAmount");
		if(!validateAmount(amountField, "Other")){
			amountField.select();
			return false;
		}	
		
/*		var amountField = dojo.byId("dollarAmount").value.replace('$', '');
		if(parseFloat(amountField) <= 0 || amountField == ''){
			errorMsg.msg = "Total amount must be greater than 0";
			return false;
		}*/
		return true;
	}
	
	function validateAmount(inputAmountField, fieldName){
		if (inputAmountField.value != '') {
			if (!isDecimal(inputAmountField)) {		
				showErrorMessage("Please enter a valid " + fieldName + " amount (Example 100.00)", "ID");
				return false;
			}			
			
			if (parseFloat(inputAmountField.value) < 0){
				showErrorMessage(fieldName + " amount should be greater than 0", "ID");
				return false;
			}
			
			if (parseFloat(inputAmountField.value) >= 100000){
				showErrorMessage(fieldName + " amount not allowed. Please try a lesser amount.", "ID");
				return false;
			}		
		}
		return true;
	}
	
	function alignAmountFields(flag){
		if (flag == false){
			dojo.byId("transportationAmount").style.textAlign = "right";
			dojo.byId("airfareAmount").style.textAlign = "right";
			dojo.byId("lodgingAmount").style.textAlign = "right";
			dojo.byId("mealsAmount").style.textAlign = "right";
			dojo.byId("registAmount").style.textAlign = "right";
			dojo.byId("otherAmount").style.textAlign = "right";
		} else {
			dojo.byId("transportationAmount").style.textAlign = "";
			dojo.byId("airfareAmount").style.textAlign = "";
			dojo.byId("lodgingAmount").style.textAlign = "";
			dojo.byId("mealsAmount").style.textAlign = "";
			dojo.byId("registAmount").style.textAlign = "";
			dojo.byId("otherAmount").style.textAlign = "";
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
				submitTR(commentsAfterModify);
				dojo.byId("commentsAfterModify").value = "";
			}
			
	}
	
	function showCommentsAndSubmitApproval(dialogFields, e) {
		var approverComments = dojo.byId("approverComments");
		approverComments.value = dialogFields.comments;
		if (buttonApproveWithCommentsClicked){
			approveTravelRequisitionWithComments();
			// reset button clicked state
			buttonApproveWithCommentsClicked = false;
		} else if (buttonRejectClicked) {
			rejectTravelRequisition();
			// reset button clicked state
			buttonRejectClicked = false;
		}
	}
	
	function approveTravelRequisitionWithComments() {

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

			url :"TravelRequisitionApproveAction.action",
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
	
	function createExpense () {

		dojo.xhrPost( {

			url :"CreateExpenseFromTravelRequisition.action",
			handleAs :"json",
			sync: true,
			load : function(data) {
			if (data.response.status == "PROC"){
				
		    	showStatusMessage("Create Successful", "ID");	     
			    	if (data.response.expenseEventId != null && data.response.expenseEventId != ""){
			    		dojo.byId('expenseEventId').innerHTML = data.response.expenseEventId; 
			    		dojo.byId("expenseEventId").href = dojo.byId("expenseEventId").href + data.response.expenseEventId;
			    	}
			    	dojo.byId('buttonExpense').disabled = true;
			    	dojo.byId('modifyBtn').disabled = true;
			    	window.location = "ExpenseAction.action";
				} else {
					showErrorMessage (data.response.status, "ID");
				}      
			},
			error: function (error){
	        	handleAjaxError(error);
	        }
	});
	}
	
	function approveSkip(action){
	    dojo.xhrPost({        
	        url: "ApprovalNextTransaction.action?skip=" + action+"&requestId="+dojo.byId('treqEventId').value,
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
	}
	
	function enableButtonsApproverModify() {
		dojo.byId('buttonSave').style.display = "none";
		dojo.byId('buttonSubmit').style.display = "none";
		dojo.byId('buttonApprove').style.display = "inline";
		dojo.byId('buttonApproveWithComments').style.display = "inline";
		dojo.byId('buttonApproveNext').style.display = "inline";
		dojo.byId('buttonApproveSkip').style.display = "inline";
		dojo.byId('buttonReject').style.display = "inline";
	}