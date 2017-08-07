<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
	<%@page import="gov.michigan.dit.timeexpense.util.IConstants,gov.michigan.dit.timeexpense.model.core.*" %>
<html>
<head>
<title>ExpenseSummaryPage</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/dojo-1.2.3/dojo/dojo.js" djconfig="parseOnLoad:true, isDebug:false"></script>
<script type="text/javascript">
	dojo.require("dojo.parser");
	dojo.require("dojox.grid.DataGrid");
	dojo.require("dojo.data.ItemFileReadStore");
</script>

<script language="javascript">
	function getExpenseMasterId(){
		alert("Here");
		int expmId = 14;
		//ExpenseMasters expenseMasters= (ExpenseMasters)session.getAttribute(IConstants.CURR_EXPENSEMASTER);
		window.open("ExpenseSummaryReport-viewer.jsp?expmID="+expmId);
	}
	

function submitUsingAjax(){
	dojo.xhrPost({
			url: 'ExpenseSummaryReportAction.action',
			handleAs: "text",
			handle: function(data,args){
				if(typeof data == "error"){
					//if errors, do not pursue the effect of call!
					console.warn("error!",args);
				}else{
					alert(data);
					//window.open('ExpenseDetailReport-viewer.jsp?expmID='"+expmId+"')"
				}
			}
		});
}
</script>
</head>
<body>
<table cellspacing=0 cellpadding=0 class="clCaption" border=0>
 		  <tr>
		    <td align="left" style="padding-left:8;padding-top:10;width:160" nowrap><sup>&nbsp;</sup>Expense Master Id:</td>
		  	<td style="padding-left:40;" width="100px"><textarea id="expmID" rows="1" cols="10" style="width:100px;"  style="white-space: pre-wrap;" ></textarea></td>
		  </tr>
</table>
<button value="summary" style="width:110px" onClick="getExpenseMasterId()">Print Summary</button>
<!--<s:form action="ExpenseSummaryReportAction.action">
<s:submit value="summary" onclick="window.open('ExpenseSummaryReport-viewer.jsp?expmID='+expmId+')"></s:submit>
</s:form>
-->
<button value="detail" style="width:110px" onclick="window.open('ExpenseDetailReport-viewer.jsp?expmID='"+expmId+"')">Print Detail</button>
</body>
</html>