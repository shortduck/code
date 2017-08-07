package gov.michigan.dit.timeexpense.service;

import gov.michigan.dit.timeexpense.dao.ReportsDAO;
import gov.michigan.dit.timeexpense.model.core.WebReportParams;

import java.util.List;


import javax.persistence.EntityManager;

public class ReportsDSP {
	
	ReportsDAO reportsDao;
	
	public ReportsDSP(EntityManager em) {
		reportsDao = new ReportsDAO(em);
	}
	
	public boolean saveReportParams(WebReportParams params) throws Exception{

		return reportsDao.saveReportParams(params);
		


	}
	
	public List<WebReportParams> getCompletegReports(String userId) throws Exception{

		return reportsDao.getCompletedReports(userId);
		


	}
}
