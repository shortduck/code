package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to contain information about the logged in user and the security
 * modules he has access to.
 * 
 * PS: All the security modules present in the set are in upper case.
 * 
 * @author chaudharym
 */
public class UserProfile implements Serializable{

	private static final long serialVersionUID = -8646810111886454943L;

	private String userId;
	private int empIdentifier;
	private Set<String> modules;
	
	public UserProfile(String userId) {
		super();
		this.userId = userId;
	}
	
	public UserProfile(UserProfile user) {
		super();
		userId = user.getUserId();
		empIdentifier = user.getEmpIdentifier();
		
		modules = new HashSet<String>();
		modules.addAll(user.getModules());
	}
	

	public UserProfile(String userId, int empIdentifier) {
		super();
		this.empIdentifier = empIdentifier;
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getEmpIdentifier() {
		return empIdentifier;
	}

	public void setEmpIdentifier(int empIdentifier) {
		this.empIdentifier = empIdentifier;
	}

	public Set<String> getModules() {
		return modules;
	}

	public void setModules(Set<String> modules) {
		this.modules = modules;
	}
		
}
