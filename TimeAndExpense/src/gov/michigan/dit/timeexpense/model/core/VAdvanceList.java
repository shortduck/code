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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author chiduras
 */
@Entity
@Table(name = "V_ADVANCE_LIST")

@NamedQueries({@NamedQuery(name = "VAdvanceList.findAll", query = "SELECT v FROM VAdvanceList v"), @NamedQuery(name = "VAdvanceList.findByEmpIdentifier", query = "SELECT v FROM VAdvanceList v WHERE v.empIdentifier = :empIdentifier"), @NamedQuery(name = "VAdvanceList.findByApptIdentifier", query = "SELECT v FROM VAdvanceList v WHERE v.apptIdentifier = :apptIdentifier"), @NamedQuery(name = "VAdvanceList.findByRequestDate", query = "SELECT v FROM VAdvanceList v WHERE v.requestDate = :requestDate"), @NamedQuery(name = "VAdvanceList.findByAdvmIdentifier", query = "SELECT v FROM VAdvanceList v WHERE v.advmIdentifier = :advmIdentifier"), @NamedQuery(name = "VAdvanceList.findByFromDate", query = "SELECT v FROM VAdvanceList v WHERE v.fromDate = :fromDate"), @NamedQuery(name = "VAdvanceList.findByToDate", query = "SELECT v FROM VAdvanceList v WHERE v.toDate = :toDate"), @NamedQuery(name = "VAdvanceList.findByPaidPpeDate", query = "SELECT v FROM VAdvanceList v WHERE v.paidPpeDate = :paidPpeDate"), @NamedQuery(name = "VAdvanceList.findByAdvanceReason", query = "SELECT v FROM VAdvanceList v WHERE v.advanceReason = :advanceReason"), @NamedQuery(name = "VAdvanceList.findByDollarAmount", query = "SELECT v FROM VAdvanceList v WHERE v.dollarAmount = :dollarAmount"), @NamedQuery(name = "VAdvanceList.findByPermanentAdvInd", query = "SELECT v FROM VAdvanceList v WHERE v.permanentAdvInd = :permanentAdvInd"), @NamedQuery(name = "VAdvanceList.findByRevisionNumber", query = "SELECT v FROM VAdvanceList v WHERE v.revisionNumber = :revisionNumber"), @NamedQuery(name = "VAdvanceList.findByStatus", query = "SELECT v FROM VAdvanceList v WHERE v.status = :status"), @NamedQuery(name = "VAdvanceList.findByManualDepositAmount", query = "SELECT v FROM VAdvanceList v WHERE v.manualDepositAmount = :manualDepositAmount"), @NamedQuery(name = "VAdvanceList.findByAdjIdentifier", query = "SELECT v FROM VAdvanceList v WHERE v.adjIdentifier = :adjIdentifier")})

public class VAdvanceList implements Serializable {
	
    private static final long serialVersionUID = 1L;
		
    @Column(name = "EMP_IDENTIFIER")
    private int empIdentifier;
    @Column(name = "APPT_IDENTIFIER")
    private int apptIdentifier;
    @Column(name = "ADEV_IDENTIFIER")
    private int adevIdentifier;
    @Column(name = "REQUEST_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;
    
    @Id
    @Column(name = "ADVM_IDENTIFIER")
    private int advmIdentifier;
    @Column(name = "FROM_DATE")
    private Date fromDate;
    @Column(name = "TO_DATE")
    private Date toDate;
    @Column(name = "PAID_PPE_DATE")
    private Date paidPpeDate;
    @Column(name = "ORIG_PAID_DATE")
    private Date OrigPaidDate;
    @Column(name = "ADVANCE_REASON")
    private String advanceReason;
    @Column(name = "DOLLAR_AMOUNT")
    private double dollarAmount;
    @Column(name = "PERMANENT_ADV_IND")
    private String permanentAdvInd;
    @Column(name = "REVISION_NUMBER")
    private short revisionNumber;
    @Column(name = "ACTION_CODE")
    private String actionCode;
    @Column(name = "MANUAL_DEPOSIT_AMOUNT")
    private double manualDepositAmount;
    @Column(name = "ADJ_IDENTIFIER")
    private Integer adjIdentifier;
    
    @Transient
    private String enableDelete;  
   

    @Column(name = "AMOUNT_OUTSTANDING")
    private double amountOutStanding;
    
    @Transient
    private double amountOutStandingForDisplay;
    @Transient
    private double dollarAmountForDisplay;
    @Transient
    private String deleteFlag;
    
    
    public VAdvanceList() {
    }

    public int getEmpIdentifier() {
        return empIdentifier;
    }

    public void setEmpIdentifier(int empIdentifier) {
        this.empIdentifier = empIdentifier;
    }

    public int getApptIdentifier() {
        return apptIdentifier;
    }

    public void setApptIdentifier(int apptIdentifier) {
        this.apptIdentifier = apptIdentifier;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public int getAdvmIdentifier() {
        return advmIdentifier;
    }

    public void setAdvmIdentifier(int advmIdentifier) {
        this.advmIdentifier = advmIdentifier;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getOrigPaidDate() {
        return OrigPaidDate;
    }

    public void setOrigPaidDate(Date OrigPaidDate) {
        this.OrigPaidDate = OrigPaidDate;
    }

    public Date getPaidPpeDate() {
        return paidPpeDate;
    }

    public void setPaidPpeDate(Date paidPpeDate) {
        this.paidPpeDate = paidPpeDate;
    }
    
    public String getAdvanceReason() {
        return advanceReason;
    }

    public void setAdvanceReason(String advanceReason) {
        this.advanceReason = advanceReason;
    }

    public double getDollarAmount() {
        return dollarAmount;
    }

    public void setDollarAmount(double dollarAmount) {
        this.dollarAmount = dollarAmount;
    }

    public String getPermanentAdvInd() {
        return permanentAdvInd;
    }

    public void setPermanentAdvInd(String permanentAdvInd) {
        this.permanentAdvInd = permanentAdvInd;
    }

    public short getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(short revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public double getManualDepositAmount() {
        return manualDepositAmount;
    }

    public void setManualDepositAmount(double manualDepositAmount) {
        this.manualDepositAmount = manualDepositAmount;
    }

    public Integer getAdjIdentifier() {
        return adjIdentifier;
    }

    public void setAdjIdentifier(Integer adjIdentifier) {
        this.adjIdentifier = adjIdentifier;
    }

    public void setAdevIdentifier(int adevIdentifier) {
	this.adevIdentifier = adevIdentifier;
    }

    public int getAdevIdentifier() {
	return adevIdentifier;
    }

    public void setEnableDelete(String enableDelete) {
	this.enableDelete = enableDelete;
    }

    public String getEnableDelete() {
	return enableDelete;
    }

    public double getAmountOutStanding() {
        return amountOutStanding;
    }

    public void setAmountOutStanding(double amountOutStanding) {
        this.amountOutStanding = amountOutStanding;
    }


	public void setAmountOutStandingForDisplay(
			double amountOutStandingForDisplay) {
		this.amountOutStandingForDisplay = amountOutStandingForDisplay;
	}

	public double getAmountOutStandingForDisplay() {
		return amountOutStandingForDisplay;
	}

	public void setDollarAmountForDisplay(double dollarAmountForDisplay) {
		this.dollarAmountForDisplay = dollarAmountForDisplay;
	}

	public double getDollarAmountForDisplay() {
		return dollarAmountForDisplay;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

}
