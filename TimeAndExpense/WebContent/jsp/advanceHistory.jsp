<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<!-- added to solve display issue with IE8 -->
</table>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<BR>
<table width="100%">
<tr>
	<td align="left" width="80%">
	<B>Status:&nbsp;</B><span id="advanceDisplayStatus"/>
	</td>
	
</tr>
</table>
<BR>

<table class="soria" id="historyGrid" jsId="historyGrid" dojoType="dojox.grid.EnhancedGrid" query="{ revisionNumber: '*' }" 
		rowsPerPage="15" model="store" style="width:100%; height:75%">
	<thead>
		<tr>
			<th field="revisionNumber" width="07%">Version</th>
			<th field="actionCode" width="15%">Action code</th>
			<th field="comments" width="45%">Comments</th>
			<th field="modifiedUserId" width="15%">Action Taken By</th>
			<th field="modifiedDateDisplay" width="18%">Action Date</th>
		</tr>
	</thead>
</table>
<br>
<br>
<br>
<br>

<s:hidden id="jsonResponse_HistoryHidden"  value="%{jsonResponse}" />
</body>
</html>
