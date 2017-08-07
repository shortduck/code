package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

public class Ag2Bean implements Serializable {

	private static final long serialVersionUID = 8316067201412104496L;

	private int id;
	private String code;
	private String name;
	private String agency;
	private String display;

	public Ag2Bean() {

	}

	public Ag2Bean(String code, String name) {
		super();
		this.code = code;
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
	 * @return the agency_code_2
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param agency_code_2
	 *            the agency_code_2 to set
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
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
