package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.openjpa.persistence.ElementDependent;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "EXPENSE_ACTIONS")

public class ExpenseActions implements Serializable {
    private static final long serialVersionUID = 1L;
    
	@SequenceGenerator(name = "EXPENSE_ACTIONS_GENERATOR", sequenceName = "EXPA_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPENSE_ACTIONS_GENERATOR")
    @Column(name = "EXPA_IDENTIFIER")
    private Integer expaIdentifier;
    
	@Column(name = "NEXT_ACTION_CODE")
    private String nextActionCode;
    
	@Column(name = "COMMENTS")
    private String comments;

    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;

    @Column(name = "ACTION_CODE")
    private String actionCode;

    @JoinColumn(name = "EXPM_IDENTIFIER", referencedColumnName = "EXPM_IDENTIFIER")
    @ManyToOne(optional = false)
    @ElementDependent
    private ExpenseMasters expmIdentifier;

    @Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
    
    @Version
	@Column(name = "VERSION")
	private Integer version;
	    
    public ExpenseActions() {
    }

    public ExpenseActions(Integer expaIdentifier) {
        this.expaIdentifier = expaIdentifier;
    }

    /**
     * Copy constructor
     * @param old expense action
     */
    public ExpenseActions(ExpenseActions _old) {
        this.actionCode = _old.getActionCode();
    	this.nextActionCode = _old.getNextActionCode();
        this.expmIdentifier = _old.getExpmIdentifier();
        this.comments = _old.getComments();
        this.modifiedUserId = _old.getModifiedUserId();
        this.modifiedDate = new Date();
    }
    
    public Integer getExpaIdentifier() {
        return expaIdentifier;
    }

    public void setExpaIdentifier(Integer expaIdentifier) {
        this.expaIdentifier = expaIdentifier;
    }

    public String getNextActionCode() {
        return nextActionCode;
    }

    public void setNextActionCode(String nextActionCode) {
        this.nextActionCode = nextActionCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public ExpenseMasters getExpmIdentifier() {
        return expmIdentifier;
    }

    public void setExpmIdentifier(ExpenseMasters expmIdentifier) {
        this.expmIdentifier = expmIdentifier;
    }

    public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (expaIdentifier != null ? expaIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExpenseActions)) {
            return false;
        }
        ExpenseActions other = (ExpenseActions) object;
        if ((this.expaIdentifier == null && other.expaIdentifier != null) || (this.expaIdentifier != null && !this.expaIdentifier.equals(other.expaIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.ExpenseActions[expaIdentifier=" + expaIdentifier + "]";
    }
}
