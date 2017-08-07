package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.Date;

/**
 * Action to capture any forwarding requests from select employee
 * action. This action is responsible for setting the state 
 * information associated to the selected appointment for an employee.
 * 
 * Employee and the selected appointment related information is added
 * to the session and then the request dispatched to another action
 * based on the application flow.
 * 
 * @author chaudharym
 */
public class SelectEmployeeReferrerAction extends BaseAction {

	private static final long serialVersionUID = -6669192300531143575L;

	private String department;
	private String agency;
	private String tku;
	private int employeeId;
	private int appointmentId;
	private Integer apptHistoryId;
	private String positionId;
	private Date appointmentStart;
	private Date appointmentEnd;
	private Date departureDate;
	
	private AppointmentDSP appointmentService;
	
	public void prepare(){
		appointmentService = new AppointmentDSP(entityManager);
	}
	
	@Override
	public String execute() throws Exception {
		String result = "";
		
		UserSubject subject = new UserSubject(employeeId, appointmentId,appointmentStart, appointmentEnd,
				department, agency, tku, positionId);
		if(departureDate != null) subject.setAppointmentEnd(departureDate);
		subject.setApptHistoryId(apptHistoryId);
		
		// push correct appt dates in subject
		setupApptDates(subject);
		
		session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME, subject);
		
		if(getModuleIdFromSession().equals(IConstants.ADVANCE_MANAGER) || getModuleIdFromSession().equals(IConstants.ADVANCE_STATEWIDE)){
			result = "success_advance";
		}else if (getModuleIdFromSession().equals(IConstants.EXPENSE_MANAGER) || getModuleIdFromSession().equals(IConstants.EXPENSE_STATEWIDE)){
			result = "success_expense";
		} else{
			result = "success_travel_requisition";
		}
		
		return result;
	}

	private void setupApptDates(UserSubject subject) {
		AppointmentsBean apptBean = appointmentService.findActiveAppointmentDateSpan(appointmentId);
		
		if(apptBean.getDepartureDate() != null) subject.setAppointmentEnd(apptBean.getDepartureDate());
		else if(apptBean.getEnd_date() != null) subject.setAppointmentEnd(apptBean.getEnd_date());
		
		if(apptBean.getAppointment_date() != null) subject.setAppointmentDate(apptBean.getAppointment_date());
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

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Integer getApptHistoryId() {
		return apptHistoryId;
	}

	public void setApptHistoryId(Integer apptHistoryId) {
		this.apptHistoryId = apptHistoryId;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public Date getAppointmentStart() {
		return appointmentStart;
	}

	public void setAppointmentStart(Date appointmentStart) {
		this.appointmentStart = appointmentStart;
	}

	public Date getAppointmentEnd() {
		return appointmentEnd;
	}

	public void setAppointmentEnd(Date appointmentEnd) {
		this.appointmentEnd = appointmentEnd;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
}
