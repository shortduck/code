package gov.michigan.dit.timeexpense.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.michigan.dit.timeexpense.dao.AppointmentDAO;
import gov.michigan.dit.timeexpense.model.core.Agency;
import gov.michigan.dit.timeexpense.model.core.AppointmentHistory;
import gov.michigan.dit.timeexpense.model.core.AppointmentListBean;
import gov.michigan.dit.timeexpense.model.core.Department;
import gov.michigan.dit.timeexpense.model.core.Tku;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.EmployeeGeneralInformation;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseProfile;
import gov.michigan.dit.timeexpense.model.display.Location;
import gov.michigan.dit.timeexpense.model.display.StandardDistCoding;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for AppointmentDAO.
 * 
 * @author chaudharym
 */
/**
 * @author chiduras
 *
 */
public class AppointmentDAOTest extends AbstractDAOTest {

	private AppointmentDAO dao = new AppointmentDAO();

	@Before
	public void setUp() throws Exception {
		beginTransaction();
		dao.setEntityManager(em);
	}

	@Test
	public void testFindDepartments_ValidModule() {
		List<Department> result = dao.findDepartments("Mohnish003", "TEXW001");
		assertTrue(result.size() > 0);

		Department dept = result.get(0);
		assertNotNull(dept);
	}

	@Test
	public void testFindDepartments_InvalidModule() {
		List<Department> result = dao.findDepartments("Mohnish003", "Dummy");
		assertTrue(result.isEmpty());
	}

	@Test
	public void testFindAgencies_InvalidModuleDept() {
		List<Agency> result = dao.findAgencies("Mohnish003", "Dummy", "Dummy");
		assertTrue(result.isEmpty());
	}

	@Test
	public void testFindAgencies_ValidModuleDept() {
		List<Agency> result = dao.findAgencies("T_TKU59", "DCTW001", "05");
		assertTrue(!result.isEmpty());

		Agency agcy = result.get(0);
		assertNotNull(agcy);
	}

	@Test
	public void testFindTKU_ValidModuleDeptAgency() {
		List<Tku> result = dao.findTKUs("T_TKU59", "DCTW001", "05", "14");
		assertTrue(!result.isEmpty());

		Tku tku = result.get(0);
		assertNotNull(tku);
	}

	@Test
	public void testFindTKU_InvalidModuleDeptAgency() {
		List<Tku> result = dao
				.findTKUs("Mohnish003", "Dummy", "Dummy", "Dummy");
		assertTrue(result.isEmpty());
	}

	
	
	
	@Test
	public void testFindActiveAppointmentsByExpDatesEmployee_validAppointment() {
		Date expenseStartDate = new Date("12/10/2005");
		Date expenseEndDate = new Date("12/10/2005");
		String moduleId = "PRMW027";
		int empId = 133509;
		String userId = "T_HRMND99";

		List<AppointmentsBean> appointmentsList = dao
				.findActiveAppointmentsByExpDatesEmployee(expenseStartDate,
						expenseEndDate, moduleId, empId, userId);
		Assert.assertTrue(!appointmentsList.isEmpty());

		AppointmentsBean apptBean = appointmentsList.get(0);
		Assert.assertNotNull(apptBean);
	}

	@Test
	public void testFindActiveAppointmentsByExpDatesEmployee_InvalidAppointment() {
		Date expenseStartDate = new Date("12/10/1999");
		Date expenseEndDate = new Date("12/10/1999");
		String moduleId = "PRMW027";
		int empId = 133509;
		String userId = "T_HRMND99";

		List<AppointmentsBean> appointmentsList = dao
				.findActiveAppointmentsByExpDatesEmployee(expenseStartDate,
						expenseEndDate, moduleId, empId, userId);
		Assert.assertTrue(appointmentsList.isEmpty());
	}

	@Test
	public void testFindFacsAgencyByExpenseDates_valid() {
	/*	Date expenseStartDate = new Date("09/29/2010");
		Date expenseEndDate = new Date("09/30/2010");*/
		Calendar fromCal = Calendar.getInstance();
		fromCal.set(2010, 8, 29, 0, 0, 0);
		fromCal.set(Calendar.MILLISECOND, 0);
		Calendar toCal = Calendar.getInstance();
		toCal.set(2010, 9, 3, 0, 0, 0);
		toCal.set(Calendar.MILLISECOND, 0);
		Date expenseStartDate = fromCal.getTime();
		Date expenseEndDate = toCal.getTime();
		String moduleId = "EXPW001";
		int empId = 185328;
		String userId = "T_HRMND99";
		int apptId = 1035848;

		List<AppointmentsBean> appointmentsList = dao.findFacsAgencyByExpenseDatesByEmployee(expenseStartDate,expenseEndDate,apptId );
		Assert.assertTrue(!appointmentsList.isEmpty());

		AppointmentsBean apptBean = appointmentsList.get(0);
		Assert.assertNotNull(apptBean);
	}

	@Test
	public void testFindFacsAgencyByExpenseDates_Invalid() {
		Date expenseStartDate = new Date("12/20/1999");
		Date expenseEndDate = new Date("12/10/1999");
		String moduleId = "PRMW027";
		int empId = 133509;
		String userId = "T_HRMND99";
		int apptId = 107192;

		List<AppointmentsBean> appointmentsList = dao
				.findFacsAgencyByExpenseDatesByEmployee(expenseStartDate,
						expenseEndDate, apptId) ;
		Assert.assertTrue(appointmentsList.isEmpty());

	}

	@Test
	public void testFindActiveAppointmentByExpenseDatesManagerStatewide_validAppointments() {
		Date expenseStartDate = new Date("12/10/2005");
		Date expenseEndDate = new Date("12/10/2006");
		String moduleId = "PRMW027";
		int empId = 133509;
		String userId = "T_HRMND99";

		List<AppointmentsBean> appointmentsList = dao
				.findActiveAppointmentByExpenseDatesManagerStatewide(
						expenseStartDate, expenseEndDate, moduleId, empId,
						userId);
		Assert.assertTrue(!appointmentsList.isEmpty());

		AppointmentsBean appointments = appointmentsList.get(0);
		Assert.assertNotNull(appointments);

	}

	@Test
	public void testFindActiveAppointmentByExpenseDatesManagerStatewide_InvalidAppointments() {
		Date expenseStartDate = new Date("12/10/1999");
		Date expenseEndDate = new Date("12/10/1999");
		String moduleId = "PRMW027";
		int empId = 133509;
		String userId = "T_HRMND99";

		List<AppointmentsBean> appointmentsList = dao.findActiveAppointmentByExpenseDatesManagerStatewide(expenseStartDate, expenseEndDate, moduleId, empId,userId);
		Assert.assertTrue(appointmentsList.isEmpty());
	}
	
	@Test
	public void testFindEmployeeHeaderInfo(){
		int appointmentId = 107192;
		int positionLevel = 1;
		
		List<EmployeeHeaderBean> empHeaderList = dao.findEmployeeHeaderInfo(appointmentId);
				
		Assert.assertNotNull(empHeaderList);
	}
	
	@Test
	public void testFindEmployeeHeaderInfo_InvalidInput(){
		int appointmentId = 0;
		int positionLevel = 1;
		
		List<EmployeeHeaderBean> empHeaderList = dao.findEmployeeHeaderInfo(appointmentId);
				
		Assert.assertTrue(empHeaderList.isEmpty());
	}
	
	@Test
	public void testFindEmployeeHeaderInfo_withEmpIdRequestDateValid(){
		int empId = 107192;
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(2009, 02, 10);
		
		
		Date requestDate = cal.getTime();
				
		List<EmployeeHeaderBean> empHeaderList = dao.findEmployeeHeaderInfo(empId,requestDate);
				
		Assert.assertNotNull(empHeaderList);
	}
	
	@Test
	public void testFindEmployeeHeaderInfo_withEmpIdRequestDateInvalid(){
		int empId = 1;
		
		Calendar cal = Calendar.getInstance();
		cal.set(2009, 02, 10);

		Date requestDate = cal.getTime();
				
		List<EmployeeHeaderBean> empHeaderList = dao.findEmployeeHeaderInfo(empId,requestDate);
				
		Assert.assertTrue(empHeaderList.isEmpty());
	}

	
	@Test
	public void testFindEmployeeHeaderInfoEmpId_Valid(){
		int empId =  131971;
			
		List<EmployeeHeaderBean> empHeaderList = dao.findEmployeeHeaderInfoByEmpId(empId);
				
		Assert.assertNotNull(empHeaderList);
		System.out.println(empHeaderList.get(0).getDeptName());		
	}
	
	@Test
	public void testFindEmployeeHeaderInfoEmpId_InvalidInput(){
		int empId =  0;
		
		
		List<EmployeeHeaderBean> empHeaderList = dao.findEmployeeHeaderInfo(empId);
				
		Assert.assertTrue(empHeaderList.isEmpty());
	}
	
	
/*	@Test
	public void testFindAppointmentsByEmpId_ValidEmpId(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByEmpId(999999997, new Date());
		assertNotNull(appointments);
		Assert.assertEquals(2, appointments.size());
	}

	@Test
	public void testFindAppointmentsByEmpId_InvalidEmpId(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByEmpId(555666777, new Date());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	@Test
	public void testFindAppointmentsByEmpId_BeyondDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(2099, Calendar.FEBRUARY, 10);

		List<AppointmentListBean> appointments = dao.findAppointmentsByEmpId(999999997, cal.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}
*/	
	@Test
	public void testFindAppointmentsByLastNameInDept_ValidEmpId(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDept("55", "Ch", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size()>0);
	}

	@Test
	public void testLastNameCaseInsensitiveComparison_1(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDept("55", "ch", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size()>0);
	}

	@Test
	public void testLastNameCaseInsensitiveComparison_2(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDept("55", "CH", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size()>0);
	}

	@Test
	public void testFindAppointmentsByLastNameInDept_InvalidEmpId(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDept("999999", "Ch", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	@Test
	public void testFindAppointmentsByLastNameInDept_BlankLastName(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDept("55", "", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size()>0);
	}

	@Test
	public void testFindAppointmentsByLastNameInDept_BeyondDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(2999, Calendar.FEBRUARY, 10);

		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDept("55", "Ch", "T_HRMND99", "EXPW002", cal.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	@Test
	public void testFindAppointmentsByLastNameInDeptAgency_ValidEmpId(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDeptAgency("55", "01", "Ch", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size()>0);
	}

	@Test
	public void testFindAppointmentsByLastNameInDeptAgency_InvalidEmpId(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDeptAgency("999999", "01", "Ch", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	@Test
	public void testFindAppointmentsByLastNameInDeptAgency_BlankLastName(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDeptAgency("55", "01", "", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size()>0);
	}

	@Test
	public void testFindAppointmentsByLastNameInDeptAgency_BeyondDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(2999, Calendar.FEBRUARY, 10);

		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDeptAgency("55", "01", "Ch", "T_HRMND99", "EXPW002", cal.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}
	
	@Test
	public void testFindAppointmentsByLastNameInDeptAgencyTku_ValidEmpId(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDeptAgencyTku("55", "01", "370", "Ch", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size()>0);
	}

	@Test
	public void testFindAppointmentsByLastNameInDeptAgencyTku_InvalidEmpId(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDeptAgencyTku("999999", "01", "370", "Ch", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	@Test
	public void testFindAppointmentsByLastNameInDeptAgencyTku_BlankLastName(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDeptAgencyTku("55", "01", "370", "", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size()>0);
	}

	@Test
	public void testFindAppointmentsByLastNameInDeptAgencyTku_BeyondDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(2999, Calendar.FEBRUARY, 10);

		List<AppointmentListBean> appointments = dao.findAppointmentsByLastNameInDeptAgencyTku("55", "01", "370", "Ch", "T_HRMND99", "EXPW002", cal.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	
	@Test
	public void testFindAppointmentsByDept_ValidDept(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByDept("55", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size() > 0);
	}

	@Test
	public void testFindAppointmentsByDept_InvalidDept(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByDept("999999", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	@Test
	public void testFindAppointmentsByDept_BeyondDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(3999, Calendar.FEBRUARY, 10);

		List<AppointmentListBean> appointments = dao.findAppointmentsByDept("55", "T_HRMND99", "EXPW002", cal.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	
	@Test
	public void testFindAppointmentsByDeptAgency_ValidAgency(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByDeptAgency("55", "01", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size() > 0);
	}

	@Test
	public void testFindAppointmentsByDeptAgency_InvalidAgency(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByDeptAgency("55", "9999985", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	@Test
	public void testFindAppointmentsByDeptAgency_BeyondDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(3999, Calendar.FEBRUARY, 10);

		List<AppointmentListBean> appointments = dao.findAppointmentsByDeptAgency("55", "01", "T_HRMND99", "EXPW002", cal.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}
	
	@Test
	public void testFindAppointmentsByDeptAgencyTku_ValidTku(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByDeptAgencyTku("55", "01", "370", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertTrue(appointments.size() > 0);
	}

	@Test
	public void testFindAppointmentsByDeptAgencyTku_InvalidTku(){
		List<AppointmentListBean> appointments = dao.findAppointmentsByDeptAgencyTku("55", "01", "9999985", "T_HRMND99", "EXPW002", new Date());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}

	@Test
	public void testFindAppointmentsByDeptAgencyTku_BeyondDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(3999, Calendar.FEBRUARY, 10);

		List<AppointmentListBean> appointments = dao.findAppointmentsByDeptAgencyTku("55", "01", "370", "T_HRMND99", "EXPW002", cal.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}	
	
	@Test
	public void testFindEmployeeAppointmentsInDateRange_MultipleAppointments(){
		Calendar startDate = Calendar.getInstance();
		startDate.set(2015, Calendar.FEBRUARY, 10);
		
		Calendar endDate = Calendar.getInstance();
		endDate.set(2016, Calendar.FEBRUARY, 10);
		
		List<AppointmentListBean> appointments = dao.findEmployeeAppointmentsInDateRange(999999997, startDate.getTime(), endDate.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(2, appointments.size());
	}	

	@Test
	public void testFindEmployeeAppointmentsInDateRange_SingleAppointment(){
		Calendar startDate = Calendar.getInstance();
		startDate.set(2020, Calendar.FEBRUARY, 10);
		
		Calendar endDate = Calendar.getInstance();
		endDate.set(2021, Calendar.FEBRUARY, 10);
		
		List<AppointmentListBean> appointments = dao.findEmployeeAppointmentsInDateRange(999999997, startDate.getTime(), endDate.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(1, appointments.size());
	}		

	@Test
	public void testFindEmployeeAppointmentsInDateRange_DateRangeBeforeAnyAppt(){
		Calendar startDate = Calendar.getInstance();
		startDate.set(2007, Calendar.FEBRUARY, 10);
		
		Calendar endDate = Calendar.getInstance();
		endDate.set(2008, Calendar.FEBRUARY, 10);
		
		List<AppointmentListBean> appointments = dao.findEmployeeAppointmentsInDateRange(999999997, startDate.getTime(), endDate.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}	

	@Test
	public void testFindEmployeeAppointmentsInDateRange_DateRangeAfterAnyAppt(){
		Calendar startDate = Calendar.getInstance();
		startDate.set(2777, Calendar.FEBRUARY, 10);
		
		Calendar endDate = Calendar.getInstance();
		endDate.set(2778, Calendar.FEBRUARY, 10);
		
		List<AppointmentListBean> appointments = dao.findEmployeeAppointmentsInDateRange(999999997, startDate.getTime(), endDate.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(0, appointments.size());
	}	

	@Test
	public void testFindEmployeeAppointmentsInDateRange_SingleDateStart(){
		Calendar startDate = Calendar.getInstance();
		startDate.set(2009, Calendar.FEBRUARY, 2);
		
		Calendar endDate = Calendar.getInstance();
		endDate.set(2009, Calendar.FEBRUARY, 2);
		
		List<AppointmentListBean> appointments = dao.findEmployeeAppointmentsInDateRange(999999997, startDate.getTime(), endDate.getTime());
		assertNotNull(appointments);
		Assert.assertEquals(2, appointments.size());
	}	

	@Test
	public void testFindEmployeeAppointmentsInDateRange_SingleDateEnd(){
		// using deprecated Date() to arrive at absolute 00:00:00.000 !
		Date startDate = new Date(2022-1900, Calendar.FEBRUARY, 2);
		Date endDate = new Date(2022-1900, Calendar.FEBRUARY, 2);

		List<AppointmentListBean> appointments = dao.findEmployeeAppointmentsInDateRange(999999997, startDate, endDate);
		assertNotNull(appointments);
		Assert.assertEquals(1, appointments.size());
	}	
	
	@Test
	public void testfindAppointmentHistory_InvalidAppt(){
		// using deprecated Date() to arrive at absolute 00:00:00.000 !
		Date searchDate = new Date(2012-1900, Calendar.FEBRUARY, 2);

		assertNull(dao.findAppointmentHistory(0, searchDate));
	}	

	@Test
	public void testfindAppointmentHistory_ValidApptAndTimebound(){
		// using deprecated Date() to arrive at absolute 00:00:00.000 !
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 11, 11);	
		Date dt = new Date(cal.getTimeInMillis());

		List<AppointmentsBean> apptHistory = dao.findAppointmentHistory(1070786, dt);
		assertNotNull(apptHistory);
		assertNotNull(apptHistory.get(0));
		//[mc] Commenting the following as appt history Id no longer fetched by the service.
		//assertEquals(999999998, apptHistory.get(0).getApptHistoryId());
	}	
	
	@Test
	public void testfindAppointmentHistory_new(){
		// using deprecated Date() to arrive at absolute 00:00:00.000 !
		Date searchDate = new Date(2010-1900, Calendar.SEPTEMBER, 19);

		List<AppointmentsBean> apptHistory = dao.findAppointmentHistory(145792, searchDate);
		assertNotNull(apptHistory);
		assertNotNull(apptHistory.get(0));
		//[mc] Commenting the following as appt history Id no longer fetched by the service.
		//assertEquals(999999998, apptHistory.get(0).getApptHistoryId());
	}	
	
	
	@Test
	public void testFindWorkLocation(){
		// using deprecated Date() to arrive at absolute 00:00:00.000 !
		Date effectiveDate = new Date(2022-1900, Calendar.FEBRUARY, 2);
		

		Location locations = dao.findWorkLocation(110468, effectiveDate);
		assertNotNull(locations);
		System.out.println(locations.getAddressLine1());
		//Assert.assertEquals(1, appointments.size());
	}	
	
	
	
	@Test
	public void testFindHomeLocation(){
		
		Date effectiveDate = new Date();
		
		Location locations = dao.findHomeLocation(132448, effectiveDate);
		assertNotNull(locations);
		
	}	
	

	

	
	
	
	@Test
	public void testFindExpenseProfiles(){
		int apptId =109770;
		
		List<ExpenseProfile> expProfileList =  dao.findExpenseProfiles(apptId,new Date("02/11/2009") );
		Assert.assertNotNull(expProfileList);
	}
	
	@Test
	public void testFindEffectiveDate_Valid(){
		
		Date effectiveDate = dao.findEffectiveDate(106968);
		assertNotNull(effectiveDate);
		
		Date expectedDate = new Date(2222,11,31);

		assertEquals(expectedDate.getYear(), 1900 + effectiveDate.getYear());
		assertEquals(expectedDate.getMonth(), effectiveDate.getMonth());
		assertEquals(expectedDate.getDate(), effectiveDate.getDate());
	}

	
	@Test
	public void testFindEffectiveDate_InValid(){
		Date effectiveDate = dao.findEffectiveDate(99999);
		
		Date expectedDate = new Date();
		assertEquals(expectedDate.getYear(),  effectiveDate.getYear());
		assertEquals(expectedDate.getMonth(), effectiveDate.getMonth());
		assertEquals(expectedDate.getDate(), effectiveDate.getDate());
		
		//assertEquals(effectiveDate,expectedDate );
		
		
	}
	
	@Test
	public void testFindDistStartDate_ReturnsEffectiveDate() {
		Date effectiveDate = new Date(2008,11,20);
		Date distStartDate = dao.findDistStartDate(effectiveDate);
		System.out.println(effectiveDate + "    Dist Start Date ::" +  distStartDate);
		Assert.assertNotNull(distStartDate);
	}
	
	
	
	/**
	 *  Returns system Date
	 */
	@Test
	public void testFindDistStartDate_ReturnsDefaultDate() {
		Date effectiveDate = new Date(2010,11,20);
		Date distStartDate = dao.findDistStartDate(effectiveDate);
		System.out.println(effectiveDate + "    Dist Start Date ::" +  distStartDate);
		Assert.assertNotNull(distStartDate);
	}

	
	
	@Test
	public void testEmpGeneralInfo_ReturnsNone() {
		int apptId = 107019;
		Date effectiveDate = new Date(2020,11,22);
		EmployeeGeneralInformation empInfo = dao.findEmployeeGeneralInfo(apptId, effectiveDate);
		
		Assert.assertNull(empInfo);
		
	}
	
	
	@Test
	public void testEmpGeneralInfo_Valid() {
		int apptId = 107019;
		Date effectiveDate = new Date();
		EmployeeGeneralInformation empInfo = dao.findEmployeeGeneralInfo(apptId, effectiveDate);
		
		Assert.assertNotNull(empInfo);
		
	}

	
	@Test
	public void testEmpGeneralInfo_ValidWithSecurity() {
		int apptId = 107019;
		Date effectiveDate = new Date();
		String userId ="T_HRMND99";
		EmployeeGeneralInformation empInfo = dao.findEmployeeGeneralInfoManagerStatewide(apptId, effectiveDate, userId);
		
		Assert.assertNotNull(empInfo);
		
	}

	@Test
	public void testEmpGeneralInfoWithSecurity_ReturnsNone() {
	    int apptId = 107019;
		Date effectiveDate = new Date();
		String userId ="DONTKNOW";
		EmployeeGeneralInformation empInfo = dao.findEmployeeGeneralInfoManagerStatewide(apptId, effectiveDate, userId);
		
		Assert.assertNull(empInfo);
		
	}
	
	
	@Test
	public void testFindAverageHrs_ReturnsZeroHrs() {
		int apptId = 107019;
		Date effectiveDate = new Date();
		int averageHrs = dao.findAverageHrs(apptId, effectiveDate);
		
		Assert.assertEquals(0, averageHrs);
		
	}
	@Test
	public void testFindAverageHrs_ReturnsAverageHrs() {
		int apptId = 109404;
		Date effectiveDate = new Date("07/14//2007");
		int averageHrs = dao.findAverageHrs(apptId, effectiveDate);
		System.out.println(averageHrs);
		Assert.assertEquals(40, averageHrs);
		
	}

	
	
	
	@Test
	public void testFindDefaultDistributionsExpense_Valid() {
		int apptId = 106936;
		Date effectiveDate = new Date();
		Date distStartDate = new Date("12/10/2008");
		
		String userId = "T_HRMND99";
		List<StandardDistCoding> defDist = dao.findDefaultDistributionsExpense(apptId, effectiveDate, userId, distStartDate);		
		Assert.assertEquals(1, defDist.size());
		System.out.println(defDist.get(0).getSource());
		
	}
	
	@Test
	public void testFindDefaultDistributionsExpense_ReturnsZero() {
		int apptId = 106936;
		Date effectiveDate = new Date();
		Date distStartDate = new Date();		
		String userId = "T_HRMND999";
		List<StandardDistCoding> defDist = dao.findDefaultDistributionsExpense(apptId, effectiveDate, userId, distStartDate);		
		Assert.assertEquals(0, defDist.size());
		//System.out.println(defDist.get(0).getSource());
		
	}
	
	@Test
	public void testFindEmployeeHeaderInfoByApptId(){
		int appointmentId = 107192;
		List<EmployeeHeaderBean> empHeaderList = dao.findEmployeeHeaderInfoByApptId(appointmentId, Calendar.getInstance().getTime());
		Assert.assertTrue(empHeaderList.size() > 0);
	}
	
	@Test
	public void testFindFacsAgencyByExpenseDates_Z_Status_Valid() {
		Date expenseStartDate = new Date("12/10/2008");
		Date expenseEndDate = new Date("12/20/2008");
		int apptId = 107836;

		List<AppointmentsBean> appointmentsList = dao.findFacsAgencyByExpenseDatesByEmployee(expenseStartDate,expenseEndDate,apptId );
		Assert.assertTrue(!appointmentsList.isEmpty());

	}
	
	@Test
	public void testAppointmentDateSpan() {
		int apptId = 1112746;

		AppointmentsBean appt = dao.findActiveAppointmentDateSpan(apptId);
		Assert.assertEquals(appt.getDepartment(), 64);
	}
		
		
	@Test
	public void testStatusFindLatestAppointmentHistoryEmployee() {
		int empId = 133486;
		String appt = dao.findLatestActiveAppointmentHistoryEmployee(empId);
		Assert.assertFalse(appt == null);
   	}
	
	@Test
	public void testFindEmployeeByAppointmentId() {
		int apptId = 107192;
		Integer appt = dao.findEmployeeByAppointmentId(apptId);
		Assert.assertEquals(appt.intValue(), 133509);
   	}
	
	@Test
	public void testFindInactiveStatusForExpenseToDateLeave_NotAllowExpenses(){
		long count = 0;
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 6, 19);	
		java.sql.Date dt = new java.sql.Date(cal.getTimeInMillis());
		count = dao.findInactiveStatusForExpenseToDateLeave(1012107, dt);
		assertTrue(count == 0);
	}	
	
	@Test
	public void testFindInactiveStatusForExpenseToDateLeave_AllowExpenses(){
		long count = 0;
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 6, 18);	
		java.sql.Date dt = new java.sql.Date(cal.getTimeInMillis());
		count = dao.findInactiveStatusForExpenseToDateLeave(1012107, dt);
		assertTrue(count > 0);
	}	
	
	@Test
	public void testFindInactiveStatusForExpenseToDateTerminated_NotAllowExpenses(){
		long count = 0;
		Calendar cal = Calendar.getInstance();
		cal.set(2010, 11, 3);
		java.sql.Date dt = new java.sql.Date(cal.getTimeInMillis());
		count = dao.findInactiveStatusForExpenseToDateTerminated(1121806, dt);
		assertTrue(count == 0);
	}	
	
	@Test
	public void testFindInactiveStatusForExpenseToDateTerminated_AllowExpenses(){
		long count = 0;
		Calendar cal = Calendar.getInstance();
		cal.set(2010, 11, 2);	
		java.sql.Date dt = new java.sql.Date(cal.getTimeInMillis());
		count = dao.findInactiveStatusForExpenseToDateTerminated(1121806, dt);
		assertTrue(count > 0);
	}	
	
	@Test
	public void testFindAppointmentsByEmpIdAndDept(){
		int empId = 1105955;
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 8, 9);	
		Date dt = new Date(cal.getTimeInMillis());
		List<AppointmentListBean> apptList = dao.findAppointmentsByEmpIdAndDept(empId, "43", dt, "CHIDURAS",  "EXPW002");
		assertTrue(apptList.size() > 0);
	}
	
	@Test
	public void testFindAppointmentHistoryByAppointment(){
		Integer apptId = 1138053;
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 6, 14);	
		Date dt = new Date(cal.getTimeInMillis());
		AppointmentHistory apptList = dao.findAppointmentHistory(apptId, dt);
		assertTrue(apptList != null);
	}
	
	@Test
	public void    getAppointmentIdentifier() {
		Calendar cal = Calendar.getInstance();
		cal.set(2011, 11, 11);	
		Date dt = new Date(cal.getTimeInMillis());
		 List<AppointmentHistory> result = dao.getAppointmentIdentifier("94547","EXPW002");
		 List<AppointmentListBean> exp = dao.findAppointmentsByRequestIdEmpIdAndDeptAgencyTku(1073127,"", "", "", dt, "CHIDURAS", "EXPW002", "94547",result); 
		assertTrue(exp.size()>0);
	}

	@Test
	public void getListAppt()
	{
		
	}

	@Test
	public void testfindAppointmentsByEmpIdAndDeptAgencyTkuNoSearchDate(){
		List<AppointmentListBean> apptList = dao.findAppointmentsByEmpIdAndDeptAgencyTkuNoSearchDate(1105955, "43", "04", "007", "CHIDURAS", "EXPW002");
		assertTrue(apptList.size() > 1);
	}	
	
	@Test
	public void testFindLatestAppointmentForFacsAgency(){
		int appt = dao.findLatestAppointmentForFacsAgency(1064440, "47", "49");
		assertEquals(appt, 1165164);
	}	
	
	@Test
	public void testfindAppointmentHistory_validAppt_noSearchDate(){
		AppointmentHistory ah = dao.findAppointmentHistory(1176461);
		assertTrue(ah != null);
	}	
}