package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.core.EmailNotificationBean;
import gov.michigan.dit.timeexpense.model.core.NotificationReceivers;
import gov.michigan.dit.timeexpense.model.core.User;
import gov.michigan.dit.timeexpense.model.core.UserSubject;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class EmailNotificationDAO extends AbstractDAO {
 
    Logger logger = Logger.getLogger(EmailNotificationDAO.class);

    public EmailNotificationDAO() {}

    public EmailNotificationDAO(EntityManager em) {
    	super(em);
    }

    /**
     * This Method Checks the tkuoptions
     * 
     * @param Dept
     * @param Agy
     * @param Tku
     * @param msgID
     * @return True/False
     */
    public boolean checkTkuOptions(String Dept, String Agy, String Tku,
	    String msgID) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: checkTkuOptions(String Dept, String Agy, String Tku String msgID)");		
	}
	String finderQuery = "select 'X'  from dual"
		+ " where  exists (select 'x' from tkuopt_notifications"
		+ " where tku = ?3" + " and department = ?1"
		+ " and agency = ?2" + " and message_id = ?4) or"
		+ " exists (select 'x' from tkuopt_notifications"
		+ " where tku = 'AL'" + " and department = ?1"
		+ " and agency = ?2" + " and message_id = ?4"
		+ " and not exists" + " (select 'x' from tkuopt_notifications"
		+ " where tku = ?3" + " and department = ?1"
		+ " and agency = ?2))";

	Query query = entityManager.createNativeQuery(finderQuery);

	query.setParameter(1, Dept);
	query.setParameter(2, Agy);
	query.setParameter(3, Tku);
	query.setParameter(4, msgID);
	List tkuOptions = query.getResultList();
    int i=tkuOptions.size();
	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: checkTkuOptions(String Dept, String Agy, String Tku String msgID)");		
	}
	if (tkuOptions.size() > 0)
	    return true;
	else
	    return false;

    }

    /** This method gets the list of notification receivers
     * @param msgId
     * @return list of notification receivers 
     */
    public List<NotificationReceivers> findEmailReceivers(String msgId) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findEmailReceivers(String msgId)");		
	}
	
	String finderQuery = "select am from NotificationReceivers am where am.messageId = :msgId";
	Query query = entityManager.createQuery(finderQuery);
	query.setParameter("msgId", msgId);

	List<NotificationReceivers> receivers = query.getResultList();

	
	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: findEmailReceivers(String msgId)");		
	}
	
	return receivers;

    }

    /** This methods calls the oracle function to send the email.
     * @param emailBean
     * @return Integer
     */
    public int sendEmailNotification(EmailNotificationBean emailBean) {
	
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: sendEmailNotification(EmailNotificationBean emailBean)");		
	}
	String finderQuery = "select SEND_DCDS_NOTIFICATION(?1,?2,?3,?4,?5,?6,?7,?8,?9) FROM DUAL";
	Query query = entityManager.createNativeQuery(finderQuery);

	query.setParameter(1, emailBean.getToUser());
	query.setParameter(2, emailBean.getFromUser());
	query.setParameter(3, emailBean.getMsgId());
	query.setParameter(4, emailBean.getAddlText());
	query.setParameter(5, emailBean.getDept());
	query.setParameter(6, emailBean.getAgy());
	query.setParameter(7, emailBean.getTku());	
    query.setParameter(8, new Integer(0));
	query.setParameter(9, "Y");

	
	BigDecimal emailResult = (BigDecimal) query.getSingleResult();
		
	
	return emailResult.intValue();
	
    }

 
    
    /**This method finds the supervisor email id
     * @param apptId
     * @return supervisor email id
     */
    public String findSupervisorEmailId(int apptId) {
	
        String resultEmail = null;
/*	String finderQuery = "SELECT ui.mail_id,ui.user_id "
		+ " FROM hrmn_supervisor_employees hse, appointments a, user_ids ui,  hrmn_positions hp"
		+ " WHERE hse.emp_identifier = ui.emp_identifier"
		+ "  AND a.position_id = hp.position_id "
		+ "  AND hp.supervisor = hse.supervisor "
		+ "  AND SYSDATE BETWEEN hp.start_date AND hp.end_date "
		+ "  AND SYSDATE BETWEEN hse.start_date AND hse.end_date "
		+ "  AND a.appt_identifier = ?1";*/
        
        String finderQuery = "SELECT ui.mail_id,ui.user_id "
    		+ " FROM appointments a,appointment_histories ah,hrmn_supervisor_employees hse,user_ids ui "
    		+ " WHERE a.appt_identifier = ah.appt_identifier "
    		+ "  AND SYSDATE BETWEEN ah.start_date AND ah.end_date  "
    		+ " AND ah.supervisor = hse.supervisor "
    		+ "  AND SYSDATE BETWEEN hse.start_date AND hse.end_date "
    		+" AND ui.emp_identifier = hse.emp_identifier "
    		+" AND a.appt_identifier = ?1 " ;


	List<User> result = null;

	result = entityManager.createNativeQuery(finderQuery, User.class)
		.setParameter(1, apptId).getResultList();

	if (result.isEmpty()) {
	    return null;
	}
	
	resultEmail = result.get(0).getEmail();
	
	// ZH, 02/11/2013 - Commented to stop email generated using just the user_ids if no mail_id is present
/*	if (resultEmail == null) {
	    resultEmail = result.get(0).getUserId();
	} else {
	    resultEmail = result.get(0).getEmail();
	}*/	    

	return resultEmail;

    }

     /** This method finds From user Email address
     * @param userId
     * @return email address of the from user
     */
    public String findFromUserMailId(String userId) {
	
	String finderQuery = "SELECT mail_id from user_ids ui where ui.user_id = ?1";

	Query query = entityManager.createNativeQuery(finderQuery);
	query.setParameter(1, userId);

	String emailId = (String) query.getSingleResult();

	

	return emailId;

    }

    
    /** This methods finds to user email address
     * @param apptId
     * @return email address of the to user
     */
    public String findToUserMailId(int apptId) {
	
	String finderQuery = "SELECT max(mail_id) from user_ids ui, appointments a where ui.emp_identifier = a.emp_identifier and"
		+ " a.appt_identifier = ?1";

	Query query = entityManager.createNativeQuery(finderQuery);
	query.setParameter(1, apptId);

	String emailId = null;

	try {
	    emailId = (String) query.getSingleResult();
	} catch (NoResultException e) {
	    logger.info("To user Mail Id returned Null");
	    emailId = null;
	}

	
	return emailId;

    }

    /** This method finds User Name
     * @param empId
     * @return by user name
     */
    public String findByUserName(int empId) {
	
	String finderQuery = "Select last_name||', '||first_name||' '||middle_name||' '||suffix  name"
		+ " FROM personnel_histories   WHERE emp_identifier = ?1 and "
		+ "  (sysdate between personnel_histories.start_date and    personnel_histories.end_date)";

	Query query = entityManager.createNativeQuery(finderQuery);
	query.setParameter(1, empId);
	String name = null;
	try {
	    name = (String) query.getSingleResult();
	} catch (NoResultException e) {
	    logger.info("By UserName returned Null");
	    name = null;
	}

	
	return name;

    }

    /** This method finds for user name
     * @param apptId
     * @return for user name
     */
    public String findForUserName(int apptId) {
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: findForUserName(int apptId)");		
	}
	String finderQuery = "Select last_name||', '||first_name||' '||middle_name||' '||suffix  name"
		+ " FROM personnel_histories, appointments"
		+ "  WHERE personnel_histories.emp_identifier = appointments.emp_identifier and "
		+ "  sysdate between personnel_histories.start_date and   personnel_histories.end_date and"
		+ " appointments.appt_identifier = ?1";

	Query query = entityManager.createNativeQuery(finderQuery);
	query.setParameter(1, apptId);

	String name = null;

	try {
	    name = (String) query.getSingleResult();
	} catch (NoResultException e) {	  
	    name = null;
	}

	
	return name;

    }
    
    
    /** Method gets email addresses for all the PDF Approvers to email all if an expense type is of type <B>PDF</ B>.
     * @return List of Approvers.
     */
	public List<String> getPDFApprovers() {
		if (logger.isDebugEnabled()) {
			logger.debug("Enter method :: getPDFApprovars");
		}

		String finderQuery = "    SELECT DISTINCT ui.mail_id"
				+ "   FROM user_ids ui, Security s"
				+ "  WHERE     UI.USER_ID = S.USER_ID"
				+ "        AND S.ROLE_NAME = 'PDF APPROVAL'"
				+ "        AND S.MODULE_ID = 'APRF014'"
				+ "        AND S.DEPARTMENTRL = 'AL'"
				+ "        AND S.AGENCYRL = 'AL'"
				+ "        AND S.CHANGE_ACCESS_IND = 'Y'"
				+ "        AND UI.mail_id IS NOT NULL" + " ORDER BY UI.mail_id";

		Query query = entityManager.createNativeQuery(finderQuery);
		List<String> receivers = query.getResultList();

		if (logger.isDebugEnabled()) {
			logger.debug("Exit method :: getPDFApprovars");
		}

		return receivers;
	}
	//CN-BUG-147
	/** Method gets email addresses for all the TREQ Approvers to email all if travel requisition is submitted or approved and and the next_action_code is 'AOSW'.
     * @return List of Approvers.
     */
	public List<String> getTREQApprovers(UserSubject subject) {
		if (logger.isDebugEnabled()) {
			logger.debug("Enter method :: getTREQ Approvars");
		}

		String finderQuery = "    SELECT DISTINCT ui.mail_id"
				+ "   FROM user_ids ui, Security s"
				+ "  WHERE     UI.USER_ID = S.USER_ID"
				+ "        AND S.MODULE_ID = 'APRF015'"
				+ "        AND S.DEPARTMENT = ?1"
				+ "        AND S.AGENCY IN ('AL', ?2) "
				+ "        AND S.TKU IN ('AL', ?3) "
				+ "        AND S.CHANGE_ACCESS_IND = 'Y'"
				+ "        AND UI.mail_id IS NOT NULL" + " ORDER BY UI.mail_id";

		Query query = entityManager.createNativeQuery(finderQuery);
		query.setParameter(1, subject.getDepartment());
		query.setParameter(2, subject.getAgency());
		query.setParameter(3, subject.getTku());
		List<String> receivers = query.getResultList();

		if (logger.isDebugEnabled()) {
			logger.debug("Exit method :: getTREQApprovars");
		}

		return receivers;
	}
	//CN-BUG-147

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
}	

			
