package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.display.MyEmployeesListBean;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

/**
 *	Finds the appointments for the given search criteria.
 * @author jadcharlas
 */
public class MyEmployeesAction extends BaseAction{
	
	private static final long serialVersionUID = -8381023065799793117L;
	private static Logger logger = Logger.getLogger(MyEmployeesAction.class);
	private Date searchDate;
	private AppointmentDSP appointmentService;
	private String myEmployees;

	
	public void prepare(){
		appointmentService = new AppointmentDSP(entityManager);
	}
	
	@Override
	public String execute() throws Exception {
		List<MyEmployeesListBean> appointments ;
		appointments = appointmentService.findAppointmentsByMyemployees(((int) getLoggedInUser().getEmpIdentifier()),searchDate);
		setJsonResponse(jsonParser.toJson(appointments));
		return "success";
	}

	public Date getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}

	public AppointmentDSP getAppointment(){
		return appointmentService;
	}
	public void setAppointment(AppointmentDSP service){
		appointmentService = service;
	}

	public void setMyEmployees(String myEmployees) {
		this.myEmployees = myEmployees;
	}

	public String getMyEmployees() {
		return myEmployees;
	}
}
