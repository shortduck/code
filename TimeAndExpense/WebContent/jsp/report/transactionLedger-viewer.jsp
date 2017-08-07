<%@page
	import="com.businessobjects.samples.JRCHelperSample,
	com.crystaldecisions.report.web.viewer.CrystalReportViewer,
	com.crystaldecisions.reports.sdk.ReportClientDocument,
	com.crystaldecisions.sdk.occa.report.application.OpenReportOptions,
	com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase,
	com.crystaldecisions.sdk.occa.report.reportsource.IReportSource, java.text.DateFormat, java.text.SimpleDateFormat,
	com.crystaldecisions.sdk.occa.report.data.FieldValueType,java.util.Calendar,java.util.Date,
	gov.michigan.dit.timeexpense.util.IConstants"
%><script type="text/javascript" src="${pageContext.request.contextPath}/js/report_common.js"></script><%
    String rept = (String)request.getParameter("rept");
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
		String reportName = "";
		if(rept.equals("PrintCBDetails")) {
        	reportName = "TransactionLedgerWithCBDetails.rpt";
        } 
    	if(rept.equals("OmitCBDetails")) {
    		reportName = "TransactionLedgerOmitCBDetails.rpt";
    	}
    	if(rept.equals("PrintCBTotalsOnly")) {
    		reportName = "TransactionLedgerOnlyCBTotals.rpt";
    	}
    	if(rept.equals("PrintCBTotalsDept")) {
    		reportName = "TransactionLedgerDeptCBTotals.rpt";
    	}
    
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
				// DATE VALUE PARAMETER.
				Date dateParamVal = df.parse(request.getParameter("pmtDateFrom"));
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "PmtBegDatePrm", "", dateParamVal);
			}
			{
				// DATE VALUE PARAMETER.
				Date dateParamVal = df.parse(request.getParameter("pmtDateTo"));
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "PmtEndDatePrm", "", dateParamVal);
			}
			{
				// DATE VALUE PARAMETER.
				Date dateParamVal = df.parse(request.getParameter("expDateFrom"));
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ExpBegDatePrm", "", dateParamVal);
			}
			{
				// DATE VALUE PARAMETER.
				Date dateParamVal = df.parse(request.getParameter("expDateTo"));
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "ExpEndDatePrm", "", dateParamVal);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getParameter("chosenDepartment");
  			    JRCHelperSample.addDiscreteParameterValue(clientDoc, "DeptPrm", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.
    			if(rept.equals("PrintCBTotalsDept")) 
    				{}
    			else
    				{
					String stringValue = (String)request.getParameter("chosenAgency");
					JRCHelperSample.addDiscreteParameterValue(clientDoc, "AgyPrm", "", stringValue);
					}
    		}
			{
				// STRING VALUE PARAMETER.  
    			if(rept.equals("PrintCBTotalsDept")) 
    				{}
    			else
    				{
					String stringValue = (String)request.getParameter("chosenTku");
					JRCHelperSample.addDiscreteParameterValue(clientDoc, "TkuPrm", "", stringValue);
					}
			}
			{
				// STRING VALUE PARAMETER.  
    			if(rept.equals("PrintCBTotalsDept")) 
    				{}
    			else
    				{
					String stringValue = (String)request.getParameter("empId");
					JRCHelperSample.addDiscreteParameterValue(clientDoc, "EIDPrm", "", stringValue);
					}
			}
			{
				// STRING VALUE PARAMETER.  
    			if(rept.equals("PrintCBTotalsDept")) 
    				{}
    			else
    				{
					String numberValue = request.getParameter("supervisorEid");
					JRCHelperSample.addDiscreteParameterValue(clientDoc, "SuperPrm", "", numberValue);
					}
			}
			{
				// STRING VALUE PARAMETER.  
    			if(rept.equals("PrintCBTotalsDept")) 
    				{}
    			else
    				{
					String stringValue = (String)request.getParameter("type");
					JRCHelperSample.addDiscreteParameterValue(clientDoc, "TypePrm", "", stringValue);
					}
				}
			{
				// STRING VALUE PARAMETER.  
    			if(rept.equals("PrintCBTotalsDept")) 
    				{}
    			else
    				{
					String stringValue = (String)request.getParameter("longTermAdv");	
					JRCHelperSample.addDiscreteParameterValue(clientDoc, "LongTermAdvPrm", "", stringValue);
					}
				}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getParameter("adjustmentsOnly");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "AdjustmentsOnlyPrm", "", stringValue);
			}
			{
				// STRING VALUE PARAMETER.  
				String stringValue = (String)request.getParameter("unpaidExpensesOnly");
				JRCHelperSample.addDiscreteParameterValue(clientDoc, "UnpaidExpensesOnlyPrm", "", stringValue);
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