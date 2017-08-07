
package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.core.ExpenseActions;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.StateAuthCodes;
import gov.michigan.dit.timeexpense.model.core.TravelReqActions;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetailCodingBlock;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetails;
import gov.michigan.dit.timeexpense.model.core.TravelReqEvents;
import gov.michigan.dit.timeexpense.model.core.TravelReqHistory;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.core.VTravelReqList;
import gov.michigan.dit.timeexpense.model.display.TravelReqListBean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.openjpa.persistence.OpenJPAEntityManager;

public class TravelRequisitionDAO extends AbstractDAO{
	
    public TravelRequisitionDAO(){}
    
    public TravelRequisitionDAO(EntityManager em){
    	super(em);
    }
	
	private static final Logger logger = Logger.getLogger(TravelRequisitionDAO.class);

	private final static String REQUISITIONS_BY_APPT_ID_SELECT_CLAUSE = "SELECT events.APPT_IDENTIFIER apptIdentifier," +
			"appt.EMP_IDENTIFIER empIdentifier,events.TREQE_IDENTIFIER treqeIdentifier," +
			"masters.TREQM_IDENTIFIER treqmIdentifier,masters.TREQ_DATE_FROM treqDateFrom, masters.TREQ_DATE_TO treqDateTo,"+
			"masters.NATURE_OF_BUSINESS natureOfBusiness,masters.OUT_OF_STATE_IND outOfStateInd," +
			"masters.REVISION_NUMBER revisionNumber, actions.ACTION_CODE actionCode FROM " 
			+"APPOINTMENTS appt,TRAVEL_REQ_EVENTS events,"
			+"TRAVEL_REQ_MASTERS masters,TRAVEL_REQ_ACTIONS actions,EMPLOYEES emp,appointment_histories ah ";
	
	private final static String REQUISITIONS_BY_APPT_ID_SELECT_CLAUSE_2 = "SELECT events.APPT_IDENTIFIER apptIdentifier," +
	"appt.EMP_IDENTIFIER empIdentifier,events.TREQE_IDENTIFIER treqeIdentifier," +
	"masters.TREQM_IDENTIFIER treqmIdentifier,masters.TREQ_DATE_FROM treqDateFrom, masters.TREQ_DATE_TO treqDateTo,"+
	"masters.NATURE_OF_BUSINESS natureOfBusiness,masters.OUT_OF_STATE_IND outOfStateInd," +
	"masters.REVISION_NUMBER revisionNumber, '' FROM " 
	+"APPOINTMENTS appt,TRAVEL_REQ_EVENTS events,"
	+"TRAVEL_REQ_MASTERS masters, EMPLOYEES emp,appointment_histories ah ";
	
	private final static String REQUISITIONS_BY_APPT_ID_WHERE_CLAUSE=	
		"WHERE "
		+"emp.emp_identifier = APPT.emp_IDENTIFIER "
		+"AND appt.appt_identifier = ah.appt_identifier "
		+"AND EVENTS.APPT_IDENTIFIER = ?1 "
		+"AND EVENTS.TREQE_IDENTIFIER = masters.TREQE_IDENTIFIER "
		+"AND EVENTS.appt_identifier = appt.appt_identifier "
		+"AND masters.current_ind = 'Y' "
		+"AND masters.TREQ_DATE_TO BETWEEN ah.APPOINTMENT_DATE AND ah.DEPARTURE_OR_END_DATE "
	    +"AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1 "
		+"WHERE ap1.appt_identifier = ah.appt_identifier "
		+"AND ap1.end_date >= ap1.start_date and masters.TREQ_DATE_TO BETWEEN ap1.APPOINTMENT_DATE "
        +"AND ap1.DEPARTURE_OR_END_DATE) "
		+"AND actions.TREQM_IDENTIFIER(+) = masters.TREQM_IDENTIFIER "
	    +"AND actions.treqa_identifier = (SELECT MAX (treqa_identifier) FROM TRAVEL_REQ_ACTIONS TREQ_ACTION "
        +"WHERE actions.TREQM_IDENTIFIER = TREQ_ACTION.TREQM_IDENTIFIER) ";
	
	private final static String REQUISITIONS_BY_APPT_ID_WHERE_CLAUSE_2=	
		"WHERE "
		+"emp.emp_identifier = APPT.emp_IDENTIFIER "
		+"AND appt.appt_identifier = ah.appt_identifier "
		+"AND EVENTS.APPT_IDENTIFIER = ?1 "
		+"AND EVENTS.TREQE_IDENTIFIER = masters.TREQE_IDENTIFIER "
		+"AND EVENTS.appt_identifier = appt.appt_identifier "
		+"AND masters.current_ind = 'Y' "
		+"AND masters.TREQ_DATE_TO BETWEEN ah.APPOINTMENT_DATE AND ah.DEPARTURE_OR_END_DATE "
	    +"AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1 "
		+"WHERE ap1.appt_identifier = ah.appt_identifier "
		+"AND ap1.end_date >= ap1.start_date and masters.TREQ_DATE_TO BETWEEN ap1.APPOINTMENT_DATE "
        +"AND ap1.DEPARTURE_OR_END_DATE) AND NOT EXISTS (SELECT * FROM TRAVEL_REQ_ACTIONS tra2 "
		+"WHERE masters.treqm_identifier = tra2.treqm_identifier)";
	
	private final static String SECURITY_SUB_SELECT_QUERY = "AND EXISTS (select sec.user_id from security sec where sec.user_id = ?2 "
		+ " and sec.module_id = ?3 and(ah.department between decode(sec.department,'AL','00',sec.department) " 
		+ " and decode(sec.department,'AL','99',sec.department)) "
		+ " and (ah.agency between decode(sec.agency,'AL','00',sec.agency) and decode(sec.agency,'AL','99',sec.agency)) "
		+ " and (ah.tku between decode(sec.tku,'AL','000',sec.tku) and decode(sec.tku,'AL','999',sec.tku)))";
	
	@SuppressWarnings("unchecked")
	public List<VTravelReqList> findTravelReqsByEmployeeId(int employeeId) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findTravelReqsByEmployeeId(int employeeId) : return List<VTravelReqList>");
		}

		String finderQuery  = "select v from VTravelReqList v where v.empIdentifier=:empIdentifier";	
		List<VTravelReqList> treqList = entityManager.createQuery(finderQuery)
		.setParameter("empIdentifier", employeeId).getResultList();

		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findTravelReqsByEmployeeId(int employeeId)");
		}
		return treqList;
	}
	
	public List<VTravelReqList> findAdjustedTravelReqsByEmployeeId(int employeeId) {
		return null;
	}
	
	public List<VTravelReqList> findNonAdjustedTravelReqsByEmployeeId(int employeeId) {
		return null;
	}
	
	/**
	 * This method retrieves all travel requisitions records by appointment ID.
	 * @param appointmentId
	 * @param userId
	 * @param moduleId
	 * @return List of travel requisitions
	 */
/*	@SuppressWarnings("unchecked")
	public List<TravelReqListBean> findTravelReqsByAppointmentId(
			long appointmentId, String userId, String moduleId) {

		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findTravelReqsByAppointmentId(long appointmentId, String userId, String moduleId) : return List<TravelReqListBean>");
		}		
		String finderQuery = REQUISITIONS_BY_APPT_ID_SELECT_CLAUSE + REQUISITIONS_BY_APPT_ID_WHERE_CLAUSE + 
		SECURITY_SUB_SELECT_QUERY;

		List<TravelReqListBean> treqList = entityManager.createNativeQuery(finderQuery, TravelReqListBean.class).setParameter(1, appointmentId)
		.setParameter(2, userId)
		.setParameter(3, moduleId).getResultList();
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findTravelReqsByAppointmentId(long appointmentId, String userId, String moduleId)");
		}
		
		return treqList;
	}*/
	
	@SuppressWarnings("unchecked")
	public List<TravelReqListBean> findTravelReqsByAppointmentId(
			long appointmentId, String userId, String moduleId) {

		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findTravelReqsByAppointmentId(long appointmentId, String userId, String moduleId) : return List<TravelReqListBean>");
		}		
		String finderQuery = REQUISITIONS_BY_APPT_ID_SELECT_CLAUSE + REQUISITIONS_BY_APPT_ID_WHERE_CLAUSE + 
		SECURITY_SUB_SELECT_QUERY + " UNION " + REQUISITIONS_BY_APPT_ID_SELECT_CLAUSE_2 + REQUISITIONS_BY_APPT_ID_WHERE_CLAUSE_2+ SECURITY_SUB_SELECT_QUERY + " order by 3 desc";

		List<TravelReqListBean> treqList = entityManager.createNativeQuery(finderQuery, TravelReqListBean.class).setParameter(1, appointmentId)
		.setParameter(2, userId)
		.setParameter(3, moduleId).getResultList();
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findTravelReqsByAppointmentId(long appointmentId, String userId, String moduleId)");
		}
		
		return treqList;
	}
	
	
	public List<TravelReqListBean> findAdjustedTravelReqsByAppointmentId(long appointmentId, String userId, String moduleId) {
		return null;
	}
	
	public List<TravelReqListBean> findNonAdjustedTravelReqsByAppointmentId(long appointmentId, String userId, String moduleId) {
		return null;
	}
	
	/**
	 * This method persists the travel requisition events and all associated child entities
	 * @param treqEvents
	 * @return
	 */
	
	public TravelReqEvents saveTravelRequisition (TravelReqEvents treqEvent) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: saveTravelRequisition(TravelReqEvents treqEvent)");
			logger.debug("Enter JPA Operation :: Persisting TravelReqEvents entity");
		}
		
		entityManager.persist(treqEvent);

		if(logger.isDebugEnabled()){
			logger.debug("Exit JPA Operation :: saveTravelRequisition(TravelReqEvents treqEvent");
			logger.debug("Exit method :: saveTravelRequisition(TravelReqEvents treqEvent");
		}
		return treqEvent;
	}
	
	/**
	 * This method finds a travel requisition event based on the provided id  
	 * @param treqEventId
	 * @return
	 */
	
	public TravelReqEvents findTravelRequisitionEvent(int treqEventId) {
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findTravelRequisitionevent(int treqEventId)");
		}
		return entityManager.find(TravelReqEvents.class, treqEventId);
		
	}
	
	/** This method performs a JPA remove operation to delete the selected requistion.
	 * 
	 * @param treqEventId
	 * @return boolean
	 */
	public boolean deleteTravelRequisition(int treqEventId) {

		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: deleteTravelRequisition(int treqEventId)");
		}
		boolean isDeleted = true;
		// Remove the Expense event entity that matches the eventId
		entityManager.remove(entityManager.find(TravelReqEvents.class, treqEventId));
			
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: deleteTravelRequisition(int treqEventId)");
		}
		return isDeleted;
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
	
	public TravelReqMasters findTravelRequisitonByMasterId(int treqMasterId) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findTravelRequisitonByMasterId(int treqMasterId) : return TravelReqMasters");
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findTravelRequisitonByMasterId(int treqMasterId) : return TravelReqMasters");
		}
		
		return entityManager.find(TravelReqMasters.class, treqMasterId);
	}
	
	public TravelReqEvents findTravelRequisitionByEventId(int treqEventId) {
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findTravelRequisitionByEventId(int treqEventId)");
		}
		return entityManager.find(TravelReqEvents.class, treqEventId);
		
	}
	

	@SuppressWarnings("unchecked")
	public TravelReqMasters findTravelRequisitionByEventId(int treqEventId,int revisionNo) {
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: findTravelRequisitionByEventId(int treqEventId,int revisionNo) return TravelReqMasters");
		}
		
		TravelReqMasters result = null;
		String finderQuery = "select trm from TravelReqMasters trm where trm.treqeIdentifier = :treqIdentifier and trm.revisionNumber = :revNo";
		
		TravelReqEvents treqEvent = this.findTravelRequisitionByEventId(treqEventId);
		List<TravelReqMasters> mastersList = entityManager.createQuery(finderQuery)
		.setParameter("treqIdentifier", treqEvent)
		.setParameter("revNo", revisionNo).getResultList();
		
		if(mastersList.size()>0)
			result = mastersList.get(0);

		if(logger.isInfoEnabled())
			logger.info("Exit JPA operation : Finder Query for Expenses by the ExpenseEventID completed");
		
		
		if(logger.isDebugEnabled()){
			logger.debug("Exit method :: findTravelRequisitionByEventId(int treqEventId,int revisionNo)");
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<TravelReqActions> findTravelRequisitionActions(TravelReqMasters treqMaster){
		String query = "select tra from TravelReqActions tra where tra.treqmIdentifier=:treqMaster order by tra.treqaIdentifier";
		
		List<TravelReqActions> actions = entityManager.createQuery(query).setParameter("treqMaster", treqMaster).getResultList();
		
		return actions;
	}
	
	public int findMaxRevisionNo(int treqEventId) {
		Long maxRev = (Long)entityManager.createNativeQuery("select max(REVISION_NUMBER) from TRAVEL_REQ_MASTERS where TREQE_IDENTIFIER=?1", Long.class)
						.setParameter(1, treqEventId).getResultList().get(0);
		
		return maxRev == null ? 0: maxRev.intValue();
	}
	
	public TravelReqMasters saveTravelRequisition(TravelReqMasters treqMaster) {
		TravelReqMasters result = null;
		
		
		if(logger.isDebugEnabled()){
			logger.debug("Enter method :: saveTravelRequisition(TravelReqMasters treqMaster)");
		}
		if(treqMaster.getTreqmIdentifier()==null){
			entityManager.persist(treqMaster);
			result = treqMaster;
		}else{
//			OpenJPAEntityManager openJpaEntityManager = (OpenJPAEntityManager) entityManager;
//			openJpaEntityManager.dirty(treqMaster, "modifiedUserId");
			//entityManager.dirty(treqMaster, "modifiedUserId");
			result = entityManager.merge(treqMaster);
		}
		
		if(logger.isDebugEnabled()){
			
			logger.debug("Exit JPA Operation :: TravelReqMasters entity persisted successfully");
		}
		
		return result;
	}
	
	public List<TravelReqHistory> findTravelRequisionHistory(int treqEventId){

		String finderQuery = "SELECT TRA.TREQA_IDENTIFIER treqaIdentifier, TRM.TREQM_IDENTIFIER treqMasterId, TRM.REVISION_NUMBER revisionNo, TRA.MODIFIED_DATE modifiedDate, TRA.ACTION_CODE treqActionCode," +
				"TRA.COMMENTS comments, TRA.MODIFIED_USER_ID modifiedUserId FROM TRAVEL_REQ_EVENTS TRE," +
				"TRAVEL_REQ_MASTERS TRM, TRAVEL_REQ_ACTIONS TRA WHERE TRE.TREQE_IDENTIFIER = TRM.TREQE_IDENTIFIER " +
				"AND TRA.TREQM_IDENTIFIER = TRM.TREQM_IDENTIFIER " +
				"AND TRA.ACTION_CODE NOT IN ('EPRC','LPRC')" +
				"AND TRE.TREQE_IDENTIFIER = ?1 ORDER BY TRA.TREQA_IDENTIFIER";
		
		return entityManager.createNativeQuery(finderQuery,TravelReqHistory.class).setParameter(1, treqEventId).getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<TravelReqActions> approveTravelRequisition(TravelReqMasters treqMaster){
		String finderQuery = "SELECT TRA FROM TravelReqActions TRA WHERE TRA.treqmIdentifier=:treqmIdentifier AND TRA.nextActionCode IS NOT NULL";
		return entityManager.createQuery(finderQuery)
		.setParameter("treqmIdentifier",treqMaster).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TravelReqActions> findLatestAction (TravelReqMasters treqMaster) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getLatestAction");
		
		String finderQuery = "SELECT TRA FROM TravelReqActions TRA WHERE TRA.treqmIdentifier=:treqmIdentifier order by TRA.treqaIdentifier desc";
			
			List<TravelReqActions> treqActionsList = entityManager.createQuery(finderQuery).setParameter("treqmIdentifier",treqMaster).getResultList();

		return treqActionsList;
	}
	
    @SuppressWarnings("unchecked")
	public List<TravelReqActions> findNextAction(TravelReqMasters treqMaster) {
    	if(logger.isDebugEnabled()){
    		logger.debug("Enter method :: findNextAction(TravelReqMasters treqMaster)");		
    	}
    	
    	String finderQuery = "SELECT TRA FROM TravelReqActions TRA, TravelReqMasters TRM WHERE TRA.treqmIdentifier=:treqmIdentifier AND TRM.currentInd = 'Y' AND TRA.nextActionCode IS NOT NULL";
    	return entityManager.createQuery(finderQuery).setParameter(
    		"treqmIdentifier", treqMaster).getResultList();
        }
    
	/*@SuppressWarnings("unchecked")
	public TravelReqMasters updateTravelRequisitionStatus(TravelReqMasters treqMaster) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : updateTravelRequisitionStatus(TravelReqMasters treqMaster)");
		
		String finderQuery = "SELECT TRA FROM TravelReqActions TRA WHERE TRA.treqmIdentifier=:treqmIdentifier AND TRA.nextActionCode IS NOT NULL";
			
			List<TravelReqActions> treqActionsList = entityManager.createQuery(finderQuery).setParameter("treqmIdentifier",treqMaster).getResultList();
			if(treqActionsList.size()==0)
				treqMaster.setStatus(IConstants.APPROVED);

		return treqMaster;
	}*/
	
	@SuppressWarnings("unchecked")
	public TravelReqMasters updateTravelRequisitionStatus(TravelReqMasters treqMaster) {
		if(logger.isDebugEnabled())
			logger.debug("Enter method : updateTravelRequisitionStatus(TravelReqMasters treqMaster)");
		
		String finderQuery = "SELECT TRA FROM TravelReqActions TRA WHERE TRA.treqmIdentifier=:treqmIdentifier AND TRA.nextActionCode IS NOT NULL";
			
			List<TravelReqActions> treqActionsList = entityManager.createQuery(finderQuery).setParameter("treqmIdentifier",treqMaster).getResultList();
			if(treqActionsList.size()==0){
				//treqMaster.setStatus(IConstants.APPROVED);
				int masterId = treqMaster.getTreqmIdentifier();
				entityManager.createNativeQuery("UPDATE TRAVEL_REQ_MASTERS SET status = 'APPR' where treqm_identifier = ?1")
				.setParameter(1, masterId).executeUpdate();
				entityManager.flush();
				entityManager.refresh(treqMaster);
			}
					

		return treqMaster;
	}
	/**
	 * Finds travel requisition associated with an expense
	 * @param expenseEventId
	 * @return
	 */
    public TravelReqEvents findTravelRequisition (int expenseEventId) {
    	
    	String finderQuery = "SELECT tre FROM TravelReqEvents tre WHERE tre.expevIdentifier=:expevIdentifier ";
    						
		
    	List<TravelReqEvents> eventList = entityManager.createQuery(finderQuery)
											.setParameter("expevIdentifier",expenseEventId).getResultList();
								
    	return eventList.size() > 0 ? eventList.get(0): null;
        }
    /**
     * Finds the current travel requisition master for a requisition event
     * @param treqEventId
     * @return
     */
    public TravelReqMasters findTravelRequisitionCurrent (int treqEventId) {
    	
    	String finderQuery = "SELECT treqm FROM TravelReqMasters treqm WHERE treqm.treqeIdentifier.treqeIdentifier=:treqeIdentifier and " +
    						 "treqm.currentInd = 'Y'";
    						
		
    	List<TravelReqMasters> masterList = entityManager.createQuery(finderQuery)
											.setParameter("treqeIdentifier",treqEventId).getResultList();
								
    	return masterList.size() > 0 ? masterList.get(0): null;
        }
    
    public TravelReqEvents findTravelRequisitionForAdvance (int advanceEventId) {
    	
    	String finderQuery = "SELECT tre FROM TravelReqEvents tre WHERE tre.adevIdentifier=:adevIdentifier ";
    						
		
    	List<TravelReqEvents> eventList = entityManager.createQuery(finderQuery)
											.setParameter("adevIdentifier",advanceEventId).getResultList();
								
    	return eventList.size() > 0 ? eventList.get(0): null;
        }
    
    public int findTravelRequisitionCount (int expenseEventId) {
    	
    	String finderQuery = "SELECT COUNT(tre) FROM TravelReqEvents tre WHERE tre.expevIdentifier=:expevIdentifier";

    	Long count = (Long) entityManager.createQuery(finderQuery)
							.setParameter("expevIdentifier",expenseEventId).getSingleResult();
				
    	return count.intValue();
        }
	@SuppressWarnings("unchecked")
	public List<TravelReqDetailCodingBlock> findCodingBlocks(TravelReqDetails treqdIdentifier) {
    	
    	String finderQuery = "SELECT TREQCB FROM TravelReqDetailCodingBlock TREQCB WHERE TREQCB.treqdIdentifier=:treqdIdentifier";
    	List<TravelReqDetailCodingBlock> cbList = entityManager.createQuery(finderQuery).setParameter(
        		"treqdIdentifier", treqdIdentifier).getResultList();
    	return cbList;
        }
	
	public void updateTravelRequisition (int treqeIdentifier, int adevIdentifier) {
		entityManager.createNativeQuery("update Travel_req_events set adev_Identifier=?1 where treqe_identifier = ?2")
						.setParameter(1, adevIdentifier).setParameter(2, treqeIdentifier).executeUpdate();
		
		entityManager.flush();
	}
	
	/** 
	 * Method to retrieve the TravelReqDetails by treqDtlsId.
	 * @param ruleId
	 * @return TravelReqDetails
	 */
	public TravelReqDetails findTreqDetailId(int treqDtlsId){
		return entityManager.find(TravelReqDetails.class, treqDtlsId);
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
	public String getRemainingApprovalPaths(int treqmIdentifier, UserSubject userSubject ){
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getRemainingApprovalPaths Parameters: expmIdentifier:" + treqmIdentifier + "department:" + userSubject.getDepartment()  +  "agency:" + userSubject.getAgency() +  "tku:" + userSubject.getTku());

		String finderQuery = "SELECT F_TREQ_GET_NEXT_APPROVAL_PATHS ( ?1, ?2, ?3, ?4) FROM DUAL";
		Query query = entityManager.createNativeQuery(finderQuery);

		query.setParameter(1, treqmIdentifier);
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
	/**
	 * Retrieves the last action associated with a requisition master
	 * @param treq
	 * @return
	 */
	public TravelReqActions findLatestTreqAction(TravelReqMasters treq){
		String query = "select treq from TravelReqActions treq where treq.treqmIdentifier=:treq and treq.actionCode <> 'SPLT' AND treq.treqaIdentifier = " +
				"(select max(treq1.treqaIdentifier) from TravelReqActions treq1 where treq1.treqmIdentifier=:treq and treq1.actionCode <> 'SPLT')";
		
		List<TravelReqActions> actions = entityManager.createQuery(query).setParameter("treq", treq).getResultList();
		
		return actions.size() == 0 ? null: actions.get(0);
	}
	
	public TravelReqMasters findPrevTreqInStatus(TravelReqEvents treqEvent, String status, int revisionNo){
		String finderQuery = "select treqm from TravelReqMasters treqm where treqm.treqeIdentifier =:treqeIdentifier " +
				"and treqm.revisionNumber < :revisionNumber and treqm.status = :statusCode order by treqm.revisionNumber desc";
		
		List<TravelReqMasters> treqMasterList =  entityManager.createQuery(finderQuery)
														.setParameter("treqeIdentifier", treqEvent)
														.setParameter("revisionNumber", revisionNo)
														.setParameter("statusCode", status)
														.getResultList();
		
		return treqMasterList.isEmpty() ? null: treqMasterList.get(0);
	}
	
	public List<TravelReqActions> findPrevTreqApprovalAction(TravelReqEvents treqEvent, int revisionNo){
		String finderQuery = "Select treqa.* from Travel_Req_Masters treqm, Travel_Req_Actions treqa" +
				" where treqm.treqe_Identifier =?1 and treqm.treqm_Identifier = treqa.treqm_Identifier" +
				" and treqm.revision_Number < ?2 and treqa.action_Code <> 'SUBM' and treqa.action_Code <> 'RJCT' and treqa.action_Code <> 'SPLT'";
		
		List<TravelReqActions> treqActionsList =  entityManager.createNativeQuery(finderQuery)
														.setParameter(1, treqEvent.getTreqeIdentifier())
														.setParameter(2, revisionNo)
														.getResultList();
		
		
		return treqActionsList;
	}
	
	}
