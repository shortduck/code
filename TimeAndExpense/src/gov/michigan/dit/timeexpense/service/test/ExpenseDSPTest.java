/**
 * ExpenseDSPTest
 * 
 * @Author SG
 */


package gov.michigan.dit.timeexpense.service.test;

import gov.michigan.dit.timeexpense.exception.ExpenseException;
import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.DriverReimbExpTypeCbs;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseHistory;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.OutOfStateTravel;
import gov.michigan.dit.timeexpense.model.core.StateAuthCodes;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.core.VExpensesList;
import gov.michigan.dit.timeexpense.model.db.Appointments;
import gov.michigan.dit.timeexpense.model.display.ExpenseListBean;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExpenseDSPTest {

	private ExpenseDSP expenseDSP;
	private AdvanceDSP advanceService;
	private CommonDSP commDsp;
	
	private static EntityManagerFactory emf;
	private EntityManager em;

	UserProfile userProfile;
	SecurityManager securityService;

	private final int VALID_USER_EMP_ID = 999999999;
	Logger logger = Logger.getLogger(ExpenseDSPTest.class);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
	}

	@Before
	public void setUp() throws Exception {

		em = emf.createEntityManager();
		securityService = new SecurityManager(em);
		advanceService = new AdvanceDSP(em);
		expenseDSP = new ExpenseDSP(em);
		commDsp = new CommonDSP(em);
	}

	@Test
	public void testGetExpense() {
		int expenseMasterId = 100;
		ExpenseMasters expenseMasters = expenseDSP.getExpense(expenseMasterId);
		Assert.assertNotNull(expenseMasters);
	}

	@Test
	public void testSaveExpense_newRevisionNullAdjId() throws Exception {
		int expenseMasterId = 102;
		String moduleId = "PRMW027";
		int appointmentId = 107192;
		UserProfile userProfile = securityService
				.getDcdsUser(VALID_USER_EMP_ID, true);

		Appointments appointments = new Appointments();
		appointments.setApptIdentifier(107192);

		ExpenseMasters expenseMasters = expenseDSP.getExpense(expenseMasterId);
		expenseMasters.setComments("DING");
		UserSubject subject = new UserSubject();
		subject.setEmployeeId(1077335);
		subject.setAppointmentId(1139330);
		ExpenseMasters savedExpense = expenseDSP.saveExpense(expenseMasters,subject, moduleId, userProfile);
		Assert.assertNotNull(savedExpense);
	}
	
	@Test
	public void testSaveExpense_newRevisionWithAdjId() throws Exception {
		int expenseMasterId = 99;
		String moduleId = "PRMW027";
		int appointmentId = 107192;
		UserProfile userProfile = securityService
				.getDcdsUser(VALID_USER_EMP_ID, true);

		Appointments appointments = new Appointments();
		appointments.setApptIdentifier(107192);

		ExpenseMasters expenseMasters = expenseDSP.getExpense(expenseMasterId);
		expenseMasters.setComments("DING");
		UserSubject subject = new UserSubject();
		subject.setEmployeeId(1077335);
		subject.setAppointmentId(1139330);
		ExpenseMasters savedExpense = expenseDSP.saveExpense(expenseMasters,subject, moduleId, userProfile);
		Assert.assertNotNull(savedExpense);
	}

	@Test
	public void testValidateDate_TEStartDateFiscalYearSpanValidate() throws Exception{

		// Time and Expense Start Date 12/31/2008
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.YEAR, 2008);
		Date expDateFrom = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, 12);
		cal.set(Calendar.MONTH, 07);
		cal.set(Calendar.YEAR, 2009);
		Date expDateTo = cal.getTime();
		
		ExpenseMasters expenseMasters = expenseDSP.getExpense(100);
		expenseMasters.setExpDateFrom(expDateFrom);
		expenseMasters.setExpDateTo(expDateTo);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseMasters = commDsp.deleteExpenseErrors(expenseMasters, IConstants.EXP_ERR_SRC_ID_TAB);
		expenseDSP.validateDate(expenseMasters, userProfile);

		int noOfErrors = expenseMasters.getExpenseErrorsCollection().size();
		for (int i = 0; i < noOfErrors; i++) {
			System.out.println(expenseMasters.getExpenseErrorsCollection().get(
					i).getErrorCode().getErrorCode()
					+ "   "
					+ expenseMasters.getExpenseErrorsCollection().get(i)
							.getErrorCode().getErrorText());
		}
		System.out.println(noOfErrors);
		Assert.assertTrue(noOfErrors>0);
	}
	
	public ExpenseMasters prepareExpenseMasters(){


		ExpenseEvents expenseEvents = new ExpenseEvents();
		expenseEvents.setAppointmentId(107192);
		
		expenseEvents = expenseDSP.saveExpenseEvent(expenseEvents);

		ExpenseMasters expenseMasters = new ExpenseMasters();

		expenseMasters.setComments("Test Expense Data");
		expenseMasters.setCurrentInd("Y");
		expenseMasters.setExpevIdentifier(expenseEvents);
		expenseMasters.setNatureOfBusiness("Personal");
		expenseMasters.setTravelInd("Y");
		expenseMasters.setSuperReviewedReceiptsInd("Y");
		expenseMasters.setOutOfStateInd("N");
		expenseMasters.setStatus("SUBM");

		expenseMasters.setExpenseErrorsCollection(null);
		expenseMasters.setExpenseDetailsCollection(null);

		List<ExpenseMasters> expMastersList = new ArrayList<ExpenseMasters>();
		expMastersList.add(expenseMasters);
		
		expenseEvents.setExpenseMastersCollection(expMastersList);
		
		return expenseMasters;
	}
	
	@Test
	public void testSaveExpenseNew(){
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_MONTH, 2);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.YEAR, 2009);
		Date expDateFrom = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, 2);
		cal.set(Calendar.MONTH, 14);
		cal.set(Calendar.YEAR, 2009);
		Date expDateTo = cal.getTime();
		
		ExpenseMasters expenseMasters = prepareExpenseMasters();
		expenseMasters.setExpDateFrom(expDateFrom);
		expenseMasters.setExpDateTo(expDateTo);
		
		UserProfile userProfile = new UserProfile("Sourav");
		expenseDSP.saveNewExpense(expenseMasters, 107192, "EXP001", userProfile);
	}

	@Test
	public void testValidateExpenseDates_ExpStartEndDateInvalid() throws Exception{

		// Time and Expense Start Date 12/31/2008
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.YEAR, 2009);
		Date expDateFrom = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, 12);
		cal.set(Calendar.MONTH, 07);
		cal.set(Calendar.YEAR, 2007);
		Date expDateTo = cal.getTime();

		ExpenseMasters expenseMasters = expenseDSP.getExpense(100);

		expenseMasters = commDsp.deleteExpenseErrors(expenseMasters, IConstants.EXP_ERR_SRC_ID_TAB);
		expenseMasters.setExpDateFrom(expDateFrom);
		expenseMasters.setExpDateTo(expDateTo);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseDSP.validateDate(expenseMasters, userProfile);
		int noOfErrors = 0;
		if(expenseMasters.getExpenseErrorsCollection()!=null){
			noOfErrors = expenseMasters.getExpenseErrorsCollection().size();
	
			for (int i = 0; i < noOfErrors; i++) {
				System.out.println(expenseMasters.getExpenseErrorsCollection().get(
						i).getErrorCode().getErrorCode()
						+ "   "
						+ expenseMasters.getExpenseErrorsCollection().get(i)
								.getErrorCode().getErrorText());
			}
		}
		System.out.println(noOfErrors);
		Assert.assertTrue(noOfErrors>0);
	}

	@Test
	public void testValidateExpenseDates_ExpStartEndDateTEStartDate_InValid() throws Exception{

		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.YEAR, 2008);
		Date expDateFrom = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, 12);
		cal.set(Calendar.MONTH, 07);
		cal.set(Calendar.YEAR, 2007);
		Date expDateTo = cal.getTime();
	
		ExpenseMasters expenseMasters = expenseDSP.getExpense(100);
		
		expenseMasters.setExpDateFrom(expDateFrom);
		expenseMasters.setExpDateTo(expDateTo);
		
		expenseMasters = commDsp.deleteExpenseErrors(expenseMasters, IConstants.EXP_ERR_SRC_ID_TAB);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseDSP.validateDate(expenseMasters,userProfile);
		
		int noOfErrors =0;
		if(expenseMasters.getExpenseErrorsCollection()!=null){
			noOfErrors = expenseMasters.getExpenseErrorsCollection().size();
			for (int i = 0; i < noOfErrors; i++) {
				System.out.println(expenseMasters.getExpenseErrorsCollection().get(
						i).getErrorCode().getErrorCode()
						+ "   "
						+ expenseMasters.getExpenseErrorsCollection().get(i)
								.getErrorCode().getErrorText());
			}
		}
		Assert.assertTrue(noOfErrors>0);
	}

	@Test
	public void testValidateOutOfStateTravel_valid() {
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 12);
		cal.set(Calendar.YEAR, 2009);
		Date expDateFrom = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, 12);
		cal.set(Calendar.MONTH, 07);
		cal.set(Calendar.YEAR, 2009);
		Date expDateTo = cal.getTime();

		ExpenseMasters expenseMasters = prepareExpenseMasters();
		
		expenseMasters.setExpDateFrom(expDateFrom);
		expenseMasters.setExpDateTo(expDateTo);

		OutOfStateTravel outState = expenseDSP.getOutOfStateTravel(1);

		List<OutOfStateTravel> outStateList = new ArrayList<OutOfStateTravel>();
		outStateList.add(outState);

		expenseMasters.setOutOfStateTravelList(outStateList);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseDSP.validateOutOfStateTravel(expenseMasters, userProfile);
		int noOfErrors=0;
		if(expenseMasters.getExpenseErrorsCollection()!=null){
			noOfErrors = expenseMasters.getExpenseErrorsCollection().size();
			for (int i = 0; i < noOfErrors; i++) {
				System.out.println(expenseMasters.getExpenseErrorsCollection().get(
					i).getErrorCode().getErrorCode()
					+ "   "
					+ expenseMasters.getExpenseErrorsCollection().get(i)
							.getErrorCode().getErrorText());
			}
		}

		System.out.println(noOfErrors);
		Assert.assertEquals(0, noOfErrors);

	}
	
	@Test
	public void testValidateOutOfStateTravel_InValid() {
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_MONTH, 01);
		cal.set(Calendar.MONTH, 12);
		cal.set(Calendar.YEAR, 2009);
		Date expDateFrom = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, 12);
		cal.set(Calendar.MONTH, 07);
		cal.set(Calendar.YEAR, 2009);
		Date expDateTo = cal.getTime();

		ExpenseMasters expenseMasters = prepareExpenseMasters();
		
		expenseMasters.setExpDateFrom(expDateFrom);
		expenseMasters.setExpDateTo(expDateTo);
		expenseMasters.setOutOfStateInd("Y");
		
		OutOfStateTravel outState = expenseDSP.getOutOfStateTravel(5);

		List<OutOfStateTravel> outStateList = new ArrayList<OutOfStateTravel>();
		//outStateList.add(outState);

		expenseMasters.setOutOfStateTravelList(outStateList);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseDSP.validateOutOfStateTravel(expenseMasters, userProfile);

		int noOfErrors = 0;
		if(expenseMasters.getExpenseErrorsCollection()!=null){
			noOfErrors = expenseMasters.getExpenseErrorsCollection().size();
			for (int i = 0; i < noOfErrors; i++) {
				System.out.println(expenseMasters.getExpenseErrorsCollection().get(
						i).getErrorCode().getErrorCode()
						+ "   "
						+ expenseMasters.getExpenseErrorsCollection().get(i)
								.getErrorCode().getErrorText());
			}	
		}

		System.out.println(noOfErrors);
		Assert.assertTrue(noOfErrors>0);

	}
	
	@Test
	public void testValidateMultipleFacsAgency_valid() {
		
		Date expDateFrom = new Date("12/01/2009");
		Date expDateTo = new Date("12/07/2009");

		//UserProfile userProfile = new UserProfile("T_HRMND99",133217);
		UserSubject userSubject = new UserSubject();
		userSubject.setAppointmentId(107192);

		ExpenseMasters expenseMasters = prepareExpenseMasters();
		expenseMasters.setExpDateFrom(expDateFrom);
		expenseMasters.setExpDateTo(expDateTo);

		OutOfStateTravel outState = expenseDSP.getOutOfStateTravel(1);

		List<OutOfStateTravel> outStateList = new ArrayList<OutOfStateTravel>();
		outStateList.add(outState);

		//expenseMasters.setExpenseOutOfStateCollection(outStateList);
		expenseMasters.setOutOfStateTravelList(outStateList);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseDSP.validateMultipleFacsAgency(expenseMasters, expDateFrom,	expDateTo, userSubject, userProfile);
		Assert.assertNull(expenseMasters.getExpenseErrorsCollection());
	}
	
	@Test
	public void testValidateMultipleFacsAgency_InValid() {
		Date expenseStartDate = new Date("12/20/2008");
		Date expenseEndDate = new Date("12/10/2008");
		
		int empId = 1234567890;
		String userId = "T_HRMND99";
		
		UserSubject userSubject = new UserSubject();
		userSubject.setAppointmentId(107192);

		ExpenseMasters expenseMasters = prepareExpenseMasters();
		
		expenseDSP.validateMultipleFacsAgency(expenseMasters, expenseStartDate,	expenseEndDate, userSubject,userProfile);
		Assert.assertNotNull(expenseMasters.getExpenseErrorsCollection());
	}

	@Test
	public void testGetExpenseHistory() {
		int expenseEventId = 10;

		List<ExpenseHistory> expenseHistory = expenseDSP
				.getExpenseHistory(expenseEventId);
		Assert.assertNotNull(expenseHistory);
		Assert.assertTrue(!expenseHistory.isEmpty());
	}
	
	
	@Test
	public void testSubmitExpense_errorsPresent() throws Exception{
		int expenseMasterId = 9;
		String moduleId = "PRMW027";
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		
		UserSubject userSubject = new UserSubject();
		userSubject.setAppointmentId(107192);

		ExpenseMasters expenseMasters = expenseDSP.getExpense(expenseMasterId);
		expenseMasters.setStatus("");
		
		List<AdvanceLiquidations> liquidationList = new ArrayList<AdvanceLiquidations>();
		
		AdvanceLiquidations advLiquidation = new AdvanceLiquidations();
		advLiquidation.setAdvanceMaster(advanceService.getAdvanceByMasterId(1899));
		advLiquidation.setExpenseMasterId(expenseDSP.getExpense(1051).getExpmIdentifier());
		advLiquidation.setAmount(30);
		
		liquidationList.add(advLiquidation);
		
		ExpenseMasters submittedExpense = null;
		try{
			List<DriverReimbExpTypeCbs> driverReibmCbs = new ArrayList<DriverReimbExpTypeCbs> ();
			submittedExpense  = expenseDSP.submitExpense(expenseMasters, userProfile,userSubject,liquidationList, "", driverReibmCbs, IConstants.EXPENSE_EMPLOYEE);
		}
		catch(ExpenseException e){
			Assert.assertSame(ExpenseException.class, e.getClass());
		}
	}

	@Test
	public void testSubmitExpense_noErrors() throws Exception{
		String moduleId = "PRMW027";
		
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		ExpenseMasters expenseMasters = expenseDSP.getExpense(1);
		
		UserSubject userSubject = new UserSubject();
		userSubject.setAppointmentId(107192);
		
		List<AdvanceLiquidations> liquidationList = new ArrayList<AdvanceLiquidations>();
		
		AdvanceLiquidations advLiquidation = new AdvanceLiquidations();
		advLiquidation.setAdvanceMaster(advanceService.getAdvanceByMasterId(1899));
		advLiquidation.setExpenseMasterId(expenseDSP.getExpense(1051).getExpmIdentifier());
		advLiquidation.setAmount(30);
		
		liquidationList.add(advLiquidation);
		List<DriverReimbExpTypeCbs> driverReibmCbs = new ArrayList<DriverReimbExpTypeCbs> ();		
		ExpenseMasters submittedExpense  = expenseDSP.submitExpense(expenseMasters, userProfile,userSubject,liquidationList, "", driverReibmCbs, IConstants.EXPENSE_EMPLOYEE);
		Assert.assertNotNull(submittedExpense);
		
		Assert.assertEquals("SUBM", submittedExpense.getStatus());
	}
	
	@Test
	public void testValidateExpenseID_TEStartDateFiscalYearSpanValidate() throws Exception{

		// Time and Expense Start Date 12/31/2008
		
		Date expDateFrom = new Date("11/01/2008");
		Date expDateTo = new Date("12/07/2009");
		String moduleId = "EXPW001";
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		
		UserSubject userSubject = new UserSubject();
		userSubject.setAppointmentId(107192);
		
		ExpenseMasters expenseMasters = prepareExpenseMasters();
		expenseMasters.setExpDateFrom(expDateFrom);
		expenseMasters.setExpDateTo(expDateTo);
		
		expenseDSP.validateExpenseID(expenseMasters, userSubject, userProfile);

		int noOfErrors = expenseMasters.getExpenseErrorsCollection().size();
		for (int i = 0; i < noOfErrors; i++) {
			System.out.println(expenseMasters.getExpenseErrorsCollection().get(
					i).getErrorCode().getErrorCode()
					+ "   "
					+ expenseMasters.getExpenseErrorsCollection().get(i)
							.getErrorCode().getErrorText());
		}
		System.out.println(noOfErrors);
		Assert.assertTrue(noOfErrors>0);
	}
	
	@Test
	public void testApproveExpense() {
		int expenseMasterId = 999;

		boolean isApproved = false;
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		UserSubject userSubject = new UserSubject();
		userSubject.setAppointmentId(107192);
		userSubject.setDepartment("27");
		userSubject.setAgency("01");
		userSubject.setTku("509");
		String approveComments = "Expense Approved";
		ExpenseMasters expenseMasters = expenseDSP.getExpense(expenseMasterId);
		ExpenseMasters approvedExpense = expenseDSP.approveExpense(expenseMasters, userProfile, userSubject, approveComments);
		//Assert.assertTrue(!approvedExpense.getExpenseActionsCollection().isEmpty());
	}

	@Test
	public void testGetAuthorizationCodes() {
		List<StateAuthCodes> authorizationCodesList = expenseDSP.getAuthorizationCodes();
		Assert.assertNotNull(authorizationCodesList);
	}

	@Test
	public void testRejectExpense() {
		// Commenting prior to the initial build
		/*int expenseMasterId = 100;

		String moduleId = "EXPW001";
		ExpenseMasters expenseMaster = expenseDSP.getExpense(expenseMasterId);
		expenseMaster.setStatus("SUBM");
		
		ExpenseEvents expenseEvents = expenseDSP.rejectExpense(expenseMaster, moduleId);
		ExpenseMasters tempExpMast = null;
		Assert.assertNotNull(expenseEvents);
		for(ExpenseMasters expenseMasters : expenseEvents.getExpenseMastersCollection()){
			if(expenseMasters.getExpmIdentifier()==expenseMasterId){
				tempExpMast = expenseMasters;
			}
		}
		System.out.println("Status ::"+expenseMaster.getStatus());
		Assert.assertEquals("RJCT",tempExpMast.getStatus());*/

	}

	@Test
	public void testGetMaxRevisionNo() throws Exception {
		int expenseEventId = 1;
		double maxRevisionNo = expenseDSP.getMaxRevisionNo(expenseEventId);
		Assert.assertNotNull(maxRevisionNo);

	}

	@Test
	public void testValidateMultipleFacsAgency() {

		ExpenseMasters expenseMasters = expenseDSP.getExpense(1124);
		Date expenseStartDate = new Date("12/10/2001");
		Date expenseEndDate = new Date("12/10/2001");
		
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		UserSubject userSubject = new UserSubject();
		userSubject.setAppointmentId(107192);
		
		commDsp.deleteExpenseErrors(expenseMasters, IConstants.EXP_ERR_SRC_ID_TAB);
		expenseDSP.validateMultipleFacsAgency(expenseMasters, expenseStartDate,expenseEndDate, userSubject,userProfile);

		Assert.assertNotNull(expenseMasters.getExpenseErrorsCollection());

	}

	@Test
	public void testGetExpensesListEmployee_Adjusted() {
		int empId = 133509;
		List<VExpensesList> expenseListBean = null;
		String expIncludeAdjustment = IConstants.TRANSACTION_LIST_ADJUSTED_ONLY;

		expenseListBean = expenseDSP.getExpensesListEmployee(empId,expIncludeAdjustment);
		Assert.assertNotNull(expenseListBean);
	}
	
	@Test
	public void testGetExpensesListEmployee_NonAdjusted() {
		int empId = 133509;
		List<VExpensesList> expenseListBean = null;
		String expIncludeAdjustment = IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY;
		
		expenseListBean = expenseDSP.getExpensesListEmployee(empId,expIncludeAdjustment);
		Assert.assertNotNull(expenseListBean);

	}
	
	@Test
	public void testGetExpensesListEmployee_Both() {
		int empId = 133509;
		List<VExpensesList> expenseListBean = null;
		String expIncludeAdjustment = IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED;

		expenseListBean = expenseDSP.getExpensesListEmployee(empId,expIncludeAdjustment);
		Assert.assertNotNull(expenseListBean);
	}

	@Test
	public void testGetExpensesListAppointment_Adjusted() {

		int appointmentId = 107192;
		List<ExpenseListBean> expenseListBean = null;
		String expAdjustmentIdentifier = IConstants.TRANSACTION_LIST_ADJUSTED_ONLY;
		String userId = "T_HRMND99";
		String moduleId = "PRMW027";

		expenseListBean = expenseDSP.getExpensesListAppointment(appointmentId,expAdjustmentIdentifier, userId, moduleId, "01", "01", "466");
		Assert.assertNotNull(expenseListBean);
	}
	
	@Test
	public void testGetExpensesListAppointment_NonAdjusted() {

		int appointmentId = 107192;
		List<ExpenseListBean> expenseListBean = null;
		String expAdjustmentIdentifier = IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY;
		String userId = "T_HRMND99";
		String moduleId = "PRMW027";

		expenseListBean = expenseDSP.getExpensesListAppointment(appointmentId,expAdjustmentIdentifier, userId, moduleId, "01", "01", "466");
		Assert.assertNotNull(expenseListBean);
	}

	
	@Test
	public void testGetExpensesListAppointment_both() {

		int appointmentId = 107192;
		List<ExpenseListBean> expenseListBean = null;
		String expAdjustmentIdentifier = IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED;
		String userId = "T_HRMND99";
		String moduleId = "PRMW027";
		expenseListBean = expenseDSP.getExpensesListAppointment(appointmentId,expAdjustmentIdentifier, userId, moduleId, "01", "01", "466");
		Assert.assertNotNull(expenseListBean);

	}

	/*@Test
	public void testIsPreAuditRequired() {

//		String isPreAuditRequired = expenseDSP.isPreAuditRequired(109770, 1001, "59", "01");
		Assert.assertNotNull(isPreAuditRequired);
	}
*/
	@Test
	public void testIsLiqAmtGreaterThanOutstandingAmt() {

		boolean liqAmtGreaterThanOutstandingAmt = expenseDSP.isLiqAmtGreaterThanOutstandingAmt(em.find(ExpenseMasters.class, 2430));
		Assert.assertNotNull(liqAmtGreaterThanOutstandingAmt);
	}	
	
	@Test
	public void testAutoApproveExpense() {
		int expenseMasterId = 331262;
		ExpenseMasters submittedExpense = null;
		try {
		UserProfile userProfile = securityService
				.getDcdsUser(1077335, true);
		
		UserSubject userSubject = new UserSubject();
		userSubject.setDepartment("27");
		userSubject.setAgency("01");
		userSubject.setTku("509");

		ExpenseMasters expenseMasters = expenseDSP.getExpense(expenseMasterId);
		
		List<DriverReimbExpTypeCbs> driverReibmCbs = new ArrayList<DriverReimbExpTypeCbs> ();
		submittedExpense = expenseDSP.submitExpense(expenseMasters, userProfile, userSubject, null, "", driverReibmCbs, IConstants.EXPENSE_EMPLOYEE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(submittedExpense);
	}
	
}
