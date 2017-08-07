<%@ page import="gov.michigan.dit.timeexpense.model.core.TravelReqDetailCodingBlock"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>Travel Requisition Coding Block</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<Script>
/*
Display a message indicating no coding blocks exist if there are no rows
*/
function checkRows (){
var numRows = document.getElementById("treqCodingBlocks").rows.length;
if (numRows == 0){
	document.getElementById('noRowsMessage').style.display = '';
	}
}

</Script>
</head>
<body onload="checkRows();">

<H2>Travel Requisition Coding Block</H2>

<table border="1" id="treqCodingBlocks">
<th>PCT</th>
<th>AY</th>
<th>Index</th>
<th>PCA</th>
<th>Grant</th>
<th>Ph</th>
<th>AG1</th>
<th>Project</th>
<th>Ph</th>
<th>AG2</th>
<th>AG3</th>
<th>Multi</th>
<th>Std</th>
<s:iterator value="treqCbList" status="treqCbListStatus">
  <tr>
  	  <td><s:property value="percentDisplay" /></td>
  	  <td><s:property value="appropriationYear"/></td>
  	  <td><s:property value="indexCode"/></td>
  	  <td><s:property value="pca"/></td>
  	  <td><s:property value="grantNumber"/></td>
  	  <td><s:property value="grantPhase"/></td>
  	  <td><s:property value="agencyCode1"/></td>
  	  <td><s:property value="projectNumber"/></td>
  	  <td><s:property value="projectPhase"/></td>
  	  <td><s:property value="agencyCode2"/></td>
  	  <td><s:property value="agencyCode3"/></td>
  	  <td><s:property value="multipurposeCode"/></td>
  	  <td><s:property value="standardInd"/></td>
  </tr>
</s:iterator>
</table>
<div id="noRowsMessage" style="display:none">
<br>
<br>
<H3>There are no Requisition Coding Blocks associated with this request </H3>
</div>

</body>
</html>