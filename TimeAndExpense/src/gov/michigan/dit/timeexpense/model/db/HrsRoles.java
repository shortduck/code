/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "HRS_ROLES")
@NamedQueries({@NamedQuery(name = "HrsRoles.findAll", query = "SELECT h FROM HrsRoles h"), @NamedQuery(name = "HrsRoles.findByDepartment", query = "SELECT h FROM HrsRoles h WHERE h.hrsRolesPK.department = :department"), @NamedQuery(name = "HrsRoles.findByAgency", query = "SELECT h FROM HrsRoles h WHERE h.hrsRolesPK.agency = :agency"), @NamedQuery(name = "HrsRoles.findByRoleName", query = "SELECT h FROM HrsRoles h WHERE h.hrsRolesPK.roleName = :roleName"), @NamedQuery(name = "HrsRoles.findByDescription", query = "SELECT h FROM HrsRoles h WHERE h.description = :description"), @NamedQuery(name = "HrsRoles.findByModifiedUserId", query = "SELECT h FROM HrsRoles h WHERE h.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "HrsRoles.findByModifiedDate", query = "SELECT h FROM HrsRoles h WHERE h.modifiedDate = :modifiedDate")})
public class HrsRoles implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HrsRolesPK hrsRolesPK;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hrsRoles")
    private Collection<RoleModules> roleModulesCollection;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Agencies agencies;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hrsRoles")
    private Collection<UserIdRoles> userIdRolesCollection;

    public HrsRoles() {
    }

    public HrsRoles(HrsRolesPK hrsRolesPK) {
        this.hrsRolesPK = hrsRolesPK;
    }

    public HrsRoles(String department, String agency, String roleName) {
        this.hrsRolesPK = new HrsRolesPK(department, agency, roleName);
    }

    public HrsRolesPK getHrsRolesPK() {
        return hrsRolesPK;
    }

    public void setHrsRolesPK(HrsRolesPK hrsRolesPK) {
        this.hrsRolesPK = hrsRolesPK;
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

    public Collection<RoleModules> getRoleModulesCollection() {
        return roleModulesCollection;
    }

    public void setRoleModulesCollection(Collection<RoleModules> roleModulesCollection) {
        this.roleModulesCollection = roleModulesCollection;
    }

    public Agencies getAgencies() {
        return agencies;
    }

    public void setAgencies(Agencies agencies) {
        this.agencies = agencies;
    }

    public Collection<UserIdRoles> getUserIdRolesCollection() {
        return userIdRolesCollection;
    }

    public void setUserIdRolesCollection(Collection<UserIdRoles> userIdRolesCollection) {
        this.userIdRolesCollection = userIdRolesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hrsRolesPK != null ? hrsRolesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrsRoles)) {
            return false;
        }
        HrsRoles other = (HrsRoles) object;
        if ((this.hrsRolesPK == null && other.hrsRolesPK != null) || (this.hrsRolesPK != null && !this.hrsRolesPK.equals(other.hrsRolesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.HrsRoles[hrsRolesPK=" + hrsRolesPK + "]";
    }

}
