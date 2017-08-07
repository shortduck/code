package gov.michigan.dit.timeexpense.model.display;

/**
 * Used to encapsulate server side validation results for the input
 * data provided by the user.
 * 
 * @author chaudharym
 */
public class TimeAndExpenseError {

	private Integer id;
	private String source;
	private String code;
	private String description;
	private String severity;
	
	public TimeAndExpenseError(Integer id, String code, String description, String severity,
			String source) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
		this.severity = severity;
		this.source = source;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}
	
}
