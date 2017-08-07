/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "ADVANCE_MASTERS")

public class AdvanceMasters implements Serializable {
   
	private static final long serialVersionUID = -465476079815746481L;

	@SequenceGenerator(name = "ADVANCE_MASTERS_GENERATOR", sequenceName = "ADVM_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADVANCE_MASTERS_GENERATOR")
   @Column(name = "ADVM_IDENTIFIER")
    private Integer advmIdentifier;
    
    @Column(name = "REVISION_NUMBER")
    private int revisionNumber;
    @Column(name = "REQUEST_DATE")
    private Date requestDate;
    
    @Column(name = "CURRENT_IND")
    private String currentInd;
    @Column(name = "STATUS")
    private String status = "";
    @Column(name = "ADVANCE_REASON")
    private String advanceReason;
    
    @Column(name = "PERMANENT_ADV_IND")
    private String permanentAdvInd;
    @Column(name = "MANUAL_DEPOSIT_DOC_NUM")
    private String manualDepositDocNum;
    @Column(name = "MANUAL_DEPOSIT_AMOUNT")
    private double manualDepositAmount;
    @Column(name = "MANUAL_WARRANT_DOC_NUM")
    private String manualWarrantDocNum;
    
    @Column(name = "MANUAL_WARRANT_ISSD_IND")
    private String manualWarrantIssdInd;
    
    @Column(name = "MANUAL_DEPOSIT_IND")
    private String manualDepositInd;
    
    @Column(name = "FROM_DATE")
    private Date fromDate;
    
    @Column(name = "TO_DATE")
    private Date toDate;
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advmIdentifier")
    
    private List<AdvanceActions> advanceActionsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advmIdentifier")
    
    private List<AdvanceDetails> advanceDetailsCollection;
    @JoinColumn(name = "ADEV_IDENTIFIER", referencedColumnName = "ADEV_IDENTIFIER")
    @ManyToOne(optional = false)
    private AdvanceEvents adevIdentifier;
    /*@JoinColumn(name = "PAID_PP_END_DATE", referencedColumnName = "PP_END_DATE")
    @ManyToOne*/
    @Column(name = "PAID_PP_END_DATE")
    private Date paidPpEndDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advanceMaster")
    private List<AdvanceLiquidations> advanceLiquidationsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advmIdentifier")
    private List<AdvanceErrors> advanceErrorsCollection;
    @Column(name = "ADJ_IDENTIFIER")
	private Long adjIdentifier;
    
    @Version
	@Column(name = "VERSION")
	private Integer version;
    
    public Long getAdjIdentifier() {
		return adjIdentifier;
	}

	public void setAdjIdentifier(Long adjIdentifier) {
		this.adjIdentifier = adjIdentifier;
	}

	public AdvanceMasters() {
    }

    public AdvanceMasters(Integer advmIdentifier) {
        this.advmIdentifier = advmIdentifier;
    }

    public AdvanceMasters(Integer advmIdentifier, short revisionNumber, String currentInd, String permanentAdvInd, String manualWarrantIssdInd, String manualDepositInd, Date fromDate, Date toDate) {
        this.advmIdentifier = advmIdentifier;
        this.revisionNumber = revisionNumber;
        this.currentInd = currentInd;
        this.permanentAdvInd = permanentAdvInd;
        this.manualWarrantIssdInd = manualWarrantIssdInd;
        this.manualDepositInd = manualDepositInd;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Integer getAdvmIdentifier() {
        return advmIdentifier;
    }

    public void setAdvmIdentifier(Integer advmIdentifier) {
        this.advmIdentifier = advmIdentifier;
    }

    public int getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(short revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getCurrentInd() {
        return currentInd;
    }

    public void setCurrentInd(String currentInd) {
        this.currentInd = currentInd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdvanceReason() {
        return advanceReason;
    }

    public void setAdvanceReason(String advanceReason) {
        this.advanceReason = advanceReason;
    }

    public String getPermanentAdvInd() {
        return permanentAdvInd;
    }

    public void setPermanentAdvInd(String permanentAdvInd) {
        this.permanentAdvInd = permanentAdvInd;
    }

    public String getManualDepositDocNum() {
        return manualDepositDocNum;
    }

    public void setManualDepositDocNum(String manualDepositDocNum) {
        this.manualDepositDocNum = manualDepositDocNum;
    }

    public double getManualDepositAmount() {
        return manualDepositAmount;
    }

    public void setManualDepositAmount(double manualDepositAmount) {
        this.manualDepositAmount = manualDepositAmount;
    }

    public String getManualWarrantDocNum() {
        return manualWarrantDocNum;
    }

    public void setManualWarrantDocNum(String manualWarrantDocNum) {
        this.manualWarrantDocNum = manualWarrantDocNum;
    }

    public String getManualWarrantIssdInd() {
        return manualWarrantIssdInd;
    }

    public void setManualWarrantIssdInd(String manualWarrantIssdInd) {
        this.manualWarrantIssdInd = manualWarrantIssdInd;
    }

    public String getManualDepositInd() {
        return manualDepositInd;
    }

    public void setManualDepositInd(String manualDepositInd) {
        this.manualDepositInd = manualDepositInd;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public List<AdvanceActions> getAdvanceActionsCollection() {
        return advanceActionsCollection;
    }

    public void setAdvanceActionsCollection(List<AdvanceActions> advanceActionsCollection) {
        this.advanceActionsCollection = advanceActionsCollection;
    }

    public List<AdvanceDetails> getAdvanceDetailsCollection() {
        return advanceDetailsCollection;
    }

    public void setAdvanceDetailsCollection(List<AdvanceDetails> advanceDetailsCollection) {
        this.advanceDetailsCollection = advanceDetailsCollection;
    }

    public AdvanceEvents getAdevIdentifier() {
        return adevIdentifier;
    }

    public void setAdevIdentifier(AdvanceEvents adevIdentifier) {
        this.adevIdentifier = adevIdentifier;
    }

    public Date getPaidPpEndDate() {
        return paidPpEndDate;
    }

    public void setPaidPpEndDate(Date paidPpEndDate) {
        this.paidPpEndDate = paidPpEndDate;
    }

    public List<AdvanceLiquidations> getAdvanceLiquidationsCollection() {
        return advanceLiquidationsCollection;
    }

    public void setAdvanceLiquidationsCollection(List<AdvanceLiquidations> advanceLiquidationsCollection) {
        this.advanceLiquidationsCollection = advanceLiquidationsCollection;
    }

    public List<AdvanceErrors> getAdvanceErrorsCollection() {
        return advanceErrorsCollection;
    }

    public void setAdvanceErrorsCollection(List<AdvanceErrors> advanceErrorsCollection) {
        this.advanceErrorsCollection = advanceErrorsCollection;
    }

    /**
     * Calculates total amount by adding up details amounts, if they exist.
     *  
     * @return total advance amount
     */
    public double getAmount() {
        double result = 0;
        
        if(getAdvanceDetailsCollection() == null) return result;
        
        for(AdvanceDetails detail : getAdvanceDetailsCollection()){
            result += detail.getDollarAmount();
        }
    	
    	return result;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (advmIdentifier != null ? advmIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvanceMasters)) {
            return false;
        }
        AdvanceMasters other = (AdvanceMasters) object;
        if ((this.advmIdentifier == null && other.advmIdentifier != null) || (this.advmIdentifier != null && !this.advmIdentifier.equals(other.advmIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AdvanceMasters[advmIdentifier=" + advmIdentifier + "]";
    }

}
