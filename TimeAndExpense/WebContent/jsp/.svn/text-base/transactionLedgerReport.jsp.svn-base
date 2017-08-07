<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.struts2.components.If"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transaction Ledger Report</title>
</head>
<body>

<%@page import="com.crystaldecisions.reports.sdk.*" %>
<%@page import="com.crystaldecisions.sdk.occa.report.data.*" %>
<%@page import="com.crystaldecisions.report.web.viewer.*" %>
<%@page import="com.crystaldecisions.reports.reportengineinterface.JPEReportSourceFactory" %>
<%@page import="com.crystaldecisions.sdk.occa.report.reportsource.IReportSourceFactory2" %>
<%@page import="com.crystaldecisions.sdk.occa.report.reportsource.IReportSource" %>
<%@page import="com.crystaldecisions.sdk.occa.report.data.*" %>
<%@page import="com.crystaldecisions.report.web.viewer.*" %>
<%@page import="com.crystaldecisions.sdk.occa.report.*" %>
<%@page import="java.util.*" %>
<%@page import="gov.michigan.dit.timeexpense.util.*"%>
<%
//check to see if the report source already exists
Object reportSource = session.getAttribute("reportSource");
String deptPrm = (String)request.getAttribute("chosenDept");
String agyPrm = (String)request.getAttribute("chosenAgency");
String tkuPrm = (String)request.getAttribute("chosenTku");
String eIDPrm = (String)request.getParameter("empId");
//String myEmployees = request.getParameter("myEmployees");
String superPrm = (String)request.getAttribute("supervisor");
//String SuperPrm = "";
//if(myEmployees == "Y") {
// to do - get supervisor code user is assigned to as supervisor
//}
Date pmtBegDatePrm = (Date)request.getAttribute("pmtDateFrom");
//Date PmtBegDatePrm = IConstants.defaultStartDate;
Date pmtEndDatePrm = (Date)request.getAttribute("pmtDateTo");
//Date PmtEndDatePrm = IConstants.defaultEndDate;
Date expBegDatePrm = (Date)request.getAttribute("expDateFrom");
//Date ExpBegDatePrm = IConstants.defaultStartDate;
Date expEndDatePrm = (Date)request.getAttribute("expDateTo");
//Date ExpEndDatePrm = IConstants.defaultEndDate;
String typePrm = (String)request.getAttribute("type");
String longTermAdvPrm = (String)request.getAttribute("longTermAdv");
//if(LongTermAdvPrm == null) {
//	LongTermAdvPrm = "N";
//	}
String adjustmentsOnlyPrm = (String)request.getAttribute("adjustmentsOnly");
//if(AdjustmentsOnlyPrm == null) {
//	AdjustmentsOnlyPrm = "N";
//	}
String rept = (String)request.getAttribute("rept");

//if the report source has not been opened
if(reportSource == null) {
    ReportClientDocument reportClientDoc = new ReportClientDocument();
    String report = "";
	//you will need to modify report variable to point to your report
    if(rept.equals("PrintCBDetails")) {
        report = "jsp/report/TransactionLedgerWithCBDetails.rpt";
    } 
/*    if(rept.equals("OmitCBDetails")) {
    	report = "jsp/report/ExpenseDetailReport.rpt";
    }
    if(rept.equals("PrintCBTotalsOnly")) {
    	report = "jsp/report/ExpenseDetailReport.rpt";
    }*/
	IReportSourceFactory2 rptSrcFactory = new JPEReportSourceFactory();
	reportSource = rptSrcFactory.createReportSource(report,request.getLocale());
//    reportClientDoc.open(report, 0);
//    reportSource = reportClientDoc.getReportSource();
    session.setAttribute("reportSource", reportSource);
}

//---------- Create the Parameter Field Objects -------------
//Create a Fields collection object to store the parameter fields in.
Fields fields = new Fields();

//Create a ParameterField object for each field that you wish to set.
ParameterField deptField = new ParameterField();
ParameterField agyField = new ParameterField();
ParameterField tkuField = new ParameterField();
ParameterField eidField = new ParameterField();
ParameterField superField = new ParameterField();
ParameterField pmtBegField = new ParameterField();
ParameterField pmtEndField = new ParameterField();
ParameterField expBegField = new ParameterField();
ParameterField expEndField = new ParameterField();
ParameterField longTermAdvField = new ParameterField();
ParameterField adjsOnlyField = new ParameterField();
ParameterField typeField = new ParameterField();

//Create a Values object and a ParameterFieldDiscreteValue object for each parameter field you wish to set.
//If a ranged value is being set, a ParameterFieldRangeValue object should be used instead of the discrete value object.
Values deptVal = new Values();
Values agyVal = new Values();
Values tkuVal = new Values();
Values eidVal = new Values();
Values superVal = new Values();
Values pmtBegVal = new Values();
Values pmtEndVal = new Values();
Values expBegVal = new Values();
Values expEndVal = new Values();
Values longTermAdvVal = new Values();
Values adjsOnlyVal = new Values();
Values typeVal = new Values();

ParameterFieldDiscreteValue deptDV = new ParameterFieldDiscreteValue();
ParameterFieldDiscreteValue agyDV = new ParameterFieldDiscreteValue();
ParameterFieldDiscreteValue tkuDV = new ParameterFieldDiscreteValue();
ParameterFieldDiscreteValue eidDV = new ParameterFieldDiscreteValue();
ParameterFieldDiscreteValue superDV = new ParameterFieldDiscreteValue();
ParameterFieldDiscreteValue pmtBegDV = new ParameterFieldDiscreteValue();
ParameterFieldDiscreteValue pmtEndDV = new ParameterFieldDiscreteValue();
ParameterFieldDiscreteValue expBegDV = new ParameterFieldDiscreteValue();
ParameterFieldDiscreteValue expEndDV = new ParameterFieldDiscreteValue();
ParameterFieldDiscreteValue longTermAdvDV = new ParameterFieldDiscreteValue();
ParameterFieldDiscreteValue adjsOnlyDV = new ParameterFieldDiscreteValue();
ParameterFieldDiscreteValue typeDV = new ParameterFieldDiscreteValue();

//----------- Initialize the parameter fields ----------

//Set the name and value for each parameter field that is added.
//Values for parameter fields are represented by a ParameterFieldDiscreteValue or ParameterFieldRangeValue object.
//Set the parameter name
deptField.setName("DeptPrm");
agyField.setName("AgyPrm");
tkuField.setName("TkuPrm");
eidField.setName("EIDPrm");
superField.setName("SuperPrm");
pmtBegField.setName("PmtBegDatePrm");
pmtEndField.setName("PmtEndDatePrm");
expBegField.setName("ExpBegDatePrm");
expEndField.setName("ExpEndDatePrm");
longTermAdvField.setName("LongTermAdvPrm");
adjsOnlyField.setName("AdjustmentsOnlyPrm");
typeField.setName("TypePrm");

//You must set the report name. Set the report name to an empty string if your report does not contain a subreport; otherwise, the report name will be the name of the subreport
deptField.setReportName("");
agyField.setReportName("");
tkuField.setReportName("");
eidField.setReportName("");
superField.setReportName("");
pmtBegField.setReportName("");
pmtEndField.setReportName("");
expBegField.setReportName("");
expEndField.setReportName("");
longTermAdvField.setReportName("");
adjsOnlyField.setReportName("");
typeField.setReportName("");

//Set the values for the parameter
deptDV.setValue(deptPrm);
agyDV.setValue(agyPrm);
tkuDV.setValue(tkuPrm);
eidDV.setValue(eIDPrm);
superDV.setValue(superPrm);
pmtBegDV.setValue(pmtBegDatePrm);
pmtEndDV.setValue(pmtEndDatePrm);
expBegDV.setValue(expBegDatePrm);
expEndDV.setValue(expEndDatePrm);
longTermAdvDV.setValue(longTermAdvPrm);
adjsOnlyDV.setValue(adjustmentsOnlyPrm);
typeDV.setValue(typePrm);

//Add the parameter field values to the Values collection object.
deptVal.add(deptDV);
agyVal.add(agyDV);
tkuVal.add(tkuDV);
eidVal.add(eidDV);
superVal.add(superDV);
pmtBegVal.add(pmtBegDV);
pmtEndVal.add(pmtEndDV);
expBegVal.add(expBegDV);
expEndVal.add(expEndDV);
longTermAdvVal.add(longTermAdvDV);
adjsOnlyVal.add(adjsOnlyDV);
typeVal.add(typeDV);

//Set the current Values collection for each parameter field.
deptField.setCurrentValues(deptVal);
agyField.setCurrentValues(agyVal);
tkuField.setCurrentValues(tkuVal);
eidField.setCurrentValues(eidVal);
superField.setCurrentValues(superVal);
pmtBegField.setCurrentValues(pmtBegVal);
pmtEndField.setCurrentValues(pmtEndVal);
expBegField.setCurrentValues(expBegVal);
expEndField.setCurrentValues(expEndVal);
longTermAdvField.setCurrentValues(longTermAdvVal);
adjsOnlyField.setCurrentValues(adjsOnlyVal);
typeField.setCurrentValues(typeVal);

//Add each parameter field to the Fields collection.
//The Fields object is now ready to be used with the viewer.
fields.add(deptField);
fields.add(agyField);
fields.add(tkuField);
fields.add(eidField);
fields.add(superField);
fields.add(pmtBegField);
fields.add(pmtEndField);
fields.add(expBegField);
fields.add(expEndField);
fields.add(longTermAdvField);
fields.add(adjsOnlyField);
fields.add(typeField);

//create the viewer and render the report
CrystalReportViewer viewer = new CrystalReportViewer();
viewer.setReportSource(reportSource);
viewer.setOwnPage(true);
viewer.setOwnForm(true);
//viewer.setEnableLogonPrompt(false);  
//viewer.setDatabaseLogonInfos(connInfos);

//set the parameters into the viewer
viewer.setParameterFields(fields);
//viewer.setEnableParameterPrompt(false);

//refresh the viewer if necessary (only required once)
if (session.getAttribute("refreshed") == null)
    {
    viewer.refresh();
    session.setAttribute("refreshed", "true");
    }
//viewer.setOwnPage(true);
//viewer.processHttpRequest(request, response, getServletConfig().getServletContext(), out);
viewer.processHttpRequest(request, response, getServletConfig().getServletContext(), null); 
//viewer.dispose();
%>
</body>
</html>