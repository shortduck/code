package gov.michigan.dit.timeexpense.model.core;

import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.persistence.ElementDependent;

@Entity
@Table(name="TRAVEL_REQ_DETAILS")
public class TravelReqDetails implements Serializable {
	@Id
    @SequenceGenerator(name = "TRAVEL_REQ_DETAILS_GENERATOR", sequenceName = "TREQD_IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAVEL_REQ_DETAILS_GENERATOR")
	@Column(name="TREQD_IDENTIFIER")
	private Integer treqdIdentifier;

	@Column(name="TRANSPORTATION_AMOUNT")
	private double transportationAmount;
	
	@Column(name="AIRFARE_AMOUNT")
	private double airfareAmount;
	
	@Column(name="LODGING_AMOUNT")
	private double lodgingAmount;

	@Column(name="MEALS_AMOUNT")
	private double mealsAmount;

	@Column(name="REGIST_AMOUNT")
	private double registAmount;

	@Column(name="OTHER_AMOUNT")
	private double otherAmount;

	@Column(name="DOLLAR_AMOUNT")
	private double dollarAmount;
	
	@Transient
	private String transportationAmountDisplay;
	
	@Transient
	private String airfareAmountDisplay;
	
	@Transient
	private String lodgingAmountDisplay;

	@Transient
	private String mealsAmountDisplay;

	@Transient
	private String registAmountDisplay;

	@Transient
	private String otherAmountDisplay;

	@Transient
	private String dollarAmountDisplay;

	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@ManyToOne
	@JoinColumn(name="TREQM_IDENTIFIER")
	private TravelReqMasters treqmIdentifier;

	@ElementDependent
	@OneToMany(cascade = CascadeType.ALL, mappedBy="treqdIdentifier", fetch = FetchType.LAZY)
	private List<TravelReqDetailCodingBlock> travelReqDetailCodingBlockCollection;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;

	private static final long serialVersionUID = 1L;

	public TravelReqDetails() {
		super();
	}
	
	public TravelReqDetails(TravelReqDetails oldDetail) {
		transportationAmount = oldDetail.transportationAmount;
		airfareAmount=oldDetail.airfareAmount;
		lodgingAmount = oldDetail.lodgingAmount;
		mealsAmount = oldDetail.mealsAmount;
		registAmount = oldDetail.registAmount;
		otherAmount = oldDetail.otherAmount;
		dollarAmount = oldDetail.dollarAmount;
		modifiedUserId = oldDetail.modifiedUserId;
		modifiedDate = oldDetail.modifiedDate;
		treqmIdentifier = oldDetail.treqmIdentifier;
		
		// deep copy coding blocks
		travelReqDetailCodingBlockCollection = new ArrayList<TravelReqDetailCodingBlock>(oldDetail.getTravelReqDetailCodingBlockCollection().size());
		for(TravelReqDetailCodingBlock oldCB : oldDetail.getTravelReqDetailCodingBlockCollection()){
			TravelReqDetailCodingBlock _newCb = new TravelReqDetailCodingBlock(oldCB);
			_newCb.setTreqdIdentifier(this);
			travelReqDetailCodingBlockCollection.add(_newCb);
		}
		
	}

	public long getTreqdIdentifier() {
		return this.treqdIdentifier;
	}

	public void setTreqdIdentifier(Integer treqdIdentifier) {
		this.treqdIdentifier = treqdIdentifier;
	}

	public double getTransportationAmount() {
		return this.transportationAmount;
	}

	public void setTransportationAmount(double transportationAmount) {
		this.transportationAmount = transportationAmount;
	}
    
	
	public double getAirfareAmount() {
		return airfareAmount;
	}

	public void setAirfareAmount(double airfareAmount) {
		this.airfareAmount = airfareAmount;
	}

	public double getMealsAmount() {
		return this.mealsAmount;
	}

	public void setMealsAmount(double mealsAmount) {
		this.mealsAmount = mealsAmount;
	}

	public double getRegistAmount() {
		return this.registAmount;
	}

	public void setRegistAmount(double registAmount) {
		this.registAmount = registAmount;
	}

	public double getOtherAmount() {
		return this.otherAmount;
	}

	public void setOtherAmount(double otherAmount) {
		this.otherAmount = otherAmount;
	}

	public double getDollarAmount() {
		return this.dollarAmount;
	}

	public void setDollarAmount(double dollarAmount) {
		this.dollarAmount = dollarAmount;
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

	public List<TravelReqDetailCodingBlock> getTravelReqDetailCodingBlockCollection() {
		return this.travelReqDetailCodingBlockCollection;
	}

	public void setTravelReqDetailCodingBlockCollection(List<TravelReqDetailCodingBlock> travelReqDetailCodingBlockCollection) {
		this.travelReqDetailCodingBlockCollection = travelReqDetailCodingBlockCollection;
	}

	public double getLodgingAmount() {
		return lodgingAmount;
	}

	public void setLodgingAmount(double lodgingAmount) {
		this.lodgingAmount = lodgingAmount;
	}

	/*public String getTransportationAmountDisplay() {
		if (transportationAmount > 0)
			return TimeAndExpenseUtil.displayAmountTwoDigits(transportationAmount);
		else
			return "";
	}*/
	
	public String getTransportationAmountDisplay() {
		return formatAmountDisplay(transportationAmount);		
	}

	public void setTransportationAmountDisplay(String transportationAmountDisplay) {
		if (!StringUtils.isEmpty(transportationAmountDisplay))
			this.transportationAmount = Double.parseDouble(transportationAmountDisplay);
	}
	
	public String getAirfareAmountDisplay() {
		 return formatAmountDisplay(airfareAmount);
	}

	public void setAirfareAmountDisplay(String airfareAmountDisplay) {
		if (!StringUtils.isEmpty(airfareAmountDisplay))
			this.airfareAmount = Double.parseDouble(airfareAmountDisplay);
	}

	public String getLodgingAmountDisplay() {
		return formatAmountDisplay(lodgingAmount);
	}

	public void setLodgingAmountDisplay(String lodgingAmountDisplay) {
		if (!StringUtils.isEmpty(lodgingAmountDisplay))
			this.lodgingAmount = Double.parseDouble(lodgingAmountDisplay);
	}

	public String getMealsAmountDisplay() {
		return formatAmountDisplay(mealsAmount);
	}

	public void setMealsAmountDisplay(String mealsAmountDisplay) {
		if (!StringUtils.isEmpty(mealsAmountDisplay))
			this.mealsAmount = Double.parseDouble(mealsAmountDisplay);
	}

	public String getRegistAmountDisplay() {
		return formatAmountDisplay(registAmount);
	}

	public void setRegistAmountDisplay(String registAmountDisplay) {
		if (!StringUtils.isEmpty(registAmountDisplay))
			this.registAmount = Double.parseDouble(registAmountDisplay);
	}

	public String getOtherAmountDisplay() {
		return formatAmountDisplay(otherAmount);
	}

	public void setOtherAmountDisplay(String otherAmountDisplay) {
		if (!StringUtils.isEmpty(otherAmountDisplay))
			this.otherAmount = Double.parseDouble(otherAmountDisplay);
	}

	public String getDollarAmountDisplay() {
		return formatAmountDisplay(dollarAmount);

	}

	public void setDollarAmountDisplay(String dollarAmountDisplay) {
		if (!StringUtils.isEmpty(dollarAmountDisplay))
			this.dollarAmount = Double.parseDouble(dollarAmountDisplay);
	}	
	private String formatAmountDisplay(double amountField){
		String retString = "";
		if (amountField > 0)
			retString = TimeAndExpenseUtil.displayAmountTwoDigits(amountField);
		return retString;
	}
	}
