
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="sitenav" type="com.ibm.etools.siteedit.sitelib.core.SiteNavBean" scope="request"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<title>trail_horizontal</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/horizontal-trail.css">
</head>
<body>
<div>
<c:out value='${sitenav.start}' escapeXml='false'/>
<table class="htrail_table">
	<tbody class="htrail_table_body">
		<tr class="htrail_table_row">
<c:forEach var="item" items="${sitenav.items}" begin="0" step="1" varStatus="status">
<c:if test="${!status.first}">
			<td class="htrail_cell_separator"><c:out value='${sitenav.separator}' escapeXml='false'/></td>
</c:if>
			<td class="htrail_cell_normal">
			<c:out value='<a href="${item.href}" class="htrail_item_normal">${item.label}</a>' escapeXml='false'/>
			</td>
</c:forEach>
		</tr>
	</tbody>
</table>
<c:out value='${sitenav.end}' escapeXml='false'/>
</div>
</body>
</html>
