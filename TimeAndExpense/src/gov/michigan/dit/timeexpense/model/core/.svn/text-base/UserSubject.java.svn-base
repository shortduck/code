package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

/**
 * Class to store information about the subject(user) that a manager/statewide
 * employee has chosen to work with.
 * 
 * @author chaudharym
 */
public class UserSubject implements Serializable {

	private static final long serialVersionUID = -6781140096130352544L;

	private String department;
	private String agency;
	private String tku;
	private int employeeId;
	private int appointmentId;
	private int apptHistoryId;
	private String positionId;
	private Date appointmentStart;
	private Date appointmentEnd;
	private Date appointmentDate;

	private boolean singleAppointmentChosen;
	
	public UserSubject() {}
	
	public UserSubject(int employeeId, int appointmentId, 
			Date appointmentStart, Date appointmentEnd,
			String department, String agency, String tku,
			String positionId) {
		this.agency = agency;
		this.appointmentEnd = appointmentEnd;
		this.appointmentId = appointmentId;
		this.appointmentStart = appointmentStart;
		this.department = department;
		this.employeeId = employeeId;
		this.positionId = positionId;
		this.tku = tku;
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
	public int getApptHistoryId() {
		return apptHistoryId;
	}

	public void setApptHistoryId(int apptHistoryId) {
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

	public boolean isSingleAppointmentChosen() {
		return singleAppointmentChosen;
	}
	public void setSingleAppointmentChosen(boolean singleAppointmentChosen) {
		this.singleAppointmentChosen = singleAppointmentChosen;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
}
