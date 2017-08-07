package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="TRAVEL_REQ_ACTIONS")
public class TravelReqActions implements Serializable {
	@SequenceGenerator(name = "TRAVEL_REQISITIONS_ACTIONS_GENERATOR", sequenceName = "TREQA_IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAVEL_REQISITIONS_ACTIONS_GENERATOR")
	@Id
	@Column(name="TREQA_IDENTIFIER")
	private Integer treqaIdentifier;

	@Column(name="ACTION_CODE")
	private String actionCode;

	@Column(name="NEXT_ACTION_CODE")
	private String nextActionCode;

	private String comments;

	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@ManyToOne
	@JoinColumn(name="TREQM_IDENTIFIER")
	private TravelReqMasters treqmIdentifier;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;

	private static final long serialVersionUID = 1L;

	public TravelReqActions() {
		super();
	}
	
    public TravelReqActions(TravelReqActions _old) {
        this.actionCode = _old.getActionCode();
    	this.nextActionCode = _old.getNextActionCode();
        this.treqmIdentifier = _old.getTreqmIdentifier();
        this.comments = _old.getComments();
        this.modifiedUserId = _old.getModifiedUserId();
        this.modifiedDate = new Date();
    }

	public Integer getTreqaIdentifier() {
		return this.treqaIdentifier;
	}

	public void setTreqaIdentifier(Integer treqaIdentifier) {
		this.treqaIdentifier = treqaIdentifier;
	}

	public String getActionCode() {
		return this.actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getNextActionCode() {
		return this.nextActionCode;
	}

	public void setNextActionCode(String nextActionCode) {
		this.nextActionCode = nextActionCode;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getModifiedUserId() {
		return this.modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public java.util.Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public TravelReqMasters getTreqmIdentifier() {
		return this.treqmIdentifier;
	}

	public void setTreqmIdentifier(TravelReqMasters treqmIdentifier) {
		this.treqmIdentifier = treqmIdentifier;
	}

}
