/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "PCAS")
@NamedQueries({@NamedQuery(name = "Pcas.findAll", query = "SELECT p FROM Pcas p"), @NamedQuery(name = "Pcas.findByFacsAgy", query = "SELECT p FROM Pcas p WHERE p.pcasPK.facsAgy = :facsAgy"), @NamedQuery(name = "Pcas.findByAppropriationYear", query = "SELECT p FROM Pcas p WHERE p.pcasPK.appropriationYear = :appropriationYear"), @NamedQuery(name = "Pcas.findByPca", query = "SELECT p FROM Pcas p WHERE p.pcasPK.pca = :pca"), @NamedQuery(name = "Pcas.findByStartDate", query = "SELECT p FROM Pcas p WHERE p.startDate = :startDate"), @NamedQuery(name = "Pcas.findByEndDate", query = "SELECT p FROM Pcas p WHERE p.endDate = :endDate"), @NamedQuery(name = "Pcas.findByStatusCode", query = "SELECT p FROM Pcas p WHERE p.statusCode = :statusCode"), @NamedQuery(name = "Pcas.findByIndexCode", query = "SELECT p FROM Pcas p WHERE p.indexCode = :indexCode"), @NamedQuery(name = "Pcas.findByGrantNumber", query = "SELECT p FROM Pcas p WHERE p.grantNumber = :grantNumber"), @NamedQuery(name = "Pcas.findByGrantPhase", query = "SELECT p FROM Pcas p WHERE p.grantPhase = :grantPhase"), @NamedQuery(name = "Pcas.findByAgencyCode1", query = "SELECT p FROM Pcas p WHERE p.agencyCode1 = :agencyCode1"), @NamedQuery(name = "Pcas.findByProjectNumber", query = "SELECT p FROM Pcas p WHERE p.projectNumber = :projectNumber"), @NamedQuery(name = "Pcas.findByProjectPhase", query = "SELECT p FROM Pcas p WHERE p.projectPhase = :projectPhase"), @NamedQuery(name = "Pcas.findByAgencyCode2", query = "SELECT p FROM Pcas p WHERE p.agencyCode2 = :agencyCode2"), @NamedQuery(name = "Pcas.findByAgencyCode3", query = "SELECT p FROM Pcas p WHERE p.agencyCode3 = :agencyCode3"), @NamedQuery(name = "Pcas.findByMultipurposeCode", query = "SELECT p FROM Pcas p WHERE p.multipurposeCode = :multipurposeCode"), @NamedQuery(name = "Pcas.findByFund", query = "SELECT p FROM Pcas p WHERE p.fund = :fund"), @NamedQuery(name = "Pcas.findByAppropriation", query = "SELECT p FROM Pcas p WHERE p.appropriation = :appropriation"), @NamedQuery(name = "Pcas.findByRti", query = "SELECT p FROM Pcas p WHERE p.rti = :rti"), @NamedQuery(name = "Pcas.findByFunction", query = "SELECT p FROM Pcas p WHERE p.function = :function"), @NamedQuery(name = "Pcas.findByProgram", query = "SELECT p FROM Pcas p WHERE p.program = :program"), @NamedQuery(name = "Pcas.findByName", query = "SELECT p FROM Pcas p WHERE p.name = :name"), @NamedQuery(name = "Pcas.findByFacsLastProcDate", query = "SELECT p FROM Pcas p WHERE p.facsLastProcDate = :facsLastProcDate"), @NamedQuery(name = "Pcas.findByModifiedDate", query = "SELECT p FROM Pcas p WHERE p.modifiedDate = :modifiedDate"), @NamedQuery(name = "Pcas.findByModifiedUserId", query = "SELECT p FROM Pcas p WHERE p.modifiedUserId = :modifiedUserId")})
public class Pcas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PcasPK pcasPK;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "STATUS_CODE")
    private String statusCode;
    @Column(name = "INDEX_CODE")
    private String indexCode;
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
    @Column(name = "RTI")
    private String rti;
    @Column(name = "FUNCTION")
    private String function;
    @Column(name = "PROGRAM")
    private String program;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pcas")
    private Collection<ProjectPcas> projectPcasCollection;
    @JoinColumn(name = "FACS_AGY", referencedColumnName = "FACS_AGY", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private FacsAgencies facsAgencies;

    public Pcas() {
    }

    public Pcas(PcasPK pcasPK) {
        this.pcasPK = pcasPK;
    }

    public Pcas(PcasPK pcasPK, Date startDate, Date endDate, String statusCode) {
        this.pcasPK = pcasPK;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusCode = statusCode;
    }

    public Pcas(String facsAgy, String appropriationYear, String pca) {
        this.pcasPK = new PcasPK(facsAgy, appropriationYear, pca);
    }

    public PcasPK getPcasPK() {
        return pcasPK;
    }

    public void setPcasPK(PcasPK pcasPK) {
        this.pcasPK = pcasPK;
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

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
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

    public String getRti() {
        return rti;
    }

    public void setRti(String rti) {
        this.rti = rti;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
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

    public Collection<ProjectPcas> getProjectPcasCollection() {
        return projectPcasCollection;
    }

    public void setProjectPcasCollection(Collection<ProjectPcas> projectPcasCollection) {
        this.projectPcasCollection = projectPcasCollection;
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
        hash += (pcasPK != null ? pcasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pcas)) {
            return false;
        }
        Pcas other = (Pcas) object;
        if ((this.pcasPK == null && other.pcasPK != null) || (this.pcasPK != null && !this.pcasPK.equals(other.pcasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Pcas[pcasPK=" + pcasPK + "]";
    }

}
