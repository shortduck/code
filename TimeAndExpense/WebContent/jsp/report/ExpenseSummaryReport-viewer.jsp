<%@page
	import="com.businessobjects.samples.JRCHelperSample,
	com.crystaldecisions.report.web.viewer.CrystalReportViewer,
	gov.michigan.dit.timeexpense.model.display.TEReprotClientDocument,
	com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
	com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
	com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
	com.crystaldecisions.sdk.occa.report.document.PrintReportOptions,
	com.crystaldecisions.reports.sdk.PrintOutputController,
	com.crystaldecisions.sdk.occa.report.exportoptions.*,	
	java.io.*,
	com.crystaldecisions.sdk.occa.report.data.FieldValueType,java.util.Calendar,java.util.Date"%>
	
	<%@page import="gov.michigan.dit.timeexpense.util.IConstants"%>

<script type="text/javascript"	src="${pageContext.request.contextPath}/js/report_common.js"></script>	
	
<%
	try {
	String reportName = "ExpenseSummaryReport.rpt";
	TEReprotClientDocument clientDoc = (TEReprotClientDocument) session.getAttribute("summaryReport");
	if (clientDoc == null){
		clientDoc = new TEReprotClientDocument();
				// Open report
		clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);
		// logon to database
		JRCHelperSample.logonDataSourceJndi(clientDoc, application.getAttribute(IConstants.APPLICATION_CACHE));
		session.setAttribute("summaryReport", clientDoc);
	}
		IReportSource reportSource = null;
		reportSource = clientDoc.getReportSource();
		
			// ****** END LOGON DATASOURCE SNIPPET **************** 	
		
		// ****** BEGIN CONNECT PARAMETERS SNIPPET ****************
		{
			String numberValue = (String) request.getParameter("expenseMasterId");
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "expmID", "", new Integer(numberValue));
		}
		/*
		{
				// NUMBER VALUE PARAMETER.  
				Integer numberValue = (Integer)request.getAttribute("expenseMasterId");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "Pm-V_EXP_MSTR.EXPM_IDENTIFIER", "ReqSumByCB", numberValue);
			}
			{
				// NUMBER VALUE PARAMETER.  
				Integer numberValue = (Integer)request.getAttribute("expenseMasterId");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "Pm-V_EXP_MSTR.EXPM_IDENTIFIER", "ReqSumByCat", numberValue);
			}
			{
				// NUMBER VALUE PARAMETER.  
				Integer numberValue = (Integer)request.getAttribute("expenseMasterId");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "Pm-V_EXP_MSTR.EXPM_IDENTIFIER", "ReqTots", numberValue);
			}*/
		
		
		// ****** END CONNECT PARAMETERS SNIPPET ****************	

		// ****** BEGIN CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************  
		{

	JRCHelperSample.exportPDF(reportSource, request, response, pageContext.getServletContext(), false);
            
		}
		// ****** END CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************		

	} catch (ReportSDKExceptionBase e) {
		out.println(e);
	} /*finally{
		if(clientDoc.isOpen()) clientDoc.close();
	}*/
	%>

