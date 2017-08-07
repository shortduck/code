/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.display;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;


/**
 *
 * @author chiduras
 */
public class EmployeeGeneralInformation implements Serializable {

	private static final long serialVersionUID = -3412511457176132777L;

	private String employeeName;
    private String ssn;
    private String department;
    private String agency;
    private String tku;
    private String tkuName;
    private Date fmlaExpirationDate;
    private Date phStartDate;
    private Date phEndDate;
    private int apptIdentifier;
    private Date moveDate;
    private String apptType;
    private String apptMethod;
    private String apptDuration;
    private Date appointmentDate;
    private Date ahStartDate;
    private Date ahEndDate;
    private Date departureDate;
    private String onCallCode;
    private String plan;
    private double planHours;
    private Date planExpirationDate;
    private String positionNumber;
    private String positionId;
    private String classType;
    private String positionType;
    private String positionSchedule;
    private String positionDuration;
    private String classCode;
    private String workSite;
    private String workCounty;
    private String pcel;
    private String flsaCode;
    private Date flsaExpirationDate;
    private double standardHoursRegular;
    private double standardHoursShift2;
    private double standardHoursShift3;
    private String salaryClass;
    private String jobCode;
    private String departmentCode;
    private String exempt;
    private int employeeIdentifier;
    private String hrmnDeptName;
    private String apscDescription;    
    private Integer supervisorEmployeeId;
    private String supervisorName;
    private String apptStatusCode;
    private String bargainingUnit;
    private String retirementCode;
    private int averageHrs;
    
    public EmployeeGeneralInformation() {
	
    }
      


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
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

    public String getTkuName() {
        return tkuName;
    }

    public void setTkuName(String tkuName) {
        this.tkuName = tkuName;
    }

    public Date getFmlaExpirationDate() {
        return fmlaExpirationDate;
    }

    public void setFmlaExpirationDate(Date fmlaExpirationDate) {
        this.fmlaExpirationDate = fmlaExpirationDate;
    }

    public Date getPhStartDate() {
        return phStartDate;
    }

    public void setPhStartDate(Date phStartDate) {
        this.phStartDate = phStartDate;
    }

    public Date getPhEndDate() {
        return phEndDate;
    }

    public void setPhEndDate(Date phEndDate) {
        this.phEndDate = phEndDate;
    }

    public int getApptIdentifier() {
        return apptIdentifier;
    }

    public void setApptIdentifier(int apptIdentifier) {
        this.apptIdentifier = apptIdentifier;
    }

    public Date getMoveDate() {
        return moveDate;
    }

    public void setMoveDate(Date moveDate) {
        this.moveDate = moveDate;
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

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getAhStartDate() {
        return ahStartDate;
    }

    public void setAhStartDate(Date ahStartDate) {
        this.ahStartDate = ahStartDate;
    }

    public Date getAhEndDate() {
        return ahEndDate;
    }

    public void setAhEndDate(Date ahEndDate) {
        this.ahEndDate = ahEndDate;
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

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public double getPlanHours() {
        return planHours;
    }

    public void setPlanHours(double planHours) {
        this.planHours = planHours;
    }

    public Date getPlanExpirationDate() {
        return planExpirationDate;
    }

    public void setPlanExpirationDate(Date planExpirationDate) {
        this.planExpirationDate = planExpirationDate;
    }

    public String getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(String positionNumber) {
        this.positionNumber = positionNumber;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
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

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
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

    public String getPcel() {
        return pcel;
    }

    public void setPcel(String pcel) {
        this.pcel = pcel;
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

    public double getStandardHoursRegular() {
        return standardHoursRegular;
    }

    public void setStandardHoursRegular(double standardHoursRegular) {
        this.standardHoursRegular = standardHoursRegular;
    }

    public double getStandardHoursShift2() {
        return standardHoursShift2;
    }

    public void setStandardHoursShift2(double standardHoursShift2) {
        this.standardHoursShift2 = standardHoursShift2;
    }

    public double getStandardHoursShift3() {
        return standardHoursShift3;
    }

    public void setStandardHoursShift3(double standardHoursShift3) {
        this.standardHoursShift3 = standardHoursShift3;
    }

    public String getSalaryClass() {
        return salaryClass;
    }

    public void setSalaryClass(String salaryClass) {
        this.salaryClass = salaryClass;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

 
    public String getExempt() {
        return exempt;
    }

    public void setExempt(String exempt) {
        this.exempt = exempt;
    }

    public int getEmployeeIdentifier() {
        return employeeIdentifier;
    }

    public void setEmployeeIdentifier(int employeeIdentifier) {
        this.employeeIdentifier = employeeIdentifier;
    }

    public String getHrmnDeptName() {
        return hrmnDeptName;
    }

    public void setHrmnDeptName(String hrmnDeptName) {
        this.hrmnDeptName = hrmnDeptName;
    }

   

    public Integer getSupervisorEmployeeId() {
        return supervisorEmployeeId;
    }

    public void setSupervisorEmployeeId(Integer supervisorEmployeeId) {
        this.supervisorEmployeeId = supervisorEmployeeId;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getApptStatusCode() {
        return apptStatusCode;
    }

    public void setApptStatusCode(String apptStatusCode) {
        this.apptStatusCode = apptStatusCode;
    }

    public String getBargainingUnit() {
        return bargainingUnit;
    }

    public void setBargainingUnit(String bargainingUnit) {
        this.bargainingUnit = bargainingUnit;
    }

    public int getAverageHrs() {
        return averageHrs;
    }

    public void setAverageHrs(int averageHrs) {
        this.averageHrs = averageHrs;
    }

    public void setApscDescription(String apscDescription) {
	this.apscDescription = apscDescription;
    }

    public String getApscDescription() {
	return apscDescription;
    }

    public void setRetirementCode(String retirementCode) {
	this.retirementCode = retirementCode;
    }

    public String getRetirementCode() {
	return retirementCode;
    }

    public void setDepartmentCode(String departmentCode) {
	this.departmentCode = departmentCode;
    }

    public String getDepartmentCode() {
	return departmentCode;
    }

   
   
}
