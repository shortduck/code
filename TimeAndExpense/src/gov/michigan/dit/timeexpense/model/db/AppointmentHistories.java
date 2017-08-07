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
@Table(name = "APPOINTMENT_HISTORIES")
@NamedQueries({@NamedQuery(name = "AppointmentHistories.findAll", query = "SELECT a FROM AppointmentHistories a"), @NamedQuery(name = "AppointmentHistories.findByAphsIdentifier", query = "SELECT a FROM AppointmentHistories a WHERE a.aphsIdentifier = :aphsIdentifier"), @NamedQuery(name = "AppointmentHistories.findByStartDate", query = "SELECT a FROM AppointmentHistories a WHERE a.startDate = :startDate"), @NamedQuery(name = "AppointmentHistories.findByEndDate", query = "SELECT a FROM AppointmentHistories a WHERE a.endDate = :endDate"), @NamedQuery(name = "AppointmentHistories.findByDepartment", query = "SELECT a FROM AppointmentHistories a WHERE a.department = :department"), @NamedQuery(name = "AppointmentHistories.findByAgency", query = "SELECT a FROM AppointmentHistories a WHERE a.agency = :agency"), @NamedQuery(name = "AppointmentHistories.findByTku", query = "SELECT a FROM AppointmentHistories a WHERE a.tku = :tku"), @NamedQuery(name = "AppointmentHistories.findByPositionNumber", query = "SELECT a FROM AppointmentHistories a WHERE a.positionNumber = :positionNumber"), @NamedQuery(name = "AppointmentHistories.findByAppointmentDate", query = "SELECT a FROM AppointmentHistories a WHERE a.appointmentDate = :appointmentDate"), @NamedQuery(name = "AppointmentHistories.findByMoveDate", query = "SELECT a FROM AppointmentHistories a WHERE a.moveDate = :moveDate"), @NamedQuery(name = "AppointmentHistories.findByDepartureDate", query = "SELECT a FROM AppointmentHistories a WHERE a.departureDate = :departureDate"), @NamedQuery(name = "AppointmentHistories.findByOnCallCode", query = "SELECT a FROM AppointmentHistories a WHERE a.onCallCode = :onCallCode"), @NamedQuery(name = "AppointmentHistories.findByClassType", query = "SELECT a FROM AppointmentHistories a WHERE a.classType = :classType"), @NamedQuery(name = "AppointmentHistories.findByPcel", query = "SELECT a FROM AppointmentHistories a WHERE a.pcel = :pcel"), @NamedQuery(name = "AppointmentHistories.findByBasePayRate", query = "SELECT a FROM AppointmentHistories a WHERE a.basePayRate = :basePayRate"), @NamedQuery(name = "AppointmentHistories.findByShift2PremiumPercent", query = "SELECT a FROM AppointmentHistories a WHERE a.shift2PremiumPercent = :shift2PremiumPercent"), @NamedQuery(name = "AppointmentHistories.findByShift3PremiumPercent", query = "SELECT a FROM AppointmentHistories a WHERE a.shift3PremiumPercent = :shift3PremiumPercent"), @NamedQuery(name = "AppointmentHistories.findByPayStepCode", query = "SELECT a FROM AppointmentHistories a WHERE a.payStepCode = :payStepCode"), @NamedQuery(name = "AppointmentHistories.findByPlan", query = "SELECT a FROM AppointmentHistories a WHERE a.plan = :plan"), @NamedQuery(name = "AppointmentHistories.findByPlanHours", query = "SELECT a FROM AppointmentHistories a WHERE a.planHours = :planHours"), @NamedQuery(name = "AppointmentHistories.findByPlanExpirationDate", query = "SELECT a FROM AppointmentHistories a WHERE a.planExpirationDate = :planExpirationDate"), @NamedQuery(name = "AppointmentHistories.findByFlsaCode", query = "SELECT a FROM AppointmentHistories a WHERE a.flsaCode = :flsaCode"), @NamedQuery(name = "AppointmentHistories.findByFlsaExpirationDate", query = "SELECT a FROM AppointmentHistories a WHERE a.flsaExpirationDate = :flsaExpirationDate"), @NamedQuery(name = "AppointmentHistories.findByStandardHoursReg", query = "SELECT a FROM AppointmentHistories a WHERE a.standardHoursReg = :standardHoursReg"), @NamedQuery(name = "AppointmentHistories.findByStandardHoursShift2", query = "SELECT a FROM AppointmentHistories a WHERE a.standardHoursShift2 = :standardHoursShift2"), @NamedQuery(name = "AppointmentHistories.findByStandardHoursShift3", query = "SELECT a FROM AppointmentHistories a WHERE a.standardHoursShift3 = :standardHoursShift3"), @NamedQuery(name = "AppointmentHistories.findByOrganizationNumber", query = "SELECT a FROM AppointmentHistories a WHERE a.organizationNumber = :organizationNumber"), @NamedQuery(name = "AppointmentHistories.findByApptType", query = "SELECT a FROM AppointmentHistories a WHERE a.apptType = :apptType"), @NamedQuery(name = "AppointmentHistories.findByApptMethod", query = "SELECT a FROM AppointmentHistories a WHERE a.apptMethod = :apptMethod"), @NamedQuery(name = "AppointmentHistories.findByApptDuration", query = "SELECT a FROM AppointmentHistories a WHERE a.apptDuration = :apptDuration"), @NamedQuery(name = "AppointmentHistories.findByClassCode", query = "SELECT a FROM AppointmentHistories a WHERE a.classCode = :classCode"), @NamedQuery(name = "AppointmentHistories.findByPositionType", query = "SELECT a FROM AppointmentHistories a WHERE a.positionType = :positionType"), @NamedQuery(name = "AppointmentHistories.findByPositionSchedule", query = "SELECT a FROM AppointmentHistories a WHERE a.positionSchedule = :positionSchedule"), @NamedQuery(name = "AppointmentHistories.findByPositionDuration", query = "SELECT a FROM AppointmentHistories a WHERE a.positionDuration = :positionDuration"), @NamedQuery(name = "AppointmentHistories.findByWorkSite", query = "SELECT a FROM AppointmentHistories a WHERE a.workSite = :workSite"), @NamedQuery(name = "AppointmentHistories.findByWorkCounty", query = "SELECT a FROM AppointmentHistories a WHERE a.workCounty = :workCounty"), @NamedQuery(name = "AppointmentHistories.findByModifiedDate", query = "SELECT a FROM AppointmentHistories a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "AppointmentHistories.findByModifiedUserId", query = "SELECT a FROM AppointmentHistories a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "AppointmentHistories.findByDeptCode", query = "SELECT a FROM AppointmentHistories a WHERE a.deptCode = :deptCode"), @NamedQuery(name = "AppointmentHistories.findBySalaryClass", query = "SELECT a FROM AppointmentHistories a WHERE a.salaryClass = :salaryClass"), @NamedQuery(name = "AppointmentHistories.findBySchedule", query = "SELECT a FROM AppointmentHistories a WHERE a.schedule = :schedule"), @NamedQuery(name = "AppointmentHistories.findByPayGrade", query = "SELECT a FROM AppointmentHistories a WHERE a.payGrade = :payGrade"), @NamedQuery(name = "AppointmentHistories.findByExempt", query = "SELECT a FROM AppointmentHistories a WHERE a.exempt = :exempt"), @NamedQuery(name = "AppointmentHistories.findByProcessLevel", query = "SELECT a FROM AppointmentHistories a WHERE a.processLevel = :processLevel"), @NamedQuery(name = "AppointmentHistories.findByPprismApptStatusCode", query = "SELECT a FROM AppointmentHistories a WHERE a.pprismApptStatusCode = :pprismApptStatusCode"), @NamedQuery(name = "AppointmentHistories.findByPositionLevel", query = "SELECT a FROM AppointmentHistories a WHERE a.positionLevel = :positionLevel"), @NamedQuery(name = "AppointmentHistories.findByTransferType", query = "SELECT a FROM AppointmentHistories a WHERE a.transferType = :transferType"), @NamedQuery(name = "AppointmentHistories.findByTransferDate", query = "SELECT a FROM AppointmentHistories a WHERE a.transferDate = :transferDate"), @NamedQuery(name = "AppointmentHistories.findBySequenceNumber", query = "SELECT a FROM AppointmentHistories a WHERE a.sequenceNumber = :sequenceNumber")})
public class AppointmentHistories implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "APHS_IDENTIFIER")
    private Integer aphsIdentifier;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "AGENCY")
    private String agency;
    
    @Column(name = "TKU")
    private String tku;
    @Column(name = "POSITION_NUMBER")
    private String positionNumber;
    @Column(name = "APPOINTMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentDate;
    @Column(name = "MOVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date moveDate;
    @Column(name = "DEPARTURE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureDate;
    @Column(name = "ON_CALL_CODE")
    private String onCallCode;
    @Column(name = "CLASS_TYPE")
    private String classType;
    @Column(name = "PCEL")
    private String pcel;
    @Column(name = "BASE_PAY_RATE")
    private BigDecimal basePayRate;
    @Column(name = "SHIFT2_PREMIUM_PERCENT")
    private BigDecimal shift2PremiumPercent;
    @Column(name = "SHIFT3_PREMIUM_PERCENT")
    private BigDecimal shift3PremiumPercent;
    @Column(name = "PAY_STEP_CODE")
    private String payStepCode;
    @Column(name = "PLAN")
    private String plan;
    @Column(name = "PLAN_HOURS")
    private BigDecimal planHours;
    @Column(name = "PLAN_EXPIRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date planExpirationDate;
    @Column(name = "FLSA_CODE")
    private String flsaCode;
    @Column(name = "FLSA_EXPIRATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date flsaExpirationDate;
    @Column(name = "STANDARD_HOURS_REG")
    private BigDecimal standardHoursReg;
    @Column(name = "STANDARD_HOURS_SHIFT2")
    private BigDecimal standardHoursShift2;
    @Column(name = "STANDARD_HOURS_SHIFT3")
    private BigDecimal standardHoursShift3;
    @Column(name = "ORGANIZATION_NUMBER")
    private String organizationNumber;
    @Column(name = "APPT_TYPE")
    private String apptType;
    @Column(name = "APPT_METHOD")
    private String apptMethod;
    @Column(name = "APPT_DURATION")
    private String apptDuration;
    @Column(name = "CLASS_CODE")
    private String classCode;
    @Column(name = "POSITION_TYPE")
    private String positionType;
    @Column(name = "POSITION_SCHEDULE")
    private String positionSchedule;
    @Column(name = "POSITION_DURATION")
    private String positionDuration;
    @Column(name = "WORK_SITE")
    private String workSite;
    @Column(name = "WORK_COUNTY")
    private String workCounty;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "DEPT_CODE")
    private String deptCode;
    @Column(name = "SALARY_CLASS")
    private String salaryClass;
    @Column(name = "SCHEDULE")
    private String schedule;
    @Column(name = "PAY_GRADE")
    private String payGrade;
    @Column(name = "EXEMPT")
    private String exempt;
    @Column(name = "PROCESS_LEVEL")
    private String processLevel;
    @Column(name = "PPRISM_APPT_STATUS_CODE")
    private String pprismApptStatusCode;
    @Column(name = "POSITION_LEVEL")
    private Short positionLevel;
    @Column(name = "TRANSFER_TYPE")
    private String transferType;
    @Column(name = "TRANSFER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transferDate;
    @Column(name = "SEQUENCE_NUMBER")
    private Integer sequenceNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aphsIdentifier")
    private Collection<AppointmentHistSpecialPays> appointmentHistSpecialPaysCollection;
    @JoinColumn(name = "APPT_IDENTIFIER", referencedColumnName = "APPT_IDENTIFIER")
    @ManyToOne(optional = false)
    private Appointments apptIdentifier;
    @JoinColumn(name = "APPT_STATUS_CODE", referencedColumnName = "APPT_STATUS_CODE")
    @ManyToOne
    private AppointmentStatusCodes apptStatusCode;
    @JoinColumn(name = "BARGAINING_UNIT", referencedColumnName = "BARGAINING_UNIT")
    @ManyToOne
    private BargainingUnits bargainingUnit;
    @JoinColumn(name = "HJBCD_IDENTIFIER", referencedColumnName = "HJBCD_IDENTIFIER")
    @ManyToOne
    private HrmnJobCodes hjbcdIdentifier;
    @JoinColumn(name = "LOCATION", referencedColumnName = "LOCATION")
    @ManyToOne
    private HrmnLocations location;
    @JoinColumn(name = "SUPERVISOR", referencedColumnName = "SUPERVISOR")
    @ManyToOne
    private HrmnSupervisorCodes supervisor;
    @JoinColumn(name = "RETIREMENT_CODE", referencedColumnName = "RETIREMENT_CODE")
    @ManyToOne
    private RetirementCodes retirementCode;

    public AppointmentHistories() {
    }

    public AppointmentHistories(Integer aphsIdentifier) {
        this.aphsIdentifier = aphsIdentifier;
    }

    public AppointmentHistories(Integer aphsIdentifier, Date startDate, Date endDate, String department, String agency, String tku) {
        this.aphsIdentifier = aphsIdentifier;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.agency = agency;
        this.tku = tku;
    }

    public Integer getAphsIdentifier() {
        return aphsIdentifier;
    }

    public void setAphsIdentifier(Integer aphsIdentifier) {
        this.aphsIdentifier = aphsIdentifier;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getTku() {
        return tku;
    }

    public void setTku(String tku) {
        this.tku = tku;
    }

    public String getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(String positionNumber) {
        this.positionNumber = positionNumber;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getMoveDate() {
        return moveDate;
    }

    public void setMoveDate(Date moveDate) {
        this.moveDate = moveDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getOnCallCode() {
        return onCallCode;
    }

    public void setOnCallCode(String onCallCode) {
        this.onCallCode = onCallCode;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getPcel() {
        return pcel;
    }

    public void setPcel(String pcel) {
        this.pcel = pcel;
    }

    public BigDecimal getBasePayRate() {
        return basePayRate;
    }

    public void setBasePayRate(BigDecimal basePayRate) {
        this.basePayRate = basePayRate;
    }

    public BigDecimal getShift2PremiumPercent() {
        return shift2PremiumPercent;
    }

    public void setShift2PremiumPercent(BigDecimal shift2PremiumPercent) {
        this.shift2PremiumPercent = shift2PremiumPercent;
    }

    public BigDecimal getShift3PremiumPercent() {
        return shift3PremiumPercent;
    }

    public void setShift3PremiumPercent(BigDecimal shift3PremiumPercent) {
        this.shift3PremiumPercent = shift3PremiumPercent;
    }

    public String getPayStepCode() {
        return payStepCode;
    }

    public void setPayStepCode(String payStepCode) {
        this.payStepCode = payStepCode;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public BigDecimal getPlanHours() {
        return planHours;
    }

    public void setPlanHours(BigDecimal planHours) {
        this.planHours = planHours;
    }

    public Date getPlanExpirationDate() {
        return planExpirationDate;
    }

    public void setPlanExpirationDate(Date planExpirationDate) {
        this.planExpirationDate = planExpirationDate;
    }

    public String getFlsaCode() {
        return flsaCode;
    }

    public void setFlsaCode(String flsaCode) {
        this.flsaCode = flsaCode;
    }

    public Date getFlsaExpirationDate() {
        return flsaExpirationDate;
    }

    public void setFlsaExpirationDate(Date flsaExpirationDate) {
        this.flsaExpirationDate = flsaExpirationDate;
    }

    public BigDecimal getStandardHoursReg() {
        return standardHoursReg;
    }

    public void setStandardHoursReg(BigDecimal standardHoursReg) {
        this.standardHoursReg = standardHoursReg;
    }

    public BigDecimal getStandardHoursShift2() {
        return standardHoursShift2;
    }

    public void setStandardHoursShift2(BigDecimal standardHoursShift2) {
        this.standardHoursShift2 = standardHoursShift2;
    }

    public BigDecimal getStandardHoursShift3() {
        return standardHoursShift3;
    }

    public void setStandardHoursShift3(BigDecimal standardHoursShift3) {
        this.standardHoursShift3 = standardHoursShift3;
    }

    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(String organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public String getApptMethod() {
        return apptMethod;
    }

    public void setApptMethod(String apptMethod) {
        this.apptMethod = apptMethod;
    }

    public String getApptDuration() {
        return apptDuration;
    }

    public void setApptDuration(String apptDuration) {
        this.apptDuration = apptDuration;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getPositionSchedule() {
        return positionSchedule;
    }

    public void setPositionSchedule(String positionSchedule) {
        this.positionSchedule = positionSchedule;
    }

    public String getPositionDuration() {
        return positionDuration;
    }

    public void setPositionDuration(String positionDuration) {
        this.positionDuration = positionDuration;
    }

    public String getWorkSite() {
        return workSite;
    }

    public void setWorkSite(String workSite) {
        this.workSite = workSite;
    }

    public String getWorkCounty() {
        return workCounty;
    }

    public void setWorkCounty(String workCounty) {
        this.workCounty = workCounty;
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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getSalaryClass() {
        return salaryClass;
    }

    public void setSalaryClass(String salaryClass) {
        this.salaryClass = salaryClass;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getPayGrade() {
        return payGrade;
    }

    public void setPayGrade(String payGrade) {
        this.payGrade = payGrade;
    }

    public String getExempt() {
        return exempt;
    }

    public void setExempt(String exempt) {
        this.exempt = exempt;
    }

    public String getProcessLevel() {
        return processLevel;
    }

    public void setProcessLevel(String processLevel) {
        this.processLevel = processLevel;
    }

    public String getPprismApptStatusCode() {
        return pprismApptStatusCode;
    }

    public void setPprismApptStatusCode(String pprismApptStatusCode) {
        this.pprismApptStatusCode = pprismApptStatusCode;
    }

    public Short getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(Short positionLevel) {
        this.positionLevel = positionLevel;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Collection<AppointmentHistSpecialPays> getAppointmentHistSpecialPaysCollection() {
        return appointmentHistSpecialPaysCollection;
    }

    public void setAppointmentHistSpecialPaysCollection(Collection<AppointmentHistSpecialPays> appointmentHistSpecialPaysCollection) {
        this.appointmentHistSpecialPaysCollection = appointmentHistSpecialPaysCollection;
    }

    public Appointments getApptIdentifier() {
        return apptIdentifier;
    }

    public void setApptIdentifier(Appointments apptIdentifier) {
        this.apptIdentifier = apptIdentifier;
    }

    public AppointmentStatusCodes getApptStatusCode() {
        return apptStatusCode;
    }

    public void setApptStatusCode(AppointmentStatusCodes apptStatusCode) {
        this.apptStatusCode = apptStatusCode;
    }

    public BargainingUnits getBargainingUnit() {
        return bargainingUnit;
    }

    public void setBargainingUnit(BargainingUnits bargainingUnit) {
        this.bargainingUnit = bargainingUnit;
    }

    public HrmnJobCodes getHjbcdIdentifier() {
        return hjbcdIdentifier;
    }

    public void setHjbcdIdentifier(HrmnJobCodes hjbcdIdentifier) {
        this.hjbcdIdentifier = hjbcdIdentifier;
    }

    public HrmnLocations getLocation() {
        return location;
    }

    public void setLocation(HrmnLocations location) {
        this.location = location;
    }

    public HrmnSupervisorCodes getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(HrmnSupervisorCodes supervisor) {
        this.supervisor = supervisor;
    }

    public RetirementCodes getRetirementCode() {
        return retirementCode;
    }

    public void setRetirementCode(RetirementCodes retirementCode) {
        this.retirementCode = retirementCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aphsIdentifier != null ? aphsIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppointmentHistories)) {
            return false;
        }
        AppointmentHistories other = (AppointmentHistories) object;
        if ((this.aphsIdentifier == null && other.aphsIdentifier != null) || (this.aphsIdentifier != null && !this.aphsIdentifier.equals(other.aphsIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AppointmentHistories[aphsIdentifier=" + aphsIdentifier + "]";
    }

}
