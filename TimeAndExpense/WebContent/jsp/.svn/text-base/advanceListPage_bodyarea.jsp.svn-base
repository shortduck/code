<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>

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
	src="${pageContext.request.contextPath}/js/date.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/enGrid.css"></link>

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
		dijit.byId("gridNode").showMessage("Loading ... Please wait");
		dojo.xhrPost({
			url : 'AjaxAdvanceListAction.action',
			handleAs : "json",
			handle : function(data, args) {
				if (typeof data == "error") {
					//if errors, do not pursue the effect of call!
					console.warn("error!", args);
				} else {
					resultsGridStore = new dojo.data.ItemFileReadStore({
						data : {
							items : data.response
						}
					});
					updateResultGrid(data.response);
					dijit.byId('gridNode').resize();
				}
			}
		});
	});

	function updateResultGrid(data) {

		dojo
				.forEach(
						data,
						function(item) {

							//Display Delete Link if enable_delete = Y				
							if (item.deleteFlag == "Y"
									&& item.revisionNumber == 0) {
								item.delete_link = "<a href=AdvanceDeleteAction.action?advanceEventId="
										+ item.adevIdentifier
										+ " onclick=\"return confirm('Are you sure you want to delete this advance?')\">"
										+ "Delete" + "</a>";
							}

							//right justify revision number	  
							item.revisionNumber = "<span style='float:right'>"
									+ item.revisionNumber
									+ "</span>";

							//display 15 characters of advanceReason
							if (item.advanceReason != undefined) {
								item.shortReason = item.advanceReason
										.substring(0, 30);
							}

							//call function to display blanks instead of 'undefined' for empty field 
							convertNullFieldsToEmptyStrings(item);
						});

		dijit.byId('gridNode').noDataMessage = "No Data Found";

		resultsGridStore = new dojo.data.ItemFileReadStore({
			data : {
				identifier : 'adevIdentifier',
				items : data
			}
		});

		//set date formatting to mmddyyyy for all date columns
		resultsGridStore.comparatorMap = {
			'requestDate' : compareDates_MMDDYYYY_Format,
			'paidPpeDate' : compareDates_MMDDYYYY_Format,
			'OrigPaidDate' : compareDates_MMDDYYYY_Format,
			'fromDate' : compareDates_MMDDYYYY_Format,
			'toDate' : compareDates_MMDDYYYY_Format
		};

		//call function(s) for tool tips 
		dijit.byId('gridNode').setStore(resultsGridStore);
		var grid = dijit.byId('gridNode');
		dojo.connect(grid, "onCellMouseOver", showTooltip);
		dojo.connect(grid, "onCellMouseOut", hideTooltip);

	}

	//function for displaying tooltip
	var showTooltip = function(e) {
		var testGrid = dijit.byId('gridNode');
		var reason = testGrid._by_idx[e.rowIndex].item.advanceReason;

		if (e.cellIndex == 4) {
			if (reason != undefined) {
				var msg = reason;
				dijit.showTooltip(msg, e.cellNode);
			}
		}
	};

	//function for hiding tooltip
	var hideTooltip = function(e) {
		dijit.hideTooltip(e.cellNode);
	};

	//function to display blanks instead of undefined for empty field 
	function convertNullFieldsToEmptyStrings(item) {
		if (item.adevIdentifier == undefined)
			item.adevIdentifier = '';
		if (item.requestDate == undefined)
			item.requestDate = '';
		if (item.paidPpeDate == undefined)
			item.paidPpeDate = '';
		if (item.OrigPaidDate == undefined)
			item.OrigPaidDate = '';
		if (item.shortReason == undefined)
			item.shortReason = '';
		if (item.fromDate == undefined)
			item.fromDate = '';
		if (item.toDate == undefined)
			item.toDate = '';
		if (item.dollarAmountForDisplay == undefined)
			item.dollarAmountForDisplay = '';
		if (item.amountOutStandingForDisplay == undefined)
			item.amountOutStandingForDisplay = '';
		if (item.revisionNumber == undefined)
			item.revisionNumber = '';
		if (item.status == undefined)
			item.status = '';
		if (item.delete_link == undefined)
			item.delete_link = '';

	};

	//function used to determine whether to display advances with or without adjustments or both
	function filterRows(selectedChkBox) {
		var reportType;
		if (selectedChkBox.value == 'A') {
			reportType = "TRAN_WO_ADJ";
		} else if (selectedChkBox.value == 'AA') {
			reportType = "TRAN_ADJ";
		} else {
			reportType = "TRAN";
		}

		dojo.xhrPost({
			url : "AjaxAdvanceListAction.action?advanceListType=" + reportType,
			handleAs : "json",
			handle : function(data, args) {
				if (typeof data == "error") {
					//if errors, do not pursue the effect of call!
					console.warn("error!", args);
				} else {
					updateResultGrid(data.response);
					dijit.byId('gridNode').resize();
				}
			}
		});
		return false;
	}

	function formatAdvanceIdGridLink(value, rowIndex) {
	  	return "<a href=AdvanceAction.action?advanceMasterId=" + this.grid.getItem(rowIndex).advmIdentifier + ">" + value + "</a>";
	}			
</script>
<span style="position: relative; top: 20px"><jsp:include
		page="empHeaderInfo.jsp"></jsp:include></span>
<div id="advSelectCheckBoxes"
	style="width: 98%; height: 10%; position: relative; left: 2px; top: 30px; white-space: nowrap;">
	<fieldset
		style="position: relative; border: solid 1px; top: 1px; left: 2px; height: 37px; width: 420px">
		<legend style="color: black">&nbsp;Type</legend>
		<s:form theme="simple">
			<table>
				<tr>
					<td><s:radio name="advCheckBox"
							list="#{'A':'Advances Only','AA':'Advances Adjustments Only','B':'Both'}"
							value="%{'B'}" cssStyle="margin-left:10"
							onclick="filterRows(this)" />
					</td>
				</tr>
			</table>
		</s:form>
	</fieldset>
</div>



<div>
	<table id="gridNode" jsId="gridNode" dojoType="dojox.grid.EnhancedGrid"
		query="{ adevIdentifier : '*' }" plugins="{ filter:{ruleCount: 0}}"
		rowsPerPage="20" model="jsonStore"
		style="width: 98%; height: 30%; position: relative; top: 30px">


		<thead>
			<tr>
				<th field="adevIdentifier" width="6%"
					formatter="formatAdvanceIdGridLink"><center>Advance
						ID</center></th>
				<th field="requestDate" width="8%"><center>Req. Date</center></th>
				<th field="paidPpeDate" width="8%"><center>Paid Date</center></th>
				<th field="OrigPaidDate" width="8%"><center>Original
						Paid Date</center></th>
				<th field="shortReason" width="13%"><center>Reason</center></th>
				<th field="fromDate" width="7%"><center>Advance From</center></th>
				<th field="toDate" width="7%"><center>Advance To</center></th>
				<th field="dollarAmount" width="10%"
					formatter="rightAlignGridCellNumber"><center>Amount
						Requested($)</center></th>
				<th field="amountOutStanding" width="11%"
					formatter="rightAlignGridCellNumber"><center>Amount
						Outstanding($)</center></th>
				<th field="revisionNumber" width="4%" formatter="formatGridLink"><center>Rev.
						No.</center></th>
				<th field="actionCode" width="5%"><center>Last Action</center>
				</th>
				<th field="delete_link" width="5%" formatter="formatGridLink"><center>Delete</center>
				</th>
			</tr>
		</thead>
	</table>
	<br>
</div>
<div style="position: relative; top: 30px">
	<s:form theme="simple" action="AdvanceCreateAction">
		<s:submit value="Create New Advance" disabled="%{eligibleFlag}">
		</s:submit>



	</s:form>
</div>
<br>






