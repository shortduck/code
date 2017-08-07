package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.StateAuthCodes;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetails;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.TravelReqOutOfState;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.ComponentViewState;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.ErrorDisplayBean;
import gov.michigan.dit.timeexpense.model.display.TripIdView;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.service.TravelRequisitionDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TravelRequisitionViewUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Action to cater to requests for creating a new expense, viewing an
 * existing expense,  and modifying an existing expense.
 * 
 * It also provides functionality to view different revisions of the
 * given expense.
 * 
 */
public class TravelRequisitionIdAction extends AbstractAction {
	private static final long serialVersionUID = -7153945847561208325L;

	private int treqMasterId;
	private String errorsJson;

	private TravelReqMasters treqMaster;
	private TravelReqDetails treqDetails;
	private EmployeeHeaderBean empInfo;
	private TravelRequisitionViewUtil viewUtil;
	private TripIdView view;

	public List<StateAuthCodes> authCodes;
	private List<Integer> selectedAuthCodes;
	
	private TravelRequisitionDSP treqService;
	private AppointmentDSP appointmentService;
	private AdvanceDSP advanceService;
	private CommonDSP commonService;
	private CodingBlockDSP codingBlockService;
	private SecurityManager securityService;
	private SecurityManager security;
	private String displaySaveButton;
	private String displaySubmitButton;
	private String displayApproveButton = "none";
	private String displayApproveWithCommentsButton = "none";
	private String displayApproveNextButton = "none";
	private String displayApproveSkipButton = "none";
	private String displayRejectButton = "none";
	private String displayCreateExpenseButton = "none";
	private boolean disableSaveButton;
	private boolean disableSubmitButton;
	private boolean disableApproveButton;
	private boolean disableApproveWithCommentsButton;
	private boolean disableApproveNextButton;
	private boolean disableApproveSkipButton;
	private boolean disableRejectButton;
	private String displayAdvance = "";
	
	
	
	private String focusTab;
	
	public TravelRequisitionIdAction() {
		view = new TripIdView();
	}

	public void prepare(){
				
		treqService = new TravelRequisitionDSP(entityManager);
		appointmentService = new AppointmentDSP(entityManager);
		security = new SecurityManager(entityManager);
		commonService = new CommonDSP(entityManager);
		securityService = new SecurityManager(entityManager);
		advanceService = new AdvanceDSP(entityManager);
		codingBlockService = new CodingBlockDSP(entityManager);
		
		viewUtil = new TravelRequisitionViewUtil();
		viewUtil.setJsonParser(jsonParser);
		viewUtil.setAppointmentService(appointmentService);
		viewUtil.setCodingBlockService(codingBlockService);
	}
	
	/**
	 * Prepares the data for initial view state. 
	 */
	public String execute() throws Exception {
		authCodes = treqService.getAuthorizationCodes();

		Integer treqMasterId = (Integer)session.get("RequestedTravelRequisitionId");
		Integer treqEventId = (Integer)session.get("RequestedTravelRequisitionEventId");

		// if explicit expense master Id provided always fetch it
		if(treqMasterId != null){
			treqMaster = treqService.getTravelRequisition(treqMasterId);
			treqDetails = treqMaster.getTravelReqDetailsCollection().get(0);
			session.put(IConstants.TRAVEL_REQUISITION_SESSION_DATA, treqMaster);
			
			// remove 'treqMasterId' as soon as travel requisition state in session is established
			synchronized (session) {
				session.remove("treqMasterId");
			}
		}else if (treqEventId != null){
			int maxRevision = treqService.getMaxRevisionNo(treqEventId);
			treqMaster = treqService.getTravelRequisitionByEventId(treqEventId, maxRevision);
			treqDetails = treqMaster.getTravelReqDetailsCollection().get(0);
			session.put(IConstants.TRAVEL_REQUISITION_SESSION_DATA, treqMaster);
		} else{
			treqMaster = (TravelReqMasters)session.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);
			treqMaster = entityManager.merge(treqMaster);
		}
		
		//populate selected auth code list
		if(treqMaster != null && treqMaster.getTravelReqOutOfStateCollection() != null){
			selectedAuthCodes = new ArrayList<Integer>();
			for(TravelReqOutOfState oost : treqMaster.getTravelReqOutOfStateCollection()){
				selectedAuthCodes.add(oost.getStacIdentifier());
			}
		}
		
		// get employee information from session
		setEmpInfo((EmployeeHeaderBean)session.get(IConstants.EMP_HEADER_INFO));
		// ZH - Commented misleading error
		/*if (getEmpInfo() == null) {
			// Possibly inactive employee
			setupError(
					"",
					"The employee is in a \"No Pay\" status. Please update employee status in HRMN in order to approve."					
					);
			return IConstants.FAILURE;
		}*/
		if (treqMaster != null && treqMaster.getTreqeIdentifier().getApptIdentifier() != null
				&& getEmpInfo() != null 
				&& treqMaster.getTreqeIdentifier().getApptIdentifier() !=
			getEmpInfo().getApptId()){
			// change header info in case the expense was created for an old appointment
			List<EmployeeHeaderBean> empInfoList = appointmentService.
									getEmployeeHeaderInfoByApptId(treqMaster.getTreqeIdentifier().getApptIdentifier(), 
											treqMaster.getTreqDateFrom());
			if (!empInfoList.isEmpty()){
				setEmpInfo(empInfoList.get(0));
			}
		}
		
		// whenever an expense is shown for the first time, reset information
		// stored in session to help during one expense tab run. These include:
		// 1) Resetting the MODIFY flag
		// 2) Resetting total advance outstanding amount
		synchronized (session){
			session.remove(IConstants.MODIFY_BUTTON_STATE_SESSION);
			session.remove(IConstants.EXPENSE_TOTAL_OUTSTANDING_ADVANCE_AMOUNT);
		}
		
		// set focusable tab to let the view render the correct tab
		if(session.containsKey("FOCUS_TAB") && session.get("FOCUS_TAB") != null && !"".equals((String)session.get("FOCUS_TAB"))){
			focusTab = (String)session.get("FOCUS_TAB");
			synchronized (session) {
				session.remove("FOCUS_TAB");
			} 
		}
		
		// update with correct UserSubject, if employee viewing his expenses.
		if(treqMaster != null && IConstants.EXPENSE_EMPLOYEE.equals(getModuleIdFromSession())){
			// update session with correct UserSubject according to the chosen expense dates.
			updateUserSubjectWithCorrectAppointmentInfoForOpenedExpense(treqMaster);
		}

		// push updated expense into session
		session.put(IConstants.TRAVEL_REQUISITION_SESSION_DATA, treqMaster);
		
		return IConstants.SUCCESS;
	}
	
	private void setupError(String errorCode, String errorMsg) {
		if(logger.isInfoEnabled()) logger.error(errorCode + " - " + errorMsg);
	
		error = new ErrorDisplayBean();
		error.setErrorCode(errorCode);
		error.setErrorMessage(errorMsg);
		error.setRedirectOption(false);
		error.setPreviousOption(true);
	}

	private void updateUserSubjectWithCorrectAppointmentInfoForOpenedExpense(TravelReqMasters expense) {
		// Update the UserSubject in session, according to the referred expense.
		List<AppointmentsBean> appts = treqService.getActiveAppointments(expense.getTreqDateFrom(),expense.getTreqDateTo(), 
											getLoggedInUser().getUserId(), getModuleIdFromSession(), getUserSubject().getEmployeeId());

		if(appts.size() == 1){
			AppointmentsBean appt = appts.get(0);
			
			UserSubject subject = getUserSubject();
			subject.setEmployeeId((int)appt.getEmp_identifier());
			subject.setAppointmentId((int)appt.getAppt_identifier());
			subject.setAppointmentStart(appt.getStart_date());
			subject.setAppointmentEnd(appt.getEnd_date());
			subject.setPositionId(appt.getPosition_id());
			subject.setDepartment(appt.getDepartment());
			subject.setAgency(appt.getAgency());
			subject.setTku(appt.getTku());
			subject.setSingleAppointmentChosen(false);
			
			if(appt.getDepartureDate() != null) subject.setAppointmentEnd(appt.getDepartureDate());
			
			// push correct appt dates in subject
			setupApptDates(subject, appt.getAppt_identifier());
		}
	}

	private void setupApptDates(UserSubject subject, long appointmentId) {
		AppointmentsBean apptBean = appointmentService.findActiveAppointmentDateSpan(appointmentId);
		
		if(apptBean.getDepartureDate() != null) subject.setAppointmentEnd(apptBean.getDepartureDate());
		else if(apptBean.getEnd_date() != null) subject.setAppointmentEnd(apptBean.getEnd_date());
		
		if(apptBean.getAppointment_date() != null) subject.setAppointmentDate(apptBean.getAppointment_date());
	}
	
	public String create() throws Exception{
		// remove expense from session
		synchronized (session) {
			session.remove(IConstants.EXPENSE_SESSION_DATA);
		}
		
		// invoke execute as normal
		return execute();
	}

	/**
	 * Finds the next revision of the currently displayed ExpenseMasters.
	 * 
	 * @throws Exception
	 */
	
	
	public String getNextRevision() throws Exception{
		TravelReqMasters currTreqMaster = (TravelReqMasters)session.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);

		// set expense master Id to current. If next not found then we'll show
		// the current one.
		if(currTreqMaster != null) treqMasterId = currTreqMaster.getTreqmIdentifier();
		
		TravelReqMasters nextTreqMaster = null;
		if(currTreqMaster != null){
			nextTreqMaster = treqService.getTravelRequisitionByEventId(
					currTreqMaster.getTreqeIdentifier().getTreqeIdentifier(), 
					currTreqMaster.getRevisionNumber() + 1);
		}
		
		if(nextTreqMaster != null)
			treqMasterId = nextTreqMaster.getTreqmIdentifier();
		
		// set expense to null, to let view setup function skip it
		treqMaster = null;		
		return IConstants.SUCCESS;
	}

	/**
	 * Finds the previous revision of the currently displayed ExpenseMasters.
	 * 
	 * @throws Exception
	 */
	
	
	public String getPreviousRevision()  throws Exception{
		TravelReqMasters currTreqMaster = (TravelReqMasters)session.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);

		// set expense master Id to current. If previous not found then we'll show
		// the current one.
		if(currTreqMaster != null) treqMasterId = currTreqMaster.getTreqmIdentifier();
		
		TravelReqMasters previousTreqMaster = null;

		if(currTreqMaster !=  null){
			previousTreqMaster = treqService.getTravelRequisitionByEventId(
					currTreqMaster.getTreqeIdentifier().getTreqeIdentifier(), 
					currTreqMaster.getRevisionNumber() - 1);
		}
		
		if(previousTreqMaster != null){
			treqMasterId = previousTreqMaster.getTreqmIdentifier();
		}
		
		// set expense to null, to let view setup function skip it
		treqMaster = null;
		
		return IConstants.SUCCESS;
	}
	
	/**
	 * Prepares view state.
	 */
	public void setupDisplay() {
		errorsJson = viewUtil.prepareExpenseErrorsJson(treqMaster);
		setupTripIdView();
		
		if (treqMaster == null){
			treqMaster = new TravelReqMasters();
			treqMaster.setTreqDateRequest(Calendar.getInstance().getTime());
		}
	}

	private void setupTripIdView() {
		String status = (treqMaster==null) ? null : treqMaster.getStatus();
		
		boolean writeAccess = security.checkModuleAccess(getLoggedInUser(), 
									getModuleIdFromSession(), getUserSubject().getDepartment(),
									getUserSubject().getAgency(), getUserSubject().getTku());
		
		setPageViewState(status, writeAccess);
		setSaveViewState(status, writeAccess);
		setModifyViewState(status, writeAccess);
		setPreviousRevisionViewState();
		setNextRevisionViewState();
		
		if (super.getModuleIdFromSession().equals(IConstants.APPROVE_WEB_MANAGER)
				|| super.getModuleIdFromSession().equals(IConstants.APPROVE_WEB_STATEWIDE)) {
			this.setupDisplayButtonsManagerSw();
		} else {
			// employee access
			this.setupDisplayButtonsEmployee();
		}
		// check if the advance check box can be displayed
		boolean displayAdvCheckbox = advanceService.getAdvanceEligibility(getUserSubject().getAppointmentId());
		if (!displayAdvCheckbox){
			displayAdvance = "none";
		}
		//display create expense button
		if ((status != null) && status.equals(IConstants.APPROVED) && writeAccess && (treqMaster.getTreqeIdentifier().getExpevIdentifier() == null)){
			displayCreateExpenseButton = "";
		}
	}

	private void setPageViewState(String status, boolean writeAccess) {
		// if user came in to create new expense
		if(treqMaster == null) view.setEditable(true);
		// if expense is undergoing modification
		else if(isExpenseBeingModified()) view.setEditable(true);
		// if current expense in SAVE status
		else if(writeAccess && "Y".equalsIgnoreCase(treqMaster.getCurrentInd()) && (status==null || "".equals(status))){
			//view.setEditable(true);
			if(treqMaster.getRevisionNumber() < 1) view.setEditable(true);
			else{
				setPageViewStateEmployee();

			}
		}
	}
	/**
	 * Sets page view for employee access
	 */
	private void setPageViewStateEmployee() {
		// check for rejections upfront
		boolean previousRevisionRejected = false;
		TravelReqMasters prevRequisition = treqService
				.getTravelRequisitionByEventId(treqMaster.getTreqeIdentifier()
						.getTreqeIdentifier(),
						treqMaster.getRevisionNumber() - 1);

		if (prevRequisition != null && isUserInEmployeeRole()
				&& !StringUtils.isEmpty(prevRequisition.getStatus())
				&& IConstants.REJECTED.equals(prevRequisition.getStatus())) {
			// last revision rejected and no actions yet
			previousRevisionRejected = true;
		}

		TravelReqMasters prevApprovedTreq = treqService
				.getPrevTreqMasterInApprovedStatus(treqMaster
						.getTreqeIdentifier(), treqMaster.getRevisionNumber());

		if (previousRevisionRejected || prevApprovedTreq == null) {
			view.setEditable(true);
		} else if (prevApprovedTreq != null && isUserInEmployeeRole()) {
			view.setEditable(false);
		} else {
			view.setEditable(true);
		}

		if (view.isEditable() && isUserInEmployeeRole()) {
			boolean priorApprovalStep = treqService.approvalStepExists(
					treqMaster.getTreqeIdentifier(), treqMaster
							.getRevisionNumber());

			if (priorApprovalStep) {
				// at least one approval step has been completed
				view.setEditable(false);
			}
		}
	}

	protected boolean isExpenseBeingModified(){
		return session.containsKey(IConstants.MODIFY_BUTTON_STATE_SESSION)
			&& (Boolean)session.get(IConstants.MODIFY_BUTTON_STATE_SESSION);
	}
	
	private void setSaveViewState(String status, boolean writeAccess) {
		if(view.isEditable())
			view.setSave(ComponentViewState.ENABLED);
		else
			view.setSave(ComponentViewState.DISABLED);
	}

	/**
	 * Decides the view state (i.e Enabled, Disabled or Hidden) for the 'Modify'
	 * element. The following conditions determine the result:
	 * <ul>
	 * 	<li>Employees can modify their expenses until not approved by the first approval</li>
	 * 	<li>Employees cannot modify their expenses after first level approval until being processed</li>
	 * 	<li>Processed expenses can be adjusted by employees only if they have the correct security role.
	 * 		To check for this role, access to module 'EXPF001' is checked. 
	 * 	</li>
	 *  <li> Managers can modify the expense report only if they have write access to user's dept/agency/tku 
	 *  	 and modify access to the current approval module (if the expense happens to be in the approval path). 
	 *  	 This later check would prevent a manager from modifying the expense once it has been approved by the 
	 *  	 next approver (only if he has no access to modify and override the next approvers actions).
	 *  </li>
	 * </ul>
	 * 
	 * @param status
	 * @param writeAccess
	 */
	private void setModifyViewState(String status, boolean writeAccess) {
		// if no expense master or if it's already being modified, disable it. 
		if(treqMaster == null || isExpenseBeingModified() || "N".equalsIgnoreCase(treqMaster.getCurrentInd())){
			view.setModify(ComponentViewState.DISABLED);
			return;
		}
		
		if (treqMaster.getStatus() != null){
			if (treqMaster.getStatus().equals(IConstants.PROCESSED) ||
					treqMaster.getStatus().equals(IConstants.EXTRACTED)){
				view.setModify(ComponentViewState.DISABLED);
				return;
			}
		}
		
		// if employee role
		if(isUserInEmployeeRole()){
			boolean modifiable = treqService.isTravelRequisitionModifiableByEmployee(treqMaster, getLoggedInUser(), getUserSubject());
			view.setModify(modifiable ? ComponentViewState.ENABLED : ComponentViewState.DISABLED);
			
		// if supervisor role
		}else{
			// if no write access, disable it
			if(!writeAccess){
				view.setModify(ComponentViewState.DISABLED);
			
			// else determine if they can modify at the expense's current status
			}else{
				boolean modifiable = treqService.isTravelRequisitionModifiableBySupervisor(treqMaster, getLoggedInUser(), getUserSubject());
				
				view.setModify(modifiable ? ComponentViewState.ENABLED : ComponentViewState.DISABLED);
			}
		}
	}
	
	private void setPreviousRevisionViewState() {
		if(treqMaster == null || treqMaster.getRevisionNumber()<1)//|| isExpenseBeingModified())
			view.setPreviousRevision(ComponentViewState.DISABLED);
		else
			view.setPreviousRevision(ComponentViewState.ENABLED);
	}

	private void setNextRevisionViewState() {
		int maxRevisionNo = -1;
		if(treqMaster != null)
			maxRevisionNo = treqService.getMaxRevisionNo(treqMaster.getTreqeIdentifier().getTreqeIdentifier());
		
		// if NEW expense OR expense with max rev no., disable 'NEXT'
		if(maxRevisionNo < 0 || treqMaster.getRevisionNumber() == maxRevisionNo)
			view.setNextRevision(ComponentViewState.DISABLED);
		else
			view.setNextRevision(ComponentViewState.ENABLED);
	}
	
	private void setupDisplayButtonsManagerSw() {
		displaySaveButton = "none";
		displaySubmitButton = "none";
		displayApproveButton = "inline";
		displayApproveWithCommentsButton = "inline";
		displayApproveNextButton = "inline";
		displayApproveSkipButton = "inline";
		displayRejectButton = "inline";
		if (ActionHelper.statusEqualOrGreaterThanSubmit(treqMaster)) {
			if (super.getModuleIdFromSession().equals(IConstants.APPROVE_WEB_MANAGER)) {
				String lastAction = treqService.getLatestAction(
						treqMaster).get(0).getActionCode();
				if (IConstants.APPROVAL_STEP1.equals(lastAction)) {
					// manager has already approved. this is done to guard
					// against a page refresh in manager approvals
					setDisableApprovalsButtons(true);
				} else {
					setDisableApprovalsButtons(false);
				}
			} else {
				// Statewide Access
				if (IConstants.APPROVED.equals(treqMaster.getStatus())) {
					setDisableApprovalsButtons(true);
				} else {
					setDisableApprovalsButtons(false);
				}
			}
			/*if (this.disableModifyButtonForAction()) {
				// user does not have security access for last action
				display.setDisableModifyButton(true);
			} else {
				display.setDisableModifyButton(false);
			}*/
		}
		// ZH, 05/11/2010 - Added security check for next action code when advance is being approved
		if (checkSecurityNextAction() < IConstants.SECURITY_UPDATE_MODULE_ACCESS){
			setDisableApprovalsButtons(true);
		}
	}
	
	private void setDisableApprovalsButtons(boolean flag) {
		disableApproveButton = flag;
		disableApproveWithCommentsButton = flag;
		disableApproveNextButton = flag;
		disableApproveSkipButton = flag;
		disableRejectButton = flag;
	}
	
	private int checkSecurityNextAction() {
		String nextActionCode = treqService.getLatestAction(treqMaster)
		.get(0).getNextActionCode();
		if (!StringUtils.isEmpty(nextActionCode)) {
			String moduleNextAction = commonService.getRefCode(nextActionCode);
			if (!StringUtils.isEmpty(moduleNextAction)) {
				// we have a module now - check security
				return securityService
						.getModuleAccessMode(getLoggedInUser(),
								moduleNextAction, getUserSubject()
										.getDepartment(), getUserSubject()
										.getAgency(), getUserSubject().getTku());
			}
		}
		// return lowest security setting if there is no next action
		return IConstants.SECURITY_NO_MODULE_ACCESS;
	}
	
private boolean disableModifyButtonForAction() {
		
		if (!StringUtils.isEmpty(treqMaster.getCurrentInd()) &&
				"N".equals(treqMaster.getCurrentInd()))
			return true;

		if (treqMaster.getStatus().equals(IConstants.EXTRACTED)) {
			// modify may not be done in extracted status
			return true;
		}

		// ZH - commneted the code below per direction from users. Modification
		// will be allowed
		// if Liquidations are present will added validation
		/*
		 * if (advanceMaster.getStatus().equals(IConstants.APPROVED) &&
		 * advanceMaster.getAdvanceLiquidationsCollection() != null &&
		 * advanceMaster.getAdvanceLiquidationsCollection().size() > 0) { //
		 * Liquidations exist. These must be cleared before the advance can be
		 * modifed return true; }
		 */

		String module = super.getModuleIdFromSession();

		if (treqMaster.getStatus().equals(IConstants.PROCESSED)
				&& !module.equals(IConstants.ADVANCE_STATEWIDE)) {
			// only statewide employees may perform modifications after PROC
			return true;
		}

		// Check to see if logged in user is subject employee
		// or if user does not have access to the Next_Action security module
		if (!this.getSecurityModuleAccess())
			return true;

		// ever been processed before. If so, only statewide may Modify the
		// advance
		if (treqMaster.getRevisionNumber() > 0) {
			if (!module.equals(IConstants.ADVANCE_STATEWIDE)
					&& !module.equals(IConstants.APPROVE_WEB_STATEWIDE)) {
				return true;
			}
		}
		
		return false;
	}

private void setupDisplayButtonsEmployee() {
	if (treqMaster != null && ActionHelper.statusEqualOrGreaterThanSubmit(treqMaster)) {
		displaySaveButton = "inline";
		displaySubmitButton = "inline";
		disableSaveButton = true;
		disableSubmitButton = true;
		//disableModifyButton = false;
		displayApproveButton = "none";
		displayApproveWithCommentsButton = "none";
		displayApproveNextButton = "none";
		displayApproveSkipButton = "none";
		displayRejectButton = "none";
		/*if (this.disableModifyButtonForAction()) {
			// user does not have security access for last action
			display.setDisableModifyButton(true);
		} else {
			display.setDisableModifyButton(false);
		}*/

	}
}

private boolean getSecurityModuleAccess() {
	// ZH commented per defect # 19 in Test, per direction by user
	/*if (super.getLoggedInUser().getEmpIdentifier() == super
			.getUserSubject().getEmployeeId()) {
		// user is also subject. Modify should be disabled
		return false;
	}*/

	if (treqMaster.getTravelReqActionsCollection() != null
			&& !treqMaster.getTravelReqActionsCollection().isEmpty()) {
		// if in approval path, check access for approval modules through
		// ref_codes
		String actionCode = treqService.getLatestAction(treqMaster)
				.get(0).getActionCode();
		String moduleAction = "";
		if (!StringUtils.isEmpty(actionCode)) {
			moduleAction = commonService.getRefCode(actionCode);
		} 

		int security = 0;
		if (!moduleAction.equals("")) {
			security = this.checkSecurity(moduleAction);
			if (security < IConstants.SECURITY_UPDATE_MODULE_ACCESS) {
				// User does not have update security access for current Action. Check next action code
				String nextActionCode = treqService.getLatestAction(treqMaster)
				.get(0).getNextActionCode();
				moduleAction = commonService.getRefCode(nextActionCode);
				security = this.checkSecurity(moduleAction);
			}
		} else {
			security = this.checkSecurity(super.getModuleIdFromSession());
		}
		if (security < IConstants.SECURITY_UPDATE_MODULE_ACCESS) {
			return false;
		}

	}
	return true;
}

private int checkSecurity(String moduleId) {
	if ("".equals(moduleId))
		moduleId = super.getModuleIdFromSession();
	empInfo = (EmployeeHeaderBean) session.get(IConstants.EMP_HEADER_INFO);
	UserProfile profile = super.getLoggedInUser();
	return securityService.getModuleAccessMode(profile, moduleId, super
			.getUserSubject().getDepartment(), super.getUserSubject()
			.getAgency(), super.getUserSubject().getTku());
}

	public boolean isDisableSaveButton() {
	return disableSaveButton;
}

public void setDisableSaveButton(boolean disableSaveButton) {
	this.disableSaveButton = disableSaveButton;
}

public boolean isDisableSubmitButton() {
	return disableSubmitButton;
}

public void setDisableSubmitButton(boolean disableSubmitButton) {
	this.disableSubmitButton = disableSubmitButton;
}

public boolean isDisableApproveButton() {
	return disableApproveButton;
}

public void setDisableApproveButton(boolean disableApproveButton) {
	this.disableApproveButton = disableApproveButton;
}

public boolean isDisableApproveWithCommentsButton() {
	return disableApproveWithCommentsButton;
}

public void setDisableApproveWithCommentsButton(
		boolean disableApproveWithCommentsButton) {
	this.disableApproveWithCommentsButton = disableApproveWithCommentsButton;
}

public boolean isDisableRejectButton() {
	return disableRejectButton;
}

public void setDisableRejectButton(boolean disableRejectButton) {
	this.disableRejectButton = disableRejectButton;
}

	/**
	 * Prepares JSON representation of view state.
	 * 
	 * @return
	 */
	public String getViewJson() {
		return jsonParser.toJson(view);
	}
	
	public int getTreqMasterId() {
		return treqMasterId;
	}

	public void setTreqMasterId(int treqMasterId) {
		this.treqMasterId = treqMasterId;
	}

	public List<StateAuthCodes> getAuthCodes() {
		return authCodes;
	}

	public void setAuthCodes(List<StateAuthCodes> authCodes) {
		this.authCodes = authCodes;
	}

	public List<Integer> getSelectedAuthCodes() {
		return selectedAuthCodes;
	}

	public void setSelectedAuthCodes(List<Integer> selectedAuthCodes) {
		this.selectedAuthCodes = selectedAuthCodes;
	}
	
	public void setView(TripIdView view) {
		this.view = view;
	}

	public TripIdView getView() {
		return view;
	}
	
	public String getErrorsJson() {
		return errorsJson;
	}

	public void setErrorsJson(String expenseErrorsJson) {
		this.errorsJson = expenseErrorsJson;
	}
	
	public EmployeeHeaderBean getEmpInfo() {
		return empInfo;
	}

	public void setEmpInfo(EmployeeHeaderBean empInfo) {
		this.empInfo = empInfo;
	}

	public String getFocusTab() {
		return focusTab;
	}

	public void setFocusTab(String focusTab) {
		this.focusTab = focusTab;
	}

	public TravelReqMasters getTreqMaster() {
		return treqMaster;
	}

	public void setTreqMaster(TravelReqMasters treqMaster) {
		this.treqMaster = treqMaster;
	}

	public TravelReqDetails getTreqDetails() {
		return treqDetails;
	}

	public void setTreqDetails(TravelReqDetails treqDetails) {
		this.treqDetails = treqDetails;
	}

	public String getDisplaySaveButton() {
		return displaySaveButton;
	}

	public void setDisplaySaveButton(String displaySaveButton) {
		this.displaySaveButton = displaySaveButton;
	}

	public String getDisplaySubmitButton() {
		return displaySubmitButton;
	}

	public void setDisplaySubmitButton(String displaySubmitButton) {
		this.displaySubmitButton = displaySubmitButton;
	}

	public String getDisplayApproveButton() {
		return displayApproveButton;
	}

	public void setDisplayApproveButton(String displayApproveButton) {
		this.displayApproveButton = displayApproveButton;
	}

	public String getDisplayApproveWithCommentsButton() {
		return displayApproveWithCommentsButton;
	}

	public void setDisplayApproveWithCommentsButton(
			String displayApproveWithCommentsButton) {
		this.displayApproveWithCommentsButton = displayApproveWithCommentsButton;
	}

	public String getDisplayRejectButton() {
		return displayRejectButton;
	}

	public void setDisplayRejectButton(String displayRejectButton) {
		this.displayRejectButton = displayRejectButton;
	}

	public String getDisplayAdvance() {
		return displayAdvance;
	}

	public void setDisplayAdvance(String displayAdvance) {
		this.displayAdvance = displayAdvance;
	}

	public String getDisplayCreateExpenseButton() {
		return displayCreateExpenseButton;
	}

	public void setDisplayCreateExpenseButton(String displayCreateExpenseButton) {
		this.displayCreateExpenseButton = displayCreateExpenseButton;
	}

	public String getDisplayApproveNextButton() {
		return displayApproveNextButton;
	}

	public void setDisplayApproveNextButton(String displayApproveNextButton) {
		this.displayApproveNextButton = displayApproveNextButton;
	}

	public String getDisplayApproveSkipButton() {
		return displayApproveSkipButton;
	}

	public void setDisplayApproveSkipButton(String displayApproveSkipButton) {
		this.displayApproveSkipButton = displayApproveSkipButton;
	}

	public boolean isDisableApproveNextButton() {
		return disableApproveNextButton;
	}

	public void setDisableApproveNextButton(boolean disableApproveNextButton) {
		this.disableApproveNextButton = disableApproveNextButton;
	}

	public boolean isDisableApproveSkipButton() {
		return disableApproveSkipButton;
	}

	public void setDisableApproveSkipButton(boolean disableApproveSkipButton) {
		this.disableApproveSkipButton = disableApproveSkipButton;
	}	
}
