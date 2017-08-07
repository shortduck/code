package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;
import java.util.Date;

/**
 * Class to encapsulate the data for the approval transaction listing.
 *
 * @author GhoshS
 */
public class ExpenseApprovalTransactionBean implements Serializable {

	private static final long serialVersionUID = -7167046611623040130L;

	private String name;
	private long empIdentifier;
	private long expevIdentifier;
	private Date fromDate;
	private Date toDate;
	private Long adjIdentifier;
	private double Amount;
	


	public ExpenseApprovalTransactionBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ExpenseApprovalTransactionBean(Long adjIdentifier, long empIdentifier,
			Date fromDate, Date expDateTo, long expmIdentifier, String name) {
		super();
		this.adjIdentifier = adjIdentifier;
		this.empIdentifier = empIdentifier;
		this.fromDate = fromDate;
		this.toDate = expDateTo;
		this.expevIdentifier = expmIdentifier;
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getEmpIdentifier() {
		return empIdentifier;
	}


	public void setEmpIdentifier(long empIdentifier) {
		this.empIdentifier = empIdentifier;
	}


	public long getExpevIdentifier() {
		return expevIdentifier;
	}


	public void setExpevIdentifier(long expevIdentifier) {
		this.expevIdentifier = expevIdentifier;
	}


	public Date getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public Date getToDate() {
		return toDate;
	}


	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	public Long getAdjIdentifier() {
		return adjIdentifier;
	}


	public void setAdjIdentifier(Long adjIdentifier) {
		this.adjIdentifier = adjIdentifier;
	}


	public double getAmount() {
		return Amount;
	}


	public void setAmount(double amount) {
		Amount = amount;
	}
	
}
