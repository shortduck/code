package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Security implements Serializable {
	private static final long serialVersionUID = 170012996L;

	@Id
	@Column(name="URTO_IDENTIFIER")
	private long id;

	@Column(name="MODULE_ID")
	private String moduleId;

	@Column(name="CHANGE_ACCESS_IND")
	private String changeAccessInd;

	private String department;

	private String agency;

	private String tku;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ID")
	private User user;
	
	@Column(name="START_DATE")
	private Date startDate;
	
	@Column(name="END_DATE")
	private Date endDate;

	public Security() {
		super();
	}

	public long getUrtoIdentifier() {
		return this.id;
	}

	public String getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getChangeAccessInd() {
		return this.changeAccessInd;
	}

	public void setChangeAccessInd(String changeAccessInd) {
		this.changeAccessInd = changeAccessInd;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAgency() {
		return this.agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getTku() {
		return this.tku;
	}

	public void setTku(String tku) {
		this.tku = tku;
	}

	public User getUserId() {
		return this.user;
	}

	public void setUserId(User userId) {
		this.user = userId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
