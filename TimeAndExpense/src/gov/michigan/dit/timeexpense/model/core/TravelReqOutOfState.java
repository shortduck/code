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
@Table(name="TRAVEL_REQ_OUT_OF_STATE")
public class TravelReqOutOfState implements Serializable {
	@Id
	@SequenceGenerator(name = "TREQ_OOST_GENERATOR", sequenceName = "TREQ_OOST_IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TREQ_OOST_GENERATOR")
	@Column(name="TREQ_OOST_IDENTIFIER")
	private Integer treqOostIdentifier;

	@Column(name="STAC_IDENTIFIER")
	private Integer stacIdentifier;

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

	public TravelReqOutOfState() {
		super();
	}
	
	public TravelReqOutOfState(TravelReqOutOfState old) {
		stacIdentifier = new Integer(old.stacIdentifier);
		treqmIdentifier = old.treqmIdentifier;
		modifiedUserId = old.modifiedUserId;
		modifiedDate = Calendar.getInstance().getTime();
	}
	public Integer getTreqOostIdentifier() {
		return this.treqOostIdentifier;
	}

	public void setTreqOostIdentifier(Integer treqOostIdentifier) {
		this.treqOostIdentifier = treqOostIdentifier;
	}

	public Integer getStacIdentifier() {
		return this.stacIdentifier;
	}

	public void setStacIdentifier(Integer stacIdentifier) {
		this.stacIdentifier = stacIdentifier;
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

	public TravelReqMasters getTreqmIdentifier() {
		return this.treqmIdentifier;
	}

	public void setTreqmIdentifier(TravelReqMasters treqmIdentifier) {
		this.treqmIdentifier = treqmIdentifier;
	}

}
