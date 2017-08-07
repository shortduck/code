package gov.michigan.dit.timeexpense.action.test;

import gov.michigan.dit.timeexpense.action.SaveExpenseAction;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

public class SaveExpenseActionTest {

	private static SaveExpenseAction action;
	private static EntityManagerFactory emf;
	private EntityManager em;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
		action = new SaveExpenseAction();
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
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testValidate() {
		action.setExpenseMaster(prepareDummyExpense());
		action.validate();
		Assert.assertTrue(action.getFieldErrors().size() == 0);
	}

	@Test
	public void testExecute() throws Exception{
		action.setExpenseMaster(prepareDummyExpense());
		action.execute();
		//Assert.assertTrue();
	}

	private ExpenseMasters prepareDummyExpense(){
		ExpenseMasters dummyExpense = new ExpenseMasters();
		dummyExpense.setComments("Comments");
		dummyExpense.setCurrentInd("Y");
		dummyExpense.setExpDateFrom(new Date(2012-1900, Calendar.FEBRUARY, 2));
		dummyExpense.setExpDateTo(new Date(2012-1900, Calendar.MARCH, 2));
		dummyExpense.setNatureOfBusiness("NatureOfBiz");
		dummyExpense.setOutOfStateInd(false);
		dummyExpense.setTravelInd("N");
		
		return dummyExpense;
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
