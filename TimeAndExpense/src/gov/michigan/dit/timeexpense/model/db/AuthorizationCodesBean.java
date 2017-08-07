package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;

public class AuthorizationCodesBean implements Serializable {

	private static final long serialVersionUID = 6694742208566817250L;

	private long id;
	private String description;
	
	
	public AuthorizationCodesBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
