<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="gov.michigan.dit.timeexpense.util.IConstants"%>

<html>
<head>
<title>logout</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="/jsp/dojoInclude.jsp"></jsp:include>
<% 
String s=(String)session.getAttribute(IConstants.NO_DCDS_USER_ID);
if(s != null && s.equalsIgnoreCase("true")){
%>
<script type="text/javascript">    
 alert("DCDS User id does not exist ");    
</script>
<%  
}  
%>
<script>
dojo.require("dojo.cookie");
function logout (){
dojo.cookie('statewideApprovalsFilterPref', '', {expires: -1, path: '/'});
dojo.cookie('managerApprovalsFilterPref', '', {expires: -1, path: '/'});
dojo.cookie("statewideselectEmployeeFilterPref", '', {expires: -1, path: '/'});
dojo.cookie("managerselectEmployeeFilterPref", '', {expires: -1, path: '/'});
dojo.cookie('statewideApprovalsFilterRelationPref', '', {expires: -1, path: '/'});
dojo.cookie('managerApprovalsFilterRelationPref', '', {expires: -1, path: '/'});
dojo.cookie("statewideselectEmployeeFilterRelationPref", '', {expires: -1, path: '/'});
dojo.cookie("managerselectEmployeeFilterRelationPref", '', {expires: -1, path: '/'});
//alert ("You MUST close all browser windows to LOGOUT");
document.getElementById('server_logout_form').submit();
//window.close();
}

</script>

</head>
<body onload="logout();">
<%session.invalidate(); %>
<form id='server_logout_form' action="ibm_security_logout" method="post">
<input type="submit" name="logout" value="Logout">
<INPUT TYPE="hidden" name="logoutExitPage" VALUE="/" >
</form>
</body>
</html>
