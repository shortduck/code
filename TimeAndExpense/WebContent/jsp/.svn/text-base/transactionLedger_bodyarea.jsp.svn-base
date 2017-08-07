<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
	<!-- calendar styles & scripts -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/jscal2.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/win2k/win2k.css" /> 

<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/lang/en.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
<script language="JavaScript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/reports.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/report_common.js"></script>

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

	
	function setupDisplay(){
		var moduleId = trimStr(dojo.byId('module_hidden').value);
		if(moduleId == 'EXPR004'){
			var scopeTable = dojo.byId('deptAgencyTkuTable');
			scopeTable.style.display = 'none';
		}
	}
	
	function validateAndSubmit(form){
		var errorMsg = {msg:''};
		
		// clear any previous error messages
		displayValidationErrorMsg('');
		
		// validate user input
		///if(validateUserInput(errorMsg)){
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
		var scopeCorrect = true;
		
		//var moduleId = trimStr(dojo.byId('module_hidden').value);
		//if(moduleId != 'EXPR004'){
		//	scopeCorrect = validateDeptAgencyTku(errorMsg) && validateDeptForEmpId(errorMsg) && validateEmpIdForNumeric(errorMsg);
		//}

		var moduleId = trimStr(dojo.byId('module_hidden').value);
		var skipDeptAgyTkuValidation = (moduleId == 'EXPR004') || (moduleId == 'EXPR008' && dojo.byId('myEmployees').checked);
	
		var scopeCorrect = true;
		if(!skipDeptAgyTkuValidation){
			scopeCorrect = validateDeptAgencyTku(errorMsg) && validateDeptForEmpId(errorMsg) && validateEmpIdForNumeric(errorMsg);
		}
	
		return scopeCorrect
				&& validateDeptForMyEmployees(errorMsg)
				&& validateFromDate(errorMsg) 
				&& validateToDate(errorMsg)
				&& validateDateFromLessThanTo(errorMsg)				
				&& validateFromDate2(errorMsg) 
				&& validateToDate2(errorMsg)
				&& validateDate2FromLessThanTo(errorMsg)				
                && validatePmtDateRequired(errorMsg)
                && validateDateRangeRequired(errorMsg)
                && validateLongTermAdvances(errorMsg)
                && validateListUnpaidExpenses(errorMsg)
                && validateReportName(errorMsg)
                && validateDeptCBTotalsInput(errorMsg);
	}

	function displayValidationErrorMsg(msg){
	    dojo.byId('errorMsg').style.color = "red";
		dojo.byId('errorMsg').innerHTML = msg;
	}
	
	function validateReportName(errorMsg){
	   var _valid = true;
	   var reportName = dojo.byId("reportName_text");
		if(reportName.value.length >1)
	     {
		    dojo.forEach(reportName.value.split(reportName), function(item){
			item = trimStr(item);
			if(item.length <1) return;
			var validDateRegex =   /^[\w.\-]+$/; 
			if(!validDateRegex.test(item)) _valid = false;
		   });
	     }
	    
	   if(!_valid) errorMsg.msg = "Please enter a valid Report name.Report name should contain only alphanumeric characters(a-z,A-Z,0-9),underscore(_) and hyphen.";
	  return _valid;
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
			errorMsg.msg = "Department must be entered when My employees is not checked";
		}
		
		return (errorMsg.msg.length >0)? false: true;
	}
	
	function validateEmpIdForNumeric(errorMsg){
	  var empId = dojo.byId('empId_text').value;
	  var deptSelected = isDepartmentSelected();
	  
	  if (!(IsNumeric(empId)) && (empId.length > 0)) {
	  	if (!deptselected) {
	  		errorMsg.msg = "Department must be entered when Employee Id is not";
	  	}
  		else {
  			errorMsg.msg = "Employee Id should be numeric";
  		}
	  }
	  return (errorMsg.msg.length >0)? false: true;
	
	}
	
		function validateDeptForEmpId(errorMsg){
		var deptSelected = isDepartmentSelected();

		errorMsg.msg = (dojo.byId('empId_text').value.length > 0 && !deptSelected) ?
							'Department required for report based on Emp Id' : '';
		
		return (errorMsg.msg.length >0)? false: true;
	}
	
	function validateFromDate(errorMsg){
		var fDate = dojo.byId('f_fromDateField').value;

		errorMsg.msg = '';
		return (errorMsg.msg.length >0)? false: true;	
	}
	function validateToDate(errorMsg){
		var tDate = dojo.byId('f_toDateField').value;
		
		errorMsg.msg = '';
		return (errorMsg.msg.length >0)? false: true;	
	}
	function validateDateFromLessThanTo(errorMsg){
		var fDate = Date.parse(dojo.byId('f_fromDateField').value);
		var tDate = Date.parse(dojo.byId('f_toDateField').value);
		
		if (fDate != null && tDate != null) {
			errorMsg.msg = (fDate.compareTo(tDate)>0)? "Payment 'To' date cannot be less than 'From' date":'';
		} else {
			errorMsg.msg = '';
		}
		return (errorMsg.msg.length >0)? false: true;	
	}
	function validateFromDate2(errorMsg){
		var fDate = dojo.byId('f_fromDateField_2').value;
		errorMsg.msg = '';
		if( !validTeStDt(dojo.byId('f_fromDateField_2')))
	  	{
  		errorMsg.msg = "Expense From date cannot be before 01/01/2010 ";
  		}
		return (errorMsg.msg.length >0)? false: true;	
		   
	}
	function validateToDate2(errorMsg){
		var tDate = dojo.byId('f_toDateField_2').value;
		errorMsg.msg = ''; 
			if( !validTeStDt(dojo.byId('f_toDateField_2')))
  		{
  		errorMsg.msg = "Expense To date cannot be before 01/01/2010 ";
  		}
		return (errorMsg.msg.length >0)? false: true;	
		   
	}
	function validateDate2FromLessThanTo(errorMsg){
		var fDate = Date.parse(dojo.byId('f_fromDateField_2').value);
		var tDate = Date.parse(dojo.byId('f_toDateField_2').value);
		
		if (fDate != null && tDate != null) {
			errorMsg.msg = (fDate.compareTo(tDate)>0)? "Expense 'To' date cannot be less than 'From' date":'';
		} else {
			errorMsg.msg = '';
		}
		return (errorMsg.msg.length >0)? false: true;	
	}
	
	function validatePmtDateRequired(errorMsg) {
		var fDate = dojo.byId('f_fromDateField').value;
		var tDate = dojo.byId('f_toDateField').value;
		var advSelected = dojo.byId('typeAdv').checked; 
		var expSelected = dojo.byId('typeExp').checked; 
		var bothSelected = dojo.byId('typeBoth').checked; 
     	if(!expSelected && (fDate == "" || tDate == "")){
			errorMsg.msg = "Payment 'From' and 'To' dates are required to select advances";
			return false;
		}
		return (errorMsg.msg.length >0)? false: true;;
	}
	
	function validateDateRangeRequired(errorMsg) {
		var fDate = dojo.byId('f_fromDateField').value;
		var tDate = dojo.byId('f_toDateField').value;
		var fDate2 = dojo.byId('f_fromDateField_2').value;
		var tDate2 = dojo.byId('f_toDateField_2').value;
     	if(fDate == "" && tDate == "" && fDate2 == "" && tDate2 == ""){
			errorMsg.msg = "Either payment date range or expense date range required";
			return false;
		}
		// Limit the payment date range to one year
		if (dateDifferenceInDays(fDate, tDate) > 366){
			errorMsg.msg = "Payment date range may not exceed 366 days";
			return false;
		}
		// Limit the expense date range to one year
		if (dateDifferenceInDays(fDate2, tDate2) > 366){
			errorMsg.msg = "Expense date range may not exceed 366 days";
			return false;
		}
		return (errorMsg.msg.length >0)? false: true;;
	}
	
	function validateLongTermAdvances(errorMsg) {
		var expSelected = dojo.byId('typeExp').checked;
		var longTermAdvSelected = dojo.byId('longTermAdv').checked;
		if(expSelected && longTermAdvSelected) {
			errorMsg.msg = "Cannot select Include Long Term Advances when Expenses only selected";
			return false;
		}
		return (errorMsg.msg.length >0)? false:true;;
	}
	
	function validateListUnpaidExpenses(errorMsg) {
		var advSelected = dojo.byId('typeAdv').checked; 
		var unpaidExpensesSelected = dojo.byId('unpaidExpensesOnly').checked;
		var fDate = Date.parse(dojo.byId('f_fromDateField').value);
		var tDate = Date.parse(dojo.byId('f_toDateField').value);
		if (advSelected && unpaidExpensesSelected) {
			errorMsg.msg = "Cannot select Unpaid Expenses when Advances only selected";
			return false;
		} else {
			if ((fDate != null || tDate != null) && unpaidExpensesSelected) {
				errorMsg.msg = "Cannot enter Payment Date range when Unpaid Expenses only selected";
				return false;
			}
		}
		return (errorMsg.msg.length >0)? false:true;;
	}
	// Additional validations for Department Coding Block totals 
	function validateDeptCBTotalsInput(errorMsg){

		var deptCbTotalsOnly = dojo.byId('reptPrintCBTotalsDept').checked;
		if (deptCbTotalsOnly){
			// agency must be blank
			if (agency_cb.value != ""){
				errorMsg.msg = 'Agency must be blank for this report';
				return false;
			}
			// TKU must be blank
			if (tku_cb.value != ""){
				errorMsg.msg = 'TKU must be blank for this report';
				return false;
			}
			// Employee ID must be blank
			if (dojo.byId('empId_text').value != ""){
				errorMsg.msg = 'Employee ID must be blank for this report';
				return false;
			}
			// My employees may not be checked
			if (dojo.byId('myEmployees').checked){
				errorMsg.msg = '\"My Employees\" checkbox may not be selected for this report';
				return false;
			}
			// "Expenses" must be selected as the transaction type
			if (!dojo.byId('typeExp').checked){
				errorMsg.msg = 'Transaction type must be set to \"Expenses\" for this report';
				return false;
			}
			// Long term cash advances may not be checked for this report
			if (dojo.byId('longTermAdv').checked){
				errorMsg.msg = 'Long term advances may not be selected for this report';
				return false;
			}
		}
		return true;
	}
	
</script>
<form id="empSelectionForm">

	<table style="margin: 15px 0px 2px 0px">
		<tr>
			<td align="center" width="700" height="45"
				style="color: black; font-weight: bold; font-size: 10pt">Transaction
				Ledger Reports</td>
		</tr>
	</table>

	<table
		style="margin-top: 5px; margin-left: 0px; margin-right: 0px; margin-bottom: 1px"
		id="deptAgencyTkuTable">

		<tr>
			<td>

				<fieldset style="border: 1px solid black">

					<legend style="color: black; font-weight: bold; font-size: 9pt">
						<b>Select Employees</b>
					</legend>
					<table style="margin: 1%" width="600">

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
							<td height="8px"></td>
						</tr>
						<tr>
							<td align="right" width="117">Report Name:&nbsp;</td>
							<td width="282"><input id="reportName_text" type="text"
								name="reportName" style="width: 8em">
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
							<td colspan="2"></td>
						</tr>
					</table>
				</fieldset></td>
		</tr>
	</table>

	<table
		style="margin-top: 5px; margin-left: 0px; margin-right: 0px; margin-bottom: 1px">
		<tr>
			<td>
				<fieldset style="border: 1px solid black">
					<legend style="color: black; font-weight: bold; font-size: 9pt">
						<b>Select Transactions</b>
					</legend>
					<table style="margin: 1%" width="600">
						<tr>
							<td><s:label theme="simple">&nbsp;Payment Dates From: &nbsp;</s:label>
								<s:textfield id="f_fromDateField" name="pmtDateFrom"
									theme="simple" onblur="syncDate(this); return true;"
									maxlength="10"></s:textfield> <img
								src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_fromDateTrigger" alt="Choose from date"></td>
							<td><s:label theme="simple">&nbsp;To:&nbsp;</s:label> <s:textfield
									id="f_toDateField" name="pmtDateTo" theme="simple"
									onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
								<img src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_toDateTrigger" alt="Choose to date"></td>
						</tr>
						<tr>
							<td height="5px"></td>
						</tr>
						<tr>
							<td><s:label theme="simple">&nbsp;Expense Dates From: &nbsp;</s:label>
								<s:textfield id="f_fromDateField_2" name="expDateFrom"
									theme="simple" onblur="syncDate(this); return true;"
									maxlength="10"></s:textfield> <img
								src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_fromDateTrigger_2" alt="Choose from date"></td>
							<td><s:label theme="simple">&nbsp;To:&nbsp;</s:label> <s:textfield
									id="f_toDateField_2" name="expDateTo" theme="simple"
									onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
								<img src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_toDateTrigger_2" alt="Choose to date"></td>
						</tr>
					</table>
					<table style="margin: 1%" width="600">
						<tr>
							<td height="5px"></td>
						</tr>
						<tr>
							<td>
								<!-- If value (1st column) for any list item is changed 
     then function validatePmtDateRequired will need to be changed --> <s:label
									theme="simple">&nbsp;Transaction type:&nbsp;</s:label> <s:radio
									name="type" id="type" theme="simple"
									list="#{'Adv':' Advances', 
						        'Exp':' Expenses',
						        'Both':' Both'}"
									value="%{'Both'}" cssStyle="margin-left:20" /></td>
						</tr>
					</table>
					<table style="margin: 1%" width="600">
						<tr>
							<td height="5px"></td>
						</tr>
						<tr>
							<td height="17" align="left"><input value="Y"
								type="checkbox" name="longTermAdv" id="longTermAdv"
								checked="checked">
							</td>
							<td align="left" width="575">Include long term
								advances&nbsp;</td>
							<td></td>
						</tr>
						<tr>
							<td height="5px"></td>
						</tr>
						<tr>
							<td height="17" align="left"><input value="Y"
								type="checkbox" name="adjustmentsOnly" id="adjustmentsOnly">
							</td>
							<td align="left" width="300">Include adjustments only&nbsp;</td>
							<td></td>
						</tr>
						<tr>
							<td height="5px"></td>
						</tr>
						<tr>
							<td height="17" align="left"><input value="Y"
								type="checkbox" name="unpaidExpensesOnly"
								id="unpaidExpensesOnly">
							</td>
							<td align="left" width="300">List unpaid expenses only&nbsp;</td>
							<td></td>
						</tr>
					</table>
				</fieldset></td>
		</tr>
	</table>

	<table width="615"
		style="margin-top: 5px; margin-left: 0px; margin-right: 0px; margin-bottom: 1px">
		<tr>
			<td>
				<fieldset style="border: 1px solid black">
					<legend style="color: black; font-weight: bold; font-size: 9pt">
						<b>Select report style</b>
					</legend>
					<table style="margin: 1%">
						<tr>
							<td><s:radio name="rept" id="rept"
									list="#{'PrintCBDetails':' Print CB Details', 
						        'OmitCBDetails':' Omit CB Details',
						        'PrintCBTotalsOnly':' Print CB total(s) only',
						        'PrintCBTotalsDept':' Print Dept CB total(s) only'}"
									value="%{'PrintCBDetails'}" cssStyle="margin-left:1px" /></td>
						</tr>
						<tr>
						</tr>
					</table>
				</fieldset></td>
		</tr>
	</table>
	<!-- ZH, 04/15/2013 -- Commented for the upgrade rollback
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
						<tr>
						</tr>
					</table>
				</fieldset></td>
		</tr>
	</table>
 -->
	<table
		style="margin-top: 5px; margin-left: 0px; margin-right: 0px; margin-bottom: 1px">
		<tr>
			<td colspan="10"><div id="errorMsg"
					style="font-style: italic; font-size: 9pt; color: red"></div>
			</td>
		</tr>
	</table>
</form>

<button
	onclick="saveTransactionLedgerReportParams('${request.contextPath}/jsp/report/transactionLedger-viewer.jsp');">Batch Run</button>

	 <button
	onclick="openTransactionLedgerReport('${request.contextPath}/jsp/report/transactionLedger-viewer.jsp');">Run Report</button>

<div style="display: none">
	<s:textfield id="jsonResponse_hidden" theme="simple"
		value="%{jsonResponse}" />
	<s:textfield id="module_hidden" theme="simple" value="%{moduleId}" />
</div>




