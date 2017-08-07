package gov.michigan.dit.timeexpense.action.test;

import gov.michigan.dit.timeexpense.action.SelectEmployeeAction;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
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

public class SelectEmployeeActionTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	private SelectEmployeeAction action;
	
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

		Map	sessionMap = new HashMap();
		sessionMap.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, new UserProfile("T_TKU59"));
		
		action = new SelectEmployeeAction();
		action.setSession(sessionMap);
		action.setEntityManager(em);
		action.prepare();
		em.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
	}

	@Test
	public void testGetDepartment() {
		action.setModuleId("DCTW001");
		Assert.assertFalse(action.getDepartment().isEmpty());
	}

	@Test
	public void testGetAgency() {
		action.setModuleId("DCTW001");
		Assert.assertFalse(action.getAgency("05").isEmpty());
	}

	@Test
	public void testGetTKU() {
		action.setModuleId("DCTW001");
		Assert.assertFalse(action.getTKU("05", "01").isEmpty());
	}

	@Test
	public void testExecute() {
		action.setModuleId("DCTW001");
		action.setEntityManager(em);
		String result = action.execute();
		Assert.assertEquals("success", result);
		
		String jsonDepts = action.getJsonResponse();
		Assert.assertFalse(jsonDepts == null);
		Assert.assertFalse("".equals(jsonDepts));
	}
	
}
