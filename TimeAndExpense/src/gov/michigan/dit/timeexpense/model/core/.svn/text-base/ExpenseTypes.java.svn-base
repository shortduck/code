package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Core class for expense types.
 *
 * @author chaudharym 
 * @author GhoshS
 */
@Entity
@Table(name = "EXPENSE_TYPES")
public class ExpenseTypes implements Serializable {

	private static final long serialVersionUID = -7219294460538275978L;

	@Id
    @Column(name = "EXP_TYPE_CODE")
    private String expTypeCode;
    
    @Column(name = "START_DATE")
    private Date startDate;
    
    @Column(name = "END_DATE")
    private Date endDate;
    
    @Column(name = "DESCRIPTION")
    private String description;    
   
	@Column(name = "DISPLAY_ORDER")
    private short displayOrder;
    
    @Column(name = "CATEGORY_CODE")
    private String categoryCode;

    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expTypeCode")
    private List<ExpenseDetails> expenseDetails;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "expTypeCode")
    private List<ExpenseTypeRules> expenseTypeRules;

    public ExpenseTypes() {
    }

    public ExpenseTypes(String expTypeCode) {
        this.expTypeCode = expTypeCode;
    }

    public ExpenseTypes(String expTypeCode, String description) {
        this.expTypeCode = expTypeCode;
        this.description = description;
    }

    public String getExpTypeCode() {
        return expTypeCode;
    }

    public void setExpTypeCode(String expTypeCode) {
        this.expTypeCode = expTypeCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public short getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(short displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	 public String getCategoryCode() {
			return categoryCode;
		}

		public void setCategoryCode(String categoryCode) {
			this.categoryCode = categoryCode;
		}

	
	public List<ExpenseDetails> getExpenseDetails() {
		return expenseDetails;
	}

	public void setExpenseDetails(List<ExpenseDetails> expenseDetails) {
		this.expenseDetails = expenseDetails;
	}

	public List<ExpenseTypeRules> getExpenseTypeRules() {
		return expenseTypeRules;
	}

	public void setExpenseTypeRules(List<ExpenseTypeRules> expenseTypeRules) {
		this.expenseTypeRules = expenseTypeRules;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (expTypeCode != null ? expTypeCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ExpenseTypes)) {
            return false;
        }
        ExpenseTypes other = (ExpenseTypes) object;
        if ((this.expTypeCode == null && other.expTypeCode != null) || (this.expTypeCode != null && !this.expTypeCode.equals(other.expTypeCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExpenseTypes[expTypeCode=" + expTypeCode + "]";
    }
}
