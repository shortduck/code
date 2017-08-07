package gov.michigan.dit.timeexpense.interceptor;

import gov.michigan.dit.timeexpense.action.BaseAction;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;

/**
 * Interceptor acting as a view controller. It gives actions 
 * a chance to define the state of their GUI components. 
 * This is done by invoking the method, <code>setupDisplay</code>
 * in the action.
 * PS: Interceptor instances are shared by multiple request threads
 * and therefore any instance field operations must be synchronized.   
 * 
 * @author chaudharym
 */
public class ViewSetupInterceptor implements Interceptor {

	private static final long serialVersionUID = -2144139195516721278L;
	private static Logger logger = Logger.getLogger(ViewSetupInterceptor.class); 

	public void init() {}

	public String intercept(ActionInvocation invocation) throws Exception {
		if(logger.isDebugEnabled())
			logger.debug("Giving action a chance to setup display");

		// attach a listener to be executed after action invocation 
		// but before results rendering.
		invocation.addPreResultListener(new PreResultListener() {
            public void beforeResult(ActionInvocation invocation, String resultCode) {
            	((BaseAction)invocation.getAction()).setupDisplay();
            }
        });
		
		return invocation.invoke();
	}

	public void destroy() {}
}
