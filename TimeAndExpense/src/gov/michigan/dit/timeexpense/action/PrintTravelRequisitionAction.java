package gov.michigan.dit.timeexpense.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.michigan.dit.timeexpense.model.core.Department;
import gov.michigan.dit.timeexpense.model.core.StateAuthCodes;
import gov.michigan.dit.timeexpense.model.core.TravelReqActions;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetails;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.TravelReqOutOfState;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.EmployeeGeneralInformation;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.TravelRequisitionReportDisplayBean;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.PersonnelHistoriesDSP;
import gov.michigan.dit.timeexpense.service.TravelRequisitionDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

public class PrintTravelRequisitionAction extends AbstractAction implements ServletRequestAware {
	private static final long serialVersionUID = 1831518973738159840L;

    private AppointmentDSP apptService;
    private AdvanceDSP advanceService;
    private PersonnelHistoriesDSP personnelHistoriesService;
    private HttpServletRequest request;
    private Date selectedDate;
    public List<StateAuthCodes> authCodes;
	private TravelRequisitionDSP treqService;
	private CommonDSP commonDsp;
  
    
    public void prepare() {
    	this.apptService = new AppointmentDSP(entityManager);
    	advanceService = new AdvanceDSP(entityManager);
    	personnelHistoriesService = new PersonnelHistoriesDSP(entityManager);
    	treqService = new TravelRequisitionDSP(entityManager);
    	this.commonDsp=new CommonDSP(entityManager);
    	
    }

    public String execute() throws Exception {    	
    	TravelReqMasters master = (TravelReqMasters) session.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);
    	List<TravelReqOutOfState> outOfStateAuthDesc=master.getTravelReqOutOfStateCollection();//cn
    	StringBuilder buffer = new StringBuilder();
    	if(master == null) return IConstants.FAILURE;
    	  	
    	master = entityManager.merge(master);
    	TravelReqDetails detail = master.getTravelReqDetailsCollection().get(0);
    	TravelRequisitionReportDisplayBean display = new TravelRequisitionReportDisplayBean();
    	EmployeeHeaderBean empInfo = (EmployeeHeaderBean) session.get(IConstants.EMP_HEADER_INFO);
    	display.setName(empInfo.getEmpName());
    
    	authCodes = treqService.getAuthorizationCodes();
	    	 for(TravelReqOutOfState treqOutOfState:outOfStateAuthDesc){
	    		 int stacIdentifierVal=treqOutOfState.getStacIdentifier();
	         	 for(StateAuthCodes stateCode:authCodes){
					if(stateCode.getStacIdentifier() == stacIdentifierVal ){
						buffer.append(stateCode.getDescription());
					    buffer.append("; \n");
					    break;
					}
				}
			}
	    display.setOutOfStateAuthCodes(buffer.toString());
    	display.setTreqIdentifier(master.getTreqeIdentifier().getTreqeIdentifier().toString());
    	display.setVersionNumber( String.valueOf(master.getRevisionNumber()));
    	
    	String deptName= commonDsp.getFacsAgencyName(getUserSubject().getDepartment(), getUserSubject().getAgency(), master.getTreqDateFrom(), master.getTreqDateTo());
		if("Y".equals(master.getOutOfStateInd())){
    		display.setDepartmentName(deptName+"\n"+"REQUEST FOR OUT OF STATE TRAVEL AUTHORIZATION");
    	}else{
    		display.setDepartmentName(deptName+"\n"+"REQUEST FOR TRAVEL");
    	}

    	
    	display.setOfficeProgram(master.getOfficeProgram());
    	display.setComments(master.getComments());
    	display.setRequestDate(TimeAndExpenseUtil.displayDate(master.getTreqDateRequest()));
    	display.setDesitination(master.getDestination());
    	display.setTransportationVia(master.getTransportationVia());
    	display.setTransportationVia(master.getTransportationVia());
    	display.setOfficeProgram(master.getOfficeProgram());
    	display.setTravelDates(TimeAndExpenseUtil.displayDate(master.getTreqDateFrom()) + " - " +
    			TimeAndExpenseUtil.displayDate(master.getTreqDateTo()));
    	display.setEmpIdentifier(Integer.toString(empInfo.getEmpId()));
    	display.setNatureOfBusiness(master.getNatureOfBusiness());
    	if (detail.getTransportationAmount() > 0)
    		display.setTransportationAmount(TimeAndExpenseUtil.displayAmountTwoDigits(detail.getTransportationAmount()));
    	if (detail.getAirfareAmount() > 0)
    		display.setAirFareAmount(TimeAndExpenseUtil.displayAmountTwoDigits(detail.getAirfareAmount()));
    	if (detail.getLodgingAmount() > 0)
    	    display.setLodgingAmount(TimeAndExpenseUtil.displayAmountTwoDigits(detail.getLodgingAmount()));
    	if (detail.getMealsAmount() > 0)
    		display.setMealsAmount(TimeAndExpenseUtil.displayAmountTwoDigits(detail.getMealsAmount()));
    	if (detail.getRegistAmount() > 0)
    		display.setRegistAmount(TimeAndExpenseUtil.displayAmountTwoDigits(detail.getRegistAmount()));
    	if (detail.getOtherAmount() > 0)
    	display.setOtherAmount(TimeAndExpenseUtil.displayAmountTwoDigits(detail.getOtherAmount()));
    	Double totalAmount = detail.getTransportationAmount()+detail.getAirfareAmount() + detail.getLodgingAmount() 
    					+ detail.getMealsAmount() + detail.getRegistAmount() + detail.getOtherAmount();
    	String total = TimeAndExpenseUtil.displayAmountTwoDigits(totalAmount);
    	
    	display.setTotalAmount("$" + total);
    	double advanceAmount = 0.0;
    	if (master.getTreqeIdentifier().getAdevIdentifier() != null){
    		
    		advanceAmount = advanceService.getAmountOutstandingByEventId(master.getTreqeIdentifier().getAdevIdentifier());
    		display.setAdvanceAmount("An advance of $" + TimeAndExpenseUtil.displayAmountTwoDigits(advanceAmount) + " has been requested for the employee");
    	}
        
    	TravelReqActions affw = master.findTravelReqActions(IConstants.APPROVAL_STEP5);
    	if (affw != null) {
    		String name = personnelHistoriesService.getEmployeeNameForUserId(affw.getModifiedUserId());
    		display.setApprovalName(name);
    	}
    	TravelReqActions aosw = master.findTravelReqActions("AOSW");
    	if (aosw != null) {
    		String name = personnelHistoriesService.getEmployeeNameForUserId(aosw.getModifiedUserId());
    		display.setOutOfStateApprovalName(name);
    	}

    	request.setAttribute("DISPLAY", display);

    	return IConstants.SUCCESS;
    }

     public void setServletRequest(HttpServletRequest request) {
    	this.request = request;
    }
   
}
