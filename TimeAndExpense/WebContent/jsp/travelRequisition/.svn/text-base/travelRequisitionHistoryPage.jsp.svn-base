<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<table>
<!-- added to solve display issue with IE8 -->
</table>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="s" uri="/struts-tags"%>
<title>Insert title here</title>

</head>
<body>
<BR>
<table width="100%">
<tr>
	<td align="left">
	<B>Status:&nbsp;</B><s:property value="%{status}"/>
	</td>
</tr>
</table>
<BR>
<table class="soria" id="historyGrid" dojotype="dojox.grid.EnhancedGrid" query="{ treqaIdentifier: '*' }" plugins="{filter: false}" 
rowsPerPage="5" style="position:relative:top:30px;left:10px;width:90%; height:750px">

	<thead>
		<tr>
			<th field="revisionNo" width="07%"><center>Version</center></th>
			<th field="treqActionCode" width="15%"><center>Action Code</center></th>
			<th field="comments" width="45%"><center>Comments</center></th>
			<th field="modifiedUserId" width="15%"><center>Action Taken By</center></th>
			<th field="modifiedDateDisplay" width="18%"><center>Action Date</center></th>
		</tr>
	</thead>
</table>

<s:hidden id="jsonResponse_HistoryHidden"     value="%{jsonResponse}" />
</body>
</html>
