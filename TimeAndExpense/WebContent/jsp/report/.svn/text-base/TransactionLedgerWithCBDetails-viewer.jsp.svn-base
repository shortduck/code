<%@page import="com.businessobjects.samples.JRCHelperSample,
com.crystaldecisions.report.web.viewer.CrystalReportViewer,
com.crystaldecisions.reports.sdk.ReportClientDocument,
com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
java.util.Date"
%><%
	// This sample code calls methods from the JRCHelperSample class, which 
	// contains examples of how to use the BusinessObjects APIs. You are free to 
	// modify and distribute the source code contained in the JRCHelperSample class. 

	try {

		String rept = (String)request.getAttribute("rept");
		String reportName = "";
		if(rept.equals("PrintCBDetails")) {
        	reportName = "jsp/report/TransactionLedgerWithCBDetails.rpt";
        } 
/*    if(rept.equals("OmitCBDetails")) {
    	reportName = "jsp/report/TransactionLedgerOmitCBDetails.rpt";
    }
    if(rept.equals("PrintCBTotalsOnly")) {
    	reportName = "jsp/report/TransactionLedgerOnlyCBTotals.rpt";
    }*/
		
		ReportClientDocument clientDoc = (ReportClientDocument) session.getAttribute(reportName);

		if (clientDoc == null) {
			// Report can be opened from the relative location specified in the CRConfig.xml, or the report location
			// tag can be removed to open the reports as Java resources or using an absolute path
			// (absolute path not recommended for Web applications).

			clientDoc = new ReportClientDocument();
			
			// Open report
			clientDoc.open(reportName, OpenReportOptions._openAsReadOnly);

		/*
			// ****** BEGIN LOGON DATASOURCE SNIPPET ****************  
			{
				//	Database username and password
				String userName = "dbUser";			// TODO: Fill in database user
				String password = "dbPassword";		// TODO: FIll in password

				// logon to database
				JRCHelperSample.logonDataSource(clientDoc, userName, password);
			}
			// ****** END LOGON DATASOURCE SNIPPET **************** 		
		*/


			// ****** BEGIN CONNECT PARAMETERS SNIPPET ****************		
			{
				// DATE VALUE PARAMETER.
				Date dateParamVal = (Date)request.getAttribute("pmtDateFrom");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "PmtBegDatePrm", "", dateParamVal);
			}
			{
				// DATE VALUE PARAMETER.
				Date dateParamVal = (Date)request.getAttribute("pmtDateTo");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "PmtEndDatePrm", "", dateParamVal);
			}
			{
				// DATE VALUE PARAMETER.
				Date dateParamVal = (Date)request.getAttribute("expDateFrom");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ExpBegDatePrm", "", dateParamVal);
			}
			{
				// DATE VALUE PARAMETER.
				Date dateParamVal = (Date)request.getAttribute("expDateTo");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ExpEndDatePrm", "", dateParamVal);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("chosenDept");
  			    JRCHelperSample.addDiscreteParameterValue(clientDoc, "DeptPrm", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("chosenAgency");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "AgyPrm", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("chosenTku");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "TkuPrm", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getParameter("empId");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "EIDPrm", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("supervisor");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "SuperPrm", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("type");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "TypePrm", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("longTermAdv");	
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "LongTermAdvPrm", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getAttribute("adjustmentsOnly");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "AdjustmentsOnlyPrm", "", stringValue);
			}
		/*
			{
				// STRING VALUE PARAMETER.  
				String stringValue = "String parameter value.";	// TODO: Fill in value
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "Pm-V_TRANS_LEDGER_MAIN.TYPE", "AdvExpCodingBlocks", stringValue);
			}
		    {
				// NUMBER VALUE PARAMETER.  
				Integer numberValue = new Integer(7);	// TODO: Fill in value
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "Pm-V_TRANS_LEDGER_MAIN.MSTR_ID", "AdvExpCodingBlocks", numberValue);
			}

			// ****** END CONNECT PARAMETERS SNIPPET ****************	
		*/

			// Store the report document in session
			session.setAttribute(reportName, clientDoc);

		}

				/*
			// ****** BEGIN CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************  
			{
				// Create the CrystalReportViewer object
				CrystalReportViewer crystalReportPageViewer = new CrystalReportViewer();

				//	set the reportsource property of the viewer
				IReportSource reportSource = clientDoc.getReportSource();				
				crystalReportPageViewer.setReportSource(reportSource);

				// set viewer attributes
				crystalReportPageViewer.setOwnPage(true);
				crystalReportPageViewer.setOwnForm(true);

				// Apply the viewer preference attributes



				// Process the report
				crystalReportPageViewer.processHttpRequest(request, response, application, null); 

			}
			// ****** END CONNECT CRYSTALREPORTPAGEVIEWER SNIPPET ****************		
		*/


	} catch (ReportSDKExceptionBase e) {
	    out.println(e);
	} 
	
%>