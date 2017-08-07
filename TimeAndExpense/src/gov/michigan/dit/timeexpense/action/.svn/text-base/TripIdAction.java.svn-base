package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.OutOfStateTravel;
import gov.michigan.dit.timeexpense.model.core.StateAuthCodes;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.ComponentViewState;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.ErrorDisplayBean;
import gov.michigan.dit.timeexpense.model.display.TripIdView;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.util.ExpenseViewUtil;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Action to cater to requests for creating a new expense, viewing an
 * existing expense,  and modifying an existing expense.
 * 
 * It also provides functionality to view different revisions of the
 * given expense.
 * 
 * @author chaudharym
 */
public class TripIdAction extends AbstractAction {
	private static final long serialVersionUID = -7153945847561208325L;

	private int expenseMasterId;
	private Integer expenseEventId;
	private String errorsJson;

	private ExpenseMasters expenseMaster;
	private EmployeeHeaderBean empInfo;
	private ExpenseViewUtil viewUtil;
	private CodingBlockDSP codingBlockService;
	private TripIdView view;

	public List<StateAuthCodes> authCodes;
	private List<Integer> selectedAuthCodes;
	
	private ExpenseDSP expenseService;
	private AppointmentDSP appointmentService;
	private SecurityManager security;
	
	private String focusTab;
	
	private String adjustmentBtnVisibility;
	private String adjustmentList;
	private String rstarsList;
	
	
	public TripIdAction() {
		view = new TripIdView();
	}

	public void prepare(){
		codingBlockService = new CodingBlockDSP(entityManager);
		appointmentService = new AppointmentDSP(entityManager);
		viewUtil = new ExpenseViewUtil();
		viewUtil.setJsonParser(jsonParser);
		viewUtil.setCodingBlockService(codingBlockService);
		viewUtil.setAppointmentService(appointmentService);
		
		expenseService = new ExpenseDSP(entityManager);
		
		security = new SecurityManager(entityManager);
	}
	
	/**
	 * Prepares the data for initial view state. 
	 */
	public String execute() throws Exception {
		authCodes = expenseService.getAuthorizationCodes();
        StringBuilder listOfValues=new StringBuilder("[");
        String jsonResponse = "";
		Integer expenseMasterId = (Integer)session.get("RequestedExpenseId");
		Integer expenseEventId = (Integer)session.get("RequestedExpenseEventId");

		// if explicit expense master Id provided always fetch it
		if(expenseMasterId != null){
			expenseMaster = expenseService.getExpense(expenseMasterId);
			session.put(IConstants.EXPENSE_SESSION_DATA, expenseMaster);
			
			// remove 'expenseMasterId' as soon as expense state in session is established
			synchronized (session) {
				session.remove("RequestedExpenseId");
			}
		} else if (expenseEventId != null){
			int maxRevision = expenseService.getMaxRevisionNo(expenseEventId);
			expenseMaster = expenseService.getExpenseByExpenseEventId(expenseEventId, maxRevision);
		} else{
			expenseMaster = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);
			expenseMaster = entityManager.merge(expenseMaster);
		}
		
		//populate selected auth code list
		if(expenseMaster != null && expenseMaster.getOutOfStateTravelList() != null){
			selectedAuthCodes = new ArrayList<Integer>();
			for(OutOfStateTravel oost : expenseMaster.getOutOfStateTravelList()){
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
		if (expenseMaster != null && expenseMaster.getExpevIdentifier().getAppointmentId() != null
				&& getEmpInfo() != null 
				&& expenseMaster.getExpevIdentifier().getAppointmentId() !=
			getEmpInfo().getApptId()){
			// change header info in case the expense was created for an old appointment
			List<EmployeeHeaderBean> empInfoList = appointmentService.
									getEmployeeHeaderInfoByApptId(expenseMaster.getExpevIdentifier().getAppointmentId(), 
											expenseMaster.getExpDateTo());
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
		
		// update with correct UserSubject
		if(expenseMaster != null && (expenseMaster.getExpevIdentifier().getAppointmentId() != getUserSubject().getAppointmentId())){
			// update session with correct UserSubject according to the chosen expense dates.
			updateUserSubjectWithCorrectAppointmentInfo(expenseMaster);
		}

		// push updated expense into session
		session.put(IConstants.EXPENSE_SESSION_DATA, expenseMaster);
		
		//if this is a new expense then ignore getting adjustments for this expense.
		adjustmentBtnVisibility = "none";
		if (expenseMaster != null){
			List <BigDecimal> adjustmentIdentifiers =  expenseService.getAdjustmentsForExpense( getUserSubject().getAppointmentId() , expenseMaster.getExpmIdentifier());
			int rstarsCount = expenseService.getRStartsAdjustmentsForExpense( getUserSubject().getAppointmentId() , expenseMaster.getExpmIdentifier());
				
			if (adjustmentIdentifiers.isEmpty())
				adjustmentBtnVisibility = "none";
			else			
			{
				adjustmentBtnVisibility = "inline";			
              
				for(int i=0;i<adjustmentIdentifiers.size();i++){
					if(i==0){
						listOfValues.append(adjustmentIdentifiers.get(i).toString());
					} else {
						listOfValues.append(","+adjustmentIdentifiers.get(i).toString());
					}
				}
				listOfValues.append("] ");
				adjustmentList=listOfValues.toString().replace("[", "").replace("]","");
			
//				adjustmentList = adjustmentIdentifiers.toString().replace("[", "")
//						.replace("]", "");
				
				if (rstarsCount > 0)
					rstarsList = "Invalid CB that erred on this expense in R*Stars has been corrected.  Please "+
								 "see Distribution Inquiry by Employee screen in DCDS to view updated Coding Block for this expense";							
				else
					rstarsList = "There were no differences in the R*Stars.";
			}
			
		}
		
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
	
	/**
	 * Swap the user subject in case expense being viewed is for a previous appointment
	 * @param expense
	 */

	private void updateUserSubjectWithCorrectAppointmentInfo(ExpenseMasters expense) {
		// Update the UserSubject in session, according to the referred expense.
		List<AppointmentsBean> appts = expenseService.getActiveAppointments(expense.getExpDateFrom(),expense.getExpDateTo(), 
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
		ExpenseMasters currExpense = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);

		// set expense master Id to current. If next not found then we'll show
		// the current one.
		if(currExpense != null) expenseMasterId = currExpense.getExpmIdentifier();
		
		ExpenseMasters nextExpense = null;
		if(currExpense != null){
			nextExpense = expenseService.getExpenseByExpenseEventId(
							currExpense.getExpevIdentifier().getExpevIdentifier(), 
								currExpense.getRevisionNumber() + 1);
		}
		
		if(nextExpense != null)
			expenseMasterId = nextExpense.getExpmIdentifier();
		
		// set expense to null, to let view setup function skip it
		expenseMaster = null;
		
		// set startup tab pref in session
		session.put("FOCUS_TAB", focusTab);
		
		return IConstants.SUCCESS;
	}

	/**
	 * Finds the previous revision of the currently displayed ExpenseMasters.
	 * 
	 * @throws Exception
	 */
	public String getPreviousRevision()  throws Exception{
		ExpenseMasters currExpense = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);

		// set expense master Id to current. If previous not found then we'll show
		// the current one.
		if(currExpense != null) expenseMasterId = currExpense.getExpmIdentifier();
		
		ExpenseMasters previousExpense = null;
		if(currExpense !=  null){
			previousExpense = expenseService.getExpenseByExpenseEventId(
								currExpense.getExpevIdentifier().getExpevIdentifier(), 
									currExpense.getRevisionNumber() - 1);
		}
		
		if(previousExpense != null){
			expenseMasterId = previousExpense.getExpmIdentifier();
		}
		
		// set expense to null, to let view setup function skip it
		expenseMaster = null;
		
		// set startup tab pref in session
		session.put("FOCUS_TAB", focusTab);
		
		return IConstants.SUCCESS;
	}
	
	/**
	 * Prepares view state.
	 */
	public void setupDisplay() {
		errorsJson = viewUtil.prepareExpenseErrorsJson(expenseMaster);
		setupTripIdView();
	}

	private void setupTripIdView() {
		String status = (expenseMaster==null) ? null : expenseMaster.getStatus();
		
		boolean writeAccess = security.checkModuleAccess(getLoggedInUser(), 
									getModuleIdFromSession(), getUserSubject().getDepartment(),
									getUserSubject().getAgency(), getUserSubject().getTku());
		
		setPageViewState(status, writeAccess);
		setSaveViewState(status, writeAccess);
		setModifyViewState(status, writeAccess);
		setPreviousRevisionViewState();
		setNextRevisionViewState();
	}

	private void setPageViewState(String status, boolean writeAccess) {
		// if user came in to create new expense
		if(expenseMaster == null) view.setEditable(true);
		// if expense is undergoing modification
		else if(isExpenseBeingModified()) view.setEditable(true);
		// if current expense in SAVE status
		else if(writeAccess && "Y".equalsIgnoreCase(expenseMaster.getCurrentInd()) && (status==null || "".equals(status))){
			// enabled for non-submitted expenses 
			if(expenseMaster.getRevisionNumber() < 1) view.setEditable(true);
			else{
				// check for rejections upfront
				boolean previousRevisionRejected = false;
				ExpenseMasters prevExpense = expenseService.getExpenseByExpenseEventId(
						expenseMaster.getExpevIdentifier().getExpevIdentifier(), 
						expenseMaster.getRevisionNumber() - 1);	
				
				if (prevExpense != null && isUserInEmployeeRole() &&
						!StringUtils.isEmpty(prevExpense.getStatus()) &&
						IConstants.REJECTED.equals(prevExpense.getStatus())) {
					// last revision rejected and no actions yet
					previousRevisionRejected = true;
				} 
				
					// Has this expenses been processed before?
					ExpenseMasters prevExpInProc = expenseService
							.getPrevExpenseMasterInProcStatus(expenseMaster
									.getExpevIdentifier(), expenseMaster
									.getRevisionNumber());
					boolean priorApprovalStep = false;
					boolean userHasExpAdjRole = false;
					if (prevExpInProc == null) {
						// Never been processed, check for approval steps on the previous revisions
						if (!previousRevisionRejected){
							priorApprovalStep = expenseService.approvalStepExists(
									expenseMaster.getExpevIdentifier(),
									expenseMaster.getRevisionNumber(), false);
						}
					} else {
						// Has been processed before, check for approval steps on the previous revisions
						// that is later than the last revision processed 
						if (!previousRevisionRejected){
							priorApprovalStep = expenseService.approvalStepExists(
									prevExpInProc.getExpevIdentifier(),
									prevExpInProc.getRevisionNumber(), true);
						}
						UserSubject subject = getUserSubject();
						userHasExpAdjRole = security.checkModuleAccess(
								getLoggedInUser(),
								IConstants.EXPENSE_ADJUSTMENT_MODULE, subject
										.getDepartment(), subject.getAgency(),
								subject.getTku());
					}
					// if revisions before initial PROC, allow modifications OTHERWISE allow if security 
					// granted to adjust.
					if (prevExpInProc == null
							|| (prevExpInProc != null && userHasExpAdjRole)) {
						view.setEditable(true);
					}
					
					if (view.isEditable() && isUserInEmployeeRole()){
							if (priorApprovalStep) {
								// at least one approval step has been completed 
							view.setEditable(false);
					}
		
				}
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
		
		//if this is an PDF expense type, check if the supervisor has Modify role.			
		if (expenseMaster != null && expenseMaster.isPdfInd() && view.getSave() == ComponentViewState.ENABLED){				
			int moduleAccess = security.getModuleAccessMode(getLoggedInUser(), IConstants.PDF_MODULE,
					getUserSubject().getDepartment(), getUserSubject().getAgency(),
					getUserSubject().getTku());
			
			if (moduleAccess == IConstants.SECURITY_INQUIRY_MODULE_ACCESS || moduleAccess == IConstants.SECURITY_NO_MODULE_ACCESS){				
				view.setSave(ComponentViewState.DISABLED);
				view.setEditable(false);
			}
		}
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
		if(expenseMaster == null || isExpenseBeingModified() || "N".equalsIgnoreCase(expenseMaster.getCurrentInd())){
			view.setModify(ComponentViewState.DISABLED);
			return;
		}
		
		// if employee role
		if(isUserInEmployeeRole()){
			boolean modifiable = expenseService.isExpenseModifiableByEmployee(expenseMaster, getLoggedInUser(), getUserSubject());
			view.setModify(modifiable ? ComponentViewState.ENABLED : ComponentViewState.DISABLED);
			
		// if supervisor role
		}else{
			// if no write access, disable it
			if(!writeAccess){
				view.setModify(ComponentViewState.DISABLED);
			
			// else determine if they can modify at the expense's current status
			}else{
				boolean modifiable = expenseService.isExpenseModifiableBySupervisor(expenseMaster, getLoggedInUser(), getUserSubject(), view.getSave());
				
				view.setModify(modifiable ? ComponentViewState.ENABLED : ComponentViewState.DISABLED);
			}
		}
	}
	
	private void setPreviousRevisionViewState() {
		if(expenseMaster == null || expenseMaster.getRevisionNumber()<1)//|| isExpenseBeingModified())
			view.setPreviousRevision(ComponentViewState.DISABLED);
		else
			view.setPreviousRevision(ComponentViewState.ENABLED);
	}

	private void setNextRevisionViewState() {
		int maxRevisionNo = -1;
		if(expenseMaster != null)
			maxRevisionNo = expenseService.getMaxRevisionNo(expenseMaster.getExpevIdentifier().getExpevIdentifier());
		
		// if NEW expense OR expense with max rev no., disable 'NEXT'
		if(maxRevisionNo < 0 || expenseMaster.getRevisionNumber() == maxRevisionNo)
			view.setNextRevision(ComponentViewState.DISABLED);
		else
			view.setNextRevision(ComponentViewState.ENABLED);
	}
	
	/**
	 * Prepares JSON representation of view state.
	 * 
	 * @return
	 */
	public String getViewJson() {
		return jsonParser.toJson(view);
	}
	
	public int getExpenseMasterId() {
		return expenseMasterId;
	}

	public void setExpenseMasterId(int expenseMasterId) {
		this.expenseMasterId = expenseMasterId;
	}

	public List<StateAuthCodes> getAuthCodes() {
		return authCodes;
	}

	public void setAuthCodes(List<StateAuthCodes> authCodes) {
		this.authCodes = authCodes;
	}

	public ExpenseMasters getExpenseMaster() {
		return expenseMaster;
	}

	public void setExpenseMaster(ExpenseMasters expenseMasters) {
		this.expenseMaster = expenseMasters;
	}

	public List<Integer> getSelectedAuthCodes() {
		return selectedAuthCodes;
	}

	public void setSelectedAuthCodes(List<Integer> selectedAuthCodes) {
		this.selectedAuthCodes = selectedAuthCodes;
	}
	
	public String getDefaultTravelIndicator(){
		return (expenseMaster==null)? "Y": expenseMaster.getTravelInd();
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

	public int getExpenseEventId() {
		return expenseEventId;
	}

	public void setExpenseEventId(int expenseEventId) {
		this.expenseEventId = expenseEventId;
	}

	public String getAdjustmentBtnVisibility() {
		return adjustmentBtnVisibility;
	}

	public void setAdjustmentBtnVisibility(String adjustmentBtnVisibility) {
		this.adjustmentBtnVisibility = adjustmentBtnVisibility;
	}

	public String getAdjustmentList() {
		return adjustmentList;
	}

	public void setAdjustmentList(String adjustmentList) {
		this.adjustmentList = adjustmentList;
	}

	public String getRstarsList() {
		return rstarsList;
	}

	public void setRstarsList(String rstarsList) {
		this.rstarsList = rstarsList;
	}
	
}
