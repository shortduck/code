/**
 * This js is used for all Agency Options calls and validations
 * 
 * @kaws1
 */

// declare references to widget comboboxes
var dept_cb;
var agency_cb;
var state_cb;
var loc_cb;
var location_cbF;
var location_cbT;
var pageEventHandlers = [];

// prepares a reusable blank data store
function prepareNoElementStore() {
	var noElementArray = [];
	noElementArray[0] = {
		display : ""
	};

	noElementStore = prepareStore(noElementArray);
}
// prepares a Read Only store for comboboxes
function prepareStore(jsonStr) {
	var deptData = {};
	deptData.identifier = 'display';
	deptData.items = jsonStr;
	return new dojo.data.ItemFileReadStore({
		data : deptData
	});
}

/**
 * This is for update the agencies and departmet.
 * 
 * @return
 */
function initDeptAgencyCB() {

	updateDepartmentsAgencies(dojo
			.fromJson(dojo.byId('jsonResponse_hidden').value));

	// also prepare the no element store for future use
	prepareNoElementStore();
}

/* Prepares a Read store */
function prepareReadStore(itemArray, idColumnName) {
	var stateCBData = {};
	stateCBData.identifier = idColumnName;
	stateCBData.items = itemArray;

	return new dojo.data.ItemFileReadStore({
		data : stateCBData
	});
}

/**
 * This is used to display the locations when selected.
 * 
 * @param data
 * @return
 */
function updateLocation(data) {
	var displayVal = data.chosenValue;
	var store_locations = prepareReadStore(data.agyLocations, 'display');
	loc_cb = new dijit.form.ComboBox({
		id : "ag_locations",
		name : "ag_locations",
		searchAttr : 'display',
		store : store_locations,
		value : displayVal,
		displayedValue : displayVal,
		style : "width:6em ,height:40em,mazSize:5"
	}, 'ag_locations');
}

/**
 * Update the state list on change
 * 
 * @param data
 * @return
 */

function updateStateList(data) {
	var st_store = prepareStore(data.statesUs);

	state_cb = new dijit.form.FilteringSelect({
		id : "state_cb",
		name : "state_cb",
		store : st_store,
		searchAttr : "code",
		value : "MI",
		displayedValue : "MI",
		style : "width:5em"
	}, "state_cb");
}

// prepares a Read Only store for comboboxes
function prepareStoreState(jsonStr) {

	var deptData = {};
	deptData.identifier = 'display';
	deptData.items = jsonStr;
	return new dojo.data.ItemFileReadStore({
		data : deptData
	});
}

// Updates the Agency combobox with new data
function updateDepartmentsAgencies(data) {

	var dept_store = prepareStore(data.departments);
	var agency_store = prepareStore(data.agencies);
	// chosen field
	// alert("agency_store "+agency_store);
	var selDept = data.chosenDepartment;
	// alert("selDept "+selDept);
	var selAgency = data.chosenAgency;

	// parse to combo box
	dept_cb = new dijit.form.FilteringSelect({
		id : "departments",
		name : "chosenDepartment",
		store : dept_store,
		searchAttr : "display",
		value : selDept,
		displayedValue : selDept,
		style : "width:20em",
		maxHeight:"400"
	}, "department_cb");
	agency_cb = new dijit.form.FilteringSelect({
		id : "agencies",
		name : "chosenAgency",
		store : agency_store,
		searchAttr : "display",
		value : selAgency,
		displayedValue : selAgency,
		style : "width:23em"
	}, "agency_cb");

	// store default dept value for further reference
	defaultDept = data.chosenDepartment;

	// connect to onchage events to the refreshpage and create the tooltip. kp
	dojo.connect(dept_cb, "onChange", function() {
		refreshAgenciesAgy();
		addTooltip(dept_cb, dept_cb.id);
	});

}

// Invoked upon new department selection in departments combobox.
// PS: Due to connected event notification, this method also triggers tku
// updation.
// So, selecting new department implies agencies reload (which automatically
// reloads Tkus)
function refreshAgenciesAgy() {

	// left align in IE by focusing on CB
	// Commented to fix issue#692
	// dept_cb.comboNode.focus();

	if (!dept_cb.isValid() || dept_cb.value == '') {
		// blank out the agency and tku data stores
		agency_cb.store = noElementStore;
		agency_cb.setValue("");
		agency_cb.setDisplayedValue("");

		return;
	}
	fetchFromServer("FindAgencies.action?chosenDepartment=" + dept_cb.value,
			updateAgencies);
}

// Updates the Agency combobox with new data
function updateAgencies(data) {
	// agency_cb.store = prepareStore(data.response.agencies);
	agency_cb.set("store", prepareStore(data.response.agencies));
	agency_cb.setValue(data.response.chosenAgency); // this triggers tku
													// updation immediately
													// after execution!
	agency_cb.setDisplayedValue(data.response.chosenAgency);
}

function increaseDeptAgyDDLength() {
	if (dept_cb != undefined) {
		dept_cb.style.width = "16em";
		agency_cb.style.width = "16em";
	}
}

function validateUserInput(errorMsg) {
	return validateDeptAgency(errorMsg);
}

function validateDeptAgency(errorMsg) {

	if (dept_cb != undefined) {
		if (!dept_cb.isValid()) {
			errorMsg.msg = 'Invalid Department';
		} else if (!agency_cb.isValid()) {
			errorMsg.msg = 'Invalid Agency';
		} else if (isAgencySelected() && !isDepartmentSelected()) {
			errorMsg.msg = 'Department required for agency';
		}
	}
	return (errorMsg.msg.length > 0) ? false : true;

}

function displayValidationErrorMsg(msg) {

	dojo.byId('errorMsg').innerHTML = msg;
}

/**
 * This changes the case of the value entered in text box-new Agency location
 * 
 * @param obj
 * @return
 */
function changeCase(obj) {
	obj.value = obj.value.toUpperCase();
}

/**
 * Displays the error messages in Location page
 */
function displayLocationErrorMsg(msg) {
	dojo.byId('errorMsg').style.color = "RED";
	dojo.byId('errorMsg').innerHTML = msg;
}

/**
 * Displays the error messages in Location page
 */
function displayLocationMsg(msg) {
	dojo.byId('errorMsg').innerHTML = '';
	dojo.byId('errorMsg').style.color = "GREEN";
	dojo.byId('errorMsg').innerHTML = msg;
}

/**
 * This function adds a new location for a given agency /department
 * 
 * @param obj
 * @return
 */
function saveNewLocation(obj)
{
 
var vaSt = document.getElementById("state_cb").value;
var modVal = dojo.byId("module_hidden").value;
var vaAgLoc = dojo.byId('ag_Newlocations').value;
 
if(trimStr(vaAgLoc).length == 0)
{
	displayLocationErrorMsg(" New Location Not Selected ");
  return false;
}
else
{
	

			dojo.xhrPost({
			url : 'updateAgencyCity.action?ag_location='
					+ dojo.byId('ag_Newlocations').value + '&deptNum='
					+ dojo.byId('deptNum').value + '&agencyNum='
					+ dojo.byId('agencyNum').value + '&stProv=' + vaSt
					+ '&module_hidden=' + modVal,
			handleAs : "json",
			handle : function(data, args) {
				if (typeof data == "error") {
					console.warn("error!", args);
				} else {
					if (data.validationErrors.errors != null) {
						displayLocationErrorMsg(data.validationErrors.errors);
					} else {
						finddataAfterSave(data.response);
						displayLocationMsg("New agency location added.");
					}
					setUnchanged();
				}
			}
		});
	
	

}
}

/**
 * This initializes the grid in mileage page
 * 
 * @param data
 * @return
 */


 function initResultsAgyGrid(data) {
	//resultGridStore = emptyGridStore;
	dijit.byId('locationgrid').setStore(resultGridStore);
	updateResultGrid(data);
}

function preInitMileagePage() {
	finddata(dojo.fromJson(dojo.byId('jsonResponse_hidden').value));
	initMileagePage(" ", dN, aN, modV);
}

/**
 * This function is called on tab change to initialize the mileage page
 * 
 * @param url
 * @param dN
 * @param aN
 * @return
 */
function initMileagePage(url, dN, aN, modV) {

	// setTimeout(function() {},1250);
	displayValidationMilesSuccMsg(' ');
	displayValidationMilesErrorMsg('');
	addOnChange();

	dojo
			.xhrPost({
				url : 'agyMilesUpdate.action?deptNum=' + dN + '&agencyNum='
						+ aN + '&module_hidden=' + modV,
				handleAs : "json",
				handle : function(data, args) {
					if (typeof data == "error") {
						console.warn("error!", args);
					} else {

						updateResultMilesGrid(data.response);
						dojo.byId("newMileAgy").disabled = data.response.writeAccess;
						dojo.byId("saveMiles").disabled = data.response.writeAccess;
						dojo.byId("newStateMile").disabled = data.response.stateAccessUpd;
						;
						dojo.byId("saveStateMiles").disabled = data.response.stateAccessUpd;

						if (data.response.stateAccessUpd) {
							dojo.byId("stateDetails").style.display = 'none';
							dojo.byId("saveStateMiles").style.display = 'none';
							dojo.byId("newStateMile").style.border = "0px";
							dojo.byId("colState").style.display = "none";
							dojo.byId("newStateMile").style.display = 'none';
						}

						dojo.byId("saveStateMiles").visisble = data.response.stateAccessUpd;
					}
				}
			});

}

/**
 * This function is called to save new miles for the agency for two cities
 * 
 * @param obj
 * @return
 */
function saveNewMiles(obj, num) {
	var errorM = '';
	var fromGrid = '';
	var toGrid = '';
	var newMileAgy = dojo.byId("newMileAgy").value;
	var newStateMile = dojo.byId("newStateMile").value;
	var agNVal = dojo.byId('agVal').value;
	var to_StateFlag = '';
	var from_StateFlag = '';
	if (locationFromgrid.selection.getSelected().length <= 0) {
		errorM = errorM + ' Select from City <br> ';
	} else {
		fromGrid = locationFromgrid.selection.getSelected()[0].elocIdentifier;

		if (locationFromgrid.selection.getSelected()[0].department == "AL"
				&& locationFromgrid.selection.getSelected()[0].agency == "AL")
			from_StateFlag = "Y";
		else
			from_StateFlag = "N";

	}
	if (locationTogrid.selection.getSelected().length <= 0) {
		errorM = errorM + ' Select To City <br>';
	} else {

		toGrid = locationTogrid.selection.getSelected()[0].elocIdentifier;
		if (locationTogrid.selection.getSelected()[0].department == "AL"
				&& locationTogrid.selection.getSelected()[0].agency == "AL")
			to_StateFlag = "Y";
		else
			to_StateFlag = "N";

	}
	if ((dojo.byId("newMileAgy").value) == '' && num == 2) {
		errorM = errorM + ' New Agency Mileage Not Entered. <br> ';
	}
	if ((dojo.byId("newStateMile").value) == '' && num == 1) {
		errorM = errorM + ' New State Mileage Not Entered. <br> ';
	}

	if (errorM.trim().length > 0) {
		displayValidationMilesErrorMsg(errorM);
		return false;
	} else {
		if (num == 1) {
			if (from_StateFlag == 'Y' && to_StateFlag == 'Y') {
				dojo
						.xhrPost({
							url : 'saveStateNewMileage.action?fromMiles='
									+ fromGrid + '&toMiles=' + toGrid
									+ '&saveStateMiles=' + newStateMile
									+ '&deptNum=' + deptNum1.value
									+ '&agencyNum=' + agencyNum1.value
									+ '&agNVal=' + agNVal,

							handleAs : "json",
							handle : function(data, args) {
								if (typeof data == "error") {
									console.warn("error!", args);
								} else {
									dojo.byId('mLval').value = data.response.mileage;
									dojo.byId("newStateMile").value = '';
									displayValidationMilesSuccMsg('New StateWide Mileage Updated ');
									setUnchanged();
								}
							}
						});
			} else {
				displayValidationMilesErrorMsg(' State Wide Locations Needed for State Mileage ...  ');
				dojo.byId("newStateMile").value = '';

			}

		} else {
			dojo
					.xhrPost({
						url : 'saveNewMileage.action?fromMiles=' + fromGrid
								+ '&toMiles=' + toGrid + '&newMileAgy='
								+ newMileAgy + '&deptNum=' + deptNum1.value
								+ '&agencyNum=' + agencyNum1.value + '&agNVal='
								+ agNVal,

						handleAs : "json",
						handle : function(data, args) {
							if (typeof data == "error") {
								console.warn("error!", args);
							} else {
								dojo.byId('aLval').value = data.response.agyMileage;
								dojo.byId('agVal').value = data.response.agycmIdentifier;
								dojo.byId("newMileAgy").value = '';
								displayValidationMilesSuccMsg('New Agency Mileage Updated ');
								setUnchanged();
							}
						}
					});
		}
	}

}

/**
 * This function is called when user selects the two rows from the mileage tab
 * in Agency . The function makes a call to retrieve the common miles between
 * the selected cities from the two grids.
 * 
 * @param dN
 * @param aN
 * @return
 */
function getStateDefinedMiles(dN, aN) {
	var fromGrid = locationFromgrid.selection.getSelected()[0].elocIdentifier;
	var toGrid = locationTogrid.selection.getSelected()[0].elocIdentifier;

	dojo.xhrPost({
		url : 'getStateMilesUpdate.action?fromMiles=' + fromGrid + '&toMiles='
				+ toGrid,
		handleAs : "json",
		handle : function(data, args) {
			if (typeof data == "error") {
				console.warn("error!", args);
			} else {
				dojo.byId('mLval').value = data.response.mLval;
				dojo.byId('aLval').value = data.response.aLval;
				dojo.byId('agVal').value = data.response.agVal;
			}
		}
	});
}

/* Invoked when user selects a row in the grid */
function processExpenseDetailsGridRowSelection(event) {

	// if (locationFromgrid.selection.getSelectedCount() > 1 )
	// locationFromgrid.selection.clear();

	var fromGrid = locationFromgrid.selection.getSelected()[0];
	var toGrid = locationTogrid.selection.getSelected()[0];
	locationTogrid.selection.background = 'green';
	locationFromgrid.selection.background = "RED";
	if (fromGrid != null && toGrid != null) {
		displayValidationMilesErrorMsg("");
		getStateDefinedMiles(fromGrid.elocIdentifier, toGrid.elocIdentifier);
	}
}

function disconnectEvents() {
	dojo.forEach(pageEventHandlers, dojo.disconnect);

	// Reinitialize the handler array as all previous entries are now eligible
	// for GC.
	pageEventHandlers = [];
}
/**
 * This is called to enable the events when the user selects the two rows from
 * the two grids in the mileage screen
 * 
 * @return
 */
function connectEvents() {
	if (locationFromgrid != null && locationTogrid != null)
		pageEventHandlers.push(dojo.connect(locationTogrid, 'onRowClick',
				'processExpenseDetailsGridRowSelection'));
	if (locationFromgrid != null && locationTogrid != null)
		pageEventHandlers.push(dojo.connect(locationFromgrid, 'onRowClick',
				'processExpenseDetailsGridRowSelection'));

	dojo.connect(locationFromgrid, "onStyleRow", function(row) {
		if (row.selected) {
			row.customStyles += 'background-color:#2DE817;';
		}
	});

	dojo.connect(locationTogrid, "onStyleRow", function(row) {
		if (row.selected) {
			row.customStyles += 'background-color:#2DE817;';
		}
	});

	window.onkeypress = handleUserKeyPress;
}

function handleUserKeyPress() {
	userLastKeypressTime = new Date();
}

/**
 * Trim String function
 */
String.prototype.trim = function() {
	return this.replace(/^\s*/, "").replace(/\s*$/, "");
}
