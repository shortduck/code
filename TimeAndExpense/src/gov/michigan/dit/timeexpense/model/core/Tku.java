package gov.michigan.dit.timeexpense.model.core;

import gov.michigan.dit.timeexpense.model.db.Tkus;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TKUS")
public class Tku implements Serializable {

	private static final long serialVersionUID = 1L;
    
	@EmbeddedId
    protected TkuPK tkuPK;
    private String name;

    public Tku() {
    	tkuPK = new TkuPK();
    }

    public Tku(TkuPK tkusPK) {
        this.tkuPK = tkusPK;
    }

    public Tku(String department, String agency, String tku) {
        this.tkuPK = new TkuPK(department, agency, tku);
    }

    public TkuPK getTkuPK() {
        return tkuPK;
    }

    public void setTkuPK(TkuPK tkusPK) {
        this.tkuPK = tkusPK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTku() {
        return getTkuPK().getTku();
    }

    public void setTku(String tku) {
        this.tkuPK.setTku(tku);
    }    
    
    public String getDepartment() {
        return tkuPK.getDepartment();
    }

    public void setDepartment(String department) {
        tkuPK.setDepartment(department);
    }

    public String getAgency() {
        return tkuPK.getAgency();
    }

    public void setAgency(String agency) {
        tkuPK.setAgency(agency);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tkuPK != null ? tkuPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tkus)) {
            return false;
        }
        Tkus other = (Tkus) object;
        if ((this.tkuPK == null && other.getTkusPK() != null) || (this.tkuPK != null && !this.tkuPK.equals(other.getTkusPK()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tku[tkusPK=" + tkuPK + "]";
    }

}
