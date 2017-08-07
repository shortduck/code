package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class representing Appointment history information.
 *
 * @author chaudharyM
 */
@Entity
@Table(name = "APPOINTMENT_HISTORIES")
public class AppointmentHistory implements Serializable {

	private static final long serialVersionUID = 765084089386848696L;

	@Id
    @Column(name = "APHS_IDENTIFIER")
    private Integer apptHistoryId;

    @Column(name = "APPT_IDENTIFIER")
    private Integer appointmentId;
	
    @Column(name = "START_DATE")
    private Date startDate;
    
    @Column(name = "END_DATE")
    private Date endDate;
    
    private String department;
    private String agency;
    private String tku;
    
	@Column(name = "APPT_STATUS_CODE")
	private String apptStatusCode;
	
	@Column(name = "DEPARTURE_DATE")
	private Date departureDate;
	
	@Column(name = "APPOINTMENT_DATE")
	private Date apptDate;
	
	@Column(name = "DEPARTURE_OR_END_DATE")
	private Date departureOrEndDate;
	
	@Column(name = "POSITION_LEVEL")
	private short positionLevel;

    public AppointmentHistory() {
    }

    public AppointmentHistory(Integer id) {
        this.apptHistoryId = id;
    }

    public AppointmentHistory(Integer id, Integer apptId, Date startDate, Date endDate, String department, String agency, String tku) {
        this.apptHistoryId = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.agency = agency;
        this.tku = tku;
        this.appointmentId = apptId;
    }

	public Integer getApptHistoryId() {
		return apptHistoryId;
	}

	public void setApptHistoryId(Integer apptHistoryId) {
		this.apptHistoryId = apptHistoryId;
	}

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
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

	public String getApptStatusCode() {
		return apptStatusCode;
	}

	public void setApptStatusCode(String apptStatusCode) {
		this.apptStatusCode = apptStatusCode;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getApptDate() {
		return apptDate;
	}

	public void setApptDate(Date apptDate) {
		this.apptDate = apptDate;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (apptHistoryId != null ? apptHistoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppointmentHistory)) {
            return false;
        }
        AppointmentHistory other = (AppointmentHistory) object;
        if ((this.apptHistoryId == null && other.apptHistoryId != null) || (this.apptHistoryId != null && !this.apptHistoryId.equals(other.apptHistoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AppointmentHistory[id=" + apptHistoryId + "]";
    }

	public void setDepartureOrEndDate(Date departureOrEndDate) {
		this.departureOrEndDate = departureOrEndDate;
	}

	public Date getDepartureOrEndDate() {
		return departureOrEndDate;
	}

	public void setPositionLevel(short positionLevel) {
		this.positionLevel = positionLevel;
	}

	public short getPositionLevel() {
		return positionLevel;
	}


}
