/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import gov.michigan.dit.timeexpense.model.core.TkuoptTaOptions;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "TKUS")
@NamedQueries({@NamedQuery(name = "Tkus.findAll", query = "SELECT t FROM Tkus t"), @NamedQuery(name = "Tkus.findByDepartment", query = "SELECT t FROM Tkus t WHERE t.tkusPK.department = :department"), @NamedQuery(name = "Tkus.findByAgency", query = "SELECT t FROM Tkus t WHERE t.tkusPK.agency = :agency"), @NamedQuery(name = "Tkus.findByTku", query = "SELECT t FROM Tkus t WHERE t.tkusPK.tku = :tku"), @NamedQuery(name = "Tkus.findByStartDate", query = "SELECT t FROM Tkus t WHERE t.startDate = :startDate"), @NamedQuery(name = "Tkus.findByEndDate", query = "SELECT t FROM Tkus t WHERE t.endDate = :endDate"), @NamedQuery(name = "Tkus.findByName", query = "SELECT t FROM Tkus t WHERE t.name = :name"), @NamedQuery(name = "Tkus.findByAbbreviation", query = "SELECT t FROM Tkus t WHERE t.abbreviation = :abbreviation"), @NamedQuery(name = "Tkus.findByModifiedDate", query = "SELECT t FROM Tkus t WHERE t.modifiedDate = :modifiedDate"), @NamedQuery(name = "Tkus.findByModifiedUserId", query = "SELECT t FROM Tkus t WHERE t.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "Tkus.findByStatus", query = "SELECT t FROM Tkus t WHERE t.status = :status")})
public class Tkus implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TkusPK tkusPK;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ABBREVIATION")
    private String abbreviation;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "STATUS")
    private String status;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tkus")
    private TkuoptTaOptions tkuoptTaOptions;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Agencies agencies;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tkus")
    private Collection<TkuoptApprovalPaths> tkuoptApprovalPathsCollection;
    @OneToMany(mappedBy = "tkus")
    private Collection<Security> securityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tkus")
    private Collection<TkuActions> tkuActionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tkus")
    private Collection<TkuoptHoursTypes> tkuoptHoursTypesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tkus")
    private Collection<NonEmp> nonEmpCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tkus")
    private Collection<TkuoptNotifications> tkuoptNotificationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tkus")
    private Collection<Appointments> appointmentsCollection;

    public Tkus() {
    }

    public Tkus(TkusPK tkusPK) {
        this.tkusPK = tkusPK;
    }

    public Tkus(TkusPK tkusPK, Date startDate, Date endDate) {
        this.tkusPK = tkusPK;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Tkus(String department, String agency, String tku) {
        this.tkusPK = new TkusPK(department, agency, tku);
    }

    public TkusPK getTkusPK() {
        return tkusPK;
    }

    public void setTkusPK(TkusPK tkusPK) {
        this.tkusPK = tkusPK;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TkuoptTaOptions getTkuoptTaOptions() {
        return tkuoptTaOptions;
    }

    public void setTkuoptTaOptions(TkuoptTaOptions tkuoptTaOptions) {
        this.tkuoptTaOptions = tkuoptTaOptions;
    }

    public Agencies getAgencies() {
        return agencies;
    }

    public void setAgencies(Agencies agencies) {
        this.agencies = agencies;
    }

    public Collection<TkuoptApprovalPaths> getTkuoptApprovalPathsCollection() {
        return tkuoptApprovalPathsCollection;
    }

    public void setTkuoptApprovalPathsCollection(Collection<TkuoptApprovalPaths> tkuoptApprovalPathsCollection) {
        this.tkuoptApprovalPathsCollection = tkuoptApprovalPathsCollection;
    }

    public Collection<Security> getSecurityCollection() {
        return securityCollection;
    }

    public void setSecurityCollection(Collection<Security> securityCollection) {
        this.securityCollection = securityCollection;
    }

    public Collection<TkuActions> getTkuActionsCollection() {
        return tkuActionsCollection;
    }

    public void setTkuActionsCollection(Collection<TkuActions> tkuActionsCollection) {
        this.tkuActionsCollection = tkuActionsCollection;
    }

    public Collection<TkuoptHoursTypes> getTkuoptHoursTypesCollection() {
        return tkuoptHoursTypesCollection;
    }

    public void setTkuoptHoursTypesCollection(Collection<TkuoptHoursTypes> tkuoptHoursTypesCollection) {
        this.tkuoptHoursTypesCollection = tkuoptHoursTypesCollection;
    }

    public Collection<NonEmp> getNonEmpCollection() {
        return nonEmpCollection;
    }

    public void setNonEmpCollection(Collection<NonEmp> nonEmpCollection) {
        this.nonEmpCollection = nonEmpCollection;
    }

    public Collection<TkuoptNotifications> getTkuoptNotificationsCollection() {
        return tkuoptNotificationsCollection;
    }

    public void setTkuoptNotificationsCollection(Collection<TkuoptNotifications> tkuoptNotificationsCollection) {
        this.tkuoptNotificationsCollection = tkuoptNotificationsCollection;
    }

    public Collection<Appointments> getAppointmentsCollection() {
        return appointmentsCollection;
    }

    public void setAppointmentsCollection(Collection<Appointments> appointmentsCollection) {
        this.appointmentsCollection = appointmentsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tkusPK != null ? tkusPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tkus)) {
            return false;
        }
        Tkus other = (Tkus) object;
        if ((this.tkusPK == null && other.tkusPK != null) || (this.tkusPK != null && !this.tkusPK.equals(other.tkusPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Tkus[tkusPK=" + tkusPK + "]";
    }

}
