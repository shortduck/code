/**
 * DSP for common services
 * 
 * ZH - 02/05/2009
 */

package gov.michigan.dit.timeexpense.service;

import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.model.core.AdvanceErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseProfileRules;
import gov.michigan.dit.timeexpense.model.core.ExpenseProfiles;
import gov.michigan.dit.timeexpense.model.core.RefCodes;
import gov.michigan.dit.timeexpense.model.core.TravelReqErrors;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.model.display.ExpenseTypeDisplayBean;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

public class CommonDSP {

	private CommonDAO commonDao;

	public CommonDSP(EntityManager em) {
		commonDao = new CommonDAO(em);
	}
	
	public List getNewsItemList() {
		return commonDao.getNewsItemList(IConstants.NEWS_BULLETIN_ITEMS);
	}
	
	/***
	 * This method retrieves the Fiscal year end date for the current year
	 * @return
	 * @throws ParseException
	 */

	public Date getFiscalYearEndDate() throws ParseException {
		
		Date fiscalYearEndDate = null;
		StringBuffer strBuf = new StringBuffer();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		try {
			fiscalYearEndDate = df.parse((strBuf.append("09/30/")
					.append(currentYear)).toString());
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fiscalYearEndDate;
	}
	
	/**
	 * Retrieves the Fiscal Year End Date for the Given Date 
	 * @param expenseStartDate
	 * @return
	 * @throws ParseException
	 */
	public Date getFiscalYearEndDate(Date expenseStartDate) throws ParseException {
		Date fiscalYearEndDate = null;
		StringBuffer strBuf = new StringBuffer();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(expenseStartDate);
		
		int expenseStartDateMonth = cal.get(Calendar.MONTH);
		int expenseStartDateYear = cal.get(Calendar.YEAR);

		if (expenseStartDateMonth <= 8) {
			try {
				fiscalYearEndDate = df.parse((strBuf.append("09/30/").append(expenseStartDateYear)).toString());
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				fiscalYearEndDate = df.parse((strBuf.append("09/30/").append(expenseStartDateYear + 1)).toString());
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		return fiscalYearEndDate;
	}
		
	/**
	 * This method gets the ExpenseProfilerule value provided the Appointment and the Rule identifier 
	 * @param apptId
	 * @param ruleId
	 * @return String
	 */
	
	public ExpenseProfileRules getExpenseProfileRules(int apptId,int ruleId){
		
		ExpenseProfileRules expenseProfileRules = null;
		
		// get the last profile end date for the given appt
		Date lastProfileEndDate = commonDao.findMaxExpenseProfileEndDateInAppt(apptId);
		if(lastProfileEndDate == null) lastProfileEndDate = Calendar.getInstance().getTime();
		
		ExpenseProfiles expenseProfile = commonDao.findExpenseProfiles(apptId, lastProfileEndDate);
		
		
		if(expenseProfile!=null && expenseProfile.getExpenseProfileRulesCollection()!=null){
			// loop through the ExpenseProfileRules List if not NULL
			for(ExpenseProfileRules expProfileRules : expenseProfile.getExpenseProfileRulesCollection()){
				if(expProfileRules.getRuleIdentifier()==ruleId){
					expenseProfileRules = expProfileRules;
				}
			}
		}
		return expenseProfileRules;
	}
	/**
	 * This method gets the ExpenseTypeRule value provided the Appointment and the Rule identifier 
	 * @param apptId
	 * @param ruleId
	 * @return String
	 */
	
	/*public String getExpenseTypeRuleValue(String expenseTypeCode,int ruleId){
		ExpenseTypes expenseTypes = commonDao.findExpenseTypes(expenseTypeCode);
		String ruleValue = "N";
		try{
			if(expenseTypes.getExpenseTypeRulesCollection()!=null){
				for(ExpenseTypeRules expTypeRules : expenseTypes.getExpenseTypeRulesCollection()){
					if(expTypeRules.getRuleIdentifier()==ruleId){
						ruleValue = expTypeRules.getValue();
					}
				}
			}
		}catch(Exception e){
			ruleValue= "N";
		}
		return ruleValue;
	}
	*/
	/***
	 * This method populates the ExpenseErrors with errorCode,errorSource and ExpenseMasterId
	 * @param errorCode
	 * @param errorSource
	 * @param expenseMasters
	 * @return ExpenseErrors
	 */
	
	public void populateErrors(String errorCode,String errorSource, ExpenseMasters expenseMasters, UserProfile user){
		
		
		ErrorMessages errorMessages = commonDao.findErrorCode(errorCode);
		
		/* Populate the Expense error */
		errorMessages.setErrorText(commonDao.findErrorTextByCode(errorCode));

		ExpenseErrors expenseErrors = new ExpenseErrors();
		expenseErrors.setExpmIdentifier(expenseMasters);
		expenseErrors.setErrorCode(errorMessages);
		expenseErrors.setErrorSource(errorSource);
	    expenseErrors.setModifiedUserId(user.getUserId());
		List<ExpenseErrors> errorsList = expenseMasters.getExpenseErrorsCollection();
		if(errorsList==null){
			errorsList = new ArrayList<ExpenseErrors>();
			expenseMasters.setExpenseErrorsCollection(errorsList);
		}
			
		//AI-26449
		if(expenseMasters.getTravelInd().equals("N") && errorCode.equals(IConstants.EXPENSE_COMPARE_WITH_TIME_SHEET_HOURS)){
		}else{
		errorsList.add(expenseErrors); /* Add errors to ErrorsList */
		}
		
		//expenseMasters.getExpenseErrorsCollection().addAll(errorsList); /* update the master with the errorList */
		
		
		return;
		
	}
	
	public void populateErrors(String errorCode,String errorSource, TravelReqMasters treqMasters, UserProfile user){
		
		ErrorMessages errorMessages = commonDao.findErrorCode(errorCode);
		
		/* Populate the Expense error */
		errorMessages.setErrorText(commonDao.findErrorTextByCode(errorCode));

		TravelReqErrors treqErrors = new TravelReqErrors();
		treqErrors.setTreqmIdentifier(treqMasters);
		treqErrors.setErrorCode(errorMessages);
		treqErrors.setErrorSource(errorSource);
	    treqErrors.setModifiedUserId(user.getUserId());
		List<TravelReqErrors> errorsList = treqMasters.getTreqErrorsCollection();
		if(errorsList==null){
			errorsList = new ArrayList<TravelReqErrors>();
			treqMasters.setTreqErrorsCollection(errorsList);
		}
			
		
		errorsList.add(treqErrors); /* Add errors to ErrorsList */
		
		//expenseMasters.getExpenseErrorsCollection().addAll(errorsList); /* update the master with the errorList */
		
		
		return;
		
	}
	
	
	
	public ErrorMessages getErrorCode(String errorCode){
		return commonDao.findErrorCode(errorCode);
	}

	/**
	 * Gets a perticular System Code value
	 * @param systemCode
	 * @return
	 */
	public String getSystemCodeValue(String systemCode) {
		return commonDao.findSystemCodeValue(systemCode);
	}
	
	public ExpenseMasters deleteExpenseErrors(ExpenseMasters expenseMasters,String errorSource) {
		return commonDao.deleteExpenseErrors(expenseMasters, errorSource);
	}
	
	public TravelReqMasters deleteTravelReqErrors(TravelReqMasters treqMasters,String errorSource) {
		return commonDao.deleteTravelReqErrors(treqMasters, errorSource);
	}

	/**
	 * Deletes expense errors with matching error codes. 
	 */
	public void deleteExpenseErrors(ExpenseMasters expenseMasters, List<String> errorCodes) {
		if(expenseMasters == null || errorCodes == null || errorCodes.isEmpty() ||
				expenseMasters.getExpenseErrorsCollection().isEmpty()) return;
		
		commonDao.deleteExpenseErrors(expenseMasters, errorCodes);
	}
	
	/**
	 * Gets the module for an action code
	 * @param actionCode
	 * @return
	 */
	
	public String getRefCode (String actionCode){
		if(actionCode == null || "".equals(actionCode.trim()))
			return IConstants.STRING_BLANK;
		
		List<String> refCodes = commonDao.findModuleIdByActionCode(actionCode);
		if (!refCodes.isEmpty())
			return refCodes.get(0);
		
		return IConstants.STRING_BLANK;
	}
	
	/**
	 * Determines if all existing errors are coding block related or not. This method may be extended to include expense
	 * 
	 * @param userSubject
	 * @param errorsList
	 * @return true if all errors are coding block related
	 */
	
	public boolean allCodingBlockErrors(UserSubject userSubject, List errorsList) {
		boolean allCbErrors = false;
		int minCBError = Integer.parseInt(commonDao.findMinCodingBlockErrorCode());
		int maxCBError = Integer.parseInt(commonDao.findMaxCodingBlockErrorCode());
/*		if (errorsList.isEmpty())
			return false;*/
		if (errorsList.get(0).getClass().equals(AdvanceErrors.class)) {
			for (AdvanceErrors item : (ArrayList<AdvanceErrors>) (errorsList)) {
				if (item.getErrorCode().getIcon() == 2) {
					int errorCode = Integer.parseInt(item.getErrorCode().getErrorCode());
					if (errorCode >= minCBError && errorCode <= maxCBError) {
						allCbErrors = true;
					} else {
						return false;
					}
				}
			}
		}
		return allCbErrors;
	}
	/**
	 * Determines if all existing errors are coding block related or not. This method may be extended to include expense
	 * 
	 * @param userSubject
	 * @param errorsList
	 * @return true if all errors are coding block related
	 */
	
	public boolean allCodingBlockErrorsForExpense(UserSubject userSubject,List errorsList) {
		boolean allCbErrors = false;
		int minCBError = Integer.parseInt(commonDao.findMinCodingBlockErrorCode());
		int maxCBError = Integer.parseInt(commonDao.findMaxCodingBlockErrorCode());
		if (errorsList.isEmpty())
			return false;
		if (errorsList.get(0).getClass().equals(ExpenseErrors.class)) {
			for (ExpenseErrors item : (ArrayList<ExpenseErrors>) (errorsList)) {
				if (item.getErrorCode().getIcon() == 2) {
					int errorCode = Integer.parseInt(item.getErrorCode().getErrorCode());
					if (errorCode >= minCBError && errorCode <= maxCBError) {
						allCbErrors = true;
					}else{
						return false;
					}
				}
			}
		}
		return allCbErrors;
	}	
	
	/**
	 * Determines if all existing travel requisition errors are coding block related or not
	 * 
	 * @param userSubject
	 * @param errorsList
	 * @return true if all errors are coding block related
	 */
	
	public boolean allCodingBlockErrorsTravelReq(UserSubject userSubject,List errorsList) {
		boolean allCbErrors = false;
		int minCBError = Integer.parseInt(commonDao.findMinCodingBlockErrorCode());
		int maxCBError = Integer.parseInt(commonDao.findMaxCodingBlockErrorCode());
		if (errorsList.isEmpty())
			return false;
		if (errorsList.get(0).getClass().equals(TravelReqErrors.class)) {
			for (TravelReqErrors item : (ArrayList<TravelReqErrors>) (errorsList)) {
				if (item.getErrorCode().getIcon() == 2) {
					int errorCode = Integer.parseInt(item.getErrorCode().getErrorCode());
					if (errorCode >= minCBError && errorCode <= maxCBError) {
						allCbErrors = true;
					}else{
						return false;
					}
				}
			}
		}
		return allCbErrors;
	}
	

	/*
	 * finds the Payroll Processing End Date from the Calendar Table
	 */
	public Date findPpEndDate() {
		return commonDao.findPpEndDate();
	}

	/**
	 * Gets the List of Help related urls 
	 * @return
	 */
	public List<RefCodes> getHelpUrls() {
		return commonDao.getHelpUrls();
	}
	
	public String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}

	public String padLeft(String s, int n) {
	    return String.format("%1$#" + n + "s", s);  
	}
	
	public String padLeftWithCharacter(int number, int n, String c) {
		if (n < 1) return number + "";
		String format = "%" + c +  Integer.toString(n+1) + "d";
	    return String.format(format, number);
	}

	
	public String convertToMilitaryTime(String timeAMPM){
		
		String strMilitaryTime = "";
		
		timeAMPM = timeAMPM.toLowerCase(); //something like 8:30am
		
		boolean add12 = (timeAMPM.indexOf("pm") != -1)?true:false;

		//convert hour to int
		int hour = Integer.parseInt(timeAMPM.split(":")[0]);

		int minutes = Integer.parseInt(  timeAMPM.split(":")[1].replaceAll("\\D+","").replaceAll("^0+","") ); //get the letters out of the minute part and get a number out of that, also, strip out leading zeros

		int militaryTime = hour + ((add12)? 12:0);
		
		if(!add12 && militaryTime == 12)
			militaryTime = 0; //account for 12am

		//if length is less than 4 characters then add leading 0.
		if  (Integer.toString(militaryTime).length() <= 4){
			strMilitaryTime = padLeft(Integer.toString(militaryTime),
					4 - Integer.toString(militaryTime).length());
		}
		
		return strMilitaryTime;

	}
	
	public static boolean isUserSuperUser(UserProfile userProfile ){
		return (userProfile.getModules().contains(IConstants.SUPER_USER)) ? true
				: false;
	}

	public String constructExpenseTypesJson(List<ExpenseTypeDisplayBean> expenseTypes) {
		
		StringBuilder buff = new StringBuilder();
		buff.append("[");
		buff.append(getBlankExpenseTypeJson());

		for(ExpenseTypeDisplayBean expenseType : expenseTypes){
			buff.append(",{code:\""+ expenseType.getExpTypeCode() +"\",");
			buff.append("display:\""+ expenseType.getDescription() +"\"}");
		}
		
		buff.append("]");
		
		return buff.toString();
	}
	
	/*
	 * Creates blank <code>ExpenseTypes</code> element JSON.
	 */
	private String getBlankExpenseTypeJson() {
		return "{code:\"\", display:\"\"}";
	}
	/*
	 * gets the facs Agency name based on dept,agency , to and from dates.
	 */
	public String getFacsAgencyName(String deptNo,String agency,Date fromDate,Date toDate){
		String facsName=null;
				facsName=commonDao.findFacsAgencyName(deptNo,agency,fromDate,toDate);
		
		return facsName;
	}
	
	/**
	 * Derives the appropriation year based upon the expense TO date
	 */
	public String getCurrentFiscalYear(Date expenseToDate){
		String fiscalYear = "";
		try {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			expenseToDate = df.parse(df.format(expenseToDate));
			if (getFiscalYearEndDate().after(expenseToDate) || getFiscalYearEndDate().equals(expenseToDate)){
				// Fiscal year is current Calendar year
				fiscalYear = String.valueOf(
						Calendar.getInstance().get(Calendar.YEAR)).substring(2);
			} else {
				// Fiscal year is current Calendar year
				fiscalYear = String.valueOf((Calendar.getInstance().get(Calendar.YEAR) + 1)). substring(2);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fiscalYear;
	}
}
	
