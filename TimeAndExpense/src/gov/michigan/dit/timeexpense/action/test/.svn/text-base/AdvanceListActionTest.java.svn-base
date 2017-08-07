package gov.michigan.dit.timeexpense.action.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.michigan.dit.timeexpense.action.AdvanceAction;
import gov.michigan.dit.timeexpense.action.AdvanceListAction;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.model.core.AdvanceEvents;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.core.VAdvanceList;
import gov.michigan.dit.timeexpense.model.display.AdvApprovalTransaction;
import gov.michigan.dit.timeexpense.model.display.DisplayAdvance;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class AdvanceListActionTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private static AdvanceListAction listaction = new AdvanceListAction ();
	private AdvanceDSP advanceService;
	private AppointmentDSP apptService;
	private CommonDSP commonService;
	private CommonDAO commonDao;
	//private Map session = new HashMap();
	
	@BeforeClass
	public static void init() {
		Map session = new HashMap<String, Object>();
		listaction.setSession(session);
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
		listaction.setEntityManager(em);
		listaction.prepare();
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}


	/**
	 * Deletes an existing advance event
	 */
	
	@Test
	public void testDeleteAdvance() {			
		listaction.setAdvanceEventId(15);
		String result = listaction.deleteAdvance();
		Assert.assertEquals(result,"success");
		
}
	/**
	 * Gets the list of advances for where the user is manager
	 */
	
	@Test
	public void testGetAdvanceListAppt() {
		
		Map session = new HashMap();
		session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, "ADVW002");
		UserProfile profile = new UserProfile("T_DEPT99",134067);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
		EmployeeHeaderBean empInfo = new EmployeeHeaderBean();
		empInfo.setApptId(109744);
		listaction.setEmpInfo(empInfo);
		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.YEAR, 1999);
		Date startDate = startCal.getTime();
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.YEAR, 2222);
		Date endDate = endCal.getTime();
		
		UserSubject employeeSubject = new UserSubject (134067, 109744, startDate, startDate, "59", "01", "100", "");
		
		session.put(IConstants.EMP_HEADER_INFO, empInfo);
		session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, employeeSubject);
		listaction.setSession(session);
		
		String test = listaction.getAdvanceList();

		String json=listaction.getJsonResponse();
		
		System.out.println(json);
		Assert.assertFalse(StringUtils.isEmpty(json));
		Assert.assertEquals(test,"success");
		
				
	}
	
	/**
	 * Test teh report type parameter
	 */
	
	@Test
	public void testgetAdvanceListEmployeeAdvancesOnlyWoAdjustment() {
		
		Map session = new HashMap();
		session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, "ADVW001");
		UserProfile profile = new UserProfile("T_DEPT99",134067);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
		EmployeeHeaderBean empInfo = new EmployeeHeaderBean();
		empInfo.setApptId(109744);
		listaction.setAdvanceListType(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY);
		listaction.setEmpInfo(empInfo);
		session.put(IConstants.EMP_HEADER_INFO, empInfo);
		listaction.setSession(session);

		
		String test = listaction.getAdvanceList();

		String json=listaction.getJsonResponse();
		
		System.out.println(json);
		Assert.assertFalse(StringUtils.isEmpty(json));
		Assert.assertEquals(test,"success");
		
				
	}
	
	/**
	 * Generic test for an advance list
	 */
	
	@Test
	public void testviewAdvanceList(){
		EmployeeHeaderBean empInfo = new EmployeeHeaderBean();
		empInfo.setEmpId(134067);
		listaction.setEmpInfo(empInfo);
		
		Map session= new HashMap();
		UserProfile profile = new UserProfile("T_DEPT99",134067);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
		listaction.setSession(session);
		
		String test = listaction.viewAdvanceList();
		
		int appt = listaction.getEmpInfo().getApptId();
		int empId = listaction.getEmpInfo().getEmpId();
		
		
		Assert.assertEquals(test, IConstants.SUCCESS);
		System.out.println("appt" + appt + " " + "empid" + " " + empId);
		
	}
	
	/**
	 * 
	 */
	
	@Test
	public void testviewAdvanceListEligibility(){
		EmployeeHeaderBean empInfo = new EmployeeHeaderBean();
		empInfo.setEmpId(134067);
		empInfo.setApptId(109744);
		listaction.setEmpInfo(empInfo);
		
		Map session= new HashMap();
		UserProfile profile = new UserProfile("T_DEPT99",134067);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
		listaction.setSession(session);
		
		String test = listaction.viewAdvanceList();
				
		Assert.assertEquals(listaction.getEligibleFlag(), "false");
				
	}

	public void setCommonService(CommonDSP commonService) {
		this.commonService = commonService;
	}

	public CommonDSP getCommonService() {
		return commonService;
	}

	public void setCommonDao(CommonDAO commonDao) {
		this.commonDao = commonDao;
	}

	public CommonDAO getCommonDao() {
		return commonDao;
	}
}
