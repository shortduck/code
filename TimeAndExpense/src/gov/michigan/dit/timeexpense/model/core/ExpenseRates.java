package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author GhoshS
 */
@Entity
@Table(name = "EXPENSE_RATES")

public class ExpenseRates implements Serializable {

	private static final long serialVersionUID = 9134157574831629279L;

	@Id
	@Column(name = "EXPR_IDENTIFIER")
	private Integer exprIdentifier;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "END_DATE")
	private Date endDate;

	@Column(name = "RATE_AMT")
	private double rateAmt;

	@Column(name = "EXP_TYPE_CODE")
	private String expTypeCode;

	public ExpenseRates() {
	}

	public ExpenseRates(Integer exprIdentifier, Date startDate, Date endDate,
			double rateAmt) {
		this.exprIdentifier = exprIdentifier;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rateAmt = rateAmt;
	}

	public Integer getExprIdentifier() {
		return exprIdentifier;
	}

	public void setExprIdentifier(Integer exprIdentifier) {
		this.exprIdentifier = exprIdentifier;
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

	public double getRateAmt() {
		return rateAmt;
	}

	public void setRateAmt(double rateAmt) {
		this.rateAmt = rateAmt;
	}

	public String getExpTypeCode() {
		return expTypeCode;
	}

	public void setExpTypeCode(String expTypeCode) {
		this.expTypeCode = expTypeCode;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (exprIdentifier != null ? exprIdentifier.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof ExpenseRates)) {
			return false;
		}
		ExpenseRates other = (ExpenseRates) object;
		if ((this.exprIdentifier == null && other.exprIdentifier != null)
				|| (this.exprIdentifier != null && !this.exprIdentifier
						.equals(other.exprIdentifier))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.michigan.dit.timeexpense.model.ExpenseRates[exprIdentifier="
				+ exprIdentifier + "]";
	}

}
