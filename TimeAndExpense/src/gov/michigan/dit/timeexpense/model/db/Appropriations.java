/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "APPROPRIATIONS")
@NamedQueries({@NamedQuery(name = "Appropriations.findAll", query = "SELECT a FROM Appropriations a"), @NamedQuery(name = "Appropriations.findByApprIdentifier", query = "SELECT a FROM Appropriations a WHERE a.apprIdentifier = :apprIdentifier"), @NamedQuery(name = "Appropriations.findByAppropriationYear", query = "SELECT a FROM Appropriations a WHERE a.appropriationYear = :appropriationYear"), @NamedQuery(name = "Appropriations.findByAppropriation", query = "SELECT a FROM Appropriations a WHERE a.appropriation = :appropriation"), @NamedQuery(name = "Appropriations.findByStartDate", query = "SELECT a FROM Appropriations a WHERE a.startDate = :startDate"), @NamedQuery(name = "Appropriations.findByEndDate", query = "SELECT a FROM Appropriations a WHERE a.endDate = :endDate"), @NamedQuery(name = "Appropriations.findByStatusCode", query = "SELECT a FROM Appropriations a WHERE a.statusCode = :statusCode"), @NamedQuery(name = "Appropriations.findByExpenditureObjectInclInd", query = "SELECT a FROM Appropriations a WHERE a.expenditureObjectInclInd = :expenditureObjectInclInd"), @NamedQuery(name = "Appropriations.findByRevenueObjectInclInd", query = "SELECT a FROM Appropriations a WHERE a.revenueObjectInclInd = :revenueObjectInclInd"), @NamedQuery(name = "Appropriations.findByName", query = "SELECT a FROM Appropriations a WHERE a.name = :name"), @NamedQuery(name = "Appropriations.findByFacsLastProcDate", query = "SELECT a FROM Appropriations a WHERE a.facsLastProcDate = :facsLastProcDate"), @NamedQuery(name = "Appropriations.findByModifiedDate", query = "SELECT a FROM Appropriations a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "Appropriations.findByModifiedUserId", query = "SELECT a FROM Appropriations a WHERE a.modifiedUserId = :modifiedUserId")})
public class Appropriations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "APPR_IDENTIFIER")
    private Integer apprIdentifier;
    
    @Column(name = "APPROPRIATION_YEAR")
    private String appropriationYear;
    
    @Column(name = "APPROPRIATION")
    private String appropriation;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "STATUS_CODE")
    private String statusCode;
    @Column(name = "EXPENDITURE_OBJECT_INCL_IND")
    private String expenditureObjectInclInd;
    @Column(name = "REVENUE_OBJECT_INCL_IND")
    private String revenueObjectInclInd;
    @Column(name = "NAME")
    private String name;
    @Column(name = "FACS_LAST_PROC_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date facsLastProcDate;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @JoinColumn(name = "FACS_AGY", referencedColumnName = "FACS_AGY")
    @ManyToOne(optional = false)
    private FacsAgencies facsAgy;

    public Appropriations() {
    }

    public Appropriations(Integer apprIdentifier) {
        this.apprIdentifier = apprIdentifier;
    }

    public Appropriations(Integer apprIdentifier, String appropriationYear, String appropriation, Date startDate, Date endDate, String statusCode) {
        this.apprIdentifier = apprIdentifier;
        this.appropriationYear = appropriationYear;
        this.appropriation = appropriation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusCode = statusCode;
    }

    public Integer getApprIdentifier() {
        return apprIdentifier;
    }

    public void setApprIdentifier(Integer apprIdentifier) {
        this.apprIdentifier = apprIdentifier;
    }

    public String getAppropriationYear() {
        return appropriationYear;
    }

    public void setAppropriationYear(String appropriationYear) {
        this.appropriationYear = appropriationYear;
    }

    public String getAppropriation() {
        return appropriation;
    }

    public void setAppropriation(String appropriation) {
        this.appropriation = appropriation;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getExpenditureObjectInclInd() {
        return expenditureObjectInclInd;
    }

    public void setExpenditureObjectInclInd(String expenditureObjectInclInd) {
        this.expenditureObjectInclInd = expenditureObjectInclInd;
    }

    public String getRevenueObjectInclInd() {
        return revenueObjectInclInd;
    }

    public void setRevenueObjectInclInd(String revenueObjectInclInd) {
        this.revenueObjectInclInd = revenueObjectInclInd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFacsLastProcDate() {
        return facsLastProcDate;
    }

    public void setFacsLastProcDate(Date facsLastProcDate) {
        this.facsLastProcDate = facsLastProcDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public FacsAgencies getFacsAgy() {
        return facsAgy;
    }

    public void setFacsAgy(FacsAgencies facsAgy) {
        this.facsAgy = facsAgy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apprIdentifier != null ? apprIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appropriations)) {
            return false;
        }
        Appropriations other = (Appropriations) object;
        if ((this.apprIdentifier == null && other.apprIdentifier != null) || (this.apprIdentifier != null && !this.apprIdentifier.equals(other.apprIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Appropriations[apprIdentifier=" + apprIdentifier + "]";
    }

}
