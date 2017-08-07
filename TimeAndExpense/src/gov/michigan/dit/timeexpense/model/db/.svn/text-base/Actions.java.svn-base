/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.NamedQuery;

/**
 *
 * @author hussainz
 */
@Entity
@Table(name = "ACTIONS")
@NamedQueries({@NamedQuery(name="getActions", query = "SELECT a FROM Actions a"),@NamedQuery(name="getActionsOrdered", query = "SELECT a FROM Actions a ORDER BY a.actionCode"),
@NamedQuery(name="getActionsByApprovalInd", query = "SELECT a FROM Actions a WHERE a.approvalInd = :approvalInd"),
@NamedQuery(name="getActionsByDescription", query = "SELECT a FROM Actions a WHERE a.description = :description"),
@NamedQuery(name="getActionsByModifiedUserId", query = "SELECT a FROM Actions a WHERE a.modifiedUserId = :modifiedUserId"),
@NamedQuery(name="getActionsByModifiedDate", query = "SELECT a FROM Actions a WHERE a.modifiedDate = :modifiedDate")})
public class Actions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ACTION_CODE", nullable = false)
    private String actionCode;
    @Column(name = "APPROVAL_IND", nullable = false)
    private String approvalInd;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public Actions() {
    }

    public Actions(String actionCode) {
        this.actionCode = actionCode;
    }

    public Actions(String actionCode, String approvalInd) {
        this.actionCode = actionCode;
        this.approvalInd = approvalInd;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getApprovalInd() {
        return approvalInd;
    }

    public void setApprovalInd(String approvalInd) {
        this.approvalInd = approvalInd;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actionCode != null ? actionCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actions)) {
            return false;
        }
        Actions other = (Actions) object;
        if ((this.actionCode == null && other.actionCode != null) || (this.actionCode != null && !this.actionCode.equals(other.actionCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Actions[actionCode=" + actionCode + "]";
    }

}
