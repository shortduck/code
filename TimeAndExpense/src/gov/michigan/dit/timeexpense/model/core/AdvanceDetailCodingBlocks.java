/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "ADVANCE_DETAIL_CODING_BLOCKS")
@NamedQueries({@NamedQuery(name = "AdvanceDetailCodingBlocks.findAll", query = "SELECT a FROM AdvanceDetailCodingBlocks a"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByAdvdcIdentifier", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.advdcIdentifier = :advdcIdentifier"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByCodingBlock", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.codingBlock = :codingBlock"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByFacsAgy", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.facsAgy = :facsAgy"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByAppropriationYear", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.appropriationYear = :appropriationYear"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByIndexCode", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.indexCode = :indexCode"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByPca", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.pca = :pca"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByGrantNumber", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.grantNumber = :grantNumber"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByGrantPhase", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.grantPhase = :grantPhase"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByAgencyCode1", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.agencyCode1 = :agencyCode1"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByProjectNumber", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.projectNumber = :projectNumber"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByProjectPhase", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.projectPhase = :projectPhase"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByAgencyCode2", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.agencyCode2 = :agencyCode2"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByAgencyCode3", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.agencyCode3 = :agencyCode3"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByMultipurposeCode", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.multipurposeCode = :multipurposeCode"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByStandardInd", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.standardInd = :standardInd"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByDollarAmount", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.dollarAmount = :dollarAmount"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByPercent", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.percent = :percent"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByModifiedUserId", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "AdvanceDetailCodingBlocks.findByModifiedDate", query = "SELECT a FROM AdvanceDetailCodingBlocks a WHERE a.modifiedDate = :modifiedDate")})
public class AdvanceDetailCodingBlocks implements Serializable {
    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name = "ADVANCE_DETAIL_CODING_GENERATOR", sequenceName = "ADVDC_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADVANCE_DETAIL_CODING_GENERATOR")
    
    @Column(name = "ADVDC_IDENTIFIER")
    private Integer advdcIdentifier;
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
    private String standardInd = "N";
    
    @Column(name = "DOLLAR_AMOUNT")
    private double dollarAmount = 0.0;
    
    @Column(name = "PERCENT")
    private double percent = 0.0;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
   
    private Date modifiedDate;
    @JoinColumn(name = "ADVD_IDENTIFIER", referencedColumnName = "ADVD_IDENTIFIER")
    @ManyToOne(optional = false)
    private AdvanceDetails advdIdentifier;
    
    @Version
	@Column(name = "VERSION")
	private Integer version;

    public AdvanceDetailCodingBlocks() {
    }

    public AdvanceDetailCodingBlocks(Integer advdcIdentifier) {
        this.advdcIdentifier = advdcIdentifier;
    }

    public AdvanceDetailCodingBlocks(Integer advdcIdentifier, String standardInd, double dollarAmount, double percent) {
        this.advdcIdentifier = advdcIdentifier;
        this.standardInd = standardInd;
        this.dollarAmount = dollarAmount;
        this.percent = percent;
    }

    public Integer getAdvdcIdentifier() {
        return advdcIdentifier;
    }

    public void setAdvdcIdentifier(Integer advdcIdentifier) {
        this.advdcIdentifier = advdcIdentifier;
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

    public AdvanceDetails getAdvdIdentifier() {
        return advdIdentifier;
    }

    public void setAdvdIdentifier(AdvanceDetails advdIdentifier) {
        this.advdIdentifier = advdIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (advdcIdentifier != null ? advdcIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvanceDetailCodingBlocks)) {
            return false;
        }
        AdvanceDetailCodingBlocks other = (AdvanceDetailCodingBlocks) object;
        if ((this.advdcIdentifier == null && other.advdcIdentifier != null) || (this.advdcIdentifier != null && !this.advdcIdentifier.equals(other.advdcIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AdvanceDetailCodingBlocks[advdcIdentifier=" + advdcIdentifier + "]";
    }

}
