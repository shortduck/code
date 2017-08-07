/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;

public class DisplayAdvanceList implements Serializable {
	
	private static final long serialVersionUID = 8065875460110725453L;

	private int empIdentifier;
    private int apptIdentifier;
    private int adevIdentifier;
    private String requestDate;
    private int advmIdentifier;
    private String fromDate;
    private String toDate;
    private String paidPpeDate;
    private String OrigPaidDate;
    private String advanceReason;
    private double dollarAmount;
    private String permanentAdvInd;
    private short revisionNumber;
    private String status;
    private double manualDepositAmount;
    private Integer adjIdentifier;
    private String enableDelete;
    private double amountOutstanding;

    
    public DisplayAdvanceList() {
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

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public int getAdvmIdentifier() {
        return advmIdentifier;
    }

    public void setAdvmIdentifier(int advmIdentifier) {
        this.advmIdentifier = advmIdentifier;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getPaidPpeDate() {
        return paidPpeDate;
    }

    public void setPaidPpeDate(String paidPpeDate) {
        this.paidPpeDate = paidPpeDate;
    }

    public String getOrigPaidDate() {
        return OrigPaidDate;
    }

    public void setOrigPaidDate(String OrigPaidDate) {
        this.OrigPaidDate = OrigPaidDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

	public void setAmountOutstanding(double amountOutstanding) {
		this.amountOutstanding = amountOutstanding;
	}

	public double getAmountOutstanding() {
		return amountOutstanding;
	}

}
