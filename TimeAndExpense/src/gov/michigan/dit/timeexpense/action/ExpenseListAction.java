/**
 * Author SG
 * 
 */

package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.Appointment;
import gov.michigan.dit.timeexpense.model.core.AppointmentListBean;
import gov.michigan.dit.timeexpense.model.core.TkuoptTaOptions;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.core.VExpensesList;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseListBean;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Calendar;

public class ExpenseListAction extends AbstractAction {

	private long employeeId = 0;
	private int expenseEventId = 0;
	private String expenseListType;
	private ExpenseDSP expenseService = null;
	private AppointmentDSP appointmentService = null;
	private CodingBlockDSP codingBlockService = null;
	private gov.michigan.dit.timeexpense.service.SecurityManager securityService = null;
	private EmployeeHeaderBean empInfo;
	private String moduleId;
	private String disableCreateButton;
	private String cloningAvailable;

	Logger logger = Logger.getLogger(ExpenseListAction.class);

	public String getDisableCreateButton() {
		return disableCreateButton;
	}

	public void setDisableCreateButton(String disableCreateButton) {
		this.disableCreateButton = disableCreateButton;
	}

	@Override
	public void prepare() {
		expenseService = new ExpenseDSP(entityManager);
		appointmentService = new AppointmentDSP(entityManager);
		securityService = new gov.michigan.dit.timeexpense.service.SecurityManager(
				entityManager);
		codingBlockService = new CodingBlockDSP(entityManager);
	}

	public String viewExpenseList() {
		logger.info("Action: View Expense List invoked");
		String result = IConstants.SUCCESS;
		cloningAvailable = "none";

		// set moduleId in session with the new value
		if (moduleId != null && !"".equals(moduleId.trim()))
			session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, moduleId);
		
		UserSubject subject = getUserSubject();
		UserProfile profile = getLoggedInUser();
		
		// set employee header information
		setEmpInfo(subject, profile);

		Appointment appt = null;
		// Employee Only access
		if ((getModuleIdFromSession().equals(IConstants.EXPENSE_EMPLOYEE))) {
			Date today = new Date();
			appt = appointmentService.findAppointment(empInfo.getApptId());			
			if (appt != null) {
				// create new subject and update in session
				subject = new UserSubject(empInfo.getEmpId(), empInfo.getApptId(), null, null, appt.getDepartment(), appt.getAgency(), appt.getTku(), null);
				session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
			}
		}

		int returnCode = 0;
		if (subject != null)
			// Security check
			returnCode = securityService.getModuleAccessMode(getLoggedInUser(), getModuleIdFromSession(),
					subject.getDepartment(), subject.getAgency(), subject.getTku());

		disableCreateButton = (returnCode == 2) ? "false" : "true";

		if ("false".equals(disableCreateButton))
			// No valid appointment for subject
			disableCreateButton = (super.getUserSubject().getAppointmentId() == 0) ? "true"
					: "false";
		
		if ("false".equals(disableCreateButton)){
			// At least 1 employee appointment must be valid currently 
			Date currDate = Calendar.getInstance().getTime();
			List<AppointmentsBean> apptList = appointmentService.getActiveAppointmentsByExpDatesEmployee(currDate, 
				currDate, moduleId, super.getUserSubject().getEmployeeId(), super.getLoggedInUser().getUserId());
			
			disableCreateButton = (apptList.size() == 0) ? "true"
					: "false";		
		}
		
		if ("true".equals(disableCreateButton)){
			// The button is now set to be disabled. Check for current active or Z status
			boolean isEmployeeActive = appointmentService.isEmployeeActive(getUserSubject().getEmployeeId());			
			disableCreateButton = (isEmployeeActive && returnCode == 2) ? "false"
					: "true";		
		}
		
		//determine if TKU allows cloning option.
		TkuoptTaOptions tkuOptions = null;
		
		if (subject != null) {
			tkuOptions = codingBlockService.getCBMetaData(
					subject.getDepartment(), subject.getAgency(),
					subject.getTku());
			cloningAvailable = ("Y".equalsIgnoreCase(tkuOptions
					.getCopyExpFuncInd())) ? IConstants.STRING_INLINE : IConstants.STRING_NONE;
			
			//Now check if the user has expense update rights? If not then make the button invisible.
			if (IConstants.STRING_INLINE.equalsIgnoreCase(cloningAvailable) && disableCreateButton == "true"){
				cloningAvailable = IConstants.STRING_NONE;
			}
		}
		
		ExpenseLineItemDSP expenseLineItemDSP = new ExpenseLineItemDSP(entityManager);
		List<String> cities =  expenseLineItemDSP.findCities(subject.getDepartment(), subject.getAgency());
		session.put(IConstants.CITIES, cities);
		
		return result;
	}
	/*
	 * Set Emp Info header information
	 * 
	 */
	
	private void setEmpInfo(UserSubject subject, UserProfile profile){
		List<EmployeeHeaderBean> empInfoList = null;
		// find header info
		// ZH - Added search date parameter as a fix for defect # 93
		Date searchDate = (Date) session.get(IConstants.APPT_SEARCH_DATE);
		if (searchDate == null)
			searchDate = Calendar.getInstance().getTime();
		
		if (getModuleIdFromSession().equals(IConstants.EXPENSE_EMPLOYEE)){
		empInfoList = appointmentService
				.getEmployeeHeaderInfoByEmpId((int) profile
						.getEmpIdentifier());
		} else {
			// supervisor or SW access
			empInfoList = appointmentService.getEmployeeHeaderInfoByApptId(
					subject.getAppointmentId(), searchDate);
		}
		
		if (!empInfoList.isEmpty()) {
			empInfo = empInfoList.get(0);
		}

		session.put(IConstants.EMP_HEADER_INFO, empInfo);
	}

	public String getExpenseList() {
		logger.info("Ajax Action: get Expense List ..");
		ExpenseListBean expenseListBean=new ExpenseListBean();
		UserProfile profile = (UserProfile) session.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
		if (profile != null) {
			String moduleId = (String) session.get(IConstants.LEFT_NAV_CURRENT_MODULE_ID);
			int securityCheck = this.getModuleAccessMode(profile, moduleId);
			if (moduleId.equals(IConstants.EXPENSE_MANAGER)|| moduleId.equals(IConstants.EXPENSE_STATEWIDE)) {//Manager and state wide
				UserSubject subject = (UserSubject) session.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME);				
				// get expenses for current appointment
				List<ExpenseListBean> expenseByApptList = expenseService
						.getExpensesListAppointment(
								subject.getAppointmentId(),
								IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED,
								profile.getUserId(), moduleId,
								subject.getDepartment(), subject.getAgency(),
								subject.getTku());
				// get expenses for other appointments 
				expenseByApptList = getExpenseListOtherAppointments(expenseByApptList, profile, subject, moduleId);
				this.modifyExpenseItemPriorToDisplayAppointment(expenseByApptList,securityCheck);
			    super.setJsonResponse(jsonParser.toJson(expenseByApptList));
			} else {//employee
				List<VExpensesList> expenseList = expenseService.getExpensesListEmployee((int) profile.getEmpIdentifier(),IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);
				this.modifyExpenseItemPriorToDisplayEmployee(expenseList,securityCheck);
				super.setJsonResponse(jsonParser.toJson(expenseList));
			}
		}
		return IConstants.SUCCESS;
	}
	
	/**
	 * Retrieves expenses for appointments other than the appointment for 
	 * the chosen User Subject
	 * @param origAdvanceByApptList
	 * @param profile
	 * @param subject
	 * @param moduleId
	 * @return
	 */
	
	private List<ExpenseListBean> getExpenseListOtherAppointments (List<ExpenseListBean> origExpenseByApptList, UserProfile profile, UserSubject subject, String moduleId){
		
		Map<String, Map<String, Object>> userContext = (Map<String, Map<String, Object>>)session.get(IConstants.USER_CONTEXT);
		Map<String, Object> userModuleContext = null;
		if (userContext == null){
			// No search criteria exists at the moment
			return origExpenseByApptList;
		} else {
			userModuleContext = userContext.get(moduleId);
			if (userModuleContext == null) {
				// likely access from MyEmployees, after another Search Action
				return origExpenseByApptList;
			}
		}
		
		String dept = ActionHelper.extractCode((String) userModuleContext.get(IConstants.SE_USER_SELECTED_DEPT));
		String agency = ActionHelper.extractCode((String) userModuleContext.get(IConstants.SE_USER_SELECTED_AGENCY));
		String tku = ActionHelper.extractCode((String) userModuleContext.get(IConstants.SE_USER_SELECTED_TKU));

		List<AppointmentListBean> apptList = appointmentService.getAppointmentsByEmpIdAndDeptNoSearchDate (subject.getEmployeeId(), dept, agency, tku, profile.getUserId(), moduleId);
		
		List<ExpenseListBean> expenseByApptList = new ArrayList<ExpenseListBean> ();
		if (origExpenseByApptList != null && origExpenseByApptList.size() > 0){
			for (ExpenseListBean item: origExpenseByApptList){
				expenseByApptList.add(item);
			}
		}

			for (AppointmentListBean item: apptList){
				if (item.getAppointmentId() != subject.getAppointmentId()){
						List<ExpenseListBean> expenseByApptListNew = expenseService.getExpensesListAppointment(item.getAppointmentId(),IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED, profile.getUserId(), moduleId,
								dept, agency, tku
								);
						if (expenseByApptListNew != null){
							for (ExpenseListBean itemExpense: expenseByApptListNew){
								expenseByApptList.add(itemExpense);
							}
						}
				}
		
		}
		return expenseByApptList;
	}


	/**
	 * Used to get the Module Access Code from the Security Service
	 * @param profile
	 * @param moduleId
	 * @return Module Access Code
	 */
	private int getModuleAccessMode(UserProfile profile, String moduleId) {
		empInfo = (EmployeeHeaderBean) session.get(IConstants.EMP_HEADER_INFO);
		if (empInfo != null) {
			Appointment appt = appointmentService.findAppointment(empInfo.getApptId());
			if (profile != null && moduleId != null && appt != null) {
				return securityService.getModuleAccessMode(profile, moduleId, appt.getDepartment(),appt.getAgency(), appt.getTku());	
			}			
		}
		return IConstants.SECURITY_NO_MODULE_ACCESS;
	}
	
	/**
	 * Sets the Delete Flag for the ExpenseListBean , which is used show/hide delete link in Expense List Page
	 * @param expenseList
	 * @param securityCheck
	 */
	private void modifyExpenseItemPriorToDisplayAppointment(List<ExpenseListBean> expenseList, int securityCheck) {
		for (ExpenseListBean item: expenseList){
			if (securityCheck == IConstants.SECURITY_UPDATE_MODULE_ACCESS && (item.getActionCode() == null || StringUtils.isEmpty(item.getActionCode().trim())) )
				item.setDeleteFlag("Y");
			else
				item.setDeleteFlag("N");	
			// ZH - Defect # 309. Blank out if Audit shows as the latest Action Code for the expense
			if (IConstants.AUDIT.equals(item.getActionCode())){
				item.setActionCodeForDisplay(expenseService.getLatestExpenseActionExcludingAudit(expenseService.getExpense(item.getExpmIdentifier())));
			} else {
				item.setActionCodeForDisplay(item.getActionCode());
			}
			// ZH - commented fix for defect # 264
			//setAuditFlag(item);
		}		
	}
	
	private void modifyExpenseItemPriorToDisplayEmployee(List<VExpensesList> expenseList, int securityCheck) {
		for (VExpensesList item: expenseList){
			if (securityCheck == IConstants.SECURITY_UPDATE_MODULE_ACCESS && (item.getActionCode() == null || StringUtils.isEmpty(item.getActionCode().trim())) )
				item.setDeleteFlag("Y");
			else
				item.setDeleteFlag("N");	
			// ZH - Defect # 309. Blank out if Audit shows as the latest Action Code for the expense
			if (IConstants.AUDIT.equals(item.getActionCode())){
				item.setActionCodeForDisplay(expenseService.getLatestExpenseActionExcludingAudit(expenseService.getExpense(item.getExpmIdentifier())));
			} else {
				item.setActionCodeForDisplay(item.getActionCode());
			}
			// ZH - commented fix for defect # 264
			//setAuditFlag(item);
		}		
	}
	/**
	 * It sets the audit for manager  in the ExpenseListBean which shows 'Y' or 'N'
	 * 
	 */
	private void setAuditFlag(ExpenseListBean expense) {
	   		int val=expenseService.getActionCode(expense.getExpmIdentifier(), "AUDT");
	   		if(val==1){
	   			expense.setAudit("Y");
	   		    }
			else
				expense.setAudit("N");		
	}
	/**
	 * It sets the audit for employee in the VExpenseList which shows 'Y' or 'N'
	 * 
	 */
	private void setAuditFlag(VExpensesList expense) {
	   		int val=expenseService.getActionCode(expense.getExpmIdentifier(), "AUDT");
	   		if(val==1){
	   			expense.setAudit("Y");
	   		    }
			else
				expense.setAudit("N");	
	}

	/**
	 * Sets the Delete Flag for the VExpensesList , which is used show/hide delete link in Expense List Page
	 * @param expenseList
	 * @param securityCheck
	 */
	private void setDeleteFlagVExpensesList(List<VExpensesList> expenseList, int securityCheck) {
		for (VExpensesList item: expenseList){
			if (securityCheck == IConstants.SECURITY_UPDATE_MODULE_ACCESS && (item.getActionCode() == null || StringUtils.isEmpty(item.getActionCode().trim())) )
				item.setDeleteFlag("Y");
			else
				item.setDeleteFlag("N");				
		}		
	}


	/**
	 * Deletes an Expense event
	 * 
	 * @return
	 */
	public String deleteExpense() {
		logger.info("Action: Delete Advance List invoked");
		String result = IConstants.SUCCESS;
		expenseService.deleteExpense(expenseEventId);
		setEmpInfo((EmployeeHeaderBean) session.get(IConstants.EMP_HEADER_INFO));

		return result;
	}

	public String getFilteredExpenseList() {
		UserProfile profile = (UserProfile) session.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
		if (profile != null) {
			String moduleId = (String) session.get(IConstants.LEFT_NAV_CURRENT_MODULE_ID);
			int securityCheck = this.getModuleAccessMode(profile, moduleId);
			if (moduleId.equals(IConstants.EXPENSE_MANAGER)	|| moduleId.equals(IConstants.EXPENSE_STATEWIDE)) {//Manager and state wide
				UserSubject subject = (UserSubject) session.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME);	
				List<ExpenseListBean> expenseByApptList = expenseService.getExpensesListAppointment(subject.getAppointmentId(), expenseListType,profile.getUserId(), moduleId, subject.getDepartment(), subject.getAgency(), subject.getTku());
				this.modifyExpenseItemPriorToDisplayAppointment(expenseByApptList, securityCheck);
				setJsonResponse(jsonParser.toJson(expenseByApptList));
			} else {//employee
				List<VExpensesList> expenseList = expenseService.getExpensesListEmployee((int) profile.getEmpIdentifier(),expenseListType);
				modifyExpenseItemPriorToDisplayEmployee(expenseList, securityCheck);
				setJsonResponse(jsonParser.toJson(expenseList));
			}
		}
		return IConstants.SUCCESS;
	}

	public String setUserSubjectInSession() {

		// Set the userSubject into session
		UserProfile profile = (UserProfile) session
				.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
		UserSubject subject = (UserSubject) session
				.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME);
		if (subject == null) {
			Appointment appt = appointmentService.findAppointment(empInfo
					.getApptId());
			subject = new UserSubject();
			subject.setEmployeeId((int) profile.getEmpIdentifier());
			subject.setAppointmentId(appt.getId());
			subject.setDepartment(appt.getDepartment());
			subject.setAgency(appt.getAgency());
			subject.setTku(appt.getTku());
			subject.setAppointmentStart(null);
			subject.setAppointmentEnd(null);
			subject.setPositionId(appt.getPositionId());
			session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		}
		String moduleId = getModuleIdFromSession();
		if (StringUtils.isEmpty(moduleId)) {
			// if(!session.containsKey(IConstants.LEFT_NAV_CURRENT_MODULE_ID)){
			moduleId = IConstants.EXPENSE_EMPLOYEE;
			session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, moduleId);
		}
		return IConstants.SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.michigan.dit.timeexpense.action.BaseAction#prepare()
	 */

	public String getExpenseListType() {
		return expenseListType;
	}

	public void setExpenseListType(String expenseListType) {
		this.expenseListType = expenseListType;
	}

	public AppointmentDSP getAppointmentService() {
		return appointmentService;
	}

	public void setAppointmentService(AppointmentDSP appointmentService) {
		this.appointmentService = appointmentService;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public int getExpenseEventId() {
		return expenseEventId;
	}

	public void setExpenseEventId(int expenseEventId) {
		this.expenseEventId = expenseEventId;
	}

	public ExpenseDSP getExpenseService() {
		return expenseService;
	}

	public void setExpenseService(ExpenseDSP expenseService) {
		this.expenseService = expenseService;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public EmployeeHeaderBean getEmpInfo() {
		return empInfo;
	}

	public void setEmpInfo(EmployeeHeaderBean empInfo) {
		this.empInfo = empInfo;
	}
	
	public String getCloningAvailable() {
		return cloningAvailable;
	}

	public void setCloningAvailable(String cloningAvailable) {
		this.cloningAvailable = cloningAvailable;
	}
}
