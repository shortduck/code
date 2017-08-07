package gov.michigan.dit.timeexpense.action.test;

import gov.michigan.dit.timeexpense.action.ExpenseDetailsAction;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.HashMap;
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

public class ExpenseDetailsActionTest {
	
	private static EntityManagerFactory emf;
	private EntityManager em;
	
	private ExpenseDetailsAction action; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@Before
	public void setUp() throws Exception {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		action = new ExpenseDetailsAction();
		action.setEntityManager(em);
		
		populateTestData();
		action.prepare();
	}

	private void populateTestData() {
		ExpenseMasters master = em.find(ExpenseMasters.class, 999999999);

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(IConstants.EXPENSE_SESSION_DATA, master);
		action.setSession(sessionMap);
	}

	@After
	public void tearDown() throws Exception {
		em.flush(); 
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testFindExpenseDetails() {
		//ZH - commented code prior to build
		/*
		String detailsJson = action.constructExpenseDetailsJson();
		Assert.assertSame(IConstants.SUCCESS, detailsJson);
		
		System.out.println(action.getJsonResponse());
		
		Assert.assertNotNull(action.getJsonResponse());
		Assert.assertNotSame("", action.getJsonResponse());
		*/
	}

	@Test
	public void testFindCitiesJson(){
		String json = action.constructCityJson();
		Assert.assertTrue(json.length() > 2);
	}
	
}
