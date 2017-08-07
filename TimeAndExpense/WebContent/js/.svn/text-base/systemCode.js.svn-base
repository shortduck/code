// Created By Smriti Kaw
/** This js file is for System code javascript functions
  function filterRows
  function disableFormFields
  function updateResultGrid
  function displayValidationErrorMsg
  function setupSingleDateSysCalendar
  function prepareSingleDateSysCalendar
  function pastDateCheck
  function compareDTs
*/
var cuDT;
var accessVal;
var formModified = false;
/**
 * Displays the drop down menu for System Codes on Search page
 */
function filterRows(selectedChkBox){
		dijit.byId('resultsGrid').showMessage("Loading... Please wait");
		disableFormFields(true); 
		var filterCriteria;		 
		filterCriteria = {systemCode: '*'}; 
			
		// check if store exists
		if(resultsGridStore._getItemsArray().length > 0){
			dijit.byId('resultsGrid').filter(filterCriteria);
			
		}
	 
		dijit.byId('resultGrid').showMessage('');
		dijit.byId('resultGrid').resize();
	
		disableFormFields(false);
		
		// update record count 
		var gridRows = dijit.byId('resultsGrid').rowCount;
		if(gridRows > 0){
			dojo.byId('recordCount').innerHTML = gridRows;
			dojo.byId('recordCountMsg').style.visibility = 'visible';
		}else{
			dojo.byId('recordCount').innerHTML = '0';
			dojo.byId('recordCountMsg').style.visibility = 'hidden';
		}
				
				
	}
 
/**
 * Displays teh error message
 */
function displayValidationErrorMsg(msg){
	dojo.byId('errorMsg').innerHTML = msg;
}

/**
 * Displays the Calendar
 */
function setupSingleDateSysCalendar(){
	prepareSingleDateSysCalendar("startDate", "startDateTrigger");
	prepareSingleDateSysCalendar("endDate", "endDateTrigger");
	 		
}

function prepareSingleDateSysCalendar(input_field_id, button_id){
	Calendar.setup({
        inputField     :    input_field_id,      // id of the input field
        ifFormat       :    "%m/%d/%Y",       // format of the input field
        showsTime      :    false,            // will display a time selector
        button         :    button_id,   // trigger for the calendar (button ID)
        //onClose    		: fromClosed,
        singleClick    :    true         // double-click mode
    });
	
}

/**
 * Date Checks
 */
function pastDateCheck(input_field_id)
{
	user_date = constructDateFromInputString(trimStr(input_field_id.value));
	
	if(!isNaN(user_date) && user_date != null){
		input_field_id.value = user_date.toString('MM/dd/yyyy');
	}else{
		input_field_id.value = '';
	}
 
	return compareDTs(cuDT,input_field_id.value);
	 

	}

/**
 * Date Checks
 */
function compareDTs(start ,end)
{ 
	var fDate = Date.parse(dojo.byId('startDate').value);
	var tDate = Date.parse(dojo.byId('endDate').value);
	
	errorMsg.msg = (fDate.compareTo(tDate)>0)? "Error in dates ":'';
	return (errorMsg.msg.length >0)? false: true;	
}
/**
 * Trim String function
 */
String.prototype.trim = function () {
    return this.replace(/^\s*/, "").replace(/\s*$/, "");
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
	if (formModified)
		return 'There are unsaved changes';
}


function addOnChange(){
	// data input fields
	 var f = document.getElementsByTagName('input');
	 for(var i=0;i<f.length;i++){
		 if(f[i].getAttribute('type')!='submit') 
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

function addChangeEvent(field){
	if(window.addEventListener){ // Mozilla, Netscape, Firefox
		field.addEventListener('change', setChanged, true);
	} else { // IE
		field.attachEvent('onchange', setChanged);
	}
}


function removeChangeEvent(field){
	if(window.removeEventListener){ // Mozilla, Netscape, Firefox
		field.removeEventListener('change', setChanged, true);
	} else { // IE
		field.removeEvent('onchange', setChanged);
	}

}
/**
 *  this function sets the avlues from the server to fields
 * @param data
 * @return
 */

function modifySetdata(data)
{ 
		var serverResponse = dojo.fromJson(dojo.byId('jsonResponse_hidden').value); 
		//alert(serverResponse[0].systemCode);
		dojo.byId('systemCode').value=data.response[0].systemCode;
		dojo.byId('startDate').value=data.response[0].startDate;
		dojo.byId('endDate').value=data.response[0].endDate;
		dojo.byId('description').value=data.response[0].description;
		dojo.byId('value').value=data.response[0].value;
		dojo.byId('modifiedUserId').value=data.response[0].modifiedUserId;
		dojo.byId('modifiedDate').value=data.response[0].modifiedDate;
		dojo.byId('dateCurrent').value=data.response[0].dateCurrent;
		dojo.byId('edit').value=data.response[0].edit; 
}



