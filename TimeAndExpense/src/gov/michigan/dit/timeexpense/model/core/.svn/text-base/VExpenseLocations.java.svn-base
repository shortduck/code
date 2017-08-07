/**
 * 
 */
package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author bhurata
 *
 */
@Entity
@Table(name = "V_Expense_Locations")
@NamedQueries({@NamedQuery(name = "VExpenseLocations.findAll", query = "SELECT v FROM VExpenseLocations v")
, @NamedQuery(name = "findByDeptAgency", query = "SELECT v FROM VExpenseLocations v WHERE v.department = :department AND v.agency = agency")})
public class VExpenseLocations  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ELOC_IDENTIFIER")
    private Integer elocIdentifier;
    
    @Column(name = "CITY")
    private String city;
    
    @Column(name = "ST_PROV")
    private String stProv;
    
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "AGENCY")
    private String agency;
    
    @Column(name = "statewide")
    private String stateWide;


    public VExpenseLocations(){    	
    }
    
    public Integer getElocIdentifier() {
		return elocIdentifier;
	}

	public void setElocIdentifier(Integer elocIdentifier) {
		this.elocIdentifier = elocIdentifier;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStProv() {
		return stProv;
	}

	public void setStProv(String stProv) {
		this.stProv = stProv;
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

	public String getStateWide() {
		return stateWide;
	}

	public void setStateWide(String stateWide) {
		this.stateWide = stateWide;
	}
}
