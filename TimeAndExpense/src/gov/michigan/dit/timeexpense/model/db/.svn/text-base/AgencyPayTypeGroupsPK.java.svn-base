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
public class AgencyPayTypeGroupsPK implements Serializable {
    
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "AGENCY")
    private String agency;
    
    @Column(name = "PAY_TYPE_GROUP")
    private String payTypeGroup;

    public AgencyPayTypeGroupsPK() {
    }

    public AgencyPayTypeGroupsPK(String department, String agency, String payTypeGroup) {
        this.department = department;
        this.agency = agency;
        this.payTypeGroup = payTypeGroup;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getPayTypeGroup() {
        return payTypeGroup;
    }

    public void setPayTypeGroup(String payTypeGroup) {
        this.payTypeGroup = payTypeGroup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (department != null ? department.hashCode() : 0);
        hash += (agency != null ? agency.hashCode() : 0);
        hash += (payTypeGroup != null ? payTypeGroup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyPayTypeGroupsPK)) {
            return false;
        }
        AgencyPayTypeGroupsPK other = (AgencyPayTypeGroupsPK) object;
        if ((this.department == null && other.department != null) || (this.department != null && !this.department.equals(other.department))) {
            return false;
        }
        if ((this.agency == null && other.agency != null) || (this.agency != null && !this.agency.equals(other.agency))) {
            return false;
        }
        if ((this.payTypeGroup == null && other.payTypeGroup != null) || (this.payTypeGroup != null && !this.payTypeGroup.equals(other.payTypeGroup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyPayTypeGroupsPK[department=" + department + ", agency=" + agency + ", payTypeGroup=" + payTypeGroup + "]";
    }

}
