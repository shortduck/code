package gov.michigan.dit.timeexpense.model.core;

import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.openjpa.persistence.ElementDependent;

@Entity
@Table(name="TRAVEL_REQ_MASTERS")
public class TravelReqMasters implements Serializable {
	@Id
    @SequenceGenerator(name = "TRAVEL_REQ_MASTERS_GENERATOR", sequenceName = "TREQM_IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAVEL_REQ_MASTERS_GENERATOR")
	@Column(name="TREQM_IDENTIFIER")
	private Integer treqmIdentifier;

	@Column(name="REVISION_NUMBER")
	private int revisionNumber;

	@Column(name="CURRENT_IND")
	private String currentInd;
	
	@Column(name="TREQ_DATE_REQUEST")
	private Date treqDateRequest;

	@Column(name="TREQ_DATE_FROM")
	private Date treqDateFrom;

	@Column(name="TREQ_DATE_TO")
	private Date treqDateTo;

	@Column(name="NATURE_OF_BUSINESS")
	private String natureOfBusiness;

	private String status;

	@Column(name="TRANSPORTATION_VIA")
	private String transportationVia;

	@Column(name="OUT_OF_STATE_IND")
	private String outOfStateInd;

	private String comments;
	
	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@ManyToOne
	@JoinColumn(name="TREQE_IDENTIFIER")
	private TravelReqEvents treqeIdentifier;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="treqmIdentifier", fetch = EAGER)
	private List<TravelReqDetails> travelReqDetailsCollection;

	@ElementDependent
	@OneToMany(cascade = CascadeType.ALL, mappedBy="treqmIdentifier", fetch = EAGER)
	private List<TravelReqOutOfState> travelReqOutOfStateCollection;

	@ElementDependent
	@OneToMany(cascade = CascadeType.ALL, mappedBy="treqmIdentifier", fetch = EAGER)
	private List<TravelReqActions> travelReqActionsCollection;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "treqmIdentifier", fetch = EAGER)
	private List<TravelReqErrors> treqErrorsCollection;
	
	@Column(name="REQUEST_ADVANCE")
	private String requestAdvance;
	
	@Column(name="OFFICE_PROGRAM")
	private String officeProgram;
	
	@Column(name="DESTINATION")
	private String destination;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;
	
	public TravelReqMasters(TravelReqMasters _old) {
		super();
		revisionNumber = _old.revisionNumber;
		currentInd = _old.currentInd;
		treqDateRequest = _old.treqDateRequest;
		treqDateFrom = _old.treqDateFrom;
		treqDateTo = _old.treqDateTo;
		transportationVia = _old.transportationVia;
		natureOfBusiness = _old.natureOfBusiness;
		comments = _old.comments;
		status = _old.status;
		outOfStateInd = _old.outOfStateInd;
		destination = _old.destination;
		officeProgram = _old.officeProgram;
		requestAdvance = _old.requestAdvance;
        modifiedUserId = _old.modifiedUserId;
		modifiedDate = new Date();
		
		// deep copy out of state travel list
		travelReqOutOfStateCollection = new ArrayList<TravelReqOutOfState>(_old.getTravelReqOutOfStateCollection().size());
		for(TravelReqOutOfState _oldOutOfStateTravel: _old.getTravelReqOutOfStateCollection()){
			TravelReqOutOfState _newOutOfStateTravel = new TravelReqOutOfState(_oldOutOfStateTravel);
			_newOutOfStateTravel.setTreqmIdentifier(this);
			travelReqOutOfStateCollection.add(_newOutOfStateTravel);
		}
		
		// deep copy expense details
		/*travelReqDetailsCollection = new ArrayList<TravelReqDetails>(_old.getTravelReqDetailsCollection().size());
		for(TravelReqDetails oldDetail : _old.getTravelReqDetailsCollection()){
			TravelReqDetails _newDetail = new TravelReqDetails(oldDetail);
			_newDetail.setTreqmIdentifier(this);
			travelReqDetailsCollection.add(_newDetail);
		}*/
	
		// deep copy expense errors
		treqErrorsCollection = new ArrayList<TravelReqErrors>(_old.getTreqErrorsCollection().size());
		for(TravelReqErrors oldError : _old.getTreqErrorsCollection()){
			TravelReqErrors newError = new TravelReqErrors(oldError);
			newError.setTreqmIdentifier(this);
			treqErrorsCollection.add(newError);
		}
	}

	public TravelReqMasters() {
		super();
	}

	public Integer getTreqmIdentifier() {
		return this.treqmIdentifier;
	}

	public void setTreqmIdentifier(Integer treqmIdentifier) {
		this.treqmIdentifier = treqmIdentifier;
	}

	public int getRevisionNumber() {
		return this.revisionNumber;
	}

	public void setRevisionNumber(int revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	public String getCurrentInd() {
		return this.currentInd;
	}

	public void setCurrentInd(String currentInd) {
		this.currentInd = currentInd;
	}

	public Date getTreqDateFrom() {
		return this.treqDateFrom;
	}

	public void setTreqDateFrom(Date treqDateFrom) {
		this.treqDateFrom = treqDateFrom;
	}

	public Date getTreqDateTo() {
		return this.treqDateTo;
	}

	public void setTreqDateTo(Date treqDateTo) {
		this.treqDateTo = treqDateTo;
	}

	public String getNatureOfBusiness() {
		return this.natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransportationVia() {
		return this.transportationVia;
	}

	public void setTransportationVia(String transportationVia) {
		this.transportationVia = transportationVia;
	}

	public String getOutOfStateInd() {
		return this.outOfStateInd;
	}

	public void setOutOfStateInd(String outOfStateInd) {
		this.outOfStateInd = outOfStateInd;
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

	public TravelReqEvents getTreqeIdentifier() {
		return this.treqeIdentifier;
	}

	public void setTreqeIdentifier(TravelReqEvents treqeIdentifier) {
		this.treqeIdentifier = treqeIdentifier;
	}

	public List<TravelReqDetails> getTravelReqDetailsCollection() {
		return this.travelReqDetailsCollection;
	}

	public void setTravelReqDetailsCollection(List<TravelReqDetails> travelReqDetailsCollection) {
		this.travelReqDetailsCollection = travelReqDetailsCollection;
	}

	public List<TravelReqOutOfState> getTravelReqOutOfStateCollection() {
		return this.travelReqOutOfStateCollection;
	}

	public void setTravelReqOutOfStateCollection(List<TravelReqOutOfState> travelReqOutOfStateCollection) {
		this.travelReqOutOfStateCollection = travelReqOutOfStateCollection;
	}

	public List<TravelReqActions> getTravelReqActionsCollection() {
		return this.travelReqActionsCollection;
	}
	
	public TravelReqActions findTravelReqActions(String actionCode) {
		for (TravelReqActions travelReqActions : travelReqActionsCollection) {	
			if (travelReqActions.getActionCode().equals(actionCode)) {
				return travelReqActions;
			}
		}
		return null;
	}

	public void setTravelReqActionsCollection(List<TravelReqActions> travelReqActionsCollection) {
		this.travelReqActionsCollection = travelReqActionsCollection;
	}

	public List<TravelReqErrors> getTreqErrorsCollection() {
		return treqErrorsCollection;
	}

	public void setTreqErrorsCollection(List<TravelReqErrors> treqErrorsCollection) {
		this.treqErrorsCollection = treqErrorsCollection;
	}
	
	/**
	 * Convenience method to return boolean depending upon
	 * the 'outOfStateInd' value.
	 * 
	 * @return TRUE - If out of state travel
	 * 			FALSE - If NOT out of state travel
	 */
	public boolean isOutOfStateInd() {
		return "Y".equals(outOfStateInd)?true:false;
	}

	/**
	 * Convenience method to populate 'outOfStateInd' value.
	 * 
	 */
	public void setOutOfStateInd(boolean outOfStateInd) {
		this.outOfStateInd = outOfStateInd ? "Y" : "N";
	}

	public Date getTreqDateRequest() {
		return treqDateRequest;
	}

	public void setTreqDateRequest(Date treqDateRequest) {
		this.treqDateRequest = treqDateRequest;
	}

	public String getRequestAdvance() {
		return requestAdvance;
	}

	public void setRequestAdvance(String requestAdvance) {
		this.requestAdvance = requestAdvance;
	}
	/**
	 * Convenience method to return boolean depending upon
	 * the 'outOfStateInd' value.
	 * 
	 * @author chaudharym
	 * @return TRUE - If out of state travel
	 * 			FALSE - If NOT out of state travel
	 */
	public boolean isRequestAdvance() {
		return "Y".equals(requestAdvance)?true:false;
	}
	
	public void setRequestAdvance(boolean requestAdvance) {
		this.requestAdvance = requestAdvance ? "Y" : "N";
	}

	public String getOfficeProgram() {
		return officeProgram;
	}

	public void setOfficeProgram(String officeProgram) {
		this.officeProgram = officeProgram;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
