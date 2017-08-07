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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author GhoshS
 */
@Entity
@Table(name = "EXPENSE_PROFILES")

public class ExpenseProfiles implements Serializable {

	private static final long serialVersionUID = 4625302242384795609L;

	@Id
	@Column(name = "EXPF_IDENTIFIER")
	private Integer expfIdentifier;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "MODIFIED_USER_ID")
	private String modifiedUserId;

	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name = "OLD_APPT_IDENTIFIER")
	private Integer oldApptIdentifier;

	//@JoinColumn(name = "APPT_IDENTIFIER", referencedColumnName = "APPT_IDENTIFIER")
	//@ManyToOne(optional = false)
	@Column(name = "APPT_IDENTIFIER")
	private int appointmentId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "expfIdentifier")
	private List<ExpenseProfileRules> expenseProfileRulesCollection;

	public ExpenseProfiles() {
	}

	
	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}



	public ExpenseProfiles(Integer expfIdentifier) {
		this.expfIdentifier = expfIdentifier;
	}

	public ExpenseProfiles(Integer expfIdentifier, Date startDate, Date endDate) {
		this.expfIdentifier = expfIdentifier;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Integer getExpfIdentifier() {
		return expfIdentifier;
	}

	public void setExpfIdentifier(Integer expfIdentifier) {
		this.expfIdentifier = expfIdentifier;
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

	public String getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getOldApptIdentifier() {
		return oldApptIdentifier;
	}

	public void setOldApptIdentifier(int oldApptIdentifier) {
		this.oldApptIdentifier = oldApptIdentifier;
	}

	public List<ExpenseProfileRules> getExpenseProfileRulesCollection() {
		return expenseProfileRulesCollection;
	}

	public void setExpenseProfileRulesCollection(
			List<ExpenseProfileRules> expenseProfileRulesCollection) {
		this.expenseProfileRulesCollection = expenseProfileRulesCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (expfIdentifier != null ? expfIdentifier.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof ExpenseProfiles)) {
			return false;
		}
		ExpenseProfiles other = (ExpenseProfiles) object;
		if ((this.expfIdentifier == null && other.expfIdentifier != null)
				|| (this.expfIdentifier != null && !this.expfIdentifier
						.equals(other.expfIdentifier))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.michigan.dit.timeexpense.model.db.ExpenseProfiles[expfIdentifier="
				+ expfIdentifier + "]";
	}

}
