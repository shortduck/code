package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.Agency;
import gov.michigan.dit.timeexpense.model.core.Department;
import gov.michigan.dit.timeexpense.model.core.Tku;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.display.ExpenseTypeDisplayBean;
import gov.michigan.dit.timeexpense.model.display.SecurityScope;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This is the Action class which handles the form submission for the select 
 * employee Search and the gets the response back and delegates it to the JSP.
 * 
 * @author chaudharym
 */

public class SelectEmployeeAction extends AbstractAction{
	
	private static final long serialVersionUID = -47149109652L;
	
	private AppointmentDSP appointmentService;
	private ExpenseLineItemDSP expenseLineItemDSP;
	private CommonDSP commonDSP;
	private String moduleId;
	
	private String chosenDepartment;
	private String chosenAgency;
	private String chosenTku;

	private SecurityScope securityScope;
	private String displayMyEmployees = "none";
	
	private Map<String, Object> userPref;
	private boolean autoSearch;
	
	public SelectEmployeeAction() {
		securityScope = new SecurityScope();
	}

	public void prepare(){
		appointmentService = new AppointmentDSP(entityManager);
		expenseLineItemDSP = new ExpenseLineItemDSP(entityManager);
		commonDSP = new CommonDSP(entityManager);
	}
	
	/**
	 * Default execute method to be called for the action invocation.
	 * It finds departments, agencies and TKUs for a given user and module.
	 * As agencies require a department to relate to, the first found 
	 * department fetched in the departments list is chosen for this purpose.
	 * Similarly, first found agency is used to fetch the related TKUs.
	 */
	public String execute(){
	    
	    // push moduleId to session for further reuse
	    if(moduleId != null && !"".equals(moduleId.trim())){
	    	session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, moduleId);
	    }
	    
	    //get user preferences, if any
	    userPref = findUserPreferences();
	    //to show Default Type Of Report
	    if (session.get("statewideApprovalsTypeOfReportPref") == null) {
	    	session.put("statewideApprovalsTypeOfReportPref", "A");	    	
		}
	    securityScope.setDepartments(getDepartment());
	    chosenDepartment = determineChosenDept();
		
		if(chosenDepartment !=null && !"".equals(chosenDepartment)){
			securityScope.setAgencies(getAgency(chosenDepartment.substring(0,2)));
			chosenAgency = determineChosenAgency();
		}
		
		if(chosenAgency !=null && !"".equals(chosenAgency)){
			securityScope.setTkus(getTKU(chosenDepartment.substring(0,2), chosenAgency.substring(0,2)));
			chosenTku = determineChosenTku();
		}

		// build and set the json response string
		setJsonResponse(buildJsonResponse(true, true, true));
		
		setupMyEmployeeViewState();
		
		return "success";
	}
	
	private Map<String, Object> findUserPreferences() {
	    Map<String, Map<String, Object>> userContext = (Map<String, Map<String, Object>>)session.get(IConstants.USER_CONTEXT);
	    
	    Map<String, Object> userPrefs = null;
	    if(userContext != null) userPrefs = userContext.get(moduleId);
	    
	    return userPrefs;
	}

	private String determineChosenDept() {
		return (userPref != null && userPref.containsKey(IConstants.SE_USER_SELECTED_DEPT))?
			(String)userPref.get(IConstants.SE_USER_SELECTED_DEPT) : securityScope.getFirstDepartmentDisplayValue();
	}

	private String determineChosenAgency() {
		return (userPref != null && userPref.containsKey(IConstants.SE_USER_SELECTED_AGENCY))?
			(String)userPref.get(IConstants.SE_USER_SELECTED_AGENCY) : securityScope.getFirstAgencyDisplayValue();
	}

	private String determineChosenTku() {
		return (userPref != null && userPref.containsKey(IConstants.SE_USER_SELECTED_TKU))?
			(String)userPref.get(IConstants.SE_USER_SELECTED_TKU) : securityScope.getFirstTkuDisplayValue();
	}

	private String determineChosenEmpId() {
		return (userPref != null && userPref.containsKey(IConstants.SE_EMP_ID))?
			(String)userPref.get(IConstants.SE_EMP_ID) : "";
	}
	
	private String determineChosenLastname() {
		return (userPref != null && userPref.containsKey(IConstants.SE_LAST_NAME))?
			(String)userPref.get(IConstants.SE_LAST_NAME) : "";
	}

	private String determineChosenSearchDate() {
		return (userPref != null && userPref.containsKey(IConstants.SE_APPT_SEARCH_DATE))?
				TimeAndExpenseUtil.displayDate((Date)userPref.get(IConstants.SE_APPT_SEARCH_DATE)) : "";
	}
	
	private String determineChosenRequestId() {
		return (userPref != null && userPref.containsKey("requestId"))? (String) userPref.get("requestId") : "";
	}

	private String buildJsonResponse(boolean includeDepts, boolean includeAgencies, boolean includeTkus) {
		StringBuilder buff = new StringBuilder();
		buff.append("{");
		if(includeDepts) buff.append("departments:"+securityScope.getCombinedDepartmentCodeNameWithEmptyElementWithoutAllScopeJson()+",");
		if(includeAgencies) buff.append("agencies:"+securityScope.getCombinedAgencyCodeNameWithEmptyElementWithoutAllScopeJson()+",");
		if(includeTkus) buff.append("tkus:"+securityScope.getCombinedTkuCodeNameWithEmptyElementWithoutAllScopeJson()+",");
		
		// initialize to empty string if any is null
		chosenDepartment = (chosenDepartment == null)? "": chosenDepartment;
		chosenAgency = (chosenAgency == null)? "": chosenAgency;
		chosenTku = (chosenTku == null)? "": chosenTku;
		
		// if user preferences are being used, then enable auto search
		if(userPref != null && userPref.containsKey(IConstants.SE_USER_SELECTED_DEPT)) autoSearch = true;
		
		buff.append("chosenDepartment:\""+chosenDepartment+"\",");
		buff.append("chosenAgency:\""+chosenAgency+"\",");
		buff.append("chosenTku:\""+chosenTku+"\",");
		buff.append("chosenEmpId:\""+determineChosenEmpId()+"\",");
		buff.append("chosenLastname:\""+determineChosenLastname()+"\",");
		buff.append("chosenSearchDate:\""+ determineChosenSearchDate()+"\",");
		buff.append("requestId:\""+ determineChosenRequestId()+"\",");
		buff.append("travelExpense:"+appointmentService.getTravelExpense()+",");
		buff.append("expenseTypesInOutState:"+ appointmentService.getExpenseTypeState()+",");
		buff.append("specificExpense:"
				+ commonDSP.constructExpenseTypesJson(expenseLineItemDSP
						.findAllExpenseTypesWithMileageIndicator()) + ",");
		buff.append("autoSearch:"+autoSearch);
		buff.append("}");
		
		return buff.toString();
	}
	
	/**
	 * Service to find the agencies for a department. The first fetched
	 * agency is set to default in the <code>chosenAgency</code> variable.
	 * 
	 * @return JSON string representation of agencies
	 */
	public String updateAgencies(){
		securityScope.setAgencies(getAgency(chosenDepartment.substring(0, 2)));
		chosenAgency = securityScope.getFirstAgencyDisplayValue();

		setJsonResponse(buildJsonResponse(false, true, false));
		
		return "success";
	}

	/**
	 * Service to find the TKUs for an agency. The first TKU
	 * is set to default in the <code>chosenTku</code> variable.
	 * 
	 * @return JSON string representation of agencies
	 */
	public String updateTkus(){
		securityScope.setTkus(getTKU(chosenDepartment.substring(0,2), chosenAgency.substring(0,2)));
		chosenTku = securityScope.getFirstTkuDisplayValue();
		
		setJsonResponse(buildJsonResponse(false, false, true));
		
		return "success";
	}


	/**
	 * For a given module, finds the departments that the given user has access to. 
	 * Invokes AppointmentDSP.getDepartment(user, moduleID).
	 * 
	 * @param userId
	 * @param moduleId
	 * @return
	 */
	public List<Department> getDepartment(){
		UserProfile user = getUser();
		return appointmentService.getDepartments(user, getModuleIdFromSession());
	}

	/**
	 * For a given module, finds the agencies in the given department that the given 
	 * user has access to. Invokes AppointmentDSP.getAgency(user, moduleId, dept).
	 * 
	 * @param userID
	 * @param moduleId
	 * @param deptNo
	 * @return
	 */
	public List<Agency> getAgency(String dept){
		// if no department present, agencies cannot be found! 
		if("".equals(chosenDepartment)){
			return null;
		}
		
		UserProfile user = getUser();
		return appointmentService.getAgencies(user, getModuleIdFromSession(), dept);
	}
	
	/**
	 * For a given module, finds the TKUs in the given agency and department that the 
	 * given user has access to. Invokes AppointmentDSP.getTKU(user, moduleId, dept, agency).
	 * 
	 * @param userID
	 * @param moduleId
	 * @param deptCode
	 * @param agencyCode
	 * @return
	 */
	public List<Tku> getTKU(String dept, String agency){
		// if no agency present, tku cannot be found! 
		if("".equals(chosenAgency)){
			return null;
		}
		
		UserProfile user = getUser();
		return appointmentService.getTkus(user, getModuleIdFromSession(), dept, agency);
	}

	/**
	 * Setup display component state.
	 */
	public void setupMyEmployeeViewState() {
	    if (IConstants.ADVANCE_MANAGER.equalsIgnoreCase(moduleId)
				|| IConstants.EXPENSE_MANAGER.equalsIgnoreCase(moduleId)
				|| IConstants.NON_ROUTINE_TRAVEL_REQUISITION.equalsIgnoreCase(moduleId)
				|| IConstants.MANAGER_REPORT_NON_ROUTINE_TRAVELER.equalsIgnoreCase(moduleId)
				|| IConstants.MANAGER_REPORT_ROUTINE_TRAVELER.equalsIgnoreCase(moduleId)
				|| IConstants.MANAGER_REPORT_EXCEPTION.equalsIgnoreCase(moduleId)
				|| IConstants.MANAGER_REPORT_RECEIPTS_REQUIRED.equalsIgnoreCase(moduleId)
				|| IConstants.MANAGER_REPORT_TRANSACTION_LEDGER.equalsIgnoreCase(moduleId)) {
			setDisplayMyEmployees("inline");
		}
	}
	
	private UserProfile getUser(){
	    return (UserProfile)session.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
	}
	
	public AppointmentDSP getAppointmentService() {
		return appointmentService;
	}

	public void setAppointmentService(AppointmentDSP appointmentService) {
		this.appointmentService = appointmentService;
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

	public SecurityScope getSecurityScope() {
		return securityScope;
	}

	public void setSecurityScope(SecurityScope securityScope) {
		this.securityScope = securityScope;
	}


	public void setDisplayMyEmployees(String displayMyEmployees) {
		this.displayMyEmployees = displayMyEmployees;
	}

	public String getDisplayMyEmployees() {
		return displayMyEmployees;
	}

}
