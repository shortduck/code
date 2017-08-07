package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

public class Ag3Bean implements Serializable {

	private static final long serialVersionUID = 6664116322954395624L;

	private int id;
	private String code;
	private String name;
	private String agency;
	private String display;

	public Ag3Bean() {

	}

	public Ag3Bean(String code, String name) {
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

	public String getCode() {
		return code;
	}

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
