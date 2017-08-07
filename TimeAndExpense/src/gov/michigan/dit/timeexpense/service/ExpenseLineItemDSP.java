package gov.michigan.dit.timeexpense.service;

import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseLineItemDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseTypeDAO;
import gov.michigan.dit.timeexpense.exception.ExpenseRateNotFoundException;
import gov.michigan.dit.timeexpense.exception.TimeAndExpenseException;
import gov.michigan.dit.timeexpense.model.core.ExpenseActions;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseLocations;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseRates;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypeRules;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypes;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.db.Actions;
import gov.michigan.dit.timeexpense.model.display.CodingBlockSummaryBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseSummaryDetailsBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseTypeDisplayBean;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.ExpenseViewUtil;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

public class ExpenseLineItemDSP {

	private ExpenseLineItemDAO expenseLineItemDAO;

	private ExpenseDAO expenseDAO;
	private CommonDAO commonDao;
	private CommonDSP commonDsp;
	private AdvanceDSP advanceService;
	private ExpenseTypeDAO expenseTypeDao;
	private ExpenseDSP expenseService;
	EntityManager entityManager;
	
	
	private int seq = 1;
	private static final Logger logger = Logger.getLogger(ExpenseLineItemDSP.class);

	public ExpenseLineItemDSP() {}

	public ExpenseLineItemDSP(EntityManager em) {
		expenseLineItemDAO = new ExpenseLineItemDAO(em);
		expenseDAO = new ExpenseDAO(em);
		commonDao = new CommonDAO(em);
		commonDsp = new CommonDSP(em);
		advanceService = new AdvanceDSP(em);
		expenseTypeDao = new ExpenseTypeDAO(em);
		entityManager =  em;
	}

	/**
	 * This method retrieves the common mile between the source and destination, if exists. Otherwise returns 0.
	 * If source city or state is NULL or blank string or if the states have value "NA", then always 0 is returned.

	 * @param srcCity
	 * @param srcState
	 * @param destCity
	 * @param destState
	 * @return mileage as double
	 */
	public double getCommonMiles(String srcCity, String srcState, String destCity, String destState, String department, String agency) {
		double mileage = 0;

		if (!(srcCity == null || srcCity.equals("") || srcState == null || srcState.equals("") || srcState.equals("NA")
				|| destCity == null || destCity.equals("") || destState == null || destState.equals("") || destState.equals("NA"))) {
			mileage = expenseLineItemDAO.findCommonMiles(srcCity, srcState,destCity, destState, department, agency); 
		}
		
		return mileage;
	}

	// TODO [SG] is this method required ???
	public void applyExpenseLineItem(ExpenseMasters expenseMasters,
			ExpenseDetails expenseDetails,UserProfile userProfile,String moduleId, int appointmentId,Date[]expenseDatesFromAction) {
		
	}
	
	/***
	 * This method validates the following. 
	 * <ul>
	 * 	<li>Departure time cannot be blank when Return time is not blank and vice versa.</li>
	 * 	<li>Departure time cannot be later than the Return Time</li>
	 * </ul>
	 * 
	 * @param expenseMasters
	 * @param details
	 */

	public void validateTripTime(ExpenseMasters expenseMasters, UserProfile userProfile){
		if(expenseMasters == null || expenseMasters.getExpenseDetailsCollection() == null
			 || expenseMasters.getExpenseDetailsCollection().isEmpty()) return;
		List<ExpenseDetails> details = expenseMasters.getExpenseDetailsCollection();
		
		if(logger.isDebugEnabled())
			logger.debug("Validating the Departure time and Return time always exist.");

		for(ExpenseDetails detail : details){
			Date departT = detail.getDepartTime();
			Date returnT = detail.getReturnTime();

			if(expenseMasters.isTravelRelated() && departT == null && returnT == null){
				commonDsp.populateErrors(IConstants.EXP_DEPARTURE_RETURN_TIME_MISSING_WARNING, 
						IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + detail.getLineItem(), expenseMasters, userProfile);
			}			
			else if(departT != null && returnT == null)
				commonDsp.populateErrors(IConstants.EXPENSE_DETAILS_DEPART_TIME_RETURN_TIME_COMBINATION_NOT_EXISTS, 
						IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + detail.getLineItem(), expenseMasters, userProfile);
			else if(departT == null && returnT != null)
				commonDsp.populateErrors(IConstants.EXPENSE_DETAILS_DEPARTURE_TIME_MUST_EXIST, 
						IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + detail.getLineItem(), expenseMasters, userProfile);
			else if(departT != null && returnT != null && departT.after(returnT))
					commonDsp.populateErrors(IConstants.EXPENSE_DETAILS_DEPARTURE_TIME_EARLIER_THAN_RETURN_TIME, 
							IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + detail.getLineItem(), expenseMasters, userProfile);
		}
	}
	
	/**
	 * Performs the expenseLineItem validations.
	 * 
	 * @param expenseMaster
	 * @param expenseDtls
	 */
	public void validateExpenseLineItem(ExpenseMasters expenseMaster, UserProfile userProfile, UserSubject userSubject, String moduleID) {
		// Delete any errors related to the ExpenseLine if they exist
		List<ExpenseErrors> errorsList = expenseMaster.getExpenseErrorsCollection();
		if(errorsList==null)
			errorsList = new ArrayList<ExpenseErrors>();
		else
			expenseMaster = commonDao.deleteExpenseErrors(expenseMaster, IConstants.EXP_ERR_SRC_DTL_TAB);
		
		// Call the Expense Line Item validations
		validateExpenseDetailsDate(expenseMaster, userProfile);
		validateExpenseDetailAmount(expenseMaster, userProfile);
		validateExpenseTypeAndCommentsForMileage(expenseMaster, userProfile);
		validateTripTime(expenseMaster, userProfile);
		validateStateForOutOfStateTravel(expenseMaster, userProfile, userSubject);
		validateCommentsForInternationalExpenses(expenseMaster, userProfile);
		validateSelectCityExpenseSelectCityLocationPair(expenseMaster, userProfile);
		expenseService =  new ExpenseDSP(entityManager);
		expenseService.validatePDFExpense(expenseMaster, userProfile, userSubject, moduleID);
		
		//pass bargain Unit and Premium mileage validation for for Super User
		if (!CommonDSP.isUserSuperUser(userProfile)) {
			validateMealTimes(expenseMaster, userProfile, userSubject);
			validateExpenseTypeBargainUnit(expenseMaster, userProfile, userSubject);		
			validateExpenseTypeMileageRule(expenseMaster, userProfile, userSubject);
		}
		
		if(logger.isDebugEnabled())
			logger.debug("Validation errors found: "+expenseMaster.getExpenseErrorsCollection().size());
		
	}
	
	
	private void validateExpenseTypeMileageRule(ExpenseMasters expenseMaster,
			UserProfile userProfile, UserSubject userSubject) {
		
		//1. For each expense type
		//2. If expense type = "P" (premium) and mileage >= 100 [This is potential candidate for logging error.]
		//3. Check if the expense type is configured with the 1006 rule with value as "y".
		//4.Log error.
		
		for (ExpenseDetails dtl : expenseMaster.getExpenseDetailsCollection()) {
			if (expenseTypeDao.isExpensePremiumMileage(dtl.getExpTypeCode()
					.getExpTypeCode())) {

				if (dtl.getMileage() > 100
						|| (!dtl.isRoundTrip() && dtl.getMileage() > 50)) {
					List<ExpenseTypeRules> listExpenseTypeRules = dtl
							.getExpTypeCode().getExpenseTypeRules();
					for (ExpenseTypeRules rules : listExpenseTypeRules) {
						if (rules.getRuleIdentifier() == 1006
								&& "Y".equalsIgnoreCase(rules.getValue())) {
							commonDsp
									.populateErrors(
											IConstants.EXPENSE_DETAILS_VALIDATE_PREMIUM_MILEAGE,
											IConstants.EXP_ERR_SRC_DTL_TAB
													+ IConstants.SINGLE_BLANK
													+ dtl.getLineItem(),
											expenseMaster, userProfile);
							break;
						}
					}
				}
			}
		}
	}

	private void validateExpenseTypeBargainUnit(ExpenseMasters expenseMaster,
			UserProfile userProfile, UserSubject userSubject) {

		// call DSP-> DAO
		for (ExpenseDetails dtl : expenseMaster.getExpenseDetailsCollection()) {
			
			int bargainUnitCount = isValidExpenseBargainUnit(
					userSubject.getAppointmentId(), dtl.getExpTypeCode()
							.getExpTypeCode(), expenseMaster.getExpDateTo());

			if (bargainUnitCount == 0)
				commonDsp
						.populateErrors(
								IConstants.EXPENSE_DETAILS_VALIDATE_EXPENSE_BARGAINING_UNIT,
								IConstants.EXP_ERR_SRC_DTL_TAB
										+ IConstants.SINGLE_BLANK
										+ dtl.getLineItem(), expenseMaster,
								userProfile);
		}
	}

	/**
	 * Validate Meals Times, Call F_TKU_MEAL_TIME function. This will be validated just for the meal types.	 
	 * 
	 * @param expenseMaster
	 * @param userSubject
	 */
	private void validateMealTimes(ExpenseMasters expenseMaster, UserProfile userProfile, UserSubject userSubject) {

		for (ExpenseDetails dtl : expenseMaster.getExpenseDetailsCollection()) {
			
			String categoryCode = dtl.getExpTypeCode().getCategoryCode();			
			boolean isMealType = ExpenseViewUtil.isExpenseMealType(dtl
					.getExpTypeCode().getExpenseTypeRules());
			
			if (isMealType){
				
				if (userSubject != null){
					
					String departHours = commonDsp.padLeftWithCharacter(
								dtl.getDepartTime().getHours(),
								2 - Integer.toString(dtl.getDepartTime().getHours()).length(),
								"0"
								);
					
					String departMinutes = commonDsp.padLeftWithCharacter(
							dtl.getDepartTime().getMinutes(),
							2 - Integer.toString(dtl.getDepartTime().getMinutes()).length(),
							"0"
							);
					
					String returnHours = commonDsp.padLeftWithCharacter(
							dtl.getReturnTime().getHours(),
							2 - Integer.toString(dtl.getReturnTime().getHours()).length(),
							"0"
							);
					
					String returnMinutes = commonDsp.padLeftWithCharacter(
							dtl.getReturnTime().getMinutes(),
							2 - Integer.toString(dtl.getReturnTime().getMinutes()).length(),
							"0"
							);
					
					int noOfRecords = isMealTimeValid(
							categoryCode
							,dtl.getExpDate()
							, departHours + departMinutes
							,returnHours + returnMinutes
							,userSubject.getDepartment(), userSubject.getAgency()
							,userSubject.getTku()
							);

					if (noOfRecords == 0){
						commonDsp
								.populateErrors(
										IConstants.EXPENSE_DETAILS_VALIDATE_MEAL_TIMES,
										IConstants.EXP_ERR_SRC_DTL_TAB
												+ IConstants.SINGLE_BLANK
												+ dtl.getLineItem(), expenseMaster,
										userProfile);
					}
				}
			}
		}
	}

	/**
	 * Validating if the selected expense is a valid select city expense for
	 * that date.
	 * 
	 * @param expenseMaster
	 * @param userProfile
	 */
	private void validateSelectCityExpenseSelectCityLocationPair(
			ExpenseMasters expenseMaster, UserProfile userProfile) {

		// 1. Get Expense Type
		// 2. Get Expense Date
		// 3. Get "To" city and state
		// 4. Call DAO

		for (ExpenseDetails dtl : expenseMaster.getExpenseDetailsCollection()) {

			boolean isSelectCityExpenseValid = isExpenseSelectCityPairValidOnDate(
					dtl.getExpDate(), dtl.getToElocCity(), dtl
							.getToElocStProv(), dtl.getExpTypeCode()
							.getExpTypeCode());

			if (isSelectCityExpenseValid == false) {
				commonDsp
						.populateErrors(
								IConstants.EXPENSE_DETAILS_LOCATION_NOT_SELECT_CITY,
								IConstants.EXP_ERR_SRC_DTL_TAB
										+ IConstants.SINGLE_BLANK
										+ dtl.getLineItem(), expenseMaster,
								userProfile);
			}
		}

	}

	/**
	 * Adds errors to the expense collection for each line item claiming 
	 * expenses for international locations without comments.
	 * 
	 * @param expenseMaster
	 */
	private void validateCommentsForInternationalExpenses(ExpenseMasters expenseMaster, UserProfile userProfile) {
		for(ExpenseDetails dtl : expenseMaster.getExpenseDetailsCollection()){
			if(("IX".equals(dtl.getFromElocStProv()) || "IX".equals(dtl.getToElocStProv())) 
					&& (dtl.getComments() == null || dtl.getComments().trim().length() < 1)){
				commonDsp.populateErrors(IConstants.EXP_DETAIL_COMMENT_MISSING_FOR_INTERNATIONAL_EXPENSE, 
							IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + dtl.getLineItem(), expenseMaster, userProfile);
			}
		}
	}

	/**
	 * Checks if any of the expense details date is beyond the expense date. If so, an error is added to the
	 * <code>ExpenseMasters's</code> error collection.
	 *
	 * @param master
	 * @param details
	 */
	public void validateExpenseDetailsDate(ExpenseMasters master, UserProfile userProfile){
		if (master == null || master.getExpenseDetailsCollection() == null
			|| master.getExpenseDetailsCollection().isEmpty()) return;
		List<ExpenseDetails> details = master.getExpenseDetailsCollection();
		if(details == null) return;

		if(logger.isInfoEnabled()) logger.info("Validating : if Expense date is between Expense dates entered on the ID Tab");
		
		for(ExpenseDetails dtl : details){
			if(isExpenseDateBeyondExpenseMasterDates(dtl.getExpDate(), master)){
				commonDsp.populateErrors(IConstants.EXPENSE_DETAILS_DATE_NOT_BETWEEN_EXPENSE_ID_DATE, 
											IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + dtl.getLineItem(), master, userProfile);
			}
		}
	}

	/**
	 * Validates expense details to have reimbursement amount greater than zero.
	 * 
	 * @param expenseMaster
	 */
	public void validateExpenseDetailAmount(ExpenseMasters expense, UserProfile userProfile) {
		if(expense == null || expense.getExpenseDetailsCollection() == null) return;
		
		for(ExpenseDetails detail : expense.getExpenseDetailsCollection()){
			if(detail.getDollarAmount() == 0){
				commonDsp.populateErrors(IConstants.EXP_DTL_AMT_ZERO_ERROR,
						IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + detail.getLineItem(), detail.getExpmIdentifier(), userProfile);
			}
		}
		
	}

	/**
	 * This method performs the date validation 
	 * @param expenseDate
	 * @param expenseMaster
	 * @return
	 */
	private boolean isExpenseDateBeyondExpenseMasterDates(Date expenseDate, ExpenseMasters expenseMaster){
		if(logger.isDebugEnabled())
			logger.debug("Validating Expense Date is within the dates on the Expense ID");
		
		return expenseDate.after(expenseMaster.getExpDateTo()) || expenseDate.before(expenseMaster.getExpDateFrom());
	}
	
	/**
	 * This method performs the following validations 
	 * 	a. Validates that the Mileage is not blank if the Expense_type is MILEAGE RELATED
	 *  b. Validates that comments are entered when the reimbursement amount is changed by the user
	 *  c. Validate that comments are entered when an Expense type requires comments to be entered
	 *   
	 * @param details
	 */
	public void validateExpenseTypeAndCommentsForMileage(ExpenseMasters expenseMaster, UserProfile userProfile){
		if(expenseMaster == null || expenseMaster.getExpenseDetailsCollection() == null
			|| expenseMaster.getExpenseDetailsCollection().isEmpty()) return;
		List<ExpenseDetails> details = expenseMaster.getExpenseDetailsCollection();

		for(ExpenseDetails expenseDetail : details){
			
			// Check expense type validity on line item date
			List<ExpenseTypes> expenseTypes = expenseLineItemDAO.findExpenseType(expenseDetail.getExpTypeCode().getExpTypeCode(), 
																					expenseDetail.getExpDate());
			// Invalid expense type
			if(expenseTypes.size() < 1){
				// add permanent warning if standard rate overridden
				//TODO[mc]: replace with valid error code when available
				commonDsp.populateErrors(IConstants.INVALID_EXPENSE_TYPE, 
					IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + expenseDetail.getLineItem(), expenseDetail.getExpmIdentifier(),userProfile);
			}
			
			double stdExpTypeRate = findStandardExpenseTypeRates(expenseDetail.getExpTypeCode().getExpTypeCode(), expenseDetail.getExpDate());
			double stdReimbursementAmt = isMileageRelated(expenseDetail.getExpTypeCode()) ?  
											(expenseDetail.getMileage() * stdExpTypeRate) : stdExpTypeRate;
			
			// round off to 2 decimal places
			stdReimbursementAmt = ((double)Math.round(stdReimbursementAmt*100))/100;
			
			// If amount overriding standard reimbursement amount, except for vicinity miles(handled below)
			if(stdExpTypeRate > 0 && expenseDetail.getDollarAmount() > stdReimbursementAmt && expenseDetail.getVicinityMileage() <= 0){
				// add permanent warning if standard rate overridden
				commonDsp.populateErrors(IConstants.EXPENSE_DETAILS_REIMBURSEMENT_AMOUNT_CHANGED, 
						IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + expenseDetail.getLineItem(), expenseDetail.getExpmIdentifier(),userProfile);

				// add error if comments missing
				if(expenseDetail.getComments() == null || expenseDetail.getComments().equals("")){
					commonDsp.populateErrors(IConstants.EXPENSE_DETAILS_COMMENTS_REQUIRED_WHEN_REIMBURSEMENT_AMOUNT_CHANGED, 
							IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + expenseDetail.getLineItem(), expenseDetail.getExpmIdentifier(),userProfile);
				}
			}

			// vicinity mile checks
			if(expenseDetail.getVicinityMileage()> 0){
				commonDsp.populateErrors(IConstants.EXPENSE_DETAILS_VICINITY_MILES_ENTERED, 
						IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + expenseDetail.getLineItem(), expenseDetail.getExpmIdentifier(),userProfile);
				
				if(expenseDetail.getComments() == null || expenseDetail.getComments().equals("")){
					commonDsp.populateErrors(IConstants.EXPENSE_DETAILS_COMMENTS_REQUIRED_VICNITY_MILES_ENTERED, 
							IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + expenseDetail.getLineItem(), expenseDetail.getExpmIdentifier(),userProfile);
				}
			}
			//Overnight selection for lodging.
			if((expenseDetail.getOvernightInd()==null || "N".equalsIgnoreCase(expenseDetail.getOvernightInd()))  &&  (expenseDetail.getExpTypeCode().getDescription().toLowerCase().contains("lodging")) ){
				commonDsp.populateErrors(IConstants.OVERNIGHT_NOT_SELECTED_FOR_LODGING_EXPENSE_TYPE, 
						IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + expenseDetail.getLineItem(), expenseDetail.getExpmIdentifier(),userProfile);
			}
			
			
			// Execute all rules attached to this detail, if configured to execute.
			List<ExpenseTypeRules> rules = expenseDetail.getExpTypeCode().getExpenseTypeRules();
			
			for(ExpenseTypeRules rule : rules){
				performRuleValidation(expenseDetail, rule, userProfile);
			}
		}
	}
		
	private void performRuleValidation(ExpenseDetails expenseDetail,ExpenseTypeRules rule, UserProfile userProfile) {
		if("N".equalsIgnoreCase(rule.getValue())) return;

		// If ZERO miles (i.e. no miles or vicinity miles) entered for MILEAGE_TYPE expense type, raise Error.
		if(rule.getRuleIdentifier().equals(IConstants.MILEAGE_RELATED_RULE_ID) && expenseDetail.getMileage()<= 0 && expenseDetail.getVicinityMileage() <= 0){
			commonDsp.populateErrors(IConstants.EXPENSE_DETAILS_MILES_REQUIRED_FOR_MILEAGE_EXPENSE_TYPE, 
					IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + expenseDetail.getLineItem(), expenseDetail.getExpmIdentifier(),userProfile);
		}
		
		// Rules attached to comments
		if(rule.getRuleIdentifier().equals(IConstants.EXPENSE_TYPE_RULE_COMMENTS_REQUIRED)
				&& (expenseDetail.getComments() == null || "".equals(expenseDetail.getComments()))){
			
			boolean commentWarningExists = false;
			for(ExpenseErrors err: expenseDetail.getExpmIdentifier().getExpenseErrorsCollection()){
				if(IConstants.EXPENSE_DETAILS_COMMENTS_REQUIRED_WHEN_REIMBURSEMENT_AMOUNT_CHANGED.equals(err.getErrorCode().getErrorCode())){
					
					// make sure this error is for this detail line only
					int existingErrorLineNo = -1; 
					try{
						existingErrorLineNo = Integer.parseInt(err.getErrorSource().substring(err.getErrorSource().length()-1, err.getErrorSource().length()));
					}catch(Exception ex){}
					
					if(existingErrorLineNo > -1 && existingErrorLineNo == expenseDetail.getLineItem()){
						commentWarningExists = true;
						break;
					}
				}
			}
			
			if(!commentWarningExists){
				commonDsp.populateErrors(IConstants.EXPENSE_DETAILS_COMMENTS_REQUIRED_FOR_EXPENSE_TYPE, 
						IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + expenseDetail.getLineItem(), expenseDetail.getExpmIdentifier(),userProfile);
			}
		}
	}
	
	/** 
	 * Method to validate MICHIGAN state for expenses not marked as out-of-state travel.
	 *  
	 */
	public void validateStateForOutOfStateTravel(ExpenseMasters expense, UserProfile userProfile, UserSubject userSubject){
		if(expense == null || expense.getExpenseDetailsCollection() == null
			|| expense.getExpenseDetailsCollection().isEmpty()) return;
		
		//get valid States list for this TKU
		
		String states = getInStateList(userSubject.getDepartment(), userSubject.getAgency(), userSubject.getTku());
		
		for(ExpenseDetails detail : expense.getExpenseDetailsCollection()){
			
			boolean validFromState = states.contains(detail.getFromElocStProv()) || "NA".equals(detail.getFromElocStProv());
			boolean validToState = states.contains(detail.getToElocStProv()) || "NA".equals(detail.getToElocStProv());
			
			if("N".equalsIgnoreCase(expense.getOutOfStateInd()) && !(validFromState && validToState)){
					commonDsp.populateErrors(IConstants.EXPENSE_DETAILS_WARNING_FROM_TO_STATE_FOR_IN_STATE_TRAVEL,
							IConstants.EXP_ERR_SRC_DTL_TAB + IConstants.SINGLE_BLANK + detail.getLineItem(), expense,userProfile);
			}
		}
	}

	/***
	 * This method adds the ExpenseDetails collection to the ExpenseMasters and saves the ExpenseMaster
	 * @param expenseMaster
	 * @param expenseDtls
	 * @return ExpenseMasters
	 */
	@SuppressWarnings("unchecked")
/*	public ExpenseMasters saveExpenseLineItems(ExpenseMasters expenseMaster,List<ExpenseDetails> expenseDtls) {

		if(logger.isDebugEnabled())
			logger.debug("Enter method : saveExpenseLineItems(ExpenseMasters expenseMaster,List<ExpenseDetails> expenseDtls):return ExpenseMasters");
		
		// validate Expense LineItems 
		   validateExpenseLineItem(expenseMaster,expenseDtls);
		// Check if there are more than line item to be saved
			List<ExpenseDetails> expenseDtlsList = null;
			if(expenseMaster.getExpenseDetailsCollection()==null || expenseMaster.getExpenseDetailsCollection().size()==0)
				expenseDtlsList = new ArrayList<ExpenseDetails>();
			else
				expenseDtlsList = expenseMaster.getExpenseDetailsCollection();

			Iterator expenseDtlsIter = expenseDtls.iterator();
			while(expenseDtlsIter.hasNext()){
				ExpenseDetails expenseDetails = (ExpenseDetails)expenseDtlsIter.next();
				
				expenseDetails.setExpmIdentifier(expenseMaster);
				expenseDtlsList.add(expenseDetails);
			}
			ExpenseMasters savedExpense= expenseDAO.saveExpense(expenseMaster);
			
			if(logger.isDebugEnabled())
				logger.debug("Exit method : saveExpenseLineItems(ExpenseMasters expenseMaster,List<ExpenseDetails> expenseDtls)");
			return savedExpense;
	}*/
	
	/****
	 * This method calculates the total taxable and non-taxable expenses based on Expense Type
	 * @param expenseSummaryBeanList
	 * @return List<Double>
	 */

	public List<Double> calculateExpenses(
			List<ExpenseDetails> expenseDtlsList) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : calculateExpenses(	List<ExpenseDetails> expenseDtlsList):return List<Double>");
		
		double taxableAmount = 0;
		double nonTaxableAmount = 0;
		List<Double> expenseAmtList = new ArrayList<Double>();

		for(ExpenseDetails expenseDtls : expenseDtlsList){
			boolean taxableExpenseType = false;
			try{
				taxableExpenseType = isExpenseTypeTaxable(expenseDtls.getExpTypeCode());
			}catch(TimeAndExpenseException tae){
				// Ignore this Expense detail, if no exact match found !
				continue;
			}

			if(taxableExpenseType){
				taxableAmount += expenseDtls.getDollarAmount();
			}
			else{
				nonTaxableAmount += expenseDtls.getDollarAmount();
			}	
			
/*			List<ExpenseTypeRules> rules = expenseDtls.getExpTypeCode().getExpenseTypeRules();
			
			
			
			for(ExpenseTypeRules rule : rules){
				if(rule.equals(IConstants.TAXABLE_EXPENSES)){
					taxableAmount += expenseDtls.getDollarAmount();
				}
				else{
					nonTaxableAmount += expenseDtls.getDollarAmount();
				}
			}*/
		}
		/*for (int index = 0; index < expenseDtlsList.size(); index++) {
			String taxableRuleValue = commonDsp.getExpenseTypeRuleValue(expenseDtlsList.get(index).getExpTypeCode().getExpTypeCode(), Integer.parseInt(IConstants.TAXABLE_EXPENSES));
			if (taxableRuleValue.equals("Y")) {
				taxableAmount += expenseDtlsList.get(index).getDollarAmount();

			} else {
				nonTaxableAmount += expenseDtlsList.get(index).getDollarAmount();
			}
		}*/
		expenseAmtList.add(taxableAmount);
		expenseAmtList.add(nonTaxableAmount);
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : calculateExpenses(	List<ExpenseDetails> expenseDtlsList)");
		
		return expenseAmtList;
	}
	
	public boolean isExpenseTypeTaxable(ExpenseTypes expType) throws TimeAndExpenseException{
		ExpenseTypeRules taxabilityRule = null;
		taxabilityRule = expenseLineItemDAO.findExpenseTypeByCodeAndRuleId(expType, 1009);
		
		if ("Y".equalsIgnoreCase(taxabilityRule.getValue())) {
			return true;
		} else {
			return false;
		}
		
	}

	/*****
	 * This method gets the Expense summary details. This method invokes multiple DAO methods to build the Summary details
	 * @param expenseMasters
	 * @param expenseSummaryBeanList
	 * @return
	 */

	public List<Object> getExpenseSummaryDetails(ExpenseMasters expenseMaster,int employeeId) {
		
		List<ExpenseSummaryDetailsBean> expenseSummaryList = null;
		List<CodingBlockSummaryBean> cbSummaryBeanList = null;
		List<Double> expenseAmountsList = this.calculateExpenses(expenseMaster.getExpenseDetailsCollection());
		double amountLiquidated = advanceService.getExpenseLiquidationByMasterId(expenseMaster.getExpmIdentifier());
		
		expenseSummaryList = expenseLineItemDAO.findExpenseSummaryDetails(expenseMaster.getExpmIdentifier());
		cbSummaryBeanList = expenseLineItemDAO.findExpenseSummaryByCodingBlock(expenseMaster.getExpmIdentifier());
		List<CodingBlockSummaryBean> cbSummaryBeanListNew = new ArrayList<CodingBlockSummaryBean>();
		for (Iterator iterator = cbSummaryBeanList.iterator(); iterator.hasNext();) {
			CodingBlockSummaryBean item = (CodingBlockSummaryBean) iterator.next();
			item.setSequence(seq);
			seq++;
			cbSummaryBeanListNew.add(item);
		}
		List<Object> returnList = new ArrayList<Object>();
		returnList.add(expenseSummaryList);
		returnList.add(cbSummaryBeanListNew);
		returnList.add(amountLiquidated);
		returnList.add(expenseAmountsList);

		return returnList;
	}
	
	/****
	 * This method deletes the ExpenseDetails record passed in and updates the Expense Master.
	 * @param expenseMasters
	 * @param expenseDtlsId
	 */

	public void deleteExpenseLineItem(ExpenseMasters expenseMasters,
			int expenseDtlsId) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : deleteExpenseLineItem(ExpenseMasters expenseMasters,int expenseDtlsId):return void");

		Collection<ExpenseDetails> expenseDetailsList = expenseMasters.getExpenseDetailsCollection();
		Iterator<ExpenseDetails> expDtlsIterator = expenseDetailsList.iterator();

		ExpenseDetails expenseDetails = getExpenseLineItemDAO().getEntityManager().find(ExpenseDetails.class, expenseDtlsId);
		getExpenseLineItemDAO().getEntityManager().remove(expenseDetails);
		/*while (expDtlsIterator.hasNext()) {
			ExpenseDetails ed = expDtlsIterator.next();
			for (int i = 0; i < expenseDtlsId.length; i++) {
				if (ed.getExpdIdentifier().equals(expenseDtlsId)) {
					expDtlsIterator.remove();
				}
			}
		}*/
		expenseDAO.saveExpense(expenseMasters);
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : deleteExpenseLineItem(ExpenseMasters expenseMasters,int expenseDtlsId)");
	}

	public List<ExpenseTypes> getExpenseTypes(Date expenseDate) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpenseTypesAndRate(Date expenseDate):return List<ExpenseSummaryBean>");
		
		List<ExpenseTypes> expenseTypes = expenseLineItemDAO.findExpenseTypes(expenseDate);
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpenseTypesAndRate(Date expenseDate):return List<ExpenseSummaryBean>");

		return expenseTypes;
	}

	public boolean auditComplete(ExpenseMasters expenseMasters, String userId) {
	
		if(logger.isDebugEnabled())
			logger.debug("Enter method : auditComplete(ExpenseMasters expenseMasters, String userId) : return boolean");
		
		ExpenseActions expenseActions = new ExpenseActions();
		expenseActions.setExpmIdentifier(expenseMasters);
		
		Actions action = getExpenseDAO().getEntityManager().find(Actions.class, "AUDT");
		
		expenseActions.setActionCode(IConstants.AUDIT);
		expenseActions.setModifiedUserId(userId);
		//expenseActions.setModifiedDate(Calendar.getInstance().getTime());
		
		//expenseMasters.getExpenseActionsCollection().add(expenseActions);
		expenseDAO.saveExpenseAction(expenseActions);
		
		if(logger.isInfoEnabled())
			logger.info("Enter JPA operation : Persisting ExpenseMasters entity");

			expenseDAO.saveExpense(expenseMasters);
		if(logger.isInfoEnabled())
			logger.info("Exit JPA operation : ExpenseMasters entity persistance completed..");
			
		if(logger.isDebugEnabled())
			logger.debug("Exit method : auditComplete(ExpenseMasters expenseMasters, String userId)");

		return true;
	}
	
	/**
	 * This method retrieves the Expense details information that is attached to an Expense Master. 
	 * Also retrieves the advance amount outstanding for the employee and all Expense locations.  	
	 * @param expenseMasters
	 * @return 
	 */
	
	
	public List<Object> getExpenseLineItems(ExpenseMasters expenseMasters) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpenseLineItems(ExpenseMasters expenseMasters): return List<Object>");
		
		Collection<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		expenseDetailsList = expenseLineItemDAO
				.findExpenseLineItems(expenseMasters);
		/*
		 * TODO Call AdvanceDSP.getOutstandingAdvByEmpId(empId) to retrieve the
		 * total outstanding for the employee.
		 */
		double outStandingAdvanceByEmpId = 100;
		List<ExpenseLocations> expenseLocList = expenseLineItemDAO.findLocations();
		ArrayList<Object> objList = new ArrayList<Object>();

		objList.add(expenseDetailsList);
		objList.add(expenseLocList);
		objList.add(new Double(outStandingAdvanceByEmpId));
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpenseLineItems(ExpenseMasters expenseMasters)");

		return objList;
	}
	
	/**
	 * Checks all the rules applied to the given expense type to
	 * find a mileage related rule. If found returns <code>true</code>,
	 * else returns <code>false</code>.
	 */
	public boolean isMileageRelated(ExpenseTypes expenseType){
		boolean result = false;
		
		for(ExpenseTypeRules rule : expenseType.getExpenseTypeRules()){
			if(IConstants.MILEAGE_RELATED_RULE_ID.equals(rule.getRuleIdentifier()) && "Y".equalsIgnoreCase(rule.getValue())){
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public double getExpenseRateByExpenseType(String expTypeCode, Date expenseDate) throws ExpenseRateNotFoundException{
		return expenseLineItemDAO.findExpenseRateByExpenseType(expTypeCode, expenseDate);
	}
	
	/**
	 * Finds the standard reimbursement rate for the given expense types.
	 * 
	 * @param expTypeCodes -  list of expense type codes
	 * @param effectiveDate - Historized date
	 * @return Map conatining expense type code and corresponding standard rate
	 */
	public Map<String, Double> getExpenseRatesForExpenseTypes(List<String> expTypeCodes, Date effectiveDate){
		List<ExpenseRates> rates = expenseLineItemDAO.findExpenseRatesForExpenseTypes(expTypeCodes, effectiveDate);
		
		Map<String, Double> expenseTypeRate = new HashMap<String, Double>(rates.size(), 1);
		
		for(ExpenseRates rate : rates){
			expenseTypeRate.put(rate.getExpTypeCode(), rate.getRateAmt());
		}
		
		return expenseTypeRate;
	}

	/**
	 * Finds the standard reimbursement rate for an expense type on a given date.
	 * 
	 * @param expTypeCode - Expense type code
	 * @param effectiveDate -  date for which the rate is desired
	 * @return standard rate if found, 0 otherwise
	 */	
	public double findStandardExpenseTypeRates(String expTypeCode, Date effectiveDate){
		List<ExpenseRates> rates = expenseLineItemDAO.findExpenseRatesForExpenseTypeWithinDateRange(expTypeCode, 
																				effectiveDate, effectiveDate);
	
		return (rates != null && rates.size() > 0 && rates.get(0) != null) ? rates.get(0).getRateAmt(): 0;
	}
	
	
	/**
	 * Finds all the <code>ExpenseRates</code>, within the date range provided,
	 * for the given expense type. This can be used to determine whether there was
	 * a rate change within the dates provided (in which case it returns multiple records)
	 * or not (single record).
	 * 
	 * @param expTypeCode - Expense type code
	 * @param startDate -  start of date range
	 * @param endDate -  end of date range
	 * @return List of <code>ExpenseRates</code>
	 */	
	public List<ExpenseRates> findExpenseRatesForExpenseTypeWithinDateRange(String expTypeCode, Date startDate, Date endDate){
		return expenseLineItemDAO.findExpenseRatesForExpenseTypeWithinDateRange(expTypeCode, startDate, endDate);
	}
	
	public String findExpenseTypeCodeByFlags(String expenseTypeCode,
			String isOutOfState, String isOvernight, String isSelectCity) {
		return expenseTypeDao.findExpenseTypeCodeByFlags(expenseTypeCode,
				isOutOfState, isOvernight, isSelectCity);
	}
	
	/**
	 * Finds all the <code>expenseTypes</code> valid for the given date, along with the applicable
	 * standard reimbursement rate and an indicator to indicate whether it's mileage related.
	 * 
	 * @param date for lookup
	 * @return list of all expense types
	 */
	public List<ExpenseTypeDisplayBean> findAllExpenseTypesWithMileageIndicator(Date date, String isOutOfState, String isTravel, 
			String isOvernight, boolean isPDF, String Department, String Agency, String TKU){
		
		return expenseTypeDao.findAllExpenseTypesWithMileageIndicator(date,isOutOfState, isTravel, 
				isOvernight, isPDF, Department, Agency, TKU);
	}
	
	public List<ExpenseTypeDisplayBean> findAllExpenseTypesWithMileageIndicator(){
		return expenseTypeDao.findAllExpenseTypesWithMileageIndicator();
	}
	
	public List<ExpenseTypeDisplayBean> findAllExpenseTypesSuperUser(Date date){
		
		return expenseTypeDao.findAllExpenseTypesSuperUser(date);
	}
	
	public int isSelectCity(Date expenseDate, String strCity, String strState){
		return expenseTypeDao.isSelectCity(expenseDate, strCity, strState);
	}
	
	public String getCategoryDescription(String categoryCode){
		return expenseTypeDao.getCategoryDescription(categoryCode);
	}
	
	public int isSelectCityExpense(String expenseTypeCode){
		return expenseTypeDao.isSelectCityExpense(expenseTypeCode);
	}
	
	public boolean isExpenseSelectCityPairValidOnDate(Date expenseDate, String strCity, String strState,String expenseTypeCode){
		return expenseTypeDao.isExpenseSelectCityPairValidOnDate(expenseDate, strCity, strState, expenseTypeCode);
	}
	
	public int isMealTimeValid(String categoryCode, Date expenseDate, String startTime, String endTime, String department, String Agency, String TKU){
		return expenseLineItemDAO.isMealTimeValid(categoryCode, expenseDate, startTime, endTime, department, Agency, TKU);
	}
	
	public int isValidExpenseBargainUnit(int apptId, String expenseTypeCode, Date expenseToDate){
		return expenseLineItemDAO.isValidExpenseBargainUnit(apptId, expenseTypeCode, expenseToDate);
	}
	
	public String getInStateList(String department, String Agency, String TKU){
		return expenseLineItemDAO.getInStateList(department, Agency, TKU);
	}
		
	/**
	 * Finds all the states for which travel requests can be made. 
	 * PS: The returned map is never NULL, though may be empty if no authorized states found.
	 * 
	 * @return Map containing state code as key and state name as value.
	 */
	public List<Object> findTravelAuthorizedStates(){
		return expenseLineItemDAO.findTravelAuthorizedStates();
	}
	
	public int getMaxLineNo(ExpenseMasters expenseMaster){
		return expenseLineItemDAO.findMaxLineNo(expenseMaster);
	}
	
	public ExpenseTypes getExpenseTypes(String expenseTypeCode){
		return expenseLineItemDAO.findExpenseTypes(expenseTypeCode);
	}
	
	public List<ExpenseLocations> getLocations(){
		return expenseLineItemDAO.findLocations();
	}
	
	public List<String> findCities(String Department, String Agency){
		return expenseLineItemDAO.findCities(Department, Agency);
	}
	
	public ExpenseDetails getExpenseDetailByExpenseDetailsId(int expenseDtlsId){
		return expenseLineItemDAO.findExpenseDetailByExpenseDetailsId(expenseDtlsId);
	}

	public void setExpenseLineItemDAO(ExpenseLineItemDAO expenseLineItemDAO) {
		this.expenseLineItemDAO = expenseLineItemDAO;
	}

	public ExpenseLineItemDAO getExpenseLineItemDAO() {
		return expenseLineItemDAO;
	}
	
	public CommonDAO getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDAO commonDao) {
		this.commonDao = commonDao;
	}

	public ExpenseDAO getExpenseDAO() {
		return expenseDAO;
	}

	public void setExpenseDAO(ExpenseDAO expenseDAO) {
		this.expenseDAO = expenseDAO;
	}
	
}
