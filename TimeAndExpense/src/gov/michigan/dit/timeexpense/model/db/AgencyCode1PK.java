/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author GhoshS
 */
@Embeddable
public class AgencyCode1PK implements Serializable {
    
    @Column(name = "FACS_AGY")
    private String facsAgy;
    
    @Column(name = "AGENCY_CODE_1")
    private String agencyCode1;

    public AgencyCode1PK() {
    }

    public AgencyCode1PK(String facsAgy, String agencyCode1) {
        this.facsAgy = facsAgy;
        this.agencyCode1 = agencyCode1;
    }

    public String getFacsAgy() {
        return facsAgy;
    }

    public void setFacsAgy(String facsAgy) {
        this.facsAgy = facsAgy;
    }

    public String getAgencyCode1() {
        return agencyCode1;
    }

    public void setAgencyCode1(String agencyCode1) {
        this.agencyCode1 = agencyCode1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facsAgy != null ? facsAgy.hashCode() : 0);
        hash += (agencyCode1 != null ? agencyCode1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyCode1PK)) {
            return false;
        }
        AgencyCode1PK other = (AgencyCode1PK) object;
        if ((this.facsAgy == null && other.facsAgy != null) || (this.facsAgy != null && !this.facsAgy.equals(other.facsAgy))) {
            return false;
        }
        if ((this.agencyCode1 == null && other.agencyCode1 != null) || (this.agencyCode1 != null && !this.agencyCode1.equals(other.agencyCode1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyCode1PK[facsAgy=" + facsAgy + ", agencyCode1=" + agencyCode1 + "]";
    }

}
