package gov.michigan.dit.timeexpense.action.test;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.SystemCodesDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import org.apache.struts2.StrutsTestCase;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import org.junit.Test;

public class SystemCodesActionTest extends StrutsTestCase{
	
	
	private SystemCodesDSP sysCodeDsp;
	
	public Date getCurrentDateTs() throws Exception
	{
		Date date = new Date();
		 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		 DateFormat formatter ;
		 formatter = new SimpleDateFormat("MM/dd/yyyy");
		 String retDate = dateFormat.format(date).toString(); 
		 date = (Date)formatter.parse(retDate); 
		 
		 Date date1 = new Date();
		 date1 = (Date)formatter.parse(dateFormat.format(date)); 
		 return date1;
	}
	
	@Test
	public void foo() {
		int i = 10;
	}
	/**
	 * test for missing attributes in create System Code
	 * @throws Exception
	 */
	@Test	
	public void testModifyCode() throws Exception {
		ActionProxy proxy = getActionProxy("/SaveNewSystemCode.action");
		String dExpenseDate = "02/16/2012";
		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		com.opensymphony.xwork2.ActionContext actionContext = proxy
				.getInvocation().getInvocationContext();
		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1084649, 1086246, dExpenseDate,
				"7", "01", "703", "DARNELLE", "06/15/2008", "12/31/2222"));

		Map<String, Object> nsystemCode =prepareDummySysCode(
				" ", getCurrentdate(),getCurrentdate(),"sdfsdf","sdfsdf","user",getCurrentdate());
		actionContext.setParameters(nsystemCode);
	 

		// get Expense related Items
	/*	actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test GB - JUnit", "test GB - JUnit", "N", false));
*/
		String retSaveExpense = proxy.execute();
		assertTrue(retSaveExpense.equals(IConstants.SUCCESS));
	}
	/**
	 * Test for missing attributes for update
	 * @throws Exception
	 */
	@Test	
	public void testDebugUpdateCode() throws Exception {
		ActionProxy proxy = getActionProxy("/SaveSystemCode.action");
		String dExpenseDate = "02/16/2012";
		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		com.opensymphony.xwork2.ActionContext actionContext = proxy
				.getInvocation().getInvocationContext();
		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1084649, 1086246, dExpenseDate,
				"7", "01", "703", "DARNELLE", "06/15/2008", "12/31/2222"));

		Map<String, Object> nsystemCode =prepareDummySysCode(
				"8900", getCurrentdate(),getCurrentdate()," ","sdfsdf","user",getCurrentdate());
		actionContext.setParameters(nsystemCode);
	 

		// get Expense related Items
	/*	actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test GB - JUnit", "test GB - JUnit", "N", false));
*/
		String retSaveExpense = proxy.execute();
		assertTrue(retSaveExpense.equals(IConstants.SUCCESS));
	}
	
	/**
	 * Check for missing attribute on modify new SysteCode
	 * @throws Exception
	 */
	
	@Test	
	public void testUpdateCode() throws Exception {
		ActionProxy proxy = getActionProxy("/UpdateNewSystemCode.action");
		String dExpenseDate = "02/16/2012";
		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		com.opensymphony.xwork2.ActionContext actionContext = proxy
				.getInvocation().getInvocationContext();
		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1084649, 1086246, dExpenseDate,
				"7", "01", "703", "DARNELLE", "06/15/2008", "12/31/2222"));

		Map<String, Object> nsystemCode =prepareDummySysCode(
				"8900", getCurrentdate(),getCurrentdate()," ","sdfsdf","user",getCurrentdate());
		actionContext.setParameters(nsystemCode);
	 

		// get Expense related Items
	/*	actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test GB - JUnit", "test GB - JUnit", "N", false));
*/
		String retSaveExpense = proxy.execute();
		assertTrue(retSaveExpense.equals(IConstants.SUCCESS));
	}
	/**
	 * 
	 * @throws Exception
	 */
	@Test	
	public void testfindAllCode() throws Exception {
		ActionProxy proxy = getActionProxy("/AjaxfindSearchResults.action");
		String dExpenseDate = "02/16/2012";
		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		com.opensymphony.xwork2.ActionContext actionContext = proxy
				.getInvocation().getInvocationContext();
		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1084649, 1086246, dExpenseDate,
				"7", "01", "703", "DARNELLE", "06/15/2008", "12/31/2222"));

		Map<String, Object> searchCode =prepareDummySysCode(
				null);
		actionContext.setParameters(searchCode);
	 

		// get Expense related Items
	/*	actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test GB - JUnit", "test GB - JUnit", "N", false));
*/
		String retSaveExpense = proxy.execute();
		assertTrue(retSaveExpense.equals(IConstants.SUCCESS));
	}
	/**
	 * This test is to get list of all the system codes , test done to check for duplicates 
	 * in display and allow duplicate system codes
	 * @throws Exception
	 */
	@Test	
	public void testgetListAllCode() throws Exception {
		ActionProxy proxy = getActionProxy("/systemCodesInitialize.action");
		String dExpenseDate = "02/16/2012";
		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		com.opensymphony.xwork2.ActionContext actionContext = proxy
				.getInvocation().getInvocationContext();
		actionContext.setSession(constructDummySession(
				IConstants.EXPENSE_EMPLOYEE, 1084649, 1086246, dExpenseDate,
				"7", "01", "703", "DARNELLE", "06/15/2008", "12/31/2222"));

		Map<String, Object> searchCode =prepareDummySysCode(
				null);
		//actionContext.setParameters(searchCode);
	 

		// get Expense related Items
	/*	actionContext.setParameters(prepareDummyExpense(dExpenseDate,
				"test GB - JUnit", "test GB - JUnit", "N", false));
*/
		String retSaveExpense = proxy.execute();
		assertTrue(retSaveExpense.equals(IConstants.SUCCESS));
	}
	
	
	
	
	private static Map<String, Object> prepareDummySysCode(String codeval)
	{
		Map<String, Object> mapSystemCode = new HashMap<String, Object>(); 
		mapSystemCode.put("searchCode", "SEY1"); 
		return mapSystemCode;
	}
	
	private static Map<String, Object> prepareDummySysCode(String sysCode,
			String stDt,String enDt, String desc,String val, String modUser, String ModDt)
	{
		Map<String, Object> mapSystemCode = new HashMap<String, Object>(); 
		mapSystemCode.put("nsystemCode.systemCode", sysCode);
		mapSystemCode.put("nsystemCode.startDate",stDt );
		mapSystemCode.put("nsystemCode.endDate",enDt);
		mapSystemCode.put("nsystemCode.description", desc);
		mapSystemCode.put("nsystemCode.value", val);
		mapSystemCode.put("nsystemCode.modifiedUserId", modUser);
		mapSystemCode.put("nsystemCode.modifiedDate", ModDt);
		return mapSystemCode;
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
	public String getCurrentdate()
	{
	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	   //get current date time with Date()
	   Date date = new Date();
	   System.out.println(dateFormat.format(date));
       String retDate = dateFormat.format(date).toString(); 
	   return retDate;
	   
	}

}
