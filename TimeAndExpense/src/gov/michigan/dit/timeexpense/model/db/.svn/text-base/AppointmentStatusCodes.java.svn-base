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
@Table(name = "APPOINTMENT_STATUS_CODES")
@NamedQueries({@NamedQuery(name = "AppointmentStatusCodes.findAll", query = "SELECT a FROM AppointmentStatusCodes a"), @NamedQuery(name = "AppointmentStatusCodes.findByApptStatusCode", query = "SELECT a FROM AppointmentStatusCodes a WHERE a.apptStatusCode = :apptStatusCode"), @NamedQuery(name = "AppointmentStatusCodes.findByDescription", query = "SELECT a FROM AppointmentStatusCodes a WHERE a.description = :description"), @NamedQuery(name = "AppointmentStatusCodes.findByModifiedUserId", query = "SELECT a FROM AppointmentStatusCodes a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "AppointmentStatusCodes.findByModifiedDate", query = "SELECT a FROM AppointmentStatusCodes a WHERE a.modifiedDate = :modifiedDate")})
public class AppointmentStatusCodes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "APPT_STATUS_CODE")
    private String apptStatusCode;
    
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(mappedBy = "apptStatusCode")
    private Collection<AppointmentHistories> appointmentHistoriesCollection;

    public AppointmentStatusCodes() {
    }

    public AppointmentStatusCodes(String apptStatusCode) {
        this.apptStatusCode = apptStatusCode;
    }

    public AppointmentStatusCodes(String apptStatusCode, String description) {
        this.apptStatusCode = apptStatusCode;
        this.description = description;
    }

    public String getApptStatusCode() {
        return apptStatusCode;
    }

    public void setApptStatusCode(String apptStatusCode) {
        this.apptStatusCode = apptStatusCode;
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

    public Collection<AppointmentHistories> getAppointmentHistoriesCollection() {
        return appointmentHistoriesCollection;
    }

    public void setAppointmentHistoriesCollection(Collection<AppointmentHistories> appointmentHistoriesCollection) {
        this.appointmentHistoriesCollection = appointmentHistoriesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apptStatusCode != null ? apptStatusCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppointmentStatusCodes)) {
            return false;
        }
        AppointmentStatusCodes other = (AppointmentStatusCodes) object;
        if ((this.apptStatusCode == null && other.apptStatusCode != null) || (this.apptStatusCode != null && !this.apptStatusCode.equals(other.apptStatusCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AppointmentStatusCodes[apptStatusCode=" + apptStatusCode + "]";
    }

}
