/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "APPOINTMENT_HIST_SPECIAL_PAYS")
@NamedQueries({@NamedQuery(name = "AppointmentHistSpecialPays.findAll", query = "SELECT a FROM AppointmentHistSpecialPays a"), @NamedQuery(name = "AppointmentHistSpecialPays.findByAhspIdentifier", query = "SELECT a FROM AppointmentHistSpecialPays a WHERE a.ahspIdentifier = :ahspIdentifier"), @NamedQuery(name = "AppointmentHistSpecialPays.findBySpecialPayCode", query = "SELECT a FROM AppointmentHistSpecialPays a WHERE a.specialPayCode = :specialPayCode"), @NamedQuery(name = "AppointmentHistSpecialPays.findBySpecialPayRate", query = "SELECT a FROM AppointmentHistSpecialPays a WHERE a.specialPayRate = :specialPayRate"), @NamedQuery(name = "AppointmentHistSpecialPays.findByModifiedDate", query = "SELECT a FROM AppointmentHistSpecialPays a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "AppointmentHistSpecialPays.findByModifiedUserId", query = "SELECT a FROM AppointmentHistSpecialPays a WHERE a.modifiedUserId = :modifiedUserId")})
public class AppointmentHistSpecialPays implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "AHSP_IDENTIFIER")
    private Integer ahspIdentifier;
    
    @Column(name = "SPECIAL_PAY_CODE")
    private String specialPayCode;
    @Column(name = "SPECIAL_PAY_RATE")
    private BigDecimal specialPayRate;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @JoinColumn(name = "APHS_IDENTIFIER", referencedColumnName = "APHS_IDENTIFIER")
    @ManyToOne(optional = false)
    private AppointmentHistories aphsIdentifier;

    public AppointmentHistSpecialPays() {
    }

    public AppointmentHistSpecialPays(Integer ahspIdentifier) {
        this.ahspIdentifier = ahspIdentifier;
    }

    public AppointmentHistSpecialPays(Integer ahspIdentifier, String specialPayCode) {
        this.ahspIdentifier = ahspIdentifier;
        this.specialPayCode = specialPayCode;
    }

    public Integer getAhspIdentifier() {
        return ahspIdentifier;
    }

    public void setAhspIdentifier(Integer ahspIdentifier) {
        this.ahspIdentifier = ahspIdentifier;
    }

    public String getSpecialPayCode() {
        return specialPayCode;
    }

    public void setSpecialPayCode(String specialPayCode) {
        this.specialPayCode = specialPayCode;
    }

    public BigDecimal getSpecialPayRate() {
        return specialPayRate;
    }

    public void setSpecialPayRate(BigDecimal specialPayRate) {
        this.specialPayRate = specialPayRate;
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

    public AppointmentHistories getAphsIdentifier() {
        return aphsIdentifier;
    }

    public void setAphsIdentifier(AppointmentHistories aphsIdentifier) {
        this.aphsIdentifier = aphsIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ahspIdentifier != null ? ahspIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppointmentHistSpecialPays)) {
            return false;
        }
        AppointmentHistSpecialPays other = (AppointmentHistSpecialPays) object;
        if ((this.ahspIdentifier == null && other.ahspIdentifier != null) || (this.ahspIdentifier != null && !this.ahspIdentifier.equals(other.ahspIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AppointmentHistSpecialPays[ahspIdentifier=" + ahspIdentifier + "]";
    }

}
