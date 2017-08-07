/**
 * 
 */
package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.ApprovalTransactionBean;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.List;

/**
 *  Action to capture any forwarding requests from Approve list page
 * action. This action is responsible for setting the state 
 * information associated to the selected appointment for an employee.
 * 
 * Employee and the selected appointment related information is added
 * to the session and then the request dispatched to another action
 * based on the application flow.
 * @author chiduras
 *
 */
public class ApproveReferrerAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -130065387614843595L;

    private String department;
	private String agency;
	private String tku;
	private int empIdentifier;
	private int apptIdentifier;
	private long requestId;
	private long masterId;
       private String transactionType;
       private AppointmentDSP appointmentService;
       private EmployeeHeaderBean empInfo;
	
	
     public void prepare(){
	 appointmentService = new AppointmentDSP(entityManager);
     }
	
	@Override
	public String execute() throws Exception {
		String result = "";
		
		UserSubject subject = new UserSubject(empIdentifier, apptIdentifier,
									null, null,department, agency, tku, null);

		setupApptDates(subject);
		session.put(IConstants.USER_SUBJECT_SESSION_KEY_NAME,subject); 
		
		List<EmployeeHeaderBean> empInfoList = null;
		UserProfile profile = (UserProfile) session.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
		
		// find emp info
		if(subject == null){
			//Employee Only access
				empInfoList = appointmentService.getEmployeeHeaderInfoByEmpId((int)profile.getEmpIdentifier());
		}
		else{
			// supervisor access
			empInfoList = appointmentService.getEmployeeHeaderInfoByEmpId(subject.getEmployeeId());
		}
		
		if (!empInfoList.isEmpty()) {
			session.put(IConstants.EMP_HEADER_INFO, empInfoList.get(0));
			setEmpInfo(empInfoList.get(0));
		} else {
			// no emp info could located, remove existing emp info from session if there is any?
			session.remove(IConstants.EMP_HEADER_INFO);
		}

		if (transactionType.equalsIgnoreCase("Advance")){
			result = "success_advance";
		}else if (transactionType.equalsIgnoreCase("Expense")) {
			result = "success_expense";
		} else {
			result = "success_travel_requisition";
		}
		
		//removeFromApprovalQueue();
		
		return result;
	}
	
	private void setupApptDates(UserSubject subject) {
		AppointmentsBean apptBean = appointmentService.findActiveAppointmentDateSpan(apptIdentifier);
		
		if(apptBean.getDepartureDate() != null) subject.setAppointmentEnd(apptBean.getDepartureDate());
		else if(apptBean.getEnd_date() != null) subject.setAppointmentEnd(apptBean.getEnd_date());
		
		if(apptBean.getAppointment_date() != null) {
			subject.setAppointmentStart(apptBean.getAppointment_date());
			subject.setAppointmentDate(apptBean.getAppointment_date());
		}
	}
	/**
	 * Remove an item from approval queue if present
	 */
	@SuppressWarnings("unchecked")
	private void removeFromApprovalQueue(){
		List<ApprovalTransactionBean> apprList = (List<ApprovalTransactionBean>) session.get("APPROVAL_QUEUE");
		if (apprList == null)
			return;
		for (ApprovalTransactionBean item: apprList){
			if (item.getRequestId() == requestId){
				apprList.remove(item);
				break;
			}
		}
	}
	
	public void setDepartment(String department) {
	    this.department = department;
	}
	public String getDepartment() {
	    return department;
	}
	public void setAgency(String agency) {
	    this.agency = agency;
	}
	public String getAgency() {
	    return agency;
	}
	public void setTku(String tku) {
	    this.tku = tku;
	}
	public String getTku() {
	    return tku;
	}
	

	public void setRequestId(long requestId) {
	    this.requestId = requestId;
	}

	public long getRequestId() {
	    return requestId;
	}

	public void setMasterId(long masterId) {
	    this.masterId = masterId;
	}

	public long getMasterId() {
	    return masterId;
	}

	public void setEmpIdentifier(int empIdentifier) {
	    this.empIdentifier = empIdentifier;
	}

	public int getEmpIdentifier() {
	    return empIdentifier;
	}

	public void setApptIdentifier(int apptIdentifier) {
	    this.apptIdentifier = apptIdentifier;
	}

	public int getApptIdentifier() {
	    return apptIdentifier;
	}

	public void setTransactionType(String transactionType) {
	    this.transactionType = transactionType;
	}

	public String getTransactionType() {
	    return transactionType;
	}

	 public AppointmentDSP getApptService() {
	        return appointmentService;
	    }



	    public void setApptService(AppointmentDSP apptService) {
	        this.appointmentService = apptService;
	    }

	    public void setEmpInfo(EmployeeHeaderBean empInfo) {
		this.empInfo = empInfo;
	    }

	    public EmployeeHeaderBean getEmpInfo() {
		return empInfo;
	    }

	

}
