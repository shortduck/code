/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

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
 * @author hussainz
 */
@Entity
@Table(name = "SYSTEM_CODES")
@NamedQueries({@NamedQuery(name = "SystemCodes.findBySystemCode", query = "SELECT s FROM SystemCodes s WHERE s.systemCodesPK.systemCode = :systemCode"), @NamedQuery(name = "SystemCodes.findByStartDate", query = "SELECT s FROM SystemCodes s WHERE s.systemCodesPK.startDate = :startDate"), @NamedQuery(name = "SystemCodes.findByEndDate", query = "SELECT s FROM SystemCodes s WHERE s.endDate = :endDate"), @NamedQuery(name = "SystemCodes.findByDescription", query = "SELECT s FROM SystemCodes s WHERE s.description = :description"), @NamedQuery(name = "SystemCodes.findByValue", query = "SELECT s FROM SystemCodes s WHERE s.value = :value"), @NamedQuery(name = "SystemCodes.findByModifiedUserId", query = "SELECT s FROM SystemCodes s WHERE s.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "SystemCodes.findByModifiedDate", query = "SELECT s FROM SystemCodes s WHERE s.modifiedDate = :modifiedDate")})
public class SystemCodes implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SystemCodesPK systemCodesPK;
    @Column(name = "END_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    @Column(name = "VALUE", nullable = false)
    private String value;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public SystemCodes() {
    }

    public SystemCodes(SystemCodesPK systemCodesPK) {
        this.systemCodesPK = systemCodesPK;
    }

    public SystemCodes(SystemCodesPK systemCodesPK, Date endDate, String description, String value) {
        this.systemCodesPK = systemCodesPK;
        this.endDate = endDate;
        this.description = description;
        this.value = value;
    }

    public SystemCodes(String systemCode, Date startDate) {
        this.systemCodesPK = new SystemCodesPK(systemCode, startDate);
    }

    public SystemCodesPK getSystemCodesPK() {
        return systemCodesPK;
    }

    public void setSystemCodesPK(SystemCodesPK systemCodesPK) {
        this.systemCodesPK = systemCodesPK;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (systemCodesPK != null ? systemCodesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SystemCodes)) {
            return false;
        }
        SystemCodes other = (SystemCodes) object;
        if ((this.systemCodesPK == null && other.systemCodesPK != null) || (this.systemCodesPK != null && !this.systemCodesPK.equals(other.systemCodesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SystemCodes[systemCodesPK=" + systemCodesPK + "]";
    }

}
