<!-- 
This page meant to be included as the header on transaction listing and detail pages
where employee information is displayed. All data fields are read from the action.

ZH - 04/03/2009
 -->

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	function getEmpInfoLink() {
		var employeeId = dojo.byId('employeeId').value;
		var appointmentId = dojo.byId('appointmentId').value;
		//var href = "EmpInfoAction.action?employeeId=" +  employeeId + "&appointmentId=" + appointmentId;
		var comments = window.open("EmpInfoAction.action?employeeId="
				+ employeeId + "&appointmentId=" + appointmentId, "list",
				"width=900px,height=700px,modal=no,scrollbars=1");
		//location.href=href;
	}
</script>
<span>
<s:label theme="simple" value="Emp ID #:"
				cssStyle="margin-left:01" />&nbsp; <s:textfield theme="simple"
				name="empInfo.empId" id="employeeId" readonly="true"
				cssStyle="border:none" size="6"></s:textfield> <s:label
				theme="simple" value="Name:" cssStyle="margin-left:12" /> <s:a
				theme="simple" href="javascript:getEmpInfoLink();" id="empName"
				onclick="showUnsavedChangesPopup = false;">
				<s:property value="empInfo.empName" />
			</s:a> <s:label theme="simple" value="Proc. Lvl:" cssStyle="margin-left:12" />
			<s:property value="empInfo.processLevel" /> <s:label theme="simple"
				value="HRMN Dept:" cssStyle="margin-left:12" /> <s:property
				value="empInfo.displayName" />
</span>
<span style="display:block;margin-top:5px; ">												
	<s:label theme="simple" value="Barg Unit:"
	cssStyle="margin-left:1" /> <s:property value="empInfo.bargUnit" />
</span>
	


<s:hidden theme="simple" id="appointmentId" name="empInfo.apptId"></s:hidden>

