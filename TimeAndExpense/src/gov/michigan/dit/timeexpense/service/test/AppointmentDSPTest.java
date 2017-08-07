package gov.michigan.dit.timeexpense.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import gov.michigan.dit.timeexpense.dao.AppointmentDAO;
import gov.michigan.dit.timeexpense.model.core.Agency;
import gov.michigan.dit.timeexpense.model.core.AppointmentListBean;
import gov.michigan.dit.timeexpense.model.core.Department;
import gov.michigan.dit.timeexpense.model.core.Tku;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseProfile;
import gov.michigan.dit.timeexpense.model.display.Location;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppointmentDSPTest {

	private AppointmentDSP service;
	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (emf != null && emf.isOpen())
			emf.close();
	}

	@Before
	public void setUp() throws Exception {
		service = new AppointmentDSP(em);
		AppointmentDAO dao = new AppointmentDAO();

		em = emf.createEntityManager();
		dao.setEntityManager(em);

		service.setAppointmentDao(dao);
		em.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testGetDepartments_ValidModuleUser() {
		UserProfile user = new UserProfile("T_TKU59");
		List<Department> depts = service.getDepartments(user, "DCTW001");
		Assert.assertNotNull("Valid department not found for user(T_TKU59) and module(DCTW001)!", depts);
		
		Department dept = depts.get(0);
		Assert.assertNotNull(dept);
	}

	@Test
	public void testGetDepartments_InvalidModuleUser() {
		UserProfile user = new UserProfile("DummyUser");
		List<Department> depts = service.getDepartments(user, "DummyModule");
		Assert.assertTrue(depts.size() == 0);
	}

	@Test
	public void testGetAgencies_ValidModuleUserDepartment() {
		UserProfile user = new UserProfile("T_TKU59");
		List<Agency> agencies = service.getAgencies(user, "DCTW001", "05");
		Assert.assertNotNull("Valid agencies not found for user(T_TKU59), module(DCTW001) and department(05)!", agencies);
		
		Agency agency = agencies.get(0);
		Assert.assertNotNull(agency);
	}

	@Test
	public void testGetAgencies_InvalidModuleUserDepartment() {
		UserProfile user = new UserProfile("DummyUser");
		List<Agency> agencies = service.getAgencies(user, "DummyModule", "DummyDept");
		Assert.assertTrue(agencies.size() == 0);
	}

	@Test
	public void testGetTkus_ValidModuleUserDepartment() {
		UserProfile user = new UserProfile("T_TKU59");
		List<Tku> tkus = service.getTkus(user, "DCTW001", "05", "01");
		Assert.assertNotNull("Valid tkus not found for user(T_TKU59), module(DCTW001), department(05) and agency(01)!", tkus);
		
		Tku tku = tkus.get(0);
		Assert.assertNotNull(tku);
	}

	@Test
	public void testGetTkus_InvalidModuleUserDepartment() {
		UserProfile user = new UserProfile("DummyUser");
		List<Tku> tkus = service.getTkus(user, "DummyModule", "DummyDept" ,"DummyAgency");
		Assert.assertTrue(tkus.size() == 0);
	}
	
	
	@Test
	public void testFindAppointmentsByEmpId_ValidEmpId(){
		List<AppointmentListBean> appointments = service.getAppointmentsByEmpIdAndDept(999999997, "55", "", "", "T_HRMND99", "EXPW002", new Date(),"1");
		assertNotNull(appointments);
		Assert.assertEquals(1, appointments.size());
	}

	@Test
	public void testFindAppointmentsByEmpId_InvalidEmpId(){
		List<AppointmentListBean> appointments = service.getAppointmentsByEmpIdAndDept(123456789, "55", "", "", "T_HRMND99", "EXPW002", new Date(),"1");
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}
	

	@Test
	public void testFindAppointmentsByEmpId_validEmpId_Dept_Only(){
		List<AppointmentListBean> appointments = service.getAppointmentsByEmpIdAndDept(134774, "27", "", "", "T_HRMND99", "EXPW002", new Date(),"1");
		assertNotNull(appointments);
		Assert.assertEquals(2, appointments.size());
	}
	
	@Test
	public void testFindAppointmentsByEmpId_validEmpId_Dept_Agency(){
		List<AppointmentListBean> appointments = service.getAppointmentsByEmpIdAndDept(134774, "27", "95", "", "T_HRMND99", "EXPW002", new Date(),"1");
		assertNotNull(appointments);
		Assert.assertEquals(2, appointments.size());
	}
	
	@Test
	public void testFindAppointmentsByEmpId_validEmpId_Dept_Agency_TKU(){
		List<AppointmentListBean> appointments = service.getAppointmentsByEmpIdAndDept(134774, "27", "95", "110", "T_HRMND99", "EXPW002", new Date(),"1");
		assertNotNull(appointments);
		Assert.assertEquals(1, appointments.size());
	}

	@Test
	public void testFindAppointmentsByEmpId_InvalidParamter(){
		List<AppointmentListBean> appointments = service.getAppointmentsByEmpIdAndDept(0, "55", "", "", "T_HRMND99", "EXPW002", new Date(),"1");
		assertNull(appointments);
	
	}
	
	
	@Test
	public void testFindAppointmentsByLastNameInDeptAgencyTku_ValidEmpId(){
		List<AppointmentListBean> appointments = service.getAppointmentsByLastNameInDeptAgencyTku("55", "01", "370", "Ch", "T_HRMND99", "EXPW002", new Date(),"1");
		assertNotNull(appointments);
		Assert.assertEquals(1, appointments.size());
	}

	@Test
	public void testFindAppointmentsByLastNameInDeptAgencyTku_InvalidEmpId(){
		List<AppointmentListBean> appointments = service.getAppointmentsByLastNameInDeptAgencyTku("999999", "01", "370", "Ch", "T_HRMND99", "EXPW002", new Date(),"1");
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}
	

	@Test
	public void testFindAppointmentsByLastNameInDeptAgencyTku_BlankLastName(){
		List<AppointmentListBean> appointments = service.getAppointmentsByLastNameInDeptAgencyTku("55", "01", "370", "", "T_HRMND99", "EXPW002", new Date(),"1");
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size()>0);
	}

	@Test
	public void testFindAppointmentsByLastNameInDeptAgencyTku_BeyondDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(2099, Calendar.FEBRUARY, 10);

		List<AppointmentListBean> appointments = service.getAppointmentsByLastNameInDeptAgencyTku("55", "01", "370", "Ch", "T_HRMND99", "EXPW002", cal.getTime(),"1");
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	
	@Test
	public void testFindAppointmentsByDept_ValidDept(){
		List<AppointmentListBean> appointments = service.getAppointmentsByLastNameInDeptAgencyTku("55", null,null,null, "T_HRMND99", "EXPW002", new Date(),"1");
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size() > 0);
	}

	@Test
	public void testFindAppointmentsByDept_InvalidDept(){
		List<AppointmentListBean> appointments = service.getAppointmentsByLastNameInDeptAgencyTku("999999", null,null,null, "T_HRMND99", "EXPW002", new Date(),"1");
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	@Test
	public void testFindAppointmentsByDept_BeyondDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(3999, Calendar.FEBRUARY, 10);

		List<AppointmentListBean> appointments = service.getAppointmentsByLastNameInDeptAgencyTku("55",  null,null,null, "T_HRMND99", "EXPW002", cal.getTime(),"1");
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	   
	
	
	@Test
	public void testFindEmployeeHeaderInfo(){
		int appointmentId = 107192;
		
		List<EmployeeHeaderBean> empHeaderList = service.getEmployeeHeaderInfo(appointmentId);
				
		Assert.assertNotNull(empHeaderList);
	}
	
	@Test
	public void testFindEmployeeHeaderInfo_InvalidInput(){
		int appointmentId = 0;
		
		List<EmployeeHeaderBean> empHeaderList = service.getEmployeeHeaderInfo(appointmentId);
				
		Assert.assertTrue(empHeaderList.isEmpty());
	}
	

	@Test
	public void testFindEmployeeHeaderInfoEmpId_Valid(){
		int empId =  131971;
			
		List<EmployeeHeaderBean> empHeaderList = service.getEmployeeHeaderInfoByEmpId(empId);
				
		Assert.assertNotNull(empHeaderList);
		
	}
	
	@Test
	public void testFindEmployeeHeaderInfoEmpId_InvalidInput(){
		int empId =  0;
				
		List<EmployeeHeaderBean> empHeaderList = service.getEmployeeHeaderInfoByEmpId(empId);
				
		Assert.assertTrue(empHeaderList.isEmpty());
	}
	
	@Test
	public void testFindHomeLocation(){
		
		Date effectiveDate = new Date();
		

		Location locations = service.getEmployeeHomeLocation(132448, effectiveDate);
		assertNotNull(locations);
		
	}	
	
	
	@Test
	public void testFindExpenseProfiles(){
		int apptId =109770;
		
		List<ExpenseProfile> expProfileList =  service.getEmployeeExpenseProfiles(apptId,new Date("02/11/2009") );
		Assert.assertNotNull(expProfileList);
		System.out.println(expProfileList.size());
	}
	
	@Test
	public void testFindDistStartDate_ReturnsEffectiveDate() {
		Date effectiveDate = new Date(2008,11,20);
		Date distStartDate = service.getDistributionStartDate(effectiveDate);
		System.out.println(effectiveDate + "    Dist Start Date ::" +  distStartDate);
		Assert.assertNotNull(distStartDate);
	}
	
	
	
	/**
	 *  Returns system Date
	 */
	@Test
	public void testFindDistStartDate_ReturnsDefaultDate() {
		Date effectiveDate = new Date(2010,11,20);
		Date distStartDate = service.getDistributionStartDate(effectiveDate);
		System.out.println(effectiveDate + "    Dist Start Date ::" +  distStartDate);
		Assert.assertNotNull(distStartDate);
	}

	
	@Test
	public void testFindEffectiveDate_Valid(){
		int apptId = 106968;
		Date effectiveDate = service.getEffectiveDate(apptId);
		assertNotNull(effectiveDate);
		
		Date expectedDate = new Date(2222,11,31);

		assertEquals(expectedDate.getYear(), 1900 + effectiveDate.getYear());
		assertEquals(expectedDate.getMonth(), effectiveDate.getMonth());
		assertEquals(expectedDate.getDate(), effectiveDate.getDate());
	}

	
	@Test
	public void testFindEffectiveDate_InValid(){
	    int apptId = 999999;
		Date effectiveDate = service.getEffectiveDate(apptId);
		
		Date expectedDate = new Date();
		assertEquals(expectedDate.getYear(),  effectiveDate.getYear());
		assertEquals(expectedDate.getMonth(), effectiveDate.getMonth());
		assertEquals(expectedDate.getDate(), effectiveDate.getDate());
		
		
		
	}
	
	@Test
	public void testFindInactiveStatusForExpenseToDateLeave_NotAllowExpenses(){
		long count = 0;
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 6, 19);	
		java.sql.Date dt = new java.sql.Date(cal.getTimeInMillis());
		assertFalse(service.getInactiveStatusForExpenseToDate(1012107, dt));
	}	
	
	@Test
	public void testFindInactiveStatusForExpenseToDateLeave_AllowExpenses(){
		long count = 0;
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 6, 18);	
		java.sql.Date dt = new java.sql.Date(cal.getTimeInMillis());
		assertTrue(service.getInactiveStatusForExpenseToDate(1012107, dt));
	}	
	
	@Test
	public void testFindInactiveStatusForExpenseToDateTerminated_NotAllowExpenses(){
		long count = 0;
		Calendar cal = Calendar.getInstance();
		cal.set(2010, 11, 3);
		java.sql.Date dt = new java.sql.Date(cal.getTimeInMillis());
		assertFalse(service.getInactiveStatusForExpenseToDate(1121806, dt));
	}	
	
	@Test
	public void testFindInactiveStatusForExpenseToDateTerminated_AllowExpenses(){
		long count = 0;
		Calendar cal = Calendar.getInstance();
		cal.set(2010, 11, 2);	
		java.sql.Date dt = new java.sql.Date(cal.getTimeInMillis());
		assertTrue(service.getInactiveStatusForExpenseToDate(1121806, dt));
	}	
	
	
}
