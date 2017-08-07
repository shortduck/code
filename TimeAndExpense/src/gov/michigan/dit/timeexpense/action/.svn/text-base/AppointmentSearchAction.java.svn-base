package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.AppointmentListBean;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *	Finds the appointments for the given search criteria.
 *	Appointments can be found on the basis of department, agency
 *	TKU, last name, search date and employee id.
 *	PS: For any type of search criteria department is required.    
 *
 * @author chaudharym
 */
public class AppointmentSearchAction extends BaseAction{
	
	private static final long serialVersionUID = -8381023065799793117L;
	private static Logger logger = Logger.getLogger(AppointmentSearchAction.class);
	
	private String moduleId;
	
	protected String chosenDepartment;
	protected String chosenAgency;
	protected String chosenTku;

	private Date searchDate;
	protected String empId;
	private String lastname;
	private String requestId;

	private AppointmentDSP appointmentService;
	
	static final String DEPT_REQUIRED = "Department required";
	
	public void prepare(){
		appointmentService = new AppointmentDSP(entityManager);
	}
	
	@Override
	public String execute() throws Exception {
		moduleId = getModuleIdFromSession();
		if(chosenDepartment!=null && chosenDepartment.trim().length()>0 ){
		saveUserSelectionInSession(chosenDepartment, chosenAgency, chosenTku);
		}
		
		String deptCode = extractCode(chosenDepartment);
		String agencyCode = extractCode(chosenAgency);
		String tkuCode = extractCode(chosenTku);
		
		// ZH - added search date to session in order to be used in subsequent list pages
		session.put(IConstants.APPT_SEARCH_DATE, searchDate);

		List<AppointmentListBean> appointments ;
		if(empId != null && !"".equals(empId) && empId.length()>0){
			//ZH - pass in agency and tku. Only department was passed to the service before
			appointments = appointmentService.getAppointmentsByEmpIdAndDept(Integer.parseInt(empId), deptCode,  
					agencyCode, tkuCode, getLoggedInUser().getUserId(), getModuleIdFromSession(), searchDate,requestId);
		}else{
			appointments = appointmentService.getAppointmentsByLastNameInDeptAgencyTku(deptCode, agencyCode, tkuCode, lastname,
												getLoggedInUser().getUserId(), getModuleIdFromSession(), searchDate,requestId);
		}
		
		setJsonResponse(jsonParser.toJson(appointments));
		return "success";
	}
	
	protected boolean isEmployeeViewingSelfReport() {
		String moduleId = getModuleIdFromSession();
		
		return IConstants.EMPLOYEE_REPORT_RECEIPTS_REQUIRED.equalsIgnoreCase(moduleId) || 
				IConstants.EMPLOYEE_REPORT_EXCEPTION.equalsIgnoreCase(moduleId) ||
				IConstants.EMPLOYEE_REPORT_ROUTINE_TRAVELER.equalsIgnoreCase(moduleId) ||
				IConstants.EMPLOYEE_REPORT_TRANSACTION_LEDGER.equalsIgnoreCase(moduleId);
	}

	protected void saveUserSelectionInSession(String department, String agency, String tku) {
		Map<String, Map<String, Object>> userContext = (Map<String, Map<String, Object>>)session.get(IConstants.USER_CONTEXT);
		
		// if it does not exist, create it
		if(userContext == null){
			userContext = new HashMap<String, Map<String, Object>>();
			session.put(IConstants.USER_CONTEXT, userContext);
		}
		
		// from global user context find module based prefs
		Map<String, Object> userModuleContext = userContext.get(getModuleIdFromSession());
		// if it does not exist, create it
		if(userModuleContext == null){
			userModuleContext = new HashMap<String, Object>();
			userContext.put(getModuleIdFromSession(), userModuleContext);
		}
		
		userModuleContext.put(IConstants.SE_USER_SELECTED_DEPT, department==null ? "" : department);
		userModuleContext.put(IConstants.SE_USER_SELECTED_AGENCY, agency==null ? "" : agency);
		userModuleContext.put(IConstants.SE_USER_SELECTED_TKU, tku==null ? "" : tku);
		userModuleContext.put(IConstants.SE_EMP_ID, empId==null ? "" : empId);
		userModuleContext.put(IConstants.SE_LAST_NAME, lastname==null ? "" : lastname);
		userModuleContext.put(IConstants.SE_APPT_SEARCH_DATE, searchDate==null ? (new Date()) : searchDate);
		userModuleContext.put("requestId", requestId==null ? "" : requestId);
	}

	/**
	 * Tries to populate the logged in employees appointment info. If successful returns TRUE, else FALSE.
	 */
	protected boolean populateEmployeeInfo() {
		UserProfile user = getLoggedInUser();
		empId = user.getEmpIdentifier() + "";
		
		// find dept, agency & tku
		List<AppointmentsBean> appts = appointmentService.findAppointmentHistory(user.getEmpIdentifier(), new Date());
		
		// If TRUE multiple appts found 
		//TODO[mc]: TRUE multiple moved POST PROD
		//if(appts.size > 1){
		//}
		
		if(appts.size() == 0) return false;
		else{
			AppointmentsBean appt = appts.get(0);
			chosenDepartment = appt.getDepartment();
			chosenAgency = appt.getAgency();
			chosenTku = appt.getTku();
		}
		
		return true;
	}
	
	protected String extractCode(String str) {
		if(str == null) return null;
		String result="";
		
		int index = str.indexOf(" ");
		if(index>0){
			result = str.substring(0, index);
		}
		
		return result;
	}

	@Override
	public void validate() {
		boolean result = validateDepartment() && validateAgency()
			&& validateTku() && validateSearchDate() 
			&& validateEmpId() && validateLastname();
		if (StringUtils.isNotEmpty(requestId) && !StringUtils.isNumeric(requestId)) {
			addFieldError("requestId", "Request Id must be numeric.");
			result = false;
		}
		
		// log failed input validation
		if(logger.isDebugEnabled() && !result)
			logger.info("Validation failed");
	}

	protected boolean validateDepartment() {
		if(requestId==null || requestId.trim().length()==0)
		{
		 if(chosenDepartment == null || "".equals(chosenDepartment)){
			addFieldError("chosenDepartment", DEPT_REQUIRED);
			return false;
		}
		}
		return true;
	}

	protected boolean validateAgency() {
		if(chosenAgency != null && !"".equals(chosenAgency)
				&& ((chosenDepartment == null || "".equals(chosenDepartment)&&(requestId==null || requestId.trim().length()==0)))){
			addFieldError("chosenAgency", DEPT_REQUIRED);
			return false;
		}
		return true;
	}

	protected boolean validateTku() {
		if(chosenTku != null && !"".equals(chosenTku) 
				&& (chosenAgency == null || "".equals(chosenAgency)
				|| (chosenDepartment == null || "".equals(chosenDepartment)&&(requestId==null || requestId.trim().length()==0)))){
			addFieldError("chosenTku", DEPT_REQUIRED);
			return false;
		}
		return true;
	}

	protected boolean validateEmpId() {
		String errMsg = "";
		
		if(empId == null || "".equals(empId)){
			return true;
		}
		
		try{
			Integer.parseInt(empId);
		}catch(Exception ex){
			errMsg = "Emp Id must be numeric";
		}
		if(requestId==null || requestId.trim().length()==0)
		{
			
		}else if(chosenDepartment == null || "".equals(chosenDepartment))
			errMsg = DEPT_REQUIRED;
		
		if(!"".equals(errMsg)){
			addFieldError("empId", errMsg);
			return false;
		}
		
		return true;
	}

	protected boolean validateSearchDate() {
		if(searchDate != null && ((chosenDepartment == null || "".equals(chosenDepartment) && (requestId==null || requestId.trim().length()==0)))){
			addFieldError("searchDate", DEPT_REQUIRED);
			return false;
		}
		return true;
	}

	protected boolean validateLastname() {
		if(lastname != null && !"".equals(lastname) 
				&& ((chosenDepartment == null || "".equals(chosenDepartment)&& (requestId==null || requestId.trim().length()==0)))){
			addFieldError("searchDate", DEPT_REQUIRED);
			return false;
		}
		return true;
	}	
	
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getChosenDepartment() {
		return chosenDepartment;
	}

	public void setChosenDepartment(String chosenDepartment) {
		this.chosenDepartment = chosenDepartment;
	}

	public String getChosenAgency() {
		return chosenAgency;
	}

	public void setChosenAgency(String chosenAgency) {
		this.chosenAgency = chosenAgency;
	}

	public String getChosenTku() {
		return chosenTku;
	}

	public void setChosenTku(String chosenTku) {
		this.chosenTku = chosenTku;
	}

	public Date getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
		if(this.empId != null) this.empId = this.empId.trim();
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
		if(this.lastname != null) this.lastname = this.lastname.trim();
	}

	public AppointmentDSP getAppointment(){
		return appointmentService;
	}
	public void setAppointment(AppointmentDSP service){
		appointmentService = service;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
