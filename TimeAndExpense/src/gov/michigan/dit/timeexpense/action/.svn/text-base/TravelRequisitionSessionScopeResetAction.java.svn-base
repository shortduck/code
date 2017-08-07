package gov.michigan.dit.timeexpense.action;

import org.apache.log4j.Logger;

import gov.michigan.dit.timeexpense.util.IConstants;

/**
 * Action class to reset all the expense related data in session before
 * working with a new expense.
 * 
 * @author chaudharym
 */
public class TravelRequisitionSessionScopeResetAction extends BaseAction {

	private static final long serialVersionUID = 7557914169787323857L;
	private static Logger logger = Logger.getLogger(TravelRequisitionSessionScopeResetAction.class);
	
	private Integer treqMasterId;
	private Integer treqEventId;
	
	@Override
	public String execute() throws Exception {
		try{
			synchronized(session){
				session.remove(IConstants.TRAVEL_REQUISITION_SESSION_DATA);
				session.remove(IConstants.MODIFY_BUTTON_STATE_SESSION);
				session.remove(IConstants.CURR_EXPENSEMASTER);
				session.remove(IConstants.EXPENSE_TOTAL_OUTSTANDING_ADVANCE_AMOUNT);
				session.remove(IConstants.EXPENSE_CURRENT_EXPENSE_MASTER_INDEX);
				session.remove(IConstants.EXPENSE_CURRENT_EXPENSE_EVENT);
				session.remove("RequestedTravelRequisitionEventId");
			}
		}catch(Exception ex){
			logger.error("Exception encountered while clearing expense session data.", ex);
			return IConstants.FAILURE;
		}
		
		// set expense master Id in session, if present
		if(treqMasterId != null){
			session.put("RequestedTravelRequisitionId", treqMasterId);
		} else if (treqEventId != null){
			session.put("RequestedTravelRequisitionEventId", treqEventId);
			session.remove("RequestedTravelRequisitionId");
		} else {
			session.remove("RequestedTravelRequisitionId");
		}
		
		//return expenseMasterId == null ? "CreateNewExpense" : "ViewExpense"; 
		return IConstants.SUCCESS;
	}

	public Integer getTreqMasterId() {
		return treqMasterId;
	}

	public void setTreqMasterId(Integer treqMasterId) {
		this.treqMasterId = treqMasterId;
	}

	public Integer getTreqEventId() {
		return treqEventId;
	}

	public void setTreqEventId(Integer treqEventId) {
		this.treqEventId = treqEventId;
	}
}
