<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>

<!-- calendar styles & scripts -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/jscal2.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/border-radius.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/win2k/win2k.css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/lang/en.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/agencyOptions.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/date.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/enGrid.css"></link>



<!-- JS to render drop downs -->



<script type="text/javascript">
dojo.require("dojox.grid.EnhancedGrid");
dojo.require("dojox.grid.enhanced.plugins.Filter");
dojo.require("dojo.data.ItemFileWriteStore"); 
dojo.require("dojo.parser");
dojo.require("dojox.grid.DataGrid");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dijit.Tooltip");


</script>


<script type="text/javascript">
	var mystore = new dojo.data.ItemFileWriteStore({
		data1 : data1
	});
	var data1 = {
		identifier : 'deptNum',
		items : []
	};

	resultGridStore = null;
	emptyGridStore = new dojo.data.ItemFileReadStore({
		data : {
			identifier : 'identifierKey',
			items : []
		}
	});

	dojo.addOnLoad(function() {
		initDeptAgencyCB();
		increaseDeptAgyDDLength();
		initResultsGrid();		
		var value = dojo.fromJson(dojo.byId("jsonResponse_hidden").value);

		dojo.byId("module_hidden").value = value.writeAccess;

	});

	function initResultsGrid() {
		resultGridStore = emptyGridStore;
		dijit.byId('resultGrid').setStore(resultGridStore);
	}

	function validateAndSubmit(form) {
		var errorMsg = {
			msg : ''
		};
		displayValidationErrorMsg('');
		if (validateUserInput(errorMsg)) {
			fetchAndDisplayResults(form);
		} else {
			displayValidationErrorMsg(errorMsg.msg);
		}

	}

	function fetchAndDisplayResults(obj) {
		dojo.xhrPost({
			url : 'searchAgyDeptList.action',
			handleAs : "json",
			form : obj,
			handle : function(data, args) {
				if (typeof data == "error") {
					console.warn("error!", args);
				} else {
					updateResultGrid(data.response);
				}
			}
		});
	}

	function disableFormFields(_disable) {
		var searchBtn = dojo.byId('ad_searchBtn');
		if (_disable) {
			searchBtn.value = 'Searching...';
			searchBtn.disabled = true;
			disableFields(true);
			dijit.byId('resultGrid').showMessage("Loading... Please wait");
		} else {
			searchBtn.value = 'Search';
			searchBtn.disabled = false;
			disableFields(false);
		}
	}

	//display results
	function updateResultGrid(data) {
		var storeToUse;
		//convertNullFieldsToEmptyStrings(data);
		editButton = "Edit";
		if (data.length < 1) {
			displayValidationErrorMsg('No results found');
			storeToUse = emptyGridStore;
		} else {

			dojo
					.forEach(
							data,
							function(item) {
								var tempAccess = "";
								tempAccess = dojo.byId("module_hidden").value;

								item.select = "<a href=agySelectAgencyLocation.action?identifierKey="
										+ item.identifierKey
										+ "&module_hidden="
										+ tempAccess
										+ ">"
										+ "Select" + "</a>";
								if (item.deptNum == undefined)
									item.deptNum = '';
								if (item.agencyNum == undefined)
									item.agencyNum = '';
								if (item.agencyName == undefined)
									item.agencyName = '';
							});
		}
		
		storeToUse = new dojo.data.ItemFileReadStore({
		data : {
			identifier : 'deptNum',
			items : data
			}
		});
		
		resultGridStore = storeToUse;
		createStoreAndpopulateresultGrid(data);

		dijit.byId('resultGrid').noDataMessage = "No Data Found.. Please modify your search criteria";

	}

	/**
	 * Display the values in Select 
	 */
	function createStoreAndpopulateresultGrid(data) {
		var pp = 'identifierKey';
		if (data != null) {
			resultGridStore = new dojo.data.ItemFileReadStore({
				data : {
					identifier : pp,
					items : data
				}
			});
			dijit.byId('resultGrid').setStore(resultGridStore);
		}
	}

	var layout = [ {
		field : "Deapartment Number",
		datatype : "string"
	}, {
		field : "Agency Number",
		datatype : "string"
	}, {
		field : "Agency Name",
		datatype : "string"
	}, {
		field : "Select",
		datatype : "string"
	}

	];

	
	var mystructure = [ {
		cells : [ {
			field : "Deapartment Number",
			datatype : "string"
		}, {
			field : "Agency Number",
			datatype : "string"
		}, {
			field : "Agency Name",
			datatype : "string"
		}, {
			field : "Select",
			datatype : "string"
		}

		]
	} ]
</script>

<form id="agencyLocSearchForm"
	onsubmit="validateAndSubmit(this);return false;">

	<br> <br>
	<table border="0" id="selectAgyLoc" align="left" cellspacing="2">
		<tr>
			<td>Department :&nbsp;</td>
			<td><input id="department_cb" name="chosenDept"
				style="width: 12em;"></td>
			<td>Agency :&nbsp;</td>
			<td><input style="width: 30em" id="agency_cb"
				name="chosenAgency"></td>
			<td><input id="ad_searchBtn" type="submit" value="Search">
			</td>
		</tr>
	</table>

	<br> <br> <br> <br> <span>Search Results</span>
	<table id="resultGrid" jsId="resultGrid"
		dojoType="dojox.grid.EnhancedGrid" query="{ identifierKey : '*' }"
		rowsPerPage="20" model="jsonStore"
		style="width: 96%; height: 30%; position: relative;" store="mystore"
		plugins="{ filter:{ruleCount: 0}}
		 ">

		<thead>
			<tr>
				<th field="deptNum" width="12%"><em>Department Number</em></th>
				<th field="agencyNum" width="12%"><em>Agency Number</em></th>
				<th field="agencyName" width="50%"><em>Agency Name</em></th>
				<th field="select" width="6%" formatter="formatGridLink">
					Select</th>
			</tr>
		</thead>
	</table>
	<br>
</form>
<div style="position: relative; top: 30px" id="createDiv">

	<div style="display: none" id="errorMsg"
		style="font-style:italic; font-size:9pt; color:red"></div>
</div>

<div style="display: none">
	<s:textfield id="jsonResponse_hidden" theme="simple"
		value="%{jsonResponse}" />
	<s:textfield id="module_hidden" theme="simple" value="%{writeAccess}" />
	<!-- 	<s:textfield id="module_hidden" theme="simple" value="%{moduleId}" />-->
</div>
