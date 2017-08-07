/**
 * Approval queue features
 */

package gov.michigan.dit.timeexpense.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gov.michigan.dit.timeexpense.model.display.ApprovalTransactionBean;
import gov.michigan.dit.timeexpense.util.IConstants;

/**
 * Action class to just touch the session and do nothing else
 * 
 */
@SuppressWarnings("serial")
public class ApprovalQueueAction extends AbstractAction {
	private String requestParams;
    private String department;
	private String agency;
	private String tku;
	private int empIdentifier;
	private int apptIdentifier;
	private long masterId;
	private long requestId;
    private String transactionType;
    private String approveExpense; 

    /**
     * Add an item to the approval queue
     */
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		List<ApprovalTransactionBean> currentQueue = (List<ApprovalTransactionBean>) session.get("APPROVAL_QUEUE");
		if (currentQueue == null){
			currentQueue = new ArrayList<ApprovalTransactionBean>();
		}
		
		ApprovalTransactionBean item = new ApprovalTransactionBean();
		item.setTransactionType(transactionType);
		item.setRequestId(requestId);
		item.setMasterId(masterId);
		item.setDepartment(department);
		item.setAgency(agency);
		item.setTku(tku);
		item.setApptIdentifier(apptIdentifier);
		item.setEmpIdentifier(empIdentifier);
	
		currentQueue.add(item);
		session.put("APPROVAL_QUEUE", currentQueue);
	
		return IConstants.SUCCESS;
	}
	
	/**
	 * Fetch and return the next item in the approval queue
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getNextApprovalTransaction() throws Exception {
		String actionValue="";

		List<ApprovalTransactionBean> currentQueue = (List<ApprovalTransactionBean>) session.get("APPROVAL_QUEUE");
	
		Map<String, String[]> paramMap = getRequest().getParameterMap();
		if (paramMap.containsKey("skip"))
			actionValue = paramMap.get("skip")[0];
		if (currentQueue != null && currentQueue.size() > 0) {
		for (ApprovalTransactionBean item1 : currentQueue) {
			if (currentQueue.size() > 1 && (item1.getRequestId() == requestId) ) {
				if ("skip".equalsIgnoreCase(actionValue)) {
					ApprovalTransactionBean backUp = item1;
					currentQueue.remove(item1);
					currentQueue.add(backUp);
					break;

				} else {//if the user clicks other than skip button then it gets removed from the queue
					currentQueue.remove(item1);
					session.remove(IConstants.LAST_OPERATION);
					break;
				}
				//If the  queue size is 1 then it gets removed from the queue
			} else if (currentQueue.size() == 1) {
				currentQueue.remove(item1);
				session.remove(IConstants.LAST_OPERATION);
				break;
			}
		}
		}
		if (currentQueue != null && currentQueue.size() > 0) {
			ApprovalTransactionBean item = (ApprovalTransactionBean) currentQueue
					.get(0);
			setJsonResponse(jsonParser.toJson(item));

		}

		return IConstants.SUCCESS;
	}
	
	/**
	 * Remove an item from the approval queue
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String removeApprovalTransaction() throws Exception {
		List<ApprovalTransactionBean> currentQueue = (List<ApprovalTransactionBean>) session.get("APPROVAL_QUEUE");
		
		if (currentQueue != null){
			Map<String, String[]> paramMap = getRequest().getParameterMap();
			if (paramMap.containsKey("requestId")){
				String requestId = (String) paramMap.get("requestId")[0];
				for (ApprovalTransactionBean item: currentQueue){
					if (item.getRequestId() == new Long(requestId)){
						currentQueue.remove(item);
						setJsonResponse("SUCCESS");
						break;
					}
				}
			}
		}
		 setJsonResponse("");
	
		return IConstants.SUCCESS;
	}
	
	/**
	 * Remove an item from the approval queue
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String clearApprovalQueue() throws Exception {
		List<ApprovalTransactionBean> currentQueue = (List<ApprovalTransactionBean>) session.get("APPROVAL_QUEUE");		
		if (currentQueue != null && !currentQueue.isEmpty()){
			// clear approval queue
			currentQueue.clear();
		}		
		// clear last action
		if(session.containsKey(IConstants.LAST_OPERATION)){
			synchronized (session) {
				session.remove(IConstants.LAST_OPERATION);
			}
		}
		 setJsonResponse("");
	
		return IConstants.SUCCESS;
	}

	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
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

	public int getEmpIdentifier() {
		return empIdentifier;
	}

	public void setEmpIdentifier(int empIdentifier) {
		this.empIdentifier = empIdentifier;
	}

	public int getApptIdentifier() {
		return apptIdentifier;
	}

	public void setApptIdentifier(int apptIdentifier) {
		this.apptIdentifier = apptIdentifier;
	}

	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public String getApproveExpense() {
		return approveExpense; 
	}

	public void setApproveExpense(String approveExpense ) {
		this.approveExpense  = approveExpense; 
	}


}
