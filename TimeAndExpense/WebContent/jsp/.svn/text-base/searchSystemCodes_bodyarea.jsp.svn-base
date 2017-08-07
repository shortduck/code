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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/systemCode.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
<script language="JavaScript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/enGrid.css"></link>



<!-- JS to render drop downs -->
<style type="text/css">
/*.EnhancedGrid {
	position: absolute;
	left: 0;
	top: -20;
	background-color: 272603;
	border: 1px solid gray;
	max-width: 450px;
	font-size: smaller;
	width: 150px;
	text-align: left;
}*/
</style>


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
<script>
 
   

</script>

<script type="text/javascript">


var data1 = {
	      identifier: 'systemCode',
	      items: []
	    };


 resultGridStore = null;
 emptyGridStore = new dojo.data.ItemFileReadStore({data: {identifier: 'systemCode startDate', items: [] }});

dojo.addOnLoad(function(){
		
		initSystemCodeVal();
		var serverResponse = dojo.fromJson(dojo.byId('jsonResponse_hidden').value);
		 accessVal =serverResponse.writeAccess;
		
	  //  accessTopage(accessVal);
	  	 	initResultsGrid();
	  	 	//document.createDiv.newCode.submitBtn.disabled=true;
	  	
	  	 	
		               // Highlights the department selected.  kp. 
	});

function initResultsGrid(){
 resultGridStore = emptyGridStore;
	 dijit.byId('resultGrid').setStore(resultGridStore);
}

 

 // submit search 
function validateAndSubmit(obj)
{
	//var x= dijit.byId("systemCodespk").displayedValue;
	//alert(obj.id);
	dojo.xhrPost({
		url: 'AjaxfindSearchResults.action?searchCode='+syscode_cb.displayedValue, 
	 	handleAs: "json",
		handle: function(data,args){
		//	disableFormFields(false);
			if(typeof data == "error"){
				//if errors, do not pursue the effect of call!
				console.warn("error!",args);
			}else{
	 			updateResultGrid(data.response);
				//selectSystemCode();	  // Highlights the department selected.  kp.
				//callbackMethod(data); 
			}
		}
	});

}

 


function selectSystemCode(){
//	dojo.byId('sysCode').focus();
	//dojo.byId('departments').select();
}
function disableFormFields(_disable) {
	//   alert (dojo.byId('sy_searchBtn').value);
		var searchBtn = dojo.byId('sy_searchBtn');
		if(_disable){
			// disable the search button and modify text
			searchBtn.value = 'Searching...';
			searchBtn.disabled = true;
			disableFields(true);
			dijit.byId('resultGrid').showMessage("Loading... Please wait");
		}else{
			// re-enable the disabled search button and revert original text
			searchBtn.value = 'Search';
			searchBtn.disabled = false;
			disableFields(false);
		}
	} 

function disableFields(state){
	if(syscode_cb != null) syscode_cb.attr('disabled', state);
	 
}
// display results
function updateResultGrid(data){
	var storeToUse;
	//convertNullFieldsToEmptyStrings(data);
	editButton = "Edit";
	if(data.length <1){
		displayValidationErrorMsg('No results found');
		storeToUse = emptyGridStore;
	}else{
		
	 dojo.forEach(data, function(item){
	    item.edit = "<a href=systemCodeEditAction.action?SystemCodeEId="+ item.systemCode +"\">"+"Edit"+"</a>";
		if(item.systemCode == undefined)item.systemCode='';
		if(item.startDate == undefined)item.startDate='';
		if(item.endDate == undefined) item.endDate='';
		if(item.value == undefined) item.value='';
		if(item.description == undefined) item.description='';
		 
		storeToUse = new dojo.data.ItemFileReadStore(
				{data: {identifier: 'systemCode',
					items: data}
				}
			);
		 
	}); 
	}
	resultGridStore = storeToUse;
	createStoreAndpopulateresultGrid(data)
	
	dijit.byId('resultGrid').setStore(resultGridStore);
	dijit.byId('resultGrid').noDataMessage = "No Data Found.. Please modify your search criteria";
	
}

/**
 * Display the values in Select 
 */
function createStoreAndpopulateresultGrid(data){
	var pp ='systemCode startDate';
	if(data !=null)
		{
	resultGridStore = new dojo.data.ItemFileReadStore(
						{data: {identifier: pp.value,
							items: data}
						}
					);
	dijit.byId('resultGrid').setStore(resultGridStore);
	resultGridStore.comparatorMap = {
				'startDate': compareDates_MMDDYYYY_Format,
				'endDate': compareDates_MMDDYYYY_Format
				 
	};	
		}
}

var layout = [ 
              {field: "systemCode", datatype: "string" },
      	    {field: "startDate", datatype: "date" ,autoComplete: true},
      	    {field: "endDate", datatype: "date" ,autoComplete: true },
      	    {field: "Value", datatype: "string" },
      	    {field: "Description", datatype: "string" },
      	  {field: "Edit", datatype: "string" }
            ];


var mystructure = [{
	  cells:[
        {field: "System Code", datatype: "string" },
	    {field: "Start Date", datatype: "date" ,autoComplete: true},
	    {field: "End Date", datatype: "date",autoComplete: true  },
	    {field: "Value", datatype: "string" },
	    {field: "Description", datatype: "string" },
	    {field: "Edit", datatype: "string" }
	    
	          ]
	          
	         
	}];

	var mystore = new dojo.data.ItemFileWriteStore({data1: data1});
 
</script>
<form id="systemCodeSearchForm"
	onsubmit="validateAndSubmit(this);return false;">

	<br> <br>
	<table border="0" id="selectSysCode" width="400" align="center">

		<tr>
			<td align="center"><label> System Code :&nbsp;</label></td>
			<td>&nbsp;</td>
			<td><select id="sysCode" name="chosenSysCode"></select>
			</td>
			<td><input id="sy_searchBtn" type="submit" value="Search">
			</td>
		</tr>
	</table>
	<br>
	<br> <span style="color: black; font-weight: bold; font-size: 9pt">Search
		Results</span>

	<!--<table id="resultGrid" dojotype="dojox.grid.EnhancedGrid" query="{systemCode : '*'}" store="resultGridStore"    selectionMode="none" style="height:25px">-->
	<table id="resultGrid" jsId="resultGrid"
		dojoType="dojox.grid.EnhancedGrid" query="{ systemCode : '*' }"
		rowsPerPage="20" model="jsonStore"
		style="width: 96%; height: 30%; position: relative; top: 30px"
		strucure="mystructure" store="mystore" plugins="{ filter:{ruleCount: 0}}
		 ">

		<thead>
			<tr>
				<th field="systemCode" width="10%"><em>System Code</em>
				</th>
				<th field="startDate" width="10%"><em>Start Date</em>
				</th>
				<th field="endDate" width="10%"><em>End Date</em>
				</th>
				<th field="value" width="15%"><em>Value</em>
				</th>
				<th field="description" width="47%"><em>Description</em>
				</th>
				<th field="edit" width="8%" formatter="formatGridLink">Edit</th>
			</tr>
		</thead>
	</table>
	<br>
</form>
<div style="position: relative; top: 30px" id="createDiv">
	<s:form theme="simple" action="NewSystemCode" id="newCode">
		<s:submit align="center" id="submitBtn" value="Create New System Code"
			disabled="%{writeAccess}">
		</s:submit>

	</s:form>
	<div style="display: none" id="errorMsg"
		style="font-style:italic; font-size:9pt; color:red"></div>
</div>



<div style="display: none"></div>
<div style="display: none">
	<s:textfield id="jsonResponse_hidden" theme="simple"
		value="%{jsonResponse}" />
	<s:textfield id="systemCodeListVal"
		value="%{#session.systemCodeListVal}" />
	<!-- 		<s:textfield id="module_hidden" theme="simple" value="%{moduleId}" />-->
</div>
