package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.core.AdvanceDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.CodingBlockElement;
import gov.michigan.dit.timeexpense.model.core.DefaultDistributionsAdvAgy;
import gov.michigan.dit.timeexpense.model.core.DriverReimbExpTypeCbs;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.TkuoptTaOptions;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetailCodingBlock;
import gov.michigan.dit.timeexpense.model.display.Ag1Bean;
import gov.michigan.dit.timeexpense.model.display.Ag2Bean;
import gov.michigan.dit.timeexpense.model.display.Ag3Bean;
import gov.michigan.dit.timeexpense.model.display.GrantBean;
import gov.michigan.dit.timeexpense.model.display.GrantPhaseBean;
import gov.michigan.dit.timeexpense.model.display.IndexCodesBean;
import gov.michigan.dit.timeexpense.model.display.MultiBean;
import gov.michigan.dit.timeexpense.model.display.PcaBean;
import gov.michigan.dit.timeexpense.model.display.ProjectBean;
import gov.michigan.dit.timeexpense.model.display.ProjectPhaseBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class CodingBlockDAO extends AbstractDAO {

	private static ExpenseDAO expenseDAO = new ExpenseDAO();
	private static final Logger logger = Logger.getLogger(CodingBlockDAO.class);

	public CodingBlockDAO() {
		
	}
	
	public CodingBlockDAO(EntityManager em) {
		super(em);
	}
	 
	/***
	 * This method performs the CodingBlock validation .. 
	 * builds the input parameters and invokes the F_CB_VALIDATE Oracle function
	 * @param expenseDetailCodingBlock
	 * @param cbElement
	 * @return String
	 */

	public String validateExpenseCodingBlocks(ExpenseDetailCodingBlocks expenseDetailCodingBlock,
			CodingBlockElement cbElement) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : validateExpenseCodingBlocks(ExpenseDetailCodingBlocks expenseDetailCodingBlock,CodingBlockElement cbElement) : return String");
		
		String finderQuery = "SELECT F_CB_VALIDATE ( ?1, ?2, ?3, ?4, ?5, ?6, ?7 , ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19) FROM DUAL";
		Query query = entityManager.createNativeQuery(finderQuery);
		/*
		 * add checks for if coding block element is null and set query
		 * parameter to "" if it is
		 */
			query.setParameter(1, expenseDetailCodingBlock.getFacsAgy() == null ? "":expenseDetailCodingBlock.getFacsAgy());
			query.setParameter(2, expenseDetailCodingBlock.getAppropriationYear()==null ? "" : expenseDetailCodingBlock.getAppropriationYear());
			query.setParameter(3, expenseDetailCodingBlock.getIndexCode()==null ? "" : expenseDetailCodingBlock.getIndexCode());
			query.setParameter(4, expenseDetailCodingBlock.getPca()==null ? "" : expenseDetailCodingBlock.getPca());
			query.setParameter(5, expenseDetailCodingBlock.getGrantNumber()==null ? "" : expenseDetailCodingBlock.getGrantNumber());
			query.setParameter(6, expenseDetailCodingBlock.getGrantPhase()==null ? "" : expenseDetailCodingBlock.getGrantPhase());
			query.setParameter(7, expenseDetailCodingBlock.getAgencyCode1()==null ? "" : expenseDetailCodingBlock.getAgencyCode1());
			query.setParameter(8, expenseDetailCodingBlock.getProjectNumber()==null ? "" : expenseDetailCodingBlock.getProjectNumber());			
			query.setParameter(9, expenseDetailCodingBlock.getProjectPhase()==null ? "" : expenseDetailCodingBlock.getProjectPhase());
			query.setParameter(10, expenseDetailCodingBlock.getAgencyCode2()==null ? "" : expenseDetailCodingBlock.getAgencyCode2());
			query.setParameter(11, expenseDetailCodingBlock.getAgencyCode3()==null ? "" : expenseDetailCodingBlock.getAgencyCode3());
			query.setParameter(12, expenseDetailCodingBlock.getMultipurposeCode()==null ? "" : expenseDetailCodingBlock.getMultipurposeCode());	
			
			// Set TA_INDICATOR and CB_SOURCE to "1" as F_CB_VALIDATE does not use it 
			query.setParameter(13, "1");
			query.setParameter(14, "1");
			query.setParameter(15, cbElement.getDeptCode());
			query.setParameter(16, cbElement.getAgency());
			query.setParameter(17, cbElement.getTku());
			query.setParameter(18, "1");

			String payDate = formatDate(cbElement.getPayDate());
			query.setParameter(19, payDate);

			String returnCode = (String) query.getSingleResult();
			
			if(logger.isDebugEnabled())
				logger.debug("Exit method : validateExpenseCodingBlocks(ExpenseDetailCodingBlocks expenseDetailCodingBlock,CodingBlockElement cbElement)");

			return returnCode;
	}
	
	public String validateTravelRequisitionCodingBlocks(TravelReqDetailCodingBlock treqDetailCodingBlock,
			CodingBlockElement cbElement) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : validateExpenseCodingBlocks(ExpenseDetailCodingBlocks expenseDetailCodingBlock,CodingBlockElement cbElement) : return String");
		
		String finderQuery = "SELECT F_CB_VALIDATE ( ?1, ?2, ?3, ?4, ?5, ?6, ?7 , ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19) FROM DUAL";
		Query query = entityManager.createNativeQuery(finderQuery);
		/*
		 * add checks for if coding block element is null and set query
		 * parameter to "" if it is
		 */
			query.setParameter(1, treqDetailCodingBlock.getFacsAgy() == null ? "":treqDetailCodingBlock.getFacsAgy());
			query.setParameter(2, treqDetailCodingBlock.getAppropriationYear()==null ? "" : treqDetailCodingBlock.getAppropriationYear());
			query.setParameter(3, treqDetailCodingBlock.getIndexCode()==null ? "" : treqDetailCodingBlock.getIndexCode());
			query.setParameter(4, treqDetailCodingBlock.getPca()==null ? "" : treqDetailCodingBlock.getPca());
			query.setParameter(5, treqDetailCodingBlock.getGrantNumber()==null ? "" : treqDetailCodingBlock.getGrantNumber());
			query.setParameter(6, treqDetailCodingBlock.getGrantPhase()==null ? "" : treqDetailCodingBlock.getGrantPhase());
			query.setParameter(7, treqDetailCodingBlock.getAgencyCode1()==null ? "" : treqDetailCodingBlock.getAgencyCode1());
			query.setParameter(8, treqDetailCodingBlock.getProjectNumber()==null ? "" : treqDetailCodingBlock.getProjectNumber());			
			query.setParameter(9, treqDetailCodingBlock.getProjectPhase()==null ? "" : treqDetailCodingBlock.getProjectPhase());
			query.setParameter(10, treqDetailCodingBlock.getAgencyCode2()==null ? "" : treqDetailCodingBlock.getAgencyCode2());
			query.setParameter(11, treqDetailCodingBlock.getAgencyCode3()==null ? "" : treqDetailCodingBlock.getAgencyCode3());
			query.setParameter(12, treqDetailCodingBlock.getMultipurposeCode()==null ? "" : treqDetailCodingBlock.getMultipurposeCode());	
			
			// Set TA_INDICATOR and CB_SOURCE to "1" as F_CB_VALIDATE does not use it 
			query.setParameter(13, "1");
			query.setParameter(14, "1");
			query.setParameter(15, cbElement.getDeptCode());
			query.setParameter(16, cbElement.getAgency());
			query.setParameter(17, cbElement.getTku());
			query.setParameter(18, "1");

			String payDate = formatDate(cbElement.getPayDate());
			query.setParameter(19, payDate);

			String returnCode = (String) query.getSingleResult();
			
			if(logger.isDebugEnabled())
				logger.debug("Exit method : validateExpenseCodingBlocks(ExpenseDetailCodingBlocks expenseDetailCodingBlock,CodingBlockElement cbElement)");

			return returnCode;
	}
	
	/****
	 * This method performs the Advance Coding block validation
	 * @param advanceDetailCodingBlock
	 * @param cbElement
	 * @return
	 */

	public String validateAdvanceCodingBlocks(
			AdvanceDetailCodingBlocks advanceDetailCodingBlock,
			CodingBlockElement cbElement) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : validateAdvanceCodingBlocks(AdvanceDetailCodingBlocks expenseDetailCodingBlock,CodingBlockElement cbElement) : return String");
		
		String finderQuery = "SELECT F_CB_VALIDATE ( ?1, ?2, ?3, ?4, ?5, ?6, ?7 , ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19) FROM DUAL";
		Query query = entityManager.createNativeQuery(finderQuery);

		query.setParameter(1, advanceDetailCodingBlock.getFacsAgy()==null? "" : advanceDetailCodingBlock.getFacsAgy());
		query.setParameter(2, advanceDetailCodingBlock.getAppropriationYear()==null? "" : advanceDetailCodingBlock.getAppropriationYear());
		query.setParameter(3, advanceDetailCodingBlock.getIndexCode()==null? "" : advanceDetailCodingBlock.getIndexCode());
		query.setParameter(4, advanceDetailCodingBlock.getPca()==null? "" : advanceDetailCodingBlock.getPca());
		query.setParameter(5, advanceDetailCodingBlock.getGrantNumber()==null? "" : advanceDetailCodingBlock.getGrantNumber());
		query.setParameter(6, advanceDetailCodingBlock.getGrantPhase()==null? "" : advanceDetailCodingBlock.getGrantPhase());
		query.setParameter(7, advanceDetailCodingBlock.getAgencyCode1()==null? "" : advanceDetailCodingBlock.getAgencyCode1());
		query.setParameter(8, advanceDetailCodingBlock.getProjectNumber()==null? "" : advanceDetailCodingBlock.getProjectNumber());
		query.setParameter(9, advanceDetailCodingBlock.getProjectPhase()==null? "" : advanceDetailCodingBlock.getProjectPhase());
		query.setParameter(10, advanceDetailCodingBlock.getAgencyCode2()==null? "" : advanceDetailCodingBlock.getAgencyCode2());
		query.setParameter(11, advanceDetailCodingBlock.getAgencyCode3()==null? "" : advanceDetailCodingBlock.getAgencyCode3());
		query.setParameter(12, advanceDetailCodingBlock.getMultipurposeCode()==null? "" : advanceDetailCodingBlock.getMultipurposeCode());

		// Set TA_INDICATOR and CB_SOURCE to "1" as F_CB_VALIDATE does not use it 
		query.setParameter(13, "1");
		query.setParameter(14, "1");

		query.setParameter(15, cbElement.getDeptCode());
		query.setParameter(16, cbElement.getAgency());
		query.setParameter(17, cbElement.getTku());
		query.setParameter(18, "1");

		String payDate = formatDate(cbElement.getPayDate());
		query.setParameter(19, payDate);

		String returnCode = (String) query.getSingleResult();
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : validateAdvanceCodingBlocks(AdvanceDetailCodingBlocks expenseDetailCodingBlock,CodingBlockElement cbElement)");

		return returnCode;
	}
	
	/**
	 * This method formats the date that is passed as a parameter to the F_CB_VALIDATE Oracle function 
	 * @param date
	 * @return String
	 */

	public String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
		String dateStr = sdf.format(date);
		
		return dateStr;
	}
	
	/****
	 * This method retrieves all coding blocks that are associated to an Expense line Item.
	 * @param expenseLineItem
	 * @return List<ExpenseDetailCodingBlocks>
	 */

	@SuppressWarnings("unchecked")
	public List<ExpenseDetailCodingBlocks> findExpenseDetailCodingBlocks(
			ExpenseDetails expenseLineItem) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method :: findExpenseDetailCodingBlocks(ExpenseDetails expenseLineItem) : return List<ExpenseDetailCodingBlocks>");
		
		String finderQuery = "SELECT ec from ExpenseDetailCodingBlocks ec where ec.expdIdentifier=:expdIdentifier";
		List<ExpenseDetailCodingBlocks> expenseCodingBlockList = (List<ExpenseDetailCodingBlocks>) entityManager
				.createQuery(finderQuery).setParameter("expdIdentifier",
						expenseLineItem).getResultList();
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method :: findExpenseDetailCodingBlocks(ExpenseDetails expenseLineItem)");
		
		return expenseCodingBlockList;
	}

	/***
	 * This method retrieves the CodingBlock metadata from the TkuoptTaOptions entity that determines which CodingBlock elements to display 
	 * @param dept
	 * @param agy
	 * @param tku
	 * @return
	 */
	
	public TkuoptTaOptions findCBMetaData(String dept, String agy, String tku) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method :: findCBMetaData(String dept, String agy, String tku) : return TkuoptTaOptions");
		
		TkuoptTaOptions tkuoptTaOptions = null;
		String findquery = "SELECT t FROM TkuoptTaOptions t where t.tkuoptTaOptionsPK.department=:department " +
				"and t.tkuoptTaOptionsPK.agency=:agency and t.tkuoptTaOptionsPK.tku=:tku";

		Query query = entityManager.createQuery(findquery)
		.setParameter("department", dept)
		.setParameter("agency", agy)
		.setParameter("tku", tku);

		try {
			tkuoptTaOptions = (TkuoptTaOptions) query.getSingleResult();
		} catch (Exception e) {
			// When no data found for the selected TKU.. Set the TKU parameter to AL 
			query.setParameter("tku", "AL");
			tkuoptTaOptions = (TkuoptTaOptions) query.getSingleResult();
		}
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method :: findCBMetaData(String dept, String agy, String tku)");

		return tkuoptTaOptions;
	}
	
	/***
	 * This method retrieves the Index for the selected Appropriation year, Department, Agency, TKU, paydate
	 * @param cbElementBean
	 * @return List<IndexCodesBean>
	 */

	public List<IndexCodesBean> findAllIndexes(CodingBlockElement cbElementBean) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method :: findAllIndexes(CodingBlockElement cbElementBean) : return List<IndexCodesBean>");
		
		String finderQuery = "SELECT GROUP_EXPENSE_CB_TKUS.GECB_IDENTIFIER ID,GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY CODE,INDEX_CODES.NAME NAME, "
				+ "INDEX_CODES.APPROPRIATION_YEAR APPR_YEAR,INDEX_CODES.FACS_AGY AGENCY, (GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY || '   ' || INDEX_CODES.NAME) DISPLAY"
				+ " FROM GROUP_EXPENSE_CB_TKUS, INDEX_CODES WHERE "
				+ "INDEX_CODES.INDEX_CODE=GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY AND "
				+ "INDEX_CODES.APPROPRIATION_YEAR = GROUP_EXPENSE_CB_TKUS.APPROPRIATION_YEAR AND "
				+ "INDEX_CODES.FACS_AGY = GROUP_EXPENSE_CB_TKUS.FACS_AGY AND "
				+ "INDEX_CODES.APPROPRIATION_YEAR = ?1 AND "
				+ "GROUP_EXPENSE_CB_TKUS.ELEMENT_TYPE = ?2 "
				+ "AND GROUP_EXPENSE_CB_TKUS.DEPARTMENT=?3 AND "
				+ "GROUP_EXPENSE_CB_TKUS.AGENCY=?4 AND GROUP_EXPENSE_CB_TKUS.TKU=?5 AND INDEX_CODES.STATUS_CODE='A' "
				+ "AND ?6 BETWEEN INDEX_CODES.START_DATE AND INDEX_CODES.END_DATE";

		Query query = entityManager.createNativeQuery(finderQuery,IndexCodesBean.class);
		query.setParameter(1, cbElementBean.getAppropriationYear());
		query.setParameter(2, cbElementBean.getCbElementType());
		query.setParameter(3, cbElementBean.getDeptCode());
		query.setParameter(4, cbElementBean.getAgency());
		query.setParameter(5, cbElementBean.getTku());
		query.setParameter(6, cbElementBean.getPayDate());

		List<IndexCodesBean> indexList = query.getResultList();
		if(indexList!=null){
			if(indexList.isEmpty()){
				query.setParameter(5, "AL");
				indexList = query.getResultList();
			}
		}
		if(logger.isDebugEnabled())
			logger.debug("Exit method :: findAllIndexes(CodingBlockElement cbElementBean)");
		
		return indexList;
	}
	
	/***
	 * This method retrieves the PCA for the selected Appropriation year, Department, Agency, TKU and paydate
	 * @param cbElementBean
	 * @return List<PcaBean>
	 */

	public List<PcaBean> findAllPCA(CodingBlockElement cbElementBean) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method :: findAllPCA(CodingBlockElement cbElementBean) : return List<PcaBean>");

		String finderQuery = "SELECT GROUP_EXPENSE_CB_TKUS.GECB_IDENTIFIER ID, GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY CODE, PCAS.NAME name,PCAS.APPROPRIATION_YEAR appr_year,PCAS.FACS_AGY agency,(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY || '   ' || PCAS.NAME) DISPLAY"
				+ " FROM GROUP_EXPENSE_CB_TKUS, PCAS "
				+ "WHERE PCAS.PCA=GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY AND "
				+ "PCAS.APPROPRIATION_YEAR = GROUP_EXPENSE_CB_TKUS.APPROPRIATION_YEAR AND "
				+ "PCAS.FACS_AGY=GROUP_EXPENSE_CB_TKUS.FACS_AGY AND "
				+ "PCAS.APPROPRIATION_YEAR = ?1 AND "
				+ "GROUP_EXPENSE_CB_TKUS.ELEMENT_TYPE=?2 AND "
				+ "GROUP_EXPENSE_CB_TKUS.DEPARTMENT=?3 AND "
				+ "GROUP_EXPENSE_CB_TKUS.AGENCY= ?4 AND "
				+ "GROUP_EXPENSE_CB_TKUS.TKU= ?5 AND "
				+ "PCAS.STATUS_CODE='A' AND "
				+ "?6 BETWEEN PCAS.START_DATE AND PCAS.END_DATE";

		Query query = entityManager.createNativeQuery(finderQuery,PcaBean.class);
		query.setParameter(1, cbElementBean.getAppropriationYear());
		query.setParameter(2, cbElementBean.getCbElementType());
		query.setParameter(3, cbElementBean.getDeptCode());
		query.setParameter(4, cbElementBean.getAgency());
		query.setParameter(5, cbElementBean.getTku());
		query.setParameter(6, cbElementBean.getPayDate());

		List<PcaBean> pcaList = query.getResultList();
		if(pcaList!=null){
			if(pcaList.isEmpty()){
				query.setParameter(5, "AL");
				pcaList = query.getResultList();
			}
		}
		if(logger.isDebugEnabled())
			logger.debug("Exit method :: findAllPCA(CodingBlockElement cbElementBean)");
		
		return pcaList;
	}
	
	/****
	 * This method retrieves the Grant for the given Department, Agency, TKU and paydate
	 * @param cbElementBean
	 * @return
	 */

	public List<GrantBean> findAllGrantNo(CodingBlockElement cbElementBean) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findAllGrantNo(CodingBlockElement cbElementBean) : return List<GrantBean>");
		
		String finderQuery = "SELECT DISTINCT SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1,6) CODE, GROUP_EXPENSE_CB_TKUS.GECB_IDENTIFIER ID, V_GRANT_NAMES.NAME NAME,V_GRANT_NAMES.FACS_AGY AGENCY,(SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1,6) || '   ' || V_GRANT_NAMES.NAME) DISPLAY FROM GROUP_EXPENSE_CB_TKUS, V_GRANT_NAMES WHERE "
				+ "V_GRANT_NAMES.GRANT_NUMBER= SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1,6) AND "
				+ "V_GRANT_NAMES.FACS_AGY = GROUP_EXPENSE_CB_TKUS.FACS_AGY AND GROUP_EXPENSE_CB_TKUS.ELEMENT_TYPE=?1 AND "
				+ "GROUP_EXPENSE_CB_TKUS.DEPARTMENT=?2 AND GROUP_EXPENSE_CB_TKUS.AGENCY=?3 AND "
				+ "GROUP_EXPENSE_CB_TKUS.TKU= ?4 AND V_GRANT_NAMES.STATUS_CODE = 'A' AND "
				+ "?5 BETWEEN V_GRANT_NAMES.START_DATE  AND V_GRANT_NAMES.END_DATE";

		Query query = entityManager.createNativeQuery(finderQuery,GrantBean.class);
		query.setParameter(1, cbElementBean.getCbElementType());
		query.setParameter(2, cbElementBean.getDeptCode());
		query.setParameter(3, cbElementBean.getAgency());
		query.setParameter(4, cbElementBean.getTku());
		query.setParameter(5, cbElementBean.getPayDate());

		List<GrantBean> grantList = query.getResultList();
		if(grantList!=null){
			if(grantList.isEmpty()){
				query.setParameter(4, "AL");
				grantList = query.getResultList();
			}
		}	
		if(logger.isDebugEnabled())
			logger.debug("Exit method :: findAllGrantNo(CodingBlockElement cbElementBean)");
		
		return grantList;
	}
	
	/****
	 * This method retrieves the Projects for the selected Department, Agency, TKU and Pay Date
	 * @param cbElementBean
	 * @return List<ProjectBean>
	 * 
	 * ZH, 05/26/2010 - Added order by clause
	 */

	public List<ProjectBean> findAllProjectNo(CodingBlockElement cbElementBean) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findAllProjectNo(CodingBlockElement cbElementBean) : return List<ProjectBean>");
		
		String finderQuery = "SELECT DISTINCT SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1,6) CODE, GROUP_EXPENSE_CB_TKUS.GECB_IDENTIFIER ID, SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,7,2) "
				+ "PROJECT_PHASE, DECODE(PROJECTS.AGENCY_CODE_2,NULL,PROJECTS.NAME) NAME,PROJECTS.FACS_AGY AGENCY,(SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1,6) || '    ' || DECODE(PROJECTS.AGENCY_CODE_2,NULL,PROJECTS.NAME)) DISPLAY FROM GROUP_EXPENSE_CB_TKUS, PROJECTS "
				+ "WHERE PROJECTS.PROJECT_NUMBER=SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1,6) AND "
				+ "PROJECTS.PROJECT_PHASE=SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,7,2) AND "
				+ "PROJECTS.FACS_AGY=GROUP_EXPENSE_CB_TKUS.FACS_AGY AND "
				+ "GROUP_EXPENSE_CB_TKUS.ELEMENT_TYPE=?1 AND "
				+ "GROUP_EXPENSE_CB_TKUS.DEPARTMENT=?2 AND "
				+ "GROUP_EXPENSE_CB_TKUS.AGENCY=?3 AND "
				+ "GROUP_EXPENSE_CB_TKUS.TKU=?4 AND "
				+ "PROJECTS.STATUS_CODE='A' AND "
				+ "?5 BETWEEN PROJECTS.START_DATE AND PROJECTS.END_DATE ORDER BY DISPLAY";

		Query query = entityManager.createNativeQuery(finderQuery,ProjectBean.class);
		query.setParameter(1, cbElementBean.getCbElementType());
		query.setParameter(2, cbElementBean.getDeptCode());
		query.setParameter(3, cbElementBean.getAgency());
		query.setParameter(4, cbElementBean.getTku());
		query.setParameter(5, cbElementBean.getPayDate());

		List<ProjectBean> projectList = query.getResultList();
		if(projectList!=null){
			if(projectList.isEmpty()){
				query.setParameter(4, "AL");
				projectList = query.getResultList();
			}
		}
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findAllProjectNo(CodingBlockElement cbElementBean)");
		
		return projectList;
	}
	
	/****
	 * This method retrieves the AG1 for the selected Department, Agency, TKU and Pay Date
	 * @param cbElementBean
	 * @return List<Ag1Bean>
	 */

	public List<Ag1Bean> findAllAg1(CodingBlockElement cbElementBean) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findAllAg1(CodingBlockElement cbElementBean) : return List<Ag1Bean>");
		
		String finderQuery = "SELECT DISTINCT GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY code,GROUP_EXPENSE_CB_TKUS.GECB_IDENTIFIER ID,AGENCY_CODE_1.NAME NAME,AGENCY_CODE_1.FACS_AGY AGENCY,(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY || '    ' || AGENCY_CODE_1.NAME) DISPLAY  "
				+ "FROM GROUP_EXPENSE_CB_TKUS, AGENCY_CODE_1 "
				+ "WHERE AGENCY_CODE_1.AGENCY_CODE_1=GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY AND "
				+ "AGENCY_CODE_1.FACS_AGY=GROUP_EXPENSE_CB_TKUS.FACS_AGY AND "
				+ "GROUP_EXPENSE_CB_TKUS.ELEMENT_TYPE=?1 AND "
				+ "GROUP_EXPENSE_CB_TKUS.DEPARTMENT=?2 AND "
				+ "GROUP_EXPENSE_CB_TKUS.AGENCY=?3 AND "
				+ "GROUP_EXPENSE_CB_TKUS.TKU=?4 AND "
				+ "AGENCY_CODE_1.STATUS_CODE='A' AND "
				+ "?5 BETWEEN AGENCY_CODE_1.START_DATE AND AGENCY_CODE_1.END_DATE";

		Query query = entityManager.createNativeQuery(finderQuery,Ag1Bean.class);
		query.setParameter(1, cbElementBean.getCbElementType());
		query.setParameter(2, cbElementBean.getDeptCode());
		query.setParameter(3, cbElementBean.getAgency());
		query.setParameter(4, cbElementBean.getTku());
		query.setParameter(5, cbElementBean.getPayDate());

		List<Ag1Bean> ag1List = query.getResultList();
		if(ag1List!=null){
			if(ag1List.isEmpty()){
				query.setParameter(4, "AL");
				ag1List = query.getResultList();
			}
		}
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findAllAg1(CodingBlockElement cbElementBean)");

		return ag1List;
	}

	/***
	 * This method retrieves the AG2 for the selected Department, Agency, TKU and Pay Date
	 * @param cbElementBean
	 * @return List<Ag2Bean>
	 */
	
	@SuppressWarnings("unchecked")
	public List<Ag2Bean> findAllAg2(CodingBlockElement cbElementBean) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findAllAg2(CodingBlockElement cbElementBean) : return List<Ag2Bean>");
		
		String finderQuery = "SELECT DISTINCT GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY code,GROUP_EXPENSE_CB_TKUS.GECB_IDENTIFIER ID,AGENCY_CODE_2.NAME NAME,AGENCY_CODE_2.FACS_AGY AGENCY,(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY || '   ' || AGENCY_CODE_2.NAME) DISPLAY "
				+ "FROM GROUP_EXPENSE_CB_TKUS, AGENCY_CODE_2 "
				+ "WHERE AGENCY_CODE_2.AGENCY_CODE_2=GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY AND "
				+ "AGENCY_CODE_2.FACS_AGY=GROUP_EXPENSE_CB_TKUS.FACS_AGY AND "
				+ "GROUP_EXPENSE_CB_TKUS.ELEMENT_TYPE=?1 AND "
				+ "GROUP_EXPENSE_CB_TKUS.DEPARTMENT=?2 AND "
				+ "GROUP_EXPENSE_CB_TKUS.AGENCY=?3 AND "
				+ "GROUP_EXPENSE_CB_TKUS.TKU=?4 AND "
				+ "AGENCY_CODE_2.STATUS_CODE='A' AND "
				+ "?5 BETWEEN AGENCY_CODE_2.START_DATE AND AGENCY_CODE_2.END_DATE";

		Query query = entityManager.createNativeQuery(finderQuery,Ag2Bean.class);
		query.setParameter(1,cbElementBean.getCbElementType());
		query.setParameter(2, cbElementBean.getDeptCode());
		query.setParameter(3, cbElementBean.getAgency());
		query.setParameter(4, cbElementBean.getTku());
		query.setParameter(5, cbElementBean.getPayDate());

		List<Ag2Bean> ag2List = query.getResultList();
		if(ag2List!=null){
			if(ag2List.isEmpty()){
				query.setParameter(4, "AL");
				ag2List = query.getResultList();
			}
		}
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findAllAg1(CodingBlockElement cbElementBean)");

		return ag2List;
	}
	
	/***
	 * This method retrieves the AG3 for the selected Department, Agency, TKU and Pay Date
	 * @param cbElementBean
	 * @return List<Ag3Bean>
	 */

	public List<Ag3Bean> findAllAg3(CodingBlockElement cbElementBean) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findAllAg3(CodingBlockElement cbElementBean) : return List<Ag3Bean>");
		
		String finderQuery = "SELECT DISTINCT GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY code, GROUP_EXPENSE_CB_TKUS.GECB_IDENTIFIER ID,AGENCY_CODE_3.NAME NAME,AGENCY_CODE_3.FACS_AGY AGENCY,(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY || '    ' || AGENCY_CODE_3.NAME) DISPLAY "
				+ "FROM GROUP_EXPENSE_CB_TKUS, AGENCY_CODE_3 "
				+ "WHERE AGENCY_CODE_3.AGENCY_CODE_3=GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY AND "
				+ "AGENCY_CODE_3.FACS_AGY=GROUP_EXPENSE_CB_TKUS.FACS_AGY AND "
				+ "GROUP_EXPENSE_CB_TKUS.ELEMENT_TYPE=?1 AND "
				+ "GROUP_EXPENSE_CB_TKUS.DEPARTMENT=?2 AND "
				+ "GROUP_EXPENSE_CB_TKUS.AGENCY=?3 AND "
				+ "GROUP_EXPENSE_CB_TKUS.TKU=?4 AND "
				+ "AGENCY_CODE_3.STATUS_CODE='A' AND "
				+ "?5 BETWEEN AGENCY_CODE_3.START_DATE AND AGENCY_CODE_3.END_DATE";

		Query query = entityManager.createNativeQuery(finderQuery,Ag3Bean.class);
		query.setParameter(1, cbElementBean.getCbElementType());
		query.setParameter(2, cbElementBean.getDeptCode());
		query.setParameter(3, cbElementBean.getAgency());
		query.setParameter(4, cbElementBean.getTku());
		query.setParameter(5, cbElementBean.getPayDate());

		List<Ag3Bean> ag3List = query.getResultList();
		if(ag3List!=null){
			if(ag3List.isEmpty()){
				query.setParameter(4, "AL");
				ag3List = query.getResultList();
			}
		}
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findAllAg3(CodingBlockElement cbElementBean)");
		
		return ag3List;
	}
	
	/****
	 * This method retrieves the GrantPhase Number provided the Grant Number, Department, Agency, TKU and pay date.
	 * @param cbElementBean
	 * @param grantNo
	 * @return List<GrantPhaseBean>
	 */

	public List<GrantPhaseBean> findGrantPhaseByGrantNo(
			CodingBlockElement cbElementBean, String grantNo) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findGrantPhaseByGrantNo(CodingBlockElement cbElementBean, String grantNo) : return List<GrantPhaseBean>");
		
		String finderQuery = "SELECT DISTINCT SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1,6) CODE,GROUP_EXPENSE_CB_TKUS.GECB_IDENTIFIER ID,"
				+ "SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,7,2) GRANT_PHASE,GRANTS.FACS_AGY AGENCY, GRANTS.GRANT_NUMBER GRANTNO,(SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1,6) || '    ' || DECODE(GRANTS.AGENCY_CODE_1,NULL,GRANTS.NAME)) DISPLAY, "
				+ "DECODE(GRANTS.AGENCY_CODE_1,NULL,GRANTS.NAME) NAME FROM GROUP_EXPENSE_CB_TKUS,GRANTS "
				+ "WHERE (GRANTS.GRANT_NUMBER = SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1, 6)) AND "
				+ "GRANTS.GRANT_NUMBER = ?1 AND "
				+ "GRANTS.GRANT_PHASE = SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,7,2) AND "
				+ "GRANTS.FACS_AGY = GROUP_EXPENSE_CB_TKUS.FACS_AGY AND "
				+ "GROUP_EXPENSE_CB_TKUS.ELEMENT_TYPE = ?2 AND "
				+ "GROUP_EXPENSE_CB_TKUS.DEPARTMENT=?3 AND "
				+ "GROUP_EXPENSE_CB_TKUS.AGENCY=?4 AND "
				+ "GROUP_EXPENSE_CB_TKUS.TKU=?5 AND "
				+ "GRANTS.STATUS_CODE = 'A' AND "
				+ "?6 BETWEEN GRANTS.START_DATE AND GRANTS.END_DATE";

		Query query = entityManager.createNativeQuery(finderQuery,GrantPhaseBean.class);
		query.setParameter(1, grantNo);
		query.setParameter(2, cbElementBean.getCbElementType());
		query.setParameter(3, cbElementBean.getDeptCode());
		query.setParameter(4, cbElementBean.getAgency());
		query.setParameter(5, cbElementBean.getTku());
		query.setParameter(6, cbElementBean.getPayDate());

		List<GrantPhaseBean> grantPhaseList = query.getResultList();
		if(grantPhaseList!=null){
			if(grantPhaseList.isEmpty()){
				query.setParameter(5, "AL");
				grantPhaseList = query.getResultList();
			}
		}
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findGrantPhaseByGrantNo(CodingBlockElement cbElementBean, String grantNo)");
		
		return grantPhaseList;
	}

	
	/****
	 * This method retrieves the Multi-purpose code provided the Department, Agency , TKU and pay date
	 * @param cbElementBean
	 * @return
	 */
	public List<MultiBean> findAllMulti(CodingBlockElement cbElementBean) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findAllMulti(CodingBlockElement cbElementBean) : return List<MultiBean>");

		String finderQuery = "SELECT DISTINCT GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY code, GROUP_EXPENSE_CB_TKUS.GECB_IDENTIFIER ID,MULTIPURPOSE_CODES.NAME NAME,MULTIPURPOSE_CODES.FACS_AGY AGENCY,(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY || '    ' || MULTIPURPOSE_CODES.NAME) DISPLAY"
				+ " FROM GROUP_EXPENSE_CB_TKUS, MULTIPURPOSE_CODES "
				+ "WHERE MULTIPURPOSE_CODES.MULTIPURPOSE_CODE=GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY AND "
				+ "MULTIPURPOSE_CODES.FACS_AGY=GROUP_EXPENSE_CB_TKUS.FACS_AGY AND "
				+ "GROUP_EXPENSE_CB_TKUS.ELEMENT_TYPE=?1 AND "
				+ "GROUP_EXPENSE_CB_TKUS.DEPARTMENT=?2 AND "
				+ "GROUP_EXPENSE_CB_TKUS.AGENCY=?3 AND "
				+ "GROUP_EXPENSE_CB_TKUS.TKU=?4 AND "
				+ "MULTIPURPOSE_CODES.STATUS_CODE='A' AND "
				+ "?5 BETWEEN MULTIPURPOSE_CODES.START_DATE AND MULTIPURPOSE_CODES.END_DATE";

		Query query = entityManager.createNativeQuery(finderQuery,MultiBean.class);
		query.setParameter(1, cbElementBean.getCbElementType());
		query.setParameter(2, cbElementBean.getDeptCode());
		query.setParameter(3, cbElementBean.getAgency());
		query.setParameter(4, cbElementBean.getTku());
		query.setParameter(5, cbElementBean.getPayDate());

		List<MultiBean> multiList = query.getResultList();
		if(multiList!=null){
			if(multiList.isEmpty()){
				query.setParameter(4, "AL");
				multiList = query.getResultList();
			}
		}
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findAllMulti(CodingBlockElement cbElementBean) : return List<MultiBean>");
		
		return multiList;
	}

	
	/**
	 * This method retrieves the Project phase given the Project number
	 * @param cbElementBean
	 * @param projectNo
	 * @return
	 */
	public List<ProjectPhaseBean> findProjectPhaseByProjectNo(
			CodingBlockElement cbElementBean, String projectNo) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findProjectPhaseByProjectNo(CodingBlockElement cbElementBean, String projectNo) : return List<ProjectPhaseBean>");

		String finderQuery = "SELECT DISTINCT SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1,6) CODE, GROUP_EXPENSE_CB_TKUS.GECB_IDENTIFIER ID, V_PROJECT_NAMES.NAME NAME,V_PROJECT_NAMES.FACS_AGY AGENCY,(SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1,6) || '    ' || V_PROJECT_NAMES.NAME) DISPLAY,V_PROJECT_NAMES.PROJECT_NUMBER PROJECTNO, "
				+ "SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,7,2) PROJECT_PHASE "
				+ "FROM GROUP_EXPENSE_CB_TKUS, V_PROJECT_NAMES "
				+ "WHERE V_PROJECT_NAMES.PROJECT_NUMBER= SUBSTR(GROUP_EXPENSE_CB_TKUS.ELEMENT_KEY,1,6) AND "
				+ "V_PROJECT_NAMES.PROJECT_NUMBER = ?1 AND "
				+ "V_PROJECT_NAMES.FACS_AGY=GROUP_EXPENSE_CB_TKUS.FACS_AGY AND "
				+ "GROUP_EXPENSE_CB_TKUS.ELEMENT_TYPE=?2 AND "
				+ "GROUP_EXPENSE_CB_TKUS.DEPARTMENT=?3 AND "
				+ "GROUP_EXPENSE_CB_TKUS.AGENCY=?4 AND "
				+ "GROUP_EXPENSE_CB_TKUS.TKU=?5 AND "
				+ "V_PROJECT_NAMES.STATUS_CODE='A' AND "
				+ "?6 BETWEEN V_PROJECT_NAMES.START_DATE AND V_PROJECT_NAMES.END_DATE";

		Query query = entityManager.createNativeQuery(finderQuery,ProjectPhaseBean.class);
		query.setParameter(1, projectNo);
		query.setParameter(2, cbElementBean.getCbElementType());
		query.setParameter(3, cbElementBean.getDeptCode());
		query.setParameter(4, cbElementBean.getAgency());
		query.setParameter(5, cbElementBean.getTku());
		query.setParameter(6, cbElementBean.getPayDate());

		List<ProjectPhaseBean> projPhaseList = query.getResultList();
		if(projPhaseList!=null){
			if(projPhaseList.isEmpty()){
				query.setParameter(5, "AL");
				projPhaseList = query.getResultList();
			}
		}
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findProjectPhaseByProjectNo(CodingBlockElement cbElementBean, String projectNo)");
		
		return projPhaseList;
	}

	/**
	 * This method invokes the F_PAY_DATE Oracle function to get the pay date given a Date
	 * @param inputDate
	 * @return
	 */
	public Date findPayDate(java.util.Date inputDate) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findPayDate(java.util.Date inputDate) : return Date");
		
		String finderQuery = "SELECT f_pay_date(?1) from dual";
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findPayDate(java.util.Date inputDate) : return Date");
		
		return (Date)entityManager.createNativeQuery(finderQuery)
				.setParameter(1, inputDate).getSingleResult();
		
		
	}

	/**
	 * this method retrieves the default distribution Advance Agency Coding Blocks given the Department, Agency and Appropriation year
	 * @param dept
	 * @param agy
	 * @param appropriationYear
	 * @return List<DefaultDistributionsAdvAgy>
	 */

	public List<DefaultDistributionsAdvAgy> findDefaultAgencyAdvanceCodingBlock(
			String dept, String agy, Date requestDate) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findDefaultAgencyAdvanceCodingBlock(String dept, String agy, String appropriationYear) : return List<DefaultDistributionsAdvAgy>");

		String finderQuery = "SELECT dd from DefaultDistributionsAdvAgy dd "
				+ "where dd.department =:dept and "
				+ "dd.agencies=:agy and :requestDate between dd.startDate and dd.endDate";

		Query query = entityManager.createQuery(finderQuery);
		query.setParameter("dept", dept);
		query.setParameter("agy", agy);
		query.setParameter("requestDate", requestDate);

		List<DefaultDistributionsAdvAgy> defDistAdvAgyList = query.getResultList();
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findDefaultAgencyAdvanceCodingBlock(String dept, String agy, String appropriationYear)");

		return defDistAdvAgyList;
	}
	
/*
	public PayTypes findPayTypes(int payTypeId) {
		PayTypes payTypes = entityManager.find(PayTypes.class, payTypeId);
		return payTypes;
	}
*/

	/**
	 * This method finds the CodingBlock given the codingBlockId
	 */
	
	public ExpenseDetailCodingBlocks findCodingBlockByCodingBlockId(int expenseCodingBlockId){
		if(logger.isDebugEnabled())
			logger.debug("Enter method : findCodingBlockByCodingBlockId(int expenseCodingBlockId) : return ExpenseDetailCodingBlocks");
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : findCodingBlockByCodingBlockId(int expenseCodingBlockId) : return ExpenseDetailCodingBlocks");
		return entityManager.find(ExpenseDetailCodingBlocks.class, expenseCodingBlockId);
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	/**
	 * fetch list of driver reimbursement coding blocks
	 * @param dept
	 * @param agy
	 * @param expTypeCode
	 * @return
	 */

	public List<DriverReimbExpTypeCbs> findDriverReimbExpTypeCb(
			String dept, String agy, String expTypeCode) {

		String finderQuery = "SELECT dretc from DriverReimbExpTypeCbs dretc "
				+ "where dretc.department =:dept and "
				+ "dretc.agency=:agy and dretc.expTypeCode=:expTypeCode";

		Query query = entityManager.createQuery(finderQuery);
		query.setParameter("dept", dept);
		query.setParameter("agy", agy);
		query.setParameter("expTypeCode", expTypeCode);

		List<DriverReimbExpTypeCbs> driverReimbExpTypeCbs = query.getResultList();

		return driverReimbExpTypeCbs;
	}
	
	public List<DriverReimbExpTypeCbs> findDriverReimbExpTypeCbs() {

		String finderQuery = "SELECT dretc from DriverReimbExpTypeCbs dretc ";

		Query query = entityManager.createQuery(finderQuery);

		List<DriverReimbExpTypeCbs> driverReimbExpTypeCbs = query.getResultList();

		return driverReimbExpTypeCbs;
	}

}
