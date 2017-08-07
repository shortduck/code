package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.core.AdvanceActions;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetails;
import gov.michigan.dit.timeexpense.model.core.AdvanceErrors;
import gov.michigan.dit.timeexpense.model.core.AdvanceEvents;
import gov.michigan.dit.timeexpense.model.core.AdvanceHistory;
import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.core.VAdvanceList;
import gov.michigan.dit.timeexpense.model.db.Actions;
import gov.michigan.dit.timeexpense.model.display.AdvApprovalTransaction;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.openjpa.persistence.OpenJPAEntityManager;

/**
 * @author ChiduraS
 * 
 */
@SuppressWarnings("unchecked")
public class AdvanceDAO extends AbstractDAO {
   private  Logger logger = Logger.getLogger(AdvanceDAO.class);

   
    private final String ADV_LIST_BY_APPT_ID = " SELECT emp.emp_identifier,   events.appt_identifier,"
	    + "events.adev_identifier, masters.request_date, masters.advm_identifier,"
	    + " masters.from_date, masters.TO_DATE, masters.paid_pp_end_date PAID_PPE_DATE, masters.advance_reason,"
	    + " details.dollar_amount, masters.permanent_adv_ind,  masters.revision_number,"
	    + " action.action_code, masters.manual_deposit_amount, masters.adj_identifier,"
	    + " events.outstanding_amount amount_outstanding, masters.orig_paid_date "
	    + " FROM appointments appt,  appointment_histories ah,   advance_events events,  advance_masters masters,"
	    + " advance_details details,  employees emp,  advance_actions action";
    
    
    private final String ADV_LIST_BY_APPT_ID_PROC = " SELECT emp.emp_identifier,   events.appt_identifier,"
	    + "events.adev_identifier, masters.request_date, masters.advm_identifier,"
	    + " masters.from_date, masters.TO_DATE, calendar_date PAID_PPE_DATE, masters.advance_reason,"
	    + " details.dollar_amount, masters.permanent_adv_ind,  masters.revision_number,"
	    + " action.action_code, masters.manual_deposit_amount, masters.adj_identifier,"
	    + " events.outstanding_amount amount_outstanding, masters.orig_paid_date"
	    + " FROM appointments appt,  appointment_histories ah,   advance_events events,  advance_masters masters,"
	    + " advance_details details,  employees emp,  advance_actions action, calendar";

    private final String ADV_LIST_BY_APPT_ID_WHERE_1 = "  WHERE     emp.emp_identifier = appt.emp_identifier "
	    + " AND appt.appt_identifier = ah.appt_identifier "
	    + " AND masters.request_date between ah.appointment_date and ah.departure_or_end_date"
	    + "	AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ah1"
		+ " WHERE ah1.appt_identifier = ah.appt_identifier"
		+ " AND ah1.end_date >= ah1.start_date AND masters.request_date between ah1.appointment_date and ah1.departure_or_end_date)"
	    + "    AND events.appt_identifier = appt.appt_identifier "
	    + "    AND events.appt_identifier = ?1 "
	    + "    AND events.adev_identifier = masters.adev_identifier "
	    + "    AND masters.current_ind = 'Y' "
	    + "    AND masters.status in ('APPR','SUBM','XTCT','HSNT','RJCT')"
	    + "    AND masters.advm_identifier = details.advm_identifier "
	    + "    AND action.advm_identifier = masters.advm_identifier "
	    + "    AND action.adva_identifier = "
	    + "          (SELECT MAX (adva_identifier) "
	    + "           FROM advance_actions adv_action "
	    + "           WHERE action.advm_identifier = adv_action.advm_identifier "
	    + "                 AND adv_action.action_code != 'AUDT') ";
    
    private final String ADV_LIST_BY_APPT_ID_WHERE_1_PROC = "  WHERE     emp.emp_identifier = appt.emp_identifier "
	    + " AND appt.appt_identifier = ah.appt_identifier "
	    + " AND masters.request_date between ah.appointment_date and ah.departure_or_end_date"
	    + "	AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ah1"
		+ " WHERE ah1.appt_identifier = ah.appt_identifier"
		+ " AND ah1.end_date >= ah1.start_date AND masters.request_date between ah1.appointment_date and ah1.departure_or_end_date)"
	    + "    AND events.appt_identifier = appt.appt_identifier "
	    + "    AND events.appt_identifier = ?1 "
	    + "    AND events.adev_identifier = masters.adev_identifier "
	    + "    AND masters.current_ind = 'Y' "
	    + "    AND masters.status = 'PROC'"
	    + "    AND masters.advm_identifier = details.advm_identifier "
	    + "    AND action.advm_identifier = masters.advm_identifier "
	    + "    AND action.adva_identifier = "
	    + "          (SELECT MAX (adva_identifier) "
	    + "           FROM advance_actions adv_action "
	    + "           WHERE action.advm_identifier = adv_action.advm_identifier "
	    + "                 AND adv_action.action_code != 'AUDT') "
	    + "  AND  ((calendar_date BETWEEN masters.paid_pp_end_date "
		+ "								  AND masters.paid_pp_end_date + 14) "
		+ "			AND pay_day_ind = 'Y')";

    private final String ADV_LIST_BY_APPT_ID_SELECT_2 = " SELECT emp.emp_identifier,   events.appt_identifier, events.adev_identifier,"
	    + " masters.request_date,  masters.advm_identifier,  masters.from_date,"
	    + " masters.TO_DATE,  masters.paid_pp_end_date PAID_PPE_DATE,   masters.advance_reason,"
	    + " details.dollar_amount, masters.permanent_adv_ind,"
	    + " masters.revision_number, actions.action_code, masters.manual_deposit_amount,"
	    + "  masters.adj_identifier,"
	    + " events.outstanding_amount amount_outstanding, masters.orig_paid_date "
	    + " FROM appointments appt, appointment_histories ah, advance_events events, advance_masters masters,  advance_details details,advance_actions actions,"
	    + "  employees emp";

    private final String ADV_LIST_BY_APPT_ID_WHERE_2 =

    " WHERE  emp.emp_identifier = appt.emp_identifier "
	    + "    AND appt.appt_identifier = ah.appt_identifier "
	    +"     AND actions.advm_identifier = masters.advm_identifier " 
	    + " AND masters.request_date between ah.appointment_date and ah.departure_or_end_date AND ah.end_date = (select max(ah1.end_date) from APPOINTMENT_HISTORIES ah1 "
		+ " WHERE ah1.appt_identifier = ah.appt_identifier "
		+ " AND ah1.end_date >= ah1.start_date AND masters.request_date between ah1.appointment_date and ah1.departure_or_end_date) "
	    + "    AND events.appt_identifier = appt.appt_identifier "
	    + "    AND events.appt_identifier = ?1 "
	    + "    AND events.adev_identifier = masters.adev_identifier "
	    + "    AND masters.advm_identifier = details.advm_identifier "
	    + "    AND masters.current_ind = 'Y' "
	    + "    AND masters.advm_identifier = details.advm_identifier "
	    + "    AND NOT EXISTS (SELECT *  FROM advance_actions aa  WHERE masters.advm_identifier = aa.advm_identifier and aa.action_code!='SPLT') ";//CHANDANA 26445
    
    private final String ADV_LIST_BY_APPT_ID_SELECT_3 = " SELECT emp.emp_identifier,   events.appt_identifier, events.adev_identifier,"
	    + " masters.request_date,  masters.advm_identifier,  masters.from_date,"
	    + " masters.TO_DATE,  masters.paid_pp_end_date PAID_PPE_DATE,   masters.advance_reason,"
	    + " details.dollar_amount, masters.permanent_adv_ind,"
	    + " masters.revision_number,' ' ACTION_CODE, masters.manual_deposit_amount,"
	    + "  masters.adj_identifier,"
	    + " events.outstanding_amount amount_outstanding, masters.orig_paid_date "
	    + " FROM appointments appt, appointment_histories ah, advance_events events, advance_masters masters,  advance_details details,"
	    + "  employees emp";

    private final String ADV_LIST_BY_APPT_ID_WHERE_3 =

    " WHERE  emp.emp_identifier = appt.emp_identifier "
	    + "    AND appt.appt_identifier = ah.appt_identifier "
	    + " AND masters.request_date between ah.appointment_date and ah.departure_or_end_date AND ah.end_date = (select max(ah1.end_date) from APPOINTMENT_HISTORIES ah1 "
		+ " WHERE ah1.appt_identifier = ah.appt_identifier "
		+ " AND ah1.end_date >= ah1.start_date AND masters.request_date between ah1.appointment_date and ah1.departure_or_end_date) "
	    + "    AND events.appt_identifier = appt.appt_identifier "
	    + "    AND events.appt_identifier = ?1 "
	    + "    AND events.adev_identifier = masters.adev_identifier "
	    + "    AND masters.advm_identifier = details.advm_identifier "
	    + "    AND masters.current_ind = 'Y' "
	    + "    AND masters.advm_identifier = details.advm_identifier "
	    + "    AND NOT EXISTS (SELECT *  FROM advance_actions aa  WHERE masters.advm_identifier = aa.advm_identifier ) ";
    
    private final String ADV_LIST_ADJ_IDENTIFIER = " AND masters.adj_identifier ";

    private final String SECURITY_QUERY = "(SELECT sec.user_id FROM SECURITY sec WHERE sec.user_id = ?2 AND sec.module_id = ?3 AND "
	    + " (ah.department BETWEEN DECODE(sec.department,'AL','00',sec.department) AND DECODE(sec.department,'AL','99',sec.department)) AND "
	    + " (ah.agency BETWEEN DECODE(sec.agency,'AL','00',sec.agency) AND DECODE(sec.agency,'AL','99',sec.agency)) AND "
	    + " (ah.tku BETWEEN DECODE(sec.tku,'AL','000',sec.tku) AND DECODE(sec.tku,'AL','999',sec.tku))) ";


    public AdvanceDAO() {
    }

    public AdvanceDAO(EntityManager em) {
	super(em);
    }

   

    /**
     * This method retrieves the Non adjusted advances by employee id
     * 
     * @param empIdentifier
     * @return list of advances
     */

    public List<VAdvanceList> findNonAdjustedAdvanceListByEmployeeId(
	    int employeeId) {
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findNonAdjustedAdvanceListByEmployeeId( int employeeId)");		
	}
	String finderQuery = "select al from VAdvanceList al where al.empIdentifier = :empIdentifier and al.adjIdentifier is null order by  al.requestDate DESC";
	Query query = entityManager.createQuery(finderQuery);
	query.setParameter("empIdentifier", employeeId);

	List<VAdvanceList> advanceList = (List<VAdvanceList>) query
		.getResultList();

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findNonAdjustedAdvanceListByEmployeeId( int employeeId)");		
	}
	return advanceList;
    }

    /**
     * This method retrieves the adjusted advances by employee id
     * 
     * @param empIdentifier
     * @return list of advances
     */

    public List<VAdvanceList> findAdjustedAdvanceListByEmployeeId(int employeeId) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdjustedAdvanceListByEmployeeId(int employeeId)");		
	}
	String finderQuery = "select al from VAdvanceList al where al.empIdentifier = :empIdentifier and al.adjIdentifier is not null order by  al.requestDate DESC";
	Query query = entityManager.createQuery(finderQuery);
	query.setParameter("empIdentifier", employeeId);

	List<VAdvanceList> advanceList = (List<VAdvanceList>) query
		.getResultList();

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdjustedAdvanceListByEmployeeId(int employeeId)");		
	}
	return advanceList;
    }

    /**
     * This method retrieves the both adjusted and non adjusted advances by
     * employee id
     * 
     * @param empIdentifier
     * @return list of advances
     */

    public List<VAdvanceList> findAdvanceListByEmployeeId(int employeeId) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdvanceListByEmployeeId(int employeeId)");		
	}
	String finderQuery = "select al from VAdvanceList al where al.empIdentifier = :empIdentifier order by  al.requestDate desc";
	Query query = entityManager.createQuery(finderQuery);
	query.setParameter("empIdentifier", employeeId);

	List<VAdvanceList> advanceList = (List<VAdvanceList>) query
		.getResultList();

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdvanceListByEmployeeId(int employeeId)");		
	}
	
	return advanceList;
    }

    /**
     * This method retrieves the Non adjusted advances by appointment Id
     * 
     * @param apptId
     * @param userId
     * @param moduleId
     * @return list of advances
     */
    public List<VAdvanceList> findNonAdjustedAdvanceListByApptID(int apptId,
	    String userId, String moduleId) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findNonAdjustedAdvanceListByApptID(int apptId, String userId, String moduleId)");		
	}
	
	List<VAdvanceList> result = null;

	if (userId == null) {
	    return result;
	}

	String query = ADV_LIST_BY_APPT_ID + ADV_LIST_BY_APPT_ID_WHERE_1
		+ ADV_LIST_ADJ_IDENTIFIER + "IS NULL " + " AND EXISTS "
		+ SECURITY_QUERY + "UNION" + ADV_LIST_BY_APPT_ID_SELECT_2
		+ ADV_LIST_BY_APPT_ID_WHERE_2 + ADV_LIST_ADJ_IDENTIFIER
		+ "IS NULL " + " AND EXISTS" + SECURITY_QUERY
		+ " order by 4 desc";

	result = entityManager.createNativeQuery(query, VAdvanceList.class)
		.setParameter(1, apptId).setParameter(2, userId).setParameter(
			3, moduleId).getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findNonAdjustedAdvanceListByApptID(int apptId, String userId, String moduleId)");		
	}
	

	return result;
    }

    /**
     * This method retrieves the adjusted advances by appointment Id
     * 
     * @param apptId
     * @param userId
     * @param moduleId
     * @return list of advances
     */

    public List<VAdvanceList> findAdjustedAdvanceListByApptID(int apptId,
	    String userId, String moduleId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdjustedAdvanceListByApptID(int apptId, String userId, String moduleId)");		
	}
	
	
	List<VAdvanceList> result = null;

	if (userId == null) {
	    return result;
	}

	String query = ADV_LIST_BY_APPT_ID + ADV_LIST_BY_APPT_ID_WHERE_1
		+ ADV_LIST_ADJ_IDENTIFIER + "IS NOT NULL " + " AND EXISTS "
		+ SECURITY_QUERY + "UNION" + ADV_LIST_BY_APPT_ID_SELECT_2
		+ ADV_LIST_BY_APPT_ID_WHERE_2 + ADV_LIST_ADJ_IDENTIFIER
		+ "IS NOT NULL " + " AND EXISTS" + SECURITY_QUERY
		+ " order by 4 desc";

	result = entityManager.createNativeQuery(query, VAdvanceList.class)
		.setParameter(1, apptId).setParameter(2, userId).setParameter(
			3, moduleId).getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdjustedAdvanceListByApptID(int apptId, String userId, String moduleId)");		
	}

	return result;
    }

    /**
     * This method retrieves  both adjusted and non adjusted advances by
     * appointment Id
     * 
     * @param apptId
     * @param userId
     * @param moduleId
     * @return list of advances
     */

    public List<VAdvanceList> findAdvanceListByApptID(int apptId,
	    String userId, String moduleId) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdvanceListByApptID(int apptId, String userId, String moduleId)");		
	}
	
	List<VAdvanceList> result = null;

	if (userId == null) {
	    return result;
	}

	String query =  ADV_LIST_BY_APPT_ID_PROC + ADV_LIST_BY_APPT_ID_WHERE_1_PROC +
	           " AND EXISTS " + SECURITY_QUERY + "UNION" +
	         ADV_LIST_BY_APPT_ID + ADV_LIST_BY_APPT_ID_WHERE_1
		   + " AND EXISTS " + SECURITY_QUERY + "UNION"
	   	+ ADV_LIST_BY_APPT_ID_SELECT_2 + ADV_LIST_BY_APPT_ID_WHERE_2
		+ " AND EXISTS" + SECURITY_QUERY + "UNION" + ADV_LIST_BY_APPT_ID_SELECT_3 + ADV_LIST_BY_APPT_ID_WHERE_3 + " AND EXISTS" + SECURITY_QUERY + " order by 4 desc";

	result = entityManager.createNativeQuery(query, VAdvanceList.class)
		.setParameter(1, apptId).setParameter(2, userId).setParameter(
			3, moduleId).getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdvanceListByApptID(int apptId, String userId, String moduleId)");		
	}

	return result;

    }

    /**
     * This method retrieves all advances by advanceMasterId
     * 
     * @param advanceMasterId
     * @return list of advance
     */
    public AdvanceMasters findAdvanceByMasterId(int advanceMasterId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdvanceByMasterId(int apptId, String userId, String moduleId)");		
	}
	
	AdvanceMasters result = entityManager.find(AdvanceMasters.class,
		advanceMasterId);
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdvanceByMasterId(int apptId, String userId, String moduleId)");		
	}
	
	return result;
    }

    /**
     * This method retrieves amount of liquidation for an expense master
     * 
     * @param expenseMasterId
     * @return Sum of advanceLiquidations
     */
    
    public double findExpenseLiquidationByMasterId(int expenseMasterId) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findExpenseLiquidationByMasterId(int expenseMasterId)");		
	}
	
	String query = "SELECT SUM(al.AMOUNT) FROM Advance_Liquidations al,Advance_Masters am " +
			"WHERE al.advm_identifier = am.advm_identifier " +
			"AND al.EXPM_Identifier = ?1 " +
			"AND am.revision_number = (Select Max(master.revision_number) FROM Advance_Masters master " +
			"WHERE master.adev_Identifier=am.adev_Identifier " +
			"AND master.status IN ('APPR', 'HSNT', 'XTCT', 'PROC')) ";
	
	 BigDecimal result = null;
	try {
	     result = (BigDecimal) entityManager.createNativeQuery(query)
		.setParameter(1, expenseMasterId).getSingleResult();
	} catch (NoResultException e) {
	    result = null;
	   
	}	
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findExpenseLiquidationByMasterId(int expenseMasterId)");		
	}
	if (result == null){
	    return 0;
	}else {
	    return result.doubleValue(); 
	}
	

    }

    /** This method finds the Advance Outstanding list by employeeId
     * @param employeeId
     * @return List<VAdvanceList>
     */
    public List<VAdvanceList> findAdvanceOutstandingListByEmpID(int employeeId) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdvanceOutstandingListByEmpI(int employeeId)");		
	}
	
	List<VAdvanceList> result = null;

	if (employeeId == 0) {
	    return result;
	}
	
	String query = "SELECT APPT_IDENTIFIER, REQUEST_DATE, ADVM_IDENTIFIER, ADEV_IDENTIFIER, DOLLAR_AMOUNT," +
	" MANUAL_DEPOSIT_AMOUNT, FROM_DATE, TO_DATE, PAID_PPE_DATE, PERMANENT_ADV_IND, AMOUNT_OUTSTANDING, ADVANCE_REASON" +
	" FROM V_Advance_List WHERE EMP_IDENTIFIER = ?1 AND ACTION_CODE in ('APPR','XTCT','PROC','HSNT')";

	result = entityManager.createNativeQuery(query, VAdvanceList.class)
		.setParameter(1, employeeId).getResultList();

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdvanceOutstandingListByEmpID(int employeeId)");		
	}
	return result;

    }
// 05/05/2009
    /**
     * This method retrieves all advances awaiting approval for all employees
     * for a given supervisor with supervisor employee id
     * 
     * @param supervisorEmpId
     * @return List<AdvApprovalTransaction>
     */

    public List<AdvApprovalTransaction> findAdvancesAwaitingApproval(
	    int supervisorEmpId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdvancesAwaitingApproval(int supervisorEmpId)");		
	}
	String finderQuery = "select al from VAdvApprovalList al where al.supervisorEmpidentifier = ?1";

	List<AdvApprovalTransaction> approvalTransList = (List<AdvApprovalTransaction>) entityManager
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
     * @return List<AdvApprovalTransaction>
     */
    public List<AdvApprovalTransaction> findAdjustedAdvancesAwaitingApproval(
	    int supervisorEmpId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdjustedAdvancesAwaitingApproval(int supervisorEmpId)");		
	}
	String finderQuery = "select al from VAdvApprovalList al where al.supervisorEmpidentifier = ?1 and al.adjIdentifier is not null";

	List<AdvApprovalTransaction> approvalTransList = (List<AdvApprovalTransaction>) entityManager
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
     * @return List<AdvApprovalTransaction>
     */

    public List<AdvApprovalTransaction> findNonAdjustedAdvancesAwaitingApproval(
	    int supervisorEmpId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findNonAdjustedAdvancesAwaitingApproval(int supervisorEmpId)");		
	}
	
	String finderQuery = "select al from VAdvApprovalList al where al.supervisorEmpidentifier = ?1 and al.adjIdentifier is null";

	List<AdvApprovalTransaction> approvalTransList = (List<AdvApprovalTransaction>) entityManager
		.createQuery(finderQuery).setParameter(1, supervisorEmpId)
		.getResultList();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findNonAdjustedAdvancesAwaitingApproval(int supervisorEmpId)");		
	}
	

	return approvalTransList;
    }

    


    /**
     * This method persists the advance Masters entity with its associated child
     * entities
     * 
     * @param advanceMasters
     * @return The advance master that is persisted
     */

    public AdvanceMasters saveAdvanceMaster(AdvanceMasters advanceMasters) {

		if (logger.isDebugEnabled()) {
			logger.debug("Enter method :: saveAdvanceMaster(AdvanceMasters advanceMasters)");
		}

		if (advanceMasters.getAdvmIdentifier() == null) {
			entityManager.persist(advanceMasters);
			return advanceMasters;
		} else {
			OpenJPAEntityManager openJpaEntityManager = (OpenJPAEntityManager) entityManager;
			openJpaEntityManager.dirty(advanceMasters, "modifiedUserId");
			return entityManager.merge(advanceMasters);
		}

    }

    /**
     * This method persists the Advance events and all associated child entities
     * 
     * @param advanceEvents
     * @return boolean
     */

    public boolean saveAdvanceEvent(AdvanceEvents advanceEvents) {
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: saveAdvanceEvent(AdvanceEvents advanceEvents)");		
	}

	boolean isSaved = false;
	
	if(advanceEvents.getAdevIdentifier() == null) entityManager.persist(advanceEvents);
	else entityManager.merge(advanceEvents);
	
	isSaved = true;

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: saveAdvanceEvent(AdvanceEvents advanceEvents)");		
	}
	return isSaved;
    }

    /**
     * This method performs a JPA remove operation to delete advance.
     * 
     * @param advanceMasterId
     * @return boolean 
     */
    public boolean deleteAdvanceMaster(int advanceMasterId) {
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: deleteAdvanceMaster(int advanceMasterId)");		
	}
	boolean isDeleted = true;
	String finderQuery = "select am from AdvanceMasters am where am.advmIdentifier = :advmIdentifier";

	logger
		.info("Enter JPA operation : Removing ExpenseMasters Entity in deleteExpense");
	AdvanceMasters advanceMaster = (AdvanceMasters) getEntityManager()
		.createQuery(finderQuery).setParameter("advmIdentifier",
			advanceMasterId).getSingleResult();

	entityManager.remove(advanceMaster);

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: deleteAdvanceMaster(int advanceMasterId)");		
	}
	return isDeleted;
    }

    /**
     * This method sets the status on the advance master as reject
     * 
     * @param advanceMaster
     * @return AdvanceMasters
     */

    public AdvanceMasters rejectAdvance(AdvanceMasters advanceMaster) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: rejectAdvance(AdvanceMasters advanceMaster)");		
	}
	advanceMaster.setStatus("RJCT");
	AdvanceMasters rejectedAdvance = entityManager.merge(advanceMaster);

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: rejectAdvance(AdvanceMasters advanceMaster)");		
	}
	return rejectedAdvance;
    }

    /**
     * This method persists the Advance Liquidations
     * 
     * @param advanceLiquidations
     * @return boolean
     */

    public boolean saveLiquidations(AdvanceLiquidations advanceLiquidations) {
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: saveLiquidations(AdvanceLiquidations advanceLiquidations)");		
	}
	boolean isSaved = false;

	//entityManager.persist(advanceLiquidations);
	advanceLiquidations = entityManager.merge(advanceLiquidations);
	//entityManager.flush();

	isSaved = true;

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: saveLiquidations(AdvanceLiquidations advanceLiquidations)");		
	}

	return isSaved;
	
	
    }

    /**
     * This method returns Advance Events
     * 
     * @param advanceEventId
     * @return AdvanceEvents
     */

   
    public AdvanceEvents findAdvanceByAdvanceEventId(int advanceEventId) {
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdvanceByAdvanceEventId(int advanceEventId)");		
	}
	AdvanceEvents result = entityManager.find(AdvanceEvents.class,
		advanceEventId);
	return result;
    }

    /**
     * This method retrieves the maximum revision number given the advance
     * eventId.
     * 
     * @param advanceEventId
     * @return maxRevisionNo int
     */

    public int findMaxRevisionNo(int advanceEventId) {
    	Long maxRev = (Long)entityManager.createNativeQuery("select max(REVISION_NUMBER) from ADVANCE_MASTERS where ADEV_IDENTIFIER=?1", Long.class)
		.setParameter(1, advanceEventId).getResultList().get(0);

    	return maxRev == null ? 0: maxRev.intValue();
    }

    /**
     * This method finds the Advance Master record given the advance event Id
     * and Revision No
     * 
     * @param advanceEventId
     * @param revisionNo
     * @return AdvanceMasters
     */
    public AdvanceMasters findAdvanceByAdvanceEventId(AdvanceEvents advEvent,
	    int revisionNo) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdvanceByAdvanceEventId(AdvanceEvents advEvent,  int revisionNo)");		
	}
	String finderQuery = "select am from AdvanceMasters am where am.adevIdentifier = :adevIdentifier and am.revisionNumber = :revNo";

	if (advEvent == null) {
	    return null;
	}
	AdvanceMasters result = null;

	Query query = entityManager.createQuery(finderQuery);
	query.setParameter("adevIdentifier", advEvent);
	query.setParameter("revNo", revisionNo);

	List<AdvanceMasters> masters = query.getResultList();
	if (masters.size() > 0) {
	    result = masters.get(0);
	}

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findAdvanceByAdvanceEventId(AdvanceEvents advEvent,  int revisionNo)");		
	}
	return result;
    }

    
    /** This Method finds the actioncode
     * @param actionCode
     * @return Actions
     */
    public Actions findActionCode(String actionCode) {
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findActionCode(String actionCode)");		
	}
	
	Actions action = entityManager.find(Actions.class, actionCode);
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findActionCode(String actionCode)");		
	}
	return action;
    }

    // ZH - Added for now. May need to be removed after JPA review
    /**
     * @param advanceActions
     * @return AdvanceActions
     */
    public AdvanceActions saveAdvanceActions(AdvanceActions advanceActions) {
	// entityManager.persist(advanceMasters);
	entityManager.persist(advanceActions);
	return null;
	// entityManager.flush();
    }

    // ZH - Added 03/13/2009
    /** This method deletes Advance errors
     * @param advanceMasters
     * @return AdvanceMasters
     */
    public AdvanceMasters deleteAdvanceErrors(AdvanceMasters advanceMasters) {

    	Iterator it = advanceMasters.getAdvanceErrorsCollection().iterator();
    	while (it.hasNext()) {
    	    AdvanceErrors error = (AdvanceErrors) it.next();
    	    error = entityManager.find(AdvanceErrors.class, error.getAdveIdentifier());
    	    it.remove();
    	    entityManager.remove(error);
    	}

    	entityManager.flush();

    	return advanceMasters;
        }
    
    // ZH - Added 05/22/2009
    /** This method deletes Advance errors
     * @param advanceMasters
     * @return AdvanceMasters
     */
    public AdvanceMasters deleteAdvanceDetailCodingBlocks (AdvanceMasters advanceMasters) {

    	Iterator it = advanceMasters.getAdvanceDetailsCollection().get(0).getAdvanceDetailCodingBlocksCollection().iterator();
    	while (it.hasNext()) {
    		AdvanceDetailCodingBlocks cb = (AdvanceDetailCodingBlocks) it.next();
    		cb = entityManager.find(AdvanceDetailCodingBlocks.class, cb.getAdvdcIdentifier());
    	    it.remove();
    	    entityManager.remove(cb);
    	}
    	
    	entityManager.flush();

    	return advanceMasters;
        }
    
    public void deleteAdvanceDetailCodingBlocks (List<AdvanceDetailCodingBlocks> cbList) {

    	Iterator it = cbList.iterator();
    	while (it.hasNext()) {
    		AdvanceDetailCodingBlocks cb = (AdvanceDetailCodingBlocks) it.next();
    		cb = entityManager.find(AdvanceDetailCodingBlocks.class, cb.getAdvdcIdentifier());
    	    it.remove();
    	    entityManager.remove(cb);

    	}

        }

    // ZH - Added for now. May need to be removed after JPA review
    /** This method saves advance details
     * @param advanceDetails
     * @return AdvanceDetails
     */
    public AdvanceDetails saveAdvanceDetails(AdvanceDetails advanceDetails) {
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: saveAdvanceDetails(AdvanceDetails advanceDetails)");		
	}
	
	// entityManager.persist(advanceMasters);
	entityManager.persist(advanceDetails);
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: saveAdvanceDetails(AdvanceDetails advanceDetails)");		
	}
	
	return null;
	// entityManager.flush();
    }

    /** This methods deletes advance events and related records
     * @param advanceEventId
     * @return boolean
     */
    public boolean deleteAdvanceEvent(int advanceEventId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: deleteAdvanceEvent(int advanceEventId)");		
	}
	boolean isDeleted = false;
	
	// delete any existing references in travel requisitions
	entityManager.createNativeQuery("UPDATE TRAVEL_REQ_EVENTS SET adev_identifier = null where adev_identifier = ?1")
	.setParameter(1, advanceEventId).executeUpdate();

	entityManager.remove(entityManager.find(AdvanceEvents.class,
		advanceEventId));

	isDeleted = true;

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: deleteAdvanceEvent(int advanceEventId)");		
	}
	return isDeleted;

    }

    /**
     * Retrieves the pay type for advances
     * 
     * @param systemCodeValue
     * @return pay type
     */
    public Integer findPayType(String systemCodeValue, Date requestDate) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findPayType(String systemCodeValue)");		
	}
	
	
	String finderQuery = "SELECT pt.pytpIdentifier from PayTypes pt where pt.payType = :systemCodeValue and :requestDate between " +
						"pt.startDate and pt.endDate";
	
	     List<Integer> payTypesList = entityManager.createQuery(finderQuery)
		.setParameter("systemCodeValue", systemCodeValue).setParameter("requestDate",
				requestDate).getResultList();
	     
	     if (!payTypesList.isEmpty())
	    	 return payTypesList.get(0);
	
	return null;
    }
    
    /**
     * Retrieves the expense type for advances. 
     * 
     * @param systemCodeValue
     * @return pay type
     */
    public Integer findAdvanceExpenseType(Integer payTypeCode, Date requestDate) {

	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdvanceExpenseType(Integer payTypeCode, Date requestDate)");		
	}
	
	String finderQuery = "SELECT pytp_identifier from Expense_Types et where et.pytp_identifier = ?1 and ?2 between " +
						"et.start_date and et.end_date";
	
	     List<Integer> payTypesList = entityManager.createNativeQuery(finderQuery, Integer.class)
		.setParameter(1, payTypeCode).setParameter(2,
				requestDate).getResultList();
	     
	     if (!payTypesList.isEmpty())
	    	 return payTypesList.get(0);
	
	return null;
    }

    /**
     * This method retrieves an AdvanceAction where next action code is not
     * null. Primary use for this function is in approvals
     * 
     * @param advanceMaster
     * @return List<AdvanceActions>
     */
    public List<AdvanceActions> findNextAction(AdvanceMasters advanceMaster) {
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findNextAction(AdvanceMasters advanceMaster)");		
	}
	
	String finderQuery = "SELECT AA FROM AdvanceActions AA, AdvanceMasters AM WHERE AA.advmIdentifier=:advmIdentifier AND AM.currentInd = 'Y' AND AA.nextActionCode IS NOT NULL";
	return entityManager.createQuery(finderQuery).setParameter(
		"advmIdentifier", advanceMaster).getResultList();
    }

    /**
     * This method retrieves an AdvanceLiquidations
     * @param advanceMasterId
     * @return List<AdvanceLiquidations>
     */
    public List<AdvanceLiquidations> findAdvanceLiquidationsByMasterId(
	    int advanceMasterId) {
	
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findAdvanceLiquidationsByMasterId(nt advanceMasterId))");		
		}
	
		AdvanceMasters advanceMaster  = findAdvanceByMasterId(advanceMasterId);
		String finderQuery = "select AL from AdvanceLiquidations AL where AL.advanceMaster = :advanceMaster";
		
		List<AdvanceLiquidations> liquidationsList = (List<AdvanceLiquidations>)entityManager.createQuery(finderQuery).setParameter("advanceMaster", advanceMaster).getResultList();
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findAdvanceLiquidationsByMasterId(nt advanceMasterId))");		
		}
	
		return liquidationsList;
    }

    /**
     * This method returns advance outstanding amount
     * 
     * @param advanceMasterId
     * @return AdvanceOutstandingAmount
     */
    public double findAdvanceOutstandingByMasterId(int advanceMasterId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findAdvanceOutstandingByMasterId(int advanceMasterId))");		
	}
	String finderQuery = "SELECT VAL.amountOutStanding FROM VAdvanceList VAL WHERE VAL.advmIdentifier=?1";
	return (Double)entityManager.createQuery(finderQuery).setParameter(1,
		advanceMasterId).getSingleResult();
    }
    
    public AdvanceLiquidations findLiquidationByExpenseAndAdvanceMastId(ExpenseMasters expenseMaster,AdvanceMasters advanceMaster){
    	String finderQuery = "select liq from AdvanceLiquidations liq where liq.expenseMaster=:expenseMaster and liq.advanceMaster=:advanceMaster";
    	
    	AdvanceLiquidations advLiquidation = null;
    	List<AdvanceLiquidations> advanceLiquidateList =  (List<AdvanceLiquidations>)entityManager.createQuery(finderQuery).setParameter("expenseMaster", expenseMaster).setParameter("advanceMaster", advanceMaster).getResultList();
    	if(advanceLiquidateList.size()>0)
    		advLiquidation = advanceLiquidateList.get(0);
    	
    	return advLiquidation;
    	
    }
    
    /** This method updates the status of the expense record subsequent to an approval
	 * 
	 * @param expenseMaster
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public AdvanceMasters updateAdvanceStatus(AdvanceMasters advanceMaster) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : updateAdvanceStatus(advanceMaster)");
		
		String finderQuery = "SELECT AA FROM AdvanceActions AA WHERE AA.advmIdentifier=:advmIdentifier AND AA.nextActionCode IS NOT NULL";
			
			List<AdvanceActions> advActionsList = entityManager.createQuery(finderQuery).setParameter("advmIdentifier",advanceMaster).getResultList();
			if(advActionsList.size()==0)
				advanceMaster.setStatus(IConstants.APPROVED);

		return advanceMaster;
	}
	
	@SuppressWarnings("unchecked")
	public List<AdvanceActions> findLatestAction (AdvanceMasters advanceMaster) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getLatestAction");
		
		String finderQuery = "SELECT AA FROM AdvanceActions AA WHERE AA.advmIdentifier=:advmIdentifier AND AA.actionCode <> 'SPLT' order by AA.advaIdentifier desc";
			
			List<AdvanceActions> advActionsList = entityManager.createQuery(finderQuery).setParameter("advmIdentifier",advanceMaster).getResultList();

		return advActionsList;
	}
	
	/**
	 * Returns the list of current revisions for an employee
	 * @param empId
	 * @return
	 */
	
    public List<AdvanceMasters> findAllCurrentAdvanceMastersByEmpId(int empId) {
    	if(logger.isDebugEnabled()){
    		logger.debug("Enter method :: findAllCurrentAdvanceMastersByEmpId(int empId)");		
    	}
    	String finderQuery =  "SELECT AM FROM AdvanceMasters AM, AdvanceEvents AE, Appointment APPT "  
							+ "WHERE APPT.empId =:empId AND "
							+ "AE.apptIdentifier = APPT.id AND AE.adevIdentifier = AM.adevIdentifier.adevIdentifier AND "
							+ "AM.currentInd = 'Y'";
		List<AdvanceMasters> advMastersList = entityManager.createQuery(finderQuery).setParameter("empId",empId).getResultList();

		return advMastersList;
        }
    
    /**
     * Returns all prior revisions for an Advance Event if in Approved or higher status
     * @param advanceEvent
     * @return
     */
    
    public List<AdvanceMasters> findAllNonCurrentAdvanceMastersByAdvanceEvent(AdvanceEvents advanceEvent) {
    	if(logger.isDebugEnabled()){
    		logger.debug("Enter method :: findAllAdvanceMastersByAdvanceEventId(int advanceEventId)");		
    	}
    	String finderQuery = "SELECT AM FROM AdvanceMasters AM WHERE AM.adevIdentifier=:advanceEventId "
    						+ "AND AM.currentInd = 'N' AND AM.status IN ('APPR', 'HSNT', 'XTCT', 'PROC') "
    						+ "order by AM.revisionNumber desc";
		
		List<AdvanceMasters> advMastersList = entityManager.createQuery(finderQuery).setParameter("advanceEventId",advanceEvent).getResultList();
		logger.info(advMastersList.size());
    	return advMastersList;
        }
    
    public List<AdvanceLiquidations> findAdvanceLiquidations (AdvanceMasters advanceMaster, int expenseMasterId) {

    	String finderQuery = "SELECT AL FROM AdvanceLiquidations AL WHERE AL.advanceMaster =:advanceMaster "
    						+ "AND AL.expenseMasterId =:expenseMasterId ";
		
		List<AdvanceLiquidations> advLiquidationsList = entityManager.createQuery(finderQuery).setParameter("advanceMaster",advanceMaster)
		.setParameter("expenseMasterId", expenseMasterId).getResultList();
		logger.info(advLiquidationsList.size());
    	return advLiquidationsList;
        }
    
    // ZH - May use this method for better performance later on.
    public List<AdvanceMasters> findPreviousApprovedOrHigherRevision (AdvanceEvents advanceEvent) {
    	if(logger.isDebugEnabled()){
    		logger.debug("Enter method :: findPreviousApprovedOrHigherRevision (AdvanceMasters advanceMaster)");		
    	}
    	String finderQuery = "SELECT AM FROM AdvanceMasters AM WHERE AM.adevIdentifier=:advanceEventId "
    						+ "AND AM.currentInd = 'N' AND AM.status IN ('APPR', 'HSNT', 'XTCT', 'PROC') "
    						+ "AND AM.revisionNumber = (Select Max(master.revisionNumber) FROM AdvanceMasters master "
    						+ "WHERE master.adevIdentifier=:advanceEventId " 
    						+ "AND master.status IN ('APPR', 'HSNT', 'XTCT', 'PROC') " 
    						+ "AND master.currentInd='N')";
		
		List<AdvanceMasters> advMastersList = entityManager.createQuery(finderQuery)
											.setParameter("advanceEventId",advanceEvent).getResultList();
								
		logger.info(advMastersList.size());
    	return advMastersList;
        }
    
      public List<AdvanceMasters> findPreviousApprovedOrHigherRevisionForLiqCarryForward (AdvanceEvents advanceEvent) {
    	if(logger.isDebugEnabled()){
    		logger.debug("Enter method :: findPreviousApprovedOrHigherRevision (AdvanceMasters advanceMaster)");		
    	}
    	String finderQuery = "SELECT AM FROM AdvanceMasters AM, AdvanceLiquidations AL "
    						+ "WHERE AM.adevIdentifier=:advanceEventId "
    						+ "AND AM.currentInd = 'N' AND AM.status IN ('APPR', 'HSNT', 'XTCT', 'PROC') "
    						+ "AND AM.revisionNumber = (Select Max(master.revisionNumber) FROM AdvanceMasters master "
    						+ "WHERE master.adevIdentifier=:advanceEventId " 
    						+ "AND master.status IN ('APPR', 'HSNT', 'XTCT', 'PROC') " 
    						+ "AND master.currentInd='N') and AL.advanceMaster.advmIdentifier = Am.advmIdentifier";
		
		List<AdvanceMasters> advMastersList = entityManager.createQuery(finderQuery)
											.setParameter("advanceEventId",advanceEvent).getResultList();
								
		logger.info(advMastersList.size());
    	return advMastersList;
        }
    

    public long findExpenseLiquidationsCount (AdvanceMasters advanceMaster, ExpenseMasters expenseMaster) {
    	
    	String finderQuery = "SELECT COUNT(AL) FROM AdvanceLiquidations AL WHERE AL.advanceMaster=:advanceMaster "
    						+ "AND AL.expenseMasterId =:expenseMasterId";
		
		long count = (Long) entityManager.createQuery(finderQuery)
											.setParameter("advanceMaster",advanceMaster)
											.setParameter("expenseMasterId",expenseMaster.getExpmIdentifier()).getSingleResult();
								
		return count;
        }
    
    /**
     * Returns the sum of outstanding advance amount for an employee
     * @param empId
     * @return
     */
    
    public double findAmountOutstandingByEmpId (int empId) {
    	
    	
    	String finderQuery = "SELECT NVL(SUM(AE.Outstanding_Amount), 0) FROM Advance_Events AE, Appointments APPT " +
    						 "WHERE APPT.emp_Identifier = ?1  " +
    						 "AND AE.appt_Identifier = APPT.appt_identifier";
		
    	BigDecimal amount = (BigDecimal) entityManager.createNativeQuery(finderQuery)
											.setParameter(1,empId)
											.getSingleResult();
								
		return amount.doubleValue();
        }
    
	public List<AdvanceHistory> findAdvanceHistory(int advanceEventId){

		String finderQuery = " SELECT AM.REVISION_NUMBER revisionNumber, AA.MODIFIED_DATE modifiedDate, AA.ACTION_CODE actionCode," +
							 "AA.COMMENTS comments, AA.MODIFIED_USER_ID modifiedUserId FROM ADVANCE_EVENTS AE," +
							 "ADVANCE_MASTERS AM, ADVANCE_ACTIONS AA WHERE AE.ADEV_IDENTIFIER = AM.ADEV_IDENTIFIER " +
							 "AND AA.ADVM_IDENTIFIER = AM.ADVM_IDENTIFIER AND AE.ADEV_IDENTIFIER = ?1 ORDER BY AA.MODIFIED_DATE";
		
		return entityManager.createNativeQuery(finderQuery,AdvanceHistory.class).setParameter(1, advanceEventId).getResultList();
		
	}
	
    public List<AdvanceLiquidations> findAdvanceLiquidationsList (AdvanceMasters advanceMaster) {
    	if(logger.isDebugEnabled()){
    		logger.debug("Enter method :: findAdvanceLiquidationsList (AdvanceMasters advanceMaster)");		
    	}
    	String finderQuery = "SELECT AL FROM AdvanceLiquidations AL, ExpenseMasters EM "
    						+ "WHERE AL.advanceMaster=:advanceMaster "
    						+ "AND AL.expenseMasterId = EM.expmIdentifier "
    						+ "AND EM.revisionNumber = (Select Max(master.revisionNumber) FROM ExpenseMasters master "
    						+ "WHERE master.expevIdentifier=EM.expevIdentifier " 
    						+ "AND master.status IN ('APPR', 'HSNT', 'XTCT', 'PROC'))"; 
		
		List<AdvanceLiquidations> advLiquidationsList = entityManager.createQuery(finderQuery)
											.setParameter("advanceMaster",advanceMaster).getResultList();
								
    	return advLiquidationsList;
        }
    
    /**
     * Returns a count of expenses in XTCT or HSNT status if used for Liquidations
     * @param advanceMaster
     * @return
     */
    
    public long findAdvanceLiquidationsCountXTCTorHSNT (AdvanceMasters advanceMaster) {
    	if(logger.isDebugEnabled()){
    		logger.debug("Enter method :: findAdvanceLiquidationsList (AdvanceMasters advanceMaster)");		
    	}
    	String finderQuery = "SELECT Count(AL) FROM AdvanceLiquidations AL, ExpenseMasters EM "
    						+ "WHERE AL.advanceMaster=:advanceMaster "
    						+ "AND AL.expenseMasterId = EM.expmIdentifier "
    						+ "AND EM.revisionNumber = (Select Max(master.revisionNumber) FROM ExpenseMasters master "
    						+ "WHERE master.expevIdentifier=EM.expevIdentifier " 
    						+ "AND master.status IN ('HSNT', 'XTCT'))"; 
		
		long count = (Long) entityManager.createQuery(finderQuery)
											.setParameter("advanceMaster",advanceMaster).getSingleResult();
								
    	return count;
        }
    
    public double findAmountOutstandingByEventId (int advanceEventId) {    	
    	String finderQuery = "SELECT AE.Outstanding_Amount FROM Advance_Events AE " +
    						 "WHERE AE.adev_Identifier = ?1";
		
    	BigDecimal amount = (BigDecimal) entityManager.createNativeQuery(finderQuery)
											.setParameter(1,advanceEventId)
											.getSingleResult();
								
		return amount.doubleValue();
        }
    
    /**
     * This finder gets the approved advance amount for the given parameters
     * @param advanceMaster
     * @return double
     */
         public double findNonPermAdvance(Date expDateFrom, Date expDateTo, int advanceMasterId) {
		double amtRet = 0.0;
		java.sql.Date dateFrom = new java.sql.Date (expDateFrom.getTime());
		java.sql.Date dateTo = new java.sql.Date (expDateTo.getTime());
		if (logger.isDebugEnabled()) {
			logger
					.debug("Enter method :: findNonPermAdvance (AdvanceMasters advanceMaster)");
		}
		String queryBuild = " select ae.outstanding_amount from advance_masters am, advance_events ae where  "
				+ " am.adev_identifier = ae.adev_identifier and am.status in('APPR','PROC') and am.current_ind='Y' and  am.permanent_adv_ind='N' "
				+ " and (((?1 between am.from_date and am.to_date) or (?2 between am.from_date and am.to_date))"
				+ " or ((am.from_date between ?1 and ?2) or (am.to_date between ?1 and ?2)))"
				+ " and am.advm_identifier = ?3";

		List<BigDecimal> amountList = (List<BigDecimal>) entityManager
				.createNativeQuery(queryBuild).setParameter(1, dateFrom)
				.setParameter(2, dateTo)
				.setParameter(3, advanceMasterId).getResultList();

		if (amountList.size() > 0) {
			amtRet = amountList.get(0).doubleValue();
		}
		return amtRet;
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
     	public String getRemainingApprovalPaths(int advmIdentifier, UserSubject userSubject ){
     		
     		if(logger.isDebugEnabled())
     			logger.debug("Enter method : getRemainingApprovalPaths Parameters: expmIdentifier:" + advmIdentifier + "department:" + userSubject.getDepartment()  +  "agency:" + userSubject.getAgency() +  "tku:" + userSubject.getTku());

     		String finderQuery = "SELECT F_ADV_GET_NEXT_APPROVAL_PATHS ( ?1, ?2, ?3, ?4) FROM DUAL";
     		Query query = entityManager.createNativeQuery(finderQuery);

     		query.setParameter(1, advmIdentifier);
     		query.setParameter(2, userSubject.getDepartment());
     		query.setParameter(3, userSubject.getAgency());
     		query.setParameter(4, userSubject.getTku());
     		
     		String returnPaths =  (String)query.getSingleResult();
     		if (returnPaths.trim().length() == 0)
     			returnPaths = "";
     		else
     			//removing last comma
     			returnPaths = "Awaiting "
     					+ returnPaths.substring(0, returnPaths.length() - 1); 
     			
     		
     		if(logger.isDebugEnabled())
     			logger.debug("Exit method : getRemainingApprovalPaths Value:" + returnPaths);

     		return returnPaths;

     	}
     	
}
