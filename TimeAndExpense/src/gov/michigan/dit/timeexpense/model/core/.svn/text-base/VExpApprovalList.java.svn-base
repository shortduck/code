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

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "V_EXP_APPROVAL_LIST")
@NamedQueries({@NamedQuery(name = "VExpApprovalList.findAll", query = "SELECT v FROM VExpApprovalList v"), @NamedQuery(name = "VExpApprovalList.findByName", query = "SELECT v FROM VExpApprovalList v WHERE v.name = :name"), @NamedQuery(name = "VExpApprovalList.findByHrmnEmpIdentifier", query = "SELECT v FROM VExpApprovalList v WHERE v.hrmnEmpIdentifier = :hrmnEmpIdentifier"), @NamedQuery(name = "VExpApprovalList.findByApptEmpIdentifier", query = "SELECT v FROM VExpApprovalList v WHERE v.apptEmpIdentifier = :apptEmpIdentifier"), @NamedQuery(name = "VExpApprovalList.findByExpevIdentifier", query = "SELECT v FROM VExpApprovalList v WHERE v.expevIdentifier = :expevIdentifier"), @NamedQuery(name = "VExpApprovalList.findByAdjIdentifier", query = "SELECT v FROM VExpApprovalList v WHERE v.adjIdentifier = :adjIdentifier"), @NamedQuery(name = "VExpApprovalList.findByExpDateFrom", query = "SELECT v FROM VExpApprovalList v WHERE v.expDateFrom = :expDateFrom"), @NamedQuery(name = "VExpApprovalList.findByExpDateTo", query = "SELECT v FROM VExpApprovalList v WHERE v.expDateTo = :expDateTo"), @NamedQuery(name = "VExpApprovalList.findByAmount", query = "SELECT v FROM VExpApprovalList v WHERE v.amount = :amount")})
public class VExpApprovalList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;   
    @Column(name = "HRMN_EMP_IDENTIFIER")
    private int hrmnEmpIdentifier;   
    @Column(name = "EMP_IDENTIFIER")
    private int empIdentifier;
    @Column(name = "APPT_IDENTIFIER")
    private Integer apptIdentifier;
    @Id
    @Column(name = "EXPEV_IDENTIFIER")
    private int expevIdentifier;
    @Column(name = "ADJ_IDENTIFIER")
    private Long adjIdentifier;
    @Column(name = "EXP_DATE_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expDateFrom;
    @Column(name = "EXP_DATE_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expDateTo;
    @Column(name = "AMOUNT")
    private double amount;
    @Column(name = "EXPM_IDENTIFIER")
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
    
    public VExpApprovalList() {
    }
   

    public int getHrmnEmpIdentifier() {
        return hrmnEmpIdentifier;
    }

    public void setHrmnEmpIdentifier(int hrmnEmpIdentifier) {
        this.hrmnEmpIdentifier = hrmnEmpIdentifier;
    }
  

    public int getExpevIdentifier() {
        return expevIdentifier;
    }

    public void setExpevIdentifier(int expevIdentifier) {
        this.expevIdentifier = expevIdentifier;
    }

    public Long getAdjIdentifier() {
        return adjIdentifier;
    }

    public void setAdjIdentifier(Long adjIdentifier) {
        this.adjIdentifier = adjIdentifier;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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


    public void setEmpIdentifier(int empIdentifier) {
	this.empIdentifier = empIdentifier;
    }


    public int getEmpIdentifier() {
	return empIdentifier;
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
