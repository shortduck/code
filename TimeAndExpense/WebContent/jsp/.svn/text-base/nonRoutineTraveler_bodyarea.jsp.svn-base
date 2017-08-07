<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
	<!-- calendar styles & scripts -->
<link rel="stylesheet" type="text/css" href="/TimeAndExpense/JSCal2-1.9/src/css/jscal2.css" />
<link rel="stylesheet" type="text/css" href="/TimeAndExpense/JSCal2-1.9/src/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="/TimeAndExpense/JSCal2-1.9/src/css/win2k/win2k.css" /> 

<script type="text/javascript" src="/TimeAndExpense/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript" src="/TimeAndExpense/JSCal2-1.9/src/js/lang/en.js"></script>

<script type="text/javascript" src="/TimeAndExpense/js/date.js"></script>
<script language="JavaScript" src="/TimeAndExpense/js/time_expense_calendar.js"></script>


<script type="text/javascript" src="/TimeAndExpense/js/te_dat_scope.js"></script>
<script type="text/javascript" src="/TimeAndExpense/js/report_common.js"></script>

<!-- JS to render drop downs -->
<script type="text/javascript">
	dojo.require("dojo.parser");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dijit.Dialog");
</script>
<script>
function IsNumeric(strString)
   //  check for valid numeric strings	
   {
   var strValidChars = "0123456789";
   var strChar;
   var blnResult = true;

   if (strString.length == 0) return false;

   //  test strString consists of valid characters listed above
   for (i = 0; i < strString.length && blnResult == true; i++)
      {
      strChar = strString.charAt(i);
      if (strValidChars.indexOf(strChar) == -1)
         {
         blnResult = false;
         }
      }
   return blnResult;
   }

</script>

<script type="text/javascript">
	// methods to be invoked immediately after page DOM construction
	dojo.addOnLoad(function(){
		initTwoSingleDateCalendars();
		initDeptAgencyTkuCB();
		<%--setupDisplay();--%>
		
		// increase dropdown length
		increaseDeptAgyTkuDDLength();
		
		// connect 'My Employees' event hook
		var myEmpNode = dojo.byId('myEmployees');
		dojo.connect(myEmpNode, 'onclick', function(){handleMyEmployeesChange(myEmpNode.checked)});
	});

	function handleMyEmployeesChange(isMyEmpChecked){
		// reset form fields
		if(isMyEmpChecked){
			dept_cb.setValue('');
			dept_cb.setDisplayedValue('');
			agency_cb.setValue('');
			agency_cb.setDisplayedValue('');
			tku_cb.setValue('');
			tku_cb.setDisplayedValue('');
			dojo.byId('empId_text').value = '';
		}else{
			dept_cb.setValue(defaultDept);
			dept_cb.setDisplayedValue(defaultDept);
		}
		
		// disable dept, agency, tku & empId fields
		dept_cb.attr('disabled', isMyEmpChecked);
		agency_cb.attr('disabled', isMyEmpChecked);
		tku_cb.attr('disabled', isMyEmpChecked);
		dojo.byId('empId_text').disabled = isMyEmpChecked;
	}
	
	<%-- function setupDisplay(){
		var moduleId = trimStr(dojo.byId('module_hidden').value);
		if(moduleId == 'EXPR013'){
			var scopeTable = dojo.byId('deptAgencyTkuTable');
			scopeTable.style.display = 'none';
		}
	}--%>
	
	function validateAndSubmit(form){
		var errorMsg = {msg:''};
		
		// clear any previous error messages
		displayValidationErrorMsg('');
		
		// validate user input
		if(validateUserInput(errorMsg)){
			// Submit search request parameters to the report
			// fetchAndDisplayAppointments(form);
			return true;
		}else{
			// show validation error
			displayValidationErrorMsg(errorMsg.msg);
			return false;
		}
	}

	
	function validateUserInput(errorMsg){
		var empIdEL = dojo.byId('empId_text');
		if(empIdEL != null && empIdEL.value != null) empIdEL.value = trimStr(empIdEL.value);
	
		var moduleId = trimStr(dojo.byId('module_hidden').value);
		var skipDeptAgyTkuValidation = (moduleId == 'EXPR013') || (moduleId == 'EXPR014' && dojo.byId('myEmployees').checked);
	
		var scopeCorrect = true;
		if(!skipDeptAgyTkuValidation){
			scopeCorrect = validateDeptAgencyTku(errorMsg) && validateDeptForEmpId(errorMsg) && validateEmpIdForNumeric(errorMsg);
		}
	
		return 	scopeCorrect 
				&& validateDeptForMyEmployees(errorMsg)
				&& validateRequestFromDate(errorMsg) 
				&& validateRequestToDate(errorMsg)
				&& validateStatusCheck(errorMsg)
				&& validateDateFromLessThanTo(errorMsg);				
	}
	
	function validateStatusCheck(errorMsg){
	 var statusCheckSave=dojo.byId('NonTravelSaveCheckBox').checked;
	 var statusCheckSubmitted=dojo.byId('NonTravelSubmittedCheckBox').checked;
	 var statusCheckFullyApproved=dojo.byId('NonTravelFullyapprovedCheckBox').checked;
	 var statusCheckPROC=dojo.byId('NonTravelPROCCheckBox').checked;
	 if(!statusCheckSave && !statusCheckSubmitted && !statusCheckFullyApproved && !statusCheckPROC)
		   errorMsg.msg = "Travel Request Status  has to be checked";
	 return (errorMsg.msg.length >0)? false: true;		
	 
	}
	
	function validateRequestFromDate(errorMsg){
	var fDate = dojo.byId('f_fromDateField').value;
	if( !validTeStDt(dojo.byId('f_fromDateField')))
	  	{
  		errorMsg.msg = "Non Routine travel  From date cannot be before 01/01/2010 ";
		return (errorMsg.msg.length >0)? false: true;	
		  } 
	errorMsg.msg = (fDate == '') ? "Non Routine travel  'From' date missing":'';
	return (errorMsg.msg.length >0)? false: true;
	}
    function validateRequestToDate(errorMsg){
	var tDate = dojo.byId('f_toDateField').value;
	if( !validTeStDt(dojo.byId('f_toDateField')))
  		{
  		errorMsg.msg = "Non Routine travel  To date cannot be before 01/01/2010 ";
		return (errorMsg.msg.length >0)? false: true;	
		  }	
	errorMsg.msg = (tDate == '') ? "Non Routine travel  'To' date missing":'';
	return (errorMsg.msg.length >0)? false: true;	
}		


	function displayValidationErrorMsg(msg){
		dojo.byId('errorMsg').innerHTML = msg;
	}

	function validateDeptAgencyTku(errorMsg){
		if(!dept_cb.isValid()){
			errorMsg.msg = 'Invalid Department';
		}else if(!agency_cb.isValid()){
			errorMsg.msg = 'Invalid Agency';
		}else if(!tku_cb.isValid()){
			errorMsg.msg = 'Invalid TKU';
		}else if(isAgencySelected() && !isDepartmentSelected()){
			errorMsg.msg = 'Department required for agency';
		}else if(isTkuSelected() && !isAgencySelected()){
			errorMsg.msg = 'Agency required for TKU';
		}
		
		return (errorMsg.msg.length >0)? false: true;
	}
	
		function validateDeptForMyEmployees(errorMsg){
		var deptSelected = isDepartmentSelected();
        var myEmployeeSelected = dojo.byId('myEmployees').checked; 
        
        if (dojo.byId('myEmployees').style.display == "none" && !deptSelected){
        	errorMsg.msg = "Please enter Department";
        }else if (!myEmployeeSelected && !deptSelected){
			errorMsg.msg = "Please enter Department when My employees is not checked";
		}
		
		return (errorMsg.msg.length >0)? false: true;
	}
	
	
	function validateEmpIdForNumeric(errorMsg){
	  var empId = dojo.byId('empId_text').value;
	  
	  if (!(IsNumeric(empId)) && (empId.length > 0)) {
	  errorMsg.msg = "Employee Id should be numeric";
	  }
	  return (errorMsg.msg.length >0)? false: true;
	
	}
	
	
		function validateDeptForEmpId(errorMsg){
		var deptSelected = isDepartmentSelected();

		errorMsg.msg = (dojo.byId('empId_text').value.length > 0 && !deptSelected) ?
							'Department required for report based on emp Id' : '';
		
		return (errorMsg.msg.length >0)? false: true;
	}
	
	function validateDateFromLessThanTo(errorMsg){
		var fDate = Date.parse(dojo.byId('f_fromDateField').value);
		var tDate = Date.parse(dojo.byId('f_toDateField').value);
		
		errorMsg.msg = (fDate.compareTo(tDate)>0)? "Non Routine travel  'To' date cannot be less than 'From' date":'';
		return (errorMsg.msg.length >0)? false: true;	
	}
</script>
	
<!--/TimeAndExpense/RoutineTravelerReport-viewer.jsp-->
<form action="${pageContext.request.contextPath}/jsp/NonRoutineTravelerReport.action" method="get" target="_blank"
	onsubmit="return validateAndSubmit(this);" id="empSelectionForm">
	<table style="margin: 15px 0px 2px 0px">
		<tr>
			<td align="center" width="700" height="45"
				style="color: black; font-weight: bold; font-size: 10pt">Non Routine
				Travel Requisitions </td>
		</tr>
	</table>

	<table id="deptAgencyTkuTable"
		style="margin-top: 5px; margin-left: 0px; margin-right: 0px; margin-bottom: 1px">
		<tr>
			<td>
				<fieldset style="border: 1px solid black">
					<legend style="color: black; font-weight: bold; font-size: 9pt">
						<b>Select Employees</b>
					</legend>
					<table style="margin: 1%" width="550">
						<tr>
							<td align="right" width="117">Dept:&nbsp;</td>
							<td width="282"><input id="department_cb" name="chosenDept">
							</td>
						</tr>
						<tr>
							<td height="8px"></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td align="right" width="117">Agency:&nbsp;</td>
							<td width="282"><input id="agency_cb" name="chosenAgency">
							</td>
						</tr>
						<tr>
							<td height="8px"></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td align="right" width="117">TKU:&nbsp;</td>
							<td width="282"><input id="tku_cb" name="chosenTku">
							</td>
						</tr>
						<tr>
							<td height="8px"></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td align="right" width="117">Emp Id:&nbsp;</td>
							<td width="282"><input id="empId_text" type="text"
								name="empId" style="width: 8em">
							</td>
							<td colspan="2"></td>
						</tr>
						<tr>
							<td height="5px"></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td align="right" width="117"><label
								style='display:<s:property value="%{displayMyEmployees}"/>'>My
									Employees:&nbsp;</label>
							</td>

							<td height="17" align="left" width="282"><input
								type="checkbox" name="myEmployees" id="myEmployees"
								style='display:<s:property value="%{displayMyEmployees}"/>'>
							</td>							
						</tr>
					</table>
				</fieldset></td>
		</tr>
	</table>

<table
	style="margin-top: 10px; margin-left: 0px; margin-right: 0px; margin-bottom: 1px"
	border="0">
	<tr>
		<td>
		<fieldset style="border: 1px solid black"><legend
			style="color: black; font-weight: bold; font-size: 9pt"> <b>Select
		Non Routine Travel report dates</b> </legend> <!-- AI-25799 -->

		<table>
		    <tr>
							<td  colspan="4"><s:label theme = "simple">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
							&nbsp&nbsp&nbsp&nbsp;Travel <sup>*</sup>&nbsp&nbsp</s:label></td>
				            <td colspan="4" ><s:radio theme = "simple" id="travelSelection" name="travelSelection"
									list="#{'No':'In State','Yes':'Out Of State','B':'Both'}"
									value="%{'No'}" cssStyle="margin-right:10" />
				            </td>			

	        </tr>
	        <tr>
							<td height="12px" colspan="8"></td>
			</tr> 
			<tr>
				<td colspan="4"><s:label theme = "simple">&nbsp;Travel Request Status<sup>*</sup>&nbsp&nbsp</s:label>
				</td>&nbsp

				<td><s:checkbox  theme = "simple" id="NonTravelSaveCheckBox" name="save"
					checked="checked"></s:checkbox> <s:label theme = "simple" align="left"
					id="nonTravelSavw" value="Save" />&nbsp</td>
				<td><s:checkbox theme = "simple" id="NonTravelSubmittedCheckBox"
					name="submitted"></s:checkbox> <s:label theme = "simple" align="left"
					id="nonTravelSubmitted" value="Submitted" />&nbsp</td>

				<td><s:checkbox theme = "simple" id="NonTravelFullyapprovedCheckBox"
					name="approved">
				</s:checkbox> <s:label theme = "simple" align="left" id="nonTravelFullyApproved"
					value="FullyApproved" />&nbsp</td>

				<td><s:checkbox theme = "simple" id="NonTravelPROCCheckBox" name="proc">
				</s:checkbox> <s:label theme = "simple" align="left" id="nonTravelPROC" value="PROC" /></td>
			</tr>
		</table>&nbsp


		<!--   End AI-25799 -->
		<table style="margin: 1%" width="550">
			<tr>
				<td align="left"><s:label theme="simple">&nbsp;Request Date From: &nbsp;</s:label>
				<s:textfield id="f_fromDateField" name="nonTravelDateFrom" theme="simple"
					onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
				<img src="/TimeAndExpense/image/calendar.gif" id="f_fromDateTrigger"
					alt="Choose from date"></td>
				<td align="right"><s:label theme="simple">&nbsp;To:&nbsp;</s:label>
				<s:textfield id="f_toDateField" name="nonTravelDateTo" theme="simple"
					onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
				<img src="/TimeAndExpense/image/calendar.gif" id="f_toDateTrigger"
					alt="Choose to date"></td>
			</tr>
		</table>
		</fieldset>
		</td>
	</tr>

</table>

<!--  ZH, 04/15/2013 -- Commented for the upgrade rollback
			<table width="565"
					style="margin-top: 5px; margin-left: 0px; margin-right: 0px; margin-bottom: 1px">
					<tr>
						<td>
							<fieldset style="border: 1px solid black">
								<legend style="color: black; font-weight: bold; font-size: 9pt">
									<b>Select export type</b>
								</legend>
								<table style="margin: 1%">
									<tr>
										<td><s:radio name="exportType" id="exportType"
												list="#{'PDF':' PDF', 
						        'Excel':' Excel'}"
												value="%{'PDF'}" cssStyle="margin-left:1px" />
										</td>
									</tr>
									<tr>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
				</table>
				-->
	<table>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><input type="submit" value="Select">
			</td>
		</tr>
		<tr>
			<td colspan="10"><div id="errorMsg"
					style="font-style: italic; font-size: 9pt; color: red"></div>
			</td>
		</tr>

	</table>

</form>

<div style="display: none">
	<s:textfield id="jsonResponse_hidden" theme="simple"
		value="%{jsonResponse}" />
	<s:textfield id="module_hidden" theme="simple" value="%{moduleId}" />
</div>

