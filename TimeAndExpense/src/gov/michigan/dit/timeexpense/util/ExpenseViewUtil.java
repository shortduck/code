package gov.michigan.dit.timeexpense.util;

import gov.michigan.dit.timeexpense.action.ActionHelper;
import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
import gov.michigan.dit.timeexpense.model.core.Appointment;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypeRules;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.model.display.TimeAndExpenseError;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import com.google.gson.Gson;


/**
 * Utility class containing methods to assist in preparing view state for expenses. 
 * E.g. Preparing expense errors for display, expense details display etc. 
 * 
 * @author chaudharym
 */
public class ExpenseViewUtil{
	public static String EMPTY_JSON_ARRAY_STRING = "items:[], totalAmount:0";
	
	protected Gson jsonParser;
	private CodingBlockDSP codingBlockService;
	private AppointmentDSP appointmentService;
	
	

	/**
	 * Prepares JSON formatted string for the <code>ExpenseErrors</code>
	 * attached to the given <code>ExpenseMasters</code>.
	 * 
	 * @param expenseMaster <code>ExpenseMasters</code> containing the <code>ExpenseErrors</code>
	 * @return JSON representation of <code>ExpenseErrors</code>
	 */
	public String prepareExpenseErrorsJson(ExpenseMasters expenseMaster) {
		if(expenseMaster == null || expenseMaster.getExpenseErrorsCollection() == null 
				|| expenseMaster.getExpenseErrorsCollection().size()<1) return "";
		
		List<TimeAndExpenseError> expenseErrors = new ArrayList<TimeAndExpenseError>();
		for(ExpenseErrors expErr : expenseMaster.getExpenseErrorsCollection()){
			ErrorMessages errorMsg = expErr.getErrorCode();
			String severity = errorMsg.getSeverity();
			if (expErr.getErrorSource().startsWith(IConstants.EXP_ERR_SRC_CB_TAB_PREFIX)) {
				Integer appointmentId = expenseMaster.getExpevIdentifier().getAppointmentId();
				Appointment appointment = appointmentService.findAppointment(appointmentId);
				AgencyOptions cbAgencyOptions = codingBlockService.getCBAgencyOptions(appointment.getDepartment(), appointment.getAgency());
				if ("Y".equals(cbAgencyOptions.getAllowInvalidCbElementsInd()) && !expenseMaster.isPdfInd()) {
					severity = IConstants.STRING_WARNING;
				}
			}
			
			expenseErrors.add(new TimeAndExpenseError(expErr.getExperIdentifier(),
					errorMsg.getErrorCode(), errorMsg.getErrorText(),
					severity, 
					ActionHelper.formatCbErrorSource(expenseMaster, expErr.getErrorSource())));
		}
		
		return jsonParser.toJson(expenseErrors);
	}
	
	/**
	 * Prepares a list of <code>TimeAndExpenseError</code> representing errors attached to the 
	 * given <code>ExpenseMasters</code>.
	 * 
	 * @param expense - The <code>ExpenseMasters</code> in which the errors are present.
	 * @return List<TimeAndExpenseError> - List of errors for view.
	 */
	public List<TimeAndExpenseError> prepareTimeAndExpenseErrors(ExpenseMasters expense) {
		List<TimeAndExpenseError> displayErrors = new ArrayList<TimeAndExpenseError>();

		if(expense!= null){
			List<ExpenseErrors> errors = expense.getExpenseErrorsCollection();
			
			if(errors != null){
				for(ExpenseErrors expenseError : errors){
			
					ErrorMessages errorMsg = expenseError.getErrorCode();
					String severity = errorMsg.getSeverity();
					if (expenseError.getErrorSource().startsWith(IConstants.EXP_ERR_SRC_CB_TAB_PREFIX)) {
						Integer appointmentId = expense.getExpevIdentifier().getAppointmentId();
						Appointment appointment = appointmentService.findAppointment(appointmentId);
						AgencyOptions cbAgencyOptions = codingBlockService.getCBAgencyOptions(appointment.getDepartment(), appointment.getAgency());
						if ("Y".equals(cbAgencyOptions.getAllowInvalidCbElementsInd()) && !expense.isPdfInd()) {
							severity = IConstants.STRING_WARNING;
						}
					}
					displayErrors.add(new TimeAndExpenseError(setErrorIdentifierforDisplay(expenseError), 
							errorMsg.getErrorCode(), 
							errorMsg.getErrorText(), severity, 
							ActionHelper.formatCbErrorSource(expense, expenseError.getErrorSource())));
				}
			}
		}
		
		return displayErrors;
	}
	
	public static boolean isExpenseMealType(List<ExpenseTypeRules> expenseTypeRules ){
		boolean isMealType = false;
		
		for(ExpenseTypeRules rules: expenseTypeRules){
			if(rules.getRuleIdentifier() == 1012 && "Y".equalsIgnoreCase(rules.getValue()))
			{
				isMealType = true;
				break;
			}
		}
		
		return isMealType;
			
	}

	/**
	 * Prepares JSON formatted string representing the expense details for the
	 * given <code>ExpenseMasters</code>.
	 * 
	 * @param expense - The <code>ExpenseMasters</code> in which the errors are present.
	 * @return JSON representation of <code>ExpenseDetails</code>
	 */
	public String prepareDetailsJson(ExpenseMasters expense, EntityManager entityManager, boolean isUserSuperUser) {
		
		if(expense == null) return EMPTY_JSON_ARRAY_STRING;
		
		List<ExpenseDetails> details = expense.getExpenseDetailsCollection();
		if(details == null || details.isEmpty()) return EMPTY_JSON_ARRAY_STRING;
		
		double totalDetailsAmount = 0;
		
		StringBuilder buff = new StringBuilder("items:[");
		String categoryCode = new String();
		
		for(ExpenseDetails detail : details){
			buff.append("{");
			
			// mandatory fields
			// Line item no must be the first field!
			buff.append("lineItemNo:"+detail.getLineItem());
			buff.append(",expdId:"+detail.getExpdIdentifier());
			buff.append(",expenseDate:\""+ (new SimpleDateFormat(IConstants.DEFAULT_DATE_FORMAT)).format(detail.getExpDate())+"\"");
			buff.append(",expenseType:\""+detail.getExpTypeCode().getExpTypeCode()+"\"");
			
			List<ExpenseTypeRules> expenseTypeRules =  detail.getExpTypeCode().getExpenseTypeRules();
			boolean isMealType = false;
			ExpenseLineItemDSP expenseLineItemService = new ExpenseLineItemDSP(entityManager);
			
			if(isExpenseMealType(expenseTypeRules))	{
				isMealType = true;
				categoryCode = detail.getExpTypeCode().getCategoryCode();
			}
			
			if (isMealType && isUserSuperUser)
				buff.append(",expenseTypeDesc:\""+detail.getExpTypeCode().getDescription()+"\"");
			
			if (isMealType && !isUserSuperUser)
				buff.append(",expenseTypeDesc:\"" + expenseLineItemService.getCategoryDescription(categoryCode) + "\"");			
			
			if (!isMealType)
				buff.append(",expenseTypeDesc:\""+detail.getExpTypeCode().getDescription()+"\"");
			
			
			buff.append(",amount:"+TimeAndExpenseUtil.displayAmountTwoDigits(detail.getDollarAmount()));
			buff.append(",roundTrip:"+detail.isRoundTrip());
			buff.append(",commonMiles:"+detail.hasCommonMilesOverridden());
			
			// non mandatory fields
			if(detail.getFromElocCity() != null && !"".equals(detail.getFromElocCity()))
			{
				/* FIX AI-21714 Comments:- Code fix for allowing escape characters 
				*/
				// buff.append(",fromCity:\""+detail.getFromElocCity()+"\""); 
				buff.append(
				",fromCity:\""+TimeAndExpenseUtil.escapeJSON(detail.getFromElocCity())+"\""); 
			}			
			if(detail.getFromElocStProv() != null && !"".equals(detail.getFromElocStProv()))
				buff.append(",fromState:\""+detail.getFromElocStProv()+"\"");
			if(detail.getToElocCity() != null && !"".equals(detail.getToElocCity()))
			{
				//buff.append(",toCity:\""+detail.getToElocCity()+"\""); 
				/* FIX AI-21714 Comments:- Code fix for allowing escape characters 
				*/
				buff.append(
				",toCity:\""+TimeAndExpenseUtil.escapeJSON(detail.getToElocCity())+"\""); 
			}				 
			if(detail.getToElocStProv() != null && !"".equals(detail.getToElocStProv()))
				buff.append(",toState:\""+detail.getToElocStProv()+"\"");
			
			if(detail.getDepartTime() != null && !"".equals(detail.getDepartTime()))
				buff.append(",departTime:\""+TimeAndExpenseUtil.constructTimeString(detail.getDepartTime())+"\"");
			if(detail.getReturnTime() != null && !"".equals(detail.getReturnTime()))
				buff.append(",returnTime:\""+TimeAndExpenseUtil.constructTimeString(detail.getReturnTime())+"\"");
			if(detail.getMileage() > 0)
				buff.append(",miles:"+detail.getMileage()+"");
			if(detail.getVicinityMileage() > 0)
				buff.append(",vicinityMiles:"+detail.getVicinityMileage()+"");
			if(detail.getComments() != null && !"".equals(detail.getComments())){
				//PS [mc 9/24/2010]: The JSON character encoding is no longer required as the character
				// encoding is specified to be UTF-8 on all AJAX responses in the Result.
				//buff.append(",comments:\""+TimeAndExpenseUtil.escapeJSON(detail.getComments())+"\"");
				buff.append(",comments:\""+TimeAndExpenseUtil.escapeJSON(detail.getComments())+"\"");
			}
			
			if(detail.getExpTypeCode().getCategoryCode() != null && !"".equals(detail.getExpTypeCode().getCategoryCode())){
				buff.append(",categoryCode:\""+detail.getExpTypeCode().getCategoryCode()+"\"");	
			}
			
			if (detail.getOvernightInd() != null
					&& !"".equals(detail.getOvernightInd())) {
				if ("y".equalsIgnoreCase(detail.getOvernightInd())) {
					buff.append(",overnightInd:\"Yes\"");
				} else
					buff.append(",overnightInd:\"No\"");
			}
		
			buff.append("},");
			
			// update running total
			totalDetailsAmount += detail.getDollarAmount();
		}

		// remove comma at the end, ONLY if above loop iterated atleast once
		if(details.size()>0) 
			buff.deleteCharAt(buff.length()-1);
		
		buff.append("],totalAmount:"+totalDetailsAmount);
		
		return buff.toString();
	}
	
	/**
	 * Used to add a temporary identifying value for display purposes, needed
	 * before an error is saved to database
	 * @param expErr
	 * @return
	 */
	private int setErrorIdentifierforDisplay (ExpenseErrors expErr){
		Long errorIdentifier = 0L;
		if (expErr.getExperIdentifier() != null)
			errorIdentifier = expErr.getExperIdentifier().longValue();
		else
			errorIdentifier = System.nanoTime();
		
		return errorIdentifier.intValue();
	}
	
	
	
	public Gson getJsonParser() {
		return jsonParser;
	}

	public void setJsonParser(Gson jsonParser) {
		this.jsonParser = jsonParser;
	}
	
	public void setCodingBlockService(CodingBlockDSP codingBlockService) {
		this.codingBlockService = codingBlockService;
	}

	public void setAppointmentService(AppointmentDSP appointmentService) {
		this.appointmentService = appointmentService;
	}
	
	

}
