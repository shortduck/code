/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "AGENCY_PAY_TYPE_GROUPS")
@NamedQueries({@NamedQuery(name = "AgencyPayTypeGroups.findAll", query = "SELECT a FROM AgencyPayTypeGroups a"), @NamedQuery(name = "AgencyPayTypeGroups.findByDepartment", query = "SELECT a FROM AgencyPayTypeGroups a WHERE a.agencyPayTypeGroupsPK.department = :department"), @NamedQuery(name = "AgencyPayTypeGroups.findByAgency", query = "SELECT a FROM AgencyPayTypeGroups a WHERE a.agencyPayTypeGroupsPK.agency = :agency"), @NamedQuery(name = "AgencyPayTypeGroups.findByPayTypeGroup", query = "SELECT a FROM AgencyPayTypeGroups a WHERE a.agencyPayTypeGroupsPK.payTypeGroup = :payTypeGroup"), @NamedQuery(name = "AgencyPayTypeGroups.findByDescription", query = "SELECT a FROM AgencyPayTypeGroups a WHERE a.description = :description"), @NamedQuery(name = "AgencyPayTypeGroups.findByModifiedUserId", query = "SELECT a FROM AgencyPayTypeGroups a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "AgencyPayTypeGroups.findByModifiedDate", query = "SELECT a FROM AgencyPayTypeGroups a WHERE a.modifiedDate = :modifiedDate")})
public class AgencyPayTypeGroups implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgencyPayTypeGroupsPK agencyPayTypeGroupsPK;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Agencies agencies;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agencyPayTypeGroups")
    private Collection<AgencyPayTypes> agencyPayTypesCollection;

    public AgencyPayTypeGroups() {
    }

    public AgencyPayTypeGroups(AgencyPayTypeGroupsPK agencyPayTypeGroupsPK) {
        this.agencyPayTypeGroupsPK = agencyPayTypeGroupsPK;
    }

    public AgencyPayTypeGroups(String department, String agency, String payTypeGroup) {
        this.agencyPayTypeGroupsPK = new AgencyPayTypeGroupsPK(department, agency, payTypeGroup);
    }

    public AgencyPayTypeGroupsPK getAgencyPayTypeGroupsPK() {
        return agencyPayTypeGroupsPK;
    }

    public void setAgencyPayTypeGroupsPK(AgencyPayTypeGroupsPK agencyPayTypeGroupsPK) {
        this.agencyPayTypeGroupsPK = agencyPayTypeGroupsPK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Agencies getAgencies() {
        return agencies;
    }

    public void setAgencies(Agencies agencies) {
        this.agencies = agencies;
    }

    public Collection<AgencyPayTypes> getAgencyPayTypesCollection() {
        return agencyPayTypesCollection;
    }

    public void setAgencyPayTypesCollection(Collection<AgencyPayTypes> agencyPayTypesCollection) {
        this.agencyPayTypesCollection = agencyPayTypesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agencyPayTypeGroupsPK != null ? agencyPayTypeGroupsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyPayTypeGroups)) {
            return false;
        }
        AgencyPayTypeGroups other = (AgencyPayTypeGroups) object;
        if ((this.agencyPayTypeGroupsPK == null && other.agencyPayTypeGroupsPK != null) || (this.agencyPayTypeGroupsPK != null && !this.agencyPayTypeGroupsPK.equals(other.agencyPayTypeGroupsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyPayTypeGroups[agencyPayTypeGroupsPK=" + agencyPayTypeGroupsPK + "]";
    }

}
