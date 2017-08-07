package gov.michigan.dit.timeexpense.service;

import gov.michigan.dit.timeexpense.action.ActionHelper;
import gov.michigan.dit.timeexpense.dao.AdvanceDAO;
import gov.michigan.dit.timeexpense.dao.AppointmentDAO;
import gov.michigan.dit.timeexpense.dao.CodingBlockDAO;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseLineItemDAO;
import gov.michigan.dit.timeexpense.dao.TravelRequisitionDAO;
import gov.michigan.dit.timeexpense.exception.ExpenseException;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetails;
import gov.michigan.dit.timeexpense.model.core.AdvanceErrors;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.CodingBlockElement;
import gov.michigan.dit.timeexpense.model.core.DriverReimbExpTypeCbs;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.TkuoptTaOptions;
import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
import gov.michigan.dit.timeexpense.model.core.DefaultDistributionsAdvAgy;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetailCodingBlock;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetails;
import gov.michigan.dit.timeexpense.model.core.TravelReqErrors;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.model.display.Ag1Bean;
import gov.michigan.dit.timeexpense.model.display.Ag2Bean;
import gov.michigan.dit.timeexpense.model.display.Ag3Bean;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.GrantBean;
import gov.michigan.dit.timeexpense.model.display.GrantPhaseBean;
import gov.michigan.dit.timeexpense.model.display.IndexCodesBean;
import gov.michigan.dit.timeexpense.model.display.MultiBean;
import gov.michigan.dit.timeexpense.model.display.PcaBean;
import gov.michigan.dit.timeexpense.model.display.ProjectBean;
import gov.michigan.dit.timeexpense.model.display.ProjectPhaseBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.ibm.websphere.management.Session;

import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

public class CodingBlockDSP {

	CodingBlockDAO codingBlockDAO;
	CommonDAO commonDao;
	ExpenseDAO expenseDao;
	TravelRequisitionDAO treqDAO;
	AdvanceDAO advanceDao;
	ExpenseLineItemDAO lineItemDao;
	AppointmentDAO apptDao;

	CommonDSP commonDsp;
	private static final Logger logger = Logger.getLogger(CodingBlockDSP.class);

	public CodingBlockDSP(EntityManager em) {
		codingBlockDAO = new CodingBlockDAO(em);
		commonDao = new CommonDAO(em);
		expenseDao = new ExpenseDAO(em);
		advanceDao = new AdvanceDAO(em);
		lineItemDao = new ExpenseLineItemDAO(em);
		apptDao = new AppointmentDAO(em);
		commonDsp = new CommonDSP(em);
		treqDAO = new TravelRequisitionDAO(em);
	}

	/**
	 * Get a list of Project Phases for a Project Number
	 * 
	 * @param codingBlockElement
	 * @param projectNo
	 * @return
	 */
	public List<ProjectPhaseBean> getProjectPhaseByProjectNo(
			CodingBlockElement codingBlockElement, String projectNo) {
		List<ProjectPhaseBean> projectPhaseBean = codingBlockDAO
				.findProjectPhaseByProjectNo(codingBlockElement, projectNo);
		return projectPhaseBean;
	}
	/**
	 *  Get a list of each of the coding block elements - index, pca, grant,
	 agency code 1,
	 project, agency code 2, agency code 3 and multipurpose code valid for the
	 department, agency, tku, expense end date and appropriation year
	 
	 ZH - Commenting out redundant code
	 */
	
	public List<List> findCodingBlocksDetails(String deptCode, String agency,
			String tku, TkuoptTaOptions tkuOptions, Date expenseEndDate,
			String apprYear) {
		List<IndexCodesBean> indexList = new ArrayList<IndexCodesBean>();
		List<PcaBean> pcaList = new ArrayList<PcaBean>();
		List<GrantBean> grantList = new ArrayList<GrantBean>();
		List<Ag1Bean> ag1List = new ArrayList<Ag1Bean>();
		List<ProjectBean> projectList = new ArrayList<ProjectBean>();
		List<Ag2Bean> ag2List = new ArrayList<Ag2Bean>();
		List<Ag3Bean> ag3List = new ArrayList<Ag3Bean>();
		List<MultiBean> multiList = new ArrayList<MultiBean>();
		List<List> returnList = new ArrayList<List>();
		CodingBlockElement cbElement = new CodingBlockElement();
		cbElement.setAgency(agency);
		cbElement.setAppropriationYear(apprYear);
		cbElement.setDeptCode(deptCode);
		cbElement.setTku(tku);
		cbElement.setStatus("A");
		cbElement.setPayDate(expenseEndDate);

		if (tkuOptions.getExpIndexEntryInd().equalsIgnoreCase("y")
				|| tkuOptions.getAdvIndexEntryInd().equalsIgnoreCase("y")) {
			cbElement.setCbElementType("IDX");
			indexList = codingBlockDAO.findAllIndexes(cbElement);
			/*if (indexList.size() == 0) {
				cbElement.setTku("AL");
				indexList = codingBlockDAO.findAllIndexes(cbElement);
				cbElement.setTku(tku);
			}*/
		}
		if (tkuOptions.getExpPcaEntryInd().equalsIgnoreCase("y")
				|| tkuOptions.getAdvPcaEntryInd().equalsIgnoreCase("y")) {
			cbElement.setCbElementType("PCA");
			pcaList = codingBlockDAO.findAllPCA(cbElement);
			/*if (pcaList.size() == 0) {
				cbElement.setTku("AL");
				pcaList = codingBlockDAO.findAllPCA(cbElement);
				cbElement.setTku(tku);
			}*/
		}
		if (tkuOptions.getExpGrantEntryInd().equalsIgnoreCase("y")
				|| tkuOptions.getAdvGrantEntryInd().equalsIgnoreCase("y")) {
			cbElement.setCbElementType("GRA");
			grantList = codingBlockDAO.findAllGrantNo(cbElement);
			/*if (grantList.size() == 0) {
				cbElement.setTku("AL");
				grantList = codingBlockDAO.findAllGrantNo(cbElement);
				cbElement.setTku(tku);
			}*/
		}
		if (tkuOptions.getExpAg1EntryInd().equalsIgnoreCase("y")
				|| tkuOptions.getAdvAg1EntryInd().equalsIgnoreCase("y")) {
			cbElement.setCbElementType("AG1");
			ag1List = codingBlockDAO.findAllAg1(cbElement);
			/*if (ag1List.size() == 0) {
				cbElement.setTku("AL");
				ag1List = codingBlockDAO.findAllAg1(cbElement);
				cbElement.setTku(tku);
			}*/
		}
		if (tkuOptions.getExpProjectEntryInd().equalsIgnoreCase("y")
				|| tkuOptions.getAdvProjectEntryInd().equalsIgnoreCase("y")) {
			cbElement.setCbElementType("PRO");
			projectList = codingBlockDAO.findAllProjectNo(cbElement);
		/*	if (projectList.size() == 0) {
				cbElement.setTku("AL");
				projectList = codingBlockDAO.findAllProjectNo(cbElement);
				cbElement.setTku(tku);
			}*/
		}
		if (tkuOptions.getExpAg2EntryInd().equalsIgnoreCase("y")
				|| tkuOptions.getAdvAg2EntryInd().equalsIgnoreCase("y")) {
			cbElement.setCbElementType("AG2");
			ag2List = codingBlockDAO.findAllAg2(cbElement);
			/*if (ag2List.size() == 0) {
				cbElement.setTku("AL");
				ag2List = codingBlockDAO.findAllAg2(cbElement);
				cbElement.setTku(tku);
			}*/
		}
		if (tkuOptions.getExpAg3EntryInd().equalsIgnoreCase("y")
				|| tkuOptions.getAdvAg3EntryInd().equalsIgnoreCase("y")) {
			cbElement.setCbElementType("AG3");
			ag3List = codingBlockDAO.findAllAg3(cbElement);
			/*if (ag3List.size() == 0) {
				cbElement.setTku("AL");
				ag3List = codingBlockDAO.findAllAg3(cbElement);
				cbElement.setTku(tku);
			}*/
		}
		if (tkuOptions.getExpMultipurpEntryInd().equalsIgnoreCase("y")
				|| tkuOptions.getAdvMultipurpEntryInd().equalsIgnoreCase("y")) {
			cbElement.setCbElementType("MUL");
			multiList = codingBlockDAO.findAllMulti(cbElement);
			/*if (multiList.size() == 0) {
				cbElement.setTku("AL");
				multiList = codingBlockDAO.findAllMulti(cbElement);
				cbElement.setTku(tku);
			}*/
		}
		returnList.add(indexList);
		returnList.add(pcaList);
		returnList.add(grantList);
		returnList.add(ag1List);
		returnList.add(projectList);
		returnList.add(ag2List);
		returnList.add(ag3List);
		returnList.add(multiList);

		return returnList;
	}

	// Get a list of each of the coding block elements - index, pca, grant,
	// agency code 1,
	// project, agency code 2, agency code 3 and multipurpose code valid for the
	// department, agency, tku, expense end date and appropriation year
	public List<List> findCodingBlocksDetailsIndexPcaOnly(String deptCode,
			String agency, String tku, TkuoptTaOptions tkuOptions,
			Date expenseEndDate, String apprYear) {
		List<IndexCodesBean> indexList = new ArrayList<IndexCodesBean>();
		List<PcaBean> pcaList = new ArrayList<PcaBean>();

		List<List> returnList = new ArrayList<List>();
		CodingBlockElement cbElement = new CodingBlockElement();
		cbElement.setAgency(agency);
		cbElement.setAppropriationYear(apprYear);
		cbElement.setDeptCode(deptCode);
		cbElement.setTku(tku);
		cbElement.setStatus("A");
		cbElement.setPayDate(expenseEndDate);

		if (tkuOptions.getExpIndexEntryInd().equalsIgnoreCase("y")
				|| tkuOptions.getAdvIndexEntryInd().equalsIgnoreCase("y")) {
			cbElement.setCbElementType("IDX");
			indexList = codingBlockDAO.findAllIndexes(cbElement);
			if (indexList.size() == 0) {
				cbElement.setTku("AL");
				indexList = codingBlockDAO.findAllIndexes(cbElement);
				cbElement.setTku(tku);
			}
		}
		if (tkuOptions.getExpPcaEntryInd().equalsIgnoreCase("y")
				|| tkuOptions.getAdvPcaEntryInd().equalsIgnoreCase("y")) {
			cbElement.setCbElementType("PCA");
			pcaList = codingBlockDAO.findAllPCA(cbElement);
			if (pcaList.size() == 0) {
				cbElement.setTku("AL");
				pcaList = codingBlockDAO.findAllPCA(cbElement);
				cbElement.setTku(tku);
			}
		}

		returnList.add(indexList);
		returnList.add(pcaList);

		return returnList;
	}

	// Delete expense detail coding blocks
	public ExpenseMasters deleteExpenseDetailCodingBlocks(
			ExpenseDetailCodingBlocks expDetailCB, ExpenseMasters expenseMaster) {

		// if (codingBlockList == null)
		// return null;

		// Remove Coding block from ExpenseDetailCodingBlocks List
		// for (ExpenseDetailCodingBlocks expDetailCB : codingBlockList) {
		codingBlockDAO.getEntityManager().remove(expDetailCB);
		// }
		// invoke Save on ExpenseMaster
		ExpenseMasters savedExpense = expenseDao.saveExpense(expenseMaster);

		return savedExpense;

	}

	/**
	 * Get a list of Grant Phases for a Grant Number
	 * 
	 * @param codingBlockElement
	 * @param grantNo
	 * @return
	 */
	public List<GrantPhaseBean> getGrantPhaseByGrantNo(
			CodingBlockElement codingBlockElement, String grantNo) {
		List<GrantPhaseBean> grantPhaseBean = codingBlockDAO
				.findGrantPhaseByGrantNo(codingBlockElement, grantNo);
		return grantPhaseBean;
	}

	/***
	 * Get expense detail coding blocks for and expense detail line item
	 * 
	 * @param expenseLineItemId
	 * @return
	 */
	public List<ExpenseDetailCodingBlocks> getExpenseDetailCodingBlocksByExpenseDetails(
			ExpenseDetails expenseLineItemId) {
		return null;
	}

	/***
	 * Save expense detail coding blociks
	 * 
	 * @param codingBlock
	 * @return
	 */
	public ExpenseDetailCodingBlocks saveExpenseDetailCodingBlocks(
			ExpenseDetailCodingBlocks[] codingBlock) {
		return null;
	}

	/***
	 * Get TKUOPT TA OPTIONS for department, agency and tku
	 * 
	 * @param deptCode
	 * @param agency
	 * @param tku
	 * @return
	 */
	public TkuoptTaOptions getCBMetaData(String deptCode, String agency,
			String tku) {
		TkuoptTaOptions tkuOptTa = codingBlockDAO.findCBMetaData(deptCode,
				agency, tku);
		return tkuOptTa;
	}

	/***
	 * Validate advance detail coding blocks
	 * 
	 * @param cbElement
	 * @param advanceDetailsList
	 */
	public void validateAdvanceCodingBlocks(CodingBlockElement cbElement,
			List<AdvanceDetails> advanceDetailsList, UserProfile userProfile) {
		AdvanceDetails advanceDetails = null;
		String returnCode;
		List<AdvanceDetailCodingBlocks> advanceDetailCodingBlockList = new ArrayList<AdvanceDetailCodingBlocks>();
		AdvanceDetailCodingBlocks advanceDetailCodingBlock = null;
		AdvanceMasters advanceMasters = advanceDetailsList.get(0)
				.getAdvmIdentifier();
		List<AdvanceErrors> advanceErrorsList = advanceMasters
				.getAdvanceErrorsCollection();

		// Check for advance errors
		if (advanceErrorsList == null || advanceErrorsList.isEmpty()) {
			// If there are no advance errors currently then create an advance
			// error list
			advanceErrorsList = new ArrayList<AdvanceErrors>();
			advanceMasters.setAdvanceErrorsCollection(advanceErrorsList);
		} // loop through the advance details
		for (int index = 0; index < advanceDetailsList.size(); index++) {
			advanceDetails = advanceDetailsList.get(index);
			advanceDetailCodingBlockList = advanceDetails
					.getAdvanceDetailCodingBlocksCollection();
			// For each advance detail loop through the coding blocks list and
			// validate each coding block
			for (int idx = 0; idx < advanceDetailCodingBlockList.size(); idx++) {
				advanceDetailCodingBlock = advanceDetailCodingBlockList
						.get(idx);

				if (!advanceDetailCodingBlock.getStandardInd().equals("Y")) {
					returnCode = codingBlockDAO.validateAdvanceCodingBlocks(
							advanceDetailCodingBlock, cbElement);
					if (returnCode != null) {
						// return code from validate should always be not null
						if (!returnCode.substring(0, 5).equals(
								IConstants.CODING_BLOCK_VALID_CODE)) {
							// if the return code does not indicate a valid
							// coding
							// block then
							// create error messages
							ErrorMessages errorMessages = commonDsp
									.getErrorCode(returnCode);
							/*
							 * errorMessages.setErrorCode(returnCode);
							 * errorMessages.setErrorText(commonDao
							 * .findErrorTextByCode(returnCode));
							 */
							AdvanceErrors advanceErrors = new AdvanceErrors();
							advanceErrors.setAdvmIdentifier(advanceMasters);
							advanceErrors.setErrorCode(errorMessages);
							advanceErrors.setModifiedUserId(userProfile
									.getUserId());
							advanceErrors.setErrorSource(ActionHelper.getConcatCodingBlockValue(advanceDetailCodingBlock));
							advanceErrorsList.add(advanceErrors);
						}
					}
				}
			}
		}
		advanceMasters.setAdvanceErrorsCollection(advanceErrorsList);
		// advanceDao.saveAdvanceMaster(advanceMasters);
	}

	/***
	 * Validate expense detail coding blocks
	 * 
	 * @param cbElement
	 * @param expenseDetailsList
	 * @throws ExpenseException 
	 */
	public void validateExpenseCodingBlocks(ExpenseMasters expenseMasters,
			CodingBlockElement cbElement,
			List<ExpenseDetails> expenseDetailsList, UserProfile userProfile,
			UserSubject userSubject, List<DriverReimbExpTypeCbs> driverReibmCbs)
			throws ExpenseException {
		ExpenseDetails expenseDetails = null;
		String returnCode = "";
		expenseDetailsList = expenseMasters.getExpenseDetailsCollection();

		List<ExpenseDetailCodingBlocks> expenseDetailCodingBlockList = new ArrayList<ExpenseDetailCodingBlocks>();
		ExpenseDetailCodingBlocks expenseDetailCodingBlock = null;
		List<ExpenseErrors> expenseErrorsList = expenseMasters
				.getExpenseErrorsCollection();

		// Check for expense errors
		if (expenseErrorsList == null) {
			// If there are no expense errors currently then create an advance
			// error list
			expenseErrorsList = new ArrayList<ExpenseErrors>();
			expenseMasters.setExpenseErrorsCollection(expenseErrorsList);
		} else if (!expenseErrorsList.isEmpty()) {
			// If there are expense errors on file then delete them
			// delete the errors related to Coding Block tab

			ExpenseMasters updatedExpenseMasters = commonDao
					.deleteExpenseErrors(expenseMasters, "CB");
		}
		// At this point the expense errors list should not contain any coding
		// block errors

		// loop through the expense details
		for (int index = 0; index < expenseDetailsList.size(); index++) {
			expenseDetails = expenseDetailsList.get(index);
			if (isDriverReimbursement(expenseDetails.getExpTypeCode()
					.getExpTypeCode(), driverReibmCbs) || expenseMasters.isPdfInd()) {
				// Driver reimbursement or PDF CBs are not validated
				continue;
			}
			expenseDetailCodingBlockList = expenseDetails
					.getExpenseDetailCodingBlocksCollection();
			// redundant check for percentages, AI 22912
			if (!validatePercentages(expenseMasters, userProfile,
					expenseDetails)) {
				throw new ExpenseException();
			}

			// For each expense detail loop through the coding blocks list and
			// validate each coding block
			for (int idx = 0; idx < expenseDetailCodingBlockList.size(); idx++) {
				expenseDetailCodingBlock = expenseDetailCodingBlockList
						.get(idx);

				// validate if standard coding block has been selected
				// when agency options say otherwise
				validateAgencyOptionForStandardCodingBlock(
						expenseDetailCodingBlock, expenseMasters,
						expenseDetails, userProfile, userSubject);

				if (expenseDetailCodingBlock.getStandardInd().equals("Y"))
					continue;
				returnCode = codingBlockDAO.validateExpenseCodingBlocks(
						expenseDetailCodingBlock, cbElement);
				if (returnCode != null) {
					// return code from validate should always be not null
					if (!returnCode.substring(0, 5).equals(
							IConstants.CODING_BLOCK_VALID_CODE)) {
						// if the return code does not indicate a valid coding
						// block then
						// create error messages
						ErrorMessages errorMessages = commonDsp
								.getErrorCode(returnCode);
						/*
						 * errorMessages.setErrorCode(returnCode);
						 * errorMessages.setErrorText(commonDao
						 * .findErrorTextByCode(returnCode));
						 */
						ExpenseErrors expenseErrors = new ExpenseErrors();
						expenseErrors.setExpmIdentifier(expenseMasters);
						expenseErrors.setErrorCode(errorMessages);
						expenseErrors
								.setModifiedUserId(userProfile.getUserId());
						/*
						 * expenseErrors .setErrorSource("Ex Ln" + " " +
						 * expenseDetails.getLineItem() + " - " + "CB" + " " +
						 * (idx + 1));
						 */
						expenseErrors
								.setErrorSource("CB "
										+ expenseDetails.getLineItem()
										+ " - "
										+ ActionHelper
												.getConcatCodingBlockValue(expenseDetailCodingBlock));
						expenseErrorsList.add(expenseErrors);
					}
				}
			}
		}

		// expenseMasters.setExpenseErrorsCollection(expenseErrorsList);
	}
	
	private void validateAgencyOptionForStandardCodingBlock(
			ExpenseDetailCodingBlocks expenseDetailCodingBlock, ExpenseMasters expenseMasters, ExpenseDetails expenseDetails, UserProfile userProfile, UserSubject userSubject) {
		
		AgencyOptions agencyOptions = commonDao.findAgencyOptions(userSubject
				.getDepartment(), userSubject.getAgency());
		
		if ("y".equalsIgnoreCase(agencyOptions.getReqCbExp())
				&& "y".equalsIgnoreCase(expenseDetailCodingBlock
						.getStandardInd()))
			
			commonDsp.populateErrors(
					IConstants.CODING_BLOCK_SPECIFIC_CB_REQUIRED,
					IConstants.EXP_ERR_SRC_CB_TAB + expenseDetails.getLineItem(),
					expenseMasters, userProfile);
	}

	public void validateTravelRequisitionCodingBlocks(TravelReqMasters treqMasters,
			CodingBlockElement cbElement,
			List<TravelReqDetails> treqDetailsList, UserProfile userProfile) {
		TravelReqDetails treqDetails = null;
		String returnCode = "";
		treqDetailsList = treqMasters.getTravelReqDetailsCollection();

		List<TravelReqDetailCodingBlock> treqDetailCodingBlockList = new ArrayList<TravelReqDetailCodingBlock>();
		TravelReqDetailCodingBlock treqDetailCodingBlock = null;
		List<TravelReqErrors> treqErrorsList = treqMasters
				.getTreqErrorsCollection();

		// Check for expense errors
		if (treqErrorsList == null) {
			// If there are no expense errors currently then create an advance
			// error list
			treqErrorsList = new ArrayList<TravelReqErrors>();
			treqMasters.setTreqErrorsCollection(treqErrorsList);
		} else if (!treqErrorsList.isEmpty()) {
			// If there are expense errors on file then delete them
			// delete the errors related to Coding Block tab

			TravelReqMasters updatedTreqMasters = commonDao
					.deleteTravelRequisitionErrors(treqMasters,
							"CB");
		}
		// At this point the expense errors list should not contain any coding
		// block errors

		// loop through the expense details
		for (int index = 0; index < treqDetailsList.size(); index++) {
			treqDetails = treqDetailsList.get(index);
			treqDetailCodingBlockList = treqDetails
					.getTravelReqDetailCodingBlockCollection();
			// For each expense detail loop through the coding blocks list and
			// validate each coding block
			for (int idx = 0; idx < treqDetailCodingBlockList.size(); idx++) {
				treqDetailCodingBlock = treqDetailCodingBlockList
						.get(idx);
				if (treqDetailCodingBlock.getStandardInd().equals("Y"))
					continue;
				returnCode = codingBlockDAO.validateTravelRequisitionCodingBlocks(
						treqDetailCodingBlock, cbElement);
				if (returnCode != null && codingBlockIndexPcaError(returnCode.substring(0, 5))){
					cbElement.setPayDate(Calendar.getInstance().getTime());
					returnCode = codingBlockDAO.validateTravelRequisitionCodingBlocks(
							treqDetailCodingBlock, cbElement);
				}
				if (returnCode != null) {
					// return code from validate should always be not null
					if (!returnCode.substring(0, 5).equals(
							IConstants.CODING_BLOCK_VALID_CODE)) {
						// if the return code does not indicate a valid coding
						// block then
						// create error messages
						ErrorMessages errorMessages = commonDsp
								.getErrorCode(returnCode);
						/*
						 * errorMessages.setErrorCode(returnCode);
						 * errorMessages.setErrorText(commonDao
						 * .findErrorTextByCode(returnCode));
						 */
						TravelReqErrors expenseErrors = new TravelReqErrors();
						expenseErrors.setTreqmIdentifier(treqMasters);
						expenseErrors.setErrorCode(errorMessages);
						expenseErrors.setModifiedUserId(userProfile.getUserId());
/*						expenseErrors
								.setErrorSource("Ex Ln" + " " + 
										expenseDetails.getLineItem() + " - " + 
										"CB" + " " + (idx + 1));*/
						expenseErrors.setErrorSource("CB " + " - " +
								ActionHelper.getConcatCodingBlockValue(treqDetailCodingBlock));
						treqErrorsList.add(expenseErrors);
					}
				}
			}
		}
		
		// expenseMasters.setExpenseErrorsCollection(expenseErrorsList);
	}
	
	private boolean codingBlockIndexPcaError(String returnCode) {
		return Integer.parseInt(returnCode) >= Integer
				.parseInt(IConstants.CODING_BLOCK_MIN_INDEX_PCA_ERROR_CODE) ? Integer
				.parseInt(returnCode) <= Integer
				.parseInt(IConstants.CODING_BLOCK_MAX_INDEX_PCA_ERROR_CODE)
				: false;

	}


	/**
	 * Get a list of default distributions advance agencies for a department and
	 * 
	 * @param deptCode
	 * @param agency
	 * @return
	 */
	public List<DefaultDistributionsAdvAgy> getDefaultAgencyAdvanceCodingBlock(
			String deptCode, String agency, Date requestDate) {

		List<DefaultDistributionsAdvAgy> defaultDistributionsAdvAgy = codingBlockDAO
				.findDefaultAgencyAdvanceCodingBlock(deptCode, agency,
						requestDate);
		return defaultDistributionsAdvAgy;
	}

	/**
	 * Get a list of expense detail coding blocks for an expense detail line
	 * item
	 * 
	 * @param expenseDetails
	 * @return
	 */

	public List<ExpenseDetailCodingBlocks> getCodingBlocksByExpenseLineItem(
			ExpenseDetails expenseDetails) {
		List<ExpenseDetailCodingBlocks> expenseCodingBlocksList = codingBlockDAO
				.findExpenseDetailCodingBlocks(expenseDetails);
		return expenseCodingBlocksList;
	}

	/**
	 * This method attaches the Expense Coding blocks to the ExpenseMasters and
	 * calls the DAO Save Checks to see if invalid Coding block is allowed at
	 * the Agency level. if "Yes" , saves the Master with the attached coding
	 * block else throws an Exception.
	 * 
	 * @param expenseMasters
	 * @param expenseDetailsList
	 * @param cbElement
	 * @return ExpenseMasters
	 * @throws ExpenseException
	 */

	public ExpenseMasters saveCodingBlocks(ExpenseMasters expenseMasters,
			List<ExpenseDetails> expenseDetailsList,
			CodingBlockElement cbElement, UserProfile userProfile, UserSubject userSubject, List<DriverReimbExpTypeCbs> driverReibmCbs ) throws ExpenseException {

		boolean isInvalidCodingBlockAllowed = false;
		
		for (ExpenseDetails item : expenseDetailsList) {
			// perform CB rounding
			item.setExpenseDetailCodingBlocksCollection(this
					.preSaveCodingBlocks(item.getDollarAmount(), item
							.getExpenseDetailCodingBlocksCollection(), userProfile));
			// save each CB item individually because of openjpa issue with saving multiple items in 
			// collection
			for (ExpenseDetailCodingBlocks cb: item
							.getExpenseDetailCodingBlocksCollection()){
				if (cb.getExpdcIdentifier() == null){
					expenseDao.getEntityManager().persist(cb);
				} else{
					expenseDao.getEntityManager().merge(cb);
				}
			}
			expenseDao.getEntityManager().merge(item);
		}
		
		// validate the Coding block
		validateExpenseCodingBlocks(expenseMasters, cbElement,
				expenseDetailsList, userProfile, userSubject, driverReibmCbs);
		
		return expenseDao.saveExpense(expenseMasters);
	}
	
	public TravelReqMasters saveTravelRequisitionCodingBlocks(TravelReqMasters treqMaster,
			List<TravelReqDetails> treqDetailsList,
			CodingBlockElement cbElement, UserProfile userProfile) throws ExpenseException {

		boolean isInvalidCodingBlockAllowed = false;
		
		for (TravelReqDetails item : treqDetailsList) {
			// perform CB rounding
			item.setTravelReqDetailCodingBlockCollection(this
					.preSaveTravelRequisitionCodingBlocks(item.getDollarAmount(), item
							.getTravelReqDetailCodingBlockCollection(), userProfile));
			// save each CB item individually because of openjpa issue with saving multiple items in 
			// collection
			for (TravelReqDetailCodingBlock cb: item
							.getTravelReqDetailCodingBlockCollection()){
				if (cb.getTreqdcIdentifier() == null){
					expenseDao.getEntityManager().persist(cb);
				} else{
					expenseDao.getEntityManager().merge(cb);
				}
			}
			expenseDao.getEntityManager().merge(item);
		}
		
		treqDAO.getEntityManager().flush();
		
		// validate the Coding block
		validateTravelRequisitionCodingBlocks(treqMaster, cbElement,
				treqDetailsList, userProfile);
		//expenseDao.getEntityManager().flush();
		return treqDAO.saveTravelRequisition(treqMaster);
	}

	
	@SuppressWarnings("unchecked")
	public String prepareCodingBlockStoreData(Date inputDate, String dept,
			String agency, String tku, String apprYear,
			TkuoptTaOptions tkuOptTaOptions) throws Exception {

		Gson gson = new Gson();

		// For testing, date is hard-coded ..
		// Date expenseEndDate = new Date("02/03/2008");

		List codingBlockList = this.findCodingBlocksDetails(dept, agency, tku,
				tkuOptTaOptions, inputDate, apprYear);

		List<IndexCodesBean> indexCodes = (List<IndexCodesBean>) codingBlockList
				.get(0);
		/*
		 * List<IndexCodesBean> indexCodes = new ArrayList<IndexCodesBean> ();
		 * IndexCodesBean blank = new IndexCodesBean (); indexCodes.add(blank);
		 */
		String indexJson = gson.toJson(indexCodes);
		String retIndexJson = "{index:" + indexJson + "}";

		List<PcaBean> pcaList = (List<PcaBean>) codingBlockList.get(1);
		String pcaJson = gson.toJson(pcaList);
		String retPcaJson = "{pca:" + pcaJson + "}";

		List<GrantBean> grantList = (List<GrantBean>) codingBlockList.get(2);
		String grantJson = gson.toJson(grantList);
		String retGrantJson = "{grant:" + grantJson + "}";

		List<Ag1Bean> ag1List = (List<Ag1Bean>) codingBlockList.get(3);
		String ag1Json = gson.toJson(ag1List);
		String retAg1Json = "{ag1:" + ag1Json + "}";

		List<ProjectBean> projectsList = (List<ProjectBean>) codingBlockList
				.get(4);
		String projectJson = gson.toJson(projectsList);
		String retprojectsJson = "{project:" + projectJson + "}";

		List<Ag2Bean> ag2List = (List<Ag2Bean>) codingBlockList.get(5);
		String ag2Json = gson.toJson(ag2List);
		String retag2Json = "{ag2:" + ag2Json + "}";

		List<Ag3Bean> ag3List = (List<Ag3Bean>) codingBlockList.get(6);
		String ag3Json = gson.toJson(ag3List);
		String retag3Json = "{ag3:" + ag3Json + "}";

		List<MultiBean> multiList = (List<MultiBean>) codingBlockList.get(7);
		String multiJson = gson.toJson(multiList);
		String retMultiJson = "{multi:" + multiJson + "}";

		/*
		 * String returnStoreJson = "[" + retIndexJson + "," + retPcaJson + ","
		 * + retGrantJson + "," + retAg1Json + "," + retprojectsJson + "," +
		 * retag2Json + "," + retag3Json + "," + retMultiJson + "]";
		 */

		String returnStoreJson = "[" + retIndexJson + "," + retPcaJson + ","
				+ retGrantJson + "," + retAg1Json + "," + retprojectsJson + ","
				+ retag2Json + "," + retag3Json + "," + retMultiJson
				+ ", {apprYear: " + "\"" + apprYear + "\"}" + "]";

		// if(!flag)
		// res.getWriter().write(returnStoreJson);

		return returnStoreJson;

	}

	@SuppressWarnings("unchecked")
	public String prepareCodingBlockStoreDataIndexPcaOnly(Date inputDate,
			String dept, String agency, String tku, String apprYear,
			TkuoptTaOptions tkuOptTaOptions) throws Exception {

		Gson gson = new Gson();

		// For testing, date is hard-coded ..
		// Date expenseEndDate = new Date("02/03/2008");

		List codingBlockList = this.findCodingBlocksDetailsIndexPcaOnly(dept,
				agency, tku, tkuOptTaOptions, inputDate, apprYear);

		List<IndexCodesBean> indexCodes = (List<IndexCodesBean>) codingBlockList
				.get(0);
		if (indexCodes.isEmpty()) {
			indexCodes = new ArrayList<IndexCodesBean>();
			IndexCodesBean blank = new IndexCodesBean();
			blank.setAppr_year(apprYear);
			indexCodes.add(blank);
		}
		/*
		 * List<IndexCodesBean> indexCodes = new ArrayList<IndexCodesBean> ();
		 * IndexCodesBean blank = new IndexCodesBean (); indexCodes.add(blank);
		 */
		String indexJson = gson.toJson(indexCodes);
		String retIndexJson = "{index:" + indexJson + "}";

		List<PcaBean> pcaList = (List<PcaBean>) codingBlockList.get(1);

		if (pcaList.isEmpty()) {
			pcaList = new ArrayList<PcaBean>();
			PcaBean blank = new PcaBean();
			blank.setAppr_year(apprYear);
			pcaList.add(blank);
		}

		String pcaJson = gson.toJson(pcaList);
		String retPcaJson = "{pca:" + pcaJson + "}";

		String returnStoreJson = "[" + retIndexJson + "," + retPcaJson + "]";

		// if(!flag)
		// res.getWriter().write(returnStoreJson);

		return returnStoreJson;

	}

	/**
	 * This method validate whether two Coding block are identical or not
	 * 
	 * @param cbList
	 * @return boolean
	 */

	public boolean validateIdenticalExpenseCodingBlock(
			List<ExpenseDetailCodingBlocks> cbList) {

		boolean identicalCodingBlockError = false;
		Set unique = new HashSet();

		for (int index = 0; index < cbList.size(); index++) {
			ExpenseDetailCodingBlocks expObj = (ExpenseDetailCodingBlocks) cbList
					.get(index);
			int beforeExpdcId = expObj.getExpdcIdentifier();
			int beforeExpdId = expObj.getExpdIdentifier().getExpdIdentifier();
			double beforePercent = expObj.getPercent();
			expObj.setExpdcIdentifier(null);
			expObj.setExpdIdentifier(null);
			expObj.setPercent(0);
			boolean added = unique.add(expObj);
			if (!added) {
				identicalCodingBlockError = true;
				expObj.setExpdcIdentifier(beforeExpdcId);
				expObj.setExpdIdentifier(lineItemDao
						.findExpenseDetailByExpenseDetailsId(beforeExpdId));
				expObj.setPercent(beforePercent);
				break;
			} else {
				expObj.setExpdcIdentifier(beforeExpdcId);
				expObj.setExpdIdentifier(lineItemDao
						.findExpenseDetailByExpenseDetailsId(beforeExpdId));
				expObj.setPercent(beforePercent);
			}
		}
		return identicalCodingBlockError;
	}

	/**
	 * Determine the pay date from the expense start date
	 * 
	 * @param expenseStartDate
	 * @return
	 */
	public Date getPayDate(Date expenseStartDate) {
		Date payDate = codingBlockDAO.findPayDate(expenseStartDate);
		return payDate;
	}

	/**
	 * Get the agency options for a department and agency
	 */
	public AgencyOptions getCBAgencyOptions(String deptCode, String agency) {
		return commonDao.findAgencyOptions(deptCode, agency);
	}

	/**
	 * This method retrieves the FACS_AGENCY for an employee given the expense
	 * start date, expense end date and userId.
	 * 
	 * @param expenseStartDate
	 * @param expenseEndDate
	 * @param empId
	 * @param userId
	 * @return String
	 */

	public String getFacsAgencyByExpenseDatesEmployee(Date expenseStartDate,
			Date expenseEndDate, long apptId, String userId) {

		String facsAgency = "";

		List<AppointmentsBean> apptList = apptDao
				.findFacsAgencyByExpenseDatesByEmployee(expenseStartDate,
						expenseEndDate, apptId);
		if (!apptList.isEmpty()) {
			facsAgency = apptList.get(0).getFacs_agy();
		}
		return facsAgency;
	}

	/**
	 * Sets decimal percentages, amounts and performs rounding. This method also sets modified_user_id for CB
	 * rows
	 */

	private List<ExpenseDetailCodingBlocks> preSaveCodingBlocks(
			double dollarAmount, List<ExpenseDetailCodingBlocks> toRoundList, UserProfile userProfile) {
		
		int listSize = toRoundList.size();
		int lastRowNumber = listSize - 1;
		double sumAfter = 0;
		
		for (int i = 0; i < listSize; i++) {
			ExpenseDetailCodingBlocks item = toRoundList.get(i);
			if (i < lastRowNumber) {
				// not last row - set only amount. Rounding mode is dow if not the last row 
				item.setDollarAmount(TimeAndExpenseUtil
						.roundToTwoDigitsDown(dollarAmount * (item.getPercent())));
				sumAfter += item.getDollarAmount();
			} else {
				// last row - calculate difference and set for last row's amount
				double difference = dollarAmount - sumAfter;
				item.setDollarAmount(TimeAndExpenseUtil
						.roundToTwoDigits(difference));
			}
			item.setModifiedUserId(userProfile.getUserId());
		}
		
		return toRoundList;
	}
	
	/**
	 * Sets decimal percentages, amounts and performs rounding. This method also sets modified_user_id for CB
	 * rows
	 */

	private List<TravelReqDetailCodingBlock> preSaveTravelRequisitionCodingBlocks(
			double dollarAmount, List<TravelReqDetailCodingBlock> toRoundList, UserProfile userProfile) {
		
		int listSize = toRoundList.size();
		int lastRowNumber = listSize - 1;
		double sumAfter = 0;
		
		for (int i = 0; i < listSize; i++) {
			TravelReqDetailCodingBlock item = toRoundList.get(i);
			if (i < lastRowNumber) {
				// not last row - set only amount. Rounding mode is dow if not the last row 
				item.setDollarAmount(TimeAndExpenseUtil
						.roundToTwoDigitsDown(dollarAmount * (item.getPercent())));
				sumAfter += item.getDollarAmount();
			} else {
				// last row - calculate difference and set for last row's amount
				double difference = dollarAmount - sumAfter;
				item.setDollarAmount(TimeAndExpenseUtil
						.roundToTwoDigits(difference));
			}
			item.setModifiedUserId(userProfile.getUserId());
		}
		
		return toRoundList;
	}

	public ExpenseDetailCodingBlocks getCodingBlockByCodingBlockId(
			int expenseCodingBlockId) {
		return codingBlockDAO
				.findCodingBlockByCodingBlockId(expenseCodingBlockId);
	}

	public String getFormattedDate(Date inputDate) {
		return codingBlockDAO.formatDate(inputDate);
	}
	/**
	 * Adjusts amounts for Coding Blocks in case an expense has been updated or modified
	 * @param expenseMaster
	 */
	
	public void updateExpenseDetailAmounts (ExpenseMasters expenseMaster, UserProfile userProfile){
		if (expenseMaster.getExpenseDetailsCollection().isEmpty())
			return;
		// Loop through expense details and update amount for all coding blocks rows
		// associated with each detail
		for (ExpenseDetails detail: expenseMaster.getExpenseDetailsCollection()){
			/*for (ExpenseDetailCodingBlocks cb: detail.getExpenseDetailCodingBlocksCollection()){
				cb.setDollarAmount(TimeAndExpenseUtil.roundToTwoDigits(detail.getDollarAmount() * cb.getPercent()));
			}*/
			this.preSaveCodingBlocks(detail.getDollarAmount(), 
					detail.getExpenseDetailCodingBlocksCollection(), userProfile);
		}
		codingBlockDAO.getEntityManager().flush();		
	}
	
	public DriverReimbExpTypeCbs getDriverReimbExpTypeCb(String dept, String agy, String expTypeCode) {
		DriverReimbExpTypeCbs retVal = null;
		
		List<DriverReimbExpTypeCbs> list = codingBlockDAO.findDriverReimbExpTypeCb(dept, agy, expTypeCode);
		if (list.size() > 0){
			retVal = list.get(0);
		}
		return retVal;

	}
	
	public List<DriverReimbExpTypeCbs> getDriverReimbExpTypeCbs() {
		
		return codingBlockDAO.findDriverReimbExpTypeCbs();

	}
	
	/**
	 * Validate coding block percentage
	 * @param expenseMasters
	 * @param userProfile
	 * @param expenseDetails
	 * @return
	 */
	private boolean validatePercentages(ExpenseMasters expenseMasters, UserProfile userProfile, ExpenseDetails expenseDetails){
		ActionHelper helper = new ActionHelper();
		boolean retValue = true;
		boolean percentValid = helper.validatePercentageSumTotal(expenseDetails.getExpenseDetailCodingBlocksCollection());
	
		// Insert errors into list
		if (!percentValid) {
			ExpenseErrors expenseErrors = new ExpenseErrors();
			expenseErrors.setExpmIdentifier(expenseMasters);
			ErrorMessages errorMessages = commonDsp
			.getErrorCode(IConstants.CODING_BLOCK_PERCENT_NOT_EQUAL_100);
			expenseErrors.setErrorCode(errorMessages);
			expenseErrors.setModifiedUserId(userProfile.getUserId());
			expenseErrors.setErrorSource("CB " + expenseDetails.getLineItem());
			expenseMasters.getExpenseErrorsCollection().add(expenseErrors);
			retValue = false;
		}
		return retValue;
	}
	/**
	 * Determines if an expense type is for Driver's Reimbursement
	 * @param expTypeCode
	 * @param driverReibmCbs
	 * @return
	 */
	private boolean isDriverReimbursement(String expTypeCode, List<DriverReimbExpTypeCbs> driverReibmCbs){
		boolean retValue = false;
		if (driverReibmCbs != null){
			for (DriverReimbExpTypeCbs	item:	driverReibmCbs){
				if (expTypeCode.equals(item.getExpTypeCode())){
					retValue = true;
					break;
				}
			}
		}
		return retValue;
	}
	/**
	 * Returns false if expense is of type PDF. Also adds a required error if the expense lines do not include 
	 * Coding Blocks
	 * @param expense
	 * @param userProfile
	 * @return
	 */
	public void validatePdf(ExpenseMasters expense, UserProfile userProfile) {
		if (expense.getExpenseErrorsCollection() != null && !expense.getExpenseErrorsCollection().isEmpty()){
			expense = commonDao.deleteExpenseErrors(expense, "CB");
		}
			List<ExpenseDetails> expenseDetailsList = expense
					.getExpenseDetailsCollection();
			for (ExpenseDetails item : expenseDetailsList) {
				if (item.getExpenseDetailCodingBlocksCollection().size() == 0
						|| (item.getExpenseDetailCodingBlocksCollection()
								.size() == 1 && "Y".equals(item
								.getExpenseDetailCodingBlocksCollection()
								.get(0).getStandardInd()))) {
					// either no coding blocks or only a single STD CB present
					addExpenseError(expense, item,
							IConstants.CODING_BLOCK_REQUIRED_FOR_PDF_EXPENSES,
							userProfile);
				} else {
					List<ExpenseDetailCodingBlocks> codingBlockList = item
							.getExpenseDetailCodingBlocksCollection();
					for (ExpenseDetailCodingBlocks itemCb : codingBlockList) {
						if ("Y".equals(itemCb.getStandardInd())) {
							// STD, not allowed for PDF expenses
							addExpenseError(
									expense,
									item,
									IConstants.CODING_BLOCK_REQUIRED_FOR_PDF_EXPENSES,
									userProfile);
							break;
						}
					}
				}
		}
	}
/**
 * Adds a new error to expense collection
 * @param expense
 * @param expenseDetails
 * @param errorCode
 * @param userProfile
 */
	private void addExpenseError(ExpenseMasters expense,
			ExpenseDetails expenseDetails, String errorCode,
			UserProfile userProfile) {
		ErrorMessages errorMessages = commonDsp.getErrorCode(errorCode);
		ExpenseErrors expenseErrors = new ExpenseErrors();
		expenseErrors.setExpmIdentifier(expense);
		expenseErrors.setErrorCode(errorMessages);
		expenseErrors.setModifiedUserId(userProfile.getUserId());

		expenseErrors.setErrorSource("CB " + expenseDetails.getLineItem());
		expense.getExpenseErrorsCollection().add(expenseErrors);
		codingBlockDAO.getEntityManager().persist(expenseErrors);
	}

	/**
	 * Following are the "getters" and "setters" for the DAOs used in this DSP
	 * 
	 * @return
	 */
	public CodingBlockDAO getCodingBlockDAO() {
		return codingBlockDAO;
	}

	public void setCodingBlockDAO(CodingBlockDAO codingBlockDAO) {
		this.codingBlockDAO = codingBlockDAO;
	}

	public CommonDAO getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDAO commonDao) {
		this.commonDao = commonDao;
	}

	public ExpenseDAO getExpenseDao() {
		return expenseDao;
	}

	public void setExpenseDao(ExpenseDAO expenseDao) {
		this.expenseDao = expenseDao;
	}

	public AdvanceDAO getAdvanceDao() {
		return advanceDao;
	}

	public void setAdvanceDao(AdvanceDAO advanceDao) {
		this.advanceDao = advanceDao;
	}

	public ExpenseLineItemDAO getLineItemDao() {
		return lineItemDao;
	}

	public void setLineItemDao(ExpenseLineItemDAO lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	public AppointmentDAO getApptDao() {
		return apptDao;
	}

	public void setApptDao(AppointmentDAO apptDao) {
		this.apptDao = apptDao;
	}

}
