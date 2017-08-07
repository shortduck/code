/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "PAY_PERIODS")
@NamedQueries({@NamedQuery(name = "PayPeriods.findAll", query = "SELECT p FROM PayPeriods p"), @NamedQuery(name = "PayPeriods.findByPpEndDate", query = "SELECT p FROM PayPeriods p WHERE p.ppEndDate = :ppEndDate"), @NamedQuery(name = "PayPeriods.findByPpStartDate", query = "SELECT p FROM PayPeriods p WHERE p.ppStartDate = :ppStartDate"), @NamedQuery(name = "PayPeriods.findByPpNumber", query = "SELECT p FROM PayPeriods p WHERE p.ppNumber = :ppNumber"), @NamedQuery(name = "PayPeriods.findByCalendarYear", query = "SELECT p FROM PayPeriods p WHERE p.calendarYear = :calendarYear"), @NamedQuery(name = "PayPeriods.findByFiscalYear", query = "SELECT p FROM PayPeriods p WHERE p.fiscalYear = :fiscalYear"), @NamedQuery(name = "PayPeriods.findByModifiedUserId", query = "SELECT p FROM PayPeriods p WHERE p.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "PayPeriods.findByModifiedDate", query = "SELECT p FROM PayPeriods p WHERE p.modifiedDate = :modifiedDate"), @NamedQuery(name = "PayPeriods.findBySplitPayFy", query = "SELECT p FROM PayPeriods p WHERE p.splitPayFy = :splitPayFy")})
public class PayPeriods implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "PP_END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ppEndDate;
    
    @Column(name = "PP_START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ppStartDate;
    
    @Column(name = "PP_NUMBER")
    private short ppNumber;
    
    @Column(name = "CALENDAR_YEAR")
    private short calendarYear;
    
    @Column(name = "FISCAL_YEAR")
    private short fiscalYear;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "SPLIT_PAY_FY")
    private Short splitPayFy;
    @OneToMany(mappedBy = "paidPpEndDate")
    private Collection<AdvanceMasters> advanceMastersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ppEndDate")
    private Collection<Tas> tasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ppEndDate")
    private Collection<TkuActions> tkuActionsCollection;
   // @OneToMany(mappedBy = "paidPpEndDate")
    //private Collection<ExpenseMasters> expenseMastersCollection;

    public PayPeriods() {
    }

    public PayPeriods(Date ppEndDate) {
        this.ppEndDate = ppEndDate;
    }

    public PayPeriods(Date ppEndDate, Date ppStartDate, short ppNumber, short calendarYear, short fiscalYear) {
        this.ppEndDate = ppEndDate;
        this.ppStartDate = ppStartDate;
        this.ppNumber = ppNumber;
        this.calendarYear = calendarYear;
        this.fiscalYear = fiscalYear;
    }

    public Date getPpEndDate() {
        return ppEndDate;
    }

    public void setPpEndDate(Date ppEndDate) {
        this.ppEndDate = ppEndDate;
    }

    public Date getPpStartDate() {
        return ppStartDate;
    }

    public void setPpStartDate(Date ppStartDate) {
        this.ppStartDate = ppStartDate;
    }

    public short getPpNumber() {
        return ppNumber;
    }

    public void setPpNumber(short ppNumber) {
        this.ppNumber = ppNumber;
    }

    public short getCalendarYear() {
        return calendarYear;
    }

    public void setCalendarYear(short calendarYear) {
        this.calendarYear = calendarYear;
    }

    public short getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(short fiscalYear) {
        this.fiscalYear = fiscalYear;
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

    public Short getSplitPayFy() {
        return splitPayFy;
    }

    public void setSplitPayFy(Short splitPayFy) {
        this.splitPayFy = splitPayFy;
    }

    public Collection<AdvanceMasters> getAdvanceMastersCollection() {
        return advanceMastersCollection;
    }

    public void setAdvanceMastersCollection(Collection<AdvanceMasters> advanceMastersCollection) {
        this.advanceMastersCollection = advanceMastersCollection;
    }

    public Collection<Tas> getTasCollection() {
        return tasCollection;
    }

    public void setTasCollection(Collection<Tas> tasCollection) {
        this.tasCollection = tasCollection;
    }

    public Collection<TkuActions> getTkuActionsCollection() {
        return tkuActionsCollection;
    }

    public void setTkuActionsCollection(Collection<TkuActions> tkuActionsCollection) {
        this.tkuActionsCollection = tkuActionsCollection;
    }
/*
    public Collection<ExpenseMasters> getExpenseMastersCollection() {
        return expenseMastersCollection;
    }

    public void setExpenseMastersCollection(Collection<ExpenseMasters> expenseMastersCollection) {
        this.expenseMastersCollection = expenseMastersCollection;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ppEndDate != null ? ppEndDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayPeriods)) {
            return false;
        }
        PayPeriods other = (PayPeriods) object;
        if ((this.ppEndDate == null && other.ppEndDate != null) || (this.ppEndDate != null && !this.ppEndDate.equals(other.ppEndDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.PayPeriods[ppEndDate=" + ppEndDate + "]";
    }

}
