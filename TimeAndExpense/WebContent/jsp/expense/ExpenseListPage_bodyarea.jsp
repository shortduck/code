<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
	
<%
// This is required to force no caching in IE
response.setHeader("Cache-Control", "no-cache, private, no-store, max-age=0, s-maxage=0, must-revalidate, proxy-revalidate");
response.setDateHeader ("Expires", -1);
response.setHeader("Pragma","no-cache");
%>

<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="PRIVATE">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-STORE">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="MAX-AGE=0">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="S-MAXAGE=0">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="MUST-REVALIDATE">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="PROXY-REVALIDATE">

<link href="${pageContext.request.contextPath}/css/calendar-win2k-1.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/enGrid.css"></link>

<!--  Calendar CSS and JSs START -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/jscal2.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/win2k/win2k.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/lang/en.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/date.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/expense_common.js"></script>
<!--  Calendar CSS and JSs END -->

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

sup {
	color: RED;
}
</style>

<!-- JS to render advance tab control and grid -->
<script type="text/javascript">
	dojo.require("dojox.grid.EnhancedGrid");
	dojo.require("dojox.grid.enhanced.plugins.Filter");
	dojo.require("dojo.parser");
	dojo.require("dojox.grid.DataGrid");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.Tooltip");
</script>

<script type="text/javascript">
	var store = null;
	dojo.addOnLoad(function() {
		dijit.byId("resultGrid").showMessage("Loading ... Please wait");
		displayExpenseList();
		initTwoSingleDateCalendars("fromDate", "fromDateSelector", "toDate",
				"toDateSelector");
	});

	function displayExpenseList() {
		dojo.xhrPost({
			url : 'AjaxExpenseListAction.action',
			handleAs : "json",
			handle : function(data, args) {
				if (typeof data == "error") {
					//if errors, do not pursue the effect of call!
					console.warn("error!", args);
				} else {
					updateResultGrid(data.response);
					dijit.byId('resultGrid').resize();					
				}
			}
		});
	}
	var resultsGridStore = null;
	// function to display the grid
	function updateResultGrid(data) {

		//convertNullFieldsToEmptyStrings(data);
		deleteButton = "Delete";

		dojo.forEach(
						data,
						function(item) {
							if (item.actionCodeForDisplay == undefined)
								item.actionCodeForDisplay = '';
								// ZH - commented fix for defect # 264
							/*if(item.audit == undefined)
							    item.audit='';		*/				
						    if (item.natureOfBusiness == undefined)
								item.natureOfBusiness = '';
							if (item.expDateFrom == undefined)
								item.expDateFrom = '';
							if (item.expDateTo == undefined)
								item.expDateTo = '';
							if (item.paidDate == undefined)
								item.paidDate = '';
							if (item.OrigPaidDate == undefined)
								item.OrigPaidDate = '';
							if (item.travelInd == undefined)
								item.travelInd = '';
							if (item.outOfStateInd == undefined)
								item.outOfStateInd = '';
							if (item.revisionNumber == undefined)
								item.revisionNumber = '';
							
							item.s_deleteFlag = '';
							if (item.deleteFlag == "Y"
									&& item.revisionNumber == 0) {
								item.s_deleteFlag = "<a href=ExpenseDeleteAction.action?expenseEventId="
										+ item.expevIdentifier
										+ " onclick=\"return confirm('Are you sure you want to delete this Expense?')\">"
										+ "Delete" + "</a>";
								//item.s_deleteFlag = '<a href="" onclick ="return AjaxDeleteExpense('+item.expevIdentifier+')" >'+deleteButton+'</a>';
								//return item.s_deleteFlag;
							}
							if (item.natureOfBusiness != undefined) {
							}
							item.shortComments = item.natureOfBusiness
									.substring(0, 60);
						});
		dijit.byId('resultGrid').noDataMessage = "No Data Found.. Please modify your search criteria";
		createStoreAndpopulateResultsGrid(data);
		dojo.connect(dijit.byId('resultGrid'), 'onCellMouseOver',
						'showToolTip');
		dojo.connect(dijit.byId('resultGrid'), 'onCellMouseOut', 'hideToolTip');

		if (dijit.byId('resultGrid') != null)
			dojo.connect(dijit.byId('resultGrid'), 'onRowClick',
					'processExpenseDetailsGridRowSelection');

	}

	function formatExpenseIdGridLink(value, rowIndex) {
		return "<a href=ExpenseAction.action?expenseMasterId="
				+ this.grid.getItem(rowIndex).expmIdentifier + ">" + value;
	}

	function showToolTip(event) {

		var expenseGrid = dijit.byId('resultGrid');
		var natureOfBusiness = expenseGrid._by_idx[event.rowIndex].item.natureOfBusiness;
		if (event.cellIndex == 5) {
			if (natureOfBusiness != undefined) {
				var msg = natureOfBusiness;
				dijit.showTooltip(msg, event.cellNode);
			}
		}
	}

	function hideToolTip(e) {
		dijit.hideTooltip(e.cellNode);
	}

	function createStoreAndpopulateResultsGrid(data) {
		resultsGridStore = new dojo.data.ItemFileReadStore({
			data : {
				identifier : 'expevIdentifier',
				items : data
			}
		});

		resultsGridStore.comparatorMap = {
			'expDateFrom' : compareDates_MMDDYYYY_Format,
			'expDateTo' : compareDates_MMDDYYYY_Format,
			'paidDate': compareDates_MMDDYYYY_Format,
			'OrigPaidDate': compareDates_MMDDYYYY_Format
		};
		dijit.byId('resultGrid').setStore(resultsGridStore);
	}

	function filterRows(selectedChkBox) {
		var reportType;
		if (selectedChkBox.value == 'E') {
			reportType = 'TRAN_WO_ADJ';
		} else if (selectedChkBox.value == 'EA') {
			reportType = 'TRAN_ADJ';
		} else {
			reportType = 'TRAN';
		}

		dojo.xhrPost({
			url : "ExpenseListTypeAction.action?expenseListType=" + reportType,
			handleAs : "json",
			handle : function(data, args) {
				if (typeof data == "error") {
					//if errors, do not pursue the effect of call!
					console.warn("error!", args);
				} else {
					updateResultGrid(data.response);
					dijit.byId('resultGrid').resize();
				}
			}
		});
		return false;
	}

	function showCloneExpenseDialog() {

		dojo.byId("errorMsg").innerHTML = "";
		dijit.byId('dialogCloneExpense').show();
		dijit.byId('btnContinue').focus();
		window.onbeforeunload = null;
	}

	function cancelClick() {
		dijit.byId('dialogCloneExpense').hide();
	}

	function cloneClick() {

		var errorMsg = {
			msg : ''
		};

		if (!validateUserInput(errorMsg)) {
			//set error message
			dojo.byId("errorMsg").innerHTML = errorMsg.msg;
			return;
		}

		var grid = dijit.byId('resultGrid');
		var selExpDtl = grid.selection.getSelected()[0];
		var validDetail = isNotNull(selExpDtl);
		var expevIdentifier = 0;

		var fromDate;
		var toDate;

		fromDate = dojo.byId("fromDate");
		toDate = dojo.byId("toDate");

		if (validDetail && selExpDtl.expevIdentifier[0])
			expevIdentifier = selExpDtl.expevIdentifier[0];

		//make server call.
		var requestData = {};
		requestData.expevIdentifier = expevIdentifier;
		requestData.fromDate = fromDate.value;
		requestData.toDate = toDate.value;
		var origFDate = grid.selection.getSelected()[0].expDateFrom[0];
		var origTDate = grid.selection.getSelected()[0].expDateTo[0];
		requestData.origFromDate = origFDate;
		requestData.origToDate = origTDate;

		serverRequestCall("CloneExpense.action", requestData, afterClone);
	}

	function afterClone(data) {
		if (data != null && data.response != null) {
			//if not a number then display the error message
			if (data.response.expevIdentifier == 0) {
				dojo.byId("errorMsg").innerHTML = data.response.message;
			} else {
				//redirect it to the expense page
				dijit.byId('dialogCloneExpense').hide();
				document.location.href = "ExpenseAction.action?expenseMasterId="
						+ data.response.expevIdentifier;
			}
		} else
			dojo.byId("errorMsg").innerHTML = "Unable to clone expense at this time.";
	}

	/* Invoked when user selects a row in the grid */
	function processExpenseDetailsGridRowSelection(event) {
	
		var grid = dijit.byId('resultGrid');
		
		if (grid.selection.getSelected()[0].pdfInd[0].toLowerCase() == 'y')		
			dojo.byId("buttonCloneExpense").disabled = true;
		else
			dojo.byId("buttonCloneExpense").disabled = false;
		
	}

	/* 
	 * Utility method to make an Ajax call to the server and 
	 * invoke the callback method upon receiving successful response
	 */
	function serverRequestCall(serviceUrl, _data, callbackMethod) {
		dojo.xhrPost({
			url : serviceUrl,
			handleAs : "json",
			content : _data,
			sync : true,
			handle : function(data, args) {
				callbackMethod(data);
			}
		});
	}

	/* Validate user input upon SAVE */
	function validateUserInput(errorMsg) {

		return validateFromDate(errorMsg) && validateToDate(errorMsg)
				&& validateDateFromLessThanTo(errorMsg)
				&& validateSameCloneDates(errorMsg)
				&& validateOverlappingCloneDates(errorMsg);
	}

	function validateFromDate(errorMsg) {
		var fDate = dojo.byId('fromDate').value;

		errorMsg.msg = (fDate == '') ? "Expense 'From' date missing" : '';
		return (errorMsg.msg.length > 0) ? false : true;
	}
	function validateToDate(errorMsg) {
		var tDate = dojo.byId('toDate').value;

		errorMsg.msg = (tDate == '') ? "Expense 'To' date missing" : '';
		return (errorMsg.msg.length > 0) ? false : true;
	}
	function validateDateFromLessThanTo(errorMsg) {
		var fDate = Date.parse(dojo.byId('fromDate').value);
		var tDate = Date.parse(dojo.byId('toDate').value);

		errorMsg.msg = (fDate.compareTo(tDate) > 0) ? "Expense 'To' date cannot be less than 'From' date"
				: '';
		return (errorMsg.msg.length > 0) ? false : true;
	}

	function validateSameCloneDates(errorMsg) {

		var grid = dijit.byId('resultGrid');

		var origFDate = Date
				.parse(grid.selection.getSelected()[0].expDateFrom[0]);
		var origTDate = Date
				.parse(grid.selection.getSelected()[0].expDateTo[0]);

		var fDate = Date.parse(dojo.byId('fromDate').value);
		var tDate = Date.parse(dojo.byId('toDate').value);

		errorMsg.msg = ((origFDate.compareTo(fDate) == 0) && (origTDate
				.compareTo(tDate) == 0)) ? "Original Expense dates are same as cloned dates"
				: '';

		return (errorMsg.msg.length > 0) ? false : true;

	}
	function validateOverlappingCloneDates(errorMsg) {

		var grid = dijit.byId('resultGrid');

		var origFDate = Date
				.parse(grid.selection.getSelected()[0].expDateFrom[0]);
		var origTDate = Date
				.parse(grid.selection.getSelected()[0].expDateTo[0]);

		var fDate = Date.parse(dojo.byId('fromDate').value);
		var tDate = Date.parse(dojo.byId('toDate').value);

		if ((fDate >= origFDate && fDate <= origTDate)
				|| (tDate >= origFDate && tDate <= origTDate)) {
			errorMsg.msg = "Cloned dates are overlapping original expense dates.";

		}

		return (errorMsg.msg.length > 0) ? false : true;
	}


</script>

<div dojoType="dijit.Dialog" id="dialogCloneExpense"
	title="Clone Expense"
	style="border: 1px; background: white; display: none">

	<table border="0">
		<tr>
			<td colspan="2">Enter the From and To date for expense to be
				cloned.</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">&nbsp;From Date<sup>*</sup>&nbsp;
				<input type="text" id="fromDate" name="fromDate"
				size="10" onblur="syncFromDate();"/> <img
				src="${pageContext.request.contextPath}/image/calendar.gif"
				id="fromDateSelector" alt="Choose dates" /></td>
			<td align="left" nowrap="nowrap">&nbsp;To Date<sup>*</sup>&nbsp;
				<input type="text" id="toDate" name="toDate"
				onblur="syncToDate(this); return true;" size="10" /> <img
				src="${pageContext.request.contextPath}/image/calendar.gif"
				id="toDateSelector" alt="Choose dates" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button dojoType=dijit.form.Button id="btnClone"
					onclick="cloneClick();">Clone</button> &nbsp;&nbsp;
				<button dojoType=dijit.form.Button id="btnCancel"
					onclick="cancelClick();">Cancel</button></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="middle" colspan="2"><div id="errorMsg"
					style="font-size: 9pt; color: red">&nbsp;</div>
			</td>
		</tr>
	</table>
</div>
<span style="position: relative; top: 10px"><jsp:include
		page="../empHeaderInfo.jsp"></jsp:include></span>
<br>
<div id="expSelectCheckBoxes"
	style="width: 98%; height: 10%; position: relative; left: 2px; top: 30px; white-space: nowrap;">
	<fieldset
		style="position: relative; border: solid 1px; top: 1px; left: 2px; height: 30px; width: 420px">
		<legend style="color: black">&nbsp;Type</legend>
		<s:form theme="simple">
			<table>
				<tr>
					<td><s:radio name="expCheckBox"
							list="#{'E':'Expense Reports Only', 'EA':'Adjustment Reports Only','B':'Both'}"
							value="%{'B'}" cssStyle="margin-left:10"
							onclick="filterRows(this)" /> <!--   --></td>
				</tr>
			</table>
		</s:form>
	</fieldset>
</div>


<table class="soria" id="resultGrid" dojotype="dojox.grid.EnhancedGrid"
	query="{ expevIdentifier: '*' }" rowsPerPage="20"
	plugins="{ filter:{ruleCount: 0}}" store="resultsGridStore"
	selectionMode="single"
	style="width: 98%; height: 30%; position: relative; top: 30px; white-space: nowrap; overflow: hidden">
	<thead>
		<tr>
			<th field="expevIdentifier" width="5%"
				formatter="formatExpenseIdGridLink">
				<center>Expense ID</center>
			</th>
			<th field="expDateFrom" width="9%">
				<center>Start Date</center>
			</th>
			<th field="expDateTo" width="9%">
				<center>End Date</center>
			</th>
			<th field="paidDate" width="9%">
				<center>Paid Date</center>
			</th>
			<th field="OrigPaidDate" width="9%">
				<center>Original Paid Date</center>
			</th>
			<th field="shortComments" width="20%">
				<center>Nature of Business</center>
			</th>
			<th field="travelInd" width="5%">
				<center>Travel Related?</center>
			</th>
			<th field="outOfStateInd" width="7%">
				<center>Out-of-State</center>
			</th>
			<th field="revisionNumber" width="7%">
				<center>Revision Number</center>
			</th>
			<th field="actionCodeForDisplay" width="10%">Last Action</th>
			<!--  ZH - commented fix for defect # 264
			<th field="audit" width="6%"><center>Audit</center></th>
			-->
			<th field="s_deleteFlag" width="10%" formatter="formatGridLink">
				<center>Delete</center>
			</th>
		</tr>
	</thead>
</table>
<br>
<div style="position: relative; top: 30px">
	<s:form theme="simple" action="setUserSubjectInSession">
		<s:submit  class="ie11_button"  value="Create New Expense" disabled="%{disableCreateButton}"></s:submit>
		<input id="buttonCloneExpense" type="button"
			value="Create New From Selected" 
			onclick="javascript:showCloneExpenseDialog();" disabled="true" style='display: ${cloningAvailable}' >		
	</s:form>
</div>
