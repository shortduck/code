package gov.michigan.dit.timeexpense.exception;


/**
 * Exception raised if <code>ExpenseRates</code> not found for an <code>ExpenseTypes</code>
 * 
 * @author chaudharym
 */
public class ExpenseRateNotFoundException extends TimeAndExpenseException {

	private static final long serialVersionUID = -351409617135907930L;

	public ExpenseRateNotFoundException(){
		super();
	}
	
	public ExpenseRateNotFoundException(String errorCode, String msg) {
		super(errorCode, msg);
	}
	
	public ExpenseRateNotFoundException(String msg) {
		super(msg);
	}
	
	public ExpenseRateNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
