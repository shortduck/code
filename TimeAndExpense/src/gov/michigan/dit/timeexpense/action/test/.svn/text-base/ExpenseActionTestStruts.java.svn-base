package gov.michigan.dit.timeexpense.action.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import org.apache.struts2.StrutsTestCase;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import org.junit.Test;


/*
 * Class to create Struts test cases.
 * @author BhuratA  
 * */

public class ExpenseActionTestStruts extends StrutsTestCase {

	private ExpenseDSP expenseDSP;

	private Map<String, Object> getExpenseDetailsJSON(String dExpenseDate) {

		Map<String, Object> jsonExpenseDetails = new HashMap<String, Object>();
		String sb = new String();

		sb = "[{\"lineItemNo\":1,\"expenseDate\":\"??/??/????\",\"expenseType\":\"0126\",\"expenseTypeDesc\":\"Lunch - In State Non Taxable\",\"expenseTypeStdRate\":7.25,\"amount\":\"5.00\",\"fromCity\":\"ACCRA\",\"toCity\":\"AMSTERDAM\",\"fromState\":\"MI\",\"toState\":\"MI\",\"roundTrip\":false,\"departTime\":\"\",\"returnTime\":\"\",\"miles\":0,\"vicinityMiles\":0,\"commonMiles\":false,\"comments\":\"\",\"expdId\":0}]";
		sb = sb.replace("??/??/????", dExpenseDate);

		jsonExpenseDetails.put("expenseDetailsJson", sb);

		return jsonExpenseDetails;
	}

	private static Map<String, Object> constructDummySession(String strLeftNav,
			int EmpID, int AppointmentId, String dExpenseDate,
			String DepartmentId, String AgencyId, String TKUId, String UserID,
			String appointmentDate, String appoinmentEnd) {
		Map<String, Object> sessionContext = new HashMap<String, Object>();

		// Set Left Navigation Module
		sessionContext.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, strLeftNav);

		UserSubject subject = new UserSubject();
		subject.setEmployeeId(EmpID);
		subject.setAppointmentId(AppointmentId);
		subject.setAppointmentStart(new Date(dExpenseDate));
		subject.setAppointmentDate(new Date(appointmentDate));
		subject.setAppointmentEnd(new Date(appoinmentEnd));
		subject.setDepartment(DepartmentId);
		subject.setAgency(AgencyId);
		subject.setTku(TKUId);

		UserProfile user = new UserProfile(UserID);
		user.setEmpIdentifier(EmpID);

		sessionContext.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		sessionContext.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, user);

		return sessionContext;
	}

	/*
	 * Method to create dummy Expense data.
	 */
	private Map<String, Object> prepareDummyExpense(String dExpenseDate,
			String strNatureOfBusiness, String strComments,
			String strTravelInd, boolean isOutOfStateInd) {

		Map<String, Object> mapExpenseMaster = new HashMap<String, Object>();

		mapExpenseMaster.put("expenseMaster.expDateFrom", dExpenseDate);
		mapExpenseMaster.put("expenseMaster.expDateTo", dExpenseDate);
		mapExpenseMaster.put("expenseMaster.natureOfBusiness",
				strNatureOfBusiness);
		mapExpenseMaster.put("expenseMaster.comments", strComments);
		mapExpenseMaster.put("expenseMaster.travelInd", strTravelInd);
		mapExpenseMaster.put("expenseMaster.outOfStateInd", Boolean
				.toString(isOutOfStateInd));

		return mapExpenseMaster;
	}

	public boolean addExpenseDetails(String dExpenseDate, Object expenseMaster,
			Object userSubject, Object user) throws Exception {

		ActionProxy proxyExpenseDetail = getActionProxy("/SaveExpenseDetails.action");
		ActionContext actionExpenseDetail = proxyExpenseDetail.getInvocation()
				.getInvocationContext();

		Map<String, Object> savedExpenseMaster = new HashMap<String, Object>();

		// 1.
		savedExpenseMaster.put("ExpenseSessionData", expenseMaster);
		// 2.
		savedExpenseMaster.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, user);

		actionExpenseDetail.setSession(savedExpenseMaster);

		// Setting request for the Expense Details
		actionExpenseDetail.setParameters(getExpenseDetailsJSON(dExpenseDate));

		String strReturn = proxyExpenseDetail.execute();

		savedExpenseMaster = actionExpenseDetail.getSession();

		boolean isSubmitExpenseSuccessful = submitExpense(savedExpenseMaster
				.get("ExpenseSessionData"), userSubject, user);

		return ((strReturn.equalsIgnoreCase(IConstants.SUCCESS)) && (isSubmitExpenseSuccessful));

	}

	private boolean submitExpense(Object expenseMaster, Object userSubject,
			Object user) throws Exception {
		ActionProxy proxySubmitExpense = getActionProxy("/AjaxSubmitExpense.action");
		ActionContext actionSubmitExpense = proxySubmitExpense.getInvocation()
				.getInvocationContext();
		Map<String, Object> mapSubmitExpense = new HashMap<String, Object>();

		// 1. Create User
		mapSubmitExpense.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, user);

		// 2. Expense Master
		mapSubmitExpense.put("ExpenseSessionData", expenseMaster);

		// 3.userSubject
		mapSubmitExpense.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME,
				userSubject);

		actionSubmitExpense.setSession(mapSubmitExpense);

		String returnSubmitExpense = proxySubmitExpense.execute();

		// Get the ExpenseMaster from the session, to get the latest
		// ExpenseMaster object.

		mapSubmitExpense = actionSubmitExpense.getSession();

		String returnApproveExpense = approveExpense(mapSubmitExpense
				.get("ExpenseSessionData"), userSubject);

		return (returnSubmitExpense == returnApproveExpense);
	}

	private String approveExpense(Object expenseMaster, Object userSubject)
			throws Exception {

		ActionProxy proxyApproveExpense = getActionProxy("/AjaxApproveExpense.action");
		ActionContext actionApproveExpense = proxyApproveExpense
				.getInvocation().getInvocationContext();
		Map<String, Object> mapSubmitExpense = new HashMap<String, Object>();

		// 1. Create User
		UserProfile user = new UserProfile("SMITHM6");
		user.setEmpIdentifier(1109253);
		mapSubmitExpense.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, user);

		// 2. Expense Master
		mapSubmitExpense.put("ExpenseSessionData", expenseMaster);

		// 3.userSubject
		mapSubmitExpense.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME,
				userSubject);

		// 4. MOdule
		mapSubmitExpense.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID,
				IConstants.EXPENSE_MANAGER);

		actionApproveExpense.setSession(mapSubmitExpense);

		String returnApproveExpense = proxyApproveExpense.execute();

		return returnApproveExpense;
	}

	@Test
	/*
	 * Test Case for a simple case resulting in Success Emp id: 1084649
	 */
	public void testEmployee_Success_Emp() throws Exception {

		String dExpenseDate = "02/16/2012";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		ActionContext actionContext = proxy.getInvocation()
				.getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1084649, 1086246, dExpenseDate,
				"7", "01", "703", "DARNELLE", "06/15/2008", "12/31/2222"));

		Map<String, Object> expenseMaster = prepareDummyExpense(dExpenseDate,
				"test AA - JUnit", "test AA - JUnit", "N", false);

		// Setting Expense Object via request
		actionContext.setParameters(expenseMaster);

		String retSaveExpense = proxy.execute();

		Map<String, Object> sessionExpenseMaster = actionContext.getSession();

		assertTrue(addExpenseDetails(dExpenseDate, sessionExpenseMaster
				.get("ExpenseSessionData"), sessionExpenseMaster
				.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME),
				sessionExpenseMaster
						.get(IConstants.USER_PROFILE_SESSION_KEY_NAME)) == retSaveExpense
				.equalsIgnoreCase(IConstants.SUCCESS));

	}

	@Test
	/*
	 * Test case for GB Employee Emp id: 1109253 Date: 03/15/2010 Module:
	 * Employee
	 */
	public void testEmployee_Invalid_GB_Emp() throws Exception {

		String dExpenseDate = "03/15/2010";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		com.opensymphony.xwork2.ActionContext actionContext = proxy
				.getInvocation().getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1109253, 1153537, dExpenseDate,
				"76", "01", "104", "REEVES", "03/20/2011", "07/14/2011"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test GB - JUnit", "test GB - JUnit", "N", false));

		String retSaveExpense = proxy.execute();
		assertTrue(retSaveExpense.equals("input"));
	}

	@Test
	/*
	 * Test case for AA Employee Emp id: 1084649 Date: 03/15/2010 Module:
	 * Employee
	 */
	public void testEmployee_Success_AA_Emp() throws Exception {

		String dExpenseDate = "03/15/2010";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		ActionContext actionContext = proxy.getInvocation()
				.getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1084649, 1086246, dExpenseDate,
				"7", "01", "703", "DARNELLE", "06/15/2008", "12/31/2222"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test AA - JUnit", "test AA - JUnit", "N", false));

		String retSaveExpense = proxy.execute();

		Map<String, Object> sessionExpenseMaster = actionContext.getSession();

		assertTrue(addExpenseDetails(dExpenseDate, sessionExpenseMaster
				.get("ExpenseSessionData"), sessionExpenseMaster
				.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME),
				sessionExpenseMaster
						.get(IConstants.USER_PROFILE_SESSION_KEY_NAME)) == retSaveExpense
				.equalsIgnoreCase(IConstants.SUCCESS));

	}

	@Test
	/*
	 * Test case for True Multiple Employee Emp id: 1068286 Date: 03/15/2010
	 * Module: Employee
	 */
	public void testEmployee_True_Multiple_Emp() throws Exception {

		String dExpenseDate = "03/15/2010";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		ActionContext actionContext = proxy.getInvocation()
				.getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1068286, 1115616, dExpenseDate,
				"47", "63", "7", "LAFOILLT", "12/30/2007", "12/31/2222"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"True Multiple Employee - JUnit",
				"True Multiple Employee - JUnit", "N", false));

		String retSaveExpense = proxy.execute();
		assertTrue(retSaveExpense.equals("input"));
	}

	@Test
	/*
	 * Test case for True Multiple Manager Emp id: 1068286 Date: 03/15/2010
	 * Module: Manager
	 */
	public void testEmployee_True_Multiple_Mgr() throws Exception {

		String dExpenseDate = "03/15/2010";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		ActionContext actionContext = proxy.getInvocation()
				.getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_MANAGER, 1068286, 1083241, dExpenseDate,
				"55", "1", "830", "SMITHM6", "12/30/2007", "12/31/2222"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"True Multiple Manager - JUnit",
				"True Multiple Manager - JUnit", "N", false));

		String retSaveExpense = proxy.execute();

		Map<String, Object> sessionExpenseMaster = actionContext.getSession();

		assertTrue(addExpenseDetails(dExpenseDate, sessionExpenseMaster
				.get("ExpenseSessionData"), sessionExpenseMaster
				.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME),
				sessionExpenseMaster
						.get(IConstants.USER_PROFILE_SESSION_KEY_NAME)) == retSaveExpense
				.equalsIgnoreCase(IConstants.SUCCESS));

	}

	@Test
	/*
	 * Test case for Save Expense Invalid Emp id: 1109253
	 */
	public void testEmployee_Valid_GB_Mgr() throws Exception {

		String dExpenseDate = "07/14/2011";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		ActionContext actionContext = proxy.getInvocation()
				.getInvocationContext();

		// actionContext.setSession(constructDummySession(
		// IConstants.EXPENSE_MANAGER, 1109253, 1138053, dExpenseDate,
		// "76", "01", "104", "SMITHM6"));

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_MANAGER, 1109253, 1138053, dExpenseDate,
				"39", "01", "253", "SMITHM6", "03/20/2011", "07/14/2011"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test GB - JUnit Valid GB Manager",
				"test GB - JUnit Valid GB Manager", "N", false));

		String retSaveExpense = proxy.execute();

		Map<String, Object> sessionExpenseMaster = actionContext.getSession();

		assertTrue(addExpenseDetails(dExpenseDate, sessionExpenseMaster
				.get("ExpenseSessionData"), sessionExpenseMaster
				.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME),
				sessionExpenseMaster
						.get(IConstants.USER_PROFILE_SESSION_KEY_NAME)) == retSaveExpense
				.equalsIgnoreCase(IConstants.SUCCESS));

	}

	@Test
	/*
	 * Test case for Save Expense Invalid Emp id: 1094910/ZANDERT Date:
	 * 03/17/2010 Module: Employee
	 */
	public void testEmployee_Invalid_EA_Emp() throws Exception {

		String dExpenseDate = "03/17/2010";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		com.opensymphony.xwork2.ActionContext actionContext = proxy
				.getInvocation().getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1094910, 1151671, dExpenseDate,
				"43", "19", "731", "ZANDERT", "02/7/2010", "03/16/2010"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test EA - JUnit Invalid Employee",
				"test EA - JUnit Invalid Employee", "N", false));

		String retSaveExpense = proxy.execute();
		assertTrue(retSaveExpense.equals("input"));
	}

	@Test
	/*
	 * Test case for Save Expense Invalid Emp id: 1094910/ZANDERT Date:
	 * 05/14/2010 Module: Employee
	 */
	public void testEmployee_Valid_EA_Emp() throws Exception {

		String dExpenseDate = "05/14/2010";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		com.opensymphony.xwork2.ActionContext actionContext = proxy
				.getInvocation().getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1094910, 1114434, dExpenseDate,
				"43", "19", "731", "ZANDERT", "05/14/2010", "10/1/2010"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test EA - JUnit Valid EA Employee",
				"test EA - JUnit Valid EA Employee", "N", false));

		String retSaveExpense = proxy.execute();

		Map<String, Object> sessionExpenseMaster = actionContext.getSession();

		assertTrue(addExpenseDetails(dExpenseDate, sessionExpenseMaster
				.get("ExpenseSessionData"), sessionExpenseMaster
				.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME),
				sessionExpenseMaster
						.get(IConstants.USER_PROFILE_SESSION_KEY_NAME)) == retSaveExpense
				.equalsIgnoreCase(IConstants.SUCCESS));
	}

	@Test
	/*
	 * Test case for Save Expense Invalid Emp id: 1094910 Date: 03/17/2010
	 * Module: Manager
	 */
	public void testEmployee_Invalid_EA_Mgr() throws Exception {

		String dExpenseDate = "03/17/2010";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		ActionContext actionContext = proxy.getInvocation()
				.getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_MANAGER, 1094910, 1114434, dExpenseDate,
				"43", "19", "731", "SMITHM6", "02/7/2010", "03/16/2010"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test EA - JUnit Invalid Manager",
				"test EA - JUnit Invalid Manager", "N", false));

		String retSaveExpense = proxy.execute();
		assertTrue(retSaveExpense.equals("input"));
	}

	@Test
	/*
	 * Test case for Save Expense valid Emp id: 1094910/ZANDERT Date: 05/14/2010
	 * Module: Manager
	 */
	public void testEmployee_Valid_EA_Mgr() throws Exception {

		String dExpenseDate = "05/14/2010";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		ActionContext actionContext = proxy.getInvocation()
				.getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_MANAGER, 1094910, 1114434, dExpenseDate,
				"43", "19", "731", "SMITHM6", "05/14/2010", "10/1/2010"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test EA - JUnit Valid Manager",
				"test EA - JUnit Valid Manager", "N", false));

		String retSaveExpense = proxy.execute();

		Map<String, Object> sessionExpenseMaster = actionContext.getSession();

		assertTrue(addExpenseDetails(dExpenseDate, sessionExpenseMaster
				.get("ExpenseSessionData"), sessionExpenseMaster
				.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME),
				sessionExpenseMaster
						.get(IConstants.USER_PROFILE_SESSION_KEY_NAME)) == retSaveExpense
				.equalsIgnoreCase(IConstants.SUCCESS));

	}

	@Test
	/*
	 * Test case for ZA Valid Employee Emp id: 1112663/WALKERC16 Date:
	 * 03/10/2012 Module: Employee
	 */
	public void testEmployee_Valid_ZA_Emp() throws Exception {

		String dExpenseDate = "03/10/2012";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		ActionContext actionContext = proxy.getInvocation()
				.getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1112663, 1150483, dExpenseDate,
				"64", "01", "015", "WALKERC16", "8/29/2011", "12/31/2222"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test ZA - JUnit Valid Employee",
				"test ZA - JUnit Valid Employee", "Y", false));

		String retSaveExpense = proxy.execute();

		Map<String, Object> sessionExpenseMaster = actionContext.getSession();

		assertTrue(addExpenseDetails(dExpenseDate, sessionExpenseMaster
				.get("ExpenseSessionData"), sessionExpenseMaster
				.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME),
				sessionExpenseMaster
						.get(IConstants.USER_PROFILE_SESSION_KEY_NAME)) == retSaveExpense
				.equalsIgnoreCase(IConstants.SUCCESS));
	}

	@Test
	/*
	 * Test case for ZA Valid manager Emp id: 1112663 Date: 03/10/2012 Module:
	 * Manager
	 */
	public void testEmployee_Valid_ZA_Mgr() throws Exception {

		String dExpenseDate = "03/10/2012";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		ActionContext actionContext = proxy.getInvocation()
				.getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_MANAGER, 1112663, 1150483, dExpenseDate,
				"64", "01", "015", "SMITHM6", "8/29/2011", "12/31/2222"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test ZA - JUnit - Valid Manager",
				"test ZA - JUnit - Valid Manager", "Y", false));

		String retSaveExpense = proxy.execute();

		Map<String, Object> sessionExpenseMaster = actionContext.getSession();

		assertTrue(addExpenseDetails(dExpenseDate, sessionExpenseMaster
				.get("ExpenseSessionData"), sessionExpenseMaster
				.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME),
				sessionExpenseMaster
						.get(IConstants.USER_PROFILE_SESSION_KEY_NAME)) == retSaveExpense
				.equalsIgnoreCase(IConstants.SUCCESS));
	}

	@Test
	/*
	 * Test case for ZA Invalid manager Emp id: 1112663 Date: 08/28/2011 Module:
	 * Manager
	 */
	public void testEmployee_Invalid_ZA_Mgr() throws Exception {

		String dExpenseDate = "08/28/2011";

		ActionProxy proxy = getActionProxy("/SaveExpense.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		ActionContext actionContext = proxy.getInvocation()
				.getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_MANAGER, 1112663, 1150483, dExpenseDate,
				"64", "01", "015", "SMITHM6", "8/29/2011", "12/31/2222"));

		// get Expense related Items
		actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test ZA - JUnit - Invalid Manager",
				"test ZA - JUnit - Invalid Manager", "Y", false));

		String retSaveExpense = proxy.execute();

		assertTrue(retSaveExpense.equalsIgnoreCase("input"));
	}

}
