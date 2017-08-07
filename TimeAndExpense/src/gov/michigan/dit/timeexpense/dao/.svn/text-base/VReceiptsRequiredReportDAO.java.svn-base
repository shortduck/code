package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.core.VReceiptsRequiredReport;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.beans.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VReceiptsRequiredReportDAO extends AbstractDAO {

	public VReceiptsRequiredReportDAO(EntityManager em) {
		super(em);
	}

	String MAIN_SQL = " Select NAME,  EMP_IDENTIFIER, EXPEV_IDENTIFIER, EXP_DATE_FROM,"
			+ " EXP_DATE_TO,   NATURE_OF_BUSINESS,    STATUS,    DEPARTMENT,"
			+ " DEPT_NAME,    AGENCY, AGY_NAME,    TKU, TKU_NAME, SUPERVISOR ,SUP_NAME,"
			+ " PAYDATE, TRAVEL_TYPE, PRE_AUDIT_REQ, SUM_DOLLAR_AMOUNT, SUPEREMPID, GROUPFIELD,"
			+ " TRAVEL_IND, OUT_OF_STATE_IND, EXPENSE_TYPE, dbms_random.random as RandomNumber from V_Receipts_Required_Report where 1=1 ";
	String sub_clause_Dept = " and department =?";
	String agency = " and agency=?";
	String tku = " and tku=?";
	String amtLess = "  and SUM_DOLLAR_AMOUNT <=?";
	String amtGrt = " and SUM_DOLLAR_AMOUNT >= ? ";
	String subEmp = " and emp_identifier= ?";
	String payDate = " and paydate>=? and paydate<=? ";
	String auditSw = " and pre_audit_req= ? ";
	String payDTFrom = " and PAYDATE >=? ";
	String payDTTo = " and PAYDATE <=? ";
	String expDTF = " and EXP_DATE_FROM >=? ";
	String expDTTO = " and EXP_DATE_TO <=? ";
	String traveInd = " and TRAVEL_IND = ?  ";
	String outOfStat = "  and OUT_OF_STATE_IND = ? ";
	String expenseType = " and EXPENSE_TYPE = ?";
	String supervisor = " and SUPEREMPID = ?";

	public VReceiptsRequiredReportDAO() {

	}

	@SuppressWarnings("unchecked")
	public List<VReceiptsRequiredReport> getReceiptsRequiredReport(VReceiptsRequiredReport rpt) {

		StringBuilder str = new StringBuilder();
		str.append(MAIN_SQL);
		List<VReceiptsRequiredReport> returnReportList = null;
		List<Object> parameters = new ArrayList<Object>();
		try {
			if (rpt.getDepartment() != null && rpt.getDepartment().length() > 0) {
				str.append(sub_clause_Dept);
				parameters.add(rpt.getDepartment());				
			}
			if (rpt.getAgency() != null && rpt.getAgency().length() > 0) {
				str.append(agency);
				parameters.add(rpt.getAgency());
			}
			if (rpt.getTku() != null && rpt.getTku().length() > 0) {
				str.append(tku);
				parameters.add(rpt.getTku());
			}
			if (rpt.getAmtGreater() != null) {
				str.append(amtGrt);
				parameters.add(rpt.getAmtGreater().doubleValue());
			}
			if (rpt.getAmtLesser() != null) {
				str.append(amtLess);
				parameters.add(rpt.getAmtLesser().doubleValue());
			}
			if (rpt.getEmpIdentifier() != null) {
				str.append(subEmp);
				parameters.add(rpt.getEmpIdentifier());
			}
			if (rpt.getExpDateFrom() != null) {
				str.append(expDTF);
				parameters.add(rpt.getExpDateFrom());
			}
			if (rpt.getExpDateTo() != null) {
				str.append(expDTTO);
				parameters.add(rpt.getExpDateTo());
			}

			if (rpt.getPayAmtFrm() != null) {
				str.append(payDTFrom);
				parameters.add(rpt.getPayAmtFrm());
			}

			if (rpt.getPayAmtTo() != null) {
				str.append(payDTTo);
				parameters.add(rpt.getPayAmtTo());
			}
			if (rpt.getTravelInd() != null && !rpt.getTravelInd().equals("")) {
				str.append(traveInd);
				parameters.add(rpt.getTravelInd());
			}

			if (rpt.getOutOfStateInd() != null && !rpt.getOutOfStateInd().equals("")) {
				str.append(outOfStat);
				parameters.add(rpt.getOutOfStateInd());
			}
			if (rpt.getExpenseType() != null && !rpt.getExpenseType().equals("")) {
				str.append(expenseType);
				parameters.add(rpt.getExpenseType());
			}
			if (rpt.getSuperempid() != null && rpt.getSuperempid() > 0) {
				str.append(supervisor);
				parameters.add(rpt.getSuperempid());
			}
			str.append(" order by RandomNumber");
			StringBuilder countSql = new StringBuilder(str);
			countSql.insert(0, "select count(name || emp_identifier || expev_identifier) from (");
			countSql.append(')');
			Query query = entityManager.createNativeQuery(countSql.toString(), Integer.class);
			int i = 1;
			for (Object object : parameters) {
				query = query.setParameter(i++, object);
			}
			Integer singleResult = (Integer) query.getSingleResult();
			
			str.insert(0, "select distinct NAME,  EMP_IDENTIFIER, EXPEV_IDENTIFIER, EXP_DATE_FROM,"
			+ " EXP_DATE_TO,   NATURE_OF_BUSINESS,    STATUS,    DEPARTMENT,"
			+ " DEPT_NAME,    AGENCY, AGY_NAME,    TKU, TKU_NAME, SUPERVISOR ,SUP_NAME,"
			+ " PAYDATE, TRAVEL_TYPE, PRE_AUDIT_REQ, SUM_DOLLAR_AMOUNT, SUPEREMPID, GROUPFIELD,"
			+ " TRAVEL_IND, OUT_OF_STATE_IND, EXPENSE_TYPE from (");
			str.append(") where rownum < ? order by name, expev_identifier");
			
			query = entityManager.createNativeQuery(str.toString(), VReceiptsRequiredReport.class);
			i = 1;
			for (Object object : parameters) {
				query = query.setParameter(i++, object);
			}
			query = query.setParameter(i, singleResult * (rpt.getRandPer() / 100));
			returnReportList = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnReportList;

	}

}
