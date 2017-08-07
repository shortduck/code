package gov.michigan.dit.timeexpense.service;

import gov.michigan.dit.timeexpense.dao.SecurityDAO;
import gov.michigan.dit.timeexpense.exception.TimeAndExpenseException;
import gov.michigan.dit.timeexpense.model.core.Security;
import gov.michigan.dit.timeexpense.model.core.User;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * SecurityManager provides authorization services to the application. It is
 * responsible for authorizing requests to the application modules based on
 * user's configured security settings. For a given user only those security 
 * modules are fetched that are valid on the date of query.
 * 
 * This class also provides facility for querying configured security module's
 * access. 
 * 
 * @author chaudharym
 * 
 * 
 */
public class SecurityManager {
	
	private static Logger logger = Logger.getLogger(SecurityManager.class);
	
	private SecurityDAO securityDao;

	public SecurityManager(EntityManager em){
		securityDao = new SecurityDAO(em);
	}
	
	/**
	 * Finds DSDS user for the given employee Id. If no user found for
	 * the given employee Id, then returns NULL. A valid user profile is
	 * constructed only if:
	 * 1) User has been granted access to the application
	 * 2) User's access has not expired.
	 * 
	 * If any of the above if true, an exception with appropriate error code
	 * and message is thrown back.
	 * 
	 * If all permissions are in place and are valid, a <code>UserProfile</code>
	 * is created and returned. The last login date is also updated for the user
	 * in the database.
	 * 
	 * @param employee Id
	 * @return DCDS user - If valid user found
	 * @return NULL - If no user found
	 * @throws TimeAndExpenseException 
	 * 			- If more than 1 DCDS userIds found for the employee 
	 * 				with the given employee Id.
	 * 			- If NO access to the application defined for user.
	 * 			- If application access expired for the given user.
	 * Changes Req 49 By Smriti Kaw
	 * Non Employees can also access  Time And Expense 
	 */
	public UserProfile getDcdsUser(int empId, boolean isSelfServiceUser) throws TimeAndExpenseException {
		UserProfile result = null;
		
		User user = securityDao.findUserByEmpId(empId);
		
		if(user != null){		 
			assertApplicationAccess(user);	
			result = populateUserProfile(user, isSelfServiceUser);			
			//update the last login date for the user
			updateLastLoginDate(user);
		} 
		
		return result;
	}

	/**
	 * Checks application access to assert
	 * 1) User has been given access to the application
	 * 2) The granted access is not expired
	 * 
	 * If any of the above fails, a <code>TimeAndExpenseException</code>
	 * is thrown with appropriate error code and description.
	 */
	public void assertApplicationAccess(User user) throws TimeAndExpenseException{
		int result = checkApplicationAccess(user);
		
		if(result == -1){
			throw new TimeAndExpenseException(IConstants.APP_ACCESS_DENIED_ERRCODE, 
					securityDao.findErrorTextByCode(IConstants.APP_ACCESS_DENIED_ERRCODE));
		}else if(result == -2){
			throw new TimeAndExpenseException(IConstants.APP_ACCESS_EXPIRED_ERRCODE,
					securityDao.findErrorTextByCode(IConstants.APP_ACCESS_EXPIRED_ERRCODE));
		}else{
			return;
		}
	}

	private void updateLastLoginDate(User userInfo) {
		userInfo.setLastLogin(new Date());
		userInfo.setModifiedUserId(userInfo.getUserId());
		boolean status = securityDao.updateUserLastLoginDate(userInfo);
		if(!status){
			logger.error(IConstants.USER_LAST_LOGIN_UPDATE_FAILED + userInfo.getUserId());
		}
	}

	/**
	 * Checks whether the user trying to login has access to the application.
	 * 
	 * @return  1  - If access granted and not expired
	 * 			-1 - If NO access granted
	 * 			-2 - If access granted but expired
	 */
	public int checkApplicationAccess(User user) {
		// by default, no application access
		int result = -1;
		
		for(Security sec : user.getSecurity()){
			if(IConstants.APP_MODULE_ID.equals(sec.getModuleId().toUpperCase())){
				Date now = new Date();
				// expired access
				if(now.before(sec.getStartDate()) || now.after(sec.getEndDate())){
					result = -2;
				
				// valid access	
				}else{
					result = 1;
				}
			break;
			}
		}
		return result;
	}

	/**
	 * Checks whether the logged in user has access to the given module Id.
	 * It checks for module accessibility irrespective of the scope and the
	 * modification privilege. 
	 * 
	 * @param module Id
	 * @return TRUE  - If user has access to the module.
	 * 		   FALSE - If user doesn't have access to the module.
	 */
	public boolean checkModuleAccess(UserProfile user, String moduleId) {
		return user.getModules().contains(moduleId.toUpperCase());
	}
		
	/**
	 * Checks whether the logged in user has access to the given module Id 
	 * with the specified security scope.

	 * @param moduleId  
	 * @param dept - The department for which the module access is to be found.
	 * @param agency - The agency for which the module access is to be found.
	 * @param tku - The tku for which the module access is to be found.
	 * @return TRUE - If access granted.
	 * 			FALSE - If no access granted.
	 */
	public boolean checkModuleAccess(UserProfile user, String moduleId, String dept, String agency, String tku) {
		if(user == null) return false;
		String security = securityDao.findSecurityScope(user.getUserId(), moduleId, dept, agency, tku);
		return (security != null) ? security.equalsIgnoreCase("Y") : false;
	}
	/**
	 *  Checks whether the logged in user has access to the given module Id 
	 * with the specified security scope. 
	 * @param userId
	 * @param moduleId
	 * @return TRUE - If access granted.
	 * 			FALSE - If no access granted.
	 */
	public boolean checkAccess(String userId,String moduleId) {
	 
		String security = securityDao.findSecurityScope(userId, moduleId);
		return (security != null) ? security.equalsIgnoreCase("Y") : false;
	}
	
	
	/**
	 * Checks whether the logged in user has access to the given module Id 
	 * with the specified security scope.
	 *
	 * @param moduleId  
	 * @param dept - The department for which the module access is to be found.
	 * @param agency - The agency for which the module access is to be found.
	 * @param tku - The tku for which the module access is to be found.
	 * @return 0 - If no security
	 * 		   1 - if inquiry only access
	 *		   2 - If update access
	 *
	 * ZH - 05/15/2009: Updated to make use of enhanced scope checks
	 */
	
	public int getModuleAccessMode (UserProfile user, String moduleId, String dept, String agency, String tku) {
		if(user == null) 
			return IConstants.SECURITY_NO_MODULE_ACCESS;
		String security = securityDao.findSecurityScope(user.getUserId(), moduleId, dept, agency, tku);
		if (security == null || security.equals(""))
			return IConstants.SECURITY_NO_MODULE_ACCESS;
		if (security.equalsIgnoreCase("N"))
			return IConstants.SECURITY_INQUIRY_MODULE_ACCESS;
		if (security.equalsIgnoreCase("Y"))
			return IConstants.SECURITY_UPDATE_MODULE_ACCESS;
		
		//default return
		return IConstants.SECURITY_NO_MODULE_ACCESS;
	}

	private UserProfile populateUserProfile(User user, boolean isSelfServiceUser) {
		UserProfile userProfile = new UserProfile(user.getUserId(), user.getEmpIdentifier());

		Set<String> modules = new HashSet<String>();
		Date now = new Date();

		// get only those security modules that are valid as of today 
		for(Security sec : user.getSecurity()){
			if(!now.before(sec.getStartDate()) &&  !now.after(sec.getEndDate())) {
				modules.add(sec.getModuleId().trim().toUpperCase());
			}
		}
		
		//Add Superuser Module if the user is coming from SelfService
		if (isSelfServiceUser && !modules.contains(IConstants.SUPER_USER.trim().toUpperCase()))
			modules.add(IConstants.SUPER_USER.trim().toUpperCase());
		
		userProfile.setModules(Collections.unmodifiableSet(modules));
		return userProfile;
	}
	
	public int isPDFModuleAvailable (UserProfile user, String moduleId, String dept, String agency, String tku) {
		if(user == null) 
			return IConstants.SECURITY_NO_MODULE_ACCESS;
		String security = securityDao.isPDFModuleAvailable(user.getUserId(), moduleId, dept, agency, tku);
		if (security == null || security.equals(""))
			return IConstants.SECURITY_NO_MODULE_ACCESS;
		if (security.equalsIgnoreCase("N"))
			return IConstants.SECURITY_INQUIRY_MODULE_ACCESS;
		if (security.equalsIgnoreCase("Y"))
			return IConstants.SECURITY_UPDATE_MODULE_ACCESS;
		
		//default return
		return IConstants.SECURITY_NO_MODULE_ACCESS;
	}
	
}
