package gov.michigan.dit.timeexpense.dao.test;

import static org.junit.Assert.assertNotNull;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.model.core.ExpenseActions;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseHistory;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.OutOfStateTravel;
import gov.michigan.dit.timeexpense.model.core.StateAuthCodes;
import gov.michigan.dit.timeexpense.model.core.VExpensesList;
import gov.michigan.dit.timeexpense.model.db.Appointments;
import gov.michigan.dit.timeexpense.model.display.ExpenseListBean;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExpenseDAOTest extends AbstractDAOTest {

	private ExpenseDAO expenseDAO = new ExpenseDAO();
	private CommonDAO commonDao = new CommonDAO();

	public ExpenseDAOTest() {
		// TODO Auto-generated constructor stub
	}

	@Before
	public void startTransaction() {
		super.beginTransaction();
		expenseDAO.setEntityManager(em);

	}

	@Test
	public void testfindExpenseByExpenseEventID() {
		ExpenseEvents expEvents = expenseDAO.findExpenseByExpenseEventId(1);
		assertNotNull(expEvents);
	}
	
	@Test
	public void testfindExpenseHistory() {
		List<ExpenseHistory> expHistoryList= expenseDAO.findExpenseHistory(10);
		assertNotNull(expHistoryList);
		Assert.assertTrue(!expHistoryList.isEmpty());
		
	}
	
	@Test
	public void testfindExpenseByExpenseEventIDRevisionNo() {
		ExpenseMasters expMasters = expenseDAO.findExpenseByExpenseEventId(1,5);
		assertNotNull(expMasters);
	}

	@Test
	public void testFindExpenseByExpenseMasterId() {
		int expenseMasterId = 9;
		ExpenseMasters expMasters = expenseDAO.findExpenseByExpenseMasterId(expenseMasterId);
		assertNotNull(expMasters);
	}

	@Test
	public void testFindAuthorizationCodes() {
		List<StateAuthCodes> authCodeList = (List<StateAuthCodes>) expenseDAO.findAuthorizationCodes();
		assertNotNull(authCodeList);
	}

	@Test
	public void testFindMaxRevisionNo() throws Exception{
		int maxRevisionNo = expenseDAO.findMaxRevisionNo(1);
		assertNotNull(maxRevisionNo);
	}

	@Test
	public void testFindExpensesByAppointmentId() {
		List<ExpenseListBean> expListBean = expenseDAO.findExpensesByAppointmentId(107944, "T_DEPT99", "EXPW002", false);
		assertNotNull(expListBean);
		Assert.assertTrue(expListBean.size()>0);
	}

	Integer expevId = null;
	@Test
	public void testDeleteExpenses() {

		insertRecord();
		
		ExpenseEvents expenseEvents = expenseDAO.findExpenseByExpenseEventId(expevId.intValue());
		boolean deleted = expenseDAO.deleteExpenseEvent(expenseEvents.getExpevIdentifier());
		Assert.assertEquals(true, deleted);

	}

	@Test
	public void insertRecord() {
		
		ExpenseEvents expenseEvents = new ExpenseEvents();
		expenseEvents.setAppointmentId(107192);
		em.persist(expenseEvents);
		expevId = expenseEvents.getExpevIdentifier();
	}

	@Test
	public void testFindAdjustedExpensesByAppointmentId() {

		int appointmentId = 107944;
		String userId = "T_DEPT99";
		String moduleId = "EXPW002";
		List<ExpenseListBean> expenseListBean = expenseDAO
				.findAdjustedExpensesByAppointmentId(appointmentId, userId,
						moduleId, false);
		assertNotNull(expenseListBean);
		//Assert.assertTrue(expenseListBean.size()>0);
	}

	@Test
	public void testFindNonAdjustedExpensesByAppointmentId() {

		int appointmentId = 107192;
		String userId = "T_HRMND99";
		String moduleId = "PRMW027";
		List<ExpenseListBean> expenseListBean = expenseDAO.findNonAdjustedExpensesByAppointmentId(appointmentId, userId, moduleId, false);
		assertNotNull(expenseListBean);
		Assert.assertTrue(expenseListBean.size()>0);
	}

	@Test
	public void testFindExpensesByEmployeeId() {

		int employeeId = 133509;
		List<VExpensesList> expenseListBean = expenseDAO
				.findExpensesByEmployeeId(employeeId);

		assertNotNull(expenseListBean);
		System.out.println(expenseListBean.size());
	}

	@Test
	public void testFindAdjustedExpensesByEmployeeId() {

		int employeeId = 133509;
		List<VExpensesList> expenseListBean = expenseDAO.findAdjustedExpensesByEmployeeId(employeeId);
		System.out.println(expenseListBean.size());
		assertNotNull(expenseListBean);
	}

	@Test
	public void testFindNonAdjustedExpensesByEmployeeId() {

		int employeeId = 133509;
		List<VExpensesList> expenseListBean = expenseDAO.findNonAdjustedExpensesByEmployeeId(employeeId);
		assertNotNull(expenseListBean);
		System.out.println(expenseListBean.size());
	}
	
	@Test
	public void testApproveExpense(){
		ExpenseMasters expenseMaster = expenseDAO.findExpenseByExpenseMasterId(999);
		List<ExpenseActions> expActionsList = expenseDAO.approveExpense(expenseMaster);
		Assert.assertNotNull(expActionsList);
	}

	/*****************************************************************************************************************/
	
	@Test
	public void testSaveExpense() {

		Appointments appts = new Appointments();
		appts.setApptIdentifier(107192);
		
		ExpenseEvents expenseEvents = new ExpenseEvents();
		expenseEvents.setAppointmentId(107192);

		ExpenseMasters expenseMaster = new ExpenseMasters();
		List<ExpenseMasters> expMasterList = new ArrayList<ExpenseMasters>();

		expenseMaster.setExpDateFrom(new Date("11/01/2008"));
		expenseMaster.setExpDateTo(new Date("11/11/2008"));
		expenseMaster.setCurrentInd("Y");
		expenseMaster.setAdjIdentifier(null);
		expenseMaster.setTravelInd("Y");
		expenseMaster.setOutOfStateInd("N");
		expenseMaster.setExpevIdentifier(expenseEvents);
		expenseMaster.setSuperReviewedReceiptsInd("N");
				
		OutOfStateTravel ofsTravel = expenseDAO.getEntityManager().find(OutOfStateTravel.class, 1);
		
		List<OutOfStateTravel> outofStateList = new ArrayList<OutOfStateTravel>();
		outofStateList.add(ofsTravel);
		
		expenseMaster.setOutOfStateTravelList(outofStateList);
		expenseMaster.setExpenseDetailsCollection(new ArrayList<ExpenseDetails>());
		expenseMaster.setExpenseErrorsCollection(new ArrayList<ExpenseErrors>());

		
		expMasterList.add(expenseMaster);
		expenseEvents.setExpenseMastersCollection(expMasterList);

		ExpenseEvents savedExpenseEvents = expenseDAO.saveExpense(expenseEvents);

		Assert.assertNotNull(savedExpenseEvents);
	}
	
	@Test
	public void testfindPrevExpenseMasterInProcStatus(){
		ExpenseEvents expenseEvents = expenseDAO.findExpenseByExpenseEventId(1382);
		ExpenseMasters expenseMaster = expenseDAO.findPrevExpenseInStatus(expenseEvents, IConstants.SUBMIT, 4);
		Assert.assertNotNull(expenseMaster);
	}

	@Test
	public void testFindExpenseByLiquidationId(){
		ExpenseMasters expense = expenseDAO.findExpenseByLiquidationId(1422);
		assertNotNull(expense);
	}
	
	public ExpenseMasters prepareExpenseMasters(){

		Appointments appointments = commonDao.findAppointment(107192);
		
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
	public void testFindPrevExpenseApprovalAction(){
		ExpenseMasters expenseMaster = expenseDAO.findExpenseByExpenseMasterId(3869);
		List<ExpenseActions> expenseActionsList = expenseDAO.findPrevExpenseApprovalAction(expenseMaster.getExpevIdentifier(), expenseMaster.getRevisionNumber());
		Assert.assertFalse(expenseActionsList.isEmpty());
	}
	
	@Test
	public void testFindAutoApprovalStatus(){
		int autoApprove = expenseDAO.findAutoApprovalStatus("27", "01", "509", 331262, 233148);
		Assert.assertTrue(autoApprove == 0);
	}
	
	@Test
	public void testFindApprovalAdjustmentCategory(){
		String approvalCategory = expenseDAO.findApprovalAdjustmentCategory(331432);
		Assert.assertTrue(approvalCategory.equals("AEXB"));
	}
}
