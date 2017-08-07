package gov.michigan.dit.timeexpense.interceptor;

import gov.michigan.dit.timeexpense.action.ActionHelper;
import gov.michigan.dit.timeexpense.action.BaseAction;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.display.ErrorDisplayBean;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.JPAUtil;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Interceptor implementing the <code>OpenSessionInView</code>
 * pattern. It attaches an <code>EntityManager</code> to the 
 * underlying thread processing the request. Data access layers
 * can then read this EntityManager and use it for data access.
 * PS: Interceptor instances are shared by multiple request threads
 * and therefore any instance field operations must be synchronized.   
 * 
 * @author chaudharym
 */
public class TransactionInterceptor implements Interceptor {

	private static final long serialVersionUID = -5097670252409014L;
	private static Logger logger = Logger.getLogger(TransactionInterceptor.class); 

	public void init() {}

	/**
	 * Intercepts current request under process
	 * 
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = IConstants.GLOBAL_EXCEPTION;
		// Setup context and other data necessary for subsequent security check after a successful login 
		ActionContext context = invocation.getInvocationContext ();
	    HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
	    HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);
	    HttpSession session =  request.getSession(true);
	    
	    if (!request.isRequestedSessionIdValid() && !context.getName().equals("Login")){
	    	ErrorDisplayBean error = ((BaseAction)invocation.getAction()).getError();
	    	if(error == null) error = new ErrorDisplayBean();
	    	error.setErrorMessage("Your session expired due to inactivity for security reasons. Please close the browser window and start the application again to proceed.");
	    	error.setRedirectOption(false);
	    	((BaseAction)invocation.getAction()).setError(error);
	
	    	// return global exception if an unexpected error has occurred
	    	return result;
	    }
	    // set cache control
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader ("Expires", -1);
	    
	    // Ensure that only one request per session is executing. If another request is in process
	    // then hold on until it finishes. 
	    synchronized(this) {
		    Boolean ready = readyForProcessing(session);
		    if(!ready) this.wait();
		    else if (!("RoutineTravelerReport".equals(context.getName())) &&
		    		!("ExceptionReportAction".equals(context.getName())) &&
		    		!("ReceiptsRequiredReportAction".equals(context.getName())) &&
		    		!("TransactionLedgerViewer".equals(context.getName()))) {
		    	// set ready state if reports are not being run
		    	session.setAttribute(IConstants.READY_STATE, Boolean.FALSE);
		    }
		}
	    
	    //log request/session parameters
	    ActionHelper.logParameters(context, request, session);
	    
		EntityManager em = null;
		try{
			// create entity manager
			em = JPAUtil.getEntityManagerFactory().createEntityManager();
			
			// set entity manager in underlying Action
			((BaseAction)invocation.getAction()).setEntityManager(em);
			
			// execute the configured Action within the transaction boundaries
			em.getTransaction().begin();
			String module = IConstants.STRING_BLANK;
			boolean securityAccess = false;
			
			// extract session attributes to be saved later if necessary
			Map currentSessionAttributes = ActionHelper.extractSessionAttributes(session);
			
			// get module and user information
			
			//[mc - 01/25/10] Always find moduleId from session first.
			String moduleId = (String)session.getAttribute(IConstants.LEFT_NAV_CURRENT_MODULE_ID);
			if(moduleId == null || "".equals(moduleId)) module = request.getParameter("moduleId");
			
			UserProfile profile = (UserProfile) session.getAttribute(IConstants.USER_PROFILE_SESSION_KEY_NAME);
			
			if (module != null && !IConstants.STRING_BLANK.equals(module) && profile != null)
				// User has successfully logged into the application and has accessed some application feature
				// that performed UserSubject setup. Last check left is for the module id and that is done below
				// by using the module id passed in the request. 
				securityAccess = ((BaseAction)invocation.getAction()).checkModuleSecurity(module, profile);
			else 
				// Most likely because this is the initial access to the application. No module check done in this case
				securityAccess = true;
			if (securityAccess){
				
				// checks prior to this action invocation
				String prevAction = (String) session.getAttribute("PreviousPageAction");
				if ("true".equals(prevAction) || "ViewExpenseList".equals(context.getName()) ||
						"ExpenseDeleteAction".equals(context.getName()) ||
						"AdvanceListAction".equals(context.getName()) ||
						"AdvanceDeleteAction".equals(context.getName())){
					// previous page link has been used or do not forward from client
					((BaseAction)invocation.getAction()).setMoveForward("false");
				} else{
					// Either not a back action or back button has been used
					// rather than the Previous Page link
					((BaseAction)invocation.getAction()).setMoveForward("true");
				}
				
				result = invocation.invoke();
				// only commit if not previously committed or rolled back.
				if(em.getTransaction().isActive()) em.getTransaction().commit();
				
				// Copy previous values to stack if not an Ajax action or PreviousPageAction or the action executed
				// immediately after PreviousPageAction
				if (!"AjaxValidationAndResponseResult".equals(invocation.getResult().getClass().getSimpleName()) &&
						!"PreviousPageAction".equals(context.getName()) &&
						(prevAction == null || "false".equals(prevAction))){
					ActionHelper.copySessionAttributes(currentSessionAttributes, session, context.getName());	
				}
				
				if ("true".equals(prevAction)){
					session.setAttribute("PreviousPageAction", "false");
				}
			}
			else {
				result = IConstants.SECURITY_FAILURE;
				// rollback if not previously committed or rolled back.
				if(em.getTransaction().isActive()) em.getTransaction().rollback();
			}
			
		} catch (RollbackException e) {
		    if (e.getCause() instanceof OptimisticLockException) {
    			logger.error("Record was already modified.", e);
    			((BaseAction)invocation.getAction()).addFieldError("errors", "The record you are attempting to change has been modified by another user.  Please reload the record and then make any changes necessary.");			
		    }
		} catch (OptimisticLockException e) {
			logger.error("Record was already modified.", e);
			((BaseAction)invocation.getAction()).addFieldError("errors", "The record you are attempting to change has been modified by another user.  Please reload the record and then make any changes necessary.");
		} catch(Exception ex){
			// If unable to create EntityManagerFactory, raise error
				if(em == null){
				logger.error("Unable to create EntityManagerFactory!", ex);
			}
			// if any unhandled exception is encountered, try to roll back the transaction
			// and show the error page.
			else if(em != null && em.isOpen() && em.getTransaction().isActive()){
				try{
					em.getTransaction().rollback();
					logger.error("Transaction successfully rolled back.", ex);
				}catch(Exception e){
					// if unable to roll back, log the exception and go to error page.
					logger.error("Exception encountered during transaction rollback. Could not rollback transaction!", e);
				}
			} else
				logger.error("Exception", ex);
		}finally{
			if(em != null && em.isOpen()){
				em.close();
			}
			
			synchronized (this){ 
				session.setAttribute(IConstants.READY_STATE, Boolean.TRUE);
				this.notifyAll();
			}
		}
		
		return result;
	}
	
	private Boolean readyForProcessing(HttpSession session) {
		Boolean ready = (Boolean)session.getAttribute(IConstants.READY_STATE);
		
		if(ready == null){
			ready = Boolean.TRUE;
			session.setAttribute(IConstants.READY_STATE, ready);
		}
	
		return ready;
	}

	public void destroy() {}
}
