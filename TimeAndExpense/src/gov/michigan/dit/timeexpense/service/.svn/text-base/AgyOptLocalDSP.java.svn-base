package gov.michigan.dit.timeexpense.service;
 
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;

import gov.michigan.dit.timeexpense.model.display.DisplayAgencyDept;
import gov.michigan.dit.timeexpense.model.display.DisplayDeptAgyLoc;
import gov.michigan.dit.timeexpense.model.core.VExpenseLocations;
import gov.michigan.dit.timeexpense.dao.AgyOptLocCommonMilDAO;
import gov.michigan.dit.timeexpense.dao.AppointmentDAO;
import gov.michigan.dit.timeexpense.model.core.Agency;
import gov.michigan.dit.timeexpense.model.core.AgencyCommonMileages;
import gov.michigan.dit.timeexpense.model.core.CommonMileages;
import gov.michigan.dit.timeexpense.model.core.ExpenseLocations;
import gov.michigan.dit.timeexpense.model.core.States;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.util.IConstants;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

public class AgyOptLocalDSP {

	private AgyOptLocCommonMilDAO agyOptCommDao;
	private SecurityManager security;
	CommonDSP commonDsp;
	AppointmentDAO appointmentDao;
	
	public AgyOptLocalDSP(EntityManager em) {
		agyOptCommDao = new AgyOptLocCommonMilDAO(em);
		security = new SecurityManager(em);
		commonDsp = new CommonDSP(em);
		appointmentDao = new AppointmentDAO(em);
		// UserProfile profile = super.getLoggedInUser();

	}
	/**
	 * This method is for saving miles between cities in Agency Common Mileage table
	 * @param objSave
	 * @return
	 */
	
	public AgencyCommonMileages saveAgyNewMiles(AgencyCommonMileages objSave)
	{
		AgencyCommonMileages agyMil =agyOptCommDao.saveNewMileage(objSave);
		return agyMil;
	}
	
	
	/**
	 * This method is for saving miles between cities in Agency Common Mileage table
	 * @param objSave
	 * @return
	 */
	
	public CommonMileages saveCommonNewMiles(CommonMileages objSave)
	{
		CommonMileages agyMil =agyOptCommDao.saveCommonMileage(objSave);
		return agyMil;
	}
	
	
	/**
	 * Get Common mileage between cities 
	 * @param city1
	 * @param city2
	 * @return miles in Int
	 */
	
	public int milesCommBtwCity(int city1,int city2)
	{
		return agyOptCommDao.getMilesCommBtwn(city1, city2) ;
	}
	/**
	 * get Agency mileage between cities
	 * @param city1
	 * @param city2
	 * @return mileage as Int
	 */
	public AgencyCommonMileages milesAgyBtwCity(int city1,int city2)
	{
		return agyOptCommDao.getMilesBtwn(city1, city2) ;
	}
	
	/**
	 * This method is for updating a new location for the agency/dept
	 * @param obj
	 * @return String
	 */
	public boolean updateLocation(ExpenseLocations obj)
	{
		boolean retvalue = agyOptCommDao.updateNewCity(obj);
		return retvalue;
	}
	/**
	 * This method is for deleting a  location for the agency/dept
	 * @param obj
	 * @return String
	 */
	public String deleteLocation(int numberL)
	{
		ExpenseLocations expLocations = agyOptCommDao.checkValidLoc(numberL);
		if (expLocations!=null)
		{ 
		agyOptCommDao.deleteAgencyLoc( expLocations.getDepartment(),  expLocations.getAgency(),numberL);
		}
		agyOptCommDao.deleteCityForDeptAgy(numberL);
		
		return IConstants.SUCCESS;
	}
	
	/**
	 * This method retrieves The error codes
	 * @param retvalidate
	 * @return
	 */
	
	public ErrorMessages getErrorCode(String retvalidate )
	{
		ErrorMessages errMess = commonDsp.getErrorCode(retvalidate);
		return errMess;
	}
	
	/**
	 * This method is used for getting the search results for a given dept and agency
	 * @param chosenDepartment
	 * @param chosenAgency
	 * @return List<DisplayAgencyDept>
	 */
	public List<DisplayAgencyDept> findSearchResultsWithParam(String chosenDepartment,String chosenAgency )
	{
		
		List<DisplayAgencyDept> retDisplay = new ArrayList();
		List<Agency> result=agyOptCommDao.getSearchResultsWithParams(chosenDepartment, chosenAgency);
		if(result !=null && result.size()>0)
		{
			retDisplay= getDisplayDetails(result);
		}
				 
		return retDisplay;
	}
	/**
	 * This method is used for getting the search results for a given dept
	 * @param chosenDepartment
	 * @return List<DisplayAgencyDept>
	 */
	
	public  List<DisplayAgencyDept> findSearchResultsWithParamDept(String chosenDepartment, String userId, String moduleId)
	{
		List<DisplayAgencyDept> retDisplay = new ArrayList();
		List<Agency> result=appointmentDao.findAgencies(userId, moduleId, chosenDepartment);
		if(result !=null && result.size()>0)
		{
			retDisplay= getDisplayDetails(result);
		}
				 
		return retDisplay;
	}
	/**
	 * This method is used for getting the search results  
	 * @return List<DisplayAgencyDept>
	 */
	public List<DisplayAgencyDept> findSearchResults (  )
	{
		List<DisplayAgencyDept> retDisplay = new ArrayList();
		List<Agency> result=agyOptCommDao.getSearchResults ();
		if(result !=null && result.size()>0)
		{
			retDisplay=getDisplayDetails(result);
		}
		return retDisplay;
	}
	/**
	 * This is used to set the identifier key 
	 * @param listD
	 * @return List<DisplayAgencyDept>
	 */
	private List<DisplayAgencyDept> getDisplayDetails(List<Agency>  listD)
	{
		List<DisplayAgencyDept> retDisplay = new ArrayList(listD.size());
		for(int i=0;i<listD.size();i++)
		{
			DisplayAgencyDept temp = new DisplayAgencyDept();
			 
			StringBuffer tempKey = new StringBuffer();
			tempKey.append(listD.get(i).getAgencyPK().getDepartment());
			tempKey.append(",");
			tempKey.append(listD.get(i).getAgencyPK().getAgency());
			temp.setIdentifierKey(tempKey.toString().trim());
			temp.setAgencyName(listD.get(i).getName());
			temp.setAgencyNum(listD.get(i).getAgencyPK().getAgency());
			temp.setDeptNum(listD.get(i).getAgencyPK().getDepartment()); 
			 
			 
			retDisplay.add(temp);
		}
		return retDisplay;
	}
	

	 
	/**
	 * This method retrieves details for a given agency and department
	 * @param dept
	 * @param agency
	 * @return DisplayDeptAgyLoc
	 */
	
	public DisplayDeptAgyLoc getDetailsDeptLoc(String dept, String agency,
			Map<String, Object> applicationCache, boolean getAllCities) {
		
		DisplayDeptAgyLoc dObject = new DisplayDeptAgyLoc();
		
		
		if (getAllCities){
			
			List<VExpenseLocations> stateALLList = agyOptCommDao.getALLStateforDept(
					dept, agency);
			
			dObject.setStateLocations(stateALLList);
			
		}
		else{
			List<ExpenseLocations> expList = agyOptCommDao.getCityStateforDeptAgy(
					dept, agency);
			dObject.setAgyLocations(expList);
		}
		
		dObject.setAgency(agency);
		dObject.setDepartment(dept);
		
		List<Object> objStates = (List<Object>)applicationCache.get(IConstants.STATES);
		List<States> listStates = new ArrayList();  
		
		for(Object obj : objStates){
			Object[] state = (Object[])obj;
			States objState = new States();
			objState.setCode((String)state[0]);
			objState.setDisplay((String)state[0]);			
			listStates.add(objState);
		}
		
		dObject.setStatesUs(listStates);

		List<Agency> agy = agyOptCommDao.getSearchResultsWithParams(dept,
				agency);
		dObject.setAgencyName(agy.get(0).getName());

		return dObject;
	}
	
	/**
	 * This method gets the list of locations 
	 * @param dept
	 * @param agency
	 * @return DisplayDeptAgyLoc
	 */
//	public DisplayDeptAgyLoc getLocations(String dept,String agency)
//	{
//		DisplayDeptAgyLoc dObject = new DisplayDeptAgyLoc();
//		
//		 List<ExpenseLocations> expList=agyOptCommDao.getlistDeptAgy(dept,agency);
//		
//		 dObject.setAgency(agency);
//		 dObject.setDepartment(dept);
//		 dObject.setAgyLocations(expList);
//		   List<String>    stList =agyOptCommDao.getListOfStates();
//		   List<States> st = new ArrayList();
//		  for(int i=0;i<stList.size();i++)
//		  {
//			  States newSt = new States();
//			  newSt.setCode(stList.get(i));
//			  newSt.setDisplay(stList.get(i));
//			  st.add(newSt);
//		  } 
//		 dObject.setStatesUs(st);
//		 
//		 List<Agency> agy = agyOptCommDao.getSearchResultsWithParams(dept,agency);
//		 dObject.setAgencyName(agy.get(0).getName());
//		 
//	 	return dObject;
//	}
	
	
	/**
	 * This gives the city and state description in combination.
	 * @param dept
	 * @param agency
	 * @return
	 */
	public List<ExpenseLocations> getDetailsL(String dept,String agency)
	{
		 List<ExpenseLocations> expList=agyOptCommDao.getCityStateforDeptAgy(dept,agency);
		 if(expList!=null && expList.size()>0)
				 {
			       for(int i =0;i< expList.size();i++)
			       {
			    	   String Abc =expList.get(i).getCity()+","+expList.get(i).getStProv();
			    	   expList.get(i).setDescription(Abc);
			       }
				 }
		 return  expList;
	}
	/**
	 * This method validates the delete key  
	 * @param delPkCheck
	 * @return String
	 */
	public String validateDelete(int delPkCheck)
	{
		ExpenseLocations expData = agyOptCommDao.checkValidLoc(delPkCheck);
		if(expData !=null && expData.getElocIdentifier()==delPkCheck)
		{
		return IConstants.SUCCESS;
		}
		else
		{
			return IConstants.COMMON_MILEAGE_DELETE_LOCATION;
		}
	}
	/**
	 * This method checks the valid parameters passed for dept and agency
	 * @param agyNum
	 * @param deptNum
	 * @return
	 */
	
	public String validateAgyDeptValues(String agyNum,String deptNum)
	{
		List<Agency> agy = agyOptCommDao.getSearchResultsWithParams(deptNum,agyNum);
		if(agy!=null && agy.size()>0 )
		{
		return IConstants.SUCCESS;
		}else
		{
			return IConstants.COMMON_MILEAGE_AGY_DEPT;
		}
	}
	
	 
	/**
	 * This method checks the logged in users access to update the state mileage 
	 * @param prof
	 * @param moduleId
	 * @return boolean
	 */
	
	public boolean checkStateLocationAccess(UserProfile prof ,String moduleId)
	{
		boolean stateAccess = security.checkAccess(prof.getUserId(), moduleId);
		return stateAccess;
	}
	
	
	/**
	 * Method to check if the city is already present in Statewide or same
	 * department/Agency.
	 * 
	 * @param city
	 * @param state
	 * @param department
	 * @param agency
	 * @return String
	 */
	public String isCityAlreadyExists(String city, String state,
			String department, String agency) {

		return agyOptCommDao.isCityAlreadyExists(city, state, department,
				agency);
	}
	

}
