package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.ErrorDisplayBean;
import gov.michigan.dit.timeexpense.model.display.TimeAndExpenseError;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.util.GsonDateAdapter;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Base action providing common services for user session, entity manager, 
 * validation handling and view state setup. If an <code>Action</code> 
 * requires only these services, then they should extend this base action.
 * E.g. Actions catering exclusively to Ajax requests can extend from this
 * Action to avail the common services.
 * 
 * @author chaudharym
 */
public abstract class BaseAction extends ActionSupport implements SessionAware, Preparable, ServletContextAware{
	private static final long serialVersionUID = -6546769156693876745L;

	protected Map<String, Object> session;
	protected EntityManager entityManager;
	protected Gson jsonParser;
	
	protected ErrorDisplayBean error;
	
	private String jsonResponse;
	private SecurityManager securityService;
	
	private Map<String, Object> applicationCache;
	
	/**
	 * Business validation errors collection
	 */
	private List<TimeAndExpenseError> timeExpenseErrors;
	private String moveForward = "";
	
	public BaseAction(){
		applicationCache = new HashMap<String, Object>();
		jsonParser = new GsonBuilder()
					.registerTypeAdapter(Date.class, new GsonDateAdapter()).create();
	}
	
	/**
	 * Empty initialization method to perform any initialization before the
	 * <code>Action</code> gets executed. E.g. Initializing action dependencies. 
	 */
	public void prepare(){}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		securityService = new SecurityManager(entityManager);
	}
	
	public void setSession(Map session) {
		this.session = session;
	}

	public void setServletContext(ServletContext context){
		applicationCache = (Map<String, Object>)context.getAttribute(IConstants.APPLICATION_CACHE);
		if(applicationCache == null) applicationCache = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getApplicationCache() {
		return applicationCache;
	}

	/**
	 * Adds a TimeAndExpenseError to the existing list of
	 * TimeAndExpenseError errors. Also raises an action error
	 * if not already generated to prevent the request from being
	 * processed beyond the validate method, if validation errors 
	 * are present.
	 * 
	 * @param TimeAndExpenseError
	 */
	public void addTimeAndExpenseError(TimeAndExpenseError error){
		// not created on instance creation so as to avoid
		// unnecessary list creation, in case no errors exist. 
		if(timeExpenseErrors == null){
			timeExpenseErrors = new ArrayList<TimeAndExpenseError>();
		}
		
		// add empty action error to inhibit action method execution
		// in case of validation errors.
		if(!hasActionErrors()){
			addActionError("");
		}
		
		timeExpenseErrors.add(error);
	}
	
	/**
	 * Adds all the TimeAndExpenseErrors to the existing list of
	 * TimeAndExpenseError errors. Also raises an action error
	 * if not already generated to prevent the request from being
	 * processed beyond the validate method, if validation errors 
	 * are present.
	 * 
	 * @param TimeAndExpenseError
	 */
	public void addTimeAndExpenseErrors(List<TimeAndExpenseError> errors){
		if(errors == null || errors.isEmpty()) return;
		
		// not created on instance creation so as to avoid
		// unnecessary list creation, in case no errors exist. 
		if(timeExpenseErrors == null){
			timeExpenseErrors = new ArrayList<TimeAndExpenseError>();
		}
		
		// add empty action error to inhibit action method execution
		// in case of validation errors.
		if(!hasActionErrors()){
			addActionError("");
		}
		
		timeExpenseErrors.addAll(errors);
	}
	
	public List<TimeAndExpenseError> getTimeExpenseErrors(){
		return timeExpenseErrors;
	}
	
	/**
	 * Provides JSON representation for current TimeExpenseErrors.
	 * 
	 * @return JSON string containing all TimeExpenseErrors
	 */
	public String getTimeExpenseErrorsJson(){
		return jsonParser.toJson(timeExpenseErrors);
	}

	/**
	 * Provides JSON representation for current validation errors.
	 * 
	 * @return JSON string containing all validation errors.
	 */
	public String getValidationErrorsJson(){
		return jsonParser.toJson(getFieldErrors());
	}

	public ErrorDisplayBean getError() {
		return error;
	}

	public void setError(ErrorDisplayBean error) {
		this.error = error;
	}

	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}

	/**
	 * Returns an object containing logged in user's information. 
	 * 
	 * @return UserProfile
	 */
	public UserProfile getLoggedInUser(){
		return (UserProfile)session.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
	}
	
	/**
	 * Returns an object containing information about the user upon which the work is being done
	 * by the logged in user. If the logged in user is working for himself, then this would return null.
	 * 
	 * @return UserProfile
	 */
	public UserSubject getUserSubject(){
		return (UserSubject)session.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME);
	}
	
	/**
	 * Returns the moduleId if available in session, otherwise returns an empty String.
	 * It never returns NULL, so the invoking function need not perform any NULL check.
	 * 
	 * @return moduleId  - If moduleId information available in session.
	 * 			"" - If moduleId information unavailable in session.
	 */
	protected String getModuleIdFromSession(){
		String result = null;
		
		if(session != null)
			result = (String)session.get(IConstants.LEFT_NAV_CURRENT_MODULE_ID);
		
		return result == null ? "" : result;
	}
	
	/**
	 * An empty implementation of setupDisplay method. Actions who want to
	 * setup view state should override this method.
	 */
	public void setupDisplay(){}

	/**
	 * Determines from the current moduleId whether the logged in user is playing the
	 * role of an employee or not. It returns TRUE only if module Id equals <code>IConstants.EXPENSE_EMPLOYEE</code>,
	 *  or <code>IConstants.ADVANCE_EMPLOYEE</code>.
	 * 
	 * @return TRUE or FALSE
	 */
	public boolean isUserInEmployeeRole(){
		String module = getModuleIdFromSession();
		
		return IConstants.EXPENSE_EMPLOYEE.equals(module) || IConstants.ADVANCE_EMPLOYEE.equals(module) || IConstants.TRAVEL_REQUISITIONS_EMPLOYEE.equals(module) ;
				
	}
	
	/**
	 * Determines from the current moduleId whether the logged in user is playing the
	 * role of a manager or not. It returns TRUE only if module Id equals <code>IConstants.EXPENSE_MANAGER</code>,
	 * <code>IConstants.ADVANCE_MANAGER</code> or <code>IConstants.APPROVE_WEB_MANAGER</code>.
	 * 
	 * @return TRUE or FALSE
	 */
	public boolean isUserInManagerRole(){
		String module = getModuleIdFromSession();
		
		return IConstants.EXPENSE_MANAGER.equals(module) || IConstants.ADVANCE_MANAGER.equals(module)
				|| IConstants.APPROVE_WEB_MANAGER.equals(module);
	}

	/**
	 * Determines from the current moduleId whether the logged in user is playing the
	 * role of a statewide user or not. It returns TRUE only if module Id equals <code>IConstants.EXPENSE_STATEWIDE</code>,
	 * <code>IConstants.ADVANCE_STATEWIDE</code> or <code>IConstants.APPROVE_WEB_STATEWIDE</code>.
	 * 
	 * @return TRUE or FALSE
	 */
	public boolean isUserInStatewideRole(){
		String module = getModuleIdFromSession();
		
		return IConstants.EXPENSE_STATEWIDE.equals(module) || IConstants.ADVANCE_STATEWIDE.equals(module)
				|| IConstants.APPROVE_WEB_STATEWIDE.equals(module);
	}
	

	/**
	 *  This method is used exclusively for checking module 
	 *  security through the transaction interceptor. 
	 *  
	 * @param moduleId
	 * @param profile
	 * @param userSubject
	 * @return
	 */
	
	public boolean checkModuleSecurity(String moduleId, UserProfile profile) {
		boolean hasAccess = true;

		if (profile != null){
		
			hasAccess = securityService.checkModuleAccess(profile, moduleId);
		}

		return hasAccess;
	}

	public void setMoveForward(String moveForward) {
		this.moveForward = moveForward;
	}

	public String getMoveForward() {
		return moveForward;
	}

	/** Utility function to check a string for being numeric */
	public boolean isInteger(String str){
		boolean result = true;
		
		try{ 
			Integer.parseInt(str); 
		}catch(Exception e){ 
			result = false;
		}
		
		return result;
	}
}
