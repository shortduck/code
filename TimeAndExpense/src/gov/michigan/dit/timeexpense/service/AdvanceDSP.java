/**
 * Class Description:  Processes business logic operations associated with advance requests. This
 * DSP also provides services for Advance Liquidations for expense reimbursement requests.
 * Author(s): GL, ZH
 * Date: 03/17/2009
 * 
 */

package gov.michigan.dit.timeexpense.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import gov.michigan.dit.timeexpense.dao.AdvanceDAO;
import gov.michigan.dit.timeexpense.dao.AppointmentDAO;
import gov.michigan.dit.timeexpense.dao.CodingBlockDAO;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.model.core.AdvanceHistory;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.VAdvanceList;
import gov.michigan.dit.timeexpense.model.core.AdvanceActions;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetails;
import gov.michigan.dit.timeexpense.model.core.AdvanceErrors;
import gov.michigan.dit.timeexpense.model.core.AdvanceEvents;
import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.CodingBlockElement;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseProfileRules;
import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.db.Appointments;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.model.display.AdvApprovalTransaction;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.DisplayAdvance;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

public class AdvanceDSP {
	// instance variables
	public AdvanceDAO advanceDao;
	public CommonDAO commonDao;
	public ExpenseDAO expenseDao;
	public HashMap<String, String> errorMap = null;
	private AppointmentDAO apptDAO;
	private CommonDSP commonService;
	private CodingBlockDSP codingBlockService;
	private AppointmentDSP apptService;
	private EmailNotificationDSP emailService;
	private AdvanceCodingBlockDSP advanceCodingBlockService;

	private CodingBlockDAO codingBlockDao;
	Logger logger = Logger.getLogger(AdvanceDSP.class);

	public AdvanceDSP(EntityManager entityManager) {
		advanceDao = new AdvanceDAO();
		advanceDao.setEntityManager(entityManager);

		commonService = new CommonDSP(entityManager);

		apptDAO = new AppointmentDAO();
		apptDAO.setEntityManager(entityManager);

		commonDao = new CommonDAO();
		commonDao.setEntityManager(entityManager);

		codingBlockDao = new CodingBlockDAO();
		codingBlockDao.setEntityManager(entityManager);

		codingBlockService = new CodingBlockDSP(entityManager);
		codingBlockService.setCodingBlockDAO(codingBlockDao);
		codingBlockService.setCommonDao(commonDao);

		apptService = new AppointmentDSP(entityManager);
		emailService = new EmailNotificationDSP(entityManager);
		
		expenseDao = new ExpenseDAO();
		expenseDao.setEntityManager(entityManager);

		advanceCodingBlockService = new AdvanceCodingBlockDSP(entityManager);
	}

	/**
	 * This method is used to Save Advances.
	 * 
	 * @param advanceMasters
	 * @param apptIdentifier
	 * @param moduleId
	 *            - Needed for security information
	 * @param userProfile
	 *            - Used for security and user id for the modified_user_id
	 * @return
	 */
	public AdvanceMasters saveAdvance(AdvanceMasters advanceMasters,
			int apptIdentifier, String moduleId, UserProfile userProfile,
			UserSubject userSubject) {

		List<AdvanceDetailCodingBlocks> cbList = advanceMasters
				.getAdvanceDetailsCollection().get(0)
				.getAdvanceDetailCodingBlocksCollection();
		if (advanceMasters.getAdvanceDetailsCollection().get(0)
				.getAdvanceDetailCodingBlocksCollection().size() > 0) {
			double dollarAmount = advanceMasters.getAdvanceDetailsCollection()
					.get(0).getDollarAmount();
			advanceMasters.getAdvanceDetailsCollection().get(0)
					.setAdvanceDetailCodingBlocksCollection(
							advanceCodingBlockService.preSaveCodingBlocks(
									dollarAmount, cbList, userProfile));
		}

		// Calls the method Validate, which deletes the error list and calls
		// subsequent validate methods
		this.validate(advanceMasters, moduleId, userProfile, userSubject);
		
		this.saveAdvanceErrors(advanceMasters);

		// set modified user ids
		advanceMasters.getAdvanceDetailsCollection().get(0).setModifiedUserId(
				userProfile.getUserId());
		advanceMasters.setModifiedUserId(userProfile.getUserId());

		if (advanceMasters.getAdevIdentifier() == null) {
			// this request is to create a new advance request
			this.saveAdvanceNew(advanceMasters, apptIdentifier, moduleId,
					userProfile);
		} else {
			// update to an existing request
			advanceMasters = this.saveAdvanceExisting(advanceMasters,
					userProfile, userSubject);
		}
		return advanceMasters;
	}

	/**
	 * This method is used to save Advances which are NEW. The adev_identifier
	 * passed from Actions doesn't already exist.
	 * 
	 * @param advanceMasters
	 * @param apptIdentifier
	 * @param moduleId
	 * @param userProfile
	 * @return
	 */

	public boolean saveAdvanceNew(AdvanceMasters advanceMasters,
			int apptIdentifier, String moduleId, UserProfile userProfile) {

		// setup details with the pay type
		if (advanceMasters.getAdvanceDetailsCollection() != null) {
			setPayType(advanceMasters);

			// creating a new instance of advanceEvents and appointments
			AdvanceEvents advanceEvents = new AdvanceEvents();
			advanceEvents.setApptIdentifier(apptIdentifier);
			advanceEvents.setModifiedUserId(userProfile.getUserId());
			// add derived values to advance masters
			advanceMasters.setAdevIdentifier(advanceEvents);
			advanceMasters.setCurrentInd("Y");
			advanceMasters.setRevisionNumber(new Short("0"));
			List<AdvanceMasters> mastersList = new ArrayList<AdvanceMasters>();
			mastersList.add(advanceMasters);
			advanceEvents.setAdvanceMastersCollection(mastersList);
			// initiate save processing
			advanceDao.saveAdvanceEvent(advanceEvents);
			if (logger.isInfoEnabled())
				logger.info("Created new advance with event id: "
						+ advanceEvents.getAdevIdentifier());
		}
		return true;

	}

	/**
	 * This method is saves the Adjusted/Modified Advances. The adev_identifier
	 * passed from actions exists.
	 * 
	 * @param advanceMasters
	 * @param userProfile
	 * @return
	 */
	private AdvanceMasters saveAdvanceExisting(AdvanceMasters advanceMasters,
			UserProfile userProfile, UserSubject subject) {

		// if (StringUtils.isEmpty(advanceMasters.getStatus())) {
		// updates to existing advance master, only needs to be saved
		advanceMasters.getAdevIdentifier().setApptIdentifier(subject.getAppointmentId());
		setPayType(advanceMasters);
		advanceMasters.setModifiedUserId(userProfile.getUserId());
		advanceMasters = advanceDao.saveAdvanceMaster(advanceMasters);
		if (logger.isInfoEnabled())
			logger.info("Existing advance masters saved");
		// }
		return advanceMasters;
	}

	/**
	 * This method copies incoming advance master to prepare for creating the
	 * new revision of AdvanceMasters
	 * 
	 * 
	 * @param AdvanceMasters
	 *            advanceMasters
	 * @return
	 */

	public AdvanceMasters createNewRevision(AdvanceMasters currAdvanceMaster,
			UserProfile userProfile) {

		// create a new instance of AdvanceMasters and perform a deep copy
		AdvanceMasters advanceMasters = new AdvanceMasters();
		this.copyAdvanceMasterFields(currAdvanceMaster, advanceMasters,
				userProfile);
		this.copyAdvanceDetails(currAdvanceMaster, advanceMasters, userProfile);
		this.copyAdvanceActions(currAdvanceMaster, advanceMasters, userProfile);
		//this.copyLiquidations(currAdvanceMaster, advanceMasters, userProfile);

		return advanceMasters;
	}

	/**
	 * emailNotification will sent out email about the action taken. This
	 * routine will be called only if earlier action is successful.
	 */
	public void emailNotification(AdvanceMasters advanceMasters,
			UserSubject subject, UserProfile profile, String comments) {
		int returnCode = 0;
		String totalAdvanceAmount="";
		//Get total advance amount
		if(advanceMasters.getAdvanceDetailsCollection().get(0).getDollarAmount()==0)
			totalAdvanceAmount="0";
		else
			totalAdvanceAmount=TimeAndExpenseUtil.displayAmountTwoDigits(advanceMasters.getAdvanceDetailsCollection().get(0).getDollarAmount());
		
		String notificationMessage = emailService.getNotificationMessage(
				advanceMasters, subject, profile);
		
		if (!StringUtils.isEmpty(notificationMessage)) {
			
			String addittionalText;
			addittionalText = "  Details:\rAdvance ID: " + advanceMasters.getAdevIdentifier().getAdevIdentifier().toString() + ".  ";
			addittionalText += "  From Date: " + new SimpleDateFormat("MM-dd-yyyy").format(advanceMasters.getFromDate()) + ".  ";
			addittionalText += "  To Date: " + new SimpleDateFormat("MM-dd-yyyy").format(advanceMasters.getToDate()) + ".  ";
			addittionalText += "  Amount: $" + totalAdvanceAmount + ".  ";
			comments = (comments.trim().length() == 0) ? "--" : comments.trim();
			addittionalText += "  Comments: " + comments + ".  ";
			addittionalText += "\r";
			
			returnCode = emailService.processEmailNotifications(subject, profile.getEmpIdentifier(), profile
					.getUserId(), notificationMessage, Calendar.getInstance()
					.getTime(), addittionalText);
		}
		if (logger.isInfoEnabled())
			logger.info("Notification return code: " + returnCode);

		if (returnCode == 0) {
			logger.info("Notification return code: " + returnCode);
			returnCode = 10551;
		} else {
			// insert or update notification errors
			processAdvanceNotificationErrors(advanceMasters, profile,
					returnCode);
		}

	}

	/**
	 * This method updates AdvanceMaster status to SUBM and creates a new
	 * Advance Action
	 * 
	 * @param AdvanceMasters
	 *            advanceMasters
	 * @return boolean
	 */

	public AdvanceMasters submitAdvance(AdvanceMasters advanceMasters,
			UserSubject subject, UserProfile userProfile, String approverComments) {

		if (advanceMasters.getAdvanceActionsCollection() != null
				&& !advanceMasters.getAdvanceActionsCollection().isEmpty()) {
			// an modify-by-approver by scenario and an action already exists,
			// only need to add comments and update outstanding amount
			advanceMasters.getAdvanceActionsCollection().get(0).setComments(
					approverComments);
				if (advanceMasters.getStatus().equals(IConstants.APPROVED)) {
					this.setCarryforwardLqiuidaionsList(advanceMasters, userProfile);
					this.updateAmountOutstanding(advanceMasters);
				} else{
				advanceMasters.setStatus(IConstants.SUBMIT);
			}
		} else {
			// new submission update status on AdvanceMasters to SUBM
			advanceMasters.setStatus(IConstants.SUBMIT);

			// Create a new instance of AdvancesActions Collections
			List<AdvanceActions> actionsList = advanceMasters
					.getAdvanceActionsCollection();

			if (actionsList == null) {
				actionsList = new ArrayList<AdvanceActions>();
				advanceMasters.setAdvanceActionsCollection(actionsList);
			}

			// Create a new instance of AdvanceActions
			AdvanceActions actions = new AdvanceActions();
			actions.setActionCode(IConstants.SUBMIT);
			// Update actions with advm from AdvanceMasters
			actions.setAdvmIdentifier(advanceMasters);
			actions.setModifiedUserId(userProfile.getUserId());
			if (!StringUtils.isEmpty(approverComments))
				actions.setComments(approverComments.trim());
			// Add new instance of Actions to Collection of Actions
			actionsList.add(actions);
			// advanceMasters.setAdvanceActionsCollection(actionsList);
			advanceMasters = advanceDao.saveAdvanceMaster(advanceMasters);
		}

		// prepare and process notification
		// this.processNotification(advanceMasters, subject);

		return advanceMasters;
	}

	/**
	 * This method updates AdvanceMaster status to RJCT and creates a new
	 * Advance Action
	 * 
	 * @param AdvanceMasters
	 *            advanceMasters
	 * @return boolean
	 */

	public AdvanceMasters rejectAdvance(AdvanceMasters advanceMaster,
			UserSubject subject, UserProfile profile, String approverComments) {
		AdvanceActions newAdvanceActions = new AdvanceActions();
		newAdvanceActions.setActionCode(IConstants.REJECTED);
		newAdvanceActions.setAdvmIdentifier(advanceMaster);
		newAdvanceActions.setModifiedUserId(profile.getUserId());
		if (!StringUtils.isEmpty(approverComments))
			newAdvanceActions.setComments(approverComments);

		if (advanceMaster.getAdvanceActionsCollection() != null)
			advanceMaster.getAdvanceActionsCollection().add(newAdvanceActions);
		else {
			List<AdvanceActions> newAdvanceActionsList = new ArrayList<AdvanceActions>();
			newAdvanceActionsList.add(newAdvanceActions);
			advanceMaster.setAdvanceActionsCollection(newAdvanceActionsList);
		}

		// update status for advance masters
		advanceMaster.setStatus(IConstants.REJECTED);
		advanceMaster.setModifiedUserId(profile.getUserId());
		advanceMaster = advanceDao.saveAdvanceMaster(advanceMaster);

		// prepare and process notification
		// this.processNotification(advanceMaster, subject);

		if (logger.isInfoEnabled())
			logger.info("Advance Approved with master id: "
					+ advanceMaster.getAdvmIdentifier());

		return advanceMaster;
	}

	/**
	 * This method updates AdvanceMaster status to APPR and creates a new
	 * Advance Action
	 * 
	 * @param AdvanceMasters
	 *            advanceMasters
	 * @return boolean
	 */

	public AdvanceMasters approveAdvance(AdvanceMasters advanceMaster,
			UserSubject subject, UserProfile userProfile, String approverComments) {

		List<AdvanceActions> advanceActionList = advanceDao
				.findNextAction(advanceMaster);
		AdvanceActions newAdvanceActions = new AdvanceActions();

		if (advanceActionList != null && !advanceActionList.isEmpty()) {
			// set new action to the last_action_code of previous action
			String action = advanceActionList.get(0).getNextActionCode();
			newAdvanceActions.setActionCode(action);
		}
		// ZH - commented per defect # 66
		/*else {
			newAdvanceActions.setActionCode(IConstants.APPROVED);
		}*/
		newAdvanceActions.setAdvmIdentifier(advanceMaster);
		newAdvanceActions.setModifiedUserId(userProfile.getUserId());
		if (!StringUtils.isEmpty(approverComments))
			newAdvanceActions.setComments(approverComments);

		if (advanceMaster.getAdvanceActionsCollection() != null)
			advanceMaster.getAdvanceActionsCollection().add(newAdvanceActions);
		else {
			List<AdvanceActions> newAdvanceActionsList = new ArrayList<AdvanceActions>();
			newAdvanceActionsList.add(newAdvanceActions);
			advanceMaster.setAdvanceActionsCollection(newAdvanceActionsList);
		}

		// update status for advance masters
		// advanceMaster = advanceDao.updateAdvanceStatus(advanceMaster);
		advanceMaster = advanceDao.saveAdvanceMaster(advanceMaster);
		advanceDao.getEntityManager().flush();
		advanceMaster = advanceDao.updateAdvanceStatus(advanceMaster);

		if (advanceMaster.getStatus().equals(IConstants.APPROVED)) {
			this.setCarryforwardLqiuidaionsList(advanceMaster, userProfile);
			this.updateAmountOutstanding(advanceMaster);
		}

		advanceMaster.setModifiedUserId(userProfile.getUserId());
		advanceMaster = advanceDao.saveAdvanceMaster(advanceMaster);

		// prepare and process notification
		// this.processNotification(advanceMaster, subject);

		if (logger.isInfoEnabled())
			logger.info("Advance Approved with master id: "
					+ advanceMaster.getAdvmIdentifier());

		return advanceMaster;
	}

	private void updateAmountOutstanding(AdvanceMasters advanceMaster) {
		double amountOutstanding = advanceMaster.getAdevIdentifier()
				.getOutstandingAmount();
		// ZH - Commenting this because of AI - 22787, duplicate liquidations
		/*if (amountOutstanding == 0) {
			// first time approval. Manual deposit scenario should not occur
			// in this case
			advanceMaster.getAdevIdentifier().setOutstandingAmount(
					advanceMaster.getAdvanceDetailsCollection().get(0)
							.getDollarAmount());
		} else if (amountOutstanding > 0) {*/
			// a modification with a possibility of liquidations
			double requestAmount = advanceMaster.getAdvanceDetailsCollection()
					.get(0).getDollarAmount();
			double manualDepositAmount = advanceMaster.getManualDepositAmount();
			// double liquidatedAmount = requestAmount - amountOutstanding -
			// manualDepositAmount;
			double liquidatedAmount = this
					.calculateAmountLiquidatedByExpense(advanceMaster);
			amountOutstanding = requestAmount - liquidatedAmount
					- manualDepositAmount;
			advanceMaster.getAdevIdentifier().setOutstandingAmount(
					amountOutstanding);
		//}
	}

	private void copyAdvanceMasterFields(AdvanceMasters currAdvanceMaster,
			AdvanceMasters advanceMasters, UserProfile userProfile) {
		advanceMasters.setCurrentInd("Y");
		advanceMasters.setAdevIdentifier(currAdvanceMaster.getAdevIdentifier());
		advanceMasters.setRequestDate(currAdvanceMaster.getRequestDate());
		// ZH - Commented per defect # 1008
		//advanceMasters.setPaidPpEndDate(currAdvanceMaster.getPaidPpEndDate());
		advanceMasters.setAdvanceReason(currAdvanceMaster.getAdvanceReason());
		advanceMasters.setPermanentAdvInd(currAdvanceMaster
				.getPermanentAdvInd());
		advanceMasters.setManualDepositDocNum(currAdvanceMaster
				.getManualDepositDocNum());
		advanceMasters.setManualDepositAmount(currAdvanceMaster
				.getManualDepositAmount());
		advanceMasters.setManualWarrantDocNum(currAdvanceMaster
				.getManualWarrantDocNum());
		advanceMasters.setManualWarrantIssdInd(currAdvanceMaster
				.getManualWarrantIssdInd());
		advanceMasters.setManualDepositInd(currAdvanceMaster
				.getManualDepositInd());
		advanceMasters.setFromDate(currAdvanceMaster.getFromDate());
		advanceMasters.setToDate(currAdvanceMaster.getToDate());
		// Updating fields on new AdvanceMasters which vary for each
		// revision
		advanceMasters.setRevisionNumber((short) (currAdvanceMaster
				.getRevisionNumber() + 1));
		/*
		 * if (IConstants.APPROVED.equals(currAdvanceMaster.getStatus())){
		 * advanceMasters.setStatus(currAdvanceMaster.getStatus()); } else {
		 * advanceMasters.setStatus(""); }
		 */

		advanceMasters.setStatus(currAdvanceMaster.getStatus());
		advanceMasters.setModifiedUserId(userProfile.getUserId());

		if (currAdvanceMaster.getStatus()
				.equalsIgnoreCase(IConstants.PROCESSED)) {
			// Add adjustment identifier if status is processed
			advanceMasters.setAdjIdentifier(commonDao
					.findAdjustmentIdentifier());
			advanceMasters.setAdevIdentifier(currAdvanceMaster
					.getAdevIdentifier());
		} else if (currAdvanceMaster.getAdjIdentifier() != null
				&& currAdvanceMaster.getAdjIdentifier() > 0) {
			advanceMasters.setAdjIdentifier(currAdvanceMaster
					.getAdjIdentifier());
		}

	}

	private void copyAdvanceDetails(AdvanceMasters currAdvanceMaster,
			AdvanceMasters advanceMasters, UserProfile userProfile) {
		// copy detail fields
		if (currAdvanceMaster.getAdvanceDetailsCollection() != null) {
			List<AdvanceDetails> detailsList = new ArrayList<AdvanceDetails>();
			for (AdvanceDetails details : currAdvanceMaster
					.getAdvanceDetailsCollection()) {
				AdvanceDetails newDetails = new AdvanceDetails();
				newDetails.setAdvdIdentifier(null);
				newDetails.setAdvmIdentifier(advanceMasters);
				newDetails.setPytpIdentifier(details.getPytpIdentifier());
				newDetails.setDollarAmount(details.getDollarAmount());
				detailsList.add(newDetails);

			}
			advanceMasters.setAdvanceDetailsCollection(detailsList);
		}
	}

	private void copyAdvanceActions(AdvanceMasters currAdvanceMaster,
			AdvanceMasters advanceMasters, UserProfile userProfile) {
		// copy last action and status if approver performed submission
		if (!currAdvanceMaster.getAdvanceActionsCollection().isEmpty()) {
			AdvanceActions lastAction = this.getLatestAction(currAdvanceMaster)
					.get(0);
			if (!(IConstants.SUBMIT.equals(lastAction.getActionCode()))
					&& "A".equals(lastAction.getActionCode().substring(0, 1))
					&& !currAdvanceMaster.getStatus().equals(
							IConstants.PROCESSED)) {
				// some approval step has been completed. Create a new action
				// and copy previous action values.
				AdvanceActions lastApproverAction = new AdvanceActions();
				lastApproverAction.setAdvmIdentifier(advanceMasters);
				lastApproverAction.setActionCode(lastAction.getActionCode());
				// comments will be added as part of approver submission
				// lastApproverAction.setComments(lastAction.getComments());
				lastApproverAction.setModifiedUserId(userProfile.getUserId());
				advanceMasters.setStatus(currAdvanceMaster.getStatus());
				List<AdvanceActions> actionsList = new ArrayList<AdvanceActions>();
				actionsList.add(lastApproverAction);
				advanceMasters.setAdvanceActionsCollection(actionsList);
			}
		}
	}

	private void copyLiquidations(AdvanceMasters currentAdvanceMaster,
			AdvanceMasters advanceMaster, UserProfile profile) {
		if (currentAdvanceMaster.getAdvanceLiquidationsCollection() != null
				&& !currentAdvanceMaster.getAdvanceLiquidationsCollection()
						.isEmpty()) {
			List<AdvanceLiquidations> newLiquidationsList = new ArrayList<AdvanceLiquidations>();
			for (AdvanceLiquidations item : currentAdvanceMaster
					.getAdvanceLiquidationsCollection()) {
				AdvanceLiquidations newLiquidation = new AdvanceLiquidations();
				newLiquidation.setAdvanceMaster(advanceMaster);
				newLiquidation.setAmount(item.getAmount());
				newLiquidation.setExpenseMasterId(item.getExpenseMasterId());
				newLiquidation.setModifiedUserId(profile.getUserId());
				newLiquidationsList.add(newLiquidation);
			}
			advanceMaster.setAdvanceLiquidationsCollection(newLiquidationsList);
		}

	}

/**
 * Copies liquidations from a prior advance revision
 * @param advanceMaster
 * @param userProfile
 * @return
 */

	private List<AdvanceLiquidations> getLiquidationsListForCarryForward(
			AdvanceMasters advanceMaster, UserProfile userProfile) {
				
		List<AdvanceLiquidations> carryLiquidationList = new ArrayList<AdvanceLiquidations>();
		List<AdvanceMasters> advanceMasterList = advanceDao.
									findPreviousApprovedOrHigherRevisionForLiqCarryForward(advanceMaster.getAdevIdentifier());
		if (!advanceMasterList.isEmpty()){
			for (AdvanceLiquidations item: advanceMasterList.get(0).getAdvanceLiquidationsCollection()){
				ExpenseMasters expenseMaster = expenseDao.findExpenseByExpenseMasterId(
						item.getExpenseMasterId());
				if ("N".equals(expenseMaster.getCurrentInd())){
					// liquidations will be carried forward only for expense that have been approved
					continue;
				}
				AdvanceLiquidations liq = new AdvanceLiquidations();
				liq.setAdvanceMaster(advanceMaster);
				liq.setExpenseMasterId(item.getExpenseMasterId());
				liq.setAmount(item.getAmount());
				liq.setModifiedUserId(userProfile.getUserId());
				carryLiquidationList.add(liq);
				// added explicit persist statements in order to save all liquidations. Only the first element
				// in the list was getting saved without explicit persist 
				advanceDao.getEntityManager().persist(liq);
			}
		}
	
		return carryLiquidationList;
	}

	/**
	 * This method will be called by expense to liquidate an existing advance
	 * from a expense request
	 * 
	 * @param advanceMaster
	 * @param liquidations
	 * @return
	 */
	public boolean liquidate(List<AdvanceLiquidations> liquidationsList,
			UserProfile userProfile, ExpenseMasters expenseMasters) {
		validateLiquidations(liquidationsList, expenseMasters,userProfile);
		boolean success = true;
		try {
			for (AdvanceLiquidations item : liquidationsList) {
				item.setModifiedUserId(userProfile.getUserId());
				advanceDao.saveLiquidations(item);
			}
		} catch (Exception e) {
			logger.error(e);
			success = false;
		}
		return success;
	}

	/**
	 * Deletes an advance event
	 * 
	 * @param advanceEventId
	 * @return
	 */

	public boolean deleteAdvanceEvent(int advanceEventId) {
		if (this.validateDeleteAdvanceEvent(advanceEventId))
			return advanceDao.deleteAdvanceEvent(advanceEventId);

		if (logger.isInfoEnabled())
			logger.info("Advance Deleted with event id: " + advanceEventId);

		return false;
	}

	/**
	 * Retrieve advances for all appointments of an employee
	 * 
	 * @param employeeId
	 * @param reportType
	 * @return List<VAdvanceList>
	 */

	public List<VAdvanceList> getAdvanceListEmployee(int employeeId,
			String reportType) {

		if (reportType
				.equals(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED)) {
			return advanceDao.findAdvanceListByEmployeeId(employeeId);
		}

		else if (reportType
				.equals(IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY)) {
			return advanceDao
					.findNonAdjustedAdvanceListByEmployeeId(employeeId);
		}

		else if (reportType.equals(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY)) {
			return advanceDao.findAdjustedAdvanceListByEmployeeId(employeeId);
		}
		return null;
	}

	/**
	 * Get advances for a single appointment
	 * 
	 * @param apptId
	 * @param reportType
	 * @param userId
	 * @param moduleId
	 * @return List<AdvanceListItem>
	 */

	public List<VAdvanceList> getAdvanceListAppointment(int apptId,
			String reportType, String userId, String moduleId) {

		if (reportType
				.equals(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED)) {
			return advanceDao.findAdvanceListByApptID(apptId, userId, moduleId);

		} else if (reportType
				.equals(IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY)) {
			return advanceDao.findNonAdjustedAdvanceListByApptID(apptId,
					userId, moduleId);
		}

		else if (reportType.equals(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY)) {
			return advanceDao.findAdjustedAdvanceListByApptID(apptId, userId,
					moduleId);
		}
		return null;
	}

	/**
	 * This method updates retrieves the max revision number of the advance
	 * record.
	 * 
	 * @param int advanceEventId
	 * @return advanceEventId
	 */

	public int getMaxRevisionNo(int advanceEventId) {
		return advanceDao.findMaxRevisionNo(advanceEventId);
	}

	/**
	 * 
	 * This method calls methods to validate the SystemStartDate, DateRange, and
	 * FiscalYear passed in on the AdvanceMasters. If AdvanceMasters from action
	 * is null, the program returns to Actions with no further validation
	 * 
	 */
	public void validateDates(AdvanceMasters advanceMasters, UserProfile userProfile) {

		if (advanceMasters == null)
			return;

		this.validateSystemStartDate(advanceMasters, userProfile);
		this.validateDateRange(advanceMasters, userProfile);
		this.validateAdvanceRequestDate(advanceMasters, userProfile);
		// ZH - Not needed for advances, only needed for expenses
		// this.validateFiscalYear(advanceMasters);
	}

	//
	/*
	 * Validates Expense Request Date and adds error in the error table if it is
	 * greater than the Payroll Processing End Date
	 */
	private void validateAdvanceRequestDate(AdvanceMasters advanceMasters, UserProfile userProfile) {
		Date ppEndDate = commonService.findPpEndDate();
		if (ppEndDate != null
				&& ppEndDate.before(advanceMasters.getRequestDate())) {
			this
					.addError(
							advanceMasters,
							IConstants.ADVANCE_REQUESTDATE_GREATER_THAN_CURRENT_PP_ENDDATE, userProfile);
		}
	}

	/**
	 * If the systemStartDate or requestDate are invalid -- errors are added to
	 * Advance Errors Collection
	 * 
	 */

	private void validateSystemStartDate(AdvanceMasters advanceMasters, UserProfile userProfile) {

		Date systemStartDate = commonDao.getTimeAndExpenseStartDate();

		// checks to see if the requestDate is before the systemStartDate
		if (advanceMasters.getRequestDate().before(systemStartDate)) {
			this
					.addError(
							advanceMasters,
							IConstants.ADVANCE_DATE_EARLIER_THAN_TIMEEXPENSE_APPLICATION_START_DATE, userProfile);
		}
	}

	/**
	 * Used in Save validation to test for valid appointments. This method will
	 * also be called to display multiple appointments to the user, if there are
	 * mutiple appointments within the date range
	 * 
	 * @param advanceMasters
	 * @param moduleId
	 * @param userProfile
	 * @return List<AppointmentsBean>
	 */

	public List<AppointmentsBean> getAppointmentsForDateRange(Date fromDate,
			Date toDate, String moduleId, UserSubject userSubject,
			UserProfile userProfile) {

		List<AppointmentsBean> appointmentsList = null;

		if (moduleId.equalsIgnoreCase(IConstants.ADVANCE_EMPLOYEE)) {
			appointmentsList = apptDAO
					.findActiveAppointmentsByExpDatesEmployee(fromDate, toDate,
							moduleId, userSubject.getEmployeeId(), userProfile
									.getUserId());
		} else {
			appointmentsList = apptDAO
					.findActiveAppointmentByExpenseDatesManagerStatewide(
							fromDate, toDate, moduleId, userSubject
									.getEmployeeId(), userProfile.getUserId());
		}

		return appointmentsList;
	}

	/**
	 * Prepares history list from previously retrieved data
	 * 
	 * @param advanceEvent
	 * @return List<AdvanceHistory>
	 */

	public List<AdvanceHistory> getAdvanceHistory(AdvanceEvents advanceEvent) {

		List<AdvanceHistory> advanceHistoryList = advanceDao
				.findAdvanceHistory(advanceEvent.getAdevIdentifier());
		for (Iterator<AdvanceHistory> iterator = advanceHistoryList.iterator(); iterator
				.hasNext();) {
			AdvanceHistory item = (AdvanceHistory) iterator.next();
			item.setModifiedDateDisplay(TimeAndExpenseUtil.displayDateTime(item
					.getModifiedDate()));
		}
		return advanceHistoryList;
	}

	/**
	 * Sums up all liquidations for an advance and returns the result
	 * 
	 * @param advanceMaster
	 * @return double
	 */

	public void setAdvanceDisplayAmounts(AdvanceMasters advanceMaster,
			DisplayAdvance display) {
		AdvanceMasters advanceMasterUsedForDisplayAmounts = null;
		if (isStatusGreaterThanSubmit(advanceMaster)) {
			// current revision is the one we need
			advanceMasterUsedForDisplayAmounts = advanceMaster;
		} else {
			// Prior revisions exist. Loop through, find the last non-rejected
			// revison and get amount liquidated
			List<AdvanceMasters> advanceList = advanceMaster
					.getAdevIdentifier().getAdvanceMastersCollection();
			// current revision is at the last index, start with the one next to
			// last
			int numMastersMaxIndex = advanceList.size() - 1;
			int index = numMastersMaxIndex - 1;
			while (index >= 0) {
				if (isStatusGreaterThanSubmit(advanceList.get(index))) {
					advanceMasterUsedForDisplayAmounts = advanceList.get(index);
					break;
				}
				index--;
			}
		}
		if (advanceMasterUsedForDisplayAmounts != null)
			this.setDisplayAmounts(advanceMasterUsedForDisplayAmounts, display);

	}

	private void setDisplayAmounts(AdvanceMasters advanceMaster,
			DisplayAdvance display) {
		double amountOutstanding = advanceMaster.getAdevIdentifier()
				.getOutstandingAmount();
		double amountLiquidatedByDeposit = advanceMaster
				.getManualDepositAmount();
		double amountLiquidatedByExpense = this
				.calculateAmountLiquidatedByExpense(advanceMaster);
		display.setAmountOutstanding(amountOutstanding);
		display.setLiquidatedByDeposit(amountLiquidatedByDeposit);
		display.setLiquidatedByExpense(amountLiquidatedByExpense);
	}

	/**
	 * Retrieve a single advance event
	 * 
	 * @param advanceEventId
	 * @return AdvanceEvents
	 */

	public AdvanceEvents getAdvanceEvent(int advanceEventId) {
		return advanceDao.findAdvanceByAdvanceEventId(advanceEventId);
	}

	/**
	 * Determines the eligibility for an employee to receive new cash advances
	 * 
	 * @param appointmentId
	 *            - employee's primary appointment
	 * @return - The return value determines if the "Create New Advance" buttons
	 *         gets enabled on the advance list page boolean
	 */
	public boolean getAdvanceEligibility(int apptIdentifier) {
		boolean eligible = false;
		Appointments appointment = apptService.getAppointments(apptIdentifier);
		// Extract profile rule value
		ExpenseProfileRules profileRules = commonService
				.getExpenseProfileRules(apptIdentifier,
						IConstants.EXPENSE_PROFILE_RULE_ADVANCES_ALLOWED);
		AgencyOptions agencyOptions = commonDao
				.findAgencyOptions(appointment.getHrmnDeptCodes()
						.getHrmnDeptCodesPK().getDepartment(), appointment
						.getHrmnDeptCodes().getHrmnDeptCodesPK().getAgency());
		if (profileRules != null) {
			if (profileRules.getValue().equals("Y"))
				eligible = true;
			else if (profileRules.getValue().equals("N"))
				eligible = false;
			// use agency options since no rule is defined
			else if (profileRules.getValue().equals(
					IConstants.EXPENE_PROFILE_AGENCY_DEFAULT)) {
				if (agencyOptions.getAdvancesAllowedInd().equals("Y"))
					eligible = true;
			}
		} else {
			if (agencyOptions.getAdvancesAllowedInd().equals("Y"))
				eligible = true;
		}
		return eligible;
	}

	/**
	 * Returns the total advance outstanding amount for an employee. This method
	 * will be called from expenses
	 * 
	 * @param employeeId
	 * @param expenseMasterId
	 * @return double
	 */
	public double getTotalAdvanceOutstandingAmount(int employeeId) {

		return advanceDao.findAmountOutstandingByEmpId(employeeId);
	}

	/**
	 * This method is used to get advances pending approval for "My employees"
	 * working under a supervisor. The second parameter checks whether adjusted
	 * advances are to be retrieved or not
	 * 
	 * @param supervisorEmpId
	 * @param requestType
	 * @return VExpApprovalList - List of advances awaiting approval
	 */

	public List<AdvApprovalTransaction> getAdvancesAwaitingApprovalSupervisorMyEmployees(
			int supervisorEmpId, String requestType) {

		if (requestType == null || supervisorEmpId <= 0) {
			return null;
		}

		if (requestType
				.equals(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED)) {
			return advanceDao.findAdvancesAwaitingApproval(supervisorEmpId);
		} else if (requestType
				.equals(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY)) {
			return advanceDao
					.findAdjustedAdvancesAwaitingApproval(supervisorEmpId);
		} else if (requestType == IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY) {
			return advanceDao
					.findNonAdjustedAdvancesAwaitingApproval(supervisorEmpId);
		}
		return null;
	}

	/**
	 * Main validation method. This method performs all business validations and
	 * processes errors by saving'em in the database
	 * 
	 * @param advanceMasters
	 * @param moduleId
	 * @param userProfile
	 *            void
	 */

	private void validate(AdvanceMasters advanceMasters, String moduleId,
			UserProfile userProfile, UserSubject userSubject) {

		if (advanceMasters.getAdvanceErrorsCollection() != null
				&& (!advanceMasters.getAdvanceErrorsCollection().isEmpty())) {
			advanceDao.deleteAdvanceErrors(advanceMasters);
		}
		this.validateDates(advanceMasters, userProfile);
		this.validateManualWarrant(advanceMasters, userProfile);
		this.validateManualDeposit(advanceMasters, userProfile);
		// ZH - these validation are now triggered from Action or are not needed
		/*
		 */
		// this.validateApptsForDateRange(advanceMasters, moduleId, userSubject,	userProfile);
		// this.validateCodingBlocksInput(advanceMasters);
		this.validateCodingBlocks(advanceMasters, userSubject, userProfile);
		if (advanceMasters.getAdvanceErrorsCollection() != null
				&& (!advanceMasters.getAdvanceErrorsCollection().isEmpty())) {
			int size = advanceMasters.getAdvanceErrorsCollection().size();

			if (logger.isInfoEnabled())
				logger.info("Advance Masters "
						+ advanceMasters.getAdvmIdentifier() + " saved with "
						+ size + " errors.");

		}

	}

	/**
	 * If the dateRange passed from AdvanceMasters is invalid errors are added
	 * to Advance Errors Collection
	 */
	private void validateDateRange(AdvanceMasters advanceMasters, UserProfile userProfile) {

		if (advanceMasters.getFromDate().after(advanceMasters.getToDate())) {
			this.addError(advanceMasters,
					IConstants.ADVANCE_TO_DATE_EARLIER_THAN_EXPENSE_FROM_DATE, userProfile);
		}
	}

	public void validateApptsForDateRange(AdvanceMasters advanceMasters,
			String moduleId, UserSubject userSubject, UserProfile userProfile) {

		List<AppointmentsBean> appointmentsList = this
				.getAppointmentsForDateRange(advanceMasters.getRequestDate(),
						advanceMasters.getRequestDate(), moduleId, userSubject,
						userProfile);

		if (appointmentsList.isEmpty()) {
			this.addError(advanceMasters,
					IConstants.ADVANCE_INVALID_APPOINTMENTS_FOR_DATE_RANGE, userProfile);
		} else if (appointmentsList.size() > 1) {
			this.addError(advanceMasters,
					IConstants.ADVANCE_MUTIPLE_APPOINTMENTS_FOR_DATE_RANGE, userProfile);
		}

	}

	/**
	 * 
	 * @param advanceMasters
	 *            void
	 */
	private void validateCodingBlocks(AdvanceMasters advanceMasters,
			UserSubject userSubject, UserProfile userProfile) {

		CodingBlockElement cbElement = new CodingBlockElement();
		cbElement.setDeptCode(userSubject.getDepartment());
		cbElement.setAgency(userSubject.getAgency());
		cbElement.setTku(userSubject.getTku());
		cbElement.setStatus("A");
		cbElement.setPayDate(Calendar.getInstance().getTime());
		// cbElement.setAppropriationYear("08");

		if (advanceMasters.getAdvanceDetailsCollection().get(0)
				.getAdvanceDetailCodingBlocksCollection() != null)
			codingBlockService.validateAdvanceCodingBlocks(cbElement,
					advanceMasters.getAdvanceDetailsCollection(), userProfile);
	}

	public void validateManualWarrant(AdvanceMasters advanceMasters, UserProfile userProfile) {

		if (advanceMasters.getManualWarrantIssdInd().equals("Y")) {
			if (StringUtils.isEmpty(advanceMasters.getManualWarrantDocNum())) {
				this
						.addError(
								advanceMasters,
								IConstants.ADVANCE_MANUAL_WARRANT_DOCUMENT_NUMBER_MISSING, userProfile);
			}
		}

	}

	public void validateManualDeposit(AdvanceMasters advanceMasters, UserProfile userProfile) {

		if (advanceMasters.getManualDepositInd().equals("Y")) {
			if (StringUtils.isEmpty(advanceMasters.getManualDepositDocNum())) {
				this
						.addError(
								advanceMasters,
								IConstants.ADVANCE_MANUAL_DEPOSIT_DOCUMENT_NUMBER_MISSING, userProfile);
			}
			if (advanceMasters.getManualDepositAmount() == 0) {
				this.addError(advanceMasters,
						IConstants.ADVANCE_MANUAL_AMOUNT_MISSING, userProfile);
			}
		}

	}

	/**
	 * 
	 * @param liquidations
	 * @return
	 */
	public boolean validateLiquidations(
			List<AdvanceLiquidations> liquidationsList,
			ExpenseMasters expenseMasters, UserProfile userProfile) {

		commonService.deleteExpenseErrors(expenseMasters,
				IConstants.EXP_ERR_SRC_LIQ_TAB);
		if (liquidationsList != null && !liquidationsList.isEmpty()) {
			for (int i = 0; i < liquidationsList.size(); i++) {
				double liquidatedAmount = Double.valueOf(advanceDao
						.findAdvanceOutstandingByMasterId(liquidationsList.get(
								i).getAdvanceMaster().getAdvmIdentifier()));

				if (liquidationsList.get(i).getAmount() > liquidatedAmount) {
					commonService
							.populateErrors(
									IConstants.EXPENSE_LIQUIDATIONS_ERROR_LIQUIDATED_AMOUNT_GREATER_THAN_OUTSTANDING_ADVANCE,
									IConstants.EXP_ERR_SRC_LIQ_TAB
											+ ". Adv:"
											+ liquidationsList.get(i)
													.getAdvanceMaster()
													.getAdvmIdentifier(),
									expenseMasters, userProfile);
					return false;
				}

			}
		}
		return true;
	}

	/**
	 * Performs a check to see if an advance event may be deleted. The test is
	 * to see if only single masters exists and teh status of the single master
	 * is blank.
	 * 
	 * @param advanceEventId
	 * @return
	 */

	private boolean validateDeleteAdvanceEvent(int advanceEventId) {
		boolean canDelete = false;
		AdvanceEvents advanceEvent = advanceDao
				.findAdvanceByAdvanceEventId(advanceEventId);
		if (advanceEvent.getAdvanceMastersCollection() != null
				&& advanceEvent.getAdvanceMastersCollection().size() == 1) {
			if (StringUtils.isEmpty(advanceEvent.getAdvanceMastersCollection()
					.get(0).getStatus()))
				canDelete = true;
		}
		return canDelete;
	}

	/**
	 * Adds a new error to the errors collection
	 * 
	 * @param advanceMasters
	 * @param advError
	 *            void
	 */

	public void addError(AdvanceMasters advanceMasters, String advError, UserProfile userProfile) {
		List<AdvanceErrors> errorsList = advanceMasters
				.getAdvanceErrorsCollection();

		if (errorsList == null) {
			errorsList = new ArrayList<AdvanceErrors>();
			advanceMasters.setAdvanceErrorsCollection(errorsList);
		}

		ErrorMessages errorMessages = advanceDao.getEntityManager().find(
				ErrorMessages.class, advError);
		AdvanceErrors advanceErrors = new AdvanceErrors();
		advanceErrors.setAdvmIdentifier(advanceMasters);
		advanceErrors.setErrorCode(errorMessages);
		advanceErrors.setErrorSource(IConstants.ADVANCE_ERROR_SOURCE);
		if (userProfile != null)
			advanceErrors.setModifiedUserId(userProfile.getUserId());
		errorsList.add(advanceErrors);

	}

	/************ Getters and Setters *********************/

	public void setCodingBlockDao(CodingBlockDAO codingBlockDao) {
		this.codingBlockDao = codingBlockDao;
	}

	public CodingBlockDAO getCodingBlockDao() {
		return codingBlockDao;
	}

	public CodingBlockDSP getCodingBlockService() {
		return codingBlockService;
	}

	public void setCodingBlockService(CodingBlockDSP codingBlockService) {
		this.codingBlockService = codingBlockService;
	}

	public void setDao(AdvanceDAO advanceDao) {
		this.advanceDao = advanceDao;
	}

	public CommonDAO getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDAO commonDao) {
		this.commonDao = commonDao;
	}

	public AppointmentDAO getApptDAO() {
		return apptDAO;
	}

	public void setApptDAO(AppointmentDAO apptDAO) {
		this.apptDAO = apptDAO;
	}

	public CommonDSP getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonDSP commonService) {
		this.commonService = commonService;
	}
	
	public AdvanceDAO getAdvanceDao() {
		return advanceDao;
	}

	public void setAdvanceDao(AdvanceDAO advanceDao) {
		this.advanceDao = advanceDao;
	}

	/**
	 * 
	 * This method retrieves the AdvanceMasters by AdvanceMasterId
	 * 
	 * @param advanceMasterId
	 * @return
	 */

	public AdvanceMasters getAdvanceByMasterId(int advanceMasterId) {
		return advanceDao.findAdvanceByMasterId(advanceMasterId);
	}

	/**
	 * This method retrieves ExpenseLiquidations by AdvanceMasterId(ADVM)
	 * 
	 * @param expenseMasterId
	 * @return
	 */

	public double getExpenseLiquidationByMasterId(int expenseMasterId) {
		return advanceDao.findExpenseLiquidationByMasterId(expenseMasterId);
	}

	/**
	 * 
	 * @param employeeId
	 * @return
	 */

	public List<VAdvanceList> getAdvanceOutstandingListByEmpID(int employeeId) {
		return advanceDao.findAdvanceOutstandingListByEmpID(employeeId);
	}

	public AdvanceMasters getAdvanceByAdvanceEventId(AdvanceEvents advEvent,
			int revisionNo) {
		return advanceDao.findAdvanceByAdvanceEventId(advEvent, revisionNo);

	}

	public AdvanceMasters deleteAdvanceDetailCodingBlocks(
			AdvanceMasters advanceMasters) {
		return advanceDao.deleteAdvanceDetailCodingBlocks(advanceMasters);
	}

	/*public String getFacsAgency(Date fromDate, Date toDate, long appointmentId) {
		List<AppointmentsBean> listFacsAgencies = apptDAO
				.findFacsAgencyByExpenseDatesByEmployee(fromDate, toDate,
						appointmentId);
		if (!listFacsAgencies.isEmpty())
			return listFacsAgencies.get(0).getFacs_agy();
		else
			return "";
	}*/

	public AdvanceLiquidations getLiquidationByExpenseAndAdvanceMastId(
			ExpenseMasters expenseMaster, AdvanceMasters advanceMaster) {
		return advanceDao.findLiquidationByExpenseAndAdvanceMastId(
				expenseMaster, advanceMaster);
	}

	public boolean submitWithCbErrors(UserSubject userSubject,
			List<AdvanceErrors> errorsList) {
		boolean canSubmit = false;
		AgencyOptions agencyOptions = commonDao.findAgencyOptions(userSubject
				.getDepartment(), userSubject.getAgency());
		if (agencyOptions.getAllowInvalidCbElementsInd().equals("Y")) {
			if (commonService.allCodingBlockErrors(userSubject, errorsList))
				canSubmit = true;
		}

		return canSubmit;
	}

	public void setAdjustmentIdentifier(AdvanceMasters advanceMaster) {
		if (advanceMaster.getStatus().equalsIgnoreCase(IConstants.PROCESSED)) {
			// Add adjustment identifier if status is processed
			advanceMaster
					.setAdjIdentifier(commonDao.findAdjustmentIdentifier());
		}
	}

	/**
	 * Used to determine eligibility for advance modification
	 * 
	 * @param advanceMaster
	 * @return
	 */

	public List<AdvanceActions> getLatestAction(AdvanceMasters advanceMaster) {
		return advanceDao.findLatestAction(advanceMaster);
	}

	/**
	 * Sums up all liquidations for an advance for display purposes
	 */
	public double calculateAmountLiquidatedByExpense(AdvanceMasters advanceMaster) {

		double liquidationAmount = 0;
		List<AdvanceLiquidations> liqList = advanceDao.findAdvanceLiquidationsList(advanceMaster);
		if (liqList != null
				&& !liqList.isEmpty()) {
			for (AdvanceLiquidations item : liqList) {
				ExpenseMasters expense = advanceDao.getEntityManager().find(ExpenseMasters.class, 
						item.getExpenseMasterId());
				if (isStatusGreaterThanSubmit(expense)){
				liquidationAmount += item.getAmount();
				}
			}
		}

		return liquidationAmount;
	}

	/**
	 * Returns a list of advance masters that will be used for Liquidations. The
	 * method will return a prior revision of Advance Master if one exists where
	 * the current revision is in Submit or lower status but a prior revision
	 * exists that was in Approved or higher status.
	 * 
	 * @param empId
	 * @return
	 */
	public List<AdvanceMasters> getAdvanceListByEmpId(int empId,
			ExpenseMasters expenseMaster) {
		List<AdvanceMasters> advanceMasterList = advanceDao
				.findAllCurrentAdvanceMastersByEmpId(empId);
		List<AdvanceMasters> liqAdvanceMasterList = new ArrayList<AdvanceMasters>();
		for (AdvanceMasters item : advanceMasterList) {

			if (this.isStatusGreaterThanSubmit(item)) {
				// current revision may be used for liquidations
				liqAdvanceMasterList.add(item);
			} else if (item.getRevisionNumber() != 0) {
				// other revision exists
				AdvanceMasters adv = this.findPriorRevisionForLiquidation(item);
				if (adv != null) {
					// there is a prior revision that may be used for
					// liquidations. Add to the list
					liqAdvanceMasterList.add(adv);
				}

			}

		}
		// From the list of all advances, send back only the advances that have
		// outstanding amount greater than 0 or advances that have been totally
		// liquidated but were
		// used for the given expense
		Iterator<AdvanceMasters> it = liqAdvanceMasterList.iterator();
		while (it.hasNext()) {
			AdvanceMasters item = (AdvanceMasters) it.next();
			if (item.getAdevIdentifier().getOutstandingAmount() == 0) {
				if (advanceDao
						.findExpenseLiquidationsCount(item, expenseMaster) == 0) {
					it.remove();
				}

			}
		}

		return liqAdvanceMasterList;
	}

	/**
	 * Returns a non-current revision if in Approved or higher status
	 * 
	 * @param advanceMaster
	 * @return
	 */

	private AdvanceMasters findPriorRevisionForLiquidation(
			AdvanceMasters advanceMaster) {
		List<AdvanceMasters> allMastersForEventList = advanceDao
				.findPreviousApprovedOrHigherRevision(advanceMaster
						.getAdevIdentifier());
		if (!allMastersForEventList.isEmpty()) {
			return allMastersForEventList.get(0);
		}
		return null;
	}

	/**
	 * Returns if the Advance Master is in Approved or higher status
	 * 
	 * @param advanceMaster
	 * @return
	 */

	public boolean isStatusGreaterThanSubmit(AdvanceMasters advanceMaster) {
		if (advanceMaster.getStatus() == null)
			return false;
		if (advanceMaster.getStatus().equals(IConstants.APPROVED)
				|| advanceMaster.getStatus().equals(IConstants.EXTRACTED)
				|| advanceMaster.getStatus().equals(
						IConstants.HOURS_ADJUSTMENT_SENT)
				|| advanceMaster.getStatus().equals(IConstants.PROCESSED)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns if the Advance Master is in Approved or higher status
	 * 
	 * @param advanceMaster
	 * @return
	 */

	public boolean isStatusGreaterThanSubmit(ExpenseMasters expenseMaster) {
		if (expenseMaster.getStatus() == null)
			return false;
		if (expenseMaster.getStatus().equals(IConstants.APPROVED)
				|| expenseMaster.getStatus().equals(IConstants.EXTRACTED)
				|| expenseMaster.getStatus().equals(
						IConstants.HOURS_ADJUSTMENT_SENT)
				|| expenseMaster.getStatus().equals(IConstants.PROCESSED)) {
			return true;
		}
		return false;
	}

	/**
	 * Sets the appropriate Pay Type for Cash Advances and Manual Warrants
	 * 
	 * @param advanceMasters
	 */

	private void setPayType(AdvanceMasters advanceMasters) {
		AdvanceDetails advanceDetails = advanceMasters
				.getAdvanceDetailsCollection().get(0);
		if (advanceMasters.getManualWarrantIssdInd().equals("N"))
			advanceDetails.setPytpIdentifier(this
					.getPayType(IConstants.ADVANCE_PAYROLL_SYSTEM_CODE, advanceMasters.getRequestDate()));
		else if (advanceMasters.getManualWarrantIssdInd().equals("Y"))
			advanceDetails.setPytpIdentifier(this
					.getPayType(IConstants.ADVANCE_MANUAL_WARRANT_SYSTEM_CODE, advanceMasters.getRequestDate()));
	}

	/**
	 * Gets the pay type identifier for an advance
	 * 
	 * @param systemCode
	 * @return
	 */

	public Integer getAdvanceExpenseType(Integer payTypeCode, Date requestDate) {
		return advanceDao.findAdvanceExpenseType(payTypeCode, requestDate);
	}
	
	/**
	 * Gets the pay type identifier for an advance
	 * 
	 * @param systemCode
	 * @return
	 */

	public Integer getPayType(String systemCode, Date requestDate) {
		String systemCodeValue = commonService.getSystemCodeValue(systemCode);
		Integer payType = advanceDao.findPayType(systemCodeValue, requestDate);
		return payType;
	}

	/**
	 * Called to determine eligibility for Advance Modifications
	 * 
	 * @param advanceMaster
	 * @return
	 */

	public boolean previouslyProcessedRevisionExists(
			AdvanceMasters advanceMaster) {
		List<AdvanceMasters> allMastersForEventList = advanceDao
				.findAllNonCurrentAdvanceMastersByAdvanceEvent(advanceMaster
						.getAdevIdentifier());
		if (!allMastersForEventList.isEmpty()) {
			for (AdvanceMasters item : allMastersForEventList) {
				if (!StringUtils.isEmpty(item.getStatus())) {
					if (item.getStatus().equals(IConstants.PROCESSED)) {
						return true;
					}
				}

			}
		}
		return false;
	}

	/**
	 * Updates exiting Notification errors or inserts new ones in case a
	 * notification could not be sent.
	 * 
	 * @param advanceMasters
	 * @param userProfile
	 * @param returnCode
	 */

	private void processAdvanceNotificationErrors(
			AdvanceMasters advanceMasters, UserProfile userProfile,
			int returnCode) {
		boolean errorExists = false;
		if (advanceMasters.getAdvanceErrorsCollection() != null){
		Iterator<AdvanceErrors> it = advanceMasters.getAdvanceErrorsCollection().iterator();
		while (it.hasNext()) {
			AdvanceErrors error = (AdvanceErrors) it.next();
			if (IConstants.NOTIFICATION_NOT_DUE_TO_SYSTEM_PROBLEMS.equals(error
					.getErrorCode().getErrorCode())
					|| IConstants.NOTIFICATION_NOT_SENT_SINCE_USERID_NOT_DEFINED
							.equals(error.getErrorCode().getErrorCode())) {
				// there are existing notification errors... update user id only
				error.setModifiedUserId(userProfile.getUserId());
				errorExists = true;
			}
		}
		}

		if (!errorExists) {
			// add new notification error according to the return code received
			if (returnCode == Integer
					.parseInt(IConstants.NOTIFICATION_NOT_SENT_SINCE_USERID_NOT_DEFINED))
				this
						.addError(
								advanceMasters,
								IConstants.NOTIFICATION_NOT_SENT_SINCE_USERID_NOT_DEFINED, userProfile);
			else if (returnCode == Integer
					.parseInt(IConstants.NOTIFICATION_NOT_DUE_TO_SYSTEM_PROBLEMS)) {
				this.addError(advanceMasters,
						IConstants.NOTIFICATION_NOT_DUE_TO_SYSTEM_PROBLEMS, userProfile);
			}
		}

	}
	//AI-26436
	public List<AppointmentsBean> getActiveAppointments(Date advanceRequestDate1, Date advanceRequestDate2, 
			String userId, String moduleId,long empId) {//advanceRequestDate1 and advanceRequestDate2 are the same date.That is advance request date
		
		if (moduleId.equalsIgnoreCase(IConstants.ADVANCE_EMPLOYEE)) {
			return apptDAO.findActiveAppointmentsByExpDatesEmployee(advanceRequestDate1, advanceRequestDate2,moduleId,empId,userId);

		}else if (moduleId.equalsIgnoreCase(IConstants.ADVANCE_MANAGER)	|| moduleId.equalsIgnoreCase(IConstants.ADVANCE_STATEWIDE)
				|| moduleId.equalsIgnoreCase(IConstants.APPROVE_WEB_MANAGER)	|| moduleId.equalsIgnoreCase(IConstants.APPROVE_WEB_STATEWIDE)) {
			return apptDAO.findActiveAppointmentByExpenseDatesManagerStatewide(advanceRequestDate1, advanceRequestDate2,moduleId,empId,userId);
		}
		return null;
		
	}//AI-26436

	public AdvanceMasters getAdvanceMasterByAdvanceEventId(
			AdvanceEvents advanceEvent, int revisionNo) {
		return advanceDao.findAdvanceByAdvanceEventId(advanceEvent, revisionNo);
	}
	
	private void setCarryforwardLqiuidaionsList (AdvanceMasters advanceMasters, UserProfile userProfile){
		// Final approval step - set amount outstanding and carry forward
		// liquidations if needed. This may not happen since new advances
		// from APPR status also end up in APPR status so new revision creation will
		// carry forward liquidations
		if (advanceMasters.getRevisionNumber() == 0){
			// only revision. No need for carry forward
			return;
		}
		List<AdvanceLiquidations> liqList = this
				.getLiquidationsListForCarryForward(advanceMasters,
						userProfile);
		if (liqList != null && !liqList.isEmpty()){
		advanceMasters
				.setAdvanceLiquidationsCollection(liqList);
		}
	}
	
	private void saveAdvanceErrors (AdvanceMasters advanceMaster){
		if(advanceMaster.getAdvanceErrorsCollection() != null &&
				!advanceMaster.getAdvanceErrorsCollection().isEmpty()){
			for(AdvanceErrors error : advanceMaster.getAdvanceErrorsCollection()){
				if(error.getAdveIdentifier() == null)
					advanceDao.getEntityManager().persist(error);
				else
					advanceDao.getEntityManager().merge(error);
			}
		}
	}
	
	/**
	 * Checks to see if there are any expenses in HSNT or XTCT status that have been used to liquidate an advance
	 * @param advanceMaster
	 * @return
	 */
	
	public boolean existLiquidationsExpenseXTCTorHSNT (AdvanceMasters advanceMaster){
			long count = advanceDao.findAdvanceLiquidationsCountXTCTorHSNT(advanceMaster);
			if (count > 0){
				return true;
			}
		return false;
	}
	
	public double getAmountOutstandingByEventId (int advanceEventId){
		return advanceDao.findAmountOutstandingByEventId(advanceEventId);
	}
	
	/**
	 * This method checks to see if any advance exists for the expense dates passed in the parameters
	 *  
	 * @param fromDat1
	 * @param toDat1
	 * @param advmId
	 * @return double
	 */
	
	public double getNonPermAdvance(Date expDateFrom, Date expDateTo, int advanceMasterId)
	{
	double retVal=0.0;
	retVal = advanceDao.findNonPermAdvance(expDateFrom, expDateTo, advanceMasterId);
	return retVal;
	}
	
	/**
	 * Returns following Action (Approval Paths) 
	 * <p>
	 * Method return Approval Paths only if the current status of the expense is either <code>SUBM</code> or 
	 * any other Action codes present in the  <code>tkuopt_approval_paths</code> Table.
	 * 
	 * @param	expmIdentifier  expense master identifier
	 * @param	UserSubject  UserSubject for department, agency ,tku
	 * @return	string with remaining approval paths     
	 * 
	 */
	public String getRemainingApprovalPaths(Integer advmIdentifier, UserSubject UserSubject) {
		return advanceDao.getRemainingApprovalPaths(advmIdentifier, UserSubject);
	}
	
}
