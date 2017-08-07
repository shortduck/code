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
	dojo.require("dojo.parser");
	dojo.require("dojox.grid.DataGrid");
	dojo.require("dojox.data.QueryReadStore");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dojo.cookie");
</script>

<script type="text/javascript">
	// declare global results grid store
	resultsGridStore = null;
	var selectedChkBoxTemp = null;	
	// declare global empty result grid store
	emptyGridStore = new dojo.data.ItemFileReadStore({data: {identifier: 'requestId', items: [] }});
	
	// methods to be invoked immediately after page DOM construction
	dojo.addOnLoad(function(){
		initDeptAgencyTkuCB();
		initResultsGrid();
		initDefaults();
		searchAppointments();
		selectDepartment();            // Highlights the department selected.  kp. 
		clearApprovalQueue();
	});
	
	dojo.addOnUnload(function() {
		SaveFilter();	
	});

	// function to initialize the grid at page rendering with no search results
	function initNoResultsGrid(){
		resultsGridStore = emptyGridStore;
		dijit.byId('resultsGrid').setStore(resultsGridStore);
		dojo.cookie('statewideApprovalsFilterPref', '', {expires: -1, path: '/'});
		dojo.cookie('statewideApprovalsFilterRelationPref', '', {expires: -1, path: '/'});
	}

	function initResultsGrid(){
		resultsGridStore = emptyGridStore;
		dijit.byId('resultsGrid').setStore(resultsGridStore);
	}

	function initDefaults(){
		var dept_cookie = dojo.cookie('statewideApprovalsDeptSelectEmpPref');
		if (dept_cookie != undefined) {
			dept_cb.setDisplayedValue(dept_cookie);		
		
			var agy_cookie = dojo.cookie('statewideApprovalsAgySelectEmpPref');
			if (agy_cookie != undefined) {
				agency_cb.setDisplayedValue(agy_cookie);
			} else {
				agency_cb.setValue("");
				agency_cb.setDisplayedValue("");
			}
			var tku_cookie = dojo.cookie('statewideApprovalsTkuSelectEmpPref');
			if (tku_cookie != undefined) {
				tku_cb.setDisplayedValue(tku_cookie);
			} else {
				tku_cb.setValue("");
				tku_cb.setDisplayedValue("");
			}
		}
		var empId_cookie = dojo.cookie('statewideApprovalsEmpIdSelectEmpPref');
		if (empId_cookie) {
			dojo.byId('empId_text').value = empId_cookie;
		}
		var lastname_cookie = dojo.cookie('statewideApprovalsLastNameSelectEmpPref');
		if (lastname_cookie) {
			dojo.byId('lastname_text').value = lastname_cookie;
		}
	}

	function searchAppointments(){
		
		validateAndSubmit(dojo.byId('empSelectionForm'));
		
		
	}

	function validateAndSubmit(form){
		var errorMsg = {msg:''};
		
		// clear any previous error messages
		displayValidationErrorMsg('');
		
		dojo.cookie('statewideApprovalsDeptSelectEmpPref', dept_cb.getValue(), {path: '/'});
		dojo.cookie('statewideApprovalsAgySelectEmpPref', agency_cb.getValue(), {path: '/'});
		dojo.cookie('statewideApprovalsTkuSelectEmpPref', tku_cb.getValue(), {path: '/'});
		dojo.cookie('statewideApprovalsEmpIdSelectEmpPref', dojo.byId('empId_text').value, {path: '/'});
		dojo.cookie('statewideApprovalsLastNameSelectEmpPref', dojo.byId('lastname_text').value, {path: '/'});
		// validate user input
		if(validateUserInput(errorMsg)){
			// submit search request
			fetchAndDisplayApprovalList(form);
		}else{
			// show validation error
			displayValidationErrorMsg(errorMsg.msg);
		}
	}

	function fetchAndDisplayApprovalList(obj){
		
		dojo.xhrPost({
			url: 'FindApprovalList.action',
			form: obj,
			handleAs: "json",
			handle: function(data,args){
				disableFormFields(false);
				if(typeof data == "error"){
					//if errors, do not pursue the effect of call!
					console.warn("error!",args);
				}else{
					updateResultGrid(data.response);
					var statewideApprovalsFilterPref = dojo.cookie("statewideApprovalsFilterPref");
					var statewideApprovalsFilterRelationPref = dojo.cookie("statewideApprovalsFilterRelationPref");
					if (statewideApprovalsFilterPref != null) {
						filterRows(statewideApprovalsFilterPref, statewideApprovalsFilterRelationPref);	
					}
					selectDepartment();	  // Highlights the department selected.  kp. 
				}
			}
		});
		disableFormFields(true);
	}
	
	function disableFormFields(_disable) {
		var searchBtn = dojo.byId('sw_searchBtn');
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
		
		// disable all radio fields
		dojo.query("input[type='radio']").attr("disabled", state);
	}
	
	function validateUserInput(errorMsg){
	
     	var empIdEL = dojo.byId('empId_text');
		var lastnameEL = dojo.byId('lastname_text');
		if(empIdEL != null && empIdEL.value != null) empIdEL.value = trimStr(empIdEL.value);
		if(lastnameEL != null && lastnameEL.value != null) lastnameEL.value = trimStr(lastnameEL.value);
		
		return validateDeptAgencyTku(errorMsg) 
				&& validateDeptForEmpId(errorMsg) 
				&& validateEmpIdForNumeric(errorMsg)
				&& validateDeptForLastname(errorMsg)
				&& validateEmpIdLastName(errorMsg);
				}
	
	function updateResultGrid(data){
		var storeToUse;
	
		if(data.length <1){
			displayValidationErrorMsg('No results found');
			storeToUse = emptyGridStore;
		}else{
			displayValidationErrorMsg('');
			
			dojo.forEach(data, function(item){
			item.primaryId = item.requestId + "_" + item.transactionType;
			item._displayName = '';
				 
			if(item.lastName != undefined) item._displayName += item.lastName.trim();
			if(item.firstName != undefined) item._displayName += ', ' + item.firstName.trim();
			if(item.middleName != undefined) item._displayName += ' ' + item.middleName.trim();
					
			if(item.transactionType == undefined) item.transactionType='';
			if(item.name == undefined) item.name='';
			if(item.empIdentifier == undefined) item.empIdentifier='';
			if(item.requestId == undefined) item.requestId='';
			if(item.fromDate == undefined) item.startDate='';
			if(item.toDate == undefined) item.endDate='';
			if(item.dollarAmount == undefined) item.dollarAmount='';
			if(item.adjIdentifier == undefined) item.adjIdentifier='';
			if(item.deptCode == undefined) item.deptCode='';

			item.empInfo_link = item._displayName + "_" + item.empIdentifier + "_" + item.apptIdentifier + "_" + item._displayName;				
	
	      var requestParams = dojo.objectToQuery(item);
			var itemId = dojo.toJson(item);
	        
	        item.chkBoxUpdate = '<input type="checkbox" align="center" id="'+ item.requestId +'" onClick="getCheckedRows(this)" />';
	        item.chkBoxUpdate.innerHTML = itemId;
	        
			if(item.transactionType == 'Advance'){
		    	item.appt_link =  "<a href=ApproveReferrer.action?" + requestParams + ">" + 'View' + "</a>";			    			
			}
			
			if(item.transactionType == 'Expense'){
		    	item.appt_link =  "<a href=ApproveReferrer.action?" + requestParams + ">" + 'View' + "</a>";			
			}
			
			if(item.transactionType == 'TREQ'){
		    	item.appt_link =  "<a href=ApproveReferrer.action?" + requestParams + ">" + 'View' + "</a>";			
			}
		});
				
			storeToUse = new dojo.data.ItemFileReadStore(
								{data: {identifier: 'primaryId',
									items: data}
								}
							);
		}
		
		resultsGridStore = storeToUse;
						
						
	//set date formatting to mmddyyyy for all date columns
		resultsGridStore.comparatorMap = {
   				'fromDate': compareDates_MMDDYYYY_Format,
   				'toDate': compareDates_MMDDYYYY_Format,
   				'lastActionDate': compareDates_MMDDYYYY_Format
   				};				
		
		var resultsGrid = dijit.byId('resultsGrid');
		resultsGrid.setStore(resultsGridStore);
		
		if(data.length > 0){
			dojo.byId('recordCount').innerHTML = data.length;
			dojo.byId('recordCountMsg').style.visibility = 'visible';
		}else{
			dojo.byId('recordCount').innerHTML = '0';
			dojo.byId('recordCountMsg').style.visibility = 'hidden';
		}		
	
		// to fix grid header misalignment
		resultsGrid.resize();
	
		
	}
	
	function formatEmployeeIdLink(value) {
		var values = value.split("_");
		return '<a href="javascript:geEmployeeInfoLink('+ values[1] +','+ values[2]+');" name="View Employee Information" title="View Employee Information">'+values[0]+'</a>';
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
	
		if(!dept_cb.isValid() || dept_cb.getValue()==''){
			errorMsg.msg = 'Department Required!';
		}else if(!agency_cb.isValid()){
			errorMsg.msg = 'Agency Required!';
		}else if(!tku_cb.isValid()){
			errorMsg.msg = 'TKU Required!';
		}else if(isAgencySelected() && !isDepartmentSelected()){
			errorMsg.msg = 'Department required for agency!';
		}else if(isTkuSelected() && !isAgencySelected()){
			errorMsg.msg = 'Agency required for TKU!';
		}
		
		return (errorMsg.msg.length >0)? false: true;
	}
	
	function validateDeptForEmpId(errorMsg){
		var deptSelected = isDepartmentSelected();

		errorMsg.msg = (dojo.byId('empId_text').value.length > 0 && !deptSelected) ?
							'Department required for search based on Emp Id' : '';
		
		return (errorMsg.msg.length >0)? false: true;
	}
	
	
	function validateEmpIdForNumeric(errorMsg){
	  var empId = dojo.byId('empId_text').value;
	  
	  if (!(isNumeric(empId)) && (empId.length > 0)) {
	  errorMsg.msg = "Employee Id should be numeric";
	  }
	  return (errorMsg.msg.length >0)? false: true;
	
	}
	
	
	function validateDeptForLastname(errorMsg){
		var deptSelected = isDepartmentSelected();

		errorMsg.msg = (dojo.byId('lastname_text').value.length > 0 && !deptSelected) ?
							'Department required for search based on last name' : '';
							
		return (errorMsg.msg.length >0)? false: true;
	}
	
	
	function validateEmpIdLastName(errorMsg){
		errorMsg.msg = (dojo.byId('empId_text').value.length > 0 && dojo.byId('lastname_text').value.length > 0) ?
							'Please enter Emp Id or Last name' : '';
							
		return (errorMsg.msg.length >0)? false: true;
	}
	function SaveFilter(){
		dojo.cookie("statewideApprovalsFilterPref", dojo.toJson(dijit.byId('resultsGrid').getFilter()), {path: '/'});
		dojo.cookie("statewideApprovalsFilterRelationPref", dijit.byId('resultsGrid').getFilterRelation(), {path: '/'});
	} 
		
	function filterRows(filter, relation){
		disableFormFields(true);
		
		// check if store exists
		if(resultsGridStore._getItemsArray().length > 0){
			if (filter != null) {				
				dijit.byId('resultsGrid').setFilter(dojo.fromJson(filter), relation);
			}
		}
		
		dijit.byId('resultsGrid').showMessage('');
		dijit.byId('resultsGrid').resize();
		
		disableFormFields(false);
						
		// save field state on server
		SaveFilter();
	}
	
	// Highlights the department selected. kp 
	function selectDepartment(){
		dijit.byId('departments').focus();
	}
	
</script>

<table
	style="margin-top: 2%; margin-left: 1%; margin-right: 1%; margin-bottom: 2%">
	<tr>
		<td>
			<!-- <form onsubmit="validateAndSubmit(this);return false;" id="empSelectionForm"> -->
			<fieldset style="border: 1px solid black">
				<legend style="color: black; font-weight: bold; font-size: 9pt">
					<b>Select Employee</b>
				</legend>
				<form 
					onsubmit="dojo.byId('searchButtonClicked').value = true;initNoResultsGrid();dojo.byId('recordCountMsg').style.visibility='hidden';validateAndSubmit(this);return false;"
					id="empSelectionForm">
					<table>
						<tr>
							<td><br>
							</td>
						</tr>
						<tr>
							<td>&nbsp;Dept&nbsp;</td>
							<td><input id="department_cb" name="chosenDepartment">
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
							<td><input id="sw_searchBtn" type="submit" value="Search">
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
					<div style="display: none">
	<s:textfield id="searchButtonClicked" theme="simple" name = "searchButtonClicked"/>

</div>
				</form>
			</fieldset> <!-- </form> --></td>
	</tr>

	<tr>
		
	</tr>
	<tr>
		<td>
			<!--  results grid -->
			<span style="color: black; font-weight: bold; font-size: 9pt">Search
	        Results</span>
           <!-- record count -->
          <span id="recordCountMsg" style="color: black; font-size: 9pt; visibility: hidden">&nbsp;(<span
	        id="recordCount"></span>&nbsp; records)</span>
			<table id="resultsGrid" dojotype="dojox.grid.EnhancedGrid"
				query="{requestId: '*'}" store="resultsGridStore" rowsPerPage="15"
				plugins="{ filter:{ruleCount: 0}}" selectionMode="none"
				style="width: 800px; height: 500px">
				<thead>
					<tr>
						<th field="chkBoxUpdate" width="5%" selectionMode="none" formatter="formatGridLink">
							<center>Select</center>
						</th>
						<th field="appt_link" width="5%" formatter="formatGridLink">View</th>
						<th field="transactionType" width="9%">Tran Type</th>
						<th field="empInfo_link" width="22%"
							formatter="formatEmployeeIdLink">Name</th>
						<th field="empIdentifier" width="7%">EID#</th>
						<th field="requestId" width="9%">Request ID</th>
						<th field="fromDate" width="9%">Start Date</th>
						<th field="toDate" width="9%">End Date</th>
						<th field="dollarAmount" width="8%"
							formatter="formatCurrencyWithNoDollarSign">Amount($)</th>
						<th field="adjIdentifierDisplay" width="4%">Adj Ind</th>
						<th field="deptCode" width="8%">Dept Code</th>
						<th field="lastActionDate" width="10%">Last Action</th>
					</tr>
				</thead>
			</table></td>
	</tr>
</table>

<div style="display: none">
	<s:textfield id="jsonResponse_hidden" theme="simple"
		value="%{jsonResponse}" />
	<s:textfield id="statewideApprovalsFilter"
		value="%{#session.statewideApprovalsFilterPref}" />
</div>

