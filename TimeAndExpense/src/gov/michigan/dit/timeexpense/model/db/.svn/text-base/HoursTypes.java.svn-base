/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

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
@Table(name = "HOURS_TYPES")
@NamedQueries({@NamedQuery(name = "HoursTypes.findAll", query = "SELECT h FROM HoursTypes h"), @NamedQuery(name = "HoursTypes.findByHoursType", query = "SELECT h FROM HoursTypes h WHERE h.hoursType = :hoursType"), @NamedQuery(name = "HoursTypes.findByStartDate", query = "SELECT h FROM HoursTypes h WHERE h.startDate = :startDate"), @NamedQuery(name = "HoursTypes.findByEndDate", query = "SELECT h FROM HoursTypes h WHERE h.endDate = :endDate"), @NamedQuery(name = "HoursTypes.findByDistributeInd", query = "SELECT h FROM HoursTypes h WHERE h.distributeInd = :distributeInd"), @NamedQuery(name = "HoursTypes.findByPprismPaidHoursType", query = "SELECT h FROM HoursTypes h WHERE h.pprismPaidHoursType = :pprismPaidHoursType"), @NamedQuery(name = "HoursTypes.findByPprismNonpaidHoursType", query = "SELECT h FROM HoursTypes h WHERE h.pprismNonpaidHoursType = :pprismNonpaidHoursType"), @NamedQuery(name = "HoursTypes.findByDescription", query = "SELECT h FROM HoursTypes h WHERE h.description = :description"), @NamedQuery(name = "HoursTypes.findByLeaveTypeInd", query = "SELECT h FROM HoursTypes h WHERE h.leaveTypeInd = :leaveTypeInd"), @NamedQuery(name = "HoursTypes.findByDisplayOrder", query = "SELECT h FROM HoursTypes h WHERE h.displayOrder = :displayOrder"), @NamedQuery(name = "HoursTypes.findByTaInd", query = "SELECT h FROM HoursTypes h WHERE h.taInd = :taInd"), @NamedQuery(name = "HoursTypes.findByDcdsInd", query = "SELECT h FROM HoursTypes h WHERE h.dcdsInd = :dcdsInd"), @NamedQuery(name = "HoursTypes.findByModifiedDate", query = "SELECT h FROM HoursTypes h WHERE h.modifiedDate = :modifiedDate"), @NamedQuery(name = "HoursTypes.findByLotrInd", query = "SELECT h FROM HoursTypes h WHERE h.lotrInd = :lotrInd"), @NamedQuery(name = "HoursTypes.findByModifiedUserId", query = "SELECT h FROM HoursTypes h WHERE h.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "HoursTypes.findByPaidHourlyInd", query = "SELECT h FROM HoursTypes h WHERE h.paidHourlyInd = :paidHourlyInd"), @NamedQuery(name = "HoursTypes.findByPprismPaidHoursTypeOld", query = "SELECT h FROM HoursTypes h WHERE h.pprismPaidHoursTypeOld = :pprismPaidHoursTypeOld"), @NamedQuery(name = "HoursTypes.findByPprismNonPaidHoursTypeOld", query = "SELECT h FROM HoursTypes h WHERE h.pprismNonPaidHoursTypeOld = :pprismNonPaidHoursTypeOld"), @NamedQuery(name = "HoursTypes.findByStatus", query = "SELECT h FROM HoursTypes h WHERE h.status = :status")})
public class HoursTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "HOURS_TYPE")
    private String hoursType;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "DISTRIBUTE_IND")
    private String distributeInd;
    @Column(name = "PPRISM_PAID_HOURS_TYPE")
    private String pprismPaidHoursType;
    @Column(name = "PPRISM_NONPAID_HOURS_TYPE")
    private String pprismNonpaidHoursType;
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "LEAVE_TYPE_IND")
    private String leaveTypeInd;
    @Column(name = "DISPLAY_ORDER")
    private String displayOrder;
    
    @Column(name = "TA_IND")
    private String taInd;
    
    @Column(name = "DCDS_IND")
    private String dcdsInd;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    @Column(name = "LOTR_IND")
    private String lotrInd;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "PAID_HOURLY_IND")
    private String paidHourlyInd;
    @Column(name = "PPRISM_PAID_HOURS_TYPE_OLD")
    private String pprismPaidHoursTypeOld;
    @Column(name = "PPRISM_NON_PAID_HOURS_TYPE_OLD")
    private String pprismNonPaidHoursTypeOld;
    
    @Column(name = "STATUS")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hoursTypes")
    private Collection<TkuoptHoursTypes> tkuoptHoursTypesCollection;

    public HoursTypes() {
    }

    public HoursTypes(String hoursType) {
        this.hoursType = hoursType;
    }

    public HoursTypes(String hoursType, Date startDate, Date endDate, String distributeInd, String leaveTypeInd, String taInd, String dcdsInd, String lotrInd, String status) {
        this.hoursType = hoursType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distributeInd = distributeInd;
        this.leaveTypeInd = leaveTypeInd;
        this.taInd = taInd;
        this.dcdsInd = dcdsInd;
        this.lotrInd = lotrInd;
        this.status = status;
    }

    public String getHoursType() {
        return hoursType;
    }

    public void setHoursType(String hoursType) {
        this.hoursType = hoursType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDistributeInd() {
        return distributeInd;
    }

    public void setDistributeInd(String distributeInd) {
        this.distributeInd = distributeInd;
    }

    public String getPprismPaidHoursType() {
        return pprismPaidHoursType;
    }

    public void setPprismPaidHoursType(String pprismPaidHoursType) {
        this.pprismPaidHoursType = pprismPaidHoursType;
    }

    public String getPprismNonpaidHoursType() {
        return pprismNonpaidHoursType;
    }

    public void setPprismNonpaidHoursType(String pprismNonpaidHoursType) {
        this.pprismNonpaidHoursType = pprismNonpaidHoursType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLeaveTypeInd() {
        return leaveTypeInd;
    }

    public void setLeaveTypeInd(String leaveTypeInd) {
        this.leaveTypeInd = leaveTypeInd;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getTaInd() {
        return taInd;
    }

    public void setTaInd(String taInd) {
        this.taInd = taInd;
    }

    public String getDcdsInd() {
        return dcdsInd;
    }

    public void setDcdsInd(String dcdsInd) {
        this.dcdsInd = dcdsInd;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getLotrInd() {
        return lotrInd;
    }

    public void setLotrInd(String lotrInd) {
        this.lotrInd = lotrInd;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getPaidHourlyInd() {
        return paidHourlyInd;
    }

    public void setPaidHourlyInd(String paidHourlyInd) {
        this.paidHourlyInd = paidHourlyInd;
    }

    public String getPprismPaidHoursTypeOld() {
        return pprismPaidHoursTypeOld;
    }

    public void setPprismPaidHoursTypeOld(String pprismPaidHoursTypeOld) {
        this.pprismPaidHoursTypeOld = pprismPaidHoursTypeOld;
    }

    public String getPprismNonPaidHoursTypeOld() {
        return pprismNonPaidHoursTypeOld;
    }

    public void setPprismNonPaidHoursTypeOld(String pprismNonPaidHoursTypeOld) {
        this.pprismNonPaidHoursTypeOld = pprismNonPaidHoursTypeOld;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Collection<TkuoptHoursTypes> getTkuoptHoursTypesCollection() {
        return tkuoptHoursTypesCollection;
    }

    public void setTkuoptHoursTypesCollection(Collection<TkuoptHoursTypes> tkuoptHoursTypesCollection) {
        this.tkuoptHoursTypesCollection = tkuoptHoursTypesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hoursType != null ? hoursType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HoursTypes)) {
            return false;
        }
        HoursTypes other = (HoursTypes) object;
        if ((this.hoursType == null && other.hoursType != null) || (this.hoursType != null && !this.hoursType.equals(other.hoursType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.HoursTypes[hoursType=" + hoursType + "]";
    }

}
