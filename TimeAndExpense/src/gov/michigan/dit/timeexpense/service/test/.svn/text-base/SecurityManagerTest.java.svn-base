package gov.michigan.dit.timeexpense.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import gov.michigan.dit.timeexpense.dao.SecurityDAO;
import gov.michigan.dit.timeexpense.model.core.User;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.util.IConstants;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SecurityManagerTest {
	
	private SecurityManager service;
	private static EntityManagerFactory emf;
	private EntityManager em;
	
	private final int VALID_USER_EMP_ID = 999999999;
	private final String SINGLE_USER_ID = "Mohnish003";
	private final String USER_ID_WITH_NO_APP_ACCESS = "Mohnish005";
	private final String USER_ID_WITH_EXPIRED_APP_ACCESS = "Mohnish004";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if(emf != null && emf.isOpen())
			emf.close();
	}

	@Before
	public void setUp() throws Exception {
		
		em = emf.createEntityManager();
		service = new SecurityManager(em);
		em.getTransaction().begin();		
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testGetDcdsUserId() throws Exception{
		UserProfile user = service.getDcdsUser(VALID_USER_EMP_ID, true);
		
		assertNotNull(user);
		assertNotNull(user.getUserId());
		assertNotSame("", user.getUserId());
	}

	@Test
	public void testCheckApplicationAccess() {
		Assert.assertEquals(1, service.checkApplicationAccess(em.find(User.class, SINGLE_USER_ID)));
	}

	@Test
	public void testCheckModuleAccess_ValidModule() throws Exception{
		UserProfile userProfile = service.getDcdsUser(VALID_USER_EMP_ID, true);
		assertTrue(service.checkModuleAccess(userProfile, IConstants.APP_MODULE_ID));
	}

	@Test
	public void testCheckModuleAccess_InvalidModule() throws Exception{
		UserProfile userProfile = service.getDcdsUser(VALID_USER_EMP_ID, true);
		assertFalse(service.checkModuleAccess(userProfile, "MohnishDummyModule"));
	}
	
	@Test
	public void testCheckScopedModuleAccess_InvalidModule() {
		UserProfile userProfile = new UserProfile("Mohnish003");
		assertFalse(service.checkModuleAccess(userProfile, "MohnishDummyModule", "NA", "NA", "NA"));
	}
	/* ZH - May not be a valid test case
	@Test
	public void testCheckScopedModuleAccess_WriteAccess() {
		final String ALL = IConstants.SECURITY_SCOPE_ALL; 
		UserProfile userProfile = new UserProfile("Mohnish003");
		assertTrue(service.checkModuleAccess(userProfile, "TEXW001", ALL, ALL, ALL));
	}*/
	
	/*
	@Test
	public void testCheckScopedModuleAccess_ReadAccess() {
		UserProfile userProfile = new UserProfile("Mohnish003");
		assertFalse(service.checkModuleAccess(userProfile, "TEXW001", "59", "01", "705"));
	}*/
	
	// commented out the test above and replaced module data
	@Test
	public void testCheckScopedModuleAccess_ReadAccess() {
		UserProfile userProfile = new UserProfile("Mohnish003");
		assertFalse(service.checkModuleAccess(userProfile, "ADVW001", "59", "01", "705"));
	}
	
	@Test
	public void testNoApplicationAccess() throws Exception{
		Assert.assertEquals(-1, service.checkApplicationAccess(em.find(User.class, USER_ID_WITH_NO_APP_ACCESS)));
	}
	
	@Test
	public void testExpiredApplicationAccess() throws Exception{
		Assert.assertEquals(-2, service.checkApplicationAccess(em.find(User.class, USER_ID_WITH_EXPIRED_APP_ACCESS)));	
	}
	
	/* ZH - May not be a valid test case
	@Test
	public void testGetModuleAccessMode_UpdateAccess() {
		final String ALL = IConstants.SECURITY_SCOPE_ALL; 
		UserProfile userProfile = new UserProfile("Mohnish003");
		assertEquals(service.getModuleAccessMode(userProfile, "TEXW001", ALL, ALL, ALL), IConstants.SECURITY_UPDATE_MODULE_ACCESS);
	}
	*/
	
	@Test
	public void testGetModuleAccessMode_InquiryAccess() {
		final String ALL = IConstants.SECURITY_SCOPE_ALL; 
		UserProfile userProfile = new UserProfile("T_MAP02");
		assertEquals(service.getModuleAccessMode(userProfile, "DCTR004", "07", "01", "000"), IConstants.SECURITY_INQUIRY_MODULE_ACCESS);
	}
	/* ZH - May not be a valid test case
	@Test
	public void testGetModuleAccessMode_NoAccess() {
		final String ALL = IConstants.SECURITY_SCOPE_ALL; 
		UserProfile userProfile = new UserProfile("T_MAP02");
		assertEquals(service.getModuleAccessMode(userProfile, "DCTR004", ALL, ALL, ALL), IConstants.SECURITY_NO_MODULE_ACCESS);
	}*/
}
