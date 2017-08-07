/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "BARGAINING_UNITS")
@NamedQueries({@NamedQuery(name = "BargainingUnits.findAll", query = "SELECT b FROM BargainingUnits b"), @NamedQuery(name = "BargainingUnits.findByBargainingUnit", query = "SELECT b FROM BargainingUnits b WHERE b.bargainingUnit = :bargainingUnit"), @NamedQuery(name = "BargainingUnits.findByStartDate", query = "SELECT b FROM BargainingUnits b WHERE b.startDate = :startDate"), @NamedQuery(name = "BargainingUnits.findByEndDate", query = "SELECT b FROM BargainingUnits b WHERE b.endDate = :endDate"), @NamedQuery(name = "BargainingUnits.findByName", query = "SELECT b FROM BargainingUnits b WHERE b.name = :name"), @NamedQuery(name = "BargainingUnits.findByModifiedDate", query = "SELECT b FROM BargainingUnits b WHERE b.modifiedDate = :modifiedDate"), @NamedQuery(name = "BargainingUnits.findByModifiedUserId", query = "SELECT b FROM BargainingUnits b WHERE b.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "BargainingUnits.findByStatus", query = "SELECT b FROM BargainingUnits b WHERE b.status = :status")})
public class BargainingUnits implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "BARGAINING_UNIT")
    private String bargainingUnit;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "NAME")
    private String name;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "STATUS")
    private String status;
    @OneToMany(mappedBy = "bargainingUnit")
    private Collection<NonEmp> nonEmpCollection;
    @OneToMany(mappedBy = "bargainingUnit")
    private Collection<AppointmentHistories> appointmentHistoriesCollection;

    public BargainingUnits() {
    }

    public BargainingUnits(String bargainingUnit) {
        this.bargainingUnit = bargainingUnit;
    }

    public BargainingUnits(String bargainingUnit, Date startDate, Date endDate) {
        this.bargainingUnit = bargainingUnit;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getBargainingUnit() {
        return bargainingUnit;
    }

    public void setBargainingUnit(String bargainingUnit) {
        this.bargainingUnit = bargainingUnit;
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

    public Collection<NonEmp> getNonEmpCollection() {
        return nonEmpCollection;
    }

    public void setNonEmpCollection(Collection<NonEmp> nonEmpCollection) {
        this.nonEmpCollection = nonEmpCollection;
    }

    public Collection<AppointmentHistories> getAppointmentHistoriesCollection() {
        return appointmentHistoriesCollection;
    }

    public void setAppointmentHistoriesCollection(Collection<AppointmentHistories> appointmentHistoriesCollection) {
        this.appointmentHistoriesCollection = appointmentHistoriesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bargainingUnit != null ? bargainingUnit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BargainingUnits)) {
            return false;
        }
        BargainingUnits other = (BargainingUnits) object;
        if ((this.bargainingUnit == null && other.bargainingUnit != null) || (this.bargainingUnit != null && !this.bargainingUnit.equals(other.bargainingUnit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.BargainingUnits[bargainingUnit=" + bargainingUnit + "]";
    }

}
