package gov.michigan.dit.timeexpense.service;

import gov.michigan.dit.timeexpense.dao.AdvanceDAO;
import gov.michigan.dit.timeexpense.dao.AdvanceLiquidationDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.model.core.AdvanceEvents;
import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidationsPK;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.display.AdvanceLiqEditBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseLiquidationDisplay;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * Domain service class for advance liquidations. 
 * 
 * @author chaudharym
 */
public class AdvanceLiquidationDSP {
	private static Logger logger = Logger.getLogger(AdvanceLiquidationDSP.class);

	private AdvanceLiquidationDAO liquidationDao;
	private AdvanceDAO advanceDao;
	private ExpenseDAO expenseDao;
	
	public AdvanceLiquidationDSP(EntityManager em){
		liquidationDao = new AdvanceLiquidationDAO(em);
		advanceDao = new AdvanceDAO(em);
		expenseDao = new ExpenseDAO(em);
	}
	
	/**
	 * Finds expense for given liquidation. 
	 * 
	 * @param liquidation Id
	 * @return expense master
	 */
	public ExpenseMasters findExpenseByLiquidationId(Integer liquidationId){
		return expenseDao.findExpenseByLiquidationId(liquidationId);
	}
	
	/**
	 * Finds liquidation done by the expense with the given expense master Id. 
	 * 
	 * @param expense master Id
	 * @return liquidations
	 */
	public List<AdvanceLiquidations> findLiquidationsByExpenseId(Integer expmId){
		return liquidationDao.findLiquidationsByExpenseId(expmId);
	}
	
	/**
	 * Finds liquidations list (max) advm_identifier with the given expense master Id. 
	 * 
	 * @param expense master Id
	 * @return liquidations
	 */
	public List<AdvanceLiqEditBean> findLiquidationsByExpenseIdForEdit(Integer expmId){
		return liquidationDao.findLiquidationsByExpenseIdForEdit(expmId);
	}
	/**
	 * Finds expense for given liquidation. 
	 * 
	 * @param liquidation Id
	 * @return expense master
	 */
	public AdvanceLiquidations findLiquidationByExpenseMasterAndAdvanceMaster(Integer advmIdentifier, Integer expmIdentifier){
		 return liquidationDao.findLiquidationByAdvanceAndExpenseId(advmIdentifier, expmIdentifier);
	}
	
	/**
	 * Fetches liquidations for a previously processed expense
	 * @param expmIdentifier
	 * @return
	 */
	
	public AdvanceLiquidations findLiquidationByExpenseMaster(Integer expmIdentifier){
		 return liquidationDao.findLiquidationByExpenseId(expmIdentifier);
	}
	
	/**
	 * Processes the given list of liquidation display beans for updating/inserting
	 * <code>AdvanceLiquidations</code> records. The rules to determine the course 
	 * of action are as follows:
	 * <ul>
	 * 	<li><ul>If liquidation already exists:
	 * 			<li>and liquidation amount is ZERO, then delete the liquidation.</li>
	 * 			<li>and liquidation amount is greater than ZERO, then update the existing liquidation.</li>
	 * 		</ul>
	 * 	</li>
	 * 	<li><ul>If no previous liquidation exists:
	 * 			<li>and liquidation amount is ZERO, ignore and do not create any liquidation</li>
	 * 			<li>and liquidation amount is greater than ZERO, then create new liquidation.</li>
	 * 		</ul>
	 * 	</li>
	 * </ul>
	 * 
	 * If the liquidation already exists,
	 * then it's updated with the amount from the display bean, otherwise a new 
	 * liquidation is created. 
	 * 
	 * @param list of liquidation display bean
	 * @param expense
	 */
	public void saveOrUpdateLiquidations(List<ExpenseLiquidationDisplay> liquidationsDisplay,
			ExpenseMasters expense, UserProfile user){
		
		for(ExpenseLiquidationDisplay userLiquidation : liquidationsDisplay){
			
			AdvanceLiquidations liquidation = liquidationDao.findLiquidationByAdvanceAndExpenseId(userLiquidation.getAdvmIdentifier(), expense.getExpmIdentifier());
			
			// prev liquidation exists
			if(liquidation != null){
				// delete
				if(userLiquidation.getAmountLiquidated() == 0){
					liquidationDao.deleteLiquidation(liquidation);
					continue;
				}
				// update 
				else if(userLiquidation.getAmountLiquidated() > 0){
					liquidation.setAmount(userLiquidation.getAmountLiquidated());
					liquidation.setModifiedUserId(user.getUserId());
				}
			}
			// no prev liquidation
			else{
				// create new, if requested i.e. amount > 0
				if(userLiquidation.getAmountLiquidated() > 0){
					// create new
					liquidation = new AdvanceLiquidations();
					
					// set advance bi-directional relation
					AdvanceMasters advance = advanceDao.findAdvanceByMasterId(userLiquidation.getAdvmIdentifier());
					liquidation.setAdvanceMaster(advance);
					advance.getAdvanceLiquidationsCollection().add(liquidation);
					
					liquidation.setExpenseMasterId(expense.getExpmIdentifier());
					liquidation.setAmount(userLiquidation.getAmountLiquidated());
					liquidation.setModifiedUserId(user.getUserId());
				}
			}
			
			if(liquidation != null ) liquidationDao.saveAdvanceLiquidation(liquidation);
		}
		
	}

	/**
	 * Calculates the amount liquidated by an <code>ExpenseEvents</code> from the given advance. 
	 * Generally, the current version of <code>ExpenseMasters</code>, from among all the masters 
	 * attached to this event, represents the liquidation amount. But if the current version is 
	 * not finally approved (i.e not in APPR status), then EITHER we need to ignore 
	 * the liquidations done by that master (if it's a brand new expense that was never final approved)
	 * OR we need to consider the previous expense master that still holds valid liquidations 
	 * (i.e this previous version should have received final approval and not be in rejected status).
	 * 
	 * @param expense event
	 * @param AdvanceMasters
	 * @return effective liquidation done by this expense event
	 */
	public double calculateEffectiveLiquidationAmountFromAdvance(ExpenseEvents expenseEvent, AdvanceMasters advance) {
		double result = 0;
		
		List<ExpenseMasters> expenseRevs = expenseEvent.getExpenseMastersCollection();
		
		// make a revision - expense map, for convenience & to avoid fetching expense again
		Map<Integer, ExpenseMasters> expenseRevisionMap = new HashMap<Integer, ExpenseMasters>(expenseRevs.size(), 1);
		for(ExpenseMasters expense : expenseRevs){
			expenseRevisionMap.put(expense.getRevisionNumber(), expense);
		}
		
		for(ExpenseMasters expense : expenseRevs){
			// if it's not the current, ignore it.
			if("N".equalsIgnoreCase(expense.getCurrentInd())){
				continue;
			}
			
			String status = expense.getStatus();
			// expense not yet APPR 
			if(status == null || "".equals(status) || IConstants.SUBMIT.equals(status)){
				
				// Is this the first revision? If so, ignore it.
				if(expense.getRevisionNumber()== 0){
					break;
				}
				// Take immediately previous final approved revision that is not rejected
				else{
					int currentExpenseRevisionNo = expense.getRevisionNumber();
					
					for(int i=currentExpenseRevisionNo-1; i>=0 ; i--){
						ExpenseMasters prevExpense = expenseRevisionMap.get(i);
						
						if(prevExpense != null && !IConstants.REJECTED.equals(prevExpense.getStatus())
								&& !"".equals(prevExpense.getStatus()) && !IConstants.SUBMIT.equals(prevExpense.getStatus())){
							result = liquidationDao.findTotalLiquidationAmountByExpenseFromAdvance(prevExpense.getExpmIdentifier(), advance);
							break;
						}
					}
					
					// once we process the current expense, we ALWAYS find the effective amount as
					// the else section above iterates in decreasing order of revisions until finding
					// the correct expense to consider. So, after that iterations, we'll always have
					// the result and do not need to do any further processing for this expense event
					break;
				}
			}
			// Final approved expense NOT rejected
			else if(!IConstants.REJECTED.equalsIgnoreCase(status)){
				// If submitted or higher BUT NOT rejected, then that's the effective expense to be considered.
				result = liquidationDao.findTotalLiquidationAmountByExpenseFromAdvance(expense.getExpmIdentifier(), advance);
				break;
			}
		}
		
		return result;
	}
		
	/**
	 * Processes functionality that needs to be done on liquidations when an expense is approved by the
	 * final approver.
	 * 
	 * @param approved expense
	 */
	public void effectLiquidationsUponFinalExpenseApproval(ExpenseMasters expense, boolean finalApproverModifyingApprovedExp){
		// if expense not in APPR status, return
		if(!IConstants.APPROVED.equals(expense.getStatus())) return;
		
		// Undo the effect of liquidations done by previous finally approved processed expense.
		// Find expense who's liquidations need to be undone. IT's always the previous PROC expense revision,
		// except if the final approver is modifying an approved expense (in which case we need to undone liq
		// attached to the previous approved exp revision).
		ExpenseMasters expenseToBeUndone = finalApproverModifyingApprovedExp ? 
			expenseDao.findPrevExpenseInStatus(expense.getExpevIdentifier(), IConstants.APPROVED, expense.getRevisionNumber()) :  
			expenseDao.findPrevExpenseInStatus(expense.getExpevIdentifier(), IConstants.PROCESSED ,expense.getRevisionNumber());
		
		if(expenseToBeUndone != null){
			// undo liquidations effect on outstanding amt by this exp
			undoLiquidationEffectOnFinalExpenseApproval(expenseToBeUndone);
			expenseDao.saveExpense(expenseToBeUndone);
		}
		
		List<AdvanceLiquidations> liquidations = liquidationDao.findLiquidationsByExpenseId(expense.getExpmIdentifier());
		
		for(AdvanceLiquidations liq : liquidations){
			// only perform updates for the current revision of advance
			if ("Y".equals(liq.getAdvanceMaster().getCurrentInd())){
			AdvanceEvents advance = liq.getAdvanceMaster().getAdevIdentifier();
			advance.setOutstandingAmount(advance.getOutstandingAmount() - liq.getAmount());
			advanceDao.saveAdvanceEvent(advance);
	}
		}
	}
	
	/**
	 * Undoes the effect of liquidations on outstanding advance amount.
	 * 
	 * @param approved expense
	 */
	public void undoLiquidationEffectOnFinalExpenseApproval(ExpenseMasters expense){
		// if expense not in APPR status, return
		if(!IConstants.PROCESSED.equals(expense.getStatus()) && !IConstants.APPROVED.equals(expense.getStatus())) return;
		
		List<AdvanceLiquidations> liquidations = liquidationDao.findLiquidationsByExpenseId(expense.getExpmIdentifier());
		
		for(AdvanceLiquidations liq : liquidations){
			if ("Y".equals(liq.getAdvanceMaster().getCurrentInd())){
			// only perform updates for the current revision of advance
			AdvanceEvents advance = liq.getAdvanceMaster().getAdevIdentifier();
			advance.setOutstandingAmount(advance.getOutstandingAmount() + liq.getAmount());			
			advanceDao.saveAdvanceEvent(advance);
			}
		}
	}
	
	/**
	 * This method saves the data in ADVANCE_LIQUIATIONS for approved advances
	 * @param advance
	 * @param expmID
	 * @param amtLi
	 */
	public void saveApporovedAdvLiquid(AdvanceMasters advance,int expmID,double amtLi)
	{
		AdvanceLiquidations liquidation = new AdvanceLiquidations();
		liquidation.setAdvanceMaster(advance);
		liquidation.setExpenseMasterId(expmID);
		liquidation.setAmount(amtLi);
		
		liquidationDao.saveAdvanceLiquidation(liquidation);		
	}
	
/**
 * 
 * @param advance
 * @param expmID
 * @param amtLi
 * @return
 */
	public boolean checkSaveLiquidation(AdvanceMasters advance,int expmID,double amtLi)
	{
		List<AdvanceLiquidations> listAll = liquidationDao.checkAdvanceLiquidation(expmID,advance);	
		if(listAll !=null && listAll.size()>0)
		{
		return true;
		}else
		{
			return false;
		}
	}
	
	/**
	 * Calculates the amount liquidated by an <code>ExpenseEvents</code>. Generally, the current version
	 * of <code>ExpenseMasters</code>, from among all the masters attached to this event, represents
	 * the liquidation amount. But if the current version is in SAVED status (i.e has not yet been 
	 * submitted for approval), then EITHER we need to ignore the liquidations done by that master
	 * (if this is a brand new expense that was never submitted) OR we need to consider the previous
	 * expense master that still holds valid liquidations (i.e this previous version should not be 
	 * in rejected status).
	 * 
	 * @param expense event
	 * @return effective liquidation done by this expense event
	 */
//[mc]: Commenting in view of liquidation refactoring. Try using "calculateEffectiveLiquidationAmountFromAdvance" instead.
/*	public double calculateEffectiveLiquidationAmount(ExpenseEvents expenseEvent) {
		double result = 0;
		
		List<ExpenseMasters> expenseRevs = expenseEvent.getExpenseMastersCollection();
		
		// make a revision - expense map, for convenience & to avoid fetching expense again
		Map<Integer, ExpenseMasters> expenseRevisionMap = new HashMap<Integer, ExpenseMasters>(expenseRevs.size(), 1);
		for(ExpenseMasters expense : expenseRevs){
			expenseRevisionMap.put(expense.getRevisionNumber(), expense);
		}
		
		for(ExpenseMasters expense : expenseRevs){
			// if it's not the current, ignore it.
			if("N".equalsIgnoreCase(expense.getCurrentInd())){
				continue;
			}
			
			String status = expense.getStatus();
			// expense in SAVE status
			if(status == null || "".equals(status)){
				
				// Is this the first revision? If so, ignore it.
				if(expense.getRevisionNumber()== 0){
					break;
				}
				// Take immediately previous revision that is not rejected
				else{
					int currentExpenseRevisionNo = expense.getRevisionNumber();
					
					for(int i=currentExpenseRevisionNo-1; i>=0 ; i--){
						ExpenseMasters prevExpense = expenseRevisionMap.get(i);
						
						if(prevExpense != null && !IConstants.REJECTED.equals(prevExpense.getStatus())){
							result = liquidationDao.findTotalLiquidationAmountByExpense(prevExpense.getExpmIdentifier());
							break;
						}
					}
					
					// once we process the current expense, we ALWAYS find the effective amount as
					// the else section above iterates in decreasing order of revisions until finding
					// the correct expense to consider. So, after that iterations, we'll always have
					// the result and do not need to do any further processing for this expense event
					break;
				}
			}
			// NOT in SAVE status and NOT rejected
			else if(!IConstants.REJECTED.equalsIgnoreCase(status)){
				// If submitted or higher BUT NOT rejected, then that's the effective expense to be considered.
				result = liquidationDao.findTotalLiquidationAmountByExpense(expense.getExpmIdentifier());
				break;
			}
		}
		
		return result;
	}*/

	/**
	 * Calculates the amount liquidated by an <code>ExpenseMasters</code>. If the expense is in submitted
	 * or higher status and not rejected, then the liquidation amount is returned. But if the expense is 
	 * in saved status, then the amount returned is ZERO because the liquidations attempted by this expense
	 * would only be effective once its submitted. If the expense has been rejected, then also the liquidations
	 * attempted by it are invalid and hence ZERO is returned.   
	 * 
	 * @param expense master
	 * @return effective liquidation done by this expense master
	 */
//[mc]: Commenting in view of liquidation refactoring.
/*	public double calculateEffectiveLiquidationAmount(ExpenseMasters expense) {
		double result = 0;
	
		String status = expense.getStatus();
		if(status != null && !"".equals(status) && !IConstants.REJECTED.equalsIgnoreCase(status)){
			result = expense.getAmount();
		}
		
		return result;
	}
*/	
}
