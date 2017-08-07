package gov.michigan.dit.timeexpense.action.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.michigan.dit.timeexpense.action.AdvanceAction;
import gov.michigan.dit.timeexpense.action.BaseAction;
import gov.michigan.dit.timeexpense.exception.TimeAndExpenseException;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.DisplayAdvance;
import gov.michigan.dit.timeexpense.service.AdvanceCodingBlockDSP;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class AdvanceActionTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private static AdvanceAction action = new AdvanceAction ();
	private AdvanceDSP advanceService;
	private Map<String, String[]> paramsMap;
	
	@BeforeClass
	public static void init() {
		Map session = new HashMap<String, Object>();
		UserProfile profile = new UserProfile ("T_DEPT_99", 134067);
		UserSubject subject = new UserSubject (134067, 109744, null, null, "59", "01", "100", "");
		session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
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
		em.getTransaction().begin();
		action.setEntityManager(em);
		advanceService = new AdvanceDSP (em);
		action.prepare();
	}

	@After
	public void tearDown() throws Exception {
		if (em.getTransaction().isActive())
			em.getTransaction().commit();
		em.close();
	}

/*	
	@Test
	public void testSubmitAdvance() {
		action.setAdvanceMasterId("1001");
		action.viewAdvance();
		action.submitAdvance();
		assertEquals((action.getAdvanceMaster().getStatus()), "SUBM");
	}
	
	@Test
	public void testRejectAdvance() {
		action.setAdvanceMasterId("1001");
		action.viewAdvance();
		action.rejectAdvance();
		assertEquals((action.getAdvanceMaster().getStatus()), "RJCT");
	}
	
	@Test
	public void testGetPreviousRevision() {
		action.setAdvanceMasterId("10002");
		action.viewAdvance();
		action.getPreviousRevision();
		assertEquals((action.getAdvanceMaster().getRevisionNumber()), 0);
	}
	
	@Test
	public void testGetNextRevision() {
		action.setAdvanceMasterId("10001");
		action.viewAdvance();
		action.getNextRevision();
		assertEquals((action.getAdvanceMaster().getRevisionNumber()), 1);
	}
	*/
	
	/**
	 * Creates a new advance with no coding block entry and only required fields entered
	 */
	@Test
	public void testSaveNew_NoCB() {
		action.setDisplay(this.prepareInputDisplayRequiredFields());
		action.setParamMap(new HashMap());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	/**
	 * Creates a new Permanent advance with required fields only. No CB is included
	 */
	
	@Test
	public void testSaveNewWithReason_NoCB() {
		DisplayAdvance display = this.prepareInputDisplayRequiredFields();
		display.setAdvanceReason("From Junit");
		action.setDisplay(display);
		action.setParamMap(new HashMap());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	
	/**
	 * Creates a new Permanent advance with required fields only. No CB is included
	 */
	
	@Test
	public void testSaveNewWithPermanentFlag_NoCB() {
		DisplayAdvance display = this.prepareInputDisplayRequiredFields();
		display.setPermanentAdvInd("true");
		action.setDisplay(display);
		action.setParamMap(new HashMap());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	/**
	 * Creates a new advance with manual warrant. A manual warrant doc number is included
	 */
	
	@Test
	public void testSaveNewWithManualWarrantWithDocNumber_NoCB() {
		DisplayAdvance display = this.prepareInputDisplayRequiredFields();
		display.setManualWarrantIssdInd("Yes");
		display.setManualWarrantDocNum("ZH1234");
		action.setDisplay(display);
		action.setParamMap(new HashMap());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	/**
	 * Negative test for manual warrant with no doc number provided, should produce an error
	 */
	
	@Test
	public void testSaveNewWithManualWarrant_NoDocNumber_NoCB() {
		DisplayAdvance display = this.prepareInputDisplayRequiredFields();
		display.setManualWarrantIssdInd("Yes");
		action.setDisplay(display);
		action.setParamMap(new HashMap());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	/**
	 * Creates a new advance with only required fields and manual deposit
	 */
	
	@Test
	public void testSaveNewWithManualDeposit_NoCB() {
		DisplayAdvance display = this.prepareInputDisplayRequiredFields();
		display.setManualDepositDocNum("ZH-124");
		display.setManualDepositAmount("123.45");
		action.setDisplay(display);
		action.setParamMap(new HashMap());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	/**
	 * Creates a new advance with only required fields and Std Coding block at 100%
	 */
	
	@Test
	public void testSaveNew_StdCBSingleRow() {
		action.setDisplay(this.prepareInputDisplayRequiredFields());
		action.setParamMap(this.prepareInputStdCBSingleRow());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	@Test
	public void testSaveNew_IndexPCACBSingleRow() {
		action.setDisplay(this.prepareInputDisplayRequiredFields());
		action.setParamMap(this.prepareMapWithIndexPCACBSingleRow());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	@Test
	public void testSaveNewWithManualWarrant() {
		DisplayAdvance display = this.prepareInputDisplayRequiredFields();
		display.setManualWarrantIssdInd("Y");
		display.setManualWarrantDocNum("1234");
		action.setDisplay(display);
		Map params = this.prepareMapWithIndexPCACBSingleRow();
		action.setParamMap(params);
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	@Test
	public void testViewAdvance() {
		action.setAdvanceEventId(1416);
		action.viewAdvance();
		assertNotNull(action.getAdvanceMaster());
	}
	
	private DisplayAdvance prepareInputDisplayRequiredFields (){
		DisplayAdvance display = new DisplayAdvance ();
		display.setRequestDate("1/1/2009");
		display.setFromDate("1/1/2009");
		display.setToDate("1/9/2009");
		display.setDollarAmount(50.15);
		display.setAdvanceReason("Testing new Save method");
		
		return display;
	}
	
	
	private Map prepareInputMap (){
		Map<String, String[]> paramsMap = new HashMap ();
		String [] requestDate = {"1/1/2009"};
		paramsMap.put("display.requestDate", requestDate);
		String [] fromDate = {"1/1/2009"};
		paramsMap.put("display.fromDate", fromDate);
		String [] toDate = {"1/9/2009"};
		paramsMap.put("display.toDate", toDate);
		String [] dollarAmount = {"50.15"};
		paramsMap.put("display.dollarAmount", dollarAmount);
		String [] manualWarrantIssdInd = {"N"};
		paramsMap.put("display.manualWarrantIssdInd", manualWarrantIssdInd);
		String [] permanentAdvInd = {"true"};
		paramsMap.put("display.permanentAdvInd", permanentAdvInd);
		
		return paramsMap;
	}
	
	private Map prepareMapWithPermamnentFlag (){
		paramsMap = this.prepareInputMap();
		String [] permanentAdvInd = {"true"};
		paramsMap.put("display.permanentAdvInd", permanentAdvInd);
		
		return paramsMap;
	}
	
	private Map prepareMapWithManualWarrant (Map paramsMap){
		//paramsMap = this.prepareInputMap();
		String [] manualWarrantIssdInd = {"Y"};
		paramsMap.put("display.manualWarrantIssdInd", manualWarrantIssdInd);
		String [] manualWarrantDocNum = {"ding"};
		paramsMap.put("display.manualWarrantDocNum", manualWarrantDocNum);
		
		return paramsMap;
	}

	private Map prepareInputStdCBSingleRow (){
		Map paramsMap = new HashMap ();
		String [] pct = {"100"};
		paramsMap.put("pct", pct);
		String [] ay = {""};
		paramsMap.put("ay", ay);
		String [] std = {"on"};
		paramsMap.put("std", std);		
		return paramsMap;
	}
	
	private Map prepareMapWithIndexPCACBSingleRow (){
		paramsMap = this.prepareInputMap();
		String [] pct = {"100"};
		paramsMap.put("pct", pct);
		String [] ay = {"09"};
		paramsMap.put("ay", ay);	
		String [] index = {"09030"};
		paramsMap.put("index", index);
		String [] pca = {"88887"};
		paramsMap.put("pca", pca);
		return paramsMap;
	}
	
	@Test
	public void testSaveNewWithIndexCBTwoRows() {
		action.setDisplay(this.prepareInputDisplayRequiredFields());
		action.setParamMap(this.prepareMapWithIndexCBTwoRows());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	private Map prepareMapWithIndexCBTwoRows (){
		paramsMap = this.prepareInputMap();
		String [] pct = {"52", "48"};
		paramsMap.put("pct", pct);
		String [] ay = {"08", "08"};
		paramsMap.put("ay", ay);	
		String [] index = {"00206", "00207"};
		paramsMap.put("index", index);		
		
		return paramsMap;
	}
	
	@Test
	public void testSaveNewWithStdAndIndexTwoRowsCB() {
		action.setDisplay(this.prepareInputDisplayRequiredFields());
		action.setParamMap(this.prepareMapWithStdAndIndexTwoRowsCB());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	@Test
	public void testSaveExistingWithStdAndIndexTwoRowsCB() {
		action.setDisplay(this.prepareInputDisplayRequiredFields());
		action.setParamMap(this.prepareInputStdCBSingleRow());
		Map session = new HashMap ();
		session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER, advanceService.getAdvanceByMasterId(2264));
		UserSubject subject = new UserSubject (134067, 109744, null, null, "59", "01", "100", "");
		session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		UserProfile profile = new UserProfile ("T_DEPT_99", 134067);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
		action.setSession(session);
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}

	}
	
	private Map prepareMapWithStdAndIndexTwoRowsCB (){
		paramsMap = this.prepareInputMap();
		String [] std = {"on"};
		paramsMap.put("std", std);
		String [] pct = {"52", "48"};
		paramsMap.put("pct", pct);
		String [] ay = {"", "08"};
		paramsMap.put("ay", ay);	
		String [] index = {"", "00206"};
		paramsMap.put("index", index);		
		
		return paramsMap;
	}
	
	@Test
	public void testSaveNewWithIndexAndPcaSingleRowCB() {
		action.setDisplay(this.prepareInputDisplayRequiredFields());
		action.setParamMap(this.prepareMapWithIndexAndPcaSingleRowCB());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	@Test
	public void testUpdateWithIndexAndPcaSingleRowCB() {
		action.setDisplay(this.prepareInputDisplayRequiredFields());
		action.setParamMap(this.prepareMapWithIndexAndPcaSingleRowCB());
		AdvanceMasters advanceMaster = advanceService.getAdvanceByMasterId(2015);
		AdvanceDetailCodingBlocks cb = advanceMaster.getAdvanceDetailsCollection().get(0).getAdvanceDetailCodingBlocksCollection().get(0);
		Map session = new HashMap ();
		session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER, advanceMaster);
		UserSubject subject = new UserSubject (134067, 109744, null, null, "59", "01", "100", "");
		session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		UserProfile profile = new UserProfile ("T_DEPT_99", 134067);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
		action.setSession(session);
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	@Test
	public void testUpdateWithStdAndIndexTwoRowsCB() {
		action.setDisplay(this.prepareInputDisplayRequiredFields());
		action.setParamMap(this.prepareMapWithStdAndIndexTwoRowsCB());
		AdvanceMasters advanceMaster = advanceService.getAdvanceByMasterId(2015);
		AdvanceDetailCodingBlocks cb = advanceMaster.getAdvanceDetailsCollection().get(0).getAdvanceDetailCodingBlocksCollection().get(0);
		Map session = (Map) TimeAndExpenseUtil.getPrivateField(action, BaseAction.class, "session");
		session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER, advanceMaster);
		UserSubject subject = new UserSubject (134067, 109744, null, null, "59", "01", "100", "");
		session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		UserProfile profile = new UserProfile ("T_DEPT_99", 134067);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
		action.setSession(session);
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	private Map prepareMapWithIndexAndPcaSingleRowCB (){
		paramsMap = this.prepareInputMap();
		String [] pct = {"100"};
		paramsMap.put("pct", pct);
		String [] ay = {"09"};
		paramsMap.put("ay", ay);	
		String [] index = {"00206"};
		paramsMap.put("index", index);
		String [] pca = {"88887"};
		paramsMap.put("pca", pca);
		
		return paramsMap;
	}
	
	@Test
	public void testSaveNewWithIndexAndPcaTwoRowsCB() {
		action.setParamMap(this.prepareMapWithIndexAndPcaTwoRowsCB());
		try {
			action.save();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		AdvanceMasters advanceMaster = action.getAdvanceMaster();
		assertNotNull(advanceMaster.getAdevIdentifier());
	}
	
	private Map prepareMapWithIndexAndPcaTwoRowsCB (){
		paramsMap = this.prepareInputMap();
		String [] pct = {"48", "52"};
		paramsMap.put("pct", pct);
		String [] ay = {"08", "06"};
		paramsMap.put("ay", ay);	
		String [] index = {"00206"};
		paramsMap.put("index", index);
		String [] pca = {"77777"};
		paramsMap.put("pca", pca);
		
		return paramsMap;
	}
	
	@Test
	public void testSubmitAdvance (){
		Map session = new HashMap<String, Object>();
		UserProfile profile = new UserProfile ("T_DEPT_99", 134067);
		UserSubject subject = new UserSubject (134067, 109744, null, null, "59", "01", "100", "");
		AdvanceMasters advanceMaster = advanceService.getAdvanceByMasterId(2264);
		session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER, advanceMaster);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
		session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		action.setParamMap(new HashMap());
		action.setSession(session);
		String success = "";
		try {
			success = action.submitAdvance();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		assertEquals(success, IConstants.SUCCESS);
	}
	
	
	@Test
	public void testApproveAdvance (){
		Map session = new HashMap<String, Object>();
		UserProfile profile = new UserProfile ("T_DEPT_99", 134067);
		UserSubject subject = new UserSubject (134067, 109744, null, null, "59", "01", "100", "");
		AdvanceMasters advanceMaster = advanceService.getAdvanceByMasterId(2118);
		session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER, advanceMaster);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
		session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		Map<String, String[]> paramsMap = new HashMap ();
		String [] approverComments = {"Approved"};
		paramsMap.put("approverComments", approverComments);
		action.setParamMap(paramsMap);
		action.setSession(session);
		String success = "";
		try {
			success = action.approveAdvance();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		assertEquals(success, IConstants.SUCCESS);
	}
	
	@Test
	public void testRejectAdvance (){
		Map session = new HashMap<String, Object>();
		UserProfile profile = new UserProfile ("T_DEPT_99", 134067);
		UserSubject subject = new UserSubject (134067, 109744, null, null, "59", "01", "100", "");
		AdvanceMasters advanceMaster = advanceService.getAdvanceByMasterId(1876);
		session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER, advanceMaster);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
		session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		Map<String, String[]> paramsMap = new HashMap ();
		String [] approverComments = {"Rejections"};
		paramsMap.put("approverComments", approverComments);
		action.setParamMap(paramsMap);
		action.setSession(session);
		String success = "";
		try {
			success = action.rejectAdvance();
		} catch (TimeAndExpenseException e) {
			e.printStackTrace();
		}
		assertEquals(success, IConstants.SUCCESS);
	}
	
	@Test
	public void testUpdateCB (){
		/*Map session = new HashMap<String, Object>();
		UserProfile profile = new UserProfile ("T_DEPT_99", 134067);
		UserSubject subject = new UserSubject (134067, 109744, null, null, "59", "01", "100", "");
		AdvanceMasters advanceMaster = advanceService.getAdvanceByMasterId(1876);
		session.put(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER, advanceMaster);
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, profile);
		session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		Map<String, String[]> paramsMap = new HashMap ();
		String [] approverComments = {"Rejections"};
		paramsMap.put("approverComments", approverComments);
		action.setParamMap(paramsMap);
		action.setSession(session);
		String success = action.rejectAdvance();
		assertEquals(success, IConstants.SUCCESS);*/
		AdvanceMasters advanceMasters = advanceService.getAdvanceByMasterId(2013);
		List<AdvanceDetailCodingBlocks> cbList = advanceMasters.getAdvanceDetailsCollection().get(0).getAdvanceDetailCodingBlocksCollection();
		AdvanceDetailCodingBlocks cb = cbList.get(0);
		String cbConcat = cb.getCodingBlock();
		System.out.println("length is: " + cbConcat.length());
		System.out.println("length is: " + cbConcat.trim().length());
		System.out.println("length is: " + cbConcat.trim());
		assertNotNull(cb);
	}
	
	/*@Test
	public void testValidateCodingBlocks (){
		AdvanceMasters advanceMaster = advanceService.getAdvanceByMasterId(1905);
		action.setAdvanceMaster(advanceMaster);
		boolean success = action.validateCodingBlocks();
		assertEquals(success, false);
	}
*/
}
