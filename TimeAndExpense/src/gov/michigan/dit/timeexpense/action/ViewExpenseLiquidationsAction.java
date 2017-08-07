package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.display.ExpenseLiquidationDisplay;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.AdvanceLiquidationDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Action class for displaying liquidations for a given expense.
 * PS: If an employee has the ability to enter expenses, he must be
 * able to see his advances and liquidate them irrespective of the security settings.
 * E.g. If there is no security access to see the advances in Advances module, they are
 * not shown in Advances module. But for Expense module, the outstanding advances would
 * be shown and allowed to be liquidated, if the user is able to submit his expenses.
 * 
 * @author chaudharym
 */
public class ViewExpenseLiquidationsAction extends BaseAction {
	private static final long serialVersionUID = 5116795829332314955L;

	private AdvanceDSP advanceService;
	private AdvanceLiquidationDSP liquidationService;
	
	@Override
	public void prepare() {
		super.prepare();
		
		advanceService = new AdvanceDSP(entityManager);
		liquidationService = new AdvanceLiquidationDSP(entityManager);
	}

	@Override
	public String execute() throws Exception {
		ExpenseMasters expenseMaster = (ExpenseMasters)session.get(IConstants.CURR_EXPENSEMASTER);

		if(expenseMaster != null){ 
			expenseMaster = entityManager.merge(expenseMaster);
	
			// find liquidations afresh. DO NOT try to fetch from session as that would
			// prevent seeing liquidation changes after they are saved successfully 
			List<ExpenseLiquidationDisplay> liquidations = prepareLiquidations(expenseMaster);
			// Put this list in session for reuse during SAVE validation.
			// PS: remove this from session soon after validation success.
			session.put(IConstants.ADVANCE_OUTSTANDING_LIST, liquidations);
	
			// calculate outstanding advance amount
			double totalOutstandingAdvanceAmt = calculateOutstandingAdvanceAmount(liquidations);
			
			// [mc 02/08] Always update the amount. Though this change makes no difference because this amount is
			// anyways cleared on TripIdAction invocation but to preserve sanity, if liquidation list is put into 
			// session(above) then the amount calculated from it should also be updated.
			//updateSessionIfMissing(IConstants.EXPENSE_TOTAL_OUTSTANDING_ADVANCE_AMOUNT, totalOutstandingAdvanceAmt);
			session.put(IConstants.EXPENSE_TOTAL_OUTSTANDING_ADVANCE_AMOUNT, totalOutstandingAdvanceAmt);
			
			double totalExpenses = expenseMaster.getAmount();
			
			setJsonResponse(prepareResponseJson(liquidations, totalOutstandingAdvanceAmt, totalExpenses));
		}
		else{
			setJsonResponse(prepareResponseJson(new ArrayList<ExpenseLiquidationDisplay>(), 0, 0));
		}
		
		return IConstants.SUCCESS;
	}
	
	private List<ExpenseLiquidationDisplay> prepareLiquidations(ExpenseMasters expenseMaster) {
		List<AdvanceMasters> advances = advanceService.getAdvanceListByEmpId(getUserSubject().getEmployeeId(), expenseMaster);
		
		List<ExpenseLiquidationDisplay> result = new ArrayList<ExpenseLiquidationDisplay>(advances.size());
	//	Date stExp,endExp;
	//	double advanceCalc=0.0;
		// calculate outstanding advance amount 
		for(AdvanceMasters advance: advances){
			// if liquidation is null check for advance dates
			/*	stExp =   expenseMaster.getExpDateFrom();
			endExp=  expenseMaster.getExpDateTo();
			advanceCalc = advanceService.getNonPermAdvance(stExp, endExp, advance.getAdvmIdentifier());
			
			if (advanceCalc >0.0)
			{
				//display.setAmountLiquidated(advanceCalc);
				// Enter row in Advance_Liquidations table
			//	userLiquidation.getAdvmIdentifier(), expense.getExpmIdentifier()); 
				//liquidationsDisplay.set(0, liquidDisplay); 
				// check if liquidation already saved
				
				liquidationService.checkSaveLiquidation(advance, expenseMaster.getExpmIdentifier(),advanceCalc);
				
				liquidationService.saveApporovedAdvLiquid(advance, expenseMaster.getExpmIdentifier(),advanceCalc);
				
			}*/
			AdvanceLiquidations liquidation = null;
			ExpenseLiquidationDisplay display = null;
			if ("Y".equals(expenseMaster.getCurrentInd())){
				liquidation =  liquidationService.findLiquidationByExpenseMasterAndAdvanceMaster(
						advance.getAdvmIdentifier(), expenseMaster.getExpmIdentifier());
				display = prepareLiquidationDisplayFromAdvance(advance);
			} else {
				List<AdvanceLiquidations> liquidationList = liquidationService.findLiquidationsByExpenseId(expenseMaster.getExpmIdentifier());
				if (liquidationList != null && !liquidationList.isEmpty()){
					for (AdvanceLiquidations item: liquidationList){
						AdvanceMasters advanceForPriorLiquidation = advanceService.getAdvanceByMasterId(item.getAdvanceMaster().getAdvmIdentifier());
						display = prepareLiquidationDisplayFromAdvance(advanceForPriorLiquidation);
						display.setAmountLiquidated(item.getAmount());
						result.add(display);
					}
					break;
				}
			}
			

			// ADJUSTMENTS for DISPLAY purposes:
			// If a liquidation was done from this advance, then outstanding amt should be the original
			// plus the liquidation as this liquidation can anytime be changed by the user
		
			if(liquidation != null && "Y".equals(expenseMaster.getCurrentInd())){
				double effectiveExpenseLiquidationFromAdv =  liquidationService.calculateEffectiveLiquidationAmountFromAdvance(expenseMaster.getExpevIdentifier(), advance);
				
				display.setAdjustedAmountOutstanding(display.getAmountOutstanding() + effectiveExpenseLiquidationFromAdv);
				display.setAmountLiquidated(liquidation.getAmount());
			}
			if (display != null){
				result.add(display);
			}
			
		}
		
		return result;
	}

	private ExpenseLiquidationDisplay prepareLiquidationDisplayFromAdvance(AdvanceMasters advance) {
		ExpenseLiquidationDisplay display = new ExpenseLiquidationDisplay();
		display.setAdevIdentifier(advance.getAdevIdentifier().getAdevIdentifier());
		display.setAdvanceFromDate(advance.getFromDate());
		display.setAdvanceToDate(advance.getToDate());
		display.setAdvanceReason(advance.getAdvanceReason());
		display.setAdvmIdentifier(advance.getAdvmIdentifier());
		display.setAmountLiquidated(0);
		display.setAmountOutstanding(advance.getAdevIdentifier().getOutstandingAmount());
		display.setAdjustedAmountOutstanding(display.getAmountOutstanding());
		display.setDateRequested(advance.getRequestDate());
		display.setDollarAmount(advance.getAmount());
		display.setPaidDate(advance.getPaidPpEndDate());
		display.setPermanentAdvInd(advance.getPermanentAdvInd());
		
		return display;
	}

	/**
	 * Calculates total amount from all the outstanding advances.
	 * 
	 * @param advances
	 * @return total outstanding amount from all advances
	 */
	private double calculateOutstandingAdvanceAmount(List<ExpenseLiquidationDisplay> advances) {
		if(advances == null) return 0;
		
		double amountOutstanding = 0;
		
		for (ExpenseLiquidationDisplay item : advances) {
			amountOutstanding += item.getAmountOutstanding();
		}
		
		return amountOutstanding;
	}

	private String prepareResponseJson(List<ExpenseLiquidationDisplay> advances, 
										double totalOutstandingAdvance, double totalExpenses) {
		
		StringBuilder buff = new StringBuilder();
		buff.append("{outstandingAdvance:'" + TimeAndExpenseUtil.displayAmountTwoDigits(totalOutstandingAdvance) + "',");
		buff.append("expense:'" + TimeAndExpenseUtil.displayAmountTwoDigits(totalExpenses) + "',");
		buff.append("advances: " + jsonParser.toJson(advances));
		buff.append("}");
		
		return buff.toString();
	}
}
