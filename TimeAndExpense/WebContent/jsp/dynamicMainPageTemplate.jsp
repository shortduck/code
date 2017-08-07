<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
<%@ page import="gov.michigan.dit.timeexpense.model.core.UserProfile" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>
<head>
<title>
<tiles:getAsString name="documentTitle"></tiles:getAsString>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="PRAGMA" CONTENT="non-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">

<link href="${pageContext.request.contextPath}/css/mainTemplate.css"
	rel="stylesheet" type="text/css" />
<jsp:include page="/jsp/dojoInclude.jsp"></jsp:include>
<script type="text/javascript"	src="${pageContext.request.contextPath}/js/forms.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/js/te_common.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/timer.js"></script>
</head>
<body class="soria mainTemplate">
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}"/>
<div id="container">
<tiles:insertAttribute name="headerarea"></tiles:insertAttribute>
<tiles:insertAttribute name="menuarea"></tiles:insertAttribute>
	<div style="display:none">
	<s:textfield id="validationErrors" value="%{validationErrorsJson}" theme="simple"/> 
	<s:textfield id="bizErrors" value="%{errorsJson}" theme="simple"/>
	<s:textfield id="contactusurl" value="%{#session.URLCNT}"/>
	<s:textfield id="faqurl" value="%{#session.URLFAQ}"/>
	<s:textfield id="trainingurl" value="%{#session.URLTRNG}"/>
	<s:textfield id="moveForward" value="%{moveForward}"/>
</div>
	<div id="mainContent" >
		<siteedit:navtrail target="home,parent,ancestor,self" start=""
		separator="&gt;&gt;" end=""
		spec="/TimeAndExpense/jsp/horizontal-trail.jsp"></siteedit:navtrail>	
		<tiles:insertAttribute name="bodyarea"></tiles:insertAttribute>
	</div>
<p class="clearFloat"/>
	<div style="text-align: center;" id="footer"><tiles:insertAttribute name="footerarea"></tiles:insertAttribute></div>
</div>
</body>
</html>