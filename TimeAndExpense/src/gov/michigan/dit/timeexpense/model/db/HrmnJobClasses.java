/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "HRMN_JOB_CLASSES")
@NamedQueries({@NamedQuery(name = "HrmnJobClasses.findAll", query = "SELECT h FROM HrmnJobClasses h"), @NamedQuery(name = "HrmnJobClasses.findByJobClass", query = "SELECT h FROM HrmnJobClasses h WHERE h.jobClass = :jobClass"), @NamedQuery(name = "HrmnJobClasses.findByModifiedUserId", query = "SELECT h FROM HrmnJobClasses h WHERE h.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "HrmnJobClasses.findByModifiedDate", query = "SELECT h FROM HrmnJobClasses h WHERE h.modifiedDate = :modifiedDate")})
public class HrmnJobClasses implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "JOB_CLASS")
    private String jobClass;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "CLASS_TYPE", referencedColumnName = "CLASS_TYPE")
    @ManyToOne(optional = false)
    private ClassType classType;
    @OneToMany(mappedBy = "jobClass")
    private Collection<HrmnJobCodes> hrmnJobCodesCollection;

    public HrmnJobClasses() {
    }

    public HrmnJobClasses(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
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

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public Collection<HrmnJobCodes> getHrmnJobCodesCollection() {
        return hrmnJobCodesCollection;
    }

    public void setHrmnJobCodesCollection(Collection<HrmnJobCodes> hrmnJobCodesCollection) {
        this.hrmnJobCodesCollection = hrmnJobCodesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobClass != null ? jobClass.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrmnJobClasses)) {
            return false;
        }
        HrmnJobClasses other = (HrmnJobClasses) object;
        if ((this.jobClass == null && other.jobClass != null) || (this.jobClass != null && !this.jobClass.equals(other.jobClass))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.HrmnJobClasses[jobClass=" + jobClass + "]";
    }

}
