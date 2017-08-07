package gov.michigan.dit.timeexpense.model.core;

import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name="TRAVEL_REQ_DETAIL_CODING_BLOCK")
public class TravelReqDetailCodingBlock implements Serializable {
	@Id
	@SequenceGenerator(name = "TREQ_OOST_GENERATOR", sequenceName = "TREQDC_IDNETIFIER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TREQ_OOST_GENERATOR")
	@Column(name="TREQDC_IDENTIFIER")
	private Integer treqdcIdentifier;

	@Column(name="CODING_BLOCK")
	private String codingBlock;

	@Column(name="FACS_AGY")
	private String facsAgy;

	@Column(name="APPROPRIATION_YEAR")
	private String appropriationYear;

	@Column(name="INDEX_CODE")
	private String indexCode;

	private String pca;

	@Column(name="GRANT_NUMBER")
	private String grantNumber;

	@Column(name="GRANT_PHASE")
	private String grantPhase;

	@Column(name="AGENCY_CODE_1")
	private String agencyCode1;

	@Column(name="PROJECT_NUMBER")
	private String projectNumber;

	@Column(name="PROJECT_PHASE")
	private String projectPhase;

	@Column(name="AGENCY_CODE_2")
	private String agencyCode2;

	@Column(name="AGENCY_CODE_3")
	private String agencyCode3;

	@Column(name="MULTIPURPOSE_CODE")
	private String multipurposeCode;

	@Column(name="STANDARD_IND")
	private String standardInd;

	@Column(name="DOLLAR_AMOUNT")
	private double dollarAmount;

	private double percent;

	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@ManyToOne
	@JoinColumn(name="TREQD_IDENTIFIER")
	private TravelReqDetails treqdIdentifier;
	
	@Transient
	private String percentDisplay;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;

	private static final long serialVersionUID = 1L;

	public TravelReqDetailCodingBlock() {
		super();
	}
	
	public TravelReqDetailCodingBlock(TravelReqDetailCodingBlock oldCB) {
		codingBlock = oldCB.codingBlock;
		facsAgy = oldCB.facsAgy;
		appropriationYear = oldCB.appropriationYear;
		indexCode = oldCB.indexCode;
		pca = oldCB.pca;
		grantNumber = oldCB.grantNumber;
		grantPhase = oldCB.grantPhase;
		agencyCode1 = oldCB.agencyCode1;
		projectNumber = oldCB.projectNumber;
		projectPhase = oldCB.projectPhase;
		agencyCode2 = oldCB.agencyCode2;
		agencyCode3 = oldCB.agencyCode3;
		multipurposeCode = oldCB.multipurposeCode;
		standardInd = oldCB.standardInd;
		dollarAmount = oldCB.dollarAmount;
		percent = oldCB.percent;
		modifiedUserId = oldCB.modifiedUserId;
		modifiedDate = oldCB.modifiedDate;
	}

	public Integer getTreqdcIdentifier() {
		return this.treqdcIdentifier;
	}

	public void setTreqdcIdentifier(Integer treqdcIdentifier) {
		this.treqdcIdentifier = treqdcIdentifier;
	}

	public String getCodingBlock() {
		return this.codingBlock;
	}

	public void setCodingBlock(String codingBlock) {
		this.codingBlock = codingBlock;
	}

	public String getFacsAgy() {
		return this.facsAgy;
	}

	public void setFacsAgy(String facsAgy) {
		this.facsAgy = facsAgy;
	}

	public String getAppropriationYear() {
		return this.appropriationYear;
	}

	public void setAppropriationYear(String appropriationYear) {
		this.appropriationYear = appropriationYear;
	}

	public String getIndexCode() {
		return this.indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getPca() {
		return this.pca;
	}

	public void setPca(String pca) {
		this.pca = pca;
	}

	public String getGrantNumber() {
		return this.grantNumber;
	}

	public void setGrantNumber(String grantNumber) {
		this.grantNumber = grantNumber;
	}

	public String getGrantPhase() {
		return this.grantPhase;
	}

	public void setGrantPhase(String grantPhase) {
		this.grantPhase = grantPhase;
	}

	public String getAgencyCode1() {
		return this.agencyCode1;
	}

	public void setAgencyCode1(String agencyCode1) {
		this.agencyCode1 = agencyCode1;
	}

	public String getProjectNumber() {
		return this.projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getProjectPhase() {
		return this.projectPhase;
	}

	public void setProjectPhase(String projectPhase) {
		this.projectPhase = projectPhase;
	}

	public String getAgencyCode2() {
		return this.agencyCode2;
	}

	public void setAgencyCode2(String agencyCode2) {
		this.agencyCode2 = agencyCode2;
	}

	public String getAgencyCode3() {
		return this.agencyCode3;
	}

	public void setAgencyCode3(String agencyCode3) {
		this.agencyCode3 = agencyCode3;
	}

	public String getMultipurposeCode() {
		return this.multipurposeCode;
	}

	public void setMultipurposeCode(String multipurposeCode) {
		this.multipurposeCode = multipurposeCode;
	}

	public String getStandardInd() {
		return this.standardInd;
	}

	public void setStandardInd(String standardInd) {
		this.standardInd = standardInd;
	}

	public double getDollarAmount() {
		return this.dollarAmount;
	}

	public void setDollarAmount(double dollarAmount) {
		this.dollarAmount = dollarAmount;
	}

	public double getPercent() {
		return this.percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
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

	public TravelReqDetails getTreqdIdentifier() {
		return this.treqdIdentifier;
	}

	public void setTreqdIdentifier(TravelReqDetails treqdIdentifier) {
		this.treqdIdentifier = treqdIdentifier;
	}

	public String getPercentDisplay() {
		return TimeAndExpenseUtil.displayAmountTwoDigits(this.percent * 100);
	}

	public void setPercentDisplay(double percentDisplay) {
		this.percentDisplay = Double.toString(percent);
	}

}
