package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

public class GrantPhaseBean implements Serializable {

	private static final long serialVersionUID = 3630604479782243360L;

	private int id;
	private String code;
	private String grant_phase;
	private String name;
	private String agency;
	private String display;
	private String grantNo;

	public GrantPhaseBean() {
	}

	public GrantPhaseBean(String code, String grant_phase, String name) {
		super();
		this.code = code;
		this.grant_phase = grant_phase;
		this.name = name;
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

	/**
	 * @return the grant_number
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param grant_number
	 *            the grant_number to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the grant_phase
	 */
	public String getGrant_phase() {
		return grant_phase;
	}

	/**
	 * @param grant_phase
	 *            the grant_phase to set
	 */
	public void setGrant_phase(String grant_phase) {
		this.grant_phase = grant_phase;
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
