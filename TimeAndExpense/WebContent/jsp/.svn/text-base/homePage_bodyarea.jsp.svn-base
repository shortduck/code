<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
<script type="text/javascript">
	dojo.require("dojox.grid.EnhancedGrid");
	dojo.require("dojo.parser");
	dojo.require("dojox.data.QueryReadStore");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.Dialog");
	dojo.require("dijit.form.Button");
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dojox.grid.DataGrid");
	dojo.require("dijit.dijit");
</script>            


<script type="text/javascript">
		var store = null;
		dojo.addOnLoad(function() {
		
		dojo.xhrPost({
			url: 'AjaxNewsBulletinListAction.action',
			handleAs: "json",
			handle: function(data,args){
				if(typeof data == "error"){
					//if errors, do not pursue the effect of call!
					console.warn("error!",args);
				}else{				
					var gridItems = dojo.fromJson(data.response);
					resultsGridStore = 
						new dojo.data.ItemFileReadStore({data: {identifier: 'description',items: gridItems.newList}});
					var firstTime = gridItems.firstTimeFlag;	
					if(firstTime == "true")
					{				
						authDialog();
					}	
					dijit.byId('newsItemsGrid').setStore(resultsGridStore);
					
				}
			}
		});
		});

	function authDialog(){
		dojo.cookie('statewideApprovalsDeptSelectEmpPref', null, {path: '/', expires: -1});
		dojo.cookie('statewideApprovalsAgySelectEmpPref', null, {path: '/', expires: -1});
		dojo.cookie('statewideApprovalsTkuSelectEmpPref', null, {path: '/', expires: -1});
		dojo.cookie('statewideApprovalsEmpIdSelectEmpPref', null, {path: '/', expires: -1});
		dojo.cookie('statewideApprovalsLastNameSelectEmpPref', null, {path: '/', expires: -1});
		dojo.cookie("statewideApprovalsFilterPref", null, {path: '/', expires: -1});
		dojo.cookie('managerApprovalsSelectEmpPref', null, {path: '/', expires: -1});
		dojo.cookie("managerApprovalsFilterPref", null, {path: '/', expires: -1});
		dijit.byId('authorizationDialog').show();
		dojo.byId('okBtn').focus();
	}

	function okClick()
	{	
		dijit.byId('authorizationDialog').hide();
	}

	function cancelClick()
	{	
		dijit.byId('authorizationDialog').hide();
		window.location.href='jsp/logout.jsp';
	}
		
		
</script>
<!-- User information div -->
<br>
<div>
	<!-- News Bulletin Grid -->
	<h1 style="margin-left: 250px; position: relative; top: 40px">
		<b>Expense Bulletin</b>
	</h1>
	<table id="newsItemsGrid" jsId="grid1"
		dojoType="dojox.grid.EnhancedGrid" query="{ startDate: '*' }"
		rowsPerPage="5" model="jsonStore"
		style="position: relative; top: 50; width: 752px; height: 30%">
		<thead>
			<tr>
				<th field="startDate" width="15%">Start Date</th>
				<th field="description" width="85%">Description</th>
			</tr>
		</thead>
	</table>
</div>
<div dojoType="dijit.Dialog" id="authorizationDialog"
	style="display: none; border: 1px; border-bottom-color: black; border-left-color: black; border-right-color: black; border-top-color: black; background: white"
	title="<b>I certify all expense reimbursement requests submitted or <br>approved within this application were incurred in the discharge of <br>authorized official business and represent proper charges.</b>">
	<table align="center">
		<tr>
			<td width="30%"><button dojoType="dijit.form.Button"
					style="text-align: right;" id="okBtn" onclick="okClick();">Accept</button>
			</td>
			<td width="40%">&nbsp;</td>
			<td width="30%"><button dojoType="dijit.form.Button"
					style="text-align: right;" id="cancelBtn" onclick="cancelClick();">Cancel</button>
			</td>
		</tr>
	</table>
</div>



