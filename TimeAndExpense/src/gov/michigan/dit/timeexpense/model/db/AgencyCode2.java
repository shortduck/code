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
@Table(name = "AGENCY_CODE_2")
@NamedQueries({@NamedQuery(name = "AgencyCode2.findAll", query = "SELECT a FROM AgencyCode2 a"), @NamedQuery(name = "AgencyCode2.findByFacsAgy", query = "SELECT a FROM AgencyCode2 a WHERE a.agencyCode2PK.facsAgy = :facsAgy"), @NamedQuery(name = "AgencyCode2.findByAgencyCode2", query = "SELECT a FROM AgencyCode2 a WHERE a.agencyCode2PK.agencyCode2 = :agencyCode2"), @NamedQuery(name = "AgencyCode2.findByStartDate", query = "SELECT a FROM AgencyCode2 a WHERE a.startDate = :startDate"), @NamedQuery(name = "AgencyCode2.findByEndDate", query = "SELECT a FROM AgencyCode2 a WHERE a.endDate = :endDate"), @NamedQuery(name = "AgencyCode2.findByStatusCode", query = "SELECT a FROM AgencyCode2 a WHERE a.statusCode = :statusCode"), @NamedQuery(name = "AgencyCode2.findByName", query = "SELECT a FROM AgencyCode2 a WHERE a.name = :name"), @NamedQuery(name = "AgencyCode2.findByFacsLastProcDate", query = "SELECT a FROM AgencyCode2 a WHERE a.facsLastProcDate = :facsLastProcDate"), @NamedQuery(name = "AgencyCode2.findByModifiedDate", query = "SELECT a FROM AgencyCode2 a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "AgencyCode2.findByModifiedUserId", query = "SELECT a FROM AgencyCode2 a WHERE a.modifiedUserId = :modifiedUserId")})
public class AgencyCode2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgencyCode2PK agencyCode2PK;
    
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

    public AgencyCode2() {
    }

    public AgencyCode2(AgencyCode2PK agencyCode2PK) {
        this.agencyCode2PK = agencyCode2PK;
    }

    public AgencyCode2(AgencyCode2PK agencyCode2PK, Date startDate, Date endDate, String statusCode) {
        this.agencyCode2PK = agencyCode2PK;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusCode = statusCode;
    }

    public AgencyCode2(String facsAgy, String agencyCode2) {
        this.agencyCode2PK = new AgencyCode2PK(facsAgy, agencyCode2);
    }

    public AgencyCode2PK getAgencyCode2PK() {
        return agencyCode2PK;
    }

    public void setAgencyCode2PK(AgencyCode2PK agencyCode2PK) {
        this.agencyCode2PK = agencyCode2PK;
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
        hash += (agencyCode2PK != null ? agencyCode2PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyCode2)) {
            return false;
        }
        AgencyCode2 other = (AgencyCode2) object;
        if ((this.agencyCode2PK == null && other.agencyCode2PK != null) || (this.agencyCode2PK != null && !this.agencyCode2PK.equals(other.agencyCode2PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyCode2[agencyCode2PK=" + agencyCode2PK + "]";
    }

}
