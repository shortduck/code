package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

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

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author GhoshS
 */
@Entity
@Table(name = "EXPENSE_DETAIL_CODING_BLOCKS")

public class ExpenseDetailCodingBlocks implements Serializable {

	private static final long serialVersionUID = 7622544327915151434L;

	@SequenceGenerator(name = "EXPENSE_CODING_BLOCKS_GENERATOR", sequenceName = "EXPDC_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPENSE_CODING_BLOCKS_GENERATOR")
	@Column(name = "EXPDC_IDENTIFIER")
	private Integer expdcIdentifier;

	@Column(name = "CODING_BLOCK")
	private String codingBlock = "";
	
	@Column(name = "FACS_AGY")
	private String facsAgy = "";
	
	@Column(name = "APPROPRIATION_YEAR")
	private String appropriationYear = "";
	
	@Column(name = "INDEX_CODE")
	private String indexCode = "";
	
	@Column(name = "PCA")
	private String pca = "";
	
	@Column(name = "GRANT_NUMBER")
	private String grantNumber = "";
	
	@Column(name = "GRANT_PHASE")
	private String grantPhase = "";
	
	@Column(name = "AGENCY_CODE_1")
	private String agencyCode1 = "";
	
	@Column(name = "PROJECT_NUMBER")
	private String projectNumber = "";
	
	@Column(name = "PROJECT_PHASE")
	private String projectPhase = "";
	
	@Column(name = "AGENCY_CODE_2")
	private String agencyCode2 = "";
	
	@Column(name = "AGENCY_CODE_3")
	private String agencyCode3 = "";
	
	@Column(name = "MULTIPURPOSE_CODE")
	private String multipurposeCode = "";

	@Column(name = "STANDARD_IND")
	private String standardInd = "";

	@Column(name = "DOLLAR_AMOUNT")
	private double dollarAmount;

	@Column(name = "PERCENT")
	private double percent;
	
	@Column(name = "MODIFIED_USER_ID")
	private String modifiedUserId = "";
	
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	
	@JoinColumn(name = "EXPD_IDENTIFIER", referencedColumnName = "EXPD_IDENTIFIER")
	@ManyToOne(optional = false)
	private ExpenseDetails expdIdentifier;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;

	public ExpenseDetailCodingBlocks() {
	}

	public ExpenseDetailCodingBlocks(ExpenseDetailCodingBlocks oldCB) {
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
		//expdIdentifier = oldCB.expdIdentifier;

	}
	
	public ExpenseDetailCodingBlocks(Integer expdcIdentifier) {
		this.expdcIdentifier = expdcIdentifier;
	}

	public ExpenseDetailCodingBlocks(Integer expdcIdentifier,
			String standardInd, double dollarAmount, int percent) {
		this.expdcIdentifier = expdcIdentifier;
		this.standardInd = standardInd;
		this.dollarAmount = dollarAmount;
		this.percent = percent;
	}

	public Integer getExpdcIdentifier() {
		return expdcIdentifier;
	}

	public void setExpdcIdentifier(Integer expdcIdentifier) {
		this.expdcIdentifier = expdcIdentifier;
	}

	public String getCodingBlock() {
		return codingBlock;
	}

	public void setCodingBlock(String codingBlock) {
		this.codingBlock = codingBlock;
	}

	public String getFacsAgy() {
		return facsAgy;
	}

	public void setFacsAgy(String facsAgy) {
		this.facsAgy = facsAgy;
	}

	public String getAppropriationYear() {
		return appropriationYear;
	}

	public void setAppropriationYear(String appropriationYear) {
		this.appropriationYear = appropriationYear;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getPca() {
		return pca;
	}

	public void setPca(String pca) {
		this.pca = pca;
	}

	public String getGrantNumber() {
		return grantNumber;
	}

	public void setGrantNumber(String grantNumber) {
		this.grantNumber = grantNumber;
	}

	public String getGrantPhase() {
		return grantPhase;
	}

	public void setGrantPhase(String grantPhase) {
		this.grantPhase = grantPhase;
	}

	public String getAgencyCode1() {
		return agencyCode1;
	}

	public void setAgencyCode1(String agencyCode1) {
		this.agencyCode1 = agencyCode1;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getProjectPhase() {
		return projectPhase;
	}

	public void setProjectPhase(String projectPhase) {
		this.projectPhase = projectPhase;
	}

	public String getAgencyCode2() {
		return agencyCode2;
	}

	public void setAgencyCode2(String agencyCode2) {
		this.agencyCode2 = agencyCode2;
	}

	public String getAgencyCode3() {
		return agencyCode3;
	}

	public void setAgencyCode3(String agencyCode3) {
		this.agencyCode3 = agencyCode3;
	}

	public String getMultipurposeCode() {
		return multipurposeCode;
	}

	public void setMultipurposeCode(String multipurposeCode) {
		this.multipurposeCode = multipurposeCode;
	}

	public String getStandardInd() {
		return standardInd;
	}

	public void setStandardInd(String standardInd) {
		this.standardInd = standardInd;
	}

	public double getDollarAmount() {
		return dollarAmount;
	}

	public void setDollarAmount(double dollarAmount) {
		this.dollarAmount = dollarAmount;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
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

	public ExpenseDetails getExpdIdentifier() {
		return expdIdentifier;
	}

	public void setExpdIdentifier(ExpenseDetails expdIdentifier) {
		this.expdIdentifier = expdIdentifier;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (expdcIdentifier != null ? expdcIdentifier.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (this == object)
			return true;

		if (object == null)
			return false;

		if (!this.getClass().equals(object.getClass())) {
			return false;
		}

		ExpenseDetailCodingBlocks other = (ExpenseDetailCodingBlocks) object;
		if (!StringUtils.isEmpty(other.getCodingBlock())){
			if (!StringUtils.isEmpty(this.getCodingBlock())){
				if (other.getCodingBlock().equals(this.getCodingBlock()))
					return true;
				else
					return false;
			}
		}
				
		return false;
		/*return this.agencyCode1.equals(other.agencyCode1)
				&& this.agencyCode2.equals(other.agencyCode2)
				&& this.agencyCode3.equals(other.agencyCode3)
				&& this.appropriationYear.equals(other.appropriationYear)
				&& this.indexCode.equals(other.indexCode)
				&& this.pca.equals(other.pca)
				&& this.grantNumber.equals(other.grantNumber)
				&& this.grantPhase.equals(other.grantPhase)
				&& this.projectNumber.equals(other.projectNumber)
				&& this.projectPhase.equals(other.projectPhase)
				&& this.multipurposeCode.equals(other.multipurposeCode)
				&& this.percent == other.percent;*/
	}

	@Override
	public String toString() {
		return "gov.michigan.dit.timeexpense.model.ExpenseDetailCodingBlocks[expdcIdentifier="
				+ expdcIdentifier + "]";
	}

}
