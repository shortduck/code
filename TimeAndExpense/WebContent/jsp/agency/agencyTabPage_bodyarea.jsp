<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page
	import="gov.michigan.dit.timeexpense.model.core.UserProfile"%><%@taglib
	prefix="s" uri="/struts-tags"%><%@taglib
	uri="http://www.ibm.com/siteedit/sitelib" prefix="siteedit"%>
	
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/jscal2.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/border-radius.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/JSCal2-1.9/src/css/win2k/win2k.css" />

<script type="text/javascript"
	src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/jscal2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/JSCal2-1.9/src/js/lang/en.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/agencyOptions.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/date.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/time_expense_calendar.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/systemCode.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/enGrid.css"/>


<script type="text/javascript">
dojo.require("dojox.grid.EnhancedGrid");
dojo.require("dojox.grid.enhanced.plugins.Filter");
dojo.require("dojo.data.ItemFileWriteStore"); 
dojo.require("dojo.parser");
dojo.require("dojox.grid.DataGrid");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dijit.form.FilteringSelect");
dojo.require("dijit.Tooltip");
dojo.require("dijit.form.Select");  
dojo.require("dojo.store.Memory");
dojo.require("dijit.form.NumberTextBox");
dojo.require("dijit.form.NumberSpinner");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");
</script>

<script type="text/javascript">
	var checkSaveAgainCounter = 0;

	var previousTab = "idTab";
	var firstTime = true;
	var cbTabLoaded = false;
	var tabsToReload = [];
	// added to hold reference for last tab until Ajax save is completed through the popup
	var lastTabForAjaxSave;

	dojo.addOnLoad(function() {
	
		var serverR = dojo.fromJson(dojo.byId('jsonResponse_hidden').value);
		dojo.byId("module_hidden").value = serverR.writeAccess;
		performTabLoadPostProcessing();

	});

	/*
	 This function performs the tab processing .
	 */
	function performTabLoadPostProcessing() {

		dojo
				.connect(
						dijit.byId('agency_tab_container'),
						"selectChild",
						function(child) {
							var result = null;
							var responseToConfirm = null;

							result = checkFormState();
							var tab = dijit.byId('agency_tab_container');
							var problemDuringSave = false;

							if (child.id == 'mileAgeTab') {
								initMileagePage("agyMilesUpdate.action", dN,
										aG, accessval);
							}
							if (result == 'There are unsaved changes. By leaving this page, you will lose all unsaved data.'
									&& checkSaveAgainCounter == 0) {
								responseToConfirm = confirm("There are unsaved changes. Do you want to save and proceed?");
								if (responseToConfirm) {
									checkSaveAgainFlag = true;
									lastTabForAjaxSave = previousTab;
									problemDuringSave = previousTab == "idTab" ? saveNewLoc()
											: (previousTab == "mileAgetab" ? saveMilede()
													: "");
								}

								if (!responseToConfirm || problemDuringSave) {
									checkSaveAgainCounter = 1;
									tab.selectChild(previousTab);
								}

							} else if (checkSaveAgainCounter == 1) {
								checkSaveAgainCounter = 0;
							}
							displayLocationErrorMsg('');
						});
	}


	resultGridStore = null;
	resultGridStoreF = null;
	emptyGridStoreF = new dojo.data.ItemFileReadStore({
		data : {
			identifier : 'elocIdentifier',
			items : []
		}
	});
	resultGridStoreT = null;
	emptyGridStoreT = new dojo.data.ItemFileReadStore({
		data : {
			identifier : 'elocIdentifier',
			items : []
		}
	});

	var data1 = {
		identifier : 'elocIdentifier',
		items : []
	};

	var layout = [ {
		field : "City",
		datatype : "string"
	}, {
		field : "stProv",
		datatype : "string"
	}, {
		field : "delButton",
		datatype : "string"
	}

	];

	var layoutF = [ {
		field : "City",
		datatype : "string"
	}, {
		field : "stProv",
		datatype : "string"
	}, {
		field : "stateWide",
		datatype : "string"
	}

	];
	var layoutT = [ {
		field : "City",
		datatype : "string"
	}, {
		field : "stProv",
		datatype : "string"
	}, {
		field : "stateWide",
		datatype : "string"
	}

	];
	var mystructureF = [ {
		cells : [ {
			field : "City",
			datatype : "string"
		}, {
			field : "stProv",
			datatype : "string"
		} ]
	} ];
	var mystructureT = [ {
		cells : [ {
			field : "City",
			datatype : "string"
		}, {
			field : "stProv",
			datatype : "string"
		} ]
	} ];

	var mystore = new dojo.data.ItemFileWriteStore({
		data1 : data1
	});
	var mystoreF = resultGridStoreF;
	var mystoreT = resultGridStoreF;
	/**
	 Displays the screen values after new location Save
	 */

	function finddataAfterSave(data) {
		accessVal = data.writeAccess;
		dojo.byId('deptNum').value = data.department;
		dojo.byId('agencyNum').value = data.agency;
		var accessval = data.writeAccess;
		initResultsAgyGrid(data.agyLocations, accessval);		
		dojo.byId('ag_Newlocations').value = '';
	}



	function validateAndSubmit(form) {
		var errorMsg = {
			msg : ''
		};
		displayValidationErrorMsg('');
		if (validateUserInput(errorMsg)) {
			fetchAndDisplayResults(form);
		} else {
			displayValidationErrorMsg(errorMsg.msg);
		}

	}

	function fetchAndDisplayResults(obj) {
		dojo.xhrPost({
			url : 'searchAgyDeptList.action',
			handleAs : "json",
			form : obj,
			handle : function(data, args) {
				if (typeof data == "error") {
					console.warn("error!", args);
				} else {
					updateResultGrid(data.response);
				}
			}
		});
	}

	function disableFormFields(_disable) {
	}

	/*
	 Deletes a given location from agency ,department
	 */
	function deleteLoc(locationName, location, department, agency) {

		if (confirm(" Location  " + locationName + "  will be deleted ")) {
			deleteLocation(location, department, agency);

		} else {
			displayLocationErrorMsg('');
			return false;
		}
	}

	/*
	 Called when delete hyperlink is clicked
	 */
	function deleteLocation(locName, department, agency) {
		var valueWrite = dojo.byId('module_hidden').value;

		dojo.xhrPost({
			url : 'deleteAgyDeptLocation.action?ag_location=' + locName
					+ '&identifierKey=' + department + ',' + agency
					+ '&module_hidden=' + valueWrite,
			handleAs : "json",
			form : dojo.byId("deptAgyLocForm"),
			handle : function(data, args) {
				if (typeof data == "error") {
					console.warn("error!", args);
				} else {
					finddataAfterSave(data.response);
					displayLocationErrorMsg(" Agency Location Deleted ");
				}
			}
		});
	}

	/*
	 Updates the grid on the the milesage page
	 */
	function updateResultMilesGrid(data) {

		dojo.byId('deptNum1').value = data.department;
		dojo.byId('agencyNum1').value = data.agency;
		dojo.byId('agencyName1').value = data.agencyName;
		updateMilesGrid(data.stateLocations);
	}

	function updateMilesGrid(data) {
		if (data.length < 1) {
			displayValidationErrorMsg('No results found');
			storeToUse = emptyGridStoreF;
		}/* else {

			dojo.forEach(data, function(item) {
				if (item.city == undefined)
					item.city = '';
				if (item.stProv == undefined)
					item.stProv = '';
				if (item.stateWide == undefined)
					item.stateWide = '';
				
				});

			});

		}
		*/
		
		disconnectEvents();
		storeToUse = new dojo.data.ItemFileReadStore({
					data : {
						identifier : 'elocIdentifier',
						items : data
					}
					});		
		
		dijit.byId('locationFromgrid').setStore(storeToUse);
		dijit.byId('locationTogrid').setStore(storeToUse);

		dijit.byId('locationFromgrid').noDataMessage = "No Data Found.. Please modify your search criteria";
		dijit.byId('locationTogrid').noDataMessage = "No Data Found.. Please modify your search criteria";
		
		connectEvents();
	}

	/**
	 Display errors for miles page
	 */
	function displayValidationMilesErrorMsg(msg) {
		dojo.byId('statusArea').innerHTML = '';
		dojo.byId('statusArea').style.color = "RED";
		dojo.byId('statusArea').innerHTML = msg;

	}
	function displayValidationMilesSuccMsg(msg) {
		dojo.byId('statusArea').innerHTML = '';
		dojo.byId('statusArea').style.color = "Green";
		dojo.byId('statusArea').innerHTML = msg;

	}
</script>
<div style="height: 7px">
	<!-- -->
</div>
<div style="display: none">
	<s:textfield id="jsonResponse_hidden" theme="simple"
		value="%{jsonResponse}" />
</div>

<div style="height: 5px">
	<!-- -->
</div>

<div id="agency_tab_container" dojotype="dijit.layout.TabContainer" controllerWidget="dijit.layout.TabController"
	style="width: 98%; height: 80%; position: relative">

	<div id="idTab" dojotype="dijit.layout.ContentPane" title="Locations">
		<span><jsp:include page="editAgyLocation.jsp" /></span>
	</div>
	<div id="mileAgeTab" dojotype="dijit.layout.ContentPane"
		title="Mileage " align="left" preLoad="false"
		loadingMessage="Loading Agency Miles Page. Please wait.."
		href="jsp/agency/mileageAgency.jsp" preventCache="true"></div>
	<span id="statusArea" style="font-style: italic; color: red;"></span>

	<div style="display: none">
		<div id="errorMsg" style="font-size: 9pt;"></div>
		<s:textfield id="agVal" name="agVal">
		</s:textfield>
		<s:textfield id="focusTabId" name="focusTab" />
		<s:textfield id="module_hidden" name="module_hidden" />
	</div>
	
	

	

</div>