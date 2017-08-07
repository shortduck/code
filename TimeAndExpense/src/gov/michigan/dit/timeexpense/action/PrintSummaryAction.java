package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * This is the Action class which handles the Print Summary Report.
 * 
 * @author chiduras
 * @author chaudharym
 */
public class PrintSummaryAction extends AbstractAction implements ServletRequestAware {
	private static final long serialVersionUID = 6092321006141575712L;

    private AppointmentDSP apptService;

    private HttpServletRequest request;

    public void prepare() {
    	apptService = new AppointmentDSP(entityManager);
    }

    public String execute() throws Exception {
    	UserSubject subject = getUserSubject();
    	String tkuName = apptService.findTkuName(subject.getDepartment(), subject.getAgency(), subject.getTku());
    	
    	// As report needs details and CB lists, associate detail and coding block lists if not already fetched from DB
    	ExpenseMasters expense = (ExpenseMasters) session.get(IConstants.EXPENSE_SESSION_DATA);
    	if(expense == null) return IConstants.FAILURE;
    	
    	request.setAttribute("TKU", subject.getTku());
    	request.setAttribute("TKU_NAME", tkuName);
    	
    	return IConstants.SUCCESS;
    }

    public void setServletRequest(HttpServletRequest request) {
    	this.request = request;
    }

}
