package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Version;

import org.apache.openjpa.persistence.ElementDependent;

/**
 * 
 * @author GhoshS
 */
@Entity
@Table(name = "EXPENSE_DETAILS")

public class ExpenseDetails implements Serializable {
	private static final long serialVersionUID = -2906821864587757914L;

	@SequenceGenerator(name = "EXPENSE_DETAILS_GENERATOR", sequenceName = "EXPD_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPENSE_DETAILS_GENERATOR")
	@Column(name = "EXPD_IDENTIFIER")
	private Integer expdIdentifier;

	@Column(name = "LINE_ITEM")
	private int lineItem;

	@Column(name = "EXP_DATE")
	private Date expDate;
	
	@Column(name = "DEPART_TIME")
	private Date departTime;
	
	@Column(name = "RETURN_TIME")
	private Date returnTime;
	
	@Column(name = "MILEAGE")
	private double mileage;

	@Column(name = "MILE_OVERRIDE_IND")
	private String mileOverrideInd;
	
	@Column(name = "VICINITY_MILEAGE")
	private double vicinityMileage;
	
	@Column(name = "TO_ELOC_CITY")
	private String toElocCity;
	
	@Column(name = "TO_ELOC_ST_PROV")
	private String toElocStProv;
	
	@Column(name = "FROM_ELOC_CITY")
	private String fromElocCity;
	
	@Column(name = "FROM_ELOC_ST_PROV")
	private String fromElocStProv;

	@Column(name = "ROUND_TRIP_IND")
	private String roundTripInd;

	@Column(name = "DOLLAR_AMOUNT")
	private double dollarAmount;

	@Column(name = "DOLLAR_DIFFERENCE")
	private double dollarDifference;
	
	@Column(name = "COMMENTS")
	private String comments;
	
	@Column(name = "MODIFIED_USER_ID")
	private String modifiedUserId;
	
	@Column(name = "OVERNIGHT_IND")
	private String overnightInd; 				
	
	@Column(name = "MODIFIED_DATE")	
	private Date modifiedDate;
	@JoinColumn(name = "EXPM_IDENTIFIER", referencedColumnName = "EXPM_IDENTIFIER")
	
	@ManyToOne(optional = false)
	private ExpenseMasters expmIdentifier;
	
	@JoinColumn(name = "EXP_TYPE_CODE", referencedColumnName = "EXP_TYPE_CODE")
	@ManyToOne(optional = false)
	private ExpenseTypes expTypeCode;

	@ElementDependent
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "expdIdentifier", fetch = FetchType.LAZY)
	private List<ExpenseDetailCodingBlocks> expenseDetailCodingBlocksCollection;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;

	public ExpenseDetails() {
	}

	public ExpenseDetails(Integer expdIdentifier) {
		this.expdIdentifier = expdIdentifier;
	}

	public ExpenseDetails(Integer expdIdentifier, short lineItem, Date expDate,
			String mileOverrideInd, String roundTripInd, double dollarAmount) {
		this.expdIdentifier = expdIdentifier;
		this.lineItem = lineItem;
		this.expDate = expDate;
		this.mileOverrideInd = mileOverrideInd;
		this.roundTripInd = roundTripInd;
		this.dollarAmount = dollarAmount;
	}

	/** Copy constructor to <b>deep</b> copy all properties except the detailId.
	 *  PS: The collections inside are also deep copied.
	 */
	public ExpenseDetails(ExpenseDetails oldDetail) {
		lineItem = oldDetail.lineItem;
		expDate = oldDetail.expDate;
		departTime = oldDetail.departTime;
		returnTime = oldDetail.returnTime;
		mileage = oldDetail.mileage;
		mileOverrideInd = oldDetail.mileOverrideInd;
		vicinityMileage = oldDetail.vicinityMileage;
		toElocCity = oldDetail.toElocCity;
		toElocStProv = oldDetail.toElocStProv;
		fromElocCity = oldDetail.fromElocCity;
		fromElocStProv = oldDetail.fromElocStProv;
		roundTripInd = oldDetail.roundTripInd;
		dollarAmount = oldDetail.dollarAmount;
		dollarDifference = oldDetail.dollarDifference;
		comments = oldDetail.comments;
		modifiedUserId = oldDetail.modifiedUserId;
		modifiedDate = oldDetail.modifiedDate;
		expTypeCode = oldDetail.expTypeCode;
		overnightInd = oldDetail.overnightInd;
		
		// deep copy coding blocks
		expenseDetailCodingBlocksCollection = new ArrayList<ExpenseDetailCodingBlocks>(oldDetail.getExpenseDetailCodingBlocksCollection().size());
		for(ExpenseDetailCodingBlocks oldCB : oldDetail.getExpenseDetailCodingBlocksCollection()){
			ExpenseDetailCodingBlocks _newCb = new ExpenseDetailCodingBlocks(oldCB);
			_newCb.setExpdIdentifier(this);
			expenseDetailCodingBlocksCollection.add(_newCb);
		}
		
	}
	
	public Integer getExpdIdentifier() {
		return expdIdentifier;
	}

	public void setExpdIdentifier(Integer expdIdentifier) {
		this.expdIdentifier = expdIdentifier;
	}

	public int getLineItem() {
		return lineItem;
	}

	public void setLineItem(int lineItem) {
		this.lineItem = lineItem;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public Date getDepartTime() {
		return departTime;
	}

	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public double getMileage() {
		return mileage;
	}

	public void setMileage(double mileage) {
		this.mileage = mileage;
	}

	public String getMileOverrideInd() {
		return mileOverrideInd;
	}

	public void setMileOverrideInd(String mileOverrideInd) {
		this.mileOverrideInd = mileOverrideInd;
	}

	public double getVicinityMileage() {
		return vicinityMileage;
	}

	public void setVicinityMileage(double vicinityMileage) {
		this.vicinityMileage = vicinityMileage;
	}

	public String getToElocCity() {
		return toElocCity;
	}

	public void setToElocCity(String toElocCity) {
		this.toElocCity = toElocCity;
	}

	public String getToElocStProv() {
		return toElocStProv;
	}

	public void setToElocStProv(String toElocStProv) {
		this.toElocStProv = toElocStProv;
	}

	public String getFromElocCity() {
		return fromElocCity;
	}

	public void setFromElocCity(String fromElocCity) {
		this.fromElocCity = fromElocCity;
	}

	public String getFromElocStProv() {
		return fromElocStProv;
	}

	public void setFromElocStProv(String fromElocStProv) {
		this.fromElocStProv = fromElocStProv;
	}

	public String getRoundTripInd() {
		return roundTripInd;
	}

	public void setRoundTripInd(String roundTripInd) {
		this.roundTripInd = roundTripInd;
	}

	public double getDollarAmount() {
		return dollarAmount;
	}

	public void setDollarAmount(double dollarAmount) {
		this.dollarAmount = dollarAmount;
	}

	public double getDollarDifference() {
		return dollarDifference;
	}

	public void setDollarDifference(double dollarDifference) {
		this.dollarDifference = dollarDifference;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public ExpenseMasters getExpmIdentifier() {
		return expmIdentifier;
	}

	public void setExpmIdentifier(ExpenseMasters expmIdentifier) {
		this.expmIdentifier = expmIdentifier;
	}

	public ExpenseTypes getExpTypeCode() {
		return expTypeCode;
	}

	public void setExpTypeCode(ExpenseTypes expTypeCode) {
		this.expTypeCode = expTypeCode;
	}

	public String getOvernightInd() {
		return overnightInd;
	}

	public void setOvernightInd(String overnightInd) {
		this.overnightInd = overnightInd;
	}

	public boolean hasCommonMilesOverridden(){
		return "Y".equalsIgnoreCase(mileOverrideInd);
	}
	
	public boolean isRoundTrip(){
		return "Y".equalsIgnoreCase(roundTripInd);
	}
	
	public List<ExpenseDetailCodingBlocks> getExpenseDetailCodingBlocksCollection() {
		return expenseDetailCodingBlocksCollection;
	}

	public void setExpenseDetailCodingBlocksCollection(
			List<ExpenseDetailCodingBlocks> expenseDetailCodingBlocksCollection) {
		this.expenseDetailCodingBlocksCollection = expenseDetailCodingBlocksCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (expdIdentifier != null ? expdIdentifier.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof ExpenseDetails)) {
			return false;
		}
		ExpenseDetails other = (ExpenseDetails) object;
		if ((this.expdIdentifier == null && other.expdIdentifier != null)
				|| (this.expdIdentifier != null && !this.expdIdentifier
						.equals(other.expdIdentifier))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.michigan.dit.timeexpense.model.ExpenseDetails[expdIdentifier="
				+ expdIdentifier + "]";
	}

}