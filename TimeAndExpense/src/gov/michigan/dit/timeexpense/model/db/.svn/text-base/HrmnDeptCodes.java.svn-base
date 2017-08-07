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
@Table(name = "HRMN_DEPT_CODES")
@NamedQueries({@NamedQuery(name = "HrmnDeptCodes.findAll", query = "SELECT h FROM HrmnDeptCodes h"), @NamedQuery(name = "HrmnDeptCodes.findByDepartment", query = "SELECT h FROM HrmnDeptCodes h WHERE h.hrmnDeptCodesPK.department = :department"), @NamedQuery(name = "HrmnDeptCodes.findByAgency", query = "SELECT h FROM HrmnDeptCodes h WHERE h.hrmnDeptCodesPK.agency = :agency"), @NamedQuery(name = "HrmnDeptCodes.findByDeptCode", query = "SELECT h FROM HrmnDeptCodes h WHERE h.hrmnDeptCodesPK.deptCode = :deptCode"), @NamedQuery(name = "HrmnDeptCodes.findByProcessLevel", query = "SELECT h FROM HrmnDeptCodes h WHERE h.processLevel = :processLevel"), @NamedQuery(name = "HrmnDeptCodes.findByName", query = "SELECT h FROM HrmnDeptCodes h WHERE h.name = :name"), @NamedQuery(name = "HrmnDeptCodes.findByStartDate", query = "SELECT h FROM HrmnDeptCodes h WHERE h.startDate = :startDate"), @NamedQuery(name = "HrmnDeptCodes.findByEndDate", query = "SELECT h FROM HrmnDeptCodes h WHERE h.endDate = :endDate"), @NamedQuery(name = "HrmnDeptCodes.findByModifiedUserId", query = "SELECT h FROM HrmnDeptCodes h WHERE h.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "HrmnDeptCodes.findByModifiedDate", query = "SELECT h FROM HrmnDeptCodes h WHERE h.modifiedDate = :modifiedDate"), @NamedQuery(name = "HrmnDeptCodes.findByStatus", query = "SELECT h FROM HrmnDeptCodes h WHERE h.status = :status")})
public class HrmnDeptCodes implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrmnDeptCodesPK hrmnDeptCodesPK;
    
    @Column(name = "PROCESS_LEVEL")
    private String processLevel;
    @Column(name = "NAME")
    private String name;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    @Column(name = "STATUS")
    private String status;
    /*@JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Agencies agencies;
    @OneToMany(mappedBy = "hrmnDeptCodes")
    private Collection<Appointments> appointmentsCollection;*/

    public HrmnDeptCodes() {
    }

    public HrmnDeptCodes(HrmnDeptCodesPK hrmnDeptCodesPK) {
        this.hrmnDeptCodesPK = hrmnDeptCodesPK;
    }

    public HrmnDeptCodes(HrmnDeptCodesPK hrmnDeptCodesPK, String processLevel, String status) {
        this.hrmnDeptCodesPK = hrmnDeptCodesPK;
        this.processLevel = processLevel;
        this.status = status;
    }

    public HrmnDeptCodes(String department, String agency, String deptCode) {
        this.hrmnDeptCodesPK = new HrmnDeptCodesPK(department, agency, deptCode);
    }

    public HrmnDeptCodesPK getHrmnDeptCodesPK() {
        return hrmnDeptCodesPK;
    }

    public void setHrmnDeptCodesPK(HrmnDeptCodesPK hrmnDeptCodesPK) {
        this.hrmnDeptCodesPK = hrmnDeptCodesPK;
    }

    public String getProcessLevel() {
        return processLevel;
    }

    public void setProcessLevel(String processLevel) {
        this.processLevel = processLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /*public Agencies getAgencies() {
        return agencies;
    }

    public void setAgencies(Agencies agencies) {
        this.agencies = agencies;
    }*/

    /*public Collection<Appointments> getAppointmentsCollection() {
        return appointmentsCollection;
    }

    public void setAppointmentsCollection(Collection<Appointments> appointmentsCollection) {
        this.appointmentsCollection = appointmentsCollection;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrmnDeptCodesPK != null ? hrmnDeptCodesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrmnDeptCodes)) {
            return false;
        }
        HrmnDeptCodes other = (HrmnDeptCodes) object;
        if ((this.hrmnDeptCodesPK == null && other.hrmnDeptCodesPK != null) || (this.hrmnDeptCodesPK != null && !this.hrmnDeptCodesPK.equals(other.hrmnDeptCodesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.HrmnDeptCodes[hrmnDeptCodesPK=" + hrmnDeptCodesPK + "]";
    }

}
