package gov.michigan.dit.timeexpense.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotSame;
import gov.michigan.dit.timeexpense.dao.AdvanceDAO;
import gov.michigan.dit.timeexpense.dao.AppointmentDAO;
import gov.michigan.dit.timeexpense.dao.CodingBlockDAO;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetails;
import gov.michigan.dit.timeexpense.model.core.AdvanceEvents;
import gov.michigan.dit.timeexpense.model.core.AdvanceHistory;
import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.Appointment;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.core.VAdvanceList;
import gov.michigan.dit.timeexpense.model.db.Appointments;
import gov.michigan.dit.timeexpense.model.db.PayTypes;
import gov.michigan.dit.timeexpense.model.core.VExpApprovalList;
import gov.michigan.dit.timeexpense.model.display.AdvApprovalTransaction;
import gov.michigan.dit.timeexpense.model.display.AdvanceListItem;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseApprovalTransactionBean;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
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


public class AdvanceDSPTest {

	private AdvanceDSP advanceDSP;
	private static EntityManagerFactory emf;
	private EntityManager em;
	private AdvanceDAO advDao = new AdvanceDAO();
	
	private CommonDAO commonDao;
	private CommonDSP commonService;
	
	private AppointmentDAO appointmentDao;
	private AppointmentDSP appointmentService;
	
	private CodingBlockDSP codingBlockService = new CodingBlockDSP(em);
	private CodingBlockDAO codingBlockDao;
	
	private ExpenseDSP expenseService = new ExpenseDSP(em);
	private ExpenseDAO expenseDao;
	
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
		
		//AdvanceDAO dao = new AdvanceDAO();
		
		em = emf.createEntityManager();
		advanceDSP = new AdvanceDSP (em);

		commonDao = new CommonDAO();
		commonDao.setEntityManager(em);
		//advanceDSP.setCommonDao(commonDao);
		
		/*advanceDSP.setCommonService(commonService);
		advanceDSP.setCommonDao(commonDao);
		*/
		commonService = new CommonDSP (em);
		//advanceDSP.setCommonService(commonService);
		
		appointmentService = new AppointmentDSP (em);
		/*appointmentDao = new AppointmentDAO();
		appointmentDao.setEntityManager(em);
		appointmentService.setAppointmentDao(appointmentDao);*/
		
		codingBlockDao = new CodingBlockDAO();
		codingBlockDao.setEntityManager(em);
		codingBlockService.setCodingBlockDAO(codingBlockDao);
		codingBlockService.setCommonDao(commonDao);
		//advanceDSP.setCodingBlockService(codingBlockService);
		
		//AppointmentDAO apptDao = new AppointmentDAO();
		//apptDao.setEntityManager(em);
		//advanceDSP.setApptDAO(apptDao);
		
		expenseService = new ExpenseDSP (em);
		expenseDao = new ExpenseDAO();
		expenseDao.setEntityManager(em);
		expenseService.setExpenseDAO(expenseDao);
		
		 /*AppointmentDSP apptDsp = new AppointmentDSP(em);
	        AppointmentDAO apptDao = new AppointmentDAO(em);
	        apptDao.setEntityManager(em);
	        apptDsp.setAppointmentDao(apptDao);*/
		
		em.getTransaction().begin();		
	}

	@After
	public void tearDown() throws Exception {
		if (em.getTransaction().isActive())
			em.getTransaction().commit();
		em.close();
	}
		
	@Test
	public void testGetAdvanceListEmployeeNoResults(){
		int employeeId = 9999999;
		String advIncludeAdjustment = "HELLO";
		
		List<VAdvanceList> list = advanceDSP.getAdvanceListEmployee(employeeId,advIncludeAdjustment);
		assertNull(list);
		}
		
	@Test
	public void testGetAdvanceListEmployeeResults(){
		int employeeId = 134067;
		String advIncludeAdjustment = "BOTH";
		
		List<VAdvanceList> list = advanceDSP.getAdvanceListEmployee(employeeId,advIncludeAdjustment);
		assertTrue(list.size() > 0) ;
		}
		
	
	@Test
	public void testGetNonAdjustedAdvanceListByEmployeeIdWithNoResults(){
		int employeeId = 132530;
		String advIncludeAdjustment = "HELP";
		
		List<VAdvanceList> list = advanceDSP.getAdvanceListEmployee(employeeId,advIncludeAdjustment);
		assertNull(list);

		}
	
	@Test
	public void testGetNonAdjustedAdvanceListByEmployeeIdWithResults(){
		int employeeId = 132530;
		String advIncludeAdjustment = "ADV_WO_ADJ";
		
		List<VAdvanceList> list = advanceDSP.getAdvanceListEmployee(employeeId,advIncludeAdjustment);
		assertTrue(list.size() > 0) ;
        
		
		}
	
	@Test
	public void testGetAdjustedAdvanceListByEmployeeIdNoResults(){

        int employeeId = 9999999;
        String advIncludeAdjustment = "ADV_ADJ";
        
		List<VAdvanceList> list = advanceDSP.getAdvanceListEmployee(employeeId,advIncludeAdjustment);
		assertTrue(list.isEmpty());
	}
	
	//This  method tests Adjusted Advance ListId By employee w valid 
	//employeeId and valid advIncludeAdjustment
	
	
	// This test works
	
	@Test
	public void testGetAdjustedAdvanceListByEmployeeIdResults(){
		
		int employeeId = 135348; 
		String advIncludeAdjustment = "ADV_ADJ";
		
		List<VAdvanceList> list = advanceDSP.getAdvanceListEmployee(employeeId,advIncludeAdjustment);
		assertTrue(list.size() > 0);	
	
		}
	
	
	@Test
	public void testGetNonAdjustedAdvanceListByApptIDNoResults(){
//		
        int apptId = 10933;
        String userId = "T_EMP";
        String moduleId = "999999";
        String advIncludeAdjustment = "99999";
		
		List<VAdvanceList> list = advanceDSP.getAdvanceListAppointment(apptId, advIncludeAdjustment, userId, moduleId);
		assertNull(list);
		}
	
	
	//
	//GOOD TEST.  I used the same appt id for both adjusted
	// and not adjusted test changing the value in adj_identifier
	// between tests.
	
	@Test
	public void testGetNonAdjustedAdvanceListByApptIDResults(){
		
		int apptId = 109333;
		String advIncludeAdjustment = "ADV_WO_ADJ";
        String userId = "T_EMP";
        String moduleId = "ADJW011";
          
		List<VAdvanceList> list = advanceDSP.getAdvanceListAppointment(apptId, advIncludeAdjustment, userId, moduleId);
		assertTrue(list.size() > 0);
		
	}	
	
	
	//This method tests the Adjusted Advance List by Appt with invalid data
	// Good test
	
	@Test
	public void testGetAdjustedAdvanceListbyApptNoResults(){
		
		int apptId = 10933;
        String userId = "T_EMP";
        String moduleId = "999999";
        String advIncludeAdjustment = "99999";
        
        List<VAdvanceList> list = advanceDSP.getAdvanceListAppointment(apptId, advIncludeAdjustment, userId, moduleId);
		assertNull(list);
		}
        
	
	//This method tests the Adjusted Advance List by Appt with valid data
	// good test
	
	@Test
	public void testGetAdjustedAdvanceListbyApptResults(){
		
		int apptId = 109333;
        String userId = "T_EMP";
        String moduleId = "ADJW011";
        String advIncludeAdjustment = "ADV_ADJ";
        
        List<VAdvanceList> list = advanceDSP.getAdvanceListAppointment(apptId, advIncludeAdjustment, userId, moduleId);
        assertTrue(list.size() > 0);
		}
	
	
	
	//This method retrieves the Adjusted advances by appointment id
	// good test
	@Test
	public void testGetAdjustedAdvanceListByApptIDNoResults(){
	
        int apptId = 109333;
        String advIncludeAdjustment = "HELP";
        String userId = "T_EMP";
        String moduleId = "ADJW011";
		
		List<VAdvanceList> list = advanceDSP.getAdvanceListAppointment(apptId, advIncludeAdjustment, userId, moduleId);
		assertNull(list);
		}
	
	
	//good test
	///
	///didn't get good test today
	///
	@Test
	public void testGetAdjustedAdvanceListByApptIDResults(){
	
        int apptId = 109333;  
        String advIncludeAdjustment = "BOTH";
        String userId = "T_EMP";
        String moduleId = "ADJW011";
        
        
		List<VAdvanceList> list = advanceDSP.getAdvanceListAppointment(apptId, advIncludeAdjustment, userId, moduleId);
		assertTrue(list.size() > 0);
		
		}
	
	
	//This method is used to get advances pending approval for all employees working
	//under a supervisor
	
	public void testGetAdvancesAwaitingApprovalResults(){
		
		int supervisorEmpId= 134874;
		String requestType = "ADV";
		
		List<AdvApprovalTransaction> list = advanceDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(supervisorEmpId, requestType);
//		assertNotNull(list);
		assertTrue(list.size() > 0);
		
	}
	
	//This method retrieves the AdvanceMasters by AdvanceMasterId
	//good test
	@Test	
		public void testgetAdvanceByMasterIdResults(){
		AdvanceMasters Master= advanceDSP.getAdvanceByMasterId(15);
		assertNotNull(Master);
	}
	
	//test worked
	@Test	
		public void testgetAdvanceByMasterIdNoResults(){
		AdvanceMasters Master= advanceDSP.getAdvanceByMasterId(99);
		assertNull(Master);
	}
	
	
	@Test 
	public void testgetAdvanceByEventIdResults(){
		
		int revisionNo = 1;
		
		AdvanceMasters mastersOrig = advanceDSP.getAdvanceByMasterId(10);
		AdvanceEvents events = mastersOrig.getAdevIdentifier();
		AdvanceMasters mastersRetreive =  advanceDSP.getAdvanceByAdvanceEventId(events, revisionNo);
		Assert.assertNotNull(mastersRetreive);
	
	}
	
	@Test 
	public void testgetAdvanceByEventIdNoResults(){
		
		int revisionNo = 5;
		
		AdvanceMasters mastersOrig = advanceDSP.getAdvanceByMasterId(10);
		AdvanceEvents events = mastersOrig.getAdevIdentifier();
		AdvanceMasters mastersRetreive =  advanceDSP.getAdvanceByAdvanceEventId(events, revisionNo);
		Assert.assertNull(mastersRetreive);
	
	
	}
	
	
	@Test
	
	public void testgetAdvanceHistory(){
			
	AdvanceEvents advanceEvent = advanceDSP.getAdvanceEvent(15);
		
	List<AdvanceHistory> historyList = advanceDSP.getAdvanceHistory(advanceEvent);
		assertTrue(!historyList.isEmpty());		
		}
	

	@Test
	public void testSubmitAdvanceResults(){
		UserSubject subject = this.createUserSubject();
		UserProfile profile = this.createUserProfile();
		AdvanceMasters masterBefore = advanceDSP.getAdvanceByMasterId(1876);
		AdvanceMasters masterAfter =  advanceDSP.submitAdvance(masterBefore, subject, profile, "");
		Assert.assertEquals( masterAfter.getStatus(),IConstants.SUBMIT);
	}
	
	
	///////////////////////////////////////////////////
	//testing to see if advance master status set to "RJCT"
	// and adding record to advance action 
	// good test
	@Test
	
	public void testRejectAdvanceResults(){
		UserSubject subject = this.createUserSubject();
		UserProfile profile = this.createUserProfile();
		AdvanceMasters master = advanceDSP.getAdvanceByMasterId(1876);
		master = advanceDSP.rejectAdvance(master, subject, profile, "");
		Assert.assertEquals(master.getStatus(), "RJCT");
	}
	
	@Test
	public void testApproveAdvance() {
		UserSubject subject = this.createUserSubject();
		UserProfile profile = this.createUserProfile();
		AdvanceMasters advanceMaster = advanceDSP.getAdvanceByMasterId(1876);
		advanceMaster = advanceDSP.approveAdvance(advanceMaster, subject, profile, "");
		assertEquals(advanceMaster.getStatus(), IConstants.APPROVED);
	}
	
	//Testing that no max revision number is retrieved for an 
	//Advance Master using an invalid  adev_id
	
	// ZAck helping set up test condition?????assert
	@Test
	
	public void testGetMaxRevisionNoNoResults(){
		int advanceEventId = 99;
		
		int revisionno = advanceDSP.getMaxRevisionNo(advanceEventId);
		System.out.print(revisionno);
		assertEquals(revisionno, null);
	}

	
	///////////////////////////////////////////////////////////
	
	//Testing that the max revision number is retrieved for an 
	//Advance Master using the adev_id
	
	//good test
@Test
	
	public void testGetMaxRevisionResults(){
		int advanceEventId = 16;
		
		int revisionno = advanceDSP.getMaxRevisionNo(advanceEventId);
		assertEquals(revisionno, 0);
		System.out.print("Revision No is  " + revisionno);
	}
		
	
	
	@Test
	public void testSaveAdvanceNewNoCodingBlocks() {
		UserProfile userProfile = new UserProfile ("T_EMP", 135348);
		UserSubject subject = this.createUserSubject();
		int appointmentId = 109333;
		AdvanceMasters advanceMasters = this.createNewAdvanceMastersNoErrors();
		advanceDSP.saveAdvance(advanceMasters, appointmentId, "ADVW001", userProfile, subject);
		AdvanceMasters advanceMastersAfterSave = advanceDSP.getAdvanceByMasterId(advanceMasters.getAdvmIdentifier());
		assertEquals(advanceMastersAfterSave.getAdevIdentifier().getApptIdentifier(), 109333);		
		
	}
	
	@Test
	public void testSaveAdvanceNewWithErrorsNoCodingBlocks() {
		int appointmentId = 109333;
		UserProfile userProfile = new UserProfile ("T_EMP", 135348);
		UserSubject subject = this.createUserSubject();
		AdvanceMasters advanceMasters = this.createNewAdvanceMastersDateErrors();
		advanceDSP.saveAdvance(advanceMasters, appointmentId, "ADVW001", userProfile, subject);
		AdvanceMasters advanceMastersAfterSave = advanceDSP.getAdvanceByMasterId(advanceMasters.getAdvmIdentifier());
		assertEquals(advanceMastersAfterSave.getAdevIdentifier().getApptIdentifier(), 109333);
	}
	
	@Test
	public void testSaveAdvanceNewWithManualWarrantErrorsNoCodingBlocks() {
		int appointmentId = 109333;
		UserProfile userProfile = new UserProfile ("T_EMP", 135348);
		UserSubject subject = this.createUserSubject();
		AdvanceMasters advanceMasters = this.createNewAdvanceMastersNoErrors();
		advanceMasters.setManualWarrantIssdInd("Y");
		advanceDSP.saveAdvance(advanceMasters, appointmentId, "ADVW001", userProfile, subject);
		AdvanceMasters advanceMastersAfterSave = advanceDSP.getAdvanceByMasterId(advanceMasters.getAdvmIdentifier());
		assertEquals(advanceMastersAfterSave.getAdevIdentifier().getApptIdentifier(), 109333);
	}
	
	@Test
	public void testSaveAdvanceNewWithManualDepositErrorsNoCodingBlocks() {
		int appointmentId = 109333;
		UserProfile userProfile = new UserProfile ("T_EMP", 135348);
		UserSubject subject = this.createUserSubject();
		AdvanceMasters advanceMasters = this.createNewAdvanceMastersNoErrors();
		advanceMasters.setManualDepositInd("Y");
		advanceDSP.saveAdvance(advanceMasters, appointmentId, "ADVW001", userProfile, subject);
		AdvanceMasters advanceMastersAfterSave = advanceDSP.getAdvanceByMasterId(advanceMasters.getAdvmIdentifier());
		assertEquals(advanceMastersAfterSave.getAdevIdentifier().getApptIdentifier(), 109333);
	}
	
	@Test
	public void testSaveAdvanceNewWithCodingBlocksSingleRowStd() {
		UserProfile userProfile = new UserProfile ("T_EMP", 135348);
		UserSubject subject = this.createUserSubject();
		int appointmentId = 109333;
		AdvanceMasters advanceMasters = this.createNewAdvanceMastersNoErrorsWithCodingBlocksSingleRowStd();
		advanceDSP.saveAdvance(advanceMasters, appointmentId, "ADVW001", userProfile, subject);
		AdvanceMasters advanceMastersAfterSave = advanceDSP.getAdvanceByMasterId(advanceMasters.getAdvmIdentifier());
		assertEquals(advanceMastersAfterSave.getAdevIdentifier().getApptIdentifier(), 109333);		
		
	}
	
	@Test
	public void testSaveAdvanceNewWithCodingBlocksSingleRowIndex() {
		UserProfile userProfile = new UserProfile ("T_EMP", 135348);
		UserSubject subject = this.createUserSubject();
		int appointmentId = 109333;
		AdvanceMasters advanceMasters = this.createNewAdvanceMastersWithCodingBlocksSingleRowIndex();
		advanceDSP.saveAdvance(advanceMasters, appointmentId, "ADVW001", userProfile, subject);
		AdvanceMasters advanceMastersAfterSave = advanceDSP.getAdvanceByMasterId(advanceMasters.getAdvmIdentifier());
		assertEquals(advanceMastersAfterSave.getAdevIdentifier().getApptIdentifier(), 109333);		
		
	}
	
	@Test
	public void testSaveAdvanceExistingCurrentRevision() {
		int appointmentId = 109333;
		UserProfile userProfile = new UserProfile ("T_EMP", 135348);
		UserSubject subject = this.createUserSubject();
		AdvanceMasters advanceMasters = advanceDSP.getAdvanceByMasterId(1523);
		advanceMasters.setAdvanceReason("Testing create new revision");
		advanceMasters.setModifiedUserId(userProfile.getUserId());
		int apptIdentifier = advanceMasters.getAdevIdentifier().getApptIdentifier();
		advanceDSP.saveAdvance(advanceMasters, appointmentId, "ADVW001", userProfile, subject);		
	}
	
	@Test
	public void testSaveAdvanceExistingNewRevision() {
		int appointmentId = 109333;
		UserProfile userProfile = new UserProfile ("T_EMP", 135348);
		UserSubject subject = this.createUserSubject();
		AdvanceMasters advanceMasters = advanceDSP.getAdvanceByMasterId(1492);
		advanceMasters.setAdvanceReason("Testing create new revision");
		advanceMasters.setModifiedUserId(userProfile.getUserId());
		int apptIdentifier = advanceMasters.getAdevIdentifier().getApptIdentifier();
		advanceDSP.saveAdvance(advanceMasters, appointmentId, "ADVW001", userProfile, subject);		
	}
	
	@Test
	public void testSaveAdvanceExistingNewRevisionAddAdjIdentifier() {
		int appointmentId = 109333;
		UserProfile userProfile = new UserProfile ("T_EMP", 135348);
		UserSubject subject = this.createUserSubject();
		AdvanceMasters advanceMasters = advanceDSP.getAdvanceByMasterId(1534);
		advanceMasters.setAdvanceReason("Testing create new revision");
		advanceMasters.setModifiedUserId(userProfile.getUserId());
		int apptIdentifier = advanceMasters.getAdevIdentifier().getApptIdentifier();
		advanceDSP.saveAdvance(advanceMasters, appointmentId, "ADVW001", userProfile, subject);		
	}
	
	@Test
	public void testValidateDates() {
		
		AdvanceMasters advanceMasters = advanceDSP.getAdvanceByMasterId(10102);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1999);
		advanceMasters.setRequestDate(cal.getTime());
		System.out.println(cal.getTime());
		advanceMasters.setToDate(cal.getTime());
		CommonDAO commonDao = new CommonDAO ();
		commonDao.setEntityManager(em);
		advanceDSP.setCommonDao(commonDao);
		
		advanceMasters.setFromDate(Calendar.getInstance().getTime());	
		advanceDSP.validateDates(advanceMasters, new UserProfile("T_HRMND99"));
		if (advanceMasters.getAdvanceErrorsCollection() != null)
			assertNotSame(advanceMasters.getAdvanceErrorsCollection().size(), 0);
		else
			System.out.println("try again");
	}
	
	
	
	@Test
	public void testSaveAdvanceExistingInsertErrors() {
		int appointmentId = 109333;
		UserProfile userProfile = new UserProfile ("T_EMP", 135348);
		UserSubject subject = this.createUserSubject();
		AdvanceMasters advanceMasters = advanceDSP.getAdvanceByMasterId(1524);
		Calendar calForError = Calendar.getInstance();
		calForError.set(Calendar.YEAR, 1999);
		Date reqDate =  calForError.getTime();
		System.out.println(reqDate);
		advanceMasters.setRequestDate(reqDate);
		Date fromDate = Calendar.getInstance().getTime();
		advanceMasters.setFromDate(fromDate);
		Date toDate = calForError.getTime();
		advanceMasters.setToDate(toDate);
		advanceMasters.setModifiedUserId(userProfile.getUserId());
		advanceDSP.saveAdvance(advanceMasters, appointmentId, "ADVW001", userProfile, subject);
		AdvanceMasters advanceMastersAfterSave = advanceDSP.getAdvanceByMasterId(1137);
		assertEquals(advanceMastersAfterSave.getAdvanceErrorsCollection().size(), 2);
	}
	
	@Test
	public void testMultipleAppointments() {
		
		UserProfile userProfile = new UserProfile ("T_HRMND99", 133509);
		UserSubject subject = this.createUserSubject();
		AdvanceMasters advanceMasters = advanceDSP.getAdvanceByMasterId(10102);		
		List<AppointmentsBean> apptList = advanceDSP.getAppointmentsForDateRange(advanceMasters.getFromDate(), 
				advanceMasters.getToDate(), IConstants.ADVANCE_EMPLOYEE, subject, userProfile);
		assertEquals(apptList.size() , 2);
	}
	
	@Test
	public void testGetPayType() {
		advanceDSP.setCommonService(commonService);
		Integer paytype = advanceDSP.getPayType(IConstants.ADVANCE_PAYROLL_SYSTEM_CODE, 
				Calendar.getInstance().getTime());		
		System.out.println("Pay Type is: " + paytype);
		assertNotNull(paytype);
	}
	
/*	@Test
	public void testprepareAdvanceCodingBlocks() {
		AdvanceMasters master = advanceDSP.getAdvanceByMasterId(1476);
		String store = advanceDSP.prepareAdvanceCodingBlocks(master);
		System.out.println(store);
		assertNotNull(store);
	}
*/	
	@Test
	public void testGetAdvanceEligibilityYes() {
		Appointment appointment = appointmentService.findAppointment(109798);
		boolean eligible = advanceDSP.getAdvanceEligibility(appointment.getId());
		assertTrue(eligible);
	}
	
	@Test
	public void testGetAdvanceEligibilityNo() {
		Appointments appointment = appointmentDao.findAppointment(106934);
		boolean eligible = advanceDSP.getAdvanceEligibility(appointment.getApptIdentifier());
		assertTrue(eligible);
	}
	
	@Test
	public void testDeleteAdvance() {
		assertTrue(advanceDSP.deleteAdvanceEvent(1118));
	}
	
/*	@Test
	public void testGetAdvanceOutstandingList() {
		List<VAdvanceList> advanceList = advanceDSP.getAdvanceOutstandingList(135348, 9);
		assertTrue(!advanceList.isEmpty());
	}*/
	
	@Test
	public void testGetAdvanceOutstandingAmount() {
		double amountOutstanding = 0.00;
		amountOutstanding = advanceDSP.getTotalAdvanceOutstandingAmount(134720);
		assertTrue(amountOutstanding > 0.00);
	}
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorMyEmployees() {
		List<AdvApprovalTransaction> advances = advanceDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);
		assertTrue(!advances.isEmpty());
	}	
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorMyEmployeesAdjusted() {
		List<AdvApprovalTransaction> advances = advanceDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_ADJUSTED_ONLY);
		assertTrue(!advances.isEmpty());
	}	
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorMyEmployeesNonAdjusted() {
		List<AdvApprovalTransaction> advances = advanceDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY);
		assertTrue(!advances.isEmpty());
	}	
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorAllEmployees() {
		List<AdvApprovalTransaction> advances = advanceDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);
		assertTrue(!advances.isEmpty());
	}
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorAllEmployeesAdjusted() {
		List<AdvApprovalTransaction> advances = advanceDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_ADJUSTED_ONLY);
		assertTrue(!advances.isEmpty());
	}	
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorAllEmployeesNonAdjusted() {
		List<AdvApprovalTransaction> advances = advanceDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY);
		assertTrue(!advances.isEmpty());
	}
	
		@Test
	public void testGetFacsAgency(){
		Date date = Calendar.getInstance().getTime();
		String facsAgency = appointmentService.getFacsAgency(date, date, 109744);
		Assert.assertEquals( facsAgency,"591");
	}
	
	@Test
	public void testAdvanceLiquidations() {
		UserProfile userProfile = new UserProfile ("T_EMP", 135348);
		ExpenseMasters expenseMasters = expenseService.getExpense(1262);
		AdvanceMasters advanceMasters = advanceDSP.getAdvanceByMasterId(1547);
		List<AdvanceLiquidations> liquidationsList = new ArrayList<AdvanceLiquidations> ();
		AdvanceLiquidations liquidation = new AdvanceLiquidations ();
		liquidation.setAdvanceMaster(advanceMasters);
		liquidation.setExpenseMasterId(expenseMasters.getExpmIdentifier());
		liquidation.setAmount(10);
		liquidationsList.add(liquidation);
		advanceDSP.liquidate(liquidationsList, userProfile, expenseMasters);
		AdvanceMasters advanceMastersAfterLiquidation = advanceDSP.getAdvanceByMasterId(1547);
		assertTrue(!advanceMastersAfterLiquidation.getAdvanceLiquidationsCollection().isEmpty());
	    }
	
	@Test
	public void testSubmitWithCbErrors() {
		UserSubject subject = this.createUserSubject();
		AdvanceMasters advanceMaster = advanceDSP.getAdvanceByMasterId(1980);
		boolean canSubmit = advanceDSP.submitWithCbErrors(subject, advanceMaster.getAdvanceErrorsCollection());
		assertTrue(canSubmit);
	    }
	
	
	// commented for compile
	/*@Test
	public void testGetAdvanceLiquidationsAmount() {
		AdvanceMasters advanceMaster = advanceDSP.getAdvanceByMasterId(2351);
		double liqAmount = advanceDSP.getAdvanceLiquidationsAmount(advanceMaster);
		assertEquals(liqAmount, 0);
	    }
	*/
	private AdvanceMasters createNewAdvanceMastersNoErrors(){
		
		AdvanceMasters advanceMasters = new AdvanceMasters();
		advanceMasters.setRevisionNumber(new Short ("0"));
		advanceMasters.setCurrentInd("Y");
		advanceMasters.setPermanentAdvInd("N");
		advanceMasters.setManualWarrantIssdInd("N");
		advanceMasters.setManualWarrantIssdInd("N");
		advanceMasters.setManualDepositInd("N");
		advanceMasters.setRequestDate(new Date());
		advanceMasters.setFromDate(Calendar.getInstance().getTime());
		advanceMasters.setToDate(Calendar.getInstance().getTime());
		// add details
		List<AdvanceDetails> detailsList = new ArrayList<AdvanceDetails> ();
		AdvanceDetails details = new AdvanceDetails ();
		details.setAdvmIdentifier(advanceMasters);
		details.setDollarAmount(100.00);
		details.setPytpIdentifier(advanceDSP.getPayType(IConstants.ADVANCE_PAYROLL_SYSTEM_CODE,
				Calendar.getInstance().getTime()));
		detailsList.add(details);
		// add details to master
		advanceMasters.setAdvanceDetailsCollection(detailsList);
		return advanceMasters;
	}
	
	private AdvanceMasters createNewAdvanceMastersDateErrors(){
		
		AdvanceMasters advanceMasters = new AdvanceMasters();
		advanceMasters.setRevisionNumber(new Short ("0"));
		advanceMasters.setCurrentInd("Y");
		advanceMasters.setPermanentAdvInd("N");
		advanceMasters.setManualWarrantIssdInd("N");
		advanceMasters.setManualWarrantIssdInd("N");
		advanceMasters.setManualDepositInd("N");
		Calendar calForError = Calendar.getInstance();
		calForError.set(Calendar.YEAR, 1999);
		Date reqDate =  calForError.getTime();
		System.out.println(reqDate);
		advanceMasters.setRequestDate(reqDate);
		Date fromDate = Calendar.getInstance().getTime();
		advanceMasters.setFromDate(fromDate);
		Date toDate = calForError.getTime();
		advanceMasters.setToDate(toDate);
		List<AdvanceDetails> detailsList = new ArrayList<AdvanceDetails> ();
		AdvanceDetails details = new AdvanceDetails ();
		details.setAdvmIdentifier(advanceMasters);
		details.setDollarAmount(100.00);
		detailsList.add(details);
		advanceMasters.setAdvanceDetailsCollection(detailsList);
		return advanceMasters;
	}
	
	private AdvanceMasters createNewAdvanceMastersNoErrorsWithCodingBlocksSingleRowStd(){
		
		AdvanceMasters advanceMasters = this.createNewAdvanceMastersNoErrors();
		// add coding blocks
		List<AdvanceDetailCodingBlocks> cbList = new ArrayList<AdvanceDetailCodingBlocks> ();
		AdvanceDetailCodingBlocks cb = new AdvanceDetailCodingBlocks ();
		cb.setAdvdIdentifier(advanceMasters.getAdvanceDetailsCollection().get(0));
		cb.setPercent(1);
		cb.setDollarAmount(100);
		cb.setFacsAgy("591");
		cb.setStandardInd("Y");
		cbList.add(cb);
		// add coding block list to details
		advanceMasters.getAdvanceDetailsCollection().get(0).setAdvanceDetailCodingBlocksCollection(cbList);
	
		return advanceMasters;
	}
	
	private AdvanceMasters createNewAdvanceMastersWithCodingBlocksSingleRowIndex(){
		
		AdvanceMasters advanceMasters = this.createNewAdvanceMastersNoErrors();
		// add coding blocks
		List<AdvanceDetailCodingBlocks> cbList = new ArrayList<AdvanceDetailCodingBlocks> ();
		AdvanceDetailCodingBlocks cb = new AdvanceDetailCodingBlocks ();
		cb.setAdvdIdentifier(advanceMasters.getAdvanceDetailsCollection().get(0));
		cb.setPercent(1);
		cb.setDollarAmount(100);
		cb.setFacsAgy("591");
		cb.setIndexCode("00206");
		cb.setStandardInd("N");
		cbList.add(cb);
		// add coding block list to details
		advanceMasters.getAdvanceDetailsCollection().get(0).setAdvanceDetailCodingBlocksCollection(cbList);
	
		return advanceMasters;
	}
	
    @Test
    public void testGetAdvanceListByEmpId (){
    	ExpenseMasters expenseMaster = expenseService.getExpense(2487);
    	List<AdvanceMasters> advanceMasterList = advanceDSP.getAdvanceListByEmpId(134720, expenseMaster);
    	System.out.println("size is: "+ advanceMasterList.size());
    	assertTrue(advanceMasterList.size() > 1);
    }
	
	private UserSubject createUserSubject (){
		UserSubject subject = new UserSubject(134067, 109744, null, null, "59", "01", "100", null);
		return subject;
	}
	
	private UserProfile createUserProfile (){
		UserProfile profile = new UserProfile("T_DEPT99", 134067);
		return profile;
	}
	
	/* @Test
	public void testGetLiquidationsList (){
		 AdvanceMasters advanceMasters = advanceDSP.getAdvanceByMasterId(2351);
		 AdvanceLiquidations liq = advanceMasters.getAdvanceLiquidationsCollection().get(0);
		 List<AdvanceLiquidations> liqList = advanceDSP.getLiquidationsList(liq);
		 assertEquals(liqList.size(), 1);
	}*/
	 
	// ZH - Commented before build
	 @Test
		public void testCalculateOutstandingAdvanceAmount (){
		/* AdvanceMasters advanceMasters = advanceDSP.getAdvanceByMasterId(2351);
		 assertTrue(advanceDSP.calculateOutstandingAdvanceAmount(advanceMasters) > 0);*/
	 }
	 
	 @Test
		public void testCalculateLiquidatedByExpenseAmount (){
		 AdvanceMasters advanceMasters = advanceDSP.getAdvanceByMasterId(2605);
		 assertEquals(advanceDSP.calculateAmountLiquidatedByExpense(advanceMasters), 25);
	 }
	

		
}
