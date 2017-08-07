package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

public class IndexCodesBean implements Serializable {

	private static final long serialVersionUID = 1069492545798911294L;
	
	private int id;
	private String code="";
	private String name="";
	private String appr_year="";
	private String agency="";
	private String display="";

	public IndexCodesBean() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public IndexCodesBean(String appr_year, String index_code, String index_name) {
		super();
		this.appr_year = appr_year;
		this.code = index_code;
		this.name = index_name;
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
	 * @return the index_code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param index_code
	 *            the index_code to set
	 */
	public void setCode(String code) {
		this.code = code;

		if (display == null || "".equals(display)) {
			display = code;
		} else {
			display = code + " " + name;
		}
	}

	/**
	 * @return the index_name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param index_name
	 *            the index_name to set
	 */
	public void setName(String index_name) {
		this.name = index_name;

		if (display == null || "".equals(display)) {
			display = name;
		} else {
			display += " " + name;
		}
	}

	/**
	 * @return the appr_year
	 */
	public String getAppr_year() {
		return appr_year;
	}

	/**
	 * @param appr_year
	 *            the appr_year to set
	 */
	public void setAppr_year(String appr_year) {
		this.appr_year = appr_year;
	}

}
