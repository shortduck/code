package gov.michigan.dit.timeexpense.service;

import gov.michigan.dit.timeexpense.dao.AppointmentDAO;
import gov.michigan.dit.timeexpense.dao.ApproveDAO;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseActionsDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseLineItemDAO;
import gov.michigan.dit.timeexpense.exception.ExpenseException;
import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
import gov.michigan.dit.timeexpense.model.core.CodingBlockElement;
import gov.michigan.dit.timeexpense.model.core.DriverReimbExpTypeCbs;
import gov.michigan.dit.timeexpense.model.core.ExpenseActions;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseHistory;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseProfileRules;
import gov.michigan.dit.timeexpense.model.core.OutOfStateTravel;
import gov.michigan.dit.timeexpense.model.core.StateAuthCodes;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.core.VExpensesList;
import gov.michigan.dit.timeexpense.model.display.AdvanceLiqEditBean;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.ComponentViewState;
import gov.michigan.dit.timeexpense.model.display.ExpenseListBean;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ExpenseDSP {
	ExpenseLineItemDAO expenseLineItemDAO;
	ExpenseDAO expenseDAO;
	AppointmentDAO apptDAO;
	CommonDAO commonDao;
	private ApproveDAO approveDao;
	private ExpenseActionsDAO expenseActionsDao;
	
	CommonDSP commonDsp;
	ExpenseLineItemDSP expenseLineService;
	CodingBlockDSP codingBlockService;
	AdvanceDSP advanceService;
	private SecurityManager securityService;
	private AdvanceLiquidationDSP liquidationService;	
	private EmailNotificationDSP emailService;
	Logger logger = Logger.getLogger(ExpenseDSP.class);

	public ExpenseDSP(EntityManager em) {
		commonDsp = new CommonDSP(em);
		expenseLineService = new ExpenseLineItemDSP(em);
		codingBlockService = new CodingBlockDSP(em);
		advanceService = new AdvanceDSP(em);
		securityService = new SecurityManager(em);
		liquidationService = new AdvanceLiquidationDSP(em);				
		expenseLineItemDAO = new ExpenseLineItemDAO(em);
		expenseDAO = new ExpenseDAO(em);
		apptDAO = new AppointmentDAO(em);
		commonDao = new CommonDAO(em);
		approveDao = new ApproveDAO(em);
		expenseActionsDao = new ExpenseActionsDAO(em);
		emailService = new EmailNotificationDSP(em);
	}

	/**
	 * The following method validates the following :
	 * 		i.  Expense start date is greater than Time and Expense Start date
	 * 		ii. Expense start date is earlier than expense end date
	 * 		iii.Expense start and end dates do not span fiscal years
	 * 		iv. The Out-of-state auth-code is selected for out of state travel
	 * If there are errors, then an ExpenseError object is added to the ExpenseErrors collection 
	 * @param object
	 */
	
	public void validateExpenseID(ExpenseMasters expenseMasters, UserSubject userSubject, UserProfile userProfile) throws Exception{
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : validateExpenseID");
		
		// Delete Expense Errors having Error source starting with 'ID'   
		List<ExpenseErrors> errorsList = expenseMasters.getExpenseErrorsCollection();
		if(errorsList==null)
			errorsList = new ArrayList<ExpenseErrors>();
		else
			expenseMasters = commonDao.deleteExpenseErrors(expenseMasters, IConstants.EXP_ERR_SRC_ID_TAB);
		
		// Invoke validations
		validateDate(expenseMasters, userProfile);
		validateOutOfStateTravel(expenseMasters, userProfile);
		validateMultipleFacsAgency(expenseMasters, expenseMasters.getExpDateFrom(), expenseMasters.getExpDateTo(), userSubject, userProfile);
		validateExpenseToDate(expenseMasters,expenseMasters.getExpDateTo(), userProfile);
		validateExpenseDates(expenseMasters, userSubject.getAppointmentId(), userProfile);	
		if(logger.isDebugEnabled())
			logger.debug("Exit method : validateExpenseID");
	}
	

	//PDF roles are only allowed to create PDF expense, they can't create regular expenses.
	//If user has the following roles ('TRAVL COORD EXP', 'SUPERVIS EXP', 'REGION APPR EXP', 'PERSON APPR EXP','FINAN APPR1 EXP', 'FINAN APPR2 EXP')
	//, allow them to create regular expenses, else fire an error.
	public void validatePDFExpense(ExpenseMasters expenseMaster,
			UserProfile userProfile, UserSubject userSubject, String moduleID) {
		
		if (expenseMaster.getExpenseDetailsCollection() == null  || expenseMaster.getExpenseDetailsCollection().size() < 1 )
			return;
		
		//Allow user to create his expense hence no need to test anything else.		
		if (moduleID.equals(IConstants.EXPENSE_EMPLOYEE))
			return;
		
		for (ExpenseDetails expense : expenseMaster
				.getExpenseDetailsCollection()) {
			if (isUserWithPDFRole(userProfile.getUserId(), userSubject.getDepartment(), userSubject.getAgency() , userSubject.getTku())) {
				if (!IConstants.STRING_PDF_CATEGORY_CODE
						.equalsIgnoreCase(expenseLineItemDAO
								.getExpenseCategory(expense.getExpTypeCode()
										.getExpTypeCode()))
						&& !doesUserHasStatewideRoles(userProfile
								.getUserId(), userSubject.getDepartment(), userSubject.getAgency() , userSubject.getTku(), moduleID)
								
						) {
					commonDsp
							.populateErrors(
									IConstants.USER_CREATE_ONLY_PDF_EXPENSES,
									IConstants.EXP_ERR_SRC_DTL_TAB
											+ IConstants.SINGLE_BLANK
											+ expense.getLineItem(),
									expenseMaster, userProfile);
				}
			}

		}
		
	}

	
	/*
	 * Validates Expense To Date and adds error in the error table if it is greater than the Payroll Processing End Date 
	 */
	private void validateExpenseToDate(ExpenseMasters expenseMaster,Date expDateTo, UserProfile userProfile) {
		Date ppEndDate =  commonDsp.findPpEndDate();
		if(ppEndDate != null && expDateTo.after(ppEndDate)){
			commonDsp.populateErrors(IConstants.EXPENSE_TODATE_GREATER_THAN_CURRENT_PP_ENDDATE, IConstants.EXP_ERR_SRC_ID_TAB, expenseMaster, userProfile);
		}		
	}
    

	//Check if the entered current expense dates are present for the previous expense for travel or non-travel
	//RN, 04/13/2015 -AI_26431 -Bug 361 - Added pdf_ind = 'Y' to prevent warning if a non-travel expense and PDF expense have the same date.
	public void  validateExpenseDates(ExpenseMasters savedExpense,int apptId,UserProfile userProfile){
		int expevIdentifier=0;
		if(savedExpense.getPdfInd().equals("Y")){
			return;
		}
		else{
			if(savedExpense.getExpDateFrom() != null && savedExpense.getExpDateTo() != null && savedExpense.getTravelInd()!=null) {
				if(savedExpense.getExpevIdentifier()!=null)
					expevIdentifier=savedExpense.getExpevIdentifier().getExpevIdentifier();
				else
					expevIdentifier=0;
				boolean dateExists=expenseDAO.checkExpenseDates(savedExpense.getExpDateFrom(), savedExpense.getExpDateTo(),apptId,savedExpense.getTravelInd(),expevIdentifier,savedExpense.getPdfInd());
				if(dateExists){
					commonDsp.populateErrors(IConstants.EXPENSE_DATES_EXIST, 
							IConstants.EXP_ERR_SRC_ID_TAB , savedExpense,userProfile);
				}
			}
		}
	}
    
	/***
	 * This method performs the Expense dates validation
	 * @param expenseMasters
	 * @throws Exception
	 */
	public void validateDate(ExpenseMasters expenseMasters,UserProfile userProfile) throws Exception{
		if(logger.isDebugEnabled())
			logger.debug("Enter method : validateDate");
		
		// invoke methods to validate Expense dates.
		
		boolean isStartDateEarlierThanTEStartDate = isStartDateEarlierThanTEStartDate(expenseMasters.getExpDateFrom());
		boolean isStartDateGreaterThanEndDate = isStartDateGreaterThanEndDate(expenseMasters.getExpDateFrom(),expenseMasters.getExpDateTo());
		boolean isExpenseDateSpanFiscalYears = isExpenseDateSpanFiscalYears(expenseMasters.getExpDateFrom(),expenseMasters.getExpDateTo());
		
		//Check if the expense FROM date exceeds the System permissible limit
		boolean isExpenseFromDateGreaterThanAllowedDate = isExpenseFromDateGreaterThanAllowedDate(expenseMasters.getExpDateFrom());
		
		// populate Expense Errors
		if(isStartDateEarlierThanTEStartDate)
			commonDsp.populateErrors(IConstants.EXPENSE_DATE_EARLIER_THAN_TIMEEXPENSE_APPLICATION_START_DATE,IConstants.EXP_ERR_SRC_ID_TAB, expenseMasters, userProfile);

		if(isStartDateGreaterThanEndDate)
			commonDsp.populateErrors(IConstants.EXPENSE_TO_DATE_EARLIER_THAN_EXPENSE_FROM_DATE,IConstants.EXP_ERR_SRC_ID_TAB, expenseMasters,userProfile);
		
		if(isExpenseDateSpanFiscalYears)
			commonDsp.populateErrors(IConstants.EXPENSE_DATES_SPAN_FISCAL_YEAR,IConstants.EXP_ERR_SRC_ID_TAB, expenseMasters, userProfile);
		
		//Check if the expense FROM date exceeds the System permissible limit
		if(isExpenseFromDateGreaterThanAllowedDate)
			commonDsp.populateErrors(IConstants.EXPENSE_FROM_DATE_AFTER_SYSTEM_PERMISSION,IConstants.EXP_ERR_SRC_ID_TAB, expenseMasters, userProfile);

		if(logger.isDebugEnabled())
			logger.debug("Exit method : validateDate");
	}
	
	/***
	 * This method validates whether the start date is earlier than Time and Expense Application start date
	 * @param expenseStartDate
	 * @return boolean
	 */
	
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
	
	//Check if the expense FROM date exceeds the System permissible limit
	private boolean isExpenseFromDateGreaterThanAllowedDate(Date expenseFromDate) {

		boolean isInValid = false;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		// Get the Time and Expense end date
		Date te_endDate = null;
		try {
			te_endDate = df
					.parse(commonDsp
							.getSystemCodeValue(IConstants.EXPENSE_END_DATE_SYSTEM_CODE));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (logger.isInfoEnabled())
			logger.info("Validating : if Expense from date is greater than Time&Expense End date.");

		if (expenseFromDate.after(te_endDate))
			isInValid = true;

		return isInValid;
	}
	
	/***
	 * This method validates whether the start date is earlier than end date
	 * @param expenseStartDate
	 * @param expenseEndDate
	 * @return boolean isValid
	 */
	
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
	
	
	/***
	 * This method validates whether the expense dates spans Fiscal years
	 * @param expenseStartDate
	 * @param expenseEndDate
	 * @return
	 * @throws Exception
	 */
	
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

	/**
	 * This method validates whether authorization code is selected if Out-of-state travel is TRUE
	 * If there are no authorization codes for out-of-state travel , add errors to the ExpenseErrors collection
	 * @param expenseMaster
	 */
	public void validateOutOfStateTravel(ExpenseMasters expenseMasters,UserProfile userProfile) {

		if(logger.isInfoEnabled())
			logger.info("Enter method : validateOutOfStateTravel");
		
		// check for Error AUTHORIZATION_CODES_EMPTY
		if (expenseMasters.getOutOfStateInd().trim().equalsIgnoreCase("Y") && expenseMasters.getOutOfStateTravelList().isEmpty()) 
			commonDsp.populateErrors(IConstants.AUTHORIZATION_CODES_EMPTY, IConstants.EXP_ERR_SRC_ID_TAB, expenseMasters,userProfile);
		
		if(logger.isInfoEnabled())
			logger.info("Exit method : validateOutOfStateTravel");
	}
	
	/**
	 * This method validates if the FACS agency associated with the employee's appointment changed within the expense date range.
	 * If there are multiple FACS agency associated, then add error to the ExpenseErrors collection. 
	 * @param ExpenseMasters
	 * @param expenseStartDate
	 * @param expenseEndDate
	 * @param userSubject
	 */
	
	public void validateMultipleFacsAgency(ExpenseMasters expenseMasters,
			Date expenseStartDate, Date expenseEndDate,
			UserSubject userSubject, UserProfile userProfile) {

		if(logger.isDebugEnabled())
			logger.debug("Enter method : validateMultipleFacsAgency");
		
		List<AppointmentsBean> appointmentsList = null;		 
		appointmentsList = apptDAO.findFacsAgencyByExpenseDatesByEmployee(expenseStartDate, expenseEndDate, userSubject.getAppointmentId());
			
		// if no appointments List found .. ERROR
		if(appointmentsList==null || appointmentsList.isEmpty()){
				commonDsp.populateErrors(IConstants.INVALID_FACS_AGENCY_FOR_EXPENSE_DATES, IConstants.EXP_ERR_SRC_ID_TAB, expenseMasters,userProfile);
		}
		else if(!appointmentsList.isEmpty()){	
			if (appointmentsList.size()> 1)  /* Multiple FACS agency .. ERROR */
				commonDsp.populateErrors(IConstants.MULTIPLE_FACS_AGENCY_FOR_EXPENSE_DATES, IConstants.EXP_ERR_SRC_ID_TAB, expenseMasters,userProfile);
		}
		if(logger.isDebugEnabled())
			logger.debug("Exit method : validateMultipleFacsAgency");
	}
	
	/***
	 * This method interacts with the Expense DAO to retrieve the ExpenseMaster entity given the expenseMasterId
	 * @param expenseMasterId
	 * @param revisionNo
	 * @return ExpenseMaster entity
	 */
	public ExpenseMasters getExpense(int expenseMasterId) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpense");
		// find Expense 
		ExpenseMasters expenseMasters = expenseDAO.findExpenseByExpenseMasterId(expenseMasterId);
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpense");

		return expenseMasters;
	}

	/***
	 * This method saves Expense record. It checks whether the expense is existing or not and calls the corresponding method
	 * 
	 * @param expenseMasters
	 * @param apptIdentifier
	 * @param moduleId
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	
	public ExpenseMasters saveExpense(ExpenseMasters expenseMasters,
			UserSubject subject, String moduleId, UserProfile userProfile) throws Exception{

		ExpenseMasters savedExpense = null;
		
		//If the expev_identifier does not exist, create a new ExpenseMaster
		if (expenseMasters.getExpevIdentifier()== null) {
			savedExpense = this.saveNewExpense(expenseMasters, subject.getAppointmentId(), moduleId, userProfile);
			// check for the saved liquidations and expense is not PDF and non Travel
			if (!expenseMasters.isPdfInd() && "Y".equals(expenseMasters.getTravelInd())){
				checkLiquidations(expenseMasters, subject);
			}
		} else 		//If the expev_identifier exist, create a new version with the modifications
			// update appt info in expense event
			if (!IConstants.PROCESSED.equals(expenseMasters.getStatus())) {
				expenseMasters.getExpevIdentifier().setAppointmentId(subject.getAppointmentId());
			}
			savedExpense = this.saveExpenseExisting(expenseMasters, userProfile);
			
		return savedExpense;

	}
	/**
	 * 
	 * @param expenseMaster
	 * @param userProfile
	 */
	public void checkLiquidations(ExpenseMasters expenseMaster,UserSubject subject)
	{
		List<AdvanceMasters> advances = advanceService.getAdvanceListByEmpId( subject.getEmployeeId(), expenseMaster);	
		double advanceCalc=0.0;
		for(AdvanceMasters advance: advances){
			// if liquidation is null check for advance dates
			advanceCalc = advanceService.getNonPermAdvance(expenseMaster.getExpDateFrom(), expenseMaster.getExpDateTo(), advance.getAdvmIdentifier());
			if (advanceCalc >0.0)
			{   
				boolean saveYN = liquidationService.checkSaveLiquidation(advance, expenseMaster.getExpmIdentifier(),advanceCalc);
				
				if(!saveYN)
				{
				liquidationService.saveApporovedAdvLiquid(advance, expenseMaster.getExpmIdentifier(),advanceCalc);
				}
				
			}
		}
	}
	
	/**
	 * This method saves an existing expense. If the expense was never submitted, the same revision is updated with
	 * the new changes. But if it were submitted, a new revision is created. If the modification is an adjustment 
	 * i.e. if the expense in in processed status, then a new adjustment indicator is inserted to reflect the 
	 * adjustment.
	 * 
	 * @param original expense with new data
	 * @return the saved expense
	 */
	public ExpenseMasters saveExpenseExisting(ExpenseMasters currExpenseMaster, UserProfile userProfile) throws Exception{
		ExpenseMasters savedExpense = null;

		// if never submitted
		if (currExpenseMaster.getStatus() == null || currExpenseMaster.getStatus().equals("")) {
			// save the existing expense .. the revision number and the adjustment identifier remains same
			currExpenseMaster.setModifiedUserId(userProfile.getUserId());
			savedExpense = expenseDAO.saveExpense(currExpenseMaster);
		}
		// if submitted or higher
		else{
			// Copying data to the new ExpenseMaster version from current ExpenseMaster
			ExpenseMasters expenseMasters = prepareExpenseEntityFromCurrent(currExpenseMaster);
			
			// set old expense as non current.
			currExpenseMaster.setCurrentInd("N");
			
			expenseMasters.setStatus("");
			expenseMasters.setRevisionNumber(expenseMasters.getRevisionNumber()+1);
			expenseMasters.setModifiedUserId(userProfile.getUserId());
			
			// update adjustmentId if processed expense
			if(IConstants.PROCESSED.equals(currExpenseMaster.getStatus())){
				expenseMasters.setAdjIdentifier(commonDao.findAdjustmentIdentifier()); /* get the adjIdentifier from the DB sequence */
				//always reset received reviewed indicator while creating an adjustment
				expenseMasters.setSuperReviewedReceiptsInd("N");
			}
			
			savedExpense = expenseDAO.saveExpense(expenseMasters);
			
			//Copy Audit action if the Old Expense is in Approved State(APPR) and Old expense has been audited before.			
			if (IConstants.APPROVED.equals(currExpenseMaster.getStatus())
					&& expenseDAO.isExpenseMarkedAuditComplete(currExpenseMaster)) {
				
				ExpenseActions auditActions = null;
				List<ExpenseActions> expenseActions  = expenseActionsDao.findExpenseActions(currExpenseMaster);
				
				for(ExpenseActions expenseAction : expenseActions){
					if( IConstants.AUDIT.equals(expenseAction.getActionCode())){
						auditActions = new ExpenseActions(expenseAction);
						auditActions.setExpmIdentifier(expenseMasters);
						
						//Now save the expense action
						expenseDAO.saveExpenseAction(auditActions);
						
						break;
					}	
				}
			}
		}

		return savedExpense;
	}

	
	/**
	 * This method saves a new Expense record
	 * @param expenseMasters
	 * @param apptIdentifier
	 * @param moduleId
	 * @param userProfile
	 */
	
	public ExpenseMasters saveNewExpense(ExpenseMasters expenseMasters,
			int apptIdentifier, String moduleId, UserProfile userProfile){
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : saveNewExpense");
		
		// Create new instance of Expense Event
		ExpenseEvents newExpenseEvent = new ExpenseEvents();
		// Get the Appointment
		//Appointments appointment = expenseDAO.getEntityManager().find(Appointments.class, apptIdentifier);
		
		newExpenseEvent.setAppointmentId(apptIdentifier); // Attach appointment to the Expense Event
		newExpenseEvent.setModifiedUserId(userProfile.getUserId());
		expenseMasters.setExpevIdentifier(newExpenseEvent); // Attach Expense Event to the Expense Master
		
		expenseMasters.setCurrentInd("Y"); // Set current indicator to "Y"
		expenseMasters.setRevisionNumber(0); // First revision
		expenseMasters.setModifiedUserId(userProfile.getUserId()); // Set the userId
		
		List<ExpenseMasters> expenseMasterList = new ArrayList<ExpenseMasters>();
		expenseMasterList.add(expenseMasters);
		
		// Set Expense Masters collection
		newExpenseEvent.setExpenseMastersCollection(expenseMasterList);
		
		//Call Save
		expenseDAO.saveExpense(newExpenseEvent);
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : saveNewExpense");
		
		return expenseMasters;
	}

	/****
	 * This method retrieves the history information of the Expense Record and stores it in the bean.
	 * @param expenseEventId
	 * @return List of ExpenseHistoryBean
	 */

	public List<ExpenseHistory> getExpenseHistory(int expenseEventId) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpenseHistory");
		
		List<ExpenseHistory> expenseHistoryList = new ArrayList<ExpenseHistory>();
		List<ExpenseHistory> expenseHistoryListNew = new ArrayList<ExpenseHistory>();		
		expenseHistoryList = expenseDAO.findExpenseHistory(expenseEventId);
		for (Iterator iterator = expenseHistoryList.iterator(); iterator.hasNext();) {
			ExpenseHistory item = (ExpenseHistory) iterator.next();
			item.setModifiedDateDisplay(TimeAndExpenseUtil.displayDateTime(item.getModifiedDate()));
			expenseHistoryListNew.add(item);
		}		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpenseHistory");
		
		return expenseHistoryListNew;
	}
	
	/**
	 * This method is used for submitting Expense.
	 * It invokes all validations on all the tabs and submits the expense if there are no errors with Severity "E".
	 * Adds an entry to the EXPENSE_ACTIONS table. 
	 * @param expenseMaster
	 * @param moduleId
	 * @param userSubject
	 * @param advanceLiquidationList
	 * @return submitted ExpenseMasters
	 * @throws Exception
	 */

	public ExpenseMasters submitExpense(ExpenseMasters expenseMaster, UserProfile userProfile,UserSubject userSubject,List<AdvanceLiquidations> advanceLiquidationList,String approverComments, List<DriverReimbExpTypeCbs> driverReibmCbs, String moduleID) throws Exception {
		boolean fatalErrorExists = false;

		// Perform all validations on all tabs
		validateExpenseID(expenseMaster, userSubject,userProfile);
		expenseLineService.validateExpenseLineItem(expenseMaster, userProfile, userSubject, moduleID );
		
		// For coding block validation
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");  
		String appropriationYear = String.valueOf(simpleDateformat.format(expenseMaster.getExpDateTo()));

		CodingBlockElement cbElement = new CodingBlockElement(userSubject
				.getAgency(), appropriationYear, "", userSubject
				.getDepartment(), Calendar.getInstance().getTime(), "A",
				userSubject.getTku());
		// Call CB validation
		if (expenseMaster.isPdfInd()) {
			// will not go through f_cb_validate
			codingBlockService.validatePdf(expenseMaster, userProfile);
		} else {
			// non-PDF Coding Blocks validation
			codingBlockService.validateExpenseCodingBlocks(expenseMaster,
					cbElement, expenseMaster.getExpenseDetailsCollection(),
					userProfile, userSubject, driverReibmCbs);
		}
		
		//String invalidCodingBlockAllowedInd = commonDao.findAgencyOptions(cbElement.getDeptCode(), cbElement.getAgency()).getAllowInvalidCbElementsInd();
		
		// Call Liquidations validation
		//advanceService.validateLiquidations(advanceLiquidationList, expenseMaster);
		
		// Check whether Errors of Severity "ERROR" exists 
		if(expenseMaster.getExpenseErrorsCollection()!=null) {
			if(expenseMaster.getExpenseErrorsCollection().size()>0) {
				for(ExpenseErrors errors:expenseMaster.getExpenseErrorsCollection()) {
					if(errors.getErrorCode().getIcon()==2) { // Icon value 2 indicates an ERROR
						fatalErrorExists=true;
						break;
					}
				}
			}
			
		
			if (fatalErrorExists) {
				boolean canSubmit = false;
				boolean cbErrors = commonDsp.allCodingBlockErrorsForExpense(userSubject, expenseMaster.getExpenseErrorsCollection());
				String allowInvalidCbElementsInd = "N";
				if (cbErrors) {
					AgencyOptions agencyOptions = commonDao.findAgencyOptions(userSubject.getDepartment(), userSubject.getAgency());					
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
		}
		
		// assign approval category if an adjustment
		if (expenseMaster.getAdjIdentifier() != null && expenseMaster.getAdjIdentifier() > 0){
			String adjType = expenseDAO.findApprovalAdjustmentCategory(expenseMaster.getExpmIdentifier());
			if (adjType != null){
				expenseMaster.setAdjTypeApproval(adjType);
				expenseMaster = expenseDAO.saveExpense(expenseMaster);
			}
			
		}

		ExpenseActions newAction = assignExpenseStatusAndMoveToAppropriateQueue(expenseMaster, userProfile, userSubject, approverComments);
				
		// Invoke the DAO method 
		ExpenseMasters submittedExpense = expenseDAO.saveExpense(expenseMaster);

		return submittedExpense;
	}
	
	/**
	 * emailNotification will sent out email about the action taken. 
	 * This routine will be called only if earlier action is successful.
	 */
	public void emailNotification(ExpenseMasters expenseMasters, UserSubject subject, UserProfile profile, String comments)
	{
		int returnCode = 0;
		String totalExpenseAmount="";
		
		if(expenseMasters.getAmount()==0)
			totalExpenseAmount="0";
		else
			totalExpenseAmount=TimeAndExpenseUtil.displayAmountTwoDigits(expenseMasters.getAmount());
		
			String notificationMessage = emailService.getNotificationMessage(expenseMasters, subject, profile);
			if (!StringUtils.isEmpty(notificationMessage))
			{
				String addittionalText;
				addittionalText = "\t  \t\rDetails:\t  \t\r\rRequest ID: " + expenseMasters.getExpevIdentifier().getExpevIdentifier().toString()  + ".\r\r";
				addittionalText += "\t  \t\rFrom Date: " + new SimpleDateFormat("MM-dd-yyyy").format(expenseMasters.getExpDateFrom())  + ".\r\r";
				addittionalText += "\t  \t\rTo Date: " + new SimpleDateFormat("MM-dd-yyyy").format(expenseMasters.getExpDateTo())  + ".\r\r";
				addittionalText += "\t  \t\rAmount: $" + totalExpenseAmount + ".\r\r";
				comments = (comments.trim().length() == 0) ? "--" : comments.trim();
				addittionalText += "\t  \t\rComments: " + comments + ".\r\r";
				addittionalText += "\t  \t";


				returnCode = emailService.processEmailNotifications(subject, profile.getEmpIdentifier(), profile.getUserId(), notificationMessage, Calendar.getInstance().getTime(), addittionalText );
			}
			if (logger.isInfoEnabled())
				logger.info("Notification return code: " + returnCode);

			if (returnCode == 0)
			{
				logger.info("Notification return code: " + returnCode);
				//returnCode = 10551;
			} else { 
				// insert or update notification errors
				processExpenseNotificationErrors(expenseMasters, profile, returnCode);
		}
	}

	
	
private ExpenseActions assignExpenseStatusAndMoveToAppropriateQueue(ExpenseMasters expense, UserProfile userProfile, UserSubject userSubject, String approverComments){
		
		ExpenseMasters prevExpense = null;
		boolean prevExpenseAutoApproved = false;
		if(expense.getRevisionNumber()>0){
			prevExpense = expenseDAO.findExpenseByExpenseEventId(
										expense.getExpevIdentifier().getExpevIdentifier(), 
										expense.getRevisionNumber()-1);
			if (prevExpense != null){
				prevExpenseAutoApproved = expenseDAO.expenseAutoApprovalActionExists(prevExpense);
			}
		}
		
		ExpenseActions newAction = null;
		boolean managerApprovalExists = commonDao.findManagerApprovalStep(userSubject.getDepartment(), userSubject.getAgency(), userSubject.getTku(), IConstants.EXPENSE_REIMBURSEMENT_APPROVAL_CATEGORY);
		boolean autoApproveExpense = false;

		// If new, rejected or processed start by submitting and adding 
		if(prevExpense == null || IConstants.REJECTED.equals(prevExpense.getStatus()) 
				|| IConstants.PROCESSED.equals(prevExpense.getStatus())){			
			int autoApprove = expenseDAO.findAutoApprovalStatus(userSubject.getDepartment(), userSubject.getAgency(), userSubject.getTku(), expense.getExpmIdentifier(), expense.getExpevIdentifier().getExpevIdentifier());
			if (autoApprove == 0 && !managerApprovalExists && expense.getAdjIdentifier() == null){
				//auto approve scenario ... requires 2 actions
				expense.setStatus("APPR");
				newAction = new ExpenseActions();
				newAction.setActionCode(IConstants.SUBMIT);
				newAction.setExpmIdentifier(expense);
				newAction.setComments("AUTO");
				newAction.setModifiedUserId(userProfile.getUserId());
				autoApproveExpense = true;
				//update advance outstanding amount
				liquidationService.effectLiquidationsUponFinalExpenseApproval(expense, false);
			} else{
				// no auto approval, follow approval path
				newAction = addNewSubmitAction (expense, newAction, userProfile);
			}
		}
		else if (prevExpenseAutoApproved && !managerApprovalExists){
			// previously auto approved but no manager action exists. put back in approval queue
			newAction = addNewSubmitAction (expense, newAction, userProfile);
		}
		// if SUBM or APPR
		else{
			// Put expense in same status.
			// PS: If the expense was previously in APPR status, then only the final approver can modify it.
			// Therefore, when the final approver modifies and submits, then we leave the expense in APPR status
			// This is equivalent to doing: 1) Submitting expense 2) Automatic final approval by approver.
			expense.setStatus(prevExpense.getStatus());

			// If the expense was in APPR status and then modified by the final approver to be put into APPR again
			// then accomodate liquidations changes accordingly.
			if(IConstants.APPROVED.equals(prevExpense.getStatus())){
				liquidationService.effectLiquidationsUponFinalExpenseApproval(expense, true);
			}
			
			ExpenseActions latestAction = expenseActionsDao.findLatestExpenseActionExcludingActions(prevExpense);
			if(latestAction != null){
				newAction = new ExpenseActions(latestAction);
				newAction.setExpmIdentifier(expense);
				newAction.setModifiedUserId(userProfile.getUserId());
				
				if (approverComments!= null && approverComments.length()>0) newAction.setComments(approverComments);
			}
		}
		
		if(newAction != null) {
			newAction = expenseDAO.saveExpenseAction(newAction);
			if (approverComments!= null && approverComments.length()>0) {
				newAction.setComments(approverComments);
				newAction = expenseDAO.saveExpenseAction(newAction);
			}
			if (IConstants.APPROVED.equals(expense.getStatus())){
				if (newAction.getNextActionCode() != null){
					// expense was likely auto-approved before
					// next action code is not null. This will cause expense to show in the approval queue
					// if next action code is not null
					newAction.setNextActionCode(null);
				}
			}
		}
		
		// saved last, in case there is a SUBM action as well
		if (autoApproveExpense){
			saveNewAutoApproveExpenseAction(expense);
		}
				
		return newAction;
	}

	/**
	 * This method deletes the Expense Master entity from the underlying database.
	 * @param expenseMasterId
	 * @return boolean 
	 */

	public boolean deleteExpense(int expenseEventId) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : deleteExpense");
		
		boolean isDeleted = false;
		if (expenseEventId > 0) 
			isDeleted = expenseDAO.deleteExpenseEvent(expenseEventId);
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : deleteExpense");
		
		return isDeleted;
	}

	public List<StateAuthCodes> getAuthorizationCodes() {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getAuthorizationCodes");

		List<StateAuthCodes> authCodeList = expenseDAO.findAuthorizationCodes();

		if(logger.isDebugEnabled())
			logger.debug("Exit method : getAuthorizationCodes");
		
		return authCodeList;
	}
	
	/**
	 * This method approves the Expense record. 
	 * @param expenseMaster
	 * @return ExpenseMasters
	 */

	public ExpenseMasters approveExpense(ExpenseMasters expenseMaster,UserProfile userProfile, UserSubject subject, String approverComments) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method : approveExpense");
		}

		List<ExpenseActions> expenseActionList = expenseDAO.approveExpense(expenseMaster);
		
		ExpenseActions newExpenseActions = new ExpenseActions();
		if(expenseActionList!=null && !expenseActionList.isEmpty()){
				newExpenseActions.setActionCode(expenseActionList.get(0).getNextActionCode());
		}//else{
			//newExpenseActions.setActionCode(IConstants.APPROVED);
		//}
		newExpenseActions.setExpmIdentifier(expenseMaster);
		newExpenseActions.setModifiedUserId(userProfile.getUserId());
		
		//If no comments pass an empty string to this variable
		if (StringUtils.isEmpty(approverComments)){
			newExpenseActions.setComments("");
		}else{
			newExpenseActions.setComments(approverComments);
		}
		newExpenseActions = expenseDAO.saveExpenseAction(newExpenseActions);
		
		ExpenseMasters savedExpense = expenseDAO.saveExpense(expenseMaster);

		ExpenseMasters updatedExpenseMaster = expenseDAO.updateExpenseStatus(savedExpense); 
		
		ExpenseMasters updatedExpense = null;
		if(updatedExpenseMaster.getStatus().equals(IConstants.APPROVED)){
			updatedExpenseMaster.setModifiedUserId(userProfile.getUserId());
			updatedExpense = expenseDAO.saveExpense(updatedExpenseMaster); /* Invoke DAO method to save the Entity */
			
			liquidationService.effectLiquidationsUponFinalExpenseApproval(updatedExpense, false);
		} else if (newExpenseActions.getActionCode().equals(IConstants.APPROVAL_STEP1)
				&& expenseMaster.getAdjIdentifier() == null){
			int autoApprove = expenseDAO.findAutoApprovalStatus(subject.getDepartment(), subject.getAgency(), subject.getTku(), updatedExpenseMaster.getExpmIdentifier(), updatedExpenseMaster.getExpevIdentifier().getExpevIdentifier());
			if (autoApprove == 0){
				//auto approve scenario ... requires 2 actions
				updatedExpenseMaster.setStatus("APPR");
				saveNewAutoApproveExpenseAction(updatedExpenseMaster);
				updatedExpense = expenseDAO.saveExpense(updatedExpenseMaster);
				// reset next action code for manager approval if exists
				newExpenseActions.setNextActionCode(null);
				//update advance outstanding amount
				liquidationService.effectLiquidationsUponFinalExpenseApproval(updatedExpenseMaster, false);
			}
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method : approveExpense");
		}

		return updatedExpense;
	}

	/**
	 * This method sets the STATUS of the Expense Entity to RJCT,sets the current indicator to "N" and the current indicator of the previous expense record to "Y" (if not also "RJCT")
	 * and calls the DAO method to persist the entity. 
	 * Also adds a ExpenseActions instance.
	 * @param expenseMaster
	 * @param moduleId
	 * @param approverComments 
	 * @return
	 */
	
	public ExpenseEvents rejectExpense(ExpenseMasters expenseMaster, UserProfile userProfile,String moduleId, String approverComments) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : rejectExpense");

		ExpenseActions newExpenseActions = new ExpenseActions();
		newExpenseActions.setActionCode(IConstants.REJECTED);
		newExpenseActions.setExpmIdentifier(expenseMaster);
		newExpenseActions.setModifiedUserId(userProfile.getUserId());
		// If no comments pass an empty string to this variable
		if (StringUtils.isEmpty(approverComments)){
			newExpenseActions.setComments("");
		}else{
			newExpenseActions.setComments(approverComments);
		}
		newExpenseActions.setComments(approverComments);
		expenseDAO.saveExpenseAction(newExpenseActions);
		
		//int prevRevisionNo = 0;
		// Get Expense Event Entity from the Expense Master Id
		int expenseEventId = expenseMaster.getExpevIdentifier().getExpevIdentifier();
		ExpenseEvents expenseEvents = expenseDAO.findExpenseByExpenseEventId(expenseEventId);
		
		// Get the revision number of the passed Expense Masters
/*		int currRevisionNo = expenseMaster.getRevisionNumber();
		// loop through the Master collection to get the previous revision number
		for(ExpenseMasters expMaster : expenseEvents.getExpenseMastersCollection()){
			if(expMaster.getRevisionNumber()<currRevisionNo){
				prevRevisionNo = expMaster.getRevisionNumber();
			}
		}
		// loop through the Masters collection  to set that previous revision's CURRENT INDICATOR to Y
		for(ExpenseMasters expenseMasters : expenseEvents.getExpenseMastersCollection()){
				if(expenseMasters.getRevisionNumber()==prevRevisionNo){
					if(!expenseMasters.getStatus().equals(IConstants.REJECTED)){
						expenseMasters.setCurrentInd("Y");
					}
				}
		}*/
		// Set the status of the passed-in Expense Master to REJECTED and CURRENT INDICATOR TO N
		expenseMaster.setStatus(IConstants.REJECTED);
/*		if(expenseEvents.getExpenseMastersCollection().size() != 1){
			expenseMaster.setCurrentInd("N");
		}*/
		//Save ExpenseMaster
		ExpenseEvents savedExpenseEvents = expenseDAO.saveExpense(expenseEvents);
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : rejectExpense");
		
		return savedExpenseEvents;
	}

	
	/**
	 * This method finds the maxRevision number of the expense record given the expense event Id.
	 * @param expenseEventId
	 * @return maxRevisionNo 
	 */
	public int getMaxRevisionNo(int expenseEventId){
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getMaxRevisionNo");
		int maxRevisionNo = 0;
		
		maxRevisionNo = expenseDAO.findMaxRevisionNo(expenseEventId);
				
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getMaxRevisionNo");
		 
		return maxRevisionNo;
	}
	
	/***
	 * This method is used to get all the expenses of an employee given the employee Id
	 * @param empId
	 * @param expIncludeAdjustment
	 * @return VExpensesList - List of expenses
	 * 
	 */

	public List<VExpensesList> getExpensesListEmployee(int empId,
			String expIncludeAdjustment) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpensesListEmployee");
		
		List<VExpensesList> expenseListBean = null;
		if (expIncludeAdjustment == null || empId < 0) {
			return null;
		}
		// Corresponding DAO methods are invoked depending on the expIncludedAdjustment parameter value
		if (expIncludeAdjustment.equalsIgnoreCase(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY))  /* ADJUSTED Expenses */
			expenseListBean = expenseDAO.findAdjustedExpensesByEmployeeId(empId);
		else if (expIncludeAdjustment.equalsIgnoreCase(IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY)) /* NON_ADJUSTED Expenses */
			expenseListBean = expenseDAO.findNonAdjustedExpensesByEmployeeId(empId);
		else if(expIncludeAdjustment.equalsIgnoreCase(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED)) /* BOTH Expenses */
			expenseListBean = expenseDAO.findExpensesByEmployeeId(empId);

		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpensesListEmployee");
		
		return expenseListBean;
	}
	/********
	 * This method is used to find if the expense has got 'AUDT' action code.
	 */
	public int getActionCode(int expenseMasterId, String actionCode){
		int countVal=expenseDAO.findActionCode(expenseMasterId, actionCode);
		return countVal;
	}
	
	/***
	 * This method is used to get all the expenses for an employee’s appointment.
	 * @param appointmentId
	 * @param expAdjustmentIdentifier
	 * @param userId
	 * @param moduleId
	 * @return VExpensesList - List of expenses 
	 */
	public List<ExpenseListBean> getExpensesListAppointment(int appointmentId,
			String expAdjustmentIdentifier, String userId, String moduleId
			, String department, String agency, String tku
			) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpensesListAppointment");
		
		List<ExpenseListBean> expenseListBean = new ArrayList<ExpenseListBean>();
		if (appointmentId < 0 || expAdjustmentIdentifier == null
				|| userId == null || moduleId == null) {
			return null;
		}
		
		//check if the user has only PDFs roles.
		//in this case we ill be showing expenses of type PDF.
		boolean doesUserHasPDFRole = isUserWithPDFRole(userId, department, agency, tku);
		boolean doesUserHasStatewideRole = doesUserHasStatewideRoles(userId, department, agency, tku, moduleId);
		boolean showPDFExpensesOnly = (doesUserHasPDFRole && !doesUserHasStatewideRole) ? true : false;
				
		// Depending on the Adjustment identifier different DAO methods are called.
		if (expAdjustmentIdentifier.equalsIgnoreCase(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY)) 
			expenseListBean = expenseDAO.findAdjustedExpensesByAppointmentId(appointmentId,userId, moduleId, showPDFExpensesOnly );
		else if (expAdjustmentIdentifier.equalsIgnoreCase(IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY)) 
			expenseListBean = expenseDAO.findNonAdjustedExpensesByAppointmentId(appointmentId,userId, moduleId, showPDFExpensesOnly );
		else if(expAdjustmentIdentifier.equalsIgnoreCase(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED))
			expenseListBean = expenseDAO.findExpensesByAppointmentId(appointmentId, userId, moduleId, showPDFExpensesOnly );
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpensesListAppointment");
		
		return expenseListBean;
	}


	

	/***
	 * This method copies the attributes of the passed-in currExpenseMaster to the new instance
	 * @param currExpenseMaster
	 * @return ExpenseMasters
	 */
	
	private ExpenseMasters prepareExpenseEntityFromCurrent(ExpenseMasters currExpenseMaster){
		// deep copy expense master
		ExpenseMasters newExpensemaster = new ExpenseMasters(currExpenseMaster);
		newExpensemaster.setExpevIdentifier(currExpenseMaster.getExpevIdentifier());

		//[mc]: Deep copy of liquidations also required here !!
		// But we cannot do it here as liquidation doesn't have expense but only expenseId. And Id is unavailable
		// until the new expense is flushed. So liquidations carry over part should be done in action layer after
		// saving of new expense is complete.
		// Similarly, the new expense needs to be put into the same action queue as the old one, if in SUBM or APPR status.
		// So this too shud be done in action layer.
		
		return newExpensemaster;		
	}
	
	public List<AppointmentsBean> getActiveAppointments(Date expenseStartDate, Date expenseEndDate, 
			String userId, String moduleId,long empId) {
		
		if (moduleId.equalsIgnoreCase("EXPW001")) {
			return apptDAO.findActiveAppointmentsByExpDatesEmployee(expenseStartDate, expenseEndDate,moduleId,empId,userId);

		}else if (moduleId.equalsIgnoreCase("EXPW002")	|| moduleId.equalsIgnoreCase("EXPW003")
				|| moduleId.equalsIgnoreCase("APRW003")	|| moduleId.equalsIgnoreCase("APRW004")) {
			return apptDAO.findActiveAppointmentByExpenseDatesManagerStatewide(expenseStartDate, expenseEndDate,moduleId,empId,userId);
		}
		return null;
		
	}
	
	public ExpenseMasters getExpenseByExpenseEventId(int expenseEventId, int revisionNo){
		return expenseDAO.findExpenseByExpenseEventId(expenseEventId, revisionNo);
	}
	
	/**
	 * This method determines if pre-audit is required for an employee 
	 * @param apptId
	 * @param ruleId
	 * @param dept
	 * @param agy
	 * @return String
	 */
public String isPreAuditRequired(UserSubject subject, int ruleId, ExpenseMasters expenseMaster){
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method :isPreAuditRequired");
		
		String preAuditRequired = "N";
		
		String nextActionCodeSw = this.getNextActionCode(expenseMaster);		
		if (nextActionCodeSw!=null){
			if (nextActionCodeSw.equalsIgnoreCase(IConstants.APPROVAL_STEP5)|| nextActionCodeSw.equalsIgnoreCase(IConstants.APPROVAL_STEP6) ){				
			}
			else  {
				return preAuditRequired;
			}	    
			 
		}
		
		preAuditRequired = getPreAuditRuleValue(subject.getAppointmentId(), ruleId, subject.getDepartment(), subject.getAgency());
		
		if ("N".equals(preAuditRequired)){
			// Check to see if there is a newer appointment for the same FACS agency. If yes, 
			// is pre-audit required for the newer appointment?
			List<AppointmentsBean> apptList = apptDAO.findFacsAgencyByExpenseDatesByEmployee(expenseMaster.getExpDateFrom(), expenseMaster.getExpDateTo(), subject.getAppointmentId());
			if (apptList.size() == 1){
				// only a single facs agency was fetched
				AppointmentsBean currentFacsAgyRecord = apptList.get(0);
				Integer latestAppt = apptDAO.findLatestAppointmentForFacsAgency(subject.getEmployeeId(), currentFacsAgyRecord.getDepartment(), currentFacsAgyRecord.getAgency());
				if (latestAppt != null && latestAppt != subject.getAppointmentId()){
					// latest appointment is not the same as the subject appointment
					preAuditRequired = getPreAuditRuleValue(latestAppt, ruleId, currentFacsAgyRecord.getDepartment(), currentFacsAgyRecord.getAgency());
				}
			}
		}
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method :isPreAuditRequired");
		
		return preAuditRequired;
	}
	
	/**
	 * Determines whether an expense is modifiable by the provided user in an employee role.
	 * The following criteria determine the outcome:
	 * <ul>
	 * 	<li>Employees can modify their expenses until not approved by the first approval</li>
	 * 	<li>Employees cannot modify their expenses after first level approval until being processed</li>
	 * 	<li>Processed or rejected expenses can be adjusted by employees only if they have the correct security role.
	 * 		To check for this role, access to module 'EXPF001' is checked. 
	 * 	</li>
	 * </ul>
	 * @param expense - Expense report
	 * @param user - Logged in user who's trying to access his own expense report
	 * @return TRUE - if expense modifiable, FALSE otherwise
	 */
	public boolean isExpenseModifiableByEmployee(ExpenseMasters expense, UserProfile user, UserSubject subject){
        if(expense == null) return false;
        boolean result = false;
        // If expense is an adjustment and user does not have EXP_ADJ role, do not allow him to MODIFY
        ExpenseMasters prevExpInProc = null;
        if(expense.getRevisionNumber() > 0) 
                    prevExpInProc = getPrevExpenseMasterInProcStatus(expense.getExpevIdentifier(), expense.getRevisionNumber());
        boolean userHasExpAdjRole = user.getModules().contains(IConstants.EXPENSE_ADJUSTMENT_MODULE);
        // Evaluate for all cases except when previously PROC and user doesn't have EXP_ADJ role,
        // because in that case user just cannot MODIFY whatever be the status  
        if(prevExpInProc == null || (prevExpInProc != null && userHasExpAdjRole)){
                    String status = expense.getStatus();
                    if(IConstants.SUBMIT.equals(status)){
                                //get the last action performed on the expense
                                ExpenseActions lastAction = null;
                                List<ExpenseActions> actions = expenseActionsDao.findExpenseActions(expense);
                                if(actions != null && !actions.isEmpty()){
                                            lastAction = actions.get(actions.size()-1);
                                            // Find whether this last action is in approval chain. If it is, then this expense
                                            // is in approval path and first level approver has approved it. If not, then this
                                            // expense is still awaiting for first level approval, and hence can be modified.
                                            if(!approveDao.isExpenseInApprovalPathAfterFirstLevelApproval(lastAction.getActionCode())) 

                                                        result = true;
                                }
                    }
                    else if(IConstants.PROCESSED.equals(status) && userHasExpAdjRole) result = true;
                    else if(IConstants.REJECTED.equals(status)) result = prevExpInProc == null || (prevExpInProc != null && userHasExpAdjRole);

        }
        return result;
}

	/**
	 * Determines whether an expense is modifiable by the provided user in a supervisor role.
	 * A supervisor can modify the expense report only if they have write access to user's dept/agency/tku 
	 * and modify access to the current approval module. This later check would prevent a manager from
	 * modifying the expense once it has been approved by the next approver (only if he has no access to 
	 * modify and override the next approvers actions).
	 * PS: This assumes that the supervisor already has write access to the subject user's dept/agency/tku.
	 * If not true, then this method need not be called as the manager cannot modify the expenses according
	 * to this first level requirement. 
	 * 
	 * @param expense - Expense report
	 * @param user - Logged in user who's trying to access his own expense report
	 * @param subject - The employee whose's the expense report is being processed
	 * @return TRUE - if expense modifiable, FALSE otherwise
	 */	
	public boolean isExpenseModifiableBySupervisor(ExpenseMasters expense, UserProfile user, UserSubject subject, ComponentViewState saveView) {

        if(expense == null || user == null || subject == null) return false;
        boolean result = false;
        
        // user cannot modify his own expense when arriving through Mgr/SW link. He must go through 'Employee' link
        if(user.getEmpIdentifier() == subject.getEmployeeId()) return result;
        
		//if this is an PDF expense type, check if the supervisor has Modify role.	
		if (expense.isPdfInd()){	
			int moduleAccess = securityService.getModuleAccessMode(user, IConstants.PDF_MODULE,
					subject.getDepartment(), subject.getAgency(),
					subject.getTku());
			
			if (moduleAccess == IConstants.SECURITY_INQUIRY_MODULE_ACCESS || moduleAccess == IConstants.SECURITY_NO_MODULE_ACCESS)
				result = false;
			else if (moduleAccess == IConstants.SECURITY_UPDATE_MODULE_ACCESS)
				result = true;
			
			if (saveView == ComponentViewState.ENABLED)
				result = false;
			
			return result;
		}
        
        ExpenseMasters prevExpInProc = null;
        if(expense.getRevisionNumber() > 0) 
                    prevExpInProc = getPrevExpenseMasterInProcStatus(expense.getExpevIdentifier(), expense.getRevisionNumber());
        boolean userHasExpAdjRole = securityService.checkModuleAccess(user, IConstants.EXPENSE_ADJUSTMENT_MODULE, subject.getDepartment(), subject.getAgency(), subject.getTku());

        // If expense is an adjustment and user does not have EXP_ADJ role, do not allow him to MODIFY
        if(prevExpInProc != null && !userHasExpAdjRole){
                    result = false;
                    return result;
        }
        String status = expense.getStatus();
        // Supervisors can modify submitted expenses until they have not been extracted 
        // or partially extracted (i.e. adjustment initiated as indicative by the adj_type)
        if(status == null || "".equals(status) || 
                    (IConstants.APPROVED.equals(status) 
                                && expenseDAO.isAdjustmentTypeSetForApprovedExpense(expense.getExpmIdentifier()))){
                    result = false;
        }
        else if(IConstants.PROCESSED.equals(status) && userHasExpAdjRole) result = true;
        else if(IConstants.SUBMIT.equals(status) || IConstants.APPROVED.equals(status)){
                    //get the last action performed on the expense
                    ExpenseActions lastAction = null;
                    List<ExpenseActions> actions = expenseActionsDao.findExpenseActions(expense);
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
                    // supervisor can modify unprocessed rejected expenses, on behalf of employee
                    // For processed expenses, however, they need the security.
                    result = (prevExpInProc == null) ? true : userHasExpAdjRole;
        }
        return result;
}           

	
	/**
	 * Finds the latest action taken on an expense.
	 * 
	 * @param expense
	 * @return
	 */
	public ExpenseActions findLatestExpenseAction(ExpenseMasters expense){
		ExpenseActions result = null;
		
		List<ExpenseActions> expenseActions = expenseActionsDao.findExpenseActions(expense);
		if(expenseActions != null && !expenseActions.isEmpty()){
			result = expenseActions.get(expenseActions.size()-1);
		}
		
		return result;
	}
	
	public String getNextActionCode(ExpenseMasters expenseMaster) {
		List<ExpenseActions> expenseActionList = expenseDAO.approveExpense(expenseMaster);
		if (expenseActionList != null && !expenseActionList.isEmpty()) {
			return expenseActionList.get(0).getNextActionCode();
		}
		return null;
	}
	
	/**
	 * Method used to check if expense is trying to overliquidate an advance. 
	 * @param expmIdentifier
	 * @return TRUE - If overliquidation is being done, FALSE otherwise
	 */
	public boolean isLiqAmtGreaterThanOutstandingAmt(ExpenseMasters expense) {
		boolean result = false;
		List<AdvanceLiquidations> liqs = liquidationService.findLiquidationsByExpenseId(expense.getExpmIdentifier());
		
		for(AdvanceLiquidations liq : liqs){
			double effectiveExpenseLiquidationFromAdv =  
				liquidationService.calculateEffectiveLiquidationAmountFromAdvance(expense.getExpevIdentifier(), liq.getAdvanceMaster());
			
			double effectiveExpenseLiquidationAvailable = effectiveExpenseLiquidationFromAdv + 
				liq.getAdvanceMaster().getAdevIdentifier().getOutstandingAmount();
			
			if (liq.getAmount() > effectiveExpenseLiquidationAvailable) {
				return true;
			}
		}
		
		return result;
	}
	
	
	/**
	 * Method used to check for the current_ind of advance when the expense is getting approved via statewide. 
	 * @param expmIdentifier
	 * @return TRUE - If current_ind = 'Y', FALSE otherwise
	 */
	public boolean isAdvanceCurrentInd(ExpenseMasters expense) {
		boolean result = true;		
		List<AdvanceLiqEditBean> liqs = liquidationService.findLiquidationsByExpenseIdForEdit(expense.getExpmIdentifier());
		
		if (liqs.isEmpty()) {
		  return true;
		}
		for(AdvanceLiqEditBean liq : liqs){
			if ("N".equals(liq.getCurrentInd())){
			   return false;
			}
		}
		return result;
	}
	
	/** Inserts or updates notification errors
	 * Update existing 
	 * @param expenseMasters
	 * @param userProfile
	 * @return
	 */
	
	private void processExpenseNotificationErrors (ExpenseMasters expenseMasters, UserProfile userProfile, int returnCode){
		Iterator it = expenseMasters.getExpenseErrorsCollection().iterator();
		boolean errorExists = false;
    	while (it.hasNext()) {
    	    ExpenseErrors error = (ExpenseErrors) it.next();
    	    if (IConstants.NOTIFICATION_NOT_DUE_TO_SYSTEM_PROBLEMS.equals(error.getErrorCode().getErrorCode()) 
    	    		|| IConstants.NOTIFICATION_NOT_SENT_SINCE_USERID_NOT_DEFINED.equals(error.getErrorCode().getErrorCode())){
    	    	// there are existing notification errors... update user id only
    	     	  error.setModifiedUserId(userProfile.getUserId());
    	     	 errorExists = true;
    	    }
    	}
    	
    	if (!errorExists) {
			// there were no existing errors, insert new
			if (returnCode == 10551) {
				commonDsp
						.populateErrors(
								IConstants.NOTIFICATION_NOT_SENT_SINCE_USERID_NOT_DEFINED,
								IConstants.EXPENSE_SUMMARY_TAB, expenseMasters, userProfile);
			} else if (returnCode == 10550) {
				commonDsp.populateErrors(
						IConstants.NOTIFICATION_NOT_DUE_TO_SYSTEM_PROBLEMS,
						IConstants.EXP_ERR_SRC_ID_TAB, expenseMasters, userProfile);
			}
			// explicit flush is done to get error id for display
			expenseDAO.getEntityManager().flush();
		}
		
	}
	
	public boolean isExpenseMarkedAuditComplete(ExpenseMasters expense){
		return expenseDAO.isExpenseMarkedAuditComplete(expense);
	}
	
	public ExpenseMasters getPrevExpenseMasterInProcStatus(ExpenseEvents expenseEvent, int revisionNo){
		return expenseDAO.findPrevExpenseInStatus(expenseEvent, IConstants.PROCESSED, revisionNo);
	}
	
	public boolean approvalStepExists(ExpenseEvents expenseEvent, int revisionNo, boolean proc){
		List<ExpenseActions> expenseActionsList;
		if (!proc)
			// Expense has never been processed
			expenseActionsList = expenseDAO.findPrevExpenseApprovalAction(expenseEvent, revisionNo);
		else
			// Expense has never been processed
			expenseActionsList = expenseDAO.findPrevExpenseApprovalActionProc(expenseEvent, revisionNo);
		
		return expenseActionsList.isEmpty() ? false: true;
	}
	
	public String isUserOptionExpenseValid(int expmIdentifier, String isTravel, String isPDF, String isOutOfState){
		
		String strReturnValue =  expenseDAO.isUserOptionExpenseValid(expmIdentifier, isTravel, isPDF, isOutOfState);
		StringBuffer sb = new StringBuffer();
		
		//DAO method will return codes. We will decode the return values and return the same to caller method.
		//Possible return values 1-6
		
		String[] returnValues = strReturnValue.trim().split(",");
		
		
		
		for(String str : returnValues){
			
		
			if (str.endsWith("1"))
				sb.append("Meals related expenses before switching to non-travel.<BR />");
			
			if (str.endsWith("2"))
				sb.append("Non-PDF expense types before switching to PDF.<BR />");
			
			if (str.endsWith("3"))
				sb.append("PDF expense types before removing PDF option.<BR />");
			
			if (str.endsWith("4"))
				sb.append("In State expense types before switching to out of state option.<BR />");
			
			if (str.endsWith("5"))
				sb.append("Out of state expense types before switching to In State option.<BR />");
			
			if (str.endsWith("6"))
				sb.append("Incidental expenses before making overnight switch to No.");
		}
		
		if (sb.length() > 0)
			sb.insert(0, "Please delete the following:<BR />");

		return sb.toString();			
	}
	
	
	public String certifyExpense(ExpenseMasters expenseMaster, UserProfile userProfile){
		//ExpenseActions certifyExpenseAction
		
		String jsonResponse = "";//{msg:'Rejected successfully', RejectSuccess : true}";
		
		
		try {

			ExpenseActions newAction = new ExpenseActions();
			newAction.setExpmIdentifier(expenseMaster);
			newAction.setModifiedUserId(userProfile.getUserId());		
			newAction.setComments("Expense certified by employee.");
			newAction.setActionCode(IConstants.CERTIFY);
			newAction.setModifiedUserId(userProfile.getUserId());

			newAction = expenseDAO.saveExpenseAction(newAction);
			
		} catch (Exception e) {
			return 	jsonResponse = "{msg:'" + e.getMessage()+ "'}";
		}
		
		return 	jsonResponse = "{msg:'success'}";
		
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
	public String getRemainingApprovalPaths(Integer expmIdentifier, UserSubject UserSubject) {
		return expenseDAO.getRemainingApprovalPaths(expmIdentifier, UserSubject);
	}
	
	
	public ExpenseEvents saveExpenseEvent(ExpenseEvents expenseEvents){
		return expenseDAO.saveExpense(expenseEvents);
	}
	
	public OutOfStateTravel getOutOfStateTravel(int outOfStateId){
		return expenseDAO.findOutOfStateTravel(outOfStateId);
	}
	
	/**
	 * Clones an existing expense
	 * @param expevIdentifier expense event identifier
	 * @param fromDate
	 * @param toDate 
	 * @return String with error message if any.
	 */
    public String cloneExpense(int expevIdentifier, Date fromDate, Date toDate){
    	return expenseDAO.cloneExpense(expevIdentifier, fromDate, toDate);
    }
    
	/**
	 * Validates Expense dates with Timesheet dates
	 * @param expevIdentifier expense event identifier 
	 * @return String with error message if any.
	 */
    public String compareExpenseDateToTimesheetDate(int expevIdentifier){
    	return expenseDAO.compareExpenseDateToTimesheetDate(expevIdentifier);
    }
    
    /**
     * Method to check if the expenses have been submitted by other users.
     * @param expmIdentifier
     * @return
     */
    public int checkExpensesEnteredByOtherUser(int expmIdentifier, String loggedInUser){
    	return expenseDAO.checkExpensesEnteredByOtherUser(expmIdentifier, loggedInUser);
    }
    
    /**
     * Method to get adjustments made on the expense
     * @param appointmentIdentifier
     * @param expmIdentifier
     * @return
     */    
public List<BigDecimal> getAdjustmentsForExpense(int appointmentIdentifier, int expmIdentifier){
    	return expenseDAO.getAdjustmentsForExpense(appointmentIdentifier, expmIdentifier);
    }

/**
 * Returns the flag value from profile or agency options
 * @param apptId
 * @param ruleId
 * @param dept
 * @param agy
 * @return
 */
private String getPreAuditRuleValue (int apptId, int ruleId, String dept, String agy){
	String preAuditRequired = "N";
	ExpenseProfileRules expenseProfileRules = commonDsp.getExpenseProfileRules(apptId, ruleId);
	
	String ruleValue = null;
	if(expenseProfileRules != null) ruleValue = expenseProfileRules.getValue();		
	
	if(ruleValue != null  && ruleValue.equals("Y")) preAuditRequired = "Y";
	// if rule Value is "N" , then check the rule at the agency level
	//if(ruleValue == null || ruleValue.equals("N")) preAuditRequired = "N";
	if(ruleValue != null  && ruleValue.trim().equalsIgnoreCase("Agy Dfault")){
		AgencyOptions agencyOptions = commonDao.findAgencyOptions(dept, agy);
		preAuditRequired = agencyOptions.getPreAuditRequiredInd();
	}
	return  preAuditRequired;
}

/**
 * Method to get RStars adjustments count made on the expense
 * @param appointmentIdentifier
 * @param expmIdentifier
 * @return
 */    
public int getRStartsAdjustmentsForExpense(int appointmentIdentifier, int expmIdentifier){
	return expenseDAO.getRStartsAdjustmentsForExpense(appointmentIdentifier, expmIdentifier);
}

/**
 * Create a new expense action for auto approvals
 * @param expenseMaster
 */
private void saveNewAutoApproveExpenseAction (ExpenseMasters expenseMaster){
	ExpenseActions autoAction = new ExpenseActions ();
	autoAction = new ExpenseActions();
	autoAction.setActionCode(IConstants.APPROVAL_AUTO);
	autoAction.setExpmIdentifier(expenseMaster);
	autoAction.setModifiedUserId("SYSTEM");
	autoAction = expenseDAO.saveExpenseAction(autoAction);
}

private ExpenseActions addNewSubmitAction (ExpenseMasters expense, ExpenseActions newAction, UserProfile userProfile){
	expense.setStatus("SUBM");	
	newAction = new ExpenseActions();
	newAction.setActionCode(IConstants.SUBMIT);
	newAction.setExpmIdentifier(expense);
	newAction.setModifiedUserId(userProfile.getUserId());
	return newAction;
}
	/**
	 * Returns if the user has <B>PDF ENTRY</B> or <B>PDF APPROVAL</B> role present. 
	 * @param userID
	 * @return boolean, TRUE if he has role, false otherwise
	 */	
	public boolean isUserWithPDFRole(String userID, String department, String agency, String tku) {
		return expenseDAO.isUserWithPDFRole(userID,department, agency, tku);
	}

	/**
	 * Method to determine if the user has roles other than PDF 
	 * @param expense
	 * @return <B>true</b> if user has roles other than 'PDF ENTRY', 'PDF APPROVAL' role.
	 */	
	public boolean doesUserHasStatewideRoles(String userID, String department, String agency, String tku, String moduleID) {
		return expenseDAO.doesUserHasStatewideRoles(userID, department, agency, tku, moduleID);
	}
	
	public String getLatestExpenseActionExcludingAudit(ExpenseMasters expense){
		String retVal = "";
		ExpenseActions latestAction = expenseActionsDao.findLatestExpenseActionExcludingAuditCertify(expense);
		if ( latestAction == null){
			retVal = "";
		} else {
			retVal = latestAction.getActionCode();
		}
		return retVal;
	}
	
    
	public void setExpenseDAO(ExpenseDAO expenseDAO) {
		this.expenseDAO = expenseDAO;
	}

	public ExpenseDAO getExpenseDAO() {
		return expenseDAO;
	}

	public void setExpenseLineItemDAO(ExpenseLineItemDAO expenseLineItemDAO) {
		this.expenseLineItemDAO = expenseLineItemDAO;
	}

	public ExpenseLineItemDAO getExpenseLineItemDAO() {
		return expenseLineItemDAO;
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
