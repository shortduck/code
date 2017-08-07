<!-- This JSP serves up the navigation trail for the application. The default navigation trail from RAD
has been modified for:

1) There are no hyperlinks in the breadcrmbs. The trail is only for informational reasons
2) A link to previous page has been added at the end of the nav trail. The users will use this link instead of the back
button to conform with HRMN training, i.e. "don't use the back button"

ZH - 04/07/2009

 -->
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="sitenav" type="com.ibm.etools.siteedit.sitelib.core.SiteNavBean" scope="request"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
<title>trail_horizontal</title>
<link rel="stylesheet" type="text/css" href="/TimeAndExpense/theme/horizontal-trail__.css">
</head>
<body>
<div>

<c:out value='${sitenav.start}' escapeXml='false'/>
<table class="htrail2_table">
	<tbody class="htrail2_table_body">
		<tr class="htrail2_table_row">
<c:forEach var="item" items="${sitenav.items}" begin="0" step="1" varStatus="status">

<c:if test="${!status.first}">
<%
//used to determine whether or not to show link to previous page 
String isHomePage = "false";
pageContext.setAttribute("isHomePage", isHomePage);
%>
			
<td class="htrail2_cell_separator"><c:out value='&nbsp;${sitenav.separator}&nbsp;' escapeXml='false'/></td>
</c:if>
<c:if test="${status.first}">
<% 
//used to determine whether or not to show link to previous page
String isHomePage = "true";
pageContext.setAttribute("isHomePage", isHomePage);

%>

</c:if>

<!-- Removed hyperlink from the generated navigation trail -->


			<td class="htrail2_cell_normal">
			<!-- Set module id, used to determine if Select Employee page options necessary.
			The default value for page title set in web site navigation is for Expenses. Here, the 
			title for the specific page is switched if the module id is for Advances-->
			<c:set var = "module" value='<%= (String) request.getParameter("moduleId") %>'/> 
			<c:choose>
						
			<c:when test = "${(module eq 'ADVW002')}">
			<!-- Select Employee - Manager Advances-->
			<c:choose>
			<c:when test="${done eq 'no'}"> 
			<!-- if not already printed, print out the title for advances. This is done exactly once -->
			<c:out value='Select Employee Manager - Advances'  escapeXml='false'/>
			</c:when>
			<c:otherwise>
			<!-- Not advance or already printed, print nav trail item -->
			<c:out value='${item.label}' escapeXml='false'/>
			<c:set var = "done" value = "no"/>
			</c:otherwise>
			</c:choose>
			</c:when>
			
			<c:when test = "${(module eq 'ADVW003')}">
			<!-- Select Employee - Manager Statewide -->
			<c:choose>
			<c:when test="${done eq 'no'}"> 
			<!-- if not already printed, print out the title for advances. This is done exactly once -->
			<c:out value='Select Employee Satewide - Advances'  escapeXml='false'/>
			</c:when>
			<c:otherwise>
			<!-- Not advance or already printed, print nav trail item -->
			<c:out value='${item.label}' escapeXml='false'/>
			<c:set var = "done" value = "no"/>
			</c:otherwise>
			</c:choose>
			</c:when>
			
			<c:when test = "${(module eq 'EXPW002')}">
			<!-- Select Employee - Manager -->
			<c:choose>
			<c:when test="${done eq 'no'}"> 
			<!-- if not already printed, print out the title for advances. This is done exactly once -->
			<c:out value='Select Employee Manager - Expenses'  escapeXml='false'/>
			</c:when>
			<c:otherwise>
			<!-- Not advance or already printed, print nav trail item -->
			<c:out value='${item.label}' escapeXml='false'/>
			<c:set var = "done" value = "no"/>
			</c:otherwise>
			</c:choose>
			</c:when>
			
           <c:when test = "${(module eq 'EXPW003')}">
			<!-- Select Employee - Manager -->
			<c:choose>
			<c:when test="${done eq 'no'}"> 
			<!-- if not already printed, print out the title for advances. This is done exactly once -->
			<c:out value='Select Employee Statewide - Expenses'  escapeXml='false'/>
			</c:when>
			<c:otherwise>
			<!-- Not advance or already printed, print nav trail item -->
			<c:out value='${item.label}' escapeXml='false'/>
			<c:set var = "done" value = "no"/>
			</c:otherwise>
			</c:choose>
			</c:when>
			
			<c:when test = "${(module eq 'NTRW002')}">
			<!-- Select Employee - Manager -->
			<c:choose>
			<c:when test="${done eq 'no'}"> 
			<!-- if not already printed, print out the title for advances. This is done exactly once -->
			<c:out value='Select Employee Manager - Non-Routine Travel Requisitions'  escapeXml='false'/>
			</c:when>
			<c:otherwise>
			<!-- Not Non-routine Travel Requisition or already printed, print nav trail item -->
			<c:out value='${item.label}' escapeXml='false'/>
			<c:set var = "done" value = "no"/>
			</c:otherwise>
			</c:choose>
			</c:when>
			
			<c:when test = "${(module eq 'NTRW003')}">
			<!-- Select Employee - Manager Statewide -->
			<c:choose>
			<c:when test="${done eq 'no'}"> 
			<!-- if not already printed, print out the title for advances. This is done exactly once -->
			<c:out value='Select Employee Satewide - Non-Routine Travel Requisitions'  escapeXml='false'/>
			</c:when>
			<c:otherwise>
			<!-- Not Non-routine Travel Requisition or already printed, print nav trail item -->
			<c:out value='${item.label}' escapeXml='false'/>
			<c:set var = "done" value = "no"/>
			</c:otherwise>
			</c:choose>
			</c:when>
           
           

			
			<c:when test = "${(module eq 'EXPR005') || 
							  (module eq 'EXPR006') ||
							  (module eq 'EXPR007') || 
							  (module eq 'EXPR008')}">
			<!-- Reports - Manager -->
			<c:choose>
			<c:when test="${done eq 'no'}"> 
			<!-- if not already printed, print out the title for advances. This is done exactly once -->
			<c:out value='Reports - Manager' escapeXml='false'/>
			</c:when>
			<c:otherwise>
			<!-- Not advance or already printed, print nav trail item -->
			<c:out value='${item.label}' escapeXml='false'/>
			<c:set var = "done" value = "no"/>
			</c:otherwise>
			</c:choose>
			</c:when>
			<c:when test = "${(module eq 'EXPR009') || 
							  (module eq 'EXPR010') ||
							  (module eq 'EXPR011') || 
							  (module eq 'EXPR012')}">
			<!-- Reports - Manager -->
			<c:choose>
			<c:when test="${done eq 'no'}"> 
			<!-- if not already printed, print out the title for advances. This is done exactly once -->
			<c:out value='Reports - Statewide' escapeXml='false'/>
			</c:when>
			<c:otherwise>
			<!-- Not advance or already printed, print nav trail item -->
			<c:out value='${item.label}' escapeXml='false'/>
			<c:set var = "done" value = "no"/>
			</c:otherwise>
			</c:choose>
			</c:when>
			<c:otherwise>
			<!-- Not advance or already printed, keep printing nav trail items from web site navigation -->
			<c:out value='${item.label}' escapeXml='false'/>
			</c:otherwise>
			</c:choose>
			</td>			
</c:forEach>

<script type="text/javascript">
function invokePreviousAction (){

	dojo.xhrPost({
		url :"PreviousPageAction.action",
		handleAs :"json",
		sync: true,
		load : function(data) {
		}
	});
	previousLinkUsed = true;
	history.back();
}
</script>

<!-- Show previous link on all pages except the home page -->

<c:if test="${isHomePage == 'false'}">
<td style="margin-right:50px;" width="300px" align="center">
			<c:out value='<a href="#" style="text-decoration:none;" onClick="invokePreviousAction();">&lt; Previous Page</a>' escapeXml='false'/>
			</td>
</c:if>
		</tr>
	</tbody>
</table>
<c:out value='${sitenav.end}' escapeXml='false'/>

</div>
</body>
</html>

