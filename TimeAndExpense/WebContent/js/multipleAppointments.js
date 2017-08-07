var businessDomain = "";

/* If multiple appointments found while trying to
	 *  save TripId information, prompt user to choose
	 *  an apointment.
	 */    
	function handleMultipleAppointments(appointments,  domain){
		var multiApptDialog = dijit.byId('multipleAppointmentsDialog');
		businessDomain = domain;
		// set business area such as Advance or Expense
		multiApptDialog.setContent("<br/>" + prepareAppointmentsTableHtml(appointments, domain) + "<br/>");
		multiApptDialog.show();
	}
	
	/* Prepares HTML text for a table listing multiple appointments. */
	function prepareAppointmentsTableHtml(appointments, domain){
		var htmlStr = '';
		
		htmlStr += 'Multiple appointments found for the entered expense dates. <br/>Please choose an appointment for the expense report.<br/><br/>';
		
		htmlStr += "<table id='multipleApptTable'>";
		htmlStr += "<thead><tr>";
		htmlStr += "<th field='appt_identifier' align='center' bgcolor><b>&nbsp;Position&nbsp;&nbsp;</b></th>";
		htmlStr += "<th field='agency' align='center'><b>&nbsp;&nbsp;Appt date&nbsp;&nbsp;</b></th>";
		htmlStr += "<th field='process_level' align='center'><b>&nbsp;&nbsp;Process Level&nbsp;&nbsp;</b></th>";
		htmlStr += "<th field='tku' align='center'><b>&nbsp;&nbsp;HRMN Dept&nbsp;&nbsp;</b></th>";
		htmlStr += "<th field='department' align='center'><b>&nbsp;&nbsp;TKU&nbsp;&nbsp;</b></th>";
		htmlStr += "</tr></thead><tbody>";
		htmlStr += "<tr><td colspan='5'><hr/><td/><tr/>";
		
		dojo.forEach(appointments, function(item){
			htmlStr += "<tr><td>&nbsp;</td></tr>";
			htmlStr += "<tr>";
			htmlStr += "<td align='center'>&nbsp;<a href='javascript:selectAppt("+item.appt_identifier+ ")'>" + item.position_id + "</a>&nbsp;</td>";
			htmlStr += "<td align='center'>&nbsp;" + item.appointment_date + "&nbsp;</td>";
			htmlStr += "<td align='center'>&nbsp;" + item.process_level + "&nbsp;</td>";
			htmlStr += "<td align='center'>&nbsp;" + item.dept_code + "&nbsp;</td>";
			htmlStr += "<td align='center'>&nbsp;" + item.tku + "&nbsp;</td>";
			htmlStr += "</tr>";
		});
		
		htmlStr += "</tbody></table>";
		
		return htmlStr;
	}

	/* Invoked upon appointment selction by user */
	function selectAppt(selectedAppt, domain){
	  	// close multiple appt popup
  		dijit.byId('multipleAppointmentsDialog').hide();
		
		dojo.xhrPost ({
			url: 'EmployeeAppointmentSelection.action?apptId='+selectedAppt,
			handleAs: "json",
			load: function (data) {
				// try to save as the popup was in response to save operation
			if (businessDomain == "EXPENSE")
				saveExpense();
			else if ((businessDomain == "ADVANCE"))
				saveAdvance(fromSubmit, "");
			},
			error: function (error) {}
            });
	}