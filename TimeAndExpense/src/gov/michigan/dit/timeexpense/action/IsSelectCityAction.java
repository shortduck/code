/**
 * 
 */
package gov.michigan.dit.timeexpense.action;

import java.util.Date;
import javax.print.attribute.standard.DateTimeAtCompleted;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;

import gov.michigan.dit.timeexpense.model.display.ErrorDisplayBean;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.model.core.UserProfile;

/**
 * @author bhurata
 * Action to check if the "To" City/State/Expense Date combination is a select city
 */
public class IsSelectCityAction extends AbstractAction {
	private static Logger logger = Logger.getLogger(LoginAction.class);
	private ExpenseLineItemDSP service;
	
	//user can select multiple dates, hence "expenseDate" is String.
	//We will take the max date from the dates string. 
	private String expenseDate;
	private String toCity;
	private String toState;
	private String jsonResponse;
	private String ExpenseTypeCode; 
	
	public void prepare() {
		service = new ExpenseLineItemDSP(entityManager);
	}

	public String execute() {
		// Business logic to determine select city

		if (toCity == null || toState == null || expenseDate == null) {
			setJsonResponse("{IsSelectCity:''}");
		} else if (toCity.length() > 0 && toState.length() > 0
				&& expenseDate.length() > 0) {
			String sb = new String(expenseDate);
			String[] expenseDates = sb.split(",");

			Date maxExpenseDate = new Date(
					expenseDates[expenseDates.length - 1]);

			int selectCityCount = service.isSelectCity(maxExpenseDate, toCity,
					toState);
			if (selectCityCount == 1)
				setJsonResponse("{IsSelectCity:'select_city'}");
			else if (selectCityCount == 0)
				setJsonResponse("{IsSelectCity:''}");
			else {
				setJsonResponse("{IsSelectCity:''}");
				addFieldError("errors",
						"Too many values for \"Select City\" for City:"
								+ toCity + " and State:" + toState);
			}
		}

		return IConstants.SUCCESS;
	}
	
	public String isExpenseSelectCityExpense() {
		 
		UserProfile userProfile = (UserProfile) session
				.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);

		if (CommonDSP.isUserSuperUser(userProfile)) {
			setJsonResponse("{select_city_expense:'no'}");
		} else {
			if (ExpenseTypeCode == null || ExpenseTypeCode.length() == 0)
				setJsonResponse("{select_city_expense:'no'}");
			else {

				int count = service.isSelectCityExpense(ExpenseTypeCode);

				if (count > 0) {
					setJsonResponse("{select_city_expense:'yes'}");
				} else {
					setJsonResponse("{select_city_expense:'no'}");
				}
			}
		}
		return IConstants.SUCCESS;
	}
	
	public String getExpenseTypeCode() {
		return ExpenseTypeCode;
	}


	public void setExpenseTypeCode(String expenseTypeCode) {
		ExpenseTypeCode = expenseTypeCode;
	}


	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
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

	public String getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(String expenseDate) {
		this.expenseDate = expenseDate;
	}	
}
