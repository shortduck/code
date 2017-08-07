/**
 * 
 */
package gov.michigan.dit.timeexpense.action.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.michigan.dit.timeexpense.action.ExpenseCodingBlockAction;
import gov.michigan.dit.timeexpense.model.core.CodingBlockElement;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.TkuoptTaOptions;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
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

/**
 * @author GhoshS
 *
 */
public class ExpenseCodingBlockActionTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private static ExpenseCodingBlockAction action = new ExpenseCodingBlockAction ();
	private CodingBlockDSP codingBlockService;
	private ExpenseDSP expenseService;
	
	
	@BeforeClass
	public static void init() {
		Map<String, Object> session = new HashMap<String, Object>();
		action.setSession(session);
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@Before
	public void setUp() throws Exception {
		em = emf.createEntityManager();
		
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		String dept = "59";
		String agency = "01";
		String tku = "001";
		
		sessionMap.put(IConstants.LOGGED_IN_USER_DEPARTMENT, dept);
		sessionMap.put(IConstants.LOGGED_IN_USER_AGENCY, agency);
		sessionMap.put(IConstants.LOGGED_IN_USER_TKU, tku);
		
		expenseService = new ExpenseDSP(em);
		codingBlockService = new CodingBlockDSP(em);
		ExpenseMasters expenseMaster = expenseService.getExpense(1051);
		sessionMap.put(IConstants.EXPENSE_SESSION_DATA, expenseMaster);
		
		UserSubject userSubject = new UserSubject();
		userSubject.setDepartment("59");
		userSubject.setAgency("01");
		userSubject.setTku("001");
		
		UserProfile userProfile = new UserProfile("T_DEPT99",134067);
		
		ExpenseDetailCodingBlocks expCB = new ExpenseDetailCodingBlocks();
		expCB.setPercent(100);
		expCB.setAppropriationYear("08");
		expCB.setDollarAmount(20);
		expCB.setStandardInd("N");
		
		List<ExpenseDetailCodingBlocks> exCBList = new ArrayList<ExpenseDetailCodingBlocks>();
		sessionMap.put(IConstants.CODING_BLOCK_LIST,exCBList);
		
		ExpenseDetails expenseDetails = expenseMaster.getExpenseDetailsCollection().get(0);
		
		List<ExpenseDetails> expList = new ArrayList<ExpenseDetails>();
		expList.add(expenseDetails);
		
		CodingBlockElement cbElement = new CodingBlockElement();
		cbElement.setDeptCode(userSubject.getDepartment());
		cbElement.setAgency(userSubject.getAgency());
		cbElement.setTku(userSubject.getTku());
		cbElement.setStatus("A");
		cbElement.setPayDate(expenseMaster.getExpDateTo());
		sessionMap.put(IConstants.CODING_BLOCK_ELEMENTS_DATA, cbElement);
		
		sessionMap.put(IConstants.EXPENSE_DETAILS_LIST_TO_BE_UPDATED, expList);
		
		sessionMap.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, userSubject);
		sessionMap.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, userProfile);
		action.setSession(sessionMap);
		action.setEntityManager(em);
		action.prepare();
		
		em.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		if (em.getTransaction().isActive())
			em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void testGetExpenseDetailsList(){
		
		Map<String,Object> session = new HashMap<String,Object>();
		ExpenseMasters expenseMasters = expenseService.getExpense(104);
		session.put(IConstants.CURR_EXPENSEMASTER, expenseMasters);
		
		String result = action.getExpenseDetailsList();
		Assert.assertEquals(IConstants.SUCCESS, result);
		
		
	}
	
	@Test
	public void testGetExpenseDetailCodingBlock(){
		action.setExpDtlsId(1009);
		String result = action.getExpenseDetailCodingBlock();
		Assert.assertEquals(IConstants.SUCCESS, result);
	}
	
	@Test
	public void testPrepareExpenseCodingBlockMetaData() throws Exception{
		String dept = "59";
		String agency = "01";
		String tku = "001";
		ExpenseMasters expenseMasters = expenseService.getExpense(104);
		TkuoptTaOptions tkuoptTaOptions = codingBlockService.getCBMetaData(dept, agency, tku);
		String codingBlockStore = action.prepareExpenseCodingBlockMetaData(tkuoptTaOptions,expenseMasters);
		Assert.assertTrue(codingBlockStore.length()>0);
	}
	
	@Test
	public void testPrepareExpenseCodingBlocksData(){
		String result = action.prepareExpenseCodingBlocksData(1009);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testGetGrantPhase(){
		
		action.setGrantNo("19033");
		String result = action.getGrantPhase();
		Assert.assertEquals("success", result);
		
		String json=action.getJsonResponse();
		Assert.assertTrue(!StringUtils.isEmpty(json));
	}
	
	@Test
	public void testGetProjectPhase(){
		
		action.setProjectNo("100764");
		String result = action.getProjectPhase();
		Assert.assertEquals("success", result);
		
		String json=action.getJsonResponse();
		Assert.assertTrue(!StringUtils.isEmpty(json));
	}
	
	@Test
	public void testGetNewStoreForApprYear() throws Exception{
		action.setApprYear("09");
		String result = action.getNewStoreForApprYear();
		Assert.assertEquals("success", result);
		
		String json=action.getJsonResponse();
		Assert.assertTrue(!StringUtils.isEmpty(json));
	}
	// ZH - Method no longer there
	/*@Test
	public void testApplyCodingBlock(){
		String codingBlockJson = "{\"lineItemId\":[\"1009\"],\"codingBlocks\":[{\"percent\":0,\"appropriationYear\":\"0.0\",\"indexCode\":\"00200   LANSING - DESIGN EXCEPTION REPORTING\",\"pca\":\"\",\"grantNumber\":\"\",\"grantPhase\":\"0.0\",\"agencyCode1\":\"\",\"agencyCode2\":\"\",\"agencyCode3\":\"\",\"projectNumber\":\"\",\"projectPhase\":\"0.0\",\"multipurposeCode\":\"\",\"standardInd\":\"N\",\"expdcIdentifier\":1443,\"expdIdentifier\":1009}]}";
		action.setCodingBlockJsonFromClient(codingBlockJson);
		String result = action.applyCodingBlock();
		Assert.assertNotNull(result);
	}*/
	
	@Test
	public void testSaveCodingBlock() throws Exception{
		action.setValidCodingBlock(true);
		String result = action.saveCodingBlock();
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testDeleteCodingBlock(){
		action.setExpenseCodingBlockId(1050);
		String result = action.deleteCodingBlock();
		Assert.assertEquals("success", result);
	}
}
