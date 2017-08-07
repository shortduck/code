/**
 * Constructs the Transaction Ledger Report URL and opens teh report in a popup window
 * @param viewerUrl
 * @return
 */

function openTransactionLedgerReport (viewerUrl){
	if (!validateAndSubmit(dojo.byId('empSelectionForm')))
		return false;
	var parameters;
	dojo.xhrPost({
		url :"TransactionLedgerViewer.action",
		form: dojo.byId('empSelectionForm'),
		handleAs :"json",
		sync: true,
		load : function(data) {
			//console.log(data.response);
			if(data.validationErrors.errors != null && data.validationErrors.errors.length > 0){
				dojo.byId('errorMsg').innerHTML = data.validationErrors.errors[0];
				return false;
			}else{
				parameters = data.response.parameters;
				var url = viewerUrl + "?" + parameters;
				var winReport = window.open(url, "TransactionLedgerReport", "width=900px,height=650px,modal=no,scrollbars=1,resizable=yes,status=1");
				return false;	
			}
		}
	});
}

function saveTransactionLedgerReportParams (viewerUrl){
	if (!validateAndSubmit(dojo.byId('empSelectionForm')))
		return false;
	if (dojo.byId('reportName_text').value == ''){
		dojo.byId('errorMsg').innerHTML = "A Report Name is required for this action";
		return false;
}
	var parameters;
	dojo.xhrPost({
		url :"TransactionLedgerReportParamsAction.action",
		form: dojo.byId('empSelectionForm'),
		handleAs :"json",
		sync: true,
		load : function(data) {
			//console.log(data.response);
			if(data.validationErrors.errors != null && data.validationErrors.errors.length > 0){
				dojo.byId('errorMsg').style.color = "red";
				dojo.byId('errorMsg').innerHTML = data.validationErrors.errors[0];
				return false;
			}else{
				dojo.byId('errorMsg').style.color = "green";
				dojo.byId('errorMsg').innerHTML = "Reports parameters added to the queue. Completed report will be made available under Report Manager accessed from the Employee menu";
				return false;	
			}
		}
	});
}
