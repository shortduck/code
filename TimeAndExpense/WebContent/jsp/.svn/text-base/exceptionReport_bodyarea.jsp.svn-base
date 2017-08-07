<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>


<!-- calendar styles & scripts -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/jscal2.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/win2k/win2k.css" /> 

<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/lang/en.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>

<script language="JavaScript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/report_common.js"></script>
<!-- JS to render drop downs -->
<script type="text/javascript">
	dojo.require("dojo.parser");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dijit.Dialog");
</script>
<script type="text/javascript">

	// methods to be invoked immediately after page DOM construction
	dojo.addOnLoad(function(){
		initTwoSingleDateCalendars();
		initTwoSingleDateCalendars_2();
		initDeptAgencyTkuCB();
		setupDisplay();
	
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
   
	function IsProper(strString) {
	   var strSpecialChars = "*|,\":<>[]{}`\';()@&$#%";
	   var strChar;
	   var blnResult = true;
	
	   if (strString.length == 0) return false;
	
	   //  test strString consists of valid characters listed above
	   for (i = 0; i < strString.length && blnResult == true; i++)
	      {
	      strChar = strString.charAt(i);
	      if (strSpecialChars.indexOf(strChar) == -1)
	         {
	         blnResult = false;
	         }
	      }
	   return blnResult;
   }

	function setupDisplay(){
		var moduleId = trimStr(dojo.byId('module_hidden').value);
		if(moduleId == 'EXPR002'){
			var scopeTable = dojo.byId('deptAgencyTkuTable');
			scopeTable.style.display = 'none';
		}
	}
	
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
		var moduleId = trimStr(dojo.byId('module_hidden').value);

		var skipDeptAgyTkuValidation = (moduleId == 'EXPR002') || (moduleId == 'EXPR006' && dojo.byId('myEmployees').checked);

		var scopeCorrect = true;
		if(!skipDeptAgyTkuValidation){
			scopeCorrect = validateDeptAgencyTku(errorMsg) && validateDeptForEmpId(errorMsg) && validateEmpIdForNumeric(errorMsg);
		}

		return scopeCorrect
				&& validateDeptForMyEmployees(errorMsg)
				&& validateExpenseDate(errorMsg)
				&& validatePaymentDate(errorMsg)
				&& validateDatePresence(errorMsg)
				&& validateDateFromLessThanTo(errorMsg)
				&& validatePmtDateFromLessThanTo(errorMsg)
    			&& validateErrCodeSelection(errorMsg)
				&& validateErrCodeNoSpecChars(errorMsg);				
	}
	
	
		
	// check presence of both payment from & to dates	
	function validatePaymentDate(errorMsg){						
		var paymentFromDate = dojo.byId('f_fromDateField_2').value;
		var paymentToDate = dojo.byId('f_toDateField_2').value;
		
		if((paymentFromDate=='' && paymentToDate != '')){
			errorMsg.msg = "Please select Payment 'From' date";
			return (errorMsg.msg.length >0)? false: true;	
		}else if((paymentToDate=='' && paymentFromDate != '')){
			errorMsg.msg = "Please select Payment 'To' date";
			return (errorMsg.msg.length >0)? false: true;	
		}else return true;	
	}	
		
	// check presence of either payment from & to dates
	// or expense from & to dates
	function validateDatePresence(errorMsg){
	    var paymentFromDate = dojo.byId('f_fromDateField_2').value;
		var paymentToDate = dojo.byId('f_toDateField_2').value;
		var expenseFromDate = dojo.byId('f_fromDateField').value;
		var expenseToDate = dojo.byId('f_toDateField').value;
			
		if((paymentFromDate=='' && paymentToDate == '' &&
		    expenseFromDate=='' && expenseToDate == '')){
			errorMsg.msg = "Please Enter Payment or Expense Date Range";
			return (errorMsg.msg.length >0)? false: true;	
		}
		else return true;
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
	
	//verify both from and to error codes must be entered
	function validateErrCodeSelection(errorMsg){
	  var errCodeFrom = dojo.byId('error_code_from').value;
	  var errCodeTo = dojo.byId('error_code_to').value;
	  
	  if((errCodeFrom=='' && errCodeTo != '')){
			errorMsg.msg = "Please select Beginning Error Code";
			return (errorMsg.msg.length >0)? false: true;	
		}
		if((errCodeTo=='' && errCodeFrom != '')){
			errorMsg.msg = "Please select Ending Error Code";
			return (errorMsg.msg.length >0)? false: true;	
		}
	  if (!(IsNumeric(errCodeFrom)) || !(IsNumeric(errCodeTo))) {
			errorMsg.msg = "Beginning Error Code and Ending Error Code should be numeric ";
			return (errorMsg.msg.length >0)? false: true;	
	  } else if(parseInt(errCodeFrom,10)>parseInt(errCodeTo,10)){
			errorMsg.msg = "Beginning Error Code can't be greater than Ending Error Code";
			return (errorMsg.msg.length >0)? false: true;	
	  }	  

	  return (errorMsg.msg.length >0)? false: true;
	}
	  
	 //validate errors code has no special characters 
	function validateErrCodeNoSpecChars(errorMsg){
	  var errCodeTo = dojo.byId('error_code_to').value;
	  var errCodeFrom = dojo.byId('error_code_from').value;
	  
	  if ((IsProper(errCodeTo) || (IsProper(errCodeFrom)))) {
	  errorMsg.msg = "Error Code cannot contain special characters";
	  }
	  return (errorMsg.msg.length >0)? false: true;
	}
	
	//validate employee number numeric
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
							'Department required for report based on Emp Id' : '';
		
		return (errorMsg.msg.length >0)? false: true;
	}
	
	//validate expense date from date not greater than to date
	function validateDateFromLessThanTo(errorMsg){
		var fDate = Date.parse(dojo.byId('f_fromDateField').value);
		var tDate = Date.parse(dojo.byId('f_toDateField').value);
	  if (fDate != null && tDate != null) {
            errorMsg.msg = (fDate.compareTo(tDate)>0)? "Expense 'To' date cannot be less than 'From' date":'';
            } else {
                  errorMsg.msg = '';
            }

		return (errorMsg.msg.length >0)? false: true;	
	}
	
	//validate payment date not greater than to date
	function validatePmtDateFromLessThanTo(errorMsg){
		var fDate = Date.parse(dojo.byId('f_fromDateField_2').value);
		var tDate = Date.parse(dojo.byId('f_toDateField_2').value);
	
    	if (fDate != null && tDate != null) {
        errorMsg.msg = (fDate.compareTo(tDate)>0)? "Payment 'To' date cannot be less than 'From' date":'';
           } else {
                  errorMsg.msg = '';
            }
            return (errorMsg.msg.length >0)? false: true;   
	}
</script>
	
<s:form action="ExceptionReportAction.action" method="get"
	target="_blank" onsubmit="return validateAndSubmit(this);"
	id="empSelectionForm" theme="simple">
	<table style="margin: 15px 0px 2px 0px">
		<tr>
			<td align="center" width="550" height="45"
				style="color: black; font-weight: bold; font-size: 10pt">Exception
				Report</td>
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
					<table style="margin: 1%" width="600">
						<tr>
							<td align="right" width="158">Dept:&nbsp;</td>
							<td width="149"><input id="department_cb" name="chosenDept">
							</td>
						</tr>
						<tr>
							<td height="8px"></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td align="right" width="158">Agency:&nbsp;</td>
							<td width="149"><input id="agency_cb" name="chosenAgency">
							</td>
						</tr>
						<tr>
							<td height="8px"></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td align="right" width="158">TKU:&nbsp;</td>
							<td width="149"><input id="tku_cb" name="chosenTku">
							</td>
						</tr>
						<tr>
							<td height="8px"></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td align="right" width="158">Emp Id:&nbsp;</td>
							<td width="149"><input id="empId_text" type="text"
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
							<td align="right" width="158" height="16"><label
								style='display:<s:property value="%{displayMyEmployees}"/>'>My
									Employees:&nbsp;</label>
							</td>

							<td align="left" width="149" height="16"><input
								type="checkbox" name="myEmployees" id="myEmployees"
								style='display:<s:property value="%{displayMyEmployees}"/>'>
							</td>
							<td colspan="2" height="16"></td>
						</tr>
					</table>
				</fieldset></td>
		</tr>
	</table>

	<table
		style="margin-top: 10px; margin-left: 0px; margin-right: 0px; margin-bottom: 1px">
		<tr>
			<td height="83">
				<fieldset style="border: 1px solid black">
					<legend style="color: black; font-weight: bold; font-size: 9pt">
						<b>Select expense reports</b>
					</legend>
					<table style="margin: 1%" width="600">
						<tr>
							<td align="left" width="320" height="34"><s:label
									theme="simple">&nbsp;Payment Date From: &nbsp;</s:label> <s:textfield
									id="f_fromDateField_2" name="pmtDateFrom" theme="simple"
									onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
								<img src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_fromDateTrigger_2" alt="Choose from date"></td>
							<td align="right" width="280" height="34"><s:label
									theme="simple">&nbsp;To:&nbsp;</s:label> <s:textfield
									id="f_toDateField_2" name="pmtDateTo" theme="simple"
									onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
								<img src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_toDateTrigger_2" alt="Choose to date"></td>
						</tr>
						<tr>
							<td align="left" width="320" height="29"><s:label
									theme="simple">&nbsp;Expense Date From: &nbsp;</s:label> <s:textfield
									id="f_fromDateField" name="expDateFrom" theme="simple"
									onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
								<img src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_fromDateTrigger" alt="Choose from date"></td>
							<td align="right" width="280" height="29"><s:label
									theme="simple">&nbsp;To:&nbsp;</s:label> <s:textfield
									id="f_toDateField" name="expDateTo" theme="simple"
									onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
								<img src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_toDateTrigger" alt="Choose to date"></td>
						</tr>
					</table>
					<table style="margin: 1%" width="600">
						<tr>
							<td align="center" width="129" height="25">Error codes
								between&nbsp;</td>
							<td width="94" height="25"><input id="error_code_from"
								type="text" value="00001" name="error_code_from"
								style="width: 5em">
							</td>
							<td colspan="1" width="7" height="25"></td>
							<td align="left" width="32" height="25">and&nbsp;</td>
							<td width="277" height="25"><input id="error_code_to"
								type="text" value="99999" name="error_code_to"
								style="width: 5em">
							</td>
							<td colspan="1" height="25"></td>
						</tr>
					</table>
					<table>
						<tr>
							<td align="center" height="16" width="133">Severity &nbsp;</td>
							<td><s:radio name="severity"
									list="#{'Error':' Errors','Warning':' Warnings','B':' Both'}"
									value="%{'B'}" cssStyle="margin-left:10" /></td>
						</tr>
					</table>
				</fieldset></td>
		</tr>
		<!-- ZH, 04/15/2013 -- Commented for the upgrade rollback 
		<tr>		
		<td>
		
		<table width="615"
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
									value="%{'PDF'}" cssStyle="margin-left:1px" /></td>
						</tr>
					</table>
				</fieldset></td>
		</tr>
	</table>
	</td>
	</tr>
	-->
		<tr>
			<td height="62"><input type="submit" value="Select">
			</td>
		</tr>
		<tr>
			<td colspan="10"><div id="errorMsg"
					style="font-style: italic; font-size: 9pt; color: red"></div>
			</td>
		</tr>

	</table>
</s:form>

<div style="display: none">
	<s:textfield id="jsonResponse_hidden" theme="simple"
		value="%{jsonResponse}" />
	<s:textfield id="module_hidden" theme="simple" value="%{moduleId}" />
</div>

