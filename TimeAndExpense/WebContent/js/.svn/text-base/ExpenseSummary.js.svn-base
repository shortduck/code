	var store = null;
	var resultsSummaryByCategoryGridStore;
	var resultsSummaryByCodingBlockGridStore;
	var modifyClicked;
	var isAdj;	
	var buttonApproveNextClicked;
	var buttonApproveWithCommentsClicked;
	var moduleId = "";
	function displayExpenseSummaryList() {
		populateRevNoInSummary();
		var jsonResponseStr = dojo.byId('expenseSummaryHiddenJson').value;
		var summaryObj = eval('(' + jsonResponseStr + ')');
		dojo.forEach(summaryObj[0], function(item){
			convertNullFieldsToEmptyStrings(item);
		});		
		
		resultsSummaryByCategoryGridStore = new dojo.data.ItemFileReadStore(
							{data: {identifier: 'description',
								items:summaryObj[0] }
							}
						);
		
		dojo.forEach(summaryObj[1], function(item){
			convertNullFieldsToEmptyStrings(item);
		});
		
		resultsSummaryByCodingBlockGridStore = new dojo.data.ItemFileReadStore(
				{data: {identifier: 'sequence',
					items: summaryObj[1]}
				}
			);		
		dijit.byId('summaryByCategoryGrid').setStore(resultsSummaryByCategoryGridStore);
		dijit.byId('summaryByCodingBlockGrid').setStore(resultsSummaryByCodingBlockGridStore);

		modifyClicked = summaryObj[4];
    	dojo.byId('prevRevBtnInSummaryTab').disabled = dojo.byId('prevRevBtn').disabled;
    	dojo.byId('nextRevBtnInSummaryTab').disabled = dojo.byId('nextRevBtn').disabled;
    	
    	if (dojo.byId('lastSuccessfulOperation') != null && (dojo.byId('lastSuccessfulOperation').value != "" )) {
    		notifyExpenseSummary(dojo.byId('lastSuccessfulOperation').value, false);
    		dojo.byId('lastSuccessfulOperation').value = "";
		}else if((summaryObj[7] == "APRW003")&&(!modifyClicked)&&(summaryObj[9])){			
			if((dojo.byId('supervisorReceiptsReviewed') == null)||(dojo.byId('supervisorReceiptsReviewed') != null && !dojo.byId('supervisorReceiptsReviewed').checked)){
				notifyExpenseSummary("Supervisor must certify approval of supporting documentation on Expense tab", false);
			}
		}    	

    	if (!summaryObj[8]) {//preAuditRequiredAndCompleted
    		notifyExpenseSummary("Pre-Audit required and not completed", true);
		}   
    	isAdj = summaryObj[11];
		if((dojo.byId('supervisorReceiptsReviewed') != null && dojo.byId('supervisorReceiptsReviewed').checked && !summaryObj[6])||(!summaryObj[6] && summaryObj[7] == "APRW004")){
			dojo.byId('approveExpenseBtn').disabled = false;//enable
			dojo.byId('approveWithcommentsExpenseBtn').disabled = false;//enable
			dojo.byId('approveExpenseNextBtn').disabled = false;
			dojo.byId('approveExpenseSkipBtn').disabled = false;
		}else{
			//If no expense lines and there is liquidation. kp 
			if (summaryObj[10] && summaryObj[2] > 0) {
				dojo.byId('approveExpenseBtn').disabled = false;//enable
				dojo.byId('approveWithcommentsExpenseBtn').disabled = false;//enable
				dojo.byId('approveExpenseNextBtn').disabled = false;
				dojo.byId('approveExpenseSkipBtn').disabled = false;
			} else {
				dojo.byId('approveExpenseBtn').disabled = true;//disable
				dojo.byId('approveWithcommentsExpenseBtn').disabled = true;//disable	
				dojo.byId('approveExpenseNextBtn').disabled = true;
				//dojo.byId('approveExpenseSkipBtn').disabled = true;
			}
		}
	
		// attach counter to textfields marked with 'counter' class
		processTextFieldCounters();
		// Derive the submit button status by the "Save" button status on ID tab
		dojo.byId('submitBtn').disabled = dojo.byId('saveBtn').disabled;
		// save module id for later use
		moduleId = summaryObj[7];		
	}
	function populateRevNoInSummary(){
		dojo.byId('es_revNo').innerHTML = dojo.byId('revNo').innerHTML;
	}
	function ajaxSubmitExpense(){
		if (isAdj){
			dijit.byId('dialogCommentsAndSubmit').show();
		}else{ajaxSubmitExp("");}
	}
	function showCommentsAndSubmit(dialogFields, e) {
		commentsAfterModify = trimStr(dojo.byId("commentsAfterModifyId").value);
		if(commentsAfterModify == null || commentsAfterModify == undefined || commentsAfterModify == ""){
			notifyExpenseSummary("You must provide comments for submission", true);
			return;
		}else if(commentsAfterModify.length > 256){
			notifyExpenseSummary("You entered comments longer than 256 characters. Submission failed", true);
			return;
		}else{   		
			ajaxSubmitExp(commentsAfterModify);
		}
	}
	function ajaxSubmitExp(comments){
		var overlayDialog = dijit.byId('dummyDialogToDisableScreenWhileProcessing');
		
		// show overlay
		overlayDialog.show();
        document.body.style.cursor = 'wait';
        
        // temporarily override the hide() to persist the overlay until the server response
        var _hideFunctionBKup = overlayDialog.hide;
        overlayDialog.hide = function(){};
        
        dojo.xhrPost({
			url: "AjaxSubmitExpense.action",
			content: {'approverComments': comments},
			handleAs: "json",
			load: function(data,args){
				updateErrorsGrid(data.errors);
				var submitSuccess = data.response.submitSuccess;
				dojo.byId('submitBtn').disabled = submitSuccess;
				if (submitSuccess) {
					//window.location = window.location.pathname+"?actionCode=subm";				
					window.location = window.location;
				} else {
					notifyExpenseSummary(data.response.msg, !submitSuccess);
				}
				
		        // reset overlay's hide() & hide the overlay 
				overlayDialog.hide = _hideFunctionBKup;
		        document.body.style.cursor = 'default';
		        overlayDialog.hide();			
				}, 
			// Call this function if an error happened
			error: function (error) {
				notifyExpenseSummary("Submit failed", true);
				
		        // reset overlay's hide() & hide the overlay 
				overlayDialog.hide = _hideFunctionBKup;
				document.body.style.cursor = 'default';
				overlayDialog.hide();
			}
		});
	}
	
	function ajaxApproveExpense(){
  		var overlayDialog = dijit.byId('dummyDialogToDisableScreenWhileProcessing');
		
		// show overlay
		overlayDialog.show();
        document.body.style.cursor = 'wait';
        
        // temporarily override the hide() to persist the overlay until the server response
        var _hideFunctionBKup = overlayDialog.hide;
        overlayDialog.hide = function(){};
        			
		processApproval(buttonApproveNextClicked,overlayDialog,_hideFunctionBKup)
	}
	
	function ajaxOpenPrintDetailReport(){
		dojo.xhrPost({
			url: 'OpenPrintDetailReport.action',
			handleAs: "json",
			load: function(data,args){
				if(typeof data == "error"){
					//if errors, do not pursue the effect of call!
					console.warn("error!",args);
				}else{
					processExpenseSubmitResponse(data.response);
				}
			}
		});
	}

	function showCommentsAndSubmitApproval(dialogFields, e) {
        var approverComments = dojo.byId("approverComments");
		approverComments.value = dialogFields.comments;

		var comments = trimStr(approverComments.value);
		if(comments == null || comments == undefined || comments == ""){
			notifyExpenseSummary("You must provide comments for approval", true);
			return;
		}		
		if(comments.length > 256){
			notifyExpenseSummary("You entered comments longer than 256 characters. Approval failed.", true);
			return;
		}		
		
        var overlayDialog = dijit.byId('dummyDialogToDisableScreenWhileProcessing');
		// show overlay
		overlayDialog.show();
        document.body.style.cursor = 'wait';

        // temporarily override the hide() to persist the overlay until the server response
        var _hideFunctionBKup = overlayDialog.hide;
        overlayDialog.hide = function(){};
		processApproval(buttonApproveWithCommentsClicked,overlayDialog,_hideFunctionBKup)
	}	
	

	function processApproval(button,overlayDialog,_hideFunctionBKup){
		var message="Approve Failed";
		var comments = dojo.byId("approverComments").value;
		var supervisorReceiptsReviewed = false;
		if(dojo.byId('supervisorReceiptsReviewed') != null){
			supervisorReceiptsReviewed = dojo.byId('supervisorReceiptsReviewed').checked;
		}
		dojo.xhrPost({
			url: "AjaxApproveExpense.action",
			content: {'approverComments': comments, 'supervisorReceiptsReviewed':supervisorReceiptsReviewed},
			handleAs: "json",
			load: function(data,args){
					updateErrorsGrid(data.errors);
					var approveSuccess = data.response.approveSuccess;
					if (approveSuccess) {
						//window.location = window.location.pathname+"?actionCode=appr";
						if (button ){
							processApproveNextTransaction();
						} else {
							loadApprovalPage(moduleId);
						}
					} else {
						notifyExpenseSummary(data.response.msg, !approveSuccess);
					}
					setupDisplayPostApproval(approveSuccess);
          		//}
					setupDisplayOverlayDialog(overlayDialog,_hideFunctionBKup)
		},
		// Call this function if an error happened
		error: function (error) {
			notifyExpenseSummary(message, true);
			setupDisplayOverlayDialog(overlayDialog,_hideFunctionBKup)
		}
		});
	}
	function setupDisplayOverlayDialog(overlayDialog,_hideFunctionBKup){
		// reset overlay's hide() & hide the overlay 
		overlayDialog.hide = _hideFunctionBKup;
		document.body.style.cursor = 'default';
		overlayDialog.hide();
	}
	
	function setupDisplayPostApproval(approveSuccess){
		dojo.byId('approveWithcommentsExpenseBtn').disabled = approveSuccess;
		dojo.byId('approveExpenseBtn').disabled = approveSuccess;
		dojo.byId('rejectExpenseBtn').disabled = approveSuccess;
		dojo.byId('approveExpenseNextBtn').disabled = approveSuccess;
		dojo.byId('approveExpenseSkipBtn').disabled = approveSuccess;
	}
	
	function processApproveNextTransaction(){
		dojo.xhrPost({
	        
	        url: "ApprovalNextTransaction.action?requestId="+dojo.byId('expenseEventId').value,
	        handleAs: "json",
	        sync: true,
	        load: function(data){
	    		if (data.response.apptIdentifier != undefined && data.response.apptIdentifier != ""){
	    			var queryString = dojo.objectToQuery(data.response);
	    			dojo.byId('lastSuccessfulOperation').value = null;
	    			dojo.byId('approveExpenseSkipBtn').value="Skip";
	    			window.location = "ApproveReferrer.action?" + queryString;
	    		} else {
	    			loadApprovalPage(moduleId);
	    		}
	        },
			error: function (error){
	        	handleAjaxError(error);
	        }
	});
	}
	function showCommentsAndReject(dialogFields, e) {
	
		var approverComments = dojo.byId("approverComments");
		approverComments.value = dialogFields.comments;

		var comments = trimStr(approverComments.value);
		if(comments == null || comments == undefined || comments == ""){
			notifyExpenseSummary("You must provide comments for rejection", true);
			return;
		}
		if(comments.length > 256){
			notifyExpenseSummary("You entered comments longer than 256 characters. Rejection failed.", true);
			return;
		}			

		var overlayDialog = dijit.byId('dummyDialogToDisableScreenWhileProcessing');
		
		// show overlay
		overlayDialog.show();
        document.body.style.cursor = 'wait';

        // temporarily override the hide() to persist the overlay until the server response
        var _hideFunctionBKup = overlayDialog.hide;
        overlayDialog.hide = function(){};
         
			dojo.xhrPost({
				url: "AjaxRejectExpense.action",
				content: {'approverComments': approverComments.value},
				handleAs: "json",
				load: function(data,args){
					//if(typeof data == "error"){
						//if errors, do not pursue the effect of call!
						//console.warn("error!",args);
					//}else{
						
						updateErrorsGrid(data.errors);
						var RejectSuccess = data.response.RejectSuccess;
						if (RejectSuccess) {
							//window.location = window.location;
							// changed to either load next transaction or the approvals page
							approveSkip("reject");
						} else {
							notifyExpenseSummary(data.response.msg, !RejectSuccess);
						}
						dojo.byId('approveWithcommentsExpenseBtn').disabled = RejectSuccess;
						dojo.byId('approveExpenseBtn').disabled = RejectSuccess;
						dojo.byId('approveExpenseNextBtn').disabled = RejectSuccess;
						dojo.byId('approveExpenseSkipBtn').disabled = RejectSuccess;
						dojo.byId('rejectExpenseBtn').disabled = RejectSuccess;
					//}

						// reset overlay's hide() & hide the overlay 
						overlayDialog.hide = _hideFunctionBKup;
						document.body.style.cursor = 'default';
						overlayDialog.hide();						
				},
				// Call this function if an error happened
				error: function (error) {
					notifyExpenseSummary("Reject failed", true);

					// reset overlay's hide() & hide the overlay 
					overlayDialog.hide = _hideFunctionBKup;
					document.body.style.cursor = 'default';
					overlayDialog.hide();						
				}
			});
	}
	
	function convertNullFieldsToEmptyStrings(item){
		if(item.appropriationYear == undefined) item.appropriationYear='';
		if(item.indexCode == undefined) item.indexCode='';
		if(item.pca == undefined) item.pca='';
		if(item.grantNumber == undefined) item.grantNumber='';
		if(item.grantPhase == undefined) item.grantPhase='';
		if(item.agencyCode1 == undefined) item.agencyCode1='';
		if(item.projectNumber == undefined) item.projectNumber='';
		if(item.projectPhase == undefined) item.projectPhase='';
		if(item.agencyCode2 == undefined) item.agencyCode2='';
		if(item.agencyCode3 == undefined) item.agencyCode3='';
		if(item.multipurposeCode == undefined) item.multipurposeCode='';
		if(item.standardInd == undefined) item.standardInd='';
		if (item.dollarAmount == undefined) {
			item.dollarAmount='';	
		}

		if(item.description == undefined) item.description='';
		if (item.amount == undefined) {
			item.amount='';
		}
	};
	
	function clickCertify(){
		dojo.xhrPost({
			url: 'CertifyExpense.action',
			handleAs: "json",
			load: function(data,args){
					if( data.response.msg == "success"){
						//disbale button
						if (dojo.byId("certifyExpenseBtn") != null){
							dojo.byId("certifyExpenseBtn").disabled = true;
							notifyExpenseSummary("Certified", false);
						}
					}
					else{
						notifyExpenseSummary("Certification failed.", true);
					}
				},			
			error: function(error){
				notifyExpenseSummary("Certification failed.", true);
			}
		
		});		
	}
	
	function approveSkip(action){
		//var num=dojo.byId('expenseEventId').value;
		
	    dojo.xhrPost({        
	        url: "ApprovalNextTransaction.action?skip=" + action+"&requestId="+dojo.byId('expenseEventId').value,
	        handleAs: "json",
	        sync: true,
	        load: function(data){
	    		if (data.response.apptIdentifier != undefined && data.response.apptIdentifier != ""){
	    			var queryString = dojo.objectToQuery(data.response);
	    			window.location = "ApproveReferrer.action?" + queryString;
	    		} else {
    			loadApprovalPage(moduleId);
    		}
	        },
			error: function (error){
	        	handleAjaxError(error);
	        }
	});
	}
