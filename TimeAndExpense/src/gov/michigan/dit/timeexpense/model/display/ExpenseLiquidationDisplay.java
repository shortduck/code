package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;
import java.util.Date;

public class ExpenseLiquidationDisplay implements Serializable{
	private static final long serialVersionUID = -994438L;
	
	private int empIdentifier;
    private int apptIdentifier;
    private int adevIdentifier;
    private String requestDate;
    private int advmIdentifier;
    private String fromDate;
    private String toDate;
    private String paidPpeDate;
    
    private Date dateRequested;
    private Date advanceFromDate;
    private Date advanceToDate;
    private Date paidDate;
    
    private String advanceReason;
    private double dollarAmount;
    private String permanentAdvInd;
    private short revisionNumber;
    private double manualDepositAmount;
    private double amountLiquidated;
    private double amountOutstanding;
    private double adjustedAmountOutstanding;
    
    public Date getDateRequested() {
		return dateRequested;
	}
	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}
	public Date getAdvanceFromDate() {
		return advanceFromDate;
	}
	public void setAdvanceFromDate(Date advanceFromDate) {
		this.advanceFromDate = advanceFromDate;
	}
	public Date getAdvanceToDate() {
		return advanceToDate;
	}
	public void setAdvanceToDate(Date advanceToDate) {
		this.advanceToDate = advanceToDate;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
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
	public int getAdevIdentifier() {
		return adevIdentifier;
	}
	public void setAdevIdentifier(int adevIdentifier) {
		this.adevIdentifier = adevIdentifier;
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
	public double getManualDepositAmount() {
		return manualDepositAmount;
	}
	public void setManualDepositAmount(double manualDepositAmount) {
		this.manualDepositAmount = manualDepositAmount;
	}
	public double getAmountLiquidated() {
		return amountLiquidated;
	}
	public void setAmountLiquidated(double amountLiquidated) {
		this.amountLiquidated = amountLiquidated;
	}
	public double getAmountOutstanding() {
		return amountOutstanding;
	}
	public void setAmountOutstanding(double amountOutstanding) {
		this.amountOutstanding = amountOutstanding;
	}
	public double getAdjustedAmountOutstanding() {
		return adjustedAmountOutstanding;
	}
	public void setAdjustedAmountOutstanding(double adjustedAmountOutstanding) {
		this.adjustedAmountOutstanding = adjustedAmountOutstanding;
	}
}
