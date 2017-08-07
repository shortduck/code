		var disableForm;
		var currentSelectedDetailRow = 0;
		var cbUpdates = [];
    	
    	// Method called to display the expense details List
    	    function displayTravelRequisitionList(){
       		
        		// Get the expense details identifier and call Ajax to get the coding blocks associated with the expense detail item
        		dojo.xhrPost({
        					url:"GetTravelRequisitionCodingBlocks.action?expDtlsId=" + '1234',
        					handleAs: "json",
        					sync: true,
        					load: function(data,args){
        						if(typeof data == "error"){
        						//if errors, do not pursue the effect of call!
        							console.warn("error!",args);
        						}else{
        								
        							displayCodingBlocks(data);
        							//disable forms fields if applicable
        			    			setupDisplayCb();
        						}
        				}
        			});
        		//	dijit.byId("resultCodingBlockGrid").resize();
    				/*dojo.xhrPost({
    					url:"AjaxGetExpenseDetailsList.action",
    					handleAs: "json",
    					sync: true,
    					load: function(data,args){
    						if(typeof data == "error"){
    						//if errors, do not pursue the effect of call!
    							console.warn("error!",args);
    						}else{
    								//if there are expense line items.
    							     // Reload the CodingBlock tab page on deletion of last line from expense summary. kp  
    							updateResultGrid(data.response);
    								if (data.response.length == 0){
    								// no detail line items - disable save button and selectAll checkbox
    								dojo.byId("chkBoxSelectAll").disabled = true;
    								dojo.byId('cbSave').disabled = true;
    							     }
    								
    						}
    				}
    			});
    				// reset globals
    				rowArray = [];
    				cbUpdates = [];
    				cbTabLoaded = true;
    				*/
        			// derive the mode for the Save and revision buttons from the ID tab 
    				// derive the mode for the Save and revision buttons from the ID tab 
        			dojo.byId("cb_revNo").innerHTML = dojo.byId("revNo").innerHTML;
        			dojo.byId("cbPrev").disabled = dojo.byId("prevRevBtn").disabled;
            		dojo.byId("cbNext").disabled = dojo.byId("nextRevBtn").disabled;
            		dojo.byId("cbSave").disabled = dojo.byId('buttonSave').disabled;
    	}
    	
    	// method to retreive the response Json data and build the Grid store
    	function updateResultGrid(data){
    				/*showCheckBox(data);
    				
    				dojo.forEach(data, function(item){
    					item.dollarAmountDisplay = "<span style='float:left'>$</span>" + "<span style='float:right'>" + formatCurrency(item.dollarAmount) + "</span>";
    					convertNullFieldsToEmptyStrings(item);
    				});
    				expenseSummaryGridStore = new dojo.data.ItemFileReadStore(
    						{data: {identifier: 'expdIdentifier',
    						items: data}
    					}
    				);*/
    		/*dijit.byId('resultCodingBlockGrid').setStore(expenseSummaryGridStore);
    		var firstRow = dijit.byId("resultCodingBlockGrid").rows.grid.store._arrayOfAllItems[0];
    		var selCbRow = dijit.byId("resultCodingBlockGrid").selection.getSelected()[0];
    		// To highlight the first row on the grid
    		// Verify the firstRow should not be undefined. kp  
    		if (selCbRow == undefined && firstRow != undefined ){
    			// if the tab was not accessed before
    		dijit.byId("resultCodingBlockGrid").rows.setOverRow( 0 );
    		dijit.byId("resultCodingBlockGrid").selection.setSelected(0, true);*/
    		
    		//var row = dijit.byId("resultCodingBlockGrid").selection.getSelected()[0];
    		cellValue = firstRow.expdIdentifier;
    		
    		// Get the expense details identifier and call Ajax to get the coding blocks associated with the expense detail item
    		dojo.xhrPost({
    					url:"AjaxGetExpenseDetails.action?expDtlsId=" + '1234',
    					handleAs: "json",
    					sync: true,
    					load: function(data,args){
    						if(typeof data == "error"){
    						//if errors, do not pursue the effect of call!
    							console.warn("error!",args);
    						}else{
    							displayCodingBlocks(data);
    							//disable forms fields if applicable
    			    			setupDisplayCb();
    						}
    				}
    			});
    			//dijit.byId('resultCodingBlockGrid').selection.multiSelect = false;
    			//dojo.connect(dijit.byId('resultCodingBlockGrid'),'onCellClick', "getExpenseDetailsCodingBlock");
    		//}
    			//dijit.byId("resultCodingBlockGrid").resize();
    	}
    	
    	// utility Method to convert the "..." on each grid cell to blank. 
    	function convertNullFieldsToEmptyStrings(item){
			if(item.lineItem == undefined) item.lineItem='';
			if(item.expDate == undefined) item.expDate='';
			if(item.expTypeCode == undefined) item.expTypeCode='';
			if(item.dollarAmount == undefined) item.dollarAmount='';
		}
    	
    	
    	// Method to check which check boxes are selected. Add the Id of the selected cell to the Array. If the checkbox is unchecked, then 
    	// remove that element from the array
    	function getCheckedRows(selectedCell){
    		
    		dojo.byId('errorCodingBlockMsg').innerHTML = "";
    		if(selectedCell.checked){
    			rowArray.push(selectedCell.id);
    		}	
    		else{
    			if(!Array.indexOf){
    				// this is needed because indexOf for arrays in Internet Explorer does not work
    			          for(var i=0; i < rowArray.length; i++){
    			              if(rowArray[i]== selectedCell.id){
    			                  index = i;
    			                  break;
    			              }
    			          }
    			          if (i >= 0){
    			        	  rowArray.splice(i,1);
    			          }
    			        		  
    			  } else {
    				  rowArray.splice(rowArray.indexOf(selectedCell.id),1);
    			  }

    		}
    	}
    	
    	// Get the coding block for the selected Expense line
    	
    	function getExpenseDetailsCodingBlock(selectedRow){
    		
    		// If the user checks the checkbox or clicks on the Update cell, the coding block rows are not retrieved. User has to click on any cell 
    		// on any column other than the Update column
    		
    		if(dojo.byId('cbTable').rows.length>0 && selectedRow.cell.field!="chkBoxUpdate"){
    			currentSelectedDetailRow = selectedRow.rowIndex;			
    			//dojo.byId('cbTable').innerHTML="";
    			while(dojo.byId('cbTable').hasChildNodes()){
    				dojo.byId('cbTable').removeChild(dojo.byId('cbTable').firstChild);
    			}

    			dojo.byId('errorCodingBlockMsg').innerHTML ="";
    			
    			if(isChanged){
    				var answer = confirm("Changes will not be saved. Do you wish to continue ? ");
    				if (!answer){
    					return;
    				}
    			}
    			isChanged = false;	
    		} else{
    			//dijit.byId("resultCodingBlockGrid").selection.setSelected(0);
    			//dijit.byId("resultCodingBlockGrid").rows.setOverRow( currentSelectedDetailRow );
    			//dijit.byId("resultCodingBlockGrid").selection.setSelected(0,true);
    			//dijit.byId("resultCodingBlockGrid").selection.setSelected(selectedRow.rowIndex, false);
    		}
    		
    		// Dont do anything if user clicked on the Update column
    		if(selectedRow.cell.field=="chkBoxUpdate") return;
    		
    		
    		
    		//var row = selectedRow.grid.store._arrayOfAllItems[selectedRow.rowIndex];
    		//var row = dijit.byId("resultCodingBlockGrid").selection.getSelected()[0];
    		/*cellValue = row.expdIdentifier;*/
    		//cellValue = dijit.byId("resultCodingBlockGrid").selection.getSelected()[0].expdIdentifier;
    		dijit.byId(row.expdIdentifier).checked = true;

    			// Ajax call to the server to get the associated Coding block/s
        		dojo.xhrPost({
    					url:"AjaxGetExpenseDetails.action?expDtlsId=" + cellValue,
    					handleAs: "json",
    					sync: true,
    					handle: function(data,args){
    						if(typeof data == "error"){
    						//if errors, do not pursue the effect of call!
    							console.warn("error!",args);
    						}else{
    							displayCodingBlocks(data); // method to display the coding block/s
    						}
    				}
    			});
        		//set CB display state
        		setupDisplayCb();        		
    	}
    	
    	
    	// Method to display the coding block/s
    	function displayCodingBlocks(data){
    		
    		// These values are passed as hidden fields from the server
    		selected = data.response[0].selected; // selected data 
    		cbStore = data.response[1]; // Coding block dropdown values
    		cbOptions = data.response[2].cboption; // Coding block meta data (which dropdowns to show/hide)
    		noOfCodingBlocks = data.response[3].noOfCodingBlocks; // no of coding blocks to display
    		
    		// To stop propagating any event when a row is already selected
    		//dijit.byId('resultCodingBlockGrid').onCellClick=dojo.stopEvent;
    		createTable();
    		// disable buttons if no detail rows
        	/*if (dijit.byId("resultCodingBlockGrid").rowCount == 0){
        		dojo.byId("cbUpdate").disabled = true;
        		dojo.byId("cbSave").disabled = true;
        		
        	}*/
    	}
    	
      	
    	// Method to validate the percent 
    	function validatePercentage(queryString){
    		isPctValid = true;
    		var sum = 0;
    		for(var i=0;i<queryString.codingBlocks.length;i++){
    			if(isNaN(queryString.codingBlocks[i].percent)){
    				isPctValid = false;
    				break;
    			}
    			var pct = queryString.codingBlocks[i].percent;
    				sum += pct;
    				
    		}
    			
    			if (sum > 100){
    				showErrorMessage('The percent sum of all Coding Block rows may not exceed 100%');
    				isPctValid = false;
    			} else if (sum < 100){
    				showErrorMessage('The percent sum of all Coding Block rows may not be less than 100%');
    				isPctValid = false;
    			}
    			return isPctValid;
    		}
   
    	
    	//Method to validate appropriation year
    	function validateAppropriationYear(queryString){
    		isAyValid = true;
    		for(var i=0;i<queryString.codingBlocks.length;i++){
    			if(queryString.codingBlocks[i].appropriationYear=="" && queryString.codingBlocks[i].standardInd=="N"){
    				isAyValid = false;
    				break;
    			}
    		}
    		return isAyValid;		
    	}
    	
    	//Method to validate empty coding block
    	function validateEmptyCodingBlock(queryString){
    	
    		var isEmpty=false;
    		for(var i=0;i<queryString.codingBlocks.length;i++){
    			if((queryString.codingBlocks[i].appropriationYear=="" || queryString.codingBlocks[i].indexCode=="") 
    			&& queryString.codingBlocks[i].standardInd=="N"){
    				isEmpty = true;
    				break;
    			}
    		}
    		return isEmpty;
    	}

    	function submitCodingBlockUsingAjax(){
    				
    				// Get the expenseDetails IDs of the selected rows.
    				/*var expDetailsRows = rowArray.toString();
    				
    				// error if no rows are selected
    				if(expDetailsRows.length==0){
    					showErrorMessage("No rows are selected.  Please select a row to proceed");
    					return false;
    				}
    				dojo.byId('cb_form').checkedRowIds.value = rowArray.toString();*/

    				// build the query string
    				var queryString = buildQueryString("cb_form");
    				
    				addNewQueryString(queryString);
    				
    				// validate coding block rows
    				if (!validateCodingBlocks()){
    					return false;
    				}
    				    				
    				dojo.byId('errorCodingBlockMsg').innerHTML ="";
    				
    				//Ajax call to update the checked rows and apply the coding block changes to the selected rows.
    				//dojo.byId('cb_form').codingBlockJsonFromClient.value = dojo.toJson(queryString);
    				dojo.byId('cb_form').codingBlockJsonFromClient.value = dojo.toJson(cbUpdates);
    				
    			//rowArray = [];
    			formModified = true;
                return true;
             }
    	
    		// Callback method of submitCodingBlockUsingAjax()
    		 function processCodingBlockUpdateResponse(data) {
    			 //dojo.byId('errorCodingBlockMsg').innerHTML = "Update successful";
    			 if(data.validationErrors.errors != null){
    					processValidationErrors(data.validationErrors.errors);
    					return false;
    				}
    			 
    			 showStatusMessage("Update successful");
    			 if(data.errors != null) {
    				 updateErrorsGrid(data.errors);
    			 }
    		 }	
    		 
    		 /* Shows all errors in the status bar */
    		 function processValidationErrors(errors){
    			 var retStatus = true;
    		 	var errorMsg = '';
    		 	dojo.forEach(errors, function(item){
    		 		errorMsg += item + '<br/>';
    		 		retStatus = false;
    		 	});
    		 	
    		 	displayValidationErrorMsg(errorMsg);
    		 	return retStatus;
    		 }

    		 function displayValidationErrorMsg(msg){
    		 	//dojo.byId('statusArea').innerHTML = msg;
    		 	showErrorMessage(msg);
    		 }
    		 
    		// Callback method of submitCodingBlockUsingAjax()
    		 function processCodingBlockSaveResponse(data) {
    			 if (data.validationErrors != undefined){
    				 if (!processValidationErrors(data.validationErrors.errors))
    						 return false;
    			 }
    			 if (data.response.saveStatus == "success")
    				 showStatusMessage("Save successful", "CB");
    			 else 
    				 showErrorMessage("An error has occured", "CB");
    			 if(data.errors != null) {
    				 formatCodingBlockErrorsAjax(data.errors);
    				 updateErrorsGrid(data.errors);
    			 }
    			// clearCheckBoxes();
    			 // reset Std checkboxes in top grid if needed
    			 //setStdCheckBoxes();
    			 return true;
    		 }	
             
             function buildQueryString(formObj){
             
             	var table = dojo.byId('cbTable');
             //	var gridStore  = dijit.byId('resultCodingBlockGrid').store._arrayOfAllItems;
             	var form = dojo.byId(formObj);
             	var queryString = [];
             	
             	// If there is only 1 coding block
             	if((table.rows.length-1)==1){
             		var cbObj = {};
             			cbObj.percent = parseFloat(form.pct.value);
             			cbObj.appropriationYear = form.ay.value;
             			
             			if(form.index!=undefined) cbObj.indexCode = form.index.value;
             			if(form.pca!=undefined) cbObj.pca = form.pca.value;
             			if(form.grant!=undefined) cbObj.grantNumber = form.grant.value;
             			if(form.grantPhase!=undefined)	cbObj.grantPhase = form.grantPhase.value;
             			if(form.ag1!=undefined) cbObj.agencyCode1 = form.ag1.value;
             			if(form.ag2!=undefined) cbObj.agencyCode2 = form.ag2.value;
             			if(form.ag3!=undefined) cbObj.agencyCode3 = form.ag3.value;
             			if(form.project!=undefined) cbObj.projectNumber = form.project.value;
             			if(form.projectPhase!=undefined) cbObj.projectPhase = form.projectPhase.value;
             			if(form.multi!=undefined) cbObj.multipurposeCode = form.multi.value;
             			cbObj.standardInd = form.std.value;
             			if(dojo.byId('expdcId_0')==undefined)
             				cbObj.expdcIdentifier=null;
             			else	
             				cbObj.expdcIdentifier=parseInt(form.expdcId_0.value);
             			if(dojo.byId('expdId_0')==undefined)	
             				cbObj.expdIdentifier = null;
             			else	
             				cbObj.expdIdentifier = parseInt(form.expdId_0.value);
             				
             			queryString.push(cbObj);
             	}
             	//If more than 1 coding block
             	else{
             	for(var i =0;i<table.rows.length-1;i++){
             	
             			var cbObj = {};
             			
             			cbObj.percent = parseFloat(form.pct[i].value);
             			cbObj.appropriationYear = form.ay[i].value;
             			
             			if(form.index!=undefined) cbObj.indexCode = form.index[i].value;
             			if(form.pca!=undefined) cbObj.pca = form.pca[i].value;
             			if(form.grant!=undefined) cbObj.grantNumber = form.grant[i].value;
             			if(form.grantPhase!=undefined)	cbObj.grantPhase = form.grantPhase[i].value;
             			if(form.ag1!=undefined) cbObj.agencyCode1 = form.ag1[i].value;
             			if(form.ag2!=undefined) cbObj.agencyCode2 = form.ag2[i].value;
             			if(form.ag3!=undefined) cbObj.agencyCode3 = form.ag3[i].value;
             			if(form.project!=undefined) cbObj.projectNumber = form.project[i].value;
             			if(form.projectPhase!=undefined) cbObj.projectPhase = form.projectPhase[i].value;
             			if(form.multi!=undefined) cbObj.multipurposeCode = form.multi[i].value;
             			cbObj.standardInd = form.std[i].value;

             			// Get the expense details Id and expenseDetails Codingblock Id to determine if this is an existing coding block
             			if(dojo.byId('expdcId_'+i)==undefined)
             				cbObj.expdcIdentifier=null;
             			else	
             				cbObj.expdcIdentifier=parseInt(dojo.byId('expdcId_'+i).value);

             			if(dojo.byId('expdId_'+i)==undefined)	
             				cbObj.expdIdentifier = null;
             			else	
             				cbObj.expdIdentifier = parseInt(dojo.byId('expdId_'+i).value);
             			
             			queryString.push(cbObj);
             			
             		  }
             	}
    		    // create a Javascript object
             	var dataToBeUpdated = {};
             	// copy current selected array values
             	var currentCheckedRows = rowArray.slice(0);
    		    dataToBeUpdated.lineItemId = currentCheckedRows;
    	    	dataToBeUpdated.codingBlocks = queryString;

    	    	return dataToBeUpdated;
             }
           
             
            // Method to save the Coding block 
           	function saveCodingBlockUsingAjax(){
           		if (!submitCodingBlockUsingAjax ()){
           			return false;
           		}
           		var obj = dojo.byId('cb_form');
           		showStatusMessage("");
                    dojo.xhrPost({
                        url: "TravelRequisitionCodingBlockSave.action",
                        handleAs: "json",
                        form: obj,
                        sync: true,
                        load: function(response){
                            if(typeof response == "error"){
                                console.warn("error!",args);
                            }else{                         	              
                                cbUpdates = [];
                                saveCompleted = true;
                                if (!processCodingBlockSaveResponse(response)){
                                	// this will evaluate to true for problems during Save
                                	revertToLastTab(lastTabForAjaxSave);
                    				}
                            
                                // reset row arrays
                                //rowArray = [];
                                
                            }
                           // dijit.byId("resultCodingBlockGrid").resize();
                        },
                        error: function(error){
                        	showErrorMessage("Save failed", "CB");
                        }
                });
                    
                	// cleanup popup references
                    processPopupPostSave ();
                    formModified = false;
                return false;
             }  
             
           	// Method to delete a coding block
             function deleteCodingBlock(rowNo,expenseCodingBlockId){

             	dojo.xhrPost({
                        url: "AjaxDeleteCodingBlock.action",
                        content: {'expenseCodingBlockId': expenseCodingBlockId},
                        handleAs: "json",
                        sync: true,
                        load: function(response){
                            if(typeof data == "error"){
                                console.warn("error!",args);
                            }else{
                                console.log(response);
                            }
                        }
                });
                return false;
             }
    	
            // Method to retrict "SORT" on the "Update?" column 
    		function noSort(inSortInfo){
           
                  if(inSortInfo == 5){
                                    return false;
                            }else{
                                    return true;
                            }
            }
    		
    		// Method to select/deselect all the check boxed when Select all the clicked
            
    		function selectAll(chkbox) {
                            var grid = dijit.byId('resultCodingBlockGrid');
                            
                            if (chkbox.checked) {
                            	rowArray = [];
                            	  grid.store.fetch({
                            			query:{expdIdentifier: '*'},
                            		    onError: function(){},
                            		    onComplete: function(items, request){                
                            		    	dojo.forEach(items, function(item){
                            		    		var cbStr = item.chkBoxUpdate[0];
                                           		cbStr = cbStr.substring(0,cbStr.length-2);
                                           		cbStr += ' checked="checked" />';
                                           		
                                           		item.chkBoxUpdate[0] = cbStr;
                                           		//if (rowArray.indexOf(item.expdIdentifier+'') < 0)
                                           		rowArray.push(item.expdIdentifier+'');
                                           		//rowArray.push(item.expdIdentifier+'');
                            		    	});
                            		    }
                            		});                           		                              	                             		
                                   
    							expenseSummaryGridStore = new dojo.data.ItemFileReadStore({
    									data: {
    											identifier: 'expdIdentifier',items: grid.store._arrayOfAllItems
    										  }
    									});
    							 
                                grid.setStore(expenseSummaryGridStore);  
                                
                                   
                            } else {
                            	 
                          	  grid.store.fetch({
                      			query:{expdIdentifier: '*'},
                      		    onError: function(){},
                      		    onComplete: function(items, request){                
                      		    	dojo.forEach(items, function(item){
                      		    		var cbStr = item.chkBoxUpdate[0];
                      		    		if (cbStr.indexOf("checked") >= 0){
                                   		cbStr = cbStr.substring(0,cbStr.length-21);
                                   		cbStr += "/>";
                                   		item.chkBoxUpdate[0] = cbStr;
                      		    		}
                      		    	});
                      		    }
                      		});
                            	

                                   rowArray =[];
                                   
                                   expenseSummaryGridStore = new dojo.data.ItemFileReadStore({
   									data: {
   											identifier: 'expdIdentifier',items: grid.store._arrayOfAllItems
   										  }
   									});
                                   
                               grid.setStore(expenseSummaryGridStore); 
                               
                            	//}
                            }
                            dijit.byId("resultCodingBlockGrid").resize();
              }
              
    		// Method to show checkboxes on the the Update column	
              function showCheckBox(data) {
            	// update checkboxes
              	dojo.forEach(data, function(item){
    					item.chkBoxUpdate = '<input type="checkbox" align="center" id="'+item.expdIdentifier+'" onClick="getCheckedRows(this)" />';
    					item.chkBoxUpdate.id = item.expdIdentifier;
    				});
              	
              	// Standard checkboxes
            	dojo.forEach(data, function(item){
            		if (item.includesStd == "Y"){
            			item.chkBoxStd = '<input type="checkbox" disabled align="center" id="'+item.expdIdentifier+'cb'+'" checked="checked"/>';
					} else{
					item.chkBoxStd = '<input type="checkbox" disabled align="center" id="'+item.expdIdentifier+'cb'+'"/>';
					}
					item.chkBoxStd.id = item.expdIdentifier + 'cb';
				});
              } 
        	  function formatCurrency(num) {
        		  num = num.toString().replace(/\$|\,/g,'');
        		  if(isNaN(num))
        		  num = "0";
        		  sign = (num == (num = Math.abs(num)));
        		  num = Math.floor(num*100+0.50000000001);
        		  cents = num%100;
        		  num = Math.floor(num/100).toString();
        		  if(cents<10)
        		  cents = "0" + cents;
        		  for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
        		  num = num.substring(0,num.length-(4*i+3))+','+
        		  num.substring(num.length-(4*i+3));
        		  return (((sign)?'':'-') + num + '.' + cents);
        		  }
        	  
        	  function setupDisplayCb (){
      			disableForm = dojo.byId('buttonSave').disabled;
    			var cbForm = dojo.byId('cb_form');
    			if (disableForm){
    				var f = cbForm.getElementsByTagName('input');
    				   for(var i=0;i<f.length;i++){
    					   if (f[i].type != "submit"){
    						   // disable all other inputs other than command buttons or 
    						   // the fields that identifies coding block form. The buttons
    						   // mode will be driven according to the mode available from the ID tab
    						   f[i].readOnly = true;
    					   }
    				   }
    				// disable checkboxes in each CB row
 //   				disableCheckBoxes ();
        			// disable all combo boxes
        			toggleDojoControls("cb_form", true);
        			dojo.byId('cbSave').disabled = true;
    				
    			}
        	  }
        	  
        	// toggles only dojo combo boxes
        	  function toggleDojoControls (form, flag){
        	  	
        	  	var elem = document.getElementById(form).elements;
        	      // var box = dijit.byId('box');
        	      for(var i = 0; i < elem.length; i++){
        	      var dijitElement = dijit.byId(elem[i].id);
        	      if (dijitElement != undefined){
        	          if ((dijitElement.declaredClass == 'dijit.form.ComboBox') || (dijitElement.declaredClass == 'dijit.form.FilteringSelect')){
        	        	  	dijitElement.attr('disabled',flag);
        	          } else if (dijitElement.declaredClass == 'dijit.form.CheckBox'){
        	       		dijitElement.attr('readOnly',flag);
        	       	  }
        	      }
        	      }
        	  	
        	  }
        	  
 /*       	  function showStatusMessage(messageText) {
        			dojo.byId('errorCodingBlockMsg').style.color = "green";
        			dojo.byId('errorCodingBlockMsg').innerHTML = messageText;
        		}

        		function showErrorMessage(messageText) {
        			dojo.byId('errorCodingBlockMsg').style.color = "red";
        			dojo.byId('errorCodingBlockMsg').innerHTML = messageText;
        		}*/
        		
        		function addNewQueryString(queryString){
        			// first scrub existing ids from updates array
        			var newDetailIds = queryString.lineItemId;
    				var emptyLineItemsIndex = [];
    				/*if (cbUpdates.length > 0){
    				for (var i=0; i < newDetailIds.length ; i++){
    					for (var j=0; j < cbUpdates.length ; j++){
    						var currentDetailsIds = cbUpdates[j].lineItemId;
    						//var index = currentDetailsIds.indexOf(newDetailIds[i]);
    						var index = findArrayIndex(newDetailIds[i], currentDetailsIds);
    						if (index >= 0){
    							currentDetailsIds.splice(index, 1);
    							if (currentDetailsIds.length == 0){
    								emptyLineItemsIndex.push(j);
    							}
    							break;
    						}
    							
    					}
    					}
    				}    				
    				
    				// remove array index if all existing ids have been deleted 
    				for (var k = 0; k < cbUpdates.length ; k++){
    					if (cbUpdates[k].lineItemId.length == 0){
    						cbUpdates.splice(k, 1);
    						k -= 1;
    					}
    						
    				}*/
    				// add new query string
    				cbUpdates.push(queryString);
        		}
        		
        		function findArrayIndex (element, myArray){
        			var index = -1;
        			if(!Array.indexOf){
        				// this is needed because indexOf for arrays in Internet Explorer does not work
        			          for(var i=0; i < myArray.length; i++){
        			              if(myArray[i]== element){
        			                  index = i;
        			                  break;
        			              }
        			          }
        			        		  
        			  } else {
        				  index = myArray.indexOf(element);
        			  }
        			return index;
        		}
        		
        		/**
        		 * Clear all checkboxes
        		 * @return
        		 */
        		function clearCheckBoxes(){
        			dojo.byId("chkBoxSelectAll").checked = false;
        			selectAll(dojo.byId("chkBoxSelectAll"));
        		}
        		
        		function disableCheckBoxes(){
    			// disable top level checkbox
    			dojo.byId('chkBoxSelectAll').disabled = true;
    			// disable checkboxes for expense line items
        		var grid = dijit.byId('resultCodingBlockGrid');
              	  grid.store.fetch({
          			query:{expdIdentifier: '*'},
          		    onError: function(){},
          		    onComplete: function(items, request){                
          		    	dojo.forEach(items, function(item){
          		    		var cbStr = item.chkBoxUpdate[0];
                         		cbStr = cbStr.substring(0,cbStr.length-2);
                         		cbStr += ' disabled />';
                         		
                         		item.chkBoxUpdate[0] = cbStr;
                         		//if (rowArray.indexOf(item.expdIdentifier+'') < 0)
          		    	});
          		    }
          		});  
              	// reset store for grid
					expenseSummaryGridStore = new dojo.data.ItemFileReadStore({
						data: {
								identifier: 'expdIdentifier',items: grid.store._arrayOfAllItems
							  }
						});				 
                grid.setStore(expenseSummaryGridStore);  
                
        		}
        		
        		function setStdCheckBoxes(){
        			var cbForm = dojo.byId('cb_form');
        			var includesStd = false;
        			if (cbForm.ay.length == undefined && cbForm.ay.value == ""){
        				// blank ay used to derive Std. There must also be a single CB row 
        				includesStd = true;
        			}
        			// expense line items to be updated
        			var expenseLinesTobeUpdated = cbForm.checkedRowIds.value.split(',');
        			var size = expenseLinesTobeUpdated.length;
        			
        			var grid = dijit.byId('resultCodingBlockGrid');
        			
                    for (var i = 0; i < size ; i ++){
                    	// for each line item to be updated
                    	  grid.store.fetch({
                    			query:{expdIdentifier: '*'},
                    		    onError: function(){},
                    		    onComplete: function(items, request){                
                    		    	dojo.forEach(items, function(item){
                    		    		// match the expense line item in the grid store
                    		    		if (item.expdIdentifier == expenseLinesTobeUpdated[i]){
	                    		    		var cbStr = item.chkBoxStd[0];
	                    		    		if (!includesStd){
	                    		    			// turn off check box
		                      		    		if (cbStr.indexOf("checked") >= 0){
			                                   		cbStr = cbStr.substring(0,cbStr.length-21);
			                                   		cbStr += "\"/>";
			                                   		item.chkBoxStd[0] = cbStr;
		                      		    		}
	                    		    		} else {
	                    		    			if (cbStr.indexOf("checked") < 0){
	                    		    				// turn on check box
	                                           		cbStr = cbStr.substring(0,cbStr.length-2);
	                                           		cbStr += ' checked="checked" />';
	                                           		item.chkBoxStd[0] = cbStr;
	                    		    			}
	                    		    		}
	                    		    		return;
                    		    		}
                    		    	});
                    		    }
                    		});     
                    }
                           
						var expenseDetailsGridStore = new dojo.data.ItemFileReadStore({
								data: {
										identifier: 'expdIdentifier',items: grid.store._arrayOfAllItems
									  }
								});
						// reset store
                        grid.setStore(expenseDetailsGridStore);  
                        dijit.byId("resultCodingBlockGrid").resize();

        		}
        		
