/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "SSN_HISTORIES")
@NamedQueries({@NamedQuery(name = "SsnHistories.findAll", query = "SELECT s FROM SsnHistories s"), @NamedQuery(name = "SsnHistories.findByEmpIdentifier", query = "SELECT s FROM SsnHistories s WHERE s.ssnHistoriesPK.empIdentifier = :empIdentifier"), @NamedQuery(name = "SsnHistories.findByOldSsn", query = "SELECT s FROM SsnHistories s WHERE s.ssnHistoriesPK.oldSsn = :oldSsn"), @NamedQuery(name = "SsnHistories.findByModifiedDate", query = "SELECT s FROM SsnHistories s WHERE s.modifiedDate = :modifiedDate"), @NamedQuery(name = "SsnHistories.findByModifiedUserId", query = "SELECT s FROM SsnHistories s WHERE s.modifiedUserId = :modifiedUserId")})
public class SsnHistories implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SsnHistoriesPK ssnHistoriesPK;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @JoinColumn(name = "EMP_IDENTIFIER", referencedColumnName = "EMP_IDENTIFIER", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employees employees;

    public SsnHistories() {
    }

    public SsnHistories(SsnHistoriesPK ssnHistoriesPK) {
        this.ssnHistoriesPK = ssnHistoriesPK;
    }

    public SsnHistories(int empIdentifier, String oldSsn) {
        this.ssnHistoriesPK = new SsnHistoriesPK(empIdentifier, oldSsn);
    }

    public SsnHistoriesPK getSsnHistoriesPK() {
        return ssnHistoriesPK;
    }

    public void setSsnHistoriesPK(SsnHistoriesPK ssnHistoriesPK) {
        this.ssnHistoriesPK = ssnHistoriesPK;
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

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ssnHistoriesPK != null ? ssnHistoriesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SsnHistories)) {
            return false;
        }
        SsnHistories other = (SsnHistories) object;
        if ((this.ssnHistoriesPK == null && other.ssnHistoriesPK != null) || (this.ssnHistoriesPK != null && !this.ssnHistoriesPK.equals(other.ssnHistoriesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.SsnHistories[ssnHistoriesPK=" + ssnHistoriesPK + "]";
    }

}
