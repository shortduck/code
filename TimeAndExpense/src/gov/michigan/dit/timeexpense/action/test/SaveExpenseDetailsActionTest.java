package gov.michigan.dit.timeexpense.action.test;

import gov.michigan.dit.timeexpense.action.SaveExpenseDetailsAction;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.ExpenseDetailDisplayBean;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SaveExpenseDetailsActionTest {

	private static SaveExpenseDetailsAction action;
	private static EntityManagerFactory emf;
	private EntityManager em;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
		action = new SaveExpenseDetailsAction();
		action.setSession(constructDummySession());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@Before
	public void setUp() throws Exception {
		em = emf.createEntityManager();
		action.setEntityManager(em);
		em.getTransaction().begin();
		action.prepare();
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testUpdateExpenseDetails() {
		ExpenseMasters expense = em.find(ExpenseMasters.class, 999999999);
		
		int initialSize = expense.getExpenseDetailsCollection().size();
		
		List<ExpenseDetailDisplayBean> displayDetails = new ArrayList<ExpenseDetailDisplayBean>();
		ExpenseDetailDisplayBean dummyDtl = constructNewExpenseDetailDisplay();
		displayDetails.add(dummyDtl);
		
		action.updateExpenseDetails(expense, displayDetails);
		
		int afterUpdateSize = expense.getExpenseDetailsCollection().size();
		
		Assert.assertEquals(1, afterUpdateSize);
	}

	private ExpenseDetailDisplayBean constructNewExpenseDetailDisplay() {
		ExpenseDetailDisplayBean display = new ExpenseDetailDisplayBean();
		
		display.setLineItemNo(5);
		display.setExpenseDate(new Date());
		display.setExpenseType("Lunc");
		display.setAmount(55.55);
		display.setFromCity("Chandigarh");
		display.setFromState("MI");
		display.setToCity("Gurgaon");
		display.setToState("MI");
		display.setDepartTime("4:10 AM");
		display.setReturnTime("9:45 AM");
		display.setRoundTrip(true);
		display.setMiles(180);
		display.setVicinityMiles(150);
		display.setCommonMiles(false);
		display.setComments("Mohnish");
		
		return display;
	}

	private static Map<String, Object> constructDummySession(){
		Map<String, Object> sessionContext = new HashMap<String, Object>();
		sessionContext.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, IConstants.EXPENSE_MANAGER);
		
		UserSubject subject = new UserSubject();
		subject.setEmployeeId(999999996);
		subject.setAppointmentId(999999998);
		subject.setAppointmentStart(new Date(2021-1900, Calendar.FEBRUARY, 2));
		subject.setAppointmentEnd(new Date(2022-1900, Calendar.FEBRUARY, 2));
		subject.setDepartment("07");subject.setAgency("01");subject.setTku("705");
		
		UserProfile user = new UserProfile("Mohnish005");
		user.setEmpIdentifier(999999998);
		user.setModules(new HashSet<String>());
		
		sessionContext.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		sessionContext.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, user);
		
		return sessionContext;
	}	
}
