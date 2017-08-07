package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.exception.InvalidHrmnEmployeeException;
import gov.michigan.dit.timeexpense.exception.TimeAndExpenseException;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.display.ErrorDisplayBean;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.util.IConstants;

import org.apache.log4j.Logger;

/**
 * This action takes care of the login functionality by providing
 * authorizing access to application. If the user's security is 
 * setup as to disallow application access, he is appropriately 
 * notified about it. Otherwise, a user profile is built and 
 * stored in the user session and user is successfully logged 
 * into the application.
 * 
 * @author chaudharym
 */
public class LoginAction extends AbstractAction {
	
	private static final long serialVersionUID = -2634876990244440706L;
	private static Logger logger = Logger.getLogger(LoginAction.class);
	
	private String selfServiceId;
	private UserProfile user;
	private SecurityManager securityService;
	
	public void prepare(){
		securityService = new SecurityManager(entityManager);
		
		//TODO[mc] : Move the following to execute method to prevent
		// selfServiceId being overridden by request parameter in GET!
	/*	 if(getRequest() != null)
		 selfServiceId = getRequest().getRemoteUser();*/
		}
	
	public String execute() {
		String result = IConstants.SUCCESS;
		// AH, AI 21201 - Prevent multiple logins for same browser type
		if (!startFreshSession()) {
			setupError(
					"",
					"You are already logged in to the Time & Expense application. Please close all browser windows and access the application from a new browser window.");
			return IConstants.FAILURE;
		}

		if(getRequest() != null)
			selfServiceId = getRequest().getRemoteUser();
		
		boolean isSelfServiceUser = (selfServiceId.toLowerCase().startsWith("s")) 
									 ? true : false;
		
		int hrmnEmpId = 0;
		// Are last seven characters digits ?
		try{
			hrmnEmpId = constructHrmnEmpId();
				}catch(InvalidHrmnEmployeeException ex){
			setupError(ex.getErrorCode(), ex.getMessage());
			result = IConstants.FAILURE; 
		}
		
		//if valid self service id, construct user profile 
	 
		if(hrmnEmpId != 0){
			result = buildAndSaveUserProfile(hrmnEmpId, isSelfServiceUser);
		}
		
		return result;
	}	
	 	
	private String buildAndSaveUserProfile(int hrmnEmpId, boolean isSelfServiceUser) {
		String result = IConstants.SUCCESS;
		
		try{
			user = securityService.getDcdsUser(hrmnEmpId, isSelfServiceUser);
		}catch(TimeAndExpenseException tee){
			setupError(tee.getErrorCode(), tee.getMessage());
			result = IConstants.FAILURE;
			return result;
		}
		
		if(user != null){
			// push user profile into session
			session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, user);
			session.put(IConstants.FIRST_TIME, new String("true"));
		}else{
			setupError("", IConstants.NO_USER_FOUND);
			session.put(IConstants.NO_DCDS_USER_ID,"true");
			result = IConstants.NO_USER; 
		}
		
		return result;
	}

	private int constructHrmnEmpId() throws InvalidHrmnEmployeeException{
		if(logger.isDebugEnabled()) 
			logger.debug("HRMN self service Id received from request: "+ selfServiceId);
if(selfServiceId == null || "".equals(selfServiceId.trim())){
					throw new InvalidHrmnEmployeeException(IConstants.MISSING_HRMN_EMPID);
		}
		
		try{  
			return new Integer(selfServiceId.substring(selfServiceId.length() - 7));
			 
		}catch(Exception ex){
			throw new InvalidHrmnEmployeeException(IConstants.INVALID_HRMN_EMPID, ex);
		}
	}

	private void setupError(String errorCode, String errorMsg) {
		if(logger.isInfoEnabled()) logger.error(errorCode + " - " + errorMsg);
	
		error = new ErrorDisplayBean();
		error.setErrorCode(errorCode);
		error.setErrorMessage(errorMsg);
		error.setRedirectOption(false);
	}

	private boolean startFreshSession() {
		if(session != null) {
			UserProfile userProfile = getLoggedInUser();
			if (userProfile != null){
				return false;
			} else{
				session.clear();
			}			
		}
			return true;
	}
	
	public String getSelfServiceId() {
		return selfServiceId;
	}

	public void setSelfServiceId(String selfServiceId) {
		this.selfServiceId = selfServiceId;
	}

}
