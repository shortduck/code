/**
 * 
 */
package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
import gov.michigan.dit.timeexpense.model.core.CodingBlockElement;
import gov.michigan.dit.timeexpense.model.core.DriverReimbExpTypeCbs;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.TkuoptTaOptions;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.CodingBlockDisplay;
import gov.michigan.dit.timeexpense.model.display.CodingBlockSummaryBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseDetailsDisplayBean;
import gov.michigan.dit.timeexpense.model.display.GrantPhaseBean;
import gov.michigan.dit.timeexpense.model.display.ProjectPhaseBean;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.util.ExpenseViewUtil;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author GhoshS
 */
public class ExpenseCodingBlockAction extends AbstractAction implements ServletResponseAware, ServletRequestAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1802369469491099381L;
    private HttpServletResponse res;
    private HttpServletRequest req;

    private ExpenseLineItemDSP lineItemService;
    private CodingBlockDSP codingBlockService;
    private CommonDSP commonService;
    private AppointmentDSP appointmentService;
    private SecurityManager securityService;
    private ExpenseDSP expenseService;
    private String expenseStatus;
    private ExpenseMasters expenseMaster;
    
    private String codingBlockOptions = "";
    private String noOfCodingBlocks;
    private String result = "";
    private String codingBlockJsonFromClient;
    private boolean disableButtons = false;
    private String grantNo;
    private String projectNo;
    private String nextButton;
    private String prevButton;
    private String apprYear;
    private int expenseCodingBlockId;
    private int expDtlsId;
    private boolean isCurrentVersion = true;
    private String prevNextRequest;
    private UserSubject userSubject;
    private String statusMessage;
    private boolean isValidCodingBlock;
    private int revisionNo;
    boolean includesCbStd = false;
    private String facsAgency = "";
    private CodingBlockElement cbElement;
    List<CodingBlockDisplay> cbDisplayList;

    private static final Logger logger = Logger.getLogger(ExpenseCodingBlockAction.class);

    @Override
    public void prepare() {
	codingBlockService = new CodingBlockDSP(entityManager);
	commonService = new CommonDSP(entityManager);
	expenseService = new ExpenseDSP(entityManager);
	lineItemService = new ExpenseLineItemDSP(entityManager);
	securityService = new SecurityManager(entityManager);
	appointmentService = new AppointmentDSP(entityManager);

	expenseMaster = (ExpenseMasters) session.get(IConstants.CURR_EXPENSEMASTER);
	expenseMaster = entityManager.merge(expenseMaster);
	session.put(IConstants.CURR_EXPENSEMASTER, expenseMaster);

    }

    /**
     * This method retrieves the Expense details List. and stores it in the
     * SESSION.
     * 
     * @return
     */

    public String getExpenseDetailsList() {

	if (logger.isDebugEnabled()) {
	    logger.debug("Enter method :: getExpenseDetailsList :retrun String");
	}
	session.put(IConstants.EXPENSE_DETAIL_DELETED_CB_ROWS, null);
	// ZH, 07/08/2010 - fixed issue # 165, null pointer exceptions when ID
	// tab has not been saved.
	if (expenseMaster == null) {
	    return IConstants.SUCCESS;
	}

	setExpenseStatus(expenseMaster.getStatus());
	//Getting latest from the database. This is a temp. solution for the overnight flag bug and expense_details trigger.
	entityManager.refresh(expenseMaster);

	List<ExpenseDetails> expenseDetailsList = expenseMaster.getExpenseDetailsCollection();
	List<ExpenseDetailsDisplayBean> expDtlsDisplayList = prepareDisplayList(expenseDetailsList);

	session.put(IConstants.EXPENSE_DETAILS_LIST, expDtlsDisplayList);
	session.put(IConstants.CURR_EXPENSEMASTER, expenseMaster);

	setJsonResponse(jsonParser.toJson(expDtlsDisplayList));

	if (logger.isDebugEnabled()) {
	    logger.debug("Exit method :: getExpenseDetailsList :retrun String");
	}

	return IConstants.SUCCESS;
    }

    /**
     * This method does the following :- a. Gets the Coding block metadata to
     * determine which coding blocks to display. b. Gets the coding block data
     * for the elements c. Gets the coding block data to be displayed for the
     * selected Expense detail Line. d. Sets the coding block store, expense
     * coding metadata into Session
     * 
     * @return String
     */
    // TODO [SG] - Change all input parameter to the getPayDate() to the
    // SYSTEM_DATE
    public String getExpenseDetailCodingBlock() {

	if (logger.isDebugEnabled()) {
	    logger.debug("Enter method :: getExpenseDetailCodingBlock :retrun String");
	}

	String storeJson = "";
	String expenseCBOptions = null;
	String expenseCodingBlockJson = null;
	TkuoptTaOptions tkuOptTaOptions;
	int currYear = Calendar.getInstance().get(Calendar.YEAR);

	/*
	 * expenseMaster =
	 * (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);
	 * expenseMaster = entityManager.merge(expenseMaster);
	 */
	try {
	    UserSubject userSubjectForCbStore = (UserSubject) session.get(IConstants.USER_SUBJECT_FOR_CB_STORE);
	    //CN 183 
       
        //CN 183
	    if (userSubjectForCbStore == null
		    || (!ActionHelper.getConcatDeptAgencyTku(userSubjectForCbStore).equals(
			    ActionHelper.getConcatDeptAgencyTku(super.getUserSubject())))) {
		// need to fetch and set store with CB options in session
		session.put(IConstants.USER_SUBJECT_FOR_CB_STORE, super.getUserSubject());
		tkuOptTaOptions = codingBlockService.getCBMetaData(getUserSubject().getDepartment(), getUserSubject()
			.getAgency(), getUserSubject().getTku());
 		session.put(IConstants.CODING_BLOCK_OPTIONS, tkuOptTaOptions);
		storeJson = codingBlockService.prepareCodingBlockStoreData(Calendar.getInstance().getTime(),
			getUserSubject().getDepartment(), getUserSubject().getAgency(), getUserSubject().getTku(),
			String.valueOf(currYear).substring(2), tkuOptTaOptions);
		session.put(IConstants.CODING_BLOCK_STORE, storeJson);
	    } else {
		// session CB store will work since Dept, Agency and TKU is same
		tkuOptTaOptions = (TkuoptTaOptions) (session.get(IConstants.CODING_BLOCK_OPTIONS));
		storeJson = (String) session.get(IConstants.CODING_BLOCK_STORE);
	    }
	    String store = "{store:" + storeJson + "}";

	    expenseCBOptions = this.prepareExpenseCodingBlockMetaData(tkuOptTaOptions,expenseMaster);
	    expenseCodingBlockJson = this.prepareExpenseCodingBlocksData(expDtlsId);
	    String selectedCodingBlockData = "{selected:" + expenseCodingBlockJson + "}";

	    codingBlockOptions = "{cboption:" + expenseCBOptions + "}";
	    setCodingBlockOptions(codingBlockOptions);

	    noOfCodingBlocks = "{noOfCodingBlocks:" + noOfCodingBlocks + "}";

	    result = this.setResponseJsonString(store, codingBlockOptions, selectedCodingBlockData);

	    setResult(result);

	    setJsonResponse(result);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	if (logger.isDebugEnabled()) {
	    logger.debug("Exit method :: getExpenseDetailCodingBlock :retrun String");
	}

	return IConstants.SUCCESS;
    }

    /**
     * Builds the composite Json String to display coding block with store
     * 
     * @param store
     * @param codingBlockOptions
     * @param selectedCodingBlockData
     * @return
     */

    private String setResponseJsonString(String store, String codingBlockOptions, String selectedCodingBlockData) {
	StringBuilder buf = new StringBuilder();
	buf.append("[");
	buf.append(selectedCodingBlockData);
	buf.append(",");
	buf.append(store);
	buf.append(",");
	buf.append(codingBlockOptions);
	buf.append(",");
	buf.append(noOfCodingBlocks);
	buf.append("]");

	return buf.toString();
    }

    /**
     * prepares the ExpenseDetails display list .. ExpenseDetailsDisplayBean
     * class is used to store the values to be displayed
     * 
     * @param expenseDetailsList
     * @return List<ExpenseDetailsDisplayBean>
     */
    public List<ExpenseDetailsDisplayBean> prepareDisplayList(List<ExpenseDetails> expenseDetailsList) {

	List<ExpenseDetailsDisplayBean> expDtlsDisplayList = new ArrayList<ExpenseDetailsDisplayBean>();
	for (ExpenseDetails expDetails : expenseDetailsList) {
	    ExpenseDetailsDisplayBean displayBean = new ExpenseDetailsDisplayBean();

	    displayBean.setComments(expDetails.getComments());
	    displayBean.setDepartTime(expDetails.getDepartTime());
	    displayBean.setDollarAmount(expDetails.getDollarAmount());
	    displayBean.setDollarDifference(expDetails.getDollarDifference());
	    displayBean.setExpDate(expDetails.getExpDate());
	    displayBean.setExpdIdentifier(expDetails.getExpdIdentifier());
	    displayBean.setExpenseTypeDesc(expDetails.getExpTypeCode().getDescription());
	    displayBean.setExpenseTypeCode(expDetails.getExpTypeCode().getExpTypeCode());
	    // Set the driver reimbursement flag if expense type is driver reimbursement
	    DriverReimbExpTypeCbs dretc = ActionHelper.getDriverReimbExpTypeCb(getApplicationCache(), getUserSubject(), expDetails.getExpTypeCode().getExpTypeCode());
	    if (dretc != null){
	    	displayBean.setDriverReimbursement("Y");
	    } else {
	    	displayBean.setDriverReimbursement("N");
	    }
	    displayBean.setFromElocCity(expDetails.getFromElocCity());
	    displayBean.setFromElocStProv(expDetails.getFromElocStProv());
	    displayBean.setToElocCity(expDetails.getToElocCity());
	    displayBean.setToElocStProv(expDetails.getToElocStProv());
	    displayBean.setLineItem(expDetails.getLineItem());
	    displayBean.setMileage(expDetails.getMileage());
	    displayBean.setMileOverrideInd(expDetails.getMileOverrideInd());
	    displayBean.setModifiedDate(expDetails.getModifiedDate());
	    displayBean.setModifiedUserId(expDetails.getModifiedUserId());
	    displayBean.setOvernightInd(expDetails.getOvernightInd());
	    if (!expDetails.getExpenseDetailCodingBlocksCollection().isEmpty()
		    && ActionHelper.includesStandardCb(expDetails.getExpenseDetailCodingBlocksCollection())) {
		displayBean.setIncludesStd("Y");
	    }

	    expDtlsDisplayList.add(displayBean);
	}
	return expDtlsDisplayList;
    }

    /**
     * This method determines which Coding Block dropdown to be displayed.
     * 
     * @param dept
     * @param agency
     * @param tku
     * @return String
     * @throws Exception
     */

    public String prepareExpenseCodingBlockMetaData(TkuoptTaOptions tkuOptTaOptions,ExpenseMasters expenseMaster) throws Exception {

	// TkuoptTaOptions tkuOptTaOptions =
	// codingBlockService.getCBMetaData(dept, agency, tku);
	StringBuilder buffer = new StringBuilder();
	buffer.append("[");

	appendToCBMetaDataBuffer(buffer, "pct_", "Y");
	appendToCBMetaDataBuffer(buffer, "ay_", "Y");
	//If the expense is PDF type then index and PCA will always be shown
	if(expenseMaster.isPdfInd()){
		appendToCBMetaDataBuffer(buffer, "index_", "Y");
		appendToCBMetaDataBuffer(buffer, "pca_", "Y");
	}else{
	appendToCBMetaDataBuffer(buffer, "index_", tkuOptTaOptions.getExpIndexEntryInd());
	appendToCBMetaDataBuffer(buffer, "pca_", tkuOptTaOptions.getExpPcaEntryInd());
	}
	appendToCBMetaDataBuffer(buffer, "grant_", tkuOptTaOptions.getExpGrantEntryInd());
    
	if (tkuOptTaOptions.getExpGrantEntryInd().equals("Y"))
	    appendToCBMetaDataBuffer(buffer, "grantPhase_", "Y");
	else
	    appendToCBMetaDataBuffer(buffer, "grantPhase_", "N");

	appendToCBMetaDataBuffer(buffer, "ag1_", tkuOptTaOptions.getExpAg1EntryInd());
	appendToCBMetaDataBuffer(buffer, "project_", tkuOptTaOptions.getExpProjectEntryInd());
	if (tkuOptTaOptions.getExpProjectEntryInd().equals(("Y")))
	    appendToCBMetaDataBuffer(buffer, "projectPhase_", "Y");
	else
	    appendToCBMetaDataBuffer(buffer, "projectPhase_", "N");
	appendToCBMetaDataBuffer(buffer, "ag2_", tkuOptTaOptions.getExpAg2EntryInd());
	appendToCBMetaDataBuffer(buffer, "ag3_", tkuOptTaOptions.getExpAg3EntryInd());

	appendToCBMetaDataBuffer(buffer, "multi_", tkuOptTaOptions.getExpMultipurpEntryInd());
	appendToCBMetaDataBuffer(buffer, "std_", "Y");
	appendLastToCBMetaDataBuffer(buffer, "del_", "Y");

	buffer.append("]");

	codingBlockOptions = buffer.toString();

	return codingBlockOptions;
    }

    /**
     * Retrieves the existing Coding block data given the expense details Id.
     * 
     * @param expenseDtlsId
     * @return
     */

    public String prepareExpenseCodingBlocksData(int expenseDtlsId) {

	// ExpenseDetails expenseDetails =
	// lineItemService.getExpenseDetailByExpenseDetailsId(expenseDtlsId);
	// session.put("SELECTED_DETAILS_LIST", expenseDetails);

	List<ExpenseDetailCodingBlocks> expenseCodingBlockList = new ArrayList<ExpenseDetailCodingBlocks>();

	for (ExpenseDetails expDetails : expenseMaster.getExpenseDetailsCollection()) {
	    if (expDetails.getExpdIdentifier() == expenseDtlsId) {
		expenseCodingBlockList = expDetails.getExpenseDetailCodingBlocksCollection();
	    }
	}
	session.put(IConstants.CODING_BLOCK_LIST, expenseCodingBlockList);

	setNoOfCodingBlocks(String.valueOf(expenseCodingBlockList.size()));
	noOfCodingBlocks = String.valueOf(expenseCodingBlockList.size());

	StringBuilder buffer = new StringBuilder();
	for (int i = 0; i < expenseCodingBlockList.size(); i++) {
	    ExpenseDetailCodingBlocks codingBlock = expenseCodingBlockList.get(i);
	    buffer.append("{");

	    // appendToBuff(buffer, "pct",
	    // String.valueOf(codingBlock.getPercent()*100));
	    // ZH - commented line above
	    appendToBuff(buffer, "pct", TimeAndExpenseUtil.displayAmountTwoDigits(codingBlock.getPercent() * 100));
	    appendToBuff(buffer, "ay", codingBlock.getAppropriationYear());
	    appendToBuff(buffer, "index", codingBlock.getIndexCode());
	    appendToBuff(buffer, "pca", codingBlock.getPca());
	    appendToBuff(buffer, "grant", codingBlock.getGrantNumber());
	    appendToBuff(buffer, "expdcIdentifier", String.valueOf(codingBlock.getExpdcIdentifier()));
	    appendToBuff(buffer, "expdIdentifier", String.valueOf(codingBlock.getExpdIdentifier().getExpdIdentifier()));
	    appendToBuff(buffer, "grantPhase", codingBlock.getGrantPhase());
	    appendToBuff(buffer, "ag1", codingBlock.getAgencyCode1());
	    appendToBuff(buffer, "project", codingBlock.getProjectNumber());
	    appendToBuff(buffer, "projectPhase", codingBlock.getProjectPhase());
	    appendToBuff(buffer, "ag2", codingBlock.getAgencyCode2());
	    appendToBuff(buffer, "ag3", codingBlock.getAgencyCode3());
	    appendToBuff(buffer, "multi", codingBlock.getMultipurposeCode());
	    appendLastToBuff(buffer, "std", codingBlock.getStandardInd());

	    buffer.append("}");
	    if (i < expenseCodingBlockList.size() - 1) {
		buffer.append(",");
	    }
	}
	String retCodingBlocks = "[" + buffer.toString() + "]";
	return retCodingBlocks;
    }

    private void appendToCBMetaDataBuffer(StringBuilder buffer, String nameValue, String showValue) {
	buffer.append("{name:\"" + nameValue + "\"," + "show:\"" + showValue + "\"}");
	buffer.append(",");
    }

    private void appendLastToCBMetaDataBuffer(StringBuilder buffer, String nameValue, String showValue) {
	buffer.append("{name:\"" + nameValue + "\"," + "show:\"" + showValue + "\"}");
    }

    private void appendLastToBuff(StringBuilder buff, String property, String value) {
	buff.append(property + ":\"" + value + "\"");
    }

    private void appendToBuff(StringBuilder buff, String property, String value) {
	buff.append(property + ":\"" + value + "\"");
	buff.append(",");
    }

    /**
     * This Action is used to retrieve the Grant phase when the use selects an
     * option from the Grant dropdown
     * 
     * @return String Json
     */

    public String getGrantPhase() {

	if (userSubject == null)
	    userSubject = super.getUserSubject();
	// expenseMaster =
	// (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);

	CodingBlockElement codingBlockElement = new CodingBlockElement();
	codingBlockElement.setDeptCode(userSubject.getDepartment());
	codingBlockElement.setAgency(userSubject.getAgency());
	codingBlockElement.setTku(userSubject.getTku());
	codingBlockElement.setCbElementType(IConstants.GRA_ELEMENT_TYPE);
	codingBlockElement.setPayDate(Calendar.getInstance().getTime());

	List<GrantPhaseBean> grantPhaseList = codingBlockService.getGrantPhaseByGrantNo(codingBlockElement, grantNo);

	setJsonResponse(jsonParser.toJson(grantPhaseList));

	return IConstants.SUCCESS;
    }

    /**
     * This Action is used to retrieve the Project phase when the use selects an
     * option from the Project dropdown
     * 
     * @return String Json
     */

    public String getProjectPhase() {

	if (userSubject == null)
	    userSubject = super.getUserSubject();

	// expenseMaster =
	// (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);

	CodingBlockElement codingBlockElement = new CodingBlockElement();
	codingBlockElement.setDeptCode(userSubject.getDepartment());
	codingBlockElement.setAgency(userSubject.getAgency());
	codingBlockElement.setTku(userSubject.getTku());
	codingBlockElement.setCbElementType(IConstants.PRO_ELEMENT_TYPE);
	codingBlockElement.setPayDate(Calendar.getInstance().getTime());

	List<ProjectPhaseBean> projectPhaseList = codingBlockService.getProjectPhaseByProjectNo(codingBlockElement,
		projectNo);

	setJsonResponse(jsonParser.toJson(projectPhaseList));

	return IConstants.SUCCESS;

    }

    /**
     * Action to get the store for the given appropriation year. Invoked when
     * the user changes the "AY".
     * 
     * @return String Json
     * @throws Exception
     */

    public String getNewStoreForApprYear() throws Exception {
    	String returnStoreJson="";
	if (userSubject == null)
	    userSubject = getUserSubject();

	TkuoptTaOptions tkuOptTaOptions = (TkuoptTaOptions) (session.get(IConstants.CODING_BLOCK_OPTIONS));
	if (tkuOptTaOptions == null) {
	    tkuOptTaOptions = codingBlockService.getCBMetaData(userSubject.getDepartment(), userSubject.getAgency(),
		    userSubject.getTku());
	}

	Date dateForCbStore = null;
	// expenseMaster =
	// (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);
	if (expenseMaster != null) {
	    dateForCbStore = Calendar.getInstance().getTime();
	} else {
	    AdvanceMasters advanceMaster = (AdvanceMasters) session.get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);
	    if (advanceMaster != null) {
		dateForCbStore = advanceMaster.getRequestDate();
	    } else {
	    	TravelReqMasters treqMaster = (TravelReqMasters) session.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);
	    	 if (treqMaster != null) {
	    			dateForCbStore = Calendar.getInstance().getTime();
	    		    }
	    }
	}

	if (dateForCbStore == null)
	    dateForCbStore = Calendar.getInstance().getTime();
	//If pfd is checked then index and pca should be null.
	if (expenseMaster != null && expenseMaster.isPdfInd()){
		returnStoreJson="";
	}else{
	returnStoreJson = codingBlockService.prepareCodingBlockStoreDataIndexPcaOnly(dateForCbStore,
		userSubject.getDepartment(), userSubject.getAgency(), userSubject.getTku(), apprYear, tkuOptTaOptions);
	}
	setJsonResponse(returnStoreJson);

	return IConstants.SUCCESS;
    }

    /**
     * Action called when the user presses "Update checked rows".
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public String applyCodingBlock(CodingBlockDisplay cbDisplay) {

	/*
	 * isValidCodingBlock=
	 * this.validateExpenseCodingBlock(expCodingBlocks,expenseMaster);
	 * if(isValidCodingBlock){
	 * 
	 * } else{ return IConstants.FAILURE; } return IConstants.SUCCESS;
	 */
	return null;
    }

    private void setupCodingBlockSave() {
	if (userSubject == null)
	    userSubject = getUserSubject();

	// expenseMaster =
	// (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);

	UserProfile userProfile = super.getLoggedInUser();
	// ZH- Bug fix here
	// TODO Get the facsAgency from the SESSION
	facsAgency = codingBlockService.getFacsAgencyByExpenseDatesEmployee(expenseMaster.getExpDateFrom(),
		expenseMaster.getExpDateTo(), userSubject.getAppointmentId(), userProfile.getUserId());

	cbElement = new CodingBlockElement();
	cbElement.setDeptCode(userSubject.getDepartment());
	cbElement.setAgency(userSubject.getAgency());
	cbElement.setTku(userSubject.getTku());
	cbElement.setStatus("A");

	cbElement.setPayDate(Calendar.getInstance().getTime());

	Type cbDisplayListType = new TypeToken<List<CodingBlockDisplay>>() {
	}.getType();

	cbDisplayList = new Gson().fromJson(codingBlockJsonFromClient, cbDisplayListType);

    }

    /**
     * This method populates the ExpenseDetailCodingBlock core class from the
     * bean
     * 
     * @param codingBlocks
     * @return
     */
    private List<ExpenseDetailCodingBlocks> populateCodingBlocksFromBean(List<CodingBlockSummaryBean> codingBlocks,
	    String facsAgency) {

	List<ExpenseDetailCodingBlocks> cbList = new ArrayList<ExpenseDetailCodingBlocks>();

	for (CodingBlockSummaryBean codingBlock : codingBlocks) {
	    ExpenseDetailCodingBlocks expCodingBlock = new ExpenseDetailCodingBlocks();
	    // Redundant Check to remove any rows that have no percent. Ideally,
	    // this should be done in
	    // front-end as well but the form transferred with the Ajax calls
	    // keeps references to individual elements
	    // of deleted CB rows.
	    if (codingBlock.getPercent() == 0)
		continue;
	    expCodingBlock.setAgencyCode1(extractCode(codingBlock.getAgencyCode1()));
	    expCodingBlock.setAgencyCode2(extractCode(codingBlock.getAgencyCode2()));
	    expCodingBlock.setAgencyCode3(extractCode(codingBlock.getAgencyCode3()));
	    expCodingBlock.setAppropriationYear(codingBlock.getAppropriationYear());
	    expCodingBlock.setCodingBlock(codingBlock.getCodingBlock());
	    expCodingBlock.setExpdcIdentifier(codingBlock.getExpdcIdentifier());
	    expCodingBlock.setFacsAgy(facsAgency);
	    expCodingBlock.setGrantNumber(extractCode(codingBlock.getGrantNumber()));
	    expCodingBlock.setExpdIdentifier(codingBlock.getExpdIdentifier() == null ? null : lineItemService
		    .getExpenseDetailByExpenseDetailsId(codingBlock.getExpdIdentifier()));
	    expCodingBlock.setGrantPhase(extractCode(codingBlock.getGrantPhase()));
	    expCodingBlock.setProjectNumber(extractCode(codingBlock.getProjectNumber()));
	    expCodingBlock.setProjectPhase(extractCode(codingBlock.getProjectPhase()));
	    expCodingBlock.setIndexCode(extractCode(codingBlock.getIndexCode()));
	    expCodingBlock.setPca(extractCode(codingBlock.getPca()));
	    expCodingBlock.setMultipurposeCode(extractCode(codingBlock.getMultipurposeCode()));
	    expCodingBlock.setPercent(codingBlock.getPercent() * .01);
	    if (codingBlock.getStandardInd().equals("on"))
		expCodingBlock.setStandardInd("Y");
	    else
		expCodingBlock.setStandardInd("N");
	    // expCodingBlock.setStandardInd(codingBlock.getStandardInd());
	    // TODO ZH - This should be fixed in client, may not be easy fix.
	    // Setting in here for now to uncheck std
	    // if Appropriation Year is present.
	    if (!expCodingBlock.getAppropriationYear().equals(""))
		expCodingBlock.setStandardInd("N");
	    expCodingBlock.setExpdcIdentifier(codingBlock.getExpdcIdentifier());
	    // ZH - Not sure why this is needed. Commented for now
	    /*
	     * if(expCodingBlock.getStandardInd().equals("Y")){
	     * setStandardCodingBlockValues(expCodingBlock); }
	     */
	    cbList.add(expCodingBlock);
	}
	return cbList;
    }

    /**
     * Set the coding block values when "Standard" is selected
     * 
     * @param percent
     */
    private void setStandardCodingBlockValues(ExpenseDetailCodingBlocks codingBlock) {
	codingBlock.setAgencyCode1("");
	codingBlock.setAgencyCode2("");
	codingBlock.setAgencyCode3("");
	codingBlock.setAppropriationYear("");
	codingBlock.setCodingBlock("");
	codingBlock.setGrantNumber("");
	codingBlock.setGrantPhase("");
	codingBlock.setProjectNumber("");
	codingBlock.setProjectPhase("");
	codingBlock.setMultipurposeCode("");
    }

    /**
     * Populate expense details List of all the line items where the coding
     * block is to be applied
     * 
     * @param selectedExpDtlsRows
     * @return List<ExpenseDetails>
     */
    private List<ExpenseDetails> makeListOfSelectedExpenseDtlsItem(List<String> selectedExpDtlsRows) {

	List<ExpenseDetailsDisplayBean> expenseDtlsList = (List<ExpenseDetailsDisplayBean>) session
		.get(IConstants.EXPENSE_DETAILS_LIST);

	List<ExpenseDetails> expenseDetailsList = new ArrayList<ExpenseDetails>();

	for (ExpenseDetailsDisplayBean expDtlsDisplayBean : expenseDtlsList) {
	    for (int index = 0; index < selectedExpDtlsRows.size(); index++) {

		int expdIdentifier = expDtlsDisplayBean.getExpdIdentifier();

		if (expdIdentifier == Integer.valueOf(selectedExpDtlsRows.get(index))) {
		    ExpenseDetails expenseDetails = lineItemService.getExpenseDetailByExpenseDetailsId(expdIdentifier);

		    expenseDetailsList.add(expenseDetails);
		}
	    }
	}
	return expenseDetailsList;
    }

    /**
     * Performs the Coding block validation i. Validates that the sum of the
     * percent is always equal to 100 ii. Validates duplicate coding block
     * 
     * @param expenseCodingBlockList
     * @param expenseMasters
     * @return boolean isCodingBlockValid
     */

    private boolean validateExpenseCodingBlock(List<ExpenseDetailCodingBlocks> expenseCodingBlockList) {

	ActionHelper helper = new ActionHelper();
	boolean isCodingBlockValid = true;
	int i = 0;
	// perform coding block validations
	boolean percentValid = helper.validatePercentageSumTotal(expenseCodingBlockList);

	// Insert errors into list
	if (!percentValid) {
	    addFieldError("errors", "Coding blocks percent must equal 100.00");
	    return false;
	}

	if (expenseCodingBlockList.size() > 1) {
	    if (helper.validateIdenticalCodingBlock(expenseCodingBlockList)) {
		addFieldError("errors", "Duplicate coding block rows detected");
		return false;
	    }
	}
	return true;
    }

    /**
     * Action to save the coding blocks
     * 
     * @return
     * @throws Exception
     */

    public String saveCodingBlock() throws Exception {

	/*
	 * ExpenseMasters expenseMasters =
	 * (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);
	 * expenseMasters = entityManager.find(ExpenseMasters.class,
	 * expenseMasters.getExpmIdentifier());
	 */
	List<ExpenseDetails> expenseDetailsListToBeUpdated = null;
	// parse input coding block strings and setup meta data
	this.setupCodingBlockSave();

	for (CodingBlockDisplay cbDisplay : cbDisplayList) {
	    // extract coding blocks to be saved
	    List<ExpenseDetailCodingBlocks> codingBlockListToBeSaved = populateCodingBlocksFromBean(
		    cbDisplay.getCodingBlocks(), facsAgency);
	    // extract line items

	    // perform input data validation
	    if (!this.validateExpenseCodingBlock(codingBlockListToBeSaved)) {
		return IConstants.FAILURE;
	    }

	    expenseDetailsListToBeUpdated = this.makeListOfSelectedExpenseDtlsItem(cbDisplay.getLineItemId());
	    
	    // perform input data validation
	    if (!this.validateDriverReimbursementCodingBlocks(expenseDetailsListToBeUpdated)) {
		return IConstants.FAILURE;
	    }
	    
	    List<ExpenseDetails> originalExpenseDetailsList = expenseMaster.getExpenseDetailsCollection();
	    // loop through the Expense Details Collection and add the
	    // CodingBlock to each ExpenseDetails item
	    for (ExpenseDetails expenseDetails : expenseDetailsListToBeUpdated) {
		int originalDetailIndex = originalExpenseDetailsList.indexOf(expenseDetails);
		ExpenseDetails originalDetail = originalExpenseDetailsList.get(originalDetailIndex);
		// create or update coding blocks
		this.processInputCodingBlocks(codingBlockListToBeSaved, expenseDetails, originalDetail);
		// Remove deleted coding blocks
		this.removeCodingBlocks(codingBlockListToBeSaved,
			originalDetail.getExpenseDetailCodingBlocksCollection());
	    }
	    // redundant error check, in order to ensure that errors did not
	    // result from coding block
	    // input or update processing
	    if (!this.validateExpenseCodingBlock(codingBlockListToBeSaved)) {
		// override implicit commit by intercepter
		entityManager.getTransaction().rollback();
		return IConstants.FAILURE;
	    }

	    try {

		// delete errors prior to attempting a save
				commonService.deleteExpenseErrors(expenseMaster,
						IConstants.EXP_ERR_SRC_CB_TAB);
				List<DriverReimbExpTypeCbs> driverReibmCbs = (List<DriverReimbExpTypeCbs>) getApplicationCache()
						.get("DRIVER_REIMB_CODING_BLOCKS");
				expenseMaster = codingBlockService.saveCodingBlocks(
						expenseMaster, expenseDetailsListToBeUpdated,
						cbElement, super.getLoggedInUser(), userSubject,
						driverReibmCbs);
				session.put(IConstants.CURR_EXPENSEMASTER, expenseMaster);

		String jsonResponse = "{saveStatus:\"success\"" + "}";
		super.setJsonResponse(jsonResponse);

	    } catch (OptimisticLockException e) {
		addFieldError("errors", "The record you are attempting to change has already been modified by another user.  Please reopen the record before making any changes.");
	    }
	}
	// propagate expense errors
	ExpenseViewUtil util = new ExpenseViewUtil();
	util.setJsonParser(jsonParser);
	util.setCodingBlockService(codingBlockService);
	util.setAppointmentService(appointmentService);
	addTimeAndExpenseErrors(util.prepareTimeAndExpenseErrors(expenseMaster));
	return IConstants.SUCCESS;
    }

    private void processInputCodingBlocks(List<ExpenseDetailCodingBlocks> codingBlockListToBeSaved,
	    ExpenseDetails expenseDetailsToBeUpdated, ExpenseDetails originalDetail) {
	for (ExpenseDetailCodingBlocks cbFromClient : codingBlockListToBeSaved) {
	    boolean foundCBMatch = false;
	    for (ExpenseDetailCodingBlocks originalCB : originalDetail.getExpenseDetailCodingBlocksCollection()) {
		String concatCbFromClient = ActionHelper.getConcatCodingBlockValue(cbFromClient);
		String concatCbExisting = ActionHelper.getConcatCodingBlockValue(originalCB);
		if (concatCbFromClient.equals(concatCbExisting)) {
		    updateExistingCodingBlock(originalCB, cbFromClient);
		    foundCBMatch = true;
		    break;
		}
	    }

	    if (!foundCBMatch) {
		this.createNewCodingBlock(cbFromClient, expenseDetailsToBeUpdated, originalDetail);
	    }

	}
    }

    private void createNewCodingBlock(ExpenseDetailCodingBlocks cbFromClient, ExpenseDetails expenseDetailsToBeUpdated,
	    ExpenseDetails originalDetail) {
	// new coding block
	ExpenseDetailCodingBlocks cbCopy = new ExpenseDetailCodingBlocks(cbFromClient);
	if (cbFromClient.getStandardInd().equals("Y")) {
	    setStandardCodingBlockValues(cbCopy);
	}
	cbCopy.setExpdIdentifier(expenseDetailsToBeUpdated);
	cbCopy.setPercent(cbCopy.getPercent());
	// cbCopy.setCodingBlock(Long.toString(System.nanoTime()));
	cbCopy.setDollarAmount(originalDetail.getDollarAmount() * cbCopy.getPercent());
	originalDetail.getExpenseDetailCodingBlocksCollection().add(cbCopy);
    }

    private void removeCodingBlocks(List<ExpenseDetailCodingBlocks> newCbList,
	    List<ExpenseDetailCodingBlocks> existingCbList) {

	List<ExpenseDetailCodingBlocks> newCbListTemp = ActionHelper.copyCbList(newCbList);
	/*
	 * List<ExpenseDetailCodingBlocks> newCbListTemp = new
	 * ArrayList<ExpenseDetailCodingBlocks> ();
	 * Collections.copy(newCbListTemp, newCbList);
	 */

	Iterator it = existingCbList.iterator();
	while (it.hasNext()) {
	    boolean found = false;
	    ExpenseDetailCodingBlocks existingCbElement = (ExpenseDetailCodingBlocks) it.next();
	    if (existingCbElement.getExpdcIdentifier() == null) {
		// new row added
		continue;
	    }
	    Iterator it1 = newCbListTemp.iterator();

	    while (it1.hasNext()) {
		ExpenseDetailCodingBlocks newCbElement = (ExpenseDetailCodingBlocks) it1.next();

		if (existingCbElement.getCodingBlock().trim() != null) {
		    String newConcatCB = ActionHelper.getConcatCodingBlockValue(newCbElement);
		    if (newConcatCB.equals("")) {
			if (existingCbElement.getStandardInd().equals("Y")) {
			    found = true;
			    it1.remove();
			    break;
			}
		    } else {
			String existingConcatCb = existingCbElement.getCodingBlock().trim();
			if (StringUtils.isEmpty(existingConcatCb))
			    existingConcatCb = ActionHelper.getConcatCodingBlockValue(existingCbElement);
			if (newConcatCB.equals(existingConcatCb)) {
			    found = true;
			    it1.remove();
			    break;
			}
		    } /*
		       * else found = false;
		       */
		}
		/*
		 * else{ // new element added found = true; break; }
		 */
	    }

	    if (!found) {
		it.remove();
		// entityManager.refresh(expenseMaster);
		// expenseMaster = entityManager.merge(expenseMaster);
		// entityManager.remove(cbElement);
	    }
	}

	/*
	 * if (!includesCbStd) this.checkStdCodingBlock(existingCbList);
	 */

    }

    private void checkStdCodingBlock(List<ExpenseDetailCodingBlocks> existingCbList) {
	Iterator it = existingCbList.iterator();
	while (it.hasNext()) {
	    ExpenseDetailCodingBlocks cbElement = (ExpenseDetailCodingBlocks) it.next();
	    if (cbElement.getStandardInd().equals("Y")) {
		it.remove();
		entityManager.remove(cbElement);
	    }
	}

    }

    /**
     * @param originalCB
     * @param cbFromClient
     */
    private void updateExistingCodingBlock(ExpenseDetailCodingBlocks originalCB, ExpenseDetailCodingBlocks cbFromClient) {

	// originalCB.setCodingBlock(Long.toString(System.nanoTime()));
	originalCB.setAgencyCode1(cbFromClient.getAgencyCode1());
	originalCB.setAgencyCode2(cbFromClient.getAgencyCode2());
	originalCB.setAgencyCode3(cbFromClient.getAgencyCode3());
	originalCB.setAppropriationYear(cbFromClient.getAppropriationYear());
	originalCB.setIndexCode(cbFromClient.getIndexCode());
	originalCB.setPca(cbFromClient.getPca());
	originalCB.setGrantNumber(cbFromClient.getGrantNumber());
	originalCB.setGrantPhase(cbFromClient.getGrantPhase());
	originalCB.setProjectNumber(cbFromClient.getProjectNumber());
	originalCB.setProjectPhase(cbFromClient.getProjectPhase());
	originalCB.setMultipurposeCode(cbFromClient.getMultipurposeCode());
	originalCB.setStandardInd(cbFromClient.getStandardInd());

	originalCB.setFacsAgy(cbFromClient.getFacsAgy());
	originalCB.setDollarAmount((originalCB.getExpdIdentifier().getDollarAmount() * cbFromClient.getPercent()));
	originalCB.setPercent(cbFromClient.getPercent());

    }

    /*
     * ZH - Modified to save deleted rows in session first. The flush will take
     * place upon Save
     */

    public String deleteCodingBlock() {

	// int expdcId = Integer.valueOf(req.getParameter("expdcId"));
	ExpenseMasters expenseMaster = (ExpenseMasters) session.get(IConstants.CURR_EXPENSEMASTER);
	List<ExpenseDetailCodingBlocks> expenseCodingBlockToBeDeletedList = (ArrayList<ExpenseDetailCodingBlocks>) session
		.get("DeleteExpenseCodingBlocksList");
	if (expenseCodingBlockToBeDeletedList == null)
	    expenseCodingBlockToBeDeletedList = new ArrayList<ExpenseDetailCodingBlocks>();
	ExpenseDetailCodingBlocks expenseCodingBlockToBeDeleted = codingBlockService
		.getCodingBlockByCodingBlockId(expenseCodingBlockId);
	// codingBlockService.deleteExpenseDetailCodingBlocks(expenseCodingBlockToBeDeleted,
	// expenseMaster);
	expenseCodingBlockToBeDeletedList.add(expenseCodingBlockToBeDeleted);
	session.put("DeleteExpenseCodingBlocksList", expenseCodingBlockToBeDeletedList);
	addErrors(expenseMaster);

	/*
	 * List<ExpenseDetailCodingBlocks> expenseCBList =
	 * (List<ExpenseDetailCodingBlocks
	 * >)session.get(IConstants.CODING_BLOCK_LIST);
	 * Iterator<ExpenseDetailCodingBlocks> iter = expenseCBList.iterator();
	 * List<ExpenseDetailCodingBlocks> updatedExpenseCBList = new
	 * ArrayList<ExpenseDetailCodingBlocks>();
	 * 
	 * while(iter.hasNext()){ ExpenseDetailCodingBlocks expCodingBlock =
	 * (ExpenseDetailCodingBlocks)iter.next();
	 * if(!expCodingBlock.getExpdcIdentifier
	 * ().equals(expenseCodingBlockId)){
	 * updatedExpenseCBList.add(expCodingBlock); break; } }
	 * session.put(IConstants.CODING_BLOCK_LIST, updatedExpenseCBList);
	 */

	return IConstants.SUCCESS;
    }

    private void addErrors(ExpenseMasters expenseMaster) {

	/*
	 * if (.getExpenseErrorsCollection() != null) { if
	 * (!expenseMaster.getExpenseErrorsCollection().isEmpty()) { for
	 * (ExpenseErrors expError : expenseMaster.getExpenseErrorsCollection())
	 * { String code = expError.getErrorCode().getErrorCode(); ErrorMessages
	 * error = commonService.getErrorCode(code); String errorSource =
	 * ActionHelper.formatCbErrorSource(expenseMaster,
	 * expError.getErrorSource()); String description =
	 * error.getErrorText(); String severity = error.getSeverity(); Long
	 * errorIdentifier = 0L; if (expError.getExperIdentifier() != null)
	 * errorIdentifier = expError.getExperIdentifier().longValue(); else
	 * errorIdentifier = System.nanoTime();
	 * 
	 * TimeAndExpenseError displayError = new
	 * TimeAndExpenseError(errorIdentifier.intValue(), code, description,
	 * severity, errorSource); super.addTimeAndExpenseError(displayError); }
	 * } }
	 */
    }

    private String extractCode(String str) {
	String result = "";

	int index = str.indexOf("   ");
	if (index > 0) {
	    result = str.substring(0, index);
	} else
	    // ZH - Added to get typed in Strings
	    result = str;
	return result;
    }

    public String getNextRevisionCodingBlock() {

	// expenseMaster =
	// (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);
	int expenseEventId = expenseMaster.getExpevIdentifier().getExpevIdentifier();
	int nextRevision = expenseMaster.getRevisionNumber() + 1;

	ExpenseMasters nextExpMasters = expenseService.getExpenseByExpenseEventId(expenseEventId, nextRevision);
	if (nextExpMasters != null) {
	    expenseMaster = expenseService.getExpense(nextExpMasters.getExpmIdentifier());
	    session.put(IConstants.CURR_EXPENSEMASTER, expenseMaster);
	    isCurrentVersion = false;
	    prevNextRequest = "Y";
	    getExpenseDetailsList();
	}
	return IConstants.SUCCESS;
    }

    public String getPrevRevisionCodingBlock() {
	// expenseMaster =
	// (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);
	int expenseEventId = expenseMaster.getExpevIdentifier().getExpevIdentifier();
	int nextRevision = expenseMaster.getRevisionNumber() - 1;

	ExpenseMasters nextExpMasters = expenseService.getExpenseByExpenseEventId(expenseEventId, nextRevision);
	if (nextExpMasters != null) {
	    expenseMaster = expenseService.getExpense(nextExpMasters.getExpmIdentifier());
	    session.put(IConstants.CURR_EXPENSEMASTER, expenseMaster);
	    isCurrentVersion = false;
	    prevNextRequest = "Y";
	    getExpenseDetailsList();
	}
	return IConstants.SUCCESS;
    }

    public void setupDisplay() {

	UserProfile userprofile = (UserProfile) session.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
	String moduleId = (String) session.get(IConstants.LEFT_NAV_CURRENT_MODULE_ID);

	// TODO [SG] - Reset the Modify button session value
	// boolean isModifyMode =
	// (Boolean)session.get(IConstants.MODIFY_BUTTON_STATE_SESSION);
	boolean isModifyMode = false;
	// ExpenseMasters expenseMaster =
	// (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);
	boolean isModuleAccessible = securityService.checkModuleAccess(userprofile, moduleId);

	if (isModifyMode) {
	    if (expenseMaster.getStatus().equals(IConstants.SUBMIT)
		    || expenseMaster.getStatus().equals(IConstants.APPROVED)
		    || expenseMaster.getStatus().equals(IConstants.PROCESSED)
		    || expenseMaster.getStatus().equals(IConstants.HOURS_ADJUSTMENT_SENT)
		    || expenseMaster.getStatus().equals(IConstants.EXTRACTED)) {
		disableButtons = true;
	    } else {
		if (isModuleAccessible || !isCurrentVersion)
		    disableButtons = false;
		else
		    disableButtons = true;
	    }
	}

	if (expenseMaster != null)
	    setRevisionNo(expenseMaster.getRevisionNumber());

    }
    /**
     * redundant check - not allow CBs to be changed if Drivers Reimbursement
     * @param detailsList
     * @return
     */
    private boolean validateDriverReimbursementCodingBlocks (List<ExpenseDetails> detailsList){
    	for (ExpenseDetails item: detailsList){
    		DriverReimbExpTypeCbs dretc = ActionHelper.getDriverReimbExpTypeCb(getApplicationCache(), getUserSubject(), item.getExpTypeCode().getExpTypeCode());
    		  if (dretc != null){
    			  addFieldError("errors", "Coding blocks for Driver Reimbursement may not be modified");
    			  return false;
    		    } 
    	}
    	return true;
    }

    public String getCodingBlockOptions() {
	return codingBlockOptions;
    }

    public void setCodingBlockOptions(String codingBlockOptions) {
	this.codingBlockOptions = codingBlockOptions;
    }

    public String getNoOfCodingBlocks() {
	return noOfCodingBlocks;
    }

    public void setNoOfCodingBlocks(String noOfCodingBlocks) {
	this.noOfCodingBlocks = noOfCodingBlocks;
    }

    public void setServletResponse(HttpServletResponse res) {
	this.res = res;
    }

    public void setServletRequest(HttpServletRequest req) {
	this.req = req;
    }

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

    public String getCodingBlockJsonFromClient() {
	return codingBlockJsonFromClient;
    }

    public void setCodingBlockJsonFromClient(String codingBlockJsonFromClient) {
	this.codingBlockJsonFromClient = codingBlockJsonFromClient;
    }

    public String getExpenseStatus() {
	return expenseStatus;
    }

    public void setExpenseStatus(String expenseStatus) {
	this.expenseStatus = expenseStatus;
    }

    public boolean isDisableButtons() {
	return disableButtons;
    }

    public void setDisableButtons(boolean disableButtons) {
	this.disableButtons = disableButtons;
    }

    public String getGrantNo() {
	return grantNo;
    }

    public void setGrantNo(String grantNo) {
	this.grantNo = grantNo;
    }

    public String getProjectNo() {
	return projectNo;
    }

    public void setProjectNo(String projectNo) {
	this.projectNo = projectNo;
    }

    public String getNextButton() {
	return nextButton;
    }

    public void setNextButton(String nextButton) {
	this.nextButton = nextButton;
    }

    public String getPrevButton() {
	return prevButton;
    }

    public void setPrevButton(String prevButton) {
	this.prevButton = prevButton;
    }

    public ExpenseMasters getExpenseMaster() {
	return expenseMaster;
    }

    public void setExpenseMasters(ExpenseMasters expenseMaster) {
	this.expenseMaster = expenseMaster;
    }

    public String getApprYear() {
	return apprYear;
    }

    public void setApprYear(String apprYear) {
	this.apprYear = apprYear;
    }

    public int getExpenseCodingBlockId() {
	return expenseCodingBlockId;
    }

    public void setExpenseCodingBlockId(int expenseCodingBlockId) {
	this.expenseCodingBlockId = expenseCodingBlockId;
    }

    public String getPrevNextRequest() {
	return prevNextRequest;
    }

    public void setPrevNextRequest(String prevNextRequest) {
	this.prevNextRequest = prevNextRequest;
    }

    public String getStatusMessage() {
	return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
	this.statusMessage = statusMessage;
    }

    public boolean isValidCodingBlock() {
	return isValidCodingBlock;
    }

    public void setValidCodingBlock(boolean isValidCodingBlock) {
	this.isValidCodingBlock = isValidCodingBlock;
    }

    public int getExpDtlsId() {
	return expDtlsId;
    }

    public void setExpDtlsId(int expDtlsId) {
	this.expDtlsId = expDtlsId;
    }

    public void setRevisionNo(int revisionNo) {
	this.revisionNo = revisionNo;
    }

    public int getRevisionNo() {
	return revisionNo;
    }

}
