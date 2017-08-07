/*
 *
 * ExpenseDAO.java
 * Date - January 12, 2009
 * 
 * @Author - SG
 */

package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.core.ExpenseActions;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseHistory;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.OutOfStateTravel;
import gov.michigan.dit.timeexpense.model.core.StateAuthCodes;
import gov.michigan.dit.timeexpense.model.core.VExpApprovalList;
import gov.michigan.dit.timeexpense.model.core.VExpensesList;
import gov.michigan.dit.timeexpense.model.display.ExpenseListBean;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.model.core.UserSubject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class ExpenseDAO extends AbstractDAO{
	
	private static final Logger logger = Logger.getLogger(ExpenseDAO.class);
	// ZH, 06/07/2010 - Added call to function for Paid Date per defect # 35
	private final static String EXPENSES_BY_APPT_ID_SELECT_CLAUSE = "SELECT EVENTS.APPT_IDENTIFIER apptIdentifier," +
			"appt.EMP_IDENTIFIER empIdentifier,EVENTS.EXPEV_IDENTIFIER expevIdentifier," +
			"em.EXPM_IDENTIFIER expmIdentifier,em.EXP_DATE_FROM expDateFrom, em.EXP_DATE_TO expDateTo,"+
			"em.orig_paid_date origPaidDate, em.NATURE_OF_BUSINESS natureOfBusiness,em.TRAVEL_IND travelInd,em.OUT_OF_STATE_IND outOfStateInd," +
			"em.REVISION_NUMBER revisionNumber,em.ADJ_IDENTIFIER adjIdentifier,"
			+"action.ACTION_CODE actionCode, f_paid_date(em.PAID_PP_END_DATE) paidDate, PDF_IND pdfInd FROM " 
			+"APPOINTMENTS appt,EXPENSE_EVENTS EVENTS,"
			+"EXPENSE_MASTERS em,EXPENSE_ACTIONS action,EMPLOYEES emp,appointment_histories ah ";
	
	private final static String EXPENSES_BY_APPT_ID_SELECT_CLAUSE_2 = "SELECT EVENTS.APPT_IDENTIFIER apptIdentifier," +
	"appt.EMP_IDENTIFIER empIdentifier,EVENTS.EXPEV_IDENTIFIER expevIdentifier," +
	"em.EXPM_IDENTIFIER expmIdentifier,em.EXP_DATE_FROM expDateFrom, em.EXP_DATE_TO expDateTo, em.orig_paid_date origPaidDate,"+
	"em.NATURE_OF_BUSINESS natureOfBusiness,em.TRAVEL_IND travelInd,em.OUT_OF_STATE_IND outOfStateInd," +
	"em.REVISION_NUMBER revisionNumber,em.ADJ_IDENTIFIER adjIdentifier,'', f_paid_date(em.PAID_PP_END_DATE) paidDate, PDF_IND  pdfInd FROM "
	+"APPOINTMENTS appt,EXPENSE_EVENTS EVENTS,"
	+"EXPENSE_MASTERS em,EMPLOYEES emp,appointment_histories ah ";
		
	private final static String EXPENSES_BY_APPT_ID_WHERE_CLAUSE_1=	
		"WHERE "
		+"emp.emp_identifier = APPT.emp_IDENTIFIER "
		+"AND appt.appt_identifier = ah.appt_identifier "
		+"AND EVENTS.APPT_IDENTIFIER = ?1 "
		+"AND EVENTS.EXPEV_IDENTIFIER = em.EXPEV_IDENTIFIER "
		+"AND EVENTS.appt_identifier = appt.appt_identifier "
		+"AND em.current_ind = 'Y' "
		+"AND em.EXP_DATE_TO BETWEEN ah.APPOINTMENT_DATE AND ah.DEPARTURE_OR_END_DATE "
	    +"AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1 "
		+"WHERE ap1.appt_identifier = ah.appt_identifier "
		+"AND ap1.end_date >= ap1.start_date and em.EXP_DATE_TO BETWEEN ap1.APPOINTMENT_DATE "
        +"AND ap1.DEPARTURE_OR_END_DATE) "
		+"AND action.EXPM_IDENTIFIER(+) = em.EXPM_IDENTIFIER "
		+"AND action.expa_identifier = (SELECT MAX(expa_identifier) FROM EXPENSE_ACTIONS EXP_ACTION "
		+"WHERE action.EXPM_IDENTIFIER = EXP_ACTION.EXPM_IDENTIFIER "
	// ZH - Commented per defect # 309
	//	+"AND exp_action.action_code != 'AUDT') ";
		+"AND exp_action.action_code != 'ECRT') ";

	private final static String EXPENSES_BY_APPT_ID_WHERE_CLAUSE_2 =
		"WHERE emp.emp_identifier = APPT.emp_IDENTIFIER "
		+"AND appt.appt_identifier = ah.appt_identifier "
		+"AND EVENTS.APPT_IDENTIFIER = ?1 "
		+"AND EVENTS.appt_identifier = appt.appt_identifier "
		+"AND EVENTS.EXPEV_IDENTIFIER = em.EXPEV_IDENTIFIER "
		+"AND em.EXP_DATE_TO BETWEEN ah.APPOINTMENT_DATE AND ah.DEPARTURE_OR_END_DATE "
		+"AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1 "
		+"WHERE ap1.appt_identifier = ah.appt_identifier "
		+"AND ap1.end_date >= ap1.start_date and em.EXP_DATE_TO BETWEEN ap1.APPOINTMENT_DATE "
        +"AND ap1.DEPARTURE_OR_END_DATE) "
		+"AND em.current_ind = 'Y' "
		+"AND NOT EXISTS (SELECT * FROM EXPENSE_ACTIONS ea2 "
		+"WHERE em.expm_identifier = ea2.expm_identifier)";	
	
	private final static String SECURITY_SUB_SELECT_QUERY = "AND EXISTS (select sec.user_id from security sec where sec.user_id = ?2 "
				+ " and sec.module_id = ?3 and(ah.department between decode(sec.department,'AL','00',sec.department) " 
				+ " and decode(sec.department,'AL','99',sec.department)) "
				+ " and (ah.agency between decode(sec.agency,'AL','00',sec.agency) and decode(sec.agency,'AL','99',sec.agency)) "
				+ " and (ah.tku between decode(sec.tku,'AL','000',sec.tku) and decode(sec.tku,'AL','999',sec.tku)))";
	
	private final static String EXPENSES_BY_APPT_ID_WHERE_CLAUSE_PDF = " AND NVL(em.PDF_IND, 'N') = 'Y' "; 
	
	
	String OrderBy = " order by 3 desc ";

    private static final String SW_DEPT = " and ah.department = ?2";

    private static final String SW_AGY = " and ah.agency = ?3";

    private static final String SW_TKU = " and ah.tku = ?4";

    private static final String SW_EMPID = " and a.emp_identifier = ?2";

    private static final String SW_LASTNAME = " and UPPER(ph.last_name) LIKE UPPER(?5) ";

    public ExpenseDAO(){}
    
    public ExpenseDAO(EntityManager em){
    	super(em);
    }

	/** This method performs a JPA remove operation to delete the selected expense.
	 * 
	 * @param expenseEventId
	 * @return boolean true / false.
	 */
	public boolean deleteExpenseEvent(int expenseEventId) {

		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: deleteExpenseEvent(int expenseEventId)");
			logger.debug("Enter JPA Operation :: Deleting ExpenseEvent entity");
		}
		
		// delete any existing references in travel requisitions
		entityManager.createNativeQuery("UPDATE TRAVEL_REQ_EVENTS SET expev_identifier = null where expev_identifier = ?1")
		.setParameter(1, expenseEventId).executeUpdate();
		
		boolean isDeleted = true;
		// Remove the Expense event entity that matches the eventId
		entityManager.remove(entityManager.find(ExpenseEvents.class, expenseEventId));
			
		if(logger.isDebugEnabled()){
			logger.debug("Exit JPA Operation :: Deleting ExpenseEvent entity");
			logger.debug("Enter method :: deleteExpenseEvent(int expenseEventId)");
			
		}
		return isDeleted;
	}

	/** This method persists the Expense Masters entity with its associated child entities
	 * 
	 * @param expenseMasters
	 * @return The Expense Master that is persisted
	 */
	
	public ExpenseMasters saveExpense(ExpenseMasters expenseMasters) {
		ExpenseMasters result = null;
		
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: saveExpense(ExpenseMasters expenseMasters)");
			logger.debug("Enter JPA Operation :: Persisting ExpenseMasters entity");
		}
		if(expenseMasters.getExpmIdentifier()==null){
			entityManager.persist(expenseMasters);
			result = expenseMasters;
		}else{
			result = entityManager.merge(expenseMasters);
		}
		
		if(logger.isDebugEnabled()){
			
			logger.debug("Exit JPA Operation :: ExpenseMasters entity persisted successfully");
			logger.debug("Exit method :: saveExpense(ExpenseMasters expenseMasters)");
		}
		
		return result;
	}
	

	/**
	 * This method persists the Expense events and all associated child entities
	 * @param expenseEvents
	 * @return
	 */
	
	public ExpenseEvents saveExpense(ExpenseEvents expenseEvents) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: saveExpense(ExpenseEvents expenseEvents)");
			logger.debug("Enter JPA Operation :: Persisting ExpenseEvents entity");
		}
		
		entityManager.persist(expenseEvents);

		if(logger.isDebugEnabled()){
			logger.debug("Exit JPA Operation :: ExpenseEvents entity persisted successfully");
			logger.debug("Exit method :: saveExpense(ExpenseEvents expenseEvents)");
		}
		return expenseEvents;
	}

	/**
	 * This method finds an ExpenseEvent based on the provided expenseEventId  
	 * @param expenseEventId
	 * @return
	 */
	
	public ExpenseEvents findExpenseByExpenseEventId(int expenseEventId) {
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findExpenseByExpenseEventId(int expenseEventId)");
		}
		return entityManager.find(ExpenseEvents.class, expenseEventId);
		
	}
		
	/***
	 * This method finds the an ExpenseMaster entity with the provided expenseEventId and revisionNo
	 * @param expenseEventId
	 * @param revisionNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ExpenseMasters findExpenseByExpenseEventId(int expenseEventId,int revisionNo) {
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findExpenseByExpenseEventId(int expenseEventId,int revisionNo) return ExpenseMasters");
		}
		
		ExpenseMasters result = null;
		String finderQuery = "select em from ExpenseMasters em where em.expevIdentifier = :expevIdentifier and em.revisionNumber = :revNo";
		
		ExpenseEvents expenseEvents = this.findExpenseByExpenseEventId(expenseEventId);
		List<ExpenseMasters> mastersList = entityManager.createQuery(finderQuery)
		.setParameter("expevIdentifier", expenseEvents)
		.setParameter("revNo", revisionNo).getResultList();
		
		if(mastersList.size()>0)
			result = mastersList.get(0);

		if(logger.isInfoEnabled())
			logger.info("Exit JPA operation : Finder Query for Expenses by the ExpenseEventID completed");
		
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findExpenseByExpenseEventId(int expenseEventId,int revisionNo)");
		}
		return result;
	}
	
	/** This method retrieves the maximum revision number given the expense eventId.
	 * 
	 * @param expenseEventId
	 * @return maxRevisionNo int
	 */
	public int findMaxRevisionNo(int expenseEventId) {
		Long maxRev = (Long)entityManager.createNativeQuery("select max(REVISION_NUMBER) from EXPENSE_MASTERS where EXPEV_IDENTIFIER=?1", Long.class)
						.setParameter(1, expenseEventId).getResultList().get(0);
		
		return maxRev == null ? 0: maxRev.intValue();
	}

	/**
	 * This method retrieves the Out-of-state authorization codes
	 * @return List of authorization codes
	 */
	@SuppressWarnings("unchecked")
	public List<StateAuthCodes> findAuthorizationCodes() {
	
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findAuthorizationCodes() : return findAuthorizationCodes");
		}
	
		String findQueryString = "SELECT s FROM StateAuthCodes s WHERE s.statusCode = :statusCode order by s.stacIdentifier";
		List<StateAuthCodes> authCodeList = entityManager.createQuery(findQueryString).setParameter("statusCode", "A").getResultList();
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findAuthorizationCodes()");
		}
		// remove "Other" and add to the bottom of the list
		List<StateAuthCodes> modifiedAuthCodeList = new ArrayList<StateAuthCodes> ();
		modifiedAuthCodeList.addAll(authCodeList);
		StateAuthCodes itemOther= modifiedAuthCodeList.remove(3);
		modifiedAuthCodeList.add(itemOther);
		
		return modifiedAuthCodeList;
	}
	
	/********************************** DAO methods to retrieve Expenses given the Appointment ID (Manager)******************************************/
	
	/**
	 * This method retrieves all expenses records by appointment ID.
	 * @param appointmentId
	 * @param userId
	 * @param moduleId
	 * @return List of Expenses
	 */
	@SuppressWarnings("unchecked")
	public List<ExpenseListBean> findExpensesByAppointmentId(
			long appointmentId, String userId, String moduleId, boolean showPDFExpensesOnly) {

		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findExpensesByAppointmentId(long appointmentId, String userId, String moduleId) : return List<ExpenseListBean>");
		}
		
		String finderQuery = EXPENSES_BY_APPT_ID_SELECT_CLAUSE
				+ EXPENSES_BY_APPT_ID_WHERE_CLAUSE_1
				+ ((showPDFExpensesOnly) ? EXPENSES_BY_APPT_ID_WHERE_CLAUSE_PDF : "")
				+ SECURITY_SUB_SELECT_QUERY + " UNION "
				+ EXPENSES_BY_APPT_ID_SELECT_CLAUSE_2
				+ EXPENSES_BY_APPT_ID_WHERE_CLAUSE_2
				+ ((showPDFExpensesOnly) ? EXPENSES_BY_APPT_ID_WHERE_CLAUSE_PDF : "")
				+ SECURITY_SUB_SELECT_QUERY + OrderBy;

		List<ExpenseListBean> expenseList = entityManager.createNativeQuery(finderQuery, ExpenseListBean.class).setParameter(1, appointmentId)
		.setParameter(2, userId)
		.setParameter(3, moduleId).getResultList();
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findExpensesByAppointmentId(long appointmentId, String userId, String moduleId)");
		}
		
		return expenseList;
	}
	
	
	/** This method retrieves only adjusted expense provided the appointmentId.
	 * 
	 * @param appointmentId
	 * @param userId
	 * @param moduleId
	 * @return The list of Expenses
	 */

	@SuppressWarnings("unchecked")
	public List<ExpenseListBean> findAdjustedExpensesByAppointmentId(
			long appointmentId, String userId, String moduleId, boolean showPDFExpensesOnly) {
		

		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findAdjustedExpensesByAppointmentId(long appointmentId, String userId, String moduleId) : return List<ExpenseListBean>");
		}

		String adjCondition = " AND em.ADJ_IDENTIFIER is not null ";
		
		String finderQuery = EXPENSES_BY_APPT_ID_SELECT_CLAUSE
				+ EXPENSES_BY_APPT_ID_WHERE_CLAUSE_1
				+ ((showPDFExpensesOnly) ? EXPENSES_BY_APPT_ID_WHERE_CLAUSE_PDF : "")
				+ adjCondition
				+ SECURITY_SUB_SELECT_QUERY + " UNION "
				+ EXPENSES_BY_APPT_ID_SELECT_CLAUSE_2
				+ EXPENSES_BY_APPT_ID_WHERE_CLAUSE_2 
				+ ((showPDFExpensesOnly) ? EXPENSES_BY_APPT_ID_WHERE_CLAUSE_PDF : "")
				+ adjCondition
				+ SECURITY_SUB_SELECT_QUERY + OrderBy;

		List<ExpenseListBean> expenseList = entityManager.createNativeQuery(finderQuery, ExpenseListBean.class)
		.setParameter(1, appointmentId)
		.setParameter(2, userId)
		.setParameter(3, moduleId).getResultList();
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findAdjustedExpensesByAppointmentId(long appointmentId, String userId, String moduleId)");
		}
		
		return expenseList;
	}
	
	
	
	/**
	 * This method retrieves all the non adjusted expenses for an employee’s appointment
	 * @param appointmentId
	 * @param userId
	 * @param moduleId
	 * @return List of expenses
	 */

	@SuppressWarnings("unchecked")
	public List<ExpenseListBean> findNonAdjustedExpensesByAppointmentId(
			long appointmentId, String userId, String moduleId, boolean showPDFExpensesOnly) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findNonAdjustedExpensesByAppointmentId(long appointmentId, String userId, String moduleId) : return List<ExpenseListBean>");
		}

		// Build the query
		String adjCondition = "AND em.ADJ_IDENTIFIER is null ";
		
		String finderQuery = EXPENSES_BY_APPT_ID_SELECT_CLAUSE
				+ EXPENSES_BY_APPT_ID_WHERE_CLAUSE_1
				+ ((showPDFExpensesOnly) ? EXPENSES_BY_APPT_ID_WHERE_CLAUSE_PDF : "")
				+ adjCondition
				+ SECURITY_SUB_SELECT_QUERY + " UNION "
				+ EXPENSES_BY_APPT_ID_SELECT_CLAUSE_2
				+ EXPENSES_BY_APPT_ID_WHERE_CLAUSE_2
				+ ((showPDFExpensesOnly) ? EXPENSES_BY_APPT_ID_WHERE_CLAUSE_PDF : "")
				+ adjCondition
				+ SECURITY_SUB_SELECT_QUERY + OrderBy;
		
		List<ExpenseListBean> expenseList = entityManager.createNativeQuery(finderQuery, ExpenseListBean.class)
		.setParameter(1, appointmentId)
		.setParameter(2, userId)
		.setParameter(3, moduleId).getResultList();
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findNonAdjustedExpensesByAppointmentId(long appointmentId, String userId, String moduleId)");
		}
		
		return expenseList;
	}
	
	
	
	/**
	 * This method retrieves all expenses for an employee with the given employeeId.
	 * @param employeeId
	 * @return list of expenses
	 */

	@SuppressWarnings("unchecked")
	public List<VExpensesList> findExpensesByEmployeeId(int employeeId) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findExpensesByEmployeeId(int employeeId) : return List<ExpenseListBean>");
		}

		String finderQuery  = "select v from VExpensesList v where v.empIdentifier=:empIdentifier";	
		List<VExpensesList> expenseList = entityManager.createQuery(finderQuery)
		.setParameter("empIdentifier", employeeId).getResultList();

		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findExpensesByEmployeeId(int employeeId)");
		}
		return expenseList;
	}
	
	/**
	 * This method retrieves all adjusted expenses for an employee with the given employeeId.
	 * @param employeeId
	 * @return list of expenses
	 */

	@SuppressWarnings("unchecked")
	public List<VExpensesList> findAdjustedExpensesByEmployeeId(int employeeId) {

		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findAdjustedExpensesByEmployeeId(int employeeId) : return List<VExpensesList>");
		}
		String finderQuery = "SELECT expList from VExpensesList expList " +
				"where expList.empIdentifier = :empIdentifier and expList.adjIdentifier is not null";
		
		List<VExpensesList> expenseList = entityManager.createQuery(finderQuery)
		.setParameter(1, employeeId).getResultList();

		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findAdjustedExpensesByEmployeeId(int employeeId)");
		}
		
		return expenseList;
	}

	/**
	 * This method retrieves all non-adjusted expenses for an employee with the given employeeId.
	 * @param employeeId
	 * @return list of expenses
	 */
	@SuppressWarnings("unchecked")
	public List<VExpensesList> findNonAdjustedExpensesByEmployeeId(int employeeId) {

		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findNonAdjustedExpensesByEmployeeId(int employeeId) : return List<VExpensesList>");
		}
		
		String finderQuery = "SELECT expList from VExpensesList expList where expList.empIdentifier = :empIdentifier " +
				"and expList.adjIdentifier is null";
		
		List<VExpensesList> expenseList = entityManager.createQuery(finderQuery)
		.setParameter("empIdentifier", employeeId).getResultList();
			
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findNonAdjustedExpensesByEmployeeId(int employeeId)");
		}
		
		return expenseList;
	}
	
	/******************* DAO Methods to retrieve the transaction list for Manager Approvals when "My Employee" is selected **********************/ 
	
	/**
	 * This method retrieves all non-adjusted expenses awaiting approval 
	 * for all employees for a given supervisor with supervisorEmpId as provided
	 * @param supervisorEmpId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VExpApprovalList> findNonAdjustedExpensesAwaitingApproval(
			int supervisorEmpId) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findNonAdjustedExpensesAwaitingApproval(int supervisorEmpId) : return List<VExpApprovalList>");
		}
		
		String finderQuery = "SELECT vexp from VExpApprovalList vexp where vexp.empIdentifier = : supervisorEmpId and vexp.adjIdentifier is null";
		
		List<VExpApprovalList> approvalList = (List<VExpApprovalList>) entityManager.createQuery(finderQuery)
				.setParameter("supervisorEmpId", supervisorEmpId).getResultList();
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findNonAdjustedExpensesAwaitingApproval(int supervisorEmpId)");
		}
		return approvalList;

	}
	
	/**
	 * This method retrieves all expenses awaiting approval for all 
	 * employees for a given supervisor.
	 * @param supervisorEmpId
	 * @return
	 */
	
    
    /*****************************************************************************************************************************/
	
	/**
	 * This method finds the expense record given the expense master Id.
	 * @param expenseMasterId
	 * @return ExpenseMasters
	 */
	public ExpenseMasters findExpenseByExpenseMasterId(int expenseMasterId) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findExpenseByExpenseMasterId(int expenseMasterId) : return ExpenseMasters");
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findExpenseByExpenseMasterId(int expenseMasterId) : return ExpenseMasters");
		}
		
		return entityManager.find(ExpenseMasters.class, expenseMasterId);
	}

	/**
	 * This method finds the Action given the action code.
	 * @param actionCode
	 * @return Actions
	 */
	
	public int findActionCode(int expenseMasterId, String actionCode) {
		if (logger.isDebugEnabled()) {
			logger.debug("Enter method :: findActionCode() : return Actions");
		}

		String finderQuery = "Select count(*) from expense_actions ea where ea.expm_identifier=?1 and ea.action_code=?2";

		List<BigDecimal> count = entityManager.createNativeQuery(finderQuery)
				.setParameter(1, expenseMasterId).setParameter(2, actionCode)
				.getResultList();

		if (logger.isDebugEnabled()) {
			logger
					.debug("Exit method :: findActionCode(String actionCode) : return Actions");
		}

		return count.get(0).intValue();
	}
		
	/** This method updates the status of the expense record subsequent to an approval
	 * 
	 * @param expenseMaster
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public ExpenseMasters updateExpenseStatus(ExpenseMasters expenseMaster) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : updateExpenseStatus(ExpenseMasters)");
		
		String finderQuery = "SELECT EA FROM ExpenseActions EA WHERE EA.expmIdentifier=:expmIdentifier AND EA.nextActionCode IS NOT NULL";

		if(logger.isInfoEnabled())	
			logger.info("Enter JPA operation : Persisting ExpenseMasters in updateExpenseStatus(ExpenseMasters) method");
			
			List<ExpenseMasters> expMastList = entityManager.createQuery(finderQuery).setParameter("expmIdentifier",expenseMaster).getResultList();
			if(expMastList.size()==0)
				expenseMaster.setStatus(IConstants.APPROVED);
			/*else if(expMastList.size()>0)
				expenseMaster.setStatus(IConstants.SUBMIT);
			 */
			if(logger.isInfoEnabled())
				logger.info("Exit JPA operation : Persisting ExpenseMasters in updateExpenseStatus(ExpenseMasters) method");
			
			if(logger.isDebugEnabled())
				logger.debug("Exit method : updateExpenseStatus(ExpenseMasters)");
		return expenseMaster;
	}
	
	
	public ExpenseMasters findPrevExpenseInStatus(ExpenseEvents expenseEvent, String status, int revisionNo){
		String finderQuery = "select em from ExpenseMasters em where em.expevIdentifier =:expevIdentifier " +
				"and em.revisionNumber < :revisionNumber and em.status = :statusCode order by em.revisionNumber desc";
		
		List<ExpenseMasters> expenseMasterList =  entityManager.createQuery(finderQuery)
														.setParameter("expevIdentifier", expenseEvent)
														.setParameter("revisionNumber", revisionNo)
														.setParameter("statusCode", status)
														.getResultList();
		
		return expenseMasterList.isEmpty() ? null: expenseMasterList.get(0);
	}
	
	/**
	 * This method retrieves rows from the ExpenseActions that has NEXT_ACTION_CODE NOT NULL
	 * @param expenseMaster
	 * @return List<ExpenseActions>
	 */
	public List<ExpenseActions> approveExpense(ExpenseMasters expenseMaster){
		String finderQuery = "SELECT EA FROM ExpenseActions EA WHERE EA.expmIdentifier=:expmIdentifier AND EA.nextActionCode IS NOT NULL";
		return entityManager.createQuery(finderQuery)
		.setParameter("expmIdentifier",expenseMaster).getResultList();
	}
	
	public ExpenseActions saveExpenseAction(ExpenseActions expAction){
		ExpenseActions savedAction = null;
		
		if(expAction == null) return null;
		
		if(expAction.getExpaIdentifier() == null){
			entityManager.persist(expAction);
			savedAction = expAction;
		}else{
			savedAction = entityManager.merge(expAction);
		}
		
		entityManager.flush();
		entityManager.refresh(savedAction);
		
		return savedAction;
	}
	
	public List<ExpenseHistory> findExpenseHistory(int expenseEventId){

		String finderQuery = "SELECT EA.EXPA_IDENTIFIER expaIdentifier,EM.EXPM_IDENTIFIER expenseMasterId,EM.REVISION_NUMBER revisionNo, EA.MODIFIED_DATE modifiedDate, EA.ACTION_CODE expActionCode," +
				"EA.COMMENTS comments, EA.MODIFIED_USER_ID modifiedUserId FROM EXPENSE_EVENTS EE," +
				"EXPENSE_MASTERS EM, EXPENSE_ACTIONS EA WHERE EE.EXPEV_IDENTIFIER = EM.EXPEV_IDENTIFIER " +
				"AND EA.EXPM_IDENTIFIER = EM.EXPM_IDENTIFIER " +
				"AND EA.ACTION_CODE NOT IN ('EPRC','LPRC')" +
				"AND EE.EXPEV_IDENTIFIER = ?1 ORDER BY EA.MODIFIED_DATE";
		
		return entityManager.createNativeQuery(finderQuery,ExpenseHistory.class).setParameter(1, expenseEventId).getResultList();
		
	}
	
	/**
	 * Find <code>ExpenseMasters</code> related to the liquidation Id.
	 * 
	 * @param Liquidation Id related to <code>ExpenseMasters</code>
	 * @return <code>ExpenseMasters</code> liquidating the liquidation
	 */
	public ExpenseMasters findExpenseByLiquidationId(Integer liquidationId){
		ExpenseMasters result = null;
		
		String query = "select * from EXPENSE_MASTERS em where em.EXPM_IDENTIFIER = " +
				"(select EXPM_IDENTIFIER from ADVANCE_LIQUIDATIONS where LIQD_IDENTIFIER=?1)";
		
		List<ExpenseMasters> expenseList = entityManager.createNativeQuery(query, ExpenseMasters.class)
						.setParameter(1, liquidationId).getResultList();
		
		if(!expenseList.isEmpty()) result = expenseList.get(0);
		
		return result;
	}
	
	public OutOfStateTravel findOutOfStateTravel(int outOfStateId){
		return entityManager.find(OutOfStateTravel.class,outOfStateId);
		
	}

	public boolean isExpenseMarkedAuditComplete(ExpenseMasters expense){
		long count = (Long)entityManager.createQuery("select count(ea.expaIdentifier) from ExpenseActions ea where ea.actionCode='AUDT' and ea.expmIdentifier=:expense")
		.setParameter("expense", expense).getSingleResult();
		
		return count>0;
	}
	
	/**
	 * Determines if adjustment type has been populated for an approved expense.
	 */
	public boolean isAdjustmentTypeSetForApprovedExpense(Integer expenseId){
		String query = "select count(em.expm_identifier) from Expense_Masters em where em.expm_identifier=?1" +
				" and em.status='APPR' and em.adj_type IS NOT NULL";
		
		long count = (Long)entityManager.createNativeQuery(query, Long.class)
										.setParameter(1, expenseId)
										.getSingleResult();
		
		return count>0;
	}
	/**
	 * Used to determine is there is an ACTION on the expense other than Submission
	 * @param expenseEvent
	 * @param revisionNo
	 * @return
	 */

	public List<ExpenseActions> findPrevExpenseApprovalAction(ExpenseEvents expenseEvent, int revisionNo){
		//26484: Unable to update a rejected expense - PROD ISSUE
		//SQL changed to compare just the previous reversion and not ALL the previous revisions.
		String finderQuery = "Select ea.* from Expense_Masters em, Expense_Actions ea" +
				" where em.expev_Identifier =?1 and em.expm_Identifier = ea.expm_Identifier" +
				" and em.revision_Number = ?2 and ea.action_Code <> 'SUBM' and ea.action_Code <> 'RJCT'" + 
				" and ea.action_Code <> 'SPLT' and ea.action_Code <> 'ECRT'";
		
		List<ExpenseActions> expenseActionsList =  entityManager.createNativeQuery(finderQuery)
														.setParameter(1, expenseEvent.getExpevIdentifier())
														.setParameter(2, revisionNo - 1)
														.getResultList();
		
		
		return expenseActionsList;
	}
	
	/**
	 * Used to determine is there is an ACTION on the expense other than Submission
	 * but only for revisions that are greater than the PROC revision
	 * @param expenseEvent
	 * @param revisionNo
	 * @return
	 */
	
	public List<ExpenseActions> findPrevExpenseApprovalActionProc(ExpenseEvents expenseEvent, int revisionNo){
		String finderQuery = "Select ea.* from Expense_Masters em, Expense_Actions ea" +
				" where em.expev_Identifier =?1 and em.expm_Identifier = ea.expm_Identifier" +
				" and em.revision_Number > ?2 and ea.action_Code <> 'SUBM' and ea.action_Code <> 'RJCT'" + 
				" and ea.action_Code <> 'SPLT' and ea.action_Code <> 'ECRT'";
		
		List<ExpenseActions> expenseActionsList =  entityManager.createNativeQuery(finderQuery)
														.setParameter(1, expenseEvent.getExpevIdentifier())
														.setParameter(2, revisionNo)
														.getResultList();
		
		
		return expenseActionsList;
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
	public String getRemainingApprovalPaths(int expmIdentifier, UserSubject userSubject ){
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getRemainingApprovalPaths Parameters: expmIdentifier:" + expmIdentifier + "department:" + userSubject.getDepartment()  +  "agency:" + userSubject.getAgency() +  "tku:" + userSubject.getTku());

		String finderQuery = "SELECT F_EXP_GET_NEXT_APPROVAL_PATHS ( ?1, ?2, ?3, ?4) FROM DUAL";
		Query query = entityManager.createNativeQuery(finderQuery);

		query.setParameter(1, expmIdentifier);
		query.setParameter(2, userSubject.getDepartment());
		query.setParameter(3, userSubject.getAgency());
		query.setParameter(4, userSubject.getTku());
		
		String returnPaths =  (String)query.getSingleResult();
		if (returnPaths.trim().length() == 0)
			returnPaths = "";
		else
			//removing last comma
			if (returnPaths.endsWith(",")) 
				returnPaths =   returnPaths.substring(0, returnPaths.length() - 1);

		if(logger.isDebugEnabled())
			logger.debug("Exit method : getRemainingApprovalPaths Value:" + returnPaths);

		return returnPaths;

	}
	
	/*
	 * Method to validate Meal times
	 * return 1: Valid Meal Time
	 * 0: Invalid Meal Time
	 * 
	 * */
	public String isUserOptionExpenseValid(int expmIdentifier, String isTravel, String isPDF, String isOutOfState){
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : isUserOptionExpenseValid Parameters: expmIdentifier:" + expmIdentifier + "isTravel:" + isTravel +  "isPDF:" +isPDF +  "isOutOfState:" + isOutOfState);

		String finderQuery = "SELECT F_VALID_EXPENSE_USER_OPTION ( ?1, ?2, ?3, ?4) FROM DUAL";
		Query query = entityManager.createNativeQuery(finderQuery);

		query.setParameter(1, expmIdentifier);
		query.setParameter(2, isTravel);
		query.setParameter(3, isPDF);
		query.setParameter(4, isOutOfState);
		
		String returnCode =  (String)query.getSingleResult();
		if (returnCode == null )
			returnCode = "";
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : isMealTimeValid:Return Value:" + returnCode);

		return returnCode;

	}
	
	/**
	 * Checks to see if an expense can be automatically approved
	 * @param dept
	 * @param agency
	 * @param tku
	 * @param masterId
	 * @param eventId
	 * @return
	 */
	
    public int findAutoApprovalStatus (String dept, String agency, String tku, int masterId, int eventId) {
    	logger.debug("Enter method : findAutoApprovalStatus(String dept, String agency, String tku, int masterId, int eventId)");
    	String finderQuery = "SELECT f_auto_approve_expense(?1, ?2, ?3, ?4, ?5) from dual";
    	BigDecimal autoApprove = (BigDecimal) entityManager
    		.createNativeQuery(finderQuery).setParameter(1, dept)
    		.setParameter(2, agency)
    		.setParameter(3, tku)
    		.setParameter(4, masterId)
    		.setParameter(5, eventId)
    		.getSingleResult();

    	return autoApprove.intValue();

        }
    
	/**
	 * Clones an existing expense
	 * @param expevIdentifier expense event identifier
	 * @param fromDate
	 * @param toDate 
	 * @return String with error message if any.
	 */
    public String cloneExpense(int expevIdentifier, Date fromDate, Date toDate){
    	
		if(logger.isDebugEnabled())
			logger.debug("Enter method : closeExpense: expevIdentifier:"
					+ expevIdentifier + ", fromDate:" + fromDate + ", toDate:"
					+ toDate);
		
		String finderQuery = "SELECT F_CLONE_EXPENSE(?1,?2,?3) FROM DUAL";
			
		Query query = entityManager.createNativeQuery(finderQuery).setParameter(1, expevIdentifier)
		.setParameter(2, fromDate)
		.setParameter(3, toDate);
		
		String returnValue = query.getSingleResult().toString();
		
		if(logger.isDebugEnabled()){			
			logger.debug("Exit Method : closeExpense(int expevIdentifier, Date fromDate, Date toDate): Return value: "
					+ returnValue);
		}

		return returnValue;
	}
    
    /**
	 * Validates Expense dates with Timesheet dates
	 * @param expevIdentifier expense event identifier 
	 * @return String with error message if any.
	 */
    public String compareExpenseDateToTimesheetDate(int expevIdentifier){
    	
		if(logger.isDebugEnabled())
			logger.debug("Enter method : compareExpenseDateToTimesheetDate: expevIdentifier:"
					+ expevIdentifier);
		
		String finderQuery = "SELECT F_COMPARE_EXPENSE(?1) FROM DUAL";
			
		Query query = entityManager.createNativeQuery(finderQuery).setParameter(1, expevIdentifier);
		
		Object objReturnValue = query.getSingleResult();  
		String returnValue = "";
		
		if(objReturnValue != null)
			returnValue = objReturnValue.toString();
		
		if(logger.isDebugEnabled()){			
			logger.debug("Exit Method : compareExpenseDateToTimesheetDate(int expevIdentifier): Return value: "
					+ returnValue);
		}

		return returnValue;
    }
    
    /**
     * Method to check if the expenses have been submitted by other users.
     * @param expmIdentifier
     * @return
     */
    public int checkExpensesEnteredByOtherUser(int expmIdentifier, String loggedInUser){
    	
    	
    	if(logger.isDebugEnabled())
    		logger.debug("Enter method checkExpensesEnteredByOtherUser(int expmIdentifier) expmIdentifier: " + expmIdentifier);
    	
    	String finderQuery = "SELECT F_CHECK_EXPENSE_SUBMIT_BY_USER(?1, ?2) FROM DUAL";    		
    	Query query = entityManager.createNativeQuery(finderQuery).setParameter(1, expmIdentifier).setParameter(2, loggedInUser);
    	
    	int returnValue = Integer.parseInt(((String)query.getSingleResult()));
    	
    	return returnValue;
    	
    }
    
   
    /**
     * Method to get adjustments made on the expense
     * @param appointmentIdentifier
     * @param expmIdentifier
     * @return
     */    
    public List<BigDecimal> getAdjustmentsForExpense(int appointmentIdentifier, int expmIdentifier){
    	if(logger.isDebugEnabled())
    		logger.debug("Enter method getAdjustmentsForExpense(int appointmentIdentifier, int expmIdentifier) appointmentIdentifier: " + appointmentIdentifier + " expmIdentifier:" + expmIdentifier);
    	
    	String finderQuery = "SELECT DISTINCT ASSOCIATED_ADJ_IDENTIFIER"+
							 " FROM DETAIL_DISTRIBUTIONS dd, CB_CHANGE_REQUESTS CHR"+
							 " WHERE     dd.ASSOCIATED_ADJ_IDENTIFIER = CHR.ADJ_IDENTIFIER"+
							 "      AND dd.ASSOCIATED_ADJ_IDENTIFIER > 0"+
							 "      AND CHR.request_type = 'X'"+
							 "      AND dd.APPT_IDENTIFIER = ?1"+
							 "      AND dd.KEY_IDENTIFIER = ?2";
    	
		Query query = entityManager.createNativeQuery(finderQuery)
				.setParameter(1, appointmentIdentifier)
				.setParameter(2, expmIdentifier);
		
		List<BigDecimal> adjustmentIdentifiers =(List<BigDecimal>) query.getResultList();
    	
    	if(logger.isDebugEnabled())
    		logger.debug("Exit method getAdjustmentsForExpense(int appointmentIdentifier, int expmIdentifier)");
    	
    	return adjustmentIdentifiers;
    }
    
    /**
     * Method to get RStars adjustments count made on the expense
     * @param appointmentIdentifier
     * @param expmIdentifier
     * @return
     */    
    public int getRStartsAdjustmentsForExpense(int appointmentIdentifier, int expmIdentifier){
    	if(logger.isDebugEnabled())
    		logger.debug("Enter method getRStartsAdjustmentsForExpense(int appointmentIdentifier, int expmIdentifier) appointmentIdentifier: " + appointmentIdentifier + " expmIdentifier:" + expmIdentifier);
    	
    	String finderQuery = "SELECT COUNT (*) AS cc_count"+
							 " FROM DETAIL_DISTRIBUTIONS DD, RSTARS_TRANSACTIONS RT"+
							 " WHERE     DD.RSTR_IDENTIFIER = RT.RSTR_IDENTIFIER"+
							 "      AND RT.CHANGED_IND = 'Y'"+
							 "      AND DD.APPT_IDENTIFIER = ?1"+
							 "      AND dd.KEY_IDENTIFIER = ?2";
    	
		Query query = entityManager.createNativeQuery(finderQuery)
				.setParameter(1, appointmentIdentifier)
				.setParameter(2, expmIdentifier);
		
		int returnValue = Integer.parseInt(query.getSingleResult().toString());
    	
    	if(logger.isDebugEnabled())
    		logger.debug("Exit method getRStartsAdjustmentsForExpense(int appointmentIdentifier, int expmIdentifier)");
    	
    	return returnValue;
    }
    
		
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	/**
	 * Retrieve type of adjustment such an amount change OR
	 * coding block change or both
	 * @param currentExpenseMasterId
	 * @return
	 */
	   public String findApprovalAdjustmentCategory(int currentExpenseMasterId) {
			String finderQuery = "SELECT F_EXP_ADJ_TYPE(?1) from dual";
			String apprCategory = (String) entityManager
				.createNativeQuery(finderQuery).setParameter(1, currentExpenseMasterId)
				.getSingleResult();

			return apprCategory;

	}
	   
	/**
	 * Returns a positive count if the expense was auto approved 
	 * @param expense
	 * @return
	 */
	public boolean expenseAutoApprovalActionExists(ExpenseMasters expense){
			long count = (Long)entityManager.createQuery("select count(ea.expaIdentifier) from ExpenseActions ea where ea.actionCode='ASYS' and ea.expmIdentifier=:expense")
			.setParameter("expense", expense).getSingleResult();
			
			return count>0;
	}
	
	/**
	 * Method to determine if the user has PDF roles 
	 * @param expense
	 * @return <B>true</b> if user has 'PDF ENTRY', 'PDF APPROVAL' role.
	 */
	public boolean isUserWithPDFRole(String userID, String department, String agency, String tku) {
		String finderQuery = "SELECT COUNT (*)" + " FROM security"
				+ " WHERE     role_name IN ('PDF ENTRY', 'PDF APPROVAL')"
				+ "    AND DEPARTMENTRL = 'AL' AND AGENCYRL = 'AL'"
				+ "    and module_id  in ('EXPF003') "
				+ "   AND (?2 BETWEEN DECODE (department, 'AL', '00', department)  AND DECODE (department, 'AL', '99',department))"
				+ "   AND (?3 BETWEEN DECODE (agency, 'AL', '00', agency) AND DECODE (agency, 'AL', '99', agency))"
			    + "   AND (?4 BETWEEN DECODE (tku, 'AL', '000', tku) AND DECODE (tku, 'AL', '999', tku))"
				+ "      AND USER_ID = ?1";

		int resultCount = Integer.parseInt(entityManager
				.createNativeQuery(finderQuery)
				.setParameter(1, userID)
				.setParameter(2, department)
				.setParameter(3, agency)
				.setParameter(4, tku)
				.getSingleResult().toString()) ;

		return (resultCount > 0 ? true : false);

	}
	
	/**
	 * Method to determine if the user has roles other than PDF 
	 * @param expense
	 * @return <B>true</b> if user has roles other than 'PDF ENTRY', 'PDF APPROVAL' role.
	 */	
	public boolean doesUserHasStatewideRoles(String userID, String department, String agency, String tku, String moduleID) {
		
		String finderQuery = "";
		
		if (moduleID.equals(IConstants.EXPENSE_MANAGER)){
			finderQuery = "SELECT COUNT (*)" + " FROM security"
					+ " WHERE     role_name IN ('SUPERVIS EXP')"
					+ "  and module_id  in ('EXPW002') " 
					+ "   AND DEPARTMENTRL = 'AL' AND AGENCYRL = 'AL'"
					+ "   AND (?2 BETWEEN DECODE (department, 'AL', '00', department)  AND DECODE (department, 'AL', '99',department))"
					+ "   AND (?3 BETWEEN DECODE (agency, 'AL', '00', agency) AND DECODE (agency, 'AL', '99', agency))"
				    + "   AND (?4 BETWEEN DECODE (tku, 'AL', '000', tku) AND DECODE (tku, 'AL', '999', tku))"			    
					+ "   AND USER_ID = ?1";	
		}
		else if (moduleID.equals(IConstants.EXPENSE_STATEWIDE)){
			finderQuery = "SELECT COUNT (*)" + " FROM security"
					+ " WHERE     role_name IN ('TRAVL COORD EXP', 'REGION APPR EXP', 'PERSON APPR EXP', 'FINAN APPR1 EXP', 'FINAN APPR2 EXP', 'SUPERUSER')"
					+ "  and module_id  in ('EXPW003') " 
					+ "   AND DEPARTMENTRL = 'AL' AND AGENCYRL = 'AL'"
					+ "   AND (?2 BETWEEN DECODE (department, 'AL', '00', department)  AND DECODE (department, 'AL', '99',department))"
					+ "   AND (?3 BETWEEN DECODE (agency, 'AL', '00', agency) AND DECODE (agency, 'AL', '99', agency))"
				    + "   AND (?4 BETWEEN DECODE (tku, 'AL', '000', tku) AND DECODE (tku, 'AL', '999', tku))"			    
					+ "   AND USER_ID = ?1";
		}

		int resultCount = Integer.parseInt(entityManager
				.createNativeQuery(finderQuery)
				.setParameter(1, userID)
				.setParameter(2, department)
				.setParameter(3, agency)
				.setParameter(4, tku)
				.getSingleResult().toString());

		return (resultCount > 0 ? true : false);
	}
	//cn-bug-208
	/**
	 * check if the current expense dates exists
	 * @param expense
	 * @return <B>true</b> if dates exist.
	 */	
	//RN, 04/13/2015 -AI_26431 -Bug 361 - Added pdf_ind = 'Y' to prevent warning if a non-travel expense and PDF expense have the same date.
	public boolean checkExpenseDates(Date fromDate,Date toDate,int apptId,String travelInd,int expevIdentifier,String pdfInd) {
		
		String finderQuery="";
		
		
		finderQuery = "select count(*) from expense_masters em,expense_events ev,appointments a where em.exp_date_from=?1 and em.exp_date_to=?2  and em.current_ind='Y' and "
                +" em.expev_identifier=ev.expev_identifier and ev.appt_identifier=a.appt_identifier and a.appt_identifier=?3  and em.expev_identifier<>?4 and travel_ind=?5 and pdf_ind=?6";
		
		Query query = entityManager.createNativeQuery(finderQuery);
		
		int resultCount = Integer.parseInt(entityManager
				.createNativeQuery(finderQuery)
				.setParameter(1, fromDate)
				.setParameter(2, toDate)
				.setParameter(3, apptId)
				.setParameter(4, expevIdentifier)
				.setParameter(5,travelInd)
				.setParameter(6,pdfInd)
				.getSingleResult().toString()) ;
		
		return (resultCount > 0 ? true : false);
	}
	//cn-bug-208
	
}
