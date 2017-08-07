package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.VAdvApprovalList;
import gov.michigan.dit.timeexpense.model.core.VExpApprovalList;
import gov.michigan.dit.timeexpense.model.core.VTreqApprovalList;
import gov.michigan.dit.timeexpense.model.display.ApprovalTransactionBean;
import gov.michigan.dit.timeexpense.service.ApproveDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

public class ManagerApproveAction extends AbstractAction {

    private static final long serialVersionUID = -8899322697099867948L;

    private static Logger logger = Logger.getLogger(ManagerApproveAction.class);

    private String moduleId;
    private String employeeOption;
    private ApproveDSP approveService;
   
    private String jsonResponseOnLoad;

    @Override
    public void prepare() {
	approveService = new ApproveDSP(entityManager);

    }

    public String execute() throws Exception {

	// push moduleId to session for further reuse
	if (moduleId != null && !"".equals(moduleId.trim())) {
	    session.put(IConstants.LEFT_NAV_CURRENT_MODULE_ID, moduleId);
	}
		
	if (employeeOption == null) {
		employeeOption = (String) session.get("managerApprovalsSelectEmpPref");
		if (session.get("managerApprovalsSelectEmpPref") == null) {
			session.put("managerApprovalsSelectEmpPref", "M");		
		}
		
	} else{
		session.put("managerApprovalsSelectEmpPref", employeeOption);
	}
	if ((employeeOption == null) || (employeeOption.equalsIgnoreCase("M"))) {
// for my employees	    
	    List<VAdvApprovalList> advancesSupervisor = approveService
	    .getAdvancesAwaitingApprovalSupervisorMyEmployees(((int) getLoggedInUser().getEmpIdentifier()), IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);

	    
	  
	    List<ApprovalTransactionBean> approvalList2 = new ArrayList<ApprovalTransactionBean>();

	    if (advancesSupervisor != null){
	    
	    for (Iterator<VAdvApprovalList> iterator = advancesSupervisor
		    .iterator(); iterator.hasNext();) {
		VAdvApprovalList advApprovalList = iterator.next();
		approvalList2.add(populateDisplayBean(advApprovalList));
	    }

	    }
	    List<VExpApprovalList> expensesSupervisor = approveService
		    .getExpensesAwaitingApproval(((int) getLoggedInUser()
			    .getEmpIdentifier()), IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);

	    if (expensesSupervisor != null){
	    
	    for (Iterator<VExpApprovalList> iterator = expensesSupervisor
		    .iterator(); iterator.hasNext();) {
		VExpApprovalList expApprovalList = iterator.next();
		approvalList2.add(populateDisplayBeanExpense(expApprovalList));
	    }
	    }
	    
	    List<VTreqApprovalList> treqsSupervisor = approveService
	    .getTravelRequisitionsAwaitingApproval(((int) getLoggedInUser()
		    .getEmpIdentifier()), IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);
	    

    if (treqsSupervisor != null){
    
    for (Iterator<VTreqApprovalList> iterator = treqsSupervisor
	    .iterator(); iterator.hasNext();) {
    	VTreqApprovalList treqApprovalList = iterator.next();
	approvalList2.add(populateDisplayBeanTravelRequisition(treqApprovalList));
    }

	    }
	    this.removeLoggedInUserRecordsFromList(approvalList2);
	    this.setupDisplay(approvalList2);
	    setJsonResponse(jsonParser.toJson(approvalList2));
	    
	} else {
//for all employees
		List<ApprovalTransactionBean> advances = approveService
		    .getAdvancesAwaitingApprovalSupervisorAllEmployees(
			    getLoggedInUser().getUserId(), IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);
	    List<ApprovalTransactionBean> expenses = approveService
		    .getExpensesAwaitingApprovalAllEmployee(getLoggedInUser()
			    .getUserId(), IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);
	    List<ApprovalTransactionBean> treqs = approveService
	    .getTravelRequisitionAwaitingApprovalAllEmployee(getLoggedInUser()
		    .getUserId(), IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);	    	    

	    List<ApprovalTransactionBean> approvalList = new ArrayList<ApprovalTransactionBean>();
	    approvalList.addAll(advances);
	    approvalList.addAll(expenses);
	    approvalList.addAll(treqs);
	    this.removeLoggedInUserRecordsFromList(approvalList);
	    this.setupDisplay(approvalList);

	    setJsonResponse(jsonParser.toJson(approvalList));
	}


	return IConstants.SUCCESS;
    }

    /** This method populates the ApprovalTransaction Bean for expenses
     * @param expenseApproval
     * @return
     */
    private ApprovalTransactionBean populateDisplayBeanExpense(
        VExpApprovalList expenseApproval) {
	if (expenseApproval == null)
	    return null;
	ApprovalTransactionBean display = new ApprovalTransactionBean();
	display.setAdjIdentifier(expenseApproval.getAdjIdentifier());
	display.setApptIdentifier(expenseApproval.getApptIdentifier());
	display.setDollarAmount(expenseApproval.getAmount());
	display.setEmpIdentifier(expenseApproval.getEmpIdentifier());
	display.setFirstName(expenseApproval.getFirstName());
	display.setLastName(expenseApproval.getLastName());
	display.setMiddleName(expenseApproval.getMiddleName());
	display.setFromDate(expenseApproval.getExpDateFrom());
	display.setToDate(expenseApproval.getExpDateTo());
	display.setTransactionType("Expense");
	display.setMasterId(expenseApproval.getMasterId());
    display.setDepartment(expenseApproval.getDepartment());
    display.setAgency(expenseApproval.getAgency());
    display.setTku(expenseApproval.getTku());
    display.setRequestId(expenseApproval.getExpevIdentifier());
    display.setDeptCode(expenseApproval.getDeptCode());
    display.setLastActionDate(expenseApproval.getLastActionDate());
    return display;

    }

    /** This method populates the ApprovalTransaction Bean for Advances
     * @param advanceApproval
     * @return
     */
    private ApprovalTransactionBean populateDisplayBean(
	    VAdvApprovalList advanceApproval) {
	if (advanceApproval == null)
	    return null;

	ApprovalTransactionBean display = new ApprovalTransactionBean();
	display.setDollarAmount(advanceApproval.getDollarAmount());
	display.setAdjIdentifier(advanceApproval.getAdjIdentifier());
	display.setApptIdentifier(advanceApproval.getApptIdentifier());
	display.setEmpIdentifier(advanceApproval.getEmpIdentifier());
	display.setFirstName(advanceApproval.getFirstName());
	display.setLastName(advanceApproval.getLastName());
	display.setMiddleName(advanceApproval.getMiddleName());
	display.setMasterId(advanceApproval.getMasterId());
	display.setRequestDate(advanceApproval.getRequestDate());
	display.setRequestId(advanceApproval.getRequestId());
	display.setTransactionType("Advance");
	display.setFromDate(advanceApproval.getFromDate());
	display.setToDate(advanceApproval.getToDate());
	display.setMasterId(advanceApproval.getMasterId());
    display.setDepartment(advanceApproval.getDepartment());
	display.setAgency(advanceApproval.getAgency());
	display.setTku(advanceApproval.getTku());
    display.setDeptCode(advanceApproval.getDeptCode());
    display.setLastActionDate(advanceApproval.getLastActionDate());
	return display;
    }
    
    private ApprovalTransactionBean populateDisplayBeanTravelRequisition(
    		VTreqApprovalList expenseApproval) {
    	if (expenseApproval == null)
    	    return null;
    	ApprovalTransactionBean display = new ApprovalTransactionBean();
    	display.setApptIdentifier(expenseApproval.getApptIdentifier());
    	display.setDollarAmount(expenseApproval.getAmount());
    	display.setEmpIdentifier(expenseApproval.getEmpIdentifier());
    	display.setFirstName(expenseApproval.getFirstName());
    	display.setLastName(expenseApproval.getLastName());
    	display.setMiddleName(expenseApproval.getMiddleName());
    	display.setFromDate(expenseApproval.getTreqDateFrom());
    	display.setToDate(expenseApproval.getTreqDateTo());
    	display.setTransactionType("TREQ");
    	display.setMasterId(expenseApproval.getMasterId());
        display.setDepartment(expenseApproval.getDepartment());
        display.setAgency(expenseApproval.getAgency());
        display.setTku(expenseApproval.getTku());
        display.setRequestId(expenseApproval.getTreqeIdentifier());
        display.setDeptCode(expenseApproval.getDeptCode());
        display.setLastActionDate(expenseApproval.getLastActionDate());
        return display;

        }

  
    /**
     * prepares list for display by adding trailing zeros to the amount fields
     * 
     * @param ApprovalTransactionBean
     */
    private void setupDisplay(List<ApprovalTransactionBean> approvalList) {

	for (ApprovalTransactionBean item : approvalList) {
	    // setup amount fields to 2 digits for display
	    item.setDollarAmountForDisplay(TimeAndExpenseUtil
		    .displayAmountTwoDigits(item.getDollarAmount()));
	}

    }
    
    /**
	 * Removes the logged in users records from the list
	 * @param ApprovalTransactionBean
	 */
	private void removeLoggedInUserRecordsFromList (List<ApprovalTransactionBean> approvalList){
		long empIdentifier =  getLoggedInUser().getEmpIdentifier();
		
		if (approvalList !=null){
		    for (Iterator<ApprovalTransactionBean> iterator = approvalList.iterator(); iterator.hasNext();) {
			ApprovalTransactionBean approvalTransactionBean = (ApprovalTransactionBean) iterator.next();
			if (approvalTransactionBean.getEmpIdentifier() == empIdentifier){
			    iterator.remove();
			}
			
		    }
		}
		
			
	}

    public void setEmployeeOption(String employeeOption) {
	this.employeeOption = employeeOption;
    }

    public String getEmployeeOption() {
	return employeeOption;
    }

    public void setJsonResponseOnLoad(String jsonResponseOnLoad) {
	this.jsonResponseOnLoad = jsonResponseOnLoad;
    }

    public String getJsonResponseOnLoad() {
	return jsonResponseOnLoad;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

}
