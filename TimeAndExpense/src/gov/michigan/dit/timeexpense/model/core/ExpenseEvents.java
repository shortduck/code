package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Class representing an expense event.
 * 
 * @author chaudharym
 * @author GhoshS
 */
@Entity
@Table(name = "EXPENSE_EVENTS")

public class ExpenseEvents implements Serializable {
	private static final long serialVersionUID = -12968744550110695L;

	@Id
    @SequenceGenerator(name = "EXPENSE_EVENTS_GENERATOR", sequenceName = "EXPEV_IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPENSE_EVENTS_GENERATOR")
    @Column(name = "EXPEV_IDENTIFIER")
    private Integer expevIdentifier;
    
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;

    @Column(name = "APPT_IDENTIFIER")
    private Integer appointmentId;
    
    @Column(name = "EXP_CLONED_FROM")
    private Integer expClonedFrom;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expevIdentifier")
    @OrderBy ("revisionNumber ASC")
    private List<ExpenseMasters> expenseMastersCollection;
    
    @Version
	@Column(name = "VERSION")
	private Integer version;

    public ExpenseEvents() {
    }

    public ExpenseEvents(Integer expevIdentifier) {
        this.expevIdentifier = expevIdentifier;
    }

    public Integer getExpevIdentifier() {
        return expevIdentifier;
    }

    public void setExpevIdentifier(Integer expevIdentifier) {
        this.expevIdentifier = expevIdentifier;
    }

    public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
    
    public Integer getExpClonedFrom() {
		return expClonedFrom;
	}

	public void setExpClonedFrom(Integer expClonedFrom) {
		this.expClonedFrom = expClonedFrom;
	}

	public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public List<ExpenseMasters> getExpenseMastersCollection() {
        return expenseMastersCollection;
    }

    public void setExpenseMastersCollection(List<ExpenseMasters> expenseMastersCollection) {
        this.expenseMastersCollection = expenseMastersCollection;
    }

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (expevIdentifier != null ? expevIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExpenseEvents)) {
            return false;
        }
        ExpenseEvents other = (ExpenseEvents) object;
        if ((this.expevIdentifier == null && other.expevIdentifier != null) || (this.expevIdentifier != null && !this.expevIdentifier.equals(other.expevIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExpenseEvents[expevIdentifier=" + expevIdentifier + "]";
    }

}
