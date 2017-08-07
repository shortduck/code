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

public class TravelRequisitionListActionTestStruts extends StrutsTestCase {

	private ExpenseDSP expenseDSP;

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

	
	@Test
	/*
	 * Test Case for a simple case resulting in Success Emp id: 1084649
	 */
	public void testEmployee_Success_Emp() throws Exception {

		String dExpenseDate = "02/16/2012";

		ActionProxy proxy = getActionProxy("/TravelRequisitionListAction.action");

		// To insert objects into session use actionContext.
		// BaseAction.setSession wont' work
		ActionContext actionContext = proxy.getInvocation()
				.getInvocationContext();

		actionContext.setSession(constructDummySession(
				IConstants.TRAVEL_REQUISITIONS_EMPLOYEE, 1077335, 1139330, dExpenseDate,
				"7", "01", "703", "WEB_DCDS", "06/15/2008", "12/31/2222"));


		String retSaveExpense = proxy.execute();


		assertTrue(retSaveExpense.equals("success"));

	}

	
}
