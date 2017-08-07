package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.util.IConstants;

/**
 * Action to clear the last successful action code from session.
 * 
 * @author chaudharym
 */
public class ClearLastActionFromSessionAction extends BaseAction {
	private static final long serialVersionUID = -2973819905713548290L;

	@Override
	public String execute() throws Exception {
		if(session.containsKey(IConstants.LAST_OPERATION)){
			synchronized (session) {
				session.remove(IConstants.LAST_OPERATION);
			}
		}
		
		return IConstants.SUCCESS;
	}
}
