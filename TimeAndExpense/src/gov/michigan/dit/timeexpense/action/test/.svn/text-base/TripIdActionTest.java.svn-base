package gov.michigan.dit.timeexpense.action.test;

import static org.junit.Assert.fail;
import gov.michigan.dit.timeexpense.action.ActionHelper;
import gov.michigan.dit.timeexpense.action.TripIdAction;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.StateAuthCodes;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.db.Appointments;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.util.ExpenseViewUtil;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TripIdActionTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	private TripIdAction action = new TripIdAction();
	private ExpenseDSP expenseDsp;
	private ExpenseDAO expenseDao;
	private CommonDAO commDao;
	
	private final static int OUT_OF_STATE_EXPENSE_MASTER_ID = 999999999;
	private final static int NON_TRAVEL_EXPENSE_MASTER_ID = 999999998;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		sessionMap.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, new UserProfile("T_HRMND99"));
		action.setSession(sessionMap);

		action.setEntityManager(em);
			
		em.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testCreateNewExpense() throws Exception{
		String result = action.execute();
		Assert.assertEquals(IConstants.SUCCESS, result);
		Assert.assertTrue(action.getAuthCodes().size() >0 );
	}
	
	@Test
	public void testFindExistingExpenseWithOutOfStateTravel() throws Exception{
		action.setExpenseMasterId(OUT_OF_STATE_EXPENSE_MASTER_ID);
		String result = action.execute();
		Assert.assertEquals(IConstants.SUCCESS, result);
		Assert.assertTrue(action.getAuthCodes().size() >0 );
		
		ExpenseMasters expense =  action.getExpenseMaster();
		
		Assert.assertEquals("Y", expense.getOutOfStateInd());
		Assert.assertEquals(2, expense.getOutOfStateTravelList().size());
	}

	@Test
	public void testFindExistingExpenseWithoutTravel() throws Exception{
		action.setExpenseMasterId(NON_TRAVEL_EXPENSE_MASTER_ID);
		String result = action.execute();
		Assert.assertEquals(IConstants.SUCCESS, result);
		Assert.assertTrue(action.getAuthCodes().size() >0 );
		
		ExpenseMasters expense =  action.getExpenseMaster();
		
		Assert.assertEquals("N", expense.getOutOfStateInd());
		Assert.assertEquals("N", expense.getTravelInd());
		Assert.assertEquals(0, expense.getOutOfStateTravelList().size());
	}
	
	@Test
	public void testExecuteViewExpenseID() {
		fail("Not yet implemented");
	}

	@Test
	public void testValidateIDInput() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetupDisplay() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveExpenseMaster() {
		int expenseMasterId = 9;
		int revisionNo = 4;
		String moduleId = "107192";
		int apptId =107192;
		ExpenseMasters savedEM = null;
		Appointments appts = new Appointments();
		appts.setApptIdentifier(100);
		UserProfile userProfile = new UserProfile("Sourav");
		ExpenseMasters expenseMasters = expenseDao.findExpenseByExpenseMasterId(expenseMasterId);
		expenseMasters.setExpDateFrom(new Date(109, Calendar.FEBRUARY, 01));
		expenseMasters.setExpDateTo(new Date(110, Calendar.FEBRUARY, 01));
		UserSubject subject = new UserSubject();
		subject.setEmployeeId(1077335);
		subject.setAppointmentId(1139330);
		
		try {
			savedEM = expenseDsp.saveExpense(expenseMasters, subject, moduleId,userProfile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertNotNull(savedEM);
	}
	
	@Test
	public void saveExpenseEvents(){
		//ExpenseEvents expenseEvents = new ExpenseEvents();
		ExpenseMasters expenseMasters = new ExpenseMasters();
		String userId = "Sourav";
		UserProfile userProfile = new UserProfile("Sourav");
		String moduleId = "107192";
		int apptId =107192;
		//List<ExpenseMasters> expenseMastList = new ArrayList<ExpenseMasters>();
		Appointments appts = new Appointments();
		appts.setApptIdentifier(107192);
		
		
		expenseMasters.setCurrentInd("Y");
		expenseMasters.setSuperReviewedReceiptsInd("N");
		expenseMasters.setExpDateFrom(new Date("01/01/2009"));
		expenseMasters.setExpDateTo(new Date("01/01/2009"));
		expenseMasters.setOutOfStateInd("N");
		expenseMasters.setTravelInd("Y");
		expenseMasters.setRevisionNumber(1);
		
	//	expenseMastList.add(expenseMasters);
		
		//expenseEvents.setApptIdentifier(appts);
		//expenseEvents.setModifiedUserId("Sourav");
		//expenseEvents.setExpenseMastersCollection(expenseMastList);
		UserSubject subject = new UserSubject();
		subject.setEmployeeId(1077335);
		subject.setAppointmentId(1139330);
		try {
			expenseDsp.saveExpense(expenseMasters,subject,moduleId,userProfile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetNextRevisionNo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPreviousRevision() {
		fail("Not yet implemented");
	}

	@Test
	public void testModifyExpense() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetAutorizationCodes(){
		List<StateAuthCodes> authCodesMap  = action.getAuthCodes();
		Assert.assertNotNull(authCodesMap);
	}
	
	@Test
	public void testformatCbErrorSource(){
		expenseDao = new ExpenseDAO(em);
		ExpenseViewUtil viewUtil = new ExpenseViewUtil ();
		ExpenseMasters expenseMasters = expenseDao.findExpenseByExpenseMasterId(1204);
		System.out.println(ActionHelper.formatCbErrorSource(expenseMasters, "CB 1 - "));
		
	}
	
/*	@Test
	public void testTripIdAction(){
		action = new TripIdAction();
		Assert.assertNotNull(action);
	}*/
}
