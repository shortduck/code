package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

/**
 * Used to define state of GUI components for Expense Detail view.
 * 
 * @author chaudharym
 * 
 */
public class ExpenseDetailView implements Serializable {

	private static final long serialVersionUID = -3487887323572582555L;

	private ComponentViewState supervisorReview;
	private ComponentViewState audit;
	
	public ExpenseDetailView(){}
	
	public ExpenseDetailView(ComponentViewState supervisorReview,
			ComponentViewState audit) {
		super();
		this.supervisorReview = supervisorReview;
		this.audit = audit;
	}

	public ComponentViewState getSupervisorReview() {
		return supervisorReview;
	}

	public void setSupervisorReview(ComponentViewState supervisorReview) {
		this.supervisorReview = supervisorReview;
	}

	public ComponentViewState getAudit() {
		return audit;
	}

	public void setAudit(ComponentViewState audit) {
		this.audit = audit;
	}
	
}
