package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.exception.ExpenseException;
import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
import gov.michigan.dit.timeexpense.model.core.DriverReimbExpTypeCbs;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.CodingBlockSummaryBean;
import gov.michigan.dit.timeexpense.model.display.DisplayExpenseSummary;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseSummaryDetailsBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseSummaryReportBean;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.AdvanceLiquidationDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.util.ExpenseViewUtil;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;
import gov.michigan.dit.timeexpense.service.AppointmentDSP; 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * Action class for the expense summary tab
 * @author JadcharlaS
 * 
 */

public class ExpenseSummaryAction extends AbstractAction implements ServletRequestAware{
	private static final long serialVersionUID = -7153945847561208325L;
	private ExpenseMasters expenseMaster;
	private ExpenseLineItemDSP expenseLineService;
	private ExpenseDSP expenseService;
	private AdvanceDSP advanceService;
	private AdvanceLiquidationDSP advanceLiquidationService;
	private String approverComments;
	private CommonDSP commonService;
	private Integer expenseMasterId;
	private HttpServletRequest request;
	private SecurityManager securityService;
	private boolean noExpenseDetails = false;
	private AppointmentDSP appointmentService; 
	private DisplayExpenseSummary display;
	private CodingBlockDSP codingBlockService;
	private String supervisorReceiptsReviewed;
	
	/**
	 * Prepares the data for initial view state.
	 * @return void
	 */
	@Override
	public void prepare() {
		expenseLineService = new ExpenseLineItemDSP(entityManager);
		expenseService = new ExpenseDSP(entityManager);
		securityService = new SecurityManager(entityManager);
		commonService = new CommonDSP(entityManager);
		advanceService = new AdvanceDSP(entityManager);
		advanceLiquidationService = new AdvanceLiquidationDSP(entityManager);
		appointmentService = new AppointmentDSP(entityManager); // AI 19898 kp
		display = new DisplayExpenseSummary();
		codingBlockService = new CodingBlockDSP(entityManager);
		
	}	
	/**
	 * Prepares the data for Initial Summary Tab Page
	 * @throws Exception
	 * @return success: Find mapping in struts.xml
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {		
		expenseMaster = (ExpenseMasters) session.get(IConstants.EXPENSE_SESSION_DATA);
		// refresh master state, to enable child fetch operations
		if (expenseMaster != null) {
			expenseMaster = entityManager.merge(expenseMaster);
		}
		UserSubject user = getUserSubject();
		int security = securityService.getModuleAccessMode(getLoggedInUser(), getModuleIdFromSession(), user.getDepartment(), user.getAgency(),user.getTku());
		if (expenseMaster == null)return IConstants.SUCCESS;
		
		List<Object> expenseSummaryObjList = expenseLineService.getExpenseSummaryDetails(expenseMaster, user.getEmployeeId());
		List<ExpenseSummaryDetailsBean> expenseSummaryList = (List<ExpenseSummaryDetailsBean>) expenseSummaryObjList.get(0);
		List<CodingBlockSummaryBean> codingBlockSummaryList = (List<CodingBlockSummaryBean>) expenseSummaryObjList.get(1);
		double liquidatedAmount = (Double) expenseSummaryObjList.get(2);

		double outstandingAdvance = 0;
		if(session.containsKey(IConstants.EXPENSE_TOTAL_OUTSTANDING_ADVANCE_AMOUNT)){
			outstandingAdvance = (Double)session.get(IConstants.EXPENSE_TOTAL_OUTSTANDING_ADVANCE_AMOUNT);
		}else{
			outstandingAdvance = advanceService.getTotalAdvanceOutstandingAmount(user.getEmployeeId()); 
			session.put(IConstants.EXPENSE_TOTAL_OUTSTANDING_ADVANCE_AMOUNT, outstandingAdvance);
		}
		List<Double> expenseAmountsList = (List<Double>) expenseSummaryObjList.get(3);
		// set display amounts for the Summary tab
		setExpenseSummaryAmounts(expenseAmountsList, liquidatedAmount, outstandingAdvance);
		// setup button display and disabled modes
		setupDisplayButtons(expenseMaster, security,user, liquidatedAmount); 
		setJsonResponse(setJsonReponseString(expenseSummaryList, codingBlockSummaryList, expenseAmountsList, liquidatedAmount));		
		request.setAttribute("expenseMasterId",expenseMaster.getExpmIdentifier());		
		// push updated expense master into session
		session.put(IConstants.EXPENSE_SESSION_DATA, expenseMaster);
		//setup data needed for expense reports
		setSummaryReportData(expenseAmountsList, outstandingAdvance, expenseSummaryList, codingBlockSummaryList, liquidatedAmount);
			
		return IConstants.SUCCESS;
	}
	
	private void setExpenseSummaryAmounts(List<Double> expenseAmountsList, double liquidatedAmount, double outstandingAdvance){
		display.setNonTaxableTotalExpenses(TimeAndExpenseUtil.displayAmountTwoDigits((expenseAmountsList.get(1)).doubleValue()));
		display.setTaxableTotalExpenses(TimeAndExpenseUtil.displayAmountTwoDigits((expenseAmountsList.get(0)).doubleValue()));
		display.setTotalExpenses(TimeAndExpenseUtil.displayAmountTwoDigits((expenseAmountsList.get(0)).doubleValue() + (expenseAmountsList.get(1)).doubleValue()));
		display.setTotalDueEmployee(TimeAndExpenseUtil.displayAmountTwoDigits(((expenseAmountsList.get(0)).doubleValue() + (expenseAmountsList.get(1)).doubleValue())-liquidatedAmount));
		display.setTotalOutstandingAdvance(TimeAndExpenseUtil.displayAmountTwoDigits(outstandingAdvance));
		display.setAmountLiquidated(TimeAndExpenseUtil.displayAmountTwoDigits(liquidatedAmount));
	}
	
	private void setSummaryReportData(List<Double> expenseAmountsList, double outstandingAdvance, List<ExpenseSummaryDetailsBean> expenseSummaryList, List<CodingBlockSummaryBean> codingBlockSummaryList, double liquidatedAmount){
		// Push summary data in session for quick access during print summary report
		ExpenseSummaryReportBean summaryReportData = new ExpenseSummaryReportBean();
			// set expense data
			summaryReportData.setExpenseEventId(expenseMaster.getExpevIdentifier().getExpevIdentifier());
			summaryReportData.setExpenseDateFrom(TimeAndExpenseUtil.constructDateString(expenseMaster.getExpDateFrom()));
			summaryReportData.setExpenseDateTo(TimeAndExpenseUtil.constructDateString(expenseMaster.getExpDateTo()));
			
			// pick only 127 chars
			if(expenseMaster.getNatureOfBusiness().length() <= 127)
				summaryReportData.setExpenseBizNature(expenseMaster.getNatureOfBusiness());
			else
				summaryReportData.setExpenseBizNature(expenseMaster.getNatureOfBusiness().substring(0,127));
			
			setSummaryDataExpenseEventDesc(summaryReportData);			
			
			summaryReportData.setExpenseRevision(expenseMaster.getRevisionNumber());
			// set employee data
			EmployeeHeaderBean empInfo = (EmployeeHeaderBean)session.get(IConstants.EMP_HEADER_INFO);
			summaryReportData.setEmployeeId(empInfo.getEmpId());
			summaryReportData.setEmployeeName(empInfo.getEmpName());
			summaryReportData.setEmployeeProcessLevel(empInfo.getProcessLevel());
			summaryReportData.setEmployeeDeptCodeAndName(empInfo.getDeptCode() + " " + empInfo.getDeptName());
			//AI-29299 Added bargaining unit 
			summaryReportData.setBargUnit(empInfo.getBargUnit());
			// set amounts
			summaryReportData.setNonTaxableExpense((expenseAmountsList.get(1)).doubleValue());
			summaryReportData.setTaxableExpense((expenseAmountsList.get(0)).doubleValue());
			summaryReportData.setOutstandingAdvance(outstandingAdvance);
			summaryReportData.setAmountLiquidated(liquidatedAmount);
			//set expense details by category list
			summaryReportData.setDetails(expenseSummaryList);
			//set expense details by category list
			summaryReportData.setCodingBlocks(codingBlockSummaryList);
			
			// put in session
			session.put(IConstants.EXP_SUMMARY_REPORT_DATA, summaryReportData);
	}
	
	private void setSummaryDataExpenseEventDesc(
			ExpenseSummaryReportBean summaryReportData) {
		// pick only 254 chars
		if (expenseMaster.getComments() == null) {
			summaryReportData.setExpenseEventDesc1("");
			summaryReportData.setExpenseEventDesc2("");
		} else if (expenseMaster.getComments().length() <= 127) {
			summaryReportData.setExpenseEventDesc1(expenseMaster.getComments());
			summaryReportData.setExpenseEventDesc2("");
		} else if (expenseMaster.getComments().length() > 127
				&& expenseMaster.getComments().length() <= 254) {
			summaryReportData.setExpenseEventDesc1(expenseMaster.getComments()
					.substring(0, 127));
			summaryReportData.setExpenseEventDesc2(expenseMaster.getComments()
					.substring(127));
		} else {
			summaryReportData.setExpenseEventDesc1(expenseMaster.getComments()
					.substring(0, 127));
			summaryReportData.setExpenseEventDesc2(expenseMaster.getComments()
					.substring(127, 254));
		}
	}
	
	private String setJsonReponseString(List<ExpenseSummaryDetailsBean> expenseSummaryList, List<CodingBlockSummaryBean> codingBlockSummaryList, List<Double> expenseAmountsList, double liquidatedAmount){
		StringBuilder strBuf = new StringBuilder();
		String summary = jsonParser.toJson(expenseSummaryList) + ",";
		String codingBlockSummary = jsonParser.toJson(codingBlockSummaryList)+ ",";
		String liquidateAmount = liquidatedAmount + ",";
		String expAmtList = jsonParser.toJson(expenseAmountsList);
		
		strBuf.append("[");
		strBuf.append(summary);
		strBuf.append(codingBlockSummary);
		strBuf.append(liquidateAmount);
		strBuf.append(",");
		strBuf.append(isExpenseBeingModified());
		strBuf.append(",");
		strBuf.append(expAmtList);
		strBuf.append(",");
		strBuf.append(display.isDisableApproveButton());
		strBuf.append(",");
		strBuf.append(jsonParser.toJson(getModuleIdFromSession()));
		strBuf.append(",");
		strBuf.append(display.isPreAuditRequiredAndCompleted());
		strBuf.append(",");
		strBuf.append(display.isShowSupervisorReceiptsReviewedMessage());
		strBuf.append(",");
		strBuf.append(noExpenseDetails);
		strBuf.append(",");
		strBuf.append(expenseMaster.getRevisionNumber()>0);		
		strBuf.append("]");
		
		return strBuf.toString();
	}
	
	/**
	 * Shows/Hides,Enable/Disable the required buttons based on 
	 * Security,Status,ModuleId,nextActioncode conditions
	 * @param moduleId
	 * @param expenseMaster
	 * @param security
	 * @param user
	 */
	private void setupDisplayButtons(ExpenseMasters expenseMaster, int security, UserSubject user, double liquidatedAmount) {
		int securityNextAction = checkSecurityNextAction();
		String moduleId = getModuleIdFromSession();
		if (expenseMaster != null) {
			String status = expenseMaster.getStatus();
			boolean modifyClicked = isExpenseBeingModified();
			//Expenses Mode
			if (moduleId.equals(IConstants.EXPENSE_EMPLOYEE)|| moduleId.equals(IConstants.EXPENSE_MANAGER)|| moduleId.equals(IConstants.EXPENSE_STATEWIDE)) {
				if ((status == null || status.trim().equals(""))&& security == IConstants.SECURITY_UPDATE_MODULE_ACCESS) {
					display.setDisplaySubmitButton("inline");
				} else {
					display.setDisplaySubmitButton("inline");
					display.setDisableSubmitButton(true);
				}
				setupDisplayApprovalButtons("none");
			} else if (moduleId.equals(IConstants.APPROVE_WEB_MANAGER)) {
				//Manager Mode				
				setupDisplayButtonManagerApproval(security, modifyClicked, status, liquidatedAmount, moduleId);				
			} else if (moduleId.equals(IConstants.APPROVE_WEB_STATEWIDE)) {
				//Statewide Mode
				setupDisplayButtonStatewideApproval(user, securityNextAction, security, modifyClicked, status);
			}

		}
		display.setDisplayPrintDetailBtn("inline");
		display.setDisplayPrintSummaryBtn("inline");
		
		// ZH, 05/11/2010 - check security for next action - fixed approval defect in pilot
		if (securityNextAction < IConstants.SECURITY_UPDATE_MODULE_ACCESS){		
			setApprovalButtonDisableMode(true);
		}
		
		//set Certify button state
		setupDisplayCertifyButton();
	}	
	
	private void setupDisplayButtonManagerApproval(int security,
			boolean modifyClicked, String status, double liquidatedAmount,
			String moduleId) {
		List<ExpenseDetails> expenseDetails = expenseMaster
				.getExpenseDetailsCollection();

		if (expenseDetails != null && expenseDetails.size() > 0
				&& security == IConstants.SECURITY_UPDATE_MODULE_ACCESS) {
			display.setShowSupervisorReceiptsReviewedMessage(true);
		}

		// Kp: Add checks for $0 adjustments. Issue # 10
		if (expenseMaster.getAdjIdentifier() != null
				&& expenseMaster.getAdjIdentifier() > 0
				&& !display.isShowSupervisorReceiptsReviewedMessage()
				&& security == IConstants.SECURITY_UPDATE_MODULE_ACCESS) {
			if (expenseMaster.getExpenseDetailsCollection().size() == 0) {
				ExpenseMasters prevProcExpense = expenseService
						.getPrevExpenseMasterInProcStatus(expenseMaster
								.getExpevIdentifier(), expenseMaster
								.getRevisionNumber());
				if (prevProcExpense.getExpenseDetailsCollection().size() > 0) {
					display.setShowSupervisorReceiptsReviewedMessage(true);
				}
			}
		}

		String nextActionCode = expenseService.getNextActionCode(expenseMaster);
		// ZH - fixed defect # 249
		if (modifyClicked || status == null || status.trim().equals("")) {
			display.setDisplaySubmitButton("inline");
		} else {
			setupDisplayApprovalButtons("inline");
		}

		if ((!StringUtils.isEmpty(status))
				&& (status.equals(IConstants.SUBMIT)
						|| status.equals(IConstants.APPROVED)
						|| status.equals(IConstants.REJECTED) || status
						.equals(IConstants.PROCESSED))
				&& (security == IConstants.SECURITY_UPDATE_MODULE_ACCESS)) {

			if (status.equals(IConstants.SUBMIT)
					&& (nextActionCode != null && nextActionCode
							.equalsIgnoreCase(IConstants.APPROVAL_STEP1))) {
				setApprovalButtonDisableMode(false);
			} else {
				setApprovalButtonDisableMode(true);
			}
		} else {
			setApprovalButtonDisableMode(true);
		}

		// verify condition for only adjustments and liquidated amount is more
		// than zero. kp
		if (((expenseDetails == null
				|| ((expenseDetails.size() < 1) && expenseMaster
						.getAdjIdentifier() != null) || ((expenseDetails.size() < 1) && liquidatedAmount > 0))
				&& (IConstants.APPROVAL_STEP1.equals(nextActionCode))
				&& (security == IConstants.SECURITY_UPDATE_MODULE_ACCESS) && (moduleId
				.equals(IConstants.APPROVE_WEB_MANAGER)))) {
			noExpenseDetails = true;
			setApprovalButtonDisableMode(false);
		}
	}
	
	private void setupDisplayButtonStatewideApproval(UserSubject user,
			int securityNextAction, int security, boolean modifyClicked,
			String status) {
		if (modifyClicked || status.trim().equals("")) {
			display.setDisplaySubmitButton("inline");
		} else {
			setupDisplayApprovalButtons("inline");
		}
		if ((!StringUtils.isEmpty(status))
				&& (status.equals(IConstants.SUBMIT)
						|| status.equals(IConstants.APPROVED)
						|| status.equals(IConstants.REJECTED) || status
						.equals(IConstants.PROCESSED))
				&& (security == IConstants.SECURITY_UPDATE_MODULE_ACCESS)) {

			String preAuditRequired = expenseService.isPreAuditRequired(user, 1001, expenseMaster);
			if ("Y".equals(preAuditRequired) && (securityNextAction < 2)) {
				preAuditRequired = "N";
			}

			if (preAuditRequired != null && preAuditRequired.equals("Y")) {
				if (!expenseService.isExpenseMarkedAuditComplete(expenseMaster)) {
					display.setPreAuditRequiredAndCompleted(false);
				}
				// ZH - Fix for defect # 984. This will suppress "pre audit"
				// required
				// message and display rejection status
				if (!StringUtils.isEmpty(expenseMaster.getStatus())
						&& IConstants.REJECTED
								.equals(expenseMaster.getStatus())) {
					display.setPreAuditRequiredAndCompleted(true);
				}
			}
			if (status.equals(IConstants.SUBMIT)
					&& display.isPreAuditRequiredAndCompleted()) {
				setApprovalButtonDisableMode(false);
			} else {
				setApprovalButtonDisableMode(true);
				// ZH - fixed defect # 984. Reject should not be disabled if
				// Statewide approval
				if (IConstants.APPROVE_WEB_STATEWIDE
						.equals(getModuleIdFromSession())
						&& !(IConstants.REJECTED).equals(status)
						&& !(IConstants.APPROVED).equals(status)) {
					display.setDisableRejectButton(false);
					display.setDisableApproveSkipButton(false);
				} else {
					display.setDisableRejectButton(true);
					
				}
			}
		} else {
			setApprovalButtonDisableMode(true);
		}
	}
	
	private void setupDisplayCertifyButton(){
		//Determine if the check expense button should be disabled or enabled.		
		if (!IConstants.EXPENSE_EMPLOYEE.equalsIgnoreCase(getModuleIdFromSession())) {
			display.setDisplayCertifyButton("none"); 
		}
		else
		{
			int returnVaue = expenseService.checkExpensesEnteredByOtherUser(expenseMaster.getExpmIdentifier(), getLoggedInUser().getUserId() );
			
			if(returnVaue == -1){
				display.setDisplayCertifyButton("none");			 
			}
			else if(returnVaue == 1){
				display.setDisplayCertifyButton("inline"); 
				display.setDisableCertifyButton("false");				
			}
			else if(returnVaue == 0){
				display.setDisplayCertifyButton("inline");
				display.setDisableCertifyButton("true");				
			}
		}
	}
	/**
	 * Checks security for the module id associated with next action code, if applicable
	 * @return
	 */
	
	private int checkSecurityNextAction() {
		String nextActionCode = expenseService.getNextActionCode(expenseMaster);
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
	 * Sets flag to enable or disable approval buttons
	 * @param mode
	 */
	
	private void setApprovalButtonDisableMode(boolean mode){
		display.setDisableApproveButton(mode);
		display.setDisableApproveWithCommentsButton(mode);
		display.setDisableApproveNextButton(mode);
		display.setDisableApproveSkipButton(mode);
		display.setDisableRejectButton(mode);
	}
	
	/**
	 * Used to submit the expense.
	 * @throws Exception
	 * @return success: Find mapping in struts.xml
	 */
	public String submitExpense() throws Exception {
		UserProfile userProfile = getLoggedInUser();
		expenseMaster = (ExpenseMasters) session.get(IConstants.CURR_EXPENSEMASTER);
		expenseMaster = entityManager.merge(expenseMaster);
		String jsonResponse = "{msg:'Submitted successfully', submitSuccess : true}";
		
		// if over liquidation, raise error 
		if (!validateLiquidation(false)){
			return IConstants.SUCCESS;
		}
		// check if expense lines or liquidations is present
		if (!validateExpenseLinesOrLiquidation ()){
			return IConstants.SUCCESS; 
		}
		
		try {
			List<AdvanceLiquidations> liquidationList = (List<AdvanceLiquidations>) session
					.get(IConstants.EXPENSE_LIQUIDATION_LIST_IN_SESSION);
			List<DriverReimbExpTypeCbs> driverReibmCbs = (List<DriverReimbExpTypeCbs>) getApplicationCache()
					.get("DRIVER_REIMB_CODING_BLOCKS");
			expenseService.submitExpense(expenseMaster, userProfile,
					getUserSubject(), liquidationList, approverComments,
					driverReibmCbs, getModuleIdFromSession());
			expenseService.emailNotification(expenseMaster, getUserSubject(),
					userProfile, approverComments);
		} catch (ExpenseException expEx) {
			jsonResponse = "{msg:'Submission Failed - Please check grid below for errors', submitSuccess : false}";
		}
		// compare expense dates to timesheet dates
		compareExpenseTimesheetDates();	
		// flush the changes explicitly to the db
		persistExpenseMasterState(expenseMaster);		
		displayExpenseErrors(expenseMaster);
		setJsonResponse(jsonResponse);
		
		// push updated expense master into session
		session.put(IConstants.EXPENSE_SESSION_DATA, expenseMaster);
		session.put(IConstants.MODIFY_BUTTON_STATE_SESSION, Boolean.FALSE);
		synchronized(session){
			session.remove(IConstants.ADVANCE_OUTSTANDING_LIST);
		}
		//update session with correct operation status
		updateSessionWithLastSuccessfulOperation("Submitted successfully");		
		return IConstants.SUCCESS;
	}
	
	private boolean validateExpenseLinesOrLiquidation(){
		boolean retVal = true;
		// refresh master state, to enable child fetch operations
		if (expenseMaster != null) {
			List<AdvanceLiquidations> liquidations = advanceLiquidationService.findLiquidationsByExpenseId(expenseMaster.getExpmIdentifier());
			List<ExpenseDetails> expenseDetails = expenseMaster.getExpenseDetailsCollection();
			// Non-adjusted expenses are allowed to have no details and no liquidations. But before PROC expenses must contain
			// either details or liquidations.
			if (expenseMaster.getAdjIdentifier() == null &&
				(expenseDetails == null || expenseDetails.size() < 1) && (liquidations == null || liquidations.size() < 1)) {
				String jsonResponse = "{msg:'Submission failed because no line items were entered OR no liquidations are present',submitSuccess : false}";
				setJsonResponse(jsonResponse);
				retVal = false;
			}	else {		
				//delete expense errors
				deleteExpenseErrors();
			}
		}
		return retVal;
	}
	/**
	 * Explicitly saves all the expense errors and then flushes the changes to the db
	 * @param ExpenseMasters object that needs to be persisted
	 * @return void
	 */

	private void persistExpenseMasterState(ExpenseMasters master) {
		// persist errors
		for (ExpenseErrors error : master.getExpenseErrorsCollection()) {
			if (error.getExperIdentifier() == null)
				entityManager.persist(error);
			else
				error = entityManager.merge(error);
		}
		entityManager.flush();
	}
	
	/**
	 * Displays the expense errors
	 * @param savedExpense
	 * @return void
	 */
	private void displayExpenseErrors(ExpenseMasters savedExpense) {
		
		// propagate expense errors
		ExpenseViewUtil util = new ExpenseViewUtil();
		util.setJsonParser(jsonParser);
		util.setCodingBlockService(codingBlockService);
		util.setAppointmentService(appointmentService);
		addTimeAndExpenseErrors(util.prepareTimeAndExpenseErrors(expenseMaster));
		
		
	}
	
	/**
	 * Rejects the expense
	 * @return success: Find mapping in struts.xml
	 */
	public String rejectExpense() {
		String jsonResponse = "{msg:'Rejected successfully', RejectSuccess : true}";
		UserProfile userProfile = getLoggedInUser();
		expenseMaster = (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);
		expenseMaster = entityManager.merge(expenseMaster);
		expenseMaster.setSuperReviewedReceiptsInd("N");//As per Item#27 in Test Issues Tab

		// check whether user has security to do the operation
		if(checkSecurityNextAction() < 2){
			jsonResponse = "{msg:'Reject Failed -  You are not authorized to reject this expense.', approveSuccess : false}";
			displayExpenseErrors(expenseMaster);
			super.setJsonResponse(jsonResponse);
			return IConstants.SUCCESS;
		}
		
		expenseService.rejectExpense(expenseMaster, userProfile,getModuleIdFromSession(),approverComments);
		expenseService.emailNotification(expenseMaster, getUserSubject(), userProfile, approverComments);
		entityManager.flush();
		displayExpenseErrors(expenseMaster);
		super.setJsonResponse(jsonResponse);
		session.put(IConstants.EXPENSE_SESSION_DATA, expenseMaster);	
		//ZH, 03/21/2013 - Commented line below for defect # 161. The message below will no longer
		// be displayed since the approval listing page will be loaded upon success
		//updateSessionWithLastSuccessfulOperation("Rejected successfully");	
		return IConstants.SUCCESS;
	}
	
	/**
	 * Approves the expense
	 * @return success: Find mapping in struts.xml
	 */
	public String approveExpense() {
		UserProfile userProfile = getLoggedInUser();
		expenseMaster = (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);
		expenseMaster = entityManager.merge(expenseMaster);
		String moduleId = getModuleIdFromSession();
		if(moduleId.equals(IConstants.APPROVE_WEB_MANAGER) && supervisorReceiptsReviewed != null && supervisorReceiptsReviewed.equalsIgnoreCase("true")){
			expenseMaster.setSuperReviewedReceiptsInd("Y");	
		}	

		if (!isEmployeeActive()){
			// employee is inactive
			return IConstants.SUCCESS;
		}
		
		// check whether user has security to do the operation
		if(!getSecurityNextAction()){
			return IConstants.SUCCESS;
		}
		
		// if over liquidation, raise error 
		if (!validateLiquidation(true)){
			return IConstants.SUCCESS;
		}
	
		 if (!isAdvanceCurrent()){
			 return IConstants.SUCCESS;
		 }
	 	// no errors or other validation issues. approve and send email
	 	approveExpenseAndNotify(userProfile, getUserSubject(), "{msg:'Approved successfully', approveSuccess : true}");
		
	 	//Non Productive Hours error code-36047 and time sheet existance code -36048 will be cleared if expense is approved and Time sheet exists 
		
	 	if (expenseMaster.getExpenseErrorsCollection() != null) {
	 		List<String> errorCode = new ArrayList<String>();
			for (ExpenseErrors error : expenseMaster
					.getExpenseErrorsCollection())
				if(error.getErrorCode().getErrorCode().equals(IConstants.EXPENSE_TIME_SHEET_EXISTANCE))
					errorCode.add(IConstants.EXPENSE_TIME_SHEET_EXISTANCE);
				else if(error.getErrorCode().getErrorCode().equals(IConstants.EXPENSE_COMPARE_WITH_TIME_SHEET_HOURS))
					errorCode.add(IConstants.EXPENSE_COMPARE_WITH_TIME_SHEET_HOURS);
			    commonService.deleteExpenseErrors(expenseMaster, errorCode);
			    compareExpenseTimesheetDates();

		}
	  	
	 	return IConstants.SUCCESS;
	}	
	
	private boolean isEmployeeActive(){
		boolean retVal = true;
		// Checks the active/inactive status of the employee with the employee Id. kp. AI 19898
		if (!appointmentService.isEmployeeActive(getUserSubject().getEmployeeId())){
			String jsonResponse = "{msg:'The employee is in a \"No Pay\" status. Please update employee status in HRMN to allow approval.', approveSuccess : false}";			
			displayExpenseErrors(expenseMaster);
			super.setJsonResponse(jsonResponse);
			retVal = false;
		}
		return retVal;
	}
	
	private boolean getSecurityNextAction (){
		boolean retVal = true;
		if(checkSecurityNextAction() < 2){
			String jsonResponse = "{msg:'Approval Failed -  You are not authorized to approve this expense further.', approveSuccess : false}";
			displayExpenseErrors(expenseMaster);
			super.setJsonResponse(jsonResponse);
			retVal = false;
		}
		return retVal;
	}
	
	private boolean validateLiquidation (boolean isApproval){
		boolean retVal = true;
		boolean overLiq = validateLiqAmount();
		String jsonResponse = "";
		if(overLiq){
			if (isApproval){
			jsonResponse = "{msg:'Approval Failed -  Please check grid below for errors', approveSuccess : false}";
			} else {
				jsonResponse = "{msg:'Submission Failed - Please check grid below for errors', submitSuccess : false}";
			}
			persistExpenseMasterState(expenseMaster);
			displayExpenseErrors(expenseMaster);
			super.setJsonResponse(jsonResponse);
			retVal = false;;
		}
		return retVal;
	}
	
	private boolean isAdvanceCurrent(){
		boolean retVal = true;
		 if (getModuleIdFromSession().equals(IConstants.APPROVE_WEB_STATEWIDE)){
				boolean isAdvanceCurrent = validateAdvanceCurrentInd();
				if (!isAdvanceCurrent){
					String jsonResponse = "{msg:'Approval Failed -  Please check grid below for errors', approveSuccess : false}";
					persistExpenseMasterState(expenseMaster);
					displayExpenseErrors(expenseMaster);
					super.setJsonResponse(jsonResponse);
					retVal = false;
				}
			 }
		return retVal;
	}
	
	private void approveExpenseAndNotify(UserProfile userProfile, UserSubject subject, String jsonResponse){
		expenseService.approveExpense(expenseMaster,userProfile,subject, approverComments);
		if(expenseMaster.getStatus().equals(IConstants.APPROVED))
		{
			expenseService.emailNotification(expenseMaster, getUserSubject(), userProfile, approverComments);
		}
		
		displayExpenseErrors(expenseMaster);
		entityManager.flush();
		session.put(IConstants.EXPENSE_SESSION_DATA, expenseMaster);		
		super.setJsonResponse(jsonResponse);
		//ZH, 03/21/2013 - Commented line below for defect # 161. The message below will no longer
		// be displayed since the approval listing page will be loaded upon successful approval
		//updateSessionWithLastSuccessfulOperation("Approved successfully");			
	}
	
	
   	/** Used to check if the liquidation amount is greater than the advance outstanding amount
   	 * @return boolean
   	 */
   	private boolean validateLiqAmount() {
   		boolean overLiquidation = expenseService.isLiqAmtGreaterThanOutstandingAmt(expenseMaster);
   		UserProfile userProfile = getLoggedInUser();
   		if (overLiquidation && expenseMaster.getExpenseErrorsCollection() != null) {
   			boolean errorAlreadyPresent = false;
			for (ExpenseErrors errors : expenseMaster.getExpenseErrorsCollection()) {
				if (errors.getErrorCode().getErrorCode().equals(IConstants.AMT_LIQUIDATED_GREATER_THAN_INDIVIDUAL_ADVANCE)){
					errorAlreadyPresent = true;
					break;
				}
			}
			
			if (!errorAlreadyPresent) commonService.populateErrors(IConstants.AMT_LIQUIDATED_GREATER_THAN_INDIVIDUAL_ADVANCE, IConstants.EXP_ERR_SRC_LIQ_TAB, expenseMaster, userProfile);			
		}
   		// If over liquidation stands corrected, remove the error from the collection
   		else if(!overLiquidation && expenseMaster.getExpenseErrorsCollection() != null){
   			for(Iterator<ExpenseErrors> itr = expenseMaster.getExpenseErrorsCollection().iterator(); itr.hasNext();){
   				ExpenseErrors error = itr.next();
				if (error.getErrorCode().getErrorCode().equals(IConstants.AMT_LIQUIDATED_GREATER_THAN_INDIVIDUAL_ADVANCE)){
					itr.remove();
					//PS[mc 6/4/10] - The following statement is not required as ExpenseErrors has 'ElementDepenedent'
					// annotation in the ExpenseMasters class. Deleting something from the errors collection, automatically
					// generates the SQL delete statement. 
					//entityManager.remove(error);
					break;
				}
   			}
		}
		
   		return overLiquidation;
   	}
   	
   	
   	/** Used to check if the liquidation amount is greater than the advance outstanding amount
   	 * @return boolean
   	 */
   	private boolean validateAdvanceCurrentInd() {
   		boolean isAdvanceCurrent = expenseService.isAdvanceCurrentInd(expenseMaster);
   		UserProfile userProfile = getLoggedInUser();
   		if (!isAdvanceCurrent && expenseMaster.getExpenseErrorsCollection() != null) {
   			boolean errorAlreadyPresent = false;
			for (ExpenseErrors errors : expenseMaster.getExpenseErrorsCollection()) {
				if (errors.getErrorCode().getErrorCode().equals(IConstants.ADVANCE_LIQUIDATION_NOT_CURRENT)){
					errorAlreadyPresent = true;
					break;
				}
			}
			
			if (!errorAlreadyPresent) commonService.populateErrors(IConstants.ADVANCE_LIQUIDATION_NOT_CURRENT, IConstants.EXP_ERR_SRC_LIQ_TAB, expenseMaster, userProfile);			
		}
   		// If advance is now current, remove the error from the collection   		
   		else if(isAdvanceCurrent && expenseMaster.getExpenseErrorsCollection() != null){
   			for(Iterator<ExpenseErrors> itr = expenseMaster.getExpenseErrorsCollection().iterator(); itr.hasNext();){
   				ExpenseErrors error = itr.next();
				if (error.getErrorCode().getErrorCode().equals(IConstants.ADVANCE_LIQUIDATION_NOT_CURRENT)){
					itr.remove();
					//PS[mc 6/4/10] - The following statement is not required as ExpenseErrors has 'ElementDepenedent'
					// annotation in the ExpenseMasters class. Deleting something from the errors collection, automatically
					// generates the SQL delete statement.
					//entityManager.remove(error);
					break;
				}
   			}
		}		
   		return isAdvanceCurrent;
   	}
   	
   	/**
   	 * Method to add ECRT to Expense Actions table.
   	 * @return
   	 */
   	public String certifyExpense(){
   		
   		expenseMaster = (ExpenseMasters) session.get(IConstants.EXPENSE_SESSION_DATA);
   		UserProfile userProfile = getLoggedInUser();
   		
   		setJsonResponse(expenseService.certifyExpense(expenseMaster, userProfile));
   		return IConstants.SUCCESS;
   	}
   	
   	private void setupDisplayApprovalButtons(String displayMode){
   		display.setDisplayApproveButton(displayMode);
   		display.setDisplayApproveWithcommentsButton(displayMode);
   		display.setDisplayApproveNextButton(displayMode);
   		display.setDisplayApproveSkipButton(displayMode);
   		display.setDisplayRejectButton(displayMode);
   	}
   	
	/**
	 * Used to put the Status Message in the session so that Expense tab page checks it's value and forwards 
	 * the page to summary tab if the operation is successful
	 *  
	 * @param lastSuccessfulOperation
	 * @return void
	 */
	private void updateSessionWithLastSuccessfulOperation(String lastSuccessfulOperation) {
		session.put(IConstants.LAST_OPERATION, lastSuccessfulOperation);
	}	
	
	/**
	 * Delete all existing expense business errors
	 */
	private void deleteExpenseErrors(){
		// first delete all existing errors related to expense details
		commonService.deleteExpenseErrors(expenseMaster,IConstants.EXP_ERR_SRC_DTL_TAB);
		commonService.deleteExpenseErrors(expenseMaster, IConstants.EXP_ERR_SRC_ID_TAB);
		commonService.deleteExpenseErrors(expenseMaster,IConstants.EXP_ERR_SRC_CB_TAB);
		commonService.deleteExpenseErrors(expenseMaster, IConstants.EXP_ERR_SRC_LIQ_TAB);
		// flush expense error changes
		entityManager.flush();
	}
	
	private void compareExpenseTimesheetDates (){
		//Validate Expense Date to Timesheet Dates
		String returnValue = expenseService.compareExpenseDateToTimesheetDate(expenseMaster.getExpevIdentifier().getExpevIdentifier());
		
		//the return string contains errors by line items
		// in the format of 1:errorcode;3:errorcode.
		//if there is no error then the return value will skip the line item number
		if (returnValue.length() > 0) {
			String[] lineItemErrors = returnValue.split(";");
			for (String lineItemError : lineItemErrors) {
				commonService.populateErrors(
						lineItemError.split(":")[1],
						IConstants.EXP_ERR_SRC_DTL_TAB
								+ lineItemError.split(":")[0], expenseMaster,
						getLoggedInUser());
			}
		}
	}

	public ExpenseMasters getExpenseMaster() {
		return expenseMaster;
	}

	public void setExpenseMaster(ExpenseMasters expenseMaster) {
		this.expenseMaster = expenseMaster;
	}

	public Integer getExpenseMasterId() {
		return expenseMasterId;
	}

	public void setExpenseMasterId(Integer expenseMasterId) {
		this.expenseMasterId = expenseMasterId;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setApproverComments(String approverComments) {
		if(approverComments!= null) this.approverComments = approverComments.trim();
	}

	public String getApproverComments() {
		return approverComments;
	}

	protected boolean isExpenseBeingModified(){
		return session.containsKey(IConstants.MODIFY_BUTTON_STATE_SESSION)
			&& (Boolean)session.get(IConstants.MODIFY_BUTTON_STATE_SESSION);
	}

	public void setNoExpenseDetails(boolean noExpenseDetails) {
		this.noExpenseDetails = noExpenseDetails;
	}

	public boolean isNoExpenseDetails() {
		return noExpenseDetails;
	}

	public DisplayExpenseSummary getDisplay() {
		return display;
	}

	public void setDisplayButtons(DisplayExpenseSummary display) {
		this.display = display;
	}
	public String getSupervisorReceiptsReviewed() {
		return supervisorReceiptsReviewed;
	}
	public void setSupervisorReceiptsReviewed(String supervisorReceiptsReviewed) {
		this.supervisorReceiptsReviewed = supervisorReceiptsReviewed;
	}
}
