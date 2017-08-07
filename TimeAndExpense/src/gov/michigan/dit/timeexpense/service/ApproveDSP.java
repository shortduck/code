package gov.michigan.dit.timeexpense.service;
/**
 * @author chiduras
 *
 */

import gov.michigan.dit.timeexpense.dao.ApproveDAO;
import gov.michigan.dit.timeexpense.model.core.VAdvApprovalList;
import gov.michigan.dit.timeexpense.model.core.VExpApprovalList;
import gov.michigan.dit.timeexpense.model.core.VTreqApprovalList;
import gov.michigan.dit.timeexpense.model.display.ApprovalTransactionBean;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

public class ApproveDSP {

    private Logger logger = Logger.getLogger(AdvanceDSP.class);

    // instance variables
    public ApproveDAO approveDao;

    public ApproveDSP(EntityManager entityManager) {
	approveDao = new ApproveDAO();
	approveDao.setEntityManager(entityManager);

    }
    
       
    
    /** Method to merge Advances/Expense lists
     * @param dept
     * @param agency
     * @param tku
     * @param lastName
     * @param requestType
     * @param userId
     * @param empId
     * @return List<ApprovalTransactionBean> 
     */
    public List<ApprovalTransactionBean> getAllStateWideAdvancesExpensesAwaitingApproval(
	    String dept, String agency, String tku, String lastName,
	    String requestType, String userId, String empId) {
		
	
	// initialize dept, agency and tku if NULL
	dept = (dept == null) ? "" : dept;
	agency = (agency == null) ? "" : agency;
	tku = (tku == null) ? "" : tku;
	lastName = (lastName == null) ? "" : lastName;
	
	
	
	List<ApprovalTransactionBean> advances = this.getAllStateWideAdvancesAwaitingApproval
	(dept, agency, tku, lastName, requestType, userId, empId);
	
	List<ApprovalTransactionBean> expenses = this.getAllSwExpensesAwaitingApproval
	(dept, agency, tku, lastName, requestType, userId, empId); 
	
	List<ApprovalTransactionBean> travelRequisitions = this.getAllSwTravelRequisitionsAwaitingApproval
	(dept, agency, tku, lastName, requestType, userId, empId); 
	
	 List<ApprovalTransactionBean> returnList = new ArrayList<ApprovalTransactionBean>();
	 returnList.addAll(advances);
	 returnList.addAll(expenses);
	 returnList.addAll(travelRequisitions);
	
	return returnList;
		
    }
    

    /**
     * This method is used to get advances pending approval for "My employees"
     * working under a supervisor. The second parameter checks whether adjusted
     * advances are to be retrieved or not
     * 
     * @param supervisorEmpId
     * @param requestType
     * @return List<ApprovalTransactionBean> - List of advances awaiting approval
     */

    public List<VAdvApprovalList> getAdvancesAwaitingApprovalSupervisorMyEmployees(
	    int supervisorEmpId, String requestType) {

	if (requestType == null || supervisorEmpId <= 0) {
	    return null;
	}

	if (requestType
		.equals(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED)) {
	    return approveDao.findAdvancesAwaitingApproval(supervisorEmpId);
	} else if (requestType
		.equals(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY)) {
	    return approveDao.findAdjustedAdvancesAwaitingApproval(supervisorEmpId);
	} else if (requestType == IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY) {
	    return approveDao.findNonAdjustedAdvancesAwaitingApproval(supervisorEmpId);
	}
	return null;
    }

    /**
     * Called to display list for all employees for a manager/supervisor
     * 
     * @param userId
     * @param requestType
     * @return List<ApprovalTransactionBean>
     */

    public List<ApprovalTransactionBean> getAdvancesAwaitingApprovalSupervisorAllEmployees(
	    String userId, String requestType) {

	if (requestType == null || userId == null) {
	    return null;
	}

	if (requestType
		.equals(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED)) {
	    return approveDao
		    .findAllAdvancesAwaitingApprovalAllEmployees(userId);
	} /*else if (requestType
		.equals(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY)) {
	    return approveDao
		    .findAdjustedAdvancesAwaitingApprovalAllEmployees(userId);
	} else if (requestType == IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY) {
	    return approveDao
		    .findNonAdjustedAdvancesAwaitingApprovalAllEmployees(userId);
	}*/
	return null;
    }

    /**
     * Retrieves to display pending approval list for employees in a statewide
     * mode of application access. This method is cloned from expenses DSP
     * 
     * @param dept
     * @param agency
     * @param tku
     * @param lastName
     * @param requestType
     * @param userId
     * @param empId
     * @return List<ApprovalTransactionBean>
     */

    public List<ApprovalTransactionBean> getAllStateWideAdvancesAwaitingApproval(
	    String dept, String agency, String tku, String lastName,
	    String requestType, String userId, String empId) {

		List<ApprovalTransactionBean> result = null;
		
		boolean empIdExists = (empId != null && !"".equals(empId.trim())) ? true :  false ;
		boolean lastNameExists = (lastName != null && !"".equals(lastName.trim())) ? true :  false ;
		boolean deptExists = (dept != null && !"".equals(dept.trim())) ? true :  false ;
		boolean agencyExists = (agency != null && !"".equals(agency.trim())) ? true :  false ;
		boolean tkuExists = (tku != null && !"".equals(tku.trim())) ? true :  false ;
		
		if(empIdExists){
			if(tkuExists) result = approveDao.findSwAdvancesAwaitingApprovalEmpIdDeptAgencyTku(userId, empId, dept, agency, tku);
			else if(agencyExists) result = approveDao.findSwAdvancesAwaitingApprovalEmpIdDeptAgency(userId, empId, dept, agency);
			else if(deptExists) result = approveDao.findSwAdvancesAwaitingApprovalEmp(userId, empId, dept);
			else result = new ArrayList<ApprovalTransactionBean>();
		}else if(lastNameExists){
			if(tkuExists) result = approveDao.findSwAdvancesAwaitingApproval(userId, dept, agency, tku, lastName);
			else if(agencyExists) result = approveDao.findSwAdvancesAwaitingApprovalByDeptAgyLn(userId, dept, agency, lastName);
			else if(deptExists) result = approveDao.findSwAdvancesAwaitingApprovalByDeptLn(userId, dept, lastName);
			else result = new ArrayList<ApprovalTransactionBean>();
		}else{
			if(tkuExists) result = approveDao.findSwAdvancesAwaitingApproval(userId, dept, agency, tku);
			else if(agencyExists) result = approveDao.findSwAdvancesAwaitingApproval(userId, dept, agency);
			else if(deptExists) result = approveDao.findSwAdvancesAwaitingApproval(userId, dept);
			//else result = approveDao.findSwAdvancesAwaitingApproval(userId);
		}
		
		return result;
    }

    /**
	 * This method is used to get expenses pending approval for "My employees" working under a supervisor. 
	 * The second parameter checks whether adjusted expenses are to be retrieved or not
	 * @param supervisorEmpId
	 * @param requestType
	 * @return VExpApprovalList - List of Expenses awaiting approval
	 */
	public List<VExpApprovalList> getExpensesAwaitingApproval(
			int supervisorEmpId, String requestType) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpensesAwaitingApproval(int supervisorEmpId, String requestType): return List<ExpenseListBean>");
		
		List<VExpApprovalList> approvalTransactionBean = new ArrayList<VExpApprovalList>();

		if(requestType==null || supervisorEmpId<=0){
			return null;
		}

		if (requestType.equalsIgnoreCase(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED)) {
			approvalTransactionBean = approveDao.findExpensesAwaitingApproval(supervisorEmpId);
		} else if (requestType.equalsIgnoreCase(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY)) {
			approvalTransactionBean = approveDao.findAdjustedExpensesAwaitingApproval(supervisorEmpId);
		} else if (requestType.equals(IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY)) {
			approvalTransactionBean = approveDao.findNonAdjustedExpensesAwaitingApproval(supervisorEmpId);
		}
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpensesAwaitingApproval(int supervisorEmpId, String requestType)");
		
		return approvalTransactionBean;
	}
	
	public List<VTreqApprovalList> getTravelRequisitionsAwaitingApproval(
			int supervisorEmpId, String requestType) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getTravelRequisitionsAwaitingApproval(int supervisorEmpId, String requestType): return List<VTreqApprovalList>");
		
		List<VTreqApprovalList> approvalTransactionBean = new ArrayList<VTreqApprovalList>();

		if(requestType==null || supervisorEmpId<=0){
			return null;
		}

		if (requestType.equalsIgnoreCase(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED)) {
			approvalTransactionBean = approveDao.findTravelRequisitionsAwaitingApproval(supervisorEmpId);
		} /*else if (requestType.equalsIgnoreCase(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY)) {
			approvalTransactionBean = approveDao.findAdjustedExpensesAwaitingApproval(supervisorEmpId);
		} else if (requestType.equals(IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY)) {
			approvalTransactionBean = approveDao.findNonAdjustedExpensesAwaitingApproval(supervisorEmpId);
		}*/
		
		if(logger.isDebugEnabled())
			logger.debug("Exit method : getExpensesAwaitingApproval(int supervisorEmpId, String requestType)");
		
		return approvalTransactionBean;
	}
	
	/**
	 * This method is used to get expenses pending approval for "All employees" working under a supervisor. 
	 * The second parameter checks whether adjusted expenses are to be retrieved or not
	 * @param supervisorEmpId
	 * @param requestType
	 * @return VExpApprovalList - List of Expenses awaiting approval
	 */
	public List<ApprovalTransactionBean> getExpensesAwaitingApprovalAllEmployee(
			String userId, String requestType) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpensesAwaitingApprovalAllEmployee(String userId, String requestType): return List<ExpenseApprovalTransactionBean>");
		
		List<ApprovalTransactionBean> approvalTransactionBean = new ArrayList<ApprovalTransactionBean>();

		if(requestType==null || userId==null){
			return null;
		}

		if (requestType.equalsIgnoreCase(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED)) {
			approvalTransactionBean = approveDao.findAllExpensesAwaitingApprovalAllEmployees(userId);
		} else if (requestType.equalsIgnoreCase(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY)) {
			approvalTransactionBean = approveDao.findAdjustedExpensesAwaitingApprovalAllEmployees(userId);
		} else if (requestType.equals(IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY)) {
			approvalTransactionBean = approveDao.findNonAdjustedExpensesAwaitingApprovalAllEmployees(userId);
		}
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpensesAwaitingApprovalAllEmployee(String userId, String requestType)");
		
		return approvalTransactionBean;
	}
	
	public List<ApprovalTransactionBean> getTravelRequisitionAwaitingApprovalAllEmployee(
			String userId, String requestType) {
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getTravelRequisitionAwaitingApprovalAllEmployee(String userId, String requestType): return List<ExpenseApprovalTransactionBean>");
		
		List<ApprovalTransactionBean> approvalTransactionBean = new ArrayList<ApprovalTransactionBean>();

		if(requestType==null || userId==null){
			return null;
		}

		if (requestType.equalsIgnoreCase(IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED)) {
			approvalTransactionBean = approveDao.findAllTravelRequisitionsAwaitingApprovalAllEmployees(userId);
		} else if (requestType.equalsIgnoreCase(IConstants.TRANSACTION_LIST_ADJUSTED_ONLY)) {
			approvalTransactionBean = approveDao.findAdjustedExpensesAwaitingApprovalAllEmployees(userId);
		} else if (requestType.equals(IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY)) {
			approvalTransactionBean = approveDao.findNonAdjustedExpensesAwaitingApprovalAllEmployees(userId);
		}
		
		if(logger.isDebugEnabled())
			logger.debug("Enter method : getExpensesAwaitingApprovalAllEmployee(String userId, String requestType)");
		
		return approvalTransactionBean;
	}
	
	/***
	 * This method retrieves all state-wide expenses awaiting approval. 
	 * This method is called from other method/s where only adjusted/non/adjusted expense are required.
	 * @param dept
	 * @param agency
	 * @param tku
	 * @param lastName
	 * @param requestType
	 * @param userId
	 * @param moduleId
	 * @return List<ExpenseApprovalTransactionBean>
	 */

	public List<ApprovalTransactionBean> getAllSwExpensesAwaitingApproval(
			String dept, String agency, String tku, String lastName,
			String requestType, String userId, String empId) {

		List<ApprovalTransactionBean> result = null;
		
		boolean empIdExists = (empId != null && !"".equals(empId.trim())) ? true :  false ;
		boolean lastNameExists = (lastName != null && !"".equals(lastName.trim())) ? true :  false ;
		boolean deptExists = (dept != null && !"".equals(dept.trim())) ? true :  false ;
		boolean agencyExists = (agency != null && !"".equals(agency.trim())) ? true :  false ;
		boolean tkuExists = (tku != null && !"".equals(tku.trim())) ? true :  false ;
		
		if(empIdExists){
			if(tkuExists) result = approveDao.findSWExpensesAwaitingApprovalByEmpIdDeptAgencyTku(empId, dept, agency, tku, userId);
			else if(agencyExists) result = approveDao.findSWExpensesAwaitingApprovalByEmpIdDeptAgency(empId, dept, agency, userId);
			else if(deptExists) result = approveDao.findStateWideExpensesAwaitingApprovalEmployee(userId, empId, dept);
			else result = new ArrayList<ApprovalTransactionBean>();
		}else if(lastNameExists){
			if(tkuExists) result = approveDao.findStateWideExpensesAwaitingApproval(dept, agency, tku, lastName, userId);
			else if(agencyExists) result = approveDao.findStateWideExpensesAwaitingApprovalByDeptAgyLn(dept, agency, lastName, userId);
			else if(deptExists) result = approveDao.findStateWideExpensesAwaitingApprovalByDeptLn(dept, lastName, userId);
			else result = new ArrayList<ApprovalTransactionBean>();
		}else{
			if(tkuExists) result = approveDao.findStateWideExpensesAwaitingApproval(userId, dept, agency, tku);
			else if(agencyExists) result = approveDao.findStateWideExpensesAwaitingApproval(userId, dept, agency);
			else if(deptExists) result = approveDao.findStateWideExpensesAwaitingApproval(userId, dept);
			else result = approveDao.findStateWideExpensesAwaitingApproval(userId);
		}
		
		return result;
	}
   
public List<ApprovalTransactionBean> getAllSwTravelRequisitionsAwaitingApproval(
		String dept, String agency, String tku, String lastName,
		String requestType, String userId, String empId) {

	List<ApprovalTransactionBean> result = null;
	
	boolean empIdExists = (empId != null && !"".equals(empId.trim())) ? true :  false ;
	boolean lastNameExists = (lastName != null && !"".equals(lastName.trim())) ? true :  false ;
	boolean deptExists = (dept != null && !"".equals(dept.trim())) ? true :  false ;
	boolean agencyExists = (agency != null && !"".equals(agency.trim())) ? true :  false ;
	boolean tkuExists = (tku != null && !"".equals(tku.trim())) ? true :  false ;
	
	if(empIdExists){
		if(tkuExists) result = approveDao.findSWTravelRequisitionsAwaitingApprovalByEmpIdDeptAgencyTku(empId, dept, agency, tku, userId);
		else if(agencyExists) result = approveDao.findSWTravelRequisitionsAwaitingApprovalByEmpIdDeptAgency(empId, dept, agency, userId);
		else if(deptExists) result = approveDao.findStateWideTravelRequisitionsAwaitingApprovalEmployee(userId, empId, dept);
		else result = new ArrayList<ApprovalTransactionBean>();
	}else if(lastNameExists){
		if(tkuExists) result = approveDao.findStateWideTravelRequisitionsAwaitingApproval(dept, agency, tku, lastName, userId);
		else if(agencyExists) result = approveDao.findStateWideTravelRequisitionsAwaitingApprovalByDeptAgyLn(dept, agency, lastName, userId);
		else if(deptExists) result = approveDao.findStateWideTravelRequisitionsAwaitingApprovalByDeptLn(dept, lastName, userId);
		else result = new ArrayList<ApprovalTransactionBean>();
	}else{
		if(tkuExists) result = approveDao.findStateWideTravelRequisitionsAwaitingApproval(userId, dept, agency, tku);
		else if(agencyExists) result = approveDao.findStateWideTravelRequisitionsAwaitingApproval(userId, dept, agency);
		else if(deptExists) result = approveDao.findStateWideTravelRequisitionsAwaitingApproval(userId, dept);
		else result = approveDao.findStateWideExpensesAwaitingApproval(userId);
	}
	
	return result;
}

}
