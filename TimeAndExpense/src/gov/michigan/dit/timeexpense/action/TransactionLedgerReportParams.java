/**
 * 
 */
package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.WebReportParams;
import gov.michigan.dit.timeexpense.service.AdvanceCodingBlockDSP;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ReportsDSP;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.service.TravelRequisitionDSP;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * This is the Action class which handles the form submission for the transaction ledger reports 
 * Search and the gets the response back and delegates it to the JSP.
 * 
 * @author kurtzj
 *
 */
@SuppressWarnings("all")
public class TransactionLedgerReportParams extends AppointmentSearchAction implements ServletRequestAware{
    private static Logger logger = Logger.getLogger(TransactionLedgerReportParams.class);
	
    private Date expDateFrom;
    private Date expDateTo;
    private Date pmtDateFrom; 
    private Date pmtDateTo; 
    private String myEmployees;
    private int supervisorEid;
    private String type;
    private String longTermAdv;
    private String adjustmentsOnly;
    private String unpaidExpensesOnly;
    private String rept;
    private String exportType;
    private String reportName;

    private  HttpServletRequest request;
    private ReportsDSP reportsService;
    
	public void prepare() {
		// initialize services - called prior to all actions
		reportsService = new ReportsDSP(entityManager);
	}
    
	@Override
	public String execute() throws Exception {
		// If employee wishes to see his expense report, then dept, agency, tku & empId is not asked. So get it.
		if(isEmployeeViewingSelfReport()){
			if(!populateEmployeeInfo()) return "failure";
		}
		// If manager viewing report for his employees
		else if("on".equalsIgnoreCase(myEmployees)){
			supervisorEid = (int)getSupervisorEid();

			//PS: Set dept, agency and tku to blank string as Crystal API fails for NULL values!
		    chosenDepartment = "";
		    chosenAgency = "";
		    chosenTku = "";
	        empId = "";
		}
		else{
		    chosenDepartment = extractCode(getChosenDepartment());
		    chosenAgency = extractCode(getChosenAgency());
		    chosenTku = extractCode(getChosenTku());
            empId = getEmpId();
		}
            expDateFrom = getExpDateFrom();
            expDateTo = getExpDateTo();
            pmtDateFrom = getPmtDateFrom();
            pmtDateTo = getPmtDateTo();
            type = getType();
            longTermAdv = getLongTermAdv();
            adjustmentsOnly = getAdjustmentsOnly();
            unpaidExpensesOnly = getUnpaidExpensesOnly();
            rept = getRept();
            exportType = getExportType();
            supervisorEid = (int)getSupervisorEid();
            if(expDateFrom == null){
            	expDateFrom =  new Date(0, 0, 1);//, "01/01/1900"));
            }
            
            if(expDateTo == null){
            	expDateTo =  new Date(2222-1900, 11, 31);//, "01/01/1900"));
            }
            
            if(pmtDateFrom == null){
            	pmtDateFrom =  new Date(0, 0, 1);//, "01/01/1900"));
            }
            
            if(pmtDateTo == null){
            	pmtDateTo =  new Date(2222-1900, 11, 31);//, "01/01/1900"));
            }
            reportName = getReportName();
            WebReportParams params = new WebReportParams(getLoggedInUser().getUserId(), Calendar.getInstance().getTime(),
            		chosenDepartment, chosenAgency, chosenTku, empId, supervisorEid, pmtDateFrom, pmtDateTo, expDateFrom,
            		expDateTo, rept, type, longTermAdv, adjustmentsOnly, unpaidExpensesOnly, reportName
            		);
            
            reportsService.saveReportParams(params);
            
               
           /* String parameterString = setupRequestParameters();
            setJsonResponse("{'parameters':'"+ parameterString + "'}");*/

			return "success";
            }

	
	/**
	 * Constructs a parameter string
	 * @return
	 */
	
	private String setupRequestParameters (){
		 StringBuilder requestParameters = new StringBuilder();
         requestParameters.append("chosenDepartment=");
         requestParameters.append(chosenDepartment);
         requestParameters.append("&");
         requestParameters.append("chosenAgency=");
         requestParameters.append(chosenAgency);
         requestParameters.append("&");
         requestParameters.append("chosenTku=");
         requestParameters.append(chosenTku);
         requestParameters.append("&");
         requestParameters.append("empId=");
         requestParameters.append(empId);
         requestParameters.append("&");
         requestParameters.append("pmtDateFrom=");
         requestParameters.append(TimeAndExpenseUtil.constructDateString(pmtDateFrom));
         requestParameters.append("&");
         requestParameters.append("pmtDateTo=");
         requestParameters.append(TimeAndExpenseUtil.constructDateString(pmtDateTo));
         requestParameters.append("&");
         requestParameters.append("expDateFrom=");
         requestParameters.append(TimeAndExpenseUtil.constructDateString(expDateFrom));
         requestParameters.append("&");
         requestParameters.append("expDateTo=");
         requestParameters.append(TimeAndExpenseUtil.constructDateString(expDateTo));
         requestParameters.append("&");
         requestParameters.append("type=");
         requestParameters.append(type);
         requestParameters.append("&");
         requestParameters.append("rept=");
         requestParameters.append(rept);
         requestParameters.append("&");
         requestParameters.append("supervisorEid=");
         requestParameters.append(supervisorEid);
         requestParameters.append("&");
         requestParameters.append("longTermAdv=");
         requestParameters.append(longTermAdv);
         requestParameters.append("&");
         requestParameters.append("adjustmentsOnly=");
         requestParameters.append(adjustmentsOnly);
         requestParameters.append("&");
         requestParameters.append("unpaidExpensesOnly=");
         requestParameters.append(unpaidExpensesOnly);
         requestParameters.append("&");
         requestParameters.append("exportType=");
         requestParameters.append(exportType);
         
		return requestParameters.toString();
	}

	@Override
	public void validate() {
		boolean result = true;
		
		// validate dept, agency, tku if NOT mgr viewing report for his employees
		if(!"on".equalsIgnoreCase(myEmployees)){
			result = validateDepartment() && validateAgency() && validateTku() && validateEmpId();
		}
		
		// validate dates
		result = result && validateExpenseAndPaymentDates();
		
		// log result
		if(logger.isDebugEnabled()){
			if(result){
				logger.debug("Validation successful");
			}else{
				String errorMsg = (String)((List)getFieldErrors().get(((String)getFieldErrors().keySet().iterator().next()))).get(0);
				logger.debug("Validation failed. ERROR: "+errorMsg);
			}
		}
	}

	private boolean validateExpenseAndPaymentDates() {
		return validateExpenseDates() && validatePaymentDates() && validateDatePresence();
	}

	private boolean validateExpenseDates() {
		if(expDateFrom == null && expDateTo != null) addFieldError("errors", "Please select Expense 'From' date");
		if(expDateFrom != null && expDateTo == null) addFieldError("errors", "Please select Expense 'To' date");
		if(expDateFrom != null && expDateTo !=null && expDateTo.before(expDateFrom)){
			addFieldError("errors", "Expense 'To' date cannot be less than 'From' date");
		}
		
		return getFieldErrors().isEmpty();
	}

	private boolean validatePaymentDates() {
		if(pmtDateFrom == null && pmtDateTo != null) addFieldError("errors", "Please select Payment 'From' date");
		if(pmtDateFrom != null && pmtDateTo == null) addFieldError("errors", "Please select Payment 'To' date");
		if(pmtDateFrom != null && pmtDateTo != null && pmtDateTo.before(pmtDateFrom)){
			addFieldError("errors", "Payment 'To' date cannot be less than 'From' date");
		}

		return getFieldErrors().isEmpty();
	}

	private boolean validateDatePresence() {
		if(expDateFrom == null && expDateTo == null && pmtDateFrom == null && pmtDateTo == null){
			addFieldError("errors", "Please Enter Payment or Expense Date Range");
		}
		
		return getFieldErrors().isEmpty();
	}
	
	private void setReportName (){
		if(rept.equals("PrintCBDetails")) {
			rept = "TransactionLedgerWithCBDetails.rpt";
        } 
    	if(rept.equals("OmitCBDetails")) {
    		rept = "TransactionLedgerOmitCBDetails.rpt";
    	}
    	if(rept.equals("PrintCBTotalsOnly")) {
    		rept = "TransactionLedgerOnlyCBTotals.rpt";
    	}
    	if(rept.equals("PrintCBTotalsDept")) {
    		rept = "TransactionLedgerDeptCBTotals.rpt";
    	}
	}
	
	public Date getExpDateFrom() {
	    return expDateFrom;
	}

	public void setExpDateFrom(Date expDateFrom) {
	    this.expDateFrom = expDateFrom;
	}

	public Date getExpDateTo() {
	    return expDateTo;
	}

	public void setExpDateTo(Date expDateTo) {
	    this.expDateTo = expDateTo;
	}

	public Date getPmtDateFrom() {
	    return pmtDateFrom;
	}

	public void setPmtDateFrom(Date pmtDateFrom) {
	    this.pmtDateFrom = pmtDateFrom;
	}

	public Date getPmtDateTo() {
	    return pmtDateTo;
	}

	public void setPmtDateTo(Date pmtDateTo) {
	    this.pmtDateTo = pmtDateTo;
	}

	public String getMyEmployees() {
	    return myEmployees;
	}

	public void setMyEmployees(String myEmployees) {
	    this.myEmployees = myEmployees;
	}

	public int getSupervisorEid() {
	    if(getMyEmployees() == null) {
		supervisorEid = 0;
	    } else {
                UserProfile profile = super.getLoggedInUser();
                supervisorEid = (int)profile.getEmpIdentifier();
	    }
	    return supervisorEid;
	}

	public void setSupervisorEid(int supervisorEid) {
	    this.supervisorEid = supervisorEid;
	}

	public String getType() {
	    return type;
	}

	public void setType(String type) {
	    this.type = type;
	}

	public String getLongTermAdv() {
	    if(longTermAdv == null) {
		longTermAdv = "N";
	    }
	    return longTermAdv;
	}

	public void setLongTermAdv(String longTermAdv) {
	    this.longTermAdv = longTermAdv;
	}

	public String getAdjustmentsOnly() {
	    if(adjustmentsOnly == null) {
		adjustmentsOnly = "N";
	    }
	    return adjustmentsOnly;
	}

	public void setAdjustmentsOnly(String adjustmentsOnly) {
	    this.adjustmentsOnly = adjustmentsOnly;
	}

	public String getUnpaidExpensesOnly() {
	    if(unpaidExpensesOnly == null) {
		unpaidExpensesOnly = "N";
	    }
	    return unpaidExpensesOnly;
	}

	public void setUnpaidExpensesOnly(String unpaidExpensesOnly) {
	    this.unpaidExpensesOnly = unpaidExpensesOnly;
	}

	public String getRept() {
	    return rept;
	}

	public void setRept(String rept) {
	    this.rept = rept;
	}

	public void setServletRequest(HttpServletRequest request) {
	    this.request = request;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
}
