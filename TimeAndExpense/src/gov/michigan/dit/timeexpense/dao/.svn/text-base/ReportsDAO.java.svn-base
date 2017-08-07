package gov.michigan.dit.timeexpense.dao;

import java.util.List;

import gov.michigan.dit.timeexpense.model.core.WebReportParams;
import gov.michigan.dit.timeexpense.model.display.Ag1Bean;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ReportsDAO extends AbstractDAO {

	public ReportsDAO(EntityManager em) {
		super(em);
	}

	public ReportsDAO() {

	}

	@SuppressWarnings("unchecked")
	public boolean saveReportParams(WebReportParams params) {
		entityManager.persist(params);
		return true;
	}
	
	public List<WebReportParams> getCompletedReports(String userId) {
		
		String finderQuery = "SELECT * FROM WEB_REPORT_PARAMS WHERE USER_ID = ?1";

	Query query = entityManager.createNativeQuery(finderQuery, WebReportParams.class);
	query.setParameter(1, userId);

	List<WebReportParams> reportList = query.getResultList();
		return reportList;
	}

}
