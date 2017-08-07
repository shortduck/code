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
@Table(name = "DEPARTMENTS")
@NamedQueries({@NamedQuery(name = "Departments.findAll", query = "SELECT d FROM Departments d"), @NamedQuery(name = "Departments.findByDepartment", query = "SELECT d FROM Departments d WHERE d.department = :department"), @NamedQuery(name = "Departments.findByStartDate", query = "SELECT d FROM Departments d WHERE d.startDate = :startDate"), @NamedQuery(name = "Departments.findByEndDate", query = "SELECT d FROM Departments d WHERE d.endDate = :endDate"), @NamedQuery(name = "Departments.findByName", query = "SELECT d FROM Departments d WHERE d.name = :name"), @NamedQuery(name = "Departments.findByAbbreviation", query = "SELECT d FROM Departments d WHERE d.abbreviation = :abbreviation"), @NamedQuery(name = "Departments.findByDeptCrosswalk", query = "SELECT d FROM Departments d WHERE d.deptCrosswalk = :deptCrosswalk"), @NamedQuery(name = "Departments.findByModifiedDate", query = "SELECT d FROM Departments d WHERE d.modifiedDate = :modifiedDate"), @NamedQuery(name = "Departments.findByModifiedUserId", query = "SELECT d FROM Departments d WHERE d.modifiedUserId = :modifiedUserId")})
public class Departments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "NAME")
    private String name;
    @Column(name = "ABBREVIATION")
    private String abbreviation;
    @Column(name = "DEPT_CROSSWALK")
    private String deptCrosswalk;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departments")
    private Collection<Agencies> agenciesCollection;

    public Departments() {
    }

    public Departments(String department) {
        this.department = department;
    }

    public Departments(String department, Date startDate, Date endDate) {
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDeptCrosswalk() {
        return deptCrosswalk;
    }

    public void setDeptCrosswalk(String deptCrosswalk) {
        this.deptCrosswalk = deptCrosswalk;
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

    public Collection<Agencies> getAgenciesCollection() {
        return agenciesCollection;
    }

    public void setAgenciesCollection(Collection<Agencies> agenciesCollection) {
        this.agenciesCollection = agenciesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (department != null ? department.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departments)) {
            return false;
        }
        Departments other = (Departments) object;
        if ((this.department == null && other.department != null) || (this.department != null && !this.department.equals(other.department))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Departments[department=" + department + "]";
    }

}
