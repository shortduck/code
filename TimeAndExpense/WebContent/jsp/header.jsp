<%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
<%@ page import="gov.michigan.dit.timeexpense.model.core.UserProfile" %>
<style>
.box {
    display:;
    filter: alpha(opacity=30);
    text-align:center;
    filter:progid:DXImageTransform.Microsoft.Alpha(opacity=30);
    -moz-opacity: .30;
    -khtml-opacity: 0.3;
    opacity: 0.3;
    background-color:black;
    position:fixed;
    top:0px;
    left:0px;
    width:100%;
    height:100%;
    text-align:center;
    vertical-align:middle;    
    color: #FFFFFF;
    font:  bold 20px arial,serif
}
</style>
<!-- This will prevent duplicate tabs issue -->
<script type="text/javascript">
 if (window.name == "" ){
 	alert("It appears that you are running multiple sessions of Time & Expense. Please close all sessions except the first. Continuing with multiple sessions may result in unexpected data manipulation.");
}	
	
</script>

<form name="invalidwindow" id="invalidwindow" action="login" method="post">
</form>


<div id="header"> 

<img border="0" style="width: 100%"
	src="${pageContext.request.contextPath}/image/time-expense.jpg"/>
</div>	
<div id = "welcome" style="position:absolute;right:3%">
Welcome
<b>
<s:property value="%{#session.User.userId}" />
</b>
<a href="${pageContext.request.contextPath}/jsp/logout.jsp">[logout]</a>
</div>
<div dojoType="dijit.Dialog" id="dialogSessionContinueLogout" title="<b>Your Session is about to expire in 5 minutes.<br> Please click Continue to refresh the current page or Logout.</b>" 
		style="border: 1px ; background: white; display: none">
    <table>
       <button dojoType=dijit.form.Button id="btnContinue" onclick="continueClick();">Continue</button>
       <button dojoType=dijit.form.Button id="btnLogout" onclick="logoutClick();">Logout</button>
    </table>
</div>


