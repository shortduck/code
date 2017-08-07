/*
 * Includes validations for coding block rows
 */

var noOfCodingBlocks;

function validateCodingBlocks(){
		noOfCodingBlocks = dojo.byId("cbTable").rows.length - 1;
		// validate that total percentage etotals 100
		if (!validatePercent()){
			return false;
		}
		// Delete last row if only the percentage field is 0 and no other CB values have been entered 
		deleteLastRowIfEmpty ();
		
		// remove leading and trailing spaces from all CB elements
		trimCbElements(noOfCodingBlocks);
		
		// validates the AY entered for all rows
		if (!validateStdAndAy())
			return false;
		
		// checks to see that if a phase is entered, grant/project must also be entered
		if (!validateProjectGrantPhase())
			return false;
		
		return true;
	}

function deleteLastRowIfEmpty(){
	// Delete last row if empty
	if (isLastRowEmpty() && !(noOfCodingBlocks == 1) &&
			dojo.byId("pct_" + getLastRowIdNumber()).value != "" &&
			parseFloat(dojo.byId("pct_" + getLastRowIdNumber()).value) == "0"){
		dojo.byId("cbTable").deleteRow(dojo.byId("cbTable").rows.length - 1);
		noOfCodingBlocks = noOfCodingBlocks - 1;
	}
}

function validatePercent (){
	var sum = 0;
	for(var i=0;i < noOfCodingBlocks;i++){
		var suffix = getNextCbRowSuffix(i + 1);
		var pct = parseFloat(dojo.byId("pct_" + suffix).value);
		if (pct == "" || isNaN(pct) || (!isRowLast(suffix) && parseFloat(pct) == 0)){
			showErrorMessageCB('Please enter a valid percent amount for line number '+ (i + 1));
			return false;
		} else if (isRowLast(suffix) && parseFloat(pct) == 0){
			var concatString = getConcatString(suffix);
			if (concatString.length > 0){
				showErrorMessageCB('Please enter a valid percent amount for line number '+ (i + 1));
			}
		}
		// cumulative pct sum for all rows
		if (!isRowEmpty(suffix)){
			sum = parseFloat((pct+sum).toFixed(2));
		}
	}		
	
	if (sum > 100){
		showErrorMessageCB('The percent sum of all Coding Block rows may not exceed 100%');
		return false;
	} else if (sum < 100){
		showErrorMessageCB('The percent sum of all Coding Block rows may not be less than 100%');
		return false;
	}
	return true;
	}
			
function validateStdAndAy (){
	var ayValidated = true;
	var stdFound = false;
	
	for(var i=0;i < noOfCodingBlocks;i++){
		var suffix = getNextCbRowSuffix(i + 1);
		var ayField = dojo.byId("ay_" + suffix);
		var ayValue = dojo.byId("ay_" + suffix).value;
		var stdChecked = dojo.byId("std_" + suffix).checked;
		if (stdChecked && ayValue != ""){
			showErrorMessageCB('AY and PL Std may not be present for the same coding block row');
			return false;
		}
		if (!stdChecked && (isNaN(ayValue) || ayValue.length < 2)){
			ayField.select();
			ayField.focus();
			showErrorMessageCB('Please enter a valid 2 digit Appropriation Year');
			return false;
		}
		// check if AY or STD present
		if (ayValue.trim() == "" && !stdChecked){
			showErrorMessageCB('Either AY or PL Std must be entered for each Coding Block row');
			return false;
		}
		// check for duplicate STD
		if (stdChecked){
			if (stdFound == true){
				showErrorMessageCB('Only one Coding Block row may be PL Std');
				return false;
			}
			stdFound = true;
			}
		
		var concatString = getConcatString(suffix);
		if (!stdChecked && concatString.length <= 2){
			showErrorMessageCB("Please enter Coding Block values for line number " + (i + 1));
			return false;
		} 
	}
	return ayValidated;
}

function getNextCbRowSuffix (nextRowNumber){
	var rowId = dojo.byId('cbTable').rows[nextRowNumber].id;
	var suffix = rowId.substring(rowId.indexOf('_') + 1, rowId.length);
	if (suffix > 0)
		return suffix;
	else
		return -1;
}

function validatePctAmount(elementId){
	var rowNo = elementId.substring(elementId.indexOf('_') + 1, elementId.length);
	var pctAmountEntered = parseFloat(dojo.byId("pct_" + rowNo).value);
	if (pctAmountEntered < 0 || pctAmountEntered > 100){
		showErrorMessageCB("Please enter a valid pct amount");
		dojo.byId("pct_" + rowNo).value = "0.00";
		return false;
	} else {
		showErrorMessageCB("");
		return true;
	}
}

function validateProjectGrantPhase (){
	
	for(var i=0;i < noOfCodingBlocks;i++){
		var suffix = getNextCbRowSuffix(i + 1);
		var grant = dojo.byId("grant_" + suffix);
		var project =  dojo.byId("project_" + suffix);
		if (grant != undefined && !validatePhase (grant.value, dojo.byId("grantPhase_" + suffix).value)){
			showErrorMessageCB("Grant is required when phase is entered");
			return false;
		}
		
		if (project != undefined && !validatePhase (project.value, dojo.byId("projectPhase_" + suffix).value)){
			showErrorMessageCB("Project is required when phase is entered");
			return false;
		}
	}
	return true;
}

function validatePhase (item, itemPhase){
	if (itemPhase != undefined && itemPhase != ""){
		if (item != undefined && item == ""){
			// Blank coding block element for which a phase is entered
			return false
		}
	}
	return true;
}

function showErrorMessageCB(messageText) {
	var errorNode = dojo.byId('errorCodingBlockMsg');
	if (errorNode == undefined){
		errorNode = dojo.byId('statusArea');
	}
	errorNode.style.color = "red";
	errorNode.innerHTML = messageText;
}
