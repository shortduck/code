package gov.michigan.dit.timeexpense.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import gov.michigan.dit.timeexpense.exception.MultipleUsersException;
import gov.michigan.dit.timeexpense.model.core.PersonnelHistories;
import gov.michigan.dit.timeexpense.model.core.User;

public class PersonnelHistoriesDAO extends AbstractDAO {
	
	private SecurityDAO securityDao;
	
	public PersonnelHistoriesDAO() {
		super();
		securityDao = new SecurityDAO();
	}

	public PersonnelHistoriesDAO(EntityManager em) {
		super(em);
		securityDao = new SecurityDAO(em);
	}

	public PersonnelHistories findPersonnelHistoriesByUserId(String userId) throws MultipleUsersException {
		User user = securityDao.findUserByUserId(userId);
		if (user != null) {
			List<PersonnelHistories> histories = entityManager.createQuery("select p from PersonnelHistories p where p.empIdentifier = :empId")
							.setParameter("empId", user.getEmpIdentifier()).getResultList();
			if (histories != null && histories.size() > 0) {
				return histories.get(0);
			}
		}
		
		return null;
	}
}
