<%@page
	import="com.businessobjects.samples.JRCHelperSample,
	com.crystaldecisions.report.web.viewer.CrystalReportViewer,
	com.crystaldecisions.reports.sdk.ReportClientDocument,
	com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
	com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
	com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
	com.crystaldecisions.sdk.occa.report.data.FieldValueType,java.util.Calendar,java.util.Date,
	gov.michigan.dit.timeexpense.util.IConstants"
%><script type="text/javascript" src="/TimeAndExpense/js/report_common.js"></script><%
	String reportName = "NonRoutineTravelerReport.rpt";
	ReportClientDocument clientDoc = new ReportClientDocument();
	IReportSource reportSource = null;

	try {
		
		// Open report
		clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);

		reportSource = clientDoc.getReportSource();
		
			    // ****** BEGIN LOGON DATASOURCE SNIPPET ****************  
			{
				// logon to database
				JRCHelperSample.logonDataSourceJndi(clientDoc, application.getAttribute(IConstants.APPLICATION_CACHE));
			}
			// ****** END LOGON DATASOURCE SNIPPET **************** 
			
		// ****** BEGIN CONNECT PARAMETERS SNIPPET ****************
		{
				// STRING VALUE PARAMETER.  
				String stringValue = (String) request.getAttribute("travelSelection");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMTRAVELSELECTION", "", stringValue);
	   }
		{
			// STRING VALUE PARAMETER.  
			String stringValue = (String) request.getAttribute("chosenDept");
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMDEPT", "", stringValue);
		}
		{
			// STRING VALUE PARAMETER.  
			String stringValue = (String) request.getAttribute("chosenAgency");
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMAGY", "", stringValue);
		}
		{
			// STRING VALUE PARAMETER.  
			String stringValue = (String) request.getAttribute("chosenTku");
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMTKU", "", stringValue);
		}
		{
			// NUMBER VALUE PARAMETER.  			
			Integer numberValue = (Integer) request.getAttribute("empId");

			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMEMPID", "", numberValue);
		}
		{
			// NUMBER VALUE PARAMETER.  				
			Integer numberValue = (Integer) request.getAttribute("SuperEmpId");
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMSUPID", "", numberValue);
		}
		{
			// DATE VALUE PARAMETER.
			Date dateParamVal = (Date)request.getAttribute("nonTravelDateFrom");
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMFROMDATE", "", dateParamVal);
		}
		{
			// DATE VALUE PARAMETER.
			Date dateParamVal = (Date)request.getAttribute("nonTravelDateTo");
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMTODATE", "", dateParamVal);
		}
		{
			// STRING VALUE PARAMETER.  
			String val = (String) request.getAttribute("save");
			String stringValueStatus = "";
			if(val.equalsIgnoreCase("true"))
				 stringValueStatus = "SAVE";
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMSAVE", "", stringValueStatus);
		}
		{
			// STRING VALUE PARAMETER.  
			String stringValueStatus = "";
			String val = (String) request.getAttribute("submitted");
			if(val.equalsIgnoreCase("true"))
				stringValueStatus="SUBM";
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMSUBMITTED", "", stringValueStatus);
		}
		{
			// STRING VALUE PARAMETER.  
			String stringValueStatus = "";
			String val = (String) request.getAttribute("approved");
			if(val.equalsIgnoreCase("true"))
				stringValueStatus="APPR";
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMAPPROVED","", stringValueStatus);
		}
	    {
			// STRING VALUE PARAMETER. 
			String stringValueStatus = ""; 
			String val = (String) request.getAttribute("proc");
			if(val.equalsIgnoreCase("true"))
				stringValueStatus="PROC";
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMPROC", "", stringValueStatus);
		} 
		
		

		// ****** END CONNECT PARAMETERS SNIPPET ****************	

		// ****** BEGIN CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************  
		{
			// Process the report
			//crystalReportPageViewer.processHttpRequest(request, response, application, null);
			JRCHelperSample.exportPDF(reportSource, request, response, pageContext.getServletContext(), false);

		}
		// ****** END CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************		

	} catch (ReportSDKExceptionBase e) {
		out.println(e);
	}finally{
		if(clientDoc.isOpen()) clientDoc.close();
	}
%>