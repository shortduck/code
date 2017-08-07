package gov.michigan.dit.timeexpense.dao.test;

import gov.michigan.dit.timeexpense.dao.AgyOptLocCommonMilDAO;
import gov.michigan.dit.timeexpense.model.core.Agency;
import gov.michigan.dit.timeexpense.model.core.AgencyCommonMileages;
import gov.michigan.dit.timeexpense.model.core.CommonMileages;
import gov.michigan.dit.timeexpense.model.core.CommonMileagesPK;
import gov.michigan.dit.timeexpense.model.core.ExpenseLocations;
import gov.michigan.dit.timeexpense.model.core.VExpenseLocations;

import java.math.BigDecimal;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class AgyOptLocCommonMilDAOTest extends AbstractDAOTest{

	
	
	private AgyOptLocCommonMilDAO dao = new AgyOptLocCommonMilDAO();
	//private SystemCodeDAO dao ;
 
	public AgyOptLocCommonMilDAOTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void startTransaction(){
		super.beginTransaction(); 
		dao.setEntityManager(em);
	}
	/**
	 * This is to test the finder getSearchResults
	 */
	@Test
	public void testGetSearchResults(){

		 List<Agency>  arr=  dao.getSearchResults();
		Assert.assertTrue(arr.size()>0);
	}
	/**
	 * This is to test the finder getSearchResultsWithParams
	 */
	@Test
	public void testGetResultsParam(){

		String dept="01";
		String agy="01";
		 List<Agency>  arr=  dao.getSearchResultsWithParams(dept, agy);
		Assert.assertTrue(arr.size()>0);
	}
	
	@Test
	public void getCityStateforDeptAgy()
	{
		String dept="11";
		String agy="01";
		 List<ExpenseLocations>  arr=  dao.getCityStateforDeptAgy(dept, agy);
			Assert.assertTrue(arr.size()>0);	
	}
	
	
	/**
	 * Test case for deleeting a location
	 */
	@Test
	public void deleteCityForDeptAgy()
	{
		 int l=0;
		 Boolean arr=  dao.deleteCityForDeptAgy(2364);
		 if(arr)
		 {
			 l=1;
		 }
			Assert.assertTrue(l>0);	
	}
	/**
	 * Junit for new city location 
	 */
	@Test
	public void updateNewCity()
	{
		int l=0;
		ExpenseLocations obj= new ExpenseLocations();
		obj.setAgency("01");
		obj.setDepartment("01");
		obj.setCity("TEST");
		obj.setStProv("MI");
		boolean  arr=  dao.updateNewCity(obj);
		if(arr)
		{
			l=1;
		}
			Assert.assertTrue(l>0);	
	}
	/**
	 * Getting the list of locations
	 */
	@Test
	public void getlistDeptAgy(){
		String dept="01";
		String agy="01";
		 List<ExpenseLocations>  arr=  dao.getlistDeptAgy(dept, agy);
			Assert.assertTrue(arr.size()>0);	
	}
	/**
	 * get list of states
	 */
//	@Test
//	public void getListOfStates()
//	{
//		 
//		 List<String>  arr=  dao.getListOfStates();
//			Assert.assertTrue(arr.size()>0);	
//	}
	/**
	 * test for getting the mileage between two cities 
	 */
	@Test
	public void getMilesCommBtwn()
	{
		 
		int l=  dao.getMilesCommBtwn(100, 101);
		 Assert.assertTrue(l>0);	
	}
	/**
	 * Test for Save New mileage
	 */
	@Test 
	public void saveNewMileage()
	{
		AgencyCommonMileages  newObj = new AgencyCommonMileages();
		BigDecimal dt = new BigDecimal(45);
		BigDecimal from = new BigDecimal(2430);
		BigDecimal to = new BigDecimal(2457);
		newObj.setAgency("01");
		newObj.setAgyMileage(dt);
		newObj.setDepartment("01");
		newObj.setFromElocIdentifier(from);
		newObj.setToElocIdentifier(to);		
		AgencyCommonMileages  newObj1 =dao.saveNewMileage(newObj);
		Assert.assertTrue(newObj1.getAgycmIdentifier()>0);
		
	}
	/**
	 * get the miles between two cities
	 */
	@Test
	public void getMilesBtwn()
	{
		AgencyCommonMileages l=  dao.getMilesBtwn(2364, 2365);
		Assert.assertTrue(l.getAgycmIdentifier()>0);	
	}
	
	// check valid checkValidLoc
	
	/**
	 * Check if location is valid
	 */
	@Test
	public void getValidLocation()
	{
		ExpenseLocations l=  dao.checkValidLoc(100);
		Assert.assertTrue(l.getElocIdentifier()>0);	
	}
	
	/**
	 * Check if location is valid
	 */
	@Test
	public void getValidALLLocation()
	{
		List<VExpenseLocations> l=  dao.getALLStateforDept("01","01");
		Assert.assertTrue(l.size()>0);	
	}
	/**
	 * Check if location is valid
	 */
	@Test
	public void saveCommonMileage()
	{
		BigDecimal d1= new BigDecimal(100);
		CommonMileages obj = new CommonMileages();
		obj.setMileage(d1);		
		CommonMileagesPK ob1 = new CommonMileagesPK();
		ob1.setFromElocIdentifier(2309);
		ob1.setToElocIdentifier(2308);
		obj.setPk(ob1);
		CommonMileages l=  dao.saveCommonMileage(obj);
		Assert.assertTrue(l.getMileage().intValue()>0);	
	}
	
	@Test
	public void testGetALLStateforDept()
	{
		List<VExpenseLocations> locList = dao.getALLStateforDept("11", "01");
		Assert.assertTrue(!locList.isEmpty());	
	}
}



