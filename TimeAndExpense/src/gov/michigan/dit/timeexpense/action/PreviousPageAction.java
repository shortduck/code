/**
 * PreviousPageAction
 * 
 * Used to restore session attributes from the previous action. This action
 * is invoked through Ajax from Previous Page Link defined on the template
 * 
 */

package gov.michigan.dit.timeexpense.action;

public class PreviousPageAction extends AbstractAction {
	
	public String execute(){
		ActionHelper.restorePreviousSessionAttributes(session);
		session.put("PreviousPageAction", "true");
		String retJson= "{\"previousResult\": \"done\"}";
		setMoveForward("false"); 
		return retJson;
	}
}
