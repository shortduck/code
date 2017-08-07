<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="gov.michigan.dit.timeexpense.model.core.UserProfile"%>
	<%@taglib
	prefix="s" uri="/struts-tags"%>
<!-- Needed for yahoo menu -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/yahoo/reset-fonts-grids.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/yahoo/menu.css">
<style type="text/css">
#TimeAndExpenseMenu {
	position: static;
}

#TimeAndExpenseMenu .yuimenuitemlabel {
	_zoom: 1;
}

#TimeAndExpenseMenu .yuimenu .yuimenuitemlabel {
	_zoom: normal;
}
</style>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/yahoo/yahoo-dom-event.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/yahoo/container_core.js"></script>
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/yahoo/menu.js"></script>

<script type="text/javascript">
         
     YAHOO.util.Event.onContentReady("TimeAndExpenseMenu", function () {
         var oMenu = new YAHOO.widget.Menu("TimeAndExpenseMenu", { 
                                                 position: "static", 
                                                 hidedelay:  750, 
                                                 lazyload: true });
         oMenu.render();            
     });	
</script>

<!-- End yahoo menu setup -->
	
<!-- Menu Definition. The menu options will be toggled according to the security settings -->
<div id="sidebar1">

<center><b>Main Menu</b></center>
<BR>
<div id="TimeAndExpenseMenu" class="yui-skin-sam yuimenu"
	style="width: 100px;">
<div class="bd">
<ul class="first-of-type">
	<li class="yuimenuitem first-of-type"><a class="yuimenuitemlabel"
		href="HomePageAction.action">Home Page</a></li>
	<li class="yuimenuitem first-of-type"><a class="yuimenuitemlabel"
		href="#">Employee</a>

	<div id="employee" class="yuimenu">
	<div class="bd">
	<ul>
		<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.empAdvance}"/>"><a
			class="yuimenuitemlabel" href="AdvanceListAction.action?moduleId=ADVW001">Advances</a></li>
		<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.empNonRoutineTravelRequisition}"/>"><a
			class="yuimenuitemlabel" href="TravelRequisitionListAction.action?moduleId=NTRW001">Non-Routine Travel Requisitions</a></li>
		<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.empExpense}"/>"><a
			class="yuimenuitemlabel" href="ViewExpenseList.action?moduleId=EXPW001">Expenses</a></li>
			<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.sw_REPORT_TRANSACTION_LEDGER}"/>"><a
			class="yuimenuitemlabel" href="viewReportsAction.action?moduleId=EXPR012">Reports Manager</a></li>
			
		<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.empReports}"/>"><a class="yuimenuitemlabel" href="#">Reports</a>
		<div id="reportEmployee" class="yuimenu">
		<div class="bd">
		<ul class="first-of-type">
			<li class="yuimenuitem" 
			style="display:<s:property value="%{#session.LeftNavOptions.emp_REPORT_EXCEPTION}"/>">
			<a class="yuimenuitemlabel" href="ExceptionReport.action?moduleId=EXPR002">Exceptions</a></li>
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.emp_REPORT_RECEIPTS_REQUIRED}"/>">
			<a class="yuimenuitemlabel" href="ReceiptsRequiredReport.action?moduleId=EXPR001">Receipts
			Required</a></li>
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.emp_REPORT_ROUTINE_TRAVELER}"/>"><a class="yuimenuitemlabel" href="RoutineTraveler.action?moduleId=EXPR003"
			>Routine
			Traveler</a></li>
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.emp_REPORT_TRANSACTION_LEDGER}"/>"><a class="yuimenuitemlabel" href="TransactionLedgerReport.action?moduleId=EXPR004"
			>Transaction
			Ledger</a></li>
		</ul>
		</div>
		</div>
		</li>
	</ul>
	</div>
	</div>

	</li>
	<li class="yuimenuitem"><a class="yuimenuitemlabel"
		href="#">Manager</a>

	<div id="teManager" class="yuimenu">
	<div class="bd">
	<ul>
		<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.mngrAdvance}"/>"><a
			class="yuimenuitemlabel" href="SelectEmployee.action?moduleId=ADVW002">Advances</a></li>
			<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.mngrNonRoutineTravelRequisition}"/>"><a
			class="yuimenuitemlabel" href="SelectEmployee.action?moduleId=NTRW002">Non-Routine Travel Requisitions</a></li>
		<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.mngrApproval}"/>"><a
			class="yuimenuitemlabel" href="ManagerApprove.action?moduleId=APRW003">Approvals</a></li>
		<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.mngrExpense}"/>"><a
			class="yuimenuitemlabel" href="SelectEmployee.action?moduleId=EXPW002">Expenses</a></li>
		<li class="yuimenuitem"
		style="display:<s:property value="%{#session.LeftNavOptions.mngrReports}"/>"><a class="yuimenuitemlabel"
			href="#">Reports</a>
		<div id="reportManager" class="yuimenu">
		<div class="bd">
		<ul class="first-of-type">
			<li class="yuimenuitem" 
			style="display:<s:property value="%{#session.LeftNavOptions.mngr_REPORT_EXCEPTION}"/>"
			><a class="yuimenuitemlabel" href="ExceptionReport.action?moduleId=EXPR006">Exceptions</a></li>
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.mngr_REPORT_RECEIPTS_REQUIRED}"/>">
			<a class="yuimenuitemlabel" href="ReceiptsRequiredReport.action?moduleId=EXPR005">Receipts
			Required</a></li>
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.mngr_REPORT_ROUTINE_TRAVELER}"/>">
			<a class="yuimenuitemlabel" href="RoutineTraveler.action?moduleId=EXPR007">Routine
			Traveler</a></li>
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.mngr_REPORT_NON_ROUTINE_TRAVELER}"/>">
			<a class="yuimenuitemlabel" href="NonRoutineTraveler.action?moduleId=EXPR013">Non Routine Travel Requisitions</a></li>
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.mngr_REPORT_TRANSACTION_LEDGER}"/>">
			<a class="yuimenuitemlabel" href="TransactionLedgerReport.action?moduleId=EXPR008">Transaction
			Ledger</a></li>
		</ul>
		</div>
		</div>
		</li>
	</ul>
	</div>
	</div>

	</li>
	<li class="yuimenuitem"><a class="yuimenuitemlabel"
		href="#">Statewide</a>

	<div id="statewide" class="yuimenu">
	<div class="bd">
	<ul>
		<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.swAdvance}"/>"><a
			class="yuimenuitemlabel" href="SelectEmployee.action?moduleId=ADVW003">Advances</a></li>
		<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.swNonRoutineTravelRequisition}"/>"><a
			class="yuimenuitemlabel" href="SelectEmployee.action?moduleId=NTRW003">Non-Routine Travel Requisitions</a></li>
		<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.swApproval}"/>"><a
			class="yuimenuitemlabel" href="StateWideApproval.action?moduleId=APRW004">Approvals</a></li>
		<li class="yuimenuitem"
			style="display:<s:property value="%{#session.LeftNavOptions.swExpense}"/>"><a
			class="yuimenuitemlabel" href="SelectEmployee.action?moduleId=EXPW003">Expenses</a></li>
		<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.swReports}"/>"><a class="yuimenuitemlabel"
			href="#">Reports</a>
		<div id="reportStatewide" class="yuimenu">
		<div class="bd">
		<ul class="first-of-type">
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.sw_REPORT_EXCEPTION}"/>">
			<a class="yuimenuitemlabel" href="ExceptionReport.action?moduleId=EXPR010">Exceptions</a></li>
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.sw_REPORT_RECEIPTS_REQUIRED}"/>">
			<a class="yuimenuitemlabel"  href="ReceiptsRequiredReport.action?moduleId=EXPR009">Receipts
			Required</a></li>
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.sw_REPORT_ROUTINE_TRAVELER}"/>">
			<a class="yuimenuitemlabel" href="RoutineTraveler.action?moduleId=EXPR011">Routine
			Traveler</a></li>
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.sw_REPORT_NON_ROUTINE_TRAVELER}"/>">
			<a class="yuimenuitemlabel" href="NonRoutineTraveler.action?moduleId=EXPR014">Non Routine Travel Requisitions</a></li>
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.sw_REPORT_TRANSACTION_LEDGER}"/>">
			<a class="yuimenuitemlabel" href="TransactionLedgerReport.action?moduleId=EXPR012">Transaction
			Ledger</a></li>
		</ul>
		</div>
		</div>
		</li>
	 	<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.swParams}"/>">
		<a class="yuimenuitemlabel"		href="#">Params</a>
		<div id="paramStatewide" class="yuimenu">
		<div class="bd">
		<ul class="first-of-type"> 
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.sw_PARAM_SYSTEM_CODES}"/>">
			<a class="yuimenuitemlabel" href="systemCodesInitialize.action?moduleId=PRMW079">System Codes</a></li>
		</ul>
		</div>
		</div>
		</li>  
		<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.swAgencyOptions}"/>">
		<a class="yuimenuitemlabel"	href="#">Agency Options</a>
		<div id="agencyStatewide" class="yuimenu">
		<div class="bd">
		<ul class="first-of-type"> 
			<li class="yuimenuitem" style="display:<s:property value="%{#session.LeftNavOptions.sw_Agency_Locations}"/>">
			<a class="yuimenuitemlabel" href="agyLocationInitialize.action?moduleId=AGYW001">Agency Locations</a></li>
		</ul>
		</div>
		</div>
		</li>  
		 
	</ul>
	</div>
	</div>
	</li>
	<li class="yuimenuitem"><a class="yuimenuitemlabel"
		href="#">Help</a>
	<div id="help" class="yuimenu">
	<div class="bd">
	<!-- Contact Us references have now been changed to Helpful Tips. All code remains the same -->
	<ul>
		<li class="yuimenuitem"><a class="yuimenuitemlabel" href="#" onclick="helpFunction('faq');">FAQ</a></li>
		<li class="yuimenuitem"><a class="yuimenuitemlabel" href="#" onclick="helpFunction('training');">Training</a></li>
		<li class="yuimenuitem"><a class="yuimenuitemlabel" href="#" onclick="helpFunction('contactus');">Helpful Tips</a></li>
	</ul>
	</div>
	</div>
	</li>
</ul>
</div>
</div>

</div>