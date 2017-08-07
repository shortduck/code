package gov.michigan.dit.timeexpense.dao.test;


import gov.michigan.dit.timeexpense.dao.ExpenseTypeDAO;
import gov.michigan.dit.timeexpense.model.display.ExpenseTypeDisplayBean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExpenseTypeDAOTest extends AbstractDAOTest{

	private ExpenseTypeDAO dao = new ExpenseTypeDAO();
	
	@Before
	public void startTransaction(){
		beginTransaction();
		dao.setEntityManager(em);
	}
		
	
	@Test
	public void testFindAllExpenseTypesWithStandardRateAndMileageIndicator() throws Exception {
		List<ExpenseTypeDisplayBean> expenseTypes = dao.findAllExpenseTypesWithMileageIndicator(new Date(), "27", "01", "AL", false, "", "", "");		
		Assert.assertTrue(expenseTypes.size() > 0);
	}
	
	@Test
	public void testPDFExpenses() throws Exception {
		
		Date todaysDate = new Date();
		
		List<ExpenseTypeDisplayBean> listExpenseTypes = dao.findAllExpenseTypesWithMileageIndicator(todaysDate, "N", "N", "N",true,  "27", "01", "AL");
		
		boolean isPDFExpenseAvailable = false;
		for (ExpenseTypeDisplayBean expense: listExpenseTypes){
			if (expense.getDescription().contains("PDF")){				
				isPDFExpenseAvailable = true;
				break;
			}			
		}
		
		Assert.assertTrue(isPDFExpenseAvailable);
		
	}
	
	@Test
	public void testTravelMealExpenses() throws Exception {
		
		Date todaysDate = new Date();
		
		List<ExpenseTypeDisplayBean> listExpenseTypes = dao.findAllExpenseTypesWithMileageIndicator(todaysDate, "N", "Y", "N",false,  "27", "01", "AL");
		
		boolean isExpenseAvailable = false;
		for (ExpenseTypeDisplayBean expense: listExpenseTypes){
			if (expense.getDescription().contains("Lunch")){				
				isExpenseAvailable = true;
				break;
			}			
		}		
		Assert.assertTrue(isExpenseAvailable);		
	}
	
	
	
	@Test
	public void testNoTravelNoMealExpenses() throws Exception {
		
		Date todaysDate = new Date();
		
		List<ExpenseTypeDisplayBean> listExpenseTypes = dao.findAllExpenseTypesWithMileageIndicator(todaysDate, "N", "N", "N",false,  "27", "01", "AL");
		
		boolean isExpenseAvailable = false;
		for (ExpenseTypeDisplayBean expense: listExpenseTypes){
			if (expense.getDescription().startsWith("Breakfast")){				
				isExpenseAvailable = true;
				break;
			}			
		}
		
		Assert.assertFalse(isExpenseAvailable);		
	}
	
	
	
	@Test
	public void testIncidentalExpenses() throws Exception {
		
		Date todaysDate = new Date();
		
		List<ExpenseTypeDisplayBean> listExpenseTypes = dao.findAllExpenseTypesWithMileageIndicator(todaysDate, "N", "N", "Y", false, "27", "01", "AL");
		
		boolean isExpenseAvailable = false;
		for (ExpenseTypeDisplayBean expense: listExpenseTypes){
			if (expense.getDescription().startsWith("Incidental")){				
				isExpenseAvailable = true;
				break;
			}			
		}		
		Assert.assertTrue(isExpenseAvailable);		
	}
	

}
