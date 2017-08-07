/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "TAS")
@NamedQueries({@NamedQuery(name = "Tas.findAll", query = "SELECT t FROM Tas t"), @NamedQuery(name = "Tas.findByTaIdentifier", query = "SELECT t FROM Tas t WHERE t.taIdentifier = :taIdentifier"), @NamedQuery(name = "Tas.findByRevisionNumber", query = "SELECT t FROM Tas t WHERE t.revisionNumber = :revisionNumber"), @NamedQuery(name = "Tas.findByAdjIdentifier", query = "SELECT t FROM Tas t WHERE t.adjIdentifier = :adjIdentifier"), @NamedQuery(name = "Tas.findByAdjType", query = "SELECT t FROM Tas t WHERE t.adjType = :adjType"), @NamedQuery(name = "Tas.findByPaidPpEndDate", query = "SELECT t FROM Tas t WHERE t.paidPpEndDate = :paidPpEndDate"), @NamedQuery(name = "Tas.findByCurrentInd", query = "SELECT t FROM Tas t WHERE t.currentInd = :currentInd"), @NamedQuery(name = "Tas.findByErrorInd", query = "SELECT t FROM Tas t WHERE t.errorInd = :errorInd"), @NamedQuery(name = "Tas.findByTaEntryType", query = "SELECT t FROM Tas t WHERE t.taEntryType = :taEntryType"), @NamedQuery(name = "Tas.findByTaMissing", query = "SELECT t FROM Tas t WHERE t.taMissing = :taMissing"), @NamedQuery(name = "Tas.findBySystemGenerated", query = "SELECT t FROM Tas t WHERE t.systemGenerated = :systemGenerated"), @NamedQuery(name = "Tas.findByPrmuMiles", query = "SELECT t FROM Tas t WHERE t.prmuMiles = :prmuMiles"), @NamedQuery(name = "Tas.findByStatus", query = "SELECT t FROM Tas t WHERE t.status = :status"), @NamedQuery(name = "Tas.findByGpaAmount", query = "SELECT t FROM Tas t WHERE t.gpaAmount = :gpaAmount"), @NamedQuery(name = "Tas.findByLocalWarrantNumber", query = "SELECT t FROM Tas t WHERE t.localWarrantNumber = :localWarrantNumber"), @NamedQuery(name = "Tas.findByLocalWarrantDate", query = "SELECT t FROM Tas t WHERE t.localWarrantDate = :localWarrantDate"), @NamedQuery(name = "Tas.findByLocalWarrantAmount", query = "SELECT t FROM Tas t WHERE t.localWarrantAmount = :localWarrantAmount"), @NamedQuery(name = "Tas.findByModifiedUserId", query = "SELECT t FROM Tas t WHERE t.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "Tas.findByModifiedDate", query = "SELECT t FROM Tas t WHERE t.modifiedDate = :modifiedDate"), @NamedQuery(name = "Tas.findByTrSource", query = "SELECT t FROM Tas t WHERE t.trSource = :trSource"), @NamedQuery(name = "Tas.findByOldApptIdentifier", query = "SELECT t FROM Tas t WHERE t.oldApptIdentifier = :oldApptIdentifier")})
public class Tas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "TA_IDENTIFIER")
    private Integer taIdentifier;
    
    @Column(name = "REVISION_NUMBER")
    private short revisionNumber;
    @Column(name = "ADJ_IDENTIFIER")
    private Integer adjIdentifier;
    @Column(name = "ADJ_TYPE")
    private String adjType;
    @Column(name = "PAID_PP_END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paidPpEndDate;
    
    @Column(name = "CURRENT_IND")
    private String currentInd;
    
    @Column(name = "ERROR_IND")
    private String errorInd;
    @Column(name = "TA_ENTRY_TYPE")
    private String taEntryType;
    @Column(name = "TA_MISSING")
    private String taMissing;
    @Column(name = "SYSTEM_GENERATED")
    private String systemGenerated;
    @Column(name = "PRMU_MILES")
    private BigDecimal prmuMiles;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "GPA_AMOUNT")
    private BigDecimal gpaAmount;
    @Column(name = "LOCAL_WARRANT_NUMBER")
    private String localWarrantNumber;
    @Column(name = "LOCAL_WARRANT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date localWarrantDate;
    @Column(name = "LOCAL_WARRANT_AMOUNT")
    private BigDecimal localWarrantAmount;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "TR_SOURCE")
    private String trSource;
    @Column(name = "OLD_APPT_IDENTIFIER")
    private Integer oldApptIdentifier;
    @JoinColumn(name = "APPT_IDENTIFIER", referencedColumnName = "APPT_IDENTIFIER")
    @ManyToOne
    private Appointments apptIdentifier;
    @JoinColumn(name = "NEMP_IDENTIFIER", referencedColumnName = "NEMP_IDENTIFIER")
    @ManyToOne
    private NonEmp nempIdentifier;
    @JoinColumn(name = "PP_END_DATE", referencedColumnName = "PP_END_DATE")
    @ManyToOne(optional = false)
    private PayPeriods ppEndDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taIdentifier")
    private Collection<TaActions> taActionsCollection;

    public Tas() {
    }

    public Tas(Integer taIdentifier) {
        this.taIdentifier = taIdentifier;
    }

    public Tas(Integer taIdentifier, short revisionNumber, String currentInd, String errorInd) {
        this.taIdentifier = taIdentifier;
        this.revisionNumber = revisionNumber;
        this.currentInd = currentInd;
        this.errorInd = errorInd;
    }

    public Integer getTaIdentifier() {
        return taIdentifier;
    }

    public void setTaIdentifier(Integer taIdentifier) {
        this.taIdentifier = taIdentifier;
    }

    public short getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(short revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public Integer getAdjIdentifier() {
        return adjIdentifier;
    }

    public void setAdjIdentifier(Integer adjIdentifier) {
        this.adjIdentifier = adjIdentifier;
    }

    public String getAdjType() {
        return adjType;
    }

    public void setAdjType(String adjType) {
        this.adjType = adjType;
    }

    public Date getPaidPpEndDate() {
        return paidPpEndDate;
    }

    public void setPaidPpEndDate(Date paidPpEndDate) {
        this.paidPpEndDate = paidPpEndDate;
    }

    public String getCurrentInd() {
        return currentInd;
    }

    public void setCurrentInd(String currentInd) {
        this.currentInd = currentInd;
    }

    public String getErrorInd() {
        return errorInd;
    }

    public void setErrorInd(String errorInd) {
        this.errorInd = errorInd;
    }

    public String getTaEntryType() {
        return taEntryType;
    }

    public void setTaEntryType(String taEntryType) {
        this.taEntryType = taEntryType;
    }

    public String getTaMissing() {
        return taMissing;
    }

    public void setTaMissing(String taMissing) {
        this.taMissing = taMissing;
    }

    public String getSystemGenerated() {
        return systemGenerated;
    }

    public void setSystemGenerated(String systemGenerated) {
        this.systemGenerated = systemGenerated;
    }

    public BigDecimal getPrmuMiles() {
        return prmuMiles;
    }

    public void setPrmuMiles(BigDecimal prmuMiles) {
        this.prmuMiles = prmuMiles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getGpaAmount() {
        return gpaAmount;
    }

    public void setGpaAmount(BigDecimal gpaAmount) {
        this.gpaAmount = gpaAmount;
    }

    public String getLocalWarrantNumber() {
        return localWarrantNumber;
    }

    public void setLocalWarrantNumber(String localWarrantNumber) {
        this.localWarrantNumber = localWarrantNumber;
    }

    public Date getLocalWarrantDate() {
        return localWarrantDate;
    }

    public void setLocalWarrantDate(Date localWarrantDate) {
        this.localWarrantDate = localWarrantDate;
    }

    public BigDecimal getLocalWarrantAmount() {
        return localWarrantAmount;
    }

    public void setLocalWarrantAmount(BigDecimal localWarrantAmount) {
        this.localWarrantAmount = localWarrantAmount;
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

    public String getTrSource() {
        return trSource;
    }

    public void setTrSource(String trSource) {
        this.trSource = trSource;
    }

    public Integer getOldApptIdentifier() {
        return oldApptIdentifier;
    }

    public void setOldApptIdentifier(Integer oldApptIdentifier) {
        this.oldApptIdentifier = oldApptIdentifier;
    }

    public Appointments getApptIdentifier() {
        return apptIdentifier;
    }

    public void setApptIdentifier(Appointments apptIdentifier) {
        this.apptIdentifier = apptIdentifier;
    }

    public NonEmp getNempIdentifier() {
        return nempIdentifier;
    }

    public void setNempIdentifier(NonEmp nempIdentifier) {
        this.nempIdentifier = nempIdentifier;
    }

    public PayPeriods getPpEndDate() {
        return ppEndDate;
    }

    public void setPpEndDate(PayPeriods ppEndDate) {
        this.ppEndDate = ppEndDate;
    }

    public Collection<TaActions> getTaActionsCollection() {
        return taActionsCollection;
    }

    public void setTaActionsCollection(Collection<TaActions> taActionsCollection) {
        this.taActionsCollection = taActionsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taIdentifier != null ? taIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tas)) {
            return false;
        }
        Tas other = (Tas) object;
        if ((this.taIdentifier == null && other.taIdentifier != null) || (this.taIdentifier != null && !this.taIdentifier.equals(other.taIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Tas[taIdentifier=" + taIdentifier + "]";
    }

}
