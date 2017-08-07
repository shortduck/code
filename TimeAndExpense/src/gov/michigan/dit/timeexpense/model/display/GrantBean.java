package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

public class GrantBean implements Serializable {
	
	private static final long serialVersionUID = -6529087123190784481L;

	private int id;
	private String code;
	private String name;
	private String agency;
	private String display;
	private String grantNo;
	
	public GrantBean(){
		
	}
	public GrantBean(String code, String name) {
		super();
		this.code = code;
		this.code = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGrantNo() {
		return grantNo;
	}
	public void setGrantNo(String grantNo) {
		this.grantNo = grantNo;
	}
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	

}
