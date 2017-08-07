package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.display.ApprovalTransactionBean;
import gov.michigan.dit.timeexpense.service.ApproveDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author chiduras
 *
 */
public class StateWideApproveAction extends AppointmentSearchAction {
    
    private static final long serialVersionUID = 201612005325239861L;

    private static Logger logger = Logger.getLogger(StateWideApproveAction.class);

    private ApproveDSP approveService;
    private boolean searchButtonClicked = false;
  
    @Override
    public void prepare() {
	approveService =new ApproveDSP(entityManager);
	
    }
    
    /** (non-Javadoc)
     * @see gov.michigan.dit.timeexpense.action.AppointmentSearchAction#execute()
     */
    @Override
	public String execute() throws Exception {
    	Map<String, Map<String, Object>> userContext = (Map<String, Map<String, Object>>)session.get(IConstants.USER_CONTEXT);
    	if (userContext == null){
    		if (!searchButtonClicked){
    			return IConstants.SUCCESS;
    		}
    	}
    	saveUserSelectionInSession(getChosenDepartment(), getChosenAgency(), getChosenTku());
    	
		String deptCode = extractCode(getChosenDepartment());
		String agencyCode = extractCode(getChosenAgency());
		String tkuCode = extractCode(getChosenTku());
		
		
		List<ApprovalTransactionBean> approvalList;
		
		approvalList = approveService.getAllStateWideAdvancesExpensesAwaitingApproval
		(deptCode, agencyCode, tkuCode, getLastname(), IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED, getLoggedInUser().getUserId(), getEmpId());
		
		this.removeLoggedInUserRecordsFromList(approvalList);
		this.setupDisplay(approvalList);		
		
		setJsonResponse(jsonParser.toJson(approvalList));
		
		return IConstants.SUCCESS;
	}

	/**
	 * prepares list for display by adding trailing zeros to the amount fields
	 * @param ApprovalTransactionBean
	 */
	private void setupDisplay (List<ApprovalTransactionBean> approvalList){
	    for (ApprovalTransactionBean item: approvalList){
			// setup amount fields to 2 digits for display
		        item.setDollarAmountForDisplay(TimeAndExpenseUtil.displayAmountTwoDigits(item.getDollarAmount()));
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
	
	
    public void setApproveService(ApproveDSP approveService) {
	this.approveService = approveService;
    }

    public ApproveDSP getApproveService() {
	return approveService;
    }

	public boolean isSearchButtonClicked() {
		return searchButtonClicked;
	}

	public void setSearchButtonClicked(boolean searchButtonClicked) {
		this.searchButtonClicked = searchButtonClicked;
	}
}
