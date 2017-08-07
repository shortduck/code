<%-- tpl:insert page="/jsp/popupTemplate.jtpl" --%><!--

Template meant to used for popups in the application. This template will only include 
page specific styles and a content area.  


 -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link href="${pageContext.request.contextPath}/css/page.css"
	rel="stylesheet" type="text/css" />

<%-- tpl:put name="headarea" --%>
<title>Insert title here</title>

<script type="text/javascript">

function submitComments() {
	  if (window.opener && !window.opener.closed)
		  var comments = document.getElementById("comments").value;
	    window.opener.document.advance.approvalComments.value = comments;
	  window.close();
	}

</script>

<%-- /tpl:put --%>
</head>

<body>
<%-- tpl:put name="bodyarea" --%><body>

Enter comments:<br>
<textarea NAME="comments" COLS="40" ROWS="6" id="comments"  style="white-space: pre-wrap;" ></textarea>

<button onclick="submitComments();">Submit</button>

</body><%-- /tpl:put --%>
</body>

</html><%-- /tpl:insert --%>
