<%@page
	import="com.businessobjects.samples.JRCHelperSample,com.crystaldecisions.report.web.viewer.CrystalReportViewer,
	com.crystaldecisions.reports.sdk.ReportClientDocument,
	com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
	com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
	com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
	com.crystaldecisions.sdk.occa.report.data.FieldValueType,java.util.Calendar,java.util.Date"
%><script type="text/javascript" src="${pageContext.request.contextPath}/js/report_common.js"></script><%@page import="gov.michigan.dit.timeexpense.util.IConstants"%><%
	String reportName = "ExceptionReport.rpt";
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
				String stringValue = (String)request.getAttribute("chosenDept");	
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "Department", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("chosenAgency");	
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "Agency", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("chosenTku");	
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "TKU", "", stringValue);
			}
			
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("empId");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "EID", "", stringValue);
			}
			{
				// NUMBER VALUE PARAMETER.  
			  // NUMBER VALUE PARAMETER. 
				Integer numberValue = (Integer)request.getAttribute("supervisorEid");	
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "SuperEmpId", "", numberValue);
			}
			
			
			{
				// DATE VALUE PARAMETER.
				//Calendar calendar = Calendar.getInstance();
				//calendar.set(2004, 1, 17);
				Date dateParamVal = (Date)request.getAttribute("expDateFrom");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ExpenseDateFrom", "", dateParamVal);
			}
			{
				// DATE VALUE PARAMETER.
				//Calendar calendar = Calendar.getInstance();
				// calendar.set(2004, 1, 17);
				Date dateParamVal = (Date)request.getAttribute("expDateTo");	
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ExpenseDateTo", "", dateParamVal);
			}
			
			{
				// DATE VALUE PARAMETER.
			//	Calendar calendar = Calendar.getInstance();
			//	calendar.set(2004, 1, 17);
				Date dateParamVal = (Date)request.getAttribute("pmtDateFrom");	
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "PaymentDateFrom", "", dateParamVal);
			}
			{
				// DATE VALUE PARAMETER.
			//	Calendar calendar = Calendar.getInstance();
			//	calendar.set(2004, 1, 17);
			Date dateParamVal = (Date)request.getAttribute("pmtDateTo");	
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "PaymentDateTo", "", dateParamVal);
			}
			
			
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("severity");	
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "Severity", "", stringValue);
			}
			
			
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("error_code_from");	
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ErrCodeFrom", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("error_code_to");	
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ErrCodeTo", "", stringValue);
			}

		
		// ****** END CONNECT PARAMETERS SNIPPET ****************	

		// ****** BEGIN CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************  
		{
			JRCHelperSample.exportPDF(reportSource, request, response, pageContext.getServletContext(), false);

             
		}
		// ****** END CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************		

	} catch (ReportSDKExceptionBase e) {
		out.println(e);
	} finally{
		if(clientDoc.isOpen()) clientDoc.close();
	}
%>