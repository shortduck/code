package gov.michigan.dit.timeexpense.action;

/**
 * Action class to just touch the session and do nothing else
 * 
 * @author chaudharym
 */
@SuppressWarnings("serial")
public class DoNothingAction extends BaseAction {

	@Override
	public String execute() throws Exception {
		return "";
	}	
}
