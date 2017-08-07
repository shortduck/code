package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.Agency;
import gov.michigan.dit.timeexpense.model.core.AgencyCommonMileages;
import gov.michigan.dit.timeexpense.model.core.CommonMileages;
import gov.michigan.dit.timeexpense.model.core.CommonMileagesPK;
import gov.michigan.dit.timeexpense.model.core.Department;
import gov.michigan.dit.timeexpense.model.core.ExpenseLocations;
import gov.michigan.dit.timeexpense.model.core.States;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.model.display.DisplayAgencyDept;
import gov.michigan.dit.timeexpense.model.display.DisplayDeptAgyLoc;
import gov.michigan.dit.timeexpense.model.display.SecurityScope;
import gov.michigan.dit.timeexpense.service.AgyOptLocalDSP;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * This class handles all the system calls made for common mileage requirement
 * in Agency Options menu
 * 
 * @author KawS1
 * 
 */
@SuppressWarnings("unchecked")
public class AgyOptLocalAction extends AbstractAction {

	private static Logger logger = Logger
			.getLogger(StateWideApproveAction.class);
	private SecurityScope securityScope;
	private SecurityManager security;
	private SecurityManager securityN;
	private String chosenDepartment;
	private String chosenAgency;
	private String chosenDept;
	private Map<String, Object> userPref;
	private String moduleId;
	private boolean autoSearch;
	boolean writeAccess;
	private AppointmentDSP appointmentService;
	private AgyOptLocalDSP agyDeptLocDsp;
	private String department_cb;
	private String agency_cb;
	private String identifierKey;
	private List<ExpenseLocations> agencyDeptLocations;
	private String ag_location;
	protected ExpenseLocations nDeptAgyLoc;
	private String agencyNum;
	private String deptNum;
	private String stProv;
	private List<States> codes;
	private String state_cb;
	private String agyName;
	private String toMiles;
	private String fromMiles;
	public String mLval;
	private String newMileAgy;
	private String saveStateMiles;
	private String agNVal;
	private String module_hidden;

	public void prepare() {
		appointmentService = new AppointmentDSP(entityManager);
		nDeptAgyLoc = new ExpenseLocations();
	}

	/**
	 * Class Constructor
	 */
	public AgyOptLocalAction() {
		securityScope = new SecurityScope();
		security = new SecurityManager(entityManager);
		securityN = new SecurityManager(entityManager);
		moduleId = getModuleId();

	}

	
	
	/**
	 * This method is called when the user clicks on the Agency options menu omn
	 * the left navigation . This method initializes the page and determines the
	 * module and security of the user to access the screen.
	 * 
	 * @return String
	 */
	public String agyLocationInitialize() {

		security = new SecurityManager(entityManager);
		moduleId = getModuleId();
		nDeptAgyLoc = new ExpenseLocations();
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
		securityScope.setDepartments(getDepartment());
		chosenDepartment = determineChosenDept();

		if (chosenDepartment != null && !"".equals(chosenDepartment)) {
			securityScope.setAgencies(getAgency(chosenDepartment
					.substring(0, 2)));
			chosenAgency = determineChosenAgency();
		}
		setJsonResponse(buildJsonResponse(writeAccess));
		return IConstants.SUCCESS;
	}

	/**
	 * Return the dept name , agency and
	 * 
	 * @return String
	 */
	public String agyMilesUpdate() {
		// get the locations for the given agency /dept
		long startTime = System.currentTimeMillis();
		logger.info("*****Begin agyMilesUpdate*****" + startTime);
		agyDeptLocDsp = new AgyOptLocalDSP(entityManager);
		DisplayDeptAgyLoc obj1 = agyDeptLocDsp.getDetailsDeptLoc(deptNum,
				agencyNum, getApplicationCache() , true); 
		if(module_hidden.toUpperCase().equals("TRUE"))
		{
			obj1.setWriteAccess(true);
		}else
		{
			obj1.setWriteAccess(false);
		}
		UserProfile profile = super.getLoggedInUser();
		boolean stateAccess = agyDeptLocDsp.checkStateLocationAccess(profile,IConstants.STATEWIDE_AGENCY_LOC_AVAILABLE);
		if(!stateAccess)
		{
			obj1.setStateAccessUpd(true);
		}else
		{
			obj1.setStateAccessUpd(false);
		}
		
		
		 
		setJsonResponse(jsonParser.toJson(obj1));
		
		long endTime = System.currentTimeMillis();
		logger.info("*****End agyMilesUpdate*****" + endTime);		 
		logger.info("*****Total milisecond/seconds *****" + (endTime - startTime));
		
		return IConstants.SUCCESS;
	}

	/**
	 * This method is called when user adds a new agency location.
	 * 
	 * @return String
	 */
	public String updateAgencyCity() {
		
		agyDeptLocDsp = new AgyOptLocalDSP(entityManager);
		
		try {

			//Validating Department and Agency
			String retValidate = agyDeptLocDsp.validateAgyDeptValues(agencyNum,
					deptNum);
			
			if (!retValidate.equals(IConstants.SUCCESS)) {
				
				ErrorMessages errorMessages = agyDeptLocDsp
						.getErrorCode(retValidate);
				
				addFieldError("errors", errorMessages.getErrorText() );
				return IConstants.FAILURE;
				//throw new IllegalArgumentException(errorMessages.getErrorText());
			}
			
			//Check if this is a statewide location or city already exists in agency
			retValidate = agyDeptLocDsp.isCityAlreadyExists(ag_location.toUpperCase(), stProv,
					deptNum, agencyNum);
			
			if (!"0".equals(retValidate)) {
				
				ErrorMessages errorMessages = agyDeptLocDsp
						.getErrorCode(retValidate);
				
				addFieldError("errors", errorMessages.getErrorText() );
				return IConstants.FAILURE;
			}
			
			nDeptAgyLoc = new ExpenseLocations();
			nDeptAgyLoc.setDepartment(deptNum);
			nDeptAgyLoc.setAgency(agencyNum);
			nDeptAgyLoc.setStProv(stProv);
			nDeptAgyLoc.setCity(ag_location.toUpperCase());
			nDeptAgyLoc.setSelectCityInd("N");
			nDeptAgyLoc
					.setDisplayOrder(IConstants.EXPENSE_LOCATION_DEFAULT_ORDER);

			boolean retVal = agyDeptLocDsp.updateLocation(nDeptAgyLoc);

			DisplayDeptAgyLoc dObject = agyDeptLocDsp.getDetailsDeptLoc(
					nDeptAgyLoc.getDepartment(), nDeptAgyLoc.getAgency(),
					getApplicationCache(), false);
			
			dObject.setAgency(nDeptAgyLoc.getAgency());
			dObject.setDepartment(deptNum);
			
			security = new SecurityManager(entityManager);
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
			
			if (module_hidden.toUpperCase().equals("TRUE")) {
				dObject.setWriteAccess(true);
			} else {
				dObject.setWriteAccess(false);
			}
			setJsonResponse(jsonParser.toJson(dObject));
			if (!retVal) {
				throw new IllegalArgumentException("Error in Saving Location.");
			}

		} catch (IllegalArgumentException tl) {
			addFieldError("errors", tl.getMessage());
			String jsonResponse = "{msg:'Save Failed - Please check Location and State values', submitSuccess : false}";
		}

		return IConstants.SUCCESS;
	}

	/**
	 * This method gets the common mileage and the agency specified mileage
	 * between two cities
	 * 
	 * @return String
	 */

	public String getStateMilesUpdate() {

		int from_M = Integer.parseInt(fromMiles);
		int to_M = Integer.parseInt(toMiles);
		BigDecimal milesAgy = new BigDecimal("0"); ;
		agyDeptLocDsp = new AgyOptLocalDSP(entityManager);
		int miles = agyDeptLocDsp.milesCommBtwCity(from_M, to_M);
		AgencyCommonMileages milecCalc =  agyDeptLocDsp.milesAgyBtwCity(from_M, to_M);
		if(milecCalc !=null && milecCalc.getAgycmIdentifier()>0)
		{
		  milesAgy = milecCalc.getAgyMileage();
		}
		String commonMiles = "No Common Miles";
		String agencyMiles = "No Agency Miles";
        long agykey = milecCalc.getAgycmIdentifier();
        DisplayAgencyDept displayObj = new DisplayAgencyDept();
        displayObj.setMLval(miles+"");
        displayObj.setALval(milesAgy+"");
        displayObj.setAgVal(agykey+"");
    	setJsonResponse(jsonParser.toJson(displayObj));
		/*StringBuffer buff = new StringBuffer();
		buff.append("{"); 
			buff.append(" miles:" + miles + ","); 
			buff.append(" milesAgy:" + milesAgy + ",");
			buff.append(" agykey:" + agykey + ",");
		buff.append("}");
		String mLval = miles + "," + milesAgy + "";
		setJsonResponse(buff.toString());*/

		return IConstants.SUCCESS;
	}
/**
 * This method is used for saving new Common mileage
 * @return
 */
	
	
	public String saveStateNewMileage() {

		BigDecimal from_M = new BigDecimal(fromMiles);
		BigDecimal to_M = new BigDecimal(toMiles);
		BigDecimal newAgyMiles = new BigDecimal(saveStateMiles);
		CommonMileages objSave = new CommonMileages();
		CommonMileagesPK objPK = new CommonMileagesPK();
		objPK.setToElocIdentifier(to_M.longValue());
		objPK.setFromElocIdentifier(from_M.longValue());
		objSave.setMileage(newAgyMiles);
		objSave.setPk(objPK); 

		agyDeptLocDsp = new AgyOptLocalDSP(entityManager);
        try
        {
        	String retvalidate =agyDeptLocDsp.validateDelete(from_M.intValue());
        	if(!retvalidate.equals(IConstants.SUCCESS))
        	{
        		ErrorMessages errorMessages=agyDeptLocDsp.getErrorCode(retvalidate);
    			throw new IllegalArgumentException(errorMessages.getErrorText());
        	}
        	 retvalidate =agyDeptLocDsp.validateDelete(to_M.intValue());
        	 if(!retvalidate.equals(IConstants.SUCCESS))
         	{
         		ErrorMessages errorMessages=agyDeptLocDsp.getErrorCode(retvalidate);
     			throw new IllegalArgumentException(errorMessages.getErrorText());
         	}
		CommonMileages saveObj = agyDeptLocDsp.saveCommonNewMiles(objSave);
		mLval = agyDeptLocDsp.milesAgyBtwCity(from_M.intValue(), to_M
				.intValue())
				+ "";
	 
		 setJsonResponse(jsonParser.toJson(saveObj));
        }catch(IllegalArgumentException tl)
		{
			addFieldError("errors",tl.getMessage());
		}
		return IConstants.SUCCESS;
	
	}
	 
	
	
	/**
	 * This method creates Agency Specified new mileage between tow cities
	 * 
	 * @return
	 */

	public String saveNewMileage() {
		BigDecimal from_M = new BigDecimal(fromMiles);
		BigDecimal to_M = new BigDecimal(toMiles);
		BigDecimal newAgyMiles = new BigDecimal(newMileAgy);
		AgencyCommonMileages objSave = new AgencyCommonMileages();
		objSave.setAgency(agencyNum);
		objSave.setDepartment(deptNum);
		objSave.setFromElocIdentifier(from_M);
		objSave.setToElocIdentifier(to_M);
		objSave.setAgyMileage(newAgyMiles);
		if(agNVal!=null && agNVal.trim().length()>0)
		{
		objSave.setAgycmIdentifier(Long.parseLong(agNVal));
		}

		agyDeptLocDsp = new AgyOptLocalDSP(entityManager);
        try
        {
        	String retvalidate =agyDeptLocDsp.validateAgyDeptValues(agencyNum,deptNum);
        	if(!retvalidate.equals(IConstants.SUCCESS))
        	{
        		ErrorMessages errorMessages=agyDeptLocDsp.getErrorCode(retvalidate);
    			throw new IllegalArgumentException(errorMessages.getErrorText());
        	}
		AgencyCommonMileages saveObj = agyDeptLocDsp.saveAgyNewMiles(objSave);
		mLval = agyDeptLocDsp.milesAgyBtwCity(from_M.intValue(), to_M
				.intValue())
				+ "";
	 
		 setJsonResponse(jsonParser.toJson(saveObj));
        }catch(IllegalArgumentException tl)
		{
			addFieldError("errors",tl.getMessage());
		}
		return IConstants.SUCCESS;
	}

	/**
	 * This method is called when the user selects the select hyperlink on the
	 * search the agency dept screen The link directs the control to the method
	 * to get the location details for the given agency and department. This
	 * method sends back to the screen the list of locations and the name of
	 * agency and deprtment.
	 * 
	 * @return String
	 */
	public String agySelectAgencyLocation() {
		long startTime = System.currentTimeMillis();
		logger.info("*****Begin agySelectAgencyLocation*****" + startTime);
		
		//TODO: Why upon save new city the control is coming here ?
		
		if (identifierKey == null || identifierKey.length() == 0) {
			return IConstants.SUCCESS;
		}
		
		String[] tokens = identifierKey.split(",");
		String deptN = tokens[0];
		String agyN = tokens[1];

		agyDeptLocDsp = new AgyOptLocalDSP(entityManager);

		DisplayDeptAgyLoc dObject = agyDeptLocDsp.getDetailsDeptLoc(deptN,
				agyN.substring(0, 2), getApplicationCache(), false);

		security = new SecurityManager(entityManager);
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
		if (module_hidden.toUpperCase().equals("TRUE")) {
			dObject.setWriteAccess(true);
		} else {
			dObject.setWriteAccess(false);
		}

		setJsonResponse(jsonParser.toJson(dObject));
		long endTime = System.currentTimeMillis();
		logger.info("*****End agySelectAgencyLocation*****" + endTime);
		logger.info("*****Total milisecond/seconds *****"
				+ (endTime - startTime));
		return IConstants.SUCCESS;
	}

	/**
	 * This method is called when user selects an agency location to be deleted
	 * from the list. 
	 * @return String
	 */
	public String deleteAgyDeptLocation() {
		agyDeptLocDsp = new AgyOptLocalDSP(entityManager);
		int numberL = Integer.valueOf(ag_location);
		try
		{
		String checkvalid = agyDeptLocDsp.validateDelete(numberL);
		if(!checkvalid.equals(IConstants.SUCCESS))
		{
			ErrorMessages errorMessages=agyDeptLocDsp.getErrorCode(checkvalid);
			throw new IllegalArgumentException(errorMessages.getErrorText());
		}
	 
		agyDeptLocDsp.deleteLocation(numberL);
		}catch(IllegalArgumentException tl)
		{
			addFieldError("errors",tl.getMessage());
		}
		return agySelectAgencyLocation();

	}

//	/**
//	 * This method is for formatting the results in the drop down select on
//	 * search system code page
//	 * 
//	 * @param writeAccess
//	 * @return Json String
//	 */
//	private String buildJsonResponseObj(DisplayDeptAgyLoc dObject) {
//		StringBuilder buff = new StringBuilder();
//		buff.append("{");
//		buff.append(" agyLocations:" + dObject.getAgyLocations() + " ");
//		buff.append(" stList:"
//				+ getCombinedWithEmptyElementWithoutAllScopeJson(dObject)
//				+ ", ");
//		buff.append(" writeAccess:\"" + writeAccess + "\" ");
//		buff.append(", moduleId:\"" + moduleId + "\" ");
//		buff.append(", department:\"" + dObject.getDepartment() + "\" ");
//		buff.append(", chosenValue:\""
//				+ dObject.getAgyLocations().get(0).getCity() + "\" ");
//		buff.append(", agency:\"" + dObject.getAgency() + "\" ");
//		buff.append("}");
//		return buff.toString();
//	}

	public String getCombinedWithEmptyElementWithoutAllScopeJson(
			DisplayDeptAgyLoc dObject) {
		StringBuilder buff = new StringBuilder();

		buff.append("[");
		buff.append(getEmptyElementJson());
		buff.append(",");
		buff.append(getSystemCodeMinusAllScopeJsonWithoutArray(dObject));
		removeLastCommaCharacter(buff);
		buff.append("]");

		return buff.toString();
	}

	public String getSystemCodeMinusAllScopeJsonWithoutArray(
			DisplayDeptAgyLoc dObject) {
		StringBuilder buff = new StringBuilder();
		List<States> arrList = dObject.getStatesUs();

		if (arrList == null) {
			return "";
		}
		int count = 0;
		for (States cD : arrList) {

			buff.append("{code:\"");
			buff.append(cD.getStateCode());
			buff.append("\",display:\"");
			buff.append(cD.getStateCode());
			buff.append("\"},");
		}
		removeLastCommaCharacter(buff);
		return buff.toString();

	}

	private String getEmptyElementJson() {
		return "{code:\"\",display:\"\"}";
	}

	/**
	 * This method removes the comma from the last entry of the Json string
	 * 
	 * @param buff
	 */
	private void removeLastCommaCharacter(StringBuilder buff) {
		if (buff.length() > 0 && ',' == buff.charAt(buff.length() - 1))
			buff.deleteCharAt(buff.length() - 1);
	}

	private String getListLocations(String[] arr) {
		StringBuffer buff = new StringBuffer();
		buff.append("{");
		for (int i = 0; i < arr.length; i++) {
			buff.append("{code:\"");
			buff.append(arr[i]);
			buff.append("{display:\"");
			buff.append(arr[i]);
			buff.append("\"},");
		}
		buff.deleteCharAt(buff.length() - 1);
		return buff.toString();
	}

	
	/**
	 * This method is called when the user clicks on the search button on the
	 * search screen The user can select the options or search without any
	 * parameters.
	 * 
	 * @return String
	 */
	public String searchAgyDeptList() {
		saveUserSelectionInSession(getChosenDepartment(), getChosenAgency());
		agyDeptLocDsp = new AgyOptLocalDSP(entityManager);
		List<DisplayAgencyDept> dispList = new ArrayList();
		String deptCode = extractCode(getChosenDepartment());
		String agencyCode = extractCode(getChosenAgency());
		
		if( (deptCode != null && !"".equals(deptCode)) && agencyCode!=null &&!"".equals(agencyCode)){
			dispList = agyDeptLocDsp.findSearchResultsWithParam(deptCode,
					agencyCode);
		} else {
			if(deptCode != null && !"".equals(deptCode)&& (agencyCode==null || "".equals(agencyCode)))
			{
				dispList = agyDeptLocDsp.findSearchResultsWithParamDept(deptCode, getLoggedInUser().getUserId(), getModuleIdFromSession());
			}
			else
			{
			
			dispList = agyDeptLocDsp.findSearchResults();
			}
		}
		setJsonResponse(jsonParser.toJson(dispList));
		return IConstants.SUCCESS;
	}

	private String buildJsonResponse(boolean writeAccess) {
		StringBuilder buff = new StringBuilder();
		buff.append("{");
		buff
				.append("departments:"
						+ securityScope
								.getCombinedDepartmentCodeNameWithEmptyElementWithoutAllScopeJson()
						+ ",");
		buff
				.append("agencies:"
						+ securityScope
								.getCombinedAgencyCodeNameWithEmptyElementWithoutAllScopeJson()
						+ ",");
		// initialize to empty string if any is null
		chosenDepartment = (chosenDepartment == null) ? "" : chosenDepartment;
		chosenAgency = (chosenAgency == null) ? "" : chosenAgency;
		// if user preferences are being used, then enable auto search
		if (userPref != null
				&& userPref.containsKey(IConstants.SE_USER_SELECTED_DEPT))
			autoSearch = true;
		buff.append("chosenDepartment:\"" + chosenDepartment + "\",");
		buff.append("chosenAgency:\"" + chosenAgency + "\",");
		buff.append("writeAccess:\"" + writeAccess + "\" ");
		buff.append(",moduleId:\"" + moduleId + "\", ");
		buff.append("autoSearch:" + autoSearch);
		buff.append("}");
		return buff.toString();
	}

	/**
	 * This method is for getting the module ID
	 * 
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
	 * This sets the value of chosen agency
	 * 
	 * @return String chosenAgency
	 */
	public String getChosenAgency() {
		return chosenAgency;
	}

	/**
	 * Returns the value of the chosen agency.
	 * 
	 * @param chosenAgency
	 */

	public void setChosenAgency(String chosenAgency) {
		this.chosenAgency = chosenAgency;
	}

	/**
	 * Gets the value of the chosen department
	 * 
	 * @return chosenDept
	 */
	public String getChosenDept() {
		return chosenDept;
	}

	/**
	 * Sets the value of the chosen department
	 * 
	 * @param chosenDept
	 */
	public void setChosenDept(String chosenDept) {
		this.chosenDept = chosenDept;
	}

	/**
	 * gets the value of the chosen department
	 * 
	 * @return chosenDepartment
	 */

	public String getChosenDepartment() {
		return chosenDepartment;
	}

	/**
	 * sets the value of the chosen department
	 * 
	 * @return chosenDepartment
	 */
	public void setChosenDepartment(String chosenDepartment) {
		this.chosenDepartment = chosenDepartment;
	}

	/**
	 * 
	 * @return securityScope
	 */
	public SecurityScope getSecurityScope() {
		return securityScope;
	}

	public void setSecurityScope(SecurityScope securityScope) {
		this.securityScope = securityScope;
	}

	private String determineChosenDept() {
		return (userPref != null && userPref
				.containsKey(IConstants.SE_USER_SELECTED_DEPT)) ? (String) userPref
				.get(IConstants.SE_USER_SELECTED_DEPT)
				: securityScope.getFirstDepartmentDisplayValue();
	}

	private String determineChosenAgency() {
		return (userPref != null && userPref
				.containsKey(IConstants.SE_USER_SELECTED_AGENCY)) ? (String) userPref
				.get(IConstants.SE_USER_SELECTED_AGENCY)
				: securityScope.getFirstAgencyDisplayValue();
	}

	/**
	 * For a given module, finds the departments that the given user has access
	 * to. Invokes AppointmentDSP.getDepartment(user, moduleID).
	 * 
	 * @param userId
	 * @param moduleId
	 * @return
	 */
	public List<Department> getDepartment() {
		UserProfile user = getUser();
		return appointmentService
				.getDepartments(user, getModuleIdFromSession());
	}

	/**
	 * For a given module, finds the agencies in the given department that the
	 * given user has access to. Invokes AppointmentDSP.getAgency(user,
	 * moduleId, dept).
	 * 
	 * @param userID
	 * @param moduleId
	 * @param deptNo
	 * @return
	 */
	public List<Agency> getAgency(String dept) {
		// if no department present, agencies cannot be found!
		if ("".equals(chosenDepartment)) {
			return null;
		}

		UserProfile user = getUser();
		return appointmentService.getAgencies(user, getModuleIdFromSession(),
				dept);
	}

	private UserProfile getUser() {
		return (UserProfile) session
				.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
	}

	protected String extractCode(String str) {
		if (str == null)
			return null;
		String result = "";

		int index = str.indexOf(" ");
		if (index > 0) {
			result = str.substring(0, index);
		}

		return result;
	}

	protected void saveUserSelectionInSession(String department, String agency) {
		Map<String, Map<String, Object>> userContext = (Map<String, Map<String, Object>>) session
				.get(IConstants.USER_CONTEXT);

		// if it does not exist, create it
		if (userContext == null) {
			userContext = new HashMap<String, Map<String, Object>>();
			session.put(IConstants.USER_CONTEXT, userContext);
		}

		// from global user context find module based prefs
		Map<String, Object> userModuleContext = userContext
				.get(getModuleIdFromSession());
		// if it does not exist, create it
		if (userModuleContext == null) {
			userModuleContext = new HashMap<String, Object>();
			userContext.put(getModuleIdFromSession(), userModuleContext);
		}

		userModuleContext.put(IConstants.SE_USER_SELECTED_DEPT,
				department == null ? "" : department);
		userModuleContext.put(IConstants.SE_USER_SELECTED_AGENCY,
				agency == null ? "" : agency);

	}

	public String getStProv() {
		return stProv;
	}

	public void setStProv(String stProv) {
		this.stProv = stProv;
	}

	/**
	 * gets the value of the deptNum
	 * 
	 * @return deptNum
	 */

	public String getDeptNum() {
		return deptNum;
	}

	/**
	 * sets the value of the deptNum
	 * 
	 * @return deptNum
	 */
	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}

	/**
	 * gets the value of the Agency
	 * 
	 * @return agencyNum
	 */

	public String getAgencyNum() {
		return agencyNum;
	}

	/**
	 * sets the value of the Agency
	 * 
	 * @return agencyNum
	 */
	public void setAgencyNum(String agencyNum) {
		this.agencyNum = agencyNum;
	}

	public void setNDeptAgyLoc(ExpenseLocations nDeptAgyLoc) {
		this.nDeptAgyLoc = nDeptAgyLoc;
	}

	public ExpenseLocations getNDeptAgyLoc() {
		return nDeptAgyLoc;
	}

	/**
	 * This is a getter method to return the list of apency, dept locations
	 * 
	 * @return List<ExpenseLocations>
	 */
	public List<ExpenseLocations> getAgencyDeptLocations() {
		return agencyDeptLocations;
	}

	/**
	 * This is a setter method to return the list of apency, dept locations
	 * 
	 * @param agencyDeptLocations
	 */
	public void setAgencyDeptLocations(
			List<ExpenseLocations> agencyDeptLocations) {
		this.agencyDeptLocations = agencyDeptLocations;
	}

	/**
	 * This method sets the value of a department
	 * 
	 * @param department_cb
	 */
	public void setDepartment_cb(String department_cb) {
		this.department_cb = department_cb;
	}

	/**
	 * This method gets the value of the department;
	 * 
	 * @return String department_cb
	 */
	public String getDepartment_cb() {
		return department_cb;
	}

	/**
	 * This method sets the value of agency
	 * 
	 * @param agency_cb
	 */
	public void setAgency_cb(String agency_cb) {
		this.agency_cb = agency_cb;
	}

	/**
	 * This method returns the value of the agency
	 * 
	 * @return String agency_cb
	 */
	public String getAgency_cb() {
		return agency_cb;
	}
/**
 * Setter Method ag_location
 * @param ag_location
 */
	public void setAg_location(String ag_location) {
		this.ag_location = ag_location;
	}
/**
 Getter method ag_location
 * @return String 
 */
	public String getAg_location() {
		return ag_location;
	}
 /**
  * Setter Method identifierKey
  * @param identifierKey
  */
	public void setIdentifierKey(String identifierKey) {
		this.identifierKey = identifierKey;
	}
/**
 * Getter Method identifierKey
 * @return
 */
	public String getIdentifierKey() {
		return identifierKey;
	}
/**
 * Setter Method state_cb
 * @return
 */
	public String getState_cb() {
		return state_cb;
	}
/**
 * Getter Method state_cb
 * @param state_cb
 */
	public void setState_cb(String state_cb) {
		this.state_cb = state_cb;
	}
/**
 * Setter Method agyName
 * @return
 */
	public String getAgyName() {
		return agyName;
	}
/**
 * Getter Method agyName
 * @param agyName
 */
	public void setAgyName(String agyName) {
		this.agyName = agyName;
	}
/**
 * Getter Method toMiles
 * @return
 */
	public String getToMiles() {
		return toMiles;
	}
/**
 * Setter Method toMiles
 * @param toMiles
 */
	public void setToMiles(String toMiles) {
		this.toMiles = toMiles;
	}
/**
 * Getter Method fromMiles
 * @return
 */
	public String getFromMiles() {
		return fromMiles;
	}
/**
 * * Setter Method fromMiles 
 * @param fromMiles
 */
	public void setFromMiles(String fromMiles) {
		this.fromMiles = fromMiles;
	}
/**
 * * Getter Method newMileAgy
 * @return
 */
	public String getNewMileAgy() {
		return newMileAgy;
	}
/**
 * Setter Method newMileAgy
 * @param newMileAgy
 */
	public void setNewMileAgy(String newMileAgy) {
		this.newMileAgy = newMileAgy;
	}
/**
 * Getter Method agNVal
 * @return
 */
	public String getAgNVal() {
		return agNVal;
	}
/**
 *  Setter Method agNVal
 * @param agNVal
 */
	public void setAgNVal(String agNVal) {
		this.agNVal = agNVal;
	}
/**
 *  Getter Method module_hidden
 * @return
 */
	public String getModule_hidden() {
		return module_hidden;
	}
/**
 * Setter Method module_hidden
 * @param module_hidden
 */
	public void setModule_hidden(String module_hidden) {
		this.module_hidden = module_hidden;
	}

public String getSaveStateMiles() {
	return saveStateMiles;
}

public void setSaveStateMiles(String saveStateMiles) {
	this.saveStateMiles = saveStateMiles;
}
}
