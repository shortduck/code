/*
 * ExpenseLineItemDAO.java
 * 
 * Date - January 23, 2009
 * 
 * @Author - SG
 */

package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.exception.ExpenseRateNotFoundException;
import gov.michigan.dit.timeexpense.exception.TimeAndExpenseException;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseLocations;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseRates;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypeRules;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypes;
import gov.michigan.dit.timeexpense.model.display.CodingBlockSummaryBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseSummaryDetailsBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class ExpenseLineItemDAO extends AbstractDAO{

	private static final Logger logger = Logger.getLogger(ExpenseLineItemDAO.class);

	public ExpenseLineItemDAO() {}

	public ExpenseLineItemDAO(EntityManager em) {
		super(em);
	}
	
	/**
	 * This method retrieves the expense line items. ExpenseMaster is provided as an input to the method. 
	 * @param expenseMasters
	 * @return List<ExpenseDetails>
	 */
	@SuppressWarnings("unchecked")
	public List<ExpenseDetails> findExpenseLineItems(ExpenseMasters expenseMasters) {
		
		if(logger.isDebugEnabled())
				logger.debug("Enter Method : findExpenseLineItems(int expenseMasterId) : return List<ExpenseDetails>");
		
		List<ExpenseDetails> expenseDetailsCollection = new ArrayList<ExpenseDetails>();
		String finderQuery = "Select ed from ExpenseDetails ed where ed.expmIdentifier = :expmIdentifier";
		expenseDetailsCollection = (List<ExpenseDetails>) entityManager
				.createQuery(finderQuery).setParameter("expmIdentifier",
						expenseMasters).getResultList();
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findExpenseLineItems(int expenseMasterId)");
		
		return expenseDetailsCollection;
	}
	
	/***
	 * This method finds the common miles given the source and the destination cities and states.
	 * @param srcCity
	 * @param srcState
	 * @param destCity
	 * @param destState
	 * @return double
	 */
	public double findCommonMiles(String srcCity, String srcState,
			String destCity, String destState, String department, String agency) {
		double returnValue = 0;
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findCommonMiles(String srcCity, String srcState,String destCity, String destState : return BigDecimal");
		
		String finderQuery = "SELECT F_GET_AGENCY_COMMON_MILEAGE(?1,?2,?3,?4,?5,?6) FROM DUAL";
			
		String srcC = srcCity==null? "" : srcCity.toUpperCase();
		String srcS = srcState==null? "" : srcState.toUpperCase();
		String destC = destCity==null? "" : destCity.toUpperCase();
		String destS = destState==null? "" : destState.toUpperCase();
		department = department==null? "" : department.toUpperCase();
		agency = agency==null? "" : agency.toUpperCase();
		
		Query query = entityManager.createNativeQuery(finderQuery).setParameter(1, srcC)
																	.setParameter(2, srcS)
																	.setParameter(3, destC)
																	.setParameter(4, destS)
																	.setParameter(5, department)
																	.setParameter(6, agency)
																	;
		List<BigDecimal> mileage = query.getResultList();

		if(mileage == null || mileage.get(0) == null || mileage.size() < 1)
			returnValue = 0;
		else
			returnValue = mileage.get(0).doubleValue();
		
		if(logger.isDebugEnabled()){
			logger.debug("Commom miles for "+ srcCity +", "+srcState + " and " + destCity + ", "+ destState + " : "+ returnValue);
			logger.debug("Exit Method : findCommonMiles(String srcCity, String srcState,String destCity, String destState");
		}
		
		return returnValue;
	}
	
	
	/**
	 * This method returns a list of ExpenseTypes and Rates given the expense date.
	 * @param expenseDate
	 * @return List<ExpenseSummaryBean>
	 */
	
	@SuppressWarnings("unchecked")
	public List<ExpenseTypes> findExpenseTypes(Date expenseDate) {

		if(logger.isDebugEnabled())
			logger.debug("Enter method : findExpenseTypesAndRate(Date expenseDate) : return List<ExpenseSummaryDetailsBean>");

		String finderQuery = "select et from ExpenseTypes et where :currDate between et.startDate and et.endDate order by et.displayOrder";
		List<ExpenseTypes> expTypesList = entityManager.createQuery(finderQuery)
		.setParameter("currDate", expenseDate)
		.setParameter("currDate", expenseDate).getResultList();
		
		if(logger.isDebugEnabled())			
			logger.debug("Exit method : findExpenseTypesAndRate(Date expenseDate)");
		
		return expTypesList;
	}

	/**
	 * This method retrieves the Expense summary details by category/payType given the expense Master Id. 
	 * @param expenseMasterId
	 * @return List<ExpenseSummaryDetailsBean>
	 */
	@SuppressWarnings("unchecked")
	public List<ExpenseSummaryDetailsBean> findExpenseSummaryDetails(
			int expenseMasterId) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findExpenseSummaryDetails(int expenseMasterId) : return List<ExpenseSummaryDetailsBean>");
		
		String finderQuery = "SELECT PT.DESCRIPTION,SUM(ED.DOLLAR_AMOUNT) AMOUNT FROM EXPENSE_DETAILS ED, EXPENSE_TYPES ET, PAY_TYPES PT "
				+ "WHERE ED.EXPM_IDENTIFIER = ?1 AND ED.EXP_TYPE_CODE = ET.EXP_TYPE_CODE AND "
				+ "ET.PYTP_IDENTIFIER = PT.PYTP_IDENTIFIER AND SYSDATE BETWEEN PT.START_DATE AND PT.END_DATE GROUP BY PT.DESCRIPTION";

		if(logger.isInfoEnabled())
			logger.info("Enter JPA Operation : Performing JPA Native query operation");

		List<ExpenseSummaryDetailsBean> expSummaryList = entityManager
				.createNativeQuery(finderQuery, ExpenseSummaryDetailsBean.class)
				.setParameter(1, expenseMasterId).getResultList();

		
		logger.info("Exit JPA Operation : JPA Native query operation completed");
		logger.debug("Exit method : findExpenseSummaryDetails(int expenseMasterId)");
		return expSummaryList;
	}

	/**
	 * This method retrieves the locations.
	 * @return List<ExpenseLocations>
	 */
	@SuppressWarnings("unchecked")
	public List<ExpenseLocations> findLocations() {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findLocations : return List<ExpenseLocations>");
		
		String finderQuery = "SELECT e FROM ExpenseLocations e";
		List<ExpenseLocations> locationList = entityManager.createQuery(finderQuery).getResultList();
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findLocations");
		return locationList;
	}

	/**
	 * Finds all the available city name.
	 * 
	 * @return A list containing city names. 
	 */
	public List<String> findCities(String Department, String Agency) {
		// String query =
		// "select t.city from (SELECT distinct(city), display_order FROM Expense_Locations order by display_order, city) t";

		String strTestQuery = "SELECT COUNT(city) FROM ("
				+ "SELECT DISTINCT city, display_order"
				+ "  FROM expense_locations "
				+ " WHERE department = ?1 AND Agency = ?2)";

		Query query = entityManager.createNativeQuery(strTestQuery);
		query.setParameter(1, Department);
		query.setParameter(2, Agency);

		int iResult = Integer.parseInt(query.getSingleResult().toString());

		// Check if the Agency has any cities, else switch to Agency = "AL"
		if (iResult == 0) {
			Agency = "AL";
		}

		String strQuery = "Select DISTINCT t.city from  "
				+ "  (SELECT DISTINCT city, display_order "
				+ "          FROM expense_locations "
				+ "         WHERE department = 'AL' AND Agency = 'AL' "
				+ "        UNION "
				+ "        SELECT DISTINCT city, display_order "
				+ "          FROM expense_locations "
				+ "         WHERE department = ?1 AND Agency = ?2 "
				+ "        ORDER BY display_order, city) t order by t.city";

		query = entityManager.createNativeQuery(strQuery);
		query.setParameter(1, Department);
		query.setParameter(2, Agency);

		return query.getResultList();
	}
	
	/**
	 * 
	 * This method retrieves the ExpenseSummary by coding block given the expenseMasterId
	 * @param expenseMasterId
	 * @return List<CodingBlockSummaryBean>
	 */
	public List<CodingBlockSummaryBean> findExpenseSummaryByCodingBlock(
			int expenseMasterId) {

		String finderQuery = "SELECT EC.APPROPRIATION_YEAR appropriationYear, EC.INDEX_CODE indexCode, EC.PCA pca, EC.GRANT_NUMBER grantNumber, "
				+ "EC.GRANT_PHASE grantPhase, EC.AGENCY_CODE_1 agencyCode1, EC.AGENCY_CODE_2 agencyCode2,EC.AGENCY_CODE_3 agencyCode3, "
				+ "EC.PROJECT_NUMBER projectNumber, EC.PROJECT_PHASE projectPhase, EC.MULTIPURPOSE_CODE multipurposeCode,"
				+ "EC.STANDARD_IND standardInd, SUM(EC.DOLLAR_AMOUNT) dollarAmount"
				+ " FROM EXPENSE_DETAIL_CODING_BLOCKS EC, EXPENSE_DETAILS ED "
				+ "WHERE ED.EXPM_IDENTIFIER = ?1 AND "
				+ "EC.EXPD_IDENTIFIER =ED.EXPD_IDENTIFIER GROUP BY EC.APPROPRIATION_YEAR, EC.INDEX_CODE, EC.PCA, EC.GRANT_NUMBER, "
				+ "EC.GRANT_PHASE, EC.AGENCY_CODE_1, EC.AGENCY_CODE_2, EC.AGENCY_CODE_3, EC.PROJECT_NUMBER, EC.PROJECT_PHASE, "
				+ "EC.MULTIPURPOSE_CODE, EC.STANDARD_IND ORDER BY  EC.STANDARD_IND DESC";

		List<CodingBlockSummaryBean> expCBList = entityManager
				.createNativeQuery(finderQuery, CodingBlockSummaryBean.class)
				.setParameter(1, expenseMasterId).getResultList();
	
		return expCBList;
	}
	
	/**
	 * This method retrieves the maximum line number.
	 * @param expenseMaster
	 * @return int
	 */

	public int findMaxLineNo(ExpenseMasters expenseMaster){
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findMaxLineNo(ExpenseMasters expenseMaster)");
		
		
		int maxLineNo =  (Integer) entityManager.createQuery("select max(line.lineItem) from ExpenseDetails line where line.expmIdentifier = :expmIdentifier")
		.setParameter("expmIdentifier", expenseMaster).getSingleResult();
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findMaxLineNo(ExpenseMasters expenseMaster)");
		return maxLineNo;
	}

	@SuppressWarnings("all")
	public double findExpenseRateByExpenseType(String expenseTypeCode,Date expenseDate) throws ExpenseRateNotFoundException{
		
		String finderQuery = "select er.rateAmt from ExpenseRates er where :expenseDate between er.startDate " +
				"and er.endDate and er.expTypeCode=:expenseTypeCode";
		List<Double> expenseRate = entityManager.createQuery(finderQuery).setParameter("expenseDate", expenseDate).setParameter("expenseTypeCode", expenseTypeCode).getResultList();
		
		if(expenseRate.isEmpty()){
			throw new ExpenseRateNotFoundException("Rate not found");
		}
		
		return expenseRate.get(0).doubleValue();
	}
	
	/**
	 * Finds the <code>ExpenseRates</code> for all the provided expense type codes.
	 * 
	 * @param expTypeCodes - Expense type codes
	 * @param effectiveDate -  historized date
	 * @return List of <code>ExpenseRates</code>
	 */
	public List<ExpenseRates> findExpenseRatesForExpenseTypes(List<String> expTypeCodes, Date effectiveDate){
		
		String query = "SELECT t0 from ExpenseRates t0 " +
						"where t0.expTypeCode IN (?1) and ?2 between t0.startDate and t0.endDate";
		
		List<ExpenseRates>  rates = entityManager.createQuery(query).setParameter("1", expTypeCodes)
										.setParameter("2", effectiveDate).getResultList();
										
		
		return rates;
	}
	
	/**
	 * Finds <code>ExpenseTypes</code> with the given code and valid for the given date.
	 * 
	 * @param expense type code
	 * @param date on which it's valid 
	 * @return list of <code>ExpenseTypes</code>
	 */
	public List<ExpenseTypes> findExpenseType(String expTypeCode, Date effectiveDate){
		return entityManager.createQuery("SELECT et from ExpenseTypes et where et.expTypeCode = :expTypeCode" +
				" and :effectiveDate between et.startDate and et.endDate")
				.setParameter("expTypeCode", expTypeCode)
				.setParameter("effectiveDate", effectiveDate)
				.getResultList();
	}
	
	
	/**
	 * Finds all the <code>ExpenseRates</code>, within the date range provided,
	 * for the given expense type.
	 * 
	 * @param expTypeCode - Expense type code
	 * @param startDate -  start of date range
	 * @param endDate -  end of date range
	 * @return List of <code>ExpenseRates</code>
	 */
	public List<ExpenseRates> findExpenseRatesForExpenseTypeWithinDateRange(String expTypeCode, Date startDate, Date endDate){
		
		String query = "SELECT t0 from ExpenseRates t0 where t0.expTypeCode = :expTypeCode" +
						" and t0.startDate <= :endDate and t0.endDate>= :startDate";
		
		List<ExpenseRates>  rates = entityManager.createQuery(query)
										.setParameter("expTypeCode", expTypeCode)
										.setParameter("startDate", startDate)
										.setParameter("endDate", endDate)
										.getResultList();
		
		return rates;
	}
	
	/** 
	 * Method to retrieve the ExpenseTypes given the expenseTypeCode
	 * @param ruleId
	 * @return ExpenseTypes
	 */
	public ExpenseTypes findExpenseTypes(String expenseTypeCode){
		ExpenseTypes expenseTypes = entityManager.find(ExpenseTypes.class, expenseTypeCode);
		return expenseTypes;
	}
	
	/** 
	 * Method to retrieve the ExpenseDetails by expenseDetailsId.
	 * @param ruleId
	 * @return ExpenseDetails
	 */
	public ExpenseDetails findExpenseDetailByExpenseDetailsId(int expenseDtlsId){
		return entityManager.find(ExpenseDetails.class, expenseDtlsId);
	}

	public ExpenseTypeRules findExpenseTypeByCodeAndRuleId(ExpenseTypes expType,int ruleIdentifier) throws TimeAndExpenseException{
		String finderQuery = "Select etr from ExpenseTypeRules etr where etr.expTypeCode = :expTypeCode and etr.ruleIdentifier = :ruleIdentifier";
		
		ExpenseTypeRules expenseTypeRulesList = null;
		try{
		expenseTypeRulesList = (ExpenseTypeRules)entityManager.createQuery(finderQuery)
																	.setParameter("expTypeCode", expType)
																	.setParameter("ruleIdentifier", ruleIdentifier)
																	.getSingleResult();
		}catch (NoResultException nre) {
			throw new TimeAndExpenseException("ETR_INVALID", "Unique ETR not found");
		}catch (NonUniqueResultException nure) {
			throw new TimeAndExpenseException("ETR_INVALID", "Unique ETR not found");
		}
		
		return expenseTypeRulesList;
	}
	
	/**
	 * Finds all the states for which the travel requests can be made.
	 */
	public List<Object> findTravelAuthorizedStates(){
		return entityManager.createNativeQuery("select trim(state_code), trim(state_name) from STATES order by display_order asc").getResultList();
	}
	
	/*
	 * Method to validate Meal times
	 * return 1: Valid Meal Time
	 * 0: Invalid Meal Time
	 * 
	 * */
	public int isMealTimeValid(String categoryCode, Date expenseDate, String startTime, String endTime, String department, String Agency, String TKU){
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : isMealTimeValid Parameters: categoryCode:" + categoryCode + "expenseDate:" + expenseDate +  "startTime:" +startTime +  "endTime:" + endTime+ "department:" + department +  "Agency:" +Agency + "TKU:" +TKU);

		String finderQuery = "SELECT F_TKU_MEAL_TIME ( ?1, ?2, ?3, ?4, ?5, ?6, ?7) FROM DUAL";
		Query query = entityManager.createNativeQuery(finderQuery);

		query.setParameter(1, categoryCode);
		query.setParameter(2, expenseDate);
		query.setParameter(3, startTime);
		query.setParameter(4, endTime);
		query.setParameter(5, department);
		query.setParameter(6, Agency);
		query.setParameter(7, TKU);
		
		int returnCode =  Integer.parseInt(query.getSingleResult().toString());
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : isMealTimeValid:Return Value:" + returnCode);

		return returnCode;

	}
	
	public int isValidExpenseBargainUnit(int apptId, String expenseTypeCode, Date expenseToDate){
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : isValidExpenseBargainUnit Parameters: apptId:" + apptId + "expenseTypeCode:" + expenseTypeCode);
		
		String finderQuery = "SELECT count(ah.bargaining_unit)" +
		"  FROM appointment_histories ah, bargaining_unit_expense_types buet" +
		" WHERE     AH.BARGAINING_UNIT = buet.BARGAINING_UNIT" +
		"       AND BUET.EXP_TYPE_CODE IN (?1)" +
		"		AND ?3 between TRIM(buet.START_DATE) and TRIM(buet.END_DATE)" +	
		"       AND ah.end_date = (SELECT MAX (end_date)" +
		"                            FROM appointment_histories ah" +
		"                           WHERE AH.APPT_IDENTIFIER = ?2)" +
		"       AND AH.APPT_IDENTIFIER = ?2"  ;

		Query query = entityManager.createNativeQuery(finderQuery);
		
		query.setParameter(1, expenseTypeCode);
		query.setParameter(2, apptId);
		query.setParameter(3, expenseToDate);
		
		int returnValue =  Integer.parseInt((query.getSingleResult().toString()));
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : isValidExpenseBargainUnit");
		
		return returnValue;
	}
	
	public String getInStateList(String department, String Agency, String TKU){
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getInStateList Parameters: department:" + department +  "Agency:" +Agency + "TKU:" +TKU);

		String finderQuery = "SELECT F_GET_TKU_IN_STATES ( ?1, ?2, ?3 ) FROM DUAL";
		Query query = entityManager.createNativeQuery(finderQuery);
		
		query.setParameter(1, department);
		query.setParameter(2, Agency);
		query.setParameter(3, TKU);
				
		String states = (String)query.getSingleResult();;
		
		if (states == null)	
			states = "";
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getInStateList: Return Value:" + states);

		return states;
		
	}
	
	public String getExpenseCategory(String expenseTypeCode){
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpenseCategory Parameters: expenseTypeCode:" + expenseTypeCode);

		String finderQuery = "SELECT category_code"+
							"	FROM expense_types"+
							"	WHERE exp_type_code = ?1";
		
		Query query = entityManager.createNativeQuery(finderQuery);
		
		query.setParameter(1, expenseTypeCode);
				
		String categoryCode = (String)query.getSingleResult();;
		
		if (categoryCode == null)	
			categoryCode = "";
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpenseCategory: Return Value:" + categoryCode);

		return categoryCode;
	}	
}
