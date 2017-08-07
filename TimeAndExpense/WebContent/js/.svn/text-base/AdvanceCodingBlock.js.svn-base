/*
 * The methods commented in this file are available now in the codingBlockCommon.js files.
 */

var cBlockOptions;
var newRow = true;
var rowInitialized = false;
var rowCount = 0;
var deletedRowCount = 0;
var jsonResult;
var cbSelectedData;

function createTable() {
	var div = document.getElementById("dept_tab");
	var cbOptions = dojo.byId('cb_options_hidden').value;
	if (cbOptions != "" && cbOptions != undefined){
		cBlockOptions = eval('(' + cbOptions + ')');
	} else {
		// this should not happen unless unexpected error occurs
		showErrorMessage ("Coding block display options were not setup correctly");
		dojo.byId('buttonSave').disabled = true;
		dojo.byId('buttonSubmit').disabled = true;
		return;
	}
	var table = initializeTable(cBlockOptions.cboption);

	var result = dojo.byId('json_result_hidden').value;
	if (result != "") {
		// setup default store for current AY
		jsonResult = eval('(' + result + ')');
		var cbStore = jsonResult.store;
		storeApprYear = cbStore[8].apprYear;
	} else {
		// this should not happen unless unexpected error occurs
		showErrorMessage ("Coding block dropdown data was not setup correctly");
		dojo.byId('buttonSave').disabled = true;
		dojo.byId('buttonSubmit').disabled = true;
		return;
	}

	var selectedCBData = dojo.byId('selected_Coding_Block_data').value;
	if (selectedCBData != "")
		cbSelectedData = eval('(' + selectedCBData + ')');

	var totalRows = dojo.byId('no_of_coding_blocks_hidden').value;
	if (totalRows == 0) {
		appendRow(table, 1);
		newRow = false;
	} else {
		addRows(totalRows, table);
		rowInitialized = true;
		// load store for appropriation year if different than current AY
		resetApprYearStore ();
	}
	
	// set CB row to read only if no CB options are enabled
		setSingleStdRowReadOnly();
}

/**
 * Called to display existing rows for the first time
 * @param totalRows
 * @param table
 * @return
 */
function addRows(totalRows, table) {
	for ( var i = 0; i < totalRows; i++) {
		rowCount++;
		var createRowNew;
		if (document.all) {
			// ZH - this may not be needed, used to guard against older browsers, leaving in code for now
			createRowNew = table.insertRow();
			newRow = false;
		}

		else
			createRowNew = table.insertRow(table.rows.length);
		for ( var j = 0; j < cBlockOptions.length; j++) {
			if (cBlockOptions[j].show == "Y")
				oRow = constructCodingBlockCells(createRowNew, rowCount,
						cBlockOptions[j].name, false);
		}
		createRowNew.id = "cb_" + rowCount;
	}
	// flips delete button for the first row if necessary
	toggleFirstRowDeleteButton();
}

/**
 * Adds a new row to the coding block table
 * @param table
 * @param newRowIndex
 * @param pctValue
 * @return
 */

function appendRow(table, newRowIndex, pctValue) {
	var createRowNew;
	rowCount++;
	// var newRow = table.insertRow(table.rows.length);
	if (document.all)
		createRowNew = table.insertRow();
	else {
		newRowIndex = table.rows.length;
		createRowNew = table.insertRow(table.rows.length);
	}

	createRowNew.id = "cb_" + rowCount;

	for ( var j = 0; j < cBlockOptions.length; j++) {
		if (cBlockOptions[j].show == "Y") {
			oRow = constructCodingBlockCells(createRowNew, rowCount,
					cBlockOptions[j].name, true, pctValue);
		}
	}
	// flips delete button for the first row if necessary
	toggleFirstRowDeleteButton();

}