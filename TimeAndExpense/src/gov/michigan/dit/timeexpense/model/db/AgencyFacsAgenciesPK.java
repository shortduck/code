/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GhoshS
 */
@Embeddable
public class AgencyFacsAgenciesPK implements Serializable {
    
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "AGENCY")
    private String agency;
    
    @Column(name = "FACS_AGY")
    private String facsAgy;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    public AgencyFacsAgenciesPK() {
    }

    public AgencyFacsAgenciesPK(String department, String agency, String facsAgy, Date startDate) {
        this.department = department;
        this.agency = agency;
        this.facsAgy = facsAgy;
        this.startDate = startDate;
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

    public String getFacsAgy() {
        return facsAgy;
    }

    public void setFacsAgy(String facsAgy) {
        this.facsAgy = facsAgy;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (department != null ? department.hashCode() : 0);
        hash += (agency != null ? agency.hashCode() : 0);
        hash += (facsAgy != null ? facsAgy.hashCode() : 0);
        hash += (startDate != null ? startDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyFacsAgenciesPK)) {
            return false;
        }
        AgencyFacsAgenciesPK other = (AgencyFacsAgenciesPK) object;
        if ((this.department == null && other.department != null) || (this.department != null && !this.department.equals(other.department))) {
            return false;
        }
        if ((this.agency == null && other.agency != null) || (this.agency != null && !this.agency.equals(other.agency))) {
            return false;
        }
        if ((this.facsAgy == null && other.facsAgy != null) || (this.facsAgy != null && !this.facsAgy.equals(other.facsAgy))) {
            return false;
        }
        if ((this.startDate == null && other.startDate != null) || (this.startDate != null && !this.startDate.equals(other.startDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyFacsAgenciesPK[department=" + department + ", agency=" + agency + ", facsAgy=" + facsAgy + ", startDate=" + startDate + "]";
    }

}
