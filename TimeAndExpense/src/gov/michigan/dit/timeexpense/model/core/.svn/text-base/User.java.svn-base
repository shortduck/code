package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USER_IDS")
//@NamedQuery(name="findUserByEmpId", 
	//		query="select u from User u where u.empIdentifier = :empId")
// login changes Requirement #49
@NamedQueries({@NamedQuery(name="findUserByEmpId", 
			query="select u from User u where u.empIdentifier = :empId"),@NamedQuery(name="findUserByNonEmpId", 
			query="select u from User u where u.nempIdentifier = :nempIdentifier")})			
		

public class User implements Serializable {
	@Id
	@Column(name="USER_ID")
	private String userId;

	@Column(name="EMP_IDENTIFIER")
	private int empIdentifier;

	@Column(name="MAIL_ID")
	private String email;
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER)
	private Set<Security> security;

	@Column(name="LAST_LOGIN_DATE")
	private Date lastLogin;

	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;

	@Column(name="NEMP_IDENTIFIER")
	private int nempIdentifier;
	
	private static final long serialVersionUID = 17000L;

	public User() {
		super();
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getEmpIdentifier() {
		return this.empIdentifier;
	}

	public void setEmpIdentifier(int empIdentifier) {
		this.empIdentifier = empIdentifier;
	}

	public Set<Security> getSecurity() {
		return this.security;
	}

	public void setSecurity(Set<Security> securityCollection) {
		this.security = securityCollection;
	}

	
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public String getModifiedUserId() {
		return modifiedUserId;
	}


}
