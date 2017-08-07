<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html;charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<META http-equiv="Content-Type" content="text/javascript" charset="ISO-8859-1">
<TITLE>Time & Expense - Login</TITLE>

<script type="text/javascript">
  function init(){
  	if(document.getElementById('error').value.length > 0)
	  document.getElementById('errorMsg').innerHTML = 'The username or password you entered is incorrect.';
	  if (!usingSupportedBrowser()){
	  	// unsupported browser
	  	document.write("You are running an unsupported browser. Please use Internet Explorer, FireFox or Safari" +
	  					" in order to access the Time & Expense application");
	  					return;
	  	}
	   // show welcome message and login fields. At this time, the browser being used is supported and 
	   // Javascript is enabled
  	   document.getElementById("welcomeMessage").style.display = "block";
	   document.getElementById("loginFields").style.display = "block";
	   // set cursor on user name field
	   document.getElementById("username").focus();
	   setBookmarkDisplay();
  } 
  
  function usingSupportedBrowser (){
	if (navigator.userAgent.indexOf('Trident') >= 0 || 
		navigator.userAgent.indexOf('Firefox') >= 0 || 
		navigator.appVersion.indexOf('Safari') >= 0) {
		return true;
	} else
		return false;
  }
  
function addBookmark() {
	var title = "Time & Expense";
	var url = location.href.substring(0, location.href.indexOf("/jsp/login.jsp"));
	if (window.sidebar) { // firefox
		window.sidebar.addPanel(title, url,"");
	} else if( document.all ) { //MSIE
		window.external.AddFavorite( url, title);
	} else {
		alert("Sorry, your browser doesn't support this feature. Please add the bookmak manually.");
	}
}

function setBookmarkDisplay (){
if (navigator.userAgent.indexOf('Firefox') >= 0 || 
		navigator.appVersion.indexOf('Safari') >= 0){
		document.getElementById('bookmarkDisplayFF').style.display = 'inline';
		} else if (navigator.userAgent.indexOf('MSIE') >= 0) {
			document.getElementById('bookmarkDisplayIE').style.display = 'inline';
		}
}
</script>

</HEAD>
<BODY onload='init();'>
<div id="header"><img border="0" style="width: 100%" src="../../TimeAndExpense_images/time-expense.jpg">
</div>

<noscript>
<b><center>Javascript is currently disabled for your browser. Please enable Javascript in 
order to run the Time & Expense Application<center></b> 
</noscript>
<div id="welcomeMessage" style="display:none">

<table width="100%">
<tr><td><a href="javascript:addBookmark()" style="position:absolute;right:3%"><span id="bookmarkDisplayFF" style="display:none">Add bookmark</span>
<span id="bookmarkDisplayIE" style="display:none">Add to Favorites</span></a></td></tr>
	<tr><td height="2px"></td></tr>
	<tr><td align="center"><h3>Welcome to Time & Expense</h3></td></tr>
</table>

</div>

<div style="display:none" id="loginFields" >
<table cellspacing="1px" cellpadding="1px" align="center">
<tr>
	<td>
	<h4>Please login with your self service Id and password.</h4>
	</td>
</tr>
<tr>
	<br/>
	<td><div id='errorMsg' style="color:red"></div></td>
	<br/>
</tr>
<tr>
	<td>
	<form method="post" action="j_security_check">
	<table>
	<tr>
		<td>Username&nbsp;</td>
		<td><input type="text" name="j_username" id="username"></td>
	</tr>
	<tr>
		<td>Password&nbsp;</td>
		<td><input type="password" name="j_password"></td>
	</tr>
	<tr>
		<td colspan="2" align="center">	
			<input type="submit" value="Login">
		</td>
	</tr>	
	</table>
	</form>
	</td>
</tr>
</table>
</div>
<s:textfield id='error' name="error" value="%{#parameters['error']}" cssStyle="display:none"/>
	<%/*
	if("1".equals(request.getParameter("error")))
		out.print("Invalid Userid");
	if("2".equals(request.getParameter("error")))
		out.print("Invalid Role");
	*/%>
</BODY>
</HTML>
