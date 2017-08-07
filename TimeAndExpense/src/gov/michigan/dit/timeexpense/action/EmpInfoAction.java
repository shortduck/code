package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.display.EmployeeGeneralInformation;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseProfile;
import gov.michigan.dit.timeexpense.model.display.Location;
import gov.michigan.dit.timeexpense.model.display.StandardDistCoding;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This is the Action class gets employee info sends the response to the JSP.
 * 
 * @author Chiduras
 */

public class EmpInfoAction extends AbstractAction {
    private static final long serialVersionUID = 461295641376587535L;

    private AppointmentDSP appointmentService;
    private EmployeeGeneralInformation employeeInfo;
    private List<ExpenseProfile> profiles;
    private Location homeLocation;
    private Location workLocation;
    private List<StandardDistCoding> standardDistribution;
    private EmployeeHeaderBean empInfo;
    private int appointmentId;  
    private int employeeId;
    private String facsAgency;
    private String userId;
    private Date selectedDate;
    private Date distStartDate;

    public EmpInfoAction() {
	standardDistribution = new ArrayList<StandardDistCoding>();
	profiles = new ArrayList<ExpenseProfile>();
    }

    public void prepare() {
	appointmentService = new AppointmentDSP(entityManager);
    }

    @Override
    public String execute() throws Exception {

	// if apptId OR empId not passed along, do not proceed
	if(appointmentId ==0 || employeeId == 0){
	    return IConstants.FAILURE;
	}
	
	empInfo = null;
	List<EmployeeHeaderBean> empInfoList = null;
	
	if (appointmentId > 0){
		// fetch by appointment
		Date searchDate = (Date) session.get(IConstants.APPT_SEARCH_DATE);
		empInfoList = appointmentService.
		getEmployeeHeaderInfoByApptId(appointmentId, searchDate);
	} else {
		// fetch by employee id
		empInfoList = appointmentService.getEmployeeHeaderInfoByEmpId(employeeId);
	}
	 if (empInfoList != null && !empInfoList.isEmpty()) {
     	empInfo = empInfoList.get(0);
         }
	
	
	Date effectiveDate = null;
	
	if(selectedDate != null)
	    effectiveDate = new Date(selectedDate.getTime());
	else{
	    effectiveDate = appointmentService.getEffectiveDate(appointmentId);
	    
	    // if max system date, then use current date
	    if(effectiveDate.getYear()== 2222-1900 && effectiveDate.getMonth()== 11 && effectiveDate.getDate()==31){ 
		effectiveDate = new Date();
	    }
	}
	
	distStartDate = appointmentService.getDistributionStartDate(effectiveDate);

	setProfiles(appointmentService.getEmployeeExpenseProfiles(appointmentId,effectiveDate));
	
	String moduleIdFromSession = getModuleIdFromSession();
	if (moduleIdFromSession != null && (moduleIdFromSession.equalsIgnoreCase(IConstants.EXPENSE_EMPLOYEE)||moduleIdFromSession.equalsIgnoreCase(IConstants.ADVANCE_EMPLOYEE))) {
		empInfo = (EmployeeHeaderBean) session.get(IConstants.EMP_HEADER_INFO);
		if (appointmentId != empInfo.getApptId()){
			if (!appointmentService.isEmployeeAppointment(employeeId, appointmentId)){
				// if the appointment is not the same as in empinfo header and does not belong to the same employee
				error = ActionHelper.setupError(error, "", "You are not authorized to view this page", false);
				return IConstants.FAILURE;
			}
		}
		setEmployeeInfo(appointmentService.getEmployeeGeneralInfo(appointmentId,effectiveDate));	
	} else {
		setEmployeeInfo(appointmentService.getEmployeeGeneralInfo(appointmentId,effectiveDate,getLoggedInUser().getUserId()));
		if (employeeInfo == null){
			// supervisor does not have security to view this employee's information
			error = ActionHelper.setupError(error, "", "You are not authorized to view this page", false);
			return IConstants.FAILURE;
		}
	}	
	// AI 21418 - moved down after the security check
	setHomeLocation(appointmentService.getEmployeeHomeLocation(employeeId,effectiveDate));
	setWorkLocation(appointmentService.getEmployeeWorkLocation(appointmentId, effectiveDate));
	standardDistribution = appointmentService.getStandardDistribution(appointmentId, effectiveDate, getLoggedInUser().getUserId(), distStartDate);

	setFacsAgency(appointmentService.getFacsAgency(effectiveDate,effectiveDate,appointmentId));
	
	selectedDate = effectiveDate;
	// build and set the json response string
	setJsonResponse(jsonParser.toJson(standardDistribution));

	return IConstants.SUCCESS;

    }
    
    private Date getMaxSystemDate(){
	Calendar cal = Calendar.getInstance();
	cal.set(2222, Calendar.DECEMBER, 31,0,0,0);
	
	return cal.getTime();
    }
    
   
    public AppointmentDSP getAppointmentService() {
	return appointmentService;
    }

    public void setAppointmentService(AppointmentDSP appointmentService) {
	this.appointmentService = appointmentService;
    }

    public EmployeeGeneralInformation getEmployeeInfo() {
	return employeeInfo;
    }

    public void setEmployeeInfo(
	    EmployeeGeneralInformation employeeGeneralInformation) {
	this.employeeInfo = employeeGeneralInformation;
	
    }

    public void setHomeLocation(Location homeLocation) {
	this.homeLocation = homeLocation;
    }

    public Location getHomeLocation() {
	return homeLocation;
    }

    public Location getWorkLocation() {
	return workLocation;
    }

    public void setWorkLocation(Location workLocation) {
	this.workLocation = workLocation;
    }

    public int getAppointmentId() {
	return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
	this.appointmentId = appointmentId;
    }

    public int getEmployeeId() {
	return employeeId;
    }

    public void setEmployeeId(int employeeId) {
	this.employeeId = employeeId;
    }

    public Date getSelectedDate() {
	return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
	this.selectedDate = selectedDate;
    }

    public List<ExpenseProfile> getProfiles() {
	return profiles;
    }

    public void setProfiles(List<ExpenseProfile> profiles) {
	this.profiles = profiles;
    }

    public List<StandardDistCoding> getStandardDistribution() {
	return standardDistribution;
    }

    public void setEmpInfo(EmployeeHeaderBean empInfo) {
	this.empInfo = empInfo;
    }

    public EmployeeHeaderBean getEmpInfo() {
	return empInfo;
    }

	public void setFacsAgency(String facsAgency) {
		this.facsAgency = facsAgency;
	}

	public String getFacsAgency() {
		return facsAgency;
	}
    
    
}
