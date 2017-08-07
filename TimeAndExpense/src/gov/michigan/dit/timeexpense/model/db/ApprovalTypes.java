/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "APPROVAL_TYPES")
@NamedQueries({@NamedQuery(name = "ApprovalTypes.findAll", query = "SELECT a FROM ApprovalTypes a"), @NamedQuery(name = "ApprovalTypes.findByApprovalCode", query = "SELECT a FROM ApprovalTypes a WHERE a.approvalCode = :approvalCode"), @NamedQuery(name = "ApprovalTypes.findByName", query = "SELECT a FROM ApprovalTypes a WHERE a.name = :name"), @NamedQuery(name = "ApprovalTypes.findByModifiedUserId", query = "SELECT a FROM ApprovalTypes a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "ApprovalTypes.findByModifiedDate", query = "SELECT a FROM ApprovalTypes a WHERE a.modifiedDate = :modifiedDate")})
public class ApprovalTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "APPROVAL_CODE")
    private String approvalCode;
    @Column(name = "NAME")
    private String name;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public ApprovalTypes() {
    }

    public ApprovalTypes(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        hash += (approvalCode != null ? approvalCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApprovalTypes)) {
            return false;
        }
        ApprovalTypes other = (ApprovalTypes) object;
        if ((this.approvalCode == null && other.approvalCode != null) || (this.approvalCode != null && !this.approvalCode.equals(other.approvalCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.ApprovalTypes[approvalCode=" + approvalCode + "]";
    }

}
