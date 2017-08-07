/**
 * This class includes all actions for the Advance list page
 * 
 * ZH - 03/20/2009
 * 
 * Note: UI field formatting only done for now
 */

package gov.michigan.dit.timeexpense.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Calendar;

import gov.michigan.dit.timeexpense.model.core.Appointment;
import gov.michigan.dit.timeexpense.model.core.AppointmentListBean;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.core.VAdvanceList;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;
import gov.michigan.dit.timeexpense.service.SecurityManager;

public class AdvanceListAction extends AbstractAction {

	private int advanceEventId;
	private AdvanceDSP advanceService;
	private AppointmentDSP apptService;
	private SecurityManager securityService;
	// used for emp info header
	private EmployeeHeaderBean empInfo;
	private CommonDSP commonService;
	private String eligibleFlag = "true";
	private String moduleId = "";
	private String advanceListType = "";

	Logger logger = Logger.getLogger(AdvanceListAction.class);

	/**
	 * Declares service instances and associated DAOs.
	 */
	public AdvanceListAction() {
	}

	/**
	 * Associates entity managers for each DAO needed
	 */

	public void prepare() {

		advanceService = new AdvanceDSP(entityManager);
		apptService = new AppointmentDSP(entityManager);
		commonService = new CommonDSP(entityManager);
		securityService = new SecurityManager(entityManager);
	}

	/**
	 * Responsible initial load of the transaction listing page. This method
	 * also retrieves the emp header info and stores in session for subsequent
	 * use
	 * 
	 * @return
	 */
	public String viewAdvanceList() {
		// session.put("moduleId", null);
		String result = IConstants.SUCCESS;
		// determine access mode

		if (StringUtils.isEmpty(moduleId)) {
			// this more than likely is manager or SW access
			moduleId = super.getModuleIdFromSession();
		} else{
			// employee access
			session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, moduleId);
		}
		
		UserProfile profile = (UserProfile) session
				.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
		UserSubject userSubject = super.getUserSubject();
		// find out subject employee
		List<EmployeeHeaderBean> empInfoList = null;
		if (userSubject != null && !(moduleId.equals(IConstants.ADVANCE_EMPLOYEE))) {
			// Supervisor or SW access
			Date searchDate = (Date) session.get(IConstants.APPT_SEARCH_DATE);
			if (searchDate == null)
				searchDate = Calendar.getInstance().getTime();
			empInfoList = apptService
					.getEmployeeHeaderInfoByApptId(userSubject
							.getAppointmentId(), searchDate);
			if (empInfoList != null && !empInfoList.isEmpty())
			empInfo = empInfoList.get(0);
		} else {
			// employee access
			empInfoList = apptService
					.getEmployeeHeaderInfoByEmpId((int) super.getLoggedInUser().getEmpIdentifier());
			empInfo = empInfoList.get(0);
			Appointment appt = apptService.findAppointment(empInfo.getApptId());
			userSubject = new UserSubject(empInfo.getEmpId(), empInfo.getApptId(), null, null, appt.getDepartment(), appt.getAgency(), appt.getTku(), null);
			session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, userSubject);
		}

		session.put(IConstants.EMP_HEADER_INFO, empInfo);

		// set eligibity to enable the create button
		if (empInfo != null)  
			eligibleFlag = this.getNewAdvanceEligibility(profile, userSubject);

		return result;
	}

	/**
	 * Logic to determine eligibility flag
	 * 
	 * @param profile
	 * @return
	 */

	private String getNewAdvanceEligibility(UserProfile profile, UserSubject subject) {
		// finding an appointment identifier to get the eligibility flag
		Appointment appt = apptService.findAppointment(empInfo.getApptId());
		boolean newAdvanceEligibleProfile = false;

		// perform checks to enable "Create New Button"
		int securityCheck = securityService
				.getModuleAccessMode(profile, moduleId, appt.getDepartment(),
						appt.getAgency(), appt.getTku());
		if (securityCheck == IConstants.SECURITY_UPDATE_MODULE_ACCESS) {
			// does have update access. now check expense profile
			newAdvanceEligibleProfile = advanceService
					.getAdvanceEligibility(subject.getAppointmentId());
			if (newAdvanceEligibleProfile)
				// false here evaluates to disable=false on the UI
				eligibleFlag = "false";
		}
		
		if ("false".equals(eligibleFlag)){
			Date currDate = Calendar.getInstance().getTime();
			List<AppointmentsBean> apptList = apptService.getActiveAppointmentsByExpDatesEmployee(currDate, 
				currDate, moduleId, super.getUserSubject().getEmployeeId(), super.getLoggedInUser().getUserId());
		
		if (apptList.size () == 0)
			eligibleFlag = "true";
		}
		
		if ("true".equals(eligibleFlag)){
			// The button is now set to be disabled. Check for current active or Z status
			boolean isEmployeeActive = apptService.isEmployeeActive(getUserSubject().getEmployeeId());			
			eligibleFlag = (isEmployeeActive && newAdvanceEligibleProfile) ? "false"
					: "true";		
		}
		
		return eligibleFlag;
	}

	/**
	 * Ajax call to populate the grid
	 * 
	 * @return
	 */
	public String getAdvanceList() {
		logger.info("Ajax Action: Get Advance List invoked");
		List<VAdvanceList> advanceList = null;
		String result = IConstants.SUCCESS;

		// set advanceListType to "BOTH", if reportType is not passed from
		// advanceListPage.jsp
		if (StringUtils.isEmpty(advanceListType))
			advanceListType = IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED;

		// store name, processLevel, deptCode, deptName, empId and apptId in
		// empInfo
		empInfo = (EmployeeHeaderBean) session.get(IConstants.EMP_HEADER_INFO);

		moduleId = (String) session.get(IConstants.LEFT_NAV_CURRENT_MODULE_ID);

		advanceList = this.getAdvanceList(moduleId, empInfo);
		this.setupDisplay(advanceList);
		String jsonString = jsonParser.toJson(advanceList);
		logger.info(jsonString);
		super.setJsonResponse(jsonString);
		return result;
	}

	/**
	 * Engages the appropriate DSP method to fecth the list of advances whether
	 * employee or Manager/SW access
	 * 
	 * @param moduleId
	 * @param empInfo
	 * @return
	 */

	private List<VAdvanceList> getAdvanceList(String moduleId,
			EmployeeHeaderBean empInfo) {
		List<VAdvanceList> advanceList = null;
		// if module is employee(ADVW001) call method
		if (moduleId.equals(IConstants.ADVANCE_EMPLOYEE)) {
			advanceList = advanceService.getAdvanceListEmployee(empInfo
					.getEmpId(), advanceListType);
		}

		// if module is manager(ADVW002) or statewide (ADVW003) call method
		else if ((moduleId.equals(IConstants.ADVANCE_MANAGER) || (moduleId
				.equals(IConstants.ADVANCE_STATEWIDE)))) {
			UserProfile profile = (UserProfile) session
					.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
			String userId = profile.getUserId();
			UserSubject employeeSubject = super.getUserSubject();
			// get advances for current appointment
			advanceList = advanceService.getAdvanceListAppointment(
					employeeSubject.getAppointmentId(), advanceListType,
					userId, moduleId);
			// get advances for other appointments
			advanceList = getAdvanceListOtherAppointments(advanceList, profile, employeeSubject, moduleId);
		}
		return advanceList;

	}

	/**
	 * Retrieves advances for appointments other than the appointment for 
	 * the chosen User Subject
	 * @param origAdvanceByApptList
	 * @param profile
	 * @param subject
	 * @param moduleId
	 * @return
	 */
	
private List<VAdvanceList> getAdvanceListOtherAppointments (List<VAdvanceList> origAdvanceByApptList, UserProfile profile, UserSubject subject, String moduleId){
		
		Map<String, Map<String, Object>> userContext = (Map<String, Map<String, Object>>)session.get(IConstants.USER_CONTEXT);
		Map<String, Object> userModuleContext = null;
		if (userContext == null){
			// No search criteria exists at the moment
			return origAdvanceByApptList;
		} else {
			userModuleContext = userContext.get(moduleId);
			if (userModuleContext == null) {
				// likely access from MyEmployees, after another Search Action
				return origAdvanceByApptList;
			}
		}
		
		String dept = ActionHelper.extractCode((String) userModuleContext.get(IConstants.SE_USER_SELECTED_DEPT));
		String agency = ActionHelper.extractCode((String) userModuleContext.get(IConstants.SE_USER_SELECTED_AGENCY));
		String tku = ActionHelper.extractCode((String) userModuleContext.get(IConstants.SE_USER_SELECTED_TKU));

		List<AppointmentListBean> apptList = apptService.getAppointmentsByEmpIdAndDeptNoSearchDate (subject.getEmployeeId(), dept, agency, tku, profile.getUserId(), moduleId);
		
		List<VAdvanceList> advanceByApptList = new ArrayList<VAdvanceList> ();
		if (origAdvanceByApptList != null && origAdvanceByApptList.size() > 0){
			for (VAdvanceList item: origAdvanceByApptList){
				advanceByApptList.add(item);
			}
		}

			for (AppointmentListBean item: apptList){
				if (item.getAppointmentId() != subject.getAppointmentId()){
						List<VAdvanceList> advanceByApptListNew = advanceService.getAdvanceListAppointment(item.getAppointmentId(),IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED, profile.getUserId(), moduleId);
						if (advanceByApptListNew != null){
							for (VAdvanceList itemExpense: advanceByApptListNew){
								advanceByApptList.add(itemExpense);
							}
						}
				}
		
		}
		return advanceByApptList;
	}

	/**
	 * prepares list for display by adding trailing zeros to the amount fields
	 * @param advanceList
	 */
	private void setupDisplay (List<VAdvanceList> advanceList){
		Appointment appt = apptService.findAppointment(empInfo.getApptId());
		UserProfile profile = (UserProfile) session
		.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
		int securityCheck = securityService
				.getModuleAccessMode(profile, moduleId, appt.getDepartment(),
						appt.getAgency(), appt.getTku());
		for (VAdvanceList item: advanceList){
			// setup amount fields to 2 digits for display
			item.setAmountOutStandingForDisplay(item.getAmountOutStanding());
			item.setDollarAmountForDisplay(item.getDollarAmount());
			// perform security checks to see if user may delete advances
			if (securityCheck == IConstants.SECURITY_UPDATE_MODULE_ACCESS && StringUtils.isEmpty(item.getActionCode().trim()))
				item.setDeleteFlag("Y");
			else
				item.setDeleteFlag("N");				
		}
		
	}

	/**
	 * Deletes an advance event
	 * 
	 * @return
	 */
	public String deleteAdvance() {
		logger.info("Action: Delete Advance List invoked");
		String result = IConstants.SUCCESS;
		advanceService.deleteAdvanceEvent(advanceEventId);
		setEmpInfo((EmployeeHeaderBean) session.get(IConstants.EMP_HEADER_INFO));
		// session.get(IConstants.EMP_HEADER_INFO));
		return result;
	}

	public int getAdvanceEventId() {
		return advanceEventId;
	}

	public void setAdvanceEventId(int advanceEventId) {
		this.advanceEventId = advanceEventId;
	}

	public AdvanceDSP getAdvanceService() {
		return advanceService;
	}

	public void setAdvanceService(AdvanceDSP advanceService) {
		this.advanceService = advanceService;
	}

	public void setEmpInfo(EmployeeHeaderBean empInfo) {
		this.empInfo = empInfo;
	}

	public EmployeeHeaderBean getEmpInfo() {
		return empInfo;
	}

	public void setEligibleFlag(String eligibleFlag) {
		this.eligibleFlag = eligibleFlag;
	}

	public String getEligibleFlag() {
		return eligibleFlag;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public void setCommonService(CommonDSP commonService) {
		this.commonService = commonService;
	}

	public CommonDSP getCommonService() {
		return commonService;
	}

	public String getAdvanceListType() {
		return advanceListType;
	}

	public void setAdvanceListType(String advanceListType) {
		this.advanceListType = advanceListType;
	}

}
