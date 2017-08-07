/**
 * Author SG
 * 
 */

package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.Appointment;
import gov.michigan.dit.timeexpense.model.core.AppointmentListBean;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.core.VAdvanceList;
import gov.michigan.dit.timeexpense.model.core.VTravelReqList;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.TravelReqListBean;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.TravelRequisitionDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Calendar;

public class TravelRequisitionListAction extends AbstractAction {

	private static final long serialVersionUID = 8579246909798585960L;
	private long employeeId = 0;
	private String travelRequisitionListType;
	private TravelRequisitionDSP treqService = null;
	private AppointmentDSP appointmentService = null;
	private gov.michigan.dit.timeexpense.service.SecurityManager securityService = null;
	private EmployeeHeaderBean empInfo;
	private String moduleId;
	private String disableCreateButton;
	private int treqEventId;

	Logger logger = Logger.getLogger(TravelRequisitionListAction.class);

	public String getDisableCreateButton() {
		return disableCreateButton;
	}

	public void setDisableCreateButton(String disableCreateButton) {
		this.disableCreateButton = disableCreateButton;
	}

	@Override
	public void prepare() {
		treqService = new TravelRequisitionDSP(entityManager);
		appointmentService = new AppointmentDSP(entityManager);
		securityService = new gov.michigan.dit.timeexpense.service.SecurityManager(
				entityManager);
	}

	public String viewTravelRequisitionList() {
		logger.info("Action: View Travel Requisition List invoked");
		String result = IConstants.SUCCESS;

		// set moduleId in session with the new value
		if (moduleId != null && !"".equals(moduleId.trim()))
			session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, moduleId);
		
		UserSubject subject = getUserSubject();
		UserProfile profile = getLoggedInUser();
		
		// set employee header information
		setEmpInfo(subject, profile);

		Appointment appt = null;
		// Employee Only access
		if ((getModuleIdFromSession().equals(IConstants.TRAVEL_REQUISITIONS_EMPLOYEE))) {
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
			disableCreateButton = (isEmployeeActive && (returnCode == 2)) ? "false"
					: "true";		
		}
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
		
		if (getModuleIdFromSession().equals(IConstants.TRAVEL_REQUISITIONS_EMPLOYEE)){
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

	public String getTravelRequisitionList() {
		logger.info("Ajax Action: get Travel Requisition List ..");
		UserProfile profile = (UserProfile) session.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
		if (profile != null) {
			String moduleId = (String) session.get(IConstants.LEFT_NAV_CURRENT_MODULE_ID);
			int securityCheck = this.getModuleAccessMode(profile, moduleId);
			if (moduleId.equals(IConstants.TRAVEL_REQUISITIONS_MANAGER)|| moduleId.equals(IConstants.TRAVEL_REQUISITIONS_STATEWIDE)) {//Manager and state wide
				UserSubject subject = (UserSubject) session.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME);
				List<TravelReqListBean> treqByApptList = treqService.getTravelReqsListAppointment(subject.getAppointmentId(),IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED,profile.getUserId(), moduleId);
				treqByApptList = getTreqListOtherAppointments(treqByApptList, profile, subject, moduleId);
				setDeleteFlagExpenseListBean(treqByApptList,securityCheck);
				setJsonResponse(jsonParser.toJson(treqByApptList));
			} else {//employee
				List<VTravelReqList> treqList = treqService.getTravelReqListEmployee((int) profile.getEmpIdentifier(),IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);
				this.setDeleteFlagVExpensesList(treqList,securityCheck);
				super.setJsonResponse(jsonParser.toJson(treqList));
			}
		}
		return IConstants.SUCCESS;
	}
	
	/**
	 * Retrieves requisitions for appointments other than the appointment for 
	 * the chosen User Subject
	 * @param origAdvanceByApptList
	 * @param profile
	 * @param subject
	 * @param moduleId
	 * @return
	 */
	
private List<TravelReqListBean> getTreqListOtherAppointments (List<TravelReqListBean> origTreqByApptList, UserProfile profile, UserSubject subject, String moduleId){
		
	Map<String, Map<String, Object>> userContext = (Map<String, Map<String, Object>>)session.get(IConstants.USER_CONTEXT);
	Map<String, Object> userModuleContext = null;
	if (userContext == null){
		// No search criteria exists at the moment
		return origTreqByApptList;
	} else {
		userModuleContext = userContext.get(moduleId);
		if (userModuleContext == null) {
			// likely access from MyEmployees, after another Search Action
			return origTreqByApptList;
		}
	}
	
		String dept = ActionHelper.extractCode((String) userModuleContext.get(IConstants.SE_USER_SELECTED_DEPT));
		String agency = ActionHelper.extractCode((String) userModuleContext.get(IConstants.SE_USER_SELECTED_AGENCY));
		String tku = ActionHelper.extractCode((String) userModuleContext.get(IConstants.SE_USER_SELECTED_TKU));

		List<AppointmentListBean> apptList = appointmentService.getAppointmentsByEmpIdAndDeptNoSearchDate (subject.getEmployeeId(), dept, agency, tku, profile.getUserId(), moduleId);
		
		List<TravelReqListBean> treqByApptList = new ArrayList<TravelReqListBean> ();
		if (origTreqByApptList != null && origTreqByApptList.size() > 0){
			for (TravelReqListBean item: origTreqByApptList){
				treqByApptList.add(item);
			}
		}

			for (AppointmentListBean item: apptList){
				if (item.getAppointmentId() != subject.getAppointmentId()){
						List<TravelReqListBean> treqByApptListNew = treqService.getTravelReqsListAppointment(item.getAppointmentId(),IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED, profile.getUserId(), moduleId);
						if (treqByApptListNew != null){
							for (TravelReqListBean itemExpense: treqByApptListNew){
								treqByApptList.add(itemExpense);
							}
						}
				}
		
		}
		return treqByApptList;
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
	 * @param expenseByApptList
	 * @param securityCheck
	 */
	private void setDeleteFlagExpenseListBean(List<TravelReqListBean> expenseByApptList, int securityCheck) {
		if (expenseByApptList != null){
			for (TravelReqListBean item: expenseByApptList){
				if (securityCheck == IConstants.SECURITY_UPDATE_MODULE_ACCESS && (item.getActionCode() == null || StringUtils.isEmpty(item.getActionCode().trim())) )
					item.setDeleteFlag("Y");
				else
					item.setDeleteFlag("N");				
			}		
		}
	}

	/**
	 * Sets the Delete Flag for the VExpensesList , which is used show/hide delete link in Expense List Page
	 * @param expenseList
	 * @param securityCheck
	 */
	private void setDeleteFlagVExpensesList(List<VTravelReqList> expenseList, int securityCheck) {
		for (VTravelReqList item: expenseList){
			if (securityCheck == IConstants.SECURITY_UPDATE_MODULE_ACCESS && (item.getActionCode() == null || StringUtils.isEmpty(item.getActionCode().trim())) )
				item.setDeleteFlag("Y");
			else
				item.setDeleteFlag("N");				
		}		
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
			moduleId = IConstants.TRAVEL_REQUISITIONS_EMPLOYEE;
			session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, moduleId);
		}
		return IConstants.SUCCESS;
	}

	/**
	 * Deletes a travel requisition event
	 * 
	 * @return
	 */
	public String deleteTravelRequisition() {
		logger.info("Action: Delete travel requisition invoked");
		String result = IConstants.SUCCESS;
		treqService.deleteTravelRequisition(treqEventId);
		setEmpInfo((EmployeeHeaderBean) session.get(IConstants.EMP_HEADER_INFO));

		return result;
	}

	public String getExpenseListType() {
		return travelRequisitionListType;
	}

	public void setExpenseListType(String expenseListType) {
		this.travelRequisitionListType = expenseListType;
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

	public TravelRequisitionDSP getTreqService() {
		return treqService;
	}

	public void setTreqService(TravelRequisitionDSP treqService) {
		this.treqService = treqService;
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

	public String getEnableCreateButton() {
		return disableCreateButton;
	}

	public void setEnableCreateButton(String enableCreateButton) {
		this.disableCreateButton = enableCreateButton;
	}

	public int getTreqEventId() {
		return treqEventId;
	}

	public void setTreqEventId(int treqEventId) {
		this.treqEventId = treqEventId;
	}
}
