<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
	
	<!-- calendar styles & scripts -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/jscal2.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/win2k/win2k.css" /> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/lang/en.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/systemCode.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
<script language="JavaScript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/systemCode.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>





<!-- JS to render drop downs -->
<style>   
input.uppercase{   
text-transform: uppercase;   
}   
</style>
<script type="text/javascript">
	dojo.require("dojo.parser");
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dojox.grid.DataGrid");
	dojo.require("dijit.Dialog");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dojo.data.ItemFileWriteStore");
	dojo.require("dijit.form.ComboBox");
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dijit.form.Textarea");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.form.NumberTextBox");
	dojo.require("dijit.form.CheckBox");
	dojo.require("dijit.Tooltip");
</script>
<script>
 
   

</script>

<script type="text/javascript">  

dojo.addOnLoad(function(){
	var serverResponse = dojo.fromJson(dojo.byId('jsonResponse_hidden').value);
	var firstRound=1;
	dojo.byId('currDate').value=serverResponse;
	cuDT = serverResponse;
	dojo.byId("modifiedDate").value=serverResponse.currentDate;
	dojo.byId("startDate").value=serverResponse.currentDate;
	dojo.byId("modifiedUserId").value=serverResponse.user; 
	dojo.byId("endDate").value="12/31/2222";
	setupCreateDateSysCalendar();
    addOnChange();
    dojo.byId("updBtn").style.visibility='hidden';
		               // Highlights the department selected.  kp.
	processTextFieldCounters();
	});

function setupCreateDateSysCalendar(){
	prepareSCreateDateSysCalendar("startDate", "f_startDateTrigger");
	prepareSCreateDateSysCalendar("endDate", "f_endDateTrigger");
}

function checkFormState(){ 
	if (codeModified)
		return 'There are unsaved changes'; 
}



function prepareSCreateDateSysCalendar(input_field_id, button_id){
	Calendar.setup({
        inputField     :    input_field_id,      // id of the input field
        ifFormat       :    "%m/%d/%Y",       // format of the input field
        showsTime      :    false,            // will display a time selector
        button         :    button_id,   // trigger for the calendar (button ID)
        //onClose    		: fromClosed,
        singleClick    :    true         // double-click mode
    });
    
    //processTextFieldCounters();
	
}
 
  
var formModified = false;
/*
function checks for validations 
*/

	
function saveSystemCode(){ 
		//var errorMsg = {msg:''}; 
		var len_sys=dojo.byId('systemCode').value;
		var stDt =dojo.byId('startDate').value;
		var edDt =dojo.byId('endDate').value;
		var vv =dojo.byId('value').value;
		var dd=dojo.byId('description').value;
		var errMess ='';
		if(len_sys==null || len_sys.trim().length==0)
		{
		  errMess = "System Code  Not Entered<br>";		 
		}
		if(stDt==null || stDt.trim().length==0)
		{
		  errMess = errMess +( " Start date Not Entered<br>"); 
		}
		if(edDt==null || edDt.trim().length==0)
		{
		  errMess = errMess +( "\r\n End Date  Not Entered<br>");
		  
		}
		if(vv==null || vv.trim().length==0)
		{
		  errMess = errMess +( " Value Not Entered <br>");
		  
		}
		if(dd==null || dd.trim().length==0)
		{
		  errMess = errMess +( "\n Description Not Entered<br> ");
		  
		}
		if(errMess.trim().length>0)
		{
		 showErrMessage(errMess);
		 return problemDuringSave;
		}
		
		
		var problemDuringSave = false; 
			if(!compareDTs(dojo.byId('endDate'),dojo.byId('startDate')))
		{
			showErrMessage(" Start Date Cannot be more than End Date!!!!");					
			return problemDuringSave;
		}
		// Validation for  description should not be grater than 256 character.
		var len_desc=dojo.byId('description').value;
		if(len_desc.length>256)
		{
		 showErrMessage(" Description is longer than allowed (256 characters). Please enter a shorter value.");					
			return problemDuringSave;
		}
		
		//-------------------------------------------------
		var len_sys=dojo.byId('systemCode').value;
		if(len_sys.length<4)
		{
		 showErrMessage(" System Code Error Enter 4 digits!!!!");					
			return problemDuringSave;
		}
		problemDuringSave = submitSaveRequest();  
		return problemDuringSave;
  		//validateUserInput(errorMsg);
  	}
  	
  	function updateSystemCode(){  		
		//var errorMsg = {msg:''};
	 if(formModified)
	 {
		var problemDuringSave = false;
		var dt = dojo.byId('endDate').value;
		var vv =dojo.byId('value').value;
		var dd=dojo.byId('description').value;
		var mess='';
        if(dt==null ||dt.trim().length==0)
        {
        	mess =("End Date Cannot be empty <br>");  
        }
		
		if(!pastDateCheck(dojo.byId('endDate')))
		{
			mess = mess +(" Start Date Cannot be more than End Date! <br>");					
			 
		}	
		if((vv==null || vv=='' ||vv.trim().length==0))
		{
			mess = mess +(" Value  is empty  <br>" ); 
			 
		}
		if((dd==null || dd=='' ||dd.trim().length==0))
		{
			mess = mess +(" Description is empty <br>" ); 
			 
		}
		if(mess.trim().length>0)
		{
			showErrMessage(mess);
			return problemDuringSave;
		} 
		problemDuringSave = submitUpdateRequest();		
		return problemDuringSave; 
		}
		else
		{
		  showErrMessage(" Data was not modified ");
		  return false;
		}
		
  		//validateUserInput(errorMsg);
  	}
  	
  	// save request
	function submitUpdateRequest(){
		dojo.byId('statusArea').innerHTML = '';
		dojo.xhrPost ({
			url: 'UpdateNewSystemCode.action',
			form: 'editCodeForm',
			handleAs: "json",
    		sync: true,	   	
    		
    	handle: function(data,args){
		//	disableFormFields(false);
			if(typeof data == "error"){
				//if errors, do not pursue the effect of call!
				console.warn("error!",args);
			}else{
				 setUnchanged();
				//modifySetdata(data);
	 		   showStatusMessage("Update Successful");
			}
		}
	});

  }
  	
  	
  	
 	function setUnchanged(){
		formModified = false;
	} 
	 
  	/*
  	submits the form for 
  	creating a new System Code
  	*/
 
	
	function submitSaveRequest(){
		dojo.xhrPost ({
			url: 'SaveNewSystemCode.action',
			form: 'editCodeForm',
			handleAs: "json",
    		sync: true,	   	
    		
    	handle: function(data,args){
		//	disableFormFields(false);
			if(typeof data == "error"){
				//if errors, do not pursue the effect of call!
				console.warn("error!",args);
			}else{
			if(data.validationErrors.errors!=null)
			{
			processTripIdValidationErrors(data.validationErrors.errors);
			}
			else
			{
			 setUnchanged();
			   dojo.byId("saveBtn").style.visibility='hidden'; 
			   dojo.byId("updBtn").style.visibility='visible'; 
			   dojo.byId('systemCode').style.border=0;
			    dojo.byId('startDate').style.border=0;
			   dojo.byId('systemCode').readonly=true;
		       dojo.byId('startDate').readonly=true;
		       dojo.byId('f_startDateTrigger').disabled=true;
		        dojo.byId('f_startDateTrigger').style.visibility='hidden';
	 		   showStatusMessage(" Save Successful ");
	 		   firstRound =2;
	 		   }
			}
		}
	});

  }
  /*
  Shows valid messages
  */
function showStatusMessage(messageText) {
	dojo.byId('statusArea').style.color = "green";
	dojo.byId('statusArea').innerHTML = messageText;
}
  /*
  Shows valid messages
  */
function showErrMessage(messageText) {
	dojo.byId('statusArea').style.color = "red";
	dojo.byId('statusArea').innerHTML = messageText;
}
		 

	
	/* Process server response to the create System Code save call. */
	function processSaveResponse(data){
		
		 	var modifyBtnClicked = false;
		// if save successful
		if(data.response.systemCode != null){
			// update request Id
			dojo.byId('systemCode').value = data.response.systemCode;  
				modifyBtnClicked = false;
			}	
		 else{
			 
			// this return will evaluate to problemsDuringSave = true;
			return true;
		}
		
	}
	
		function processTripIdValidationErrors(errors){
		var errorMsg = '';
		dojo.forEach(errors, function(item){
			errorMsg += item + '<br/>';
		});
		
		//displayValidationErrorMsg(errorMsg);
		notifyTripId(errorMsg, true);
	}
		
	
	
	function notifyTripId(msg, isErrorMsg){
	if(isErrorMsg == null) isErrorMsg = true;
	var errorMsgNode = dojo.byId('statusArea');
	notifyTab(errorMsgNode, msg, isErrorMsg);
}
	
	function notifyTab(tabErrorMsgNode, msg, isErrorMsg){
	dojo.style(tabErrorMsgNode, 'color', isErrorMsg ? 'RED': 'GREEN');
	tabErrorMsgNode.innerHTML = msg;
}

function changeCase(obj)
{
 obj.value = obj.value.toUpperCase();
 
}
 
</script>
	
<br>
<br>


<s:form id="editCodeForm" method="post" theme="simple">
	<center>
		<fieldset style="border: 1px solid black; width: 60%">
			<legend style="color: black; font-weight: bold; font-size: 9pt">
				<em>System Code Details</em>
			</legend>

			<table border="0" align="center" width="500">
				<tr height="5px">
					<td align="left" width="150"><s:label>System Code<font
								color="red">*</font> :&nbsp;&nbsp&nbsp </s:label></td>
					<td width="350"><s:textfield id="systemCode"
							name="nsystemCode.systemCode" size="4" maxlength="4"
							onChange="addOnChange();" onblur="changeCase(this);"></s:textfield>
					</td>
				</tr>
				<tr height="5px">
				</tr>
				<tr height="5px">
					<td align="left" width="150"><s:label>Start Date<font
								color="red">*</font> :&nbsp;&nbsp;&nbsp;&nbsp;</s:label></td>
					<td width="350"><s:textfield id="startDate"
							name="nsystemCode.startDate"
							onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
						<img src="${pageContext.request.contextPath}/image/calendar.gif"
						id="f_startDateTrigger" alt="Start date"></td>
				</tr>
				<tr height="5px">
				</tr>
				<tr height="5px">
					<td align="left" width="150"><s:label>End Date<font
								color="red">*</font>  :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</s:label></td>
					<td width="350"><s:textfield id="endDate"
							name="nsystemCode.endDate" onblur="syncDate(this); return true;"
							maxlength="10"></s:textfield> <img
						src="${pageContext.request.contextPath}/image/calendar.gif"
						id="f_endDateTrigger" alt="End date"></td>
				</tr>
				<tr height="5px">
				</tr>
				<tr>
					<td align="left" width="150"><s:label>Description<font
								color="red">*</font> :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</s:label></td>
					
					
					<td width="350" >
				   	    <s:textarea id="description" name="nsystemCode.description"  cssClass="counter charLimit.255"  style="white-space: pre-wrap;" 
							cols="40" rows="2"></s:textarea>
						<span id='descCharCount' class='count' style="color: gray;">255</span>
					</td>	 	
							
				</tr>
				<tr height="5px">
				</tr>
				<tr height="5px">
					<td align="left" width="150"><s:label> Value<font
								color="red">*</font> :&nbsp; &nbsp;&nbsp;&nbsp;</s:label></td>
					<td width="350"><s:textfield id="value"
							name="nsystemCode.value" size="18" maxlength="18">
						</s:textfield></td>
				</tr>
				<tr height="5px">
				</tr>
				<tr height="5px">
					<td align="left" width="150"><s:label>Modified User Id :&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</s:label>
					</td>
					<td width="350"><input type="text" style="border: 0px;"
						id="modifiedUserId" name="nsystemCode.modifiedUserId"></td>
				</tr>
				<tr height="5px">
				</tr>
				<tr height="5px">
					<td align="left" width="150"><s:label>Modified Date :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</s:label>
					</td>
					<td width="350"><input type="text" style="border: 0px;"
						id="modifiedDate" name="nsystemCode.modifiedDate"></td>
				</tr>
			</table>

			<table align="right" width="400">
				<tr>
					<td width="300px"><span id="statusArea"
						style="font-style: italic; color: red;"></span>
					</td>

					<td><input id="saveBtn" type="button" value="Save"
						onclick="saveSystemCode();" align="right"></td>
					<td><input id="updBtn" type="button" value="Update"
						onclick="updateSystemCode();" align="right"></td>
				</tr>
			</table>

		</fieldset>
	</center>
</s:form>

<div style="display: none" id="errorMsg"
	style="font-style:italic; font-size:9pt; color:red"></div>

<div style="display: none">
	<s:textfield id="viewJsonId" name="viewJson" />

</div>



<div style="display: none"></div>
<div style="display: none">
	<s:textfield id="jsonResponse_hidden" theme="simple"
		value="%{jsonResponse}" />
	<s:textfield id="systemCodeListVal"
		value="%{#session.systemCodeListVal}" />

	<s:textfield id="currDate" name="curDate"></s:textfield>
	<!-- 		<s:textfield id="module_hidden" theme="simple" value="%{moduleId}" />-->
</div>

