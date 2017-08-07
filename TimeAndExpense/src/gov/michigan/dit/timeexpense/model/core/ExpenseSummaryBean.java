package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;

/**
 * 
 * @author GhoshS
 */

public class ExpenseSummaryBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private int lineNo;

	private String expenseTypeCode;

	private double expenseRate;

	private String expenseTypeDesc; 
	
	private int expenseRuleId;

	private String expenseTypeRuleValue;

	private String expenseRuleType;

	private String ruleDesc;

	private double expenseAmount;
	
	private int displayOrder;
	
	public ExpenseSummaryBean() {
	}
	
	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public String getExpenseTypeCode() {
		return expenseTypeCode;
	}

	public void setExpenseTypeCode(String expenseTypeCode) {
		this.expenseTypeCode = expenseTypeCode;
	}

	public double getExpenseRate() {
		return expenseRate;
	}

	public void setExpenseRate(double expenseRate) {
		this.expenseRate = expenseRate;
	}

	public String getExpenseTypeDesc() {
		return expenseTypeDesc;
	}

	public void setExpenseTypeDesc(String expenseTypeDesc) {
		this.expenseTypeDesc = expenseTypeDesc;
	}

	public int getExpenseRuleId() {
		return expenseRuleId;
	}

	public void setExpenseRuleId(int expenseRuleId) {
		this.expenseRuleId = expenseRuleId;
	}

	public String getExpenseTypeRuleValue() {
		return expenseTypeRuleValue;
	}

	public void setExpenseTypeRuleValue(String expenseTypeRuleValue) {
		this.expenseTypeRuleValue = expenseTypeRuleValue;
	}

	public String getExpenseRuleType() {
		return expenseRuleType;
	}

	public void setExpenseRuleType(String expenseRuleType) {
		this.expenseRuleType = expenseRuleType;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}
