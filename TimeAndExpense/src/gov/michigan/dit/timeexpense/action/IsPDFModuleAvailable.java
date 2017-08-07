package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.service.SecurityManager;

import org.apache.log4j.Logger;

public class IsPDFModuleAvailable extends AbstractAction {
	private static Logger logger = Logger.getLogger(LoginAction.class);

	public String execute() {

		UserProfile userProfile = (UserProfile) session
				.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
		UserSubject userSubject = getUserSubject();
		String pdfAccessMode = "no";

		//If this is from employee menu then do not allow PDF expenses.
		//Only a manager can enter PDF expenses for an employee.
		String moduleIdFromSession = getModuleIdFromSession();
		if (moduleIdFromSession != null
				&& (moduleIdFromSession
						.equalsIgnoreCase(IConstants.EXPENSE_EMPLOYEE))) {
			pdfAccessMode = "no";
		} else {

			SecurityManager securityManager = new SecurityManager(entityManager);
			// check if the user has access to PDF expense, then check for the
			// moduleAccess level access
			int isPDFModuleAccessible = securityManager
					.isPDFModuleAvailable(userProfile, getModuleIdFromSession(),
							userSubject.getDepartment(),
							userSubject.getAgency(), userSubject.getTku());
			
			if (isPDFModuleAccessible == IConstants.SECURITY_NO_MODULE_ACCESS)
				pdfAccessMode = "no";
			else if (isPDFModuleAccessible == IConstants.SECURITY_UPDATE_MODULE_ACCESS)
				pdfAccessMode = "yes";
			else if (isPDFModuleAccessible == IConstants.SECURITY_INQUIRY_MODULE_ACCESS)
				pdfAccessMode = "readonly";
		}

		StringBuilder buff = new StringBuilder();
		buff.append("{");
		buff.append("IsPDFRolePResent:\"");
		buff.append(pdfAccessMode);
		buff.append("\"}");

		setJsonResponse(buff.toString());

		return IConstants.SUCCESS;
	}
}
