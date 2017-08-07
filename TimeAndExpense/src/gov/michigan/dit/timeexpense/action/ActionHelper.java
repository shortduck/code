package gov.michigan.dit.timeexpense.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

import gov.michigan.dit.timeexpense.model.core.AdvanceDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.AdvanceDetails;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.DriverReimbExpTypeCbs;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetailCodingBlock;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetails;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.ErrorDisplayBean;
import gov.michigan.dit.timeexpense.model.display.PreviousActionAttributes;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

@SuppressWarnings("unchecked")
public class ActionHelper {
	
	/**
	 * Sets user selected appointment in case of true multiples
	 * @param appt
	 * @param subject
	 */
	
	static Logger logger = Logger.getLogger(ActionHelper.class);

	public void updateUserSubjectWithCorrectAppointment(AppointmentsBean appt,
			UserSubject subject) {

		subject.setEmployeeId((int) appt.getEmp_identifier());
		subject.setAppointmentId((int) appt.getAppt_identifier());
		subject.setAppointmentStart(appt.getStart_date());
		subject.setAppointmentEnd(appt.getEnd_date());
		subject.setPositionId(appt.getPosition_id());
		subject.setDepartment(appt.getDepartment());
		subject.setAgency(appt.getAgency());
		subject.setTku(appt.getTku());

		// Set flag to indicate no multiple appt check required beyond this.
		subject.setSingleAppointmentChosen(false);
	}
	
	/**
	 * Creates a new CB list for temporary processing
	 * @param newCbList
	 * @return
	 */

	public static List<ExpenseDetailCodingBlocks> copyCbList(
			List<ExpenseDetailCodingBlocks> newCbList) {
		List<ExpenseDetailCodingBlocks> returnCbList = new ArrayList<ExpenseDetailCodingBlocks>();
		for (ExpenseDetailCodingBlocks item : newCbList) {
			returnCbList.add(item);
		}

		return returnCbList;
	}
	
	public static List<TravelReqDetailCodingBlock> copyCbListTravelReq(
			List<TravelReqDetailCodingBlock> newCbList) {
		List<TravelReqDetailCodingBlock> returnCbList = new ArrayList<TravelReqDetailCodingBlock>();
		for (TravelReqDetailCodingBlock item : newCbList) {
			returnCbList.add(item);
		}

		return returnCbList;
	}

	/**
	 * Returns a concatenated CB string for Advances
	 * @param cbElement
	 * @return
	 */
	public static String getConcatCodingBlockValue(
			AdvanceDetailCodingBlocks cbElement) {

		String value = "";
		if (StringUtils.isEmpty(cbElement.getAppropriationYear()))
			return "";
		value = cbElement.getAppropriationYear().trim();

		value += getValidValue(cbElement.getIndexCode(),
				IConstants.CB_INDEX_LENGTH)
				+ getValidValue(cbElement.getPca(), IConstants.CB_PCA_LENGTH)
				+ getValidValue(cbElement.getGrantNumber(),
						IConstants.CB_GRANT_NUMBER_LENGTH)
				+ getValidValue(cbElement.getGrantPhase(),
						IConstants.CB_GRANT_PHASE_LENGTH)
				+ getValidValue(cbElement.getAgencyCode1(),
						IConstants.CB_AGENCY_CODE_1_LENGTH)
				+ getValidValue(cbElement.getProjectNumber(),
						IConstants.CB_PROJECT_NUMBER_LENGTH)
				+ getValidValue(cbElement.getProjectPhase(),
						IConstants.CB_PROJECT_PHASE_LENGTH)
				+ getValidValue(cbElement.getAgencyCode2(),
						IConstants.CB_AGENCY_CODE_2_LENGTH)
				+ getValidValue(cbElement.getAgencyCode3(),
						IConstants.CB_AGENCY_CODE_3_LENGTH)
				+ getValidValue(cbElement.getMultipurposeCode(),
						IConstants.CB_MULTI_LENGTH);
		return value.trim();
	}

	/**
	 * Returns a concatenated CB string for Expenses
	 * @param cbElement
	 * @return
	 */
	
	public static String getConcatCodingBlockValue(
			ExpenseDetailCodingBlocks cbElement) {

		String value = "";
		if (StringUtils.isEmpty(cbElement.getAppropriationYear()))
			return "";
		value = cbElement.getAppropriationYear().trim();

		value += getValidValue(cbElement.getIndexCode(),
				IConstants.CB_INDEX_LENGTH)
				+ getValidValue(cbElement.getPca(), IConstants.CB_PCA_LENGTH)
				+ getValidValue(cbElement.getGrantNumber(),
						IConstants.CB_GRANT_NUMBER_LENGTH)
				+ getValidValue(cbElement.getGrantPhase(),
						IConstants.CB_GRANT_PHASE_LENGTH)
				+ getValidValue(cbElement.getAgencyCode1(),
						IConstants.CB_AGENCY_CODE_1_LENGTH)
				+ getValidValue(cbElement.getProjectNumber(),
						IConstants.CB_PROJECT_NUMBER_LENGTH)
				+ getValidValue(cbElement.getProjectPhase(),
						IConstants.CB_PROJECT_PHASE_LENGTH)
				+ getValidValue(cbElement.getAgencyCode2(),
						IConstants.CB_AGENCY_CODE_2_LENGTH)
				+ getValidValue(cbElement.getAgencyCode3(),
						IConstants.CB_AGENCY_CODE_3_LENGTH)
				+ getValidValue(cbElement.getMultipurposeCode(),
						IConstants.CB_MULTI_LENGTH);

		return value.trim();
	}
	
	public static String getConcatCodingBlockValue(
			TravelReqDetailCodingBlock cbElement) {

		String value = "";
		if (StringUtils.isEmpty(cbElement.getAppropriationYear()))
			return "";
		value = cbElement.getAppropriationYear().trim();

		value += getValidValue(cbElement.getIndexCode(),
				IConstants.CB_INDEX_LENGTH)
				+ getValidValue(cbElement.getPca(), IConstants.CB_PCA_LENGTH)
				+ getValidValue(cbElement.getGrantNumber(),
						IConstants.CB_GRANT_NUMBER_LENGTH)
				+ getValidValue(cbElement.getGrantPhase(),
						IConstants.CB_GRANT_PHASE_LENGTH)
				+ getValidValue(cbElement.getAgencyCode1(),
						IConstants.CB_AGENCY_CODE_1_LENGTH)
				+ getValidValue(cbElement.getProjectNumber(),
						IConstants.CB_PROJECT_NUMBER_LENGTH)
				+ getValidValue(cbElement.getProjectPhase(),
						IConstants.CB_PROJECT_PHASE_LENGTH)
				+ getValidValue(cbElement.getAgencyCode2(),
						IConstants.CB_AGENCY_CODE_2_LENGTH)
				+ getValidValue(cbElement.getAgencyCode3(),
						IConstants.CB_AGENCY_CODE_3_LENGTH)
				+ getValidValue(cbElement.getMultipurposeCode(),
						IConstants.CB_MULTI_LENGTH);

		return value.trim();
	}
	
	/**
	 * Used to process individual CB fields
	 * @param cbElement
	 * @return
	 */

	public static String getValidValue(String value, int numSpaces) {

		if (!StringUtils.isEmpty(value))
			return value;
		
		String cbValue = "";

		StringBuilder buffer = new StringBuilder(cbValue);
		while (buffer.length() < numSpaces) {
			buffer.append(" ");
		}
		cbValue = buffer.toString();

		return cbValue;
	}

	/**
	 * This method validates that the percentages entered in Coding Block always
	 * sum upto 100.
	 * 
	 * @param cBList
	 * @return boolean
	 */

	public boolean validatePercentageSumTotal(List cBList) {
		boolean result = true;

		double sumPercent = 0;
		for (Object cbObj : cBList) {
			double percent = TimeAndExpenseUtil
					.roundToTwoDigits(getPercent(cbObj) * 100);
			sumPercent += percent;
			sumPercent = TimeAndExpenseUtil
			.roundToTwoDigits(sumPercent);
		}
		if (sumPercent < 100 || sumPercent > 100) {
			result = false;
		}

		return result;
	}

	/**
	 * This method gets the percentages entered in Advance or expense detail
	 * coding block
	 * 
	 * @param cbObj
	 * @return double
	 */

	private double getPercent(Object cbObj) {
		double percent = 0;
		if (cbObj.getClass().equals(ExpenseDetailCodingBlocks.class)) {
			ExpenseDetailCodingBlocks expObj = (ExpenseDetailCodingBlocks) cbObj;
			percent = expObj.getPercent();
		} else if (cbObj.getClass().equals(AdvanceDetailCodingBlocks.class)) {
			AdvanceDetailCodingBlocks advObj = (AdvanceDetailCodingBlocks) cbObj;
			percent = advObj.getPercent();
		} else if (cbObj.getClass().equals(TravelReqDetailCodingBlock.class)) {
			TravelReqDetailCodingBlock advObj = (TravelReqDetailCodingBlock) cbObj;
			percent = advObj.getPercent();
		}
		return percent;
	}

	/**
	 * This method validate whether two Coding block are identical or not
	 * 
	 * @param cbList
	 * @return boolean
	 */

	public boolean validateIdenticalCodingBlock(List cbList) {
		boolean identicalCodingBlockError = false;
		Set unique = new HashSet();

		for (int index = 0; index < cbList.size(); index++) {
			if (cbList.get(index).getClass().equals(
					ExpenseDetailCodingBlocks.class)) {
				String cb = this.getCodingBlock(cbList.get(index));
				boolean added = unique.add(cb);
				if (!added) {
					identicalCodingBlockError = true;
					break;
				}
				// ZH - added for advance duplicate check
			} else if (cbList.get(index).getClass().equals(
					AdvanceDetailCodingBlocks.class)) {
				String cb = this.getCodingBlock(cbList.get(index));
				boolean added = unique.add(cb);
				if (!added) {
					identicalCodingBlockError = true;
					break;
				}
			}
		}
		return identicalCodingBlockError;
	}
	
	/**
	 * Returns eitehr an existing CB concatenated string from the core class or returns a string 
	 * constructed by concatenating individual CB values
	 * @param cbElement
	 * @return
	 */

	public String getCodingBlock(Object cbElement) {
		if (cbElement.getClass().equals(ExpenseDetailCodingBlocks.class)) {
			ExpenseDetailCodingBlocks cb = (ExpenseDetailCodingBlocks) cbElement;
			if (!StringUtils.isEmpty(cb.getCodingBlock()))
				return cb.getCodingBlock();
			else
				return ActionHelper.getConcatCodingBlockValue(cb);
		} else if (cbElement.getClass().equals(AdvanceDetailCodingBlocks.class)) {
			AdvanceDetailCodingBlocks cb = (AdvanceDetailCodingBlocks) cbElement;
			if (!StringUtils.isEmpty(cb.getCodingBlock()))
				return cb.getCodingBlock();
			else
				return ActionHelper.getConcatCodingBlockValue(cb);
		}

		return "";
	}
	
	/**
	 * Used for determining if the CB store needs to be refreshed
	 * @param subject
	 * @return
	 */

	public static String getConcatDeptAgencyTku(UserSubject subject) {
		String concatDeptAgencyTku = "";
		if (subject != null) {
			concatDeptAgencyTku = subject.getDepartment() + subject.getAgency()
					+ subject.getTku();
		}
		return concatDeptAgencyTku;
	}

	/**
	 * Extracts the code from the params map value for a given row
	 * 
	 * @param cbElementName
	 * @param arrayPosition
	 * @return
	 */

	public static String getCodeFromJsonString(Map paramMap,
			String cbElementName, int arrayPosition) {
		String value = "";
		if (paramMap.containsKey(cbElementName)) {
			value = ((String[]) paramMap.get(cbElementName))[arrayPosition];
			if (!StringUtils.isEmpty(value)) {
				int indexOfSpace = value.indexOf(" ");
				if (indexOfSpace > 0) {
					value = value.substring(0, indexOfSpace);
				}
			}
		}

		return value;

	}
	
	/**
	 * Returns if the input fields should be disabled
	 * @param advanceMaster
	 * @return
	 */

	public static boolean statusEqualOrGreaterThanSubmit(
			AdvanceMasters advanceMaster) {
		boolean result = false;
		if (!StringUtils.isEmpty(advanceMaster.getStatus())) {
			if (advanceMaster.getStatus().equals(IConstants.SUBMIT)
					|| (advanceMaster.getStatus().equals(IConstants.APPROVED))					
					|| (advanceMaster.getStatus().equals(IConstants.REJECTED))
					|| (advanceMaster.getStatus().equals(IConstants.EXTRACTED))
					|| (advanceMaster.getStatus().equals(IConstants.HOURS_ADJUSTMENT_SENT))
					|| (advanceMaster.getStatus().equals(IConstants.PROCESSED)))
				result = true;
		}
		return result;
	}
	
	public static boolean statusEqualOrGreaterThanSubmit(
			TravelReqMasters treqMaster) {
		boolean result = false;
		if (!StringUtils.isEmpty(treqMaster.getStatus())) {
			if (treqMaster.getStatus().equals(IConstants.SUBMIT)
					|| (treqMaster.getStatus().equals(IConstants.APPROVED))
					|| (treqMaster.getStatus().equals(IConstants.REJECTED))
					|| (treqMaster.getStatus().equals(IConstants.EXTRACTED))
					|| (treqMaster.getStatus().equals(IConstants.PROCESSED)))
				result = true;
		}
		return result;
	}

	/**
	 * Composes the status display message for an advance
	 * 
	 * @param advanceMaster
	 * @param advanceService
	 * @return
	 */

	public static String getStatusDisplayMessage(AdvanceMasters advanceMaster,
			AdvanceDSP advanceService , UserSubject userSubject) {

		String advanceDisplayStatus = "";
		if (StringUtils.isEmpty(advanceMaster.getStatus())) {
			// only saved at this
			return IConstants.HISTORYMSG_NEEDSTOBESUBMITTED;
		} else if (IConstants.APPROVED.equals(advanceMaster.getStatus())
				|| IConstants.EXTRACTED.equals(advanceMaster.getStatus())
				|| IConstants.HOURS_ADJUSTMENT_SENT.equals(advanceMaster
						.getStatus())) {
			// approved - waiting for post-approval processing
			advanceDisplayStatus = IConstants.HISTORYMSG_APPROVED_EXTRACTED_HOURS_ADJUSTMENT_SENT;
		} else if (IConstants.PROCESSED.equals(advanceMaster.getStatus())) {
			// processed
			advanceDisplayStatus = IConstants.HISTORYMSG_PROCESSED;
		} else if (IConstants.REJECTED.equals(advanceMaster.getStatus())) {
			// rejected
			advanceDisplayStatus = IConstants.HISTORYMSG_REJECTED;
		} else if (IConstants.SUBMIT.equals(advanceMaster.getStatus())) {
			// submitted status - check if a next action other than SUBM exists
			String lastActionCode = "";
			lastActionCode = advanceService.getLatestAction(advanceMaster).get(
					0).getActionCode();
			
			if (lastActionCode.length() > 0)
				
				advanceDisplayStatus = advanceService
						.getRemainingApprovalPaths(
								advanceMaster.getAdvmIdentifier(), userSubject);
			
		}
		
		return advanceDisplayStatus;
	}

	/**
	 * Extracts a line number from error source, for Coding Block errors only, for Expenses.
	 * 
	 * @param expenseMaster
	 * @param errorSource
	 * @return
	 */

	public static String formatCbErrorSource(ExpenseMasters expenseMaster,
			String errorSource) {
		if (!errorSource.startsWith(IConstants.EXP_ERR_SRC_CB_TAB_PREFIX))
			// not a CB validation error. Error source remains unchanged in this
			// case
			return errorSource;

		if (expenseMaster.getExpenseDetailsCollection() == null
				|| expenseMaster.getExpenseDetailsCollection().isEmpty()) {
			return errorSource;
		}

		String newErrorSource = errorSource;
		for (ExpenseDetails item : expenseMaster.getExpenseDetailsCollection()) {
			// loop through each detail line and find associated CB validation
			// errors
			List<ExpenseDetailCodingBlocks> cbList = item
					.getExpenseDetailCodingBlocksCollection();
			String concatCbFromErrorSource = "";

			concatCbFromErrorSource = newErrorSource.substring((newErrorSource
					.lastIndexOf("-") + 2), newErrorSource.length());

			newErrorSource = appendCbErrorLineNumber(cbList,
					concatCbFromErrorSource, newErrorSource);

		}
		return newErrorSource;
	}
	
	public static String formatCbErrorSource(TravelReqMasters treqMaster,
			String errorSource) {
		if (!errorSource.startsWith(IConstants.EXP_ERR_SRC_CB_TAB_PREFIX))
			// not a CB validation error. Error source remains unchanged in this
			// case
			return errorSource;

		if (treqMaster.getTravelReqDetailsCollection() == null
				|| treqMaster.getTravelReqDetailsCollection().isEmpty()) {
			return errorSource;
		}

		String newErrorSource = errorSource;
		for (TravelReqDetails item : treqMaster.getTravelReqDetailsCollection()) {
			// loop through each detail line and find associated CB validation
			// errors
			List<TravelReqDetailCodingBlock> cbList = item
					.getTravelReqDetailCodingBlockCollection();
			String concatCbFromErrorSource = "";

			concatCbFromErrorSource = newErrorSource.substring((newErrorSource
					.lastIndexOf("-") + 2), newErrorSource.length());

			newErrorSource = appendCbErrorLineNumberTravelReq(cbList,
					concatCbFromErrorSource, newErrorSource);

		}
		return newErrorSource;
	}
	
	/**
	 * Modifies existing error source for display in case of CB errors, for Expneses
	 * @param cbList
	 * @param concatCbFromErrorSource
	 * @param currentErrorSource
	 * @return
	 */

	private static String appendCbErrorLineNumber(
			List<ExpenseDetailCodingBlocks> cbList,
			String concatCbFromErrorSource, String currentErrorSource) {
		String newErrorSource = currentErrorSource;
		if (!StringUtils.isEmpty(concatCbFromErrorSource)) {
			for (int i = 0; i < cbList.size(); i++) {
				if (concatCbFromErrorSource.equals(ActionHelper
						.getConcatCodingBlockValue(cbList.get(i)))) {
					newErrorSource = newErrorSource.substring(0, newErrorSource
							.lastIndexOf("-"));
					// append CB line number to error source
					newErrorSource += " - " + (i + 1);
					break;
				}
			}
		}
		return newErrorSource;

	}
	
	private static String appendCbErrorLineNumberTravelReq(
			List<TravelReqDetailCodingBlock> cbList,
			String concatCbFromErrorSource, String currentErrorSource) {
		String newErrorSource = currentErrorSource;
		if (!StringUtils.isEmpty(concatCbFromErrorSource)) {
			for (int i = 0; i < cbList.size(); i++) {
				if (concatCbFromErrorSource.equals(ActionHelper
						.getConcatCodingBlockValue(cbList.get(i)))) {
					newErrorSource = newErrorSource.substring(0, newErrorSource
							.lastIndexOf("-"));
					// append CB line number to error source
					newErrorSource += " - " + (i + 1);
					break;
				}
			}
		}
		return newErrorSource;

	}
	
	/**
	 * Extracts a line number from error source, for Coding Block errors only, for Advances.
	 * 
	 * @param treqMaster
	 * @param errorSource
	 * @return
	 */
	
	public static String formatCbErrorSource(AdvanceMasters advanceMaster,
			String errorSource) {
		if (StringUtils.isEmpty(errorSource))
			return "";

		String newErrorSource = errorSource;
		for (AdvanceDetails item : advanceMaster.getAdvanceDetailsCollection()) {
			// loop through each detail line and find associated CB validation
			// errors
			List<AdvanceDetailCodingBlocks> cbList = item
					.getAdvanceDetailCodingBlocksCollection();
		/*	String concatCbFromErrorSource = "";

			concatCbFromErrorSource = newErrorSource.substring((newErrorSource
					.lastIndexOf(" ") + 1), newErrorSource.length());*/

			newErrorSource = appendCbErrorLineNumberAdvance(cbList,
					newErrorSource);

		}
		return newErrorSource;
	}
	
	/**
	 * Modifies existing error source for display in case of CB errors, for Advances
	 * @param cbList
	 * @param concatCbFromErrorSource
	 * @param currentErrorSource
	 * @return
	 */
	
	private static String appendCbErrorLineNumberAdvance(
			List<AdvanceDetailCodingBlocks> cbList,
			String concatCbFromErrorSource) {
		
		String newErrorSource = "";
		if (!StringUtils.isEmpty(concatCbFromErrorSource)) {
			for (int i = 0; i < cbList.size(); i++) {
				if (concatCbFromErrorSource.equals(ActionHelper
						.getConcatCodingBlockValue(cbList.get(i)))) {
					// append CB line number to error source
					newErrorSource = "CB Line " + (i + 1);
					break;
				}
			}
		}
		return newErrorSource;

	}
	/**
	 * Used for previous button - Fetch session information prior to an Action's execution
	 * @param session
	 * @return
	 */
	
	public static Map extractSessionAttributes(HttpSession session) {
		Map sessionAttributes = new HashMap();
		String moduleId = (String) session.getAttribute(IConstants.LEFT_NAV_CURRENT_MODULE_ID);
		if (!StringUtils.isEmpty(moduleId)){
			sessionAttributes.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, moduleId);
		}				
		
		UserSubject userSubject = (UserSubject) session.getAttribute(IConstants.USER_SUBJECT_SESSION_KEY_NAME);
		if (userSubject != null){
			sessionAttributes.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, userSubject);
		}		
		
		EmployeeHeaderBean empInfo = (EmployeeHeaderBean) session.getAttribute(IConstants.EMP_HEADER_INFO);
		
		if (empInfo != null){
			sessionAttributes.put(IConstants.EMP_HEADER_INFO, empInfo);
		}
		
		return sessionAttributes;
	}	
	
	/**
	 * Save current session information in the stack. This will be used when the user has actually clicked
	 * the Previous Page link
	 * 
	 * @param currentSessionAttributes
	 * @param session
	 * @param actionName
	 */
	
	public static void copySessionAttributes(Map currentSessionAttributes, HttpSession session, String actionName){
		if ("Login".equals(actionName) || "HomePageAction".equals(actionName) ||
				"ApproveReferrer".equals(actionName) ||
				"SelectEmployeeReferrer".equals(actionName) ||
				"ExpenseAction".equals(actionName) ||
				"ExpenseSummaryAction".equals(actionName) ||
				"ExpenseHistoryList".equals(actionName)
		)
			return;
		Stack<PreviousActionAttributes> stack = (Stack) session.getAttribute("SESSION_STACK");
		if (stack == null){
			stack = new Stack<PreviousActionAttributes> ();
		}
			PreviousActionAttributes prev = new PreviousActionAttributes ();
			String currentModuleId = (String) currentSessionAttributes.get(IConstants.LEFT_NAV_CURRENT_MODULE_ID);
			UserSubject userSubject = (UserSubject) currentSessionAttributes.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME);
			EmployeeHeaderBean empInfo = (EmployeeHeaderBean) currentSessionAttributes.get(IConstants.EMP_HEADER_INFO);
			if (!StringUtils.isEmpty(currentModuleId)){
				prev.setModuleId(currentModuleId);
			}	
			
			if (userSubject != null){
				prev.setUserSubject(userSubject);
			}
			
			if (empInfo != null){
				prev.setEmpInfo(empInfo);
			}
			stack.push(prev);
			session.setAttribute("SESSION_STACK", stack);

	}
	
	/**
	 * Retrieves information from the session state after previously executed action
	 * @param session
	 * @return
	 */
	public static String restorePreviousSessionAttributes(Map session){
		Stack<PreviousActionAttributes> stack = (Stack) session.get("SESSION_STACK");
		PreviousActionAttributes previousActionAttributes = null;
		
		if (stack != null && stack.size() > 0){
			previousActionAttributes = (PreviousActionAttributes) stack.pop();			
			if (previousActionAttributes != null && !StringUtils.isEmpty(previousActionAttributes.getModuleId())){
				// current module Id
				session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, previousActionAttributes.getModuleId());
			}		
			if (previousActionAttributes != null && 
					previousActionAttributes.getUserSubject() != null){
				// current user subject
				session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, previousActionAttributes.getUserSubject());
			}			
			if (previousActionAttributes != null){
				// current employee header
				session.put(IConstants.EMP_HEADER_INFO, previousActionAttributes.getEmpInfo());
			}
		}
		return "";
	}
	
	/**
	 * Used to determine if the only the CB Std element is used for a given expense line item
	 * @param cbList
	 * @return
	 */
	
	public static boolean includesStandardCb (List<ExpenseDetailCodingBlocks> cbList){
		for (ExpenseDetailCodingBlocks item: cbList){
			if (cbList.size() == 1 && "Y".equals(item.getStandardInd())){
				// only a single CB element and Std is set
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Setup information to be used with the error display
	 * @param error
	 * @param errorCode
	 * @param errorMsg
	 * @param redirect
	 * @return
	 */
	
	public static ErrorDisplayBean setupError (ErrorDisplayBean error, String errorCode, String errorMsg, boolean redirect){
			error = new ErrorDisplayBean();
			error.setErrorCode(errorCode);
			error.setErrorMessage(errorMsg);
			error.setRedirectOption(redirect);
		return error;
	}
	
	/**
	 * Dynamically changes the module id if access mode is not the main menu
	 * @param currentModule
	 * @param domain
	 * @return
	 */
	
	public static String swapModuleId (String currentModule, String domain){
		String module = currentModule;
		if (currentModule.startsWith("APR"))
			return currentModule;
		if (domain.equalsIgnoreCase("EXPENSE")){
			if (!currentModule.startsWith("EXP")){
				String lastChar = currentModule.substring(currentModule.length() - 1);
				if (lastChar.equals("1")){
					module = IConstants.EXPENSE_EMPLOYEE;
				} else if (lastChar.equals("2")){
					module = IConstants.EXPENSE_MANAGER;
				} else if (lastChar.equals("3")){
					module = IConstants.EXPENSE_STATEWIDE;
				}
			}
		} else if (domain.equalsIgnoreCase("ADVANCE")){
			if (!currentModule.startsWith("ADV")){
				String lastChar = currentModule.substring(currentModule.length() - 1);
				if (lastChar.equals("1")){
					module = IConstants.ADVANCE_EMPLOYEE;
				} else if (lastChar.equals("2")){
					module = IConstants.ADVANCE_MANAGER;
				} else if (lastChar.equals("3")){
					module = IConstants.ADVANCE_STATEWIDE;
				}
			}
		} 
		return module;
	}
	
	/**
	 * Strips the department number from a string containing department number and 
	 * description
	 * @param str
	 * @return
	 */
	public static String extractCode(String str) {
		if(str == null) return null;
		String result="";
		
		int index = str.indexOf(" ");
		if(index>0){
			result = str.substring(0, index);
		}
		
		return result;
	}
	/**
	 * Retrieves coding blocks for driver reimbursement from application cache
	 * @param applicationCache
	 * @param expDetails
	 * @param subject
	 * @param expTypeCode
	 * @return
	 */
	public static DriverReimbExpTypeCbs getDriverReimbExpTypeCb (Map<String, Object> applicationCache, UserSubject subject, String expTypeCode){
		List<DriverReimbExpTypeCbs> cbs = (List<DriverReimbExpTypeCbs>) applicationCache.get("DRIVER_REIMB_CODING_BLOCKS");
		DriverReimbExpTypeCbs cb = null;
		if (cbs != null) {
			for (DriverReimbExpTypeCbs item : cbs) {
				if (item.getExpTypeCode().equals(expTypeCode)) {
					cb = item;
					break;
				}
			}
		}
		
		return cb;
	}
	
	public static void logParameters (ActionContext context, HttpServletRequest request, HttpSession session){
		logger.info("Current Action Name" + " = " + context.getName());
		logRequestParameters(request);
		logSessionParameters(session);
	}
	/**
	 * Logs all request params
	 * @param request
	 */
	public static void logRequestParameters (HttpServletRequest request){
		// used to track if begin and end lines should be printed
		boolean log = false;
		if (request.getParameterMap().size() > 0){
			logger.info("*****Begin Request Parameters*****");
			log = true;
		}
		 Iterator it = request.getParameterMap().entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry entry = (Map.Entry)it.next();
		        String key = (String) entry.getKey();
		        String[] value = (String[] ) entry.getValue();
		        if (!StringUtils.isEmpty(value[0])){			        
			        logger.info(key + " = " + value[0]);
		        }
		    }
		    if (log)
		        logger.info("*****End Request Parameters*****");
	}
	/**
	 * Logs select session params
	 * @param session
	 */
	public static void logSessionParameters (HttpSession session){
		// used to track if begin and end lines should be printed
		boolean log = false;
		UserProfile loggedInUser = (UserProfile)session.getAttribute(IConstants.USER_PROFILE_SESSION_KEY_NAME);	       
		if (loggedInUser != null){
			log = true;
			logger.info("*****Begin Session Parameters*****");
			logger.info("Logged in user id" + " = " + loggedInUser.getUserId());
			logger.info("Logged in employee id" + " = " + loggedInUser.getEmpIdentifier());
		}
		
		UserSubject userSubject = (UserSubject)session.getAttribute(IConstants.USER_SUBJECT_SESSION_KEY_NAME);	       
		if (userSubject != null){
			logger.info("Subject user appointment id" + " = " + userSubject.getAppointmentId());
			logger.info("Subject user employee id" + " = " + userSubject.getEmployeeId());
		}
		
		String module = (String) session.getAttribute(IConstants.LEFT_NAV_CURRENT_MODULE_ID);      
		if (module != null){
			logger.info("Current left nav module" + " = " + module);
		}
		
		ExpenseMasters expense = (ExpenseMasters) session.getAttribute(IConstants.EXPENSE_SESSION_DATA);      
		if (expense != null){
			logger.info("Current Expense Master" + " = " + expense.getExpmIdentifier());
		}
		
		AdvanceMasters advance = (AdvanceMasters) session.getAttribute(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER);      
		if (advance != null){
			logger.info("Current Advance Master" + " = " + advance.getAdvmIdentifier());
		}
		
		TravelReqMasters treq = (TravelReqMasters) session.getAttribute(IConstants.TRAVEL_REQUISITION_SESSION_DATA);      
		if (treq != null){
			logger.info("Current Travel Requisition Master" + " = " + treq.getTreqmIdentifier());
		}
		if (log){
		logger.info("*****End Session Parameters*****");
		}
	}	
}
