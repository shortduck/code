package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;
import gov.michigan.dit.timeexpense.model.core.UserSubject;

/**
 * Action to handle lookups for common miles between the given From and To locations.
 * 
 * @author chaudharym
 *
 */
public class CommonMileageAction extends BaseAction{
	private static final long serialVersionUID = -3955170563971703216L;
	
	private String fromCity;
	private String fromState;
	private String toCity;
	private String toState;
	
	private ExpenseLineItemDSP expenseDetailService;
	
	@Override
	public void prepare() {
		expenseDetailService = new ExpenseLineItemDSP(entityManager);
	}
	
	@Override
	public String execute() throws Exception {
		double mileage = 0.0;
		
		if(validInput()){
			UserSubject userSubject = (UserSubject)session.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME);
			if (userSubject != null)
				mileage = expenseDetailService.getCommonMiles(fromCity, fromState, toCity, toState, userSubject.getDepartment(), userSubject.getAgency());
		}
		
		setJsonResponse("{commonMiles:{fromCity:'" + TimeAndExpenseUtil.escapeApostrophe(fromCity) +"', fromState:'"+ fromState +"', toCity:'"+ TimeAndExpenseUtil.escapeApostrophe(toCity) +"', toState:'"+ toState +
				"', miles:"+ TimeAndExpenseUtil.displayAmountTwoDigits(mileage) +"}}");
		
		return IConstants.SUCCESS;
	}
	
	public boolean validInput() {
		 return (fromCity == null || fromCity.equals("") || fromCity.equals("N/A") || fromCity.equals("NA")
				|| toCity == null || toCity.equals("") || toCity.equals("N/A") || toCity.equals("NA")
				|| fromState == null || fromState.equals("") || fromState.equals("N/A") || fromState.equals("NA")
				|| toState == null || toState.equals("") || toState.equals("N/A") || toState.equals("NA"))
				? false : true;
	}

	public ExpenseLineItemDSP getExpenseDetailService() {
		return expenseDetailService;
	}


	public void setExpenseDetailService(ExpenseLineItemDSP expenseDetailService) {
		this.expenseDetailService = expenseDetailService;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity == null ? fromCity : fromCity.trim();
	}

	public String getFromState() {
		return fromState;
	}

	public void setFromState(String fromState) {
		this.fromState = fromState == null ? fromState : fromState.trim();
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity == null ? toCity : toCity.trim();
	}

	public String getToState() {
		return toState;
	}

	public void setToState(String toState) {
		this.toState = toState == null ? toState : toState.trim();
	}
	
}
