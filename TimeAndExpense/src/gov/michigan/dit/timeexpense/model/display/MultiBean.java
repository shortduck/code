package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

public class MultiBean implements Serializable {

	private static final long serialVersionUID = 2490196582538360624L;
	
	private int id;
	private String code;
	private String name;
	private String agency;
	private String display;
	
	public MultiBean(){
		
	}
	
	public MultiBean(String multipurpose_code, String name) {
		super();
		this.code = multipurpose_code;
		this.name = name;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	
	/**
	 * @return the MULTIPURPOSE_CODE
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param multipurpose_code the mULTIPURPOSE_CODE to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	

}
