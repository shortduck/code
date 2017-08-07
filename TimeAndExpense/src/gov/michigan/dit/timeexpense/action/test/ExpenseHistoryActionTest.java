package gov.michigan.dit.timeexpense.action.test;

import gov.michigan.dit.timeexpense.action.ExpenseHistoryAction;
import gov.michigan.dit.timeexpense.dao.CodingBlockDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExpenseHistoryActionTest extends AbstractActionTest {
    private static EntityManagerFactory emf;
private EntityManager em;
	    private ExpenseDSP expenseService = null;
	    private ExpenseDAO expenseDao = null;
	    private ExpenseHistoryAction expenseHistoryAction = null;
	    
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
		em.getTransaction().begin();
		
		expenseHistoryAction = new ExpenseHistoryAction();
		expenseHistoryAction.setEntityManager(em);

		expenseDao = new ExpenseDAO();
		expenseService = new ExpenseDSP(em);

		
		expenseDao.setEntityManager(em);
		expenseService.setExpenseDAO(expenseDao);
		
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
	}
	
	@Test
	public void execute() throws Exception{
	    String result = expenseHistoryAction.execute();
	    Assert.assertEquals(IConstants.SUCCESS, result);
	}
}
