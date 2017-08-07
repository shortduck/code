package gov.michigan.dit.timeexpense.action.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import gov.michigan.dit.timeexpense.action.BaseAction;
import gov.michigan.dit.timeexpense.action.LoginAction;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class LoginActionTest{
	private static EntityManagerFactory emf;
	private EntityManager em;

	private LoginAction action;

	private final long MULTIPLE_USER_EMP_ID = 999999998L;
	private final long NO_APP_ACCESS_EMP_ID = 999999996L;
	private final long APP_ACCESS_EXPIRED_EMP_ID = 999999997L;
	
	
	public LoginActionTest() {
		action = new LoginAction();
		action.setSession(new HashMap());
	}
	
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
		action.setEntityManager(em);
		action.prepare();
		em.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testLogin_InvalidSelfServiceId(){
		action.setSelfServiceId("Dummy");
		assertEquals(IConstants.FAILURE, action.execute());
	}
	
	@Test
	public void testLogin_ValidSelfServiceId() throws Exception{
		action.setSelfServiceId("hr0134067");
		assertEquals(IConstants.SUCCESS, action.execute());
		
		Map<String, Object> session = 
			(Map<String, Object>)
			TimeAndExpenseUtil.getPrivateField(action, BaseAction.class, "session");

		UserProfile user = (UserProfile)session.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
		assertNotNull(user);
		assertNotNull(user.getUserId());
		assertNotSame("", user.getUserId());
		
	}

	@Test
	public void testInvalidSelfServiceId_UserWithoutAppAccess() throws Exception{
		// get private method
		Method privateMethod = getPrivateMethod(action, "buildAndSaveUserProfile", long.class);
		
		//action.buildAndSaveUserProfile(999999996L);
		String result = (String)privateMethod.invoke(action, NO_APP_ACCESS_EMP_ID);
		
		assertEquals(IConstants.FAILURE, result);
		Assert.assertEquals(action.getError().getErrorCode(),IConstants.APP_ACCESS_DENIED_ERRCODE);
	}

	@Test
	public void testInvalidSelfServiceId_UserWithExpiredAppAccess() throws Exception{
		// get private method
		Method privateMethod = getPrivateMethod(action, "buildAndSaveUserProfile", long.class);
		
		//action.buildAndSaveUserProfile(999999996L);
		String result = (String)privateMethod.invoke(action, APP_ACCESS_EXPIRED_EMP_ID);
		
		assertEquals(IConstants.FAILURE, result);
		Assert.assertEquals(action.getError().getErrorCode(),IConstants.APP_ACCESS_EXPIRED_ERRCODE);
	}
	
	private Method getPrivateMethod(Object obj, String methodName, Class argType) throws Exception{
		Method privateMethod = obj.getClass().getDeclaredMethod(methodName, argType);
		privateMethod.setAccessible(true);
		return privateMethod;
	}

}
