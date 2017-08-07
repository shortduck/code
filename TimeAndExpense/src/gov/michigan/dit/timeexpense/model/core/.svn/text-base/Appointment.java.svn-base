package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "APPOINTMENTS")
public class Appointment implements Serializable {
    private static final long serialVersionUID = 198379996756L;

    @Id
    @Column(name = "APPT_IDENTIFIER")
    private int id;
    
    @Column(name = "EMP_IDENTIFIER")
    private int empId;
    
    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "AGENCY")
    private String agency;

    @Column(name = "TKU")
    private String tku;

    @Column(name = "POSITION_ID")
    private String positionId;

    public Appointment() {}
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
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

	public String getTku() {
		return tku;
	}

	public void setTku(String tku) {
		this.tku = tku;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += id ;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment)object;
        
        return id==other.id;
    }

    @Override
    public String toString() {
        return "Appointment[id=" + id + "]";
    }

}
