package gov.michigan.dit.timeexpense.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.michigan.dit.timeexpense.dao.AdvanceDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.model.core.AdvanceActions;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetails;
import gov.michigan.dit.timeexpense.model.core.AdvanceEvents;
import gov.michigan.dit.timeexpense.model.core.AdvanceHistory;
import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.VAdvanceList;
import gov.michigan.dit.timeexpense.model.display.AdvApprovalTransaction;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * @author chiduras
 * 
 */

public class AdvanceDAOTest extends AbstractDAOTest {

    private AdvanceDAO advanceDAO = new AdvanceDAO();

    private ExpenseDAO expenseDAO = new ExpenseDAO();

    public AdvanceDAOTest() {
	
    }

    @Before
    public void startTransaction() {
	super.beginTransaction();
	advanceDAO.setEntityManager(em);
	expenseDAO.setEntityManager(em);
    }
    

  
    @Test
    public void testFindNonAdjustedAdvanceListByEmployeeId() {

	// int employeeId = 135348;
	int employeeId = 132530;
	List<VAdvanceList> advances = advanceDAO
		.findNonAdjustedAdvanceListByEmployeeId(employeeId);
	
	assertTrue(advances.size() > 0);

    }
    
    @Test
    public void testFindNonAdjustedAdvanceListByEmployeeId_Empty() {

	// int employeeId = 135348;
	int employeeId = 989999;
	List<VAdvanceList> advances = advanceDAO
		.findNonAdjustedAdvanceListByEmployeeId(employeeId);
	
	assertTrue(advances.isEmpty());

    }

    @Test
    public void testFindAdjustedAdvanceListByEmployeeId() {

	int employeeId = 135348;
	// int employeeId = 132530;
	List<VAdvanceList> advances = advanceDAO
		.findAdjustedAdvanceListByEmployeeId(employeeId);

	assertTrue(!advances.isEmpty());
//	System.out.println(advances.get(1).getEnableDelete());

    }

    @Test
    public void testFindAdvanceListByEmployeeId() {

	int employeeId = 135348;
	List<VAdvanceList> advances = advanceDAO
		.findAdvanceListByEmployeeId(employeeId);

	assertTrue(!advances.isEmpty());

    }

    @Test
    public void testFindNonAdjustedAdvanceListByApptID() {

	List<VAdvanceList> advances = advanceDAO
		.findNonAdjustedAdvanceListByApptID(1, "chidura", "test");

	assertTrue(advances.isEmpty());
	 System.out.println(advances.size()); 
	
    }
    
    @Test
    public void testFindNonAdjustedAdvanceListByApptID_Valid() {

	List<VAdvanceList> advances = advanceDAO
		.findNonAdjustedAdvanceListByApptID(109744, "T_DEPT99", "ADVW002");

	assertTrue(!advances.isEmpty());
	 System.out.println(advances.size()); 

    }
    
    @Test
    public void testfindAdjustedAdvanceListByApptID_Adjusted_Valid() {

	List<VAdvanceList> advances = advanceDAO.findAdjustedAdvanceListByApptID(109744,
		"T_DEPT99", "ADVW002");

	assertTrue(!advances.isEmpty());
        System.out.println(advances.size()); 
    }

    
    @Test
    public void testfindAdjustedAdvanceListByApptID_Adjusted() {

	List<VAdvanceList> advances = advanceDAO.findAdjustedAdvanceListByApptID(107833,
		"T_DEPT99", "ADVW002");

	assertTrue(!advances.isEmpty());
        System.out.println(advances.size()); 
    }

    
    
    @Test
    public void testFindAdjustedAdvanceListByApptID() {

	List<VAdvanceList> advances = advanceDAO
		.findAdjustedAdvanceListByApptID(1, "chidura", "test");

	assertTrue(advances.isEmpty());

    }

    @Test
    public void testFindAdvanceListByApptID() {

	List<VAdvanceList> advances = advanceDAO.findAdvanceListByApptID(1,
		"chidura", "test");

	assertTrue(advances.isEmpty());

    }

    @Test
    public void testFindAdvanceListByApptID_ALL() {

	List<VAdvanceList> advances = advanceDAO.findAdvanceListByApptID(109744,
		"T_DEPT99", "ADVW002");

	assertTrue(!advances.isEmpty());
        System.out.println(advances.size()); 
    }
    
    @Test
    public void testFindAdvanceByMasterId() {

	AdvanceMasters advances = advanceDAO.findAdvanceByMasterId(100);

	assertNotNull(advances);

    }

    @Test
    public void testFindExpenseLiquidationByMasterId() {

	double amountApplied = advanceDAO.findExpenseLiquidationByMasterId(101);

	assertNotNull(amountApplied);
	
	System.out.println(amountApplied);

    }

    @Test
    public void testFindExpenseLiquidationByMasterId_Invalid() {

	double amountApplied = advanceDAO.findExpenseLiquidationByMasterId(999);

	Assert.assertEquals(0, amountApplied);


    }

    
    @Test
    public void testFindAdvanceOutstandingByEmpID() {

	List<VAdvanceList> advances = advanceDAO
		.findAdvanceOutstandingListByEmpID(135348);

	assertTrue(!advances.isEmpty());

    }

    @Test
    public void testFindAdvancesAwaitingApproval() {
//134874
	List<AdvApprovalTransaction> advances = advanceDAO
		.findAdvancesAwaitingApproval(133039);

	assertNotNull(advances);

	System.out.println(advances.size());
	System.out.println(advances.get(0));
    }

    @Test
    public void testFindAdvancesAwaitingApproval_Invalid() {

	List<AdvApprovalTransaction> advances = advanceDAO
		.findAdvancesAwaitingApproval(999999);

	assertTrue(advances.isEmpty());


	
    }
    
    @Test
    public void testFindAdjustedAdvancesAwaitingApproval() {

	List<AdvApprovalTransaction> advances = advanceDAO
		.findAdjustedAdvancesAwaitingApproval(133039);

	assertNotNull(advances);

	System.out.println(advances.size());
	System.out.println(advances.get(0));
    }

    @Test
    public void testFindNonAdjustedAdvancesAwaitingApproval() {

	List<AdvApprovalTransaction> advances = advanceDAO
		.findNonAdjustedAdvancesAwaitingApproval(134874);

	assertNotNull(advances);

	
    }

    @Test
    public void testSaveAdvanceEvent_WithoutMasterCollectoin() {

	AdvanceEvents advanceEvents = new AdvanceEvents();
	advanceEvents.setModifiedUserId("Satish");
	advanceEvents.setApptIdentifier(107192);

	assertTrue(advanceDAO.saveAdvanceEvent(advanceEvents));

    }

    @Test
    public void testSaveAdvanceEvent_WithMasterCollectoin() {
	AdvanceEvents advanceEvents = new AdvanceEvents();
	// advanceEvents.setAdevIdentifier(101);
	advanceEvents.setModifiedUserId("Satish");
	advanceEvents.setApptIdentifier(107193);

	AdvanceMasters advanceMaster = new AdvanceMasters();
	advanceMaster.setCurrentInd("Y");
	advanceMaster.setRequestDate(new Date("11/01/2008"));
	advanceMaster.setAdevIdentifier(advanceEvents);
	advanceMaster.setRevisionNumber((short) 1);
	advanceMaster.setManualWarrantIssdInd("N");
	advanceMaster.setPermanentAdvInd("N");
	advanceMaster.setManualDepositInd("N");
	advanceMaster.setFromDate(new Date(2009, 1, 1));
	advanceMaster.setToDate(new Date(2010, 1, 1));

	List<AdvanceMasters> advanceMasterList = new ArrayList<AdvanceMasters>();
	advanceMasterList.add(advanceMaster);

	advanceEvents.setAdvanceMastersCollection(advanceMasterList);
    
	assertTrue(advanceDAO.saveAdvanceEvent(advanceEvents));

    }


    @Test
    public void testSaveAdvanceEvent_WithMasterDetail() {
	
	AdvanceEvents advEvent = em.find(AdvanceEvents.class, 1);
	List<AdvanceMasters> advmasters =  advEvent.getAdvanceMastersCollection();
	AdvanceMasters advmaster = advmasters.get(0);
	
	int prevDetailCount =  advmaster.getAdvanceDetailsCollection().size();

	
	AdvanceDetails advDetail = new AdvanceDetails();
	advDetail.setDollarAmount(100.00);
	advDetail.setModifiedUserId("Satish");
	
	// set bi-directional relationship
	advDetail.setPytpIdentifier(3029);
	//ptype.getAdvanceDetailsCollection().add(advDetail);

	// set bi-directional relationship
	advDetail.setAdvmIdentifier(advmaster);
	advmaster.getAdvanceDetailsCollection().add(advDetail);

	
	
	advEvent = em.merge(advEvent);
	
	int currDetailsCount = advEvent.getAdvanceMastersCollection().get(0).getAdvanceDetailsCollection().size();
	
	assertTrue(currDetailsCount > prevDetailCount);
	
    }

    
    
    @Test
    public void testSaveAdvanceMaster_WithoutDetailCollection() {

	AdvanceEvents advanceEvents = new AdvanceEvents();
	// advanceEvents.setAdevIdentifier(101);
	advanceEvents.setModifiedUserId("Satish");
	advanceEvents.setApptIdentifier(107330);

	assertTrue(advanceDAO.saveAdvanceEvent(advanceEvents));

    }
    //TODO sc- testcase for saveadvancemaster
   

  /* @Test
    public void testSaveAdvanceMaster() {

	Appointments appts = new Appointments();
	appts.setApptIdentifier(107192);
	
	AdvanceEvents advanceEvents = new AdvanceEvents();
	advanceEvents.setApptIdentifier(107192);

	AdvanceMasters advanceMaster = new AdvanceMasters();
	List<AdvanceMasters> advMasterList = new ArrayList<AdvanceMasters>();

	advanceMaster.setAdjIdentifier(null);
	advanceMaster.setAdevIdentifier(advanceEvents);
	
	
		advanceDAO.saveAdvanceMaster(advanceMaster);

    }
*/    
    @Test
    public void testDeleteAdvanceMaster() {

	insertRecord();

	boolean deleted = advanceDAO.deleteAdvanceMaster(1999);

	Assert.assertEquals(true, deleted);

    }

    public void insertRecord() {

	String Query = "INSERT INTO advance_masters "
		+ " (advm_identifier,  adev_identifier,  revision_number, request_date, "
		+ " current_ind, status,advance_reason, permanent_adv_ind, manual_deposit_doc_num, "
		+ " manual_deposit_amount, manual_warrant_doc_num, manual_warrant_issd_ind, manual_deposit_ind,"
		+ " from_date, TO_DATE, modified_date ) "
		+ " VALUES (1999, 1002,0, TO_DATE ('12/12/2008 00:00:00', 'MM/DD/YYYY HH24:MI:SS'),"
		+ " 'N','PROC','Satish Testing','Y','SC-123',100,'SC-123','N','N', "
		+ " TO_DATE ('12/10/2008 00:00:00', 'MM/DD/YYYY HH24:MI:SS'),"
		+ " TO_DATE ('12/31/2008 00:00:00', 'MM/DD/YYYY HH24:MI:SS'),"
		+ " TO_DATE ('02/25/2009 15:19:06', 'MM/DD/YYYY HH24:MI:SS') )";

	em.createNativeQuery(Query).executeUpdate();

	em.flush();

    }

    @Test
    public void testRejectAdvance() {
	int advanceMasterId = 104;
	// short revisionNo = 1;

	AdvanceMasters advanceMaster = advanceDAO
		.findAdvanceByMasterId(advanceMasterId);
	advanceMaster.setStatus("SUBM");
	advanceMaster = advanceDAO.rejectAdvance(advanceMaster);

	Assert.assertNotNull(advanceMaster);
	System.out.println("Status ::" + advanceMaster.getStatus());
	Assert.assertEquals("RJCT", advanceMaster.getStatus());

    }

    // to be tested
    @Test
    public void testSaveLiquidations() {
/*	int advanceMasterId = 100;
	// short revisionNo = 1;
	int expenseMasterId = 9;
	int revisionNo = 4;

	ExpenseMasters expenseMasters = expenseDAO
		.findExpenseByExpenseMasterId(expenseMasterId, revisionNo);
	AdvanceMasters advanceMaster = advanceDAO
		.findAdvanceByMasterId(advanceMasterId);

	AdvanceLiquidations advanceLiquidations = new AdvanceLiquidations();
	advanceLiquidations.setAdvanceMaster(advanceMaster);
	advanceLiquidations.setExpenseMaster(expenseMasters);
	advanceLiquidations.setAmount(100.00);
	advanceLiquidations.setModifiedUserId("Satish");

	List<AdvanceLiquidations> AdvanceLiquidationsList = new ArrayList<AdvanceLiquidations>();
	AdvanceLiquidationsList.add(advanceLiquidations);

	assertTrue(advanceDAO.saveLiquidations(advanceLiquidations));
*/
    }

    @Test
    public void testfindAdvanceByAdvanceEventID() {
	int advanceEventId = 1;
	AdvanceEvents advEvents = advanceDAO
		.findAdvanceByAdvanceEventId(advanceEventId);
	assertNotNull(advEvents);
    }

    @Test
    public void testFindMaxRevisionNo_ValidEventId() {

	int advanceEventId = 1;
	int maxRevisionNo = advanceDAO.findMaxRevisionNo(advanceEventId);

	if(maxRevisionNo <0 ){
	    assertTrue(false);
	}
	//assertNotNull(maxRevisionNo);

    }

    @Test
    public void testFindMaxRevisionNo_InvalidEventId() {

	int advanceEventId = -1;
	int maxRevisionNo = advanceDAO.findMaxRevisionNo(advanceEventId);

	if(maxRevisionNo >= 0){
	    assertTrue(false);
	}
	

    }
    
   
    /*
     * @Test public void testFindAdvanceByAdvanceMasterId() { int
     * advanceMasterId = 1002; int revisionNo =1;
     * 
     * AdvanceMasters advMasters = advanceDAO.findAdvanceByAdvanceMasterId(
     * advanceMasterId, revisionNo);
     * 
     * assertNotNull(advMasters); }
     * 
     * @Test public void
     * testFindAdvanceByAdvanceMasterId_WithRevisionNoLessThanZero() { int
     * advanceMasterId = 1002; int revisionNo = -1;
     * 
     * AdvanceMasters advMasters = advanceDAO.findAdvanceByAdvanceMasterId(
     * advanceMasterId, revisionNo);
     * 
     * assertNull(advMasters); }
     * 
     * @Test public void
     * testFindAdvanceByAdvanceMasterId_WithAdvanceMasterLessThanZero() { int
     * advanceMasterId =-1002; int revisionNo = -1;
     * 
     * AdvanceMasters advMasters = advanceDAO.findAdvanceByAdvanceMasterId(
     * advanceMasterId, revisionNo);
     * 
     * assertNull(advMasters); }
     */

    
    @Test
    public void testFindAdvanceByAdvanceEventId_ValidEventId() {

	AdvanceEvents advanceEvents = advanceDAO.findAdvanceByAdvanceEventId(1);
	
	AdvanceMasters advMaster = advanceDAO.findAdvanceByAdvanceEventId(advanceEvents, 1);

	assertNotNull(advMaster);

    }
    
    @Test
    public void testFindAdvanceByAdvanceEventId_InvalidEventId() {

	AdvanceEvents advanceEvents = advanceDAO.findAdvanceByAdvanceEventId(1);
	
	AdvanceMasters advMaster = advanceDAO.findAdvanceByAdvanceEventId(advanceEvents, 8);

	assertNull(advMaster);

    }
    
    @Test
    public void testDeleteAdvanceErrors() {

	AdvanceMasters advanceMasters = advanceDAO.findAdvanceByMasterId(10102);
	advanceDAO.deleteAdvanceErrors(advanceMasters);
	
	em.flush();
	assertTrue(advanceMasters.getAdvanceErrorsCollection().isEmpty());

    }
    
    @Test
    public void testDeleteAdvanceEvent() {

	AdvanceEvents advanceEventBefore = advanceDAO.getEntityManager().find(AdvanceEvents.class, 1106);
	advanceDAO.deleteAdvanceEvent(1106);
	AdvanceEvents advanceEventAfter = advanceDAO.getEntityManager().find(AdvanceEvents.class, 1106);
	assertEquals(advanceEventAfter, null);

    }
  
    @Test
    public void testDeleteAdvanceEvent_withEventId6() {

	AdvanceEvents advanceEventBefore = advanceDAO.getEntityManager().find(AdvanceEvents.class, 6);
	advanceDAO.deleteAdvanceEvent(6);
	em.flush();
	AdvanceEvents advanceEventAfter = advanceDAO.getEntityManager().find(AdvanceEvents.class, 6);
	assertEquals(advanceEventAfter, null);
	

    }
    
    
    @Test
    public void testDeleteAdvanceEvent_withEventId13() {

	AdvanceEvents advanceEventBefore = advanceDAO.getEntityManager().find(AdvanceEvents.class, 1120);
	advanceDAO.deleteAdvanceEvent(1120);
	
	AdvanceEvents advanceEventAfter = advanceDAO.getEntityManager().find(AdvanceEvents.class, 1120);
	assertEquals(advanceEventAfter, null);
	

    }
    
    @Test
    public void testDeleteAdvanceEvent_withEventId4() {

	AdvanceEvents advanceEventBefore = advanceDAO.getEntityManager().find(AdvanceEvents.class, 4);
	advanceDAO.deleteAdvanceEvent(4);
	em.flush();
	AdvanceEvents advanceEventAfter = advanceDAO.getEntityManager().find(AdvanceEvents.class, 4);
	assertEquals(advanceEventAfter, null);
	

    }
    
    @Test
    public void testFindPayTypeAdvance_Valid() {
    	Date requestDate = TimeAndExpenseUtil.getDateFromString("01/01/2009");
    	Integer payType = advanceDAO.findPayType("WTAC", requestDate);
		assertNotNull(payType);
    }
    
    @Test
    public void testFindPayTypeAdvance_InValid() {
    	Date requestDate = TimeAndExpenseUtil.getDateFromString("01/01/2007");
    	Integer payType = advanceDAO.findPayType("WTAC", requestDate);
		assertNull(payType);
    }
    
    @Test
    public void testFindPayTypeAdvanceManualDeposit() {    
    	Date requestDate = TimeAndExpenseUtil.getDateFromString("01/01/2009");
		Integer payType = advanceDAO.findPayType("WTAN", requestDate);
		System.out.println("Pay Type is: " + payType);
		assertNotNull(payType);

    }
    
    @Test
    public void testFindNextActionCode() {
    AdvanceMasters advanceMaster = advanceDAO.findAdvanceByMasterId(1509);
    List<AdvanceActions> actionsList = advanceDAO.findNextAction(advanceMaster);
	assertFalse(!actionsList.isEmpty());

    }
    
    @Test
    public void testFindAdvanceLiquidationsByMasterId() {
     List<AdvanceLiquidations> liquidationsList = advanceDAO.findAdvanceLiquidationsByMasterId(1207);
	assertTrue(!liquidationsList.isEmpty());

    }
    
    @Test
    public void testFindAdvanceOutstandingByMasterId() {
     double amount = advanceDAO.findAdvanceOutstandingByMasterId(1036);
	assertEquals(amount, 20);

    }
    
    @Test
    public void testDeleteAdvanceCodingBlocks() {

	AdvanceMasters advanceMasters = advanceDAO.findAdvanceByMasterId(1997);
	advanceDAO.deleteAdvanceDetailCodingBlocks(advanceMasters);
	
	assertTrue(advanceMasters.getAdvanceDetailsCollection().get(0).getAdvanceDetailCodingBlocksCollection().isEmpty());

    }
    
    @Test
    public void testFindLiquidationByExpenseAndAdvanceMastId(){
    	ExpenseMasters expenseMaster = expenseDAO.findExpenseByExpenseMasterId(1044);
    	AdvanceMasters advanceMaster = advanceDAO.findAdvanceByMasterId(1855);
    	AdvanceLiquidations advLiq =  advanceDAO.findLiquidationByExpenseAndAdvanceMastId(expenseMaster, advanceMaster);
    	assertNotNull(advLiq);
    }
    
    @Test
    public void testGetLatestAction (){

    	AdvanceMasters advanceMaster = advanceDAO.findAdvanceByMasterId(2172);
    	List<AdvanceActions> advActionsList =  advanceDAO.findLatestAction(advanceMaster);
    	String lastAction = advActionsList.get(0).getActionCode();
    	assertEquals(lastAction, "APRW");
    }
    
    @Test
    public void testFindAllAdvanceMastersByAdvanceEventId (){
    	AdvanceEvents advanceEvent = advanceDAO.findAdvanceByAdvanceEventId(2108);
    	List<AdvanceMasters> advanceMasterList = advanceDAO.findAllNonCurrentAdvanceMastersByAdvanceEvent(advanceEvent);
    	assertTrue(advanceMasterList.size() > 1);
    }
    
    @Test
    public void testFindAllCurrentAdvanceMastersByEmpId (){
    	List<AdvanceMasters> advanceMasterList = advanceDAO.findAllCurrentAdvanceMastersByEmpId(134067);
    	assertTrue(advanceMasterList.size() > 1);
    }
    
    @Test
    public void testFindAdvanceLiquidations (){
    	AdvanceMasters advanceMaster = advanceDAO.findAdvanceByMasterId(2351);
    	List<AdvanceLiquidations> liqList = advanceDAO.findAdvanceLiquidations(advanceMaster, 2470);
    	assertEquals(liqList.size(), 1);
    }
    
    @Test
    public void testFindPreviousApprovedOrHigherRevision (){
    	AdvanceMasters advanceMaster = advanceDAO.findAdvanceByMasterId(2506);
    	List<AdvanceMasters> prevRevision = advanceDAO. findPreviousApprovedOrHigherRevision(advanceMaster.getAdevIdentifier());
    	assertEquals( prevRevision.get(0).getRevisionNumber(), 0);
    }
    
    @Test
    public void testFindPreviousApprovedOrHigherRevisionForLiqCarryForward (){
    	AdvanceMasters advanceMaster = advanceDAO.findAdvanceByMasterId(1263);
    	List<AdvanceMasters> prevRevision = advanceDAO. findPreviousApprovedOrHigherRevisionForLiqCarryForward(advanceMaster.getAdevIdentifier());
    	assertEquals( prevRevision.get(0).getRevisionNumber(), 0);
    }
    
    @Test
    public void testFindExpenseCount (){
    	AdvanceMasters advanceMaster = advanceDAO.findAdvanceByMasterId(2506);
    	ExpenseMasters expenseMaster = advanceDAO.getEntityManager().find(ExpenseMasters.class, 2487);
    	long count = advanceDAO.findExpenseLiquidationsCount(advanceMaster, expenseMaster);
    	assertEquals( count, 0);
    }
    
    @Test
    public void testFindAdvanceOutstandingByEmpId (){
    	double amount = advanceDAO.findAmountOutstandingByEmpId(134720);
    	assertTrue(amount > 0);
    }
    
    @Test
    public void testFindAdvanceEvent (){
    	AdvanceEvents event = advanceDAO.getEntityManager().find(AdvanceEvents.class, 2448);
    	assertEquals(event.getAdvanceMastersCollection().size(), 3);
    }
    
    @Test
    public void testFindAdvanceHistory (){
    	AdvanceEvents event = advanceDAO.getEntityManager().find(AdvanceEvents.class, 2438);
    	List<AdvanceHistory> hisList = advanceDAO.findAdvanceHistory(event.getAdevIdentifier());
    	assertEquals(event.getAdvanceMastersCollection().size(), 3);
    }
    
    @Test
    public void testFindAdvanceLiquidationsList (){
    	AdvanceMasters advanceMaster = advanceDAO.findAdvanceByMasterId(1185);
    	List<AdvanceLiquidations> liqList = advanceDAO.findAdvanceLiquidationsList(advanceMaster);
    	assertEquals( liqList.size(), 1);
    }
    
    @Test
    public void testFindAdvanceExpenseTypeAdvance_Valid() {
    	Date requestDate = TimeAndExpenseUtil.getDateFromString("01/01/2009");
    	Integer expenseType = advanceDAO.findAdvanceExpenseType(3051, requestDate);
		assertNotNull(expenseType);
    }
    
    @Test
    public void testFindAdvanceExpenseType_InValid() {
    	Date requestDate = TimeAndExpenseUtil.getDateFromString("01/01/2005");
    	Integer expenseType = advanceDAO.findAdvanceExpenseType(3051, requestDate);
		assertNull(expenseType);
    }
    
    @Test
    public void testFindAdvanceLiquidationsCountXTCTorHSNT (){
    	AdvanceMasters advanceMaster = advanceDAO.findAdvanceByMasterId(1185);
    	long count = advanceDAO.findAdvanceLiquidationsCountXTCTorHSNT(advanceMaster);
    	assertEquals(count, 1);
    }
    
    @Test
    public void testFindAmountOutstandingByEventId (){
    	double amount = advanceDAO.findAmountOutstandingByEventId(16696);
    	assertTrue(amount > 0);
    }
    
    @Test
    public void testFindNonPermAdvance (){
    	Calendar cal = Calendar.getInstance();
    	cal.set(2012, 0, 27);
    	double amount = advanceDAO.findNonPermAdvance(cal.getTime(), cal.getTime(), 20504);
    	assertTrue(amount > 0);
    }
   
}
