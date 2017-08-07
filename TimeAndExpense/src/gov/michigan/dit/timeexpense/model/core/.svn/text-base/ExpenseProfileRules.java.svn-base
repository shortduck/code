package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author GhoshS
 */
@Entity
@Table(name = "EXPENSE_PROFILE_RULES")

public class ExpenseProfileRules implements Serializable {

	private static final long serialVersionUID = -966634102517540140L;

	@Id
	@Column(name = "EXPFR_IDENTIFIER")
	private Integer expfrIdentifier;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name = "MODIFIED_USER_ID")
	private String modifiedUserId;
	
	@JoinColumn(name = "EXPF_IDENTIFIER", referencedColumnName = "EXPF_IDENTIFIER")
	@ManyToOne(optional = false)
	private ExpenseProfiles expfIdentifier;
	
	//@JoinColumn(name = "RULE_IDENTIFIER", referencedColumnName = "RULE_IDENTIFIER")
	//@ManyToOne(optional = false)
	@Column(name = "RULE_IDENTIFIER")
	private int ruleIdentifier;

	public ExpenseProfileRules() {
	}

	public ExpenseProfileRules(Integer expfrIdentifier) {
		this.expfrIdentifier = expfrIdentifier;
	}

	public ExpenseProfileRules(Integer expfrIdentifier, String value) {
		this.expfrIdentifier = expfrIdentifier;
		this.value = value;
	}

	public Integer getExpfrIdentifier() {
		return expfrIdentifier;
	}

	public void setExpfrIdentifier(Integer expfrIdentifier) {
		this.expfrIdentifier = expfrIdentifier;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public ExpenseProfiles getExpfIdentifier() {
		return expfIdentifier;
	}

	public void setExpfIdentifier(ExpenseProfiles expfIdentifier) {
		this.expfIdentifier = expfIdentifier;
	}

	public int getRuleIdentifier() {
		return ruleIdentifier;
	}

	public void setRuleIdentifier(int ruleIdentifier) {
		this.ruleIdentifier = ruleIdentifier;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (expfrIdentifier != null ? expfrIdentifier.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof ExpenseProfileRules)) {
			return false;
		}
		ExpenseProfileRules other = (ExpenseProfileRules) object;
		if ((this.expfrIdentifier == null && other.expfrIdentifier != null)
				|| (this.expfrIdentifier != null && !this.expfrIdentifier
						.equals(other.expfrIdentifier))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.michigan.dit.timeexpense.model.ExpenseProfileRules[expfrIdentifier="
				+ expfrIdentifier + "]";
	}

}
