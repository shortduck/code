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
@Table(name = "PROJECTS")
@NamedQueries({@NamedQuery(name = "Projects.findAll", query = "SELECT p FROM Projects p"), @NamedQuery(name = "Projects.findByProjIdentifier", query = "SELECT p FROM Projects p WHERE p.projIdentifier = :projIdentifier"), @NamedQuery(name = "Projects.findByProjectNumber", query = "SELECT p FROM Projects p WHERE p.projectNumber = :projectNumber"), @NamedQuery(name = "Projects.findByProjectPhase", query = "SELECT p FROM Projects p WHERE p.projectPhase = :projectPhase"), @NamedQuery(name = "Projects.findByAgencyCode2", query = "SELECT p FROM Projects p WHERE p.agencyCode2 = :agencyCode2"), @NamedQuery(name = "Projects.findByStartDate", query = "SELECT p FROM Projects p WHERE p.startDate = :startDate"), @NamedQuery(name = "Projects.findByEndDate", query = "SELECT p FROM Projects p WHERE p.endDate = :endDate"), @NamedQuery(name = "Projects.findByStatusCode", query = "SELECT p FROM Projects p WHERE p.statusCode = :statusCode"), @NamedQuery(name = "Projects.findByGrantNumber", query = "SELECT p FROM Projects p WHERE p.grantNumber = :grantNumber"), @NamedQuery(name = "Projects.findByGrantPhase", query = "SELECT p FROM Projects p WHERE p.grantPhase = :grantPhase"), @NamedQuery(name = "Projects.findByAgencyCode1", query = "SELECT p FROM Projects p WHERE p.agencyCode1 = :agencyCode1"), @NamedQuery(name = "Projects.findByRti", query = "SELECT p FROM Projects p WHERE p.rti = :rti"), @NamedQuery(name = "Projects.findByName", query = "SELECT p FROM Projects p WHERE p.name = :name"), @NamedQuery(name = "Projects.findByFacsLastProcDate", query = "SELECT p FROM Projects p WHERE p.facsLastProcDate = :facsLastProcDate"), @NamedQuery(name = "Projects.findByModifiedDate", query = "SELECT p FROM Projects p WHERE p.modifiedDate = :modifiedDate"), @NamedQuery(name = "Projects.findByModifiedUserId", query = "SELECT p FROM Projects p WHERE p.modifiedUserId = :modifiedUserId")})
public class Projects implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "PROJ_IDENTIFIER")
    private Integer projIdentifier;
    
    @Column(name = "PROJECT_NUMBER")
    private String projectNumber;
    @Column(name = "PROJECT_PHASE")
    private String projectPhase;
    @Column(name = "AGENCY_CODE_2")
    private String agencyCode2;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "STATUS_CODE")
    private String statusCode;
    @Column(name = "GRANT_NUMBER")
    private String grantNumber;
    @Column(name = "GRANT_PHASE")
    private String grantPhase;
    @Column(name = "AGENCY_CODE_1")
    private String agencyCode1;
    @Column(name = "RTI")
    private String rti;
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

    public Projects() {
    }

    public Projects(Integer projIdentifier) {
        this.projIdentifier = projIdentifier;
    }

    public Projects(Integer projIdentifier, String projectNumber, Date startDate, Date endDate, String statusCode) {
        this.projIdentifier = projIdentifier;
        this.projectNumber = projectNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusCode = statusCode;
    }

    public Integer getProjIdentifier() {
        return projIdentifier;
    }

    public void setProjIdentifier(Integer projIdentifier) {
        this.projIdentifier = projIdentifier;
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

    public String getRti() {
        return rti;
    }

    public void setRti(String rti) {
        this.rti = rti;
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
        hash += (projIdentifier != null ? projIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projects)) {
            return false;
        }
        Projects other = (Projects) object;
        if ((this.projIdentifier == null && other.projIdentifier != null) || (this.projIdentifier != null && !this.projIdentifier.equals(other.projIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Projects[projIdentifier=" + projIdentifier + "]";
    }

}
