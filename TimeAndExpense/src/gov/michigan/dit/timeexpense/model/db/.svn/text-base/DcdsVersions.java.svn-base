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
@Table(name = "DCDS_VERSIONS")
@NamedQueries({@NamedQuery(name = "DcdsVersions.findAll", query = "SELECT d FROM DcdsVersions d"), @NamedQuery(name = "DcdsVersions.findByVersionNumber", query = "SELECT d FROM DcdsVersions d WHERE d.versionNumber = :versionNumber"), @NamedQuery(name = "DcdsVersions.findByStartDate", query = "SELECT d FROM DcdsVersions d WHERE d.startDate = :startDate"), @NamedQuery(name = "DcdsVersions.findByEndDate", query = "SELECT d FROM DcdsVersions d WHERE d.endDate = :endDate"), @NamedQuery(name = "DcdsVersions.findByCurrentInd", query = "SELECT d FROM DcdsVersions d WHERE d.currentInd = :currentInd"), @NamedQuery(name = "DcdsVersions.findByComments", query = "SELECT d FROM DcdsVersions d WHERE d.comments = :comments"), @NamedQuery(name = "DcdsVersions.findByModifiedUserId", query = "SELECT d FROM DcdsVersions d WHERE d.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "DcdsVersions.findByModifiedDate", query = "SELECT d FROM DcdsVersions d WHERE d.modifiedDate = :modifiedDate")})
public class DcdsVersions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "VERSION_NUMBER")
    private String versionNumber;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "CURRENT_IND")
    private String currentInd;
    
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(mappedBy = "versionNumber")
    private Collection<UserIds> userIdsCollection;

    public DcdsVersions() {
    }

    public DcdsVersions(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public DcdsVersions(String versionNumber, Date startDate, Date endDate, String currentInd, String comments) {
        this.versionNumber = versionNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentInd = currentInd;
        this.comments = comments;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
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

    public String getCurrentInd() {
        return currentInd;
    }

    public void setCurrentInd(String currentInd) {
        this.currentInd = currentInd;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public Collection<UserIds> getUserIdsCollection() {
        return userIdsCollection;
    }

    public void setUserIdsCollection(Collection<UserIds> userIdsCollection) {
        this.userIdsCollection = userIdsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (versionNumber != null ? versionNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DcdsVersions)) {
            return false;
        }
        DcdsVersions other = (DcdsVersions) object;
        if ((this.versionNumber == null && other.versionNumber != null) || (this.versionNumber != null && !this.versionNumber.equals(other.versionNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.DcdsVersions[versionNumber=" + versionNumber + "]";
    }

}
