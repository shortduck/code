<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<table>
<!-- added to solve display issue with IE8 -->
</table>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expense Summary</title>

<script type="text/javascript"	src="${pageContext.request.contextPath}/js/ExpenseCodingBlock.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath}/js/ExpenseSummary.js"></script>

<link href="${pageContext.request.contextPath}/css/calendar-win2k-1.css" rel="stylesheet" type="text/css">


<!-- JS to render advance tab control and grid -->
<script type="text/javascript">
	dojo.require("dojo.parser");
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dojox.grid.DataGrid");
	dojo.require("dojox.data.QueryReadStore");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.form.ComboBox");
	dojo.require("dijit.form.CheckBox"); 
</script>

<script type="text/javascript">
var expenseSummaryGridStore = null;
dojo.addOnLoad(displayExpenseList);

</script>
</head>

<body>
<fieldset style="position: relative; border: solid 1px; top: 1px; left: 1px; height: 135px; width: 750px">
<legend style="color: black">&nbsp;<b>Totals</b></legend>
<table width="75%">
<tr>
	<td>
	Total Expenses (Non - Taxable):
	</td>
	<td align="right">
	$<s:property value="%{display.nonTaxableTotalExpenses}"/>
	</td>
</tr>
<tr>
	<td>
	Total Expenses (Taxable):
	</td>
	<td align="right">
	$<s:property value="%{display.taxableTotalExpenses}"/>
	</td>
</tr>
<!--<br>
--><tr>
	<td>

	</td>
	<td align="right">
<b>	________</b>
	</td>
</tr>
<!--<br>
--><tr>
	<td>
	<b>Total Expenses:</b>
	</td>
	<td align="right">
	$<s:property value="%{display.totalExpenses}"/>
	</td>
</tr>
<tr>
	<td>Total Advance Liquidated :</td>
	<td align="right">$<s:property value="%{display.amountLiquidated}"/></td>
	<td>&nbsp;&nbsp;&nbsp;(Current Outstanding Advance - $<s:property value="%{display.totalOutstandingAdvance}"/>)</td>
</tr>
<!--<br>
--><tr>
	<td>

	</td>
	<td align="right">
<b>	________</b>
	</td>
</tr>
<tr>
	<td>
	<b>Total Due To/(From) Employee:</b>
	</td>
	<td align="right">
	$<s:property value="%{display.totalDueEmployee}"/>
	</td>
	<td>&nbsp;&nbsp;&nbsp;Before taxes</td>	
</tr>

 	
</table>
</fieldset>
<fieldset style="position: relative; border: solid 1px; top: 1px; left: 1px; height: 95px; width: 750px">
<legend style="color: black">&nbsp;<b>Summary By Category</b></legend>
<table class="soria" id="summaryByCategoryGrid" dojotype="dojox.grid.EnhancedGrid" query="{ description: '*' }" rowsPerPage="15" 
store="resultsSummaryByCategoryGridStore" style="position: relative; top: 1px; left: 1px; height: 70%; width: 60%" >
	<thead>
		<tr>
			<th field="description" width="70%">
			<center>Expense Category</center>
			</th>
			<th field="amount" width="30%" formatter="rightAlignGridCellNumber">
			<center>Amount</center>
			</th>
		</tr>
	</thead>
</table>
</fieldset>
<fieldset style="position: relative; border: solid 1px; top: 1px; left: 1px; height: 95px; width: 750px">
<legend style="color: black">&nbsp;<b>Summary By Coding Block</b></legend>

<table class="soria" id="summaryByCodingBlockGrid" dojotype="dojox.grid.EnhancedGrid" query="{ sequence: '*' }" rowsPerPage="15" 
store="resultsSummaryByCodingBlockGridStore" style="position: relative; top: 1px; left: 1px; height: 80%; width: 90%" >
	<thead>
		<tr>
			<th field="appropriationYear" width="3%">
			<center>AY</center>
			</th>
			<th field="indexCode" width="9.66%">
			<center>Index</center>
			</th>
			<th field="pca" width="9.66%">
			<center>PCA</center>
			</th>
			<th field="grantNumber" width="9.66%">
			<center>Grant</center>
			</th>
			<th field="grantPhase" width="3%">
			<center>Ph</center>
			</th>
			<th field="agencyCode1" width="9.66%">
			<center>AG1</center>
			</th>
			<th field="projectNumber" width="9.66%">
			<center>Project</center>
			</th>
			<th field="projectPhase" width="3%">
			<center>Ph</center>
			</th>
			<th field="agencyCode2" width="9.66%">
			<center>AG2</center>
			</th>
			<th field="agencyCode3" width="9.66%">
			<center>AG3</center>
			</th>
			<th field="multipurposeCode" width="9.66%">
			<center>Multi</center>
			</th>
			<th field="standardInd" width="4%">
			<center>Std</center>
			</th>			
			<th field="dollarAmount" width="9.66%" formatter="rightAlignGridCellNumber">
			<center>Amount</center>
			</th>
		</tr>
	</thead>
</table>

</fieldset>
<!-- fieldset for error message -->
<fieldset style="position: relative;width: 750px">
<table style="width:100%">
<tr>
	<td align="center" width="100%">
		<div id="expenseSummaryErrorMsg" style="position: relative; top: 5px;font-size:9pt;" align="center"></div>
	</td>
</tr>
</table>
</fieldset>
<!-- fieldset for buttons -->
<fieldset style="position: relative;width: 85%">
<table style="width:100%">
<tr>
	<td align="left" width="15%">
	<div align=left>
	<table style="position: relative; top: 5px;width: 100%" align="right">
		<tr align="right">
			<td align="left">
				<s:form id="summaryFormPrevNext" theme="simple">
		 		<s:submit  value="<<" id="prevRevBtnInSummaryTab" action="PreviousExpenseRevision" onclick="addTabPrefElement(this.form, 'summaryTab'); return true;"/> 
		 		<s:submit  value=">>" id="nextRevBtnInSummaryTab" action="NextExpenseRevision" onclick="addTabPrefElement(this.form, 'summaryTab'); return true;"/>
		 		<s:label value="Version: "></s:label><s:label id="es_revNo" name="expenseMaster.revisionNumber"></s:label>
		 		</s:form>
		 	</td>
		</tr>
	</table>
	</div>
	</td>

	<td align="right" width="1%">
		<div align=right>
		<table style="position: relative; top: 10px;width: 100%">
			<tr align="right">
				<td align="right"><s:submit id="submitBtn" theme="simple"  onclick="ajaxSubmitExpense();" value="Submit" cssStyle="display:%{display.displaySubmitButton}" disabled = "%{display.disableSubmitButton}"></s:submit></td>
				<td align="right"><s:submit id="approveExpenseBtn" theme="simple" onclick="buttonApproveNextClicked = false;ajaxApproveExpense();" value="Approve" cssStyle="display:%{display.displayApproveButton}" disabled = "%{display.disableApproveButton}"></s:submit></td>
				<td align="right"><s:submit id="approveWithcommentsExpenseBtn" theme="simple" value="Approve With Comments" cssStyle="display:%{display.displayApproveWithcommentsButton}" disabled = "%{display.disableApproveWithCommentsButton}" onclick="buttonApproveWithCommentsClicked=true;dijit.byId('dialogApprove').show();return false;"></s:submit></td>
				<td align="right"><s:submit id="approveExpenseNextBtn" theme="simple" onclick="buttonApproveNextClicked = true;ajaxApproveExpense();" value="Approve-Next" cssStyle="display:%{display.displayApproveNextButton}" disabled = "%{display.disableApproveNextButton}"></s:submit></td>
				<td align="right"><s:submit id="approveExpenseSkipBtn" theme="simple" onclick='approveSkip("skip");' value="Skip" cssStyle="display:%{display.displayApproveSkipButton}" disabled = "%{display.disableApproveSkipButton}"></s:submit>
				</td> 
				<td align="right"><s:submit id="rejectExpenseBtn" theme="simple" value="Reject" cssStyle="display:%{display.displayRejectButton}" disabled= "%{display.disableRejectButton}" onclick="dijit.byId('dialogReject').show();return false;"></s:submit></td>
				<td align="right">
				 <s:submit onclick="openSummaryReportUrl();" theme="simple" value="Print Summary"></s:submit></td>
				 <td align="right"><s:submit onclick="openDetailReportUrl();" theme="simple" value="Print Detail"></s:submit>
				 <td align="right"><s:submit id="certifyExpenseBtn" theme="simple" value="Certify" cssStyle="display:%{display.displayCertifyButton}" disabled="%{display.disableCertifyButton}" onclick="javascript:clickCertify();return false;"></s:submit></td>
			</tr>
		</table>
		</div>
	</td>

</tr>
</table>
</fieldset>
<div style="display: none">
<s:textfield theme="simple"
	id="approverComments" value="%{display.approverComments}" />
</div>

<s:hidden id="expenseSummaryHiddenJson" value="%{jsonResponse}" />
<s:hidden id="expenseMasterId" value="%{expenseMaster.expmIdentifier}" />
<div dojoType="dijit.Dialog" id="dialogApprove" title="<b>Comments:</b>" execute="showCommentsAndSubmitApproval(arguments[0]);" style="border: 1px ; background: white">
    <table>
		<textarea dojoType="dijit.form.Textarea" id="expensecommentsapp" style="width:300px;height:150px;border-style:solid;border-color:black;border: 1px; background: white" class="counter charLimit.256" style="white-space: pre-wrap;" name="comments"></textarea>
        <span id='expensecommentsappCharCount' class='count'>256</span>
       <button dojoType=dijit.form.Button type="submit">OK</button>
    </table>
</div>
<div dojoType="dijit.Dialog" id="dialogReject" title="<b>Comments:</b>" execute="showCommentsAndReject(arguments[0]);" style="border: 1px ; background: white">
    <table>
		<textarea dojoType="dijit.form.Textarea"  id="expensecommentsrej" style="width:300px;height:150px;border-style:solid;border-color:black;border: 1px; background: white" class="counter charLimit.256" style="white-space: pre-wrap;" name="comments"></textarea>
        <span id='expensecommentsrejCharCount' class='count'>256</span>
       <button dojoType=dijit.form.Button type="submit">OK</button>
    </table>
</div>
<div dojoType="dijit.Dialog" id="dialogCommentsAndSubmit" title="<b>Comments:</b>" execute="showCommentsAndSubmit(arguments[0]);" style="border: 1px ; background: white">

    <table>
		<textarea dojoType="dijit.form.Textarea" id="commentsAfterModifyId" style="width:300px;height:150px;border-style:solid;border-color:black;border: 1px; background: white" class="counter charLimit.256"  style="white-space: pre-wrap;" name="commentAfterModify" ></textarea>
        <span id='commentsAfterModifyIdCharCount' class='count'>256</span>
       <button dojoType=dijit.form.Button type="submit">OK</button>
    </table>
</div>
<div dojoType="dijit.Dialog" id="dummyDialogToDisableScreenWhileProcessing">
</div>
</body>
</html>
