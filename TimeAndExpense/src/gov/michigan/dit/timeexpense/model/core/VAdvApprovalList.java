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

/**
 *
 * @author chiduras
 */
@Entity
@Table(name = "V_ADV_APPROVAL_LIST")
@NamedQueries({@NamedQuery(name = "VAdvApprovalList.findAll", query = "SELECT v FROM VAdvApprovalList v")})
public class VAdvApprovalList implements Serializable {

	private static final long serialVersionUID = 7905042271646345056L;

	@Column(name = "NAME")
    private String name;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "SUPERVISOR_EMPIDENTIFIER")
    private Integer supervisorEmpidentifier;
    @Column(name = "EMP_IDENTIFIER")
    private Integer empIdentifier;
    @Column(name = "APPT_IDENTIFIER")
    private Integer apptIdentifier;
    @Id
    @Column(name = "ADEV_IDENTIFIER")
    private Integer requestId;
    @Column(name = "ADJ_IDENTIFIER")
    private Long adjIdentifier;
    @Column(name = "REQUEST_DATE")
  
    private Date requestDate;
    @Column(name = "FROM_DATE")
   
    private Date fromDate;
    @Column(name = "TO_DATE")
   
    private Date toDate;
    @Column(name = "DOLLAR_AMOUNT")
    private double dollarAmount;
    @Column(name = "ADVM_IDENTIFIER")
    private Integer masterId;
    
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "AGENCY")
    private String agency;
    
    @Column(name = "TKU")
    private String tku;
    
    @Column(name = "DEPT_CODE")
    private String deptCode;
    
    @Column(name = "LAST_ACTION_DATE")
    private Date lastActionDate;

    public VAdvApprovalList() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSupervisorEmpidentifier() {
        return supervisorEmpidentifier;
    }

    public void setSupervisorEmpidentifier(Integer supervisorEmpidentifier) {
        this.supervisorEmpidentifier = supervisorEmpidentifier;
    }

     

    public Long getAdjIdentifier() {
        return adjIdentifier;
    }

    public void setAdjIdentifier(Long adjIdentifier) {
        this.adjIdentifier = adjIdentifier;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
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

    public double getDollarAmount() {
        return dollarAmount;
    }

    public void setDollarAmount(double dollarAmount) {
        this.dollarAmount = dollarAmount;
    }

    public void setEmpIdentifier(Integer empIdentifier) {
	this.empIdentifier = empIdentifier;
    }

    public Integer getEmpIdentifier() {
	return empIdentifier;
    }

    public void setRequestId(Integer requestId) {
	this.requestId = requestId;
    }

    public Integer getRequestId() {
	return requestId;
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

    public void setApptIdentifier(Integer apptIdentifier) {
	this.apptIdentifier = apptIdentifier;
    }

    public Integer getApptIdentifier() {
	return apptIdentifier;
    }

    public void setMasterId(Integer masterId) {
	this.masterId = masterId;
    }

    public Integer getMasterId() {
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
