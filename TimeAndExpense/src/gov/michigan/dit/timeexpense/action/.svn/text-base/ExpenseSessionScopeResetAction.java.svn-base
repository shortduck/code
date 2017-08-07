package gov.michigan.dit.timeexpense.action;

import org.apache.log4j.Logger;

import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.util.IConstants;

/**
 * Action class to reset all the expense related data in session before
 * working with a new expense.
 * 
 * @author chaudharym
 */
public class ExpenseSessionScopeResetAction extends BaseAction {

	private static final long serialVersionUID = 7557914169787323857L;
	private static Logger logger = Logger.getLogger(ExpenseSessionScopeResetAction.class);
	
	private Integer expenseMasterId;
	private Integer expenseEventId;
	
	@Override
	public String execute() throws Exception {
		ExpenseMasters expenseFromTravelRequisition = (ExpenseMasters) session.get(IConstants.CURR_EXPENSEMASTER_FROM_TREQ);
		if (expenseFromTravelRequisition != null){
			expenseMasterId = expenseFromTravelRequisition.getExpmIdentifier();
		}
		try{
			synchronized(session){
				session.remove(IConstants.EXPENSE_SESSION_DATA);
				session.remove(IConstants.MODIFY_BUTTON_STATE_SESSION);
				session.remove(IConstants.CURR_EXPENSEMASTER);
				session.remove(IConstants.EXPENSE_TOTAL_OUTSTANDING_ADVANCE_AMOUNT);
				session.remove(IConstants.ADVANCE_OUTSTANDING_LIST);
				session.remove(IConstants.EXPENSE_CURRENT_EXPENSE_MASTER_INDEX);
				session.remove(IConstants.EXPENSE_CURRENT_EXPENSE_EVENT);
				session.remove(IConstants.EXP_SUMMARY_REPORT_DATA);
				session.remove(IConstants.CURR_EXPENSEMASTER_FROM_TREQ);
				session.remove("RequestedExpenseEventId");
			}
		}catch(Exception ex){
			logger.error("Exception encountered while clearing expense session data.", ex);
			return IConstants.FAILURE;
		}
		
		// set expense master Id in session, if present
		if(expenseMasterId != null){
			session.put("RequestedExpenseId", expenseMasterId);
		} else if (expenseEventId != null){
			session.put("RequestedExpenseEventId", expenseEventId);
		}
		
		//return expenseMasterId == null ? "CreateNewExpense" : "ViewExpense"; 
		String module = ActionHelper.swapModuleId(getModuleIdFromSession(), "EXPENSE");
		if (module != null){
			session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, module);
		}
		
		return IConstants.SUCCESS;
	}

	public Integer getExpenseMasterId() {
		return expenseMasterId;
	}

	public void setExpenseMasterId(Integer expenseMasterId) {
		this.expenseMasterId = expenseMasterId;
	}

	public Integer getExpenseEventId() {
		return expenseEventId;
	}

	public void setExpenseEventId(Integer expenseEventId) {
		this.expenseEventId = expenseEventId;
	}
}
