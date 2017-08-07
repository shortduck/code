<%@page
	import="com.businessobjects.samples.JRCHelperSample,
	com.crystaldecisions.report.web.viewer.CrystalReportViewer,
	gov.michigan.dit.timeexpense.model.display.TEReprotClientDocument,
	com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
	com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
	com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
	com.crystaldecisions.sdk.occa.report.data.FieldValueType,java.util.Calendar,java.util.Date"%>
	
	<%@page import="gov.michigan.dit.timeexpense.util.IConstants"%>

<script type="text/javascript"	src="${pageContext.request.contextPath}/js/report_common.js"></script>	
	
<%
	try{
	String reportName = "ExpenseDetailReport.rpt";
		TEReprotClientDocument clientDoc = (TEReprotClientDocument) session.getAttribute("detailReport");
	if (clientDoc == null){
		clientDoc = new TEReprotClientDocument();
				// Open report
		clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);
		// logon to database
		JRCHelperSample.logonDataSourceJndi(clientDoc, application.getAttribute(IConstants.APPLICATION_CACHE));
		session.setAttribute("detailReport", clientDoc);
	}
	
	
		IReportSource reportSource = null;
		reportSource = clientDoc.getReportSource();
		
		// ****** BEGIN LOGON DATASOURCE SNIPPET ****************  
			{
				// logon to database
				//JRCHelperSample.logonDataSourceJndi(clientDoc, application.getAttribute(IConstants.APPLICATION_CACHE));
			}
			// ****** END LOGON DATASOURCE SNIPPET **************** 
		
		{
			String numberValue = (String)request.getParameter("expenseMasterId");
			JRCHelperSample.addDiscreteParameterValue(clientDoc, "expmID", "", new Integer(numberValue));
						
		}
		
		// ****** END CONNECT PARAMETERS SNIPPET ****************	

		// ****** BEGIN CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************  
		{
			JRCHelperSample.exportPDF(reportSource, request, response, pageContext.getServletContext(), false);

             
		}
		// ****** END CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************		

	} catch (ReportSDKExceptionBase e) {
		out.println(e);
	} 
%>

