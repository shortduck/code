/*
 * ZH, 07/21/2011 - Commented unused SQLs and methods per request. Commented code should be
 * removed in a subsequent release
 */

package gov.michigan.dit.timeexpense.dao;

/**
 * @author chiduras
 *
 */
import gov.michigan.dit.timeexpense.model.core.VAdvApprovalList;
import gov.michigan.dit.timeexpense.model.core.VExpApprovalList;
import gov.michigan.dit.timeexpense.model.core.VTreqApprovalList;
import gov.michigan.dit.timeexpense.model.display.ApprovalTransactionBean;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class ApproveDAO extends AbstractDAO  {
   private Logger logger = Logger.getLogger(ApproveDAO.class);
   
   public ApproveDAO() {
   }

   public ApproveDAO(EntityManager em) {
	super(em);
   }
   
/*   private final String SECURITY_QUERY = "(SELECT sec.user_id FROM SECURITY sec WHERE sec.user_id = ?2 AND sec.module_id = ?3 AND "
	    + " (ah.department BETWEEN DECODE(sec.department,'AL','00',sec.department) AND DECODE(sec.department,'AL','99',sec.department)) AND "
	    + " (ah.agency BETWEEN DECODE(sec.agency,'AL','00',sec.agency) AND DECODE(sec.agency,'AL','99',sec.agency)) AND "
	    + " (ah.tku BETWEEN DECODE(sec.tku,'AL','000',sec.tku) AND DECODE(sec.tku,'AL','999',sec.tku))) ";*/

 private static final String SW_APPR_SELECT_CLAUSE_FIRST = 
             "SELECT /*+ opt_param('optimizer_index_cost_adj',1) */ /* INDEX_ASC(SEC SEC_USER_MOD_DAT_IDX) */"
        + " ph.last_name lastName, ph.middle_name middleName, ph.first_name firstName,"
            + " 'Advance' transactionType,"
	    + " a.emp_identifier empIdentifier,"
	    + " ah.appt_identifier apptIdentifier,"
	    + " ae.adev_identifier requestId,"
	    + " am.advm_identifier masterId,"
	    + " am.adj_identifier adjIdentifier,"
	    + " am.request_date requestDate,"
	    + " am.from_date fromDate, "
	    + " am.TO_DATE toDate," 
	    + " decode (am.manual_deposit_ind, 'Y', am.manual_deposit_amount, ad.dollar_amount) dollarAmount,"
	    + " ah.department department," 
	    + " ah.agency agency,"
	    + " ah.tku tku,"
	    + " ah.dept_code deptCode," 
	    + " aa.modified_date lastActionDate ";

private static final String SW_FROM_CLAUSE_FIRST = " from advance_events ae,"
	    + " advance_details ad, "
	    + " advance_actions aa,"
	    + " appointments a,"
	    + " personnel_histories ph,"
	    + " appointment_histories ah,"
	    + " advance_masters am,"
	    + " ref_codes rc";

private static final String SW_WHERE_CLUASE_FIRST = " where (ae.appt_identifier = a.appt_identifier)"
	    + "  AND (a.appt_identifier = ah.appt_identifier) "
	    + "  AND (ae.appt_identifier = ah.appt_identifier) "
	    + "  AND (am.advm_identifier = ad.advm_identifier)"
	    + "  AND (ae.adev_identifier = am.adev_identifier)"
	    + "  AND (am.advm_identifier = aa.advm_identifier)"
	    + "  AND (a.emp_identifier = ph.emp_identifier) "
	    + "  AND SYSDATE BETWEEN ph.start_date AND ph.end_date"
	    + "  AND am.current_ind = 'Y'"
	    + "  AND am.request_date BETWEEN ah.appointment_date AND ah.departure_or_end_date"
	    + "	 AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1"
		+ "  WHERE ap1.appt_identifier = ah.appt_identifier"
		+ "  AND ap1.end_date >= ap1.start_date)"
	    + "  AND SYSDATE between PH.START_DATE AND PH.END_DATE"
	    + "  AND EXISTS "
	    + "  (SELECT 'X' "
	    + "  FROM SECURITY SEC"
	    + "  WHERE     sec.user_id = ?1"
	    + "  AND sec.module_id IN ('APRF015', 'APRF014', 'APRF013', 'APRF009','APRF010', 'APRF012', 'APRF011', 'APRF008')"
	    + "  AND sec.module_id = rc.ref_code"
	    + "  AND ah.department = DECODE (sec.department,'AL', ah.department, sec.department)"
	    + "  AND ah.agency = DECODE (sec.agency, 'AL', ah.agency, sec.agency)"
	    + "  AND ah.tku = DECODE (sec.tku, 'AL', ah.tku, sec.tku))"
	    + "  AND rc.VALUE = aa.next_action_code";

//private static final String SW_APPR_SELECT_CLAUSE_SECOND = 
//"SELECT /*+ opt_param('optimizer_index_cost_adj',1) */ /* INDEX_ASC(SEC SEC_USER_MOD_DAT_IDX) */"
/*        + " ph.last_name lastName, ph.middle_name middleName, ph.first_name firstName, "
    	    + " 'Advance' transactionType,"
	    + " a.emp_identifier empIdentifier,"
	    + " ah.appt_identifier apptIdentifier,"
	    + " ae.adev_identifier requestId,"
	    + " am.advm_identifier masterId,"
	    + " am.adj_identifier adjIdentifier,"
	    + " am.request_date requestDate,"
	    + " am.from_date fromDate, "
	    + " am.TO_DATE toDate," + " am.MANUAL_DEPOSIT_AMOUNT dollarAmount, "
	    + " ah.department department," 
	    + " ah.agency agency,"
	    + " ah.tku tku,"
	    + " ah.dept_code deptCode," 
	    + " aa.modified_date lastActionDate ";*/

/*private static final String SW_FROM_CLAUSE_SECOND = " from advance_events ae,"
	    + " advance_actions aa,"
	    + " appointments a,"
	    + " personnel_histories ph,"
	    + " appointment_histories ah,"
	    + " advance_masters am," + " security sec," + " ref_codes rc";*/

/*private static final String SW_WHERE_CLUASE_SECOND = " where (ae.appt_identifier = a.appt_identifier)"
	    + "  AND (a.appt_identifier = ah.appt_identifier) "
	    + "  AND (ae.appt_identifier = ah.appt_identifier) "
	    + "  AND (ae.adev_identifier = am.adev_identifier)"
	    + "  AND (am.advm_identifier = aa.advm_identifier)"
	    + " AND (a.emp_identifier = ph.emp_identifier) "
	    + "  AND SYSDATE BETWEEN ph.start_date AND ph.end_date"
	    + "  AND am.current_ind = 'Y'"
	    + "  AND am.adj_identifier is not null"
	    + "  AND am.request_date BETWEEN ah.appointment_date AND ah.departure_or_end_date"
	    + "	 AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1"
		+ "  WHERE ap1.appt_identifier = ah.appt_identifier"
		+ "  AND ap1.end_date >= ap1.start_date)"
	    + "  AND SYSDATE between PH.START_DATE AND PH.END_DATE"
	    + "  AND sec.user_id = ?1"
	    + "  AND sec.module_id IN ('APRF013', 'APRF009','APRF010', 'APRF012', 'APRF011', 'APRF008') "
	    + "  AND sec.module_id = rc.ref_code"
	    + "  AND rc.VALUE = aa.next_action_code"
	    + "  AND ah.department =  DECODE (sec.department, 'AL', ah.department, sec.department)"
	    + "  AND ah.agency = DECODE (sec.agency, 'AL', ah.agency, sec.agency)"
	    + "  AND ah.tku = DECODE (sec.tku, 'AL', ah.tku, sec.tku)";*/

private static final String MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE_FIRST = " where (ae.appt_identifier = a.appt_identifier)"
	    + "  AND (a.appt_identifier = ah.appt_identifier) "
	    + "  AND (ae.appt_identifier = ah.appt_identifier) "
	    + "  AND (am.advm_identifier = ad.advm_identifier)"
	    + "  AND (ae.adev_identifier = am.adev_identifier)"
	    + "  AND (am.advm_identifier = aa.advm_identifier)"
	    + "  AND (a.emp_identifier = ph.emp_identifier) "
	    + "  AND SYSDATE BETWEEN ph.start_date AND ph.end_date"
	    + "  AND am.current_ind = 'Y'"	 
	    + "  AND am.request_date BETWEEN ah.appointment_date AND ah.departure_or_end_date"
	    + "	 AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1"
		+ "  WHERE ap1.appt_identifier = ah.appt_identifier"
		+ "  AND ap1.end_date >= ap1.start_date)"
	    + "  AND SYSDATE between PH.START_DATE AND PH.END_DATE"
	    + "  AND EXISTS "
        + " (SELECT 'X' "
        + "  FROM SECURITY SEC"
        + "  WHERE     sec.user_id = ?"
        + "  AND sec.module_id = 'APRF007'"
        + "  AND sec.module_id = rc.ref_code"
        + "  AND ah.department = DECODE (sec.department,'AL', ah.department, sec.department)"
        + "  AND ah.agency = DECODE (sec.agency, 'AL', ah.agency, sec.agency)"
        + "  AND ah.tku = DECODE (sec.tku, 'AL', ah.tku, sec.tku))"
        + "  AND rc.VALUE = aa.next_action_code";

/*private static final String MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE_SECOND = " WHERE (ae.appt_identifier = a.appt_identifier)"
	    + "  AND (a.appt_identifier = ah.appt_identifier) "
	    + "  AND (ae.appt_identifier = ah.appt_identifier) "
	    + "  AND (ae.adev_identifier = am.adev_identifier)"
	    + "  AND (am.advm_identifier = aa.advm_identifier)"
	    + "  AND (a.emp_identifier = ph.emp_identifier) "
	    + "  AND SYSDATE BETWEEN ph.start_date AND ph.end_date"
	    + "  AND am.current_ind = 'Y'"
	    + "  AND am.adj_identifier is not null"
	    + "  AND am.request_date BETWEEN ah.appointment_date AND ah.departure_or_end_date"
	    + "	 AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1"
		+ "  WHERE ap1.appt_identifier = ah.appt_identifier"
		+ "  AND ap1.end_date >= ap1.start_date)"
	    + "  AND SYSDATE between PH.START_DATE AND PH.END_DATE"
	    + "  AND sec.user_id = ?1"
	    + "  AND sec.module_id ='APRF007'"
	    + "  AND sec.module_id = rc.ref_code"
	    + "  AND rc.VALUE = aa.next_action_code"
	    + "  AND ah.department =  DECODE (sec.department, 'AL', ah.department, sec.department)"
	    + "  AND ah.agency = DECODE (sec.agency, 'AL', ah.agency, sec.agency)"
	    + "  AND ah.tku = DECODE (sec.tku, 'AL', ah.tku, sec.tku)";*/

private static final String SW_DEPT = " and ah.department = ?2";

private static final String SW_AGY = " and ah.agency = ?3";

private static final String SW_TKU = " and ah.tku = ?4";

private static final String SW_EMPID = " and a.emp_identifier = ?2";

private static final String SW_LASTNAME = " and UPPER(ph.last_name) LIKE UPPER(?5)";


//expense

private final static String SECURITY_SUB_SELECT_QUERY = "AND EXISTS (select sec.user_id from security sec where sec.user_id = ?2 "
	+ " and sec.module_id = ?3 and(ah.department between decode(sec.department,'AL','00',sec.department) " 
	+ " and decode(sec.department,'AL','99',sec.department)) "
	+ " and (ah.agency between decode(sec.agency,'AL','00',sec.agency) and decode(sec.agency,'AL','99',sec.agency)) "
	+ " and (ah.tku between decode(sec.tku,'AL','000',sec.tku) and decode(sec.tku,'AL','999',sec.tku)))";

private static final String STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE = "SELECT /*+ opt_param('optimizer_index_cost_adj',1) */ /* INDEX_ASC(SEC SEC_USER_MOD_DAT_IDX) */ ph.last_name lastName, ph.middle_name middleName, ph.first_name firstName, " +
						 " 'Expense' transactionType," +
					"a.emp_identifier empIdentifier,em.expev_identifier requestId," +
					 "em.expm_identifier masterId," +
					 "ah.appt_identifier apptIdentifier," +
					"em.adj_identifier adjIdentifier,em.EXP_DATE_FROM fromDate, em.EXP_DATE_TO toDate,nvl(SUM(ed.DOLLAR_AMOUNT),0) dollarAmount, "
					 + " ah.department department," 
					    + " ah.agency agency,"
					    + " ah.tku tku, "
					    + " ah.dept_code deptCode, " 
					    + " ea.modified_date lastActionDate ";

private static final String TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE = "SELECT /*+ opt_param('optimizer_index_cost_adj',1) */ /* INDEX_ASC(SEC SEC_USER_MOD_DAT_IDX) */ ph.last_name lastName, ph.middle_name middleName, ph.first_name firstName, " +
" 'TREQ' transactionType," +
"a.emp_identifier empIdentifier,trm.treqe_identifier requestId," +
"trm.treqm_identifier masterId," +
"ah.appt_identifier apptIdentifier," +
"'' adjIdentifier,trm.TREQ_DATE_FROM fromDate, trm.TREQ_DATE_TO toDate,nvl(SUM(treqd.DOLLAR_AMOUNT),0) dollarAmount, "
+ " ah.department department," 
+ " ah.agency agency,"
+ " ah.tku tku, "
+ " ah.dept_code deptCode, " 
+ " tra.modified_date lastActionDate ";

private static final String STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE = "FROM EXPENSE_EVENTS ee,EXPENSE_DETAILS ed,EXPENSE_ACTIONS ea," +
				"APPOINTMENTS a,PERSONNEL_HISTORIES ph,APPOINTMENT_HISTORIES ah," +
				"EXPENSE_MASTERS em,REF_CODES rc";

private static final String TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE = "FROM TRAVEL_REQ_EVENTS tre, TRAVEL_REQ_DETAILS treqd, TRAVEL_REQ_ACTIONS tra," +
"APPOINTMENTS a,PERSONNEL_HISTORIES ph,APPOINTMENT_HISTORIES ah," +
"TRAVEL_REQ_MASTERS trm,REF_CODES rc";

private static final String STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE = " WHERE (ee.appt_identifier = a.appt_identifier)" +
"AND (a.appt_identifier = ah.appt_identifier) " +
"AND (ee.appt_identifier = ah.appt_identifier) " +
"AND (em.expm_identifier = ed.expm_identifier(+)) " +
"AND (ee.expev_identifier = em.expev_identifier) " +
"AND (em.expm_identifier = ea.expm_identifier) " +
"AND (a.emp_identifier = ph.emp_identifier) " +
"AND em.current_ind = 'Y' " +
//"AND em.EXP_DATE_TO BETWEEN ah.appointment_date AND ah.departure_or_end_date " +
"AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1 " +
"WHERE ap1.appt_identifier = ah.appt_identifier " +
"AND ap1.end_date >= ap1.start_date) " +
"AND SYSDATE BETWEEN PH.START_DATE AND PH.END_DATE " +
"AND EXISTS (SELECT 'X' FROM SECURITY SEC " +
"  where sec.user_id = ?1 " +
"AND sec.module_id IN ('APRF015', 'APRF014', 'APRF013', 'APRF009','APRF010', 'APRF012', 'APRF011', 'APRF008') " +
"AND sec.module_id = rc.ref_code " +
"AND ah.department =  DECODE (sec.department, 'AL', ah.department, sec.department) " +
"AND ah.agency = DECODE (sec.agency, 'AL', ah.agency, sec.agency) " +
"AND ah.tku = DECODE (sec.tku, 'AL', ah.tku, sec.tku)) " +
"AND rc.VALUE = ea.next_action_code ";

private static final String TREQ_STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE = " WHERE (tre.appt_identifier = a.appt_identifier)" +
"AND (a.appt_identifier = ah.appt_identifier) " +
"AND (tre.appt_identifier = ah.appt_identifier) " +
"AND (trm.treqm_identifier = treqd.treqm_identifier(+)) " +
"AND (tre.treqe_identifier = trm.treqe_identifier) " +
"AND (trm.treqm_identifier = tra.treqm_identifier) " +
"AND (a.emp_identifier = ph.emp_identifier) " +
"AND trm.current_ind = 'Y' " +
//"AND em.EXP_DATE_TO BETWEEN ah.appointment_date AND ah.departure_or_end_date " +
"AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1 " +
"WHERE ap1.appt_identifier = ah.appt_identifier " +
"AND ap1.end_date >= ap1.start_date) " +
"AND SYSDATE BETWEEN PH.START_DATE AND PH.END_DATE " +
"AND EXISTS (SELECT 'X' FROM SECURITY SEC " +
"  where sec.user_id = ?1 " +
"AND sec.module_id IN ('APRF015', 'APRF014', 'APRF013', 'APRF009','APRF010', 'APRF012', 'APRF011', 'APRF008') " +
"AND sec.module_id = rc.ref_code " +
"AND ah.department =  DECODE (sec.department, 'AL', ah.department, sec.department) " +
"AND ah.agency = DECODE (sec.agency, 'AL', ah.agency, sec.agency) " +
"AND ah.tku = DECODE (sec.tku, 'AL', ah.tku, sec.tku)) " +
"AND rc.VALUE = tra.next_action_code ";



private static final String MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE = " WHERE (ee.appt_identifier = a.appt_identifier)" +
"AND (a.appt_identifier = ah.appt_identifier) " +
"AND (ee.appt_identifier = ah.appt_identifier) " +
"AND (em.expm_identifier = ed.expm_identifier (+)) " +
"AND (ee.expev_identifier = em.expev_identifier) " +
"AND (em.expm_identifier = ea.expm_identifier) " +
"AND (a.emp_identifier = ph.emp_identifier) " +
"AND em.current_ind = 'Y' " +
//"AND em.EXP_DATE_TO BETWEEN ah.appointment_date AND ah.departure_or_end_date " +
"AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1 " +
"WHERE ap1.appt_identifier = ah.appt_identifier " +
"AND ap1.end_date >= ap1.start_date) " +
"AND SYSDATE BETWEEN PH.START_DATE AND PH.END_DATE " +
"AND EXISTS (SELECT 'X' FROM SECURITY SEC " +
"  where sec.user_id = ?1 " +
"AND sec.module_id = 'APRF007'" +
"AND sec.module_id = rc.ref_code " +
"AND ah.department =  DECODE (sec.department, 'AL', ah.department, sec.department) " +
"AND ah.agency = DECODE (sec.agency, 'AL', ah.agency, sec.agency) " +
"AND ah.tku = DECODE (sec.tku, 'AL', ah.tku, sec.tku)) " +
"AND rc.VALUE = ea.next_action_code ";

private static final String TREQ_MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE = " WHERE (tre.appt_identifier = a.appt_identifier)" +
"AND (a.appt_identifier = ah.appt_identifier) " +
"AND (tre.appt_identifier = ah.appt_identifier) " +
"AND (trm.treqm_identifier = treqd.treqm_identifier (+)) " +
"AND (tre.treqe_identifier = trm.treqe_identifier) " +
"AND (trm.treqm_identifier = tra.treqm_identifier) " +
"AND (a.emp_identifier = ph.emp_identifier) " +
"AND trm.current_ind = 'Y' " +
"AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1 " +
"WHERE ap1.appt_identifier = ah.appt_identifier " +
"AND ap1.end_date >= ap1.start_date) " +
"AND SYSDATE BETWEEN PH.START_DATE AND PH.END_DATE " +
"AND EXISTS (SELECT 'X' FROM SECURITY SEC " +
"  where sec.user_id = ?1 " +
"AND sec.module_id = 'APRF007'" +
"AND sec.module_id = rc.ref_code " +
"AND ah.department =  DECODE (sec.department, 'AL', ah.department, sec.department) " +
"AND ah.agency = DECODE (sec.agency, 'AL', ah.agency, sec.agency) " +
"AND ah.tku = DECODE (sec.tku, 'AL', ah.tku, sec.tku)) " +
"AND rc.VALUE = tra.next_action_code ";

private static final String STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE = " GROUP BY ph.last_name, ph.middle_name, ph.first_name, " +
"a.emp_identifier, ah.appt_identifier,em.expev_identifier,em.adj_identifier,  " +
"em.exp_date_from,  em.exp_date_to,em.expm_IDENTIFIER, ah.department,ah.agency,ah.tku,ah.dept_code, ea.modified_date";

private static final String TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE = " GROUP BY ph.last_name, ph.middle_name, ph.first_name, " +
"a.emp_identifier, ah.appt_identifier,trm.treqe_identifier,'',  " +
"trm.treq_date_from,  trm.treq_date_to,trm.treqm_IDENTIFIER, ah.department,ah.agency,ah.tku,ah.dept_code, tra.modified_date";


/**
 * This method retrieves all advances awaiting approval for all employees
 * for a given supervisor with supervisor employee id
 * 
 * @param supervisorEmpId
 * @return List<ApprovalTransactionBean>
 */

public List<VAdvApprovalList> findAdvancesAwaitingApproval(
	    int supervisorEmpId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdvancesAwaitingApproval(int supervisorEmpId)");		
	}
	String finderQuery = "select al from VAdvApprovalList al where al.supervisorEmpidentifier = ?1";

	List<VAdvApprovalList> approvalTransList = (List<VAdvApprovalList>) entityManager
		.createQuery(finderQuery).setParameter(1, supervisorEmpId)
		.getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdvancesAwaitingApproval(int supervisorEmpId)");		
	}

	return approvalTransList;
}

/**
 * This method retrieves all adjusted advances awaiting approval for all
 * employees for a given supervisor with supervisor employee id
 * 
 * @param supervisorEmpId
 * @return List<ApprovalTransactionBean>
 */
public List<VAdvApprovalList> findAdjustedAdvancesAwaitingApproval(
	    int supervisorEmpId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdjustedAdvancesAwaitingApproval(int supervisorEmpId)");		
	}
	String finderQuery = "select al from VAdvApprovalList al where al.supervisorEmpidentifier = ?1 and al.adjIdentifier is not null";

	List<VAdvApprovalList> approvalTransList = (List<VAdvApprovalList>) entityManager
		.createQuery(finderQuery).setParameter(1, supervisorEmpId)
		.getResultList();

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdjustedAdvancesAwaitingApproval(int supervisorEmpId)");		
	}
	return approvalTransList;
}

/**
 * This method retrieves all non adjusted advances awaiting approval for all
 * employees for a given supervisor with supervisor employee id
 * 
 * @param supervisorEmpId
 * @return List<ApprovalTransactionBean>
 */

public List<VAdvApprovalList> findNonAdjustedAdvancesAwaitingApproval(
	    int supervisorEmpId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findNonAdjustedAdvancesAwaitingApproval(int supervisorEmpId)");		
	}
	
	String finderQuery = "select al from VAdvApprovalList al where al.supervisorEmpidentifier = ?1 and al.adjIdentifier is null";

	List<VAdvApprovalList> approvalTransList = (List<VAdvApprovalList>) entityManager
		.createQuery(finderQuery).setParameter(1, supervisorEmpId)
		.getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findNonAdjustedAdvancesAwaitingApproval(int supervisorEmpId)");		
	}
	

	return approvalTransList;
}

/**
 * This method retrieves all advances awaiting approval for Supervisor when
 * the "All employees", All Transactions is selected
 * 
 * @param userId
 * @return List<ApprovalTransactionBean>
 */
public List<ApprovalTransactionBean> findAllAdvancesAwaitingApprovalAllEmployees(
	    String userId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAllAdvancesAwaitingApprovalAllEmployees( String userId)");		
	}
	

	String finderQuery = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
		+ MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE_FIRST
		+ " order by 18";
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAllAdvancesAwaitingApprovalAllEmployees( String userId)");		
	}
	

	return entityManager.createNativeQuery(finderQuery,
		ApprovalTransactionBean.class).setParameter(1, userId)
		.getResultList();
}

/**
 * This method retrieves all advances awaiting approval for Supervisor when
 * the All employees, Adjustments Only is selected
 * 
 * @param userId
 * @return ApprovalTransactionBean
 */
/*public List<ApprovalTransactionBean> findAdjustedAdvancesAwaitingApprovalAllEmployees(
	    String userId) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdjustedAdvancesAwaitingApprovalAllEmployees( String userId)");		
	}
	String finderQuery = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
		+ MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE_FIRST
		+ " AND am.adj_identifier is not null" + " UNION "
		+ SW_APPR_SELECT_CLAUSE_SECOND + SW_FROM_CLAUSE_SECOND
		+ MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE_SECOND
		+ " AND am.adj_identifier is not null" 
		+ " order by 18";
	;

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdjustedAdvancesAwaitingApprovalAllEmployees( String userId)");		
	}
	
	return entityManager.createNativeQuery(finderQuery,
		ApprovalTransactionBean.class).setParameter(1, userId)
		.getResultList();
}
*/
/**
 * This method retrieves all advances awaiting approval for Supervisor when
 * the All employees, Adjustments Only is selected
 * 
 * @param userId
 * @return ApprovalTransactionBean
 */
/*public List<ApprovalTransactionBean> findNonAdjustedAdvancesAwaitingApprovalAllEmployees(
	    String userId) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findNonAdjustedAdvancesAwaitingApprovalAllEmployees( String userId)");		
	}
	
	String finderQuery = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
		+ MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE_FIRST
		+ " AND am.adj_identifier is null" + " UNION "
		+ SW_APPR_SELECT_CLAUSE_SECOND + SW_FROM_CLAUSE_SECOND
		+ MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE_SECOND
		+ " AND am.adj_identifier is null" 
		+ " order by 18";
	;

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findNonAdjustedAdvancesAwaitingApprovalAllEmployees( String userId)");		
	}
	
	return entityManager.createNativeQuery(finderQuery,
		ApprovalTransactionBean.class).setParameter(1, userId)
		.getResultList();
}
*/
/**
 * This method retrieves all statewide advances awaiting approval for all
 * employees, This method is called when "AL,AL,AL is selected for
 * Dept,Agy,Tku
 * 
 * @param supervisorEmpId
 * @return List<ApprovalTransactionBean>
 */
/*public List<ApprovalTransactionBean> findSwAdvancesAwaitingApproval(
	    String userId) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findSwAdvancesAwaitingApproval( String userId)");		
	}
	
	List<ApprovalTransactionBean> result = null;
	String query = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
		+ SW_WHERE_CLUASE_FIRST + " UNION "
		+ SW_APPR_SELECT_CLAUSE_SECOND + SW_FROM_CLAUSE_SECOND
		+ SW_WHERE_CLUASE_SECOND;

	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class).setParameter(1, userId)
		.getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findSwAdvancesAwaitingApproval( String userId)");		
	}
	

	return result;

}
*/
/**
 * This method retrieves all statewide advances awaiting approval for all
 * employees, This method is called when user has selected Dept, AL for
 * Agency, AL for TKU
 * 
 * @param userId
 * @param dept
 * @return List<ApprovalTransactionBean>
 */
public List<ApprovalTransactionBean> findSwAdvancesAwaitingApproval(
	    String userId, String dept) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findSwAdvancesAwaitingApproval( String userId, String dept)");		
	}
	
	
	List<ApprovalTransactionBean> result = null;
	String query = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
		+ SW_WHERE_CLUASE_FIRST + SW_DEPT;

	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class).setParameter(1, userId)
		.setParameter(2, dept).getResultList();

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findSwAdvancesAwaitingApproval( String userId, String dept)");		
	}
	
	
	return result;

}

/**
 * This method retrieves all statewide advances awaiting approval for all
 * employees, This method is called when user has selected Dept, Agency and
 * AL for TKU
 * 
 * @param userID
 * @param dept
 * @param agy
 * @return List<ApprovalTransactionBean>
 */
public List<ApprovalTransactionBean> findSwAdvancesAwaitingApproval(
	    String userId, String dept, String agy) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findSwAdvancesAwaitingApproval( String userId, String dept,String agy)");		
	}
	

	List<ApprovalTransactionBean> result = null;
	String query = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
		+ SW_WHERE_CLUASE_FIRST + SW_DEPT + SW_AGY;

	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class).setParameter(1, userId)
		.setParameter(2, dept).setParameter(3, agy).getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findSwAdvancesAwaitingApproval( String userId, String dept,String agy)");		
	}
	

	return result;

}

/**
 * This method retrieves all statewide advances awaiting approval for all
 * employees, This method is called when user has selected Dept, Agency and
 * TKU
 * 
 * @param userId
 * @param dept
 * @param agy
 * @param tku
 * @return List<ApprovalTransactionBean>
 */
public List<ApprovalTransactionBean> findSwAdvancesAwaitingApproval(
	    String userId, String dept, String agy, String tku) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findSwAdvancesAwaitingApproval( String userId, String dept,String agy, String tku)");		
	}
	

	List<ApprovalTransactionBean> result = null;
	String query = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
		+ SW_WHERE_CLUASE_FIRST + SW_DEPT + SW_AGY + SW_TKU;

	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class).setParameter(1, userId)
		.setParameter(2, dept).setParameter(3, agy)
		.setParameter(4, tku).getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findSwAdvancesAwaitingApproval( String userId, String dept,String agy, String tku)");		
	}
	

	return result;

}

/**
 * This method retrieves all statewide advances awaiting approval for all
 * employees, This method is called when user has selected entered only
 * employee number.
 * 
 * @param userId
 * @param empId
 * @return List<ApprovalTransactionBean>
 */
public List<ApprovalTransactionBean> findSwAdvancesAwaitingApprovalEmp(
	    String userId, String empId, String dept) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findSwAdvancesAwaitingApprovalEmp( String userId, String empId)");		
	}
	
	List<ApprovalTransactionBean> result = null;
	String query = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
		+ SW_WHERE_CLUASE_FIRST + SW_EMPID + " and ah.department = ?3";

	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class)
		.setParameter(1, userId)
		.setParameter(2, empId)
		.setParameter(3, dept)
		.getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findSwAdvancesAwaitingApprovalEmp( String userId, String empId)");		
	}

	return result;

}

	/**
	 * Find advances awaiting approval for the given empId in dept, agency and tku. The results should be filtered by the user's(usedId) security.
	 * 
	 * @param userId
	 * @param empId
	 * @param dept
	 * @param agency
	 * @return
	 */
	public List<ApprovalTransactionBean> findSwAdvancesAwaitingApprovalEmpIdDeptAgency(String userId, String empId, String dept, String agency) {
		String query = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
			+ SW_WHERE_CLUASE_FIRST + SW_EMPID + " and ah.department = ?3 " + " and ah.agency = ?4";
	
		return (List<ApprovalTransactionBean>)entityManager.createNativeQuery(query,
			ApprovalTransactionBean.class)
			.setParameter(1, userId)
			.setParameter(2, empId)
			.setParameter(3, dept)
			.setParameter(4, agency)
			.getResultList();
	}

	/**
	 * Find advances awaiting approval for the given empId in dept, agency and tku. The results should be filtered by the user's(usedId) security.
	 * 
	 * @param userId
	 * @param empId
	 * @param dept
	 * @param agency
	 * @param tku
	 * @return
	 */
	public List<ApprovalTransactionBean> findSwAdvancesAwaitingApprovalEmpIdDeptAgencyTku(String userId, String empId, String dept, String agency, String tku) {
		String query = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
			+ SW_WHERE_CLUASE_FIRST + SW_EMPID + " and ah.department = ?3 " + " and ah.agency = ?4 " + " and ah.tku = ?5";
	
		return (List<ApprovalTransactionBean>)entityManager.createNativeQuery(query,
			ApprovalTransactionBean.class)
			.setParameter(1, userId)
			.setParameter(2, empId)
			.setParameter(3, dept)
			.setParameter(4, agency)
			.setParameter(5, tku)
			.getResultList();
	}

/**
 * This method retrieves all statewide advances awaiting approval for all
 * employees, This method is called when user has selected Dept, Agency,TKU
 * and LastName
 * 
 * @param userId
 * @param dept
 * @param agy
 * @param tku
 * @return List<ApprovalTransactionBean>
 */
public List<ApprovalTransactionBean> findSwAdvancesAwaitingApproval(
	    String userId, String dept, String agy, String tku, String lastName) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findSwAdvancesAwaitingApproval(  String userId, String dept, String agy, String tku, String lastName)");		
	}
	
	List<ApprovalTransactionBean> result = null;
	String query = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
		+ SW_WHERE_CLUASE_FIRST + SW_DEPT + SW_AGY + SW_TKU
		+ SW_LASTNAME;

	lastName += "%";
	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class).setParameter(1, userId)
		.setParameter(2, dept).setParameter(3, agy)
		.setParameter(4, tku).setParameter(5, lastName).getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findSwAdvancesAwaitingApproval(  String userId, String dept, String agy, String tku, String lastName)");		
	}

	return result;

}

public List<ApprovalTransactionBean> findSwAdvancesAwaitingApprovalByDeptLn(
	    String userId, String dept, String lastName) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findSwAdvancesAwaitingApprovalByDeptLn(  String userId, String dept, String lastName)");		
	}
	
	List<ApprovalTransactionBean> result = null;
	String query = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
		+ SW_WHERE_CLUASE_FIRST + SW_DEPT 
		+ SW_LASTNAME;

	lastName += "%";
	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class).setParameter(1, userId)
		.setParameter(2, dept)
		.setParameter(5, lastName).getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findSwAdvancesAwaitingApprovalByDeptLn(  String userId, String dept,String lastName)");		
	}

	return result;

}

public List<ApprovalTransactionBean> findSwAdvancesAwaitingApprovalByDeptAgyLn(
	    String userId, String dept, String agy,String lastName) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findSwAdvancesAwaitingApprovalByDeptAgyLn(  String userId, String dept, String agy,String lastName)");		
	}
	
	List<ApprovalTransactionBean> result = null;
	String query = SW_APPR_SELECT_CLAUSE_FIRST + SW_FROM_CLAUSE_FIRST
		+ SW_WHERE_CLUASE_FIRST + SW_DEPT 
		+ SW_AGY 
		+ SW_LASTNAME;

	lastName += "%";
	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class).setParameter(1, userId)
		.setParameter(2, dept)
		.setParameter(3, agy)
		.setParameter(5, lastName).getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findSwAdvancesAwaitingApprovalByDeptAgyLn(  String userId, String dept, String agy,String lastName)");		
	}

	return result;

}
/******************* DAO Methods to retrieve the transaction list for Manager Approvals when "My Employee" is selected **********************/ 

/**
 * This method retrieves all non-adjusted expenses awaiting approval 
 * for all employees for a given supervisor with userId as provided
 * @param supervisorEmpId
 * @return
 */
public List<VExpApprovalList> findNonAdjustedExpensesAwaitingApproval(
		int supervisorEmpId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findNonAdjustedExpensesAwaitingApproval(int supervisorEmpId) : return List<VExpApprovalList>");
	}
	
	String finderQuery = "SELECT vexp from VExpApprovalList vexp where vexp.hrmnEmpIdentifier = : supervisorEmpId and vexp.adjIdentifier is null";
	
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
public List<VExpApprovalList> findExpensesAwaitingApproval(
		int supervisorEmpId) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findExpensesAwaitingApproval(int supervisorEmpId) : return List<VExpApprovalList>");
	}
	
	String finderQuery = "SELECT vexp from VExpApprovalList vexp where vexp.hrmnEmpIdentifier = : supervisorEmpId";
	List<VExpApprovalList> approvalList = (List<VExpApprovalList>) entityManager.createQuery(finderQuery)
			.setParameter("supervisorEmpId", supervisorEmpId).getResultList();
	

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findExpensesAwaitingApproval(int supervisorEmpId)");
	}
	
	return approvalList;
}

public List<VTreqApprovalList> findTravelRequisitionsAwaitingApproval(
		int supervisorEmpId) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findTravelRequisitionsAwaitingApproval(int supervisorEmpId) : return List<VTreqApprovalList>");
	}
	
	String finderQuery = "SELECT vtreq from VTreqApprovalList vtreq where vtreq.hrmnEmpIdentifier = : supervisorEmpId";
	List<VTreqApprovalList> approvalList = (List<VTreqApprovalList>) entityManager.createQuery(finderQuery)
			.setParameter("supervisorEmpId", supervisorEmpId).getResultList();
	

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findTravelRequisitionsAwaitingApproval(int supervisorEmpId)");
	}
	
	return approvalList;
}

/**
 * This method retrieves all adjusted expenses 
 * awaiting approval for all employees for a given supervisor.
 * @param supervisorEmpId
 * @return
 */

public List<VExpApprovalList> findAdjustedExpensesAwaitingApproval(
		int supervisorEmpId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdjustedExpensesAwaitingApproval(int supervisorEmpId) : return List<VExpApprovalList>");
	}
	
	String finderQuery = "SELECT vexp from VExpApprovalList vexp where vexp.hrmnEmpIdentifier = : supervisorEmpId and vexp.adjIdentifier is not null";
	
	List<VExpApprovalList> approvalList = (List<VExpApprovalList>) entityManager.createQuery(finderQuery)
			.setParameter("supervisorEmpId", supervisorEmpId).getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdjustedExpensesAwaitingApproval(int supervisorEmpId)");
	}
	return approvalList;
	
}

/********************************* DAO Methods to retrieve Expenses where All employees is selected ******************************/


/**
 * This method retrieves all expenses pending approval for Supervisor when the "All employees" is selected
 * @param userId
 * @return List
 */
public List<ApprovalTransactionBean> findAllExpensesAwaitingApprovalAllEmployees(String userId){
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAllExpensesAwaitingApprovalAllEmployees(String userId) : return List<ExpenseApprovalTransactionBean>");
	}
	
	String finderQuery = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE +
						 MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE +
						 STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE
						 + " order by ea.modified_date";
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAllExpensesAwaitingApprovalAllEmployees(String userId)");
	}
	
	return entityManager.createNativeQuery(finderQuery,ApprovalTransactionBean.class).setParameter(1, userId).getResultList();
}

public List<ApprovalTransactionBean> findAllTravelRequisitionsAwaitingApprovalAllEmployees(String userId){
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAllTravelRequisitionsAwaitingApprovalAllEmployees(String userId) : return List<ApprovalTransactionBean>");
	}
	
	String finderQuery = TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE +
						 TREQ_MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE +
						 TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE
						 + " order by tra.modified_date";
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAllTravelRequisitionsAwaitingApprovalAllEmployees(String userId)");
	}
	
	return entityManager.createNativeQuery(finderQuery,ApprovalTransactionBean.class).setParameter(1, userId).getResultList();
}

/***
 * This method retrieves adjusted expenses pending approval for Supervisor when the "All employees" is selected
 * @param userId
 * @return List
 */

public List<ApprovalTransactionBean> findAdjustedExpensesAwaitingApprovalAllEmployees(String userId){
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdjustedExpensesAwaitingApprovalAllEmployees(String userId) : return List<ExpenseApprovalTransactionBean>");
	}
	
	String finderQuery = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE +
						 MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE + " AND em.adj_identifier is not null" +
						 " order by ea.modified_date";
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdjustedExpensesAwaitingApprovalAllEmployees(String userId)");
	}
	return entityManager.createNativeQuery(finderQuery,ApprovalTransactionBean.class).setParameter(1, userId).getResultList();
}


/***
 * This method retrieves adjusted expenses for Supervisor when the "All employees" is selected
 * @param userId
 * @return List
 */
public List<ApprovalTransactionBean> findNonAdjustedExpensesAwaitingApprovalAllEmployees(String userId){
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findNonAdjustedExpensesAwaitingApprovalAllEmployees(String userId) : return List<ExpenseApprovalTransactionBean>");
	}
	
	String finderQuery = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE +
						 MANAGER_APPROVAL_QUERY_WHERE_CLAUSE_FOR_ALL_EMPLOYEE + " AND em.adj_identifier is null" +
						 " order by ea.modified_date";
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findNonAdjustedExpensesAwaitingApprovalAllEmployees(String userId)");
	}
	
	return entityManager.createNativeQuery(finderQuery,ApprovalTransactionBean.class).setParameter(1, userId).getResultList();
}

/***********************************************Statewide Transaction retrieval methods***************************************************/	
/**
 * This method will retrieve all the expenses awaiting approval for all employees working 
 * under the given department, agency and tku and with the given lastname. 
 * @param dept
 * @param agency
 * @param tku
 * @param lastname
 * @param userId
 * @return list of expenses
 */
public List<ApprovalTransactionBean> findStateWideExpensesAwaitingApproval(
		String dept, String agency, String tku, String lastName,
		String userId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findStateWideExpensesAwaitingApproval(String dept, String agency, String tku, String lastName,String userId) : return List<ExpenseApprovalTransactionBean>");
	}
	
	//Build the query
	String finderQuery = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE + 
	STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE  + SW_DEPT + SW_AGY +SW_TKU + SW_LASTNAME + STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;  
	lastName += "%";
	List<ApprovalTransactionBean> approvalTransList= entityManager.createNativeQuery(finderQuery,
		ApprovalTransactionBean.class).setParameter(1, userId)
			.setParameter(2, dept).setParameter(3, agency)
			.setParameter(4, tku).setParameter(5, lastName).getResultList();

	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findStateWideExpensesAwaitingApproval(String dept, String agency, String tku, String lastName,String userId)");
	}
	return approvalTransList;
}

public List<ApprovalTransactionBean> findStateWideExpensesAwaitingApprovalByDeptLn(
		String dept,String lastName,
		String userId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findStateWideExpensesAwaitingApprovalByDeptLn(String dept,String lastName,String userId) : return List<ExpenseApprovalTransactionBean>");
	}
	
	//Build the query
	String finderQuery = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE + 
	STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE  + SW_DEPT + SW_LASTNAME + STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;
	lastName += "%";
	List<ApprovalTransactionBean> approvalTransList= entityManager.createNativeQuery(finderQuery,
		ApprovalTransactionBean.class).setParameter(1, userId)
			.setParameter(2, dept)
			.setParameter(5, lastName).getResultList();

	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findStateWideExpensesAwaitingApprovalByDeptLn(String dept,String lastName,String userId)");
	}
	return approvalTransList;
}

public List<ApprovalTransactionBean> findStateWideExpensesAwaitingApprovalByDeptAgyLn(
		String dept, String agency, String lastName,
		String userId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findStateWideExpensesAwaitingApprovalByDeptAgyLn(String dept, String agency,String lastName,String userId) : return List<ExpenseApprovalTransactionBean>");
	}
	
	//Build the query
	String finderQuery = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE + 
	STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE  + SW_DEPT + SW_AGY + SW_LASTNAME + STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;
	lastName += "%";
	List<ApprovalTransactionBean> approvalTransList= entityManager.createNativeQuery(finderQuery,
		ApprovalTransactionBean.class).setParameter(1, userId)
			.setParameter(2, dept)
			.setParameter(3, agency)
			.setParameter(5, lastName).getResultList();

	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findStateWideExpensesAwaitingApprovalByDeptAgyLn(String dept, String agency,String lastName,String userId)");
	}
	return approvalTransList;
}

/**
* This method retrieves all state-wide expenses awaiting approval for all
* employees, This method is called when "AL,AL,AL is selected for
* Dept,Agy,Tku
* 
* @param userId
* @return
*/
public List<ApprovalTransactionBean> findStateWideExpensesAwaitingApproval(
    String userId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findStateWideExpensesAwaitingApproval(String userId) : return List<ExpenseApprovalTransactionBean>");
	}

	List<ApprovalTransactionBean> result = null;
	//Build the query
	String query =  STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE + 
					STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;

	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class).setParameter(1, userId).getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findStateWideExpensesAwaitingApproval(String userId)");
	}
	return result;
}

/**
* This method retrieves all state-wide expenses awaiting approval for all
* employees, This method is called when user has selected Dept, AL for
* Agency, AL for TKU
* 
* @param userId
* @param dept
* @return
*/
public List<ApprovalTransactionBean> findStateWideExpensesAwaitingApproval(
    String userId, String dept) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findStateWideExpensesAwaitingApproval(String userId, String dept) : return List<ExpenseApprovalTransactionBean>");
	}

	List<ApprovalTransactionBean> approvalTransList = null;
	//Build the query
	String query = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
	+ STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + SW_DEPT + STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE; 
	
	approvalTransList = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class)
			.setParameter(1, userId)
			.setParameter(2, dept).getResultList();

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findStateWideExpensesAwaitingApproval(String userId, String dept)");
	}
	return approvalTransList;

}

public List<ApprovalTransactionBean> findStateWideTravelRequisitionsAwaitingApproval(
	    String userId, String dept) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findStateWideTravelRequisitionsAwaitingApproval(String userId, String dept) : return List<ApprovalTransactionBean>");
		}

		List<ApprovalTransactionBean> approvalTransList = null;
		//Build the query
		String query = TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
		+ TREQ_STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + SW_DEPT + TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE; 

		approvalTransList = entityManager.createNativeQuery(query,
			ApprovalTransactionBean.class)
				.setParameter(1, userId)
				.setParameter(2, dept).getResultList();

		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findStateWideExpensesAwaitingApproval(String userId, String dept)");
		}
		return approvalTransList;

	}

/**
* This method retrieves all state-wide expenses awaiting approval for all
* employees, This method is called when user has selected Dept, Agency and
* AL for TKU
* 
* @param userID
* @param dept
* @param agy
* @return
*/
public List<ApprovalTransactionBean> findStateWideTravelRequisitionsAwaitingApproval(
    String userId, String dept, String agy) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findStateWideExpensesAwaitingApproval(String userId, String dept, String agy) : return List<ExpenseApprovalTransactionBean>");
	}

	List<ApprovalTransactionBean> result = null;
	//Build the query
	String query = TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
		+ TREQ_STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + SW_DEPT + SW_AGY + TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE; 

	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class)
			.setParameter(1, userId)
			.setParameter(2, dept).setParameter(3, agy).getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findStateWideExpensesAwaitingApproval(String userId, String dept, String agy)");
	}

	return result;
}

/**
* This method retrieves all state-wide expenses awaiting approval for all
* employees, This method is called when user has selected Department, Agency and
* TKU
* 
* @param userId
* @param dept
* @param agy
* @param tku
* @return
*/
public List<ApprovalTransactionBean> findStateWideExpensesAwaitingApproval(
    String userId, String dept, String agy, String tku) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findStateWideExpensesAwaitingApproval(String userId, String dept, String agy, String tku) : return List<ExpenseApprovalTransactionBean>");
	}

	List<ApprovalTransactionBean> result = null;
	//Build the query
	String query = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
		+ STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE+ SW_DEPT + SW_AGY + SW_TKU + STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE; 

	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class).setParameter(1, userId)
		.setParameter(2, dept).setParameter(3, agy)
		.setParameter(4, tku).getResultList();

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findStateWideExpensesAwaitingApproval(String userId, String dept, String agy, String tku)");
	}
	return result;
}

/**
* This method retrieves all state-wide expenses awaiting approval for all
* employees, This method is called when user has selected entered only
* employee number.
* 
* @param userId
* @param empId
* @return
*/
public List<ApprovalTransactionBean> findStateWideExpensesAwaitingApprovalEmployee(
    String userId, String empId, String dept) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findStateWideExpensesAwaitingApproval(String userId, String empId) : return List<ExpenseApprovalTransactionBean>");
	}

	List<ApprovalTransactionBean> result = null;
	//Build the query
	String query = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
		+ STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + SW_EMPID + " and ah.department = ?3 " + STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;

	result = entityManager.createNativeQuery(query,
		ApprovalTransactionBean.class)
		.setParameter(1, userId)
		.setParameter(2, empId)
		.setParameter(3, dept)
		.getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findStateWideExpensesAwaitingApproval(String userId, String empId)");
	}

	return result;
}

	/**
	 * Find expenses awaiting approval for the given empId in given dept, agency. The results should be filtered by the given userId security.
	 * 
	 * @param empId
	 * @param dept
	 * @param agency
	 * @param userId
	 * @return list
	 */
	public List<ApprovalTransactionBean> findSWExpensesAwaitingApprovalByEmpIdDeptAgency(String empId, String dept, String agency, String userId) {
		String query = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
			+ STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + SW_EMPID 
			+ " and ah.department = ?3 and ah.agency = ?4 "
			+ STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;

		return (List<ApprovalTransactionBean>)entityManager.createNativeQuery(query, ApprovalTransactionBean.class)
															.setParameter(1, userId)
															.setParameter(2, empId)
															.setParameter(3, dept)
															.setParameter(4, agency)
															.getResultList();
	}

	/**
	 * Find expenses awaiting approval for the given empId in given dept, agency, tku. The results should be filtered by the given userId security.
	 * 
	 * @param empId
	 * @param dept
	 * @param agency
	 * @param tku
	 * @param userId
	 * @return list
	 */
	public List<ApprovalTransactionBean> findSWExpensesAwaitingApprovalByEmpIdDeptAgencyTku(String empId, String dept, String agency, String tku, String userId) {
		String query = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
			+ STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + SW_EMPID 
			+ " and ah.department = ?3 and ah.agency = ?4 and ah.tku = ?5 "
			+ STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;

		return (List<ApprovalTransactionBean>)entityManager.createNativeQuery(query, ApprovalTransactionBean.class)
															.setParameter(1, userId)
															.setParameter(2, empId)
															.setParameter(3, dept)
															.setParameter(4, agency)
															.setParameter(5, tku)
															.getResultList();
	}

	public boolean isExpenseInApprovalPathAfterFirstLevelApproval(String action){
		long count = (Long)entityManager.createNativeQuery("select count(*) from REF_CODES where value=?1", Long.class).setParameter(1, action).getResultList().get(0) ;
		return count > 0;
	}
	
	public List<ApprovalTransactionBean> findSWTravelRequisitionsAwaitingApprovalByEmpIdDeptAgencyTku(String empId, String dept, String agency, String tku, String userId) {
		String query = TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
			+ TREQ_STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + SW_EMPID 
			+ " and ah.department = ?3 and ah.agency = ?4 and ah.tku = ?5 "
			+ TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;

		return (List<ApprovalTransactionBean>)entityManager.createNativeQuery(query, ApprovalTransactionBean.class)
															.setParameter(1, userId)
															.setParameter(2, empId)
															.setParameter(3, dept)
															.setParameter(4, agency)
															.setParameter(5, tku)
															.getResultList();
	}

	public List<ApprovalTransactionBean> findSWTravelRequisitionsAwaitingApprovalByEmpIdDeptAgency(String empId, String dept, String agency, String userId) {
		String query = TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
			+ TREQ_STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + SW_EMPID 
			+ " and ah.department = ?3 and ah.agency = ?4 "
			+ TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;

		return (List<ApprovalTransactionBean>)entityManager.createNativeQuery(query, ApprovalTransactionBean.class)
															.setParameter(1, userId)
															.setParameter(2, empId)
															.setParameter(3, dept)
															.setParameter(4, agency)
															.getResultList();
	}
	
	public List<ApprovalTransactionBean> findStateWideTravelRequisitionsAwaitingApprovalEmployee(
		    String userId, String empId, String dept) {
			
			if(logger.isDebugEnabled()){
				logger.debug("Enter method :: findStateWideTravelRequisitionsAwaitingApproval(String userId, String empId) : return List<ExpenseApprovalTransactionBean>");
			}

			List<ApprovalTransactionBean> result = null;
			//Build the query
			String query = TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
				+ TREQ_STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + SW_EMPID + " and ah.department = ?3 " + TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;

			result = entityManager.createNativeQuery(query,
				ApprovalTransactionBean.class)
				.setParameter(1, userId)
				.setParameter(2, empId)
				.setParameter(3, dept)
				.getResultList();
			
			if(logger.isDebugEnabled()){
				logger.debug("Exit method :: findStateWideTravelRequisitionsAwaitingApproval(String userId, String empId)");
			}

			return result;
		}
	
	public List<ApprovalTransactionBean> findStateWideTravelRequisitionsAwaitingApproval(
			String dept, String agency, String tku, String lastName,
			String userId) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findStateWideTravelRequisitionsAwaitingApproval(String dept, String agency, String tku, String lastName,String userId) : return List<ExpenseApprovalTransactionBean>");
		}
		
		//Build the query
		String finderQuery = TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE + 
		TREQ_STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE  + SW_DEPT + SW_AGY +SW_TKU + SW_LASTNAME + TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;  
		lastName += "%";
		List<ApprovalTransactionBean> approvalTransList= entityManager.createNativeQuery(finderQuery,
			ApprovalTransactionBean.class).setParameter(1, userId)
				.setParameter(2, dept).setParameter(3, agency)
				.setParameter(4, tku).setParameter(5, lastName).getResultList();

		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findStateWideTravelRequisitionsAwaitingApproval(String dept, String agency, String tku, String lastName,String userId)");
		}
		return approvalTransList;
	}
	
	public List<ApprovalTransactionBean> findStateWideTravelRequisitionsAwaitingApprovalByDeptAgyLn(
			String dept, String agency, String lastName,
			String userId) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findStateWideTravelRequisitionsAwaitingApproval(String dept, String agency,String lastName,String userId) : return List<ExpenseApprovalTransactionBean>");
		}
		
		//Build the query
		String finderQuery = TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE + 
		TREQ_STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE  + SW_DEPT + SW_AGY + SW_LASTNAME + TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;
		lastName += "%";
		List<ApprovalTransactionBean> approvalTransList= entityManager.createNativeQuery(finderQuery,
			ApprovalTransactionBean.class).setParameter(1, userId)
				.setParameter(2, dept)
				.setParameter(3, agency)
				.setParameter(5, lastName).getResultList();

		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findStateWideExpensesAwaitingApprovalByDeptAgyLn(String dept, String agency,String lastName,String userId)");
		}
		return approvalTransList;
	}
	
	public List<ApprovalTransactionBean> findStateWideTravelRequisitionsAwaitingApprovalByDeptLn(
			String dept,String lastName,
			String userId) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findStateWideTravelRequisitionsAwaitingApproval(String dept,String lastName,String userId) : return List<ExpenseApprovalTransactionBean>");
		}
		
		//Build the query
		String finderQuery = TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE + 
		TREQ_STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE  + SW_DEPT + SW_LASTNAME + TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;
		lastName += "%";
		List<ApprovalTransactionBean> approvalTransList= entityManager.createNativeQuery(finderQuery,
			ApprovalTransactionBean.class).setParameter(1, userId)
				.setParameter(2, dept)
				.setParameter(5, lastName).getResultList();

		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findStateWideTravelRequisitionsAwaitingApproval(String dept,String lastName,String userId)");
		}
		return approvalTransList;
	}
	
	public List<ApprovalTransactionBean> findStateWideTravelRequisitionsAwaitingApproval(
		    String userId, String dept, String agy, String tku) {
			
			if(logger.isDebugEnabled()){
				logger.debug("Enter method :: findStateWideExpensesAwaitingApproval(String userId, String dept, String agy, String tku) : return List<ExpenseApprovalTransactionBean>");
			}

			List<ApprovalTransactionBean> result = null;
			//Build the query
			String query = TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
				+ TREQ_STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE+ SW_DEPT + SW_AGY + SW_TKU + TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE; 

			result = entityManager.createNativeQuery(query,
				ApprovalTransactionBean.class).setParameter(1, userId)
				.setParameter(2, dept).setParameter(3, agy)
				.setParameter(4, tku).getResultList();

			if(logger.isDebugEnabled()){
				logger.debug("Exit method :: findStateWideTravelRequisitionsAwaitingApproval(String userId, String dept, String agy, String tku)");
			}
			return result;
		}
	
	public List<ApprovalTransactionBean> findStateWideExpensesAwaitingApproval(
		    String userId, String dept, String agy) {
			
			if(logger.isDebugEnabled()){
				logger.debug("Enter method :: findStateWideTravelRequisitionsAwaitingApproval(String userId, String dept, String agy) : return List<ExpenseApprovalTransactionBean>");
			}

			List<ApprovalTransactionBean> result = null;
			//Build the query
			String query = STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE
				+ STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + SW_DEPT + SW_AGY + STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE; 

			result = entityManager.createNativeQuery(query,
				ApprovalTransactionBean.class)
					.setParameter(1, userId)
					.setParameter(2, dept).setParameter(3, agy).getResultList();
			
			if(logger.isDebugEnabled()){
				logger.debug("Exit method :: findStateWideExpensesAwaitingApproval(String userId, String dept, String agy)");
			}

			return result;
		}
	
	public List<ApprovalTransactionBean> findStateWideTravelRequisitionsAwaitingApproval(
		    String userId) {
			
			if(logger.isDebugEnabled()){
				logger.debug("Enter method :: findStateWideTravelRequisitionsAwaitingApproval(String userId) : return List<ExpenseApprovalTransactionBean>");
			}

			List<ApprovalTransactionBean> result = null;
			//Build the query
			String query =  TREQ_STATEWIDE_APPROVAL_QUERY_SELECT_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_FROM_CLAUSE + 
			TREQ_STATEWIDE_APPROVAL_QUERY_WHERE_CLAUSE + TREQ_STATEWIDE_APPROVAL_QUERY_GROUP_BY_CLAUSE;

			result = entityManager.createNativeQuery(query,
				ApprovalTransactionBean.class).setParameter(1, userId).getResultList();
			
			if(logger.isDebugEnabled()){
				logger.debug("Exit method :: findStateWideTravelRequisitionsAwaitingApproval(String userId)");
			}
			return result;
		}
}
