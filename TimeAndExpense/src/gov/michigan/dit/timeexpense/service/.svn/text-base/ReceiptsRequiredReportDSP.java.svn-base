package gov.michigan.dit.timeexpense.service;

import java.util.List;

import gov.michigan.dit.timeexpense.dao.VReceiptsRequiredReportDAO;
import gov.michigan.dit.timeexpense.model.core.VReceiptsRequiredReport;

import javax.persistence.EntityManager;

public class ReceiptsRequiredReportDSP {
	
	private EntityManager entityManager;
	private VReceiptsRequiredReportDAO dao;
	
	public ReceiptsRequiredReportDSP() {
		this.dao = new VReceiptsRequiredReportDAO();
	}

	public ReceiptsRequiredReportDSP(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.dao = new VReceiptsRequiredReportDAO(entityManager);
	}

	public List<VReceiptsRequiredReport> getReportReceiptsRequired(VReceiptsRequiredReport repObj) {
		return dao.getReceiptsRequiredReport(repObj);
		
	}
	
	
	
}
