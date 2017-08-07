/**
 * 
 */
package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chiduras
 *
 */
public class ApprovalTransactionBean implements Serializable {
    
	private static final long serialVersionUID = 6794161224435726923L;

	private String transactionType;
    private String name;
	private long empIdentifier;
	private long apptIdentifier;
	private long requestId;
	private Date fromDate;
	private Date toDate;
	private double dollarAmount;
	private Long adjIdentifier;	
	private Date requestDate;
	private long supervisorEmpidentifier;	
	private String firstName;
	private String lastName;
	private String middleName;
	private String dollarAmountForDisplay;
	private long masterId;	
	private String adjIdentifierDisplay;
	private String department;
	private String agency;
	private String tku;
	private String deptCode;
	private Date lastActionDate;
	
	public String getTransactionType() {
	    return transactionType;
	    
	    
	}
	public void setTransactionType(String transactionType) {
	    this.transactionType = transactionType;
	}
	public long getEmpIdentifier() {
	    return empIdentifier;
	}
	public void setEmpIdentifier(long empIdentifier) {
	    this.empIdentifier = empIdentifier;
	}
	public long getRequestId() {
	    return requestId;
	}
	public void setRequestId(long requestId) {
	    this.requestId = requestId;
	}
	
	public double getDollarAmount() {
	    return dollarAmount;
	}
	public void setDollarAmount(double dollarAmount) {
	    this.dollarAmount = dollarAmount;
	}
	
	
	public void setName(String name) {
	    this.name = name;
	}
	public String getName() {
	    return name;
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
	public void setRequestDate(Date requestDate) {
	    this.requestDate = requestDate;
	}
	public Date getRequestDate() {
	    return requestDate;
	}
	public void setSupervisorEmpidentifier(long supervisorEmpidentifier) {
	    this.supervisorEmpidentifier = supervisorEmpidentifier;
	}
	public long getSupervisorEmpidentifier() {
	    return supervisorEmpidentifier;
	}
	public void setAdjIdentifier(Long adjIdentifier) {
	    this.adjIdentifier = adjIdentifier;
	       
	 
	    
	    if ( adjIdentifier == null  ){
	            adjIdentifierDisplay = "N";
	            	        }
	        else {
	            adjIdentifierDisplay = "Y";
	        }	
	    
	}
	public Long getAdjIdentifier() {
	    return adjIdentifier;
	}
	public void setAdjIdentifierDisplay(String adjIdentifierDisplay) {
	    this.adjIdentifierDisplay = adjIdentifierDisplay;
	}
	public String getAdjIdentifierDisplay() {
	    return adjIdentifierDisplay;
	}
	public void setFirstName(String firstName) {
	    this.firstName = firstName;
	}
	public String getFirstName() {
	    return firstName;
	}
	public void setLastName(String lastName) {
	    this.lastName = lastName;
	}
	public String getLastName() {
	    return lastName;
	}
	public void setMiddleName(String middleName) {
	    this.middleName = middleName;
	}
	public String getMiddleName() {
	    return middleName;
	}
	public void setDollarAmountForDisplay(String dollarAmountForDisplay) {
	    this.dollarAmountForDisplay = dollarAmountForDisplay;
	}
	public String getDollarAmountForDisplay() {
	    return dollarAmountForDisplay;
	}
	public void setApptIdentifier(long apptIdentifier) {
	    this.apptIdentifier = apptIdentifier;
	}
	public long getApptIdentifier() {
	    return apptIdentifier;
	}
	public void setMasterId(long masterId) {
	    this.masterId = masterId;
	}
	public long getMasterId() {
	    return masterId;
	}
	public void setDepartment(String department) {
	    this.department = department;
	}
	public String getDepartment() {
	    return department;
	}
	public void setAgency(String agency) {
	    this.agency = agency;
	}
	public String getAgency() {
	    return agency;
	}
	public void setTku(String tku) {
	    this.tku = tku;
	}
	public String getTku() {
	    return tku;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	
	public Date getLastActionDate() {
		return lastActionDate;
	}
	public void setLastActionDate(Date lastActionDate) {
		this.lastActionDate = lastActionDate;
	}

}
