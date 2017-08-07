<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
   <s:form id="TripIdForm" method="post" theme="simple">
 	<fieldset style="border:1px solid black; width:95%">
  	<legend style="color:black;font-weight:bold;font-size:9pt"><em>Expense Details</em></legend>
    <table style="margin:1%; width:90% ; border: 1px">
      <tr>
      <td align="right" width="96">      
       <s:label>Request Date<sup>*</sup>&nbsp;</s:label>
       </td>
       <td width="126">
       <s:textfield id="f_requestDateField" name="treqMaster.treqDateRequest" onblur="syncDate(this); return true;" maxlength="10" size="8" cssStyle="readOnly:true;color:grey"></s:textfield>
	   <img src="${pageContext.request.contextPath}/image/calendar.gif" id="f_requestDateTrigger" alt="Choose from date">
      </td>
      <td align="right">
       <s:label>Related Expense:&nbsp;</s:label>
       </td>
       <td>
	<a tabindex="-1" id="expenseEventId" href="ExpenseAction.action?expenseEventId=<s:property value="treqMaster.treqeIdentifier.expevIdentifier" />" >${treqMaster.treqeIdentifier.expevIdentifier}</a>
       <!--<s:textfield id="relatedExpense" name="treqMaster.treqeIdentifier.expevIdentifier" size="8" readonly="true" cssStyle="border:none" ></s:textfield> -->
      </td>
      <td>
       <s:label>Related Advance:&nbsp;</s:label>
       </td>
       <td>
       <a tabindex="-1" id="relatedAdvance" href="AdvanceAction.action?advanceEventId=<s:property value="treqMaster.treqeIdentifier.adevIdentifier" />" >${treqMaster.treqeIdentifier.adevIdentifier}</a>
       <!--<s:textfield id="relatedAdvance" name="treqMaster.adevIdentifier" size="8"></s:textfield>-->      
      </td>
     </tr>
     <tr>
      <td align="right" width="96">      
       <s:label>From<sup>*</sup>&nbsp;</s:label>
       </td>
       <td width="126">
       <s:textfield id="f_fromDateField" name="treqMaster.treqDateFrom" onblur="syncDate(this); return true;" maxlength="10" size="8"></s:textfield>
	   <img src="${pageContext.request.contextPath}/image/calendar.gif" id="f_fromDateTrigger" alt="Choose from date">
      </td>
      <td align="right">
       <s:label>To<sup>*</sup>&nbsp;</s:label>
       </td>
       <td>
       <s:textfield id="f_toDateField" name="treqMaster.treqDateTo" onblur="syncDate(this); return true;" maxlength="10" size="8"></s:textfield>
       <img src="${pageContext.request.contextPath}/image/calendar.gif" id="f_toDateTrigger" alt="Choose to date">
      </td>
      <td>
        <s:label id="labelRequestAdvance" value="Request Advance:"></s:label>
        </td>
       <td>
       <s:checkbox theme="simple" id="requestAdvance" label="Request Advance" name="treqMaster.requestAdvance" cssStyle="display:%{displayAdvance}"></s:checkbox>
       
      </td>
     </tr>
     <tr>
            <td colspan="6" height="3px"></td>
      </tr>
     
          <tr>
                <td>
         <s:label>Destination<sup>*</sup>&nbsp;</s:label>
         </td>
       <td>
       <s:textfield id="destination" name="treqMaster.destination" maxlength="20" size="15"></s:textfield>
      </td>
      <td align="right">
      
       <s:label>Office/Program&nbsp;</s:label>
       </td>
       <td colspan="2">
       <s:textfield id="officeProgram" name="treqMaster.officeProgram" maxlength="55" size="50"></s:textfield>
      </td>
     </tr>
    </table>
    <table>
    <tr>
    <td>
    <table style="margin:1%; width:90%" width="323">
     <tr>
      <td width="140">
       <s:label>Transportation Via<sup>*</sup>&nbsp;&nbsp;&nbsp;</s:label>
      </td>
					<td colspan="2" height="27" width="70"><s:textfield
						id="transportationVia" name="treqMaster.transportationVia"
						size="48" maxlength="80"></s:textfield></td>
				</tr>
     <tr>
					<td colspan="3" height="20">&nbsp;</td>
				</tr>
     <tr>
      	<td >
       		<s:label>&nbsp;Nature of official business<sup>*</sup>&nbsp;&nbsp;&nbsp;</s:label>
     	</td>
      <td colspan="2">
 
	   <s:textfield id="exp_desc"  name="treqMaster.natureOfBusiness" cssClass="counter charLimit.80" size="48" maxlength="80">      
       </s:textfield> 
        <span id='descCharCount' class='count' style="text-align:right" >80</span>
      
      </td>
	 </tr>
	 <tr><td colspan="3">&nbsp;</td></tr>
     <tr>
      <td width="28%">
       <s:label>&nbsp;User event description&nbsp;</s:label>
      </td>
      <td colspan="2" nowrap="nowrap">
	   <s:textarea id="treq_desc" name="treqMaster.comments" cssClass="counter charLimit.255"  style="white-space: pre-wrap;"  cols="50" rows="3"></s:textarea>
	   <span id='descCharCount' class='count'>255</span>
      </td>
	 </tr>
	</table>
	</td>
	<td>
	<table>
	<tr>
	<td valign="top">
	<b>Estimated Expenses:</b>
	</td>
	<td>
	</td>
	</tr>
		<tr>
	<td valign="top">
	Transportation:
	</td>
	<td>
	$
	<s:textfield id="transportationAmount" name = "treqDetails.transportationAmountDisplay" onChange="setDollarAmount(this);" size="6" maxlength="8"></s:textfield>
	</td>
	</tr>
			<tr>
	<td valign="top">
	Airfare:
	</td>
	<td>
	$
	<s:textfield id="airfareAmount" name = "treqDetails.airfareAmountDisplay" onChange="setDollarAmount(this);" size="6" maxlength="8"></s:textfield>
	</td>
	</tr>
			<tr>
	<td valign="top">
	Lodging:
	</td>
	<td>$
	<s:textfield id="lodgingAmount" name = "treqDetails.lodgingAmountDisplay" onChange="setDollarAmount(this);" size="6" maxlength="8"></s:textfield>
	</td>
	</tr>
	<tr>
	<td valign="top">
	Meals:
	</td>
	<td>
	$
	<s:textfield id="mealsAmount" name = "treqDetails.mealsAmountDisplay" onChange="setDollarAmount(this);" size="6" maxlength="8"></s:textfield>
	</td>
	</tr>
	<tr>
	<td valign="top">
	Registration Fees:
	</td>
	<td>
	$
	<s:textfield id="registAmount" name = "treqDetails.registAmountDisplay" onChange="setDollarAmount(this);" size="6" maxlength="8"></s:textfield>
	</td>
	</tr>
	<tr>
	<td valign="top">
	Other Costs:
	</td>
	<td>
	$
	<s:textfield id="otherAmount" name = "treqDetails.otherAmountDisplay" onChange="setDollarAmount(this);" size="6" maxlength="8"></s:textfield>
	</td>
	</tr>
				<tr>
	<td valign="top">
	Total Costs:
	</td>
	<td>
	$
	<s:textfield id="dollarAmount" name = "treqDetails.dollarAmountDisplay" cssStyle="border:none;color:gray;font-weight:bold;text-align:center" readonly="true" size="8" maxlength="8"></s:textfield>
	</td>
	</tr>
	</table>
	</td>
	</tr>
	</table>
	<table style="margin:1%;" width="100%">
     <tr>
      <td align="left">
       <s:label>&nbsp;Out-of-state travel:&nbsp;</s:label>
       <s:checkbox id="outOfStateCheckBox" label="Out-of-State" name="treqMaster.outOfStateInd"></s:checkbox>
      </td>
      <td valign="middle" >
       <s:label>&nbsp;Out-of-state <br>auth code(s):&nbsp;</s:label>
      </td>
      <td valign="middle" align="left">
            <div style="width:500px;overflow-x:scroll; overflow-y:scroll; 
overflow: -moz-scrollbars-horizontal;height:100px">
       <s:select id="outOfStateAuthCodes" name="selectedAuthCodes" list="authCodes" listKey="stacIdentifier" listValue="description" 
       			multiple="true" size="8"></s:select>
       			</div>
      </td>
	 </tr>
	 <tr><td nowrap="nowrap" style="color:red;font-size:9pt">* - Required fields</td></tr>
	 </table>
	 </fieldset>
	 <table style="width:90%">
	 <tr><td colspan="3"><br/></td></tr>
	 <tr>
	 	<td align="left" nowrap="nowrap">
	 		<s:submit action="PreviousTravelRequisitionRevision" value="<<" id="prevRevBtn" onclick="addTabPrefElement(this.form, 'idTab'); return true;"/>
	 		<s:submit action="NextTravelRequistionRevision" value=">>" id="nextRevBtn" onclick="addTabPrefElement(this.form, 'idTab'); return true;"/>
	 		<s:label value="Version: "></s:label>
	 		<s:label id="revNo" name="treqMaster.revisionNumber"></s:label>
	 		&nbsp;&nbsp;&nbsp;&nbsp;
	 	</td>
	 	<td align="left" valign="middle"><div id="errorMsg" style="font-size:9pt;"/></td>
      	<td align="right" nowrap="nowrap">
       		<!--  <input id="saveBtn" type="button" value="Save" onclick="saveTravelRequisition();">
       		<input id="submitBtn" type="button" value="Submit" onclick="submitTravelRequisition();"> -->
       		<s:submit id="buttonSave" onclick="saveTravelRequisition();return false;" value="Save" 
       		cssStyle="display:%{displaySaveButton}" disabled="%{disableSaveButton}"></s:submit>
       		<s:submit id="buttonSubmit" type="button" value="Submit" onclick="submitTravelRequisition();return false;"
       		cssStyle="display:%{displaySubmitButton}" disabled="%{disableSubmitButton}"></s:submit>
       		<s:submit id="buttonApprove" onclick="approveTravelRequisition();return false;"
				value="Approve" class="ie11_button"  cssStyle="display:%{displayApproveButton}" disabled="%{disableApproveButton}"></s:submit> 
				<s:submit
				id="buttonApproveWithComments"
				onclick="buttonApproveWithCommentsClicked=true;dijit.byId('dialog1').show();return false;"
				value="Approve With Comments" cssStyle="display:%{displayApproveWithCommentsButton}" disabled="%{disableApproveWithCommentsButton}"></s:submit> 
				<s:submit id="buttonApproveNext" onclick="buttonApproveNextClicked = true;approveTravelRequisition();return false;"
				value="Approve-Next" cssStyle="display:%{displayApproveNextButton}" disabled="%{disableApproveNextButton}"></s:submit> 
				<s:submit id="buttonApproveSkip" onclick='approveSkip("skip");return false;'
				value="Skip" cssStyle="display:%{displayApproveSkipButton}" disabled="%{disableApproveSkipButton}"></s:submit> 
				<s:submit
				id="buttonReject" onclick="buttonRejectClicked=true;dijit.byId('dialogReject').show();return false;"
				value="Reject" cssStyle="display:%{displayRejectButton}" disabled="%{disableRejectButton}"></s:submit>
       		<input id="modifyBtn" type="button" value="Modify" onclick="modifyTravelRequisition();"">
       		<s:submit id="buttonExpense" onclick="createExpense();return false;" value="Create Expense" theme="simple" cssStyle="display:%{displayCreateExpenseButton}"></s:submit>
       		<s:submit id="buttonPrint" onclick="openReportUrl();return false;" value="Print" theme="simple"></s:submit>
	  	</td>
	 </tr>
	 <tr><td colspan="3"><br></td></tr>
    </table>
    
   </s:form>
   
   <div style="display: none">
	<s:textfield theme="simple"
	id="approverComments" value="%{display.approverComments}" />
	<s:textfield theme="simple"
	id="approverComments" value="%{display.approverComments}" />
	<s:textfield theme="simple"
	id="errorsJsonAdvance" value="%{errorsJsonAdvance}" />
	<s:textfield theme="simple"
	id="accessMode" value="%{accessMode}" />
	<s:textfield theme="simple"
	id="moduleId" value="%{#session.leftNavCurrentModuleId}" />

	</div>
    
  <!-- hidden text field to control view state -->
	<div style="display:none">
		<s:textfield id="viewJsonId" name="viewJson"/>
		<s:textfield id="treqEventId_hidden" name="treqMaster.treqeIdentifier.treqeIdentifier"/>
		<s:textfield id="lastSuccessfulOperation" value="%{#session.LastSuccessfulOperation}"/>		
	</div>

<div dojoType="dijit.Dialog" id="multipleAppointmentsDialog" title="Multiple appointments found!"
    style="border: 1px ; background: white"></div>
    
    <div dojoType="dijit.Dialog" id="dialog1" title="<b>Comments:</b>" execute="showCommentsAndSubmitApproval(arguments[0]);" style="border: 1px ; background: white">

    <table>
		<textarea dojoType="dijit.form.Textarea" id="approveComments" style="width:300px;height:150px;border-style:solid;border-color:black;border: 1px; background: white" name="comments" class="counter charLimit.256"  style="white-space: pre-wrap;" >
        </textarea>
        <span id='travelRequisitionApproveCommentsCharCount' class='count'>256</span>
       <button dojoType=dijit.form.Button type="submit">OK</button>
    </table>
</div>

<div dojoType="dijit.Dialog" id="dialogReject" title="<b>Comments:</b>" execute="showCommentsAndSubmitApproval(arguments[0]);" style="border: 1px ; background: white">

    <table>
		<textarea dojoType="dijit.form.Textarea" id="rejectComments" style="width:300px;height:150px;border-style:solid;border-color:black;border: 1px; background: white" name="comments" class="counter charLimit.256"  style="white-space: pre-wrap;" >
        </textarea>
        <span id='travelRequisitionRejectCommentsCharCount' class='count'>256</span>
       <button dojoType=dijit.form.Button type="submit">OK</button>
    </table>
</div>

<div dojoType="dijit.Dialog" id="dialogCommentsAndSubmit" title="<b>Comments:</b>" execute="showCommentsAndSubmit(arguments[0]);" style="border: 1px ; background: white">

    <table>
		<textarea dojoType="dijit.form.Textarea" id="commentsAfterModify" style="width:300px;height:150px;border-style:solid;border-color:black;border: 1px; background: white" name="commentAfterModify" class="counter charLimit.256"  style="white-space: pre-wrap;" >
        </textarea>
        <span id='travelRequisitionCommentsAfterModifyCharCount' class='count'>256</span>
       <button dojoType=dijit.form.Button type="submit">OK</button>
    </table>
</div>
