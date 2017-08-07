package gov.michigan.dit.timeexpense.service.test;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.michigan.dit.timeexpense.action.ActionHelper;
import gov.michigan.dit.timeexpense.model.core.ExpenseProfileRules;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CommonDSPTest {
	
	static Logger logger = Logger.getLogger(CommonDSPTest.class);
	private CommonDSP commonDSP;
	private ExpenseDSP expenseDSP;
	private static EntityManagerFactory emf;
	private EntityManager em;
	
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
		expenseDSP = new ExpenseDSP(em);
		em.getTransaction().begin();		
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}


	@Test
	public void testGetNewsItemList() {
		List list = commonDSP.getNewsItemList();
		assertNotNull(list);
	}
	

	@Test
	public void testGetFiscalYearEndDate() {
		String date1 = "09/30/";
		String date2 = null;
		String dateWithYear = date1 + Calendar.getInstance().get(Calendar.YEAR);
		DateFormat df = new SimpleDateFormat ("MM/dd/yyyy");
				
		try{
			date2 = df.format(commonDSP.getFiscalYearEndDate());
		} catch (Exception e){
			System.out.println("error parsing");
		}
		assertEquals(dateWithYear, date2);
	}
	/*
	@Test
	public void testGetExpenseTypeRuleValue(){
		String ruleValue = commonDSP.getExpenseTypeRuleValue("Detr", 1006);
		Assert.assertNotNull(ruleValue);
	}
	*/
	@Test
	public void testGetExpenseProfileRule(){
		ExpenseProfileRules expenseProfileRules = commonDSP.getExpenseProfileRules(109770, 1001);
		Assert.assertNotNull(expenseProfileRules);
	}
	
	@Test
	public void testGetSystemCodeValue(){
		String systemCodeValue = commonDSP.getSystemCodeValue(IConstants.ADVANCE_PAYROLL_SYSTEM_CODE);
		System.out.println(systemCodeValue);
		Assert.assertNotNull(systemCodeValue);
	}
	
	@Test
	public void testGetErrorCode(){
		ErrorMessages errorMessage = commonDSP.getErrorCode("40000");
		Assert.assertEquals("E", errorMessage.getIcon());
	}
	
	@Test
	public void testGetRefCodes(){
		String moduleId = commonDSP.getRefCode("APPR");
		Assert.assertEquals("APRF001", moduleId);
	}
	
	@Test
	public void testGetCurrentFiscalYearOn(){
		Calendar cal = Calendar.getInstance();
		cal.set(2013, 8, 30);
		String fy = commonDSP.getCurrentFiscalYear(cal.getTime());
		Assert.assertEquals(fy, "13");
	}
	
	@Test
	public void testGetCurrentFiscalYearBefore(){
		Calendar cal = Calendar.getInstance();
		cal.set(2013, 8, 29);
		String fy = commonDSP.getCurrentFiscalYear(cal.getTime());
		Assert.assertEquals(fy, "13");
	}
	
	@Test
	public void testGetCurrentFiscalYearAfter(){
		Calendar cal = Calendar.getInstance();
		cal.set(2013, 9, 1);
		String fy = commonDSP.getCurrentFiscalYear(cal.getTime());
		Assert.assertEquals(fy, "14");
	}
	
	
	@Test
    public void testExpenseDatesExistWarning() throws Exception{
          
          ExpenseMasters expenseMasters = expenseDSP.getExpense(707345);//(707364)(121314)(656388)
            boolean value = false;
          for(int i=0;i<expenseMasters.getExpenseErrorsCollection().size();i++)
          {
        	   value=expenseMasters.getExpenseErrorsCollection().get(i).getErrorCode().getErrorCode().equals(IConstants.EXPENSE_DATES_EXIST);
                if(value){
                      System.out.println(expenseMasters.getExpenseErrorsCollection().get(i).getErrorCode().getErrorText());      
                      Assert.assertTrue(value);
                      break;
                }
          }
          
		  if (!value){
                      System.out.println("Did not find the testExpenseDatesExist Warning");
     	               Assert.assertTrue(false);
                    
		           }
                }
     	  
	}

	
