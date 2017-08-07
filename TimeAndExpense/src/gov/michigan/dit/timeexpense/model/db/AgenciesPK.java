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
public class AgenciesPK implements Serializable {
    
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "AGENCY")
    private String agency;

    public AgenciesPK() {
    }

    public AgenciesPK(String department, String agency) {
        this.department = department;
        this.agency = agency;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (department != null ? department.hashCode() : 0);
        hash += (agency != null ? agency.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgenciesPK)) {
            return false;
        }
        AgenciesPK other = (AgenciesPK) object;
        if ((this.department == null && other.department != null) || (this.department != null && !this.department.equals(other.department))) {
            return false;
        }
        if ((this.agency == null && other.agency != null) || (this.agency != null && !this.agency.equals(other.agency))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgenciesPK[department=" + department + ", agency=" + agency + "]";
    }

}
