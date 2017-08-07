package gov.michigan.dit.timeexpense.model.display;

/**
 * Display bean to hold information related to an error for display purposes.
 *
 * @author chaudharym
 */
public class ErrorDisplayBean {

	private String errorCode;
	private String errorMessage;
	private boolean redirectOption = false;
	private boolean previousOption = false;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isRedirectOption() {
		return redirectOption;
	}

	public void setRedirectOption(boolean redirectOption) {
		this.redirectOption = redirectOption;
	}

	public boolean isPreviousOption() {
		return previousOption;
	}

	public void setPreviousOption(boolean previousOption) {
		this.previousOption = previousOption;
	}
}
