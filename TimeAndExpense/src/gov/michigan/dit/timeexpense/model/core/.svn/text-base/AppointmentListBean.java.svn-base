package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Class to capture information for an appointment and related employee information.
 * 
 * @author chaudharym
 * 
 */
@Entity
@Table(name = "V_APPT_EMP_PH")
public class AppointmentListBean implements Serializable {
	private static final long serialVersionUID = -2786513622950497035L;

	@Id
	@Column(name = "APHS_IDENTIFIER")
	private Integer apptHistoryId;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "MIDDLE_NAME")
	private String middleName;
	
	@Column(name = "NAME_SUFFIX")
	private String nameSuffix;
	
	private String department;
	private String agency;
	private String tku;
	
	@Column(name = "POSITION_ID")
	private String positionId;
	
	@Column(name = "EMP_IDENTIFIER")
	private int employeeId;
	
	@Column(name = "APPT_IDENTIFIER")
	private int appointmentId;
	
	@Column(name = "APPOINTMENT_START")
	private Date appointmentStart;
	
	@Column(name = "APPOINTMENT_END")
	private Date appointmentEnd;
	
	@Column(name = "APPT_STATUS_CODE")
	private String apptStatusCode;
	
	@Column(name = "DEPARTURE_DATE")
	private Date departureDate;
	
	@Column(name = "APPOINTMENT_DATE")
	private Date appointmentDate;
	
	@Column(name = "DEPARTURE_OR_END_DATE")
	private Date departureOrEndDate;
	
	/**
	 * Maintains status if atleast one underlying transaction for the appointment exists.
	 */
	@Transient
	private boolean transactionPresent;
	
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

	public AppointmentListBean() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
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

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
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

	public void setApptStatusCode(String apptStatusCode) {
	    this.apptStatusCode = apptStatusCode;
	}

	public String getApptStatusCode() {
	    return apptStatusCode;
	}

	public void setApptHistoryId(Integer apptHistoryId) {
	    this.apptHistoryId = apptHistoryId;
	}

	public Integer getApptHistoryId() {
	    return apptHistoryId;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public boolean isTransactionPresent() {
		return transactionPresent;
	}

	public void setTransactionPresent(boolean transactionPresent) {
		this.transactionPresent = transactionPresent;
	}

	public void setAppointmentDepartureDate(Date appointmentDepartureDate) {
		this.departureOrEndDate = appointmentDepartureDate;
	}

	public Date getAppointmentDepartureDate() {
		return departureOrEndDate;
	}
}
