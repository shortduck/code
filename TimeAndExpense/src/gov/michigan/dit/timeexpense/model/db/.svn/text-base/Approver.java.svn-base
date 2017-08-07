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
@Table(name = "APPROVER")
@NamedQueries({@NamedQuery(name = "Approver.findAll", query = "SELECT a FROM Approver a"), @NamedQuery(name = "Approver.findByAprvrIdentifier", query = "SELECT a FROM Approver a WHERE a.aprvrIdentifier = :aprvrIdentifier"), @NamedQuery(name = "Approver.findByPositionId", query = "SELECT a FROM Approver a WHERE a.positionId = :positionId"), @NamedQuery(name = "Approver.findByModifiedDate", query = "SELECT a FROM Approver a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "Approver.findByModifiedUserId", query = "SELECT a FROM Approver a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "Approver.findByOldApptIdentifier", query = "SELECT a FROM Approver a WHERE a.oldApptIdentifier = :oldApptIdentifier")})
public class Approver implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "APRVR_IDENTIFIER")
    private Integer aprvrIdentifier;
    @Column(name = "POSITION_ID")
    private String positionId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "OLD_APPT_IDENTIFIER")
    private Integer oldApptIdentifier;
    @JoinColumn(name = "APPT_IDENTIFIER", referencedColumnName = "APPT_IDENTIFIER")
    @ManyToOne
    private Appointments apptIdentifier;
    @JoinColumn(name = "NEMP_IDENTIFIER", referencedColumnName = "NEMP_IDENTIFIER")
    @ManyToOne
    private NonEmp nempIdentifier;
    @JoinColumn(name = "APPROVER_USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private UserIds approverUserId;

    public Approver() {
    }

    public Approver(Integer aprvrIdentifier) {
        this.aprvrIdentifier = aprvrIdentifier;
    }

    public Integer getAprvrIdentifier() {
        return aprvrIdentifier;
    }

    public void setAprvrIdentifier(Integer aprvrIdentifier) {
        this.aprvrIdentifier = aprvrIdentifier;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
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

    public Integer getOldApptIdentifier() {
        return oldApptIdentifier;
    }

    public void setOldApptIdentifier(Integer oldApptIdentifier) {
        this.oldApptIdentifier = oldApptIdentifier;
    }

    public Appointments getApptIdentifier() {
        return apptIdentifier;
    }

    public void setApptIdentifier(Appointments apptIdentifier) {
        this.apptIdentifier = apptIdentifier;
    }

    public NonEmp getNempIdentifier() {
        return nempIdentifier;
    }

    public void setNempIdentifier(NonEmp nempIdentifier) {
        this.nempIdentifier = nempIdentifier;
    }

    public UserIds getApproverUserId() {
        return approverUserId;
    }

    public void setApproverUserId(UserIds approverUserId) {
        this.approverUserId = approverUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aprvrIdentifier != null ? aprvrIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Approver)) {
            return false;
        }
        Approver other = (Approver) object;
        if ((this.aprvrIdentifier == null && other.aprvrIdentifier != null) || (this.aprvrIdentifier != null && !this.aprvrIdentifier.equals(other.aprvrIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Approver[aprvrIdentifier=" + aprvrIdentifier + "]";
    }

}
