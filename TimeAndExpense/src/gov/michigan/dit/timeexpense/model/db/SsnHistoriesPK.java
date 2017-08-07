/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author GhoshS
 */
@Embeddable
public class SsnHistoriesPK implements Serializable {
    
    @Column(name = "EMP_IDENTIFIER")
    private int empIdentifier;
    
    @Column(name = "OLD_SSN")
    private String oldSsn;

    public SsnHistoriesPK() {
    }

    public SsnHistoriesPK(int empIdentifier, String oldSsn) {
        this.empIdentifier = empIdentifier;
        this.oldSsn = oldSsn;
    }

    public int getEmpIdentifier() {
        return empIdentifier;
    }

    public void setEmpIdentifier(int empIdentifier) {
        this.empIdentifier = empIdentifier;
    }

    public String getOldSsn() {
        return oldSsn;
    }

    public void setOldSsn(String oldSsn) {
        this.oldSsn = oldSsn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) empIdentifier;
        hash += (oldSsn != null ? oldSsn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SsnHistoriesPK)) {
            return false;
        }
        SsnHistoriesPK other = (SsnHistoriesPK) object;
        if (this.empIdentifier != other.empIdentifier) {
            return false;
        }
        if ((this.oldSsn == null && other.oldSsn != null) || (this.oldSsn != null && !this.oldSsn.equals(other.oldSsn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.SsnHistoriesPK[empIdentifier=" + empIdentifier + ", oldSsn=" + oldSsn + "]";
    }

}
