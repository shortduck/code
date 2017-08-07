package gov.michigan.dit.timeexpense.model.core;

import gov.michigan.dit.timeexpense.model.db.AgencyPayTypes;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "EXPENSE_LOCATIONS")
@NamedQueries({@NamedQuery(name = "ExpenseLocations.findAll", query = "SELECT e FROM ExpenseLocations e"), @NamedQuery(name = "ExpenseLocations.findByElocIdentifier", query = "SELECT e FROM ExpenseLocations e WHERE e.elocIdentifier = :elocIdentifier"), @NamedQuery(name = "ExpenseLocations.findByCity", query = "SELECT e FROM ExpenseLocations e WHERE e.city = :city"), @NamedQuery(name = "ExpenseLocations.findByStProv", query = "SELECT e FROM ExpenseLocations e WHERE e.stProv = :stProv"), @NamedQuery(name = "ExpenseLocations.findByModifiedDate", query = "SELECT e FROM ExpenseLocations e WHERE e.modifiedDate = :modifiedDate"), @NamedQuery(name = "ExpenseLocations.findbyDepartmebtAgency",query =" Select e form ExpenseLocations where e.department = :department and e.agency =:agency" ),@NamedQuery(name = "ExpenseLocations.findByModifiedUserId", query = "SELECT e FROM ExpenseLocations e WHERE e.modifiedUserId = :modifiedUserId")})
public class ExpenseLocations implements Serializable {
    private static final long serialVersionUID = 1L;
  
    
    @Id
    @SequenceGenerator(name = "EXPENSE_LOCATIONS_GENERATOR", sequenceName = "ELOC_IDENTIFIER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPENSE_LOCATIONS_GENERATOR")
   
    @Column(name = "ELOC_IDENTIFIER")
    private Integer elocIdentifier;
    
    @Basic(optional = false)
    @Column(name = "CITY")
    private String city;
    
    @Basic(optional = false)
    @Column(name = "ST_PROV")
    private String stProv;
    
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    
    @Column(name ="DEPARTMENT")
    private String department;
    
    @Column(name ="AGENCY")
    private String agency;
    
    @Column(name="SELECT_CITY_IND")
    private String selectCityInd;
    
    @Column(name="DISPLAY_ORDER")
    private String displayOrder;
    
    
    private Collection<ExpenseLocations> expenseLocations;
   

   /**
    * Get value for column SELECT_CITY_IND
    * @return String
    */
    public String getSelectCityInd()
    {
    	return selectCityInd;
    }
    /**
     * Set value for column SELECT_CITY_IND
     * @param selectCityInd
     */
    public void  setSelectCityInd(String selectCityInd)
    {
    	this.selectCityInd = selectCityInd;
    }
	/**
	 * Maintains status if at least one underlying transaction for the appointment exists.
	 */
	@Transient
	private String description;
	
	@Transient
	private String display;
	
	@Transient
	private String delButton;
	
	@Transient
	private String stateWide;
	
	
	@Transient
	private String accessT;
	
	public String getAccessT()
	{
		return accessT;
	}
	
	public void setAccessT(String accessT)
	{
		this.accessT= accessT;
	}
	
	public String getDelButton()
	{
		return delButton;
	}
	
	public void setDelButton(String delButton)
	{
		this.delButton= delButton;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description= description;
	}
	
    public ExpenseLocations() {
    }
    
    public void setAgency(String agency)
    {
    	this.agency = agency;
    }
    public String getAgency()
    {
    	return agency;
    }

    public void setDepartment(String department)
    {
    	this.department = department;
    }
    public String getDepartment()
    {
    	return department;
    }
    
    public ExpenseLocations(Integer elocIdentifier) {
        this.elocIdentifier = elocIdentifier;
    }

    public ExpenseLocations(Integer elocIdentifier, String city, String stProv) {
        this.elocIdentifier = elocIdentifier;
        this.city = city;
        this.stProv = stProv;
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

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (elocIdentifier != null ? elocIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExpenseLocations)) {
            return false;
        }
        ExpenseLocations other = (ExpenseLocations) object;
        if ((this.elocIdentifier == null && other.elocIdentifier != null) || (this.elocIdentifier != null && !this.elocIdentifier.equals(other.elocIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.ExpenseLocations[elocIdentifier=" + elocIdentifier + "]";
    }
	public Collection<ExpenseLocations> getExpenseLocations() {
		return expenseLocations;
	}
	public void setExpenseLocations(Collection<ExpenseLocations> expenseLocations) {
		this.expenseLocations = expenseLocations;
	}
	public String getDisplay() {
		return getCity()+","+getStProv();
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getStateWide() {
		return this.stateWide;
	}
	public void setStateWide(String stateWide) {
		this.stateWide = stateWide;
	}
	public String getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
}
