package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.display.ExpenseLiquidationDisplay;
import gov.michigan.dit.timeexpense.service.AdvanceLiquidationDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.util.ExpenseViewUtil;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

/**
 * Action invoked for validating and saving liquidations.
 * 
 * @author chaudharym
 *
 */
public class SaveExpenseLiquidationsAction extends BaseAction {
	private static final long serialVersionUID = -80057348541095L;

	private ExpenseMasters expense;
	
	private ExpenseDSP expenseService;
	private AdvanceLiquidationDSP liquidationService;
	private ExpenseViewUtil viewUtil;
	private CodingBlockDSP codingBlockService;
	private AppointmentDSP appointmentService;
	
	private String expenseLiquidationsJson;
	
	private List<ExpenseLiquidationDisplay> liquidationsDisplay;
	
	@Override
	public void prepare() {
		super.prepare();
		
		expense = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);
		expense = entityManager.merge(expense);
		codingBlockService = new CodingBlockDSP(entityManager);
		appointmentService = new AppointmentDSP(entityManager);
		viewUtil = new ExpenseViewUtil();
		viewUtil.setJsonParser(jsonParser);
		viewUtil.setCodingBlockService(codingBlockService);
		viewUtil.setAppointmentService(appointmentService);
		
		expenseService = new ExpenseDSP(entityManager);
		liquidationService = new AdvanceLiquidationDSP(entityManager);
	}
	
	@Override
	public String execute() throws Exception {

		liquidationService.saveOrUpdateLiquidations(liquidationsDisplay, expense, getLoggedInUser());

		entityManager.flush();
		setJsonResponse("{status:'success'}");
		addTimeAndExpenseErrors(viewUtil.prepareTimeAndExpenseErrors(expense));
		
		return IConstants.SUCCESS;
	}
	
	@Override
	public void validate() {
		if(expense == null){ addActionError("Please create expense before liquidation!"); return;}
		
		// get advance map by advance event Id
		Map<Integer, ExpenseLiquidationDisplay> liquidationMap = constructAdvanceMapByAdvanceEventId();
		
		liquidationsDisplay = getExpenseLiquidations();

		DecimalFormat f = new DecimalFormat();
		f.setMinimumFractionDigits(2);
		f.setMaximumFractionDigits(2);
		
		// find previous PROC expense revision
		ExpenseMasters prevProcExp = expenseService.getPrevExpenseMasterInProcStatus(expense.getExpevIdentifier(), expense.getRevisionNumber());
		
		for(int i=0 ; i<liquidationsDisplay.size(); i++){
			ExpenseLiquidationDisplay liquidation = liquidationsDisplay.get(i);
			if(liquidation.getAmountLiquidated() < 0){
				String errorMsg = "Row "+(i+1)+"  -  Liquidation amount cannot be less than 0";
				addFieldError("liquidations_"+(i+1), errorMsg);
			}

			double advanceOutstandingAmtAvailableToThisExpense = ((ExpenseLiquidationDisplay)liquidationMap.get(liquidation.getAdevIdentifier())).getAdjustedAmountOutstanding();
			
			if(liquidation.getAmountLiquidated() > advanceOutstandingAmtAvailableToThisExpense){
				String errorMsg = "Row "+(i+1)+"  -  Liquidation amount ($"+f.format(liquidation.getAmountLiquidated())+") cannot be more than the total outstanding advance ($"+f.format(advanceOutstandingAmtAvailableToThisExpense)+") ";
				addFieldError("liquidations_"+(i+1), errorMsg);
			}
		
			double prevLiquidationAmount = ((ExpenseLiquidationDisplay)liquidationMap.get(liquidation.getAdevIdentifier())).getAmountLiquidated();
			
			// if expense was ever processed(PROC) do no allow decrease in liquidation amount.
			if(prevProcExp != null && liquidation.getAmountLiquidated() < prevLiquidationAmount){
				String errorMsg = "Row "+(i+1)+"  -  Liquidation amount ($"+f.format(liquidation.getAmountLiquidated())+") cannot be reduced (from $"+ f.format(prevLiquidationAmount) +") for a processed expense";
				addFieldError("liquidations_"+(i+1), errorMsg);
			}
			
		}
		
		// ENSURE expense is in modifiable state.  
		ExpenseMasters exp = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);
		if(exp.getStatus() != null && !"".equals(exp.getStatus()))
				addFieldError("errors", "Save failed as expense in unmodifiable state. Please SAVE on ID tab and then try again.");
	}

	private Map<Integer, ExpenseLiquidationDisplay> constructAdvanceMapByAdvanceEventId() {
		List<ExpenseLiquidationDisplay> liquidations = (List<ExpenseLiquidationDisplay>)session.get(IConstants.ADVANCE_OUTSTANDING_LIST);
		
		if(liquidations == null) return null;
		
		Map<Integer, ExpenseLiquidationDisplay> liquidationMapByAdvanceId = 
			new HashMap<Integer, ExpenseLiquidationDisplay>(liquidations.size()+1, 1);
		
		for(ExpenseLiquidationDisplay liq : liquidations){
			liquidationMapByAdvanceId.put(liq.getAdevIdentifier(), liq);
		}

		return liquidationMapByAdvanceId;
	}

	private List<ExpenseLiquidationDisplay> getExpenseLiquidations() {
		Type expLiquidationsListType = new TypeToken<List<ExpenseLiquidationDisplay>>() {}.getType();
		return jsonParser.fromJson(expenseLiquidationsJson, expLiquidationsListType);
	}

	public String getExpenseLiquidationsJson() {
		return expenseLiquidationsJson;
	}

	public void setExpenseLiquidationsJson(String expenseLiquidationsJson) {
		this.expenseLiquidationsJson = expenseLiquidationsJson;
	}
}
