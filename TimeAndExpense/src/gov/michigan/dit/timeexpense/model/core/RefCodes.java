/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author hussainz
 */
@Entity
@Table(name = "REF_CODES")

public class RefCodes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "REF_CODE")
    private String refCode;
    
    @Column(name = "VALUE")
    private String value;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "START_DATE")
    
    private Date startDate;
    
    @Column(name = "END_DATE")
    
    private Date endDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    
    @Column(name = "MODIFIED_DATE")
    
    private Date modifiedDate;

    public RefCodes() {
    }

    public RefCodes(String refCode) {
        this.refCode = refCode;
    }

    public RefCodes(String refCode, String value, String description, Date startDate, Date endDate, Date modifiedDate) {
        this.refCode = refCode;
        this.value = value;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.modifiedDate = modifiedDate;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (refCode != null ? refCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RefCodes)) {
            return false;
        }
        RefCodes other = (RefCodes) object;
        if ((this.refCode == null && other.refCode != null) || (this.refCode != null && !this.refCode.equals(other.refCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeandexpense.model.core.RefCodes[refCode=" + refCode + "]";
    }

}
