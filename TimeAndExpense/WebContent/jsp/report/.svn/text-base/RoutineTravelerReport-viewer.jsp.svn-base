<%@page
	import="com.businessobjects.samples.JRCHelperSample,
	com.crystaldecisions.report.web.viewer.CrystalReportViewer,
	com.crystaldecisions.reports.sdk.ReportClientDocument,
	com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
	com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
	com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
	com.crystaldecisions.sdk.occa.report.data.FieldValueType,java.util.Calendar,java.util.Date,
	gov.michigan.dit.timeexpense.util.IConstants"
%><script type="text/javascript" src="${pageContext.request.contextPath}/js/report_common.js"></script><%
	String reportName = "RoutineTravelerReport.rpt";
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
			Date dateParamVal = (Date)request.getAttribute("expDateFrom");
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMFROMDATE", "", dateParamVal);
		}
		{
			// DATE VALUE PARAMETER.
			Date dateParamVal = (Date)request.getAttribute("expDateTo");
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "PRMTODATE", "", dateParamVal);
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