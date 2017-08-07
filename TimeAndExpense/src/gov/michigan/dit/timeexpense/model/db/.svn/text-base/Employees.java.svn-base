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
@Table(name = "EMPLOYEES")
@NamedQueries({@NamedQuery(name = "Employees.findAll", query = "SELECT e FROM Employees e"), @NamedQuery(name = "Employees.findByEmpIdentifier", query = "SELECT e FROM Employees e WHERE e.empIdentifier = :empIdentifier"), @NamedQuery(name = "Employees.findBySsn", query = "SELECT e FROM Employees e WHERE e.ssn = :ssn"), @NamedQuery(name = "Employees.findByModifiedDate", query = "SELECT e FROM Employees e WHERE e.modifiedDate = :modifiedDate"), @NamedQuery(name = "Employees.findByModifiedUserId", query = "SELECT e FROM Employees e WHERE e.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "Employees.findByCompany", query = "SELECT e FROM Employees e WHERE e.company = :company"), @NamedQuery(name = "Employees.findBySsnReal", query = "SELECT e FROM Employees e WHERE e.ssnReal = :ssnReal")})
public class Employees implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "EMP_IDENTIFIER")
    private Integer empIdentifier;
    
    @Column(name = "SSN")
    private String ssn;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "COMPANY")
    private Short company;
    @Column(name = "SSN_REAL")
    private String ssnReal;
    @OneToMany(mappedBy = "empIdentifier")
    private Collection<UserIds> userIdsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employees")
    private Collection<SsnHistories> ssnHistoriesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empIdentifier")
    private Collection<Appointments> appointmentsCollection;

    public Employees() {
    }

    public Employees(Integer empIdentifier) {
        this.empIdentifier = empIdentifier;
    }

    public Employees(Integer empIdentifier, String ssn) {
        this.empIdentifier = empIdentifier;
        this.ssn = ssn;
    }

    public Integer getEmpIdentifier() {
        return empIdentifier;
    }

    public void setEmpIdentifier(Integer empIdentifier) {
        this.empIdentifier = empIdentifier;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
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

    public Short getCompany() {
        return company;
    }

    public void setCompany(Short company) {
        this.company = company;
    }

    public String getSsnReal() {
        return ssnReal;
    }

    public void setSsnReal(String ssnReal) {
        this.ssnReal = ssnReal;
    }

    public Collection<UserIds> getUserIdsCollection() {
        return userIdsCollection;
    }

    public void setUserIdsCollection(Collection<UserIds> userIdsCollection) {
        this.userIdsCollection = userIdsCollection;
    }

    public Collection<SsnHistories> getSsnHistoriesCollection() {
        return ssnHistoriesCollection;
    }

    public void setSsnHistoriesCollection(Collection<SsnHistories> ssnHistoriesCollection) {
        this.ssnHistoriesCollection = ssnHistoriesCollection;
    }

    public Collection<Appointments> getAppointmentsCollection() {
        return appointmentsCollection;
    }

    public void setAppointmentsCollection(Collection<Appointments> appointmentsCollection) {
        this.appointmentsCollection = appointmentsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empIdentifier != null ? empIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employees)) {
            return false;
        }
        Employees other = (Employees) object;
        if ((this.empIdentifier == null && other.empIdentifier != null) || (this.empIdentifier != null && !this.empIdentifier.equals(other.empIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Employees[empIdentifier=" + empIdentifier + "]";
    }

}
