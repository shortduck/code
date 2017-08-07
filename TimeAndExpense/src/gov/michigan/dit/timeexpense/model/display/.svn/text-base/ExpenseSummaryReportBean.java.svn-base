package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;
import java.util.List;

/**
 * Class to hold all data for the expense summary report.
 * 
 * @author chaudharym
 */
public class ExpenseSummaryReportBean implements Serializable {
	private static final long serialVersionUID = 1229370285137564143L;

	private int expenseEventId; 
	private int employeeId; 
	private String employeeName; 
	private String employeeProcessLevel;
	private String expenseDateFrom; 
	private String expenseDateTo; 
	private String employeeDeptCodeAndName; 
	private String expenseBizNature;
	private String bargUnit;//AI-29299 Added bargaining unit
	
	// First 127 chars of description
	private String expenseEventDesc1;
	// 127-255 chars of description
	private String expenseEventDesc2;
	
	private int expenseRevision; 
	private double nonTaxableExpense; 
	private double taxableExpense; 
	private double amountLiquidated; 
	private double outstandingAdvance;
	private String tkuCodeAndName;
	
	private List<ExpenseSummaryDetailsBean> details;
	private List<CodingBlockSummaryBean> codingBlocks;
	
	public ExpenseSummaryReportBean(){}
	
	public ExpenseSummaryReportBean(int expenseEventId, int employeeId, String employeeName,
			 String processLevel,String bargUnit, String expenseDateFrom, String expenseDateTo, String deptCodeAndName,
			 String expenseBizNature, String expenseEventDesc1, String expenseEventDesc2, int expenseRevision,
			 double nonTaxableExpense, double taxableExpense, double advanceLiquidated, double outstandingAdvance){
		this.expenseEventId = expenseEventId; 
		this.employeeId = employeeId; 
		this.employeeName = employeeName; 
		this.employeeProcessLevel = processLevel;
		this.bargUnit=bargUnit;//AI-29299 Added bargaining unit
		this.expenseDateFrom = expenseDateFrom; 
		this.expenseDateTo = expenseDateTo; 
		this.employeeDeptCodeAndName = deptCodeAndName; 
		this.expenseBizNature = expenseBizNature; 
		this.expenseEventDesc1 = expenseEventDesc1; 
		this.expenseEventDesc2 = expenseEventDesc2;
		this.expenseRevision = expenseRevision; 
		this.nonTaxableExpense = nonTaxableExpense; 
		this.taxableExpense = taxableExpense; 
		this.amountLiquidated = advanceLiquidated; 
		this.outstandingAdvance = outstandingAdvance;
	}
	
	public int getExpenseEventId() {
		return expenseEventId;
	}
	public void setExpenseEventId(int expenseEventId) {
		this.expenseEventId = expenseEventId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeProcessLevel() {
		return employeeProcessLevel;
	}
	public void setEmployeeProcessLevel(String processLevel) {
		this.employeeProcessLevel = processLevel;
	}
	public String getExpenseDateFrom() {
		return expenseDateFrom;
	}
	public void setExpenseDateFrom(String expenseDateFrom) {
		this.expenseDateFrom = expenseDateFrom;
	}
	public String getExpenseDateTo() {
		return expenseDateTo;
	}
	public void setExpenseDateTo(String expenseDateTo) {
		this.expenseDateTo = expenseDateTo;
	}
	public String getEmployeeDeptCodeAndName() {
		return employeeDeptCodeAndName;
	}
	public void setEmployeeDeptCodeAndName(String deptCodeAndName) {
		this.employeeDeptCodeAndName = deptCodeAndName;
	}
	public String getExpenseBizNature() {
		return expenseBizNature;
	}
	public void setExpenseBizNature(String expenseBizNature) {
		this.expenseBizNature = expenseBizNature;
	}
	public String getExpenseEventDesc1() {
		return expenseEventDesc1;
	}
	public void setExpenseEventDesc1(String expenseEventDesc1) {
		this.expenseEventDesc1 = expenseEventDesc1;
	}
	public String getExpenseEventDesc2() {
		return expenseEventDesc2;
	}
	public void setExpenseEventDesc2(String expenseEventDesc2) {
		this.expenseEventDesc2 = expenseEventDesc2;
	}	
	public int getExpenseRevision() {
		return expenseRevision;
	}
	public void setExpenseRevision(int expenseRevision) {
		this.expenseRevision = expenseRevision;
	}
	public double getNonTaxableExpense() {
		return nonTaxableExpense;
	}
	public void setNonTaxableExpense(double nonTaxableExpense) {
		this.nonTaxableExpense = nonTaxableExpense;
	}
	public double getTaxableExpense() {
		return taxableExpense;
	}
	public void setTaxableExpense(double taxableExpense) {
		this.taxableExpense = taxableExpense;
	}
	public double getAmountLiquidated() {
		return amountLiquidated;
	}
	public void setAmountLiquidated(double advanceLiquidated) {
		this.amountLiquidated = advanceLiquidated;
	}
	public double getOutstandingAdvance() {
		return outstandingAdvance;
	}
	public void setOutstandingAdvance(double outstandingAdvance) {
		this.outstandingAdvance = outstandingAdvance;
	}
	public String getTkuCodeAndName() {
		return tkuCodeAndName;
	}
	public void setTkuCodeAndName(String tkuCodeAndName) {
		this.tkuCodeAndName = tkuCodeAndName;
	}
	public List<ExpenseSummaryDetailsBean> getDetails() {
		return details;
	}
	public void setDetails(List<ExpenseSummaryDetailsBean> details) {
		this.details = details;
	}
	public List<CodingBlockSummaryBean> getCodingBlocks() {
		return codingBlocks;
	}
	public void setCodingBlocks(List<CodingBlockSummaryBean> codingBlocks) {
		this.codingBlocks = codingBlocks;
	}
	//AI-29299 Added bargaining unit
	public String getBargUnit() {
		return bargUnit;
	}
	//AI-29299 Added bargaining unit
	public void setBargUnit(String bargUnit) {
		this.bargUnit = bargUnit;
	}
	
}
