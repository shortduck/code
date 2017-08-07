/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "DEFAULT_DISTRIBUTIONS_ADV_AGY")
@NamedQueries({@NamedQuery(name = "DefaultDistributionsAdvAgy1.findAll", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByDfdaaIdentifier", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.dfdaaIdentifier = :dfdaaIdentifier"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByStartDate", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.startDate = :startDate"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByCodingBlock", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.codingBlock = :codingBlock"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByAppropriationYear", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.appropriationYear = :appropriationYear"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByIndexCode", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.indexCode = :indexCode"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByPca", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.pca = :pca"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByGrantNumber", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.grantNumber = :grantNumber"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByGrantPhase", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.grantPhase = :grantPhase"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByAgencyCode1", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.agencyCode1 = :agencyCode1"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByProjectNumber", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.projectNumber = :projectNumber"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByProjectPhase", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.projectPhase = :projectPhase"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByAgencyCode2", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.agencyCode2 = :agencyCode2"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByAgencyCode3", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.agencyCode3 = :agencyCode3"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByMultipurposeCode", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.multipurposeCode = :multipurposeCode"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByPercent", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.percent = :percent"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByEndDate", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.endDate = :endDate"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByModifiedUserId", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "DefaultDistributionsAdvAgy1.findByModifiedDate", query = "SELECT d FROM DefaultDistributionsAdvAgy1 d WHERE d.modifiedDate = :modifiedDate")})
public class DefaultDistributionsAdvAgy implements Serializable {

	private static final long serialVersionUID = -4743400743630168867L;

	@Id

    @Column(name = "DFDAA_IDENTIFIER")
    private Integer dfdaaIdentifier;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "CODING_BLOCK")
    private String codingBlock;
    @Column(name = "APPROPRIATION_YEAR")
    private String appropriationYear;
    @Column(name = "INDEX_CODE")
    private String indexCode;
    @Column(name = "PCA")
    private String pca;
    @Column(name = "GRANT_NUMBER")
    private String grantNumber;
    @Column(name = "GRANT_PHASE")
    private String grantPhase;
    @Column(name = "AGENCY_CODE_1")
    private String agencyCode1;
    @Column(name = "PROJECT_NUMBER")
    private String projectNumber;
    @Column(name = "PROJECT_PHASE")
    private String projectPhase;
    @Column(name = "AGENCY_CODE_2")
    private String agencyCode2;
    @Column(name = "AGENCY_CODE_3")
    private String agencyCode3;
    @Column(name = "MULTIPURPOSE_CODE")
    private String multipurposeCode;

    @Column(name = "PERCENT")
    private BigDecimal percent;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    /*@JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT"), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY")})
    @ManyToOne(optional = false)*/
    @Column(name = "AGENCY")
    private String agencies;
    
    @Column(name = "DEPARTMENT")
    private String department;

    public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public DefaultDistributionsAdvAgy() {
    }

    public DefaultDistributionsAdvAgy(Integer dfdaaIdentifier) {
        this.dfdaaIdentifier = dfdaaIdentifier;
    }

    public DefaultDistributionsAdvAgy(Integer dfdaaIdentifier, Date startDate, String codingBlock, BigDecimal percent, Date endDate) {
        this.dfdaaIdentifier = dfdaaIdentifier;
        this.startDate = startDate;
        this.codingBlock = codingBlock;
        this.percent = percent;
        this.endDate = endDate;
    }

    public Integer getDfdaaIdentifier() {
        return dfdaaIdentifier;
    }

    public void setDfdaaIdentifier(Integer dfdaaIdentifier) {
        this.dfdaaIdentifier = dfdaaIdentifier;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCodingBlock() {
        return codingBlock;
    }

    public void setCodingBlock(String codingBlock) {
        this.codingBlock = codingBlock;
    }

    public String getAppropriationYear() {
        return appropriationYear;
    }

    public void setAppropriationYear(String appropriationYear) {
        this.appropriationYear = appropriationYear;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getPca() {
        return pca;
    }

    public void setPca(String pca) {
        this.pca = pca;
    }

    public String getGrantNumber() {
        return grantNumber;
    }

    public void setGrantNumber(String grantNumber) {
        this.grantNumber = grantNumber;
    }

    public String getGrantPhase() {
        return grantPhase;
    }

    public void setGrantPhase(String grantPhase) {
        this.grantPhase = grantPhase;
    }

    public String getAgencyCode1() {
        return agencyCode1;
    }

    public void setAgencyCode1(String agencyCode1) {
        this.agencyCode1 = agencyCode1;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectPhase() {
        return projectPhase;
    }

    public void setProjectPhase(String projectPhase) {
        this.projectPhase = projectPhase;
    }

    public String getAgencyCode2() {
        return agencyCode2;
    }

    public void setAgencyCode2(String agencyCode2) {
        this.agencyCode2 = agencyCode2;
    }

    public String getAgencyCode3() {
        return agencyCode3;
    }

    public void setAgencyCode3(String agencyCode3) {
        this.agencyCode3 = agencyCode3;
    }

    public String getMultipurposeCode() {
        return multipurposeCode;
    }

    public void setMultipurposeCode(String multipurposeCode) {
        this.multipurposeCode = multipurposeCode;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getAgencies() {
        return agencies;
    }

    public void setAgencies(String agencies) {
        this.agencies = agencies;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dfdaaIdentifier != null ? dfdaaIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DefaultDistributionsAdvAgy)) {
            return false;
        }
        DefaultDistributionsAdvAgy other = (DefaultDistributionsAdvAgy) object;
        if ((this.dfdaaIdentifier == null && other.dfdaaIdentifier != null) || (this.dfdaaIdentifier != null && !this.dfdaaIdentifier.equals(other.dfdaaIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.db.DefaultDistributionsAdvAgy1[dfdaaIdentifier=" + dfdaaIdentifier + "]";
    }

}
