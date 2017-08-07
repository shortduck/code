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
@Table(name = "AGENCIES")
@NamedQueries({@NamedQuery(name = "Agencies.findAll", query = "SELECT a FROM Agencies a"), @NamedQuery(name = "Agencies.findByDepartment", query = "SELECT a FROM Agencies a WHERE a.agenciesPK.department = :department"), @NamedQuery(name = "Agencies.findByAgency", query = "SELECT a FROM Agencies a WHERE a.agenciesPK.agency = :agency"), @NamedQuery(name = "Agencies.findByStartDate", query = "SELECT a FROM Agencies a WHERE a.startDate = :startDate"), @NamedQuery(name = "Agencies.findByEndDate", query = "SELECT a FROM Agencies a WHERE a.endDate = :endDate"), @NamedQuery(name = "Agencies.findByName", query = "SELECT a FROM Agencies a WHERE a.name = :name"), @NamedQuery(name = "Agencies.findByAbbreviation", query = "SELECT a FROM Agencies a WHERE a.abbreviation = :abbreviation"), @NamedQuery(name = "Agencies.findByDcdsStartDate", query = "SELECT a FROM Agencies a WHERE a.dcdsStartDate = :dcdsStartDate"), @NamedQuery(name = "Agencies.findByModifiedDate", query = "SELECT a FROM Agencies a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "Agencies.findByModifiedUserId", query = "SELECT a FROM Agencies a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "Agencies.findByCompany", query = "SELECT a FROM Agencies a WHERE a.company = :company"), @NamedQuery(name = "Agencies.findByProcessLevel", query = "SELECT a FROM Agencies a WHERE a.processLevel = :processLevel"), @NamedQuery(name = "Agencies.findByDcdsEndDate", query = "SELECT a FROM Agencies a WHERE a.dcdsEndDate = :dcdsEndDate"), @NamedQuery(name = "Agencies.findByStatus", query = "SELECT a FROM Agencies a WHERE a.status = :status")})
public class Agencies implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgenciesPK agenciesPK;
    
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
    @Column(name = "DCDS_START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dcdsStartDate;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "COMPANY")
    private Short company;
    @Column(name = "PROCESS_LEVEL")
    private String processLevel;
    @Column(name = "DCDS_END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dcdsEndDate;
    
    @Column(name = "STATUS")
    private String status;
    //@OneToOne(cascade = CascadeType.ALL, mappedBy = "agencies")
    @Column(name = "AGENCY")
    private String agencyOptions;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agencies")
    private Collection<Tkus> tkusCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agencies")
    private Collection<HrsRoles> hrsRolesCollection;
  //  @OneToMany(cascade = CascadeType.ALL, mappedBy = "agencies")
    private Collection<HrmnDeptCodes> hrmnDeptCodesCollection;
    @JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Departments departments;
    @JoinColumn(name = "FACS_AGY", referencedColumnName = "FACS_AGY")
    @ManyToOne(optional = false)
    private FacsAgencies facsAgy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agencies")
    private Collection<AgencyPayTypeGroups> agencyPayTypeGroupsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agencies")
    private Collection<AgencyFacsAgencies> agencyFacsAgenciesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agencies")
    private Collection<AgencyPayTypes> agencyPayTypesCollection;

    public Agencies() {
    }

    public Agencies(AgenciesPK agenciesPK) {
        this.agenciesPK = agenciesPK;
    }

    public Agencies(AgenciesPK agenciesPK, Date startDate, Date endDate, String name, String status) {
        this.agenciesPK = agenciesPK;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.status = status;
    }

    public Agencies(String department, String agency) {
        this.agenciesPK = new AgenciesPK(department, agency);
    }

    public AgenciesPK getAgenciesPK() {
        return agenciesPK;
    }

    public void setAgenciesPK(AgenciesPK agenciesPK) {
        this.agenciesPK = agenciesPK;
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

    public Date getDcdsStartDate() {
        return dcdsStartDate;
    }

    public void setDcdsStartDate(Date dcdsStartDate) {
        this.dcdsStartDate = dcdsStartDate;
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

    public Short getCompany() {
        return company;
    }

    public void setCompany(Short company) {
        this.company = company;
    }

    public String getProcessLevel() {
        return processLevel;
    }

    public void setProcessLevel(String processLevel) {
        this.processLevel = processLevel;
    }

    public Date getDcdsEndDate() {
        return dcdsEndDate;
    }

    public void setDcdsEndDate(Date dcdsEndDate) {
        this.dcdsEndDate = dcdsEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgencyOptions() {
        return agencyOptions;
    }

    public void setAgencyOptions(String agencyOptions) {
        this.agencyOptions = agencyOptions;
    }

    public Collection<Tkus> getTkusCollection() {
        return tkusCollection;
    }

    public void setTkusCollection(Collection<Tkus> tkusCollection) {
        this.tkusCollection = tkusCollection;
    }

    public Collection<HrsRoles> getHrsRolesCollection() {
        return hrsRolesCollection;
    }

    public void setHrsRolesCollection(Collection<HrsRoles> hrsRolesCollection) {
        this.hrsRolesCollection = hrsRolesCollection;
    }

    public Collection<HrmnDeptCodes> getHrmnDeptCodesCollection() {
        return hrmnDeptCodesCollection;
    }

    public void setHrmnDeptCodesCollection(Collection<HrmnDeptCodes> hrmnDeptCodesCollection) {
        this.hrmnDeptCodesCollection = hrmnDeptCodesCollection;
    }

    public Departments getDepartments() {
        return departments;
    }

    public void setDepartments(Departments departments) {
        this.departments = departments;
    }

    public FacsAgencies getFacsAgy() {
        return facsAgy;
    }

    public void setFacsAgy(FacsAgencies facsAgy) {
        this.facsAgy = facsAgy;
    }

    public Collection<AgencyPayTypeGroups> getAgencyPayTypeGroupsCollection() {
        return agencyPayTypeGroupsCollection;
    }

    public void setAgencyPayTypeGroupsCollection(Collection<AgencyPayTypeGroups> agencyPayTypeGroupsCollection) {
        this.agencyPayTypeGroupsCollection = agencyPayTypeGroupsCollection;
    }

    public Collection<AgencyFacsAgencies> getAgencyFacsAgenciesCollection() {
        return agencyFacsAgenciesCollection;
    }

    public void setAgencyFacsAgenciesCollection(Collection<AgencyFacsAgencies> agencyFacsAgenciesCollection) {
        this.agencyFacsAgenciesCollection = agencyFacsAgenciesCollection;
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
        hash += (agenciesPK != null ? agenciesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agencies)) {
            return false;
        }
        Agencies other = (Agencies) object;
        if ((this.agenciesPK == null && other.agenciesPK != null) || (this.agenciesPK != null && !this.agenciesPK.equals(other.agenciesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Agencies[agenciesPK=" + agenciesPK + "]";
    }

}
