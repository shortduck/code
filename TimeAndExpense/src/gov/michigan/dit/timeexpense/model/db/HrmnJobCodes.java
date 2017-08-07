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
@Table(name = "HRMN_JOB_CODES")
@NamedQueries({@NamedQuery(name = "HrmnJobCodes.findAll", query = "SELECT h FROM HrmnJobCodes h"), @NamedQuery(name = "HrmnJobCodes.findByHjbcdIdentifier", query = "SELECT h FROM HrmnJobCodes h WHERE h.hjbcdIdentifier = :hjbcdIdentifier"), @NamedQuery(name = "HrmnJobCodes.findByJobCode", query = "SELECT h FROM HrmnJobCodes h WHERE h.jobCode = :jobCode"), @NamedQuery(name = "HrmnJobCodes.findByStartDate", query = "SELECT h FROM HrmnJobCodes h WHERE h.startDate = :startDate"), @NamedQuery(name = "HrmnJobCodes.findByEndDate", query = "SELECT h FROM HrmnJobCodes h WHERE h.endDate = :endDate"), @NamedQuery(name = "HrmnJobCodes.findByDescription", query = "SELECT h FROM HrmnJobCodes h WHERE h.description = :description"), @NamedQuery(name = "HrmnJobCodes.findByStatus", query = "SELECT h FROM HrmnJobCodes h WHERE h.status = :status"), @NamedQuery(name = "HrmnJobCodes.findByModifiedUserId", query = "SELECT h FROM HrmnJobCodes h WHERE h.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "HrmnJobCodes.findByModifiedDate", query = "SELECT h FROM HrmnJobCodes h WHERE h.modifiedDate = :modifiedDate")})
public class HrmnJobCodes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "HJBCD_IDENTIFIER")
    private Integer hjbcdIdentifier;
    
    @Column(name = "JOB_CODE")
    private String jobCode;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
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
    @JoinColumn(name = "JOB_CLASS", referencedColumnName = "JOB_CLASS")
    @ManyToOne
    private HrmnJobClasses jobClass;
    @OneToMany(mappedBy = "hjbcdIdentifier")
    private Collection<AppointmentHistories> appointmentHistoriesCollection;

    public HrmnJobCodes() {
    }

    public HrmnJobCodes(Integer hjbcdIdentifier) {
        this.hjbcdIdentifier = hjbcdIdentifier;
    }

    public HrmnJobCodes(Integer hjbcdIdentifier, String jobCode, Date startDate) {
        this.hjbcdIdentifier = hjbcdIdentifier;
        this.jobCode = jobCode;
        this.startDate = startDate;
    }

    public Integer getHjbcdIdentifier() {
        return hjbcdIdentifier;
    }

    public void setHjbcdIdentifier(Integer hjbcdIdentifier) {
        this.hjbcdIdentifier = hjbcdIdentifier;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
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

    public HrmnJobClasses getJobClass() {
        return jobClass;
    }

    public void setJobClass(HrmnJobClasses jobClass) {
        this.jobClass = jobClass;
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
        hash += (hjbcdIdentifier != null ? hjbcdIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrmnJobCodes)) {
            return false;
        }
        HrmnJobCodes other = (HrmnJobCodes) object;
        if ((this.hjbcdIdentifier == null && other.hjbcdIdentifier != null) || (this.hjbcdIdentifier != null && !this.hjbcdIdentifier.equals(other.hjbcdIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.HrmnJobCodes[hjbcdIdentifier=" + hjbcdIdentifier + "]";
    }

}
