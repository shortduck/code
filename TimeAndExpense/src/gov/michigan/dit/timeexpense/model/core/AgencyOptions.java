/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "AGENCY_OPTIONS")
@NamedQueries({@NamedQuery(name = "AgencyOptions.findAll", query = "SELECT a FROM AgencyOptions a"), @NamedQuery(name = "AgencyOptions.findByDepartment", query = "SELECT a FROM AgencyOptions a WHERE a.agencyOptionsPK.department = :department"), @NamedQuery(name = "AgencyOptions.findByAgency", query = "SELECT a FROM AgencyOptions a WHERE a.agencyOptionsPK.agency = :agency"), @NamedQuery(name = "AgencyOptions.findByShowApprovedLeaveReqInd", query = "SELECT a FROM AgencyOptions a WHERE a.showApprovedLeaveReqInd = :showApprovedLeaveReqInd"), @NamedQuery(name = "AgencyOptions.findByAllowChangeLeaveHoursInd", query = "SELECT a FROM AgencyOptions a WHERE a.allowChangeLeaveHoursInd = :allowChangeLeaveHoursInd"), @NamedQuery(name = "AgencyOptions.findByAllowInvalidCbElementsInd", query = "SELECT a FROM AgencyOptions a WHERE a.allowInvalidCbElementsInd = :allowInvalidCbElementsInd"), @NamedQuery(name = "AgencyOptions.findByCodingBlockGroupsBy", query = "SELECT a FROM AgencyOptions a WHERE a.codingBlockGroupsBy = :codingBlockGroupsBy"), @NamedQuery(name = "AgencyOptions.findByTimesheetReleasedByAgyTku", query = "SELECT a FROM AgencyOptions a WHERE a.timesheetReleasedByAgyTku = :timesheetReleasedByAgyTku"), @NamedQuery(name = "AgencyOptions.findByCrosswalkPreference1", query = "SELECT a FROM AgencyOptions a WHERE a.crosswalkPreference1 = :crosswalkPreference1"), @NamedQuery(name = "AgencyOptions.findByCrosswalkPreference2", query = "SELECT a FROM AgencyOptions a WHERE a.crosswalkPreference2 = :crosswalkPreference2"), @NamedQuery(name = "AgencyOptions.findByCrosswalkPreference3", query = "SELECT a FROM AgencyOptions a WHERE a.crosswalkPreference3 = :crosswalkPreference3"), @NamedQuery(name = "AgencyOptions.findByCrosswalkPreference4", query = "SELECT a FROM AgencyOptions a WHERE a.crosswalkPreference4 = :crosswalkPreference4"), @NamedQuery(name = "AgencyOptions.findByCrosswalkPreference5", query = "SELECT a FROM AgencyOptions a WHERE a.crosswalkPreference5 = :crosswalkPreference5"), @NamedQuery(name = "AgencyOptions.findByLaborAdditiveRate", query = "SELECT a FROM AgencyOptions a WHERE a.laborAdditiveRate = :laborAdditiveRate"), @NamedQuery(name = "AgencyOptions.findByModifiedUserId", query = "SELECT a FROM AgencyOptions a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "AgencyOptions.findByModifiedDate", query = "SELECT a FROM AgencyOptions a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "AgencyOptions.findByPreAuditRequiredInd", query = "SELECT a FROM AgencyOptions a WHERE a.preAuditRequiredInd = :preAuditRequiredInd"), @NamedQuery(name = "AgencyOptions.findByAdvancesAllowedInd", query = "SELECT a FROM AgencyOptions a WHERE a.advancesAllowedInd = :advancesAllowedInd")})
public class AgencyOptions implements Serializable {

	private static final long serialVersionUID = -6903422457332385999L;

	@EmbeddedId
    protected AgencyOptionsPK agencyOptionsPK;
    
    @Column(name = "SHOW_APPROVED_LEAVE_REQ_IND")
    private String showApprovedLeaveReqInd;
    
    @Column(name = "ALLOW_CHANGE_LEAVE_HOURS_IND")
    private String allowChangeLeaveHoursInd;
    
    @Column(name = "ALLOW_INVALID_CB_ELEMENTS_IND")
    private String allowInvalidCbElementsInd;
    
    @Column(name = "CODING_BLOCK_GROUPS_BY")
    private String codingBlockGroupsBy;
    @Column(name = "TIMESHEET_RELEASED_BY_AGY_TKU")
    private String timesheetReleasedByAgyTku;
    @Column(name = "CROSSWALK_PREFERENCE_1")
    private String crosswalkPreference1;
    @Column(name = "CROSSWALK_PREFERENCE_2")
    private String crosswalkPreference2;
    @Column(name = "CROSSWALK_PREFERENCE_3")
    private String crosswalkPreference3;
    @Column(name = "CROSSWALK_PREFERENCE_4")
    private String crosswalkPreference4;
    @Column(name = "CROSSWALK_PREFERENCE_5")
    private String crosswalkPreference5;
    @Column(name = "LABOR_ADDITIVE_RATE")
    private BigDecimal laborAdditiveRate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    @Column(name = "PRE_AUDIT_REQUIRED_IND")
    private String preAuditRequiredInd;
    
    @Column(name = "ADVANCES_ALLOWED_IND")
    private String advancesAllowedInd;
    
    @Column(name = "REQ_CB_EXP")
    private String reqCbExp;
    
   // @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false)})
   // @OneToOne(optional = false)
    @Column(name = "AGENCY")
    private String agencies;

    public AgencyOptions() {
    }

    public AgencyOptions(AgencyOptionsPK agencyOptionsPK) {
        this.agencyOptionsPK = agencyOptionsPK;
    }

    public AgencyOptions(AgencyOptionsPK agencyOptionsPK, String showApprovedLeaveReqInd, String allowChangeLeaveHoursInd, String allowInvalidCbElementsInd, String codingBlockGroupsBy, String preAuditRequiredInd, String advancesAllowedInd) {
        this.agencyOptionsPK = agencyOptionsPK;
        this.showApprovedLeaveReqInd = showApprovedLeaveReqInd;
        this.allowChangeLeaveHoursInd = allowChangeLeaveHoursInd;
        this.allowInvalidCbElementsInd = allowInvalidCbElementsInd;
        this.codingBlockGroupsBy = codingBlockGroupsBy;
        this.preAuditRequiredInd = preAuditRequiredInd;
        this.advancesAllowedInd = advancesAllowedInd;
    }

    public AgencyOptions(String department, String agency) {
        this.agencyOptionsPK = new AgencyOptionsPK(department, agency);
    }

    public AgencyOptionsPK getAgencyOptionsPK() {
        return agencyOptionsPK;
    }

    public void setAgencyOptionsPK(AgencyOptionsPK agencyOptionsPK) {
        this.agencyOptionsPK = agencyOptionsPK;
    }

    public String getShowApprovedLeaveReqInd() {
        return showApprovedLeaveReqInd;
    }

    public void setShowApprovedLeaveReqInd(String showApprovedLeaveReqInd) {
        this.showApprovedLeaveReqInd = showApprovedLeaveReqInd;
    }

    public String getAllowChangeLeaveHoursInd() {
        return allowChangeLeaveHoursInd;
    }

    public void setAllowChangeLeaveHoursInd(String allowChangeLeaveHoursInd) {
        this.allowChangeLeaveHoursInd = allowChangeLeaveHoursInd;
    }

    public String getAllowInvalidCbElementsInd() {
        return allowInvalidCbElementsInd;
    }

    public void setAllowInvalidCbElementsInd(String allowInvalidCbElementsInd) {
        this.allowInvalidCbElementsInd = allowInvalidCbElementsInd;
    }

    public String getCodingBlockGroupsBy() {
        return codingBlockGroupsBy;
    }

    public void setCodingBlockGroupsBy(String codingBlockGroupsBy) {
        this.codingBlockGroupsBy = codingBlockGroupsBy;
    }

    public String getTimesheetReleasedByAgyTku() {
        return timesheetReleasedByAgyTku;
    }

    public void setTimesheetReleasedByAgyTku(String timesheetReleasedByAgyTku) {
        this.timesheetReleasedByAgyTku = timesheetReleasedByAgyTku;
    }

    public String getCrosswalkPreference1() {
        return crosswalkPreference1;
    }

    public void setCrosswalkPreference1(String crosswalkPreference1) {
        this.crosswalkPreference1 = crosswalkPreference1;
    }

    public String getCrosswalkPreference2() {
        return crosswalkPreference2;
    }

    public void setCrosswalkPreference2(String crosswalkPreference2) {
        this.crosswalkPreference2 = crosswalkPreference2;
    }

    public String getCrosswalkPreference3() {
        return crosswalkPreference3;
    }

    public void setCrosswalkPreference3(String crosswalkPreference3) {
        this.crosswalkPreference3 = crosswalkPreference3;
    }

    public String getCrosswalkPreference4() {
        return crosswalkPreference4;
    }

    public void setCrosswalkPreference4(String crosswalkPreference4) {
        this.crosswalkPreference4 = crosswalkPreference4;
    }

    public String getCrosswalkPreference5() {
        return crosswalkPreference5;
    }

    public void setCrosswalkPreference5(String crosswalkPreference5) {
        this.crosswalkPreference5 = crosswalkPreference5;
    }

    public BigDecimal getLaborAdditiveRate() {
        return laborAdditiveRate;
    }

    public void setLaborAdditiveRate(BigDecimal laborAdditiveRate) {
        this.laborAdditiveRate = laborAdditiveRate;
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

    public String getPreAuditRequiredInd() {
        return preAuditRequiredInd;
    }

    public void setPreAuditRequiredInd(String preAuditRequiredInd) {
        this.preAuditRequiredInd = preAuditRequiredInd;
    }

    public String getAdvancesAllowedInd() {
        return advancesAllowedInd;
    }

    public void setAdvancesAllowedInd(String advancesAllowedInd) {
        this.advancesAllowedInd = advancesAllowedInd;
    }

    public String getAgencies() {
        return agencies;
    }

    public void setAgencies(String agencies) {
        this.agencies = agencies;
    }
    
    public String getReqCbExp() {
		return reqCbExp;
	}

	public void setReqCbExp(String reqCbExp) {
		this.reqCbExp = reqCbExp;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (agencyOptionsPK != null ? agencyOptionsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyOptions)) {
            return false;
        }
        AgencyOptions other = (AgencyOptions) object;
        if ((this.agencyOptionsPK == null && other.agencyOptionsPK != null) || (this.agencyOptionsPK != null && !this.agencyOptionsPK.equals(other.agencyOptionsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyOptions[agencyOptionsPK=" + agencyOptionsPK + "]";
    }

}
