/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "ROLE_MODULES")
@NamedQueries({@NamedQuery(name = "RoleModules.findAll", query = "SELECT r FROM RoleModules r"), @NamedQuery(name = "RoleModules.findByDepartment", query = "SELECT r FROM RoleModules r WHERE r.roleModulesPK.department = :department"), @NamedQuery(name = "RoleModules.findByAgency", query = "SELECT r FROM RoleModules r WHERE r.roleModulesPK.agency = :agency"), @NamedQuery(name = "RoleModules.findByRoleName", query = "SELECT r FROM RoleModules r WHERE r.roleModulesPK.roleName = :roleName"), @NamedQuery(name = "RoleModules.findByModuleId", query = "SELECT r FROM RoleModules r WHERE r.roleModulesPK.moduleId = :moduleId"), @NamedQuery(name = "RoleModules.findByChangeAccessInd", query = "SELECT r FROM RoleModules r WHERE r.changeAccessInd = :changeAccessInd"), @NamedQuery(name = "RoleModules.findByEmployeeOnlyInd", query = "SELECT r FROM RoleModules r WHERE r.employeeOnlyInd = :employeeOnlyInd"), @NamedQuery(name = "RoleModules.findByDefaultOrgCodeLevel", query = "SELECT r FROM RoleModules r WHERE r.defaultOrgCodeLevel = :defaultOrgCodeLevel"), @NamedQuery(name = "RoleModules.findByDefaultTkuCodeLevel", query = "SELECT r FROM RoleModules r WHERE r.defaultTkuCodeLevel = :defaultTkuCodeLevel"), @NamedQuery(name = "RoleModules.findByModifiedUserId", query = "SELECT r FROM RoleModules r WHERE r.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "RoleModules.findByModifiedDate", query = "SELECT r FROM RoleModules r WHERE r.modifiedDate = :modifiedDate")})
public class RoleModules implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RoleModulesPK roleModulesPK;
    
    @Column(name = "CHANGE_ACCESS_IND")
    private String changeAccessInd;
    
    @Column(name = "EMPLOYEE_ONLY_IND")
    private String employeeOnlyInd;
    @Column(name = "DEFAULT_ORG_CODE_LEVEL")
    private String defaultOrgCodeLevel;
    @Column(name = "DEFAULT_TKU_CODE_LEVEL")
    private String defaultTkuCodeLevel;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false), @JoinColumn(name = "ROLE_NAME", referencedColumnName = "ROLE_NAME", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private HrsRoles hrsRoles;
    @JoinColumn(name = "MODULE_ID", referencedColumnName = "MODULE_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Module module;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleModules")
    private Collection<Security> securityCollection;

    public RoleModules() {
    }

    public RoleModules(RoleModulesPK roleModulesPK) {
        this.roleModulesPK = roleModulesPK;
    }

    public RoleModules(RoleModulesPK roleModulesPK, String changeAccessInd, String employeeOnlyInd) {
        this.roleModulesPK = roleModulesPK;
        this.changeAccessInd = changeAccessInd;
        this.employeeOnlyInd = employeeOnlyInd;
    }

    public RoleModules(String department, String agency, String roleName, String moduleId) {
        this.roleModulesPK = new RoleModulesPK(department, agency, roleName, moduleId);
    }

    public RoleModulesPK getRoleModulesPK() {
        return roleModulesPK;
    }

    public void setRoleModulesPK(RoleModulesPK roleModulesPK) {
        this.roleModulesPK = roleModulesPK;
    }

    public String getChangeAccessInd() {
        return changeAccessInd;
    }

    public void setChangeAccessInd(String changeAccessInd) {
        this.changeAccessInd = changeAccessInd;
    }

    public String getEmployeeOnlyInd() {
        return employeeOnlyInd;
    }

    public void setEmployeeOnlyInd(String employeeOnlyInd) {
        this.employeeOnlyInd = employeeOnlyInd;
    }

    public String getDefaultOrgCodeLevel() {
        return defaultOrgCodeLevel;
    }

    public void setDefaultOrgCodeLevel(String defaultOrgCodeLevel) {
        this.defaultOrgCodeLevel = defaultOrgCodeLevel;
    }

    public String getDefaultTkuCodeLevel() {
        return defaultTkuCodeLevel;
    }

    public void setDefaultTkuCodeLevel(String defaultTkuCodeLevel) {
        this.defaultTkuCodeLevel = defaultTkuCodeLevel;
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Collection<Security> getSecurityCollection() {
        return securityCollection;
    }

    public void setSecurityCollection(Collection<Security> securityCollection) {
        this.securityCollection = securityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleModulesPK != null ? roleModulesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleModules)) {
            return false;
        }
        RoleModules other = (RoleModules) object;
        if ((this.roleModulesPK == null && other.roleModulesPK != null) || (this.roleModulesPK != null && !this.roleModulesPK.equals(other.roleModulesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.RoleModules[roleModulesPK=" + roleModulesPK + "]";
    }

}
