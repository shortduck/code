<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expense Coding Block</title>


<!-- JS to render advance tab control and grid -->
<script type="text/javascript">
	dojo.require("dojox.grid.EnhancedGrid");
	dojo.require("dojox.grid.enhanced.plugins.Filter");
	dojo.require("dojo.parser");
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dojox.grid.DataGrid");
	dojo.require("dojox.data.QueryReadStore");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dijit.form.ComboBox");
	dojo.require("dijit.form.CheckBox"); 
	dojo.require("dijit.Tooltip");
</script>

<script type="text/javascript">

</script>
</head>

<body>
<div>

<fieldset
	style="position: relative; border: solid 1px; top: 10px; left: 5px; width: 775px">
<legend style="color:black;font-weight:bold;font-size:9pt"><em>Coding Block</em></legend>

<div id="cbTab" class="soria" >
	<s:form id="cb_form" theme="simple" action="ExpenseCodingBlockAction.action">
		<s:hidden id="checkedRowIds" name="checkedRowIds" />
		<s:hidden id="codingBlockJsonFromClient" name="codingBlockJsonFromClient" />
		<table id="cbTable"></table>
		<!-- 
		Commented for only a single button solution
		<button value="Update Checked Rows" id="cbUpdate" onclick="return submitCodingBlockUsingAjax(this.form);">Update Checked Rows</button>
		 -->
		<div id="expdcDiv"></div>
		<s:hidden theme="simple" id="status" name="status" value="%{expenseStatus}" />
		<s:hidden theme="simple" id="disableButtons" name="disableButtons" value="%{disableButtons}" />
		<s:hidden theme="simple" id="prevNextRequest" name="prevNextRequest" value="%{prevNextRequest}"></s:hidden>
		<s:hidden theme="simple" id="statusMessage" name="statusMessage" value="%{statusMessage}"></s:hidden>
		<div id="navigationDiv" style="position:relative;left:10px;width:90%;top:30px">
			<table cellspacing="0" cellpadding="0" border="0" style="position: relative; top: 5px; left: 10px;" width="100%">
				<tr width="90%">
					<td>
		 		<s:submit  value="<<" id="cbPrev" action="PreviousTravelRequisitionRevision" onclick="addTabPrefElement(this.form, 'cbTab'); return true;"/> 
		 		<s:submit  value=">>" id="cbNext" action="NextTravelRequistionRevision" onclick="addTabPrefElement(this.form, 'cbTab'); return true;"/>
					<s:label value="Version: " theme="simple"></s:label>
	 		<s:label id="cb_revNo" value="" theme="simple"></s:label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 		</td>
	 		<td></td>
					<td width="350px"><span id="errorCodingBlockMsg" style="font-style:italic; font-size: 9pt; color: red;"></span></td>
					<!-- td width="50%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status displayed Here</td-->
					<td><button value="Save" id="cbSave" onclick="return saveCodingBlockUsingAjax();">Save</button></td>
				</tr>
			</table>
		</div>
	</s:form>
</div>
</fieldset>
</body>
</html>
