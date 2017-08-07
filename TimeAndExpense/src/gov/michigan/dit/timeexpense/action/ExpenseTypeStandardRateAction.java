package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.ExpenseRates;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.display.ErrorDisplayBean;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Action to provide functionality for <code>ExpenseTypes</code>
 * 
 * @author chaudharym
 */
public class ExpenseTypeStandardRateAction extends BaseAction {
	private static final long serialVersionUID = -9192449343425254177L;

	private String expenseTypeCode;
	private Date startDate;
	private Date endDate;
	private String isOutOfState;
	private String isOvernight;
	private String isSelectCity;
	private String toCity;
	private String toState;
	private Date expenseDate;
	private String isTaxable;
	private boolean isMealRelated;

	private ExpenseLineItemDSP service;
	
	@Override
	public void prepare() {
		super.prepare();
		
		service = new ExpenseLineItemDSP(entityManager);
	}
	
	@Override
	public String execute() throws Exception {
		
		//if the selected expense type is BLD then 
		//call findExpenseTypeCodeByFlags with flag to get the correct expense type.
		
		UserProfile userProfile = (UserProfile) session.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
		
		if(isMealRelated && !CommonDSP.isUserSuperUser(userProfile)){

			// check if the City/State combination is a Select city.
			isSelectCity = "N";
			if (toCity.length() > 0 && toState.length() > 0 && expenseDate != null ) {
				int selectCityCount = service.isSelectCity( expenseDate, toCity,
						toState);
				if (selectCityCount == 1)
					isSelectCity = "Y";
				else if (selectCityCount == 0)
					isSelectCity = "N";
				else
					setupError("-2665",
							"Too many values for Select City for City:"
									+ toCity + " and State:" + toState
									+ " for expense type:" + expenseTypeCode);
			}
			
			//if Overnight = Yes then meals are Non-taxable. 
			if ("y".equalsIgnoreCase(isOvernight))
				isTaxable = "N";
			else
				isTaxable = "Y";

			this.expenseTypeCode = service.findExpenseTypeCodeByFlags(
					expenseTypeCode, isOutOfState, isTaxable, isSelectCity);
		}
				
		List<ExpenseRates> rates = service.findExpenseRatesForExpenseTypeWithinDateRange(expenseTypeCode, startDate, endDate);
		
		StringBuilder buff = new StringBuilder();
		buff.append("{expTypeCode:'");
		buff.append(expenseTypeCode);
		buff.append("',start:'");
		buff.append((new SimpleDateFormat(IConstants.DEFAULT_DATE_FORMAT)).format(startDate));
		buff.append("',end:'");
		buff.append((new SimpleDateFormat(IConstants.DEFAULT_DATE_FORMAT)).format(endDate));
		buff.append("',rate:");
		buff.append(jsonParser.toJson(rates));
		buff.append("}");
		
		setJsonResponse(buff.toString());
		
		return IConstants.SUCCESS;
	}
	
	private void setupError(String errorCode, String errorMsg) {
		//if(logger.isInfoEnabled()) logger.error(errorCode + " - " + errorMsg);
	
		error = new ErrorDisplayBean();
		error.setErrorCode(errorCode);
		error.setErrorMessage(errorMsg);
		error.setRedirectOption(false);
		error.setPreviousOption(true);
	}

	@Override
	public void validate() {
		super.validate();

		if(expenseTypeCode == null || "".equals(expenseTypeCode)
				||startDate == null ||endDate == null){
			addFieldError("error", "Invalid input");
		}
	}
	
	public void setExpenseTypeCode(String expenseTypeCode) {
		this.expenseTypeCode = expenseTypeCode;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getIsOutOfState() {
		return isOutOfState;
	}

	public void setIsOutOfState(String isOutOfState) {
		this.isOutOfState = isOutOfState;
	}

	public String getIsOvernight() {
		return isOvernight;
	}

	public void setIsOvernight(String isOvernight) {
		this.isOvernight = isOvernight;
	}

	public String getIsSelectCity() {
		return isSelectCity;
	}

	public void setIsSelectCity(String isSelectCity) {
		this.isSelectCity = isSelectCity;
	}
	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public String getToState() {
		return toState;
	}

	public void setToState(String toState) {
		this.toState = toState;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public boolean getIsMealRelated() {
		return isMealRelated;
	}

	public void setIsMealRelated(boolean isMealRelated) {
		this.isMealRelated = isMealRelated;
	}
}
