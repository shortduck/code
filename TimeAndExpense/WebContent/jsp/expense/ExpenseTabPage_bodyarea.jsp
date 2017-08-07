<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="gov.michigan.dit.timeexpense.model.core.UserProfile"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/jscal2.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/border-radius.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/win2k/win2k.css">

<script type="text/javascript"
	src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/lang/en.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/date.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/expense_common.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/tripId.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/expenseDetails.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ExpenseLiquidation.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ExpenseHistory.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/codingBlockCommon.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ExpenseCodingBlock.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ExpenseCodingBlockExtended.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/codingBlockValidation.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ExpenseSummary.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/multipleAppointments.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/enGrid.css">

<script type="text/javascript">
	dojo.require("dojox.grid.EnhancedGrid");
	dojo.require("dojox.grid.enhanced.plugins.Filter");
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

<!-- style for dialog overlay -->
<style>
sup {
	color: RED
}

.count {
	color: gray;
	font-size: 9pt
}

#multipleAppointmentsDialog_underlay {
	background: gray;
	opacity: 0.7;
}

#dialogApprove_underlay {
	background: gray;
	opacity: 0.7;
}

#dialogReject_underlay {
	background: gray;
	opacity: 0.7;
}

#expensecommentsrej {
	width: 300px;
	height: 150px;
	border-style: solid;
	border-color: black;
	border-width: 1;
	background: white
}

#expensecommentsapp {
	width: 300px;
	height: 150px;
	border-style: solid;
	border-color: black;
	border-width: 1;
	background: white
}
</style>
<!-- change color of tooltip -->
<style type="text/css">
.soria .dijitTooltipContainer {
	position: absolute;
	left: 0;
	top: -20;
	background-color: #F9F7BA;
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
<script type="text/javascript">
	var errorGridStore;
	var expenseReloadRequired;
	var checkSaveAgainCounter = 0;
	var previousTab = "idTab";
	var firstTime = true;
	var cbTabLoaded = false;
	var tabsToReload = [];
	// added to hold reference for last tab until Ajax save is completed through the popup
	var lastTabForAjaxSave;

	dojo.addOnLoad(function() {
		initTwoSingleDateCalendars();
		initTripId();
		populateErrorsGrid();
		performTabLoadPostProcessing();

		// attach event handlers to counter fields
		processTextFieldCounters();

		// focus on tab as directed by server
		focusTab(dojo.byId('focusTabId').value);
		var idTab = dijit.byId('idTab');
		if (idTab.isLoaded && !dojo.byId('f_fromDateField').disabled) {
			dojo.byId('f_fromDateField').focus();															
		}

		summaryOperationPostProcessing();
	});

	function summaryOperationPostProcessing() {
		var lastSuccessfulOperation = null;
		if (dojo.byId('lastSuccessfulOperation') != null) {
			lastSuccessfulOperation = dojo.byId('lastSuccessfulOperation').value;
		}
		if (lastSuccessfulOperation != null
				&& lastSuccessfulOperation.length > 0) {
			var tab = dijit.byId('expense_tab_container');
			if (tab != null)
				tab.selectChild('summaryTab');

			// clear last action from session
			dojo.xhrPost({
				url : "ClearLastAction.action"
			});
		}
	}

	// set focusable tab
	function focusTab(tabId) {
		var tabContainer = dijit.byId('expense_tab_container');
		if (tabContainer != null && tabId != null && tabId.length > 0) {
			tabContainer.selectChild(tabId);
		}
	}

	function travelTypeChange(data) {
		//1. get store
		var expenseTypeCBStore = prepareReadStore(data.response.expenseTypes,
				'code');

		//2. get expense_cb handle
		var ddExpenseType_cb = dijit.byId('expenseType_cb');

		//3. assign store to the DD

		if (ddExpenseType_cb != null) {
// 			ddExpenseType_cb.store = expenseTypeCBStore;
			ddExpenseType_cb.set("store", expenseTypeCBStore );
		}
	}

	/* Attaches event handlers to tab selection */
	function performTabLoadPostProcessing() {

		dojo.connect(
						dijit.byId('expense_tab_container'),
						"selectChild",
						function(child) {

							var result = null;
							var responseToConfirm = null;
							result = checkFormState();
							var tab = dijit.byId('expense_tab_container');
							var problemDuringSave = false;

							if (child.id == 'expenseDetailsTab') {
								serverRequestExpenseDetailsInquiry(
										"FindExpenseDetails.action", null,
										travelTypeChange);
							}
							
							if (child.id == 'idTab' && child.isLoaded && !dojo.byId('f_fromDateField').disabled) {
								dojo.byId('f_fromDateField').focus();															
							}

							if ((result) && result.indexOf("There are unsaved changes") != -1
									&& checkSaveAgainCounter == 0) {
								responseToConfirm = confirm("There are unsaved changes. Do you want to save and proceed?");
								if (responseToConfirm) {
									checkSaveAgainFlag = true;
									lastTabForAjaxSave = previousTab;

									problemDuringSave = previousTab == "idTab" ? saveExpense()
											: (previousTab == "expenseDetailsTab" ? saveExpenseDetails()
													: (previousTab == "liquidationsTab" ? saveExpenseLiquidations()
															: ""));
								}

								/*if(problemDuringSave == true)
								{	
									// take user back to previous tab where save error occured
									checkSaveAgainCounter = 1;
									tab.selectChild(previousTab);
								}*/

								if (!responseToConfirm || problemDuringSave) {
									checkSaveAgainCounter = 1;
									tab.selectChild(previousTab);
								}

							} else if (checkSaveAgainCounter == 1) {
								checkSaveAgainCounter = 0;
							}
							previousTab = tab.selectedChildWidget.id;

							if (expenseReloadRequired != null
									&& expenseReloadRequired) {
								// reload expense
								window.location = window.location;
							}
							
							

							// reload tabs if required
							if (child.id == 'idTab'
									&& expenseReloadRequired != null
									&& expenseReloadRequired) {
								// reload expense
								window.location = window.location;
							}

							// if expense details tab is selected
							else if (child.id == 'expenseDetailsTab'
									&& child.isLoaded) {
								var expDetailsGrid = dijit
										.byId('expenseDetailsGrid');
								if (expDetailsGrid != null) {
									// Grid doesn't render in IE8 in hidden mode. So, just trigger grid rendering.
									expDetailsGrid
											.setStore(expDetailsGrid.store);

									// IE unexpected behavior of not reconnecting dojo connects after page refresh!
									// Reconnect dojo events. 
									disconnectEvents();
									connectEvents();

									// Resize grid to fix column alignment issues
									expDetailsGrid.resize();

									// sync expense detail date with ID from date, if no details present
									if (expDetailsGrid.rowCount < 1
											&& dojo.byId('fromDate').value.length < 1) {
										populateDefaultDetailDate();
									}
								}
							} // if Coding Block tab is selected
							else if (child.id == 'cbTab') {
								if (dijit.byId('resultCodingBlockGrid') != null) {
									dijit.byId('resultCodingBlockGrid')
											.resize();
								}
							}

							// if liquidations tab is selected
							else if (child.id == 'liquidationsTab') {
								if (dijit.byId('expenseLiquidationsGrid') != null) {
									refreshLiquidationsAfterLoad();
								}
							}

							// if history tab is selected
							else if (child.id == 'historyTab') {
								if (dijit.byId('historyGrid') != null) {
									dijit.byId('historyGrid').resize();
								}
							}

							//always refresh summary tab
							if (findElementPositionInArray(tabsToReload,
									'summaryTab') < 0) {
								tabsToReload.push('summaryTab');
							}

							// reload tab if required
							if (tabsToReload.length > 0) {
								var ar_index = findElementPositionInArray(
										tabsToReload, child.id);
								if (ar_index > -1 && child.isLoaded) {
									child.refresh();
									tabsToReload.splice(ar_index, 1);
								}
							}
						});
	}

	/* Receives notification upon successful submission */
	function notifyExpenseSubmission() {
		expenseReloadRequired = true;
	}

	function updateErrorsGrid(errors) {
		errorGridStore = new dojo.data.ItemFileReadStore({
			data : {
				identifier : 'id',
				items : errors
			}
		});

		var errorGridNode = dijit.byId('errorGrid');
		if (errorGridNode != null) {
			errorGridNode.setStore(errorGridStore);
			errorGridNode.resize();
		}
	}

	/* Reads errors and pushes them to the grid */
	function populateErrorsGrid() {
		var expenseErrJson = dojo.byId('bizErrors').defaultValue;

		if (expenseErrJson != null && expenseErrJson != "") {
			var expErr = dojo.fromJson(expenseErrJson);
			if (expErr != null) {
				updateErrorsGrid(expErr);
			}
		}
	}

	function initCodingBlock() {
		displayExpenseList();
	}

	function initExpenseLiquidation() {
		displayLiquidationList();
	}

	function initExpenseHistory() {
		displayExpenseHistoryList();
	}

	function initExpenseSummary() {
		displayExpenseSummaryList();
	}

	function addTabPrefElement(formId, tabId) {
		var form = dojo.byId(formId);
		var newInputNode = document.createElement("input");
		newInputNode.name = 'focusTab';
		newInputNode.type = 'text';
		newInputNode.value = tabId;
		newInputNode.style.display = 'none';
		newInputNode = form.appendChild(newInputNode);
	}
	var winSummary;
	var winDetail;
	function openSummaryReportUrl() {
		//var url = "/TimeAndExpense/jsp/report/ExpSummaryReport-viewer.jsp";
		var url = "/TimeAndExpense/openPrintSummaryReport.action";

		var closed = true;
		if (winSummary != undefined)
			closed = winSummary.closed;
		winSummary = window.open(url, "PrintSummaryReport",
						"width=900px,height=650px,modal=no,scrollbars=1,resizable=yes,status=1");
		if (closed)
			winSummary.moveTo(0, 0);
		else
			winSummary.location.reload();
	}

	function openDetailReportUrl() {
		//var url = "/TimeAndExpense/jsp/report/ExpenseDetailReport-viewer.jsp?expenseMasterId=dojo.byId('expenseMasterId').value"; 
		var url = "/TimeAndExpense/openPrintDetailReport.action";

		var closed = true;
		if (winDetail != undefined)
			closed = winDetail.closed;
		winDetail = window.open(url, "PrintDetailReport",
						"width=900px,height=650px,modal=no,scrollbars=1,resizable=yes,status=1");
		if (closed)
			winDetail.moveTo(0, 0);
		else
			winDetail.location.reload();
	}

	function openRequisitionCbs() {
		//returnVal = window.open("${pageContext.request.contextPath}/jsp/expense/displayTravelRequisitionCodingBlock.jsp", "list", "width=900px,height=700px,modal=no,scrollbars=1");
		returnVal = window.open("DisplayTravelRequisitionCodingBlock.action",
				"list", "width=900px,height=700px,modal=no,scrollbars=1");
	}
</script>

<div style="height: 7px">
	<!-- -->
</div>

<span>
	<label for="expenseEventId">Request Id:</label>
	<input type="text" id="expenseEventId" size="10" style="border: none"
	readonly="readonly">
</span>
<span>		
	<jsp:include page="../empHeaderInfo.jsp" />
</span>

<div style="height: 5px">
	<!-- -->
</div>
<div id="expense_tab_container" dojotype="dijit.layout.TabContainer" controllerWidget="dijit.layout.TabController"
	style="width: 98%; height: 80%; position: relative">
	<div id="idTab" dojotype="dijit.layout.ContentPane" title="ID">
		<span><jsp:include page="TripId.jsp" /></span>
	</div>
	<div id="expenseDetailsTab" dojotype="dijit.layout.ContentPane"
		title="Expenses" loadingMessage="Loading..."
		href="jsp/expense/expenseDetails.jsp" preload="true"
		preventCache="true" onload="initExpenseDetailsGrid();" ondownloadend="setupExpenseDetailDefaults();"></div>
	<div id="cbTab" dojotype="dijit.layout.ContentPane"
		title="Coding Block" align="left" preLoad="false"
		loadingMessage="Loading Coding Block. Please wait.."
		href="jsp/expense/ExpenseCodingBlock.jsp" onload="initCodingBlock();"
		preventCache="true"></div>
	<div id="liquidationsTab" dojotype="dijit.layout.ContentPane"
		title="Liquidations"
		loadingMessage="Loading Liquidations. Please wait.."
		href="jsp/expense/ExpenseLiquidation.jsp"
		onload="initExpenseLiquidation();" preLoad="false" preventCache="true">
	</div>
	<div id="summaryTab" dojotype="dijit.layout.ContentPane"
		title="Summary" loadingMessage="Loading Summary. Please wait.."
		href="ExpenseSummaryAction.action" onload="initExpenseSummary();"
		preventCache="true"></div>

	<div id="historyTab" dojotype="dijit.layout.ContentPane"
		title="History" loadingMessage="Loading History. Please wait.."
		href="ExpenseHistoryList.action" onload="initExpenseHistory();"
		preventCache="true" refreshOnShow="true"></div>
</div>

<div id="errorGridContainer">
	<jsp:include page="../errorGrid.jsp"></jsp:include>
</div>
<div style="display: none">
	<s:textfield id="focusTabId" name="focusTab" />
</div>

