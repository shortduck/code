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
import javax.persistence.JoinColumns;
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
@Table(name = "AGENCY_FACS_AGENCIES")
@NamedQueries({@NamedQuery(name = "AgencyFacsAgencies.findAll", query = "SELECT a FROM AgencyFacsAgencies a"), @NamedQuery(name = "AgencyFacsAgencies.findByDepartment", query = "SELECT a FROM AgencyFacsAgencies a WHERE a.agencyFacsAgenciesPK.department = :department"), @NamedQuery(name = "AgencyFacsAgencies.findByAgency", query = "SELECT a FROM AgencyFacsAgencies a WHERE a.agencyFacsAgenciesPK.agency = :agency"), @NamedQuery(name = "AgencyFacsAgencies.findByFacsAgy", query = "SELECT a FROM AgencyFacsAgencies a WHERE a.agencyFacsAgenciesPK.facsAgy = :facsAgy"), @NamedQuery(name = "AgencyFacsAgencies.findByStartDate", query = "SELECT a FROM AgencyFacsAgencies a WHERE a.agencyFacsAgenciesPK.startDate = :startDate"), @NamedQuery(name = "AgencyFacsAgencies.findByEndDate", query = "SELECT a FROM AgencyFacsAgencies a WHERE a.endDate = :endDate"), @NamedQuery(name = "AgencyFacsAgencies.findByModifiedDate", query = "SELECT a FROM AgencyFacsAgencies a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "AgencyFacsAgencies.findByModifiedUserId", query = "SELECT a FROM AgencyFacsAgencies a WHERE a.modifiedUserId = :modifiedUserId")})
public class AgencyFacsAgencies implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgencyFacsAgenciesPK agencyFacsAgenciesPK;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Agencies agencies;
    @JoinColumn(name = "FACS_AGY", referencedColumnName = "FACS_AGY", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private FacsAgencies facsAgencies;

    public AgencyFacsAgencies() {
    }

    public AgencyFacsAgencies(AgencyFacsAgenciesPK agencyFacsAgenciesPK) {
        this.agencyFacsAgenciesPK = agencyFacsAgenciesPK;
    }

    public AgencyFacsAgencies(AgencyFacsAgenciesPK agencyFacsAgenciesPK, Date endDate) {
        this.agencyFacsAgenciesPK = agencyFacsAgenciesPK;
        this.endDate = endDate;
    }

    public AgencyFacsAgencies(String department, String agency, String facsAgy, Date startDate) {
        this.agencyFacsAgenciesPK = new AgencyFacsAgenciesPK(department, agency, facsAgy, startDate);
    }

    public AgencyFacsAgenciesPK getAgencyFacsAgenciesPK() {
        return agencyFacsAgenciesPK;
    }

    public void setAgencyFacsAgenciesPK(AgencyFacsAgenciesPK agencyFacsAgenciesPK) {
        this.agencyFacsAgenciesPK = agencyFacsAgenciesPK;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Agencies getAgencies() {
        return agencies;
    }

    public void setAgencies(Agencies agencies) {
        this.agencies = agencies;
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
        hash += (agencyFacsAgenciesPK != null ? agencyFacsAgenciesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyFacsAgencies)) {
            return false;
        }
        AgencyFacsAgencies other = (AgencyFacsAgencies) object;
        if ((this.agencyFacsAgenciesPK == null && other.agencyFacsAgenciesPK != null) || (this.agencyFacsAgenciesPK != null && !this.agencyFacsAgenciesPK.equals(other.agencyFacsAgenciesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyFacsAgencies[agencyFacsAgenciesPK=" + agencyFacsAgenciesPK + "]";
    }

}
