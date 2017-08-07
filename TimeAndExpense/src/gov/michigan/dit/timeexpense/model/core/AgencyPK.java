package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class AgencyPK implements Serializable {
    
	private static final long serialVersionUID = 87868678L;
	
    private String department;
	private String agency;

    public AgencyPK() {
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
    public String toString() {
        return "AgencyPK[department=" + department + ", agency=" + agency + "]";
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
        if (!(object instanceof AgencyPK)) {
            return false;
        }
        AgencyPK other = (AgencyPK) object;
        if ((this.department == null && other.department != null) || (this.department != null && !this.department.equals(other.department))) {
            return false;
        }
        if ((this.agency == null && other.agency != null) || (this.agency != null && !this.agency.equals(other.agency))) {
            return false;
        }
        return true;
    }    
}
