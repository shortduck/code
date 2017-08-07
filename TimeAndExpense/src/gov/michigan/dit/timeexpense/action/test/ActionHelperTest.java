package gov.michigan.dit.timeexpense.action.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.michigan.dit.timeexpense.action.ActionHelper;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetailCodingBlocks;
import gov.michigan.dit.timeexpense.service.CommonDSP;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActionHelperTest {
	
	private CommonDSP commonDSP;
	private static EntityManagerFactory emf;
	private EntityManager em;
	private ActionHelper helper;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if(emf != null && emf.isOpen())
			emf.close();
	}
	
	@Before
	public void setUp() throws Exception {
		em = emf.createEntityManager();
		commonDSP = new CommonDSP (em);
		helper = new ActionHelper ();
		em.getTransaction().begin();		
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}


	
	@Test
	public void testValidatePercentageSumTotalAdvances_good(){
		List<AdvanceDetailCodingBlocks> cbList = new ArrayList<AdvanceDetailCodingBlocks> ();
		AdvanceDetailCodingBlocks cb1 = new AdvanceDetailCodingBlocks();
		AdvanceDetailCodingBlocks cb2 = new AdvanceDetailCodingBlocks();
		cb1.setPercent(50.00);
		cb2.setPercent(50.00);
		cbList.add(cb1);
		cbList.add(cb2);
		assertTrue(helper.validatePercentageSumTotal(cbList));
		
	}
	
	@Test
	public void testValidatePercentageSumTotalAdvances_bad(){
		List<AdvanceDetailCodingBlocks> cbList = new ArrayList<AdvanceDetailCodingBlocks> ();
		AdvanceDetailCodingBlocks cb1 = new AdvanceDetailCodingBlocks();
		AdvanceDetailCodingBlocks cb2 = new AdvanceDetailCodingBlocks();
		cb1.setPercent(50.00);
		cb2.setPercent(60.00);
		cbList.add(cb1);
		cbList.add(cb2);
		Object test = cb1;
		assertFalse(helper.validatePercentageSumTotal(cbList));
		
	}
	
	@Test
	public void testValidatePercentageSumTotalExpense_good(){
		List<ExpenseDetailCodingBlocks> cbList = new ArrayList<ExpenseDetailCodingBlocks> ();
		ExpenseDetailCodingBlocks cb1 = new ExpenseDetailCodingBlocks();
		ExpenseDetailCodingBlocks cb2 = new ExpenseDetailCodingBlocks();
		cb1.setPercent(50.00);
		cb2.setPercent(50.00);
		cbList.add(cb1);
		cbList.add(cb2);
		assertTrue(helper.validatePercentageSumTotal(cbList));
		
	}
	
	@Test
	public void testValidatePercentageSumTotalExpense_bad(){
		List<ExpenseDetailCodingBlocks> cbList = new ArrayList<ExpenseDetailCodingBlocks> ();
		ExpenseDetailCodingBlocks cb1 = new ExpenseDetailCodingBlocks();
		ExpenseDetailCodingBlocks cb2 = new ExpenseDetailCodingBlocks();
		cb1.setPercent(50.00);
		cb2.setPercent(60.00);
		cbList.add(cb1);
		cbList.add(cb2);
		Object test = cb1;
		assertFalse(helper.validatePercentageSumTotal(cbList));
		
	}
	
	@Test
	public void testGetCodingBlockExpense_good(){
		ExpenseDetailCodingBlocks cb1 = new ExpenseDetailCodingBlocks();
		cb1.setAppropriationYear("09");
		cb1.setIndexCode("00777");
		cb1.setPca("45678");
		assertEquals("090077745678", helper.getCodingBlock(cb1));
	}
	
	@Test
	public void testGetCodingBlockAdvance_good(){
		AdvanceDetailCodingBlocks cb1 = new AdvanceDetailCodingBlocks();
		cb1.setAppropriationYear("09");
		cb1.setIndexCode("00777");
		cb1.setPca("45678");
		assertEquals("090077745678", helper.getCodingBlock(cb1));
	}
	
	@Test
	public void testValidateIdenticalCodingBlockExpense_Good(){
		List<ExpenseDetailCodingBlocks> cbList = new ArrayList<ExpenseDetailCodingBlocks> ();
		ExpenseDetailCodingBlocks cb1 = new ExpenseDetailCodingBlocks();
		ExpenseDetailCodingBlocks cb2 = new ExpenseDetailCodingBlocks();
		cb1.setAppropriationYear("09");
		cb1.setIndexCode("00777");
		cb2.setAppropriationYear("09");
		cb2.setIndexCode("00778");
		cbList.add(cb1);
		cbList.add(cb2);
		assertFalse(helper.validateIdenticalCodingBlock(cbList));		
	}
	
	@Test
	public void testValidateIdenticalCodingBlockExpense_Bad(){
		List<ExpenseDetailCodingBlocks> cbList = new ArrayList<ExpenseDetailCodingBlocks> ();
		ExpenseDetailCodingBlocks cb1 = new ExpenseDetailCodingBlocks();
		ExpenseDetailCodingBlocks cb2 = new ExpenseDetailCodingBlocks();
		cb1.setAppropriationYear("09");
		cb1.setIndexCode("00777");
		cb2.setAppropriationYear("09");
		cb2.setIndexCode("00777");
		cbList.add(cb1);
		cbList.add(cb2);
		assertTrue(helper.validateIdenticalCodingBlock(cbList));		
	}
	
	@Test
	public void testValidateIdenticalCodingBlockAdvance_Good(){
		List<AdvanceDetailCodingBlocks> cbList = new ArrayList<AdvanceDetailCodingBlocks> ();
		AdvanceDetailCodingBlocks cb1 = new AdvanceDetailCodingBlocks();
		AdvanceDetailCodingBlocks cb2 = new AdvanceDetailCodingBlocks();
		cb1.setAppropriationYear("09");
		cb1.setIndexCode("00777");
		cb1.setPca("58597");
		cb2.setAppropriationYear("09");
		cb2.setIndexCode("00778");
		cb2.setPca("58597");
		cbList.add(cb1);
		cbList.add(cb2);
		assertFalse(helper.validateIdenticalCodingBlock(cbList));		
	}
	
	@Test
	public void testValidateIdenticalCodingBlockAdvance_Bad(){
		List<AdvanceDetailCodingBlocks> cbList = new ArrayList<AdvanceDetailCodingBlocks> ();
		AdvanceDetailCodingBlocks cb1 = new AdvanceDetailCodingBlocks();
		AdvanceDetailCodingBlocks cb2 = new AdvanceDetailCodingBlocks();
		cb1.setAppropriationYear("09");
		cb1.setIndexCode("00777");
		cb1.setPca("58597");
		cb2.setAppropriationYear("09");
		cb2.setIndexCode("00777");
		cb2.setPca("58597");
		cbList.add(cb1);
		cbList.add(cb2);
		assertTrue(helper.validateIdenticalCodingBlock(cbList));		
	}
	
	@Test
	public void testGetValidValueEmpty(){
		String paddedValue = ActionHelper.getValidValue("", 5);
		assertEquals("     ", paddedValue);
	}
	
	@Test
	public void testGetValidValue(){
		String paddedValue = ActionHelper.getValidValue("ABC", 5);
		assertEquals("ABC", paddedValue);
	}
	
}
