<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@taglib
	uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%><tiles:insertDefinition
	name="dynamicMainPageTemplate">
	<tiles:putAttribute name="documentTitle" value="Transaction Ledger Reports"
		type="string"></tiles:putAttribute>
	<tiles:putAttribute name="bodyarea"
		value="/jsp/viewReports_bodyarea.jsp" type="template"></tiles:putAttribute>
</tiles:insertDefinition>