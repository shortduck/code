package gov.michigan.dit.timeexpense.dao.test;

import gov.michigan.dit.timeexpense.dao.CodingBlockDAO;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseLineItemDAO;
import gov.michigan.dit.timeexpense.model.core.CodingBlockElement;
import gov.michigan.dit.timeexpense.model.core.DriverReimbExpTypeCbs;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypes;
import gov.michigan.dit.timeexpense.model.core.TkuoptTaOptions;
import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CodingBlockDAOTest extends AbstractDAOTest {

	private CodingBlockDAO dao = new CodingBlockDAO();
	private ExpenseLineItemDAO lineItemDao = new ExpenseLineItemDAO();
	private ExpenseDAO expenseDAO = new ExpenseDAO();
	private CommonDAO commonDao = new CommonDAO();

	public CodingBlockDAOTest() {
		// TODO Auto-generated constructor stub
		Logger logger = Logger.getLogger(CodingBlockDAOTest.class);
	}

	@Before
	public void startTransaction() {
		super.beginTransaction();
		dao.setEntityManager(em);
		lineItemDao.setEntityManager(em);
		expenseDAO.setEntityManager(em);
		commonDao.setEntityManager(em);
	}

	@Test
	public void testFindCBMetaData() {
		String dept = "47";
		String agy = "05";
		String tku = "010";
		TkuoptTaOptions tkuoptTaOptions = dao.findCBMetaData(dept,
				agy, tku);
		System.out.println(tkuoptTaOptions.getAdvAg1EntryInd());
		Assert.assertNotNull(tkuoptTaOptions);

	}

	@Test
	public void testFindCBAgencyOptions() {
		String dept = "47";
		String agy = "05";

		AgencyOptions agencyOptions = commonDao.findAgencyOptions(dept, agy);

		Assert.assertNotNull(agencyOptions);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindPayDate() {
		Date expenseStartDate = new Date("02/03/2008");
		Date payDate = dao.findPayDate(expenseStartDate);
		System.out.println(expenseStartDate + "    Pay Date ::" +  payDate);
		Assert.assertNotNull(payDate);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testFindAllIndexes() {
		CodingBlockElement cbElementBean = new CodingBlockElement();

		Date pay_date = new Date("12/06/2008");
		cbElementBean.setAppropriationYear("09");
		cbElementBean.setDeptCode("59");
		cbElementBean.setAgency("01");
		cbElementBean.setTku("AL");
		cbElementBean.setCbElementType("IDX");
		cbElementBean.setStatus("A");
		cbElementBean.setPayDate(pay_date);

		List<IndexCodesBean> indexCodesList = dao.findAllIndexes(cbElementBean);

		Assert.assertNotNull(indexCodesList);
	}

	@Test
	public void testFindAllPCA() {
		CodingBlockElement cbElementBean = new CodingBlockElement();

		Date pay_date = new Date("12/06/2008");
		cbElementBean.setAppropriationYear("06");
		cbElementBean.setDeptCode("01");
		cbElementBean.setAgency("01");
		cbElementBean.setTku("001");
		cbElementBean.setCbElementType("PCA");
		cbElementBean.setStatus("A");
		cbElementBean.setPayDate(pay_date);

		List<PcaBean> pcaList = dao.findAllPCA(cbElementBean);

		Assert.assertNotNull(pcaList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllGrantNo() {
		CodingBlockElement cbElementBean = new CodingBlockElement();

		Date pay_date = new Date("12/06/2008");
		cbElementBean.setAppropriationYear("06");
		cbElementBean.setDeptCode("01");
		cbElementBean.setAgency("01");
		cbElementBean.setTku("001");
		cbElementBean.setCbElementType("GRA");
		cbElementBean.setStatus("A");
		cbElementBean.setPayDate(pay_date);

		List<GrantBean> grantList = dao.findAllGrantNo(cbElementBean);

		Assert.assertNotNull(grantList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllProjectNo() {
		CodingBlockElement cbElementBean = new CodingBlockElement();

		Date pay_date = new Date("12/06/2008");
		cbElementBean.setAppropriationYear("06");
		cbElementBean.setDeptCode("01");
		cbElementBean.setAgency("01");
		cbElementBean.setTku("001");
		cbElementBean.setCbElementType("PRO");
		cbElementBean.setStatus("A");
		cbElementBean.setPayDate(pay_date);

		List<ProjectBean> projectList = dao.findAllProjectNo(cbElementBean);

		Assert.assertNotNull(projectList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllAg1() {
		CodingBlockElement cbElementBean = new CodingBlockElement();

		Date pay_date = new Date("12/06/2008");
		cbElementBean.setAppropriationYear("06");
		cbElementBean.setDeptCode("59");
		cbElementBean.setAgency("01");
		cbElementBean.setTku("001");
		cbElementBean.setCbElementType("AG1");
		// cbElementBean.setStatus("A");
		cbElementBean.setPayDate(pay_date);

		List<Ag1Bean> ag1List = dao.findAllAg1(cbElementBean);

		Assert.assertNotNull(ag1List);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllAg2() {
		CodingBlockElement cbElementBean = new CodingBlockElement();

		Date pay_date = new Date("12/06/2008");

		cbElementBean.setAppropriationYear("06");
		cbElementBean.setDeptCode("01");
		cbElementBean.setAgency("01");
		cbElementBean.setTku("001");
		cbElementBean.setCbElementType("AG2");
		cbElementBean.setStatus("A");
		cbElementBean.setPayDate(pay_date);

		List<Ag2Bean> ag2List = dao.findAllAg2(cbElementBean);

		Assert.assertNotNull(ag2List);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllAg3() {
		CodingBlockElement cbElementBean = new CodingBlockElement();

		Date pay_date = new Date("12/06/2008");
		cbElementBean.setAppropriationYear("06");
		cbElementBean.setDeptCode("01");
		cbElementBean.setAgency("01");
		cbElementBean.setTku("001");
		cbElementBean.setCbElementType("AG3");
		cbElementBean.setStatus("A");
		cbElementBean.setPayDate(pay_date);

		List<Ag3Bean> ag3List = dao.findAllAg3(cbElementBean);

		Assert.assertNotNull(ag3List);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindGrantPhaseByGrantNo() {
		CodingBlockElement cbElementBean = new CodingBlockElement();

		Date pay_date = new Date("01/19/2009");
		cbElementBean.setAppropriationYear("09");
		cbElementBean.setDeptCode("59");
		cbElementBean.setAgency("01");
		cbElementBean.setTku("001");
		cbElementBean.setCbElementType("GRA");
		cbElementBean.setStatus("A");
		cbElementBean.setPayDate(pay_date);
		String grantNo = "000141";

		List<GrantPhaseBean> grantPhaseList = dao.findGrantPhaseByGrantNo(
				cbElementBean, grantNo);

		Assert.assertNotNull(grantPhaseList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllMulti() {
		CodingBlockElement cbElementBean = new CodingBlockElement();

		Date pay_date = new Date("12/06/2008");
		cbElementBean.setAppropriationYear("06");
		cbElementBean.setDeptCode("01");
		cbElementBean.setAgency("01");
		cbElementBean.setTku("001");
		cbElementBean.setCbElementType("MUL");
		cbElementBean.setStatus("A");
		cbElementBean.setPayDate(pay_date);

		List<MultiBean> multiList = dao.findAllMulti(cbElementBean);

		Assert.assertNotNull(multiList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindProjectPhaseByprojectNo() {
		CodingBlockElement cbElementBean = new CodingBlockElement();

		Date pay_date = new Date("12/06/2008");
		cbElementBean.setAppropriationYear("09");
		cbElementBean.setDeptCode("59");
		cbElementBean.setAgency("01");
		cbElementBean.setTku("100");
		cbElementBean.setCbElementType("PRO");
		cbElementBean.setStatus("A");
		cbElementBean.setPayDate(pay_date);
		String projectNo = "100768";

		List<ProjectPhaseBean> projPhaseList = dao.findProjectPhaseByProjectNo(
				cbElementBean, projectNo);

		Assert.assertNotNull(projPhaseList);
	}

	@Test
	public void testfindExpenseDetailCodingBlocks() {
		int expenseMasterId = 1051;
		ExpenseDetails expenseLineItem = null;

		ExpenseMasters expenseMasters = expenseDAO.findExpenseByExpenseMasterId(expenseMasterId); 
		List<ExpenseDetails> expLineItems = lineItemDao
				.findExpenseLineItems(expenseMasters);
		if (expLineItems != null) {
			expenseLineItem = expLineItems.get(0);
		}
		List<ExpenseDetailCodingBlocks> cbList = dao
				.findExpenseDetailCodingBlocks(expenseLineItem);

		Assert.assertNotNull(cbList);
	}
	
	@Test
	public void testValidateExpenseDetailCodingBlocks(){
		
		CodingBlockElement cbElementBean = new CodingBlockElement();
		ExpenseDetailCodingBlocks codingBlock = new ExpenseDetailCodingBlocks();
		
		codingBlock.setFacsAgy("591");
		codingBlock.setAppropriationYear("08");
		codingBlock.setIndexCode("61325");
		codingBlock.setPca("53600");
		codingBlock.setGrantNumber("1");
		codingBlock.setGrantPhase("1");
		codingBlock.setAgencyCode1("1");
		codingBlock.setProjectNumber("78950A");
		codingBlock.setProjectPhase("00");
		codingBlock.setAgencyCode2("1");
		codingBlock.setAgencyCode3("1");
		codingBlock.setMultipurposeCode("1");
		
		Date pay_date = new Date("02/09/2008");
		cbElementBean.setAppropriationYear("06");
		cbElementBean.setDeptCode("59");
		cbElementBean.setAgency("01");
		cbElementBean.setTku("415");
		cbElementBean.setCbElementType("IDX");
		cbElementBean.setStatus("A");
		cbElementBean.setPayDate(pay_date);
		
		String result =  dao.validateExpenseCodingBlocks(codingBlock, cbElementBean);
		Assert.assertNotNull(result);
	}
	

	
	static int lineNo = 30;
	private static int getLineItem(){
		lineNo = lineNo +1;
		return lineNo;
	}

	
	public ExpenseMasters prepareMasterWithCodingBlocks() {
		// TODO Auto-generated method stub
		ExpenseMasters expenseMasters = prepareExpenseMasters();
		ExpenseDetails expenseDetails = prepareExpenseDetails();
		expenseDetails.setLineItem(getLineItem());
		
		ExpenseDetailCodingBlocks codingBlock1 = new ExpenseDetailCodingBlocks();
		codingBlock1.setAgencyCode1("TAG1");
		codingBlock1.setAgencyCode2("TAG2");
		codingBlock1.setAgencyCode3("TAG3");
		codingBlock1.setAppropriationYear("8");
		codingBlock1.setDollarAmount(50);
		codingBlock1.setPercent(1);
		codingBlock1.setIndexCode("IDX");
		codingBlock1.setPca("TPCA");
		codingBlock1.setMultipurposeCode("TMULTI");
		codingBlock1.setStandardInd("Y");
		codingBlock1.setExpdIdentifier(expenseDetails);
		
		ExpenseDetailCodingBlocks codingBlock2 = new ExpenseDetailCodingBlocks();
		codingBlock2.setAgencyCode1("TAG1");
		codingBlock2.setAgencyCode2("TAG2");
		codingBlock2.setAgencyCode3("TAG3");
		codingBlock2.setAppropriationYear("8");
		codingBlock2.setDollarAmount(50);
		codingBlock2.setPercent(1);
		codingBlock2.setIndexCode("IDX");
		codingBlock2.setPca("TPCA");
		codingBlock2.setMultipurposeCode("TMULTI");
		codingBlock2.setStandardInd("Y");
		codingBlock2.setExpdIdentifier(expenseDetails);
				
		List<ExpenseDetailCodingBlocks> codingBlockList = new ArrayList<ExpenseDetailCodingBlocks>();
		codingBlockList.add(codingBlock1);
		codingBlockList.add(codingBlock2);
		
		ExpenseMasters savedExpense = expenseDAO.saveExpense(expenseMasters);

		em.flush();
		
		List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();
		
		expenseDetails.setExpmIdentifier(savedExpense);
		expenseDetailsList.add(expenseDetails);
		savedExpense.setExpenseDetailsCollection(expenseDetailsList);
	
		expenseDAO.saveExpense(savedExpense);
		
		em.flush();
		
		Iterator iter = savedExpense.getExpenseDetailsCollection().iterator();
		while(iter.hasNext()){
			ExpenseDetails expenseDtls = (ExpenseDetails)iter.next();
			expenseDtls.setExpenseDetailCodingBlocksCollection(codingBlockList);
			expenseDetailsList.add(expenseDtls);
		}
		savedExpense.setExpenseDetailsCollection(expenseDetailsList);
		
		expenseDAO.saveExpense(savedExpense);
	
		return savedExpense;
	}
	
	private ExpenseMasters prepareExpenseMasters(){

		
		ExpenseEvents expenseEvents = new ExpenseEvents();
		expenseEvents.setAppointmentId(108000);
		
		expenseDAO.saveExpense(expenseEvents);
		
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
	
	@Test
	public void testFindDefaultAgencyAdvanceCodingBlock(){
		
		Date requestDate = Calendar.getInstance().getTime();
		List<DefaultDistributionsAdvAgy> list = dao.findDefaultAgencyAdvanceCodingBlock("01", "01",requestDate);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size()>0);
		System.out.println("Agency Code 1 :"+list.get(0).getAgencyCode1());
	}
	
	
	public ExpenseMasters prepareCodingBlocks(){
		
			// TODO Auto-generated method stub
			ExpenseMasters expenseMasters = prepareExpenseMasters();
			ExpenseDetails expenseDetails = prepareExpenseDetails();
			expenseDetails.setExpmIdentifier(expenseMasters);
			List<ExpenseDetails> expenseDetaislList = new ArrayList<ExpenseDetails>();
			expenseDetaislList.add(expenseDetails);
			
			expenseDetails.setLineItem(getLineItem());
			
			ExpenseDetailCodingBlocks codingBlock1 = new ExpenseDetailCodingBlocks();
			codingBlock1.setAgencyCode1("TG12");
			codingBlock1.setAgencyCode2("TG22");
			codingBlock1.setAgencyCode3("TG33");
			codingBlock1.setAppropriationYear("8");
			codingBlock1.setDollarAmount(50);
			codingBlock1.setPercent(1);
			codingBlock1.setIndexCode("IDX5");
			codingBlock1.setPca("TPCA");
			codingBlock1.setMultipurposeCode("TMULT");
			codingBlock1.setStandardInd("Y");
			codingBlock1.setExpdIdentifier(expenseDetails);
			
			ExpenseDetailCodingBlocks codingBlock2 = new ExpenseDetailCodingBlocks();
			codingBlock2.setAgencyCode1("TG15");
			codingBlock2.setAgencyCode2("TG25");
			codingBlock2.setAgencyCode3("TG35");
			codingBlock2.setAppropriationYear("85");
			codingBlock2.setDollarAmount(50);
			codingBlock2.setPercent(1);
			codingBlock2.setIndexCode("IDX2");
			codingBlock2.setPca("TPCA");
			codingBlock2.setMultipurposeCode("TMULT");
			codingBlock2.setStandardInd("Y");
			codingBlock2.setExpdIdentifier(expenseDetails);
					
			List<ExpenseDetailCodingBlocks> codingBlockList = new ArrayList<ExpenseDetailCodingBlocks>();
			codingBlockList.add(codingBlock1);
			codingBlockList.add(codingBlock2);
			
			expenseDetails.setExpenseDetailCodingBlocksCollection(codingBlockList);
			
			expenseMasters.setExpenseDetailsCollection(expenseDetaislList);
			
			ExpenseMasters savedExpense = expenseDAO.saveExpense(expenseMasters);

			em.flush();
			
			return savedExpense;
	}
	
	@Test
	public void testFindDriverReimbExpTypeCbs(){
		
		List<DriverReimbExpTypeCbs> list = dao.findDriverReimbExpTypeCb("08", "01","0550");
		Assert.assertTrue(list.size()>0);
	}
}
