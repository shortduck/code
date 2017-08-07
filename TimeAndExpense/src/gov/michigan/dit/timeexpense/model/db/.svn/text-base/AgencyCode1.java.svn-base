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
@Table(name = "AGENCY_CODE_1")
@NamedQueries({@NamedQuery(name = "AgencyCode1.findAll", query = "SELECT a FROM AgencyCode1 a"), @NamedQuery(name = "AgencyCode1.findByFacsAgy", query = "SELECT a FROM AgencyCode1 a WHERE a.agencyCode1PK.facsAgy = :facsAgy"), @NamedQuery(name = "AgencyCode1.findByAgencyCode1", query = "SELECT a FROM AgencyCode1 a WHERE a.agencyCode1PK.agencyCode1 = :agencyCode1"), @NamedQuery(name = "AgencyCode1.findByStartDate", query = "SELECT a FROM AgencyCode1 a WHERE a.startDate = :startDate"), @NamedQuery(name = "AgencyCode1.findByEndDate", query = "SELECT a FROM AgencyCode1 a WHERE a.endDate = :endDate"), @NamedQuery(name = "AgencyCode1.findByStatusCode", query = "SELECT a FROM AgencyCode1 a WHERE a.statusCode = :statusCode"), @NamedQuery(name = "AgencyCode1.findByName", query = "SELECT a FROM AgencyCode1 a WHERE a.name = :name"), @NamedQuery(name = "AgencyCode1.findByFacsLastProcDate", query = "SELECT a FROM AgencyCode1 a WHERE a.facsLastProcDate = :facsLastProcDate"), @NamedQuery(name = "AgencyCode1.findByModifiedDate", query = "SELECT a FROM AgencyCode1 a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "AgencyCode1.findByModifiedUserId", query = "SELECT a FROM AgencyCode1 a WHERE a.modifiedUserId = :modifiedUserId")})
public class AgencyCode1 implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgencyCode1PK agencyCode1PK;
    
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

    public AgencyCode1() {
    }

    public AgencyCode1(AgencyCode1PK agencyCode1PK) {
        this.agencyCode1PK = agencyCode1PK;
    }

    public AgencyCode1(AgencyCode1PK agencyCode1PK, Date startDate, Date endDate, String statusCode) {
        this.agencyCode1PK = agencyCode1PK;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusCode = statusCode;
    }

    public AgencyCode1(String facsAgy, String agencyCode1) {
        this.agencyCode1PK = new AgencyCode1PK(facsAgy, agencyCode1);
    }

    public AgencyCode1PK getAgencyCode1PK() {
        return agencyCode1PK;
    }

    public void setAgencyCode1PK(AgencyCode1PK agencyCode1PK) {
        this.agencyCode1PK = agencyCode1PK;
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
        hash += (agencyCode1PK != null ? agencyCode1PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyCode1)) {
            return false;
        }
        AgencyCode1 other = (AgencyCode1) object;
        if ((this.agencyCode1PK == null && other.agencyCode1PK != null) || (this.agencyCode1PK != null && !this.agencyCode1PK.equals(other.agencyCode1PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyCode1[agencyCode1PK=" + agencyCode1PK + "]";
    }

}
