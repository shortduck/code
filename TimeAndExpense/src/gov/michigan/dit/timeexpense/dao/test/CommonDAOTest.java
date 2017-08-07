package gov.michigan.dit.timeexpense.dao.test;

import gov.michigan.dit.timeexpense.dao.AbstractDAO;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseProfiles;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypes;
import gov.michigan.dit.timeexpense.model.core.SystemCodes;
import gov.michigan.dit.timeexpense.model.db.Appointments;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class CommonDAOTest extends AbstractDAOTest{

	private CommonDAO dao = new CommonDAO();
	private AbstractDAO absDao = new AbstractDAO();
	private ExpenseDAO expenseDao = new ExpenseDAO();
		
	public CommonDAOTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void startTransaction(){
		super.beginTransaction();
		dao.setEntityManager(em);
		absDao.setEntityManager(em);
		expenseDao.setEntityManager(em);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindErrorMessage(){
		String errorCode1 = "36001";
		String errorCode2 = "36012";
		Map<String,String> errorMap= dao.findErrorMessage(errorCode1,errorCode2);
		Assert.assertNotNull(errorMap);
	}
	
	@Test
	public void testGetTimeAndExpenseStartDate(){
		Date teStartDate = dao.getTimeAndExpenseStartDate();
		System.out.println(teStartDate);
		Assert.assertNotNull(teStartDate);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetNewsItemList(){
		List<SystemCodes> newsItems  = dao.getNewsItemList("DC01");
		System.out.println(newsItems.size());
		Assert.assertNotNull(newsItems);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAdjustmentIdentifier(){
		long adjustmentIdentifier = dao.findAdjustmentIdentifier();
		
		Assert.assertTrue(adjustmentIdentifier>0);
		System.out.println(adjustmentIdentifier);
	}
	
	@Test
	public void testDeleteExpenseErrors(){
		ExpenseMasters expenseMasters = expenseDao.findExpenseByExpenseMasterId(100);
		
		ExpenseMasters updatedExpense = dao.deleteExpenseErrors(expenseMasters, IConstants.EXP_ERR_SRC_ID_TAB);
		List<ExpenseErrors> errorsList = updatedExpense.getExpenseErrorsCollection();
	}
	
	
	@Test
	public void testFindExpenseProfiles(){
		//Appointments appointments = dao.findAppointment(109770);
		
		ExpenseProfiles expProfileList =  dao.findExpenseProfiles(109770,Calendar.getInstance().getTime());
		Assert.assertNotNull(expProfileList);
	}
	
	@Test
	public void testFindExpenseTypes(){
		
		String expenseTypeCode = "Detr";
		ExpenseTypes expenseTypes = dao.findExpenseTypes(expenseTypeCode);
		Assert.assertNotNull(expenseTypes);
	}
	
	@Test
	public void testFindSystemCodeValue(){

		String systemCode = dao.findSystemCodeValue(IConstants.ADVANCE_PAYROLL_SYSTEM_CODE);
		System.out.println(systemCode);
		Assert.assertNotNull(systemCode);
	}	
	
	@Test
	public void testFindModuleIdByActionCode(){

		List<String> moduleId = dao.findModuleIdByActionCode("APPR");
		Assert.assertEquals("APRF001", moduleId.get(0));
	}	

	@Test
	public void testFindMinCodingBlockErrorCode(){

		String errorCode = (String) dao.findMinCodingBlockErrorCode();
		Assert.assertEquals(errorCode, "10146");
	}	
	
	@Test
	public void testFindMaxCodingBlockErrorCode(){

		String errorCode = (String) dao.findMaxCodingBlockErrorCode();
		Assert.assertEquals(errorCode, "10192");
	}	
	
	@Test
	public void testFindManagerApprovalStep(){

		boolean count = dao.findManagerApprovalStep("77", "01", "050", "C");
		Assert.assertTrue(count);
	}		
}
