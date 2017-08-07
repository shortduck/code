package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

public class CodingBlockElement implements Serializable {

	private static final long serialVersionUID = -5413810120627748541L;
	
	private String appropriationYear;
	private String deptCode = null;
	private String agency = null;
	private String tku = null;
	private String status = null;
	private Date payDate = null;
	private String cbElementType = null;
	
	
	public CodingBlockElement() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CodingBlockElement(String agency, String appropriationYear,
			String cbElementType, String deptCode, Date payDate, String status,
			String tku) {
		super();
		this.agency = agency;
		this.appropriationYear = appropriationYear;
		this.cbElementType = cbElementType;
		this.deptCode = deptCode;
		this.payDate = payDate;
		this.status = status;
		this.tku = tku;
	}


	public String getAppropriationYear() {
		return appropriationYear;
	}


	public void setAppropriationYear(String appropriationYear) {
		this.appropriationYear = appropriationYear;
	}


	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getTku() {
		return tku;
	}
	public void setTku(String tku) {
		this.tku = tku;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getCbElementType() {
		return cbElementType;
	}
	public void setCbElementType(String cbElementType) {
		this.cbElementType = cbElementType;
	}
	
	
}
