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
@Table(name = "CLASS_TYPE")
@NamedQueries({@NamedQuery(name = "ClassType.findAll", query = "SELECT c FROM ClassType c"), @NamedQuery(name = "ClassType.findByClassType", query = "SELECT c FROM ClassType c WHERE c.classType = :classType"), @NamedQuery(name = "ClassType.findByDescription", query = "SELECT c FROM ClassType c WHERE c.description = :description"), @NamedQuery(name = "ClassType.findByModifiedUserId", query = "SELECT c FROM ClassType c WHERE c.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "ClassType.findByModifiedDate", query = "SELECT c FROM ClassType c WHERE c.modifiedDate = :modifiedDate")})
public class ClassType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "CLASS_TYPE")
    private String classType;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classType")
    private Collection<HrmnJobClasses> hrmnJobClassesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classType")
    private Collection<AgencyPayTypes> agencyPayTypesCollection;

    public ClassType() {
    }

    public ClassType(String classType) {
        this.classType = classType;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
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

    public Collection<HrmnJobClasses> getHrmnJobClassesCollection() {
        return hrmnJobClassesCollection;
    }

    public void setHrmnJobClassesCollection(Collection<HrmnJobClasses> hrmnJobClassesCollection) {
        this.hrmnJobClassesCollection = hrmnJobClassesCollection;
    }

    public Collection<AgencyPayTypes> getAgencyPayTypesCollection() {
        return agencyPayTypesCollection;
    }

    public void setAgencyPayTypesCollection(Collection<AgencyPayTypes> agencyPayTypesCollection) {
        this.agencyPayTypesCollection = agencyPayTypesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classType != null ? classType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClassType)) {
            return false;
        }
        ClassType other = (ClassType) object;
        if ((this.classType == null && other.classType != null) || (this.classType != null && !this.classType.equals(other.classType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.ClassType[classType=" + classType + "]";
    }

}
