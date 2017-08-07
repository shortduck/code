<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
	
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/jscal2.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/win2k/win2k.css" /> 

<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/lang/en.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/AdvanceCodingBlock.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/js/codingBlockCommon.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/advance.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/multipleAppointments.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/codingBlockValidation.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/enGrid.css"/>
 

<!-- JS to render advance tab control and grid -->
<script type="text/javascript">
	dojo.require("dojox.grid.EnhancedGrid");
	dojo.require("dojox.grid.enhanced.plugins.Filter");
	dojo.require("dojo.parser");
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dojox.grid.DataGrid");
	dojo.require("dojo.data.ItemFileReadStore");
</script>
  <style>
	#dialog1_underlay {
   		background: gray;
		opacity: 0.7;
    }
     #advancecomments {
     width:300px;
     height:150px;
     border-style:solid;
     border-color:black;
     border-width: 1; 
     background: white"
     
     }
  </style> 
<!-- change color of tooltip -->
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

.count{color:gray; font-size:9pt}

</style>
<script type="text/javascript">
	
	  var errorGridStore;
  
    	dojo.addOnLoad(function(){
    	initSingleDateCalendar("requestDate", "requestDateSelector");
    	initTwoSingleDateCalendars("fromDate", "fromDateSelector", "toDate", "toDateSelector");
		createTable();
		initializeForm();
		populateErrorsGrid();
		window.onbeforeunload = checkFormState;
		// trimming values for popup text areas to remove spaces after initial load
		dijit.byId('commentsAfterModify').attr('value', '');
		dijit.byId('advancecomments').attr('value', '');
		dijit.byId('rejectComments').attr('value', '');
		
		// attach event handlers to counter fields
		processTextFieldCounters();
		
	});
	
 

	function updateErrorsGrid(errors){
		errorGridStore = new dojo.data.ItemFileReadStore(
								{data: {identifier: 'id',items: errors}}
						);
		dijit.byId('errorGrid').setStore(errorGridStore);		
	}

	// Reads errors and pushes them to the grid 
	function populateErrorsGrid(){
		//var advanceErrJson = dojo.byId('errorsJsonAdvance').value;
		var advanceErrJson = dojo.byId('bizErrors').value;
	
	  	if(advanceErrJson != null && advanceErrJson != ""){
			var advErr = dojo.fromJson(advanceErrJson);
	 		if(advErr != null){
	 			updateErrorsGrid(advErr);
	 		}
		}
	};
			
			function loadHistory() {
				/*dojo.xhrPost({
					url: 'AdvanceHistoryAction.action',
					handleAs: "json",
					handle: function(data,args){
						if(typeof data == "error"){
							//if errors, do not pursue the effect of call!
							console.warn("error!",args);
						}else{
							var gridItems = dojo.fromJson(data.response);
							dojo.byId("advanceDisplayStatus").innerHTML = gridItems.status;
							if (gridItems.treqEventId != '0'){
								dojo.byId("treqEventId").text = gridItems.treqEventId;
								dojo.byId("treqEventId").href = dojo.byId("treqEventId").href + gridItems.treqEventId;
							}
							
							resultsGridStore = 
								new dojo.data.ItemFileReadStore({data: {items: gridItems.history}});
							//dijit.byId('grid1').setStore(resultsGridStore);
							dojo.forEach(gridItems.history, function(item){
								if(item.revisionNumber == undefined) item.revisionNumber='';
								if(item.actionCode == undefined) item.actionCode='';
								if(item.comments == undefined) item.comments='';
								if(item.modifiedUserId == undefined) item.modifiedUserId='';
								if(item.modifiedDateDisplay == undefined) item.modifiedDateDisplay='';
							});
							dijit.byId('historyGrid').setStore(resultsGridStore);
							dojo.connect(dijit.byId('historyGrid'),'onCellMouseOver', 'showToolTip');
							dojo.connect(dijit.byId('historyGrid'),'onCellMouseOut', 'hideToolTip');
						}
								}
				});*/
				var jsonResponseStr = dojo.byId('jsonResponse_HistoryHidden').value;
				var gridItems = eval('(' + jsonResponseStr + ')');
				var gridItems = dojo.fromJson(gridItems);				
				var resultsGridStore = new dojo.data.ItemFileReadStore(
									{data: {items: gridItems}
									}
								);
								
				dojo.byId("advanceDisplayStatus").innerHTML = gridItems.status;
				/*if (gridItems.treqEventId != '0'){
					dojo.byId('spanTreqEventId').style.display = "";
										dojo.byId("treqEventId").text = gridItems.treqEventId;
										dojo.byId("treqEventId").href = dojo.byId("treqEventId").href + gridItems.treqEventId;
									}*/
				var treqId = dojo.byId(treqEventId).innerHTML;
				if (parseInt(treqId ) <= 0)
					dojo.byId('spanTreqEventId').style.display = "none";
									
									resultsGridStore = 
										new dojo.data.ItemFileReadStore({data: {items: gridItems.history}});
									//dijit.byId('grid1').setStore(resultsGridStore);
									dojo.forEach(gridItems.history, function(item){
										if(item.revisionNumber == undefined) item.revisionNumber='';
										if(item.actionCode == undefined) item.actionCode='';
										if(item.comments == undefined) item.comments='';
										if(item.modifiedUserId == undefined) item.modifiedUserId='';
										if(item.modifiedDateDisplay == undefined) item.modifiedDateDisplay='';
									});
									dijit.byId('historyGrid').setStore(resultsGridStore);
									dojo.connect(dijit.byId('historyGrid'),'onCellMouseOver', 'showToolTip');
									dojo.connect(dijit.byId('historyGrid'),'onCellMouseOut', 'hideToolTip');
				}
				
	function showToolTip(event){
		
		var historyGrid =  dijit.byId('historyGrid');
		var comments = historyGrid._by_idx[event.rowIndex].item.comments; 
		if(event.cellIndex==2){
			if (comments != undefined && trimStr(comments[0]) != ""){
				var msg = comments;			
    			dijit.showTooltip(msg, event.cellNode);
	   		}
		}
	}

	function hideToolTip (e) {
		dijit.hideTooltip(e.cellNode);
	}

		function enableSave () {
    		var saveButton = document.getElementById("save");
    	saveButton.disabled = false;
		}
				
				
</script>
<!--  advance tab control -->
<span> <span style="margin-right: 30px"><s:label>Advance ID: </s:label>
		<span id="adevIdentifier"><s:property value="advanceMaster.adevIdentifier.adevIdentifier" /></span></span> <jsp:include page="empHeaderInfo.jsp"></jsp:include></span>
<br>

<div id="advance_tab_container" class="soria"
	dojoType="dijit.layout.TabContainer" style="width: 98%; height: 430px;"
	doLayout="true"  controllerWidget="dijit.layout.TabController">

	<!-- advances first tab : "Advance" -->
	<div id="advanceTab" dojoType="dijit.layout.ContentPane"
		title="Advance">
		<span><jsp:include page="advance.jsp"></jsp:include></span>
	</div>

	<!-- advance second tab : "History" -->
	<div dojoType="dijit.layout.ContentPane" title="History"
		onload="loadHistory" id="historyTab"
		href="AdvanceHistoryAction.action" refreshOnShow="true"></div>

</div>

<br>

<div id="errorGridContainer">
	<jsp:include page="errorGrid.jsp"></jsp:include>
</div>
<!--  
<script type="text/javascript">

	// render errors grid 
	dojo.parser.parse(dojo.byId('errorGridContainer'));
	
	populateErrorsGrid();

	/* Reads errors and pushes them to the grid */
	function populateErrorsGrid(){
		var expenseErrJson = dojo.byId('errorsJsonAdvance').value;
	
	  	if(expenseErrJson != null && expenseErrJson != ""){
			var expErr = dojo.fromJson(expenseErrJson);
	 		if(expErr != null){
	 			updateErrorsGrid(expErr);
	 		}
		}
	};


</script>

 -->

