<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<%
// This is required to force no caching in IE
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);
%>


<!-- Included the dojoInclude.jsp as a part of Dojo 1.6.1 upgrade : KP du --> 
<jsp:include page="/jsp/dojoInclude.jsp"></jsp:include>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<title>Error</title>
</head>
<script type="text/javascript">
function setupDisplayError() {
// remove spaces and other markup information
var errorMessage = document.getElementById('errorMessage').innerHTML.replace(/^\s*|\s*$/g,'');
var errorCode = document.getElementById('errorCode').innerHTML.replace(/^\s*|\s*$/g,'');
var redirect = document.getElementById('redirect').innerHTML.replace(/^\s*|\s*$/g,'');
var previous = document.getElementById('previous').innerHTML.replace(/^\s*|\s*$/g,'');

	if (errorMessage.length > 15){
		// An error message has been sent by an action. Display the specific error message
		document.getElementById('actionErrorMessage').style.display = '';
		if (errorCode.length > 12){
		// An error code has been sent by an action. Display the specific error code
			document.getElementById('errorCode').style.display = '';
		} else {
			document.getElementById('errorCode').style.display = 'none';
		}
		if (redirect == "true") {
		// Display a link to home page if specified by action
			document.getElementById('redirectMessage').style.display = '';
		}
		if (previous == "true") {
		// Display a link to home page if specified by action
			document.getElementById('previousMessage').style.display = '';
		}
	} else {
		// Display a generic error with a link to home page
		document.getElementById('staticErrorMessage').style.display = '';
	}
}
</script>

<body onload="setupDisplayError();">

<div id="header"><img border="0" style="width: 100%"
	src="${pageContext.request.contextPath}/image/time-expense.jpg">
</div>

 <center>
  
 <div id="staticErrorMessage" style="position:relative;top:50%;display:none;color:red">
 <h3>
	An unexpected error has occurred.
 <br> 
 <br>
 You may click <a href="index.htm"> here </a> to go to the Time & Expense HomePage
 </h3>
 </div>

  <br/>
  <br/>
  <h3>
 <div id = "actionErrorMessage" style="position:relative;top:30%;display:none;color:red">

 <br/>
 <br/>
 
<span id="redirect" style="display:none">
 	<s:property value="error.redirectOption"/>
</span>

<span id="previous" style="display:none">
 	<s:property value="error.previousOption"/>
</span>

 <span id="errorCode" style="display:none">
 	Error Code: <s:property value="error.errorCode"/>
 </span>
  <span id="errorMessage">
 	Error Message: <s:property value="error.errorMessage"/>
 </span>
 
    <br/>
 	<br/>
 	
 <span id="previousMessage" style="display:none;color:red;">
	Click <a href="javascript:history.back()"> here </a> to access previous page
	<br/>
 </span>

 <span id="redirectMessage" style="display:none;color:red;">
	Click <a href="index.htm"> here </a> to access Time & Expense HomePage
 </span>

 <br/>
 <br/>
  
 </div>
  </h3>
 

 </center>


</body>
</html>
