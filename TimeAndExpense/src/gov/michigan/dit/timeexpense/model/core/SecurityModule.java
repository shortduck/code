package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;

/**
 * Stores DCDS module access information.
 * This information is fetched for a user just once during his profile creation.
 * 1) The moduleId represents the module information.
 * 2) ChangeAccessIndicator represents the change access privilege granted to the user for the module.
 *    It can have 2 possible values:
 *    i) "Y" - Represents update access for module
 *    ii) "N" - Represents read-only access for module
 * 
 * @author chaudharym
 */
public class SecurityModule implements Serializable {

	private static final long serialVersionUID = -7710511215683155291L;

	private final String moduleId;

	private final boolean changeAccess;

	private final String department;

	private final String agency;

	private final String tku;

	public SecurityModule(String moduleId, boolean changeAccess, String department, String agency, String tku) {
		this.agency = agency;
		this.changeAccess = changeAccess;
		this.department = department;
		this.moduleId = moduleId;
		this.tku = tku;
	}

	public String getModuleId() {
		return moduleId;
	}

	public boolean isChangeAccess() {
		return changeAccess;
	}

	public String getDepartment() {
		return department;
	}

	public String getAgency() {
		return agency;
	}

	public String getTku() {
		return tku;
	}
	
}
