package gov.michigan.dit.timeexpense.dao;
/**
 * The DAO Class handles the database requests for Agency Common Mileage 
 * Screen
 * @kaws1
 */
import javax.persistence.EntityManager;import javax.persistence.Query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import gov.michigan.dit.timeexpense.action.ExpenseAction;
import gov.michigan.dit.timeexpense.model.core.Agency;
import gov.michigan.dit.timeexpense.model.core.AgencyCommonMileages;
import gov.michigan.dit.timeexpense.model.core.CommonMileages;
import gov.michigan.dit.timeexpense.model.core.ExpenseLocations;
import gov.michigan.dit.timeexpense.model.core.User;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.model.core.VExpenseLocations;

@SuppressWarnings("unchecked")
public class AgyOptLocCommonMilDAO extends AbstractDAO{
	Logger logger = Logger.getLogger(AppointmentDAO.class);
	
    public AgyOptLocCommonMilDAO() {}

    public AgyOptLocCommonMilDAO(EntityManager em) {
    	super(em);
    }
 /**
  *  This method gets the list of the department, agency and agency name   
  * @return List<Agency>
  */
public List<Agency> getSearchResults()
{
	List<Agency> agyDeptList =null;
	String finderQuery = " select distinct agy from Agency agy  order by agy.name asc ";
	
		Query query = entityManager.createQuery(finderQuery);		
		agyDeptList = query.getResultList();

	return agyDeptList; 
}

/**
 * This finder gets the list of the dept and the agency code passed as parameters
 * The query gets results based on the passed parameter values.
 *  @param deptSel
 * @param agySel
 * @return List<Agency>
 */
public List<Agency> getSearchResultsWithParams(String deptSel, String agySel)
{
	List<Agency> agyDeptList =null;
	String finderQuery = " select distinct  agy.department,agy.agency,agy.name from AGENCIES agy  where  agy.department=?1 and agy.agency =?2" +
			" order by agy.name asc";

		agyDeptList =entityManager.createNativeQuery(finderQuery, Agency.class)
		.setParameter(1, deptSel).setParameter(2, agySel).getResultList();
	 
	return agyDeptList; 
}  

/**
 * This finder gets the list of the dept and the agency code passed as parameters
 * The query gets results based on the passed parameter values.
 *  @param deptSel
 * @param agySel
 * @return List<Agency>
 */
public List<Agency> getSearchResultsWithParamsDept(String deptSel)
{
	List<Agency> agyDeptList =null;
	String finderQuery = " select distinct  agy.department,agy.agency,agy.name from AGENCIES agy  where  agy.department=?1 " +
			" order by agy.name asc";
		agyDeptList =entityManager.createNativeQuery(finderQuery, Agency.class)
		.setParameter(1, deptSel).getResultList();
		
	return agyDeptList; 
} 


/**
 * Gets the list of locations in for Department and Agency listed 'AL'.
 * @param dept
 * @param agy
 * @return List<ExpenseLocations> 
 */


public List<VExpenseLocations> getALLStateforDept(String dept,String agency){
	List<VExpenseLocations> listCityByDeptAgy = null;
	
		listCityByDeptAgy = entityManager
				.createNativeQuery(
						"SELECT * FROM V_Expense_Locations v WHERE v.department IN ('AL',?1) AND v.agency  IN ('AL',?2)", VExpenseLocations.class )
				.setParameter(1, dept).setParameter(2, agency).getResultList();
	
	return listCityByDeptAgy;
	
}

/**
 * Gets the list of locations in for a given department and agency.
 * @param dept
 * @param agy
 * @return List<ExpenseLocations> 
 */
		public List<ExpenseLocations> getCityStateforDeptAgy(String dept, String agy){
			List<ExpenseLocations> cityAgy = null;			
			String finderQuery = " SELECT eloc_identifier, "+
							     "  city, "+
							     "  st_prov "+
							  "FROM Expense_Locations e "+
							  "WHERE e.department = ?1 and e.agency = ?2 ORDER BY DISPLAY_ORDER, city";

			cityAgy =entityManager.createNativeQuery(finderQuery,ExpenseLocations.class)
			.setParameter(1, dept)
			.setParameter(2, agy)
			.getResultList();	
		
			return cityAgy;
		}
		
		/**
		 * get the department and agency 
		 * @param dept
		 * @param agy
		 * @return
		 */
		public List<ExpenseLocations> getlistDeptAgy(String dept, String agy){
			List<ExpenseLocations> cityAgy = null;
			String finderQuery = " select * from Expense_Locations e "+
			"where e.department = ?1 and e.agency = ?2 order by e.department asc";
			
			cityAgy =entityManager.createNativeQuery(finderQuery,ExpenseLocations.class)
			.setParameter(1, dept)
			.setParameter(2, agy)
			.getResultList();	
			
			return cityAgy;
		}
		/**
		 * This method persists a new location to the dept / agency List
		 * @param obj
		 * @return boolean true|false
		 */
		public boolean updateNewCity(ExpenseLocations obj)
		{
			boolean valRet= true;
			if (obj.getElocIdentifier() == null || obj.getElocIdentifier()<=0 ) {
				entityManager.persist(obj);
				valRet= true;
			}
			else
			{
				valRet= false;
			}
			return valRet;
		}
		/**
		 * This method deletes the selected city location from a dept /agency list
		 * @param numberL
		 * @return boolean true|false
		 */
	
		public boolean deleteCityForDeptAgy(int numberL)
		{
			String finderQuery = " select *  from  Expense_Locations e "+
			"where e.eloc_identifier = ?";

			ExpenseLocations singleResult = (ExpenseLocations) entityManager.createNativeQuery(finderQuery,ExpenseLocations.class)
			.setParameter(1, numberL).getSingleResult();
		    entityManager.remove(singleResult); 

			return true;
		}
		
		/**
		 * This method deletes the selected cities from agency common mileages table
		 * @param agencyResult
		 * @return boolean
		 */
		public boolean deleteAgencyLoc( String dept,String agency,int valLoc )
		{
			String finderAgyAgyLoc = "select s from AgencyCommonMileages s where s.agency= :agency and s.department = :dept and ( s.toElocIdentifier = :valLoc" + 
			" or s.fromElocIdentifier= :valLoc)";	
			
			Query query = entityManager.createQuery(finderAgyAgyLoc)
			  .setParameter("agency", agency)
			  .setParameter("dept", dept)
			  .setParameter("valLoc", new BigDecimal(valLoc));

			List<AgencyCommonMileages> agencyResult = (List<AgencyCommonMileages>) query
					.getResultList();
			List<AgencyCommonMileages> copyList = new ArrayList<AgencyCommonMileages>(
					agencyResult.size());
			for (int i = 0; i < agencyResult.size(); i++) {
				copyList.add(agencyResult.get(i));
			}
			Collections.copy(copyList, agencyResult);
			Iterator it = copyList.iterator();
			while (it.hasNext()) {
				AgencyCommonMileages error = (AgencyCommonMileages) it.next();
				error = entityManager.find(AgencyCommonMileages.class, error
						.getAgycmIdentifier());
				it.remove();
				entityManager.remove(error);
			}
			Collections.copy(agencyResult, copyList);

		return true;
	}
		
		/**
		 * This method gets the Common  mileage from  the selected city location from a dept /agency list
		 * @param city1,city2
		 * @return int miles
		 */
		public int getMilesCommBtwn(int city1,int city2)
		{
			List<CommonMileages> cityAgy = null;
			String finderQuery = " select * from COMMON_MILEAGES where from_eloc_identifier =?" +
			                     " and to_eloc_identifier =?";

				cityAgy =	(List<CommonMileages>)entityManager.createNativeQuery(finderQuery,CommonMileages.class)
				.setParameter(1, city1)
				.setParameter(2, city2).getResultList();	

			if(cityAgy!=null && cityAgy.size()>0)
			{
			return cityAgy.get(0).getMileage().intValueExact();
		}else
		{
			return 0;
		}
		}
		 
		/**
		 * This gets the agency defined mileage
		 * @param city1
		 * @param city2
		 * @return int
		 */
		
		public AgencyCommonMileages getMilesBtwn(int city1,int city2)
		{
			List<AgencyCommonMileages> cityAgy = null;
			BigDecimal bd_1 = new BigDecimal (city1);
			BigDecimal bd_2 = new BigDecimal (city2);

			String finderQuery = " select s from AgencyCommonMileages s"
				+" where s.fromElocIdentifier = :bd_1"
			    +" and s.toElocIdentifier = :bd_2";
	
				Query query = entityManager.createQuery(finderQuery)
				.setParameter("bd_1", bd_1)
				.setParameter("bd_2", bd_2);
				cityAgy =(List<AgencyCommonMileages>) query.getResultList();	

			if(cityAgy!=null && cityAgy.size()>0)
			{	
				return cityAgy.get(0);
			}else
			{
				AgencyCommonMileages retVla = new AgencyCommonMileages();
				return retVla;
			}
		}
		
		
		/**
		 * This method creates the new mileage between the two cities of an agency
		 * @param city1,city2
		 * @return int miles
		 */
		public AgencyCommonMileages saveNewMileage(AgencyCommonMileages newObj)
		{
				if (newObj.getAgycmIdentifier()==0 ) {
					entityManager.persist(newObj); 
				/*	String finderQuery = " select s from AgencyCommonMileages s"
						+" where s.fromElocIdentifier ="+newObj.getFromElocIdentifier();
					Query query = entityManager.createQuery(finderQuery);
					List<AgencyCommonMileages> cityAgys =(List<AgencyCommonMileages>) query.getResultList();*/
				}
				else
				{
					newObj = entityManager.merge(newObj);
				}
			return newObj;
		}
		/**
		 *  This method is for saving new common mileage 
		 * @param newObj
		 * @return
		 */
		
		public  CommonMileages saveCommonMileage( CommonMileages newObj)
		{
			newObj = entityManager.merge(newObj);			   
			return newObj;
		}
		
	 /**
	  * get the list of States 
	  * @return List<String>
	  */
		
//		public List<String>  getListOfStates()
//		{
//			List<String> cityAgy = null;
//			String finderQuery = " select distinct e.stateCode  from  States e order by e.stateCode asc "; 
//
//        	cityAgy =entityManager.createQuery(finderQuery).getResultList();	
//
//			return cityAgy;
//		}
		
		/**
		 * This finder checks if the given location id for delete is valid
		 * @param locId
		 * @return ExpenseLocations
		 */
		public ExpenseLocations checkValidLoc(int locId)
		{
			String finderQuery = " select * from  Expense_Locations e where e.ELOC_IDENTIFIER ="+locId;
			ExpenseLocations exp =null;
			List<ExpenseLocations> checkLocations =entityManager.createNativeQuery(finderQuery,ExpenseLocations.class).getResultList();			
			if(checkLocations!=null &&checkLocations.size()>0 )
			{
				exp= checkLocations.get(0);
			}
			return exp;
		}
		
		/**
		 * Method to check if the city is already present in Statewide or same department/Agency.
		 * @param city
		 * @param state
		 * @param department
		 * @param agency  
		 * @return String
		 */		
	public String isCityAlreadyExists(String city, String state,
			String department, String agency) {
		
		String finderQuery = "SELECT count(eloc_identifier) "
				+ " FROM expense_locations " + "WHERE     UPPER (city) = ?1 "
				+ "      AND UPPER (st_prov) = ?2 "
				+ "      AND department = 'AL' " + "      AND agency = 'AL'";

		int count = Integer.parseInt(entityManager.createNativeQuery(finderQuery)
				.setParameter(1, city).setParameter(2, state).getSingleResult().toString());

		if (count > 0)
			return IConstants.LOCATION_ERROR_CITY_IN_STATEWIDE;
		
		finderQuery = "SELECT count(eloc_identifier) "
				+ " FROM expense_locations " + "WHERE     UPPER (city) = ?1 "
				+ "      AND UPPER (st_prov) = ?2 "
				+ "      AND department = ?3 " + "      AND agency = ?4 ";

		count = Integer.parseInt(entityManager.createNativeQuery(finderQuery)
				.setParameter(1, city).setParameter(2, state)
				.setParameter(3, department).setParameter(4, agency)
				.getSingleResult().toString());
		
		if (count > 0)
			return IConstants.LOCATION_ERROR_CITY_IN_AGENCY;
				
		return 	"0";

	}
	
}
