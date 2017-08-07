package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.VReceiptsRequiredReport;
import gov.michigan.dit.timeexpense.model.display.ErrorDisplayBean;
import gov.michigan.dit.timeexpense.service.ReceiptsRequiredReportDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * This is the Action class which handles the form submission for the receipts
 * required Search and the gets the response back and delegates it to the JSP.
 * 
 * @author lyong
 * 
 */
@SuppressWarnings("all")
public class ReceiptsRequiredReportAction extends AppointmentSearchAction implements ServletRequestAware {
	private static Logger logger = Logger.getLogger(ReceiptsRequiredReportAction.class);

	private Date expDateFrom;
	private Date expDateTo;
	private Date pmtDateFrom;
	private Date pmtDateTo;
	private String myEmployees;
	private int supervisorEid;
	private String auditType;
	private String exportType;
	private String expenseGreaterThan;
	private String expenseLessThan;
	private String travelExpense_store;
	private String expenseTypesInOutState_store;
	private String randomPercentage;
	private String specificExpense_store;

	private HttpServletRequest request;

	@Override
	public String execute() throws Exception {
		// If employee wishes to see his expense report, then dept, agency, tku
		// & empId is not asked. So get it.
		if (isEmployeeViewingSelfReport()) {
			if (!populateEmployeeInfo())
				return "failure";
		}
		// If manager viewing report for his employees
		else if ("on".equalsIgnoreCase(myEmployees)) {
			supervisorEid = (int) getSupervisorEid();

			// PS: Set dept, agency and tku to blank string as Crystal API fails
			// for NULL values!
			chosenDepartment = "";
			chosenAgency = "";
			chosenTku = "";
			empId = "";
		} else {
			chosenDepartment = extractCode(getChosenDepartment());
			chosenAgency = extractCode(getChosenAgency());
			chosenTku = extractCode(getChosenTku());
			empId = getEmpId();
		}

		expDateFrom = (getExpDateFrom());
		expDateTo = (getExpDateTo());
		pmtDateFrom = (getPmtDateFrom());
		pmtDateTo = (getPmtDateTo());
		auditType = (getAuditType());
		supervisorEid = (int) getSupervisorEid();

		// set variables in request

		UserProfile profile = super.getLoggedInUser();

		// set variables in request
		request.setAttribute("chosenDept", chosenDepartment);
		request.setAttribute("chosenAgency", chosenAgency);
		request.setAttribute("chosenTku", chosenTku);
		request.setAttribute("empId", empId);
		request.setAttribute("expDateFrom", expDateFrom);
		request.setAttribute("expDateTo", expDateTo);
		request.setAttribute("pmtDateFrom", pmtDateFrom);
		request.setAttribute("pmtDateTo", pmtDateTo);
		request.setAttribute("auditType", auditType);
		request.setAttribute("supervisorEid", supervisorEid);
		request.setAttribute("exportType", exportType);
		request.setAttribute("Show_X_Percent", Integer.parseInt(randomPercentage));

		return "success";
	}
	
	public String getPOJORepReceiptReq() {

		ReceiptsRequiredReportDSP recReq = new ReceiptsRequiredReportDSP(entityManager);
		if (isEmployeeViewingSelfReport()) {
			if (!populateEmployeeInfo())
				return "failure";
		}
		
		// If manager viewing report for his employees
		else if ("on".equalsIgnoreCase(myEmployees)) {
			supervisorEid = (int) getSupervisorEid();

			// PS: Set dept, agency and tku to blank string as Crystal API fails
			// for NULL values!
			chosenDepartment = "";
			chosenAgency = "";
			chosenTku = "";
			empId = "";
		} else {
			chosenDepartment = extractCode(getChosenDepartment());
			chosenAgency = extractCode(getChosenAgency());
			chosenTku = extractCode(getChosenTku());
			empId = getEmpId();
		}

		expDateFrom = (getExpDateFrom());
		expDateTo = (getExpDateTo());
		pmtDateFrom = (getPmtDateFrom());
		pmtDateTo = (getPmtDateTo());
		auditType = (getAuditType());
		supervisorEid = (int) getSupervisorEid();
		VReceiptsRequiredReport repObj = new VReceiptsRequiredReport();
		repObj.setAgency(chosenAgency);
		repObj.setDepartment(chosenDepartment);
		repObj.setTku(chosenTku);

		long dt_frm = expDateFrom.getTime();
		java.sql.Date dt = new java.sql.Date(dt_frm);

		repObj.setExpDateFrom(dt);
		long dt_to = expDateTo.getTime();
		java.sql.Date frm_dt = new java.sql.Date(dt_to);
		repObj.setExpDateTo(frm_dt);

		if (empId != null && !empId.trim().equals("")) {
			repObj.setEmpIdentifier(Long.parseLong(empId));
		}
		long py_dt = pmtDateFrom.getTime();
		java.sql.Date frm_pyDt = new java.sql.Date(py_dt);

		repObj.setPayAmtFrm(frm_pyDt);
		
		py_dt = pmtDateTo.getTime();
		java.sql.Date to_pyDt = new java.sql.Date(py_dt);

		repObj.setPayAmtTo(to_pyDt);
		
		if (expenseGreaterThan != null && !expenseGreaterThan.trim().equals("")) {
			BigDecimal amtGreater = new BigDecimal(expenseGreaterThan);
			repObj.setAmtGreater(amtGreater);
			request.setAttribute("expenseAmountFrom", repObj.getAmtGreater().doubleValue());
		} else {
			request.setAttribute("expenseAmountFrom", 0d);
		}
		if (expenseLessThan != null && !expenseLessThan.trim().equals("")) {
			BigDecimal amtLesser = new BigDecimal(expenseLessThan);

			repObj.setAmtLesser(amtLesser);
			request.setAttribute("expenseAmountTo", repObj.getAmtLesser().doubleValue());
		} else {
			request.setAttribute("expenseAmountTo", 0d);
		}
		
		if (supervisorEid > 0) {
			repObj.setSuperempid((long)supervisorEid);
		}

		repObj.setPreAuditReq(auditType);
		if (randomPercentage != null && !randomPercentage.trim().equals("")) {
			Float f_per = Float.valueOf(randomPercentage.trim()).floatValue();
			repObj.setRandPer(f_per);
			request.setAttribute("Show_X_Percent", Integer.parseInt(randomPercentage));
		} else {
			request.setAttribute("Show_X_Percent", 100);
		}
		
		repObj.setTravelInd(travelExpense_store);
		if (repObj.getTravelInd() != null) {
			request.setAttribute("travelInd", repObj.getTravelInd());
		} else {
			request.setAttribute("travelInd", "");
		}
			
		repObj.setTravelType(getExpenseTypesInOutState_store());
		if (repObj.getTravelType() != null) {
			request.setAttribute("expenseType", repObj.getTravelType());
		} else {
			request.setAttribute("expenseType", "");
		}
		repObj.setExpenseType(specificExpense_store);
		if (repObj.getExpenseType() != null) {
			request.setAttribute("specificExpenseType", repObj.getExpenseType());
		} else {
			request.setAttribute("specificExpenseType", "");
		}

		List<VReceiptsRequiredReport> reportReceiptsRequired = recReq.getReportReceiptsRequired(repObj);
		// set variables in request

		UserProfile profile = super.getLoggedInUser();
		// set variables in request
		request.setAttribute("chosenDept", chosenDepartment);
		request.setAttribute("chosenAgency", chosenAgency);
		request.setAttribute("chosenTku", chosenTku);
		request.setAttribute("empId", empId);
		request.setAttribute("expDateFrom", expDateFrom);
		request.setAttribute("expDateTo", expDateTo);
		request.setAttribute("pmtDateFrom", pmtDateFrom);
		request.setAttribute("pmtDateTo", pmtDateTo);
		request.setAttribute("auditType", auditType);
		request.setAttribute("supervisorEid", supervisorEid);
		request.setAttribute("exportType", exportType);
		
		request.setAttribute("receiptsRequiredReportResults", reportReceiptsRequired);

		// make call to DSP
		return "success";
	}
	
	public String getExpenseCriteria() throws Exception {
		
		return "success";
	}

	public String getMyEmployees() {
		return myEmployees;
	}

	public void setMyEmployees(String myEmployees) {
		this.myEmployees = myEmployees;
	}

	public int getSupervisorEid() {
		if (getMyEmployees() == null) {
			supervisorEid = 0;
		} else {
			UserProfile profile = super.getLoggedInUser();
			supervisorEid = (int) profile.getEmpIdentifier();
		}
		return supervisorEid;
	}

	public void setSupervisorEid(int supervisorEid) {
		this.supervisorEid = supervisorEid;
	}

	@Override
	public void validate() {
		boolean result = false;

		// validate dept, agency, tku if NOT mgr viewing report for his
		// employees
		if (!"on".equalsIgnoreCase(myEmployees)) {
			result = validateDepartment() && validateAgency() && validateTku() && validateEmpId();
		}

		StringBuilder errorMsg = new StringBuilder("");
		// validate dates
		validateExpenseAndPaymentDates(errorMsg);

		if (errorMsg.length() > 0) {
			addFieldError("errors", errorMsg.toString());
			ErrorDisplayBean errorBean = new ErrorDisplayBean();
			errorBean.setErrorMessage(errorMsg.toString());
			errorBean.setRedirectOption(false);
			setError(errorBean);
			if (logger.isDebugEnabled())
				logger.info("Validation failed. ERROR: " + errorMsg);
		}
	}

	private boolean validateExpenseAndPaymentDates(StringBuilder errorMsg) {
		return validateExpenseDates(errorMsg) && validatePaymentDates(errorMsg) && validateDatePresence(errorMsg);
	}

	private boolean validateExpenseDates(StringBuilder errorMsg) {
		if (expDateFrom == null && expDateTo != null) {
			errorMsg.append("Please select Expense 'From' date");
			return false;
		}
		if (expDateFrom != null && expDateTo == null) {
			errorMsg.append("Please select Expense 'To' date");
			return false;
		}
		if (expDateFrom != null && expDateTo != null && expDateTo.before(expDateFrom)) {
			errorMsg.append("Expense 'To' date cannot be less than 'From' date");
			return false;
		}

		return true;
	}

	private boolean validatePaymentDates(StringBuilder errorMsg) {
		if (pmtDateFrom == null && pmtDateTo != null) {
			errorMsg.append("Please select Payment 'From' date");
			return false;
		}
		if (pmtDateFrom != null && pmtDateTo == null) {
			errorMsg.append("Please select Payment 'To' date");
			return false;
		}
		if (pmtDateFrom != null && pmtDateTo != null && pmtDateTo.before(pmtDateFrom)) {
			errorMsg.append("Payment 'To' date cannot be less than 'From' date");
			return false;
		}

		return true;
	}

	private boolean validateDatePresence(StringBuilder errorMsg) {
		if (expDateFrom == null && expDateTo == null && pmtDateFrom == null && pmtDateTo == null) {
			errorMsg.append("Please Enter Payment or Expense Date Range");
			return false;
		}
		return true;
	}

	public Date getExpDateFrom() {
		if (expDateFrom == null) {
			expDateFrom = IConstants.defaultStartDate;
		}
		return expDateFrom;
	}

	public void setExpDateFrom(Date expDateFrom) {
		this.expDateFrom = expDateFrom;
	}

	public Date getExpDateTo() {
		if (expDateTo == null) {
			expDateTo = IConstants.defaultEndDate;
		}
		return expDateTo;
	}

	public void setExpDateTo(Date expDateTo) {
		this.expDateTo = expDateTo;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Date getPmtDateFrom() {
		if (pmtDateFrom == null) {
			pmtDateFrom = IConstants.defaultStartDate;
		}
		return pmtDateFrom;
	}

	public void setPmtDateFrom(Date pmtDateFrom) {
		this.pmtDateFrom = pmtDateFrom;
	}

	public Date getPmtDateTo() {
		if (pmtDateTo == null) {
			pmtDateTo = IConstants.defaultEndDate;
		}
		return pmtDateTo;
	}

	public void setPmtDateTo(Date pmtDateTo) {
		this.pmtDateTo = pmtDateTo;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getExpenseGreaterThan() {
		return expenseGreaterThan;
	}

	public void setExpenseGreaterThan(String expenseGreaterThan) {
		this.expenseGreaterThan = expenseGreaterThan;
	}

	public String getExpenseLessThan() {
		return expenseLessThan;
	}

	public void setExpenseLessThan(String expenseLessThan) {
		this.expenseLessThan = expenseLessThan;
	}

	public String getTravelExpense_store() {
		return travelExpense_store;
	}

	public void setTravelExpense_store(String travelNonTravelExpense) {
		this.travelExpense_store = travelNonTravelExpense;
	}

	public String getRandomPercentage() {
		return randomPercentage;
	}

	public void setRandomPercentage(String randomPercentage) {
		this.randomPercentage = randomPercentage;
	}

	public String getSpecificExpense_store() {
		return specificExpense_store;
	}

	public void setSpecificExpense_store(String expenseType) {
		this.specificExpense_store = expenseType;
	}

	public String getExpenseTypesInOutState_store() {
		return expenseTypesInOutState_store;
	}

	public void setExpenseTypesInOutState_store(String expenseTypeInOutState_store) {
		this.expenseTypesInOutState_store = expenseTypeInOutState_store;
	}
}
