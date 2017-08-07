package gov.michigan.dit.timeexpense.action.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.michigan.dit.timeexpense.action.AdvanceAction;
import gov.michigan.dit.timeexpense.action.AdvanceListAction;
import gov.michigan.dit.timeexpense.action.ExpenseListAction;
import gov.michigan.dit.timeexpense.model.core.AdvanceEvents;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.VAdvanceList;
import gov.michigan.dit.timeexpense.model.core.VExpensesList;
import gov.michigan.dit.timeexpense.model.display.AdvApprovalTransaction;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
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

@SuppressWarnings("unchecked")
public class ExpenseListActionTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	//private static AdvanceAction action = new AdvanceAction ();
	private static ExpenseListAction listaction = new ExpenseListAction ();
	private ExpenseDSP expenseService;
	private AppointmentDSP appointmentService;
	
	@BeforeClass
	public static void init() {
		Map session = new HashMap<String, Object>();
		//action.setSession(session);
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
		//action.setEntityManager(em);
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}


	
	@Test
	public void testdeleteExpense() {
			
		listaction.setExpenseEventId(1471);
		listaction.deleteExpense();
		expenseService.deleteExpense(1471);
		
		assertNull(listaction.getExpenseEventId());
			
	
	}
	
	@Test
	public void testgetExpenseList() {
		
		List<VExpensesList> list = expenseService.getExpensesListEmployee(134067, "BOTH");
		assertTrue(list.size() > 0);
	}
	
	
	@Test
	public void testviewExpenseList(){
		
		listaction.viewExpenseList();
		UserProfile profile = new UserProfile("T_HRMND99");
		List<EmployeeHeaderBean> empInfoList = appointmentService.getEmployeeHeaderInfoByEmpId((int)(profile.getEmpIdentifier()));
		Assert.assertTrue(empInfoList.size()>0);
		
	}
	
}
