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
@Table(name = "HRMN_SUPERVISOR_CODES")
@NamedQueries({@NamedQuery(name = "HrmnSupervisorCodes.findAll", query = "SELECT h FROM HrmnSupervisorCodes h"), @NamedQuery(name = "HrmnSupervisorCodes.findBySupervisor", query = "SELECT h FROM HrmnSupervisorCodes h WHERE h.supervisor = :supervisor"), @NamedQuery(name = "HrmnSupervisorCodes.findByDepartment", query = "SELECT h FROM HrmnSupervisorCodes h WHERE h.department = :department"), @NamedQuery(name = "HrmnSupervisorCodes.findByAgency", query = "SELECT h FROM HrmnSupervisorCodes h WHERE h.agency = :agency"), @NamedQuery(name = "HrmnSupervisorCodes.findByEffectiveDate", query = "SELECT h FROM HrmnSupervisorCodes h WHERE h.effectiveDate = :effectiveDate"), @NamedQuery(name = "HrmnSupervisorCodes.findByEndDate", query = "SELECT h FROM HrmnSupervisorCodes h WHERE h.endDate = :endDate"), @NamedQuery(name = "HrmnSupervisorCodes.findByDescription", query = "SELECT h FROM HrmnSupervisorCodes h WHERE h.description = :description"), @NamedQuery(name = "HrmnSupervisorCodes.findByStatus", query = "SELECT h FROM HrmnSupervisorCodes h WHERE h.status = :status"), @NamedQuery(name = "HrmnSupervisorCodes.findByModifiedUserId", query = "SELECT h FROM HrmnSupervisorCodes h WHERE h.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "HrmnSupervisorCodes.findByModifiedDate", query = "SELECT h FROM HrmnSupervisorCodes h WHERE h.modifiedDate = :modifiedDate")})
public class HrmnSupervisorCodes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "SUPERVISOR")
    private String supervisor;
    @Column(name = "DEPARTMENT")
    private String department;
    @Column(name = "AGENCY")
    private String agency;
    @Column(name = "EFFECTIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(mappedBy = "supervisor")
    private Collection<AppointmentHistories> appointmentHistoriesCollection;

    public HrmnSupervisorCodes() {
    }

    public HrmnSupervisorCodes(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Collection<AppointmentHistories> getAppointmentHistoriesCollection() {
        return appointmentHistoriesCollection;
    }

    public void setAppointmentHistoriesCollection(Collection<AppointmentHistories> appointmentHistoriesCollection) {
        this.appointmentHistoriesCollection = appointmentHistoriesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supervisor != null ? supervisor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrmnSupervisorCodes)) {
            return false;
        }
        HrmnSupervisorCodes other = (HrmnSupervisorCodes) object;
        if ((this.supervisor == null && other.supervisor != null) || (this.supervisor != null && !this.supervisor.equals(other.supervisor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.HrmnSupervisorCodes[supervisor=" + supervisor + "]";
    }

}
