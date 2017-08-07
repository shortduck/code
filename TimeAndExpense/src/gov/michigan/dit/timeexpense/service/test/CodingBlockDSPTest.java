package gov.michigan.dit.timeexpense.service.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import gov.michigan.dit.timeexpense.exception.ExpenseException;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetails;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.CodingBlockElement;
import gov.michigan.dit.timeexpense.model.core.DriverReimbExpTypeCbs;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypes;
import gov.michigan.dit.timeexpense.model.core.TkuoptTaOptions;
import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.core.DefaultDistributionsAdvAgy;
import gov.michigan.dit.timeexpense.model.display.Ag1Bean;
import gov.michigan.dit.timeexpense.model.display.Ag2Bean;
import gov.michigan.dit.timeexpense.model.display.Ag3Bean;

import gov.michigan.dit.timeexpense.model.display.GrantBean;
import gov.michigan.dit.timeexpense.model.display.GrantPhaseBean;
import gov.michigan.dit.timeexpense.model.display.IndexCodesBean;
import gov.michigan.dit.timeexpense.model.display.MultiBean;
import gov.michigan.dit.timeexpense.model.display.PcaBean;
import gov.michigan.dit.timeexpense.model.display.ProjectBean;
import gov.michigan.dit.timeexpense.model.display.ProjectPhaseBean;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CodingBlockDSPTest{
	
/*	
	CodingBlockDAO codingBlockDao = new CodingBlockDAO();
	CommonDAO commonDao = new CommonDAO();
	
	ExpenseDAO expenseDAO = new ExpenseDAO();
	AdvanceDAO advanceDAO = new AdvanceDAO();
	AbstractDAO abstractDao = new AbstractDAO();
	ExpenseLineItemDAO lineItemDao = new ExpenseLineItemDAO();*/
	CodingBlockDSP codingBlockDsp;
	ExpenseDSP expenseDsp;
	AdvanceDSP advanceDsp;
	ExpenseLineItemDSP expenseLineItemDsp;
	
	private static EntityManagerFactory emf;
	private EntityManager em;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
	}
	
	@Before         // not a comment, a direction to the compiler, annotation
	public void setUp() { //throws Exception {

		em = emf.createEntityManager();
		codingBlockDsp = new CodingBlockDSP(em);
		expenseDsp = new ExpenseDSP(em);
		//advanceDsp = new AdvanceDSP(em);
		expenseLineItemDsp = new ExpenseLineItemDSP(em);
		em.getTransaction().begin();
/*		codingBlockDao.setEntityManager(em);   //attach this DAO to an entity manager
		codingBlockDsp.setCodingBlockDAO(codingBlockDao);  //attach this DSP to the above DAO
		
		commonDao.setEntityManager(em);
		codingBlockDsp.setCommonDao(commonDao);
		
		expenseDAO.setEntityManager(em);
		codingBlockDsp.setExpenseDao(expenseDAO);
		
		advanceDAO.setEntityManager(em);
		codingBlockDsp.setAdvanceDao(advanceDAO);
		
		lineItemDao.setEntityManager(em);
		codingBlockDsp.setLineItemDao(lineItemDao);*/
	}
	
	/*@Test
	public void testApplyCodingBlocks(){
		
		int expenseMasterId = 2;
		ExpenseMasters expenseMasters = expenseDsp.getExpense(2);
		
		List<ExpenseDetails> expenseDetailsList = expenseMasters.getExpenseDetailsCollection();
		
		//ExpenseDetailCodingBlocks codingBlocks1 = codingBlockDao.getEntityManager().find(ExpenseDetailCodingBlocks.class, 1006);
		//ExpenseDetailCodingBlocks codingBlocks2 = codingBlockDao.getEntityManager().find(ExpenseDetailCodingBlocks.class, 1007);
		List<ExpenseDetailCodingBlocks> cbList = new ArrayList<ExpenseDetailCodingBlocks>();
		
		ExpenseDetailCodingBlocks codingBlocks1 = new ExpenseDetailCodingBlocks();
		ExpenseDetailCodingBlocks codingBlocks2 = new ExpenseDetailCodingBlocks();
		
		codingBlocks1.setAgencyCode1("A");
		codingBlocks1.setStandardInd("N");
		codingBlocks1.setDollarAmount(20);
		codingBlocks1.setPercent(1);
		
		codingBlocks2.setAgencyCode1("A");
		codingBlocks2.setStandardInd("N");
		codingBlocks2.setDollarAmount(20);
		codingBlocks2.setPercent(1);
		
		
		cbList.add(codingBlocks1);
		cbList.add(codingBlocks2);
		
		ExpenseMasters expenseMaster = codingBlockDsp.applyCodingBlocks(expenseMasters,expenseDetailsList, cbList);
		Assert.assertTrue(expenseMaster.getExpenseDetailsCollection().get(0).getExpenseDetailCodingBlocksCollection().contains(codingBlocks1));
		Assert.assertTrue(expenseMaster.getExpenseDetailsCollection().get(1).getExpenseDetailCodingBlocksCollection().contains(codingBlocks1));
		
	}
	*/
	
	@After
	public void tearDown() throws Exception {
		if (em.getTransaction().isActive())
			em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void testSaveCodingBlocks() throws Exception{
		ExpenseMasters expenseMasters =  expenseDsp.getExpense(999);
		List<ExpenseDetails> expenseDetailsList = expenseMasters.getExpenseDetailsCollection();
		CodingBlockElement cbElement = new CodingBlockElement();
		cbElement.setDeptCode("59");
		cbElement.setAgency("01");
		cbElement.setTku("001");
		cbElement.setStatus("A");
		cbElement.setPayDate(new Date("01/19/2009"));
		
		UserSubject userSubject = new UserSubject();
		userSubject.setDepartment("08");
		userSubject.setAgency("01");
		userSubject.setTku("AL");
		List<DriverReimbExpTypeCbs> driverReibmCbs = new ArrayList<DriverReimbExpTypeCbs> ();
		ExpenseMasters savedExpense = codingBlockDsp.saveCodingBlocks(expenseMasters, expenseDetailsList, cbElement, new UserProfile("T_DEPT99"), userSubject, driverReibmCbs);
		
	}
	
	@Test
	public void testGetPayDate() {
//		Date expenseStartDate = new Date("02/02/2009");
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_MONTH, 2);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.YEAR, 2009);
		Date expenseStartDate = cal.getTime();
		System.out.println("Expense Start Date " + expenseStartDate);
		Date payDate = codingBlockDsp.getPayDate(expenseStartDate);
		System.out.println("Pay Date " + payDate);
		Assert.assertNotNull(payDate);
		
	}
	
	@Test
	public void testFormatDate() {
//		Date expenseStartDate = new Date("02/02/2009");
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DAY_OF_MONTH, 2);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.YEAR, 2009);
		Date date = cal.getTime();
		System.out.println("Date " + date);
		String stringDate = codingBlockDsp.getFormattedDate(date);
		System.out.println("String Date " + stringDate);
		Assert.assertNotNull(stringDate);
		
	}
		
	@Test
	public void testGetCBMetaData() {
		String deptCode = "59";
		String agy = "01";
		String tku = "01";
		TkuoptTaOptions tkuOptTa = codingBlockDsp.getCBMetaData(deptCode, agy, tku);
		Assert.assertNotNull(tkuOptTa);
							
	}
		
	@Test
	public void testDeleteExpenseDetailCodingBlocks() throws Exception{
			
		insertCodingBlocks();
		ExpenseDetails expenseDetails = expenseLineItemDsp.getExpenseDetailByExpenseDetailsId(127);
		List<ExpenseDetailCodingBlocks> expenseDtlsCBList = codingBlockDsp.getExpenseDetailCodingBlocksByExpenseDetails(expenseDetails);
		
		ExpenseMasters savedExpense  = codingBlockDsp.deleteExpenseDetailCodingBlocks(expenseDtlsCBList.get(0),expenseDetails.getExpmIdentifier());
		
		Assert.assertTrue(savedExpense.getExpenseDetailsCollection().get(0).getExpenseDetailCodingBlocksCollection().isEmpty());

	}
	
	public void insertCodingBlocks() {

		String Query = "Insert into EXPENSE_CODING_BLOCKS " +
		"(EXPDC_IDENTIFIER, EXPD_IDENTIFIER, CODING_BLOCK, STANDARD_IND, DOLLAR_AMOUNT, PERCENT, MODIFIED_DATE)"+
		"Values "+
		"(878798, 127, ' TEST CB   ', 'N', 400, 4, TO_DATE('01/14/2009 11:35:32', 'MM/DD/YYYY HH24:MI:SS'))";

		em.createNativeQuery(Query).executeUpdate();

		em.flush();

	    }
	
	@Test
	public void testGetCBAgencyOptions() {
		String deptCode = "59";
		String agy = "01";
		AgencyOptions agencyOptions = codingBlockDsp.getCBAgencyOptions(deptCode, agy);
		Assert.assertNotNull(agencyOptions);
								
		}
	
	@Test
	public void testGetProjectPhaseByProjectNo() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 19);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.YEAR, 2009);
		Date payDate = cal.getTime();
		System.out.println("Pay Date " + payDate);

		CodingBlockElement codingBlockElement = new CodingBlockElement();
		codingBlockElement.setCbElementType("PRO");
		codingBlockElement.setDeptCode("59");
		codingBlockElement.setAgency("01");
		codingBlockElement.setTku("001");
		codingBlockElement.setPayDate(payDate);
		codingBlockElement.setStatus("A");
		System.out.println("Agency " + codingBlockElement.getAgency());
		System.out.println("Appr Yr " + codingBlockElement.getAppropriationYear());
		System.out.println("CB Elem Type " + codingBlockElement.getCbElementType());
		System.out.println("Dept " + codingBlockElement.getDeptCode());
		System.out.println("Status " + codingBlockElement.getStatus());
		System.out.println("Tku " + codingBlockElement.getTku());
		System.out.println("Pay Date " + codingBlockElement.getPayDate());
		
		String projectNo = "100768";
		System.out.println(projectNo);
		
		List<ProjectPhaseBean> projectPhaseBeanList = codingBlockDsp.getProjectPhaseByProjectNo(codingBlockElement, projectNo);
		Assert.assertNotNull(projectPhaseBeanList);
		Assert.assertTrue(projectPhaseBeanList.size() > 0);
		for (ProjectPhaseBean projectPhaseBean:projectPhaseBeanList){
			//System.out.println(projectPhaseBean.getName() + " " + projectPhaseBean.getCode());
		}
	}
	
	@Test
	public void testGetGrantPhaseByGrantNo() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 19);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.YEAR, 2009);
		Date payDate = cal.getTime();
		System.out.println("Pay Date " + payDate);

		CodingBlockElement codingBlockElement = new CodingBlockElement();
		codingBlockElement.setCbElementType("GRA");
		codingBlockElement.setDeptCode("59");
		codingBlockElement.setAgency("01");
		codingBlockElement.setTku("001");
		codingBlockElement.setPayDate(payDate);
		codingBlockElement.setStatus("A");
		System.out.println("Agency " + codingBlockElement.getAgency());
		System.out.println("Appr Yr " + codingBlockElement.getAppropriationYear());
		System.out.println("CB Elem Type " + codingBlockElement.getCbElementType());
		System.out.println("Dept " + codingBlockElement.getDeptCode());
		System.out.println("Status " + codingBlockElement.getStatus());
		System.out.println("Tku " + codingBlockElement.getTku());
		System.out.println("Pay Date " + codingBlockElement.getPayDate());
		
		String grantNo = "000141";
		System.out.println(grantNo);
		
		List<GrantPhaseBean> grantPhaseBeanList = codingBlockDsp.getGrantPhaseByGrantNo(codingBlockElement, grantNo);
		Assert.assertNotNull(grantPhaseBeanList);
		Assert.assertTrue(grantPhaseBeanList.size() > 0);
		for (GrantPhaseBean grantPhaseBean:grantPhaseBeanList){
			//System.out.println(grantPhaseBean.getName() + " " + grantPhaseBean.getCode() + " " + grantPhaseBean.getGrant_phase());
		}
		
	}
	
	@Test
	public void testGetDefaultAgencyAdvanceCodingBlock() {
		String deptCode = "01";
		String agency = "01";
		Date requestDate = new Date("01/01/2008");
		
		List<DefaultDistributionsAdvAgy> defaulDistributionsAdvAgyList = codingBlockDsp.getDefaultAgencyAdvanceCodingBlock(deptCode, agency,requestDate);
		Assert.assertNotNull(defaulDistributionsAdvAgyList);
		Assert.assertTrue(defaulDistributionsAdvAgyList.size() > 0);
		for (DefaultDistributionsAdvAgy defaultDistributionsAdvAgy:defaulDistributionsAdvAgyList){
			System.out.println(defaultDistributionsAdvAgy.getAppropriationYear() + " " + defaultDistributionsAdvAgy.getCodingBlock() + " " + defaultDistributionsAdvAgy.getIndexCode() + " " + defaultDistributionsAdvAgy.getPca());
		}
	}
	@Test
	public void testValidateExpenseCodingBlocks() {
		System.out.println( "at beginning of testValidateExpenseCodingBlocks");
		ExpenseMasters expenseMasters = getExpenseMasters();
		List<ExpenseDetails> expenseDetailsList = expenseMasters.getExpenseDetailsCollection();
		CodingBlockElement codingBlockElement = new CodingBlockElement();
		codingBlockElement.setAgency("01");
		codingBlockElement.setAppropriationYear("09");
		codingBlockElement.setCbElementType("IDX");
		codingBlockElement.setDeptCode("59");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 19);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.YEAR, 2009);
		Date payDate = cal.getTime();
		codingBlockElement.setPayDate(payDate);
		codingBlockElement.setStatus("A");
		codingBlockElement.setTku("001");
		UserProfile profile = new UserProfile("T_HRMND99");
		
		UserSubject userSubject = new UserSubject();
		userSubject.setDepartment("08");
		userSubject.setAgency("01");
		userSubject.setTku("AL");
		
		try {
			List<DriverReimbExpTypeCbs> driverReibmCbs = new ArrayList<DriverReimbExpTypeCbs> ();
			codingBlockDsp.validateExpenseCodingBlocks(expenseMasters,codingBlockElement, expenseDetailsList, profile, userSubject, driverReibmCbs);
		} catch (ExpenseException e) {
			e.printStackTrace();
		}
		// both asserts test for there to exist error messages
		Assert.assertNotNull(expenseMasters.getExpenseErrorsCollection());
		Assert.assertTrue(!expenseMasters.getExpenseErrorsCollection().isEmpty());
	}
	
	@Test
	public void testValidateAdvanceCodingBlocks() {
		System.out.println("at beginning of testValidateAdvanceCodingBlocks");
		AdvanceMasters advanceMasters = getAdvanceMasters();
		List<AdvanceDetails> advanceDetailsList = advanceMasters.getAdvanceDetailsCollection();
		CodingBlockElement codingBlockElement = new CodingBlockElement();
		codingBlockElement.setAgency("01");
		codingBlockElement.setAppropriationYear("08");
		codingBlockElement.setCbElementType("");
		codingBlockElement.setDeptCode("59");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 19);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.YEAR, 2009);
		Date payDate = cal.getTime();
		codingBlockElement.setPayDate(payDate);
		codingBlockElement.setStatus("A");
		codingBlockElement.setTku("001");
		System.out.println("before call validate");
	//	codingBlockDsp.validateAdvanceCodingBlocks(codingBlockElement, advanceDetailsList);
		// both asserts test for there to exist error messages
		Assert.assertNotNull(advanceMasters.getAdvanceErrorsCollection());
		Assert.assertTrue(!advanceMasters.getAdvanceErrorsCollection().isEmpty());
		}

	@SuppressWarnings("unused")
	@Test
	public void testFindCodingBlocksDetails(){
		String deptCode = "59";
		String agency = "01";
		String tku = "001";
		
		Date expEndDate = null;
		TkuoptTaOptions tkuOptions = codingBlockDsp.getCBMetaData(deptCode, agency, tku);
			
		Date expenseEndDate = new Date("02/03/2008");
		System.out.println("expense end date " + expenseEndDate);
		String apprYear = "08";
		List<List> returnList = codingBlockDsp.findCodingBlocksDetails(deptCode, agency, tku, tkuOptions, expenseEndDate, apprYear);
		List <IndexCodesBean> indexList = (List <IndexCodesBean>)returnList.get(0);
		Assert.assertTrue(indexList.size()>0);
		System.out.println("index list size = " + indexList.size());
		List <PcaBean> pcaList = (List <PcaBean>)returnList.get(1);
		Assert.assertTrue(pcaList.size()>0);
		System.out.println("pca list size = " + pcaList.size());
		List <GrantBean> grantList = (List <GrantBean>)returnList.get(2);
		Assert.assertTrue(grantList.size()>0);
		System.out.println("grant list size = " + grantList.size());
		List <Ag1Bean> ag1List = (List <Ag1Bean>)returnList.get(3);
		Assert.assertTrue(ag1List.size()>0);
		System.out.println("agency code 1 list size = " + ag1List.size());
		List <ProjectBean> projectList = (List <ProjectBean>)returnList.get(4);
		Assert.assertTrue(projectList.size()>0);
		System.out.println("project list size = " + projectList.size());
		List <Ag2Bean> ag2List = (List <Ag2Bean>)returnList.get(5);
		Assert.assertTrue(ag2List.size()>0);
		System.out.println("agency code 2 list size = " + ag2List.size());
		List <Ag3Bean> ag3List = (List <Ag3Bean>)returnList.get(6);
		Assert.assertTrue(ag3List.size()>0);
		System.out.println("agency code 3 list size = " + ag3List.size());
		List <MultiBean> multiList = (List <MultiBean>)returnList.get(7);
		Assert.assertTrue(multiList.size()>0);
		System.out.println("multipurpose code list size = " + multiList.size());

		Assert.assertTrue(indexList.size()>0);
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
	
	private ExpenseMasters prepareExpenseMasters(){

		
		ExpenseEvents expenseEvents = new ExpenseEvents();
		expenseEvents.setAppointmentId(108000);
		
		
		expenseDsp.saveExpenseEvent(expenseEvents);
		
		em.flush();

		ExpenseMasters expenseMasters = new ExpenseMasters();

		expenseMasters.setComments("Test Expense Data");
		expenseMasters.setCurrentInd("Y");
		expenseMasters.setExpevIdentifier(expenseEvents);
		expenseMasters.setNatureOfBusiness("Personal");
		expenseMasters.setTravelInd("Y");
		expenseMasters.setSuperReviewedReceiptsInd("Y");
		expenseMasters.setExpDateFrom(new Date("02/02/2009"));
		expenseMasters.setExpDateTo(new Date("02/10/2009"));
		expenseMasters.setOutOfStateInd("Y");
		expenseMasters.setStatus("SUBM");

		expenseMasters.setExpenseErrorsCollection(null);
		expenseMasters.setExpenseDetailsCollection(null);

		return expenseMasters;
	}

	private ExpenseMasters getExpenseMasters(){

		ExpenseMasters expenseMasters = expenseDsp.getExpense(2);
		//em.flush();
		return expenseMasters;
	}

/*	public AdvanceDetails prepareAdvanceDetails() {
		// TODO Auto-generated method stub
		AdvanceDetails advanceDetails = new AdvanceDetails();
		advanceDetails.setDollarAmount(100);

		advanceDetails.setPytpIdentifier(codingBlockDao.findPayTypes(3030));
		advanceDetails.setAdvanceDetailCodingBlocksCollection(null);
		
		return advanceDetails;
	}

	private AdvanceMasters prepareAdvanceMasters(){

		Appointments appointments = expenseDAO.findAppointment(108000);
		
		AdvanceEvents advanceEvents = new AdvanceEvents();
		advanceEvents.setApptIdentifier(appointments);

		advanceDAO.saveAdvanceEvent(advanceEvents);
		
		em.flush();

		AdvanceMasters advanceMasters = new AdvanceMasters();

		advanceMasters.setAdvanceReason(null);
		advanceMasters.setCurrentInd("Y");
		advanceMasters.setFromDate(new Date("02/02/2009"));
		advanceMasters.setManualDepositAmount(null);
		advanceMasters.setManualDepositDocNum(null);
		advanceMasters.setManualDepositInd("N");
		advanceMasters.setManualWarrantDocNum(null);
		advanceMasters.setManualWarrantIssdInd("N");
		advanceMasters.setPaidPpEndDate(null);
		advanceMasters.setPermanentAdvInd("N");
		advanceMasters.setRequestDate(null);
		short revisionNumber = 0;
		advanceMasters.setRevisionNumber(revisionNumber);
		advanceMasters.setStatus(null);
		advanceMasters.setToDate(new Date("02/10/2009"));
		advanceMasters.setAdvanceErrorsCollection(null);
		advanceMasters.setAdvanceActionsCollection(null);
		advanceMasters.setAdvanceDetailsCollection(null);
		advanceMasters.setAdvanceLiquidationsCollection(null);

		return advanceMasters;
	}*/	
	private AdvanceMasters getAdvanceMasters(){

//		AdvanceMasters advanceMasters = advanceDAO.findAdvanceByMasterId(1);
//		em.flush();
		//return advanceMasters;
		return null;
	}
	
	
	@Test
	public void testUpdateModifiedAmounts(){
		ExpenseMasters expenseMasters = expenseDsp.getExpense(2018);
		ExpenseDetails detail1 = expenseMasters.getExpenseDetailsCollection().get(0);
		detail1.setDollarAmount(100);
		ExpenseDetails detail2 = expenseMasters.getExpenseDetailsCollection().get(1);
		detail2.setDollarAmount(200);
		ExpenseDetails detail3 = expenseMasters.getExpenseDetailsCollection().get(2);
		detail3.setDollarAmount(300);
		UserProfile profile = new UserProfile("T_HRMND99");
		codingBlockDsp.updateExpenseDetailAmounts(expenseMasters, null);
		Assert.assertTrue(expenseMasters != null);
	}

}
