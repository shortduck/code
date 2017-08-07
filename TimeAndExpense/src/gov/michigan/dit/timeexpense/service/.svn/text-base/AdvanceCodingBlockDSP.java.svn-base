/**
 * Contains business logic for Coding block services
 */
package gov.michigan.dit.timeexpense.service;

import gov.michigan.dit.timeexpense.action.ActionHelper;
import gov.michigan.dit.timeexpense.dao.CodingBlockDAO;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetails;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.CodingBlockElement;
import gov.michigan.dit.timeexpense.model.core.DefaultDistributionsAdvAgy;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.TkuoptTaOptions;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.display.Ag1Bean;
import gov.michigan.dit.timeexpense.model.display.Ag2Bean;
import gov.michigan.dit.timeexpense.model.display.Ag3Bean;
import gov.michigan.dit.timeexpense.model.display.GrantBean;
import gov.michigan.dit.timeexpense.model.display.IndexCodesBean;
import gov.michigan.dit.timeexpense.model.display.MultiBean;
import gov.michigan.dit.timeexpense.model.display.PcaBean;
import gov.michigan.dit.timeexpense.model.display.ProjectBean;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * @author hussainz
 *
 */
public class AdvanceCodingBlockDSP {
	
	private CommonDSP commonService;
	private CodingBlockDSP codingBlockService;
	private CommonDAO commonDao;
	private String cbOptions = "";
	Logger logger = Logger.getLogger(AdvanceDSP.class);
	private EntityManager entityManager;
	
	public AdvanceCodingBlockDSP (EntityManager entityManager){
		codingBlockService = new CodingBlockDSP (entityManager);
		this.entityManager = entityManager;
	}
	
	/**
	 * Sets up data for retrieving coding blocks
	 * @return
	 */
	
	public CodingBlockElement setupCbElement (){
		String dept = "59";
		String agency = "01";
		String tku = "001";
		
		Date payDate = Calendar.getInstance().getTime();

		CodingBlockElement cbElement = new CodingBlockElement();
		cbElement.setDeptCode(dept);
		cbElement.setAgency(agency);
		cbElement.setTku(tku);
		cbElement.setStatus("A");
		cbElement.setPayDate(payDate);
		cbElement.setAppropriationYear("08");
		return cbElement;
	}
	
	/**
	 * Prepares store values for display
	 * 
	 * @param dept
	 * @param agency
	 * @param tku
	 * @param apprYear
	 * @return
	 * @throws Exception
	 */
	
	public String prepareCodingBlockStoreData(String dept, String agency, String tku, String apprYear, TkuoptTaOptions tkuOptTaOptions)
			throws Exception {

		Date requestDate = Calendar.getInstance().getTime();

		List codingBlockList = codingBlockService.findCodingBlocksDetails(dept,
				agency, tku, tkuOptTaOptions, requestDate, apprYear);
		
		return codingBlockService.prepareCodingBlockStoreData(requestDate, dept, agency, tku, apprYear, tkuOptTaOptions);
		}
	
	/**
	 * Prepares meta data for display, i.e. which elements to show
	 * @param dept
	 * @param agency
	 * @param tku
	 * @return
	 * @throws Exception
	 */
	
	public String prepareAdvanceCodingBlockMetaData(String dept, String agency, String tku, TkuoptTaOptions tkuOptTaOptions) throws Exception {

		/*tkuOptTaOptions = codingBlockService.getCBMetaData(
				dept, agency, tku);*/
		StringBuilder buffer = new StringBuilder();
		buffer.append("[");

		appendToCBMetaDataBuffer(buffer, "pct_", "Y");
		appendToCBMetaDataBuffer(buffer, "ay_", "Y");
		appendToCBMetaDataBuffer(buffer, "index_", tkuOptTaOptions
				.getAdvIndexEntryInd());
		appendToCBMetaDataBuffer(buffer, "pca_", tkuOptTaOptions
				.getAdvPcaEntryInd());
		appendToCBMetaDataBuffer(buffer, "grant_", tkuOptTaOptions
				.getAdvGrantEntryInd());

		if (tkuOptTaOptions.getAdvGrantEntryInd().equals("Y"))
			appendToCBMetaDataBuffer(buffer, "grantPhase_", "Y");
		else
			appendToCBMetaDataBuffer(buffer, "grantPhase_", "N");

		appendToCBMetaDataBuffer(buffer, "ag1_", tkuOptTaOptions
				.getAdvAg1EntryInd());
		appendToCBMetaDataBuffer(buffer, "project_", tkuOptTaOptions
				.getAdvProjectEntryInd());
		if (tkuOptTaOptions.getAdvProjectEntryInd().equals(("Y")))
			appendToCBMetaDataBuffer(buffer, "projectPhase_", "Y");
		else
			appendToCBMetaDataBuffer(buffer, "projectPhase_", "N");
		
		appendToCBMetaDataBuffer(buffer, "ag2_", tkuOptTaOptions
				.getAdvAg2EntryInd());
		appendToCBMetaDataBuffer(buffer, "ag3_", tkuOptTaOptions
				.getAdvAg3EntryInd());

		appendToCBMetaDataBuffer(buffer, "multi_", tkuOptTaOptions
				.getAdvMultipurpEntryInd());
		appendToCBMetaDataBuffer(buffer, "std_", "Y");
		appendLastToCBMetaDataBuffer(buffer, "del_", "Y");

		buffer.append("]");

		cbOptions = "{cboption:" + buffer.toString() + "}";

		return cbOptions;
	}
	
	public TkuoptTaOptions getCbMetaData (String dept, String agency, String tku){
		return codingBlockService.getCBMetaData(
				dept, agency, tku);
	}
	
	/**
	 * Used to setup existing coding block data for an advance
	 * 
	 * @param advanceMaster
	 * @return
	*  String
	 */

	public String prepareAdvanceCodingBlocks(AdvanceMasters advanceMaster) {

		AdvanceDetails advanceDetails = null;
		String retCodingBlocks = "";

		List<AdvanceDetails> advanceDetailsList = advanceMaster
				.getAdvanceDetailsCollection();

		advanceDetails = advanceDetailsList.get(0);
		if ((advanceDetails.getAdvanceDetailCodingBlocksCollection()) != null
				&& (!advanceDetails.getAdvanceDetailCodingBlocksCollection()
						.isEmpty())) {
			StringBuilder buffer = new StringBuilder();
			for (int i = 0; i < advanceDetails
					.getAdvanceDetailCodingBlocksCollection().size(); i++) {
				AdvanceDetailCodingBlocks codingBlock = advanceDetails
						.getAdvanceDetailCodingBlocksCollection().get(i);
				buffer.append("{");

				appendToBuff(buffer, "pct", TimeAndExpenseUtil.displayAmountTwoDigits(codingBlock
						.getPercent()*100));
				appendToBuff(buffer, "ay", codingBlock.getAppropriationYear());
				appendToBuff(buffer, "index", codingBlock.getIndexCode());
				appendToBuff(buffer, "pca", codingBlock.getPca());
				appendToBuff(buffer, "grant", codingBlock.getGrantNumber());
				appendToBuff(buffer, "grantPhase", codingBlock.getGrantPhase());
				appendToBuff(buffer, "ag1", codingBlock.getAgencyCode1());
				appendToBuff(buffer, "project", codingBlock.getProjectNumber());
				appendToBuff(buffer, "projectPhase", codingBlock
						.getProjectPhase());
				appendToBuff(buffer, "ag2", codingBlock.getAgencyCode2());
				appendToBuff(buffer, "ag3", codingBlock.getAgencyCode3());
				appendToBuff(buffer, "multi", codingBlock.getMultipurposeCode());
				appendLastToBuff(buffer, "std", codingBlock.getStandardInd());

				buffer.append("}");
				if (i < advanceDetails.getAdvanceDetailCodingBlocksCollection()
						.size() - 1) {
					buffer.append(",");
				}
			}

			retCodingBlocks = "[" + buffer.toString() + "]";
		}

		return retCodingBlocks;
	}
	/**
	 * Used to prepare store json string
	 * @param buffer
	 * @param nameValue
	 * @param showValue
	 */
	private void appendToCBMetaDataBuffer(StringBuilder buffer,
			String nameValue, String showValue) {
		buffer.append("{name:\"" + nameValue + "\"," + "show:\"" + showValue
				+ "\"}");
		buffer.append(",");
	}
	
	/**
	 * Used to prepare store json string, specially to prepare the string tail
	 * @param buffer
	 * @param nameValue
	 * @param showValue
	 */
	
	private void appendLastToCBMetaDataBuffer(StringBuilder buffer,
			String nameValue, String showValue) {
		buffer.append("{name:\"" + nameValue + "\"," + "show:\"" + showValue
				+ "\"}");
	}
	
	/**
	 * Service method. Used for string prep for coding block data
	 * @param buff
	 * @param property
	 * @param value
	*  void
	 */

	private void appendToBuff(StringBuilder buff, String property, String value) {
		buff.append(property + ":\"" + value + "\"");
		buff.append(",");
	}
	
	/**
	 * Service method. Used for string prep for coding block data
	 * @param buff
	 * @param property
	 * @param value
	*  void
	 */
	
	private void appendLastToBuff(StringBuilder buff, String property,
			String value) {
		buff.append(property + ":\"" + value + "\"");
	}
	
	/**
	 * Get the PL Std coding block
	 * @param dept
	 * @param agency
	 * @return
	 */
	
	public DefaultDistributionsAdvAgy getDefaultAgencyAdvanceCodingBlock (String dept, String agency, Date requestDate){
		List<DefaultDistributionsAdvAgy> defCbAgencyList = codingBlockService.getDefaultAgencyAdvanceCodingBlock(dept, agency, requestDate);
		if (!defCbAgencyList.isEmpty())
			return  defCbAgencyList.get(0);
		else
			return null;
	}
	
	private AdvanceDetailCodingBlocks getStandard (List<AdvanceDetailCodingBlocks> cbList){
		for (AdvanceDetailCodingBlocks item: cbList){
			if (item.getStandardInd().equals("Y"))
				return item;
		}
		return null;
	}
	
	private AdvanceDetailCodingBlocks getExistingCbElement (List<AdvanceDetailCodingBlocks> cbList, String concatCodingBlock){
		for (AdvanceDetailCodingBlocks item: cbList){
			if (item.getCodingBlock().trim().equals(concatCodingBlock))
				return item;
		}
		return null;
	}
	
	/**
	 * Sets decimal percentages, amounts and performs rounding for amounts
	 */

	public List<AdvanceDetailCodingBlocks> preSaveCodingBlocks(
			double dollarAmount, List<AdvanceDetailCodingBlocks> toRoundList,
			UserProfile userProfile) {

		int listSize = toRoundList.size();
		int lastRowNumber = listSize - 1;
		double sumAfter = 0;
		
		for (int i = 0; i < listSize; i++) {
			AdvanceDetailCodingBlocks item = toRoundList.get(i);
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
	 * Merge new coding blocks with the existing CBs
	 * 
	 * @param newCodingblockList
	 */

	public void mergeCodingBlocks(AdvanceMasters advanceMaster, 
			List<AdvanceDetailCodingBlocks> newCodingblockList, boolean includesCbStd) {

		List<AdvanceDetailCodingBlocks> existingCbList = advanceMaster
				.getAdvanceDetailsCollection().get(0)
				.getAdvanceDetailCodingBlocksCollection();
		List<AdvanceDetailCodingBlocks> mergedCbList = new ArrayList<AdvanceDetailCodingBlocks>();

		Iterator it = newCodingblockList.iterator();

		while (it.hasNext()) {
			AdvanceDetailCodingBlocks item = (AdvanceDetailCodingBlocks) it
					.next();

			if (("Y").equals(item.getStandardInd())) {
				AdvanceDetailCodingBlocks existingStd = this
						.getStandard(existingCbList);
				if (existingStd != null) {
					// if (existingStd.getPercent()*100 != item.getPercent())
					existingStd.setPercent(item.getPercent());
				} else
					existingCbList.add(item);

			}

			else {

				String concatCodingBlock = ActionHelper
						.getConcatCodingBlockValue(item);
				logger.info("mergeCodingBlocks: non standard. Concat CB is: "
						+ concatCodingBlock);
				AdvanceDetailCodingBlocks existingCbElement = this
						.getExistingCbElement(existingCbList, concatCodingBlock);
				if (existingCbElement != null) {
					existingCbElement.setPercent(item.getPercent());
				}

				else {
					existingCbList.add(item);
				}
			}
			// it.remove();
		}

		// advanceMaster.getAdvanceDetailsCollection().get(0).setAdvanceDetailCodingBlocksCollection(mergedCbList);
		// advanceService.getAdvanceDao().deleteAdvanceDetailCodingBlocks
		// (existingCbList);
		if (!newCodingblockList.isEmpty())
			this.removeCodingBlocks(newCodingblockList, existingCbList, includesCbStd);
	}

	/**
	 * Remove CBs from the database that have been deleted by the user
	 * 
	 * @param newCbList
	 * @param existingCbList
	 */

	private void removeCodingBlocks(List<AdvanceDetailCodingBlocks> newCbList,
			List<AdvanceDetailCodingBlocks> existingCbList, boolean includesCbStd) {

		Iterator it = existingCbList.iterator();
		while (it.hasNext()) {
			boolean found = false;
			AdvanceDetailCodingBlocks cbElement = (AdvanceDetailCodingBlocks) it
					.next();
			Iterator it1 = newCbList.iterator();
			while (it1.hasNext()) {
				AdvanceDetailCodingBlocks item = (AdvanceDetailCodingBlocks) it1
						.next();
				if (cbElement.getCodingBlock() != null) {
					String newConcatCB = ActionHelper
							.getConcatCodingBlockValue(item);
					if (newConcatCB.equals("")) {
						if (cbElement.getStandardInd().equals("Y")) {
							found = true;
							it1.remove();
							break;
						}
					} else if (cbElement.getCodingBlock() != null
							&& newConcatCB.equals(cbElement.getCodingBlock()
									.trim())) {
						found = true;
						it1.remove();
						break;
					} else if (newConcatCB.equals(ActionHelper
							.getConcatCodingBlockValue(cbElement))) {
						found = true;
						it1.remove();
						break;
					} /*
					 * else found = false;
					 */
				} else {
					found = true;
					break;
				}
			}

			if (!found) {
				it.remove();
				entityManager.remove(cbElement);
			}
		}

		if (!includesCbStd)
			this.deleteStdCodingBlock(existingCbList);

	}
	
	private void deleteStdCodingBlock(
			List<AdvanceDetailCodingBlocks> existingCbList) {
		Iterator it = existingCbList.iterator();
		while (it.hasNext()) {
			AdvanceDetailCodingBlocks cbElement = (AdvanceDetailCodingBlocks) it
					.next();
			if (cbElement.getStandardInd().equals("Y")) {
				it.remove();
				entityManager.remove(cbElement);
			}
		}

	}
	

	public void setCommonService(CommonDSP commonService) {
		this.commonService = commonService;
	}

	public CommonDSP getCommonService() {
		return commonService;
	}
	
	public CommonDAO getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDAO commonDao) {
		this.commonDao = commonDao;
	}

	public String getCbOptions() {
		return cbOptions;
	}

	public void setCbOptions(String cbOptions) {
		this.cbOptions = cbOptions;
	}
	
	public TkuoptTaOptions getTkuoptTaOptions (String dept, String agency, String tku){
		return codingBlockService.getCBMetaData(dept, agency, tku);
	}
	

}
