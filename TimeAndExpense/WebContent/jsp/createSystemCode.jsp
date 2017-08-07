<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@taglib
	uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%><tiles:insertDefinition
	name="dynamicMainPageTemplate">
	<tiles:putAttribute name="documentTitle" value="System Codes Search"
		type="string"></tiles:putAttribute>
	<tiles:putAttribute name="bodyarea"
		value="/jsp/createSystemCode_bodyarea.jsp" type="template"></tiles:putAttribute>
</tiles:insertDefinition>