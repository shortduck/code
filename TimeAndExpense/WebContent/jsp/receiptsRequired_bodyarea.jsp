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
		setupexpenseDetails();
		setupDisplay();
		
		// connect 'My Employees' event hook
		var myEmpNode = dojo.byId('myEmployees');
		dojo.connect(myEmpNode, 'onclick', function(){handleMyEmployeesChange(myEmpNode.checked);});
		});
	// prepares a Read Only store for comboboxes
	function prepareMyStore(jsonStr){
		var deptData = {};
		deptData.label = 'display';
		deptData.identifier = 'code';
		deptData.items = jsonStr;
		return new dojo.data.ItemFileReadStore({data: deptData});
	}
		
	function setupexpenseDetails() {

		var data = dojo.fromJson(dojo.byId('jsonResponse_hidden').value);
		
		var travelExpense_store = prepareMyStore(data.travelExpense);		
		travelExpenseFilteringSelect_cb = new dijit.form.FilteringSelect({
			id : "travelExpense_store",
			name : "travelExpense_store",
			store : travelExpense_store,
			searchAttr : "display",
			value : '',
			displayedValue : '',
			style : "width:7em"
		}, "travelExpense_cb");
			
		var specificExpense_store = prepareMyStore(data.specificExpense);
		specificExpense_cb = new dijit.form.FilteringSelect({
			id : "specificExpense_store",
			name : "specificExpense_store",
			store : specificExpense_store,
			searchAttr : "display",
			value : '',
			displayedValue : '',
			style : "width:15em"
		}, "specificExpense_cb");
		  

		var expenseTypesInOutState_store = prepareMyStore(data.expenseTypesInOutState);
		expenseTypesInOutState_store_cb = new dijit.form.FilteringSelect({
			id : "expenseTypesInOutState_store",
			name : "expenseTypesInOutState_store",
			store : expenseTypesInOutState_store,
			searchAttr : "display",
			value : '',
			displayedValue : '',
			style : "width:8em"
		}, "expenseTypeInOutState_cb");
			 
	}
	
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
		if(moduleId == 'EXPR001'){
			var scopeTable = dojo.byId('deptAgencyTkuTable');
			scopeTable.style.display = 'none';
		}
		
		// increase dropdown length
		increaseDeptAgyTkuDDLength();
	}
	
	function validateAndSubmit(form){
		var errorMsg = {msg:''};
		
		// clear any previous error messages
		displayValidationErrorMsg('');
		
		var expTypeNode = dijit.byId('travel_store2');
		displayValidationErrorMsg('');
		
		// validate user input
		if(validateUserInput(errorMsg)){
			return true;
		}else{
			// show validation error
			displayValidationErrorMsg(errorMsg.msg);
			return false;
		}
	}
	
	function validateUserInput(errorMsg){
		var moduleId = trimStr(dojo.byId('module_hidden').value);
		var skipDeptAgyTkuValidation = (moduleId == 'EXPR001') || (moduleId == 'EXPR005' && dojo.byId('myEmployees').checked);

		var scopeCorrect = true;
		if(!skipDeptAgyTkuValidation){
			scopeCorrect = validateDeptAgencyTku(errorMsg) && validateDeptForEmpId(errorMsg) && validateEmpIdForNumeric(errorMsg);
		}
		
		return  scopeCorrect
				&& validateDeptForMyEmployees(errorMsg)
				&& validateExpenseDate(errorMsg)
				&& validatePaymentDate(errorMsg)
				&& validateDatePresence(errorMsg)
				&& validateDateFromLessThanTo(errorMsg)
				&& validatePmtDateFromLessThanTo(errorMsg);
				
	}

	function validatePaymentDate(errorMsg){						
		// check presence of both payment from & to dates
		var paymentFromDate = dojo.byId('f_fromDateField_2').value;
		var paymentToDate = dojo.byId('f_toDateField_2').value;
		
		if((paymentFromDate=='' && paymentToDate != '')){
			errorMsg.msg = "Please select Payment 'From' date";
			return (errorMsg.msg.length >0)? false: true;	
		}
		else
		if((paymentToDate=='' && paymentFromDate != '')){
			errorMsg.msg = "Please select Payment 'To' date";
			return (errorMsg.msg.length >0)? false: true;	
		}
		else
				return true;	
		}	
	
		
		// check presence of either payment from & to dates
		// or expense from & to dates
		function validateDatePresence(errorMsg){
	    var paymentFromDate = dojo.byId('f_fromDateField_2').value;
		var paymentToDate = dojo.byId('f_toDateField_2').value;
		var expenseFromDate = dojo.byId('f_fromDateField').value;
		var expenseToDate = dojo.byId('f_toDateField').value;
			
		if((paymentFromDate=='' && paymentToDate == '' &&
		    expenseFromDate=='' && expenseFromDate == '')){
			errorMsg.msg = "Please Enter Payment or Expense Date Range";
			return (errorMsg.msg.length >0)? false: true;	
		}
		else 
		return true;	
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
        }
        else if (!myEmployeeSelected && !deptSelected){
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
        errorMsg.msg = (fDate.compareTo(tDate)>0)? "Payment 'To' date cannot be less than 'From' Date":'';
           } else {
                  errorMsg.msg = '';
            }
            return (errorMsg.msg.length >0)? false: true;   


	}
	

</script>
<s:form action="ReceiptsRequiredReportAction.action" method="get"
	target="_blank" onsubmit="return validateAndSubmit(this);"
	id="empSelectionForm" theme="simple">
	<table>
		<tr>
			<td align="center" width="550" height="45"
				style="color: black; font-weight: bold; font-size: 10pt">Receipts
				Required</td>
		</tr>
	</table>
	<table id="deptAgencyTkuTable"
		style="margin-top: 2%; margin-left: 1%; margin-right: 1%; margin-bottom: 2%">
		<tr>
			<td>
				<fieldset style="border: 1px solid black">
					<legend style="color: black; font-weight: bold; font-size: 9pt">
						<b>Select Employees</b>
					</legend>
					<table style="margin: 1%" width="600" border="0">
						<tr>
							<td align="right" width="158">Dept:&nbsp;</td>
							<td width="149"><input id="department_cb" name="chosenDept">
							</td>
						</tr>
						<tr>
							<td height="5px"></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td align="right" width="158">Agency:&nbsp;</td>
							<td width="149"><input id="agency_cb" name="chosenAgency">
							</td>
						</tr>
						<tr>
							<td height="5px"></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td align="right" width="158">TKU:&nbsp;</td>
							<td width="149"><input id="tku_cb" name="chosenTku">
							</td>
						</tr>
						<tr>
							<td height="5px"></td>
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
		style="margin-top: 2%; margin-left: 1%; margin-right: 1%; margin-bottom: 2%">
		<tr>
			<td height="83">
				<fieldset style="border: 1px solid black">
					<legend style="color: black; font-weight: bold; font-size: 9pt">
						<b>Select expense reports</b>
					</legend>
					<table style="margin: 1%" width="600" border="0">
						<tr>
							<td width="350">Expense Greater than or Equal</td>
							<td width="100"><input id="expenseGreater" name ="expenseGreaterThan" size="10"></td>
							<td width="350">Expense Less than or Equal</td>	
							<td width="100"><input id="expenseLess" name ="expenseLessThan" size="10"></td>
						</tr>
						
						<tr><td height="5px"></td></tr>
						
						<tr>
							<td height="5px">Travel Expense</td><td><input id="travelExpense_cb" name = "travelExpense"></td>
							<td height="5px">Expense Type </td><td><input id="expenseTypeInOutState_cb" name ="expenseTypeInOutState_cb"></td>
						</tr>
						
						<tr><td height="5px"></td></tr>
						
						<tr>
							<td height="5px">Specific Expense</td><td> <input id="specificExpense_cb" name ="specificExpense_cb"></td>
							<td height="5px">Random Percentage </td><td> <input id="randomPercentage" name ="randomPercentage"></td>

						</tr>

						<tr><td height="5px"></td></tr>
						
						<tr>
						</tr>
						<tr>
							<td align="left" colspan="4"><span>&nbsp;Emp Audit
									Methods:&nbsp;</span> <s:radio name="auditType"
									list="#{'N':'Post Audit','Y':'Pre Audit','B':'Both'}"
									value="%{'N'}" cssStyle="margin-left:10" />
							</td>

						</tr>
						<tr>
							<td height="20px"></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td align="left" width="320" colspan="2"><s:label theme="simple">&nbsp;Payment Date From: &nbsp;</s:label>
								<s:textfield id="f_fromDateField_2" name="pmtDateFrom"
									theme="simple" onblur="syncDate(this); return true;"
									maxlength="10"></s:textfield> <img
								src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_fromDateTrigger_2" alt="Choose from date">
							</td>
							<td align="left" width="280" colspan="2"><s:label theme="simple">&nbsp;To:&nbsp;</s:label>
								<s:textfield id="f_toDateField_2" name="pmtDateTo"
									theme="simple" onblur="syncDate(this); return true;"
									maxlength="10"></s:textfield> <img
								src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_toDateTrigger_2" alt="Choose to date">
							</td>
						</tr>
						<tr>
							<td align="left" width="320" colspan="2"><s:label theme="simple">&nbsp;Expense Date From: &nbsp;</s:label>
								<s:textfield id="f_fromDateField" name="expDateFrom"
									theme="simple" onblur="syncDate(this); return true;"
									maxlength="10"></s:textfield> <img
								src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_fromDateTrigger" alt="Choose from date">
							</td>
							<td align="left" width="280" colspan="2"><s:label theme="simple">&nbsp;To:&nbsp;</s:label>
								<s:textfield id="f_toDateField" name="expDateTo" theme="simple"
									onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
								<img src="${pageContext.request.contextPath}/image/calendar.gif"
								id="f_toDateTrigger" alt="Choose to date">
							</td>
						</tr>
					</table>
				</fieldset>
			</td>
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
												value="%{'PDF'}" cssStyle="margin-left:1px" />
										</td>
									</tr>
									<tr>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
				</table></td>

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
