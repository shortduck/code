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
@Table(name = "SECURITY")
@NamedQueries({@NamedQuery(name = "Security.findAll", query = "SELECT s FROM Security s"), @NamedQuery(name = "Security.findByUrtoIdentifier", query = "SELECT s FROM Security s WHERE s.urtoIdentifier = :urtoIdentifier"), @NamedQuery(name = "Security.findByStartDate", query = "SELECT s FROM Security s WHERE s.startDate = :startDate"), @NamedQuery(name = "Security.findByEndDate", query = "SELECT s FROM Security s WHERE s.endDate = :endDate"), @NamedQuery(name = "Security.findByEmployeeOnlyInd", query = "SELECT s FROM Security s WHERE s.employeeOnlyInd = :employeeOnlyInd"), @NamedQuery(name = "Security.findByChangeAccessInd", query = "SELECT s FROM Security s WHERE s.changeAccessInd = :changeAccessInd"), @NamedQuery(name = "Security.findByOrganizationNumber", query = "SELECT s FROM Security s WHERE s.organizationNumber = :organizationNumber"), @NamedQuery(name = "Security.findByOrganizationUpperBound", query = "SELECT s FROM Security s WHERE s.organizationUpperBound = :organizationUpperBound"), @NamedQuery(name = "Security.findByModifiedUserId", query = "SELECT s FROM Security s WHERE s.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "Security.findByModifiedDate", query = "SELECT s FROM Security s WHERE s.modifiedDate = :modifiedDate")})
public class Security implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "URTO_IDENTIFIER")
    private Integer urtoIdentifier;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "EMPLOYEE_ONLY_IND")
    private String employeeOnlyInd;
    
    @Column(name = "CHANGE_ACCESS_IND")
    private String changeAccessInd;
    @Column(name = "ORGANIZATION_NUMBER")
    private String organizationNumber;
    @Column(name = "ORGANIZATION_UPPER_BOUND")
    private String organizationUpperBound;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumns({@JoinColumn(name = "DEPARTMENTRL", referencedColumnName = "DEPARTMENT"), @JoinColumn(name = "AGENCYRL", referencedColumnName = "AGENCY"), @JoinColumn(name = "ROLE_NAME", referencedColumnName = "ROLE_NAME"), @JoinColumn(name = "MODULE_ID", referencedColumnName = "MODULE_ID")})
    @ManyToOne(optional = false)
    private RoleModules roleModules;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT"), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY"), @JoinColumn(name = "TKU", referencedColumnName = "TKU")})
    @ManyToOne
    private Tkus tkus;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private UserIds userId;

    public Security() {
    }

    public Security(Integer urtoIdentifier) {
        this.urtoIdentifier = urtoIdentifier;
    }

    public Security(Integer urtoIdentifier, Date startDate, Date endDate, String employeeOnlyInd, String changeAccessInd) {
        this.urtoIdentifier = urtoIdentifier;
        this.startDate = startDate;
        this.endDate = endDate;
        this.employeeOnlyInd = employeeOnlyInd;
        this.changeAccessInd = changeAccessInd;
    }

    public Integer getUrtoIdentifier() {
        return urtoIdentifier;
    }

    public void setUrtoIdentifier(Integer urtoIdentifier) {
        this.urtoIdentifier = urtoIdentifier;
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

    public String getEmployeeOnlyInd() {
        return employeeOnlyInd;
    }

    public void setEmployeeOnlyInd(String employeeOnlyInd) {
        this.employeeOnlyInd = employeeOnlyInd;
    }

    public String getChangeAccessInd() {
        return changeAccessInd;
    }

    public void setChangeAccessInd(String changeAccessInd) {
        this.changeAccessInd = changeAccessInd;
    }

    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(String organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    public String getOrganizationUpperBound() {
        return organizationUpperBound;
    }

    public void setOrganizationUpperBound(String organizationUpperBound) {
        this.organizationUpperBound = organizationUpperBound;
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

    public RoleModules getRoleModules() {
        return roleModules;
    }

    public void setRoleModules(RoleModules roleModules) {
        this.roleModules = roleModules;
    }

    public Tkus getTkus() {
        return tkus;
    }

    public void setTkus(Tkus tkus) {
        this.tkus = tkus;
    }

    public UserIds getUserId() {
        return userId;
    }

    public void setUserId(UserIds userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (urtoIdentifier != null ? urtoIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Security)) {
            return false;
        }
        Security other = (Security) object;
        if ((this.urtoIdentifier == null && other.urtoIdentifier != null) || (this.urtoIdentifier != null && !this.urtoIdentifier.equals(other.urtoIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Security[urtoIdentifier=" + urtoIdentifier + "]";
    }

}
