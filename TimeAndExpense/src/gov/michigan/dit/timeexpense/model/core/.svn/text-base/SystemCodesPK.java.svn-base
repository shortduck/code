/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hussainz
 */
@Embeddable
public class SystemCodesPK implements Serializable {
    @Column(name = "SYSTEM_CODE", nullable = false)
    private String systemCode;
    @Column(name = "START_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    public SystemCodesPK() {
    }

    public SystemCodesPK(String systemCode, Date startDate) {
        this.systemCode = systemCode;
        this.startDate = startDate;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemCode != null ? systemCode.hashCode() : 0);
        hash += (startDate != null ? startDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemCodesPK)) {
            return false;
        }
        SystemCodesPK other = (SystemCodesPK) object;
        if ((this.systemCode == null && other.systemCode != null) || (this.systemCode != null && !this.systemCode.equals(other.systemCode))) {
            return false;
        }
        if ((this.startDate == null && other.startDate != null) || (this.startDate != null && !this.startDate.equals(other.startDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SystemCodesPK[systemCode=" + systemCode + ", startDate=" + startDate + "]";
    }

}
