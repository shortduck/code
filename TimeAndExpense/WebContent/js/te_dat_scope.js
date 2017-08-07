	// declare references to widget comboboxes 
	var dept_cb;
	var agency_cb;
	var tku_cb;
	var syscode_cb;
	var noElementStore;
	
	// var to store the default dept value
	var defaultDept;
	
	var traInd_cb;
	var traSpec_cb;
	var tt_exp;
	
	// prepares a reusable blank data store
	function prepareNoElementStore(){
		var noElementArray = [];
		noElementArray[0] = {display:""};
		
		// blank out the agency and tku data stores
		noElementStore = prepareStore(noElementArray);
	}
	
	// prepares a Read Only store for comboboxes
	function prepareStore(jsonStr){
		var deptData = {};
		deptData.identifier = 'display';
		deptData.items = jsonStr;
		return new dojo.data.ItemFileReadStore({data: deptData});
	}

	
	// Changes by Smriti Req #13
	function initSystemCodeVal()
	{
		var j =dojo.byId('jsonResponse_hidden').value;
		updateSystemCodeVals(dojo.fromJson(dojo.byId('jsonResponse_hidden').value));
		prepareNoElementStore();
		} 
	// Changes by Smriti Req #13
	function updateSystemCodeVals(data){
		  
		var sys_code_store =prepareStore(data.systemCodespk);
		var selsysCode = data.chosenSystemCode;
		
		syscode_cb = new dijit.form.FilteringSelect({id: "systemCodespk", name: "chosenSysCode", store: sys_code_store, 
			searchAttr: "display", value: selsysCode, displayedValue:selsysCode, style:"width:12em",maxHeight:"450"}, "sysCode");
		
	}
	// prepare comboboxes with data on page load.
	function initDeptAgencyTkuCB(){
		
		updateDepartmentsAgenciesTkus(dojo.fromJson(dojo.byId('jsonResponse_hidden').value));
		
		// also prepare the no element store for future use
		prepareNoElementStore();
	}

	// Updates the Agency combobox with new data
	function updateDepartmentsAgenciesTkus(data){
		 
		var dept_store = prepareStore(data.departments);
		var agency_store = prepareStore(data.agencies);
		var tku_store = prepareStore(data.tkus);
		
		// chosen field
		var selDept = data.chosenDepartment; 
		var selAgency = data.chosenAgency;
		var selTku = data.chosenTku;
	
		// parse to combo box
		dept_cb = new dijit.form.FilteringSelect({id: "departments", name: "chosenDepartment", store: dept_store, 
			searchAttr: "display", value: selDept, displayedValue:selDept, style:"width:12em"}, "department_cb");
		agency_cb = new dijit.form.FilteringSelect({id: "agencies", name: "chosenAgency", store: agency_store, 
			searchAttr: "display", value: selAgency, displayedValue:selAgency, style:"width:12em"}, "agency_cb");
		tku_cb = new dijit.form.FilteringSelect({id: "tkus", name: "chosenTku", store: tku_store, 
			searchAttr: "display", value: selTku, displayedValue:selTku, style:"width:12em"}, "tku_cb");

		// store default dept value for further reference
		defaultDept = data.chosenDepartment;
				
		// connect to onchage events to the refreshpage and create the tooltip. kp 
    	dojo.connect(dept_cb, "onChange", function()
		{
		refreshAgencies();  
		addTooltip(dept_cb, dept_cb.id);
		});
	
       //connect to onchange events to the refreshpage and create the tooltip. kp 
	   dojo.connect(agency_cb, "onChange", function()
        {		   
    	  refreshTkus();  
 	      addTooltip(agency_cb, agency_cb.id); 
	    });
	   
	   //connect to onchange events to the refreshpage and create the tooltip. kp 
	   dojo.connect(tku_cb, "onChange", function()
	    {
	    addTooltip(tku_cb, tku_cb.id);  
	    });
	}

	// Adding the tooltip for department field on employee selection page. kp
	
 function addTooltip(inputId, compId){
		if(dijit.byId(compId+"_tt") == null){
			var newSpan = new dijit.Tooltip({id:compId+"_tt",
		    connectId: [compId],
		    label: dijit.byId(compId).value
		     });
			} else {
				newSpan = dijit.byId(compId+"_tt");
			}
   		 
			 if (inputId.getDisplayedValue().indexOf(' ') >= 0  ){
			 var temp = inputId.getDisplayedValue();
//		 inputId.setDisplayedValue(inputId.getDisplayedValue().substring(0, 17));
			 newSpan.label = temp;
			 
          } 
			 
	 //Removing the tooltip if there is no value. 
		var displayedvalue = inputId.getDisplayedValue() ; 
	    if(displayedvalue==""){
	      	dijit.byId(compId+"_tt").destroy(); 
	    }
			 
 }
	
	// Updates the Agency combobox with new data
	function updateAgencies(data){
//		agency_cb.store = prepareStore(data.response.agencies);
		agency_cb.set("store", prepareStore(data.response.agencies));
		
		agency_cb.setValue(data.response.chosenAgency); // this triggers tku updation immediately after execution!
		agency_cb.setDisplayedValue(data.response.chosenAgency);
	}

	// Updates the Tku combobox with new data
	function updateTkus(data){
//		tku_cb.store = prepareStore(data.response.tkus);
		tku_cb.set("store", prepareStore(data.response.tkus));

		tku_cb.setValue(data.response.chosenTku);
		tku_cb.setDisplayedValue(data.response.chosenTku);
	}

	// Invoked upon new department selection in departments combobox.
	// PS: Due to connected event notification, this method also triggers tku updation.
	// So, selecting new department implies agencies reload (which automatically reloads Tkus)
	function refreshAgencies(newSelectedDept) {

		//left align in IE by focusing on CB
		//Commented to fix issue#692
		//dept_cb.comboNode.focus();
		
		if(!dept_cb.isValid() || dept_cb.value==''){
			// blank out the agency and tku data stores
//			agency_cb.store = noElementStore;
//			tku_cb.store = noElementStore;
			
			agency_cb.set("store", noElementStore);
			tku_cb.set("store", noElementStore);
			
			agency_cb.setValue("");
			agency_cb.setDisplayedValue("");
			tku_cb.setValue("");
			tku_cb.setDisplayedValue("");
			return;
		}
		fetchFromServer("FindAgencies.action?chosenDepartment="+dept_cb.value, updateAgencies);
	}			

	function isDepartmentSelected(){
		return (dept_cb.isValid() && dept_cb.getValue() != '') ? true : false;
	}
	
	function isAgencySelected(){
		return (agency_cb.isValid() && agency_cb.getValue() != '') ? true : false;
	}

	function isTkuSelected(){
		return (tku_cb.isValid() && tku_cb.getValue() != '') ? true : false;
	}

	// Invoked upon new agency selection in agencies combobox
	function refreshTkus(newSelectedAgency) {

		//left align in IE by focusing on CB
		//Commented to fix issue#692
		//agency_cb.comboNode.focus();
		
		if(!dept_cb.isValid() || !agency_cb.isValid() || agency_cb.value=='' || dept_cb.value==''){
			// blank out tku data store
//			tku_cb.store = noElementStore;
			tku_cb.set("store", noElementStore);
			
			tku_cb.setValue("");
			tku_cb.setDisplayedValue("");
			return;
		}
		fetchFromServer("FindTkus.action?chosenDepartment="+dept_cb.value+"&chosenAgency="+agency_cb.value, updateTkus);
		//Commented to fix issue#692
		//tku_cb.comboNode.focus();

	}			
	
	// Utility method to make an Ajax call to the server and 
	// invoke the callback method upon receiving successful response.
	function fetchFromServer(serviceUrl, callbackMethod){
		dojo.xhrPost({
			url: serviceUrl,
			handleAs: "json",
			handle: function(data,args){
				if(data.response == null){
					//if errors, do not pursue the effect of call!
					console.warn("error!",args);
				}else{
					callbackMethod(data);
				}
			}
		});
	}
	
	function increaseDeptAgyTkuDDLength(){
		if(dept_cb != undefined){
		dept_cb.style.width = "16em";
		agency_cb.style.width = "16em";
		tku_cb.style.width = "16em";
		}
	}
