package gov.michigan.dit.timeexpense.service;


import javax.persistence.EntityManager;

import gov.michigan.dit.timeexpense.dao.PersonnelHistoriesDAO;
import gov.michigan.dit.timeexpense.exception.MultipleUsersException;
import gov.michigan.dit.timeexpense.model.core.PersonnelHistories;

public class PersonnelHistoriesDSP {
	
	private PersonnelHistoriesDAO dao;
	
	public PersonnelHistoriesDSP() {
		dao = new PersonnelHistoriesDAO();
	}

	public PersonnelHistoriesDSP(EntityManager em) {
		dao = new PersonnelHistoriesDAO(em);
	}

	public String getEmployeeNameForUserId(String userId) throws MultipleUsersException {
		PersonnelHistories ph = dao.findPersonnelHistoriesByUserId(userId);
		if (ph != null) {
			return ph.getLastName().trim() + ", " + ph.getFirstName().trim() + " " + ph.getMiddleName();
		}
		return null;
	}

}
