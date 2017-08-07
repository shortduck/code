<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>
	
	<!-- expense details grid -->
	<fieldset style="border:1px solid black; width:85%">
	<legend style="color:black;font-weight:bold;font-size:9pt"><em>Expense Summary</em></legend>
		<div style="float:left; width:70%">
		<!--  expense details grid -->
		<table id="expenseDetailsGrid" dojotype="dojox.grid.EnhancedGrid" query="{lineItemNo: '*'}" store="expenseDetailsGridStore" 
				selectionMode="single" style="width:100%; height:135px; left:2px; position:relative">
			<thead>
				<tr>
					<th field="lineItemNo" width="8%"><em>Line</em></th>
					<th field="expenseDate" width="15%"><em>Date</em></th>
					<th field="expenseTypeDesc" width="50%"><em>Expense Type</em></th>
					<th field="overnightInd" width="15%"><em>Overnight Flag</em></th>
					<th field="amount" width="11%" formatter="formatCurrencyWithNoDollarSign"><div style="text-align:right;"><em>Amount($)</em></div></th>
				</tr>
			</thead>
		</table>
		</div>
		<div style="float:right; width:25%">
	   	<table>
	   		<tr>
	   			<td>Total expenses:&nbsp;</td>
	   			<td><input type="text" id="totalExpenseAmtId" style="border : 0px" size="8" value="0.00"></td>
	   		</tr>
	   		<tr><td colspan="2" height="5px"></td></tr>
	   		<tr>
	   			<td>Advance Outstanding:&nbsp;</td>
	   			<td><input type="text" id="advOutstandingAmtId" style="border : 0px" size="8" value="0.00"></td>
	   		</tr>
	   		<tr><td colspan="2" height="5px"></td></tr>
	   		<tr>
	   			<td colspan="2" align="center"><input id="ed_add"  type="button" value="&nbsp;Add&nbsp;&nbsp;" onclick="resetExpenseDetailsForm()"></td>
			</tr>
			<tr><td colspan="2" height="4px"></td></tr>
			<tr>
				<td colspan="2" align="center"><input id="ed_delete" type="button" value="Delete" onclick="deleteExpenseDetail()"></td>
			</tr>
		</table>
		</div>
	</fieldset>

	<br/>
	
	<!-- expense detail entry form -->
	<s:form id="ed_form" action="" method="post" theme="simple">
	<fieldset style="border:1px solid black; width:85%">
	<legend style="color:black;font-weight:bold;font-size:9pt">
		<em>Expense Details</em>
		<span style="font-weight:normal;font-size:8pt">&nbsp;(<sup>*</sup> - Required fields)</span>
	</legend>
		<table border="0">
			<tr><td height="2px"></td></tr>
			<tr>
				<td align="left" nowrap="nowrap">&nbsp;Date<sup>*</sup>&nbsp;
			    	<input type="text" id="fromDate" name="expenseMaster.expDateFrom" onblur="syncFromDate();" size="10"/>
					<img src="${pageContext.request.contextPath}/image/calendar.gif" id="fromDateSelector" alt="Choose dates"/>
		      	</td>
		      	<td align="right">
		      		&nbsp;&nbsp;&nbsp;From<sup>*</sup>&nbsp;
		      	</td>
		      	<td align="left" nowrap="nowrap">
		      		<input id="fromCity_dd" name="chosenFromCity" size="8"/>
		      		<input id="fromState_dd" name="chosenFromState" size="6"/>
		      	</td>
		      	<td align="right" nowrap="nowrap">
		      		<span>&nbsp;&nbsp;&nbsp;To<sup>*</sup>&nbsp;</span>
		      	</td>
		      	<td align="left" nowrap="nowrap">
		      		<input id="toCity_dd" name="chosenToCity" size="8"/>
		      		<input id="toState_dd" name="chosenToState" size="6"/>&nbsp;
		      	</td>
		      	<td><div id="SelectCityIndicator" style="font-size:8pt;color:red;"></div></td>
			</tr>
			<tr><td height="6px"></td></tr>
			<tr>
		      <td align="left"colspan="6">
		      	<table>
		      		<tr>
		      			<td>
		      			&nbsp;Round trip&nbsp;<input type="checkbox" id="roundTrip" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=" http://inside.michigan.gov/SBO/financial_mgmt/Policies%20and%20Procedures/Financial%20Management%20Guide/Part_IV_-_Payroll_and_Other_Employee_Payments/Ch6/Section_300_Exhibit_C.pdf" target="_blank">Overnight at Temporary Work Location?</a>&nbsp;&nbsp;<input id="overnight" value="" name="overnight" style="width:2em"/>	
		      			</td>
		      			<td>
		      				&nbsp;&nbsp;&nbsp;&nbsp;Departure&nbsp;<input id="departureTime" value="" name="departureTime" style="width:4em"/>&nbsp;<input id="departureTimePhase" value="" name="departureTimePhase" style="width:3em"/>
		      			</td>
		      			<td>
		      				&nbsp;&nbsp;&nbsp;&nbsp;Return&nbsp;<input id="returnTime" value="" name="returnTime" style="width:4em"/>&nbsp;<input id="returnTimePhase" value="" name="returnTimePhase" style="width:3em"/>
		      			</td>
		      		</tr>
		      	</table>
		      </td>
			</tr>
			<tr><td height="6px"></td></tr>
			<tr>
				<td colspan="2" align="left" nowrap="nowrap">&nbsp;Expense Type<sup>*</sup>&nbsp;<input type="text" id="expenseType_cb" name="expenseType" size="15"/></td>
				<td colspan="3" align="left" nowrap="nowrap">
					<!-- miles field set -->
					<fieldset style="border:1px solid lightgrey; width:90%">
						<table cellspacing="1px" cellpadding="1px" align="left">
							<tr>
								<td align="center" valign="bottom">Miles</td>
								<td align="center" valign="bottom">Vicinity Miles</td>
								<td align="center" valign="bottom">Total Miles</td>
								<td align="center" valign="bottom" nowrap="nowrap">Common Miles?&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;<input type="text" name="miles" id="miles" style="width:6em" trim="true" 
													dojoType="dijit.form.NumberTextBox" value="0" invalidMessage= "Invalid miles" 
													constraints="{min:0, max:99999, pattern: '##,###'}"/>&nbsp;</td>
								<td>&nbsp;<input type="text" name="vicinityMiles" id="vicinityMiles" style="width:6em" trim="true" 
													dojoType="dijit.form.NumberTextBox" value="0" invalidMessage= "Invalid vicinity miles" 
													constraints="{min:0, max:99999, pattern: '##,###'}"/>&nbsp;</td>
								<td>&nbsp;<input type="text" name="totalMiles" id="totalMiles" style="width:6em" readonly="readonly" 
													dojoType="dijit.form.NumberTextBox" value="0" invalidMessage= "Invalid total miles" 
													constraints="{min:0, max:99999, pattern: '##,###'}"/>&nbsp;</td>
								<td align="center"><div id="commonMilesInd" style="font-weight: bold">NO</div></td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			<tr><td height="6px"></td></tr>
			<tr>
				<td align="left" width="215px">&nbsp;Reimbursement amount<sup>*</sup></td>
				<td><input type="text" id="reimbursementAmount" name="reimbursementAmount" style="width:7em" trim="true" 
					dojoType="dijit.form.NumberTextBox" value="0" required="false" invalidMessage= "Invalid amount" 
					constraints="{min:0, max:99999.99, type: 'currency', pattern: '##,###.##'}"/></td>
				<td colspan="3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>Travel and Other Reimbursement Rate:</i>&nbsp;<input type="text" id="ed_suggestedAmt" 
					name="ed_suggestedAmt" style="width:7em;border:none;font-weight:bold" value="" readonly="readonly" tabIndex='-1'/></td>
			</tr>
			<tr><td height="6px"></td></tr>
			<tr>
				<td valign="middle">&nbsp;Comments</td>
				<td colspan="5" nowrap="nowrap">
					<textarea rows="3" cols="80" id="expenseDetailComments" name="comments" class="counter charLimit.255"  style="white-space: pre-wrap;" ></textarea>
					<span id='ed_commentCharCount' class='count'>255</span>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="applyExpenseDetailBtn"  type="button" value="Apply" onclick="applyExpenseDetail();"/>
				</td>
			</tr>
		</table>
	</fieldset>
	<div style="height:4px"><!--  --></div>
	<!-- section after expense detail entry form -->
	<table style="width:80%">
	<tr>
		<td colspan="3"><input type="checkbox" name="supervisorReceiptsReviewed" id="supervisorReceiptsReviewed"/>Supervisor Certifies Reviewing Required Supporting Documentation</td>
	</tr>
	<tr><td height="7px"></td></tr>
	<tr>
	 	<td align="left" width="10%">
	 			<table><tr><td nowrap="nowrap">
		 		<!--
		 		<s:submit  value="<<" id="ed_prevRevBtn" onclick="handlePrevNext('ed_form','prevNextEd','PreviousExpenseRevision.action');"/> 
		 		<s:submit  value=">>" id="ed_nextRevBtn" onclick="handlePrevNext('ed_form','prevNextEd','NextExpenseRevision.action');"/>
				-->
		 		<s:submit value="<<" id="ed_prevRevBtn" action="PreviousExpenseRevision" onclick="addTabPrefElement(this.form, 'expenseDetailsTab'); return true;"/> 
		 		<s:submit value=">>" id="ed_nextRevBtn" action="NextExpenseRevision" onclick="addTabPrefElement(this.form, 'expenseDetailsTab'); return true;"/>

		 		<s:label value="Version: "></s:label>
		 		<s:label id="ed_revNo" name="expenseMaster.revisionNumber"></s:label>
		 		&nbsp;&nbsp;&nbsp;&nbsp;
		 		</td></tr></table>
	 	</td>
	 	<td align="center" width="58%" valign="middle">	
	 		<div id="expenseDetailErrorMsg" style="font-size:9pt;"/>
	 	</td>
      	<td align="left" width="32%" nowrap="nowrap">&nbsp;&nbsp;
       		<input id="auditCompleteBtn" type="button" value="Audit Complete" onclick="markExpenseAsAudited();">
       		<input id="saveExpenseDetailsBtn" type="button" value="Save" onclick="saveExpenseDetails()">
	  	</td>
	</tr>
	</table>
</s:form>

<!-- hidden text field to control view state -->
<div style="display:none">
	<input type="text" id="viewJsonId" name="viewJson">
	<input type="text" id="expenseTypesJSON"/>
	<input type="text" id="hiddenExpenseTypeCode" />
</div>
	
