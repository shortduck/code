	var cBlockOptions;
	var jsonResult;
	var cbSelectedData;
	var expenseSummaryGridStore = null;
	var firstFlag = true;
	var prevTotalRows;  
	var cbOptions;
	var selected;
	var cbStore;
	var noOfCodingBlocks;
	var store = null;
	var isChanged = false;
	//var expDetailsRows;
	var codingBlockJsonFromClient;
	var rowArray = [];
	var indexCount = 0;
	var pcaCount = 0;
	var grantCount = 0;
	var projectCount = 0;
	var ag1Count = 0;
	var ag2Count = 0;
	var ag3Count = 0;
	var multiCount = 0;
	
	var rowCount = 0;
	var deletedRowCount = 0;
	var rowInitialized = false;
				
	//method to create CODING BLOCK table
	function createTable(){
			var existingCbTable = dojo.byId('cbTable');
			if(dojo.byId('cbTable').rows.length>0){			
    			//dojo.byId('cbTable').innerHTML="";
    			while(dojo.byId('cbTable').hasChildNodes()){
    				dojo.byId('cbTable').removeChild(dojo.byId('cbTable').firstChild);
    			}

    			dojo.byId('errorCodingBlockMsg').innerHTML ="";
    		}
			
			var table = initializeTable(cbOptions);	
			var totalRows = noOfCodingBlocks;
			jsonResult = cbStore;		
			cbSelectedData = selected;
			storeApprYear = cbStore.store[8].apprYear;
		
  				// If no coding block exists for the selected line, show the default blank coding block row 
  				if(totalRows==0)
	  				appendRow(table,1);
	  			else{
	  				prevTotalRows = totalRows;
	  				addRows(totalRows,table); // Add row to show the associated coding block
	  				rowInitialized = true;
	  				// load store for appropriation year if different than current AY
	  				resetApprYearStore ();
	  			}		
	  			firstFlag = false;
	  			// set CB row to read only if no CB options are enabled
	  			setSingleStdRowReadOnly();
	  	
		}
	
		// Method to add coding block rows to the Table
		function addRows(totalRows,table){
				  for(var i=0;i<totalRows;i++){
					var newRow;
					rowCount++;
				
					if(document.all)
			  			newRow = table.insertRow();
			  		else 
			  			newRow = table.insertRow(table.rows.length);
					for(var j=0;j<cbOptions.length;j++){
			  			if(cbOptions[j].show=="Y")	
			  				oRow = constructCodingBlockCells(newRow,rowCount,cbOptions[j].name,false);
			  			
			  		}
					// Add the expense Details Id and Expense details codingblock Id as a hidden field
					var expdcCell = newRow.insertCell(newRow.childNodes.length);
					var expdcId = "expdcId_" + i;
					expdcCell.innerHTML = "<input id='"+expdcId+"' name ='"+expdcId+"' type='hidden' value = '"+cbSelectedData[i].expdcIdentifier+"'/>"; 
					
					var expdCell = newRow.insertCell(newRow.childNodes.length);
					var expdId = "expdId_" + i;
					expdCell.innerHTML = "<input id='"+expdId+"' name ='"+expdId+"' type='hidden' value = '"+cbSelectedData[i].expdIdentifier+"'/>";
					
					
					newRow.id = "cb_" + rowCount;
			  	}
				  
				  toggleFirstRowDeleteButton ();
		}
		
		function appendRow(table,newRowIndex, pctValue){					
					var newRow;
					rowCount++;
					//var newRow = table.insertRow(table.rows.length);
					// Add rows
					if(document.all)
			  			newRow = table.insertRow();
			  		else
			  			newRow = table.insertRow(table.rows.length);
					// add row id
					newRow.id = "cb_" + rowCount;
					
					// Add cells 
			  		for(var j=0;j<cbOptions.length;j++){
			  			if(cbOptions[j].show=="Y"){	
			  				oRow = constructCodingBlockCells(newRow,rowCount,cbOptions[j].name,true, pctValue);
			  				//pctValue = "";
			  			}
			  		}
			  		
			  		toggleFirstRowDeleteButton ();
		}
		
		function comboChanged(){
			isChanged = true;
		}
		// Method to destroy the combo box controls
		function destroyComboBoxControls(table,totalRows){
			
			for(var i =0;i<=totalRows;i++){
				if(dijit.byId('index_'+i)!=undefined)
					dijit.byId('index_'+i).destroy();
				
				if(dijit.byId('pca_'+i)!=undefined)
					dijit.byId('pca_'+i).destroy();
				
				
				if(dijit.byId('grant_'+i)!=undefined)
					dijit.byId('grant_'+i).destroy();
			
				if(dijit.byId('grantPhase_'+i)!=undefined)
					dijit.byId('grantPhase_'+i).destroy();
				
				if(dijit.byId('project_'+i)!=undefined)
					dijit.byId('project_'+i).destroy();
				if(dijit.byId('projectPhase_'+i)!=undefined)
					dijit.byId('projectPhase_'+i).destroy();
			
				if(dijit.byId('ag1_'+i)!=undefined)
					dijit.byId('ag1_'+i).destroy();
				if(dijit.byId('ag2_'+i)!=undefined)
					dijit.byId('ag2_'+i).destroy();
				if(dijit.byId('ag3_'+i)!=undefined)
					dijit.byId('ag3_'+i).destroy();
				if(dijit.byId('multi_'+i)!=undefined)
					dijit.byId('multi_'+i).destroy();
				if(dojo.byId('std_'+i)!=undefined)
					dojo.byId('std_'+i).destroy();
			}
			
			for(var i = table.rows.length-1; i > 0; i--){
				table.deleteRow(i);
			}

		}
      
