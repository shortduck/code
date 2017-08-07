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
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "FACS_AGENCIES")
@NamedQueries({@NamedQuery(name = "FacsAgencies.findAll", query = "SELECT f FROM FacsAgencies f"), @NamedQuery(name = "FacsAgencies.findByFacsAgy", query = "SELECT f FROM FacsAgencies f WHERE f.facsAgy = :facsAgy"), @NamedQuery(name = "FacsAgencies.findByStartDate", query = "SELECT f FROM FacsAgencies f WHERE f.startDate = :startDate"), @NamedQuery(name = "FacsAgencies.findByEndDate", query = "SELECT f FROM FacsAgencies f WHERE f.endDate = :endDate"), @NamedQuery(name = "FacsAgencies.findByStatusCode", query = "SELECT f FROM FacsAgencies f WHERE f.statusCode = :statusCode"), @NamedQuery(name = "FacsAgencies.findByName", query = "SELECT f FROM FacsAgencies f WHERE f.name = :name"), @NamedQuery(name = "FacsAgencies.findByFacsLastProcDate", query = "SELECT f FROM FacsAgencies f WHERE f.facsLastProcDate = :facsLastProcDate"), @NamedQuery(name = "FacsAgencies.findByModifiedDate", query = "SELECT f FROM FacsAgencies f WHERE f.modifiedDate = :modifiedDate"), @NamedQuery(name = "FacsAgencies.findByModifiedUserId", query = "SELECT f FROM FacsAgencies f WHERE f.modifiedUserId = :modifiedUserId")})
public class FacsAgencies implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "FACS_AGY")
    private String facsAgy;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "STATUS_CODE")
    private String statusCode;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facsAgy")
    private Collection<Appropriations> appropriationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facsAgy")
    private Collection<Agencies> agenciesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facsAgencies")
    private Collection<IndexCodes> indexCodesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facsAgencies")
    private Collection<AgencyFacsAgencies> agencyFacsAgenciesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facsAgy")
    private Collection<Projects> projectsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facsAgencies")
    private Collection<Pcas> pcasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facsAgencies")
    private Collection<AgencyCode1> agencyCode1Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facsAgencies")
    private Collection<AgencyCode2> agencyCode2Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facsAgencies")
    private Collection<AgencyCode3> agencyCode3Collection;

    public FacsAgencies() {
    }

    public FacsAgencies(String facsAgy) {
        this.facsAgy = facsAgy;
    }

    public FacsAgencies(String facsAgy, Date startDate, Date endDate, String statusCode) {
        this.facsAgy = facsAgy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusCode = statusCode;
    }

    public String getFacsAgy() {
        return facsAgy;
    }

    public void setFacsAgy(String facsAgy) {
        this.facsAgy = facsAgy;
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

    public Collection<Appropriations> getAppropriationsCollection() {
        return appropriationsCollection;
    }

    public void setAppropriationsCollection(Collection<Appropriations> appropriationsCollection) {
        this.appropriationsCollection = appropriationsCollection;
    }

    public Collection<Agencies> getAgenciesCollection() {
        return agenciesCollection;
    }

    public void setAgenciesCollection(Collection<Agencies> agenciesCollection) {
        this.agenciesCollection = agenciesCollection;
    }

    public Collection<IndexCodes> getIndexCodesCollection() {
        return indexCodesCollection;
    }

    public void setIndexCodesCollection(Collection<IndexCodes> indexCodesCollection) {
        this.indexCodesCollection = indexCodesCollection;
    }

    public Collection<AgencyFacsAgencies> getAgencyFacsAgenciesCollection() {
        return agencyFacsAgenciesCollection;
    }

    public void setAgencyFacsAgenciesCollection(Collection<AgencyFacsAgencies> agencyFacsAgenciesCollection) {
        this.agencyFacsAgenciesCollection = agencyFacsAgenciesCollection;
    }

    public Collection<Projects> getProjectsCollection() {
        return projectsCollection;
    }

    public void setProjectsCollection(Collection<Projects> projectsCollection) {
        this.projectsCollection = projectsCollection;
    }

    public Collection<Pcas> getPcasCollection() {
        return pcasCollection;
    }

    public void setPcasCollection(Collection<Pcas> pcasCollection) {
        this.pcasCollection = pcasCollection;
    }

    public Collection<AgencyCode1> getAgencyCode1Collection() {
        return agencyCode1Collection;
    }

    public void setAgencyCode1Collection(Collection<AgencyCode1> agencyCode1Collection) {
        this.agencyCode1Collection = agencyCode1Collection;
    }

    public Collection<AgencyCode2> getAgencyCode2Collection() {
        return agencyCode2Collection;
    }

    public void setAgencyCode2Collection(Collection<AgencyCode2> agencyCode2Collection) {
        this.agencyCode2Collection = agencyCode2Collection;
    }

    public Collection<AgencyCode3> getAgencyCode3Collection() {
        return agencyCode3Collection;
    }

    public void setAgencyCode3Collection(Collection<AgencyCode3> agencyCode3Collection) {
        this.agencyCode3Collection = agencyCode3Collection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facsAgy != null ? facsAgy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacsAgencies)) {
            return false;
        }
        FacsAgencies other = (FacsAgencies) object;
        if ((this.facsAgy == null && other.facsAgy != null) || (this.facsAgy != null && !this.facsAgy.equals(other.facsAgy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.FacsAgencies[facsAgy=" + facsAgy + "]";
    }

}
