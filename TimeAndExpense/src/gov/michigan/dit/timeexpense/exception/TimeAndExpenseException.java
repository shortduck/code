package gov.michigan.dit.timeexpense.exception;

/**
 * General exception class to capture error code and message
 * for any exception raised in the application. 
 * 
 * @author chaudharym
 *
 */
public class TimeAndExpenseException extends Exception{

	private static final long serialVersionUID = -5886608L;

	private String errorCode;
	
	public TimeAndExpenseException() {}

	public TimeAndExpenseException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	public TimeAndExpenseException(String msg) {
		super(msg);
	}
	
	public TimeAndExpenseException(String msg, Throwable cause){
		super(msg, cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
