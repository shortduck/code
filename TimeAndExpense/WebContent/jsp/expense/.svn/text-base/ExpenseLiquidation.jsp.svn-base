<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<table width="100%" height="90%"><tr><td>
<table width="60%" height="10%" style="top:1px; left:5px;">
	<tr><td><u>Advances Liquidated by this Expense Report</u></td></tr>
	<tr><td><br/></td></tr>
	<tr><td>Total Outstanding Advances : $<input type="text" style="border:0px;" id="liq_totalAdvanceOutstandingAmt" value="0.00"/></td></tr>
	<tr><td>Current Expenses : $<input type="text" style="border:0px;" id="liq_totalExpenses" value="0.00"/></td></tr>
	<tr><td><br/></td></tr>
</table>

<div style="font-size:8.5pt; margin-left:5px;color:blue">*Double click on the amount in 'Amount to Liquidate' column to modify it.</div>
<table class="soria" cellpadding="10" id="expenseLiquidationsGrid" dojotype="dojox.grid.EnhancedGrid" rowsPerPage="100" 
	query="{ advmIdentifier: '*' }" style="position:relative; top:1px; left:5px; height:55%; width:750px">
	<thead>
		<tr>
			<th field="dateRequested" width="8%">
			<center>Request Date</center>
			</th>
			
			<th field="adevIdentifier" width="8%">
			<center>Advance ID</center>
			</th>
			
			<th field="advanceFromDate" width="8%">
			<center>Advance From</center>
			</th>
			
			<th field="advanceToDate" width="8%">
			<center>Advance To</center>
			</th>
<!--			
	 		<th field="paidDate" width="8%">
			<center>Paid Date</center>
			</th>
-->			
			<th field="advanceReason" width="20%">
			<center>Advance Reason</center>
			</th>
			
			<th field="dollarAmount" width="8%" formatter="rightAlignGridCellNumber">
			<center>Original Amount</center>
			</th>
			
			<th field="adjustedAmountOutstanding" width="8%" formatter="rightAlignGridCellNumber">
			<center>Outstanding Amount</center>
			</th>
			
			<th field="amountLiquidated" width="8%" editable="true" formatter="rightAlignGridCellNumber">
			<center style="color:blue ; font-weight: bold">Amount to Liquidate*</center>
			</th>
			
			<th field="newOutstandingAmount" width="8%" formatter="rightAlignGridCellNumber">
			<center>New Outstanding</center>
			</th>
			
			<th field="permanentAdvInd" width="8%" formatter="centerAlignGridCellText">
			<center>Permanent Advance</center>
			</th>
		</tr>
	</thead>
</table>
<br/>
<table width="95%" height="15%" style="position: relative;top:1px; left:5px;">
<tr>
	 	<td align="left" width="10%">
	 		<s:form id="li_form"  action="" method="post" theme="simple">
	 			<table><tr><td nowrap="nowrap">
		 		<s:submit value="<<" id="liq_prevRevBtn" action="PreviousExpenseRevision" onclick="addTabPrefElement(this.form, 'liquidationsTab'); return true;"/> 
		 		<s:submit value=">>" id="liq_nextRevBtn" action="NextExpenseRevision" onclick="addTabPrefElement(this.form, 'liquidationsTab'); return true;"/>
		 		<s:label value="Version: "></s:label>
		 		<s:label id="liq_revNo" name="expenseMaster.revisionNumber"></s:label>
		 		&nbsp;&nbsp;&nbsp;&nbsp;
		 		</td></tr></table>
	 		</s:form>
	 	</td>
	 	<td align="center" align="left" width="60%" valign="middle">	
	 		<div id="expenseLiquidationErrorMsg" style="font-size:9pt;"/>
	 	</td>
      	<td align="right" width="30%">
       		<input id="saveExpenseLiquidationsBtn" type="button" value="Save" onclick="saveExpenseLiquidations();">
	  	</td>
</tr>
</table>
<br/>
</td></tr></table>
