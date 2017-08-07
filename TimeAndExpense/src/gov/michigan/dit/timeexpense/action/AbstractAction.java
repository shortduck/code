package gov.michigan.dit.timeexpense.action;

/**
 * This class is meant to be used as parent for Time and Expense
 * web actions for main pages, i.e. pages built using the main template in order
 * to display the menu, header and footer. 
 * 
 * Actions catering exclusively to the Ajax requests do not require the menu
 * options and should, therefore, not extend from this action. 
 * Instead they should extend from <code>BaseAction</code>.
 * 
 * ZH - 01/29/2009
 */

import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.display.LeftNavOptions;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

public abstract class AbstractAction extends BaseAction implements ServletRequestAware,
		ServletResponseAware {

	private static final long serialVersionUID = 6778660138140563740L;
	
	protected LeftNavOptions menuOptions;
	private HttpServletResponse response;
	private HttpServletRequest request;
	private String moduleId = "";
	
	Logger logger = Logger.getLogger(AbstractAction.class);

	/**
	 * Initializes the menu options using security settings for the logged in user
	 */
	public void initialize() {
		// Check to see if the session is already loaded with the menu options
		// accessible for the current user.
		menuOptions = (LeftNavOptions) session.get(IConstants.LEFT_NAV_OPTIONS);

		if (menuOptions == null) {
			logger.info("Initializing user menu options using security modules");
			// First time - Set menu options
			menuOptions = new LeftNavOptions();
			UserProfile profile = (UserProfile) session
					.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
			Set<String> modules = profile.getModules();
			Iterator<String> iterator = modules.iterator();
			// Report modules are not included since all report options will be enabled
			// default. Scope will limit the data included in each report
			while (iterator.hasNext()) {
				String module = iterator.next();
				if (module.equals(IConstants.ADVANCE_EMPLOYEE))
					menuOptions.setEmpAdvance(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.TRAVEL_REQUISITIONS_EMPLOYEE))
					menuOptions.setEmpNonRoutineTravelRequisition(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.EXPENSE_EMPLOYEE))
					menuOptions.setEmpExpense(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.ADVANCE_MANAGER))
					menuOptions.setMngrAdvance(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.TRAVEL_REQUISITIONS_MANAGER))
					menuOptions.setMngrNonRoutineTravelRequisition(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.APPROVE_WEB_MANAGER))
					menuOptions.setMngrApproval(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.EXPENSE_MANAGER))
					menuOptions.setMngrExpense(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.ADVANCE_STATEWIDE))
					menuOptions.setSwAdvance(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.TRAVEL_REQUISITIONS_STATEWIDE))
					menuOptions.setSwNonRoutineTravelRequisition(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.APPROVE_WEB_STATEWIDE))
					menuOptions.setSwApproval(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.EXPENSE_STATEWIDE))
					menuOptions.setSwExpense(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.EMPLOYEE_REPORT_RECEIPTS_REQUIRED))
					menuOptions.setEmp_REPORT_RECEIPTS_REQUIRED(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.EMPLOYEE_REPORT_EXCEPTION))
					menuOptions.setEmp_REPORT_EXCEPTION(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.EMPLOYEE_REPORT_ROUTINE_TRAVELER))
					menuOptions.setEmp_REPORT_ROUTINE_TRAVELER(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.EMPLOYEE_REPORT_TRANSACTION_LEDGER) &&
						setTranLedgerMenuOption(IConstants.EMPLOYEE_REPORT_TRANSACTION_LEDGER))
					menuOptions.setEmp_REPORT_TRANSACTION_LEDGER(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.MANAGER_REPORT_RECEIPTS_REQUIRED))
					menuOptions.setMngr_REPORT_RECEIPTS_REQUIRED(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.MANAGER_REPORT_EXCEPTION))
					menuOptions.setMngr_REPORT_EXCEPTION(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.MANAGER_REPORT_ROUTINE_TRAVELER))
					menuOptions.setMngr_REPORT_ROUTINE_TRAVELER(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.MANAGER_REPORT_TRANSACTION_LEDGER) && 
						setTranLedgerMenuOption(IConstants.MANAGER_REPORT_TRANSACTION_LEDGER))
					menuOptions.setMngr_REPORT_TRANSACTION_LEDGER(IConstants.STRING_BLANK);
				
				else if (module.equals(IConstants.MANAGER_REPORT_NON_ROUTINE_TRAVELER))
					menuOptions.setMngr_REPORT_NON_ROUTINE_TRAVELER(IConstants.STRING_BLANK);
			
				else if (module.equals(IConstants.STATEWIDE_REPORT_RECEIPTS_REQUIRED))
					menuOptions.setSw_REPORT_RECEIPTS_REQUIRED(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.STATEWIDE_REPORT_EXCEPTION))
					menuOptions.setSw_REPORT_EXCEPTION(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.STATEWIDE_REPORT_ROUTINE_TRAVELER))
					menuOptions.setSw_REPORT_ROUTINE_TRAVELER(IConstants.STRING_BLANK);
				
				else if (module.equals(IConstants.STATEWIDE_REPORT_NON_ROUTINE_TRAVELER))
					menuOptions.setSw_REPORT_NON_ROUTINE_TRAVELER(IConstants.STRING_BLANK);
				
				else if (module.equals(IConstants.STATEWIDE_PARAM_SYSTEM_CODES))				 
					menuOptions.setSw_PARAM_SYSTEM_CODES(IConstants.STRING_BLANK);	
				else if (module.equals(IConstants.STATEWIDE_AGENCY_OPTION_LOCATION))				 
					menuOptions.setSw_Agency_Locations(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.STATEWIDE_AGENCY_LOC_AVAILABLE))
					menuOptions.setSw_Agy_State_Locations(IConstants.STRING_BLANK);
				else if (module.equals(IConstants.STATEWIDE_REPORT_TRANSACTION_LEDGER) &&
						setTranLedgerMenuOption(IConstants.STATEWIDE_REPORT_TRANSACTION_LEDGER))
					menuOptions.setSw_REPORT_TRANSACTION_LEDGER(IConstants.STRING_BLANK);
				}
			// add main report options 
			this.setParamOptions(menuOptions);
			this.setAgencyOptions(menuOptions);			
			this.setMainReportOptions(menuOptions);	
			}
		
			// Add menu options to the session for subsequent access
			session.put(IConstants.LEFT_NAV_OPTIONS, menuOptions);
		}


	private void setParamOptions(LeftNavOptions menuOptions)
	{
		if(IConstants.STRING_BLANK.equals(menuOptions.getSw_PARAM_SYSTEM_CODES()))
		{			menuOptions.setSwParams(IConstants.STRING_BLANK);
		}
	}
	private void setAgencyOptions(LeftNavOptions menuOptions)
	{
		if(IConstants.STRING_BLANK.equals(menuOptions.getSw_Agency_Locations()))
		{			menuOptions.setSwAgencyOptions(IConstants.STRING_BLANK);
		}
	}
private void setMainReportOptions (LeftNavOptions menuOptions){
	// Employee
	if (IConstants.STRING_NONE.equals(menuOptions.getEmp_REPORT_RECEIPTS_REQUIRED())&&
			IConstants.STRING_NONE.equals(menuOptions.getEmp_REPORT_EXCEPTION()) &&
			IConstants.STRING_NONE.equals(menuOptions.getEmp_REPORT_ROUTINE_TRAVELER()) &&
			IConstants.STRING_NONE.equals(menuOptions.getEmp_REPORT_TRANSACTION_LEDGER())){
				menuOptions.setEmpReports(IConstants.STRING_NONE);
			}
	else {
		menuOptions.setEmpReports(IConstants.STRING_BLANK);
	}
	// Manager
	if (IConstants.STRING_NONE.equals(menuOptions.getMngr_REPORT_RECEIPTS_REQUIRED())&&
			IConstants.STRING_NONE.equals(menuOptions.getMngr_REPORT_EXCEPTION()) &&
			IConstants.STRING_NONE.equals(menuOptions.getMngr_REPORT_ROUTINE_TRAVELER()) &&
			IConstants.STRING_NONE.equals(menuOptions.getMngr_REPORT_NON_ROUTINE_TRAVELER()) &&
			IConstants.STRING_NONE.equals(menuOptions.getMngr_REPORT_TRANSACTION_LEDGER())){
				menuOptions.setMngrReports(IConstants.STRING_NONE);
			}
	else {
		menuOptions.setMngrReports(IConstants.STRING_BLANK);
	}
	// Statewide
	if (IConstants.STRING_NONE.equals(menuOptions.getSw_REPORT_RECEIPTS_REQUIRED())&&
			IConstants.STRING_NONE.equals(menuOptions.getSw_REPORT_EXCEPTION()) &&
			IConstants.STRING_NONE.equals(menuOptions.getSw_REPORT_ROUTINE_TRAVELER()) &&
			IConstants.STRING_NONE.equals(menuOptions.getSw_REPORT_NON_ROUTINE_TRAVELER()) &&
			IConstants.STRING_NONE.equals(menuOptions.getSw_REPORT_TRANSACTION_LEDGER())){
				menuOptions.setSwReports(IConstants.STRING_NONE);
					
			}
	else {
		menuOptions.setSwReports(IConstants.STRING_BLANK);
	}
	
	
}
	
	
	/**
	 * Sets up json data for the grid
	 * 
	 * @param list: The list of rows containing data for the grid
	 */
	
	public void writeResponseJsonString(List list) {
		TimeAndExpenseUtil util = new TimeAndExpenseUtil();
		String json = util.getJsonStringForGrid(list);
		logger.info("AbstractAction: Print Json String");
		logger.info(json);
		// write out json string as a reponse to the Ajax call
		try {
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//TODO - Set transaction ledger to true only if Statewide
	
	public boolean setTranLedgerMenuOption(String module) {
		if (IConstants.STATEWIDE_REPORT_TRANSACTION_LEDGER.equals(module))
			return true;			
		
		return false;

	}

	public LeftNavOptions getMenuOptions() {
		return menuOptions;
	}

	public void setMenuOptions(LeftNavOptions menuOptions) {
		this.menuOptions = menuOptions;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		response = arg0;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
}
