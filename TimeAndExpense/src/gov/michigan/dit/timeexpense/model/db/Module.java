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
@Table(name = "MODULE")
@NamedQueries({@NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m"), @NamedQuery(name = "Module.findByModuleId", query = "SELECT m FROM Module m WHERE m.moduleId = :moduleId"), @NamedQuery(name = "Module.findByLowestOrgCodeLevel", query = "SELECT m FROM Module m WHERE m.lowestOrgCodeLevel = :lowestOrgCodeLevel"), @NamedQuery(name = "Module.findByLowestTkuCodeLevel", query = "SELECT m FROM Module m WHERE m.lowestTkuCodeLevel = :lowestTkuCodeLevel"), @NamedQuery(name = "Module.findByDisabledInd", query = "SELECT m FROM Module m WHERE m.disabledInd = :disabledInd"), @NamedQuery(name = "Module.findByChangeAccessInd", query = "SELECT m FROM Module m WHERE m.changeAccessInd = :changeAccessInd"), @NamedQuery(name = "Module.findByName", query = "SELECT m FROM Module m WHERE m.name = :name"), @NamedQuery(name = "Module.findByDescription", query = "SELECT m FROM Module m WHERE m.description = :description"), @NamedQuery(name = "Module.findByModifiedUserId", query = "SELECT m FROM Module m WHERE m.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "Module.findByModifiedDate", query = "SELECT m FROM Module m WHERE m.modifiedDate = :modifiedDate")})
public class Module implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "MODULE_ID")
    private String moduleId;
    @Column(name = "LOWEST_ORG_CODE_LEVEL")
    private String lowestOrgCodeLevel;
    
    @Column(name = "LOWEST_TKU_CODE_LEVEL")
    private String lowestTkuCodeLevel;
    
    @Column(name = "DISABLED_IND")
    private String disabledInd;
    
    @Column(name = "CHANGE_ACCESS_IND")
    private String changeAccessInd;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
    private Collection<RoleModules> roleModulesCollection;

    public Module() {
    }

    public Module(String moduleId) {
        this.moduleId = moduleId;
    }

    public Module(String moduleId, String lowestTkuCodeLevel, String disabledInd, String changeAccessInd) {
        this.moduleId = moduleId;
        this.lowestTkuCodeLevel = lowestTkuCodeLevel;
        this.disabledInd = disabledInd;
        this.changeAccessInd = changeAccessInd;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getLowestOrgCodeLevel() {
        return lowestOrgCodeLevel;
    }

    public void setLowestOrgCodeLevel(String lowestOrgCodeLevel) {
        this.lowestOrgCodeLevel = lowestOrgCodeLevel;
    }

    public String getLowestTkuCodeLevel() {
        return lowestTkuCodeLevel;
    }

    public void setLowestTkuCodeLevel(String lowestTkuCodeLevel) {
        this.lowestTkuCodeLevel = lowestTkuCodeLevel;
    }

    public String getDisabledInd() {
        return disabledInd;
    }

    public void setDisabledInd(String disabledInd) {
        this.disabledInd = disabledInd;
    }

    public String getChangeAccessInd() {
        return changeAccessInd;
    }

    public void setChangeAccessInd(String changeAccessInd) {
        this.changeAccessInd = changeAccessInd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moduleId != null ? moduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.moduleId == null && other.moduleId != null) || (this.moduleId != null && !this.moduleId.equals(other.moduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Module[moduleId=" + moduleId + "]";
    }

}
