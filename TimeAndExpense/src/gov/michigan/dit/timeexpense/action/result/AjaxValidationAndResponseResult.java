package gov.michigan.dit.timeexpense.action.result;

/**
 * Prepares the Ajax response including the validation results
 * and any custom JSON response that the Action may want to send.
 * 
 * To achieve it the following must be true:
 * <ul>
 * 	<li>The Action using this result is a subclass of 
 * 		<code>BaseAction</code></li>
 * 	<li>The instance variable 'JsonResponse' inside the Action
 * 	is set to the custom response to be sent back.</li>
 * </ul>  
 * 
 * @author chaudharym
 */
public class AjaxValidationAndResponseResult extends AjaxValidationResult {

	public AjaxValidationAndResponseResult(){
		super();
	}
	
	@Override
	protected void populateJsonVariablesMap() {
		// populate whatever super populates
		super.populateJsonVariablesMap();
		
		// and add the custom Ajax response
		jsonProperties.put("response", action.getJsonResponse());
	}
}
