package gov.michigan.dit.timeexpense.action.test;

import static org.junit.Assert.assertNotNull;
import gov.michigan.dit.timeexpense.action.AbstractAction;
import gov.michigan.dit.timeexpense.action.HomePageAction;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
@SuppressWarnings("unchecked")
public class HomePageActionTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private static HomePageAction action = new HomePageAction();
	
	@BeforeClass
	public static void init() {
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
		action.setEntityManager(em);
		
		Map session = new HashMap<String, Object>();
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, createUserProfile());
		action.setSession(session);
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
	}


	@Test
	public void testExecute() {
		action.execute();
		assertNotNull(action.getMenuOptions());
	}
	
    private static UserProfile createUserProfile() {
    	UserProfile profile = new UserProfile ("SMITHM69");
    	Set<String> modules = new HashSet<String> ();
        modules.add(IConstants.ADVANCE_EMPLOYEE);
        modules.add(IConstants.EXPENSE_EMPLOYEE);
        modules.add(IConstants.ADVANCE_MANAGER);
        modules.add(IConstants.APPROVE_WEB_MANAGER);
        modules.add(IConstants.EXPENSE_MANAGER);
        modules.add(IConstants.ADVANCE_STATEWIDE);
        modules.add(IConstants.APPROVE_WEB_STATEWIDE);
        modules.add(IConstants.EXPENSE_STATEWIDE);
        profile.setModules(modules);
        return profile;
   
    }

}
