<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
	
	
		<link
			href="${pageContext.request.contextPath}/css/calendar-win2k-1.css"
			rel="stylesheet" type="text/css">
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/enGrid.css"></link>			

<style type="text/css">
.soria .dijitTooltipContainer {
position: absolute;
left: 0;
top: -20;
background-color: #F9F7BA;
border:1px solid gray;
max-width:450px;
font-size:smaller;
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
			dijit.byId("resultGrid").showMessage("Loading ... Please wait");
			displayRequisitionList();
		});
		
	function displayRequisitionList(){
			dojo.xhrPost({
			url: 'AjaxTravelRequisitionListAction.action',
			handleAs: "json",
			handle: function(data,args){
				if(typeof data == "error"){
					//if errors, do not pursue the effect of call!
					console.warn("error!",args);
				}else{
					updateResultGrid(data.response);
					dijit.byId('resultGrid').resize();
				}
			}
		});
	}
	resultsGridStore = null;
	// function to display the grid
	function updateResultGrid(data){
	
		//convertNullFieldsToEmptyStrings(data);
		deleteButton = "Delete";
		
		dojo.forEach(data, function(item){
			if(item.actionCode == undefined)item.actionCode='';
			if(item.natureOfBusiness == undefined)item.natureOfBusiness='';
			if(item.treqDateFrom == undefined) item.treqDateFrom='';
			if(item.treqDateTo == undefined) item.treqDateTo='';
			if(item.paidDate == undefined) item.paidDate='';
			if(item.travelInd == undefined) item.travelInd='';
			if(item.outOfStateInd == undefined) item.outOfStateInd='';
			if(item.revisionNumber == undefined) item.revisionNumber='';
			
			item.s_deleteFlag = '';
			if(item.deleteFlag == "Y" && item.revisionNumber == 0){
				item.s_deleteFlag = "<a href=TravelRequisitionDeleteAction.action?treqEventId=" + item.treqeIdentifier + " onclick=\"return confirm('Are you sure you want to delete this Travel Requisition?')\">" + "Delete" + "</a>";			    			    
			}
			if(item.natureOfBusiness!=undefined){}
				item.shortComments = item.natureOfBusiness.substring(0,60); 
		});
		dijit.byId('resultGrid').noDataMessage = "No Data Found.. Please modify your search criteria";
		createStoreAndpopulateResultsGrid(data);
		dojo.connect(dijit.byId('resultGrid'),'onCellMouseOver', 'showToolTip');
		dojo.connect(dijit.byId('resultGrid'),'onCellMouseOut', 'hideToolTip');
	}
	
	function formatTreqIdGridLink(value, rowIndex) {
		return "<a href=TravelRequisitionAction.action?treqMasterId=" + this.grid.getItem(rowIndex).treqmIdentifier + ">"+value+"</a>";
	}
	
	function showToolTip(event){
	
				var expenseGrid =  dijit.byId('resultGrid');
				var natureOfBusiness = expenseGrid._by_idx[event.rowIndex].item.natureOfBusiness; 
				if(event.cellIndex==4){
					if (natureOfBusiness != undefined){
						var msg = natureOfBusiness;			
		    			dijit.showTooltip(msg, event.cellNode);
			   		}
				}
	}
       
    function hideToolTip (e) {
        dijit.hideTooltip(e.cellNode);
	}
	
	function createStoreAndpopulateResultsGrid(data){
		resultsGridStore = new dojo.data.ItemFileReadStore(
							{data: {identifier: 'treqeIdentifier',
								items: data}
							}
						);
				
		resultsGridStore.comparatorMap = {
   				'treqDateFrom': compareDates_MMDDYYYY_Format,
   				'treqDateTo': compareDates_MMDDYYYY_Format
		};
		dijit.byId('resultGrid').setStore(resultsGridStore);
	}
	
	function createNewExpense(){
		
	}
</script>
<span style="position: relative; top: 10px"><jsp:include
		page="../empHeaderInfo.jsp"></jsp:include></span>

<table class="soria" id="resultGrid" dojotype="dojox.grid.EnhancedGrid"
	query="{treqeIdentifier: '*' }" rowsPerPage="20"
	plugins="{filter : true}" store="resultsGridStore"
	selectionMode="single"
	style="width: 98%; height: 30%; position: relative; top: 30px; white-space: nowrap; overflow: hidden">
	<thead>
		<tr>
			<th field="treqeIdentifier" width="6%"
				formatter="formatTreqIdGridLink" styles:="" text-align:="" center="">
				<center>Requisition ID</center></th>
				
			<th field="treqDateFrom" width="10%" text-align:="" center="">
				<div align="center">Request Date</div></th>
				
			<th field="treqDateFrom" width="10%">
				<center>Start Date</center></th>
				
			<th field="treqDateTo" width="10%">
				<center>End Date</center></th>
				
			<th field="shortComments" width="45%">
				<center>Nature of Official Business</center></th>
				
			<th field="outOfStateInd" width="5%">
				<center>Out-of-State</center></th>
				
			<th field="revisionNumber" width="5%">
				<center>Revision Number</center></th>
				
			<th field="actionCode" width="5%">Last Action</th>
			
			<th field="s_deleteFlag" width="4%" formatter="formatGridLink">
			
			<center>Delete</center></th>
		</tr>
	</thead>
</table>
<br>
<div style="position: relative; top: 30px">
	<s:form theme="simple"
		action="setTravelRequisitionUserSubjectInSession">
		<s:submit value="Create New Travel Requisition"
			disabled="%{disableCreateButton}"></s:submit>
		<!-- %{enableCreateButton} -->
	</s:form>
</div>
