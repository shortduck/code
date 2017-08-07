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
@Table(name = "USER_IDS")
@NamedQueries({@NamedQuery(name = "UserIds.findAll", query = "SELECT u FROM UserIds u"), @NamedQuery(name = "UserIds.findByUserId", query = "SELECT u FROM UserIds u WHERE u.userId = :userId"), @NamedQuery(name = "UserIds.findByStartDate", query = "SELECT u FROM UserIds u WHERE u.startDate = :startDate"), @NamedQuery(name = "UserIds.findByEndDate", query = "SELECT u FROM UserIds u WHERE u.endDate = :endDate"), @NamedQuery(name = "UserIds.findByPwChangeDate", query = "SELECT u FROM UserIds u WHERE u.pwChangeDate = :pwChangeDate"), @NamedQuery(name = "UserIds.findByMailId", query = "SELECT u FROM UserIds u WHERE u.mailId = :mailId"), @NamedQuery(name = "UserIds.findByTelephoneNo", query = "SELECT u FROM UserIds u WHERE u.telephoneNo = :telephoneNo"), @NamedQuery(name = "UserIds.findByLocation", query = "SELECT u FROM UserIds u WHERE u.location = :location"), @NamedQuery(name = "UserIds.findByLastLoginDate", query = "SELECT u FROM UserIds u WHERE u.lastLoginDate = :lastLoginDate"), @NamedQuery(name = "UserIds.findByModifiedUserId", query = "SELECT u FROM UserIds u WHERE u.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "UserIds.findByModifiedDate", query = "SELECT u FROM UserIds u WHERE u.modifiedDate = :modifiedDate")})
public class UserIds implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "USER_ID")
    private String userId;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "PW_CHANGE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pwChangeDate;
    @Column(name = "MAIL_ID")
    private String mailId;
    @Column(name = "TELEPHONE_NO")
    private String telephoneNo;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "LAST_LOGIN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "VERSION_NUMBER", referencedColumnName = "VERSION_NUMBER")
    @ManyToOne
    private DcdsVersions versionNumber;
    @JoinColumn(name = "EMP_IDENTIFIER", referencedColumnName = "EMP_IDENTIFIER")
    @ManyToOne
    private Employees empIdentifier;
    @JoinColumn(name = "NEMP_IDENTIFIER", referencedColumnName = "NEMP_IDENTIFIER")
    @ManyToOne
    private NonEmp nempIdentifier;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Security> securityCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userIds")
    private Collection<UserIdRoles> userIdRolesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approverUserId")
    private Collection<Approver> approverCollection;

    public UserIds() {
    }

    public UserIds(String userId) {
        this.userId = userId;
    }

    public UserIds(String userId, Date startDate, Date endDate) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Date getPwChangeDate() {
        return pwChangeDate;
    }

    public void setPwChangeDate(Date pwChangeDate) {
        this.pwChangeDate = pwChangeDate;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
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

    public DcdsVersions getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(DcdsVersions versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Employees getEmpIdentifier() {
        return empIdentifier;
    }

    public void setEmpIdentifier(Employees empIdentifier) {
        this.empIdentifier = empIdentifier;
    }

    public NonEmp getNempIdentifier() {
        return nempIdentifier;
    }

    public void setNempIdentifier(NonEmp nempIdentifier) {
        this.nempIdentifier = nempIdentifier;
    }

    public Collection<Security> getSecurityCollection() {
        return securityCollection;
    }

    public void setSecurityCollection(Collection<Security> securityCollection) {
        this.securityCollection = securityCollection;
    }

    public Collection<UserIdRoles> getUserIdRolesCollection() {
        return userIdRolesCollection;
    }

    public void setUserIdRolesCollection(Collection<UserIdRoles> userIdRolesCollection) {
        this.userIdRolesCollection = userIdRolesCollection;
    }

    public Collection<Approver> getApproverCollection() {
        return approverCollection;
    }

    public void setApproverCollection(Collection<Approver> approverCollection) {
        this.approverCollection = approverCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserIds)) {
            return false;
        }
        UserIds other = (UserIds) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.UserIds[userId=" + userId + "]";
    }

}
