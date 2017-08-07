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
@Table(name = "V_TREQ_APPROVAL_LIST")

public class VTreqApprovalList implements Serializable {
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
    @Column(name = "TREQE_IDENTIFIER")
    private int treqeIdentifier;
    @Column(name = "TREQ_DATE_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date treqDateFrom;
    @Column(name = "TREQ_DATE_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date treqDateTo;
    @Column(name = "AMOUNT")
    private double amount;
    @Column(name = "TREQM_IDENTIFIER")
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
    
    public VTreqApprovalList() {
    }
   

    public int getHrmnEmpIdentifier() {
        return hrmnEmpIdentifier;
    }

    public void setHrmnEmpIdentifier(int hrmnEmpIdentifier) {
        this.hrmnEmpIdentifier = hrmnEmpIdentifier;
    }
  

    public int getTreqeIdentifier() {
        return treqeIdentifier;
    }

    public void setTreqeIdentifier(int treqeIdentifier) {
        this.treqeIdentifier = treqeIdentifier;
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
