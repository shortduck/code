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
@Table(name = "V_EXPENSES_LIST")

public class VExpensesList implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "APPT_IDENTIFIER")
	private Integer apptIdentifier;
	@Column(name = "EMP_IDENTIFIER")
	private Integer empIdentifier;
	@Column(name = "EXPEV_IDENTIFIER")
	private Integer expevIdentifier;

	@Id
	@Column(name = "EXPM_IDENTIFIER")
	private Integer expmIdentifier;
	@Column(name = "EXP_DATE_FROM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expDateFrom;
	@Column(name = "EXP_DATE_TO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expDateTo;
	@Column(name = "NATURE_OF_BUSINESS")
	private String natureOfBusiness;
	@Column(name = "TRAVEL_IND")
	private String travelInd;
	@Column(name = "OUT_OF_STATE_IND")
	private String outOfStateInd;
	@Column(name = "REVISION_NUMBER")
	private Short revisionNumber;
	@Column(name = "ADJ_IDENTIFIER")
	private Integer adjIdentifier;
	@Column(name = "ACTION_CODE")
	private String actionCode;
	@Column(name = "PAID_DATE")
	private Date paidDate;
	@Column(name = "ORIG_PAID_DATE")
	private Date OrigPaidDate;
	@Column(name = "PDF_IND")
	private String pdfInd;
	@Transient
    private String deleteFlag;
    @Transient
    private String audit;
    @Transient
    private String actionCodeForDisplay;
	
	public VExpensesList() {
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
		return expevIdentifier;
	}

	public void setExpevIdentifier(Integer expevIdentifier) {
		this.expevIdentifier = expevIdentifier;
	}

	public Integer getExpmIdentifier() {
		return expmIdentifier;
	}

	public void setExpmIdentifier(Integer expmIdentifier) {
		this.expmIdentifier = expmIdentifier;
	}

	public Date getExpDateFrom() {
		return expDateFrom;
	}

	public void setExpDateFrom(Date expDateFrom) {
		this.expDateFrom = expDateFrom;
	}

	public Date getExpDateTo() {
		return expDateTo;
	}

	public void setExpDateTo(Date expDateTo) {
		this.expDateTo = expDateTo;
	}

	public String getTravelInd() {
		return travelInd;
	}

	public void setTravelInd(String travelInd) {
		this.travelInd = travelInd;
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

	public Integer getAdjIdentifier() {
		return adjIdentifier;
	}

	public void setAdjIdentifier(Integer adjIdentifier) {
		this.adjIdentifier = adjIdentifier;
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
    
	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}


	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public Date getPaidDate() {
		return paidDate;
	}
	
	public void setOrigPaidDate(Date OrigPaidDate) {
		this.OrigPaidDate = OrigPaidDate;
	}

	public Date getOrigPaidDate() {
		return OrigPaidDate;
	}

	public String getActionCodeForDisplay() {
		return actionCodeForDisplay;
	}

	public void setActionCodeForDisplay(String actionCodeForDisplay) {
		this.actionCodeForDisplay = actionCodeForDisplay;
	}
    public String getPdfInd() {
		return pdfInd;
	}

	public void setPdfInd(String pdfInd) {
		this.pdfInd = pdfInd;
	}
}
