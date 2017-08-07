<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
   <s:form id="TripIdForm" method="post" theme="simple">
 	<fieldset style="border:1px solid black; width:95%">
  	<legend style="color:black;font-weight:bold;font-size:9pt"><em>Expense Details</em></legend>
    <table style="margin:1%; width:80% ; border: 1px">
     <tr>
      <td align="left">
      
       <s:label>&nbsp;From<sup>*</sup>&nbsp;</s:label>
       <s:textfield id="f_fromDateField" name="expenseMaster.expDateFrom" onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
	   <img src="${pageContext.request.contextPath}/image/calendar.gif" id="f_fromDateTrigger" alt="Choose from date">
      </td>
      <td align="left">
       <s:label>&nbsp;To<sup>*</sup>&nbsp;</s:label>
       <s:textfield id="f_toDateField" name="expenseMaster.expDateTo" onblur="syncDate(this); return true;" maxlength="10"></s:textfield>
       <img src="${pageContext.request.contextPath}/image/calendar.gif" id="f_toDateTrigger" alt="Choose to date">
      </td>
     </tr>
    </table>
    <table style="margin:1%; width:85%" border=0>
     <tr>
      <td>
       <s:label>&nbsp;Nature of official business<sup>*</sup>&nbsp;&nbsp;&nbsp;</s:label>
      </td>
      <td colspan="2">
	   <s:textfield id="bizNature" name="expenseMaster.natureOfBusiness" size="48" maxlength="80"></s:textfield>       
      </td>
	 </tr>
     <tr><td colspan="3">&nbsp;</td></tr>
     <tr>
      <td width="28%">
       <s:label>&nbsp;User event description&nbsp;</s:label>
      </td>
      <td colspan="2" nowrap="nowrap">
	   <s:textarea id="exp_desc" name="expenseMaster.comments" cssClass="counter charLimit.255"  style="white-space: pre-wrap;" cols="50" rows="3"></s:textarea>
	   <span id='descCharCount' class='count'>255</span>
      </td>
	 </tr>
	 <tr><td colspan="3">&nbsp;</td></tr>
     <tr>
      <td>
       <s:label>&nbsp;Expense type<sup>*</sup>&nbsp;</s:label>
      </td>
      <td colspan="1">
	   <s:radio id="expenseType" list="#{'Y':'&nbsp;Travel&nbsp;&nbsp;&nbsp;&nbsp;', 'N':'&nbsp;Non-Travel'}" name="expenseMaster.travelInd" value="%{defaultTravelIndicator}" cssStyle="margin:1px"></s:radio>
	   &nbsp;&nbsp;	   
  	   		<s:label id = "PDFCheckBoxLabel1" value="PDF"  />
       		<s:checkbox id="PDFCheckBox" name="expenseMaster.pdfInd" > </s:checkbox>       
      </td>
	 </tr>
	</table>
	<table style="margin:1%;" width="100%">
     <tr>
      <td align="left">
       <s:label>&nbsp;Out-of-state travel:&nbsp;</s:label>
       <s:checkbox id="outOfStateCheckBox" label="Out-of-State" name="expenseMaster.outOfStateInd"></s:checkbox>
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
	 		<s:submit action="PreviousExpenseRevision" value="<<" id="prevRevBtn" onclick="addTabPrefElement(this.form, 'idTab'); return true;"/>
	 		<s:submit action="NextExpenseRevision" value=">>" id="nextRevBtn" onclick="addTabPrefElement(this.form, 'idTab'); return true;"/>
	 		<s:label value="Version: "></s:label>
	 		<s:label id="revNo" name="expenseMaster.revisionNumber"></s:label>
	 		&nbsp;&nbsp;&nbsp;&nbsp;
	 	</td>
	 	<td align="left" valign="middle"><div id="errorMsg" style="font-size:9pt;"/></td>
      	<td align="right" nowrap="nowrap">
       		<input id="saveBtn" type="button" value="Save" onclick="saveExpense();">
       		<input id="modifyBtn" type="button" value="Modify" onclick="modifyExpense();">
       		<input id="adjustmentBtn" type="button" value="Exp Dist Adjustment"  style="display:${adjustmentBtnVisibility};" 
       		onclick="javascript:showAdjustmentDialog();"
       		 >
	  	</td>
	 </tr>
	 <tr><td colspan="3"><br></td></tr>
    </table>
    
   </s:form>
    
  <!-- hidden text field to control view state -->
	<div style="display:none">
		<s:textfield id="viewJsonId" name="viewJson"/>
		<s:textfield id="expenseEventId_hidden" name="expenseMaster.expevIdentifier.expevIdentifier"/>
		<s:textfield id="lastSuccessfulOperation" value="%{#session.LastSuccessfulOperation}"/>		
	</div>

<div dojoType="dijit.Dialog" id="multipleAppointmentsDialog" title="Multiple appointments found!"
    style="border: 1px ; background: white"></div>
    
<div dojoType="dijit.Dialog" id="dialogAdjustmentDialog" title="Expense Distribution Adjustments" 
		style="border: 1px ; background: white; display: none">
		
    <table border = "0">
    	<tr>
    		<td  colspan = "2"><b>Distribution Adjustments for this expense.</b>
    		</td>
    	</tr>
    	<tr>
	    	<td align="left" nowrap="nowrap">${adjustmentList}			
		    </td>
    	</tr>
    	
		<tr>
			<td>&nbsp;</td>
		</tr>		
		<tr>
			<td><b>Differences in R*Stars</b></td>
		</tr>		
		<tr>
			<td>${rstarsList}</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
					
		<tr>
	    	<td  align = "center">	    		
	    		<button dojoType=dijit.form.Button id="btnClose" onclick="closeClick();">Close</button>
	    	</td>
	   	</tr>
	   	
		<tr>
			<td align="middle" colspan="2"><div id="errorMsg" style="font-size:9pt;color:red" >&nbsp;</div></td>
		</tr>
    </table>
</div>
