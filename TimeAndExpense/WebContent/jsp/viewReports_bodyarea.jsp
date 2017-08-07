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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>

<script language="JavaScript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/report_common.js"></script>
<!-- JS to render drop downs -->
<script type="text/javascript">
	dojo.require("dojo.parser");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dijit.Dialog");
</script>
<script type="text/javascript">
function checkRows (){
var numRows = document.getElementById("treqCodingBlocks").rows.length;
if (numRows == 0){
	document.getElementById('noRowsMessage').style.display = '';
	}
}

</script>
<div>
<br>
<br>
<H2><B>
<font color="red">
The PDF reports will be available online for a 2 week period only. Please save to local computer if needed
for longer periods.
</font>
</B>
</H2>
</div>

<div "reports" style="margin-top: 50">	
<table border="1" id="treqCodingBlocks" cellspacing="5" cellpadding="10" >
<th><b>Completed Reports</b></th>
<th><b>Submit Time</b></th>
<th><b>Completed Time</b></th>
<s:iterator value="reportsList" status="reportsList">
  <tr>
 <s:if test"%{reportFilePath != null}">
  <td> <a id="1"  href="${pageContext.request.contextPath}/jsp/report/finishedReports/<s:property value='reportFilePath'/>" target="_blank"><s:property value="reportName"/></a> </td>
    </s:if>
    
    <s:else>
        <td>   <s:property value="reportName"/> </td>
    </s:else>
<!--   	  <td> <a id="1"  href="${pageContext.request.contextPath}/jsp/report/finishedReports/<s:property value='reportFilePath'/>" ><s:property value="reportName"/></a> </td>  -->  

  	   <td>
  	  <s:property value="requesDateTime" />
  	   </td>  	 
  	   
  	     <td>
  	  <s:property value="completedDateTime" />
  	   </td>  	   
  </tr>
</s:iterator>

</table>
<BR/>
<FORM>
<INPUT TYPE="button" onClick="history.go(0)" VALUE="Refresh">
</FORM>
</div>


