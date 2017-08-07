
function getRowIndex(row){
		var rows = document.getElementById('cbTable').getElementsByTagName('tbody')[0].getElementsByTagName('tr');
		rowIdx = rows[row].rowIndex;
	}
	
	var cBlockOptions;
	
	//var cbElementsId[] = [];
	var jsonResult;
	var cbSelectedData;
	function createTable(){
			var div = document.getElementById("dept_tab");
			var table; 
			if(document.all)
				table = document.all["cbTable"];
			else
				table = document.getElementById("cbTable");	
			var tBody = document.createElement('TBODY');
			var tHeader = document.createElement('THEAD');
			//var tHeader = table.createTHead(); 
			var tHeadRow = document.createElement('TR');
			var oRow, oCell;
			var heading = new Array();
			  heading[0] = "Pct"; heading[1] = "AY"; heading[2] = "Index"; 
			   heading[3] = "PCA";heading[4] = "Grant";  heading[5] = "Phase";  
			   heading[6] = "AG1";  heading[7] = "Project";  heading[8] = "Phase";  
			   heading[9] = "Ag2"; heading[10] = "AG3";  heading[11] = "Multi";  heading[12] = "Std";

			  table.appendChild(tHeader);
			  table.appendChild(tBody);
			  oRow = document.createElement("TR");
			  tHeader.appendChild(oRow);
			  
			 var cbOptions = dojo.byId('cb_options_hidden').value;
			  alert("Coding block Options :"+cbOptions);			  
			 var result = dojo.byId('json_result_hidden').value;
			 		
			  alert(result);
			  var jsonResult = eval('('+result+')');
			  
			  var cbStore = jsonResult.store;
			  
			  
			 var selectedCBData = dojo.byId('selected_Coding_Block_data').value;
			  alert("Selected Data ::"+selectedCBData);			 
			  cbSelectedData = eval('('+selectedCBData+')');
			  cBlockOptions = eval('('+cbOptions+')');
	  		 			  
			  // Create and insert cells into the header row.
			  for(var j=0;j<cBlockOptions.cboption.length;j++){
			// alert(cBlockOptions.cboption[j]);
			 // alert(cBlockOptions.cboption[j].show);
			  	if(cBlockOptions.cboption[j].show=="Y"){
			  		oCell = document.createElement("TH");
			  		var headerValue = toProperCase(cBlockOptions.cboption[j].name.substring(0, cBlockOptions.cboption[j].name.length-1));
			  		if(headerValue !="Del")
			  			//oCell.innerHTML = cBlockOptions.cboption[j].name.substring(0, cBlockOptions.cboption[j].name.length-1).toUpperCase();
			  			oCell.innerHTML = headerValue;
			  		if(headerValue =="Grantphase" || headerValue=="Projectphase")
			  			oCell.innerHTML = "Phase";	
			   		//oCell.innerHTML = heading[j];
			    	oRow.appendChild(oCell);
			    }
			  }
	  		  var totalRows = dojo.byId('no_of_coding_blocks_hidden').value;
			  addRow(totalRows,table);
		}
		
		function addRow(totalRows,table){
				for(var i=1;i<=totalRows;i++){
					var newRow;
					if(document.all)
			  			newRow = table.insertRow();
			  		else
			  			newRow = table.insertRow(table.rows.length);	
			  		for(var j=0;j<cBlockOptions.cboption.length;j++){
			  			if(cBlockOptions.cboption[j].show=="Y")	oRow = addTextCell(newRow,i,cBlockOptions.cboption[j].name,false);
			  		}
			  	}
		}
		
		function appendRow(table,newRowIndex){
					var newRow;
					//var newRow = table.insertRow(table.rows.length);
					if(document.all)
			  			newRow = table.insertRow();
			  		else
			  			newRow = table.insertRow(table.rows.length);
			  		for(var j=0;j<cBlockOptions.cboption.length;j++){
			  			if(cBlockOptions.cboption[j].show=="Y")	oRow = addTextCell(newRow,newRowIndex,cBlockOptions.cboption[j].name,true);
			  		}
		}
		
		function addTextCell(newRow,rowIndex,name,isNewRow){
			
			var displayValue="";
			if(name=="pct_" || name=="ay_" || name=="grantPhase_" || name=="projectPhase_"){
				var cellName = name.substring(0,name.length-1);
				
				if(name=="pct_" && !isNewRow) 
					displayValue= cbSelectedData[cbSelectedData.length-1].pct;
				if(name=="ay_" && !isNewRow) 
					displayValue= cbSelectedData[cbSelectedData.length-1].ay;
				if(name=="grantPhase_" && !isNewRow) 
					displayValue= cbSelectedData[cbSelectedData.length-1].grantPhase;
				if(name=="projectPhase_" && !isNewRow) 
					displayValue= cbSelectedData[cbSelectedData.length-1].projectPhase;
				addTextBox(name+rowIndex,newRow,cellName,displayValue,newRow.childNodes.length);
			}
			else{
				if(name=="index_"){
					var indexCell = newRow.insertCell(newRow.childNodes.length);
					var indexStore = jsonResult.store[0].index;
					var newIndexStore = {};
					newIndexStore.identifier='id';
					newIndexStore.items = indexStore;
					var selectedIndexCode="";
					if(!isNewRow)
						selectedIndexCode = cbSelectedData[cbSelectedData.length-1].index;
					addComboBox("index_"+rowIndex,"index_"+rowIndex,"index",newIndexStore,"display",selectedIndexCode,indexCell);
				}
				if(name=="pca_"){
					var pcaCell = newRow.insertCell(newRow.childNodes.length);
					pca_store = jsonResult.store[1].pca;
					
					var newPcaStore = {};
					newPcaStore.identifier='id';
					newPcaStore.items = pca_store;
					var selectedPcaCode ="";
					if(!isNewRow)
						selectedPcaCode = cbSelectedData[cbSelectedData.length-1].pca;
					if(selectedPcaCode=="null")
						selectedPcaCode = "";	
					addComboBox("pca_"+rowIndex,"pca_"+rowIndex,"pca",newPcaStore,"display",selectedPcaCode,pcaCell);
				}
				
				if(name=="grant_"){
					var grantCell = newRow.insertCell(newRow.childNodes.length);
					var grant_store = jsonResult.store[2].grant;
					var newGrantStore = {};
					newGrantStore.identifier='id';
					newGrantStore.items = grant_store;
					var selectedGrantCode ="";
					if(!isNewRow)
						selectedGrantCode = cbSelectedData[cbSelectedData.length-1].grant;
					if(selectedGrantCode=="null")
						selectedGrantCode = "";	
					addComboBox("grant_"+rowIndex,"grant_"+rowIndex,"grant",newGrantStore,"display",selectedGrantCode,grantCell);
					dojo.connect(dijit.byId('grant_'+rowIndex),'onChange',function() { getGrantPhase(dijit.byId('grant_'+rowIndex),newGrantStore.items[rowIndex-1]); });
				}
				
				if(name=="project_"){
					var projectCell = newRow.insertCell(newRow.childNodes.length);
					var project_store = jsonResult.store[4].project;
					var newProjectStore = {};
					newProjectStore.identifier='id';
					newProjectStore.items = project_store;
					var selectedProjectCode = "";
					if(!isNewRow)
						selectedProjectCode = cbSelectedData[cbSelectedData.length-1].grantPhase;
					if(selectedProjectCode=="null") selectedProjectCode="";
					addComboBox("project_"+rowIndex,"project_"+rowIndex,"project",newProjectStore,"display",selectedProjectCode,projectCell);
					dojo.connect(dijit.byId('project_'+rowIndex),'onChange',function() { getProjectPhase(dijit.byId('project_'+rowIndex),newProjectStore.items[rowIndex-1]);});
				}
				
				if(name=="ag1_"){
					var ag1Cell = newRow.insertCell(newRow.childNodes.length);
					var ag1_store = jsonResult.store[3].ag1;
					var newAg1Store = {};
					newAg1Store.identifier='id';
					newAg1Store.items = ag1_store;
					var selectedAG1Code = "";
					if(!isNewRow)
						selectedAG1Code = cbSelectedData[cbSelectedData.length-1].ag1;
					if(selectedAG1Code=="null")
						selectedAG1Code = "";	
					addComboBox("ag1_"+rowIndex,"ag1_"+rowIndex,"ag1",newAg1Store,"display",selectedAG1Code,ag1Cell);
				}
				
				if(name=="ag2_"){
					var ag2Cell = newRow.insertCell(newRow.childNodes.length);
					var ag2_store = jsonResult.store[5].ag2;
					var newAg2Store = {};
					newAg2Store.identifier='id';
					newAg2Store.items = ag2_store;
					var selectedAG2Code = "";
					if(!isNewRow)
					 	selectedAG2Code = cbSelectedData[cbSelectedData.length-1].ag2;
					if(selectedAG2Code=="null")
						selectedAG2Code = ""; 	
					addComboBox("ag2_"+rowIndex,"ag2_"+rowIndex,"ag2",newAg2Store,"display",selectedAG2Code,ag2Cell);
				}
				
				if(name=="ag3_"){
				
					var ag3Cell = newRow.insertCell(newRow.childNodes.length);
					ag3_store = jsonResult.store[6].ag3;
					var newAg3Store = {};
					newAg3Store.identifier='id';
					newAg3Store.items = ag3_store;
					var selectedAG3Code = "";
					if(!isNewRow)
						selectedAG3Code = cbSelectedData[cbSelectedData.length-1].ag3;
					if(selectedAG3Code=="null")
						selectedAG3Code = "";	
					addComboBox("ag3_"+rowIndex,"ag3_"+rowIndex,"ag3",newAg3Store,"display",selectedAG3Code,ag3Cell);
				}
					
				if(name=="multi_"){
				
					var multiCell = newRow.insertCell(newRow.childNodes.length);
					multi_store = jsonResult.store[7].multi;
					
					var newMultiStore = {};
					newMultiStore.identifier='id';
					newMultiStore.items = multi_store;
					var selectedMultiCode = "";
					if(!isNewRow)
						selectedMultiCode = cbSelectedData[cbSelectedData.length-1].multi;
					if(selectedMultiCode=="null")
						selectedMultiCode = "";	
					addComboBox("multi_"+rowIndex,"multi_"+rowIndex,"multi",newMultiStore,"display",selectedMultiCode,multiCell);
				}
				
				if(name=="std_"){
					var stdCell = newRow.insertCell(newRow.childNodes.length);
					var std = "std_"+parseInt(rowIndex);
					var selectedStandard = "N";
					var dispStandard = false;
					if(!isNewRow)
						selectedStandard = cbSelectedData[cbSelectedData.length-1].std;
					(selectedStandard=="N") ? dispStandard=false :dispStandard=true;
					alert(selectedStandard+ "   "+dispStandard);
					//alert(selectedStandard);
					//stdCell.innerHTML = "<input id='"+std+"' dojotype='dijit.form.CheckBox' checked='"+dispStandard+"'/>";
					stdCell.innerHTML = "<input id='"+std+"' name='"+std+"' type='checkbox' dojotype='dijit.form.CheckBox' checked='+dispStandard+' value='"+dispStandard+"' />";// onChange='disableControls(dojo.byId('"+std+"'+"_"+rowIndex),rowIndex,cBlockOptions)'
				//	std = new dijit.form.CheckBox({id:"std_"+rowIndex,name : "std", checked : dispStandard},std);
					dojo.connect(dojo.byId("std_"+rowIndex),'change', function() {disableControls(dojo.byId("std_"+rowIndex),rowIndex,cBlockOptions)});
					//dojo.connect(dojo.byId(std),'onclick', function() {disableControls(dojo.byId(std),rowIndex,cBlockOptions)});
				}
				if(name=="del_"){
					var buttonCell = newRow.insertCell(newRow.childNodes.length);
					var del = "del_"+rowIndex;
					buttonCell.innerHTML = "<input id='"+del+"' type='button' value='Del' onclick='rowDelete(this.id)' />";		
				}
				
			}
			
		}
		
		function getNewStoreForApprYear(element){
			var selectedApprYear = dojo.byId(element).value;
			//var code = grantStore.code;
		  	dojo.xhrPost(
		   	{
		    	url: "CodingBlockApprYear.action",
		    	handleAs: "json",
		    	load: function(data) { loadStoreForApprYear(element,data); },
		    	content:{ name: selectedApprYear }
		   	});
		}
		
		function loadStoreForApprYear(element,data){

			// set index dropdown store
			var indexData = data[0].index;
			rowIndex = element.substring(element.length-1);
			var indexStore = new dojo.data.ItemFileReadStore({data: {identifier:"id", items: indexData}}); 
			dijit.byId('index_'+rowIndex).attr('store',indexStore);
			
			//set PCA dropdown store
			var pcaData = data[0].pca;
			rowIndex = element.substring(element.length-1);
			var pcaStore = new dojo.data.ItemFileReadStore({data: {identifier:"id", items: pcaData}});
			if(pcaStore.data!=null)	 
				dijit.byId('pca_'+rowIndex).attr('store',pcaStore);
			else	
				dijit.byId('pca_'+rowIndex).attr('store',null);

			//set Grant dropdown store
			var grantData = data[0].grant;
			rowIndex = element.substring(element.length-1);
			var grantStore = new dojo.data.ItemFileReadStore({data: {identifier:"id", items: grantData}}); 
			if(grantStore.data!=null)	 
				dijit.byId('grant_'+rowIndex).attr('store',grantStore);
			else
				dijit.byId('grant_'+rowIndex).attr('store',null);	
			
			//set Project dropdown store
			var projectData = data[0].project;
			rowIndex = element.substring(element.length-1);
			var projectStore = new dojo.data.ItemFileReadStore({data: {identifier:"id", items: projectData}}); 
			
			if(projectStore.data!=null)	
				dijit.byId('project_'+rowIndex).attr('store',projectStore);
			else
				dijit.byId('project_'+rowIndex).attr('store',null);	
			
			//set AG1 dropdown store
			var ag1Data = data[0].ag1;
			rowIndex = element.substring(element.length-1);
			var ag1Store = new dojo.data.ItemFileReadStore({data: {identifier:"id", items: ag1Data}}); 
			if(ag1Store.data!=null)
				dijit.byId('ag1_'+rowIndex).attr('store',ag1Store);
			else
				dijit.byId('ag1_'+rowIndex).attr('store',null);	
			
			//set AG2 dropdown store
			var ag2Data = data[0].ag2;
			rowIndex = element.substring(element.length-1);
			var ag2Store = new dojo.data.ItemFileReadStore({data: {identifier:"id", items: ag2Data}});
			if(ag2Store.data!=null) 
				dijit.byId('ag2_'+rowIndex).attr('store',ag2Store);
			else
				dijit.byId('ag2_'+rowIndex).attr('store',null);	
			
			//set AG3 dropdown store
			var ag3Data = data[0].ag1;
			rowIndex = element.substring(element.length-1);
			var ag3Store = new dojo.data.ItemFileReadStore({data: {identifier:"id", items: ag3Data}});
			if(ag3Store.data!=null) 
				dijit.byId('ag3_'+rowIndex).attr('store',ag3Store);
			else
				dijit.byId('ag3_'+rowIndex).attr('store',null);	
			
			//set Multi dropdown store
			var multiData = data[0].multi;
			rowIndex = element.substring(element.length-1);
			var multiStore = new dojo.data.ItemFileReadStore({data: {identifier:"id", items: multiData}}); 
			if(multiStore.data!=null)
				dijit.byId('multi_'+rowIndex).attr('store',multiStore);
			else
				dijit.byId('multi_'+rowIndex).attr('store',null);	
		}
		
		function addTextBox(inputId,newRow,cellId,displayValue,colIndex){
			cellId = newRow.insertCell(colIndex);
			var inputName = inputId.substring(0,parseInt(inputId.length)-2);
			if(displayValue=="null") displayValue="";
			cellId.innerHTML = "<input id='"+inputId+"' type='text' dojoType='dijit.form.ValidationTextBox' style='width:70px' name = '"+inputName+"' value = '"+displayValue+"' maxlength='2' required='true' invalidMessage='Appropriation Year is required' promptMessage='Appropriation Year' />";
			if(inputName=="pct")
				dojo.connect(dojo.byId(inputId),'change',function() {doPctValidate(inputId);});
			dojo.connect(inputId,'onFocus',function() {toggleCheckBox(newRow.childNodes.length);});
			if(inputName=="ay"){
				//dojo.connect(dojo.byId(inputId),'change',function() {getNewStoreForApprYear(inputId);});
				dojo.connect(dojo.byId(inputId),'change',function() {return checkAyInput(inputId);});
			}
				
		}
		
		function checkAyInput(element){
		
			var ayValue = dojo.byId(element).value;
			if(ayValue=="" || ayValue==null){
				dojo.byId(element).focus();
				return;
			}
			else{
				
				if(isNaN(ayValue)){
					dojo.byId(element).select();
					return;
				}
				else{
					getNewStoreForApprYear(element);
				}
			}
		}
		
		
		function addComboBox(inputId,compId,name,store,displayValue,selectedValue,cellId){

			cellId.innerHTML = 	"<input id='"+inputId+"' size='70px' maxlength = '5' style='width:70px' />";		
			var rowIndex = inputId.substring(parseInt(inputId.length)-1);

			datastore = new dojo.data.ItemFileReadStore({data: store});
			inputId = new dijit.form.ComboBox({id:compId, name : name , store : datastore, searchAttr : "display",value : selectedValue},inputId);
			dijit.byId(inputId.attr('style','width:70px'));
			dojo.connect(inputId,'onFocus',function() {toggleCheckBox(rowIndex);});
		}

		function disableControls(checkBoxControl,rowIndex,codingBlockOptions){
			
			alert("Here");
			if(checkBoxControl.checked){
				for(var i=0;i<codingBlockOptions.cboption.length;i++)
				{
					if(codingBlockOptions.cboption[i].show=="Y")
						dijit.byId("index_"+rowIndex).attr('value',"");
					if(codingBlockOptions.cboption[i].show=="Y" && codingBlockOptions.cboption[i].name=="grant_")
						dijit.byId("grant_"+rowIndex).attr('value',"");
					if(codingBlockOptions.cboption[i].show=="Y" && codingBlockOptions.cboption[i].name=="grantPhase_"){
						if(dijit.byId("grant_"+rowIndex).value=="null" || dijit.byId("grant_"+rowIndex).value=="")
							dojo.byId("grantPhase_"+rowIndex).value="";
						else	
							dijit.byId("grantPhase_"+rowIndex).attr('value',"");
					}
					if(codingBlockOptions.cboption[i].show=="Y" && codingBlockOptions.cboption[i].name=="project_")
						dijit.byId("project_"+rowIndex).attr('value',"");
						
					if(codingBlockOptions.cboption[i].show=="Y" && codingBlockOptions.cboption[i].name=="projectPhase_"){
					if(dijit.byId("project_"+rowIndex).value=="null" || dijit.byId("project_"+rowIndex).value=="")
							dojo.byId("projectPhase_"+rowIndex).value="";
						else	
							dijit.byId("projectPhase_"+rowIndex).attr('value',"");
					}
					if(codingBlockOptions.cboption[i].show=="Y" && codingBlockOptions.cboption[i].name=="ag1_")
						dijit.byId("ag1_"+rowIndex).attr('value',"");
					if(codingBlockOptions.cboption[i].show=="Y" && codingBlockOptions.cboption[i].name=="ag2_")
						dijit.byId("ag2_"+rowIndex).attr('value',"");
					if(codingBlockOptions.cboption[i].show=="Y" && codingBlockOptions.cboption[i].name=="ag3_")
						dijit.byId("ag3_"+rowIndex).attr('value',"");
					if(codingBlockOptions.cboption[i].show=="Y" && codingBlockOptions.cboption[i].name=="multi_")
						dijit.byId("multi_"+rowIndex).attr('value',"");
					if(codingBlockOptions.cboption[i].show=="Y" && codingBlockOptions.cboption[i].name=="pct_")
						dojo.byId("pct_"+rowIndex).value="";
					if(codingBlockOptions.cboption[i].show=="Y" && codingBlockOptions.cboption[i].name=="ay_")
						dojo.byId("ay_"+rowIndex).value="";
				}	
			}
		}
		
		function getGrantPhase(control,grantStore){
			var selectedGrantNo = dijit.byId(control.id).attr('displayedValue').substring(2);
			var code = grantStore.code;
		  	dojo.xhrPost(
		   	{
		    	url: "CodingBlockGrantPhase.action",
		    	handleAs: "json",
		    	load: function(data) { loadGrantPhase(control,data); },
		    	content:{ name: code }
		   	});
		 }
		 
		 function loadGrantPhase(control,jsonData){
		 
		 	var indexNo = control.id.substring(control.id.length-1);

		 	grantPhase="grantPhase_"+indexNo;
		 	var newGrantPhaseStore = {};
			newGrantPhaseStore.identifier='id';
			newGrantPhaseStore.items = jsonData;
			var datastore = new dojo.data.ItemFileReadStore({ data: newGrantPhaseStore });
			var selectedGrantPhase = "";
			//addComboBox("grantPhase_"+indexNo,"grantPhase_"+indexNo,"chosenGrantPhaseCode",newGrantPhaseStore,"display",selectedGrantPhase,"grantPhase");
		 	grantPhase = new dijit.form.ComboBox({id:grantPhase, name : grantPhase ,store : datastore, searchAttr : "display",value : selectedGrantPhase},grantPhase);
		 	dijit.byId(grantPhase).attr('style','width:70px');
		 }
		 
		 function getProjectPhase(control,projectStore){
           // rowProjectIdx = rowIndex; 
		 	var selectedProjectNo = dijit.byId(control).attr('displayedValue').substring(2);
		 	var code = projectStore.code;
		  	dojo.xhrPost(
		   	{
		    	url: "CodingBlockProjectPhase.action",
		    	handleAs: "json",
		    	load: function(data) { loadProjectPhase(control,data); },
		    	content:{ name: code }
		   	});
		 }
		 
		 function loadProjectPhase(control,jsonData){
		 	var indexNo = control.id.substring(control.id.length-1);
		 	projectPhase="projectPhase_"+indexNo;
		 	var newProjectPhaseStore = {};
			newProjectPhaseStore.identifier='id';
			newProjectPhaseStore.items = jsonData;
			var datastore = new dojo.data.ItemFileReadStore({ data: newProjectPhaseStore });
			var selectedProjectPhase = "";
		 	projectPhase = new dijit.form.ComboBox({id:projectPhase, name : projectPhase , store : newProjectPhaseStore, searchAttr : "display",value:selectedProjectPhase},projectPhase);
		 	dijit.byId(projectPhase).attr('style','width:70px');
		 }
		 
		 function toggleCheckBox(rowIndex){
		 	dijit.byId("std_"+rowIndex).attr('checked',false);
		 }

		function rowDelete(id){
				var rowId= document.getElementById(id).id;
				var rowNo = rowId.substring(parseInt(rowId.length)-1);
				
				var tableId = document.getElementById("cbTable");
				if(rowNo>1){ tableId.deleteRow(rowNo);	}
		}

		function doPctValidate(elementId){
				var pctValue = dojo.byId(elementId).value;
				rowIndex = parseInt(elementId.substring(elementId.length-1));
				
				var sum = addPct();
				if((pctValue=="" || pctValue.length==0) || parseInt(pctValue)>100 || parseInt(sum)>100){
						dojo.byId(elementId).focus();
						return;
				}
				else{
					dijit.byId("std_"+rowIndex).attr('checked',false);
					var newRowIndex = parseInt(rowIndex)+1;
					appendRow(document.getElementById("cbTable"),dojo.byId('cbTable').childNodes.length+1);
				}
		}
			
		function addPct(){
				var tableId = dojo.byId('cbTable');
				var noOfCodingBlocks = tableId.rows.length-1;
				var sumPct = 0;
				for(var i=1;i<=noOfCodingBlocks;i++){
					var pct = dojo.byId('pct_'+parseInt(i)).value;
					sumPct = parseInt(sumPct) + parseInt(pct);
				}
				return sumPct;
		} 

		function submitUsingAjax(obj){
                var queryString = dojo.formToQuery("cb_form");
                alert(queryString);                
                dojo.xhrGet({
                    //postData:queryString,
                    url: "CodingBlockSubmit.action"+"?"+queryString,
                //    content:{ name: queryString },
                    //form: obj,
                    handleAs: "json",
                    load: function(response){
                        alert(response);
                        if(typeof data == "error"){
                            //console.warn("error!",args);
                        }else{
                            // show our response
                            console.log(response);
                        }
                    }
            });
         }
         
         function toProperCase(header){
    		return header.charAt(0).toUpperCase() + header.substring(1,header.length).toLowerCase();
	}
    //dojo.addOnLoad(createTable);     
		

