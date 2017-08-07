package gov.michigan.dit.timeexpense.service;

import gov.michigan.dit.timeexpense.dao.AppointmentDAO;
import gov.michigan.dit.timeexpense.dao.ApproveDAO;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseActionsDAO;
import gov.michigan.dit.timeexpense.dao.TravelRequisitionDAO;
import gov.michigan.dit.timeexpense.exception.ExpenseException;
import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
import gov.michigan.dit.timeexpense.model.core.CodingBlockElement;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.OutOfStateTravel;
import gov.michigan.dit.timeexpense.model.core.StateAuthCodes;
import gov.michigan.dit.timeexpense.model.core.TravelReqActions;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetailCodingBlock;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetails;
import gov.michigan.dit.timeexpense.model.core.TravelReqErrors;
import gov.michigan.dit.timeexpense.model.core.TravelReqEvents;
import gov.michigan.dit.timeexpense.model.core.TravelReqHistory;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.TravelReqOutOfState;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.core.VTravelReqList;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.TravelReqListBean;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class TravelRequisitionDSP {
	TravelRequisitionDAO treqDAO;
	AppointmentDAO apptDAO;
	CommonDAO commonDao;
	private ApproveDAO approveDao;
	private ExpenseActionsDAO expenseActionsDao;
	
	CommonDSP commonDsp;
	ExpenseLineItemDSP expenseLineService;
	ExpenseDSP expenseService;
	CodingBlockDSP codingBlockService;
	AdvanceDSP advanceService;
	private SecurityManager securityService;
	private AdvanceLiquidationDSP liquidationService;	
	private EmailNotificationDSP emailService;
	
	Logger logger = Logger.getLogger(TravelRequisitionDSP.class);

	public TravelRequisitionDSP(EntityManager em) {
		commonDsp = new CommonDSP(em);
		expenseLineService = new ExpenseLineItemDSP(em);
		codingBlockService = new CodingBlockDSP(em);
		approveDao = new ApproveDAO(em);
		advanceService = new AdvanceDSP(em);
		securityService = new SecurityManager(em);
		liquidationService = new AdvanceLiquidationDSP(em);				
		treqDAO = new TravelRequisitionDAO(em);
		apptDAO = new AppointmentDAO(em);
		commonDao = new CommonDAO(em);
		emailService = new EmailNotificationDSP(em);
		expenseActionsDao = new ExpenseActionsDAO(em);
		expenseService = new ExpenseDSP(em);
	}
	
	public List<VTravelReqList> getTravelReqListEmployee(int empId,
			String expIncludeAdjustment) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpensesListEmployee");
		
		List<VTravelReqList> expenseListBean = null;
		if (expIncludeAdjustment == null || empId < 0) {
			return null;
		}
		// Corresponding DAO methods are invoked depending on the expIncludedAdjustment parameter value
		if (expIncludeAdjustment.equalsIgnoreCase(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY))  /* ADJUSTED Expenses */
			expenseListBean = treqDAO.findAdjustedTravelReqsByEmployeeId(empId);
		else if (expIncludeAdjustment.equalsIgnoreCase(IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY)) /* NON_ADJUSTED Expenses */
			expenseListBean = treqDAO.findNonAdjustedTravelReqsByEmployeeId(empId);
		else if(expIncludeAdjustment.equalsIgnoreCase(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED)) /* BOTH Expenses */
			expenseListBean = treqDAO.findTravelReqsByEmployeeId(empId);

		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpensesListEmployee");
		
		return expenseListBean;
	}
	
	public List<TravelReqListBean> getTravelReqsListAppointment(int appointmentId,
			String expAdjustmentIdentifier, String userId, String moduleId) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpensesListAppointment");
		
		List<TravelReqListBean> expenseListBean = new ArrayList<TravelReqListBean>();
		if (appointmentId < 0 || expAdjustmentIdentifier == null
				|| userId == null || moduleId == null) {
			return null;
		}
		// Depending on the Adjustment identifier different DAO methods are called.
		if (expAdjustmentIdentifier.equalsIgnoreCase(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY)) 
			expenseListBean = treqDAO.findAdjustedTravelReqsByAppointmentId(appointmentId,userId, moduleId);
		else if (expAdjustmentIdentifier.equalsIgnoreCase(IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY)) 
			expenseListBean = treqDAO.findNonAdjustedTravelReqsByAppointmentId(appointmentId,userId, moduleId);
		else if(expAdjustmentIdentifier.equalsIgnoreCase(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED))
			expenseListBean = treqDAO.findTravelReqsByAppointmentId(appointmentId, userId, moduleId);

		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpensesListAppointment");
		
		return expenseListBean;
	}
	
	/**
	 * This method deletes the travel requisition from the  database.
	 * @param expenseeventId
	 * @return boolean 
	 */

	public boolean deleteTravelRequisition(int treqEventId) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : deleteTravelRequisition");
		
		boolean isDeleted = false;
		if (treqEventId > 0) 
			isDeleted = treqDAO.deleteTravelRequisition(treqEventId);
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : deleteTravelRequisition");
		
		return isDeleted;
	}
	
	public List<StateAuthCodes> getAuthorizationCodes() {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getAuthorizationCodes");

		List<StateAuthCodes> authCodeList = treqDAO.findAuthorizationCodes();

		if(logger.isDebugEnabled())
			logger.debug("Exit method : getAuthorizationCodes");
		
		return authCodeList;
	}
	
	public TravelReqMasters getTravelRequisition (int treqMasterId) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getTravelRequisition");
		// find Expense 
		TravelReqMasters expenseMasters = treqDAO.findTravelRequisitonByMasterId(treqMasterId);
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpense");

		return expenseMasters;
	}
	
	public List<AppointmentsBean> getActiveAppointments(Date treqStartDate, Date treqEndDate, 
			String userId, String moduleId,long empId) {
		
		if (moduleId.equalsIgnoreCase("EXPW001")) {
			return apptDAO.findActiveAppointmentsByExpDatesEmployee(treqStartDate, treqEndDate,moduleId,empId,userId);

		}else if (moduleId.equalsIgnoreCase("EXPW002")	|| moduleId.equalsIgnoreCase("EXPW003")
				|| moduleId.equalsIgnoreCase("APRW003")	|| moduleId.equalsIgnoreCase("APRW004")) {
			return apptDAO.findActiveAppointmentByExpenseDatesManagerStatewide(treqStartDate, treqEndDate,moduleId,empId,userId);
		}
		return null;
	}
	
	public TravelReqMasters getTravelRequisitionByEventId(int treqEventId, int revisionNo){
		return treqDAO.findTravelRequisitionByEventId(treqEventId, revisionNo);
	}
	
	public boolean isTravelRequisitionModifiableByEmployee(TravelReqMasters treqMaster, UserProfile user, UserSubject subject){
        if(treqMaster == null) return false;
        boolean result = false;

                    String status = treqMaster.getStatus();
                    if(IConstants.SUBMIT.equals(status)){
                                //get the last action performed on the expense
                                TravelReqActions lastAction = null;
                                List<TravelReqActions> actions = treqDAO.findTravelRequisitionActions(treqMaster);
                                if(actions != null && !actions.isEmpty()){
                                            lastAction = actions.get(actions.size()-1);
                                            // Find whether this last action is in approval chain. If it is, then this expense
                                            // is in approval path and first level approver has approved it. If not, then this
                                            // expense is still awaiting for first level approval, and hence can be modified.
                                            if(!approveDao.isExpenseInApprovalPathAfterFirstLevelApproval(lastAction.getActionCode())) 

                                                        result = true;
                                }
                    }
                    else if(IConstants.REJECTED.equals(status)) result = true;

        return result;
}
	
	public boolean isTravelRequisitionModifiableBySupervisor(TravelReqMasters treqMaster, UserProfile user, UserSubject subject) {

        if(treqMaster == null || user == null || subject == null) return false;
        boolean result = false;
        
        // user cannot modify his own expense when arriving through Mgr/SW link. He must go through 'Employee' link
        if(user.getEmpIdentifier() == subject.getEmployeeId()) return result;
        
        ExpenseMasters prevExpInProc = null;

        String status = treqMaster.getStatus();
        // Supervisors can modify submitted expenses until they have not been extracted 
        // or partially extracted (i.e. adjustment initiated as indicative by the adj_type)
        if(status == null || "".equals(status)) 
                    result = false;
        else if(IConstants.SUBMIT.equals(status) || IConstants.APPROVED.equals(status)){
                    //get the last action performed on the expense
        	 		TravelReqActions lastAction = null;
        	 		List<TravelReqActions> actions = treqDAO.findTravelRequisitionActions(treqMaster);
                    boolean writeAccessGranted = true;
                    if(actions != null && !actions.isEmpty()){
                                lastAction = actions.get(actions.size()-1);
                                List<String> securityModules = expenseActionsDao.findAllModulesForExpenseAction(lastAction.getActionCode());
                                for(String secModule: securityModules){
                                            if(!securityService.checkModuleAccess(user, secModule, subject.getDepartment(), subject.getAgency(), subject.getTku())){

                                                        writeAccessGranted = false;
                                                        break;
                                            }
                                }
                                // allow MODIFY access if he has access to perform NEXT action but didn't perform the previous action
                                if(!writeAccessGranted){
                                            List<String> secModules = expenseActionsDao.findAllModulesForExpenseAction(lastAction.getNextActionCode());
                                            //boolean writeAccessGranted = true;
                                            for(String secModule: secModules){
                                                        if(securityService.checkModuleAccess(user, secModule, subject.getDepartment(), subject.getAgency(), subject.getTku())){
                                                                    writeAccessGranted = true;
                                                                    break;
                                                        }
                                            }
                                }
                                if(writeAccessGranted) result = true;
                    }
        }
        else if(IConstants.REJECTED.equals(status)){
                    result = true;
        }
        return result;
}           
	
	public int getMaxRevisionNo(int treqEventId){
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getMaxRevisionNo");
		int maxRevisionNo = 0;
		
		maxRevisionNo = treqDAO.findMaxRevisionNo(treqEventId);
				
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getMaxRevisionNo");
		 
		return maxRevisionNo;
	}
	
	public TravelReqMasters saveTravelRequisition(TravelReqMasters treqMasters, TravelReqDetails treqDetails,
			int apptIdentifier, String moduleId, UserProfile userProfile) throws Exception{

		TravelReqMasters savedTravelRequisition = null;
		
		//If the expev_identifier does not exist, create a new ExpenseMaster
		if (treqMasters.getTreqeIdentifier()== null) {
			savedTravelRequisition = this.saveNewTravelRequisition(treqMasters, treqDetails, apptIdentifier, moduleId, userProfile);
		//If the expev_identifier exist, create a new version with the modifications
		} else {
			savedTravelRequisition = this.saveTravelRequisitionExisting(treqMasters, userProfile, treqDetails);
		}

		return savedTravelRequisition;

	} 
	
	public TravelReqMasters saveTravelRequisitionExisting(TravelReqMasters currTreqMaster, UserProfile userProfile, TravelReqDetails treqDetails) throws Exception{
		TravelReqMasters savedTravelRequisition = null;

		// if never submitted
		if (currTreqMaster.getStatus() == null || currTreqMaster.getStatus().equals("")) {
			// save the existing expense .. the revision number and the adjustment identifier remains same
			currTreqMaster.setModifiedUserId(userProfile.getUserId());
			addTravelReqDetails(currTreqMaster, treqDetails);
			validateTravelRequisitionID(currTreqMaster, userProfile);
			savedTravelRequisition = treqDAO.saveTravelRequisition(currTreqMaster);
		}
		// if submitted or higher
		else{
			// Copying data to the new ExpenseMaster version from current ExpenseMaster
			TravelReqMasters treqMasters = prepareTravelRequisitionEntityFromCurrent(currTreqMaster);
			
			// set old expense as non current.
			currTreqMaster.setCurrentInd("N");
			List<TravelReqDetailCodingBlock> currentCodingBlocks = currTreqMaster.getTravelReqDetailsCollection().get(0).getTravelReqDetailCodingBlockCollection();
			// copy details and coding blocks
			addTravelReqDetails(treqMasters, treqDetails);
			copyCodingBlocks(treqMasters, currentCodingBlocks);
			validateTravelRequisitionID(treqMasters, userProfile);
			treqMasters.setStatus("");
			treqMasters.setRevisionNumber(treqMasters.getRevisionNumber()+1);
			treqMasters.setModifiedUserId(userProfile.getUserId());
			
			savedTravelRequisition = treqDAO.saveTravelRequisition(treqMasters);
		}
		return savedTravelRequisition;
	}
	
	public TravelReqMasters saveNewTravelRequisition(TravelReqMasters treqMaster, TravelReqDetails treqDetails,
			int apptIdentifier, String moduleId, UserProfile userProfile){
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : saveNewExpense");
		
		addTravelReqDetails(treqMaster, treqDetails);
		// add standard coding blocks
		addStandardCodingBlock (treqMaster.getTravelReqDetailsCollection().get(0));
		// perform biz validations and populate errors, if any
		try{
		validateTravelRequisitionID(treqMaster, userProfile);
		} catch (Exception e){
			logger.info("saveNewTravelRequisition: " + e.getMessage());
		}
		
		// Create new instance of Expense Event
		TravelReqEvents newTravelRequisitionEvent = new TravelReqEvents();
		// Get the Appointment
		//Appointments appointment = expenseDAO.getEntityManager().find(Appointments.class, apptIdentifier);
		
		newTravelRequisitionEvent.setApptIdentifier(apptIdentifier); // Attach appointment to the Expense Event
		newTravelRequisitionEvent.setModifiedUserId(userProfile.getUserId());
		
		treqDAO.getEntityManager().flush();
		
		treqMaster.setTreqeIdentifier(newTravelRequisitionEvent); // Attach Expense Event to the Expense Master
		
		treqMaster.setCurrentInd("Y"); // Set current indicator to "Y"
		treqMaster.setRevisionNumber(0); // First revision
		treqMaster.setModifiedUserId(userProfile.getUserId()); // Set the userId
		
		List<TravelReqMasters> treqMasterList = new ArrayList<TravelReqMasters>();
		treqMasterList.add(treqMaster);
		
		// Set Expense Masters collection
		newTravelRequisitionEvent.setTravelReqMastersCollection(treqMasterList);
		
		List<TravelReqOutOfState> travelList = null;
		
		if (treqMaster.getTravelReqOutOfStateCollection() != null){
			travelList = treqMaster.getTravelReqOutOfStateCollection();
			//treqMaster.setTravelReqOutOfStateCollection(null);
			}
		
		treqDAO.getEntityManager().flush();
		
		//Call Save
		treqDAO.saveTravelRequisition(newTravelRequisitionEvent);
		if (travelList != null){
			for(TravelReqOutOfState oost : travelList){
				if(oost.getTreqOostIdentifier() == null)
					treqDAO.getEntityManager().persist(oost);
				else
					oost = treqDAO.getEntityManager().merge(oost);
				treqDAO.getEntityManager().flush();
			}
			}
		
		treqDAO.getEntityManager().flush();

		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : saveNewExpense");
		
		return treqMaster;
	}

	public TravelReqMasters submitTravelRequisition(TravelReqMasters treqMasters,
			UserSubject subject, UserProfile userProfile, String approverComments) throws Exception {
		
		boolean fatalErrorExists = false;
		//cn-bug-346
		// For coding block validation
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");  
		String appropriationYear = String.valueOf(simpleDateformat.format(treqMasters.getTreqDateTo()));
		
		CodingBlockElement cbElement = new CodingBlockElement(subject
				.getAgency(), appropriationYear, "", subject.getDepartment(),
				treqMasters.getTreqDateTo(), "A", subject.getTku());
		codingBlockService.validateTravelRequisitionCodingBlocks(treqMasters,
				cbElement, treqMasters.getTravelReqDetailsCollection(),
				userProfile);
		// cn-bug-346
		// Check whether Errors of Severity "ERROR" exists
		if (treqMasters.getTreqErrorsCollection() != null) {
			if (treqMasters.getTreqErrorsCollection().size() > 0) {
				for (TravelReqErrors errors : treqMasters
						.getTreqErrorsCollection()) {
					if (errors.getErrorCode().getIcon() == 2) { // Icon value 2
																// indicates an
																// ERROR
						fatalErrorExists = true;
						break;
					}
				}
			}
		}
		
		if (fatalErrorExists) {
			boolean canSubmit = false;
			boolean cbErrors = commonDsp.allCodingBlockErrorsTravelReq(subject, treqMasters.getTreqErrorsCollection());
			String allowInvalidCbElementsInd = "N";
			if (cbErrors) {
				AgencyOptions agencyOptions = commonDao.findAgencyOptions(subject.getDepartment(), subject.getAgency());					
				allowInvalidCbElementsInd = agencyOptions.getAllowInvalidCbElementsInd();
				if (allowInvalidCbElementsInd.equals("Y")) {
					canSubmit = true;
				}
			}
			// SUBMIT cannot proceed if there are ERROR level errors
			if (!canSubmit) {
				// commonDsp.populateErrors(errorCode, errorSource,expenseMasters);
				throw new ExpenseException(IConstants.EXPENSE_SUBMIT_ERROR);
			}
		}
		
	TravelReqMasters prevTreqMasters = null;
		
		if(treqMasters.getRevisionNumber()>0){
			prevTreqMasters = treqDAO.findTravelRequisitionByEventId(
					treqMasters.getTreqeIdentifier().getTreqeIdentifier(), 
					treqMasters.getRevisionNumber()-1);
		}

		if (prevTreqMasters != null) {
			// an modify-by-approver by scenario and an action already exists,
				treqMasters = assignStatusAndMoveToAppropriateQueue(treqMasters, prevTreqMasters, userProfile, subject, approverComments);
		} else {
			// new submission update status on Masters to SUBM
			treqMasters.setStatus(IConstants.SUBMIT);

			// Create a new instance of Actions Collections
			List<TravelReqActions> actionsList = treqMasters
					.getTravelReqActionsCollection();

			if (actionsList == null) {
				actionsList = new ArrayList<TravelReqActions>();
				treqMasters.setTravelReqActionsCollection(actionsList);
			}

			// Create a new instance of Actions
			TravelReqActions actions = new TravelReqActions();
			actions.setActionCode(IConstants.SUBMIT);
			// Update actions with advm from Masters
			actions.setTreqmIdentifier(treqMasters);
			actions.setModifiedUserId(userProfile.getUserId());
			// Add new instance of Actions to Collection of Actions
			actionsList.add(actions);
		}

 			treqMasters = treqDAO.saveTravelRequisition(treqMasters);

		return treqMasters;
	}
	
	public List<TravelReqHistory> getTravelRequisitionHistory(int treqEventId) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getTravelRequisitionHistory");
		
		List<TravelReqHistory> treqHistoryList = new ArrayList<TravelReqHistory>();
		List<TravelReqHistory> treqHistoryListNew = new ArrayList<TravelReqHistory>();		
		treqHistoryList = treqDAO.findTravelRequisionHistory(treqEventId);
		for (Iterator iterator = treqHistoryList.iterator(); iterator.hasNext();) {
			TravelReqHistory item = (TravelReqHistory) iterator.next();
			item.setModifiedDateDisplay(TimeAndExpenseUtil.displayDateTime(item.getModifiedDate()));
			treqHistoryListNew.add(item);
		}		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpenseHistory");
		
		return treqHistoryListNew;
	}
	
	public String getNextActionCode(TravelReqMasters treqMaster) {
		List<TravelReqActions> treqActionList = treqDAO.approveTravelRequisition(treqMaster);
		if (treqActionList != null && !treqActionList.isEmpty()) {
			String str=treqActionList.get(0).getNextActionCode();
			return treqActionList.get(0).getNextActionCode();
		}
		return null;
	}
	
	public void validateTravelRequisitionID(TravelReqMasters treqMasters, UserProfile userProfile) throws Exception{

		
		// Delete Expense Errors having Error source starting with 'ID'   
		List<TravelReqErrors> errorsList = treqMasters.getTreqErrorsCollection();
		if(errorsList==null)
			errorsList = new ArrayList<TravelReqErrors>();
		else
			treqMasters = deleteErrors(treqMasters, IConstants.EXP_ERR_SRC_ID_TAB);
		
		// Invoke validations
		validateDate(treqMasters, userProfile);
		validateOutOfStateTravel(treqMasters, userProfile);
		validateExpenseDetailAmount(treqMasters, userProfile);
	}
	
	public void validateDate(TravelReqMasters treqMasters,UserProfile userProfile) throws Exception{
		
		boolean isStartDateEarlierThanTEStartDate = isStartDateEarlierThanTEStartDate(treqMasters.getTreqDateFrom());
		boolean isStartDateGreaterThanEndDate = isStartDateGreaterThanEndDate(treqMasters.getTreqDateFrom(),treqMasters.getTreqDateTo());
		boolean isExpenseDateSpanFiscalYears = isExpenseDateSpanFiscalYears(treqMasters.getTreqDateFrom(),treqMasters.getTreqDateTo());
		
		// populate Expense Errors
		if(isStartDateEarlierThanTEStartDate)
			commonDsp.populateErrors(IConstants.EXPENSE_DATE_EARLIER_THAN_TIMEEXPENSE_APPLICATION_START_DATE,IConstants.EXP_ERR_SRC_ID_TAB, treqMasters, userProfile);

		if(isStartDateGreaterThanEndDate)
			commonDsp.populateErrors(IConstants.EXPENSE_TO_DATE_EARLIER_THAN_EXPENSE_FROM_DATE,IConstants.EXP_ERR_SRC_ID_TAB, treqMasters,userProfile);
		
		if(isExpenseDateSpanFiscalYears)
			commonDsp.populateErrors(IConstants.EXPENSE_DATES_SPAN_FISCAL_YEAR,IConstants.EXP_ERR_SRC_ID_TAB, treqMasters, userProfile);

		if(logger.isDebugEnabled())
			logger.debug("Exit method : validateDate");
	}
	
	private boolean isStartDateEarlierThanTEStartDate(Date expenseStartDate){

		boolean isInValid = false;
		
		// Get the Time and Expense start date
		Date te_startDate = commonDao.getTimeAndExpenseStartDate();
		if(logger.isInfoEnabled())
			logger.info("Validating : if Expense from date is greater than Time&Expense Start date");
		// check for EXPENSE_DATE_EARLIER_THAN_TIMEEXPENSE_APPLICATION_START_DATE
		if (expenseStartDate.before(te_startDate)) 
			isInValid = true;
		
		return isInValid;
	}
	
	private boolean isStartDateGreaterThanEndDate(Date expenseStartDate, Date expenseEndDate){
		boolean isInValid = false;
		if(logger.isInfoEnabled())
			logger.info("Validating : if Expense from date is greater than Expense to date");
		
		// check for EXPENSE_TO_DATE_EARLIER_THAN_EXPENSE_FROM_DATE
		if (expenseStartDate.after(expenseEndDate)) {
			isInValid = true;
		}
		return isInValid;
	}
	
	private boolean isExpenseDateSpanFiscalYears(Date expenseStartDate, Date expenseEndDate) throws Exception{
		boolean isInValid = false;
		
		if(logger.isInfoEnabled())
			logger.info("Validating : if Expense from date spans fiscal year");
		
		// check for EXPENSE_DATES_SPAN_FISCAL_YEAR
		Date fiscalYearEndDate =  commonDsp.getFiscalYearEndDate(expenseStartDate);
		if ((expenseStartDate.before(fiscalYearEndDate) || expenseStartDate.equals(fiscalYearEndDate)) && expenseEndDate.after(fiscalYearEndDate)) {
			isInValid = true;
		}
		return isInValid;
	}
	
	private TravelReqMasters prepareTravelRequisitionEntityFromCurrent(TravelReqMasters currTreqMaster){
		// deep copy expense master
		TravelReqMasters newExpensemaster = new TravelReqMasters(currTreqMaster);
		newExpensemaster.setTreqeIdentifier(currTreqMaster.getTreqeIdentifier());

		//[mc]: Deep copy of liquidations also required here !!
		// But we cannot do it here as liquidation doesn't have expense but only expenseId. And Id is unavailable
		// until the new expense is flushed. So liquidations carry over part should be done in action layer after
		// saving of new expense is complete.
		// Similarly, the new expense needs to be put into the same action queue as the old one, if in SUBM or APPR status.
		// So this too shud be done in action layer.
		
		return newExpensemaster;		
	}
	
	public TravelReqMasters approveTravelRequisition(TravelReqMasters treqMaster,
			UserSubject subject, UserProfile userProfile, String approverComments) {

		List<TravelReqActions> treqActionList = treqDAO
				.findNextAction(treqMaster);
		TravelReqActions newTreqActions = new TravelReqActions();

		if (treqActionList != null && !treqActionList.isEmpty()) {
			// set new action to the last_action_code of previous action
			String action = treqActionList.get(0).getNextActionCode();
			newTreqActions.setActionCode(action);
		}

		newTreqActions.setTreqmIdentifier(treqMaster);
		newTreqActions.setModifiedUserId(userProfile.getUserId());
		if (!StringUtils.isEmpty(approverComments))
			newTreqActions.setComments(approverComments);

		if (treqMaster.getTravelReqActionsCollection() != null){
			treqMaster.getTravelReqActionsCollection().add(newTreqActions);
		}
		else {
			List<TravelReqActions> newTreqActionsList = new ArrayList<TravelReqActions>();
			newTreqActionsList.add(newTreqActions);
			treqMaster.setTravelReqActionsCollection(newTreqActionsList);
		}
		
		treqMaster = treqDAO.saveTravelRequisition(treqMaster);
		treqDAO.getEntityManager().flush();
		treqMaster = treqDAO.updateTravelRequisitionStatus(treqMaster);

		treqMaster.setModifiedUserId(userProfile.getUserId());

		return treqMaster;
	}

	
	public List<TravelReqActions> getLatestAction(TravelReqMasters treqMaster) {
		return treqDAO.findLatestAction(treqMaster);
	}
	
	public ExpenseMasters createNewExpense (Long appointmentId, TravelReqMasters treqMaster, UserSubject subject, UserProfile profile){
		// Create new event
		ExpenseEvents event = new ExpenseEvents();
		event.setAppointmentId(appointmentId.intValue());
		event.setModifiedUserId(profile.getUserId());
		// Create new master
		ExpenseMasters master = new ExpenseMasters ();
		master.setExpevIdentifier(event);
		master.setRevisionNumber(0);
		master.setCurrentInd("Y");
		master.setExpDateFrom(treqMaster.getTreqDateFrom());
		master.setExpDateTo(treqMaster.getTreqDateTo());
		master.setNatureOfBusiness(treqMaster.getNatureOfBusiness());
		master.setComments(treqMaster.getComments());//cn-bug-348
		master.setTravelInd("Y");
		master.setOutOfStateInd(treqMaster.getOutOfStateInd());
		master.setModifiedUserId(profile.getUserId());
		master.setPdfInd("N");
		if (!treqMaster.getTravelReqOutOfStateCollection().isEmpty()){
			List<OutOfStateTravel> oostList = new ArrayList<OutOfStateTravel> ();
			for (TravelReqOutOfState item:treqMaster.getTravelReqOutOfStateCollection()){
				OutOfStateTravel newItem = new OutOfStateTravel();
				newItem.setExpmIdentifier(master);
				newItem.setStacIdentifier(item.getStacIdentifier());
				newItem.setModifiedUserId(profile.getUserId());
				oostList.add(newItem);
			}
			master.setOutOfStateTravelList(oostList);
		}
		List<ExpenseMasters> masterList = new ArrayList<ExpenseMasters>();
		masterList.add(master);
		event.setExpenseMastersCollection(masterList);
		
		event = expenseService.saveExpenseEvent(event);
		treqDAO.getEntityManager().flush();
		// Auto liquidate when an expense is created from travel requisition
		expenseService.checkLiquidations(master, subject);
		return event.getExpenseMastersCollection().get(0);
	}

	public TravelReqEvents getTravelRequisitionRelatedWithExpense (int expenseEventId){
		return  treqDAO.findTravelRequisition(expenseEventId);
	}
	
	public TravelReqMasters getCurrentTravelRequisition (int treqEventId){
		return  treqDAO.findTravelRequisitionCurrent(treqEventId);
	}
	
	public TravelReqEvents getTravelRequisitionForAdvance (int advanceEventId){
		return  treqDAO.findTravelRequisitionForAdvance(advanceEventId);
	}
	
	public boolean isExpenseRelatedWithTravelRequisition (int expenseEventId){
		boolean result = false;
		int count = treqDAO.findTravelRequisitionCount(expenseEventId);
		if (count > 0 )
			result = true;
		return result;
	}
	
	public void emailNotification(TravelReqMasters treqMaster,UserSubject subject, UserProfile profile, String comments) {
		int returnCode = 0;
		String totalRequisitionAmount;
		//get total requisition amount
		if(treqMaster.getTravelReqDetailsCollection().get(0).getDollarAmount()==0)
			totalRequisitionAmount="0";
		else
			totalRequisitionAmount=TimeAndExpenseUtil.displayAmountTwoDigits(treqMaster.getTravelReqDetailsCollection().get(0).getDollarAmount());
		
		String notificationMessage = emailService.getNotificationMessage(
				treqMaster, subject, profile);
		
		if (!StringUtils.isEmpty(notificationMessage)) {

			String addittionalText;
			addittionalText = "\r\r   Details:\rRequest ID: " + treqMaster.getTreqeIdentifier().getTreqeIdentifier().toString() + ".  ";
			addittionalText += "\r   From Date: " + new SimpleDateFormat("MM-dd-yyyy").format(treqMaster.getTreqDateFrom()) + ".  ";
			addittionalText += "\r   To Date: " + new SimpleDateFormat("MM-dd-yyyy").format(treqMaster.getTreqDateTo()) + ".  ";
			addittionalText += "\r   Amount: $" + totalRequisitionAmount + ".  ";
			comments = (comments.trim().length() == 0) ? "--" : comments.trim(); 
			addittionalText += "\r   Comments: " + comments + ".  ";
			addittionalText += "\r";
			//CN BUG 147 Changed from subject.getApptID to subject object
			returnCode = emailService.processEmailNotifications(subject
					, profile.getEmpIdentifier(), profile
					.getUserId(), notificationMessage, Calendar.getInstance()
					.getTime(), addittionalText);
			//CN BUG 147
		}
		if (logger.isInfoEnabled())
			logger.info("Notification return code: " + returnCode);

		if (returnCode == 0) {
			logger.info("Notification return code: " + returnCode);
			returnCode = 10551;
		} else {
			// insert or update notification errors
			processTravelReqNotificationErrors(treqMaster, profile,
					returnCode);
		}

	}
	
	private void processTravelReqNotificationErrors(
			TravelReqMasters treqMaster, UserProfile userProfile,
			int returnCode) {
		
		boolean errorExists = false;
		if (treqMaster.getTreqErrorsCollection() != null){
		Iterator<TravelReqErrors> it = treqMaster.getTreqErrorsCollection().iterator();
		
		while (it.hasNext()) {
			TravelReqErrors error = (TravelReqErrors) it.next();
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
								treqMaster,
								IConstants.NOTIFICATION_NOT_SENT_SINCE_USERID_NOT_DEFINED, userProfile);
			else if (returnCode == Integer
					.parseInt(IConstants.NOTIFICATION_NOT_DUE_TO_SYSTEM_PROBLEMS)) {
				this.addError(treqMaster,
						IConstants.NOTIFICATION_NOT_DUE_TO_SYSTEM_PROBLEMS, userProfile);
			}
		}

	}

	public void addError(TravelReqMasters treqMaster, String treqError, UserProfile userProfile) {
		List<TravelReqErrors> errorsList = treqMaster.getTreqErrorsCollection();

		if (errorsList == null) {
			errorsList = new ArrayList<TravelReqErrors>();
			treqMaster.setTreqErrorsCollection(errorsList);
		}

		ErrorMessages errorMessages = treqDAO.getEntityManager().find(
				ErrorMessages.class, treqError);
		TravelReqErrors treqErrorNew = new TravelReqErrors();
		treqErrorNew.setTreqmIdentifier(treqMaster);
		treqErrorNew.setErrorCode(errorMessages);
		treqErrorNew.setErrorSource(IConstants.TRAVEL_REQUISITION_ERROR_SOURCE);
		if (userProfile != null)
			treqErrorNew.setModifiedUserId(userProfile.getUserId());
		errorsList.add(treqErrorNew);

	}
	
	public TravelReqMasters rejectTravelRequisition(TravelReqMasters treqMaster,
			UserSubject subject, UserProfile profile, String approverComments) {
		TravelReqActions newTreqActions = new TravelReqActions();
		newTreqActions.setActionCode(IConstants.REJECTED);
		newTreqActions.setTreqmIdentifier(treqMaster);
		newTreqActions.setModifiedUserId(profile.getUserId());
		if (!StringUtils.isEmpty(approverComments))
			newTreqActions.setComments(approverComments);

		if (treqMaster.getTravelReqActionsCollection() != null)
			treqMaster.getTravelReqActionsCollection().add(newTreqActions);
		else {
			List<TravelReqActions> newTreqActionsList = new ArrayList<TravelReqActions>();
			newTreqActionsList.add(newTreqActions);
			treqMaster.setTravelReqActionsCollection(newTreqActionsList);
		}

		// update status for advance masters
		treqMaster.setStatus(IConstants.REJECTED);
		treqMaster.setModifiedUserId(profile.getUserId());
		treqMaster = treqDAO.saveTravelRequisition(treqMaster);

		// prepare and process notification
		// this.processNotification(advanceMaster, subject);

		if (logger.isInfoEnabled())
			logger.info("Advance Approved with master id: "
					+ treqMaster.getTreqmIdentifier());

		return treqMaster;
	}
	
	public void updateTravelRequisition (int treqeIdentifier, int adevIdentifier){
		treqDAO.updateTravelRequisition(treqeIdentifier, adevIdentifier);
	}
	
	public void validateOutOfStateTravel(TravelReqMasters treqMaster,UserProfile userProfile) {

		if(logger.isInfoEnabled())
			logger.info("Enter method : validateOutOfStateTravel");
		
		// check for Error AUTHORIZATION_CODES_EMPTY
		if (treqMaster.getOutOfStateInd().trim().equalsIgnoreCase("Y") && treqMaster.getTravelReqOutOfStateCollection().isEmpty()) 
			commonDsp.populateErrors(IConstants.AUTHORIZATION_CODES_EMPTY, IConstants.EXP_ERR_SRC_ID_TAB, treqMaster,userProfile);
		
		if(logger.isInfoEnabled())
			logger.info("Exit method : validateOutOfStateTravel");
	}
	
	public void validateMultipleFacsAgency(TravelReqMasters treqMaster,
			Date expenseStartDate, Date expenseEndDate,
			UserSubject userSubject, UserProfile userProfile) {

		if(logger.isDebugEnabled())
			logger.debug("Enter method : validateMultipleFacsAgency");
		
		List<AppointmentsBean> appointmentsList = null;		 
		appointmentsList = apptDAO.findFacsAgencyByExpenseDatesByEmployee(expenseStartDate, expenseEndDate, userSubject.getAppointmentId());
			
		// if no appointments List found .. ERROR
		if(appointmentsList==null || appointmentsList.isEmpty()){
				commonDsp.populateErrors(IConstants.INVALID_FACS_AGENCY_FOR_EXPENSE_DATES, IConstants.EXP_ERR_SRC_ID_TAB, treqMaster,userProfile);
		}
		else if(!appointmentsList.isEmpty()){	
			if (appointmentsList.size()> 1)  /* Multiple FACS agency .. ERROR */
				commonDsp.populateErrors(IConstants.MULTIPLE_FACS_AGENCY_FOR_EXPENSE_DATES, IConstants.EXP_ERR_SRC_ID_TAB, treqMaster,userProfile);
		}
		if(logger.isDebugEnabled())
			logger.debug("Exit method : validateMultipleFacsAgency");
	}
	
	public void validateExpenseDetailAmount(TravelReqMasters treqMaster, UserProfile userProfile) {
		if(treqMaster == null || treqMaster.getTravelReqDetailsCollection() == null) return;
		
		for(TravelReqDetails detail : treqMaster.getTravelReqDetailsCollection()){
			if(detail.getDollarAmount() == 0){
//				populateErrors(IConstants.TRAVEL_REQUISITION_AMT_ZERO_ERROR,
//						IConstants.EXP_ERR_SRC_ID_TAB, detail.getTreqmIdentifier(), userProfile);
			}
		}
		
	}
	
public void populateErrors(String errorCode,String errorSource, TravelReqMasters treqMaster, UserProfile user){
		
		ErrorMessages errorMessages = commonDao.findErrorCode(errorCode);
		
		/* Populate the Expense error */
		errorMessages.setErrorText(commonDao.findErrorTextByCode(errorCode));

		TravelReqErrors treqErrors = new TravelReqErrors();
		treqErrors.setTreqmIdentifier(treqMaster);
		treqErrors.setErrorCode(errorMessages);
		treqErrors.setErrorSource(errorSource);
	    treqErrors.setModifiedUserId(user.getUserId());
		List<TravelReqErrors> errorsList = treqMaster.getTreqErrorsCollection();
		if(errorsList==null){
			errorsList = new ArrayList<TravelReqErrors>();
			treqMaster.setTreqErrorsCollection(errorsList);
		}
			
		
		errorsList.add(treqErrors); /* Add errors to ErrorsList */
		
		//expenseMasters.getExpenseErrorsCollection().addAll(errorsList); /* update the master with the errorList */
		
		
		return;
		
	}

public TravelReqMasters deleteErrors(TravelReqMasters treqMaster,String errorSource) {
	if (treqMaster.getTreqErrorsCollection() != null && !treqMaster.getTreqErrorsCollection().isEmpty()) {
		treqMaster.getTreqErrorsCollection().size();//added this because of lazy-loading 
	}
	for(Iterator<TravelReqErrors> itr = treqMaster.getTreqErrorsCollection().iterator(); itr.hasNext();){
		TravelReqErrors treqError = itr.next();
		
		if(treqError.getErrorSource() != null && (treqError.getErrorSource().startsWith(errorSource)) ||
				(treqError.getErrorSource().toUpperCase().contains(errorSource))) {
			itr.remove();
			treqDAO.getEntityManager().remove(treqError);
		}	
	}

	//PS[mc]: OpenJPA behaves in a strange way by complaining of duplicates,
	//if new errors are inserted after this method executed without flushing !
	// [mc] Jul 21, 2009: Commenting out as partial data changes 
	// should not be commited to the DB! As for the problem, Actions should delete
	// errors explicitly & flush before normal proceeding.
	//entityManager.flush();
	
	return treqMaster;
}

public TravelReqDetails getTravelRequisitionDetailByTreqDetailsId(int treqDtlsId){
	return treqDAO.findTreqDetailId(treqDtlsId);
}

	
	public List<TravelReqDetailCodingBlock> getCodingBlocksForDisplay (TravelReqDetails treqDetail){
		return treqDAO.findCodingBlocks(treqDetail);
	}
		
/**
 * Creates a new expense associated with the travel requisition
 * @param treqMaster
 * @param subject
 * @param profile
 * @return
 */
	
	public ExpenseMasters createExpenseFromRequisition(long appointmentId, TravelReqMasters treqMaster, UserSubject subject, UserProfile profile){
		ExpenseMasters expenseMaster = createNewExpense(appointmentId, treqMaster, subject, profile);
	
		treqMaster.getTreqeIdentifier().setExpevIdentifier(expenseMaster.getExpevIdentifier().getExpevIdentifier());
		
		TravelReqActions action = new TravelReqActions();
		action.setActionCode(IConstants.PROCESSED);
		action.setTreqmIdentifier(treqMaster);
		action.setModifiedUserId(profile.getUserId()); 
		treqMaster.getTravelReqActionsCollection().add(action);
		treqMaster.setStatus(IConstants.PROCESSED);
		
		treqDAO.saveTravelRequisition(treqMaster.getTreqeIdentifier());
		
		return expenseMaster;
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
	public String getRemainingApprovalPaths(Integer treqmIdentifier, UserSubject UserSubject) {
		return treqDAO.getRemainingApprovalPaths(treqmIdentifier, UserSubject);
	}
/**
 * Requisition already in SUBM or higher status	
 * @param treq
 * @param prevTreqMasters
 * @param userProfile
 * @param userSubject
 * @param approverComments
 * @return
 */
	private TravelReqMasters assignStatusAndMoveToAppropriateQueue(TravelReqMasters treq, TravelReqMasters prevTreqMasters, UserProfile userProfile, UserSubject userSubject, String approverComments){
		TravelReqActions newAction = null;
		
		if(prevTreqMasters == null || IConstants.REJECTED.equals(prevTreqMasters.getStatus())){
			treq.setStatus(IConstants.SUBMIT);
			newAction = new TravelReqActions();
			newAction.setActionCode(IConstants.SUBMIT);
			newAction.setTreqmIdentifier(treq);
			newAction.setComments(approverComments);
			newAction.setModifiedUserId(userProfile.getUserId());
		} else {
			TravelReqActions latestAction = treqDAO.findLatestTreqAction(prevTreqMasters);
			if(latestAction != null){
				// Put TREQ in same status.
				treq.setStatus(prevTreqMasters.getStatus());
				// copy latest action
				newAction = new TravelReqActions(latestAction);
				newAction.setTreqmIdentifier(treq);
				newAction.setModifiedUserId(userProfile.getUserId());
				
				if (approverComments!= null && approverComments.length()>0) newAction.setComments(approverComments);
			}
			}
		if (newAction != null && !newAction.getActionCode().isEmpty()){
			treq.getTravelReqActionsCollection().add(newAction);
		}

		return treq;
	}
	/**
	 * Add detail items to new or existing requisition
	 * @param treqMaster
	 * @param treqDetails
	 */
	private void addTravelReqDetails(TravelReqMasters treqMaster, TravelReqDetails treqDetails) {
		TravelReqDetails details;
		
		if (treqMaster.getTravelReqDetailsCollection() == null){
			List<TravelReqDetails> detailsList = new ArrayList<TravelReqDetails> ();
			details = new TravelReqDetails();
			detailsList.add(details);
			details.setTreqmIdentifier(treqMaster);
			treqMaster.setTravelReqDetailsCollection(detailsList);			
		} else {
			details = treqMaster.getTravelReqDetailsCollection().get(0);
		}
		details.setDollarAmount(treqDetails.getDollarAmount());
		details.setTransportationAmount(treqDetails.getTransportationAmount());
		details.setAirfareAmount(treqDetails.getAirfareAmount());
		details.setLodgingAmount(treqDetails.getLodgingAmount());
		details.setMealsAmount(treqDetails.getMealsAmount());
		details.setOtherAmount(treqDetails.getOtherAmount());
		details.setRegistAmount(treqDetails.getRegistAmount());
	}
	
	public TravelReqMasters getPrevTreqMasterInApprovedStatus(TravelReqEvents treqEvent, int revisionNo){
		return treqDAO.findPrevTreqInStatus(treqEvent, IConstants.APPROVED, revisionNo);
	}
	
	public boolean approvalStepExists(TravelReqEvents treqEvent, int revisionNo){
		List<TravelReqActions> actionsList;
		actionsList = treqDAO.findPrevTreqApprovalAction(treqEvent, revisionNo);
		
		return actionsList.isEmpty() ? false: true;
	}
	/**
	 * Used when creating new revisions, copy coding blocks to the current version
	 * @param treqMaster
	 * @param currentCbList
	 */
	private void copyCodingBlocks (TravelReqMasters treqMaster, List<TravelReqDetailCodingBlock> currentCbList){
		List<TravelReqDetailCodingBlock> newCbList = treqMaster.getTravelReqDetailsCollection().get(0).getTravelReqDetailCodingBlockCollection();
		if (newCbList == null){
			newCbList = new ArrayList<TravelReqDetailCodingBlock> ();
			treqMaster.getTravelReqDetailsCollection().get(0).setTravelReqDetailCodingBlockCollection(newCbList);
		}
		if (currentCbList != null && !currentCbList.isEmpty()){
			for (TravelReqDetailCodingBlock item: currentCbList){
				TravelReqDetailCodingBlock newCodingBlock = new TravelReqDetailCodingBlock(item);
				newCodingBlock.setTreqdIdentifier(treqMaster.getTravelReqDetailsCollection().get(0));
				newCbList.add(newCodingBlock);
			}
		}
	}
	/**
	 * Add standard coding blocks for new requisitions
	 * @param treqDetails
	 */
	private void addStandardCodingBlock (TravelReqDetails treqDetails){
			List<TravelReqDetailCodingBlock> detailsCbList = new ArrayList<TravelReqDetailCodingBlock>();
			TravelReqDetailCodingBlock cbStd = new TravelReqDetailCodingBlock();
			cbStd.setStandardInd("Y");
			cbStd.setPercent(1);
			cbStd.setCodingBlock("");
			cbStd.setDollarAmount(treqDetails.getDollarAmount());
			cbStd.setTreqdIdentifier(treqDetails);
			detailsCbList.add(cbStd);
			treqDetails.setTravelReqDetailCodingBlockCollection(detailsCbList);
	}
	
	public void setTreqDAO(TravelRequisitionDAO treqDAO) {
		this.treqDAO = treqDAO;
	}

	public TravelRequisitionDAO getTreqDAO() {
		return treqDAO;
	}

	public void setAppointmentDAO(AppointmentDAO apptDAO) {
		this.apptDAO = apptDAO;
	}

	public AppointmentDAO getAppointmentDAO() {
		return apptDAO;
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
}
