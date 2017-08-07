package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="TRAVEL_REQ_EVENTS")
public class TravelReqEvents implements Serializable {
	@Id
    @SequenceGenerator(name = "TRAVEL_REQ_EVENTS_GENERATOR", sequenceName = "TREQE_IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAVEL_REQ_EVENTS_GENERATOR")
	@Column(name="TREQE_IDENTIFIER")
	private Integer treqeIdentifier;

	@Column(name="APPT_IDENTIFIER")
	private Integer apptIdentifier;

	@Column(name="ADEV_IDENTIFIER")
	private Integer adevIdentifier;

	@Column(name="EXPEV_IDENTIFIER")
	private Integer expevIdentifier;

	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="treqeIdentifier")
	private List<TravelReqMasters> travelReqMastersCollection;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;

	private static final long serialVersionUID = 1L;

	public TravelReqEvents() {
		super();
	}

	public Integer getTreqeIdentifier() {
		return this.treqeIdentifier;
	}

	public void setTreqeIdentifier(Integer treqeIdentifier) {
		this.treqeIdentifier = treqeIdentifier;
	}

	public Integer getApptIdentifier() {
		return this.apptIdentifier;
	}

	public void setApptIdentifier(Integer apptIdentifier) {
		this.apptIdentifier = apptIdentifier;
	}

	public Integer getAdevIdentifier() {
		return this.adevIdentifier;
	}

	public void setAdevIdentifier(Integer adevIdentifier) {
		this.adevIdentifier = adevIdentifier;
	}

	public Integer getExpevIdentifier() {
		return this.expevIdentifier;
	}

	public void setExpevIdentifier(Integer expevIdentifier) {
		this.expevIdentifier = expevIdentifier;
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

	public List<TravelReqMasters> getTravelReqMastersCollection() {
		return this.travelReqMastersCollection;
	}

	public void setTravelReqMastersCollection(List<TravelReqMasters> travelReqMastersCollection) {
		this.travelReqMastersCollection = travelReqMastersCollection;
	}

}
