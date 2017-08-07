package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.List;

import com.opensymphony.xwork2.Action;

/**
 * Updates subject employee's appointment in session. 
 * 
 * @author chaudharym
 *
 */
public class EmployeeAppointmentSelectionAction extends BaseAction implements Action{

	private static final long serialVersionUID = 8758904865387321521L;
	
	private int apptId;

	private AppointmentDSP appointmentService;
	
	@Override
	public void prepare() {
		appointmentService = new AppointmentDSP(entityManager);
	}

	@Override
	public String execute() throws Exception {
		updateAppointmentInfoInSession();
		
		return IConstants.SUCCESS;
	}

	private void updateAppointmentInfoInSession() {
		List<AppointmentsBean> appts = (List<AppointmentsBean>)session.get(IConstants.MULTIPLE_APPTS_FOR_EXPENSE);
		
		long userSelectedApptId = new Long(apptId);
		
		for(AppointmentsBean appt : appts){
			if(appt.getAppt_identifier() == userSelectedApptId){
				// update SUBJECT in session
				UserSubject subject =  getUserSubject();
				subject.setAppointmentId(apptId);
				subject.setEmployeeId(new Long(appt.getEmp_identifier()).intValue());
				subject.setPositionId(appt.getPosition_id());
				subject.setDepartment(appt.getDepartment());
				subject.setAgency(appt.getAgency());
				subject.setTku(appt.getTku());
				subject.setAppointmentStart(appt.getStart_date());
				subject.setAppointmentEnd(appt.getEnd_date());
				subject.setSingleAppointmentChosen(true);
				
				// remove the multiple appts from session as soon the selection has been effected.
				synchronized (session) {
					session.remove(IConstants.MULTIPLE_APPTS_FOR_EXPENSE);
				}
			}
		}
	}
	
	public int getApptId() {
		return apptId;
	}

	public void setApptId(int apptId) {
		this.apptId = apptId;
	}
	
}
