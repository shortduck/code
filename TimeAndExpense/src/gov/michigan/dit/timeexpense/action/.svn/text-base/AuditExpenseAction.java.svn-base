package gov.michigan.dit.timeexpense.action;

import javax.persistence.OptimisticLockException;

import gov.michigan.dit.timeexpense.model.core.ExpenseActions;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.util.IConstants;

/**
 * Action responsible for auditing an expense.
 * 
 * @author chaudharym
 */
public class AuditExpenseAction extends BaseAction {

	private static final long serialVersionUID = 333893044032461249L;

	@Override
	public String execute() throws Exception {
		ExpenseMasters expense = null;
		try {

			if (session != null) {
				expense = (ExpenseMasters) session.get(IConstants.EXPENSE_SESSION_DATA);
				expense = entityManager.merge(expense);
			}

			// mark the submitted expense to be audit complete
			if (expense != null && expense.getStatus() != null && !"".equals(expense.getStatus())) {
				ExpenseActions auditAction = new ExpenseActions();
				auditAction.setActionCode(IConstants.AUDIT);
				auditAction.setExpmIdentifier(expense);
				auditAction.setModifiedUserId(getLoggedInUser().getUserId());

				entityManager.persist(auditAction);
				entityManager.flush();
				setJsonResponse("{expenseActionId:" + auditAction.getExpaIdentifier() + "}");
			}
		} catch (OptimisticLockException e) {
			addFieldError(
					"errors",
					"The record you are attempting to change has already been modified by another user.  Please reopen the record before making any changes.");
		}
		return IConstants.SUCCESS;
	}
}
