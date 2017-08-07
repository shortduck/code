package gov.michigan.dit.timeexpense.service;


import gov.michigan.dit.timeexpense.dao.EmailNotificationDAO;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.AppointmentHistory;
import gov.michigan.dit.timeexpense.model.core.EmailNotificationBean;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.NotificationReceivers;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.model.core.TravelReqActions;



import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;



/**
 * @author chiduras
 * 
 */
public class EmailNotificationDSP{
   
	AppointmentDSP apptDSP;
    
    private EmailNotificationDAO emailDAO;

    public EmailNotificationDSP(EntityManager em) {
	emailDAO = new EmailNotificationDAO(em);
	apptDSP = new AppointmentDSP(em);

	}

   private Logger logger = Logger.getLogger(EmailNotificationDSP.class);
    String dept = null;
    String agy = null;
    String tku = null;
    String addlText = null;

  
    
    /**
     * @param apptId
     *            AppointmentId of Subject
     * @param empId
     *            EmployeeId of Logged in User
     * @param userID
     *            User Id of the Logged in User
     * @param msgId
     * @param effectiveDate
     *            For Advance effectiveDate will be request date and for expense
     *            it will be expense date to
     * @return 0 if successful call for notifications, > 0 zero is error condition
     */
    public int processEmailNotifications(UserSubject subject, int empId, String userId,
	    String msgId, Date effectiveDate, String addittionalText) {
    	//CN BUG 147 
    int apptId=subject.getAppointmentId();
	if(logger.isDebugEnabled()){
		logger.debug("Enter method :: processEmailNotifications(int apptId, int empId, String userId,String msgId, Date effectiveDate)");		
	}
	if (userId == null || apptId == 0 || msgId == null || "".equals(userId)
		|| "".equals(msgId) || effectiveDate == null) {
	    return 10550; 
	}

	int emailResult = 0;
	int returnCode = 0;
	
	if(checkEmailOptions(apptId, effectiveDate, msgId)) {
	    emailResult = sendEmail(empId,subject, msgId, userId, addittionalText);		 
	}
	else {
	 return 0;   
	}
   
	switch (emailResult) {
	case 101:
	    returnCode = 10551;
	    break;
	case 2:
	    returnCode = 10550;
	    break;
	case 5:
	    returnCode = 10550;
	    break;
	default:
	    returnCode = 0;
	    break;
	}

	if(logger.isDebugEnabled()){
		logger.debug("Exit method :: processEmailNotifications(int apptId, int empId, String userId,String msgId, Date effectiveDate)");		
	}
	return returnCode;

    }

    /**
     * This method checks for email options
     * 
     * @param empid
     * @param msgId
     * @return boolean
     */
    public boolean checkEmailOptions(int apptId, Date effectiveDate,
	    String msgId) {
	
	AppointmentHistory result = apptDSP.getAppointmentHistory(apptId, effectiveDate);
        if (result == null){
            return false;
        }
            
	dept = result.getDepartment();
	agy = result.getAgency();
	tku = result.getTku();

	
	return (emailDAO.checkTkuOptions(dept, agy, tku, msgId));

    }

    /**
     * @param empId
     * @param apptId
     * @param msgId
     * @return
     */
	protected int sendEmail(int empId, UserSubject subject, String msgId, String userId,
			String addittionalText) {
        int apptId=subject.getAppointmentId();//CN BUG 147
        
		List<NotificationReceivers> listOfReceivers = emailDAO
				.findEmailReceivers(msgId);
		
		List<String> pdfApprovers = null;
		//CN-BUG 147
		List<String> treqApprovers = null;
		//CN-BUG 147
		if (listOfReceivers != null && !listOfReceivers.isEmpty()) {
			for (NotificationReceivers item : listOfReceivers) {
				EmailNotificationBean emailBean = null;
				emailBean = new EmailNotificationBean();
				if ("Supervisor".equals(item.getReceiver().trim())) {
					if (getSupervisorEmailId(apptId) == null)
						return 101;
					emailBean.setToUser(getSupervisorEmailId(apptId));
				} else {
					if ("Employee".equals(item.getReceiver().trim())) {
						if (getToUserEmailId(apptId) == null)
							return 101;
						emailBean.setToUser(getToUserEmailId(apptId));
					} 
					else if (IConstants.NOTIFICATION_PDF_APPROVE.equals(item.getReceiver().trim())){
						pdfApprovers = emailDAO.getPDFApprovers();
					}
					//CN-BUG 147
					else if (IConstants.NOTIFICATION_TRAVEL_REQ_DIRECTOR_EMAIL.equals(item.getReceiver().trim())){
						treqApprovers = emailDAO.getTREQApprovers(subject);
					}
					//CN-BUG 147
					else {
						return 1;
					}
				}

				if (msgId == "050"
						|| msgId == "052"
						|| msgId == "054"
						|| msgId == "056"
						|| msgId == "057"
						|| msgId == "058"
						|| msgId == "059"
						|| msgId == "060"
						|| msgId == "062"
						|| msgId == "064"
						|| msgId == "066"
						|| msgId == "067"
						|| msgId == "068"
						|| msgId == "069"
						|| msgId == "080"
						|| msgId == "082"
						|| msgId == "084"
						|| msgId == "085"
						|| msgId == IConstants.NOTIFICATION_PDF_REQUEST_SUBMIT_BY_SUBJECT
						|| msgId == IConstants.NOTIFICATION_PDF_REQUEST_MODIFIED_BY_SUBJECT
						|| msgId == IConstants.NOTIFICATION_PDF_REQUEST_ADJUSTED_BY_SUBJECT
						|| msgId == IConstants.NOTIFICATION_PDF_REQUEST_APPROVED
						|| msgId == IConstants.NOTIFICATION_PDF_REQUEST_REJECTED
						|| msgId == IConstants.NOTIFICATION_PDF_REQUEST_ADJUSTMENT_APPROVED
						|| msgId == IConstants.NOTIFICATION_PDF_REQUEST_ADJUSTMENT_REJECTED

				) {
					addlText = "  by " + getByUserName(empId);
				} else if (msgId == "051"
						|| msgId == "053"
						|| msgId == "055"
						|| msgId == "061"
						|| msgId == "063"
						|| msgId == "065"
						|| msgId == "081"
						|| msgId == "083"
						|| msgId == IConstants.NOTIFICATION_PDF_REQUEST_SUBMIT_BY_OTHER
						|| msgId == IConstants.NOTIFICATION_PDF_REQUEST_MODIFIED_BY_OTHER
						|| msgId == IConstants.NOTIFICATION_PDF_REQUEST_ADJUSTED_BY_OTHER) {
					addlText = " - " + getForUserName(apptId) + " - " + " by "
							+ getByUserName(empId);
				}

				if (getFromUserEmailId(userId) == null)
					return 101;
				

				emailBean.setFromUser(getFromUserEmailId(userId));
				emailBean.setDept(dept);
				emailBean.setAgy(agy);
				emailBean.setTku(tku);
				emailBean.setMsgId(msgId);
				emailBean.setAddlText(addittionalText + "\n" + addlText);

				
				//if this is for PDF then send it to all the PDF approvers				
				if (pdfApprovers != null && !pdfApprovers.isEmpty()
						&& pdfApprovers.size() > 0) {
					
					for (String pdfApprover : pdfApprovers) {
						emailBean.setToUser(pdfApprover);
						emailDAO.sendEmailNotification(emailBean);
					}
				}
				//CN BUG 147
				else if(treqApprovers!= null && !treqApprovers.isEmpty()
						&& treqApprovers.size() > 0) {
					treqApprovers(treqApprovers,emailBean);
				}
				//CN BUG 147
				else{
					emailDAO.sendEmailNotification(emailBean);
				}
				
				
			}

		} else {

			return 10550;
		}

		return 0;

	}
	//CN-BUG 147
	/**
     * This method checks for TREQ Approvers.
     */
	protected void treqApprovers(List<String> treqApprovers, EmailNotificationBean emailBean){
		
		 	for (String treqApprover : treqApprovers) {
				emailBean.setToUser(treqApprover);
				emailDAO.sendEmailNotification(emailBean);
			}
	}
	//CN-BUG 147
	/**
	 * Derives the appropriate notification email message for an advance action
	 * 
	 * @param advanceMaster
	 * @param subject
	 * @param profile
	 * @return
	 */

	public String getNotificationMessage(AdvanceMasters advanceMaster,
			UserSubject subject, UserProfile profile) {
		boolean userIsSubject = false;
		String notificationMessage = IConstants.STRING_BLANK;
		if (subject.getEmployeeId() == profile.getEmpIdentifier())
			userIsSubject = true;
		if (advanceMaster.getStatus().equals(IConstants.SUBMIT)) {
			notificationMessage = this.getSubmitNotificationMessage(
					advanceMaster, userIsSubject);
		} else if (advanceMaster.getStatus().equals(IConstants.APPROVED))
			notificationMessage = IConstants.NOTIFICATION_ADVANCE_REQUEST_APPROVED;
		else if (advanceMaster.getStatus().equals(IConstants.REJECTED))
			notificationMessage = IConstants.NOTIFICATION_ADVANCE_REQUEST_REJECTED;
		return notificationMessage;
	}
	
	public String getNotificationMessage(TravelReqMasters treqMaster,
			UserSubject subject, UserProfile profile) {
		
		boolean userIsSubject = false;
		String notificationMessage = IConstants.STRING_BLANK;
		if (subject.getEmployeeId() == profile.getEmpIdentifier())
			userIsSubject = true;
		if (treqMaster.getStatus().equals(IConstants.SUBMIT)) {
			////CN-Bug-147
			
			boolean isNextActionCodeAOSW = false;
	 		List<TravelReqActions> actionsList=treqMaster.getTravelReqActionsCollection();
	 		
	 		for (TravelReqActions item : actionsList) {
				if (item != null && item.getNextActionCode() != null) {
					if (item.getNextActionCode().equalsIgnoreCase("AOSW")) {
						isNextActionCodeAOSW = true;
						break;
					}
				}
			}
	 		if(isNextActionCodeAOSW){
	 			notificationMessage = IConstants.NOTIFICATION_TRAVEL_REQ_NEXT_ACTION_CODE;
	 		}
	 		else{
	 			notificationMessage = this.getSubmitNotificationMessage(treqMaster, userIsSubject);
	 		}//CN BUG 147
	 		
		} else if (treqMaster.getStatus().equals(IConstants.APPROVED))
				notificationMessage = IConstants.NOTIFICATION_TRAVEL_REQ_REQUEST_APPROVED;
	      else if (treqMaster.getStatus().equals(IConstants.REJECTED))
			    notificationMessage = IConstants.NOTIFICATION_TRAVEL_REQ_REQUEST_REJECTED;
		  return notificationMessage;
	}

	private String getSubmitNotificationMessage(AdvanceMasters advanceMaster,
			boolean userIsSubject) {
		String notificationMessage = IConstants.STRING_BLANK;
		if (advanceMaster.getRevisionNumber() == 0) {
			// user request for a new advance
			if (userIsSubject)
				notificationMessage = IConstants.NOTIFICATION_ADVANCE_REQUEST_SUBMIT_BY_SUBJECT;
			else
				// modification of an existing advance
				notificationMessage = IConstants.NOTIFICATION_ADVANCE_REQUEST_SUBMIT_BY_OTHER;
		} else if (advanceMaster.getRevisionNumber() > 0
				&& advanceMaster.getAdjIdentifier() == null) {
			// request modified
			if (userIsSubject)
				// user request for a new advance
				notificationMessage = IConstants.NOTIFICATION_ADVANCE_REQUEST_MODIFIED_BY_SUBJECT;
			else
				// modification of an existing advance
				notificationMessage = IConstants.NOTIFICATION_ADVANCE_REQUEST_MODIFIED_BY_OTHER;
		} else if (advanceMaster.getRevisionNumber() > 0
				&& advanceMaster.getAdjIdentifier() != null) {
			// request modified
			if (userIsSubject)
				// user request for a new advance
				notificationMessage = IConstants.NOTIFICATION_ADVANCE_REQUEST_ADJUSTED_BY_SUBJECT;
			else
				// modification of an existing advance
				notificationMessage = IConstants.NOTIFICATION_ADVANCE_REQUEST_ADJUSTED_BY_OTHER;
		}
		return notificationMessage;
	}
	
	private String getSubmitNotificationMessage(TravelReqMasters treqMaster,
			boolean userIsSubject) {
		String notificationMessage = IConstants.STRING_BLANK;
		if (treqMaster.getRevisionNumber() == 0) {
			// user request for a new advance
			if (userIsSubject)
				notificationMessage = IConstants.NOTIFICATION_TRAVEL_REQ_SUBMIT_BY_SUBJECT;
			else
				// modification of an existing advance
				notificationMessage = IConstants.NOTIFICATION_TRAVEL_REQ_REQUEST_SUBMIT_BY_OTHER;
		} else if (treqMaster.getRevisionNumber() > 0) {
			// request modified
			if (userIsSubject)
				// user request for a new advance
				notificationMessage = IConstants.NOTIFICATION_TRAVEL_REQ_REQUEST_MODIFIED_BY_SUBJECT;
			else
				// modification of an existing advance
				notificationMessage = IConstants.NOTIFICATION_TRAVEL_REQ_REQUEST_MODIFIED_BY_OTHER;
		} 
		return notificationMessage;
	}
	

	/**
	 * Derives the appropriate notification email message for an expense action
	 * 
	 * @param expenseMaster
	 * @param subject
	 * @param profile
	 * @return
	 */

	public String getNotificationMessage(ExpenseMasters expenseMaster,
			UserSubject subject, UserProfile profile) {
		boolean userIsSubject = false;
		String notificationMessage = IConstants.STRING_BLANK;
		if (subject.getEmployeeId() == profile.getEmpIdentifier())
			userIsSubject = true;
		
		if (expenseMaster.isPdfInd())
			notificationMessage = getPDFNotificationMessage(expenseMaster, userIsSubject);
		else{
			if (expenseMaster.getStatus().equals(IConstants.SUBMIT)) {
				notificationMessage = this.getSubmitNotificationMessage(
						expenseMaster, userIsSubject);
			} else if (expenseMaster.getStatus().equals(IConstants.APPROVED))
				notificationMessage = IConstants.NOTIFICATION_EXPENSE_REQUEST_APPROVED;
			else if (expenseMaster.getStatus().equals(IConstants.REJECTED))
				notificationMessage = IConstants.NOTIFICATION_EXPENSE_REQUEST_REJECTED;
		}
		
		return notificationMessage;
	}
	
	private String getPDFNotificationMessage(ExpenseMasters expenseMaster, boolean userIsSubject){
		String notificationMessage = IConstants.STRING_BLANK;
		
		if (expenseMaster.getStatus().equals(IConstants.SUBMIT)) {
			notificationMessage = this.getPDFSubmitNotificationMessage(
					expenseMaster, userIsSubject);
		} else if (expenseMaster.getStatus().equals(IConstants.APPROVED))
			notificationMessage = IConstants.NOTIFICATION_PDF_REQUEST_APPROVED;
		else if (expenseMaster.getStatus().equals(IConstants.REJECTED))
			notificationMessage = IConstants.NOTIFICATION_PDF_REQUEST_REJECTED;
		
		return notificationMessage;
		
	}
	
	private String getPDFSubmitNotificationMessage(ExpenseMasters expenseMaster,
			boolean userIsSubject) {
		String notificationMessage = IConstants.STRING_BLANK;
		if (expenseMaster.getRevisionNumber() == 0) {
			// user request for a new advance
			if (userIsSubject)
				notificationMessage = IConstants.NOTIFICATION_PDF_REQUEST_SUBMIT_BY_SUBJECT;
			else
				// modification of an existing advance
				notificationMessage = IConstants.NOTIFICATION_PDF_REQUEST_SUBMIT_BY_OTHER;
		} else if (expenseMaster.getRevisionNumber() > 0
				&& expenseMaster.getAdjIdentifier() == null) {
			// request modified
			if (userIsSubject)
				// user request for a new advance
				notificationMessage = IConstants.NOTIFICATION_PDF_REQUEST_MODIFIED_BY_SUBJECT;
			else
				// modification of an existing advance
				notificationMessage = IConstants.NOTIFICATION_PDF_REQUEST_MODIFIED_BY_OTHER;
		} else if (expenseMaster.getRevisionNumber() > 0
				&& expenseMaster.getAdjIdentifier() != null) {
			// request modified
			if (userIsSubject)
				// user request for a new advance
				notificationMessage = IConstants.NOTIFICATION_PDF_REQUEST_ADJUSTED_BY_SUBJECT;
			else
				// modification of an existing advance
				notificationMessage = IConstants.NOTIFICATION_PDF_REQUEST_ADJUSTED_BY_OTHER;
		}
		return notificationMessage;
	}

	private String getSubmitNotificationMessage(ExpenseMasters advanceMaster,
			boolean userIsSubject) {
		String notificationMessage = IConstants.STRING_BLANK;
		if (advanceMaster.getRevisionNumber() == 0) {
			// user request for a new advance
			if (userIsSubject)
				notificationMessage = IConstants.NOTIFICATION_EXPENSE_REQUEST_SUBMIT_BY_SUBJECT;
			else
				// modification of an existing advance
				notificationMessage = IConstants.NOTIFICATION_EXPENSE_REQUEST_SUBMIT_BY_OTHER;
		} else if (advanceMaster.getRevisionNumber() > 0
				&& advanceMaster.getAdjIdentifier() == null) {
			// request modified
			if (userIsSubject)
				// user request for a new advance
				notificationMessage = IConstants.NOTIFICATION_EXPENSE_REQUEST_MODIFIED_BY_SUBJECT;
			else
				// modification of an existing advance
				notificationMessage = IConstants.NOTIFICATION_EXPENSE_REQUEST_MODIFIED_BY_OTHER;
		} else if (advanceMaster.getRevisionNumber() > 0
				&& advanceMaster.getAdjIdentifier() != null) {
			// request modified
			if (userIsSubject)
				// user request for a new advance
				notificationMessage = IConstants.NOTIFICATION_EXPENSE_REQUEST_ADJUSTED_BY_SUBJECT;
			else
				// modification of an existing advance
				notificationMessage = IConstants.NOTIFICATION_EXPENSE_REQUEST_ADJUSTED_BY_OTHER;
		}
		return notificationMessage;
	}
    
    
    /**
     * Get supervisorEmail id
     * 
     * @param apptId
     * @return supervisors email id
     */
    private String getSupervisorEmailId(int apptId) {
		
	return emailDAO.findSupervisorEmailId(apptId);

    }

    /**
     * This method finds the FromUser Email Id
     * 
     * @param apptId
     * @return From user Email id
     */
    private String getFromUserEmailId(String userId) {
	


	return emailDAO.findFromUserMailId(userId);

    }

    /**
     * This method finds the to User Email Id
     * 
     * @param apptId
     * @return To User emailId
     */
    private String getToUserEmailId(int apptId) {
	
	return emailDAO.findToUserMailId(apptId);

    }

    /**
     * This method finds the by user name
     * 
     * @param empId
     * @return ByUserName
     */
    private String getByUserName(int empId) {
	
	return emailDAO.findByUserName(empId);

    }

    /**
     * This method finds for user name
     * 
     * @param apptId
     * @return forUserName
     */
    private String getForUserName(int apptId) {
	
	return emailDAO.findForUserName(apptId);

    }

    public AppointmentDSP getApptDSP() {
	return apptDSP;
    }

    public void setApptDSP(AppointmentDSP apptDSP) {
	this.apptDSP = apptDSP;
    }

    public EmailNotificationDAO getEmailDAO() {
	return emailDAO;
    }

    public void setEmailDAO(EmailNotificationDAO emailDAO) {
	this.emailDAO = emailDAO;
    }
   
    
}
