<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/jscal2.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/win2k/win2k.css" /> 

<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/lang/en.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script> 
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/systemCode.js"></script>

<!-- calendar styles & scripts -->




<!-- JS to render drop downs -->
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
	finddata(dojo.fromJson(dojo.byId('jsonResponse_hidden').value));
	setupSingleDateSysCalendar();
	//		syncDate(dojo.byId('startDate'));
		syncDate(dojo.byId('endDate'));	
		  addOnChange(); 
	processTextFieldCounters();
	});

function setupSingleDateSysCalendar(){
	//prepareSingleDateSysCalendar("startDate", "startDateTrigger");
	prepareSingleDateSysCalendar("endDate", "endDateTrigger");
	 		
}
// display the first time the page is loaded
function finddata(data)
{ 
		var serverResponse = dojo.fromJson(dojo.byId('jsonResponse_hidden').value);
		accessVal = serverResponse[0].writeAccess;
		//alert(serverResponse[0].systemCode);
		dojo.byId('systemCode').value=serverResponse[0].systemCode;
		dojo.byId('startDate').value=serverResponse[0].startDate;
		dojo.byId('endDate').value=serverResponse[0].endDate;
		dojo.byId('description').value=serverResponse[0].description;
		dojo.byId('value').value=serverResponse[0].value;
		dojo.byId('modifiedUserId').value=serverResponse[0].modifiedUserId;
		dojo.byId('modifiedDate').value=serverResponse[0].modifiedDate;
		dojo.byId('dateCurrent').value=serverResponse[0].dateCurrent;
		dojo.byId('edit').value=serverResponse[0].edit;
		dojo.byId('endDate').disabled=accessVal;
	    dojo.byId('description').disabled=accessVal;
		dojo.byId('value').disabled=accessVal;
	    dojo.byId('saveBtn').disabled = accessVal;
	    dojo.byId('delBtn').disabled = accessVal; 
	 
}
// Displays response from server

function modifySetdata(data)
{ 
		var serverResponse = dojo.fromJson(dojo.byId('jsonResponse_hidden').value);  
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

var formModified = false;
 
function saveSystemCode(){  		
		//var errorMsg = {msg:''};
		if(formModified)
		{
		var problemDuringSave = false;
		var dt = dojo.byId('endDate').value;
		var vv =dojo.byId('value').value;
		var dd=dojo.byId('description').value;
		var mess='';
		// Validation for  description should not be grater than 256 character.
		var len_desc=dojo.byId('description').value;
		
		if(len_desc.length>256)
		{
		 showErrMessage(" Description is longer than allowed (255 characters). Please enter a shorter value.");					
			return problemDuringSave;
		}
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
		 
		problemDuringSave = submitSaveRequest();		
		return problemDuringSave;
		}
		else
		{
			showErrMessage("Data was not changed");
			return false;
		}
  		//validateUserInput(errorMsg);
  		
  		
  	}

function showErrMessage(messageText) {
	dojo.byId('statusArea').style.color = "red";
	dojo.byId('statusArea').innerHTML = messageText;
}


 	function setUnchanged(){
		formModified = false;
	}
	// delete action
	function deleteSystemCode()
	{
		var tempVal=dojo.byId('systemCode').value;
		var problemDuringDelete = false; 
	  if(confirm(" Click Ok to delete the system Code"))
	  {  
		problemDuringSave = submitDeleteRequest();  		
		
		}
		else
		{
		dojo.byId('statusArea').style.color = "white";
	    dojo.byId('statusArea').innerHTML = "";
		}
	  return problemDuringDelete;
  		//validateUserInput(errorMsg);
  	}
  	// delete request
  	function submitDeleteRequest(){
  	var tempVal=dojo.byId('systemCode').value;  	
		dojo.xhrPost ({
			url: 'DeleteSystemCode.action',
			form: 'editCodeForm',
			handleAs: "json",
    		sync: true,	   	
    		
    	handle: function(data,args){
		//	disableFormFields(false);
			if(typeof data == "error"){
				//if errors, do not pursue the effect of call!
				console.warn("error!",args);
			}else{				
	 		   showStatusMessage("System Code "+tempVal+" Deleted ");
	 		  dojo.byId('saveBtn').disabled = true;
	 		 dojo.byId('delBtn').disabled = true;
			}
		}
	});

  }  
    // save request
	function submitSaveRequest(){
		dojo.byId('statusArea').innerHTML = '';
		dojo.xhrPost ({
			url: 'SaveSystemCode.action',
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
				modifySetdata(data);
	 		   showStatusMessage("Update Successful");
			}
		}
	});

  }
function showStatusMessage(messageText) {
	dojo.byId('statusArea').style.color = "green";
	dojo.byId('statusArea').innerHTML = messageText;
}
    

	
	/* Process server response to the  save call. */
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
					<td width="350"><input type="text" style="border: 0px;"
						id="systemCode" name="nsystemCode.systemCode"></td>
				</tr>
				<tr height="5px">
				</tr>
				<tr height="5px">
					<td align="left" width="150"><s:label>Start Date<font
								color="red">*</font> :&nbsp;&nbsp;&nbsp;&nbsp;</s:label></td>
					<td width="350"><input type="text" style="border: 0px;"
						id="startDate" name="nsystemCode.startDate"> <!--   <s:textfield id="startDate" name="nsystemCode.startDate" onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
	   <img src="${pageContext.request.contextPath}/image/calendar.gif" id="startDateTrigger" alt="Start date">-->
					</td>
				</tr>
				<tr height="5px">
				</tr>
				<tr height="5px">
					<td align="left" width="150"><s:label> End Date<font
								color="red">*</font> : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</s:label></td>
					<td width="350"><s:textfield id="endDate"
							name="nsystemCode.endDate" onblur="syncDate(this); return true;"
							maxlength="10"></s:textfield> <img
						src="${pageContext.request.contextPath}/image/calendar.gif"
						id="endDateTrigger" alt="End date"></td>
				</tr>
				<tr height="5px">
				</tr>
				<tr>
					<td align="left" width="150"><s:label>Description<font
								color="red">*</font> :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</s:label></td>
					<td width="350"> 
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
					<td align="left" width="150"><s:label> Modified User Id :&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</s:label>
					</td>
					<td width="350"><input type="text" style="border: 0px;"
						id="modifiedUserId" name="nsystemCode.modifiedUserId"></td>
				</tr>
				<tr height="5px">
				</tr>
				<tr height="5px">
					<td align="left" width="150" align="left"><s:label>Modified Date :&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</s:label>
					</td>
					<td width="350"><input type="text" style="border: 0px;"
						id="modifiedDate" name="nsystemCode.modifiedDate"></td>
				</tr>
			</table>

			<table align="center" width="500">
				<tr>
					<td width="300px"><span id="statusArea"
						style="font-style: italic; color: red;"></span>
					</td>

					<td><input id="saveBtn" type="button" value="Update"
						onclick="saveSystemCode();" align="left"></td>
					<td><input id="delBtn" type="button" value="Delete"
						onclick="deleteSystemCode();" align="left"></td>

				</tr>
			</table>
		</fieldset>
	</center>
</s:form>

<div style="display: none" id="errorMsg"
	style="font-style:italic; font-size:9pt; color:red"></div>

<div style="display: none">
	<s:textfield id="viewJsonId" name="viewJson" />
	<s:textfield id="edit" name="nsystemCode.edit" />
	<s:textfield id="dateCurrent" name="nsystemCode.dateCurrent"
		value="edit" />


</div>



<div style="display: none"></div>
<div style="display: none">
	<s:textfield id="jsonResponse_hidden" theme="simple"
		value="%{jsonResponse}" />
	<s:textfield id="systemCodeListVal"
		value="%{#session.systemCodeListVal}" />
	<!-- 		<s:textfield id="module_hidden" theme="simple" value="%{moduleId}" />-->
	<div></div>

</div>