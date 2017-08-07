package gov.michigan.dit.timeexpense.dao.test;

import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseLineItemDAO;
import gov.michigan.dit.timeexpense.exception.ExpenseRateNotFoundException;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseLocations;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseRates;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypes;
import gov.michigan.dit.timeexpense.model.display.CodingBlockSummaryBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseSummaryDetailsBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class ExpenseLineItemDAOTest extends AbstractDAOTest{

	private ExpenseLineItemDAO expenseLineItemDAO = new ExpenseLineItemDAO();
	private ExpenseDAO expenseDAO = new ExpenseDAO();
	private CommonDAO commonDao = new CommonDAO();

	Logger logger = Logger.getLogger(ExpenseLineItemDAOTest.class);
	
	@Before
	public void startTransaction(){
		
		beginTransaction();
		
/*		expenseDAO = new ExpenseDAO();
		commonDao = new CommonDAO();
		expenseLineItemDAO = new ExpenseLineItemDAO();*/
		
		expenseLineItemDAO.setEntityManager(em);
		expenseDAO.setEntityManager(em);
		commonDao.setEntityManager(em);
	}
	
	@Test
	public void testFindExpenseLineItems() {
		int expenseMasterId = 100;
		
		ExpenseMasters expenseMasters = expenseDAO.findExpenseByExpenseMasterId(expenseMasterId);
		Collection<ExpenseDetails> expenseDetailsCollection = expenseLineItemDAO.findExpenseLineItems(expenseMasters);
		Assert.assertNotNull(expenseDetailsCollection);
	}

	@Test
	public void testFindCommonMiles() {
		String srcCity = "Chandigarh";
		String srcState = "MI";
		String destCity = "Gurgaon";
		String destState = "MI";
		String department = "08";
		String agency = "01";

		double mileage = expenseLineItemDAO.findCommonMiles(srcCity, srcState, destCity, destState, department, agency);
		Assert.assertEquals(175.00, mileage);
	}

	@Test
	public void testFindCommonMiles_NonExisting() {
		String srcCity = "Dummy";
		String srcState = "MI";
		String destCity = "Dummy";
		String destState = "KY";
		String department = "08";
		String agency = "01";

		double mileage = expenseLineItemDAO.findCommonMiles(srcCity, srcState, destCity, destState, department, agency);
		Assert.assertEquals(0, mileage);
	}

	@Test
	public void testFindCommonMiles_BothWays() {
		String srcCity = "Chandigarh";
		String srcState = "MI";
		String destCity = "Gurgaon";
		String destState = "MI";
		String department = "08";
		String agency = "01";

		double srcToDestCommonMileage = expenseLineItemDAO.findCommonMiles(srcCity, srcState, destCity, destState, department, agency);
		double destToSourceCommonMileage = expenseLineItemDAO.findCommonMiles(destCity, destState, srcCity, srcState, department, agency);

		Assert.assertEquals(srcToDestCommonMileage, destToSourceCommonMileage);
	}

	
	@Test
	public void testFindExpenseTypesAndRate() {
		Date expenseDate = new Date("12/10/2008");
		List<ExpenseTypes> expTypesRatesList =expenseLineItemDAO.findExpenseTypes(expenseDate);
		System.out.println(expTypesRatesList.size());

		Assert.assertNotNull(expTypesRatesList);
	}

	@Test
	public void testFindExpenseSummaryDetails() {
		int expenseMasterId = 100;
		List<ExpenseSummaryDetailsBean> expSummaryList =  expenseLineItemDAO.findExpenseSummaryDetails(
				expenseMasterId);

		Assert.assertNotNull(expSummaryList);
	}

	@Test
	public void testFindLocations() {

		List<ExpenseLocations> expenseLocationsList = expenseLineItemDAO.findLocations();
		Assert.assertNotNull(expenseLocationsList);

	}
	@Test
	public void testFindExpenseSummaryByCodingBlock() {
		int expenseMasterId = 102;
		List<CodingBlockSummaryBean> expSummCodingBlock =  expenseLineItemDAO.findExpenseSummaryByCodingBlock(
				expenseMasterId);
		
		Assert.assertNotNull(expSummCodingBlock);
	}
	
	@Test
	public void testFindExpenseRateByExpenseType(){
		Date expenseDate = new Date("01/01/2008");
		
		double rateAmt = 0;
		try{
			rateAmt = expenseLineItemDAO.findExpenseRateByExpenseType("Brea", expenseDate);
		}catch(ExpenseRateNotFoundException ernfe){
			// always fail!
			Assert.assertTrue(false);
		}
		
		Assert.assertTrue(rateAmt > 0);
	}
	
	public void insertRecord() {
		em
				.createNativeQuery(
						"insert into Expense_Details(expd_identifier,expm_identifier,line_item,exp_date,mile_override_ind,exp_type_code,round_trip_ind,recurring_ind,dollar_amount) "+
						"values(900,100,13,'02-jun-08','Y','Lunc','N','N',200)").executeUpdate();
		em.flush();
	}

	@Test
	public void testFindMaxLineNo(){
		ExpenseMasters expenseMaster = expenseDAO.findExpenseByExpenseMasterId(101);
		int maxLineNo = expenseLineItemDAO.findMaxLineNo(expenseMaster);
		Assert.assertTrue(maxLineNo>0);
	}
	
	@Test
	public void testFindCities(){
		List<String> cities = expenseLineItemDAO.findCities("08", "01");
		Assert.assertFalse(cities.isEmpty());
	}
	
	@Test
	public void testFindExpenseRatesForExpenseTypes(){
		
		List<String> expenseTypeCodes = new ArrayList<String>();
		expenseTypeCodes.add("MC_0"); expenseTypeCodes.add("MC_1");
		
		Date d = new Date(); d.setYear(2009-1900); d.setMonth(1); d.setDate(1);
		
		List<ExpenseRates> rates = expenseLineItemDAO.findExpenseRatesForExpenseTypes(expenseTypeCodes, d);

		Assert.assertEquals(1, rates.size());
		
		Assert.assertEquals("MC_1", rates.get(0).getExpTypeCode());
		Assert.assertEquals(17.17, rates.get(0).getRateAmt());
	}
	
	@Test
	public void testFindExpenseRatesForExpenseTypeWithinDates_NoRateChange(){
		Date start = new Date(); start.setYear(2009-1900); start.setMonth(Calendar.JUNE); start.setDate(1);
		Date end = new Date(); end.setYear(2009-1900); end.setMonth(Calendar.JULY); end.setDate(15);
		
		List<ExpenseRates> rates = expenseLineItemDAO.findExpenseRatesForExpenseTypeWithinDateRange("0100", start, end);

		Assert.assertEquals(1, rates.size());
	}

	@Test
	public void testFindExpenseRatesForExpenseTypeWithinDates_RateChange(){
		Date start = new Date(); start.setYear(2009-1900); start.setMonth(Calendar.SEPTEMBER); start.setDate(1);
		Date end = new Date(); end.setYear(2009-1900); end.setMonth(Calendar.OCTOBER); end.setDate(15);
		
		List<ExpenseRates> rates = expenseLineItemDAO.findExpenseRatesForExpenseTypeWithinDateRange("0100", start, end);

		Assert.assertEquals(2, rates.size());
	}

	@Test
	public void testFindExpenseRatesForExpenseTypeWithinDates_NoRateChangeBoundaryCondition() throws Exception{
		Date start = (new SimpleDateFormat("yyyy/MM/dd")).parse("2009/1/1"); 
		Date end = (new SimpleDateFormat("yyyy/MM/dd")).parse("2009/9/30");
		
		List<ExpenseRates> rates = expenseLineItemDAO.findExpenseRatesForExpenseTypeWithinDateRange("0100", start, end);

		Assert.assertEquals(1, rates.size());
	}

	@Test
	public void testFindExpenseRatesForExpenseTypeWithinDates_RateChangeBoundaryCondition() throws Exception{
		Date start = (new SimpleDateFormat("yyyy/MM/dd")).parse("2009/09/30"); 
		Date end = (new SimpleDateFormat("yyyy/MM/dd")).parse("2009/10/01");
		
		List<ExpenseRates> rates = expenseLineItemDAO.findExpenseRatesForExpenseTypeWithinDateRange("0100", start, end);

		Assert.assertEquals(2, rates.size());
	}
	
	@Test
	public void testFindTravelAuthorizedStates(){
		List<Object> states = expenseLineItemDAO.findTravelAuthorizedStates();
		System.out.println(states.size());
	}
	
	@Test
	public void testIsMealTimeValid() throws Exception{
		
		Date expenseDate = (new SimpleDateFormat("yyyy/MM/dd")).parse("2012/07/08");
		
		int returnValue = expenseLineItemDAO.isMealTimeValid("D", expenseDate, "1700", "1730", "01", "01", "100");
		
		Assert.assertTrue(returnValue == 1);
		
	}
	
	public ExpenseDetails prepareExpenseDetails() {
		// TODO Auto-generated method stub
		
		Date departTime = new Date("01/01/2009");
		Date returnTime = new Date("01/03/2009");
		Date expDate = new Date("01/05/2009");
		ExpenseTypes expTypes = new ExpenseTypes();
		expTypes.setExpTypeCode("Lunc");
		
		String fromElocCity = "K";
		String fromElocStProv = "MI";
		short lineItem =1;
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
	
	public ExpenseMasters prepareExpenseMasters(){

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
	
	@Test
	public void testIsValidExpenseBargainUnit() throws Exception{		
		int apptHistoryId = 1162055;
		String expenseTypeCode = "0551";
		int returnValue = expenseLineItemDAO.isValidExpenseBargainUnit(apptHistoryId, expenseTypeCode, Calendar.getInstance().getTime());		
		Assert.assertTrue(returnValue == 1);
		
	}
	
}
