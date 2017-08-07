package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.SystemCodes;
import gov.michigan.dit.timeexpense.model.core.SystemCodesPK;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.model.display.NewDisplaySystemCd;
import gov.michigan.dit.timeexpense.model.display.SecurityScope;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.service.SystemCodesDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * @author kaws1
 * 
 */

public class SystemCodesAction extends AbstractAction {

	private static Logger logger = Logger
			.getLogger(StateWideApproveAction.class);

	private AppointmentDSP appointmentService;
	SystemCodes sysSelectResult;
	private SystemCodesDSP systemcodeService;
	private SecurityScope securityScope;
	private String moduleId;
	private Map<String, Object> userPref;
	private String chosenSysCode;
	private boolean autoSearch;
	private Integer sysCodeId;
	private String searchCode;
	private String syscode_cb;
	private String visited;
	private String SystemCodeEId;
	protected NewDisplaySystemCd nsystemCode;
	String module_hidden;
	private String currentDate;
	private String errorsJson = "";
	private SecurityManager security;
	private SecurityManager securityN;
	
	boolean writeAccess;

	private String edit;

	public SystemCodesAction() {
		securityScope = new SecurityScope();
		security = new SecurityManager(entityManager);
		securityN = new SecurityManager(entityManager);	 
		moduleId = getModuleId();
	}

	/**
	 * This method returns the list of system codes to the search system code
	 * page
	 * 
	 * @return String
	 */
	public String systemCodesInitialize() {
		systemcodeService = new SystemCodesDSP(entityManager);
		security = new SecurityManager(entityManager);
		nsystemCode = new NewDisplaySystemCd();
		moduleId = getModuleId();
		session.put(IConstants.LEFT_SYS_CODE_MODULE_ID, moduleId);
		session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, moduleId);
		UserProfile profile = super.getLoggedInUser();
		writeAccess = security.checkAccess(profile.getUserId(), moduleId);
		if (!writeAccess) {
			writeAccess = true;
		} else {
			writeAccess = false;
		}
		userPref = findUserPreferences();
		nsystemCode.setSystemCodes(getAllSysCodes());

		if (searchCode == null) {
			chosenSysCode = determineChosenSysCode();
		}
		setJsonResponse(buildJsonResponse(writeAccess));
		// System.out.println("\n the value selected is "+chosenSysCode+"  "+sysCode);
		return "success";
	}

	/**
	 * This is called when the user selects the search button
	 * 
	 * @return String
	 */
	public String findSearchResults() {
		logger.info("Action: findSearchResults invoked");
		if (searchCode != null && searchCode.length() > 0) {
			List<NewDisplaySystemCd> sysSelectResult = systemcodeService
					.getSelectedCode(searchCode);
			chosenSysCode = searchCode;
			// super.setJsonResponse(jsonParser.toJson(expenseList));
			setJsonResponse(jsonParser.toJson(sysSelectResult));
			String x = super.getJsonResponse();
			logger.info("Action: findSearchResults invoked" + x);
		} else {
			List<NewDisplaySystemCd> sysSelectResult = systemcodeService
					.getAllSystemCodes();
			// super.setJsonResponse(jsonParser.toJson(expenseList));
			setJsonResponse(jsonParser.toJson(sysSelectResult));
			String x = super.getJsonResponse();
			logger.info("Action: findSearchResults invoked" + x);
		}
		return IConstants.SUCCESS;
	}

	/**
	 * This is called when edit link is clicked
	 * 
	 * @return String
	 */
	public String systemCodeEditAction() {
		logger.info("Action: systemCodeEditAction invoked");

		securityN = new SecurityManager(entityManager);

		module_hidden = session.get(IConstants.LEFT_SYS_CODE_MODULE_ID)
				.toString();
		writeAccess = securityN.checkAccess(getLoggedInUser().getUserId(),
				module_hidden);
		if (!writeAccess) {
			writeAccess = true;
		} else {
			writeAccess = false;
		}

		List<NewDisplaySystemCd> sysSelectResult = systemcodeService
				.getSelectedCode(SystemCodeEId);
		if (sysSelectResult != null) {
			for (int i = 0; i < sysSelectResult.size(); i++) {
				sysSelectResult.get(i).setWriteAccess(writeAccess);
			}
		}
		setJsonResponse(jsonParser.toJson(sysSelectResult));
		return IConstants.SUCCESS;
	}

	/**
	 * This method is called for create system code
	 * 
	 * @return String 
	 */
	public String createSysCodeAction() {
		UserProfile profile = super.getLoggedInUser();
		profile.getUserId();
		HashMap listSt = new HashMap();
		currentDate = systemcodeService.getCurrentdate();
		listSt.put("currentDate", currentDate);
		listSt.put("user", profile.getUserId());
		setJsonResponse(jsonParser.toJson(listSt));
		return IConstants.SUCCESS;
	}

	/**
	 * this is called when user clicks delete button
	 * 
	 * @return String
	 */
	public String deleteExistingCode() {
		SystemCodes modifydata = new SystemCodes();
		SystemCodesPK tempCodeOBJ = new SystemCodesPK();

		tempCodeOBJ.setSystemCode(nsystemCode.getSystemCode());
		tempCodeOBJ.setStartDate(nsystemCode.getStartDate());
		modifydata.setSystemCodesPK(tempCodeOBJ);
		modifydata = entityManager.merge(modifydata);

		List<NewDisplaySystemCd> sysSelectResult = systemcodeService
				.deleteSystemCodes(modifydata);
		setJsonResponse(jsonParser.toJson(sysSelectResult));
		return IConstants.SUCCESS;
	}

	/**
	 * This is to modify any existing system code
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String modifyExistingCode() throws Exception {
		UserProfile profile = super.getLoggedInUser();
		profile.getUserId();
		SystemCodes modifydata = new SystemCodes();
		SystemCodesPK tempCodeOBJ = new SystemCodesPK();

		tempCodeOBJ.setSystemCode(nsystemCode.getSystemCode());
		tempCodeOBJ.setStartDate(nsystemCode.getStartDate());
		modifydata.setSystemCodesPK(tempCodeOBJ);

		modifydata.setDescription(nsystemCode.getDescription());
		modifydata.setEndDate(nsystemCode.getEndDate());
		modifydata.setModifiedDate(nsystemCode.getModifiedDate());
		modifydata.setModifiedUserId(getLoggedInUser().getUserId());
		modifydata.setValue(nsystemCode.getValue());
		String retvalidate=	systemcodeService.validateData(modifydata) ; 
		try
		{
		if(!retvalidate.equals(IConstants.SUCCESS))
		{
			ErrorMessages errorMessages=systemcodeService.getErrorCode(retvalidate);
			throw new IllegalArgumentException(errorMessages.getErrorText());
		}
			
		List<NewDisplaySystemCd> sysSelectResult = systemcodeService
				.updateSystemCodes(modifydata);
		setJsonResponse(jsonParser.toJson(sysSelectResult));
			}catch(IllegalArgumentException tl)
			{
				addFieldError("errors",tl.getMessage());
			}
		return IConstants.SUCCESS;
	}

	/**
	 * Modifies the recently creates System Code
	 * @return String
	 * @throws Exception
	 */
	public String modifyNewCode() throws Exception {
		UserProfile profile = super.getLoggedInUser();
		profile.getUserId();
		SystemCodes modifydata = new SystemCodes();
		SystemCodesPK tempCodeOBJ = new SystemCodesPK(); 
		tempCodeOBJ.setSystemCode(nsystemCode.getSystemCode());
		tempCodeOBJ.setStartDate(nsystemCode.getStartDate());
		modifydata.setSystemCodesPK(tempCodeOBJ); 
		modifydata.setDescription(nsystemCode.getDescription());
		modifydata.setEndDate(nsystemCode.getEndDate());
		modifydata.setModifiedDate(nsystemCode.getModifiedDate());
		modifydata.setModifiedUserId(getLoggedInUser().getUserId());
		modifydata.setValue(nsystemCode.getValue());
		String retvalidate=	systemcodeService.validateData(modifydata) ; 
		try
		{
		if(!retvalidate.equals(IConstants.SUCCESS))
		{
			ErrorMessages errorMessages=systemcodeService.getErrorCode(retvalidate);
			
			throw new IllegalArgumentException(errorMessages.getErrorText());
		}
		
		List<NewDisplaySystemCd> sysSelectResult = systemcodeService
				.updateSystemCodes(modifydata);
		setJsonResponse(jsonParser.toJson(sysSelectResult));
		}catch(IllegalArgumentException pl)
		{
			addFieldError("errors",pl.getMessage());
		}
		return IConstants.SUCCESS;
	}
	/**
	 * This method creates a new system code
	 * 
	 * @return String
	 */

	public String createNewSystemCode() {
		UserProfile profile = super.getLoggedInUser();
		session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, moduleId);
		profile.getUserId();
		HashMap listDisp = new HashMap();
		SystemCodes modifydata = new SystemCodes();
		SystemCodesPK tempCodeOBJ = new SystemCodesPK();

		tempCodeOBJ.setSystemCode(nsystemCode.getSystemCode().toUpperCase());
		tempCodeOBJ.setStartDate(nsystemCode.getStartDate());
		modifydata.setSystemCodesPK(tempCodeOBJ);

		modifydata.setDescription(nsystemCode.getDescription());
		modifydata.setEndDate(nsystemCode.getEndDate());
		modifydata.setModifiedDate(nsystemCode.getModifiedDate());
		modifydata.setModifiedUserId(profile.getUserId());
		modifydata.setValue(nsystemCode.getValue());
		try {
		String retvalidate=	systemcodeService.validateData(modifydata) ; 
		if(!retvalidate.equals(IConstants.SUCCESS))
		{
			ErrorMessages errorMessages=systemcodeService.getErrorCode(retvalidate);
			
			throw new IllegalArgumentException(errorMessages.getErrorText());
		}
			
			List<NewDisplaySystemCd> sysSelectResult = systemcodeService
					.createSystemCodes(modifydata); 

			setJsonResponse(jsonParser.toJson(sysSelectResult));
		} catch(IllegalArgumentException il)
		{
			addFieldError("errors",il.getMessage());
		
		}catch (Exception e) {
			addFieldError("errors",
					"Save Failed - Please check System Code and start date ");
			String jsonResponse = "{msg:'Save Failed - Please check System Code and start date', submitSuccess : false}";
			setJsonResponse(jsonParser.toJson(jsonResponse));
		}

		return IConstants.SUCCESS;
	}


	/**
	 * This builds the json string to display on the page
	 * 
	 * @return String
	 */

	private String buildJsonResponseResults() {

		StringBuilder buff = new StringBuilder();
		buff.append("{");
		buff
				.append("systemCodespk:"
						+ nsystemCode
								.getCombinedSystemCodeNameWithEmptyElementWithoutAllScopeJson()
						+ ",");

		chosenSysCode = (chosenSysCode == null) ? "" : chosenSysCode;
		if (userPref != null
				&& userPref.containsKey(IConstants.SE_USER_SELECTED_SYS_CODE)) {
			autoSearch = true;
		}
		buff.append("chosenSysCode:\"" + chosenSysCode + "\" ");

		buff.append("}");
		return buff.toString();
	}

	/**
	 * This method is for getting the module ID 
	 * @return map
	 */
	private Map<String, Object> findUserPreferences() {
		Map<String, Map<String, Object>> userContext = (Map<String, Map<String, Object>>) session
				.get(IConstants.USER_CONTEXT);

		Map<String, Object> userPrefs = null;
		if (userContext != null)
			userPrefs = userContext.get(moduleId);

		return userPrefs;
	}
/**
 *  Setter  method for the JSP elements 
 * @param syscode_cb
 */
	public void setSyscode_cb(String syscode_cb) {
		this.syscode_cb = syscode_cb;
	}

	/**
	 * Getter  method for the JSP elements 
	 * @return syscode_cb
	 */
	public String getSyscode_cb() {
		return syscode_cb;
	}

	/**
	 * Setter method for the JSP elements 
	 * @param chosenSysCode
	 */
	public void setChosenSystemCode(String chosenSysCode) {
		this.chosenSysCode = chosenSysCode;
	}

	/**
	 * Getter method for the JSP elements
	 * @return chosenSysCode
	 */
	public String getChosenSystemCode() {
		return chosenSysCode;
	}

	/**
	 * Setter method for the JSP elements
	 * @param searchCode
	 */
	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	/**
	 * Getter method for the JSP elements
	 * @return searchCode
	 */
	public String getSearchCode() {
		return searchCode;
	}

	/**
	 * Getter method for the JSP elements
	 * @return SystemCodeEId
	 */
	public String getSystemCodeEId() {
		return SystemCodeEId;
	}

	/**
	 * Setter method for the JSP elements
	 * @param SystemCodeEId
	 */
	public void setSystemCodeEId(String SystemCodeEId) {
		this.SystemCodeEId = SystemCodeEId;
	}

	/* (non-Javadoc)
	 * @see gov.michigan.dit.timeexpense.action.BaseAction#prepare()
	 */
	public void prepare() {
		systemcodeService = new SystemCodesDSP(entityManager);

	}

	/**
	 * This method is for formatting the results in the
	 * drop down select on search system code page
	 * @param writeAccess
	 * @return Json String
	 */
	private String buildJsonResponse(boolean writeAccess) {
		StringBuilder buff = new StringBuilder();
		buff.append("{");
		buff
				.append("systemCodespk:"
						+ nsystemCode
								.getCombinedSystemCodeNameWithEmptyElementWithoutAllScopeJson()
						+ ",");

		chosenSysCode = (chosenSysCode == null) ? "" : chosenSysCode;
		if (userPref != null
				&& userPref.containsKey(IConstants.SE_USER_SELECTED_SYS_CODE)) {
			autoSearch = true;
		}
		// buff.append("chosenSysCode:\""+chosenSysCode+"\" ");
		buff.append("writeAccess:\"" + writeAccess + "\" ");
		buff.append(",moduleId:\"" + moduleId + "\" ");
		buff.append("}");

		// System.out.println(buff);
		return buff.toString();
	}

	/**
	 * Getter Method for the System List
	 * @return getSystemCodes
	 */
	public List<SystemCodes> getAllSysCodes() {
		UserProfile user = getUser();
		return systemcodeService.getDistinctSystemCodes();
	}
/**
 * This method gets the logged in
 *  user information 
 * @return UserProfile
 */
	private UserProfile getUser() {
		return (UserProfile) session
				.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
	}

	/**
	 * get the selected System Code
	 * @return String
	 */
	private String determineChosenSysCode() {
		return (userPref != null && userPref
				.containsKey(IConstants.SE_USER_SELECTED_SYS_CODE)) ? (String) userPref
				.get(IConstants.SE_USER_SELECTED_SYS_CODE)
				: nsystemCode.getFirstSystemCodeDisplayValue();
	}

	/**
	 * Getter method for the displayed java object
	 * This is for displaying the system code
	 * @return nsystemCode
	 */
	public NewDisplaySystemCd getnsystemCode() {
		return nsystemCode;
	}

	/**
	 * Setter method for the nsystemCode java Object
	 * This is for displaying the system code  
	 * @param nsystemCode
	 */
	public void setnsystemCode(NewDisplaySystemCd nsystemCode) {
		this.nsystemCode = nsystemCode;
	}
 
	/**
	 * This is for the security access of the page
	 * @return boolean writeAccess
	 */
	public boolean getWriteAccess() {
		return writeAccess;
	}
	 
	/**
	 * This is for setting the security access of 
	 * the logged in user.
	 * @param writeAccess
	 */
	public void setWriteAccess(boolean writeAccess) {
		this.writeAccess = writeAccess;
	}
	

}
