/**
 * 
 */
package gov.michigan.dit.timeexpense.action;

import freemarker.template.SimpleDate;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.display.ErrorDisplayBean;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * This is the Action class which handles the form submission for the routine traveler 
 * Search and the gets the response back and delegates it to the JSP.
 * 
 * @author chiduras
 *
 */
@SuppressWarnings("all")
public class NonRoutineTravelerReport extends AppointmentSearchAction implements ServletRequestAware{
    private static Logger logger = Logger.getLogger(RoutineTravelerReport.class);
	
    private Date nonTravelDateFrom;
    private Date nonTravelDateTo;
    private String myEmployees;
    private int SuperEmpId;
    private String save ;
    private String submitted ;
    private String approved;
    private String proc;
    private String travelSelection;
    

    private int loggedInUserEmployeeId;

   
    
    private  HttpServletRequest request;
    
	@Override
	public String execute() throws Exception {
		// If employee wishes to see his expense report, then dept, agency, tku & empId is not asked. So get it.
		if(isEmployeeViewingSelfReport()){
			if(!populateEmployeeInfo()) return "failure";
		}
		// If manager viewing report for his employees
		else if("on".equalsIgnoreCase(myEmployees)){
			loggedInUserEmployeeId = (int)getLoggedInUser().getEmpIdentifier();

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
        
		int employeeId = 0;
        if ((empId != null)&& (empId.length() > 0)) {               	  
        	employeeId =  Integer.parseInt(getEmpId());
        }

        // set variables in request
        UserProfile profile = super.getLoggedInUser();
        loggedInUserEmployeeId = (int)profile.getEmpIdentifier();
       
        if ((myEmployees != null) && (myEmployees.equalsIgnoreCase("on"))){
            request.setAttribute("SuperEmpId", loggedInUserEmployeeId);
          }
        else {
            request.setAttribute("SuperEmpId", 0);
        }                
            request.setAttribute("empId", employeeId);
                            
        request.setAttribute("chosenDept", chosenDepartment);
        request.setAttribute("chosenAgency", chosenAgency);
        request.setAttribute("chosenTku", chosenTku);
        request.setAttribute("nonTravelDateFrom", nonTravelDateFrom);
        request.setAttribute("nonTravelDateTo", nonTravelDateTo);
        request.setAttribute("save", save);
        request.setAttribute("submitted", submitted);
        request.setAttribute("approved", approved);
        request.setAttribute("proc", proc);
        request.setAttribute("travelSelection",travelSelection);
      
        
        return IConstants.SUCCESS;
	}

	@Override
	public void validate() {
		boolean result = true;
		// validate dept, agency, tku if NOT mgr viewing report for his employees
		if(!"on".equalsIgnoreCase(myEmployees)){
			result = validateDepartment() && validateAgency() && validateTku() && validateEmpId();
		}
		
		StringBuilder errorMsg = new StringBuilder("");
		
		// validate dates
		validateExpenseDates(errorMsg);

		if(errorMsg.length() > 0){
			addFieldError("errors", errorMsg.toString());
			ErrorDisplayBean errorBean = new ErrorDisplayBean();
			errorBean.setErrorMessage(errorMsg.toString());
			errorBean.setRedirectOption(false);
			setError(errorBean);
			if(logger.isDebugEnabled()) logger.info("Validation failed. ERROR: "+ errorMsg);
			return;
		}
		
	}

	private boolean validateExpenseDates(StringBuilder errorMsg) {
		if(nonTravelDateFrom == null && nonTravelDateTo == null){
			errorMsg.append("Please select non travel date range");
			return false;
		}
		if(nonTravelDateFrom == null && nonTravelDateTo != null){
			errorMsg.append("Please select Expense 'From' date");
			return false;
		}
		if(nonTravelDateFrom != null && nonTravelDateTo == null){
			errorMsg.append("Please select Expense 'To' date");
			return false;
		}
		if(nonTravelDateFrom != null && nonTravelDateTo !=null && nonTravelDateTo.before(nonTravelDateFrom)){
			errorMsg.append("Expense 'To' date cannot be less than 'From' date");
			return false;
		}

		return true;
	}	
	
	
	
	public Date getNonTravelDateFrom() {
		return nonTravelDateFrom;
	}

	public void setNonTravelDateFrom(Date nonTravelDateFrom) {
		this.nonTravelDateFrom = nonTravelDateFrom;
	}

	public Date getNonTravelDateTo() {
		return nonTravelDateTo;
	}

	public void setNonTravelDateTo(Date nonTravelDateTo) {
		this.nonTravelDateTo = nonTravelDateTo;
	}

	public int getLoggedInUserEmployeeId() {
	    return loggedInUserEmployeeId;
	}

	public void setLoggedInUserEmployeeId(int loggedInUserEmployeeId) {
	    this.loggedInUserEmployeeId = loggedInUserEmployeeId;
	}

	public void setServletRequest(HttpServletRequest request) {
	    this.request = request;
	}

	public String getMyEmployees() {
	    return myEmployees;
	}

	public void setMyEmployees(String myEmployees) {
	    this.myEmployees = myEmployees;
	}
    public int getSuperEmpId() {
        return SuperEmpId;
    }

    public void setSuperEmpId(int superEmpId) {
        SuperEmpId = superEmpId;
    }
    
	public String getSave() {
		return save;
	}

	public void setSave(String save) {
		this.save = save;
	}

	public String getSubmitted() {
		return submitted;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getProc() {
		return proc;
	}

	public void setProc(String proc) {
		this.proc = proc;
	}

	public String getTravelSelection() {
		return travelSelection;
	}

	public void setTravelSelection(String travelSelection) {
		this.travelSelection = travelSelection;
	}

	
}
