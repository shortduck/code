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
public class AgencyCode3PK implements Serializable {
    
    @Column(name = "FACS_AGY")
    private String facsAgy;
    
    @Column(name = "AGENCY_CODE_3")
    private String agencyCode3;

    public AgencyCode3PK() {
    }

    public AgencyCode3PK(String facsAgy, String agencyCode3) {
        this.facsAgy = facsAgy;
        this.agencyCode3 = agencyCode3;
    }

    public String getFacsAgy() {
        return facsAgy;
    }

    public void setFacsAgy(String facsAgy) {
        this.facsAgy = facsAgy;
    }

    public String getAgencyCode3() {
        return agencyCode3;
    }

    public void setAgencyCode3(String agencyCode3) {
        this.agencyCode3 = agencyCode3;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facsAgy != null ? facsAgy.hashCode() : 0);
        hash += (agencyCode3 != null ? agencyCode3.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyCode3PK)) {
            return false;
        }
        AgencyCode3PK other = (AgencyCode3PK) object;
        if ((this.facsAgy == null && other.facsAgy != null) || (this.facsAgy != null && !this.facsAgy.equals(other.facsAgy))) {
            return false;
        }
        if ((this.agencyCode3 == null && other.agencyCode3 != null) || (this.agencyCode3 != null && !this.agencyCode3.equals(other.agencyCode3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyCode3PK[facsAgy=" + facsAgy + ", agencyCode3=" + agencyCode3 + "]";
    }

}
