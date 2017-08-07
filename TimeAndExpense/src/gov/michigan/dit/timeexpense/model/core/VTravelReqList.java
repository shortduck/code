/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
 * @author GhoshS
 */
@Entity
@Table(name = "V_TRAVEL_REQ_LIST")

public class VTravelReqList implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "APPT_IDENTIFIER")
	private Integer apptIdentifier;
	@Column(name = "EMP_IDENTIFIER")
	private Integer empIdentifier;
	@Column(name = "TREQE_IDENTIFIER")
	private Integer treqeIdentifier;

	@Id
	@Column(name = "TREQM_IDENTIFIER")
	private Integer treqmIdentifier;
	@Column(name = "TREQ_DATE_FROM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date treqDateFrom;
	@Column(name = "TREQ_DATE_TO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date treqDateTo;
	@Column(name = "NATURE_OF_BUSINESS")
	private String natureOfBusiness;
	@Column(name = "OUT_OF_STATE_IND")
	private String outOfStateInd;
	@Column(name = "REVISION_NUMBER")
	private Short revisionNumber;
	@Column(name = "ACTION_CODE")
	private String actionCode;
    @Transient
    private String deleteFlag;
	
	public VTravelReqList() {
	}

	public Integer getApptIdentifier() {
		return apptIdentifier;
	}

	public void setApptIdentifier(Integer apptIdentifier) {
		this.apptIdentifier = apptIdentifier;
	}

	public Integer getEmpIdentifier() {
		return empIdentifier;
	}

	public void setEmpIdentifier(Integer empIdentifier) {
		this.empIdentifier = empIdentifier;
	}

	public Integer getExpevIdentifier() {
		return treqeIdentifier;
	}

	public void setExpevIdentifier(Integer expevIdentifier) {
		this.treqeIdentifier = expevIdentifier;
	}

	public Integer getExpmIdentifier() {
		return treqmIdentifier;
	}

	public void setExpmIdentifier(Integer expmIdentifier) {
		this.treqmIdentifier = expmIdentifier;
	}

	public Date getExpDateFrom() {
		return treqDateFrom;
	}

	public void setExpDateFrom(Date expDateFrom) {
		this.treqDateFrom = expDateFrom;
	}

	public Date getExpDateTo() {
		return treqDateTo;
	}

	public void setExpDateTo(Date expDateTo) {
		this.treqDateTo = expDateTo;
	}

	public String getOutOfStateInd() {
		return outOfStateInd;
	}

	public void setOutOfStateInd(String outOfStateInd) {
		this.outOfStateInd = outOfStateInd;
	}

	public Short getRevisionNumber() {
		return revisionNumber;
	}

	public void setRevisionNumber(Short revisionNumber) {
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
