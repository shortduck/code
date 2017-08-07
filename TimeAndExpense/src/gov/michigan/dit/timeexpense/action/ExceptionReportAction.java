package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.ErrorDisplayBean;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

	/**
	 * This is the Action class which handles the form submission for the exception 
	 * Report Search and the gets the response back and delegates it to the JSP.
	 * 
	 * @author lyong
	 *
	 */
	@SuppressWarnings("all")
	public class ExceptionReportAction extends AppointmentSearchAction implements ServletRequestAware{
	    private static Logger logger = Logger.getLogger(ExceptionReportAction.class);
		
	    private Date expDateFrom;
	    private Date expDateTo;
	    private Date pmtDateFrom;
	    private Date pmtDateTo;
	    private String severity;
	    private String error_code_from;
	    private String error_code_to;
	    private String myEmployees;
	    private int supervisorEid;
	    private String exportType;	    
		 
		private  HttpServletRequest request;
	    
		@Override
		public String execute() throws Exception {
			// initialize scope to default strings
			
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
			
	        expDateFrom =(getExpDateFrom());
	        expDateTo = (getExpDateTo());
	        pmtDateFrom = (getPmtDateFrom());
	        pmtDateTo = (getPmtDateTo());
	        error_code_from = (getError_code_from());
	        error_code_to = (getError_code_to());
	        supervisorEid = (int)getSupervisorEid();

	 
	                // set variables in request
	                
	                UserProfile profile = super.getLoggedInUser();
	               
	                // set variables in request
	                request.setAttribute("chosenDept", chosenDepartment);
	                request.setAttribute("chosenAgency", chosenAgency);
	                request.setAttribute("chosenTku", chosenTku);
	                request.setAttribute("empId", empId);
	                if(expDateFrom == null){
	                    request.setAttribute("expDateFrom", new Date(0, 0, 1));//, "01/01/1900"));
	                }else{
	                    request.setAttribute("expDateFrom", expDateFrom);
	                }
	                if(expDateTo == null){
	                    request.setAttribute("expDateTo", new Date(2222-1900, 11, 31));
	                }else{
	                    request.setAttribute("expDateTo", expDateTo);
	                }
	                if(pmtDateFrom == null){
	                    request.setAttribute("pmtDateFrom", new Date(0, 0, 1));//, "01/01/1900"));
	                }else{
	                    request.setAttribute("pmtDateFrom", pmtDateFrom);
	                }
	                if(pmtDateTo == null){
	                    request.setAttribute("pmtDateTo", new Date(2222-1900, 11, 31));
	                }else{
	                    request.setAttribute("pmtDateTo", pmtDateTo);                
	                }
	                request.setAttribute("severity", severity);
	                request.setAttribute("error_code_from", error_code_from);
	                request.setAttribute("error_code_to", error_code_to);
	                request.setAttribute("supervisorEid", supervisorEid);
	                request.setAttribute("exportType", exportType );
	              

	                return "success";
		}

		public String getMyEmployees() {
			return myEmployees;
		}

		public void setMyEmployees(String myEmployees) {
			this.myEmployees = myEmployees;
		}

		public int getSupervisorEid() {
			if(getMyEmployees() == null){
				supervisorEid = 0;
			}else {
				UserProfile profile = super.getLoggedInUser();
				supervisorEid = (int)profile.getEmpIdentifier();
			}
			return supervisorEid;
		}

		public void setSupervisorEid(int supervisorEid) {
			this.supervisorEid = supervisorEid;
		}

		@Override
		public void validate() {
			if(IConstants.EMPLOYEE_REPORT_EXCEPTION.equalsIgnoreCase(getModuleIdFromSession())){
				return;
			}
			
			boolean result = true;
			// validate dept, agency, tku if NOT mgr viewing report for his employees
			if(!"on".equalsIgnoreCase(myEmployees)){
				result = validateDepartment() && validateAgency() && validateTku() && validateEmpId();
			}
			
			StringBuilder errorMsg = new StringBuilder("");
			
			// validate dates
			validateExpenseAndPaymentDates(errorMsg);
			
			if(errorMsg.length() > 0){
				addFieldError("errors", errorMsg.toString());
				ErrorDisplayBean errorBean = new ErrorDisplayBean();
				errorBean.setErrorMessage(errorMsg.toString());
				errorBean.setRedirectOption(false);
				setError(errorBean);
				if(logger.isDebugEnabled()) logger.info("Validation failed. ERROR: "+ errorMsg);
				return;
			}
			
			// validate error codes
			validateErrorCodes(errorMsg);
			
			if(errorMsg.length() > 0){
				addFieldError("errors", errorMsg.toString());
				ErrorDisplayBean errorBean = new ErrorDisplayBean();
				errorBean.setErrorMessage(errorMsg.toString());
				errorBean.setRedirectOption(false);
				setError(errorBean);
				if(logger.isDebugEnabled()) logger.info("Validation failed. ERROR: "+ errorMsg);
			}
		}

		private boolean validateExpenseAndPaymentDates(StringBuilder errorMsg) {
			return validateExpenseDates(errorMsg) && validatePaymentDates(errorMsg) && validateDatePresence(errorMsg);
		}

		private boolean validateExpenseDates(StringBuilder errorMsg) {
			if(expDateFrom == null && expDateTo != null){
				errorMsg.append("Please select Expense 'From' date");
				return false;
			}
			if(expDateFrom != null && expDateTo == null){
				errorMsg.append("Please select Expense 'To' date");
				return false;
			}
			if(expDateFrom != null && expDateTo !=null && expDateTo.before(expDateFrom)){
				errorMsg.append("Expense 'To' date cannot be less than 'From' date");
				return false;
			}

			return true;
		}

		private boolean validatePaymentDates(StringBuilder errorMsg) {
			if(pmtDateFrom == null && pmtDateTo != null){
				errorMsg.append("Please select Payment 'From' date");
				return false;
			}
			if(pmtDateFrom != null && pmtDateTo == null){
				errorMsg.append("Please select Payment 'To' date");
				return false;
			}
			if(pmtDateFrom != null && pmtDateTo != null && pmtDateTo.before(pmtDateFrom)){
				errorMsg.append("Payment 'To' date cannot be less than 'From' date");
				return false;
			}

			return true;
		}

		private boolean validateDatePresence(StringBuilder errorMsg) {
			if(expDateFrom == null && expDateTo == null && pmtDateFrom == null && pmtDateTo == null){
				errorMsg.append("Please Enter Payment or Expense Date Range");
				return false;
			}
			return true;
		}

		private void validateErrorCodes(StringBuilder errorBuff) {
			if(error_code_from == null || error_code_from.trim().length()==0){
				errorBuff.append("Please select Beginning Error Code");
			}else if(error_code_to == null || error_code_to.trim().length()==0){
				errorBuff.append("Please select Ending Error Code");
			}else if(!isInteger(error_code_from) || !isInteger(error_code_to)){
				errorBuff.append("Beginning Error Code and Ending Error Code should be numeric");
			}else if(Integer.parseInt(error_code_from) > Integer.parseInt(error_code_to)){
				errorBuff.append("Beginning Error Code can't be greater than Ending Error Code");
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

		public void setServletRequest(HttpServletRequest request) {
		    this.request = request;		    
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
		public String getseverity() {
			return severity;
		}

		public void setseverity(String severity) {
			this.severity = severity;
		}
		
	    public String getError_code_from() {
				return error_code_from;
		}

		public void setError_code_from(String error_code_from) {
				this.error_code_from = error_code_from;
		}
		
		public String getError_code_to() {
			return error_code_to;
		}

		public void setError_code_to(String error_code_to) {
			this.error_code_to = error_code_to;
		}
		
		public String getExportType() {
			return exportType;
		}

		public void setExportType(String exportType) {
			this.exportType = exportType;
		}
	}

