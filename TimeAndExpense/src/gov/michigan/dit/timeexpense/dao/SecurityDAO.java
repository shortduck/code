package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.exception.MultipleUsersException;
import gov.michigan.dit.timeexpense.model.core.SystemCodes;
import gov.michigan.dit.timeexpense.model.core.User;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * Data access class for all <code>User</code> and <code>Security</code> 
 * related database access.
 * 
 * @author chaudharym
 */
public class SecurityDAO extends AbstractDAO{
	
	private static Logger logger = Logger.getLogger(SecurityDAO.class);

	public SecurityDAO() {}
	
	public SecurityDAO(EntityManager em) {
		super(em);
	}
	
	/**
	 * Finds HRMN user with the provided employee Id. Along with the user
	 * all the security modules that the user has access to are also fetched. 
	 * 
	 * @param empId
	 * @return User with accessible module information
	 * @throws MultipleUsersException
	 */
	public User findUserByEmpId(int empId) throws MultipleUsersException {
		User result = null;

		List<User> users = entityManager.createNamedQuery("findUserByEmpId")
				.setParameter("empId", empId).getResultList();

		if (users != null && !users.isEmpty()) {
			if (users.size() > 1) {
				String error = findErrorTextByCode(IConstants.MULTIPLE_USERS_FOR_EMPLOYEE_ERROR_CODE);
				throw new MultipleUsersException(IConstants.MULTIPLE_USERS_FOR_EMPLOYEE_ERROR_CODE, error);
			} else {
				result = users.get(0);
			}
		}

		return result;
	}
	
	
	
	/**
	 * Finds a non HRMN user with the provided employee Id. Along with the user
	 * all the security modules that the user has access to are also fetched. 
	 * 
	 * @param empId
	 * @return User with accessible module information
	 * @throws MultipleUsersException   
	 */
	public User findUserByNonEmpId(int empId) throws MultipleUsersException {
		User result = null;

		List<User> users = entityManager.createNamedQuery("findUserByNonEmpId")
				.setParameter("nempIdentifier", empId).getResultList();

		if (users != null && !users.isEmpty()) {
			if (users.size() > 1) {
				String error = findErrorTextByCode(IConstants.MULTIPLE_USERS_FOR_EMPLOYEE_ERROR_CODE);
				throw new MultipleUsersException(IConstants.MULTIPLE_USERS_FOR_EMPLOYEE_ERROR_CODE, error);
			} else {
				result = users.get(0);
			}
		}
		result = users.get(0);
		return result;
	}
	
	/**
	 * Finds user with the provided user Id. Along with the user
	 * all the security modules that the user has access to are also fetched. 
	 * 
	 * @param userId
	 * @return User with accessible module information
	 * @throws MultipleUsersException
	 */
	public User findUserByUserId(String userId) throws MultipleUsersException {
		User result = null;

		List<User> users = entityManager.createQuery("select u from User u where u.userId = :userID")
				.setParameter("userID", userId).getResultList();

		if (users != null && !users.isEmpty()) {
			if (users.size() > 1) {
				String error = findErrorTextByCode(IConstants.MULTIPLE_USERS_FOR_EMPLOYEE_ERROR_CODE);
				throw new MultipleUsersException(IConstants.MULTIPLE_USERS_FOR_EMPLOYEE_ERROR_CODE, error);
			} else {
				result = users.get(0);
			}
		}

		return result;
	}
	
	/**
	 * Finds the security scope for a module in a department, agency and TKU.
	 * 
	 * @param moduleId
	 * @param dept
	 * @param agency
	 * @param tku
	 * @return Security scope information
	 */
	/*public Security findSecurityScope(String userId, String moduleId, String dept, String agency, String tku){
		Security security = null;
		
		User user = entityManager.find(User.class, userId);
		
		List<Security> secModules = entityManager.createQuery("select s from Security s where s.user=:user and s.moduleId = :moduleId" +
				" and s.department=:dept and s.agency=:agency and s.tku=:tku")
				.setParameter("user", user).setParameter("moduleId", moduleId)
				.setParameter("dept", dept).setParameter("agency", agency).setParameter("tku", tku)
				.getResultList();
		
		if(!secModules.isEmpty()){
			security = secModules.get(0);
		}
		
		return security;
	}*/
	
	/**
	 * Finds change access indicator value authorized to the user for the given module. The indicator
	 * value is determined starting with the finest granularity of scope and gradually decreasing scope
	 * granularity if match not found at any level. 
	 * <p>E.g. If the user provides dept/agency/tku = 1/2/3 then the indicator value for the first match 
	 * from the following would be taken:
	 * <ul>
	 * 	<li>1/2/3 (first preference)</li>
	 * 	<li>1/2/AL</li>
	 * 	<li>1/AL/2</li>
	 * 	<li>1/AL/AL</li>
	 * 	<li>AL/2/3</li>
	 * 	<li>AL/2/AL</li>
	 * 	<li>AL/AL/3</li>
	 * 	<li>AL/AL/AL  (last preference)</li>
	 * </ul>
	 * </p>
	 */
	public String findSecurityScope(String userId, String moduleId, String dept, String agency, String tku){
		String security = null;
		
		User user = entityManager.find(User.class, userId);
		
		String query = "SELECT SEC.CHANGE_ACCESS_IND FROM  SECURITY SEC " +
	     			   "WHERE (SEC.USER_ID = ?1 ) AND (SEC.MODULE_ID = ?2 ) AND " +  
	     			   "(?3 BETWEEN DECODE(SEC.DEPARTMENT,'AL','00',SEC.DEPARTMENT) AND " +
	     			   "DECODE(SEC.DEPARTMENT,'AL','99',SEC.DEPARTMENT)) AND " +
	     			   "(?4 BETWEEN DECODE(SEC.AGENCY,'AL','00',SEC.AGENCY) AND " +  
	     			   "DECODE(SEC.AGENCY,'AL','99',SEC.AGENCY)) AND " +  
	     			   "(?5 BETWEEN DECODE(SEC.TKU,'AL','000',SEC.TKU) AND " + 
	                   "DECODE(SEC.TKU,'AL','999',SEC.TKU)) " +
	                   "order by SEC.DEPARTMENT ASC, SEC.AGENCY ASC, SEC.TKU ASC, SEC.CHANGE_ACCESS_IND DESC";
		
		List<String> secModules = entityManager.createNativeQuery(query)
				.setParameter(1, user.getUserId()).setParameter(2, moduleId)
				.setParameter(3, dept).setParameter(4, agency).setParameter(5, tku)
				.getResultList();
		
		if(!secModules.isEmpty()){
			// as the exact match always appears on the top due to query ordering
			security = secModules.get(0);
		    if (security.equalsIgnoreCase("N")){
		    	for (int i = 1; i < secModules.size(); i++) {
		    		security = secModules.get(i);
		    		if (security.equalsIgnoreCase("Y")){
		    			break;
		    		}
				}
		    }
		}
		
		
		return security;
	}
	
	public String isPDFModuleAvailable(String userId, String moduleId, String dept, String agency, String tku){
		String security = null;
		
		User user = entityManager.find(User.class, userId);
		
		String query = "SELECT A.CHANGE_ACCESS_IND " +
						"  FROM security A " +
						" WHERE     role_name IN " +
						"              (SELECT DISTINCT role_name " +
						"                 FROM security sec " +
						"                WHERE     user_id = ?1 " +
						"                      AND module_id = ?2 " +
						"AND (?3 BETWEEN DECODE(SEC.DEPARTMENT,'AL','00',SEC.DEPARTMENT) AND " +
		     			   "DECODE(SEC.DEPARTMENT,'AL','99',SEC.DEPARTMENT)) AND " +
		     			   "(?4 BETWEEN DECODE(SEC.AGENCY,'AL','00',SEC.AGENCY) AND " +  
		     			   "DECODE(SEC.AGENCY,'AL','99',SEC.AGENCY)) AND " +  
		     			   "(?5 BETWEEN DECODE(SEC.TKU,'AL','000',SEC.TKU) AND " + 
		                   "DECODE(SEC.TKU,'AL','999',SEC.TKU))) " +
						"       AND user_id = ?1 " +
						"       AND module_id = '" + IConstants.PDF_MODULE  + "'" +						
						"AND (?3 BETWEEN DECODE(A.DEPARTMENT,'AL','00',A.DEPARTMENT) AND " +
		     			   "DECODE(A.DEPARTMENT,'AL','99',A.DEPARTMENT)) AND " +
		     			  "(?4 BETWEEN DECODE(A.AGENCY,'AL','00',A.AGENCY) AND " +  
		     			   "DECODE(A.AGENCY,'AL','99',A.AGENCY)) AND " +  
		     			   "(?5 BETWEEN DECODE(A.TKU,'AL','000',A.TKU) AND " + 
		                   " DECODE(A.TKU,'AL','999',A.TKU))" + 
		                   " ORDER BY A.DEPARTMENT ASC," +
		                   " A.AGENCY ASC," +
		                   " A.TKU ASC," +
		                   " A.CHANGE_ACCESS_IND DESC" ;
		
		List<String> resultList = entityManager.createNativeQuery(query)
				.setParameter(1, user.getUserId()).setParameter(2, moduleId)
				.setParameter(3, dept).setParameter(4, agency).setParameter(5, tku)
				.getResultList();
										
		if(!resultList.isEmpty()){
			// as the exact match always appears on the top due to query ordering
			security = resultList.get(0);
		    if (security.equalsIgnoreCase("N")){
		    	for (int i = 1; i < resultList.size(); i++) {
		    		security = resultList.get(i);
		    		if (security.equalsIgnoreCase("Y")){
		    			break;
		    		}
				}
		    }
		}
		 
		return security;
		
	}

	
	public String findSecurityScope(String userId,String moduleId){
		String security = null;
		
		//User user = entityManager.find(User.class, userId);
		System.out.println(" MODULE_ID "+moduleId+" User Id "+userId);
		String query = "SELECT SEC.CHANGE_ACCESS_IND FROM  SECURITY SEC " +
	     			   "WHERE SEC.USER_ID ='"+userId.toUpperCase().trim()+"' AND SEC.MODULE_ID ='"+moduleId.trim()+"'"; 
	 
		List<String> resultList = (List<String>) entityManager.createNativeQuery(query).getResultList();
		List<String> secModules =resultList;
											
		if(!secModules.isEmpty()){
			// as the exact match always appears on the top due to query ordering
			security = secModules.get(0);
		    if (security.equalsIgnoreCase("N")){
		    	for (int i = 1; i < secModules.size(); i++) {
		    		security = secModules.get(i);
		    		if (security.equalsIgnoreCase("Y")){
		    			break;
		    		}
				}
		    }
		}
		 
		
		
		 
		
		return security;
	}

	
	
	public boolean updateUserLastLoginDate(User user){
		boolean status = true;
		
		try{
			entityManager.merge(user);
		}catch(Exception ex){
			status = false;
			logger.error("unable to update last login date for the user with id: "+user.getUserId(), ex);
			// failure to update last login should not stop the transaction
			// but return status to calling method for propagation 
		}
		
		return status;
	}
	
	
	 


	
}
