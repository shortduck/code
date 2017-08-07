package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.util.IConstants;

/**
 * Action to save Filter for Manager/State-wide Approvals
 * 
 * @author jadcharlas
 * 
 */
public class SaveFilterAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String filter;

	@Override
	public void prepare() {
	}

	@Override
	public String execute() throws Exception {
		String moduleId = (String) session.get(IConstants.LEFT_NAV_CURRENT_MODULE_ID);
		if (moduleId.equals(IConstants.APPROVE_WEB_MANAGER)) {// for Manager Approvals
			session.put("managerApprovalsFilterPref", filter);
		} else if (moduleId.equals(IConstants.APPROVE_WEB_STATEWIDE)) {// for Statewide Approvals
			session.put("statewideApprovalsFilterPref", filter);
		}
		return IConstants.SUCCESS;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

}