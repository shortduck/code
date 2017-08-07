<%@ taglib prefix="s" uri="/struts-tags"%>
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
<link href="${pageContext.request.contextPath}/css/page.css" rel="stylesheet" type="text/css">
<title>Employee General Information</title>

<!-- Included the dojoInclude.jsp as a part of Dojo 1.6.1 upgrade : KP du   --> 
<jsp:include page="/jsp/dojoInclude.jsp"></jsp:include>

<!-- calendar styles & scripts -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/jscal2.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/border-radius.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/win2k/win2k.css" /> 

<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/lang/en.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/te_dat_scope.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/enGrid.css"></link>

<!-- JS to render grid -->
		<script type="text/javascript">
		dojo.require("dojox.grid.EnhancedGrid");
	dojo.require("dojox.grid.enhanced.plugins.Filter");
	dojo.require("dojo.parser");
	dojo.require("dojox.grid.DataGrid");
	dojo.require("dojo.data.ItemFileReadStore");
</script>

	<script type="text/javascript">
	
	// declare global results grid store
	resultsGridStore = null;
	
	
	// methods to be invoked immediately after page DOM construction
	dojo.addOnLoad(function(){
		initSingleDateCalendar();
		initStandardDistributionGrid();
		modifyEmpInoHyperlink ();
	});
	
	function modifyEmpInoHyperlink (){
	var empInfoHyperlink = dojo.byId(empName);
	empInfoHyperlink.style.textDecoration = "none";
	empInfoHyperlink.href = "#";
	empInfoHyperlink.style.color = "black";
	empInfoHyperlink.style.cursor = "default";
	
	}

	function initStandardDistributionGrid(){
		var jsonResponseStr = dojo.byId('jsonResponse_hidden').value;
		
		var stdDists = eval('(' + jsonResponseStr + ')');
		
		convertNullFieldsToEmptyStrings(stdDists);
		
		resultsGridStore = new dojo.data.ItemFileReadStore(
							{data: {identifier: 'dfdseIdentifier',
								items: stdDists}
							}
						);
		dijit.byId('resultsGrid').setStore(resultsGridStore);
	}
	
	function convertNullFieldsToEmptyStrings(stdDists){
		dojo.forEach(stdDists, function(item){
			if(item.appropriationYear == undefined) item.appropriationYear='';
			if(item.indexCode == undefined) item.indexCode='';
			if(item.pca == undefined) item.pca='';
			if(item.grantNumber == undefined) item.grantNumber='';
			if(item.grantPhase == undefined) item.grantPhase='';
			if(item.agencyCode1 == undefined) item.agencyCode1='';
			if(item.projectNumber == undefined) item.projectNumber='';
			if(item.projectPhase == undefined) item.projectPhase='';
			if(item.agencyCode2 == undefined) item.agencyCode2='';
			if(item.agencyCode3 == undefined) item.agencyCode3='';
			if(item.multipurposeCode == undefined) item.multipurposeCode='';
			if(item.percent == undefined) item.percent='';
			if(item.startDate == undefined) item.startDate='';
			if(item.endDate == undefined) item.endDate='';
		});
	}
	
	function refreshEmpInfo(){
		var selectedDate = dojo.byId('f_singleDateField').value;
		var apptId = dojo.byId('apptId').value;
		var empId = dojo.byId('empId').value;
		var completeurl = location.href;
		var urltokens = completeurl.split("?");
		//var empInfoUrl =  location.href + "?appointmentId="+apptId+"&employeeId="+empId+"&selectedDate="+selectedDate;
		//var urlfinal = urltokens[0]+"?"+urltokens[1]+"?appointmentId="+apptId+"&employeeId="+empId+"&selectedDate="+selectedDate;
		var urlfinal = urltokens[0]+"?appointmentId="+apptId+"&employeeId="+empId+"&selectedDate="+selectedDate;
		window.location = urlfinal;
	}
	
	</script>
<%-- /tpl:put --%>
</head>

<body>
<%-- tpl:put name="bodyarea" --%>

	<div style="display:none">
	  	<s:date name="selectedDate" id="selectedDateId" format="MM/dd/yyyy"/>
	  	<s:textfield id="apptId" name="appointmentId" value="%{appointmentId}"></s:textfield>
	  	<s:textfield id="empId" name="employeeId" value="%{employeeId}"></s:textfield>
  	</div>


   <table width="80%">
     <tr>
      <td align="right">Employee Information as of:</td>
       <td align="left" width="165">
       <s:textfield id="f_singleDateField" theme="simple" name="selectedDate" onblur="syncDate(this); return true;" 
       		maxlength="10" value="%{selectedDateId}"></s:textfield>
	   <img src='${pageContext.request.contextPath}/image/calendar.gif' id="f_singleDateTrigger" alt="Choose from date"></img>
      </td>
      <td>
      <button id="pageRefresh" onclick="refreshEmpInfo();">Refresh</button>
      </td>
     </tr>
    </table>

	<br>
			
		<span style="position:relative;top:10px"><jsp:include page="empHeaderInfo.jsp"></jsp:include></span>
			<br>

<br><br>
<fieldset style="position: relative; border:1px solid black;left: 10px;width: 90%"><legend>Employee General Information</legend>
<table>
<tbody>
           <tr>
           </tr>
			<tr>
				<td width="124" nowrap="nowrap">TKU:</td>
				<td width="123"><s:property value="employeeInfo.tku" /></td>
				<td width="103"></td>
				<td width="106"></td>
				<td width="145" nowrap="nowrap">HRMN Dept Name:</td>
				<td width="154"><s:property value="employeeInfo.hrmnDeptName" /></td>
			</tr>
			<tr>
				<td width="124" nowrap="nowrap">Appt Status Code:</td>
				<td colspan="3"><s:property value="employeeInfo.apptStatusCode" /></td>
				<td width="145" nowrap="nowrap">Position:</td>
				<td width="154"><s:property value="employeeInfo.positionId" /></td>
			</tr>
			<tr>
				<td width="124" nowrap="nowrap">Appt Effective Date:</td>
				<td width="123"><s:date name="employeeInfo.appointmentDate" format="MM/dd/yyyy" /></td>
				<td width="103" nowrap="nowrap">Departure Date:</td>
				<td width="106"><s:date name="employeeInfo.departureDate"  format="MM/dd/yyyy"/></td>
				<td width="145" nowrap="nowrap">FMLA Expiration Date:</td>
				<td width="154"><s:date name="employeeInfo.fmlaExpirationDate" format="MM/dd/yyyy"/></td>
			</tr>
			<tr>
				<td width="124" nowrap="nowrap">Voluntary Plan:</td>
				<td width="123"><s:property value="employeeInfo.plan" /></td>
				<td width="103" nowrap="nowrap">Vol.Plan Hours:</td>
				<td width="106"><s:property value="employeeInfo.planHours" /></td>
				<td width="145" nowrap="nowrap">Vol.Plan Exp Date:</td>
				<td width="154"><s:date name="employeeInfo.planExpirationDate" format="MM/dd/yyyy" /></td>
			</tr>
			<tr>
				<td colspan="6" height="18">
				<hr>
				</td>
			</tr>
			<tr style="border-top: 1px solid black">
				<td height="23" width="124">Class Type:</td>
				<td height="23" width="123"><s:property value="employeeInfo.classType" /></td>
				<td height="23" width="103">Job Code:</td>			
				<td height="23" width="106"><s:property value="employeeInfo.jobCode" /></td>
				<td height="23" width="145">Retirement Code:</td>
				<td height="23" width="154"><s:property value="employeeInfo.retirementCode" /></td>
			</tr>
			<tr>
				<td width="124">Union Code:</td>
				<td width="123"><s:property value="employeeInfo.bargainingUnit" /></td>
				<td width="103">OT Exempt:</td>
				<td width="106"><s:property value="employeeInfo.exempt" /></td>
				<td width="145">OT Exp Date:</td>
				<td width="154"><s:property value="employeeInfo.flsaExpirationDate" /></td>
			</tr>
			<tr>
				<td width="124">Std Hours Reg:</td>
				<td width="123"><s:property value="employeeInfo.standardHoursRegular" /></td>
				<td width="103">Shift 2:</td>
				<td width="106"><s:property value="employeeInfo.standardHoursShift2" /></td>
				<td width="145">Shift 3:</td>
				<td width="154"><s:property value="employeeInfo.standardHoursShift3" /></td>
			</tr>
			<tr>
				<td width="124">Average Hours:</td>
				<td width="123"><s:property value="employeeInfo.averageHrs" /></td>
				<td width="103">Supervisor:</td>
				<td width="210" ><s:property value="employeeInfo.supervisorName" /></td>
				<td width="145"></td>
				<td width="154"></td>
			</tr>
		</tbody>
	</table>
	</fieldset>
<br>
<table style="position: relative;left: 10px">
        <tr>
	     <s:if test="employeeInfo == null ">
	        <td width="246"><B>No Appointment Records Found</B></td>
	      </s:if>            
        </tr>
</table>
<br>
<fieldset style="position: relative; border:1px solid black;left: 10px;width: 90%"><legend>Expense Profiles</legend>
 <br>

<table>
    <s:iterator value="profiles">
        <tr>
            <td width="246"><s:property value="ruleName" /></td>            
            <td width="184"><s:property value="ruleValue" /></td>
        </tr>
    </s:iterator>
</table>
</fieldset>
<br>
<table style="position: relative;left: 10px">
        <tr>
	     <s:if test="profiles.size() == 0 ">
	        <td width="246"><B>No Expense Profiles Found</B></td>
	      </s:if>            
        </tr>
</table>
<br>

<fieldset style="position: relative; border:1px solid black;left: 10px;width: 90%"><legend>Locations</legend>
	 <table id="address" cellpadding="0" cellspacing="0" style="border-collapse: collapse">
	 <tbody>
		<tr>
			<td width="135"><label for="address1"><b></b></label></td>
			<td colspan="2" align="left" width="188"><b>Work</b></td>
			<td colspan="2" align="left" width="195"><b>Home</b></td>
		</tr>
		<tr>
			<td width="135">Location Code:</td>
			<td align="left" colspan="2" width="188"><s:property value="workLocation.locationCode" /></td>
			<td align="left" colspan="2" width="195"></td>
		</tr>
		<tr>
			<td width="135">Address Line1:</td>
			<td colspan="2" width="188"><s:property value="workLocation.addressLine1" /></td>
			<td colspan="2" width="195"><s:property value="homeLocation.addressLine1" /></td>
		</tr>
		<tr>
			<td width="135">Address Line2:</td>
			<td colspan="2" width="188"><s:property value="workLocation.addressLine2" /></td>
			<td colspan="2" width="195"><s:property value="homeLocation.addressLine2" /></td></tr>
		<tr>
			<td width="135">City:</td>
			<td colspan="2" width="188"><s:property value="workLocation.city" /></td>
			<td colspan="2" width="195"><s:property value="homeLocation.city" /></td></tr>
		<tr>
			<td width="135">State:</td>
			<td colspan="2" width="188"><s:property value="workLocation.state" /></td>
			<td colspan="2" width="195"><s:property value="homeLocation.state" /></td></tr>
		<tr>
			<td width="135">Zip:</td>
			<td colspan="2" width="188"><s:property value="workLocation.zip" /></td>
			<td colspan="2" width="195"><s:property value="homeLocation.zip" /></td></tr>
		<tr>
			<td width="135">County:</td>
			<td colspan="2" width="188"><s:property value="workLocation.county" /></td>
			<td colspan="2" width="195"><s:property value="homeLocation.county" /></td></tr>
	</tbody>
	</table>
	</fieldset>



<br>
<table style="position: relative;left: 10px">
        <tr>
	     <s:if test="workLocation == null ">
	     <s:if test="homeLocation == null ">
	        <td width="246"><B>No Locations Found</B></td>
	        </s:if>
	      </s:if>            
        </tr>
</table>
<br>
<div>	
<span style="color: black;position: relative;left: 5px";><B><U>Standard Distribution:</U></B></span>	
<br><br>
<span style="color: black;position: relative;left: 5px";>Facs Agy:&nbsp;&nbsp;<s:property value="%{facsAgency}"/></span>	
<br><br>
<table class="soria" id="resultsGrid" jsId="grid3" dojotype="dojox.grid.EnhancedGrid" query="{ dfdseIdentifier : '*' }" store="resultsGridStore" selectionMode="none" columnReordering="false" style="position: relative; width:900px ; height:500px;left: 5px" plugins="{filter:false}">
	
	<thead>
		<tr>
			<th field="appropriationYear" width="3%">AY</th>
			<th field="indexCode" width="8%">Index</th>
			<th field="pca" width="8%">PCA</th>
			<th field="grantNumber" width="6%">Grant</th>
			<th field="grantPhase" width="6%">Phase</th>
			<th field="agencyCode1" width="6%">AG1</th>
			<th field="projectNumber" width="6%">Project</th>
			<th field="projectPhase" width="6%">Phases</th>
			<th field="agencyCode2" width="6%">AG2</th>
			<th field="agencyCode3" width="6%">AG3</th>
			<th field="multipurposeCode" width="7%">Multi</th>
			<th field="percent" width="3%">PCT</th>
			<th field="startDate" width="8%">Start Date</th>
			<th field="endDate" width="8%">End Date</th>
			<th field="source" width="7%">Source</th>				
			</tr>
	</thead>
</table>
<br>
</div>
<div style="display: none">
		<s:textfield id="jsonResponse_hidden" theme="simple" value="%{jsonResponse}" />
</div>

<%-- /tpl:put --%>
</body>

</html><%-- /tpl:insert --%>
