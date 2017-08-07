<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Advance Page</title>


<link href="${pageContext.request.contextPath}/css/page.css"
	rel="stylesheet" type="text/css" />





<script type="text/javascript">
	dojo.require("dojo.parser");
	dojo.require("dijit.form.ComboBox");
	dojo.require("dijit.form.CheckBox"); 
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.form.ValidationTextBox");
	dojo.require("dijit.form.Textarea");
	dojo.require("dijit.Dialog");
	dojo.require("dijit.form.Button");
	
</script>



</head>

<body>

<s:form name="advance" theme="simple" id="advance">
 	<span style="color: red"> * indicates required field</span>
<!-- chandana -->
<table  width="100%" >
<tr>
	<td align="right" >
	 	<span id="spanTreqEventId" style="display:none"> <B><s:label
							id="labelTravelRequisition" theme="simple">Travel Requisition:&nbsp;</s:label>
						<a id="treqEventId" "tabindex="-1"
						href="TravelRequisitionAction.action?treqEventId=<s:property value="treqEventId" />">${treqEventId}</a>
			</span> 	 	 
	</td>
</tr>
</table>
<!-- chandana -->
	<fieldset style="border: solid 1px; padding-left: 5px" width="90%">
	<legend style="color: black">&nbsp;Advance Request</legend>

	<table class="tableMainContent" width="90%"
		style="border-collapse: separate; border-spacing: 10px">
		<tr>
			<td width="112"><s:label>
				<span style="color: red"><b>*</b> </span>Request Date: </s:label></td>
			<td width="130"><s:textfield id="requestDate" 
				name="display.requestDate" size="10" maxlength="10" onblur="syncDate(this); return true;"/> <img
				src='/TimeAndExpense/image/calendar.gif'
				id="requestDateSelector" alt="Choose dates"></img></td>
			<td width="86"><s:label>
				<span style="color: red">* </span>Amount: </s:label></td>
			<td width="150">$<s:textfield id="dollarAmount"
				name="display.dollarAmountForEditing" size="10" maxlength="8"/>
				</td>
			
			<td width="227"><s:label>Liquidated By Expense: </s:label><br>
			<s:label>Liquidated By Deposit: </s:label></td>
			<td width="125"><span style="float: left">$</span> <span
				style="float: right" id="liquidatedByExpense">
				<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${display.liquidatedByExpense}"/></span><br>
			<span style="float: left">$</span> <span style="float: right" id="liquidatedByDeposit">
			<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${display.liquidatedByDeposit}"/>
			</span><br>
			<hr width="50px" color="#FFF" align="right" />
			</td>

		</tr>
		<tr>
			<td width="112"><s:label>
				<span style="color: red">* </span>Advance From: </s:label></td>
			<td width="116"><s:textfield id="fromDate" theme="simple"
				name="display.fromDate"
				size="10" maxlength="10" onblur="syncDate(this); return true;"/> <img
				src='/TimeAndExpense/image/calendar.gif'
				id="fromDateSelector" alt="Choose dates"></img></td>
			<td width="86"><s:label>
				<span style="color: red">* </span>Advance To : </s:label></td>
			<td width="127">&nbsp;&nbsp;<s:textfield id="toDate"
				name="display.toDate"
				size="10" maxlength="10" onblur="syncDate(this); return true;"/> <img
				src='/TimeAndExpense/image/calendar.gif'
				id="toDateSelector" alt="Choose dates"></img></td>
			<td width="227"><s:label>Current Outstanding Advance: </s:label></td>
			<td width="125"><span style="float: left">$</span> <span id="amountOutstanding"
				style="float: right"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${display.amountOutstanding}"/></span></td>
		</tr>
		<tr>
			<td width="112"><s:label><span style="color: red">* </span>Reason: </s:label></td>
			<td colspan="3"><s:textarea name="display.advanceReason" cssClass="counter charLimit.255"  style="white-space: pre-wrap;" 
				label="Reason" cols="50" rows="3" id="advanceReason"></s:textarea>
				<span id='descCharCount' class='count'>255</span>
				</td>
			<td width="227"><s:label>Permanent: </s:label></td>
			<td align="left" width="125">&nbsp;&nbsp;<s:checkbox theme="simple" id="permanentAdvInd"
				name="display.permanentAdvInd" value="%{display.permanentAdvInd}"></s:checkbox></td>

		</tr>

		<tr>
			<td colspan="2"><s:label>Was Advance Issued Manually? </s:label>
			</td>
			<td width="86"><select dojoType="dijit.form.FilteringSelect"
				id="manualWarrantIssdInd" name="display.manualWarrantIssdInd"
				autocomplete="true" style="width: 80px" disabled="disabled">
				<option value=" " selected="selected">&nbsp;</option>
				<option value="Y">Yes</option>
				<option value="N">No</option>
			</select></td>


			<td width="127"></td>
			<td width="227"><s:label>Cumulative Manual Deposit(s): </s:label></td>
			<td width="125">$<s:textfield label="Cumulative Manual Deposits"
				id="manualDepositAmount" name="display.manualDepositAmount"
				size="10" maxlength="8" disabled="true" cssStyle="background-color:white"/></td>
		</tr>
		<tr>
			<td colspan="2"><s:label>Manual Warrant Doc Number: </s:label></td>
			<td width="86"><s:textfield label="Manual Warrant Doc Number"
				id="manualWarrantDocNum" name="display.manualWarrantDocNum"
				size="10" disabled="true" cssStyle="background-color:white" maxlength="8"/></td>
			<td width="127"></td>
			<td width="227"><s:label>Current Manual Deposit Doc #: </s:label></td>
			<td width="125">&nbsp;&nbsp;<s:textfield id="manualDepositDocNum"
				name="display.manualDepositDocNum" size="10"
				cssStyle="margin-left:8px;background-color:white" maxlength="8" disabled="true"/></td>
		</tr>
	</table>

	<input type="hidden" name="approvalComments" id="approvalComments"
		value="" /></fieldset>

	<fieldset style="border: solid black 1px; padding-left: 5px"><legend
		style="color: black">&nbsp;Coding Block</legend>

	<div id="dept_tab" class="tundra">

	<table id="cbTable">
	</table>
	<div id="default">
	<table>
		<tr>
			<td width="65px"><b>PL Std</b></td>
			<td width="50px" align="left"><s:property
				value="defaultCodingBlock.appropriationYear" /></td>
			<td width="65px" align="left"><s:property
				value="defaultCodingBlock.indexCode" /></td>
			<td width="90px" align="left"><s:property
				value="defaultCodingBlock.pca" /></td>
			<td width="50px" align="left"><s:property
				value="defaultCodingBlock.grantNumber" /></td>
			<td width="50px" align="left"><s:property
				value="defaultCodingBlock.grantPhase" /></td>
			<td width="70px" align="left"><s:property
				value="defaultCodingBlock.agencyCode1" /></td>
			<td width="90px" align="left"><s:property
				value="defaultCodingBlock.agencyCode2" /></td>
			<td width="50px" align="left"><s:property
				value="defaultCodingBlock.agencyCode3" /></td>
			<td width="70px" align="left"><s:property
				value="defaultCodingBlock.projectNumber" /></td>
			<td width="50px" align="left"><s:property
				value="defaultCodingBlock.projectPhase" /></td>
			<td width="50px" align="center"><s:property
				value="defaultCodingBlock.multipurposeCode" /></td>
		</tr>
	</table>
	</div>

	</div>
	</fieldset>
	<br>

	<div>
	<table>
		<tr>
			<td><s:submit id="getPrevious" value="<<" 
			action="getPreviousRevisionAction" disabled="%{display.disablePreviousButton}"></s:submit>
			</td>
			<td><s:submit id="getNext" value=">>"
				action="getNextRevisionAction"
				disabled="%{display.disableNextButton}"></s:submit></td>
			<td width="100px">Version <span id="revisionNumber"><s:property
				value="advanceMaster.revisionNumber" /></span></td>
			<td width="500px"><span id="statusArea"
				style="font-style: italic; color: red;"></span></td>
			<td align="right"  nowrap="nowrap"><s:submit id="buttonSave"
				onclick="saveAdvance();return false;" value="Save"
				disabled="%{display.disableSaveButton}" cssStyle="display:%{display.displaySaveButton}"></s:submit> 
				
				<s:submit
				id="buttonSubmit" onclick="submitAdv();return false;"
				value="Submit" disabled="%{display.disableSubmitButton}" cssStyle="display:%{display.displaySubmitButton}">
				</s:submit>
				
			<s:submit id="buttonApprove" onclick="approveAdvance();return false;"
				value="Approve" cssStyle="display:%{display.displayApproveButton}" disabled="%{display.disableApproveButton}"></s:submit> 
				<s:submit
				id="buttonApproveWithComments"
				onclick="buttonApproveWithCommentsClicked=true;dijit.byId('dialog1').show();return false;"
				value="Approve With Comments" cssStyle="display:%{display.displayApproveWithCommentsButton}" disabled="%{display.disableApproveWithCommentsButton}"></s:submit>
				<s:submit id="buttonApproveNext" onclick="buttonApproveNextClicked = true;approveAdvance();return false;"
				value="Approve-Next" cssStyle="display:%{display.displayApproveNextButton}" disabled="%{display.disableApproveNextButton}"></s:submit> 
				<s:submit id="buttonApproveSkip" onclick='approveSkip("skip");return false;'
				value="Skip" cssStyle="display:%{display.displayApproveSkipButton}" disabled="%{display.disableApproveSkipButton}"></s:submit>  
				<s:submit
				id="buttonReject" onclick="buttonRejectClicked=true;dijit.byId('dialogReject').show();return false;"
				value="Reject" cssStyle="display:%{display.displayRejectButton}" disabled="%{display.disableRejectButton}"></s:submit> 
				<s:submit
				id="buttonModify"
				onclick="modifyForm();this.disabled=true;return false;"
				value="Modify" disabled="%{display.disableModifyButton}"></s:submit>
			</td>


		</tr>
	</table>





	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
		style="margin-left: 350px"> <!--  
	<input
	type="button" value="Save" onclick="saveAdvance();return false;" /></input> 
	
	<input
	type="button" value="Submit" onclick="submitAdvance();return false;" /></input>
	<input type="button"
	value="Modify" onclick="this.disabled=true;enableSave()" /> 
   <button type="button" onclick="saveAdvance();return false;">Save</button>
   <button onclick="submitAdvance();return false;">Submit</button>
   <button onclick="modifyForm();return false;" >Modify</button>  --> </span></div>
   
   
</s:form>

<div style="display: none"><s:textfield theme="simple"
	id="no_of_coding_blocks_hidden" value="%{noOfCodingBlocks}" /> <s:textfield
	theme="simple" id="cb_options_hidden" value="%{cbOptions}" /> <s:textfield
	theme="simple" id="json_result_hidden" value="%{result}" /> <s:textfield
	theme="simple" id="selected_Coding_Block_data"
	value="%{selectedCodingBlockData}" /> <s:textfield theme="simple"
	id="status" value="%{display.viewMode}" />
	<s:textfield theme="simple"
	id="approverComments" value="%{display.approverComments}" />
	<s:textfield theme="simple"
	id="approverComments" value="%{display.approverComments}" />
	<s:textfield theme="simple"
	id="errorsJsonAdvance" value="%{errorsJsonAdvance}" />
	<s:textfield theme="simple"
	id="accessMode" value="%{accessMode}" />
	<s:textfield theme="simple"
	id="moduleId" value="%{display.moduleId}" />
	<s:textfield theme="simple"
	id="manualWarrantIssd" value="%{display.manualWarrantIssdInd}" />
	<s:textfield theme="simple"
	id="previouslyPROC" value="%{previouslyPROC}" />

	</div>
	
<div dojoType="dijit.Dialog" id="multipleAppointmentsDialog" title="Multiple appointments found!"
    class="soria" style="border: 1px ; background: white"></div>

<div dojoType="dijit.Dialog" id="dialog1" title="<b>Comments:</b>" execute="showCommentsAndSubmitApproval(arguments[0]);" style="border: 1px ; background: white">

    <table>
		<textarea dojoType="dijit.form.Textarea" id="advancecomments" style="width:300px;height:150px;border-style:solid;border-color:black;border: 1px; background: white" name="comments" class="counter charLimit.256"  style="white-space: pre-wrap;" >
        </textarea>
        <span id='advanceCommentsCharCount' class='count'>256</span>
       <button dojoType=dijit.form.Button type="submit">OK</button>
    </table>
</div>

<div dojoType="dijit.Dialog" id="dialogReject" title="<b>Comments:</b>" execute="showCommentsAndSubmitApproval(arguments[0]);" style="border: 1px ; background: white">

    <table>
		<textarea dojoType="dijit.form.Textarea" id="rejectComments" style="width:300px;height:150px;border-style:solid;border-color:black;border: 1px; background: white" name="comments" class="counter charLimit.256"  style="white-space: pre-wrap;" >
        </textarea>
        <span id='advanceRejectCommentsCharCount' class='count'>256</span>
       <button dojoType=dijit.form.Button type="submit">OK</button>
    </table>
</div>

<div dojoType="dijit.Dialog" id="dialogCommentsAndSubmit" title="<b>Comments:</b>" execute="showCommentsAndSubmit(arguments[0]);" style="border: 1px ; background: white">

    <table>
		<textarea dojoType="dijit.form.Textarea" id="commentsAfterModify" style="width:300px;height:150px;border-style:solid;border-color:black;border: 1px; background: white" name="commentAfterModify" class="counter charLimit.256"  style="white-space: pre-wrap;" >
        </textarea>
        <span id='advanceCommentsAfterModifyCharCount' class='count'>256</span>
       <button dojoType=dijit.form.Button type="submit">OK</button>
    </table>
</div>
</body>
</html>
