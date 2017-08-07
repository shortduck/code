/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
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
@Table(name = "TKUOPT_TA_OPTIONS")
@NamedQueries({@NamedQuery(name = "TkuoptTaOptions.findAll", query = "SELECT t FROM TkuoptTaOptions t"), @NamedQuery(name = "TkuoptTaOptions.findByDepartment", query = "SELECT t FROM TkuoptTaOptions t WHERE t.tkuoptTaOptionsPK.department = :department"), @NamedQuery(name = "TkuoptTaOptions.findByAgency", query = "SELECT t FROM TkuoptTaOptions t WHERE t.tkuoptTaOptionsPK.agency = :agency"), @NamedQuery(name = "TkuoptTaOptions.findByTku", query = "SELECT t FROM TkuoptTaOptions t WHERE t.tkuoptTaOptionsPK.tku = :tku"), @NamedQuery(name = "TkuoptTaOptions.findByTimeEntryByDayReqdInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.timeEntryByDayReqdInd = :timeEntryByDayReqdInd"), @NamedQuery(name = "TkuoptTaOptions.findByAuditInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.auditInd = :auditInd"), @NamedQuery(name = "TkuoptTaOptions.findByValidateMultipurpInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.validateMultipurpInd = :validateMultipurpInd"), @NamedQuery(name = "TkuoptTaOptions.findByTimeIndexEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.timeIndexEntryInd = :timeIndexEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByTimePcaEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.timePcaEntryInd = :timePcaEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByTimeGrantEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.timeGrantEntryInd = :timeGrantEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByTimeAg1EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.timeAg1EntryInd = :timeAg1EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByTimeProjectEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.timeProjectEntryInd = :timeProjectEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByTimeAg2EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.timeAg2EntryInd = :timeAg2EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByTimeAg3EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.timeAg3EntryInd = :timeAg3EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByTimeMultipurpEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.timeMultipurpEntryInd = :timeMultipurpEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByEquipIndexEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.equipIndexEntryInd = :equipIndexEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByEquipPcaEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.equipPcaEntryInd = :equipPcaEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByEquipGrantEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.equipGrantEntryInd = :equipGrantEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByEquipAg1EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.equipAg1EntryInd = :equipAg1EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByEquipProjectEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.equipProjectEntryInd = :equipProjectEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByEquipAg2EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.equipAg2EntryInd = :equipAg2EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByEquipAg3EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.equipAg3EntryInd = :equipAg3EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByEquipMultipurpEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.equipMultipurpEntryInd = :equipMultipurpEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByInvIndexEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.invIndexEntryInd = :invIndexEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByInvPcaEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.invPcaEntryInd = :invPcaEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByInvGrantEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.invGrantEntryInd = :invGrantEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByInvAg1EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.invAg1EntryInd = :invAg1EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByInvProjectEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.invProjectEntryInd = :invProjectEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByInvAg2EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.invAg2EntryInd = :invAg2EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByInvAg3EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.invAg3EntryInd = :invAg3EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByInvMultipurpEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.invMultipurpEntryInd = :invMultipurpEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByActivityIndexEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.activityIndexEntryInd = :activityIndexEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByActivityPcaEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.activityPcaEntryInd = :activityPcaEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByActivityGrantEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.activityGrantEntryInd = :activityGrantEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByActivityAg1EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.activityAg1EntryInd = :activityAg1EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByActivityProjectEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.activityProjectEntryInd = :activityProjectEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByActivityAg2EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.activityAg2EntryInd = :activityAg2EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByActivityAg3EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.activityAg3EntryInd = :activityAg3EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByActivityMultipurpEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.activityMultipurpEntryInd = :activityMultipurpEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByExpIndexEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.expIndexEntryInd = :expIndexEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByExpPcaEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.expPcaEntryInd = :expPcaEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByExpGrantEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.expGrantEntryInd = :expGrantEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByExpAg1EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.expAg1EntryInd = :expAg1EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByExpProjectEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.expProjectEntryInd = :expProjectEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByExpAg2EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.expAg2EntryInd = :expAg2EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByExpAg3EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.expAg3EntryInd = :expAg3EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByExpMultipurpEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.expMultipurpEntryInd = :expMultipurpEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByAdvIndexEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.advIndexEntryInd = :advIndexEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByAdvPcaEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.advPcaEntryInd = :advPcaEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByAdvGrantEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.advGrantEntryInd = :advGrantEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByAdvAg1EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.advAg1EntryInd = :advAg1EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByAdvProjectEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.advProjectEntryInd = :advProjectEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByAdvAg2EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.advAg2EntryInd = :advAg2EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByAdvAg3EntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.advAg3EntryInd = :advAg3EntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByAdvMultipurpEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.advMultipurpEntryInd = :advMultipurpEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByShowDefaultWorkScheduleInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.showDefaultWorkScheduleInd = :showDefaultWorkScheduleInd"), @NamedQuery(name = "TkuoptTaOptions.findByModifiedUserId", query = "SELECT t FROM TkuoptTaOptions t WHERE t.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "TkuoptTaOptions.findByModifiedDate", query = "SELECT t FROM TkuoptTaOptions t WHERE t.modifiedDate = :modifiedDate"), @NamedQuery(name = "TkuoptTaOptions.findByTimePersMilesEntryInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.timePersMilesEntryInd = :timePersMilesEntryInd"), @NamedQuery(name = "TkuoptTaOptions.findByCopyTimeFuncInd", query = "SELECT t FROM TkuoptTaOptions t WHERE t.copyTimeFuncInd = :copyTimeFuncInd")})
public class TkuoptTaOptions implements Serializable {

	private static final long serialVersionUID = 2766117135656363602L;

	@EmbeddedId
    protected TkuoptTaOptionsPK tkuoptTaOptionsPK;
    
    @Column(name = "TIME_ENTRY_BY_DAY_REQD_IND")
    private String timeEntryByDayReqdInd;
    
    @Column(name = "AUDIT_IND")
    private String auditInd;
    
    @Column(name = "VALIDATE_MULTIPURP_IND")
    private String validateMultipurpInd;
    
    @Column(name = "TIME_INDEX_ENTRY_IND")
    private String timeIndexEntryInd;
    
    @Column(name = "TIME_PCA_ENTRY_IND")
    private String timePcaEntryInd;
    
    @Column(name = "TIME_GRANT_ENTRY_IND")
    private String timeGrantEntryInd;
    
    @Column(name = "TIME_AG1_ENTRY_IND")
    private String timeAg1EntryInd;
    
    @Column(name = "TIME_PROJECT_ENTRY_IND")
    private String timeProjectEntryInd;
    
    @Column(name = "TIME_AG2_ENTRY_IND")
    private String timeAg2EntryInd;
    
    @Column(name = "TIME_AG3_ENTRY_IND")
    private String timeAg3EntryInd;
    
    @Column(name = "TIME_MULTIPURP_ENTRY_IND")
    private String timeMultipurpEntryInd;
    
    @Column(name = "EQUIP_INDEX_ENTRY_IND")
    private String equipIndexEntryInd;
    
    @Column(name = "EQUIP_PCA_ENTRY_IND")
    private String equipPcaEntryInd;
    
    @Column(name = "EQUIP_GRANT_ENTRY_IND")
    private String equipGrantEntryInd;
    
    @Column(name = "EQUIP_AG1_ENTRY_IND")
    private String equipAg1EntryInd;
    
    @Column(name = "EQUIP_PROJECT_ENTRY_IND")
    private String equipProjectEntryInd;
    
    @Column(name = "EQUIP_AG2_ENTRY_IND")
    private String equipAg2EntryInd;
    
    @Column(name = "EQUIP_AG3_ENTRY_IND")
    private String equipAg3EntryInd;
    
    @Column(name = "EQUIP_MULTIPURP_ENTRY_IND")
    private String equipMultipurpEntryInd;
    
    @Column(name = "INV_INDEX_ENTRY_IND")
    private String invIndexEntryInd;
    
    @Column(name = "INV_PCA_ENTRY_IND")
    private String invPcaEntryInd;
    
    @Column(name = "INV_GRANT_ENTRY_IND")
    private String invGrantEntryInd;
    
    @Column(name = "INV_AG1_ENTRY_IND")
    private String invAg1EntryInd;
    
    @Column(name = "INV_PROJECT_ENTRY_IND")
    private String invProjectEntryInd;
    
    @Column(name = "INV_AG2_ENTRY_IND")
    private String invAg2EntryInd;
    
    @Column(name = "INV_AG3_ENTRY_IND")
    private String invAg3EntryInd;
    
    @Column(name = "INV_MULTIPURP_ENTRY_IND")
    private String invMultipurpEntryInd;
    
    @Column(name = "ACTIVITY_INDEX_ENTRY_IND")
    private String activityIndexEntryInd;
    
    @Column(name = "ACTIVITY_PCA_ENTRY_IND")
    private String activityPcaEntryInd;
    
    @Column(name = "ACTIVITY_GRANT_ENTRY_IND")
    private String activityGrantEntryInd;
    
    @Column(name = "ACTIVITY_AG1_ENTRY_IND")
    private String activityAg1EntryInd;
    
    @Column(name = "ACTIVITY_PROJECT_ENTRY_IND")
    private String activityProjectEntryInd;
    
    @Column(name = "ACTIVITY_AG2_ENTRY_IND")
    private String activityAg2EntryInd;
    
    @Column(name = "ACTIVITY_AG3_ENTRY_IND")
    private String activityAg3EntryInd;
    
    @Column(name = "ACTIVITY_MULTIPURP_ENTRY_IND")
    private String activityMultipurpEntryInd;
    
    @Column(name = "EXP_INDEX_ENTRY_IND")
    private String expIndexEntryInd;
    
    @Column(name = "EXP_PCA_ENTRY_IND")
    private String expPcaEntryInd;
    
    @Column(name = "EXP_GRANT_ENTRY_IND")
    private String expGrantEntryInd;
    
    @Column(name = "EXP_AG1_ENTRY_IND")
    private String expAg1EntryInd;
    
    @Column(name = "EXP_PROJECT_ENTRY_IND")
    private String expProjectEntryInd;
    
    @Column(name = "EXP_AG2_ENTRY_IND")
    private String expAg2EntryInd;
    
    @Column(name = "EXP_AG3_ENTRY_IND")
    private String expAg3EntryInd;
    
    @Column(name = "EXP_MULTIPURP_ENTRY_IND")
    private String expMultipurpEntryInd;
    
    @Column(name = "ADV_INDEX_ENTRY_IND")
    private String advIndexEntryInd;
    
    @Column(name = "ADV_PCA_ENTRY_IND")
    private String advPcaEntryInd;
    
    @Column(name = "ADV_GRANT_ENTRY_IND")
    private String advGrantEntryInd;
    
    @Column(name = "ADV_AG1_ENTRY_IND")
    private String advAg1EntryInd;
    
    @Column(name = "ADV_PROJECT_ENTRY_IND")
    private String advProjectEntryInd;
    
    @Column(name = "ADV_AG2_ENTRY_IND")
    private String advAg2EntryInd;
    
    @Column(name = "ADV_AG3_ENTRY_IND")
    private String advAg3EntryInd;
    
    @Column(name = "ADV_MULTIPURP_ENTRY_IND")
    private String advMultipurpEntryInd;
    
    @Column(name = "SHOW_DEFAULT_WORK_SCHEDULE_IND")
    private String showDefaultWorkScheduleInd;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    @Column(name = "TIME_PERS_MILES_ENTRY_IND")
    private String timePersMilesEntryInd;
    
    @Column(name = "COPY_TIME_FUNC_IND")
    private String copyTimeFuncInd;
    
    @Column(name = "COPY_EXP_FUNC_IND")
    private String copyExpFuncInd;
    
    //@JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false), @JoinColumn(name = "TKU", referencedColumnName = "TKU", insertable = false, updatable = false)})
    //@OneToOne(optional = false)
    @Column(name = "TKU")
    private String tkus;

    public TkuoptTaOptions() {
    }

    public TkuoptTaOptions(TkuoptTaOptionsPK tkuoptTaOptionsPK) {
        this.tkuoptTaOptionsPK = tkuoptTaOptionsPK;
    }

    public TkuoptTaOptions(TkuoptTaOptionsPK tkuoptTaOptionsPK, String timeEntryByDayReqdInd, String auditInd, String validateMultipurpInd, String timeIndexEntryInd, String timePcaEntryInd, String timeGrantEntryInd, String timeAg1EntryInd, String timeProjectEntryInd, String timeAg2EntryInd, String timeAg3EntryInd, String timeMultipurpEntryInd, String equipIndexEntryInd, String equipPcaEntryInd, String equipGrantEntryInd, String equipAg1EntryInd, String equipProjectEntryInd, String equipAg2EntryInd, String equipAg3EntryInd, String equipMultipurpEntryInd, String invIndexEntryInd, String invPcaEntryInd, String invGrantEntryInd, String invAg1EntryInd, String invProjectEntryInd, String invAg2EntryInd, String invAg3EntryInd, String invMultipurpEntryInd, String activityIndexEntryInd, String activityPcaEntryInd, String activityGrantEntryInd, String activityAg1EntryInd, String activityProjectEntryInd, String activityAg2EntryInd, String activityAg3EntryInd, String activityMultipurpEntryInd, String expIndexEntryInd, String expPcaEntryInd, String expGrantEntryInd, String expAg1EntryInd, String expProjectEntryInd, String expAg2EntryInd, String expAg3EntryInd, String expMultipurpEntryInd, String advIndexEntryInd, String advPcaEntryInd, String advGrantEntryInd, String advAg1EntryInd, String advProjectEntryInd, String advAg2EntryInd, String advAg3EntryInd, String advMultipurpEntryInd, String showDefaultWorkScheduleInd, String timePersMilesEntryInd, String copyTimeFuncInd) {
        this.tkuoptTaOptionsPK = tkuoptTaOptionsPK;
        this.timeEntryByDayReqdInd = timeEntryByDayReqdInd;
        this.auditInd = auditInd;
        this.validateMultipurpInd = validateMultipurpInd;
        this.timeIndexEntryInd = timeIndexEntryInd;
        this.timePcaEntryInd = timePcaEntryInd;
        this.timeGrantEntryInd = timeGrantEntryInd;
        this.timeAg1EntryInd = timeAg1EntryInd;
        this.timeProjectEntryInd = timeProjectEntryInd;
        this.timeAg2EntryInd = timeAg2EntryInd;
        this.timeAg3EntryInd = timeAg3EntryInd;
        this.timeMultipurpEntryInd = timeMultipurpEntryInd;
        this.equipIndexEntryInd = equipIndexEntryInd;
        this.equipPcaEntryInd = equipPcaEntryInd;
        this.equipGrantEntryInd = equipGrantEntryInd;
        this.equipAg1EntryInd = equipAg1EntryInd;
        this.equipProjectEntryInd = equipProjectEntryInd;
        this.equipAg2EntryInd = equipAg2EntryInd;
        this.equipAg3EntryInd = equipAg3EntryInd;
        this.equipMultipurpEntryInd = equipMultipurpEntryInd;
        this.invIndexEntryInd = invIndexEntryInd;
        this.invPcaEntryInd = invPcaEntryInd;
        this.invGrantEntryInd = invGrantEntryInd;
        this.invAg1EntryInd = invAg1EntryInd;
        this.invProjectEntryInd = invProjectEntryInd;
        this.invAg2EntryInd = invAg2EntryInd;
        this.invAg3EntryInd = invAg3EntryInd;
        this.invMultipurpEntryInd = invMultipurpEntryInd;
        this.activityIndexEntryInd = activityIndexEntryInd;
        this.activityPcaEntryInd = activityPcaEntryInd;
        this.activityGrantEntryInd = activityGrantEntryInd;
        this.activityAg1EntryInd = activityAg1EntryInd;
        this.activityProjectEntryInd = activityProjectEntryInd;
        this.activityAg2EntryInd = activityAg2EntryInd;
        this.activityAg3EntryInd = activityAg3EntryInd;
        this.activityMultipurpEntryInd = activityMultipurpEntryInd;
        this.expIndexEntryInd = expIndexEntryInd;
        this.expPcaEntryInd = expPcaEntryInd;
        this.expGrantEntryInd = expGrantEntryInd;
        this.expAg1EntryInd = expAg1EntryInd;
        this.expProjectEntryInd = expProjectEntryInd;
        this.expAg2EntryInd = expAg2EntryInd;
        this.expAg3EntryInd = expAg3EntryInd;
        this.expMultipurpEntryInd = expMultipurpEntryInd;
        this.advIndexEntryInd = advIndexEntryInd;
        this.advPcaEntryInd = advPcaEntryInd;
        this.advGrantEntryInd = advGrantEntryInd;
        this.advAg1EntryInd = advAg1EntryInd;
        this.advProjectEntryInd = advProjectEntryInd;
        this.advAg2EntryInd = advAg2EntryInd;
        this.advAg3EntryInd = advAg3EntryInd;
        this.advMultipurpEntryInd = advMultipurpEntryInd;
        this.showDefaultWorkScheduleInd = showDefaultWorkScheduleInd;
        this.timePersMilesEntryInd = timePersMilesEntryInd;
        this.copyTimeFuncInd = copyTimeFuncInd;
        this.copyExpFuncInd = copyExpFuncInd;
    }

    public TkuoptTaOptions(String department, String agency, String tku) {
        this.tkuoptTaOptionsPK = new TkuoptTaOptionsPK(department, agency, tku);
    }

    public TkuoptTaOptionsPK getTkuoptTaOptionsPK() {
        return tkuoptTaOptionsPK;
    }

    public void setTkuoptTaOptionsPK(TkuoptTaOptionsPK tkuoptTaOptionsPK) {
        this.tkuoptTaOptionsPK = tkuoptTaOptionsPK;
    }

    public String getTimeEntryByDayReqdInd() {
        return timeEntryByDayReqdInd;
    }

    public void setTimeEntryByDayReqdInd(String timeEntryByDayReqdInd) {
        this.timeEntryByDayReqdInd = timeEntryByDayReqdInd;
    }

    public String getAuditInd() {
        return auditInd;
    }

    public void setAuditInd(String auditInd) {
        this.auditInd = auditInd;
    }

    public String getValidateMultipurpInd() {
        return validateMultipurpInd;
    }

    public void setValidateMultipurpInd(String validateMultipurpInd) {
        this.validateMultipurpInd = validateMultipurpInd;
    }

    public String getTimeIndexEntryInd() {
        return timeIndexEntryInd;
    }

    public void setTimeIndexEntryInd(String timeIndexEntryInd) {
        this.timeIndexEntryInd = timeIndexEntryInd;
    }

    public String getTimePcaEntryInd() {
        return timePcaEntryInd;
    }

    public void setTimePcaEntryInd(String timePcaEntryInd) {
        this.timePcaEntryInd = timePcaEntryInd;
    }

    public String getTimeGrantEntryInd() {
        return timeGrantEntryInd;
    }

    public void setTimeGrantEntryInd(String timeGrantEntryInd) {
        this.timeGrantEntryInd = timeGrantEntryInd;
    }

    public String getTimeAg1EntryInd() {
        return timeAg1EntryInd;
    }

    public void setTimeAg1EntryInd(String timeAg1EntryInd) {
        this.timeAg1EntryInd = timeAg1EntryInd;
    }

    public String getTimeProjectEntryInd() {
        return timeProjectEntryInd;
    }

    public void setTimeProjectEntryInd(String timeProjectEntryInd) {
        this.timeProjectEntryInd = timeProjectEntryInd;
    }

    public String getTimeAg2EntryInd() {
        return timeAg2EntryInd;
    }

    public void setTimeAg2EntryInd(String timeAg2EntryInd) {
        this.timeAg2EntryInd = timeAg2EntryInd;
    }

    public String getTimeAg3EntryInd() {
        return timeAg3EntryInd;
    }

    public void setTimeAg3EntryInd(String timeAg3EntryInd) {
        this.timeAg3EntryInd = timeAg3EntryInd;
    }

    public String getTimeMultipurpEntryInd() {
        return timeMultipurpEntryInd;
    }

    public void setTimeMultipurpEntryInd(String timeMultipurpEntryInd) {
        this.timeMultipurpEntryInd = timeMultipurpEntryInd;
    }

    public String getEquipIndexEntryInd() {
        return equipIndexEntryInd;
    }

    public void setEquipIndexEntryInd(String equipIndexEntryInd) {
        this.equipIndexEntryInd = equipIndexEntryInd;
    }

    public String getEquipPcaEntryInd() {
        return equipPcaEntryInd;
    }

    public void setEquipPcaEntryInd(String equipPcaEntryInd) {
        this.equipPcaEntryInd = equipPcaEntryInd;
    }

    public String getEquipGrantEntryInd() {
        return equipGrantEntryInd;
    }

    public void setEquipGrantEntryInd(String equipGrantEntryInd) {
        this.equipGrantEntryInd = equipGrantEntryInd;
    }

    public String getEquipAg1EntryInd() {
        return equipAg1EntryInd;
    }

    public void setEquipAg1EntryInd(String equipAg1EntryInd) {
        this.equipAg1EntryInd = equipAg1EntryInd;
    }

    public String getEquipProjectEntryInd() {
        return equipProjectEntryInd;
    }

    public void setEquipProjectEntryInd(String equipProjectEntryInd) {
        this.equipProjectEntryInd = equipProjectEntryInd;
    }

    public String getEquipAg2EntryInd() {
        return equipAg2EntryInd;
    }

    public void setEquipAg2EntryInd(String equipAg2EntryInd) {
        this.equipAg2EntryInd = equipAg2EntryInd;
    }

    public String getEquipAg3EntryInd() {
        return equipAg3EntryInd;
    }

    public void setEquipAg3EntryInd(String equipAg3EntryInd) {
        this.equipAg3EntryInd = equipAg3EntryInd;
    }

    public String getEquipMultipurpEntryInd() {
        return equipMultipurpEntryInd;
    }

    public void setEquipMultipurpEntryInd(String equipMultipurpEntryInd) {
        this.equipMultipurpEntryInd = equipMultipurpEntryInd;
    }

    public String getInvIndexEntryInd() {
        return invIndexEntryInd;
    }

    public void setInvIndexEntryInd(String invIndexEntryInd) {
        this.invIndexEntryInd = invIndexEntryInd;
    }

    public String getInvPcaEntryInd() {
        return invPcaEntryInd;
    }

    public void setInvPcaEntryInd(String invPcaEntryInd) {
        this.invPcaEntryInd = invPcaEntryInd;
    }

    public String getInvGrantEntryInd() {
        return invGrantEntryInd;
    }

    public void setInvGrantEntryInd(String invGrantEntryInd) {
        this.invGrantEntryInd = invGrantEntryInd;
    }

    public String getInvAg1EntryInd() {
        return invAg1EntryInd;
    }

    public void setInvAg1EntryInd(String invAg1EntryInd) {
        this.invAg1EntryInd = invAg1EntryInd;
    }

    public String getInvProjectEntryInd() {
        return invProjectEntryInd;
    }

    public void setInvProjectEntryInd(String invProjectEntryInd) {
        this.invProjectEntryInd = invProjectEntryInd;
    }

    public String getInvAg2EntryInd() {
        return invAg2EntryInd;
    }

    public void setInvAg2EntryInd(String invAg2EntryInd) {
        this.invAg2EntryInd = invAg2EntryInd;
    }

    public String getInvAg3EntryInd() {
        return invAg3EntryInd;
    }

    public void setInvAg3EntryInd(String invAg3EntryInd) {
        this.invAg3EntryInd = invAg3EntryInd;
    }

    public String getInvMultipurpEntryInd() {
        return invMultipurpEntryInd;
    }

    public void setInvMultipurpEntryInd(String invMultipurpEntryInd) {
        this.invMultipurpEntryInd = invMultipurpEntryInd;
    }

    public String getActivityIndexEntryInd() {
        return activityIndexEntryInd;
    }

    public void setActivityIndexEntryInd(String activityIndexEntryInd) {
        this.activityIndexEntryInd = activityIndexEntryInd;
    }

    public String getActivityPcaEntryInd() {
        return activityPcaEntryInd;
    }

    public void setActivityPcaEntryInd(String activityPcaEntryInd) {
        this.activityPcaEntryInd = activityPcaEntryInd;
    }

    public String getActivityGrantEntryInd() {
        return activityGrantEntryInd;
    }

    public void setActivityGrantEntryInd(String activityGrantEntryInd) {
        this.activityGrantEntryInd = activityGrantEntryInd;
    }

    public String getActivityAg1EntryInd() {
        return activityAg1EntryInd;
    }

    public void setActivityAg1EntryInd(String activityAg1EntryInd) {
        this.activityAg1EntryInd = activityAg1EntryInd;
    }

    public String getActivityProjectEntryInd() {
        return activityProjectEntryInd;
    }

    public void setActivityProjectEntryInd(String activityProjectEntryInd) {
        this.activityProjectEntryInd = activityProjectEntryInd;
    }

    public String getActivityAg2EntryInd() {
        return activityAg2EntryInd;
    }

    public void setActivityAg2EntryInd(String activityAg2EntryInd) {
        this.activityAg2EntryInd = activityAg2EntryInd;
    }

    public String getActivityAg3EntryInd() {
        return activityAg3EntryInd;
    }

    public void setActivityAg3EntryInd(String activityAg3EntryInd) {
        this.activityAg3EntryInd = activityAg3EntryInd;
    }

    public String getActivityMultipurpEntryInd() {
        return activityMultipurpEntryInd;
    }

    public void setActivityMultipurpEntryInd(String activityMultipurpEntryInd) {
        this.activityMultipurpEntryInd = activityMultipurpEntryInd;
    }

    public String getExpIndexEntryInd() {
        return expIndexEntryInd;
    }

    public void setExpIndexEntryInd(String expIndexEntryInd) {
        this.expIndexEntryInd = expIndexEntryInd;
    }

    public String getExpPcaEntryInd() {
        return expPcaEntryInd;
    }

    public void setExpPcaEntryInd(String expPcaEntryInd) {
        this.expPcaEntryInd = expPcaEntryInd;
    }

    public String getExpGrantEntryInd() {
        return expGrantEntryInd;
    }

    public void setExpGrantEntryInd(String expGrantEntryInd) {
        this.expGrantEntryInd = expGrantEntryInd;
    }

    public String getExpAg1EntryInd() {
        return expAg1EntryInd;
    }

    public void setExpAg1EntryInd(String expAg1EntryInd) {
        this.expAg1EntryInd = expAg1EntryInd;
    }

    public String getExpProjectEntryInd() {
        return expProjectEntryInd;
    }

    public void setExpProjectEntryInd(String expProjectEntryInd) {
        this.expProjectEntryInd = expProjectEntryInd;
    }

    public String getExpAg2EntryInd() {
        return expAg2EntryInd;
    }

    public void setExpAg2EntryInd(String expAg2EntryInd) {
        this.expAg2EntryInd = expAg2EntryInd;
    }

    public String getExpAg3EntryInd() {
        return expAg3EntryInd;
    }

    public void setExpAg3EntryInd(String expAg3EntryInd) {
        this.expAg3EntryInd = expAg3EntryInd;
    }

    public String getExpMultipurpEntryInd() {
        return expMultipurpEntryInd;
    }

    public void setExpMultipurpEntryInd(String expMultipurpEntryInd) {
        this.expMultipurpEntryInd = expMultipurpEntryInd;
    }

    public String getAdvIndexEntryInd() {
        return advIndexEntryInd;
    }

    public void setAdvIndexEntryInd(String advIndexEntryInd) {
        this.advIndexEntryInd = advIndexEntryInd;
    }

    public String getAdvPcaEntryInd() {
        return advPcaEntryInd;
    }

    public void setAdvPcaEntryInd(String advPcaEntryInd) {
        this.advPcaEntryInd = advPcaEntryInd;
    }

    public String getAdvGrantEntryInd() {
        return advGrantEntryInd;
    }

    public void setAdvGrantEntryInd(String advGrantEntryInd) {
        this.advGrantEntryInd = advGrantEntryInd;
    }

    public String getAdvAg1EntryInd() {
        return advAg1EntryInd;
    }

    public void setAdvAg1EntryInd(String advAg1EntryInd) {
        this.advAg1EntryInd = advAg1EntryInd;
    }

    public String getAdvProjectEntryInd() {
        return advProjectEntryInd;
    }

    public void setAdvProjectEntryInd(String advProjectEntryInd) {
        this.advProjectEntryInd = advProjectEntryInd;
    }

    public String getAdvAg2EntryInd() {
        return advAg2EntryInd;
    }

    public void setAdvAg2EntryInd(String advAg2EntryInd) {
        this.advAg2EntryInd = advAg2EntryInd;
    }

    public String getAdvAg3EntryInd() {
        return advAg3EntryInd;
    }

    public void setAdvAg3EntryInd(String advAg3EntryInd) {
        this.advAg3EntryInd = advAg3EntryInd;
    }

    public String getAdvMultipurpEntryInd() {
        return advMultipurpEntryInd;
    }

    public void setAdvMultipurpEntryInd(String advMultipurpEntryInd) {
        this.advMultipurpEntryInd = advMultipurpEntryInd;
    }

    public String getShowDefaultWorkScheduleInd() {
        return showDefaultWorkScheduleInd;
    }

    public void setShowDefaultWorkScheduleInd(String showDefaultWorkScheduleInd) {
        this.showDefaultWorkScheduleInd = showDefaultWorkScheduleInd;
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

    public String getTimePersMilesEntryInd() {
        return timePersMilesEntryInd;
    }

    public void setTimePersMilesEntryInd(String timePersMilesEntryInd) {
        this.timePersMilesEntryInd = timePersMilesEntryInd;
    }

    public String getCopyTimeFuncInd() {
        return copyTimeFuncInd;
    }

    public void setCopyTimeFuncInd(String copyTimeFuncInd) {
        this.copyTimeFuncInd = copyTimeFuncInd;
    }    

    public String getCopyExpFuncInd() {
		return copyExpFuncInd;
	}

	public void setCopyExpFuncInd(String copyExpFuncInd) {
		this.copyExpFuncInd = copyExpFuncInd;
	}

	public String getTkus() {
        return tkus;
    }

    public void setTkus(String tkus) {
        this.tkus = tkus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tkuoptTaOptionsPK != null ? tkuoptTaOptionsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TkuoptTaOptions)) {
            return false;
        }
        TkuoptTaOptions other = (TkuoptTaOptions) object;
        if ((this.tkuoptTaOptionsPK == null && other.tkuoptTaOptionsPK != null) || (this.tkuoptTaOptionsPK != null && !this.tkuoptTaOptionsPK.equals(other.tkuoptTaOptionsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.TkuoptTaOptions[tkuoptTaOptionsPK=" + tkuoptTaOptionsPK + "]";
    }

}
