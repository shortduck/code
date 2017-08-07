package gov.michigan.dit.timeexpense.exception;

public class InvalidHrmnEmployeeException extends TimeAndExpenseException {

	private static final long serialVersionUID = -598660925623918L;

	public InvalidHrmnEmployeeException() {}
	
	public InvalidHrmnEmployeeException(String msg) {
		super(msg);
	}

	public InvalidHrmnEmployeeException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
