/* Function to notify TripId tab about the status of operation performed. */
function notifyTripId(msg, isErrorMsg){
	if(isErrorMsg == null) isErrorMsg = true;

	var errorMsgNode = dojo.byId('errorMsg');
	notifyTab(errorMsgNode, msg, isErrorMsg);
}

/* Function to notify Expenses Tab about the status of operation performed. */
function notifyExpenseDetails(msg, isErrorMsg){
	if(isErrorMsg == null) isErrorMsg = true;

	var errorMsgNode = dojo.byId('expenseDetailErrorMsg');
	notifyTab(errorMsgNode, msg, isErrorMsg);
}

/* Function to notify coding Block tab about the status of operation performed. */
function notifyCodingBlock(msg, isErrorMsg){
	if(isErrorMsg == null) isErrorMsg = true;

	var errorMsgNode = dojo.byId('errorCodingBlockMsg');
	notifyTab(errorMsgNode, msg, isErrorMsg);
}

function notifyExpenseSummary(msg, isErrorMsg){
	if(isErrorMsg == null) isErrorMsg = true;

	var errorMsgNode = dojo.byId('expenseSummaryErrorMsg');
	notifyTab(errorMsgNode, msg, isErrorMsg);
}

/* Function to notify Expenses Tab about the status of operation performed. */
function notifyLiquidations(msg, isErrorMsg){
	if(isErrorMsg == null) isErrorMsg = true;

	var errorMsgNode = dojo.byId('expenseLiquidationErrorMsg');
	notifyTab(errorMsgNode, msg, isErrorMsg);
}
/* Prints out error messages to the provided node */
function notifyTab(tabErrorMsgNode, msg, isErrorMsg){
	dojo.style(tabErrorMsgNode, 'color', isErrorMsg ? 'RED': 'GREEN');
	tabErrorMsgNode.innerHTML = msg;
}

/* Switch to previous tab in case of errors */
function revertToLastTab (lastTab) {
	// switch back to previous tab if the save was applied through a popup
	if (lastTab != undefined && lastTab != ""){
		dijit.byId('expense_tab_container').selectChild(lastTab);
	}
}

function processPopupPostSave () {
	// switch back to previous tab if the save was applied through a popup
	// reset tab reference when used through
	lastTabForAjaxSave = "";
}

