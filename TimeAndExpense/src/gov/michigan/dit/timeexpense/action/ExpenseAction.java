package gov.michigan.dit.timeexpense.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

/**
 * Action class providing general methods related to the
 * <code>ExpenseMasters</code> in session.
 * 
 * @author chaudharym
 */
public class ExpenseAction extends BaseAction {
	private static final long serialVersionUID = 3583084739498735590L;
	
	private Date fromDate;
	private Date toDate;
	private Integer expevIdentifier;
	private Date origFromDate;
	private Date origToDate;
	

	/**
	 * Calculates the total expense amount for the current 
	 * expense in session. If no expense found, then the expense
	 * Id is '0' and the amount is '0'.
	 */
	public String calculateExpenseAmount(){
		double amount = 0;
		
		ExpenseMasters expense = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);
		if(expense!= null){
			expense = entityManager.merge(expense);
			amount = expense.getAmount();
		}
		
		StringBuilder buff = new StringBuilder("{");
		buff.append("expenseMasterId:");
		buff.append(expense==null?0:expense.getExpmIdentifier());
		buff.append(",amount:");
		buff.append(amount);
		buff.append("}");
		
		setJsonResponse(buff.toString());

		return IConstants.SUCCESS;
	}
	
	
	public String cloneExpense(){
		
		
		String returnValue = validateCloning();
		
		if (returnValue.length() > 0)
			setJsonResponse(makeJsonString(false, returnValue));
		
		else{
			ExpenseDSP expenseService = new ExpenseDSP(entityManager);
					
			//the return value can be a expense event identifier else will be a error message.
			returnValue = expenseService.cloneExpense(expevIdentifier, fromDate, toDate);		
			
			boolean successful = isNumber(returnValue);
			
			//Call liquidation method to auto liquidate this expense.
			if (successful)
				expenseService.checkLiquidations(expenseService.getExpense(Integer.parseInt(returnValue)), getUserSubject());
			
			setJsonResponse(makeJsonString(successful, returnValue));
		}
		
		return IConstants.SUCCESS;		
	}
	
	private String validateCloning() {
		
		//expevIdentifier, fromDate, toDate
		String errorMessage = "";
		
		if (expevIdentifier <= 0) 
			errorMessage = "Invalid expevIdentifier";
		
		if(fromDate == null )
			errorMessage = "Invalid 'from' date.";

		if(toDate == null )
			errorMessage = "Invalid 'to' date.";
		
		if (toDate.compareTo(fromDate) < 0)
			errorMessage = "Expense 'To' date cannot be less than 'From' date";
		
		if ((origFromDate.compareTo(fromDate) == 0) && (origToDate.compareTo(toDate) == 0))
			errorMessage = "Original Expense dates are same as cloned dates";
		
		if ((fromDate.after(origFromDate) && fromDate.before(origToDate))
				|| (toDate.after(origFromDate) && toDate.before(origToDate)))
			errorMessage = "Cloned dates are overlapping original expense dates";
		
		
		return errorMessage;
	}


	private String makeJsonString(boolean successful, String returnValue) {
		StringBuilder buff = new StringBuilder("{");

		buff.append("expevIdentifier:");
		// if cloning was successful then pass the expense event identifier.
		buff.append(successful ? Integer.parseInt(returnValue) : 0);
		buff.append(",message:\"");
		// if cloning was NOT successful then pass the error message.
		buff.append(successful ? "" : returnValue);
		buff.append("\"}");
		return buff.toString();
	}


	public boolean isNumber(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException nfe) {}
	    return false;
	}



	public Date getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public Date getToDate() {
		return toDate;
	}


	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	public Integer getExpevIdentifier() {
		return expevIdentifier;
	}


	public void setExpevIdentifier(Integer expevIdentifier) {
		this.expevIdentifier = expevIdentifier;
	}


	public Date getOrigFromDate() {
		return origFromDate;
	}


	public void setOrigFromDate(Date origFromDate) {
		this.origFromDate = origFromDate;
	}


	public Date getOrigToDate() {
		return origToDate;
	}


	public void setOrigToDate(Date origToDate) {
		this.origToDate = origToDate;
	}
	
	
	
}
