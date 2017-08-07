package gov.michigan.dit.timeexpense.service.test;

import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.dao.test.ExpenseLineItemDAOTest;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseLocations;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseSummaryBean;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypes;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.db.Appointments;
import gov.michigan.dit.timeexpense.model.display.CodingBlockSummaryBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseSummaryDetailsBean;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExpenseLineItemDSPTest{
	
	private ExpenseLineItemDSP expenseLineItemDSP;
	private ExpenseDSP expenseDSP;
	private CommonDSP commDsp;
	//private ExpenseLineItemDAO expenseLineItemDAO  = new ExpenseLineItemDAO();
	private ExpenseLineItemDAOTest expenseLineItemDAOTest = new ExpenseLineItemDAOTest();
	
	private static EntityManagerFactory emf;
	private EntityManager em;

	
	Logger log = Logger.getLogger(ExpenseLineItemDSPTest.class);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
	}
	
	@Before
	public void setUp() throws Exception {
		
		em = emf.createEntityManager();
		//private CommonDAO commDao = new CommonDAO();
		CommonDSP commonDsp = new CommonDSP(em);
		ExpenseDAO expenseDAO = new ExpenseDAO();
		expenseDSP = new ExpenseDSP(em);
		commDsp = new CommonDSP(em);
		expenseLineItemDSP = new ExpenseLineItemDSP(em);
/*		expenseLineItemDAO.setEntityManager(em);
		expenseDAO.setEntityManager(em);
		commDao.setEntityManager(em);

		expenseLineItemDSP.setExpenseLineItemDAO(expenseLineItemDAO);
		expenseLineItemDSP.setCommonDao(commDao);
		expenseLineItemDSP.setExpenseDAO(expenseDAO);
		expenseDSP.setExpenseDAO(expenseDAO);
		commonDsp.setCommonDao(commDao); */
		
		em.getTransaction().begin();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetExpenseLineItems() {
		int expenseMasterId = 100;
		int revisionNo = 1;
		Collection<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		ExpenseMasters expenseMasters = expenseDSP.getExpense(expenseMasterId);
		List expenseLineItemsList = expenseLineItemDSP
				.getExpenseLineItems(expenseMasters);

		expenseDetailsList = (Collection<ExpenseDetails>) expenseLineItemsList
				.get(0);
		List<ExpenseLocations> expenseLocationsList = (List<ExpenseLocations>) expenseLineItemsList
				.get(1);
		double outStandingAdvanceByEmpId = (Double) expenseLineItemsList.get(2);

		Assert.assertNotNull(expenseLineItemsList);
		Assert.assertNotNull(expenseDetailsList);
		Assert.assertNotNull(expenseLocationsList);
		Assert.assertNotNull(outStandingAdvanceByEmpId);
	}

	@Test
	public void testGetCommonMiles() {
		String srcCity = "L";
		String srcState = "M";
		String destCity = "A";
		String destState = "K";
		double mileage = expenseLineItemDSP.getCommonMiles(srcCity, srcState, destCity, destState, "08", "01");
		
		Assert.assertNotNull(mileage);
	}

	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetExpenseTypes() {
		Date expenseDate = new Date("12/10/2008");
		List<ExpenseTypes> expTypesRatesList = expenseLineItemDSP.getExpenseTypes(expenseDate);
		System.out.println(expTypesRatesList.size());
		Assert.assertNotNull(expTypesRatesList);
		return;
	}
	
	@Test
	public void testGetExpenseReimbursementAmount() {
		// TODO Auto-generated method stub
		return;
	}

	//@Test
/*	public void testSaveExpenseLineItems() {
		int expenseMasterId = 104;
		ExpenseMasters expenseMaster = expenseDSP.getExpense(expenseMasterId);
		
		ExpenseDetails expenseDetails = prepareExpenseDetails();
		expenseDetails.setLineItem(expenseLineItemDSP.getMaxLineNo(expenseMaster));
		expenseDetails.setExpmIdentifier(expenseMaster);
		
		List<ExpenseDetails> expDtlsList = new ArrayList<ExpenseDetails>();
		expDtlsList.add(expenseDetails);
		
		ExpenseMasters expenseMasters = expenseLineItemDSP.saveExpenseLineItems(expenseMaster, expDtlsList);
		Assert.assertNotNull(expenseMasters);
		
	}*/

	@Test
	public void testValidateExpenseDetailsDate_InvalidExpDateNotBetweenIDDates_singleExpDate() {
		// TODO Auto-generated method stub
		
		Date expDateFrom = new Date("02/10/2009");
		
		Date expDateTo = new Date("02/20/2009");
		Date expDate = new Date("02/25/2009");
		
		ExpenseMasters expenseMaster = expenseDSP.getExpense(100);
		expenseMaster.setExpDateFrom(expDateFrom);
		expenseMaster.setExpDateTo(expDateTo);
		
		ExpenseDetails expenseDtls = expenseLineItemDAOTest.prepareExpenseDetails();
		expenseDtls.setExpDate(expDate);
		
		List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		expenseDetailsList.add(expenseDtls);
		
		Date[] expenseDates = new Date[1];
		expenseDates[0] = new Date("01/02/2009");
		
		commDsp.deleteExpenseErrors(expenseMaster, IConstants.EXP_ERR_SRC_DTL_TAB);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseLineItemDSP.validateExpenseDetailsDate(expenseMaster, userProfile);
		Assert.assertTrue(expenseMaster.getExpenseErrorsCollection().size()>0);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testValidateExpenseDetailsDate_InvalidExpDateNotBetweenIDDates_recurringExpDate() {
		// TODO Auto-generated method stub
		
		Date expDateFrom = new Date("02/10/2009");
		
		Date expDateTo = new Date("02/20/2009");
		Date expDate = new Date("02/25/2009");
		
		ExpenseMasters expenseMaster = prepareExpenseMasters();
		expenseMaster.setExpDateFrom(expDateFrom);
		expenseMaster.setExpDateTo(expDateTo);

		List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		ExpenseDetails expenseDtls = expenseLineItemDAOTest.prepareExpenseDetails();
		expenseDtls.setExpDate(expDate);
		
		expenseDetailsList.add(expenseDtls);
		
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		String moduleId = "EXPW001"; 
		int appointmentId = 107192;
		Date[] expenseDates = new Date[3];

		expenseDates[0] = new Date("01/02/2009");
		expenseDates[1] = new Date("01/10/2009");
		expenseDates[2] = new Date("01/15/2009");
		//UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseLineItemDSP.validateExpenseDetailsDate(expenseMaster, userProfile);
		Assert.assertTrue(expenseMaster.getExpenseErrorsCollection().size()==1);
		System.out.println("Error Description :"+ expenseMaster.getExpenseErrorsCollection().get(0).getErrorCode().getErrorText());
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testValidateTripTime_InvalidDeptReturnTime() throws Exception{
		// TODO Auto-generated method stub
		
		Date expDateFrom = new Date("02/10/2009");
		
		Date expDateTo = new Date("02/20/2009");
		Date expDate = new Date("02/25/2009");
		
		Calendar deptCal = Calendar.getInstance();
		Calendar returnCal = Calendar.getInstance();
		
		deptCal.set(Calendar.DAY_OF_MONTH, 12);
		deptCal.set(Calendar.MONTH, 1);
		deptCal.set(Calendar.YEAR, 2009);
		deptCal.set(Calendar.HOUR, 9);
		deptCal.set(Calendar.MINUTE, 20);
		
		Date departTime =deptCal.getTime();
		
		returnCal.set(Calendar.DAY_OF_MONTH, 12);
		returnCal.set(Calendar.MONTH, 1);
		returnCal.set(Calendar.YEAR, 2009);
		returnCal.set(Calendar.HOUR, 8);
		returnCal.set(Calendar.MINUTE, 20);
		
		Date returnTime = returnCal.getTime();

		System.out.println(returnTime + "   " + departTime);
		
		ExpenseMasters expenseMaster = prepareExpenseMasters();
		expenseMaster.setExpDateFrom(expDateFrom);
		expenseMaster.setExpDateTo(expDateTo);
		
		List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		ExpenseDetails expenseDtls = expenseLineItemDAOTest.prepareExpenseDetails();
		expenseDtls.setExpDate(expDate);
		expenseDtls.setDepartTime(departTime);
		expenseDtls.setReturnTime(returnTime);
		
		expenseDetailsList.add(expenseDtls);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseLineItemDSP.validateTripTime(expenseMaster, userProfile);
		Assert.assertTrue(expenseMaster.getExpenseErrorsCollection().size()==1);
		System.out.println("Error Description :"+ expenseMaster.getExpenseErrorsCollection().get(0).getErrorCode().getErrorText());
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testValidateTripTime_NullDepartReturnTime() throws Exception{
		// TODO Auto-generated method stub
		
		Date expDateFrom = new Date("02/10/2009");
		
		Date expDateTo = new Date("02/20/2009");
		Date expDate = new Date("02/25/2009");
		
		Calendar deptCal = Calendar.getInstance();
		
		deptCal.set(Calendar.DAY_OF_MONTH, 12);
		deptCal.set(Calendar.MONTH, 1);
		deptCal.set(Calendar.YEAR, 2009);
		deptCal.set(Calendar.HOUR, 9);
		deptCal.set(Calendar.MINUTE, 20);
		
		Date departTime =deptCal.getTime();
		
		// Return time set to NULL
		Date returnTime = null;
		
		ExpenseMasters expenseMaster = prepareExpenseMasters();
		expenseMaster.setExpDateFrom(expDateFrom);
		expenseMaster.setExpDateTo(expDateTo);
		
		List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		ExpenseDetails expenseDtls = expenseLineItemDAOTest.prepareExpenseDetails();
		expenseDtls.setExpDate(expDate);
		expenseDtls.setDepartTime(departTime);
		expenseDtls.setReturnTime(returnTime);
		
		expenseDetailsList.add(expenseDtls);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseLineItemDSP.validateTripTime(expenseMaster,userProfile);
		Assert.assertTrue(expenseMaster.getExpenseErrorsCollection().size()>0);
	}
	
	@SuppressWarnings("deprecation")
	//@Test
	public void testValidateExpenseTypeAndCommentsForMileage_zeroMiles() throws Exception{
		Date expDateFrom = new Date("02/10/2009");
		
		Date expDateTo = new Date("02/20/2009");
		Date expDate = new Date("02/25/2009");

		ExpenseMasters expenseMaster = expenseDSP.getExpense(1117);
		expenseMaster.setExpDateFrom(expDateFrom);
		expenseMaster.setExpDateTo(expDateTo);
		
		ExpenseTypes expenseTypes = expenseLineItemDSP.getExpenseTypes("Detr");
		
		
		List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		ExpenseDetails expenseDtls = expenseLineItemDAOTest.prepareExpenseDetails();
		expenseDtls.setExpDate(expDate);
		expenseDtls.setExpTypeCode(expenseTypes);
		expenseDtls.setMileage(0);
		expenseDtls.setExpmIdentifier(expenseMaster);
		expenseDtls.setComments("");
		
		expenseDetailsList.add(expenseDtls);
		
		commDsp.deleteExpenseErrors(expenseMaster, IConstants.EXP_ERR_SRC_DTL_TAB);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseLineItemDSP.validateExpenseTypeAndCommentsForMileage(expenseMaster,userProfile);
		Assert.assertTrue(expenseMaster.getExpenseErrorsCollection().size()>0);
		int noOfErrors = expenseMaster.getExpenseErrorsCollection().size();
		for (int i = 0; i < noOfErrors; i++) {
			System.out.println(expenseMaster.getExpenseErrorsCollection().get(
					i).getErrorCode().getErrorCode()
					+ "   "
					+ expenseMaster.getExpenseErrorsCollection().get(i)
							.getErrorCode().getErrorText());
		}
		
	}
	
	@SuppressWarnings("deprecation")
	//@Test
	public void testValidateExpenseTypeAndCommentsForMileage_nonZeroMiles() throws Exception{
		// TODO Auto-generated method stub
		
		String expense_rule_type = "EXPENSE_RULE_TYPES";
		Date expDateFrom = new Date("02/10/2009");
		
		Date expDateTo = new Date("02/20/2009");
		Date expDate = new Date("02/25/2009");

		ExpenseMasters expenseMaster = expenseDSP.getExpense(1117);
		expenseMaster.setExpDateFrom(expDateFrom);
		expenseMaster.setExpDateTo(expDateTo);
		
		ExpenseTypes expenseTypes = expenseLineItemDSP.getExpenseTypes("Detr");
		
		List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		ExpenseDetails expenseDtls = expenseLineItemDAOTest.prepareExpenseDetails();
		expenseDtls.setExpDate(expDate);
		expenseDtls.setExpTypeCode(expenseTypes);
		expenseDtls.setExpmIdentifier(expenseMaster);
		expenseDtls.setMileage(20);
		expenseDtls.setDollarAmount(0); /* To make dollar_amount equal to the Expense rate amount */
		
		expenseDetailsList.add(expenseDtls);
	
		commDsp.deleteExpenseErrors(expenseMaster, IConstants.EXP_ERR_SRC_DTL_TAB);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseLineItemDSP.validateExpenseTypeAndCommentsForMileage(expenseMaster,userProfile);
		
		Assert.assertNull(expenseMaster.getExpenseErrorsCollection());
	}
	
	@SuppressWarnings("deprecation")
	//@Test
	/** The method validates whether the Comments are entered when reimbursement amount amount is changed
	 *  Will return errors as comments is set to NULL
	 */
	public void testValidateExpenseTypeAndCommentsForMileage_reimburseAmtGreater() throws Exception{
		// TODO Auto-generated method stub
		ExpenseMasters expenseMaster = expenseDSP.getExpense(1117);
		expenseMaster.setExpDateFrom(new Date("02/10/2009"));
		expenseMaster.setExpDateTo(new Date("02/20/2009"));
		
		ExpenseTypes expenseTypes = expenseLineItemDSP.getExpenseTypes("LUN2");
		
		List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		
		ExpenseDetails expenseDtls = expenseLineItemDAOTest.prepareExpenseDetails();
		expenseDtls.setExpDate(new Date("02/25/2009"));
		expenseDtls.setExpTypeCode(expenseTypes);
		expenseDtls.setMileage(20);
		expenseDtls.setComments("");
		expenseDtls.setDollarAmount(4000);
		expenseDtls.setExpmIdentifier(expenseMaster);
		
		expenseDetailsList.add(expenseDtls);

		commDsp.deleteExpenseErrors(expenseMaster, IConstants.EXP_ERR_SRC_DTL_TAB);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseLineItemDSP.validateExpenseTypeAndCommentsForMileage(expenseMaster,userProfile);
		Assert.assertTrue(expenseMaster.getExpenseErrorsCollection().size()>0);
		int noOfErrors = expenseMaster.getExpenseErrorsCollection().size();
		for (int i = 0; i < noOfErrors; i++) {
			System.out.println(expenseMaster.getExpenseErrorsCollection().get(
					i).getErrorCode().getErrorCode()
					+ "   "
					+ expenseMaster.getExpenseErrorsCollection().get(i)
							.getErrorCode().getErrorText());
		}
	}
	
	@Test
	public void testValidateToStateForOutOfStateTravel(){
		ExpenseMasters expenseMaster = expenseDSP.getExpense(1117);
		expenseMaster.setExpDateFrom(new Date("02/10/2009"));
		expenseMaster.setExpDateTo(new Date("02/20/2009"));
		expenseMaster.setOutOfStateInd("N");
		
		ExpenseTypes expenseTypes = expenseLineItemDSP.getExpenseTypes("LUN2");
		
		List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		
		ExpenseDetails expenseDtls = expenseLineItemDAOTest.prepareExpenseDetails();
		expenseDtls.setExpDate(new Date("02/25/2009"));
		expenseDtls.setExpTypeCode(expenseTypes);
		expenseDtls.setExpmIdentifier(expenseMaster);
		expenseDtls.setFromElocStProv("ABC");
		expenseDtls.setToElocStProv("ABC");
		
		expenseDetailsList.add(expenseDtls);
		
		commDsp.deleteExpenseErrors(expenseMaster, IConstants.EXP_ERR_SRC_DTL_TAB);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		UserSubject user = new UserSubject();
		user.setEmployeeId(134067);

		
		expenseLineItemDSP.validateStateForOutOfStateTravel(expenseMaster,userProfile, user);
		
		Assert.assertTrue(expenseMaster.getExpenseErrorsCollection().size()>0);
		int noOfErrors = expenseMaster.getExpenseErrorsCollection().size();
		for (int i = 0; i < noOfErrors; i++) {
			System.out.println(expenseMaster.getExpenseErrorsCollection().get(
					i).getErrorCode().getErrorCode()
					+ "   "
					+ expenseMaster.getExpenseErrorsCollection().get(i)
							.getErrorCode().getErrorText());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetExpenseSummaryDetails() {
	
		UserSubject user = new UserSubject();
		user.setEmployeeId(134067);
		
		ExpenseDetails expenseDetails1 = expenseLineItemDSP.getExpenseDetailByExpenseDetailsId(126);
		ExpenseDetails expenseDetails2 = expenseLineItemDSP.getExpenseDetailByExpenseDetailsId(127);
		
		List<ExpenseDetails> expDtlsList = new ArrayList<ExpenseDetails>();
		expDtlsList.add(expenseDetails1);
		expDtlsList.add(expenseDetails2);
		
		ExpenseMasters expenseMasters = expenseDSP.getExpense(102);
		List returnList= expenseLineItemDSP.getExpenseSummaryDetails(expenseMasters,user.getEmployeeId());
		
		List<ExpenseSummaryDetailsBean> expenseSummaryList = (List<ExpenseSummaryDetailsBean>)returnList.get(0);
		List<CodingBlockSummaryBean> cbSummaryBeanList = (List<CodingBlockSummaryBean>)returnList.get(1);
		double amountLiquidated = (Double)returnList.get(2);
		double totalOutstandingAdvAmount = (Double)returnList.get(3);
		ArrayList<Double> expenseAmountsList = (ArrayList<Double>)returnList.get(4);
		
		
		Assert.assertNotNull(expenseSummaryList);
		Assert.assertNotNull(cbSummaryBeanList);
		Assert.assertNotNull(expenseAmountsList);
		Assert.assertNotNull(totalOutstandingAdvAmount);
		Assert.assertNotNull(amountLiquidated);
		
		Assert.assertNotNull(returnList);
	}
	
	@Test
	public void testCalculateExpenses() {
		
		ExpenseDetails expenseDetails1 = expenseLineItemDSP.getExpenseDetailByExpenseDetailsId(126);
		ExpenseDetails expenseDetails2 = expenseLineItemDSP.getExpenseDetailByExpenseDetailsId(127);
		
		List<ExpenseDetails> expDtlsList = new ArrayList<ExpenseDetails>();
		expDtlsList.add(expenseDetails1);
		expDtlsList.add(expenseDetails2);
		
		ArrayList<Double> expAmtList = (ArrayList<Double>)expenseLineItemDSP.calculateExpenses(expDtlsList);
		double taxableAmt = (Double)expAmtList.get(0);
		double nonTaxableAmt = (Double)expAmtList.get(1);
		
		Assert.assertNotNull(expAmtList);
		Assert.assertNotNull(taxableAmt);
		Assert.assertNotNull(nonTaxableAmt);
		
	}

	@Test
	public void testValidateRecurringExpenseDates() {
		
		ExpenseMasters expenseMaster = expenseDSP.getExpense(1117);
		expenseMaster.setExpDateFrom(new Date("02/10/2009"));
		expenseMaster.setExpDateTo(new Date("02/20/2009"));
		
		ExpenseTypes expenseTypes = expenseLineItemDSP.getExpenseTypes("LUN2");
		
		List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		
		ExpenseDetails expenseDtls = expenseLineItemDAOTest.prepareExpenseDetails();

		expenseDtls.setExpDate(new Date("02/25/2009"));
		expenseDtls.setExpTypeCode(expenseTypes);
		expenseDtls.setExpmIdentifier(expenseMaster);
		expenseDtls.setFromElocStProv("ABC");
		expenseDtls.setToElocStProv("ABC");
		
		expenseDetailsList.add(expenseDtls);
		
		ExpenseDetails expenseDtls2 = expenseLineItemDAOTest.prepareExpenseDetails();

		expenseDtls.setExpDate(new Date("01/25/2009"));
		expenseDtls.setExpTypeCode(expenseTypes);
		expenseDtls.setExpmIdentifier(expenseMaster);
		expenseDtls.setFromElocStProv("ABC");
		expenseDtls.setToElocStProv("ABC");
		
		expenseDetailsList.add(expenseDtls2);

		commDsp.deleteExpenseErrors(expenseMaster, IConstants.EXP_ERR_SRC_DTL_TAB);
		UserProfile userProfile = new UserProfile("T_HRMND99",133509);
		expenseLineItemDSP.validateExpenseDetailsDate(expenseMaster,userProfile);
		
		Assert.assertTrue(expenseMaster.getExpenseErrorsCollection().size()>0);
		
		return;
	}

	@Test
	public void testGetLocations() {
		List<ExpenseLocations> expenseLocList = expenseLineItemDSP.getLocations();
		for(ExpenseLocations eloc:expenseLocList){
			System.out.println(eloc.getCity() + "   "+ eloc.getStProv());
		}
		Assert.assertNotNull(expenseLocList);
	}

	@Test
	public void testAuditComplete() {
		int expenseMasterId = 102;
		String userId = "T_HRMND99";
		ExpenseMasters expenseMasters = expenseDSP.getExpense(102);
		
		boolean isAuditComplete = expenseLineItemDSP.auditComplete(expenseMasters, userId);
		Assert.assertNotNull(isAuditComplete);
	}
	
	@Test
	public void testDeleteExpenseLineItem() throws Exception{
		
		ExpenseMasters expenseMasters = prepareExpenseMasters();
		
		ExpenseDetails expenseDetails = prepareExpenseDetails();
		expenseDetails.setExpmIdentifier(expenseMasters);
		
		List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		expenseDetailsList.add(expenseDetails);
		
		expenseMasters.setExpenseDetailsCollection(expenseDetailsList);
		
		//expenseDAO.saveExpense(expenseMasters);
		
		UserProfile userProfile = new UserProfile("T_HRMND99");
		expenseDSP.saveExpenseExisting(expenseMasters, userProfile);
		
		int expenseDtlsId = expenseMasters.getExpenseDetailsCollection().get(0).getExpdIdentifier();
		expenseLineItemDSP.deleteExpenseLineItem(expenseMasters, expenseDtlsId);
		
	}
	
	public ExpenseMasters prepareExpenseMasters(){

		Appointments appointments = new Appointments();
		appointments.setApptIdentifier(107192);

		ExpenseEvents expenseEvents = new ExpenseEvents();
		expenseEvents.setAppointmentId(107192);
		
		

		ExpenseMasters expenseMasters = new ExpenseMasters();

		expenseMasters.setComments("Test Expense Data");
		expenseMasters.setCurrentInd("Y");
		expenseMasters.setExpevIdentifier(expenseEvents);
		expenseMasters.setNatureOfBusiness("Personal");
		expenseMasters.setTravelInd("Y");
		expenseMasters.setSuperReviewedReceiptsInd("Y");
		expenseMasters.setStatus("SUBM");

		expenseMasters.setExpenseErrorsCollection(null);
		expenseMasters.setExpenseDetailsCollection(null);

		return expenseMasters;
	}
	
	public ExpenseDetails prepareExpenseDetails() {
		// TODO Auto-generated method stub
		
		Date departTime = new Date("01/01/2009");
		Date returnTime = new Date("01/03/2009");
		Date expDate = new Date("01/05/2009");
		ExpenseTypes expTypes = expenseLineItemDSP.getExpenseTypes("test");
		
		String fromElocCity = "K";
		String fromElocStProv = "MI";
		int lineItem =1;
		ExpenseDetails expenseDetails = new ExpenseDetails();
		expenseDetails.setComments("This is a Test");
		expenseDetails.setDepartTime(departTime);
		expenseDetails.setDollarAmount(30);
		expenseDetails.setExpDate(expDate);
		expenseDetails.setExpenseDetailCodingBlocksCollection(null);
		expenseDetails.setExpTypeCode(expTypes);
		expenseDetails.setFromElocCity(fromElocCity);
		expenseDetails.setFromElocStProv(fromElocStProv);
		expenseDetails.setLineItem(lineItem);
		expenseDetails.setMileage(100);
		expenseDetails.setMileOverrideInd("N");
		expenseDetails.setReturnTime(returnTime);
		expenseDetails.setRoundTripInd("N");
		expenseDetails.setToElocCity("M");
		expenseDetails.setToElocStProv("DE");
		expenseDetails.setVicinityMileage(10);
		
		return expenseDetails;
	}

}
