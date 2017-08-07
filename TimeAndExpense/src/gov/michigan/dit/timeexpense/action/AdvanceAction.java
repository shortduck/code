/**
 * This class includes all actions for the Advance page
 * 
 * Zahid Hussain - 02/18/2009
 * 
 */

package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.exception.TimeAndExpenseException;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetails;
import gov.michigan.dit.timeexpense.model.core.AdvanceErrors;
import gov.michigan.dit.timeexpense.model.core.AdvanceEvents;
import gov.michigan.dit.timeexpense.model.core.AdvanceHistory;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
import gov.michigan.dit.timeexpense.model.core.DefaultDistributionsAdvAgy;
import gov.michigan.dit.timeexpense.model.core.TkuoptTaOptions;
import gov.michigan.dit.timeexpense.model.core.TravelReqEvents;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.DisplayAdvance;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.TimeAndExpenseError;
import gov.michigan.dit.timeexpense.service.AdvanceCodingBlockDSP;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.service.TravelRequisitionDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class AdvanceAction extends AbstractAction {
	// display fields
	private int advanceEventId = 0;
	private int advanceMasterId = 0;
	private AdvanceMasters advanceMaster;
	private DisplayAdvance display;
	private EmployeeHeaderBean empInfo;
	private String errorsJson = "";
	
	private final int MODE_CREATE = 0;
	private final int MODE_INQUIRY_UPDATE = 1;
	private final int MODE_DISABLED = 2;
	private String previouslyPROC = "";

	// other services used
	private AdvanceDSP advanceService;
	private AdvanceCodingBlockDSP advanceCodingBlockService;
	private CodingBlockDSP codingBlockService;
	public String getErrorsJson() {
		return errorsJson;
	}

	public void setErrorsJson(String errorsJson) {
		this.errorsJson = errorsJson;
	}

	private CommonDSP commonService;
	private AppointmentDSP apptService;
	private TravelRequisitionDSP treqService;

	// Coding block fields
	private int noOfCodingBlocks = 0;
	private String cbOptions = null;
	private String selectedCodingBlockData = null;
	private String result = null;
	private String apprYear;
	boolean includesCbStd = false;
	private Map<String, String[]> paramMap;
	private DefaultDistributionsAdvAgy defaultCodingBlock;
	private SecurityManager securityService;
	private int treqEventId;

	Logger logger = Logger.getLogger(AdvanceAction.class);

	public AdvanceAction() {
		display = new DisplayAdvance();
		advanceMaster = new AdvanceMasters();
	}

	/**
	 * Initialize instance level objects
	 */

	public void prepare() {
		// initialize services - called prior to all actions
		advanceService = new AdvanceDSP(entityManager);
		commonService = new CommonDSP(entityManager);
		advanceCodingBlockService = new AdvanceCodingBlockDSP(entityManager);
		securityService = new SecurityManager(entityManager);
		apptService = new AppointmentDSP(entityManager);
		treqService = new TravelRequisitionDSP(entityManager);
		codingBlockService = new CodingBlockDSP(entityManager);
	}

	/**
	 * Action for creating a new advance.
	 * 
	 * @return
	 */
	public String createAdvance() {

		logger.info("Action: createAdvance invoked");
		// clear session variables
		session.remove(IConstants.ADVANCE_CURRENT_ADVANCE_EVENT);
		session.remove(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);
		session.remove(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER_INDEX);
		session.remove(IConstants.ADVANCE_MAX_REVISION_NUMBER);
		this.setupDisplay(this.MODE_CREATE);
		return IConstants.SUCCESS;
	}

	/**
	 * Action for viewing an existing advance
	 * 
	 * @return
	 */

	public String viewAdvance() {
		
		String result = IConstants.SUCCESS;

		if (advanceMasterId > 0)
			advanceMaster = entityManager.find(AdvanceMasters.class,
					advanceMasterId);
		else if (advanceEventId > 0) {
			AdvanceEvents advanceEvents = entityManager.find(AdvanceEvents.class,
					advanceEventId);
			int maxRevision = advanceService.getMaxRevisionNo(advanceEventId);
			advanceMaster = advanceService.getAdvanceByAdvanceEventId(
					advanceEvents, maxRevision);
		}
		TravelReqEvents treqEvent	= treqService.getTravelRequisitionForAdvance(advanceMaster.getAdevIdentifier().getAdevIdentifier());
		if (treqEvent != null){
			treqEventId = treqEvent.getTreqeIdentifier();
		}
		if (getUserSubject().getAppointmentId() != advanceMaster.getAdevIdentifier().getApptIdentifier()){
			// override subject appointment in case request date not in range for currently set subject appointment
			
			List<AppointmentsBean> appts = advanceService.getActiveAppointments
			(advanceMaster.getRequestDate(), advanceMaster.getRequestDate(),getLoggedInUser().getUserId(), getModuleIdFromSession(),
					getUserSubject().getEmployeeId() );
			if(appts.size() == 1){
						
			UserSubject subject = new UserSubject((int) appts.get(0).getEmp_identifier(), 
					(int) appts.get(0).getAppt_identifier(), appts.get(0).getStart_date(), 
					appts.get(0).getEnd_date(),appts.get(0).getDepartment(), appts.get(0).getAgency(), 
					appts.get(0).getTku(), appts.get(0).getPosition_id());
			if(appts.get(0).getDepartureDate() != null)	subject.setAppointmentEnd(appts.get(0).getDepartureDate());
						
			setupApptDates(subject, appts.get(0).getAppt_identifier());
			session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		}
		}

		session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER, advanceMaster);
		session.put(IConstants.ADVANCE_MAX_REVISION_NUMBER, advanceMaster
				.getRevisionNumber());
		
		this.setupDisplay(this.MODE_INQUIRY_UPDATE);

		return result;
	}
	//AI-26436
	private void setupApptDates(UserSubject subject, long appointmentId) {
		AppointmentsBean apptBean = apptService.findActiveAppointmentDateSpan(appointmentId);
		
		if(apptBean.getDepartureDate() != null) subject.setAppointmentEnd(apptBean.getDepartureDate());
		else if(apptBean.getEnd_date() != null) subject.setAppointmentEnd(apptBean.getEnd_date());
		
		if(apptBean.getAppointment_date() != null) subject.setAppointmentDate(apptBean.getAppointment_date());
	}//ai-26436

	/**
	 * Prepare the advance details page for the initial display. The display
	 * includes formatting data for display, coding blocks and control buttons.
	 * Further dynamic page control settings (after the initial page rendering
	 * is done at the client.
	 * 
	 * @param mode
	 */

	private void setupDisplay(int mode) {
		display.setModuleId(super.getModuleIdFromSession());
		
		if (mode == MODE_INQUIRY_UPDATE || mode == MODE_DISABLED) {
			// displaying existing data
			this.setupDisplayExistingAdvance();
			this.setupDisplayErrors();
			defaultCodingBlock = advanceCodingBlockService
					.getDefaultAgencyAdvanceCodingBlock(super.getUserSubject()
							.getDepartment(), super.getUserSubject()
							.getAgency(), advanceMaster.getRequestDate());
			
			// perform security check
			int securityCheck = securityService
			.getModuleAccessMode(getLoggedInUser(), getModuleIdFromSession(), getUserSubject().getDepartment(),
					getUserSubject().getAgency(), getUserSubject().getTku());
			
			if (securityCheck < IConstants.SECURITY_UPDATE_MODULE_ACCESS ){
				display.setViewMode("DISABLED");
				mode = MODE_DISABLED;
			} else {
				display.setViewMode(advanceMaster.getStatus());
			}
			
		} else if (mode == MODE_CREATE) {
			// new advance
			String moduleId = display.getModuleId();
			if (moduleId != null && moduleId.equalsIgnoreCase(IConstants.ADVANCE_STATEWIDE)) {
				display.setManualWarrantIssdInd(" ");
			}
			createAdvanceDisplayFromRequisition ();
			Date requestDate;
			if (display.getRequestDate() == null)
				requestDate = Calendar.getInstance().getTime();
			else
				requestDate = TimeAndExpenseUtil.getDateFromString(display.getRequestDate());
			defaultCodingBlock = advanceCodingBlockService
					.getDefaultAgencyAdvanceCodingBlock(super.getUserSubject()
							.getDepartment(), super.getUserSubject()
							.getAgency(), requestDate);
			display.setRequestDate(TimeAndExpenseUtil.displayDate(Calendar
					.getInstance().getTime()));
		}
		// emp header info
		this.setupDisplayEmpInfo();

		try {
			// coding block display
			this.setupDisplayCodingBlocks(mode);
		} catch (Exception e) {
			logger.error("Exception occured while trying to display advance coding blocks", e);
		}
		// establish mode (show/enable/disable) for buttons
		this.setupDisplayButtons(mode);

	}

	/**
	 * Setup display for Employee info header
	 */

	private void setupDisplayEmpInfo() {
		// display employee information
		if (session.get(IConstants.EMP_HEADER_INFO) != null
				&& ((EmployeeHeaderBean) session
						.get(IConstants.EMP_HEADER_INFO)).getEmpId() == super
						.getUserSubject().getEmployeeId()) {
			// already set, just display from session
			setEmpInfo((EmployeeHeaderBean) session
					.get(IConstants.EMP_HEADER_INFO));
			if (advanceMaster != null && advanceMaster.getAdevIdentifier() != null &&
					advanceMaster.getAdevIdentifier().getApptIdentifier() !=
				getEmpInfo().getApptId()){
				// change header info in case the advance was created for an old appointment
				List<EmployeeHeaderBean> empInfoList = apptService.
										getEmployeeHeaderInfoByApptId(advanceMaster.getAdevIdentifier().getApptIdentifier(), 
												advanceMaster.getRequestDate());
				if (!empInfoList.isEmpty()){
					setEmpInfo(empInfoList.get(0));
					session.put(IConstants.EMP_HEADER_INFO, empInfo);
				}
			}
		} else {
			// Employee info header not already set
			List<EmployeeHeaderBean> empInfoList = null;

			if (super.getUserSubject() != null
					&& !(super.getModuleIdFromSession().equals(IConstants.ADVANCE_EMPLOYEE))) {
				// Manager/SW access
				empInfoList = apptService.getEmployeeHeaderInfoByEmpId(super
						.getUserSubject().getEmployeeId());
				empInfo = empInfoList.get(0);
			} else {
				empInfoList = apptService
						.getEmployeeHeaderInfoByEmpId((int) (super
								.getLoggedInUser().getEmpIdentifier()));
				empInfo = empInfoList.get(0);
			}
			session.put(IConstants.EMP_HEADER_INFO, empInfo);
		}
	}

	/**
	 * Prepares the coding block display
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */

private String setupDisplayCodingBlocks(int mode) throws Exception {
		
		TkuoptTaOptions tkuOptTaOptions = null;
		String storeJson = "";
		
		UserSubject userSubjectForCbStore = (UserSubject) session.get(IConstants.USER_SUBJECT_FOR_CB_STORE);
		if (userSubjectForCbStore == null || 
				(!ActionHelper.getConcatDeptAgencyTku(userSubjectForCbStore)
						.equals(ActionHelper.getConcatDeptAgencyTku(super.getUserSubject())))){
			// create or change CB subject, options and store 
			storeJson = this.setupDisplayNewCodingBlockMetaData();
		}
		else {
			// session CB store will work since Dept, Agency and TKU is same
			tkuOptTaOptions = (TkuoptTaOptions) (session.get(IConstants.CODING_BLOCK_OPTIONS));
			cbOptions = advanceCodingBlockService
			.prepareAdvanceCodingBlockMetaData(super.getUserSubject().getDepartment(),
					super.getUserSubject().getAgency(), super.getUserSubject().getTku(), tkuOptTaOptions);
			storeJson = (String)session.get(IConstants.CODING_BLOCK_STORE);
		}		

		if (mode == MODE_INQUIRY_UPDATE) {
			// get existing data
			this.setupDisplaySelectedCodingBlocks();
		}

		result = "{store:" + storeJson + "}";
		setResult(result);

		return IConstants.SUCCESS;

	}
	
	private String setupDisplayNewCodingBlockMetaData () throws TimeAndExpenseException {
		TkuoptTaOptions tkuOptTaOptions = null;
		String storeJson = "";
		// need to fetch and set store with CB options in session
		// store data ..
		String currYear = String.valueOf(
				Calendar.getInstance().get(Calendar.YEAR)).substring(2);
		session.put(IConstants.USER_SUBJECT_FOR_CB_STORE, super.getUserSubject());
		tkuOptTaOptions = advanceCodingBlockService.getCbMetaData(getUserSubject().getDepartment(), getUserSubject().getAgency(), getUserSubject().getTku());
		session.put(IConstants.CODING_BLOCK_OPTIONS, tkuOptTaOptions);
		try {
			storeJson = advanceCodingBlockService.prepareCodingBlockStoreData(getUserSubject().getDepartment(), getUserSubject().getAgency(), getUserSubject().getTku(), currYear,tkuOptTaOptions);
		session.put(IConstants.CODING_BLOCK_STORE, storeJson);
		// Meta data to determine which coding block elements to show
		cbOptions = advanceCodingBlockService
				.prepareAdvanceCodingBlockMetaData(super.getUserSubject().getDepartment(),
						super.getUserSubject().getAgency(), super.getUserSubject().getTku(), tkuOptTaOptions);
		} catch (Exception e) {
			logger.error("Exception occured while trying to prepare advance coding block store", e);
			throw new TimeAndExpenseException (e.getMessage());
		}
		return storeJson;
	}

	private void setupDisplaySelectedCodingBlocks() {
		// Advance Coding Block data
		String advanceCodingBlockData = advanceCodingBlockService
				.prepareAdvanceCodingBlocks(advanceMaster);
		selectedCodingBlockData = "{selected:" + advanceCodingBlockData + "}";
		setSelectedCodingBlockData(advanceCodingBlockData);
		logger.info("Selected data is: " + advanceCodingBlockData);
		this.setNoOfCodingBlocks(advanceMaster.getAdvanceDetailsCollection()
				.get(0).getAdvanceDetailCodingBlocksCollection().size());
	}

	/**
	 * Ajax action for advance history tab
	 * 
	 * @return
	 */

	public String getAdvanceHistory() {

		String result = IConstants.SUCCESS;
		advanceMaster = (AdvanceMasters) session
				.get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);
		
		if (advanceMaster == null){
			return result;
		}
			
		advanceMaster = entityManager.merge(advanceMaster);
		AdvanceEvents advanceEvents = advanceMaster.getAdevIdentifier();
		if (advanceEvents.getAdevIdentifier() != null
				&& advanceEvents.getAdevIdentifier() > 0) {
			entityManager.refresh(advanceEvents);
		}
		List<AdvanceHistory> historyList = advanceService
				.getAdvanceHistory(advanceEvents);
		StringBuilder buffer = new StringBuilder("{");
		String status = ActionHelper.getStatusDisplayMessage(advanceMaster, advanceService,getUserSubject());
		// Fetch the associated travel requisition if there is one. The requisition event id will be shown on the History tab
		TravelReqEvents treqEvent	= treqService.getTravelRequisitionForAdvance(advanceMaster.getAdevIdentifier().getAdevIdentifier());
		if (treqEvent != null){
			treqEventId = treqEvent.getTreqeIdentifier();
		}
		
		
		buffer.append("history:" + jsonParser.toJson(historyList) + ", status:"
				+ "\"" + status + "\"" + ", treqEventId:" + "\"" + treqEventId + "\"");
		buffer.append("}");
		// logger.debug("history string is: " + );
		super.setJsonResponse(jsonParser.toJson(buffer.toString()));

		return result;
	}

	/**
	 * Called to determine if an advance with errors can be submitted
	 * 
	 * @param advanceMaster
	 * @param subject
	 * @return
	 */

	private boolean canSubmitAdvanceWithErrors(AdvanceMasters advanceMaster,
			UserSubject subject) {

		boolean canSubmit = false;
		boolean fatalErrorExists = false;
		if (advanceMaster.getAdvanceErrorsCollection() != null
				&& advanceMaster.getAdvanceErrorsCollection().size() > 0) {
			for (AdvanceErrors errors : advanceMaster
					.getAdvanceErrorsCollection()) {
				if (errors.getErrorCode().getIcon() == 2) { 
					// Icon value 2 indicates an ERROR
					fatalErrorExists = true;
					break;
				}
			}
		}
		if (fatalErrorExists) {
			if (!(advanceMaster.getAdvanceErrorsCollection() == null || advanceMaster
					.getAdvanceErrorsCollection().isEmpty())) {
				canSubmit = advanceService.submitWithCbErrors(subject,
						advanceMaster.getAdvanceErrorsCollection());
			}
			if (!canSubmit) {
				addFieldError("errors",
						"Submission Failed - Please check grid below for errors");
				canSubmit = false;
			}
			return canSubmit;
		} else {
			return true;
		}
	}

	/**
	 * Action for advance submission
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String submitAdvance() throws TimeAndExpenseException {

		String result = IConstants.SUCCESS;

		String approverComments = "";
		paramMap = super.getRequest().getParameterMap();
		if (paramMap.containsKey("approverComments"))
			approverComments = paramMap.get("approverComments")[0];

		advanceMaster = (AdvanceMasters) session
				.get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);
		if (advanceMaster == null){
			// in case an advance was not saved in session beforehand due validation errors
			addFieldError("errors", "Please Save advance successfully prior to submission");
			return "failure";
		}
		advanceMaster = entityManager.merge(advanceMaster);
		
		if (!validateAdvanceSubmitApproveEndDate(advanceMaster)){
			return IConstants.FAILURE;
		}
		
		try {
			if (canSubmitAdvanceWithErrors(advanceMaster, super
					.getUserSubject())) {
				advanceMaster = advanceService.submitAdvance(advanceMaster,
						super.getUserSubject(), super.getLoggedInUser(),
						approverComments);
				advanceService.emailNotification(advanceMaster, super
						.getUserSubject(), super.getLoggedInUser(), approverComments);
			} else {
				return "";
			}

			entityManager.flush();

			this.addErrors();
			// this refresh is done is for modify-by-approver scenario
			// when access to next action code is needed
			entityManager.refresh(advanceMaster); 
			session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER,
					advanceMaster);
			String jsonResponse = "{status:'" + advanceMaster.getStatus() + "'"
					+ ", revision:" + advanceMaster.getRevisionNumber()
					+ ", outstandingAmount:"
					+ advanceMaster.getAdevIdentifier().getOutstandingAmount()
					+ ", manualDepositAmount:"
					+ this.getManualDepositAmountforJsonResponse()
					+ "}";
			super.setJsonResponse(jsonResponse);
		} catch (OptimisticLockException e) {
			addFieldError("errors", "The record you are attempting to change has already been modified by another user.  Please reopen the record before making any changes.");
		} catch (Exception e) {
			logger.error("Exception occured while trying to submit advance", e);
			throw new TimeAndExpenseException(e.getMessage());
		}
		return result;
	}

	/**
	 * Action for advance approval
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public String approveAdvance() throws TimeAndExpenseException {

		String result = IConstants.SUCCESS;
		UserSubject subject = super.getUserSubject();
		UserProfile profile = super.getLoggedInUser();
		String approverComments = "";
		
		//AI 19898 kp 
        String jsonResponse = "";
		
		if (!apptService.isEmployeeActive(getUserSubject().getEmployeeId())){
			jsonResponse = "{status:'" + "EMPLOYEE_STATUS_INVALID'"
			+ "}";
			super.setJsonResponse(jsonResponse);
			return IConstants.FAILURE;
		} //kp
		
		advanceMaster = (AdvanceMasters) session
		.get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);
		advanceMaster = entityManager.merge(advanceMaster);
		// check user security for next action code
		String nextActionCode = advanceService.getLatestAction(advanceMaster)
		.get(0).getNextActionCode();
		String moduleAction = commonService.getRefCode(nextActionCode);
		if (this.checkSecurity(moduleAction) < IConstants.SECURITY_UPDATE_MODULE_ACCESS ) {
			// user does not have approval access for next action code
			throw new TimeAndExpenseException("Approval security failure");
		}
		
		//validating advance approval end date. 
		if (!validateAdvanceSubmitApproveEndDate(advanceMaster)){
			String strAdvanceEndDate = commonService.getSystemCodeValue(IConstants.ADVANCE_SUBMIT_APPROVE_END_DATE_SYSTEM_CODE);
			String strErrorMessage = commonService.getErrorCode(IConstants.ADVANCE_SUBMIT_APPROVE_END_DATE).getErrorText();			
			//Replacing date place holder with date from DB and stripping off time. 
			strErrorMessage = strErrorMessage.replace("{0}", strAdvanceEndDate.substring(0, strAdvanceEndDate.indexOf(" ")));
				
			jsonResponse = "{status:'" + "ADVANCE_APPROVING_AFTER_END_DATE'"
					+ ", msg:'" + strErrorMessage + "'}";
			super.setJsonResponse(jsonResponse);
			return IConstants.FAILURE;			
		}

		
		paramMap = super.getRequest().getParameterMap();
		if (paramMap.containsKey("approverComments"))
			approverComments = paramMap.get("approverComments")[0];

		advanceMaster = (AdvanceMasters) session
				.get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);
		advanceMaster = entityManager.merge(advanceMaster);
		try {
			advanceMaster = advanceService.approveAdvance(advanceMaster,
					subject, profile, approverComments);

			if (advanceMaster.getStatus().equals(IConstants.APPROVED)) {
				advanceService.emailNotification(advanceMaster, subject,
						profile, approverComments);
			}

			entityManager.flush();
			this.addErrors();


			session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER,
					advanceMaster);
			jsonResponse = "{status:'" + advanceMaster.getStatus()
					+ "', outstandingAmount:"
					+ advanceMaster.getAdevIdentifier().getOutstandingAmount()
					+ ", manualDepositAmount:"
					+ this.getManualDepositAmountforJsonResponse()
					+ "}";
			super.setJsonResponse(jsonResponse);
		}  catch (OptimisticLockException e) {
			addFieldError("errors", "The record you are attempting to change has already been modified by another user.  Please reopen the record before making any changes.");
		}  catch (Exception e) {
			logger.error("Exception occured while trying to approve advance", e);
			throw new TimeAndExpenseException(e.getMessage());
		}

		return result;
	}

	/**
	 * Action for advance rejection
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public String rejectAdvance() throws TimeAndExpenseException {

		String result = IConstants.SUCCESS;
		UserSubject subject = super.getUserSubject();
		UserProfile profile = super.getLoggedInUser();
		// String approverComments =
		// super.getRequest().getParameter("approverComments");
		if (paramMap == null)
			paramMap = super.getRequest().getParameterMap();
		String approverComments = paramMap.get("approverComments")[0];
		advanceMaster = (AdvanceMasters) session
				.get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);
		advanceMaster = entityManager.merge(advanceMaster);
		try {
			advanceMaster = advanceService.rejectAdvance(advanceMaster, subject, profile,
					approverComments);
			advanceService.emailNotification(advanceMaster, subject, profile, approverComments);

			entityManager.flush();

			this.addErrors();
			session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER,
					advanceMaster);
			// check to see if Modify button can be enabled after a rejection
			boolean allowModification = canModify();
			String jsonResponse = "{status:'" + advanceMaster.getStatus() + "', " 
			 + "allowModification:'" + allowModification + "'}";
			super.setJsonResponse(jsonResponse);
		} catch (OptimisticLockException e) {
			addFieldError("errors", "The record you are attempting to change has already been modified by another user.  Please reopen the record before making any changes.");
		} catch (Exception e) {
			logger.error("Exception occured while trying to reject advance", e);
			throw new TimeAndExpenseException(e.getMessage());
		}

		return result;
	}

	/**
	 * Action for getting previous advance revision
	 * 
	 * @return
	 */

	public String getPreviousRevision() {
		AdvanceMasters currentAdvance = (AdvanceMasters) session
				.get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);

		if (currentAdvance != null) {
			advanceMasterId = currentAdvance.getAdvmIdentifier();
		}

		if (currentAdvance != null) {
			advanceMaster = advanceService.getAdvanceMasterByAdvanceEventId(
					currentAdvance.getAdevIdentifier(), currentAdvance
							.getRevisionNumber() - 1);
			session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER,
					advanceMaster);
		}

		this.setupDisplay(MODE_DISABLED);
		return IConstants.SUCCESS;
	}

	/**
	 * Action for getting next advance revision
	 * 
	 * @return
	 */

	public String getNextRevision() {

		AdvanceMasters currentAdvance = (AdvanceMasters) session
				.get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);

		// set expense master Id to current. If next not found then we'll show
		// the current one.
		if (currentAdvance != null) {
			advanceMasterId = currentAdvance.getAdvmIdentifier();
		}

		if (currentAdvance != null) {
			advanceMaster = advanceService.getAdvanceMasterByAdvanceEventId(
					currentAdvance.getAdevIdentifier(), currentAdvance
							.getRevisionNumber() + 1);
			session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER,
					advanceMaster);
		}

		if (advanceMaster.getRevisionNumber() < currentAdvance
				.getRevisionNumber()) {
			// higher revision exists but is not displayed
			this.setupDisplay(MODE_DISABLED);
		} else {
			// this is the highest revision
			this.setupDisplay(MODE_INQUIRY_UPDATE);
		}
		return IConstants.SUCCESS;
	}

	/**
	 * Action for advance save or update. The data comes from the display
	 * instance variable as well as the request parameters (used for coding
	 * block data)
	 * 
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public String save() throws TimeAndExpenseException {
		/*
		 * if (accessMode == IConstants.SECURITY_NO_MODULE_ACCESS) return
		 * IConstants.FAILURE;
		 */
		String result = IConstants.SUCCESS;
		String jsonResponse = "";

		AdvanceMasters advanceMastersInSession = (AdvanceMasters) session
				.get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);

		if (this.checkInput(advanceMastersInSession)) {
			// setup a new or existing advance master
			try {
    			advanceMaster = this.getAdvanceMaster(advanceMastersInSession,
    					super.getLoggedInUser());
    			// validate request amount
    			if (!this.validateRequestAmount()){
    				entityManager.getTransaction().rollback();
    				return IConstants.FAILURE;
    			}
    
    			if (paramMap == null)
    				paramMap = super.getRequest().getParameterMap();

			

				if (paramMap != null) {
					if (!this.processInputForm())
						return IConstants.FAILURE;
				}

				advanceMaster = advanceService.saveAdvance(advanceMaster, super
						.getUserSubject().getAppointmentId(), super
						.getModuleIdFromSession(), super.getLoggedInUser(),
						super.getUserSubject());
				
				DisplayAdvance treqData = (DisplayAdvance) session.get("ADVANCE_FROM_REQUISITION");
				if (treqData != null){
					TravelReqEvents treqEvent = treqData.getRelatedTravelRequisition();
					treqService.updateTravelRequisition(treqEvent.getTreqeIdentifier(), advanceMaster.getAdevIdentifier().getAdevIdentifier());
					synchronized(session){
						session.remove("ADVANCE_FROM_REQUISITION");
					}		
				}
				
				// flush changes to DB
				entityManager.flush();
				
				String canSubmit = "true";

				if (advanceMaster.getRevisionNumber() > 0) {
					// this is a modification scenario. Check and see if a
					// submission can be allowed
					if (!canSubmitAdvanceWithErrors(advanceMaster, super
							.getUserSubject())) {
						canSubmit = "false";
					}
				}

				this.addErrors();

				if (advanceMaster.getRevisionNumber() > 0
						&& "false".equals(canSubmit)) {
					entityManager.getTransaction().rollback();
					advanceMaster = (AdvanceMasters) session
							.get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);
				} else {
					session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER,
							advanceMaster);
					session.put(IConstants.ADVANCE_MAX_REVISION_NUMBER,
							advanceMaster.getRevisionNumber());
				}

				jsonResponse = "{advanceEventId:"
						+ advanceMaster.getAdevIdentifier().getAdevIdentifier()
						+ ", revision:" + advanceMaster.getRevisionNumber()
						+ ", canSubmit:" + "\"" + canSubmit + "\"" + "}";

				super.setJsonResponse(jsonResponse);
			} catch (OptimisticLockException e) {
				addFieldError("errors", "The record you are attempting to change has already been modified by another user.  Please reopen the record before making any changes.");
			} catch (Exception e) {
				logger.error("Exception occured while trying to save advance", e);
				throw new TimeAndExpenseException(e.getMessage());
			}

			logger.info("New Advance Saved. Json response is: " + jsonResponse);
			// advanceMaster = entityManager.merge(advanceMaster);

		} else
			result = IConstants.FAILURE;
		return result;
	}
	
	/**
	 * Sets up the instance of an Advance Master, either new or retrieved from session. New revision of 
	 * an advance is created if necessary
	 * 
	 * @param advanceMastersInSession
	 * @param userProfile
	 * @return
	 */

	private AdvanceMasters getAdvanceMaster(
			AdvanceMasters advanceMastersInSession, UserProfile userProfile) {

		if (advanceMastersInSession == null) {
			advanceMaster = new AdvanceMasters();
			advanceMaster.setStatus(IConstants.STRING_BLANK);
		} else {
			// existing advance
			advanceMaster = advanceMastersInSession;
			advanceMaster = entityManager.merge(advanceMaster);
			if (ActionHelper.statusEqualOrGreaterThanSubmit(advanceMaster)) {
				// create a new revision
				AdvanceMasters prevAdvanceMaster = advanceMaster;
				prevAdvanceMaster.setCurrentInd("N");
				prevAdvanceMaster.setModifiedUserId(userProfile.getUserId());
				advanceMaster = advanceService.createNewRevision(
						prevAdvanceMaster, userProfile);
			}
		}

		return advanceMaster;
	}

	/**
	 * parse input data for save processing
	 */

	private boolean processInputForm() {
		AdvanceDetails details = null;
		int numValues = 0;

		if (advanceMaster.getAdvanceDetailsCollection() == null
				|| advanceMaster.getAdvanceDetailsCollection().isEmpty()) {
			details = new AdvanceDetails();
			details.setAdvmIdentifier(advanceMaster);
			ArrayList<AdvanceDetails> detailsList = new ArrayList<AdvanceDetails>();
			detailsList.add(details);
			advanceMaster.setAdvanceDetailsCollection(detailsList);
		} else
			details = advanceMaster.getAdvanceDetailsCollection().get(0);

		boolean includesCodingBlock = false;

		if (paramMap.containsKey("pct")) {
			String pct = paramMap.get("pct")[0];
			if (!StringUtils.isEmpty(pct)) {
				includesCodingBlock = true;
				numValues = paramMap.get("pct").length;
			} else
				numValues = 1;
		} else {
			numValues = 1;
		}

		advanceMaster.setRequestDate(TimeAndExpenseUtil
				.getDateFromString(display.getRequestDate()));
		advanceMaster.setFromDate(TimeAndExpenseUtil.getDateFromString(display
				.getFromDate()));
		advanceMaster.setToDate(TimeAndExpenseUtil.getDateFromString(display
				.getToDate()));

		details.setDollarAmount(Double.parseDouble(display.getDollarAmountForEditing()));
		advanceMaster.setAdvanceReason(display.getAdvanceReason());
		this.processPermanentAdvInd();
		this.processInputManualWarrant();
		this.processInputManualDeposit();

		if (includesCodingBlock) {
			// proceed to process input coding blocks
			if (!this.processAdvanceCodingBlocks()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Change the store in case of a new appropriation year
	 * 
	 * @return
	 * @throws Exception
	 */

	public String getNewStoreForApprYear() throws Exception {

		codingBlockService = new CodingBlockDSP(entityManager);

		TkuoptTaOptions tkuOptTaOptions = codingBlockService.getCBMetaData(
				super.getUserSubject().getDepartment(), super.getUserSubject()
						.getAgency(), super.getUserSubject().getTku());

		advanceMaster = (AdvanceMasters) session
				.get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);
		String returnStoreJson = "";
		if (advanceMaster != null)
			returnStoreJson = codingBlockService.prepareCodingBlockStoreData(
					Calendar.getInstance().getTime(), super.getUserSubject()
							.getDepartment(), super.getUserSubject()
							.getAgency(), super.getUserSubject().getTku(),
					apprYear, tkuOptTaOptions);
		else
			returnStoreJson = codingBlockService.prepareCodingBlockStoreData(
					Calendar.getInstance().getTime(), super.getUserSubject()
							.getDepartment(), super.getUserSubject()
							.getAgency(), super.getUserSubject().getTku(),
					apprYear, tkuOptTaOptions);
		setJsonResponse(returnStoreJson);

		return IConstants.SUCCESS;
	}

	/**
	 * parse and prepare input coding block data from the input request string
	 */

	private boolean processAdvanceCodingBlocks() {

		String facsAgency = apptService.getFacsAgency(advanceMaster.getFromDate(),
				advanceMaster.getToDate(), super.getUserSubject().getAppointmentId());

		List<AdvanceDetailCodingBlocks> newCodingBlockList = new ArrayList<AdvanceDetailCodingBlocks>();

		String vals[] = null;

		String std = "";
		vals = paramMap.get("pct");

		for (int i = 0; i < vals.length; i++) {
			AdvanceDetailCodingBlocks codingBlock = new AdvanceDetailCodingBlocks();
			String value = paramMap.get("pct")[i];
			codingBlock.setPercent(Double.valueOf(value) * .01);

			value = paramMap.get("ay")[i];
			if (StringUtils.isEmpty(value)) {
				// no AY so this must be std
				std = paramMap.get("std")[0];
				codingBlock.setStandardInd("Y");
				includesCbStd = true;
			} else {
				// not std. Get other elements out of params map
				codingBlock.setAppropriationYear(value);
				codingBlock.setIndexCode(ActionHelper.getCodeFromJsonString(
						paramMap, "index", i));
				codingBlock.setPca(ActionHelper.getCodeFromJsonString(paramMap,
						"pca", i));
				codingBlock.setGrantNumber(ActionHelper.getCodeFromJsonString(
						paramMap, "grant", i));
				codingBlock.setGrantPhase(ActionHelper.getCodeFromJsonString(
						paramMap, "grantPhase", i));
				codingBlock.setProjectNumber(ActionHelper
						.getCodeFromJsonString(paramMap, "project", i));
				codingBlock.setProjectPhase(ActionHelper.getCodeFromJsonString(
						paramMap, "projectPhase", i));
				codingBlock.setAgencyCode1(ActionHelper.getCodeFromJsonString(
						paramMap, "ag1", i));
				codingBlock.setAgencyCode2(ActionHelper.getCodeFromJsonString(
						paramMap, "ag2", i));
				codingBlock.setAgencyCode3(ActionHelper.getCodeFromJsonString(
						paramMap, "ag3", i));
				codingBlock.setMultipurposeCode(ActionHelper
						.getCodeFromJsonString(paramMap, "multi", i));
				codingBlock.setStandardInd("N");
			}
			codingBlock.setAdvdIdentifier(advanceMaster
					.getAdvanceDetailsCollection().get(0));
			codingBlock.setFacsAgy(facsAgency);
			newCodingBlockList.add(i, codingBlock);
		}
		// validate coding blocks
		if (!this.checkInputCodingBlock(newCodingBlockList))
			return false;

		if (advanceMaster.getAdvanceDetailsCollection().get(0)
				.getAdvanceDetailCodingBlocksCollection() == null
				|| advanceMaster.getAdvanceDetailsCollection().get(0)
						.getAdvanceDetailCodingBlocksCollection().isEmpty())
			// new coding blocks
			advanceMaster.getAdvanceDetailsCollection().get(0)
					.setAdvanceDetailCodingBlocksCollection(newCodingBlockList);

		else {
			// updates to existing data
			if (!advanceMaster.getAdvanceDetailsCollection().get(0)
					.getAdvanceDetailCodingBlocksCollection().isEmpty())
				advanceCodingBlockService.mergeCodingBlocks(advanceMaster,
						newCodingBlockList, includesCbStd);
		}

		return true;

	}

	/**
	 * prepare ManualWarrant for save processing
	 */

	private void processInputManualWarrant() {
		if (!StringUtils.isEmpty(StringUtils.trim(display
				.getManualWarrantIssdInd()))) {
			if (display.getManualWarrantIssdInd().equals("Y")) {
				advanceMaster.setManualWarrantIssdInd("Y");
				advanceMaster.setManualWarrantDocNum(display
						.getManualWarrantDocNum());
			} else {
				advanceMaster.setManualWarrantIssdInd("N");
				advanceMaster.setManualWarrantDocNum("");
			}
		} else {
			advanceMaster.setManualWarrantIssdInd("N");
			advanceMaster.setManualWarrantDocNum("");
		}
	}

	/**
	 * prepare ManualDeposit for save processing
	 */

	private void processInputManualDeposit() {
		double manualDepositAmount = 0;
		if (!StringUtils.isEmpty(display.getManualDepositAmount())) {
			manualDepositAmount = Double.parseDouble(display
					.getManualDepositAmount());
		}
		if (!StringUtils.isEmpty(display.getManualDepositDocNum())
				|| manualDepositAmount > 0) {
			// got a document number
			advanceMaster.setManualDepositInd("Y");
			advanceMaster.setManualDepositAmount(manualDepositAmount);
			advanceMaster.setManualDepositDocNum(display
					.getManualDepositDocNum());
		} else
			advanceMaster.setManualDepositInd("N");
	}

	/**
	 * prepare Permanent for save processing
	 */

	private void processPermanentAdvInd() {
		if (advanceMaster.getAdevIdentifier() != null &&
				advanceService.previouslyProcessedRevisionExists(advanceMaster))
			// do not extract checkbox value if advance has been processed
			return;
		if (display.getPermanentAdvInd().equals("true"))
			advanceMaster.setPermanentAdvInd("Y");
		else
			advanceMaster.setPermanentAdvInd("N");
	}

	/**
	 * Prepares business error messages to be displayed in the grid
	 */

	private void addErrors() {
		if (advanceMaster.getAdvanceErrorsCollection() != null) {
			if (!advanceMaster.getAdvanceErrorsCollection().isEmpty()) {
				for (AdvanceErrors advError : advanceMaster
						.getAdvanceErrorsCollection()) {
					String code = advError.getErrorCode().getErrorCode();
					ErrorMessages error = commonService.getErrorCode(code);
					String description = error.getErrorText();
					String severity = error.getSeverity();
					Long errorIdentifier = 0L;
					if (advError.getAdveIdentifier() != null)
						errorIdentifier = advError.getAdveIdentifier()
								.longValue();
					else
						errorIdentifier = System.nanoTime();
					TimeAndExpenseError displayError = new TimeAndExpenseError(
							errorIdentifier.intValue(), code, description,
							severity, advError.getErrorSource());
					super.addTimeAndExpenseError(displayError);
				}
			}
		}

	}

	private void setupDisplayErrors() {
		if (advanceMaster == null
				|| advanceMaster.getAdvanceErrorsCollection() == null
				|| advanceMaster.getAdvanceErrorsCollection().isEmpty())
			return;

		List<TimeAndExpenseError> errorsList = new ArrayList<TimeAndExpenseError>();
		for (AdvanceErrors advanceError : advanceMaster
				.getAdvanceErrorsCollection()) {
			// format Coding Block errors to match up with the displayed CB line numbers. Otherwise,
			// display the error source
			AgencyOptions cbAgencyOptions = codingBlockService.getCBAgencyOptions(getUserSubject().getDepartment(), getUserSubject().getAgency());
			ErrorMessages errorCode = advanceError.getErrorCode();
			String errorSource = advanceError.getErrorSource();
			String severity = errorCode.getSeverity();
			if (!IConstants.ADVANCE_ERROR_SOURCE.equals(errorSource)) {
				errorSource = ActionHelper.formatCbErrorSource(advanceMaster, errorSource);
				if ("Y".equals(cbAgencyOptions.getAllowInvalidCbElementsInd())) {
					severity = IConstants.STRING_WARNING;
				}
			}
			errorsList.add(new TimeAndExpenseError(advanceError
					.getAdveIdentifier(), errorCode
					.getErrorCode(),
					errorCode.getErrorText(), severity,
					errorSource));

		}

		errorsJson = jsonParser.toJson(errorsList);
		
		logger.info("existing errors: " + errorsJson);
	}

	/**
	 * Performs data input validation
	 */

	private boolean checkInput(AdvanceMasters advanceMasterInSession) {
		Date requestDate = TimeAndExpenseUtil.getDateFromString(display.getRequestDate());
		if ((StringUtils.isEmpty(display.getRequestDate()))
				|| StringUtils.isEmpty(display.getDollarAmountForEditing())
				|| ((StringUtils.isEmpty(display.getFromDate()))
						|| (StringUtils.isEmpty(display.getToDate())) || (StringUtils
						.isEmpty(display.getAdvanceReason())))) {
			addFieldError("errors",
					"Please enter all required fields indicated by *");
			return false;
		}
		
		if (!checkAppointmentHistoryManagerSw(getModuleIdFromSession(), requestDate))
			return false;
		

		if (Double.parseDouble(display.getDollarAmountForEditing()) >= 100000) {
			addFieldError("errors",
					"Requested advance amount not allowed. Please try a lesser amount.");
			return false;
		}

		if (!StringUtils.isEmpty(display.getManualDepositAmount())) {
			if (Double.parseDouble(display.getManualDepositAmount()) >= 100000) {
				addFieldError("errors",
						"Manual deposit amount not allowed. Please try a lesser amount.");
				return false;
			}
		}

		//if (advanceMasterInSession == null) {
			// only do this check for initial advance creation when access is
			// employee
			if (super.getModuleIdFromSession().equals(IConstants.ADVANCE_EMPLOYEE) &&
					!super.getUserSubject().isSingleAppointmentChosen()) {
				if (!this.validateUniqueAppointmentForAdvanceDates()) {
					return false;
				}
			}
		//}
		
		// check module security for last action
		if (!StringUtils.isEmpty(advanceMaster.getStatus())) {
			if (!this.getSecurityModuleAccess())
				return false;
		}
		
		Integer payType = validatePayType();
		// check for valid pay type 
		if ( payType == null){
			addFieldError("errors", "Advance Pay Type not valid for request date.");
			return false;
		}
		
		// check for valid expense type for the request date
		if ( !validateAdvanceExpenseType(payType)){
			addFieldError("errors", "Advance Expense Type not valid for request date.");
			return false;
		}

		//Validating Advance DEAD END date. Should be less than the system defined date.
		Date advanceEndDate = null;
		String strAdvanceEndDate = commonService.getSystemCodeValue(IConstants.ADVANCE_END_DATE_SYSTEM_CODE);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		
		Boolean isAdvanceAfterEndDate = false;
		
		logger.info("Validating Advance End Date: strAdvanceEndDate: " + strAdvanceEndDate);
		
		
		try {
			Date todaysDate =   new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date()));
			advanceEndDate = sdf.parse(strAdvanceEndDate);
			logger.info("Validating Advance End Date: todaysDate: " + todaysDate);
			if (display.getRequestDate() != null
					&&  todaysDate.after(advanceEndDate)){
				
				isAdvanceAfterEndDate = true;
				
				logger.info("isAdvanceAfterEndDate:" + isAdvanceAfterEndDate);
				
				String strErrorMessage = commonService.getErrorCode(IConstants.ADVANCE_SAVE_END_DATE).getErrorText();			
				//Replacing date place holder with date from DB and stripping off time. 
				strErrorMessage = strErrorMessage.replace("{0}", strAdvanceEndDate.substring(0, strAdvanceEndDate.indexOf(" ")));

				
				addFieldError("errors",	strErrorMessage);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		
		// Validating Advance Request date. Should be less than the system
		// defined date.
		if (!isAdvanceAfterEndDate) {
			Date advanceRequestEndDate = null;
			String strAdvanceRequestEndDate = commonService
					.getSystemCodeValue(IConstants.ADVANCE_REQUEST_END_DATE_SYSTEM_CODE);
			SimpleDateFormat advanceRequestEndDate_sdf = new SimpleDateFormat(
					"MM/dd/yyyy");
			
			logger.info("Validating Advance End Date: strAdvanceRequestEndDate: " + strAdvanceRequestEndDate);
			try {
				advanceRequestEndDate = advanceRequestEndDate_sdf
						.parse(strAdvanceRequestEndDate);
				if (display.getRequestDate() != null
						&& requestDate.after(advanceRequestEndDate)){
					
				String strErrorMessage = commonService.getErrorCode(IConstants.ADVANCE_REQUEST_END_DATE).getErrorText();			
				//Replacing date place holder with date from DB. 
				strErrorMessage = strErrorMessage.replace("{0}", strAdvanceRequestEndDate);
				
				addFieldError("errors", strErrorMessage);

				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	private boolean checkAppointmentHistoryManagerSw (String moduleId, Date requestDate){
		if(IConstants.ADVANCE_MANAGER.equals(moduleId) || IConstants.ADVANCE_STATEWIDE.equals(moduleId)){
			// Check if subject has a valid active appointment
			return validateSubjectActiveAppointment(moduleId, requestDate);

		}
		return true;
	}

	private boolean checkInputCodingBlock(List<AdvanceDetailCodingBlocks> cbList) {
		boolean validInput = true;
		// List<AdvanceDetailCodingBlocks> cbList =
		// advanceMaster.getAdvanceDetailsCollection().get(0).getAdvanceDetailCodingBlocksCollection();
		ActionHelper helper = new ActionHelper();
		if (!helper.validatePercentageSumTotal(cbList)) {
			addFieldError("errors", "Coding blocks percent must equal 100.00");
			return false;
		}
		if (cbList.size() > 1) {
			if (helper.validateIdenticalCodingBlock(cbList)) {
				addFieldError("errors", "Duplicate coding block rows detected");
				return false;
			}
		}
		/*
		 * if (!validInput) cbList.clear();
		 */
		return validInput;
	}
	
	/**
	 * Populate advance display bean for an existing advance
	 */

	private void setupDisplayExistingAdvance() {
		display.setRequestDate(TimeAndExpenseUtil.displayDate(advanceMaster
				.getRequestDate()));
		display.setFromDate(TimeAndExpenseUtil.displayDate(advanceMaster
				.getFromDate()));
		display.setToDate(TimeAndExpenseUtil.displayDate(advanceMaster
				.getToDate()));
		List<AdvanceDetails> details = advanceMaster
				.getAdvanceDetailsCollection();
		if (!(details == null) && (!details.isEmpty())) {
			display.setDollarAmount(details.get(0).getDollarAmount());
			display.setDollarAmountForEditing(TimeAndExpenseUtil.displayAmountTwoDigits(details.get(0).getDollarAmount()));
		}
		display.setAdvanceReason(advanceMaster.getAdvanceReason());

		if (!StringUtils.isEmpty(advanceMaster.getPermanentAdvInd())) {
			if (advanceMaster.getPermanentAdvInd().equals("Y"))
				// advanceMaster.setPermanentAdvInd("true");
				display.setPermanentAdvInd("true");
		}

		advanceService.setAdvanceDisplayAmounts(advanceMaster, display);

		if (!StringUtils.isEmpty(advanceMaster.getManualWarrantIssdInd())) {
			display.setManualWarrantIssdInd(advanceMaster
					.getManualWarrantIssdInd());
			display.setManualWarrantDocNum(advanceMaster
					.getManualWarrantDocNum());
		}

		if (!StringUtils.isEmpty(advanceMaster.getManualDepositDocNum()))
			display.setManualDepositDocNum(advanceMaster
					.getManualDepositDocNum());

		if (advanceMaster.getManualDepositAmount() > 0) {
			display.setManualDepositAmount(TimeAndExpenseUtil
					.displayAmountTwoDigits(advanceMaster
							.getManualDepositAmount()));
		}

	}
	
	/**
	 * Called from setupDisplay for button display whether visible/invisible or enabled/disabled
	 * @param mode
	 */

	private void setupDisplayButtons(int mode) {
		if (mode == MODE_DISABLED){
			this.disableAllButtons();
			// buttons to flip between revisions
			this.setupRevisionButtons();
			return;
		}
		if (super.getModuleIdFromSession().equals(IConstants.APPROVE_WEB_MANAGER)
				|| super.getModuleIdFromSession().equals(IConstants.APPROVE_WEB_STATEWIDE)) {
			this.setupDisplayButtonsManagerSw();
		} else {
			// employee access
			this.setupDisplayButtonsEmployee();
		}

		// buttons to flip between revisions
		this.setupRevisionButtons();
	}
	
	/**
	 * Sets up display button state for Manager or Statewide access
	 */

	private void setupDisplayButtonsManagerSw() {
		display.setDisplaySaveButton("none");
		display.setDisplaySubmitButton("none");
		display.setDisplayApproveButton("inline");
		display.setDisplayApproveWithCommentsButton("inline");
		display.setDisplayApproveNextButton("inline");
		display.setDisplayApproveSkipButton("inline");
		display.setDisplayRejectButton("inline");
		if (ActionHelper.statusEqualOrGreaterThanSubmit(advanceMaster)) {
			if (super.getModuleIdFromSession().equals(IConstants.APPROVE_WEB_MANAGER)) {
				String lastAction = advanceService.getLatestAction(
						advanceMaster).get(0).getActionCode();
				if (IConstants.APPROVAL_STEP1.equals(lastAction)) {
					// manager has already approved. this is done to guard
					// against a page refresh in manager approvals
					setDisableApprovalsButtons(true);
				} else {
					setDisableApprovalsButtons(false);
				}
			} else {
				// Statewide Access
				if (IConstants.APPROVED.equals(advanceMaster.getStatus())) {
					setDisableApprovalsButtons(true);
				} else {
					setDisableApprovalsButtons(false);
				}
			}
			if (this.disableModifyButtonForAction()) {
				// user does not have security access for last action
				display.setDisableModifyButton(true);
			} else {
				display.setDisableModifyButton(false);
			}
		}
		// ZH, 05/11/2010 - Added security check for next action code when advance is being approved
		if (checkSecurityNextAction() < IConstants.SECURITY_UPDATE_MODULE_ACCESS){
			setDisableApprovalsButtons(true);
		}
	}
	
	/**
	 * Checks security for the module id associated with next action code, if applicable
	 * @return
	 */
	
	private int checkSecurityNextAction() {
		String nextActionCode = advanceService.getLatestAction(advanceMaster)
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
	
	/**
	 * Sets up button display for employee access
	 */

	private void setupDisplayButtonsEmployee() {
		if (ActionHelper.statusEqualOrGreaterThanSubmit(advanceMaster)) {
			display.setDisplaySaveButton("inline");
			display.setDisplaySubmitButton("inline");
			display.setDisableSaveButton(true);
			display.setDisableSubmitButton(true);
			display.setDisableModifyButton(false);
			display.setDisplayApproveButton("none");
			display.setDisplayApproveWithCommentsButton("none");
			display.setDisplayApproveNextButton("none");
			display.setDisplayApproveSkipButton("none");
			display.setDisplayRejectButton("none");
			if (this.disableModifyButtonForAction()) {
				// user does not have security access for last action
				display.setDisableModifyButton(true);
			} else {
				display.setDisableModifyButton(false);
			}

		}
	}

/**
 * Performs security check for a given module
 * @param moduleId
 * @return
 */
	private int checkSecurity(String moduleId) {
		empInfo = (EmployeeHeaderBean) session.get(IConstants.EMP_HEADER_INFO);
		UserProfile profile = super.getLoggedInUser();
		return securityService.getModuleAccessMode(profile, moduleId, super
				.getUserSubject().getDepartment(), super.getUserSubject()
				.getAgency(), super.getUserSubject().getTku());
	}
	
	/**
	 * Sets up display for previous and next revisions
	 */

	private void setupRevisionButtons() {
		Integer maxRevision = (Integer) session
				.get(IConstants.ADVANCE_MAX_REVISION_NUMBER);
		if (maxRevision == null)
			return;

		if (maxRevision == 0) {
			display.setDisablePreviousButton(true);
			display.setDisableNextButton(true);
		} else if (maxRevision == advanceMaster.getRevisionNumber()) {
			display.setDisablePreviousButton(false);
			display.setDisableNextButton(true);
		} else {
			if (advanceMaster.getRevisionNumber() < maxRevision)
				display.setDisableNextButton(false);
			else
				display.setDisableNextButton(true);
			if (advanceMaster.getRevisionNumber() > 0)
				display.setDisablePreviousButton(false);
			else
				display.setDisablePreviousButton(true);

		}
	}
	
	/**
	 * Disables all buttons that may have been displayed on the advance form
	 */

	private void disableAllButtons() {
		display.setDisableSaveButton(true);
		display.setDisableSubmitButton(true);
		display.setDisableApproveButton(true);
		display.setDisableApproveWithCommentsButton(true);
		display.setDisableApproveNextButton(true);
		display.setDisableApproveSkipButton(true);
		display.setDisableRejectButton(true);
		display.setDisableModifyButton(true);
	}

	private void setDisableApprovalsButtons(boolean flag) {
		display.setDisableApproveButton(flag);
		display.setDisableApproveWithCommentsButton(flag);
		display.setDisableApproveNextButton(flag);
		display.setDisableApproveSkipButton(flag);
		display.setDisableRejectButton(flag);
	}

	public boolean validateUniqueAppointmentForAdvanceDates() {

		List<AppointmentsBean> appts = null;
		/*appts = advanceService.getAppointmentsForDateRange(TimeAndExpenseUtil
				.getDateFromString(display.getRequestDate()), TimeAndExpenseUtil
				.getDateFromString(display.getRequestDate()), super
				.getModuleIdFromSession(), super.getUserSubject(), super
				.getLoggedInUser());*/
		
		appts = apptService.findAppointmentHistory(getUserSubject().getEmployeeId(), 
				TimeAndExpenseUtil.getDateFromString(display.getRequestDate()));

		// no appointment exists
		if (appts == null || appts.isEmpty()) {
			addFieldError("errors",
					"No appointment exists for the advance dates");
			return false;
		}
		// multiple appointments exists
		else if (appts.size() > 1
				&& super.getModuleIdFromSession().equals(
						IConstants.ADVANCE_EMPLOYEE)) {
			addFieldError("appointments", jsonParser.toJson(appts));
			session.put(IConstants.MULTIPLE_APPTS_FOR_EXPENSE, appts);
			return false;
		} 
			// push the unique appointment into UserSubject
			ActionHelper helper = new ActionHelper();
			helper.updateUserSubjectWithCorrectAppointment(appts.get(0), super
					.getUserSubject());
			
			// subject has security to create expenses for this appointment.
			if (!checkSecurityAccess()){
				return false;
			}
/*			if(advanceMaster != null && advanceMaster.getRevisionNumber() == 0){
				boolean userSecurityToCreateAdvanceForSelf = securityService.checkModuleAccess(getLoggedInUser(), 
																getModuleIdFromSession(), getUserSubject().getDepartment(), 
																getUserSubject().getAgency(), getUserSubject().getTku());
				if(!userSecurityToCreateAdvanceForSelf){
					addFieldError("errors", "You are not allowed to create/update the advance for the request date.");
					return false;
				}
			}*/
		return true;
	}
	
	private boolean checkSecurityAccess (){
		if(advanceMaster != null && advanceMaster.getRevisionNumber() == 0){
			boolean userSecurityToCreateAdvanceForSelf = securityService.checkModuleAccess(getLoggedInUser(), 
															getModuleIdFromSession(), getUserSubject().getDepartment(), 
															getUserSubject().getAgency(), getUserSubject().getTku());
			if(!userSecurityToCreateAdvanceForSelf){
				addFieldError("errors", "You are not allowed to create/update the advance for the request date.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Enables mode for Modify button
	 * 
	 * @return
	 */

	private boolean disableModifyButtonForAction() {
		
		if (!StringUtils.isEmpty(advanceMaster.getCurrentInd()) &&
				"N".equals(advanceMaster.getCurrentInd()))
			return true;

		if (advanceMaster.getStatus().equals(IConstants.EXTRACTED)
				|| advanceMaster.getStatus().equals(IConstants.HOURS_ADJUSTMENT_SENT)) {
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

		if (advanceMaster.getStatus().equals(IConstants.PROCESSED)
				&& !module.equals(IConstants.ADVANCE_STATEWIDE)) {
			// only statewide employees may perform modifications after PROC
			return true;
		}

		boolean previouslyProcessed = advanceService
				.previouslyProcessedRevisionExists(advanceMaster);
		if (previouslyProcessed)
			// setup instance variable to be read in JSP
			previouslyPROC = "Y";

		// user must have update access to the adjustments module
		if (IConstants.PROCESSED.equals(advanceMaster.getStatus())
				|| previouslyProcessed) {
			if (this.checkSecurity(IConstants.ADVANCE_ADJUSTMENT_MODULE) < IConstants.SECURITY_UPDATE_MODULE_ACCESS) {
				return true;
			}
		}

		// Check to see if logged in user is subject employee
		// or if user does not have access to the Next_Action security module
		if (!this.getSecurityModuleAccess())
			return true;

		// ever been processed before. If so, only statewide may Modify the
		// advance
		if (advanceMaster.getRevisionNumber() > 0) {
			if (previouslyProcessed
					&& !module.equals(IConstants.ADVANCE_STATEWIDE)
					&& !module.equals(IConstants.APPROVE_WEB_STATEWIDE)) {
				return true;
			}
		}
		
		// ZH, 06/04/2010 - fixed defect # 133, do not allow modification if liquidations exist for the advance
		// and the expense is in XTCT or HSNT status
		if (advanceService.existLiquidationsExpenseXTCTorHSNT(advanceMaster)){
			return true;
		}

		return false;
	}

	/**
	 * Checks user access for enabling Modify button
	 * 
	 * @return
	 */

	private boolean getSecurityModuleAccess() {
		// ZH commented per defect # 19 in Test, per direction by user
		/*if (super.getLoggedInUser().getEmpIdentifier() == super
				.getUserSubject().getEmployeeId()) {
			// user is also subject. Modify should be disabled
			return false;
		}*/

		if (advanceMaster.getAdvanceActionsCollection() != null
				&& !advanceMaster.getAdvanceActionsCollection().isEmpty()) {
			// if in approval path, check access for approval modules through
			// ref_codes
			String actionCode = advanceService.getLatestAction(advanceMaster)
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
					String nextActionCode = advanceService.getLatestAction(advanceMaster)
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

	/**
	 * Validates new amount in case of advance modifications according to the
	 * following formula: fail if new_amount + current_outstanding_amount <
	 * prior_requested_amount .
	 * 
	 * This is done to guard against modifications when liquidations are present
	 * 
	 * @return
	 */
	private boolean validateRequestAmount() {
		if (advanceMaster != null && advanceService.isStatusGreaterThanSubmit(advanceMaster)) {
			double requestAmount = Double.parseDouble(display.getDollarAmountForEditing());
			if (requestAmount
					+ advanceMaster.getAdevIdentifier().getOutstandingAmount() < advanceMaster
					.getAdvanceDetailsCollection().get(0).getDollarAmount()) {
				addFieldError("errors",
						"Requested advance amount may not be less than Liquidated amount.");
				return false;
			}

		}
		return true;
	}
	
	private double getManualDepositAmountforJsonResponse() {
		double manualDepositAmount = 0;
		if (advanceMaster != null && advanceService.isStatusGreaterThanSubmit(advanceMaster)) {
			manualDepositAmount = TimeAndExpenseUtil.roundToTwoDigits(
					advanceMaster.getManualDepositAmount());

		}
		return manualDepositAmount;
	}
	
	/**
	 * Validates status for a given date for the chosen appointment
	 * @param moduleId
	 * @param requestDate
	 * @return
	 */
	
	private boolean validateSubjectActiveAppointment(String moduleId, Date requestDate) {
		UserSubject subject = getUserSubject();
		// 03/08/2010 - Redundant check commented
		//AppointmentHistory apptHistory = null;
		
		// subject has security to create expenses for this appointment.
		if (!checkSecurityAccess()){
			return false;
		}
//SC Issue 162 - Changed to to look at appointmentDate
		if(requestDate.before(subject.getAppointmentDate()) || requestDate.after(subject.getAppointmentEnd())){
			// find the appointment, for the advance request date
			//apptHistory = apptService.getAppointmentHistory(subject.getAppointmentId(), requestDate);
			
			//if(apptHistory == null){
				addFieldError("errors", "No active appointment exists for the chosen advance date.");
				return false;
			//}
		}
		
		return true;
	}
	
	/**
	 * Validates that the advance pay type is valid for request date
	 * @return
	 */
	
	private Integer validatePayType(){
	Date requestDate = TimeAndExpenseUtil.getDateFromString(display.getRequestDate());
	Integer payType = null;
	
	if ("N".equals(display.getManualWarrantIssdInd()) ||
			" ".equals(display.getManualWarrantIssdInd()))
	{
		// cash advance
		payType = advanceService.getPayType(IConstants.ADVANCE_PAYROLL_SYSTEM_CODE,
				requestDate);
	} else {
		// manual deposit
		payType = advanceService.getPayType(IConstants.ADVANCE_MANUAL_WARRANT_SYSTEM_CODE,
				requestDate);
	}
	
	return payType;
	}
	
	/**
	 * Validates that the advance pay type is valid for request date
	 * @return
	 */
	
	private boolean validateAdvanceExpenseType(Integer payType){
	Date requestDate = TimeAndExpenseUtil.getDateFromString(display.getRequestDate());
	Integer payTypeFromExpenseType = advanceService.getAdvanceExpenseType(payType, requestDate);
	
	return payTypeFromExpenseType == null ? false : true;
	}
	
	/**
	 * Establishes security for enabling the modify button after an update action
	 * @return
	 */
	
	private boolean canModify (){
		boolean canModify = true;		
		if (advanceService.previouslyProcessedRevisionExists(advanceMaster)) {
			if (this.checkSecurity(IConstants.ADVANCE_ADJUSTMENT_MODULE) < IConstants.SECURITY_UPDATE_MODULE_ACCESS) {
				canModify = false;
			}
		} else {
			if (this.checkSecurity(getModuleIdFromSession()) < IConstants.SECURITY_UPDATE_MODULE_ACCESS) {
				canModify = false;
			}
		}
		return canModify;
	}
	
	private void createAdvanceDisplayFromRequisition (){
		DisplayAdvance treqData = (DisplayAdvance) session.get("ADVANCE_FROM_REQUISITION");
		if (treqData != null){
			display.setRequestDate(treqData.getRequestDate()); 
			display.setDollarAmount(treqData.getDollarAmount());
			display.setDollarAmountForEditing(treqData.getDollarAmountForEditing());
			display.setFromDate(treqData.getFromDate()); 
			display.setToDate(treqData.getToDate()); 
			display.setAdvanceReason(treqData.getAdvanceReason()); 
		}
		/*synchronized(session){
			session.remove("ADVANCE_FROM_REQUISITION");
		}		*/
	}

	//Validating Advance Submission and Approval end date.
	private boolean validateAdvanceSubmitApproveEndDate(AdvanceMasters advanceMaster){		

		Boolean returnValue = true;
		
		Date advanceEndDate = null;
		String strAdvanceEndDate = commonService.getSystemCodeValue(IConstants.ADVANCE_SUBMIT_APPROVE_END_DATE_SYSTEM_CODE);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		
		Boolean isAdvanceAfterEndDate = false;
		
		logger.info("Validating ADVANCE_SUBMIT_APPROVE_END_DATE_SYSTEM_CODE: strAdvanceEndDate: " + strAdvanceEndDate);
		
		
		try {
			Date todaysDate =   new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date()));
			advanceEndDate = sdf.parse(strAdvanceEndDate);
			logger.info("Validating ADVANCE_SUBMIT_APPROVE_END_DATE_SYSTEM_CODE: todaysDate: " + todaysDate);
			if (advanceMaster.getRequestDate() != null
					&&  todaysDate.after(advanceEndDate)){
				
				isAdvanceAfterEndDate = true;
				
				logger.info("isAdvanceAfterEndDate:" + isAdvanceAfterEndDate);
				returnValue = false;				

				String strErrorMessage = commonService.getErrorCode(IConstants.ADVANCE_SUBMIT_APPROVE_END_DATE).getErrorText();			
				//Replacing date place holder with date from DB and stripping off time. 
				strErrorMessage = strErrorMessage.replace("{0}", strAdvanceEndDate.substring(0, strAdvanceEndDate.indexOf(" ")));

				
				addFieldError("errors", strErrorMessage);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}		
				
		return returnValue;
	}

	/********************* Getters and Setters *********************************/
	
	public String getApprYear() {
		return apprYear;
	}

	public void setApprYear(String apprYear) {
		this.apprYear = apprYear;
	}

	public DisplayAdvance getDisplay() {
		return display;
	}

	public void setDisplay(DisplayAdvance display) {
		this.display = display;
	}

	public int getAdvanceEventId() {
		return advanceEventId;
	}

	public void setAdvanceEventId(int advanceEventId) {
		this.advanceEventId = advanceEventId;
	}

	public void setNoOfCodingBlocks(int noOfCodingBlocks) {
		this.noOfCodingBlocks = noOfCodingBlocks;
	}

	public int getNoOfCodingBlocks() {
		return noOfCodingBlocks;
	}

	public void setCbOptions(String cbOptions) {
		this.cbOptions = cbOptions;
	}

	public String getCbOptions() {
		return cbOptions;
	}

	public Map<String, String[]> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String[]> paramMap) {
		this.paramMap = paramMap;
	}

	public AdvanceMasters getAdvanceMaster() {
		return advanceMaster;
	}

	public void setAdvanceMaster(AdvanceMasters advanceMaster) {
		this.advanceMaster = advanceMaster;
	}

	public void setDefaultCodingBlock(
			DefaultDistributionsAdvAgy defaultCodingBlock) {
		this.defaultCodingBlock = defaultCodingBlock;
	}

	public DefaultDistributionsAdvAgy getDefaultCodingBlock() {
		return defaultCodingBlock;
	}

	public void setSelectedCodingBlockData(String selectedCodingBlockData) {
		this.selectedCodingBlockData = selectedCodingBlockData;
	}

	public String getSelectedCodingBlockData() {
		return selectedCodingBlockData;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setEmpInfo(EmployeeHeaderBean empInfo) {
		this.empInfo = empInfo;
	}

	public EmployeeHeaderBean getEmpInfo() {
		return empInfo;
	}

	public void setBizErrors(String bizErrors) {
		this.errorsJson = bizErrors;
	}

	public String getBizErrors() {
		return errorsJson;
	}

	public void setPreviouslyPROC(String previouslyPROC) {
		this.previouslyPROC = previouslyPROC;
	}

	public String getPreviouslyPROC() {
		return previouslyPROC;
	}

	public int getAdvanceMasterId() {
		return advanceMasterId;
	}

	public void setAdvanceMasterId(int advanceMasterId) {
		this.advanceMasterId = advanceMasterId;
	}

	public int getTreqEventId() {
		return treqEventId;
	}

	public void setTreqEventId(int treqEventId) {
		this.treqEventId = treqEventId;
	}	
}
