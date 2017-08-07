package gov.michigan.dit.timeexpense.dao.test;

import static org.junit.Assert.assertTrue;
import gov.michigan.dit.timeexpense.dao.ExpenseActionsDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.model.core.ExpenseActions;
import gov.michigan.dit.timeexpense.action.ExpenseAction;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExpenseActionsDAOTest extends AbstractDAOTest{

	private ExpenseActionsDAO expenseActionDao;
	private ExpenseDAO expenseDao;

	@Before
	public void startTransaction() {
		super.beginTransaction();
		expenseActionDao = new ExpenseActionsDAO(em);
		expenseDao = new ExpenseDAO(em);
	}

	@Test
	public void testFindExpenseActions() {
		ExpenseMasters expense = expenseDao.findExpenseByExpenseMasterId(1100);
		
		List<ExpenseActions> actions = expenseActionDao.findExpenseActions(expense);
		assertTrue(actions.size()>0);
	}
	
	@Test
	public void testFindAllModulesForExpenseAction(){
		List<String> modules = expenseActionDao.findAllModulesForExpenseAction("APPR");
		assertTrue(modules.size()>0);
	}

	@Test
	public void testFindAllModulesForInvalidExpenseAction(){
		List<String> modules = expenseActionDao.findAllModulesForExpenseAction("SUBM");
		Assert.assertEquals(modules.size(), 0);
	}
	
	@Test
	public void testFindLatestExpenseAction(){
		ExpenseMasters expense = expenseDao.findExpenseByExpenseMasterId(1100);
		ExpenseActions latestAction = expenseActionDao.findLatestExpenseActionExcludingAuditCertify(expense);
		Assert.assertNotNull(latestAction);
	}
	
	@Test
	public void testCloneExpense(){
		ExpenseAction expenseAction = new ExpenseAction();
		
		String returnValue = expenseDao.cloneExpense(233288, new Date("01 oct 2012"), new Date("10 oct 2012"));
		
		boolean successful = expenseAction.isNumber(returnValue);
		
		Assert.assertTrue(successful);
	}
	
}
