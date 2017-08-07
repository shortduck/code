package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

public class ExpenseSummaryDetailsBean implements Serializable {

	private static final long serialVersionUID = -8975114716242994489L;
	
	private String description;
	private double amount;
	
	public ExpenseSummaryDetailsBean(){
		
	}
	
	public ExpenseSummaryDetailsBean(double amount, String description) {
		super();
		this.amount = amount;
		this.description = description;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
