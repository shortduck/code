package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;
import java.util.Date;

/**
 * Class to encapsulate the fields to be displayed on the travel requisition transaction
 * listing page.
 * 
 */

public class TravelReqListBean implements Serializable{
	
	private static final long serialVersionUID = 2060245010082012384L;
	
	private int apptIdentifier;
	private int empIdentifier;
	private int treqmIdentifier;
	private int treqeIdentifier;
	private Date treqDateFrom;
	private Date treqDateTo;
	private String natureOfBusiness;
	private String outOfStateInd;
	private int revisionNumber;
	private String actionCode;
    private String deleteFlag;
	public TravelReqListBean() {}
	
	public TravelReqListBean(Long adjIdentifier, int apptIdentifier,
			String natureOfBusiness, int empIdentifier, Date treqDateFrom,
			Date treqDateTo, int treqmIdentifier, int treqeIdentifier, 
			String outOfStateInd, String actionCode,
			int revisionNumber, String status) {
		this.apptIdentifier = apptIdentifier;
		this.natureOfBusiness = natureOfBusiness;
		this.empIdentifier = empIdentifier;
		this.treqDateFrom = treqDateFrom;
		this.treqDateTo = treqDateTo;
		this.treqmIdentifier = treqmIdentifier;
		this.outOfStateInd = outOfStateInd;
		this.revisionNumber = revisionNumber;
		this.actionCode = actionCode;
		this.treqeIdentifier = treqeIdentifier;
	}
	
	
	public int getTreqeIdentifier() {
		return treqeIdentifier;
	}

	public void setTreqeIdentifier(int treqeIdentifier) {
		this.treqeIdentifier = treqeIdentifier;
	}

	public int getApptIdentifier() {
		return apptIdentifier;
	}
	public void setApptIdentifier(int apptIdentifier) {
		this.apptIdentifier = apptIdentifier;
	}
	public int getEmpIdentifier() {
		return empIdentifier;
	}
	public void setEmpIdentifier(int empIdentifier) {
		this.empIdentifier = empIdentifier;
	}
	public int getTreqmIdentifier() {
		return treqmIdentifier;
	}
	public void setTreqmIdentifier(int treqmIdentifier) {
		this.treqmIdentifier = treqmIdentifier;
	}
	public Date getTreqDateFrom() {
		return treqDateFrom;
	}
	public void setTreqDateFrom(Date treqDateFrom) {
		this.treqDateFrom = treqDateFrom;
	}
	public Date getTreqDateTo() {
		return treqDateTo;
	}
	public void setTreqDateTo(Date treqDateTo) {
		this.treqDateTo = treqDateTo;
	}
	public String getOutOfStateInd() {
		return outOfStateInd;
	}
	public void setOutOfStateInd(String outOfStateInd) {
		this.outOfStateInd = outOfStateInd;
	}
	public int getRevisionNumber() {
		return revisionNumber;
	}
	public void setRevisionNumber(int revisionNumber) {
		this.revisionNumber = revisionNumber;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}
	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}
	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}
	
}
