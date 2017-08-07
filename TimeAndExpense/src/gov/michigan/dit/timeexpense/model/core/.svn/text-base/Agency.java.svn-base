package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AGENCIES")
public class Agency implements Serializable {

	private static final long serialVersionUID = 787868678L;
	
    @EmbeddedId
    protected AgencyPK agencyPK;
    
    private String name;
    
    public Agency() {
    	agencyPK = new AgencyPK();
    }

    public AgencyPK getAgencyPK() {
        return agencyPK;
    }

    public void setAgencyPK(AgencyPK agenciesPK) {
        this.agencyPK = agenciesPK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDepartment() {
        return agencyPK.getDepartment();
    }

    public void setDepartment(String department) {
        this.agencyPK.setDepartment(department);
    }

    public String getAgency() {
        return agencyPK.getAgency();
    }

    public void setAgency(String agency) {
    	agencyPK.setAgency(agency);
    }    

    @Override
    public String toString() {
        return "Agency[name=" + name + "]";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agencyPK != null ? agencyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agency)) {
            return false;
        }
        Agency other = (Agency) object;
        if ((this.agencyPK == null && other.agencyPK != null) || (this.agencyPK != null && !this.agencyPK.equals(other.agencyPK))) {
            return false;
        }
        return true;
    }
}
