<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<title>Department Agency Location</title>

<script type="text/javascript">
	var dN;
	var aG;
	var aN;
	var accessval;
	var storeToUse;

	dojo.addOnLoad(function() {
		finddata(dojo.fromJson(dojo.byId('jsonResponse_hidden').value));
	});

	function finddata(data) {

		var serverResponse = data;
		accessVal = serverResponse.writeAccess;
		dojo.byId('deptNum').value = serverResponse.department;
		dojo.byId('agencyNum').value = serverResponse.agency;
		dojo.byId('agencyName').value = serverResponse.agencyName;
		dN = serverResponse.department;
		aG = serverResponse.agency;
		aN = serverResponse.agencyName;
		accessval = serverResponse.writeAccess;
		dojo.byId("module_hidden").value = accessval;
		dojo.byId('saveBtn').disabled = accessval;
		if (accessval == "true") {
			dojo.byId('ag_Newlocations').length = 0;
		}
		dojo.byId('ag_Newlocations').disabled = accessval;
		dojo.byId('state_cb').disabled = accessval;

		updateResultGrid(serverResponse.agyLocations, accessval);
		updateStateList(serverResponse);
	}
	function updateResultGrid(data, accessVal) {

		storeToUse = new dojo.data.ItemFileReadStore({
			data : {
				identifier : 'elocIdentifier',
				items : data
			}
		});

		if (data.length < 1) {
			displayValidationErrorMsg('No results found');
		} else {

			dojo
					.forEach(
							data,
							function(item) {
								if (!accessVal) {

									if (item.agencyName == "AL") {
										item.delButton = "<a href=\"javascript:void(0)\" onclick=\"accessPermission();\">"
												+ "Delete" + "</a>";
									} else {
										item.delButton = "<a href=\"javascript:void(0)\" onclick=\"deleteLoc('"
												+ item.city
												+ ", "
												+ item.stProv
												+ "', '"
												+ item.elocIdentifier
												+ "', '"
												+ dojo.byId('deptNum').value
												+ "', '"
												+ dojo.byId('agencyNum').value
												+ "');\">" + "Delete" + "</a>";
									}
								} else {
									item.delButton = '';

								}
								if (item.city == undefined)
									item.city = '';
								if (item.stProv == undefined)
									item.stProv = '';
							});
		}

		dijit.byId('locationgrid').setStore(storeToUse);

		dijit.byId('locationgrid').noDataMessage = "No Data Found.. Please modify your search criteria";

	}
</script>

<form id="deptAgyLocForm" name="deptAgyLocForm">
	<fieldset style="border: 1px solid black; width: 85%">
		<legend style="color: black; font-weight: bold; font-size: 9pt">
			<em>Agency Locations</em>
		</legend>

		<table id="deptAgyDetail" border="0" style="margin: 1%;">
			<tr>
				<td valign="top">Department :&nbsp;<input type="text"
					style="border: 0px;" id="deptNum" name="nDeptAgyLoc.department"
					size="2" />
				</td>
				<td align="left" nowrap="nowrap">Agency :<input type="text"
					style="border: 0px;" id="agencyNum" name="nDeptAgyLoc.agency"
					size="1" /><input type="text" style="border: 0px;" size="45"
					id="agencyName" name="nDeptAgyLoc.agency"></input>
				</td>
			</tr>

		</table>

		<br>
		
		<div>
			<table>
				<tr>
					<td>Agency Locations:</td>
				</tr>
			</table>
		</div>

		<div style="height:200px">
			<table id="locationgrid" jsId="resultGrid"
				dojoType="dojox.grid.EnhancedGrid" query="{ elocIdentifier : '*' }"
				rowsPerPage="20" model="jsonStore"
				style="width: 400px; height: 100%; position: relative; top: 10px"
				selectionMode="single" store="storeToUse"
				plugins="{ filter:{ruleCount: 0}}">
				<thead>
					<tr>
						<th field="city" width="10%"><em>City</em></th>
						<th field="stProv" width="5%"><em>State</em></th>
						<th field="delButton" width="8%" formatter="formatGridLink">Delete</th>
					</tr>
				</thead>
			</table>
		</div>

		<div style= "height:100px">
			<table align="left" border="0" style="margin: 1%;">
			<tr>
			<td colspan = "4">&nbsp;</td></tr>
			
				<tr>
					<td valign="middle">New Agency Location:&nbsp;</td>
					<td valign="middle"><input id="ag_Newlocations"
						name="ag_Newlocations" size="25" align="left"
						onblur="changeCase(this);" maxlength="40">&nbsp;&nbsp;</td>
					<td valign="middle"><input id="state_cb" name="state_cb" >
					</td>
					<td valign="top"><input id="saveBtn" value="Save"
						onClick="saveNewLocation(this);return false;" type="submit">
					</td>
				</tr>

				<tr>
					<td>&nbsp;</td>
				</tr>

				<tr>
					<td colspan="4">
						<div id="errorMsg"
							style="font-size: 9pt; color: red; text-align: center"></div>
					</td>
				</tr>
			</table>
</div>
			<span id="statusAreaLocation" style="color: red;"></span>
			<div style="position: relative; top: 30px" id="createDiv1"></div>
	</fieldset>

<!-- 	<div style="display: none"> -->
<div>
		<s:textfield id="jsonResponse_hidden" theme="simple"
			value="%{jsonResponse}" />
	</div>

</form>