<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<!-- calendar styles & scripts -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/jscal2.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/win2k/win2k.css" /> 

<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/lang/en.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/enGrid.css"></link>


<style type="text/css">
.soria .dijitTooltipContainer {
	position: absolute;
	left: 0;
	top: -20;
	background-color: F9F7BA;
	border: 1px solid gray;
	max-width: 450px;
	font-size: smaller;
	width: 150px;
	text-align: left;
}
.soria .dijitTooltipRight .dijitTooltipConnector {
 visibility: hidden;
}
</style>

<!-- JS to render grid -->
<script type="text/javascript">
	dojo.require("dojox.grid.EnhancedGrid");
	dojo.require("dojox.grid.enhanced.plugins.Filter");
	dojo.require("dojo.data.ItemFileWriteStore");
	dojo.require("dojo.parser");
	dojo.require("dojox.grid.DataGrid");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dijit.form.NumberTextBox");
	dojo.require("dojo.cookie");
</script>

<script type="text/javascript">

	<c:choose>
	<c:when test = "${(displayMyEmployees eq 'none')}">
		<c:out value="var context = 'statewide';" escapeXml="false"/>
	</c:when>
	<c:otherwise>
		<c:out value="var context = 'manager';" escapeXml="false"/>
	</c:otherwise>
	</c:choose>
	
	// methods to be invoked immediately after page DOM construction
	dojo.addOnLoad(function(){
		initSingleDateCalendar('searchDate');
		initDeptAgencyTkuCB();
		initResultsGrid();
		initDefaults();
		searchAppointments();
		 
		setAttributes();
		selectDepartment();              // Highlights the department selected.  kp. 
	});
	
	dojo.addOnUnload(function() {
		SaveFilter();	
	});
	
	// variable to store calendar click function
	var selectEmployeeDateOnClick = null;
	
	// declare global results grid store
	resultsGridStore = null;
	
	// declare global empty result grid store
	emptyGridStore = new dojo.data.ItemFileReadStore({data: {identifier: 'apptHistoryId', items: [] }});

	// function to initialize the grid at page rendering with no search results
	function initResultsGrid(){
		resultsGridStore = emptyGridStore;
		dijit.byId('resultsGrid').setStore(resultsGridStore);
	}

	function initDefaults(){
		var serverResponse = dojo.fromJson(dojo.byId('jsonResponse_hidden').value);
		
		if(serverResponse.chosenEmpId != null && serverResponse.chosenEmpId.length > 0) 
			dojo.byId('empId_text').value = serverResponse.chosenEmpId;
		
		if(serverResponse.chosenLastname != null && serverResponse.chosenLastname.length > 0) 
			dojo.byId('lastname_text').value = serverResponse.chosenLastname;
		
		if(serverResponse.chosenSearchDate != null && serverResponse.chosenSearchDate.length > 0) 
			dojo.byId('searchDate').value = serverResponse.chosenSearchDate;
		else 
			dojo.byId('searchDate').value = Date.today().toString('MM/dd/yyyy');
			
		if (serverResponse.requestId != null && serverResponse.requestId.length > 0)
			dojo.byId('requestId').value = serverResponse.requestId;
	}

	function searchAppointments(){
		var serverResponse = dojo.fromJson(dojo.byId('jsonResponse_hidden').value);
		if(serverResponse != null && serverResponse.autoSearch != null && serverResponse.autoSearch){
			validateAndSubmit(dojo.byId('empSelectionForm'));
		}
	}
	
	function validateAndSubmit(form){
		var errorMsg = {msg:''};

		// clear any previous error messages
		displayValidationErrorMsg('');

		if(dojo.byId('myEmployeesId').checked){
			MyEmployeesAppointments(form);
		}else{
			// submit search request, if no validation errors. Otherwise report errors
			if(validateUserInput(errorMsg)){
				fetchAndDisplayAppointments(form);
			}else{
				displayValidationErrorMsg(errorMsg.msg); 
			}		
		}
	}

	function fetchAndDisplayAppointments(obj){
		
		dojo.xhrPost({
			url: 'FindAppointments.action',
			form: obj,
			handleAs: "json",
			handle: function(data,args){
			 	disableFormFields(false);
				if(typeof data == "error"){
					//if errors, do not pursue the effect of call!
					console.warn("error!",args);
				}else{					
					updateResultGrid(data.response);
					var selectEmployeeFilter = dojo.cookie(context + "selectEmployeeFilterPref");
					var selectEmployeeFilterRelation = dojo.cookie(context + "selectEmployeeFilterRelationPref");
					if (selectEmployeeFilter != null) {
						filterRows(selectEmployeeFilter, selectEmployeeFilterRelation);	
					}
					selectDepartment();	    // Highlights the department selected.  kp.
				}
			}
		});
		disableFormFields(true);
	}
	
	function filterRows(filter, filterRelation){
		disableFormFields(true);
		
		// check if store exists
		if(resultsGridStore._getItemsArray().length > 0){
			if (filter != null) {				
				dijit.byId('resultsGrid').setFilter(dojo.fromJson(filter), filterRelation);
			}
		}
		
		dijit.byId('resultsGrid').showMessage('');
		dijit.byId('resultsGrid').resize();
		
		disableFormFields(false);
						
		// save field state on server
		SaveFilter();
	}
	
	function MyEmployeesAppointments(obj){
		
		dojo.xhrPost({
			url: 'MyEmployeesAction.action',
			form: obj,
			handleAs: "json",
			handle: function(data,args){
				disableFormFields(false);
				myEmployeesClicked();
				if(typeof data == "error"){
					//if errors, do not pursue the effect of call!
					console.warn("error!",args);
				}else{
					updateResultGrid(data.response);
					var selectEmployeeFilter = dojo.cookie(context + "selectEmployeeFilterPref");
					var selectEmployeeFilterRelation = dojo.cookie(context + "selectEmployeeFilterRelationPref");
					if (selectEmployeeFilter != null) {
						filterRows(selectEmployeeFilter, selectEmployeeFilterRelation);	
					}
				}
			}
		});
		disableFormFields(true);
	}	
	
	function validateUserInput(errorMsg){
		var empIdEL = dojo.byId('empId_text');
		var lastnameEL = dojo.byId('lastname_text');
		if(empIdEL != null && empIdEL.value != null) empIdEL.value = trimStr(empIdEL.value);
		if(lastnameEL != null && lastnameEL.value != null) lastnameEL.value = trimStr(lastnameEL.value);
						
		return validateDeptAgencyTku(errorMsg) 
				&& validateDeptForEmpId(errorMsg) 
				&& validateDeptForLastname(errorMsg)
				&& validateEmpIdForNumeric(errorMsg)
				&& validateRequestIdForNumeric(errorMsg)
				&& validateDeptForSearchDate(errorMsg)
				&& validateSearchDate(errorMsg)
				&& validateEmpIdLastName(errorMsg);
	}
	
	function updateResultGrid(data){
		var storeToUse;
		
		if(data.length == null || data.length <1){
			displayValidationErrorMsg('No results found');
			storeToUse = emptyGridStore;
		}else{
			displayValidationErrorMsg('');
	
			dojo.forEach(data, function(item){
				item._displayName = '';
				 
				if(item.lastName != undefined) item._displayName += item.lastName.trim();
				if(item.firstName != undefined) item._displayName += ', ' + item.firstName.trim();
				if(item.middleName != undefined) item._displayName += ' ' + item.middleName.trim();
		
				var requestParams = dojo.objectToQuery(item);
		
				item.empInfo_link = item._displayName + "_" + item.employeeId + "_" + item.appointmentId + "_" + item._displayName;				
				item.appt_link = '<a href="SelectEmployeeReferrer.action?'+ requestParams+'" name="View Transactions" title="View Transactions">View</a>';
				item.transactionPresent = item.transactionPresent==true?'Y':'N';
			});

			storeToUse = new dojo.data.ItemFileReadStore(
								{data: {identifier: 'apptHistoryId',
									items: data}
								}
							);
		}
		
		resultsGridStore = storeToUse;
						
		// define Date sorting on store for 'appointmentStart' column
		resultsGridStore.comparatorMap = {
   				'appointmentDate': compareDates_MMDDYYYY_Format
		};
		dijit.byId('resultsGrid').setStore(resultsGridStore);
		
		if(data.length > 0){
			dojo.byId('recordCount').innerHTML = data.length;
			dojo.byId('recordCountMsg').style.visibility = 'visible';
		}else{
			dojo.byId('recordCount').innerHTML = '0';
			dojo.byId('recordCountMsg').style.visibility = 'hidden';
		}
	}
	
	function formatEmployeeIdLink(value) {
		var values = value.split("_");
		return '<a href="javascript:geEmployeeInfoLink('+ values[1] +','+ values[2]+');" name="View Employee Information" title="View Employee Information">'+values[0]+'</a>';
	}
	
	function disableFormFields(_disable) {
		var searchBtn = dojo.byId('se_searchBtn');
		if(_disable){
			// disable the search button and modify text
			searchBtn.value = 'Searching...';
			searchBtn.disabled = true;
			disableFields(true);
			dijit.byId('resultsGrid').showMessage("Loading... Please wait");
		}else{
			// re-enable the disabled search button and revert original text
			searchBtn.value = 'Search';
			searchBtn.disabled = false;
			
			disableFields(false);
		}
	}

	function disableFields(state){
		if(dept_cb != null) dept_cb.attr('disabled', state);
		if(agency_cb != null) agency_cb.attr('disabled', state);
		if(tku_cb != null) tku_cb.attr('disabled', state);
		dojo.byId('empId_text').disabled = state;
		dojo.byId('lastname_text').disabled = state;
		
		dojo.byId('searchDate').disabled = state;
		
		// disable calendar button
		var calField = dojo.byId('f_singleDateTrigger');
		if(state){
			selectEmployeeDateOnClick = calField.onclick;
			calField.onclick = null;
		}else{
			if(selectEmployeeDateOnClick != null) calField.onclick = selectEmployeeDateOnClick;
		}
		
		dojo.byId('myEmployeesId').disabled = state;
	}
	
	function geEmployeeInfoLink (arg1,arg2){
	comments = window.open("EmpInfoAction.action?employeeId=" +  arg1 + "&appointmentId=" + 
				arg2, "list", "width=900px,height=700px,modal=no,scrollbars=1");
	}
	function applyDateSortingOnGridStore(resultsGridStore, index){
		resultsGridStore.fields.get(index).compare = function(){
			console.log('sort event captured');
		};
	}

	function displayValidationErrorMsg(msg){
		dojo.byId('errorMsg').innerHTML = msg;
	}

	function validateDeptAgencyTku(errorMsg){
	var len_REQ = dojo.byId('requestId').value.length;

	if(len_REQ>0)
	{
	// no need to check for dept.
	}
	else if(!dept_cb.isValid() || dept_cb.getValue()=='' ){
			errorMsg.msg = 'Department Required';
		}else if(!agency_cb.isValid()){
			errorMsg.msg = 'Agency Required';
		}else if(!tku_cb.isValid()){
			errorMsg.msg = 'TKU Required';
		}else if(isAgencySelected() && !isDepartmentSelected()){
			errorMsg.msg = 'Department required for agency';
		}else if(isTkuSelected() && !isAgencySelected()){
			errorMsg.msg = 'Agency required for TKU';
		}
		
		return (errorMsg.msg.length >0)? false: true;
	}
	
	function validateDeptForEmpId(errorMsg){
		var deptSelected = isDepartmentSelected();
        var len_REQ = dojo.byId('requestId').value.length;
        if(len_REQ >0)
        {
         deptSelected=true;
        }
		errorMsg.msg = (dojo.byId('empId_text').value.length > 0 &&( !deptSelected)) ?
							'Department OR Request Id  required for search based on Emp Id' : '';
		
		return (errorMsg.msg.length >0)? false: true;
	}
	
	function validateDeptForLastname(errorMsg){
		var deptSelected = isDepartmentSelected();
         var len_REQ = dojo.byId('requestId').value.length;
         if(len_REQ>0)
         {
          deptSelected=true;
         }
         
		errorMsg.msg = (dojo.byId('lastname_text').value.length > 0 && (!deptSelected)) ?
							'Department required for search based on last name' : '';
							
		return (errorMsg.msg.length >0)? false: true;
	}
	
	function validateDeptForSearchDate(errorMsg){
		var deptSelected = isDepartmentSelected();
        var len_REQ = dojo.byId('requestId').value.length;
        if(len_REQ>0)
        {
         deptSelected=true;
        }
		errorMsg.msg = (dojo.byId('searchDate').value.length > 0 && (!deptSelected )) ?
							'Department required for search based on appointment date' : '';
							
		return (errorMsg.msg.length >0)? false: true;
	}
	
	function validateSearchDate(errorMsg){
		errorMsg.msg = dojo.byId('searchDate').value.length < 1 ?
							'Please enter a valid Appointment search date' : '';
							
		return (errorMsg.msg.length >0)? false: true;
	}
	
	function validateEmpIdLastName(errorMsg){
		errorMsg.msg = (dojo.byId('empId_text').value.length > 0 && dojo.byId('lastname_text').value.length > 0) ?
							'Please enter Emp Id or Last name' : '';
							
		return (errorMsg.msg.length >0)? false: true;
	}
	function myEmployeesClicked() {
		if(document.getElementById('myEmployeesId').checked){
		dijit.byId('departments').attr('disabled', true);
		dijit.byId('agencies').attr('disabled', true);
		dijit.byId('tkus').attr('disabled', true);
		document.getElementById('empId_text').disabled = true;
		document.getElementById('lastname_text').disabled = true;		
		}else{
		dijit.byId('departments').attr('disabled', false);
		dijit.byId('agencies').attr('disabled', false);
		dijit.byId('tkus').attr('disabled', false);
		document.getElementById('empId_text').disabled = false;
		document.getElementById('lastname_text').disabled = false;
		}
	}
	
	function validateEmpIdForNumeric(errorMsg){
	  var empId = dojo.byId('empId_text').value;
	  
	  if (!(isNumeric(empId)) && (empId.length > 0)) {
	  errorMsg.msg = "Employee Id should be numeric";
	  }
	  return (errorMsg.msg.length >0)? false: true;
	
	}
	
	function validateRequestIdForNumeric(errorMsg){
	  var requestId = dojo.byId('requestId').value;
	  
	  if (!(isNumeric(requestId)) && (requestId.length > 0)) {
	  errorMsg.msg = "Request Id should be numeric";
	  }
	  return (errorMsg.msg.length >0)? false: true;
	
	}
	
	// Highlight department selection.  kp
	
	function selectDepartment(){
		dijit.byId('departments').focus();
	}
	
	setAttributes = function(){
    dojo.attr('departments', {
              tabIndex: 1,
              name: "departments" 
    });
    dojo.attr('agencies', {
              tabIndex: 2,
              name: "agencies" 
    });
      dojo.attr('tkus', {
              tabIndex: 3,
              name: "tkus" 
    });
         dojo.attr('empId_text', {
              tabIndex: 4,
              name: "empId" 
    });
           dojo.attr('lastname_text', {
              tabIndex: 5,
              name: "lastname" 
    });
         dojo.attr('requestId', {
              tabIndex: 6,
              name: "requestId" 
    });
         dojo.attr('searchDate', {
              tabIndex: 7,
              name: "searchDate" 
    });
     dojo.attr('f_singleDateTrigger', {
              tabIndex: 8,
              name: "f_singleDateTrigger" 
    });
     dojo.attr('se_searchBtn', {
              tabIndex: 9,
              name: "se_searchBtn" 
    });
}
function SaveFilter(){
		dojo.cookie(context + "selectEmployeeFilterPref", dojo.toJson(dijit.byId('resultsGrid').getFilter()), {path: '/'});
		dojo.cookie(context + "selectEmployeeFilterRelationPref", dijit.byId('resultsGrid').getFilterRelation(), {path: '/'});
	}
</script>
<!-- Changes by Omkar start -->
<fieldset style="border: 1px solid black;">
	<!-- Changes by Omkar end -->
	<legend style="color: black; font-weight: bold; font-size: 9pt">
		<b>Select Employee</b>
	</legend>
	<form
		onsubmit="initResultsGrid();validateAndSubmit(this);dojo.byId('recordCountMsg').style.visibility='hidden';return false;"
		id="empSelectionForm">
		<table>
			<tr>
				<td><br>
				</td>
			</tr>
			<tr>
				<td>&nbsp;Dept&nbsp;</td>
				<td><input id="department_cb" name="chosenDept"  >
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>Agency&nbsp;</td>
				<td><input id="agency_cb" name="chosenAgency">
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>TKU&nbsp;</td>
				<td><input id="tku_cb" name="chosenTku">
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
					id="se_searchBtn" type="submit" value="Search">
				</td>
			</tr>
			<tr>
				<td><br>
				</td>
			</tr>
			<tr>
				<td>&nbsp;Emp Id&nbsp;</td>
				<td><input id="empId_text" type="text" name="empId" size="8"
					style="width: 8em">
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td colspan="1">Last name&nbsp;</td>
				<td><input id="lastname_text" type="text" size="12"
					name="lastname">
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>Request Id&nbsp;&nbsp;</td>
				<td><input id="requestId" type="text"
					name="requestId" size="15">
				</td>
				
			</tr>
			<tr>
				<td><br>
				</td>
			</tr>
			<tr>
				<td colspan="4" nowrap="nowrap">&nbsp;Appt search date <input
					id="searchDate" type="text" size="8"
					onblur="syncDate(this); return true;"> &nbsp;<img
					src="${pageContext.request.contextPath}/image/calendar.gif"
					id="f_singleDateTrigger" title="Date selector"></td>
				<td colspan="2" align="left">&nbsp;<label
					style='display:<s:property value="%{displayMyEmployees}"/>'>My
						Employees:&nbsp;</label><input type="checkbox" name="myEmployees"
					id="myEmployeesId"
					style='display:<s:property value="%{displayMyEmployees}"/>'
					onclick="myEmployeesClicked()"></td>
			</tr>
			<tr>
				<td><br>
				</td>
			</tr>
			<tr>
				<td colspan="5"><div id="errorMsg"
						style="font-style: italic; font-size: 9pt; color: red"></div>
				</td>
			</tr>
		</table>
	</form>
</fieldset>
<br>
<br>
<span style="color: black; font-weight: bold; font-size: 9pt">Search
	Results</span>
<!-- record count -->
<span id="recordCountMsg"
	style="color: black; font-size: 9pt; visibility: hidden">&nbsp;(<span
	id="recordCount"></span>&nbsp; records)</span>
<!--  results grid -->
<!-- Changes by Omkar start  -->
<table id="resultsGrid" dojotype="dojox.grid.EnhancedGrid"
	query="{apptHistoryId: '*'}" store="resultsGridStore"
	selectionMode="none" style="height: 425px" plugins="{ filter:{ruleCount: 0}}">
	<!-- Changes by Omkar end  -->
	<thead>
		<tr>
			<th field="appt_link" width="15%" formatter="formatGridLink"><em>View
					Transactions</em>
			</th>
			<th field="empInfo_link" width="25%" formatter="formatEmployeeIdLink"><em>Name</em>
			</th>
			<th field="employeeId" width="17%"><em>Emp Id</em>
			</th>
			<th field="positionId" width="17%"><em>Position</em>
			</th>
			<th field="appointmentDate" width="18%"><em>Appt Date</em>
			</th>
			<th field="transactionPresent" width="8%"><em>Requests</em>
			</th>
		</tr>
	</thead>
</table>

<div style="display: none">
	<s:textfield id="jsonResponse_hidden" theme="simple"
		value="%{jsonResponse}" />
</div>

