/* 
 * Utility class. Used for sundry, isolated activities such as getting error code descriptions
 */



package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.core.AgencyOptions;
import gov.michigan.dit.timeexpense.model.core.CodingBlockElement;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseProfiles;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypes;
import gov.michigan.dit.timeexpense.model.core.RefCodes;
import gov.michigan.dit.timeexpense.model.core.SystemCodes;
import gov.michigan.dit.timeexpense.model.core.TravelReqErrors;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.db.Appointments;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.model.display.PpEndDate;
import gov.michigan.dit.timeexpense.model.display.ProjectBean;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public class CommonDAO extends AbstractDAO{

	public CommonDAO() {}

	public CommonDAO(EntityManager em) {
		entityManager = em;
	}

	
	/**
	 * Fetches news bulletin items from the System codes table 
	 * 
	 * @param newsSystemCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getNewsItemList(String newsSystemCode) {
          
		 // String queryString = "select s.systemCodesPK.systemCode, s.systemCodesPK.startDate, s.description from SystemCodes s where s.systemCodesPK.systemCode = :code and :currDate between s.systemCodesPK.startDate and s.endDate";
		String queryString = "select s from SystemCodes s where s.systemCodesPK.systemCode LIKE :code and :currDate " +
							"between s.systemCodesPK.startDate and s.endDate order by s.systemCodesPK.startDate desc";
		  List<SystemCodes> newsItems = entityManager.createQuery(queryString)	  
		  .setParameter("code", newsSystemCode).setParameter("currDate",
		  Calendar.getInstance().getTime()).getResultList();
		  
		 
		return newsItems;

	}

	/**
	 * Fetches description using the error code for business errors
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public Map<String, String> findErrorMessage(String startErrorCode,String endErrorCode) {

		
		Map<String,String> errorsMap = new HashMap<String, String>();
		String queryString = "SELECT em FROM ErrorMessages em where em.errorCode between :errCodeStart and :errCodeEnd";
		Query query = entityManager.createQuery(queryString);
		query.setParameter("errCodeStart", startErrorCode);
		query.setParameter("errCodeEnd", endErrorCode);
		
		List<ErrorMessages> errorsList= query.getResultList();
		
		for(int index=0;index<errorsList.size();index++){
			errorsMap.put(errorsList.get(index).getErrorCode(), errorsList.get(index).getErrorText());
		}
		return errorsMap;
	}
	
	/**
	 * 
	 * Gets the system start date for Time and Expense
	 * 
	 */
	
	public Date getTimeAndExpenseStartDate(){
		
		Date teStartDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String finderQuery = "SELECT sc.value from SystemCodes sc where sc.systemCodesPK.systemCode = :systemCode  and :currDate between sc.systemCodesPK.startDate and sc.endDate";
		String teStartDateStr = (String)entityManager.createQuery(finderQuery).setParameter("systemCode", IConstants.TIME_EXPENSE_START_DATE_SYSTEM_CODE).setParameter("currDate",
				  Calendar.getInstance().getTime()).getSingleResult();
		
		try {
			teStartDate = sdf.parse(teStartDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return teStartDate;
	}
	
	public long findAdjustmentIdentifier(){
		
		String finderQuery = "select adj_identifier.nextval from dual";
		BigDecimal adjIdentifier = (BigDecimal)entityManager.createNativeQuery(finderQuery).getSingleResult();
	
		return adjIdentifier.longValue();
	}

	/***
	 *  Common method to delete Expense Errors
	 * @param expenseMasters
	 * @param errorCode
	 * @return
	 */
	public ExpenseMasters deleteExpenseErrors(ExpenseMasters expenseMasters,String errorSource) {
		if (expenseMasters.getExpenseErrorsCollection() != null && !expenseMasters.getExpenseErrorsCollection().isEmpty()) {
			expenseMasters.getExpenseErrorsCollection().size();//added this because of lazy-loading 
		
		
    		for(Iterator<ExpenseErrors> itr = expenseMasters.getExpenseErrorsCollection().iterator(); itr.hasNext();){
    			ExpenseErrors expenseError = itr.next();
    			
    			if(expenseError.getErrorSource() != null && (expenseError.getErrorSource().startsWith(errorSource)) ||
    					(expenseError.getErrorSource().toUpperCase().contains(errorSource))) {
    				itr.remove();
    				entityManager.remove(expenseError);
    			}	
    		}
		}
		//PS[mc]: OpenJPA behaves in a strange way by complaining of duplicates,
		//if new errors are inserted after this method executed without flushing !
		// [mc] Jul 21, 2009: Commenting out as partial data changes 
		// should not be commited to the DB! As for the problem, Actions should delete
		// errors explicitly & flush before normal proceeding.
		//entityManager.flush();
		
		return expenseMasters;
	}
	
	public TravelReqMasters deleteTravelRequisitionErrors(TravelReqMasters treqMasters,String errorSource) {
		if (treqMasters.getTreqErrorsCollection() != null && !treqMasters.getTreqErrorsCollection().isEmpty()) {
			treqMasters.getTreqErrorsCollection().size();//added this because of lazy-loading 
		}
		for(Iterator<TravelReqErrors> itr = treqMasters.getTreqErrorsCollection().iterator(); itr.hasNext();){
			TravelReqErrors treqError = itr.next();
			
			if(treqError.getErrorSource() != null && (treqError.getErrorSource().startsWith(errorSource)) ||
					(treqError.getErrorSource().toUpperCase().contains(errorSource))) {
				itr.remove();
				entityManager.remove(treqError);
			}	
		}

		//PS[mc]: OpenJPA behaves in a strange way by complaining of duplicates,
		//if new errors are inserted after this method executed without flushing !
		// [mc] Jul 21, 2009: Commenting out as partial data changes 
		// should not be commited to the DB! As for the problem, Actions should delete
		// errors explicitly & flush before normal proceeding.
		//entityManager.flush();
		
		return treqMasters;
	}
	
	public TravelReqMasters deleteTravelReqErrors(TravelReqMasters treqMasters,String errorSource) {
		if (treqMasters.getTreqErrorsCollection() != null && !treqMasters.getTreqErrorsCollection().isEmpty()) {
			treqMasters.getTreqErrorsCollection().size();//added this because of lazy-loading 
		}
		for(Iterator<TravelReqErrors> itr = treqMasters.getTreqErrorsCollection().iterator(); itr.hasNext();){
			TravelReqErrors treqError = itr.next();
			
			if(treqError.getErrorSource() != null && (treqError.getErrorSource().startsWith(errorSource)) ||
					(treqError.getErrorSource().toUpperCase().contains(errorSource))) {
				itr.remove();
				entityManager.remove(treqError);
			}	
		}		
		return treqMasters;
	}
	
	/**
	 * Deletes expense errors with matching error codes. 
	 */
	public void deleteExpenseErrors(ExpenseMasters expense, List<String> errorCodes) {
		for(Iterator<ExpenseErrors> itr = expense.getExpenseErrorsCollection().iterator(); itr.hasNext();){
			ExpenseErrors expenseError = itr.next();
			
			if(errorCodes.contains(expenseError.getErrorCode().getErrorCode())){
				itr.remove();
				entityManager.remove(expenseError);
			}	
		}
	}
	
	public List<ExpenseErrors> findErrorsByErrorSource(String errorSource){

		List<ExpenseErrors> expenseErrorsList = entityManager.createQuery("select err from ExpenseErrors err where errorSource = :errorSource")
						.setParameter("errorCode", errorSource).getResultList();
		return expenseErrorsList;	
	}
	
/*	public int deleteErrors(List<ExpenseErrors> errorSource) {
		// TODO Auto-generated method stub
		String errors[] = (String[])errorSource.toArray();
		return entityManager.createQuery("delete from ExpenseErrors expErr where expErr.errorSource in ('"+errors+"'").executeUpdate();
	}
*/	
	public ErrorMessages findErrorCode(String errorCode){
		return entityManager.find(ErrorMessages.class, errorCode);
	}
	

	/***
	 * This method retrieves the agency-wide options for a department and agency.
	 * @param dept
	 * @param agy
	 * @return AgencyOptions
	 */
	public AgencyOptions findAgencyOptions(String dept, String agy) {

		String finderQuery = "SELECT a FROM AgencyOptions a WHERE a.agencyOptionsPK.department = :department AND a.agencyOptionsPK.agency = :agency";

		Query query = entityManager.createQuery(finderQuery);
		query.setParameter("department", dept);
		query.setParameter("agency", agy);

		AgencyOptions agencyOptions = (AgencyOptions) query.getSingleResult();
		return agencyOptions;
	}
	
	/***
	 * This method retrieves the ExpenseProfile valid at the given date given an Appointment.  
	 * @param apptId
	 * @return ExpenseProfiles
	 */
	
	public ExpenseProfiles findExpenseProfiles(int apptId,Date effectiveDate){

		ExpenseProfiles expenseProfiles = null;
		
		String finderQuery = "SELECT ep from ExpenseProfiles ep where ep.appointmentId=:apptId and :effectiveDate between ep.startDate and ep.endDate";

		List<ExpenseProfiles> expProfileList = entityManager.createQuery(finderQuery)
		.setParameter("apptId", apptId)
		.setParameter("effectiveDate", effectiveDate).getResultList();

		if(expProfileList != null){ 
				if(!expProfileList.isEmpty())
					expenseProfiles = expProfileList.get(0); /* get the first element */ 
		}

		return expenseProfiles;
	}
	
	/***
	 * This method retrieves the ExpenseProfile end date for the latest profile within an appointment.  
	 * @param apptId
	 * @return expense profile end date
	 */
	public Date findMaxExpenseProfileEndDateInAppt(int apptId){
		Date lastExpenseProfileDate = null;
		
		String query = "SELECT ep from ExpenseProfiles ep where ep.appointmentId=:apptId" +
				" and ep.endDate = (select max(ep1.endDate) from ExpenseProfiles ep1 where ep1.appointmentId = ep.appointmentId)";

		List<ExpenseProfiles> expProfileList = entityManager.createQuery(query).setParameter("apptId", apptId).getResultList();

		if(!expProfileList.isEmpty()){ 
			lastExpenseProfileDate = expProfileList.get(0).getEndDate(); 
		}

		return lastExpenseProfileDate;
	}
	
	/***
	 * This method retrieves the ExpenseTypes valid at the current date given an Appointment.  
	 * @param apptId
	 * @return ExpenseTypes
	 */
	
	public ExpenseTypes findExpenseTypes(String expenseTypeCode){

		ExpenseTypes expenseTypes = null;
		
		String finderQuery = "SELECT et from ExpenseTypes et where et.expTypeCode=:expTypeCode and :currentDate between et.startDate and et.endDate";

		List<ExpenseTypes> expTypesList = entityManager.createQuery(finderQuery)
		.setParameter("expTypeCode", expenseTypeCode)
		.setParameter("currentDate", Calendar.getInstance().getTime()).getResultList();

		if(expTypesList != null){ 
				if(!expTypesList.isEmpty())
					expenseTypes = expTypesList.get(0); /* get the first element */ 
		}
		return expenseTypes;
	}
	

	public Appointments findAppointment(int apptIdentifier){
		Appointments appt = entityManager.find(Appointments.class, apptIdentifier);
		return appt;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	/**
	 * This is a general purpose method. It returns the value associated
	 * with a particular System Code. 
	 * 
	 * 
	 * @param systemCode
	 * @return
	 * 
	 * ZH - 04/14/2009
	 */
	
	public String findSystemCodeValue (String systemCode){

		
		String finderQuery = "SELECT sc.value from SystemCodes sc where sc.systemCodesPK.systemCode = :systemCode  and :currDate between sc.systemCodesPK.startDate and sc.endDate";
		String systemCodeValue = (String) entityManager.createQuery(finderQuery).setParameter("systemCode", systemCode).setParameter("currDate",
				  Calendar.getInstance().getTime()).getSingleResult();
		
		return systemCodeValue;
}
	/**
	 * Gets module ID from the Ref_Codes table 
	 * @param actionCode
	 * @return
	 */
	
	public List<String> findModuleIdByActionCode (String actionCode){
		
		String finderQuery = "select ref_code from REF_CODES WHERE value =?1";
		List<String> moduleId = entityManager.createNativeQuery(finderQuery).setParameter(1, actionCode).getResultList();
	
		return moduleId;
	}
	
/**
 * gets the minimum error code for coding block validation errors
 * @return
 */
	
public String findMinCodingBlockErrorCode (){
		
		String finderQuery = "SELECT Min(Error_Code) from Error_Messages WHERE Object_Name =?1";
		String minErrorCode = (String) entityManager.createNativeQuery(finderQuery).setParameter(1, "f_cb_validate").getSingleResult();
	
		return minErrorCode;
	}

/**
 * gets the maximum error code for coding block validation errors
 * @return
 */
	

public String findMaxCodingBlockErrorCode (){
	
	String finderQuery = "SELECT Max(Error_Code) from Error_Messages WHERE Object_Name =?1";
	String maxErrorCode = (String) entityManager.createNativeQuery(finderQuery).setParameter(1, "f_cb_validate").getSingleResult();

	return maxErrorCode;
}

/**
 * finds the Payroll Processing End Date from the Calendar Table 
 */

public Date findPpEndDate() {
	String finderQuery = "select c.pp_end_date ppEndDate,c.process_day processDay, c.curr_pp_end_date currPpEndDate from calendar c where trunc(sysdate) = trunc(c.CALENDAR_DATE)";
	Query query = entityManager.createNativeQuery(finderQuery,PpEndDate.class);	
	List<PpEndDate> calendarList = query.getResultList();
	if (calendarList != null && calendarList.size() > 0) {
		int processDay =Integer.parseInt(calendarList.get(0).getProcessDay().trim());
		Date ppEndDate = calendarList.get(0).getPpEndDate();
		if (processDay == 00){
			return CheckProcessDay00();
		}
		if ((processDay == 01) || (processDay == 02) || (processDay == 10)){
			return ppEndDate;			
		} else {
			 return calendarList.get(0).getCurrPpEndDate();			
		}		
	}else{
		return null;
	}
}

 

/**
 * finds the date to be used when the expense is entered on a weekend. Processing
 * day 00. 
 */
	
private Date CheckProcessDay00() {
	String finderQuery = "select c.pp_end_date ppEndDate,c.process_day processDay, c.curr_pp_end_date currPpEndDate from calendar c where trunc(sysdate) - 2  = trunc(c.CALENDAR_DATE)";
	Query query = entityManager.createNativeQuery(finderQuery,PpEndDate.class);	
	List<PpEndDate> calendarList = query.getResultList();
	if (calendarList != null && calendarList.size() > 0) {
		int processDay =Integer.parseInt(calendarList.get(0).getProcessDay().trim());
		Date ppEndDate = calendarList.get(0).getPpEndDate();
		if (processDay < 06){
			 return calendarList.get(0).getCurrPpEndDate();		
		} else {
			 return ppEndDate;			
		}		
	}else{
		return null;
	}
}

/**
 *  Gets the List of Help related urls 
 *  @return
 */
public List<RefCodes> getHelpUrls() {
	String finderQuery = "select rc from RefCodes rc  WHERE rc.refCode like 'URL%' and :currDate between rc.startDate and rc.endDate";
	List<RefCodes> helpUrls = entityManager.createQuery(finderQuery).setParameter("currDate",Calendar.getInstance().getTime()).getResultList();
	return helpUrls;
}

/**
 * Returns true/false if the Manager approval steps exists in the approval path
 * @param dept
 * @param agency
 * @param tku
 * @param dataCategory
 * @return
 */

public boolean findManagerApprovalStep(String dept, String agency, String tku, String dataCategory) {
	
	String finderQuery = "SELECT COUNT (*) FROM tkuopt_approval_paths " + 
						"WHERE department = ? AND agency = ? AND tku = ? " +
						"AND data_category = ? AND approval_step_1 = 'APRW'";

	Query query = entityManager.createNativeQuery(finderQuery,BigDecimal.class);
	query.setParameter(1, dept);
	query.setParameter(2, agency);
	query.setParameter(3, tku);
	query.setParameter(4, dataCategory);
	
	BigDecimal count = (BigDecimal) query.getSingleResult();
	
	if (count.intValue() == 0){
		query.setParameter(3, "AL");
		count = (BigDecimal) query.getSingleResult();
	}
	return count.intValue() == 1 ? true : false;
}
/*
 * finds the facs agency name based on dept,agency,to and from dates
 */
public String findFacsAgencyName(String deptNo,String agency,Date fromDate,Date toDate) {

	String result = null;
	String finderQuery = "select substr(fg.name,5) from facs_agencies fg,agency_facs_agencies ag where AG.FACS_AGY=FG.FACS_AGY and AG.DEPARTMENT=?1 and AG.AGENCY=?2 and "+ 
                         "?3<=ag.END_DATE AND ?4>=ag.START_DATE" ;
	
	Query query = entityManager.createNativeQuery(finderQuery,String.class);
	query.setParameter(1, deptNo);
	query.setParameter(2, agency);
	query.setParameter(3, toDate);
    query.setParameter(4, fromDate);
   List<String> facsName = query.getResultList();


	if (facsName != null && !facsName.isEmpty()) 
		result = facsName.get(0);
	else 
		result="";
	

	return result;
}


}
