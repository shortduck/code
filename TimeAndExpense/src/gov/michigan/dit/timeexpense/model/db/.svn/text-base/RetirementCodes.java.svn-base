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
@Table(name = "RETIREMENT_CODES")
@NamedQueries({@NamedQuery(name = "RetirementCodes.findAll", query = "SELECT r FROM RetirementCodes r"), @NamedQuery(name = "RetirementCodes.findByRetirementCode", query = "SELECT r FROM RetirementCodes r WHERE r.retirementCode = :retirementCode"), @NamedQuery(name = "RetirementCodes.findByStartDate", query = "SELECT r FROM RetirementCodes r WHERE r.startDate = :startDate"), @NamedQuery(name = "RetirementCodes.findByEndDate", query = "SELECT r FROM RetirementCodes r WHERE r.endDate = :endDate"), @NamedQuery(name = "RetirementCodes.findByDescription", query = "SELECT r FROM RetirementCodes r WHERE r.description = :description"), @NamedQuery(name = "RetirementCodes.findByModifiedUserId", query = "SELECT r FROM RetirementCodes r WHERE r.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "RetirementCodes.findByModifiedDate", query = "SELECT r FROM RetirementCodes r WHERE r.modifiedDate = :modifiedDate"), @NamedQuery(name = "RetirementCodes.findByStatus", query = "SELECT r FROM RetirementCodes r WHERE r.status = :status")})
public class RetirementCodes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "RETIREMENT_CODE")
    private String retirementCode;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "STATUS")
    private String status;
    @OneToMany(mappedBy = "retirementCode")
    private Collection<AppointmentHistories> appointmentHistoriesCollection;

    public RetirementCodes() {
    }

    public RetirementCodes(String retirementCode) {
        this.retirementCode = retirementCode;
    }

    public RetirementCodes(String retirementCode, Date startDate) {
        this.retirementCode = retirementCode;
        this.startDate = startDate;
    }

    public String getRetirementCode() {
        return retirementCode;
    }

    public void setRetirementCode(String retirementCode) {
        this.retirementCode = retirementCode;
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

    public Collection<AppointmentHistories> getAppointmentHistoriesCollection() {
        return appointmentHistoriesCollection;
    }

    public void setAppointmentHistoriesCollection(Collection<AppointmentHistories> appointmentHistoriesCollection) {
        this.appointmentHistoriesCollection = appointmentHistoriesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (retirementCode != null ? retirementCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RetirementCodes)) {
            return false;
        }
        RetirementCodes other = (RetirementCodes) object;
        if ((this.retirementCode == null && other.retirementCode != null) || (this.retirementCode != null && !this.retirementCode.equals(other.retirementCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.RetirementCodes[retirementCode=" + retirementCode + "]";
    }

}
