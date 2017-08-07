package gov.michigan.dit.timeexpense.model.core;

import gov.michigan.dit.timeexpense.model.core.OutOfStateTravel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.openjpa.persistence.ElementDependent;

/**
 * Class representing expense information.
 * 
 * @author chaudharym
 * @author GhoshS
 */
@Entity
@Table(name = "EXPENSE_MASTERS")

public class ExpenseMasters implements Serializable {
	private static final long serialVersionUID = 6127054022939396866L;

	@Id
	@SequenceGenerator(name = "EXPENSE_MASTERS_GENERATOR", sequenceName = "EXPM_IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPENSE_MASTERS_GENERATOR")
	@Column(name = "EXPM_IDENTIFIER")
	private Integer expmIdentifier;

	@Column(name = "REVISION_NUMBER")
	private int revisionNumber;

	@Column(name = "CURRENT_IND")
	private String currentInd;

	@Column(name = "EXP_DATE_FROM")
	private Date expDateFrom;

	@Column(name = "EXP_DATE_TO")
	private Date expDateTo;

	@Column(name = "NATURE_OF_BUSINESS")
	private String natureOfBusiness;
	
	private String status;

	@Column(name = "TRAVEL_IND")
	private String travelInd;

	@Column(name = "OUT_OF_STATE_IND")
	private String outOfStateInd;
	
	@Column(name = "PDF_IND")
	private String pdfInd;
	
	@Column(name = "ADJ_IDENTIFIER")
	private Long adjIdentifier;
	
	private String comments;

	@Column(name = "SUPER_REVIEWED_RECEIPTS_IND")
	private String superReviewedReceiptsInd;

	@Column(name = "MODIFIED_USER_ID")
	private String modifiedUserId;
	
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	
	@Column(name = "ORIG_PAID_DATE")
	private Date origPaidDate;
	
	@Column(name = "ADJ_TYPE_APPROVAL")
	private String adjTypeApproval;
	
	@ElementDependent
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "expmIdentifier")
	@OrderBy("lineItem")
	private List<ExpenseDetails> expenseDetailsCollection;

	@ElementDependent
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "expmIdentifier")
	private List<ExpenseErrors> expenseErrorsCollection;

	@ElementDependent
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "expmIdentifier")
	private List<OutOfStateTravel> outOfStateTravelList;

	@ManyToOne(optional = false)
	@JoinColumn(name = "EXPEV_IDENTIFIER")
	private ExpenseEvents expevIdentifier;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;

	public ExpenseMasters() {
	}

	public ExpenseMasters(Integer expmIdentifier) {
		this.expmIdentifier = expmIdentifier;
	}

	public ExpenseMasters(Integer expmIdentifier, int revisionNumber,
			String currentInd, Date expDateFrom, Date expDateTo,
			String travelInd, String outOfStateInd,
			String superReviewedReceiptsInd, String pdfInd) {
		this.expmIdentifier = expmIdentifier;
		this.revisionNumber = revisionNumber;
		this.currentInd = currentInd;
		this.expDateFrom = expDateFrom;
		this.expDateTo = expDateTo;
		this.travelInd = travelInd;
		this.outOfStateInd = outOfStateInd;
		this.pdfInd = pdfInd;
		this.superReviewedReceiptsInd = superReviewedReceiptsInd;
	}

	/** Copy constructor to <b>deep</b> copy all properties except the masterId
	 *  and any parent references (e.g. ExpenseEvent reference is not set and left to the caller to associate)
	 *  PS: The collections inside are also deep copied.
	 */
	public ExpenseMasters(ExpenseMasters _old) {
		super();
		revisionNumber = _old.revisionNumber;
		currentInd = _old.currentInd;
		expDateFrom = _old.expDateFrom;
		expDateTo = _old.expDateTo;
		natureOfBusiness = _old.natureOfBusiness;
		status = _old.status;
		travelInd = _old.travelInd;
		outOfStateInd = _old.outOfStateInd;
		pdfInd = _old.pdfInd;
		adjIdentifier = _old.adjIdentifier;
		comments = _old.comments;
		superReviewedReceiptsInd = _old.superReviewedReceiptsInd;
		modifiedUserId = _old.modifiedUserId;
		modifiedDate = new Date();
		origPaidDate = _old.origPaidDate;
		
		// deep copy out of state travel list
		outOfStateTravelList = new ArrayList<OutOfStateTravel>(_old.getOutOfStateTravelList().size());
		for(OutOfStateTravel _oldOutOfStateTravel: _old.getOutOfStateTravelList()){
			OutOfStateTravel _newOutOfStateTravel = new OutOfStateTravel(_oldOutOfStateTravel);
			_newOutOfStateTravel.setExpmIdentifier(this);
			outOfStateTravelList.add(_newOutOfStateTravel);
		}
		
		// deep copy expense details
		expenseDetailsCollection = new ArrayList<ExpenseDetails>(_old.getExpenseDetailsCollection().size());
		for(ExpenseDetails oldDetail : _old.getExpenseDetailsCollection()){
			ExpenseDetails _newDetail = new ExpenseDetails(oldDetail);
			_newDetail.setExpmIdentifier(this);
			expenseDetailsCollection.add(_newDetail);
		}
	
		// deep copy expense errors
		expenseErrorsCollection = new ArrayList<ExpenseErrors>(_old.getExpenseErrorsCollection().size());
		for(ExpenseErrors oldError : _old.getExpenseErrorsCollection()){
			ExpenseErrors newError = new ExpenseErrors(oldError);
			newError.setExpmIdentifier(this);
			expenseErrorsCollection.add(newError);
		}
	}
	
	public Integer getExpmIdentifier() {
		return expmIdentifier;
	}

	public void setExpmIdentifier(Integer expmIdentifier) {
		this.expmIdentifier = expmIdentifier;
	}

	public int getRevisionNumber() {
		return revisionNumber;
	}

	public void setRevisionNumber(int revisionNumber) {
		this.revisionNumber = revisionNumber;
	}

	public String getCurrentInd() {
		return currentInd;
	}

	public void setCurrentInd(String currentInd) {
		this.currentInd = currentInd;
	}

	public Date getExpDateFrom() {
		return expDateFrom;
	}

	public void setExpDateFrom(Date expDateFrom) {
		this.expDateFrom = expDateFrom;
	}

	public Date getExpDateTo() {
		return expDateTo;
	}

	public void setExpDateTo(Date expDateTo) {
		this.expDateTo = expDateTo;
	}

	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTravelInd() {
		return travelInd;
	}

	public void setTravelInd(String travelInd) {
		this.travelInd = travelInd;
	}

	public String getOutOfStateInd() {
		return outOfStateInd;
	}

	public void setOutOfStateInd(String outOfStateInd) {
		this.outOfStateInd = outOfStateInd;
	}

	public String getPdfInd() {
		return pdfInd;
	}
	
	public void setPdfInd(String ind) {
		pdfInd = ind;
	}

	public Long getAdjIdentifier() {
		return adjIdentifier;
	}

	public void setAdjIdentifier(Long adjIdentifier) {
		this.adjIdentifier = adjIdentifier;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSuperReviewedReceiptsInd() {
		return superReviewedReceiptsInd;
	}

	public void setSuperReviewedReceiptsInd(String superReviewedReceiptsInd) {
		this.superReviewedReceiptsInd = superReviewedReceiptsInd;
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

	/**
	 * Convenience method to return boolean depending upon
	 * the 'outOfStateInd' value.
	 * 
	 * @author chaudharym
	 * @return TRUE - If out of state travel
	 * 			FALSE - If NOT out of state travel
	 */
	public boolean isOutOfStateInd() {
		return "Y".equals(outOfStateInd)?true:false;
	}

	/**
	 * Convenience method to populate 'outOfStateInd' value.
	 * 
	 * @author chaudharym
	 */
	public void setOutOfStateInd(boolean outOfStateInd) {
		this.outOfStateInd = outOfStateInd ? "Y" : "N";
	}

	public boolean isPdfInd() {
		return "Y".equals(pdfInd)?true:false;
	}
	
	public void setPdfInd(boolean ind) {
		pdfInd = ind ? "Y" : "N";
	}
	
	public Date getOrigPaidDate() {
		return origPaidDate;
	}

	public void setOrigPaidDate(Date origPaidDate) {
		this.origPaidDate = origPaidDate;
	}

	public List<ExpenseDetails> getExpenseDetailsCollection() {
		return expenseDetailsCollection;
	}

	public void setExpenseDetailsCollection(
			List<ExpenseDetails> expenseDetailsCollection) {
		this.expenseDetailsCollection = expenseDetailsCollection;
	}

	public List<ExpenseErrors> getExpenseErrorsCollection() {
		return expenseErrorsCollection;
	}

	public void setExpenseErrorsCollection(
			List<ExpenseErrors> expenseErrorsCollection) {
		this.expenseErrorsCollection = expenseErrorsCollection;
	}

	public ExpenseEvents getExpevIdentifier() {
		return expevIdentifier;
	}

	public void setExpevIdentifier(ExpenseEvents expevIdentifier) {
		this.expevIdentifier = expevIdentifier;
	}

	public List<OutOfStateTravel> getOutOfStateTravelList() {
		return outOfStateTravelList;
	}

	public void setOutOfStateTravelList(List<OutOfStateTravel> outOfStateTravelList) {
		this.outOfStateTravelList = outOfStateTravelList;
	}
	
	public boolean isTravelRelated() {
		return "Y".equalsIgnoreCase(travelInd); 
	}
	
	public double getAmount(){
		double result = 0;
		
		if(getExpenseDetailsCollection() == null || getExpenseDetailsCollection().size() < 1)
			return result;
		
		for(ExpenseDetails detail : getExpenseDetailsCollection()){
			result += detail.getDollarAmount();
		}
		
		return result;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (expmIdentifier != null ? expmIdentifier.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ExpenseMasters)) {
			return false;
		}
		ExpenseMasters other = (ExpenseMasters) object;
		if ((this.expmIdentifier == null && other.expmIdentifier != null)
				|| (this.expmIdentifier != null && !this.expmIdentifier
						.equals(other.expmIdentifier))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ExpenseMasters[expmIdentifier="+ expmIdentifier + "]";
	}

	public String getAdjTypeApproval() {
		return adjTypeApproval;
	}

	public void setAdjTypeApproval(String adjTypeApproval) {
		this.adjTypeApproval = adjTypeApproval;
	}
}
