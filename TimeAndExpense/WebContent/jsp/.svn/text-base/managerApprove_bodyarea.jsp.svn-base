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
	//array for checked row ids
	var rowArray = [];
		
	// declare global results grid store
	resultsGridStore = null;
	var selectedChkBoxTemp = null;	
	// declare global empty result grid store
	emptyGridStore = new dojo.data.ItemFileReadStore({data: {identifier: 'requestId', items: [] }});

	// methods to be invoked immediately after page DOM construction
	dojo.addOnLoad(function(){
		initResultsGrid();
		clearApprovalQueue();
	});
	dojo.addOnUnload(function() {
		SaveFilter();	
	});
	
	// function to initialize the grid at page rendering with no search results
	function initNoResultsGrid(){
		resultsGridStore = emptyGridStore;
		dijit.byId('resultsGrid').setStore(resultsGridStore);
		dijit.byId('resultsGrid').setFilter();
		dojo.cookie('managerApprovalsFilterPref', '', {expires: -1, path: '/'});
		dojo.cookie('managerApprovalsFilterRelationPref', '', {expires: -1, path: '/'});
	}

	// function to initialize the grid at page rendering with no search results
	function initResultsGrid(){
		resultsGridStore = emptyGridStore;
		dijit.byId('resultsGrid').setStore(resultsGridStore);
		var empCookie = dojo.cookie('managerApprovalsSelectEmpPref');
		
		if (empCookie) {
			dojo.query('input[type=radio][name = "employeeOption"]:checked')[0].checked = false;
			dojo.byId("employeeOption" + empCookie).checked = true;
			
		}else{
		    dojo.query('input[type=radio][name = "employeeOption"]:checked')[0].checked = true;
		}
		validateAndSubmit(dojo.byId("empSelectionForm"));
	}

	function validateAndSubmit(form){
		var errorMsg = {msg:''};
		
		// clear any previous error messages
		displayValidationErrorMsg('');
		dojo.cookie('managerApprovalsSelectEmpPref', dojo.query('input[type=radio][name = "employeeOption"]:checked')[0].value, {path: '/'});
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
			url: 'FindApprovalMgrList.action',
			form: obj,
			handleAs: "json",
			handle: function(data,args){
				disableFormFields(false);
				if(typeof data == "error"){
					//if errors, do not pursue the effect of call!
					console.warn("error!",args);
				}else{
					updateResultGrid(data.response);
					var managerApprovalsFilter = dojo.cookie("managerApprovalsFilterPref");
					var managerApprovalsFilterRelation = dojo.cookie("managerApprovalsFilterRelationPref");
					if (managerApprovalsFilter != null) {
						filterRows(managerApprovalsFilter, managerApprovalsFilterRelation);	
					}
				}
			}
		});
		disableFormFields(true);
	}
	
	function disableFormFields(_disable) {
		var searchBtn = dojo.byId('ma_searchBtn');
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
		// disable all radio fields
		dojo.query("input[type='radio']").attr("disabled", state);
	}
	
	function validateUserInput(errorMsg){
		return  true;
				}
	
	function updateResultGrid(data){
		var storeToUse;
	
		if(data === undefined || data.length <1){
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
			}else if(item.transactionType == 'Expense'){
		    	item.appt_link =  "<a href=ApproveReferrer.action?" + requestParams + ">" + 'View' + "</a>";
		    } else if(item.transactionType == 'TREQ'){
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
	
	function getQueueLink(){
		var queItems = dojo.toJson(rowArray);
		//alert(queItems);
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

	function SaveFilter(){
		dojo.cookie("managerApprovalsFilterPref", dojo.toJson(dijit.byId('resultsGrid').getFilter()), {path: '/'});
		dojo.cookie("managerApprovalsFilterRelationPref", dijit.byId('resultsGrid').getFilterRelation(), {path: '/'});
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
	
</script>

<table
	style="margin-top: 2%; margin-left: 1%; margin-right: 1%; margin-bottom: 2%">
	<tr>
		<td>
			<fieldset style="border: 1px solid black">
				<legend style="color: black; font-weight: bold; font-size: 9pt">
					<b>Select Employee</b>
				</legend>
				<form
					onsubmit="initNoResultsGrid();validateAndSubmit(this);dojo.byId('recordCountMsg').style.visibility='hidden';return false;"
					id="empSelectionForm">
					<table>
						<tr>
							<td><s:radio id="employeeOption" name="employeeOption"
									list="#{'M':'My Employees','ALL':'All Employees'}"
									value="%{#session.managerApprovalsSelectEmpPref}"
									cssStyle="margin-left:20" theme="simple" />
							</td>
							<td width="373">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td><input type="submit" id="ma_searchBtn" value="Select">&nbsp;</td>
						</tr>
						<tr>
							<td height="5px"></td>
						</tr>
						<tr>
							<td colspan="2"><div id="errorMsg"
									style="font-style: italic; font-size: 9pt; color: red"></div>
							</td>
						</tr>
					</table>
				</form>

			</fieldset></td>
	</tr>


	<tr>
	</tr>
	<tr>
		<td><br>
		</td>
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
				query="{requestId: '*'}" store="resultsGridStore"
				selectionMode="none" style="width: 800px; height: 500px"
				plugins="{ filter:{ruleCount: 0}}">
				<thead>
					<tr>
						<th field="chkBoxUpdate" width="5%" selectionMode="none"
							formatter="formatGridLink">
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
						<th field="dollarAmount" width="7%"
							formatter="formatCurrencyWithNoDollarSign">Amount($)</th>
						<th field="adjIdentifierDisplay" width="4%">Adj Ind</th>
						<th field="deptCode" width="5%">Dept Code</th>
						<th field="lastActionDate" width="19%">Last Action</th>
					</tr>
				</thead>
			</table></td>
	</tr>
</table>



<div style="display: none">
	<s:textfield id="jsonResponse_hidden" theme="simple"
		value="%{jsonResponse}" />
	<s:textfield id="managerApprovalsFilter"
		value="%{#session.managerApprovalsFilterPref}" />
</div>




