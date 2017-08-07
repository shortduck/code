package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.display.ExpenseTypeDisplayBean;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Class to handle persistence related functionality for <code>ExpenseTypes</code>
 * 
 * @author chaudharym
 *
 */
public class ExpenseTypeDAO extends AbstractDAO{

	// This query assumes that every expense type has rule '1006' specified
	// for it, with a value 'Y' if mileage related or 'N' if non mileage related. 
	private final String ALL_EXPENSE_TYPE_SUPERUSER = 
		"select et.EXP_TYPE_CODE expTypeCode, et.DESCRIPTION description," +
		" et.DISPLAY_ORDER displayOrder, etr.value mileageIndicator" +
		" from EXPENSE_TYPES et, EXPENSE_TYPE_RULES etr " +
		" where ?1 between et.START_DATE and et.END_DATE" +
		" and et.DISPLAY_ORDER > 0" +
		" and etr.EXP_TYPE_CODE = et.EXP_TYPE_CODE and etr.RULE_IDENTIFIER = 1006" +
		" order by et.DISPLAY_ORDER, et.EXP_TYPE_CODE";	
	
	private final String ALL_EXPENSE_TYPES = 
		"select et.EXP_TYPE_CODE expTypeCode, et.DESCRIPTION description," +
		" et.DISPLAY_ORDER displayOrder, etr.value mileageIndicator " +
		" ,(SELECT etr.VALUE mileageIndicator " +
	    "      FROM expense_type_rules etr " +
	    "     WHERE     ETR.EXP_TYPE_CODE = et.EXP_TYPE_CODE " +
	    "           AND etr.RULE_IDENTIFIER = 1012) " +
	    "      mealIndicator " +
		" from EXPENSE_TYPES et, EXPENSE_TYPE_RULES etr , tkuopt_expense_types TET" +
		" where ?1 between et.START_DATE and et.END_DATE" +
		" and et.DISPLAY_ORDER > 0" +
		" and etr.EXP_TYPE_CODE = et.EXP_TYPE_CODE and etr.RULE_IDENTIFIER = 1006" +
		" and TET.EXP_TYPE_CODE = et.EXP_TYPE_CODE" +
		" AND TET.DEPARTMENT = ?2 " +
		" AND TET.AGENCY = ?3 " +
		" AND TET.TKU = ?4 ";
	
	
	private final String UNION  = " UNION ";
	
	private final String MEAL_EXPENSE_TYPE =
				"		SELECT DISTINCT ET.Category_code expTypeCode, " +
				"        EC.DESCRIPTION description, " +
				"        MIN (et.DISPLAY_ORDER) displayOrder, "+
				"        'N' mileageIndicator, "+
				"        'Y' mealIndicator "+
				"FROM EXPENSE_TYPES ET, EXPENSE_CATEGORIES EC, expense_type_rules etr, tkuopt_expense_types TET "+
				"WHERE     ET.Category_code = EC.CATEGORY_CODE "+
				"AND ET.EXP_TYPE_CODE = ETR.EXP_TYPE_CODE "+
				"AND ET.EXP_TYPE_CODE = TET.EXP_TYPE_CODE "+
				"AND et.DISPLAY_ORDER > 0 "+
				"AND TET.DEPARTMENT = ?2 "+
				"AND TET.AGENCY = ?3 "+
				"AND TET.TKU = ?4 "+
				"AND ?1 BETWEEN et.START_DATE AND et.END_DATE "+
				"and ET.EXP_TYPE_CODE  IN  (SELECT et.EXP_TYPE_CODE expTypeCode "+
				"         FROM EXPENSE_TYPES et, expense_type_rules etr    "+
				"        WHERE ET.EXP_TYPE_CODE = ETR.EXP_TYPE_CODE         "+              
				"              AND ETR.RULE_IDENTIFIER = 1012"+
				"              AND ETR.VALUE = 'Y'"+
				"              AND et.DISPLAY_ORDER > 0"+                      
				"       INTERSECT"+
				"       SELECT et.EXP_TYPE_CODE expTypeCode"+
				"         FROM    EXPENSE_TYPES et, expense_type_rules etr"+
				"        WHERE ET.EXP_TYPE_CODE = ETR.EXP_TYPE_CODE        "+               
				"              AND etr.RULE_IDENTIFIER = 1006"+
				"              AND et.DISPLAY_ORDER > 0       "+               
				"              ) "+
				"       GROUP BY ET.Category_code, EC.DESCRIPTION";
	
	
	private final String ALL_EXPENSE_TYPE_EXCEPT_MEAL_INCIDENTAL_PDF =
				"					SELECT DISTINCT"+
				"			        et.EXP_TYPE_CODE expTypeCode,"+
				"			        et.DESCRIPTION description,"+
				"			        et.DISPLAY_ORDER displayOrder,"+
				"			        (SELECT etr.VALUE mileageIndicator"+
				"			           FROM expense_type_rules etr"+
				"			          WHERE     ETR.EXP_TYPE_CODE = et.EXP_TYPE_CODE"+
				"			                AND etr.RULE_IDENTIFIER = 1006)"+
				"			           mileageIndicator,"+
				"        			   'N' mealIndicator "+
				"			   FROM    EXPENSE_TYPES et, expense_type_rules etr, tkuopt_expense_types TET"+
				"			  WHERE ET.EXP_TYPE_CODE = ETR.EXP_TYPE_CODE"+
				"			  AND ET.EXP_TYPE_CODE = TET.EXP_TYPE_CODE "+
				"			  AND TET.DEPARTMENT = ?2"+
				"			  AND TET.AGENCY = ?3 "+
				"			  AND TET.TKU = ?4"+
				"			  AND ?1 BETWEEN et.START_DATE AND et.END_DATE"+
				"			  AND et.DISPLAY_ORDER > 0"+
				"			  AND et.EXP_TYPE_CODE IN"+
				"			           (SELECT et.EXP_TYPE_CODE expTypeCode"+
				"			              FROM EXPENSE_TYPES et, expense_type_rules etr"+    
				"			             WHERE ET.EXP_TYPE_CODE = ETR.EXP_TYPE_CODE"+
				"			                   AND Category_code IN (?6, ?7, 'O')"+
				"			                   AND ETR.RULE_IDENTIFIER = 1010"+
				"			                   AND ETR.VALUE = ?5"+
				"			            INTERSECT"+
				"			            SELECT et.EXP_TYPE_CODE expTypeCode"+
				"			              FROM    EXPENSE_TYPES et, expense_type_rules etr"+
				"			             WHERE ET.EXP_TYPE_CODE = ETR.EXP_TYPE_CODE"+
				"			                   AND Category_code IN (?6, ?7, 'O')"+
				"			                   AND etr.RULE_IDENTIFIER = 1006 "+
				"			)";
  
	private final String INCIDENTAL_EXPENSE_TYPE =
	"	SELECT et.EXP_TYPE_CODE expTypeCode,"+
	"	       et.DESCRIPTION description,"+
	"	       et.DISPLAY_ORDER displayOrder,"+
	"	       'N' mileageIndicator,"+
	"	       'N' mealIndicator"+
	"	  FROM EXPENSE_TYPES et, tkuopt_expense_types TET"+
	"	 WHERE     ET.EXP_TYPE_CODE = TET.EXP_TYPE_CODE"+
	"	       AND TET.DEPARTMENT = ?2"+
	"	       AND TET.AGENCY = ?3"+
	"	       AND TET.TKU = ?4"+
	"	       AND Category_code = 'I'"+
	"	       AND et.DISPLAY_ORDER > 0"+
	"	       AND ?1 BETWEEN et.START_DATE AND et.END_DATE"+
	"	       AND et.EXP_TYPE_CODE IN"+
	"	              (SELECT et.EXP_TYPE_CODE expTypeCode"+
	"	                 FROM EXPENSE_TYPES et, expense_type_rules etr"+
	"	                WHERE     ET.EXP_TYPE_CODE = ETR.EXP_TYPE_CODE"+
	"	                      AND ETR.RULE_IDENTIFIER = 1010"+
	"	                      AND ETR.VALUE = ?5"+
	"	               INTERSECT"+
	"	               SELECT et.EXP_TYPE_CODE expTypeCode"+
	"	                 FROM EXPENSE_TYPES et, expense_type_rules etr"+
	"	                WHERE     ET.EXP_TYPE_CODE = ETR.EXP_TYPE_CODE"+
	"	                      AND etr.RULE_IDENTIFIER = 1006)";
		       
	private final String PDF_EXPENSE_TYPE = "SELECT et.EXP_TYPE_CODE expTypeCode," +
		"	       et.DESCRIPTION description," +
		"	       et.DISPLAY_ORDER displayOrder," +
		"	       'N' mileageIndicator," +
		"        'N' mealIndicator "+
		"	  FROM EXPENSE_TYPES et, tkuopt_expense_types TET, expense_type_rules etr" +
		"	 WHERE     ET.EXP_TYPE_CODE = ETR.EXP_TYPE_CODE" +
		"	       AND ET.EXP_TYPE_CODE = TET.EXP_TYPE_CODE" +
		"	       AND etr.RULE_IDENTIFIER = 1006" +
		"	       AND TET.DEPARTMENT = ?2" +
		"	       AND TET.AGENCY = ?3" +
		"	       AND TET.TKU = ?4" +
		"	       AND Category_code = 'P'" +
		"	       AND et.DISPLAY_ORDER > 0" +
		"	       AND ?1 BETWEEN et.START_DATE AND et.END_DATE";

	private final String ORDER_BY =
	" ORDER BY displayOrder, expTypeCode ";


    public ExpenseTypeDAO(){}
    
    public ExpenseTypeDAO(EntityManager em){
    	super(em);
    }
	

	/**
	 * Finds all <code>ExpenseTypes</code> with standard rate and an indicator
	 * to whether it's mileage related expense type for superuser.
	 * 
	 * @param Date for which the search needs to be performed
	 * @return list of ExpenseTypeDisplayBean
	 */
	public List<ExpenseTypeDisplayBean> findAllExpenseTypesSuperUser(Date date){
		
		Query query = null;
		List<ExpenseTypeDisplayBean> listExpenseTypes;
		
		query = entityManager.createNativeQuery(
				ALL_EXPENSE_TYPE_SUPERUSER,
				ExpenseTypeDisplayBean.class);

		query.setParameter(1, date);
		
		listExpenseTypes = query.getResultList();
		
		return listExpenseTypes;
	}
	
	/**
	 * Finds all <code>ExpenseTypes</code> with standard rate and an indicator
	 * to whether it's mileage related expense type.
	 * 
	 */
	public List<ExpenseTypeDisplayBean> findAllExpenseTypesWithMileageIndicator() {

		String querySelect = "SELECT et.EXP_TYPE_CODE expTypeCode, et.DESCRIPTION description, DISPLAY_ORDER displayOrder"+
							 " FROM EXPENSE_TYPES et, EXPENSE_TYPE_RULES etr" + 
							 " WHERE     SYSDATE BETWEEN et.START_DATE AND et.END_DATE" + 
							 "      AND et.DISPLAY_ORDER > 0" +
							 "      AND etr.EXP_TYPE_CODE = et.EXP_TYPE_CODE" +
							 "      AND etr.RULE_IDENTIFIER = 1006 " + ORDER_BY;
		
		Query query = entityManager.createNativeQuery(querySelect, ExpenseTypeDisplayBean.class);
		List<ExpenseTypeDisplayBean> expenseTypes = query.getResultList();

		return expenseTypes;

	}

    
	/**
	 * Finds all <code>ExpenseTypes</code> with standard rate and an indicator
	 * to whether it's mileage related expense type.
	 * 
	 * @param Date for which the search needs to be performed
	 * @return list of ExpenseTypeDisplayBean
	 */
	public List<ExpenseTypeDisplayBean> findAllExpenseTypesWithMileageIndicator(Date date, String isOutOfState, String isTravel, 
			String isOvernight, boolean isPDF, String Department,
			String Agency, String TKU) {

		Query query = null;
		StringBuffer ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY = new StringBuffer();
		List<ExpenseTypeDisplayBean> listExpenseTypes;
		String travelOrNonTravel = "";
		
		//Determine TKU.
		//Should TKU be Specific or 'AL'
		
		query = entityManager.createNativeQuery(
				ALL_EXPENSE_TYPES.toString(),
				ExpenseTypeDisplayBean.class);

		query.setParameter(1, date);
		query.setParameter(2, Department);
		query.setParameter(3, Agency);
		query.setParameter(4, TKU);
		
		listExpenseTypes = query.getResultList();

		if (listExpenseTypes == null || listExpenseTypes.size() == 0) {
			TKU =  "AL";			
		}
		
		if (isPDF) {

			ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY.append(PDF_EXPENSE_TYPE);
			ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY.append(ORDER_BY);

			query = entityManager.createNativeQuery(
					ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY.toString(),
					ExpenseTypeDisplayBean.class);
			
			query.setParameter(1, date);
			query.setParameter(2, Department);
			query.setParameter(3, Agency);
			query.setParameter(4, TKU);
			listExpenseTypes = query.getResultList();
		}

		else {
			
			//Some category that is not present
			String driversReimbursement = "XX";
			
			if (isTravel.equalsIgnoreCase("Y"))
			{
				ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY.append(MEAL_EXPENSE_TYPE);
				travelOrNonTravel = "T";
				driversReimbursement = "R";
			}
			else
				travelOrNonTravel = "N";

			if (ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY.length() > 0) {
				ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY.append(UNION);
			}

			ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY
					.append(ALL_EXPENSE_TYPE_EXCEPT_MEAL_INCIDENTAL_PDF);

			if (isOvernight != null && isOvernight.equalsIgnoreCase("Y")
					&& isTravel.equalsIgnoreCase("Y")) {
				if (ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY.length() > 0) {
					ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY.append(UNION);
				}

				ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY
						.append(INCIDENTAL_EXPENSE_TYPE);
			}

			ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY.append(ORDER_BY);

			query = entityManager.createNativeQuery(
					ALL_EXPENSE_TYPE_RATE_MILEAGE_QUERY.toString(),
					ExpenseTypeDisplayBean.class);

			query.setParameter(1, date);
			query.setParameter(2, Department);
			query.setParameter(3, Agency);
			query.setParameter(4, TKU);
			query.setParameter(5, isOutOfState.toUpperCase());			
			query.setParameter(6, travelOrNonTravel);
			query.setParameter(7, driversReimbursement);
			
			listExpenseTypes = query.getResultList();

		}

		return listExpenseTypes;

	}
	
	public String findExpenseTypeCodeByFlags(String expenseTypeCode,
			String isOutOfState, String isOvernight, String isSelectCity) {
				
		final String INTERSECT = " INTERSECT ";
		
		//Mileage
		String queryMileage = "SELECT et.EXP_TYPE_CODE" +
		"  FROM EXPENSE_TYPES et, expense_type_rules etr" +
		" WHERE     et.EXP_TYPE_CODE = etr.EXP_TYPE_CODE" +
		"       AND etr.RULE_IDENTIFIER = 1006" +		
		"       AND et.category_code = ?1";
						
		//1010 Out of State
		String queryOutOfState = "SELECT et.EXP_TYPE_CODE" +
		"  FROM EXPENSE_TYPES et, expense_type_rules etr" +
		" WHERE     et.EXP_TYPE_CODE = etr.EXP_TYPE_CODE" +
		"       AND RULE_IDENTIFIER = 1010" +
		"       AND ETR.VALUE = ?2" +
		"       AND et.category_code = ?1";
		
		//1009: Overnight
		String queryOvernight = "SELECT et.EXP_TYPE_CODE" +
		"  FROM EXPENSE_TYPES et, expense_type_rules etr" +
		" WHERE     et.EXP_TYPE_CODE = etr.EXP_TYPE_CODE" +
		"       AND etr.RULE_IDENTIFIER = 1009" +
		"       AND ETR.VALUE = ?3" +
		"       AND et.category_code = ?1";

		
		//1011 Select City		
		String querySelectCity = "SELECT et.EXP_TYPE_CODE" +
		"  FROM EXPENSE_TYPES et, expense_type_rules etr" +
		" WHERE     et.EXP_TYPE_CODE = etr.EXP_TYPE_CODE" +
		"       AND RULE_IDENTIFIER = 1011" +
		"       AND ETR.VALUE = ?4" +
		"       AND et.category_code = ?1";
		

		Query query = entityManager.createNativeQuery(queryMileage + INTERSECT + queryOutOfState + INTERSECT + queryOvernight + INTERSECT + querySelectCity);
		query.setParameter(1, expenseTypeCode);
		query.setParameter(2, isOutOfState);
		query.setParameter(3, isOvernight);		
		query.setParameter(4, isSelectCity);

		List<String> expenseTypeCodes = query.getResultList();

		return expenseTypeCodes.get(0);

	}
	
	public int isSelectCity(Date expenseDate, String strCity, String strState){
		
		String querySelect = "SELECT EL.ELOC_IDENTIFIER" +
		"  FROM expense_locations EL, EXPENSE_SELECT_CITY_DATES ESCD" +
		" WHERE     EL.ELOC_IDENTIFIER = ESCD.ELOC_IDENTIFIER" +
		"       AND ?1 BETWEEN ESCD.START_DATE AND ESCD.END_DATE" +
		"       AND EL.CITY = ?2" +
		"       AND EL.ST_PROV = ?3";
		

		Query query = entityManager.createNativeQuery(querySelect);
		
		query.setParameter(1, expenseDate);
		query.setParameter(2, strCity);
		query.setParameter(3, strState);		
		
		List<String> expenseLocation = query.getResultList();
		
		return expenseLocation.size();

	}
	
	public String getCategoryDescription(String categoryCode){
		
		String querySelect = "SELECT ec.Description" +
							 "	  FROM expense_categories EC" +
							 "		 WHERE ec.category_code = ?1";

		Query query = entityManager.createNativeQuery(querySelect);
		
		query.setParameter(1, categoryCode);
		
		String categoryDescription = query.getSingleResult().toString();
		
		return categoryDescription;
	}
	
	public int isSelectCityExpense(String expenseTypeCode){
		String querySelect = "SELECT etr.EXP_TYPE_CODE expTypeCode " +
							 " FROM expense_type_rules etr " +
							 " WHERE     ETR.EXP_TYPE_CODE = ?1 " +
							 "      AND ETR.RULE_IDENTIFIER = 1011 " +
							 "      AND ETR.VALUE = 'Y'";

		Query query = entityManager.createNativeQuery(querySelect);
		
		query.setParameter(1, expenseTypeCode);
		
		List<String> expenseLocation = query.getResultList();
		
		return expenseLocation.size();

	}
	
	
	
	/*
	 * Method to determine if the Selected expense is a select city expense but the dates/location for which the expense 
	 * was selected is not a select city location for that date.
	 * This is only possible is the even that we have recurring (multiple) expense dates.  
	 * */
	public boolean isExpenseSelectCityPairValidOnDate(Date expenseDate,
			String strCity, String strState, String expenseTypeCode) {
		
		if (isSelectCityExpense(expenseTypeCode) > 0
				&& isSelectCity(expenseDate, strCity, strState) < 1)
			return false;
		else
			return true;
	}
	
	/**
	 * Finds if the <code>ExpenseType</code> is a premium mileage type. Premium Mileage rules is 1013.
	 * 
	 * @param expenseTypeCode: Expense type code
	 * @return boolean
	 */
	public boolean isExpensePremiumMileage(String expenseTypeCode) {
		String querySelect = "SELECT count(ETR.EXP_TYPE_CODE)"
				+ " FROM expense_type_rules etr"
				+ " WHERE     ETR.EXP_TYPE_CODE = ?1"
				+ "      AND ETR.RULE_IDENTIFIER = 1013"
				+ "      AND ETR.VALUE = 'Y'";

		Query query = entityManager.createNativeQuery(querySelect);

		query.setParameter(1, expenseTypeCode);
		int expenseCount = Integer.parseInt(query.getSingleResult().toString());

		return (expenseCount > 0 ? true : false);

	}
	
}
