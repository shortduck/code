package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "STATES")
@NamedQueries({@NamedQuery(name = "States.findByStates", query = "SELECT s FROM SystemCodes s states")})
public class States implements Serializable {
	@Id
	@Column(name="STATE_CODE")
	private String stateCode;
 
	@Column(name="STATE_NAME")
	private String stateName;

	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="DISPLAY_ORDER")
	private String displayOrder;
	
	@Transient
	private String display;

	@Transient
	private String code;

	private static final long serialVersionUID = 1L;

	public States() {
		super();
	}
	
	
	public String getDisplay()
	{
		return this.stateCode;
	}
	 public void setDisplay(String display)
	 {
		 this.display =display;
	 }
	 
		
		public String getCode()
		{
			return code;
		}
		 public void setCode(String code)
		 {
			 this.code =code;
		 }

	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getModifiedUserId() {
		return this.modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

}
