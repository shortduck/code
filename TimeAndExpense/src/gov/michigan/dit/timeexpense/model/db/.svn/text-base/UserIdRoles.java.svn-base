/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "USER_ID_ROLES")
@NamedQueries({@NamedQuery(name = "UserIdRoles.findAll", query = "SELECT u FROM UserIdRoles u"), @NamedQuery(name = "UserIdRoles.findByUserId", query = "SELECT u FROM UserIdRoles u WHERE u.userIdRolesPK.userId = :userId"), @NamedQuery(name = "UserIdRoles.findByDepartment", query = "SELECT u FROM UserIdRoles u WHERE u.userIdRolesPK.department = :department"), @NamedQuery(name = "UserIdRoles.findByAgency", query = "SELECT u FROM UserIdRoles u WHERE u.userIdRolesPK.agency = :agency"), @NamedQuery(name = "UserIdRoles.findByRoleName", query = "SELECT u FROM UserIdRoles u WHERE u.userIdRolesPK.roleName = :roleName"), @NamedQuery(name = "UserIdRoles.findByModifiedUserId", query = "SELECT u FROM UserIdRoles u WHERE u.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "UserIdRoles.findByModifiedDate", query = "SELECT u FROM UserIdRoles u WHERE u.modifiedDate = :modifiedDate")})
public class UserIdRoles implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserIdRolesPK userIdRolesPK;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false), @JoinColumn(name = "ROLE_NAME", referencedColumnName = "ROLE_NAME", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrsRoles hrsRoles;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UserIds userIds;

    public UserIdRoles() {
    }

    public UserIdRoles(UserIdRolesPK userIdRolesPK) {
        this.userIdRolesPK = userIdRolesPK;
    }

    public UserIdRoles(String userId, String department, String agency, String roleName) {
        this.userIdRolesPK = new UserIdRolesPK(userId, department, agency, roleName);
    }

    public UserIdRolesPK getUserIdRolesPK() {
        return userIdRolesPK;
    }

    public void setUserIdRolesPK(UserIdRolesPK userIdRolesPK) {
        this.userIdRolesPK = userIdRolesPK;
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

    public HrsRoles getHrsRoles() {
        return hrsRoles;
    }

    public void setHrsRoles(HrsRoles hrsRoles) {
        this.hrsRoles = hrsRoles;
    }

    public UserIds getUserIds() {
        return userIds;
    }

    public void setUserIds(UserIds userIds) {
        this.userIds = userIds;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userIdRolesPK != null ? userIdRolesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserIdRoles)) {
            return false;
        }
        UserIdRoles other = (UserIdRoles) object;
        if ((this.userIdRolesPK == null && other.userIdRolesPK != null) || (this.userIdRolesPK != null && !this.userIdRolesPK.equals(other.userIdRolesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.UserIdRoles[userIdRolesPK=" + userIdRolesPK + "]";
    }

}
