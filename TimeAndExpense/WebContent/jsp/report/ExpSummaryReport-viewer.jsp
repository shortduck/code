<%@page import="com.businessobjects.samples.JRCHelperSample,
com.crystaldecisions.reports.sdk.ReportClientDocument,
com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
gov.michigan.dit.timeexpense.model.display.CodingBlockSummaryBean,
gov.michigan.dit.timeexpense.model.display.ExpenseSummaryDetailsBean,
gov.michigan.dit.timeexpense.model.display.ExpenseSummaryReportBean,
java.util.ArrayList,
java.util.List,
gov.michigan.dit.timeexpense.util.IConstants,
org.apache.log4j.Logger"%><%
	String reportName = "ExpSummaryReport.rpt";
	ReportClientDocument clientDoc = new ReportClientDocument();
	
	try {
			// Open report
			clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);

			// ****** BEGIN POPULATE WITH POJO SNIPPET ****************  
			{
			ExpenseSummaryReportBean summaryReportData = (ExpenseSummaryReportBean)session.getAttribute(IConstants.EXP_SUMMARY_REPORT_DATA);
			summaryReportData.setTkuCodeAndName((String)request.getAttribute("TKU") + " " + (String)request.getAttribute("TKU_NAME"));
			
				// **** POPULATE MAIN REPORT ****
				{

					 // Populate POJO data source
					 String className = "gov.michigan.dit.timeexpense.model.display.ExpenseSummaryDetailsBean";
					 
					 // Look up existing table in the report to set the datasource for and obtain its alias.  This table must
					 // have the same schema as the Resultset that is being pushed in at runtime.  The table could be created
					 // from a Field Definition File, a Command Object, or regular database table.  As long the Resultset
					 // schema has the same field names and types, then the Resultset can be used as the datasource for the table.
					 String tableAlias = "ExpenseSummaryDetailsBean";

					 //Create a dataset based on the class gov.michigan.dit.timeexpense.model.display.ExpenseSummaryDetailsBean
					 //If the class does not have a basic constructor with no parameters, make sure to adjust that manually
					 
					 List dataSet = new ArrayList();
					 dataSet.addAll(summaryReportData.getDetails());

					 //Push the resultset into the report (the POJO resultset will then be the runtime datasource of the report)
					 JRCHelperSample.passPOJO(clientDoc, dataSet, className, tableAlias, "SummaryByDetailCategory");
				}

				// **** POPULATE MAIN REPORT ****
				{

					 // Populate POJO data source
					 String className = "gov.michigan.dit.timeexpense.model.display.CodingBlockSummaryBean";
					 
					 // Look up existing table in the report to set the datasource for and obtain its alias.  This table must
					 // have the same schema as the Resultset that is being pushed in at runtime.  The table could be created
					 // from a Field Definition File, a Command Object, or regular database table.  As long the Resultset
					 // schema has the same field names and types, then the Resultset can be used as the datasource for the table.
					 String tableAlias = "CodingBlockSummaryBean";

					 //Create a dataset based on the class gov.michigan.dit.timeexpense.model.display.CodingBlockSummaryBean
					 //If the class does not have a basic constructor with no parameters, make sure to adjust that manually
					 List dataSet = new ArrayList();
					 dataSet.addAll(summaryReportData.getCodingBlocks());

					 //Push the resultset into the report (the POJO resultset will then be the runtime datasource of the report)
					 JRCHelperSample.passPOJO(clientDoc, dataSet, className, tableAlias, "SummaryByCodingBlocks");
				}

				// **** POPULATE MAIN REPORT ****
				{

					 // Populate POJO data source
					 String className = "gov.michigan.dit.timeexpense.model.display.ExpenseSummaryReportBean";
					 
					 // Look up existing table in the report to set the datasource for and obtain its alias.  This table must
					 // have the same schema as the Resultset that is being pushed in at runtime.  The table could be created
					 // from a Field Definition File, a Command Object, or regular database table.  As long the Resultset
					 // schema has the same field names and types, then the Resultset can be used as the datasource for the table.
					 String tableAlias = "ExpenseSummaryReportBean";

					 //Create a dataset based on the class gov.michigan.dit.timeexpense.model.display.ExpenseSummaryReportBean
					 //If the class does not have a basic constructor with no parameters, make sure to adjust that manually
					 List dataSet = new ArrayList();
					 dataSet.add(summaryReportData);

					 //Push the resultset into the report (the POJO resultset will then be the runtime datasource of the report)
					 JRCHelperSample.passPOJO(clientDoc, dataSet, className, tableAlias, "");
				}


			}
			// ****** END POPULATE WITH POJO SNIPPET ****************		
			
		// ****** BEGIN EXPORT PDF SNIPPET ****************
		{
			IReportSource reportSource = clientDoc.getReportSource();
			JRCHelperSample.exportPDF(reportSource, request, response, config.getServletContext(), false);
		}
		// ****** END EXPORT PDF SNIPPET ****************

	} catch (ReportSDKExceptionBase e) {
		Logger logger = Logger.getLogger(this.getClass());
		logger.error(e.getMessage(), e);
	
		out.write("<br/><br/><h3 style='color:red'> An unknown error occured! </h3>");
		out.flush();
	}finally{
		try{clientDoc.getReportSource().dispose();clientDoc.close();}catch(Exception ex){}
	} 
%>