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
public class AgencyCode2PK implements Serializable {
    
    @Column(name = "FACS_AGY")
    private String facsAgy;
    
    @Column(name = "AGENCY_CODE_2")
    private String agencyCode2;

    public AgencyCode2PK() {
    }

    public AgencyCode2PK(String facsAgy, String agencyCode2) {
        this.facsAgy = facsAgy;
        this.agencyCode2 = agencyCode2;
    }

    public String getFacsAgy() {
        return facsAgy;
    }

    public void setFacsAgy(String facsAgy) {
        this.facsAgy = facsAgy;
    }

    public String getAgencyCode2() {
        return agencyCode2;
    }

    public void setAgencyCode2(String agencyCode2) {
        this.agencyCode2 = agencyCode2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facsAgy != null ? facsAgy.hashCode() : 0);
        hash += (agencyCode2 != null ? agencyCode2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyCode2PK)) {
            return false;
        }
        AgencyCode2PK other = (AgencyCode2PK) object;
        if ((this.facsAgy == null && other.facsAgy != null) || (this.facsAgy != null && !this.facsAgy.equals(other.facsAgy))) {
            return false;
        }
        if ((this.agencyCode2 == null && other.agencyCode2 != null) || (this.agencyCode2 != null && !this.agencyCode2.equals(other.agencyCode2))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyCode2PK[facsAgy=" + facsAgy + ", agencyCode2=" + agencyCode2 + "]";
    }

}
