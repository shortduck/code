<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<style type="text/css">
.soria .dojoxGridRowSelected {
	background-color: #2DE817;
}

.col1 {
	width: 44%;
	height: 250px;
	padding: 1em;
	float: left;
}

.col2 {
	width: 50%;
	height: 250px;
	padding: 1em;
	float: right;
}
</style>


<s:form id="mileAgeForm" method="post" theme="simple">
	<fieldset style="border: 1px solid black; width: 85%">
		<legend style="color: black; font-weight: bold; font-size: 9pt">
			<em>&nbsp;Agency Mileage</em>
		</legend>
 		<div style="float: left; width: 100%">
			<table id="Mileage" border="0" style="margin: 1%;">
				<tr>
					<td valign="top">Department :&nbsp;</td>
					<td align="left" valign="top"><input type="text"
						style="border: 0px;" id="deptNum1" name="deptNum1" size="2"
						readonly="readonly" tabIndex="-1" /></td>
					<td align="left" valign="top">Agency :&nbsp;</td>
					<td align="left" valign="top"><input type="text"
						style="border: 0px;" id="agencyNum1" name="agencyNum1" size="1"
						readonly="readonly" tabIndex="-1" /><input type="text"
						style="border: 0px;" id="agencyName1" name="agencyName1"
						readonly="readonly" tabIndex="-1" size="40" /></td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
			</table>
 		</div>

		<div  style="float: left; width: 44%">
			<div>
				<label>Location From </label>
			</div>
 			<div id="headerDiv1">
				<table id="locationFromgrid" jsId="locationFromgrid"
					dojoType="dojox.grid.EnhancedGrid" query="{ elocIdentifier : '*' }"
					rowsPerPage="20" model="jsonStore"
					style="width: 300px; height: 250px; position: relative; top: 1px; vertical-align: top;"
					selectionMode="single" store="mystoreF"
					plugins="{ filter:{ruleCount: 0}}">
					<thead>
						<tr>
							<th field="city" width="10%"><em>City</em></th>
							<th field="stProv" width="5%"><em>State</em></th>
							<th field="stateWide" width="5%"><em>StateWide </em></th>
						</tr>
					</thead>
				</table>
 			</div> 
		</div>
 		
		<div style="float: right; width: 50%">
			<div>
				<label>Location To</label>
			</div>
			<div id="headerDiv1">
				<table id="locationTogrid" jsId="locationTogrid"
					dojoType="dojox.grid.EnhancedGrid" query="{ elocIdentifier : '*' }"
					rowsPerPage="20" model="jsonStore"
					style="width: 300px; height: 250px; position: relative; top: 1px; vertical-align: top;"
					selectionMode="single" store="mystoreT"
					plugins="{ filter:{ruleCount: 0}}">

					<thead>
						<tr>
							<th field="city" width="10%"><em>City</em></th>
							<th field="stProv" width="5%"><em>State</em></th>
							<th field="stateWide" width="5%"><em>StateWide </em></th>

						</tr>
					</thead>
				</table>
			</div>
		</div>
		
		<table border="0" style="margin: 1%;" cellspacing="1" cellpadding="1">
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
			<tr>
				<td nowrap>State Defined Common Mileage :&nbsp;</td>
				<td><input type="text" id="mLval" name="mLval"
					style="border: 0px;" readonly="readonly" tabIndex="-1" size="3">
				</td>
				<td id="stateDetails">New State Mileage :</td>
				<td id="colState"><input dojoType="dijit.form.NumberTextBox"
					type="text" style="width: 6em"
					constraints="{min:0, max:999, pattern: '####'}" id="newStateMile"
					name="newStatetMile" invalidMessage="Enter Numbers" /></td>
				<td align="left">&nbsp;&nbsp;&nbsp;<input type="submit"
					id="saveStateMiles" name="saveStateMiles" value="Save"
					onclick="javascript:saveNewMiles(mileAgeForm,1);return false;">
				</td>
			</tr>
			<tr>
				<td>Agency Defined Mileage :&nbsp;</td>
				<td><input type="text" id="aLval" name="aLval"
					style="border: 0px;" readonly="readonly" tabIndex="-1" size="3" />
				</td>
				<td nowrap>New Agency Mileage :</td>
				<td><input dojoType="dijit.form.NumberTextBox" type="text"
					id="newMileAgy" name="newMileAgy" invalidMessage:'Enter
					Numbers' style="width: 6em"
					constraints="{min:0, max:999, pattern: '####'}" /></input></td>
				<td width="300">&nbsp;&nbsp; <input type="submit"
					id="saveMiles" name="saveMiles" value="Save"
					onclick="javascript:saveNewMiles(mileAgeForm,2);return false;">
				</td>
			</tr>
			<tr>
				<td colspan="5">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="5" align="middle"><span id="statusArea"
					style="color: red; align: middle"></span></td>
			</tr>
		</table>
	</fieldset>
</s:form>
