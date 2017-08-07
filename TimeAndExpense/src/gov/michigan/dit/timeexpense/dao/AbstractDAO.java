package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.db.ErrorMessages;

import javax.persistence.EntityManager;

/**
 * Base class for all data access classes providing convenient 
 * access to JPA <code>EntityManager</code>. All data access 
 * classes must extend from this class and use EntityManager
 * provided in this class for all JPA operations.
 * 
 * @author chaudharym
 */
public class AbstractDAO {

	protected EntityManager entityManager;

	public AbstractDAO() {}
	
	public AbstractDAO(EntityManager em) {
		entityManager = em;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Finds an error text for the provided error code.
	 * 
	 * @param error code
	 * @return String representing error text.
	 */
	public String findErrorTextByCode(String errorCode){
		String result="";
		
		ErrorMessages obj = entityManager.find(ErrorMessages.class, errorCode);
		if(obj != null && obj.getErrorTitle() != null){
			result = obj.getErrorText();
		}
		
		return result;	
	}
}

