package gov.michigan.dit.timeexpense.model.display;

/**
 * Class to capture full coding block information and some expense info.
 * 
 * @author chaudharym
 */
public class ExpenseDetailAndCodingBlockBean {

	private String appropriationYear;
	private String indexCode;
	private String pca;
	private String grantNumber;
	private String grantPhase;
	private String agencyCode1;
	private String projectNumber;
	private String projectPhase;
	private String agencyCode2;
	private String agencyCode3;
	private String multipurposeCode;
	private boolean standardInd;

	private String expenseTypeDesc;
	private double expenseAmount;
	private String expenseDate;
	private int displaySequenceNumber;
	
	public ExpenseDetailAndCodingBlockBean() {
		super();
	}

	public ExpenseDetailAndCodingBlockBean(String expenseDate,
			String expenseTypeDesc, double expenseAmount,
			String appropriationYear, String indexCode, String pca,
			String grantNumber, String grantPhase, String agencyCode1,
			String projectNumber, String projectPhase, String agencyCode2,
			String agencyCode3, String multipurposeCode, boolean standardInd) {
		super();
		this.expenseDate = expenseDate;
		this.expenseTypeDesc = expenseTypeDesc;
		this.expenseAmount = expenseAmount;
		this.appropriationYear = appropriationYear == null ? "" : appropriationYear;
		this.indexCode = indexCode;
		this.pca = pca;
		this.grantNumber = grantNumber;
		this.grantPhase = grantPhase;
		this.agencyCode1 = agencyCode1;
		this.projectNumber = projectNumber;
		this.projectPhase = projectPhase;
		this.agencyCode2 = agencyCode2;
		this.agencyCode3 = agencyCode3;
		this.multipurposeCode = multipurposeCode;
		this.standardInd = standardInd;
	}

	public String getAppropriationYear() {
		return appropriationYear;
	}

	public void setAppropriationYear(String appropriationYear) {
		this.appropriationYear = appropriationYear;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getPca() {
		return pca;
	}

	public void setPca(String pca) {
		this.pca = pca;
	}

	public String getGrantNumber() {
		return grantNumber;
	}

	public void setGrantNumber(String grantNumber) {
		this.grantNumber = grantNumber;
	}

	public String getGrantPhase() {
		return grantPhase;
	}

	public void setGrantPhase(String grantPhase) {
		this.grantPhase = grantPhase;
	}

	public String getAgencyCode1() {
		return agencyCode1;
	}

	public void setAgencyCode1(String agencyCode1) {
		this.agencyCode1 = agencyCode1;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getProjectPhase() {
		return projectPhase;
	}

	public void setProjectPhase(String projectPhase) {
		this.projectPhase = projectPhase;
	}

	public String getAgencyCode2() {
		return agencyCode2;
	}

	public void setAgencyCode2(String agencyCode2) {
		this.agencyCode2 = agencyCode2;
	}

	public String getAgencyCode3() {
		return agencyCode3;
	}

	public void setAgencyCode3(String agencyCode3) {
		this.agencyCode3 = agencyCode3;
	}

	public String getMultipurposeCode() {
		return multipurposeCode;
	}

	public void setMultipurposeCode(String multipurposeCode) {
		this.multipurposeCode = multipurposeCode;
	}

	public boolean isStandardInd() {
		return standardInd;
	}

	public void setStandardInd(boolean standardInd) {
		this.standardInd = standardInd;
	}

	public String getExpenseTypeDesc() {
		return expenseTypeDesc;
	}

	public void setExpenseTypeDesc(String expenseTypeDesc) {
		this.expenseTypeDesc = expenseTypeDesc;
	}

	public double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public String getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(String expenseDate) {
		this.expenseDate = expenseDate;
	}

	public int getDisplaySequenceNumber() {
		return displaySequenceNumber;
	}

	public void setDisplaySequenceNumber(int displaySequenceNumber) {
		this.displaySequenceNumber = displaySequenceNumber;
	}
	
}
