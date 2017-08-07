package gov.michigan.dit.timeexpense.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import gov.michigan.dit.timeexpense.model.core.ExpenseHistory;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.TravelReqEvents;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.TravelRequisitionDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

/**
 * Action class for the expense History tab
 */
public class ExpenseHistoryAction extends AbstractAction implements	ServletRequestAware {
	private static final long serialVersionUID = 8357771470600623493L;
	private ExpenseDSP expenseService = null;
	private TravelRequisitionDSP treqService;
	private List<ExpenseHistory> expenseHistoryList = null;
    private HttpServletRequest req;
	private ExpenseMasters expenseMaster;
	private String status = "";
	private int treqEventId;
	private int cloneExpenseEventId;	
	private int cloneExpenseMasterId;
	private static final Logger logger = Logger.getLogger(ExpenseHistoryAction.class);

	@Override
	public void prepare() {
		expenseService = new ExpenseDSP(entityManager);
		treqService = new TravelRequisitionDSP(entityManager);
	}

	/**
	 * Gets the Expenses History.
	 * @return success:Find mapping in struts.xml
	 */
	public String getExpenseHistoryList() {
		// default method invoked when this action is called - struts framework rule
		if (logger.isDebugEnabled())
			logger.debug("inside expense history action");
		String status = "";
		expenseMaster = (ExpenseMasters) session.get(IConstants.EXPENSE_SESSION_DATA);
		
		expenseMaster = entityManager.merge(expenseMaster);

		if (expenseMaster == null) {
			super.setJsonResponse(jsonParser.toJson(expenseHistoryList));
		} else {
			expenseHistoryList = expenseService.getExpenseHistory(expenseMaster.getExpevIdentifier().getExpevIdentifier());
			
			
			cloneExpenseEventId = (expenseMaster.getExpevIdentifier().getExpClonedFrom() != null) ? expenseMaster
					.getExpevIdentifier().getExpClonedFrom() : 0;			
			
			if (cloneExpenseEventId > 0)
				cloneExpenseMasterId = expenseService
						.getExpenseByExpenseEventId(cloneExpenseEventId, 0)
						.getExpmIdentifier();			
			
			super.setJsonResponse(jsonParser.toJson(expenseHistoryList));
			status = expenseMaster.getStatus();
			String nextActionCode = null;
			nextActionCode = expenseService.getNextActionCode(expenseMaster);
			
			//when NextActionCode is null then there will be no left approval paths then simple display the status (else part)
			if (nextActionCode != null) {			
				setStatus(expenseService.getRemainingApprovalPaths(expenseMaster.getExpmIdentifier(), getUserSubject() ));
			}
			else
				if (status != null) {
					if (status.equals(IConstants.APPROVED)
							|| status.equals(IConstants.EXTRACTED)
							|| status.equals(IConstants.HOURS_ADJUSTMENT_SENT)) {
						setStatus(IConstants.HISTORYMSG_APPROVED_EXTRACTED_HOURS_ADJUSTMENT_SENT);
					} else if (status.equals(IConstants.PROCESSED)) {
						setStatus(IConstants.HISTORYMSG_PROCESSED);
					} else if (status.equals(IConstants.REJECTED)) {
						setStatus(IConstants.HISTORYMSG_REJECTED);
					}
				}
				//Expense has not been submitted
				else{
					setStatus(IConstants.HISTORYMSG_NEEDSTOBESUBMITTED);
				}
					
			// fetch related travel requisition event
			TravelReqEvents event	= treqService.getTravelRequisitionRelatedWithExpense(expenseMaster.getExpevIdentifier().getExpevIdentifier());
			if (event != null){
				treqEventId = event.getTreqeIdentifier();
			}
		}		
		return IConstants.SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest req) {
		this.req = req;
	}

	public ExpenseMasters getExpenseMaster() {
		return expenseMaster;
	}

	public void setExpenseMaster(ExpenseMasters expenseMaster) {
		this.expenseMaster = expenseMaster;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public int getTreqEventId() {
		return treqEventId;
	}

	public void setTreqEventId(int treqEventId) {
		this.treqEventId = treqEventId;
	}

	public int getCloneExpenseEventId() {
		return cloneExpenseEventId;
	}

	public void setCloneExpenseEventId(int cloneExpenseEventId) {
		this.cloneExpenseEventId = cloneExpenseEventId;
	}

	public int getCloneExpenseMasterId() {
		return cloneExpenseMasterId;
	}

	public void setCloneExpenseMasterId(int cloneExpenseMasterId) {
		this.cloneExpenseMasterId = cloneExpenseMasterId;
	}	
}
