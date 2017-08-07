package gov.michigan.dit.timeexpense.dao;
/**
 * This class lists all the finders for the System Code
 * Operations
 * @author KawS1
 */
import gov.michigan.dit.timeexpense.model.core.SystemCodes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@SuppressWarnings("unchecked")
public class SystemCodeDAO extends AbstractDAO {

	public SystemCodeDAO(EntityManager em) {
		super(em);
	}

	public SystemCodeDAO() {

	}

	/**
	 * This method gets the list of all System Codes	 
	 * @return List<SystemCodes>
	 */
	public List<SystemCodes> findAllSysCodes() {
		List<SystemCodes> sysCodeList = null;
  
		String finderQuery = " select s from SystemCodes s   order by s.systemCodesPK.systemCode asc";
		try {
			Query query = entityManager.createQuery(finderQuery);
			sysCodeList = query.getResultList();
		} catch (Exception e) {
			System.out.println("\n the exception is " + e.getMessage());
		}
		return sysCodeList; 
	}

 
	/**
	 * This method is for deleted a system Code
	 * The selected System code is passed as the parameter 
	 * @param sysObj
	 * @return true / false
	 */

	public boolean deleteSystemCode(SystemCodes sysObj) {

		boolean isDeleted = true;
		entityManager.remove(sysObj);
		entityManager.flush();
		return isDeleted;
	}
/**
 * This method updates a system code 
 * @param sysObj
 * @return SystemCodes
 */
	public SystemCodes updateSysCdData(SystemCodes sysObj) {
		SystemCodes newSystemCodes = new SystemCodes();
		if (sysObj.getSystemCodesPK().getSystemCode() == null) {
			entityManager.persist(sysObj);
			newSystemCodes = sysObj;
		} else {
			entityManager.merge(sysObj);
		}
		return sysObj;
	}
/**
 * This method is for creating a new System Code
 * @param sysObj
 * @return SystemCodes
 */
	public SystemCodes createSysCdData(SystemCodes sysObj) {
		SystemCodes newSystemCodes = new SystemCodes();

		entityManager.persist(sysObj);
		entityManager.flush();
		newSystemCodes = sysObj;

		return sysObj;
	}
/**
 * This method finds the list list of similar codes
 * @param codeVal
 * @return List<SystemCodes>
 */
	public List<SystemCodes> findSelectedCode(String codeVal) {
		// List<SystemCodes> newSystemCodes = new List<SystemCodes>;
		String finderQuery = "select s from SystemCodes s where s.systemCodesPK.systemCode like'"
				+ codeVal + "%'";
		SystemCodes newSelectCode = new SystemCodes();

		Query query = entityManager.createQuery(finderQuery);
		List<SystemCodes> newSystemCodes = (List<SystemCodes>) query
				.getResultList();
		return newSystemCodes;
	}
 
}
