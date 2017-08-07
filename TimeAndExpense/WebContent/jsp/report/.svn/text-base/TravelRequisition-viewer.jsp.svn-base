<%@ page import="com.businessobjects.samples.JRCHelperSample,
com.crystaldecisions.reports.sdk.ReportClientDocument,
com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
gov.michigan.dit.timeexpense.model.display.TravelRequisitionReportDisplayBean,
com.crystaldecisions.sdk.occa.report.application.internal.POJOResultSetHelper,
java.util.ArrayList,
java.util.List,
gov.michigan.dit.timeexpense.util.IConstants,
org.apache.log4j.Logger"%><%
	String reportName = "TravelRequisitionReport.rpt";
	ReportClientDocument clientDoc = new ReportClientDocument();

	try {
			// Open report
			clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);

			// ****** BEGIN POPULATE WITH POJO SNIPPET ****************  
			{
			TravelRequisitionReportDisplayBean display = (TravelRequisitionReportDisplayBean) request.getAttribute("DISPLAY");

				// **** POPULATE MAIN REPORT ****
				{

					 // Populate POJO data source
					 String className = "gov.michigan.dit.timeexpense.model.display.TravelRequisitionReportDisplayBean";
					 
					 // Look up existing table in the report to set the datasource for and obtain its alias.  This table must
					 // have the same schema as the Resultset that is being pushed in at runtime.  The table could be created
					 // from a Field Definition File, a Command Object, or regular database table.  As long the Resultset
					 // schema has the same field names and types, then the Resultset can be used as the datasource for the table.
					 String tableAlias = "TravelRequisitionReportDisplayBean_1";
					 
					  List dataSet = new ArrayList();
					 dataSet.add(display);

					  POJOResultSetHelper pojoHelper = new POJOResultSetHelper(TravelRequisitionReportDisplayBean.class);
   					  pojoHelper.setMemberColumnDisplaySize("getOutOfStateAuthCodes", 256);
   					  pojoHelper.setMemberColumnDisplaySize("getComments",256);
    	              java.sql.ResultSet rs = pojoHelper.createResultSet(dataSet);
                      clientDoc.getDatabaseController().setDataSource(rs, "TravelRequisitionReportDisplayBean_1", "TravelRequisitionReportDisplayBean");
					 //Push the resultset into the report (the POJO resultset will then be the runtime datasource of the report)
					 //JRCHelperSample.passPOJO(clientDoc, dataSet, className, tableAlias, "");
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