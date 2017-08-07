/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "AGENCY_CODE_3")
@NamedQueries({@NamedQuery(name = "AgencyCode3.findAll", query = "SELECT a FROM AgencyCode3 a"), @NamedQuery(name = "AgencyCode3.findByFacsAgy", query = "SELECT a FROM AgencyCode3 a WHERE a.agencyCode3PK.facsAgy = :facsAgy"), @NamedQuery(name = "AgencyCode3.findByAgencyCode3", query = "SELECT a FROM AgencyCode3 a WHERE a.agencyCode3PK.agencyCode3 = :agencyCode3"), @NamedQuery(name = "AgencyCode3.findByStartDate", query = "SELECT a FROM AgencyCode3 a WHERE a.startDate = :startDate"), @NamedQuery(name = "AgencyCode3.findByEndDate", query = "SELECT a FROM AgencyCode3 a WHERE a.endDate = :endDate"), @NamedQuery(name = "AgencyCode3.findByStatusCode", query = "SELECT a FROM AgencyCode3 a WHERE a.statusCode = :statusCode"), @NamedQuery(name = "AgencyCode3.findByName", query = "SELECT a FROM AgencyCode3 a WHERE a.name = :name"), @NamedQuery(name = "AgencyCode3.findByFacsLastProcDate", query = "SELECT a FROM AgencyCode3 a WHERE a.facsLastProcDate = :facsLastProcDate"), @NamedQuery(name = "AgencyCode3.findByModifiedDate", query = "SELECT a FROM AgencyCode3 a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "AgencyCode3.findByModifiedUserId", query = "SELECT a FROM AgencyCode3 a WHERE a.modifiedUserId = :modifiedUserId")})
public class AgencyCode3 implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgencyCode3PK agencyCode3PK;
    
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
    @JoinColumn(name = "FACS_AGY", referencedColumnName = "FACS_AGY", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private FacsAgencies facsAgencies;

    public AgencyCode3() {
    }

    public AgencyCode3(AgencyCode3PK agencyCode3PK) {
        this.agencyCode3PK = agencyCode3PK;
    }

    public AgencyCode3(AgencyCode3PK agencyCode3PK, Date startDate, Date endDate, String statusCode) {
        this.agencyCode3PK = agencyCode3PK;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusCode = statusCode;
    }

    public AgencyCode3(String facsAgy, String agencyCode3) {
        this.agencyCode3PK = new AgencyCode3PK(facsAgy, agencyCode3);
    }

    public AgencyCode3PK getAgencyCode3PK() {
        return agencyCode3PK;
    }

    public void setAgencyCode3PK(AgencyCode3PK agencyCode3PK) {
        this.agencyCode3PK = agencyCode3PK;
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

    public FacsAgencies getFacsAgencies() {
        return facsAgencies;
    }

    public void setFacsAgencies(FacsAgencies facsAgencies) {
        this.facsAgencies = facsAgencies;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agencyCode3PK != null ? agencyCode3PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyCode3)) {
            return false;
        }
        AgencyCode3 other = (AgencyCode3) object;
        if ((this.agencyCode3PK == null && other.agencyCode3PK != null) || (this.agencyCode3PK != null && !this.agencyCode3PK.equals(other.agencyCode3PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyCode3[agencyCode3PK=" + agencyCode3PK + "]";
    }

}
