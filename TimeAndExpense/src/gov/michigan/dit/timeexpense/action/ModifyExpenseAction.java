package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.util.IConstants;

/**
 * Action executed when user tries to modify an expense
 * 
 * @author chaudharym
 */
public class ModifyExpenseAction extends BaseAction {

	private static final long serialVersionUID = -7186378216507320977L;

	@Override
	public void prepare() {}

	/**
	 * Sets session variable to indicate that modify 
	 * operation is being performed.
	 */
	@Override
	public String execute() throws Exception {
		session.put(IConstants.MODIFY_BUTTON_STATE_SESSION, Boolean.TRUE);
		return super.execute();
	}
	
}
