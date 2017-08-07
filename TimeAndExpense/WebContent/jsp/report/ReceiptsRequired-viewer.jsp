<%@page import="gov.michigan.dit.timeexpense.util.JPAUtil"%>
<%@page import="gov.michigan.dit.timeexpense.dao.VReceiptsRequiredReportDAO"%>
<%@page import="java.util.List"%>
<%@page import="gov.michigan.dit.timeexpense.model.core.VReceiptsRequiredReport"%>
<%@page
	import="com.businessobjects.samples.JRCHelperSample,
	com.crystaldecisions.report.web.viewer.CrystalReportViewer,
	com.crystaldecisions.reports.sdk.ReportClientDocument,
	com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
	com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
	com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
	com.crystaldecisions.sdk.occa.report.data.FieldValueType,java.util.Calendar,java.util.Date"
%><%@page import="gov.michigan.dit.timeexpense.util.IConstants"%><script type="text/javascript"	src="${pageContext.request.contextPath}/js/report_common.js"></script><%
	// This sample code calls methods from the JRCHelperSample class, which 
	// contains examples of how to use the BusinessObjects APIs. You are free to 
	// modify and distribute the source code contained in the JRCHelperSample class. 
 
    	String reportName = "ReceiptsRequired.rpt";
		ReportClientDocument clientDoc = new ReportClientDocument();
	    IReportSource reportSource = null;
	// Open report
  	clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);
     //Create a dataset based on the class gov.michigan.dit.timeexpense.model.display.ExpenseSummaryDetailsBean
	 //If the class does not have a basic constructor with no parameters, make sure to adjust that manually

	try {
			// create entity manager
			reportSource = clientDoc.getReportSource();

			// ****** BEGIN POPULATE WITH POJO SNIPPET ****************  
			{
			
				// **** POPULATE MAIN REPORT ****
				{

					 // Populate POJO data source
					 String className = "gov.michigan.dit.timeexpense.model.core.VReceiptsRequiredReport";
					 
					 // Look up existing table in the report to set the datasource for and obtain its alias.  This table must
					 // have the same schema as the Resultset that is being pushed in at runtime.  The table could be created
					 // from a Field Definition File, a Command Object, or regular database table.  As long the Resultset
					 // schema has the same field names and types, then the Resultset can be used as the datasource for the table.
					 String tableAlias = "VReceiptsRequiredReport";

					 
					 List<VReceiptsRequiredReport> dataSet = (List<VReceiptsRequiredReport>) request.getAttribute("receiptsRequiredReportResults");
					
					 //Push the resultset into the report (the POJO resultset will then be the runtime datasource of the report)
					 JRCHelperSample.passPOJO(clientDoc, dataSet, className, tableAlias, "");
					 
				}

				

			}
			// ****** END POPULATE WITH POJO SNIPPET ****************
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
				// DATE VALUE PARAMETER.
			
				Date dateParamVal = (Date)request.getAttribute("expDateTo");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ExpenseDateTo", "", dateParamVal);
			}
			
						
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("auditType");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "AuditType", "", stringValue);
			}
			
			
			{
				// DATE VALUE PARAMETER.
			
				Date dateParamVal = (Date)request.getAttribute("pmtDateFrom");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "PaymentDateFrom", "", dateParamVal);
			}
			{
				// DATE VALUE PARAMETER.
			
				Date dateParamVal = (Date)request.getAttribute("pmtDateTo");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "PaymentDateTo", "", dateParamVal);
			}
			
					
			{
				// NUMBER VALUE PARAMETER. 
				Integer numberValue = (Integer)request.getAttribute("supervisorEid");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "SuperEmpId", "", numberValue);
			}
			{
				// DATE VALUE PARAMETER.
		
				Date dateParamVal = (Date)request.getAttribute("expDateFrom");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ExpenseDateFrom", "", dateParamVal);
			}
			{
				// NUMBER VALUE PARAMETER. 
				Integer numberValue = (Integer)request.getAttribute("Show_X_Percent");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "Show_X_Percent", "", numberValue);
			}
			
			{
				// NUMBER VALUE PARAMETER. 
				Double numberValue = (Double)request.getAttribute("expenseAmountFrom");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ExpenseAmountFrom", "", numberValue);
			}
			
			{
				// NUMBER VALUE PARAMETER. 
				Double numberValue = (Double)request.getAttribute("expenseAmountTo");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ExpenseAmountTo", "", numberValue);
			}
			
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("specificExpenseType");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "SpecificExpenseType", "", stringValue);
			}
			
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("travelInd");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "TravelInd", "", stringValue);
			}
			
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("expenseType");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ExpenseType", "", stringValue);
			}

			// ****** END CONNECT PARAMETERS SNIPPET ****************
				

			// ****** BEGIN CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************  
			{
				// Process the report
				//crystalReportPageViewer.processHttpRequest(request, response, application, null);
				JRCHelperSample.exportPDF(reportSource, request, response, pageContext.getServletContext(), false);
			}
			// ****** END CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************		
	


	} catch (Exception e) {
	    e.printStackTrace();
	} finally{
		if(clientDoc.isOpen()) clientDoc.close();
	}
%>