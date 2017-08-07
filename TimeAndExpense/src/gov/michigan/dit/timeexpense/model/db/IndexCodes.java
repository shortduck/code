/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "INDEX_CODES")
@NamedQueries({@NamedQuery(name = "IndexCodes.findAll", query = "SELECT i FROM IndexCodes i"), @NamedQuery(name = "IndexCodes.findByFacsAgy", query = "SELECT i FROM IndexCodes i WHERE i.indexCodesPK.facsAgy = :facsAgy"), @NamedQuery(name = "IndexCodes.findByAppropriationYear", query = "SELECT i FROM IndexCodes i WHERE i.indexCodesPK.appropriationYear = :appropriationYear"), @NamedQuery(name = "IndexCodes.findByIndexCode", query = "SELECT i FROM IndexCodes i WHERE i.indexCodesPK.indexCode = :indexCode"), @NamedQuery(name = "IndexCodes.findByStartDate", query = "SELECT i FROM IndexCodes i WHERE i.startDate = :startDate"), @NamedQuery(name = "IndexCodes.findByEndDate", query = "SELECT i FROM IndexCodes i WHERE i.endDate = :endDate"), @NamedQuery(name = "IndexCodes.findByStatusCode", query = "SELECT i FROM IndexCodes i WHERE i.statusCode = :statusCode"), @NamedQuery(name = "IndexCodes.findByPca", query = "SELECT i FROM IndexCodes i WHERE i.pca = :pca"), @NamedQuery(name = "IndexCodes.findByGrantNumber", query = "SELECT i FROM IndexCodes i WHERE i.grantNumber = :grantNumber"), @NamedQuery(name = "IndexCodes.findByGrantPhase", query = "SELECT i FROM IndexCodes i WHERE i.grantPhase = :grantPhase"), @NamedQuery(name = "IndexCodes.findByAgencyCode1", query = "SELECT i FROM IndexCodes i WHERE i.agencyCode1 = :agencyCode1"), @NamedQuery(name = "IndexCodes.findByProjectNumber", query = "SELECT i FROM IndexCodes i WHERE i.projectNumber = :projectNumber"), @NamedQuery(name = "IndexCodes.findByProjectPhase", query = "SELECT i FROM IndexCodes i WHERE i.projectPhase = :projectPhase"), @NamedQuery(name = "IndexCodes.findByAgencyCode2", query = "SELECT i FROM IndexCodes i WHERE i.agencyCode2 = :agencyCode2"), @NamedQuery(name = "IndexCodes.findByAgencyCode3", query = "SELECT i FROM IndexCodes i WHERE i.agencyCode3 = :agencyCode3"), @NamedQuery(name = "IndexCodes.findByMultipurposeCode", query = "SELECT i FROM IndexCodes i WHERE i.multipurposeCode = :multipurposeCode"), @NamedQuery(name = "IndexCodes.findByFund", query = "SELECT i FROM IndexCodes i WHERE i.fund = :fund"), @NamedQuery(name = "IndexCodes.findByAppropriation", query = "SELECT i FROM IndexCodes i WHERE i.appropriation = :appropriation"), @NamedQuery(name = "IndexCodes.findByFacsOrganizationCode", query = "SELECT i FROM IndexCodes i WHERE i.facsOrganizationCode = :facsOrganizationCode"), @NamedQuery(name = "IndexCodes.findByName", query = "SELECT i FROM IndexCodes i WHERE i.name = :name"), @NamedQuery(name = "IndexCodes.findByFacsLastProcDate", query = "SELECT i FROM IndexCodes i WHERE i.facsLastProcDate = :facsLastProcDate"), @NamedQuery(name = "IndexCodes.findByModifiedDate", query = "SELECT i FROM IndexCodes i WHERE i.modifiedDate = :modifiedDate"), @NamedQuery(name = "IndexCodes.findByModifiedUserId", query = "SELECT i FROM IndexCodes i WHERE i.modifiedUserId = :modifiedUserId")})
public class IndexCodes implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IndexCodesPK indexCodesPK;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "STATUS_CODE")
    private String statusCode;
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
    @Column(name = "FUND")
    private String fund;
    @Column(name = "APPROPRIATION")
    private String appropriation;
    @Column(name = "FACS_ORGANIZATION_CODE")
    private String facsOrganizationCode;
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
    @JoinColumn(name = "FACS_AGY", referencedColumnName = "FACS_AGY", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private FacsAgencies facsAgencies;

    public IndexCodes() {
    }

    public IndexCodes(IndexCodesPK indexCodesPK) {
        this.indexCodesPK = indexCodesPK;
    }

    public IndexCodes(IndexCodesPK indexCodesPK, Date startDate, Date endDate, String statusCode) {
        this.indexCodesPK = indexCodesPK;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusCode = statusCode;
    }

    public IndexCodes(String facsAgy, String appropriationYear, String indexCode) {
        this.indexCodesPK = new IndexCodesPK(facsAgy, appropriationYear, indexCode);
    }

    public IndexCodesPK getIndexCodesPK() {
        return indexCodesPK;
    }

    public void setIndexCodesPK(IndexCodesPK indexCodesPK) {
        this.indexCodesPK = indexCodesPK;
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

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getAppropriation() {
        return appropriation;
    }

    public void setAppropriation(String appropriation) {
        this.appropriation = appropriation;
    }

    public String getFacsOrganizationCode() {
        return facsOrganizationCode;
    }

    public void setFacsOrganizationCode(String facsOrganizationCode) {
        this.facsOrganizationCode = facsOrganizationCode;
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

    public FacsAgencies getFacsAgencies() {
        return facsAgencies;
    }

    public void setFacsAgencies(FacsAgencies facsAgencies) {
        this.facsAgencies = facsAgencies;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (indexCodesPK != null ? indexCodesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IndexCodes)) {
            return false;
        }
        IndexCodes other = (IndexCodes) object;
        if ((this.indexCodesPK == null && other.indexCodesPK != null) || (this.indexCodesPK != null && !this.indexCodesPK.equals(other.indexCodesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.IndexCodes[indexCodesPK=" + indexCodesPK + "]";
    }

}
