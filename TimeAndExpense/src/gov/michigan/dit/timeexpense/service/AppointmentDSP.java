/*
 * AppointmentDSP
 * 
 * Date - January 30, 2009
 * 
 * @author Mohnish Chaudary, Satish Chidura
 * @version 1.0
 */

package gov.michigan.dit.timeexpense.service;

import gov.michigan.dit.timeexpense.dao.AppointmentDAO;
import gov.michigan.dit.timeexpense.model.core.Agency;
import gov.michigan.dit.timeexpense.model.core.Appointment;
import gov.michigan.dit.timeexpense.model.core.AppointmentHistory;
import gov.michigan.dit.timeexpense.model.core.AppointmentListBean;
import gov.michigan.dit.timeexpense.model.core.Department;
import gov.michigan.dit.timeexpense.model.core.Tku;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.db.Appointments;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.EmployeeGeneralInformation;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseProfile;
import gov.michigan.dit.timeexpense.model.display.Location;
import gov.michigan.dit.timeexpense.model.display.MyEmployeesListBean;
import gov.michigan.dit.timeexpense.model.display.StandardDistCoding;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

/**
 * This class provides domain services for appointments. Includes functionality
 * to search for appointments and appointment related employee information.
 * 
 * @author chaudharym, Chiduras
 */
public class AppointmentDSP {

    private AppointmentDAO appointmentDao;
    
    public AppointmentDSP(EntityManager em) {
    	appointmentDao = new AppointmentDAO(em);
 	}
    
    /**
     * Finds all the departments that a user has access to for the given module.
     * 
     * @param user
     * @param moduleId
     * @return list of departments
     */
    public List<Department> getDepartments(UserProfile user, String moduleId) {
	List<Department> depts = null;

	if (moduleId == null || "".equals(moduleId.trim()) || user == null
		|| user.getUserId() == null
		|| "".equals(user.getUserId().trim())) {
	    return depts;
	}

	depts = appointmentDao.findDepartments(user.getUserId(), moduleId);

	return depts;
    }

    /**
     * For a given module, finds the agencies in the given department that the
     * given user has access to.
     * 
     * @param user
     * @param moduleId
     * @param dept
     * @return list of agencies
     */
    public List<Agency> getAgencies(UserProfile user, String moduleId,
	    String dept) {
	List<Agency> agencies = null;

	if (user == null || user.getUserId() == null
		|| "".equals(user.getUserId().trim()) || moduleId == null
		|| "".equals(moduleId.trim()) || dept == null
		|| "".equals(dept.trim())) {
	    return agencies;
	}

	agencies = appointmentDao
		.findAgencies(user.getUserId(), moduleId, dept);

	return agencies;
    }

    /**
     * For a given module, finds the TKUs in the given agency within a
     * department that the given user has access to.
     * 
     * @param user
     * @param moduleId
     * @param dept
     * @param agency
     * @return list of tkus
     */
    public List<Tku> getTkus(UserProfile user, String moduleId, String dept,
	    String agency) {
	List<Tku> tkus = null;

	if (user == null || user.getUserId() == null
		|| "".equals(user.getUserId().trim()) || moduleId == null
		|| "".equals(moduleId.trim()) || dept == null
		|| "".equals(dept.trim()) || agency == null
		|| "".equals(agency.trim())) {
	    return tkus;
	}

	tkus = appointmentDao
		.findTKUs(user.getUserId(), moduleId, dept, agency);

	return tkus;
    }

    /**
     * Finds TKU name using TKU code.
     * 
     * @param tku code
     * @return tku name
     */
    public String findTkuName(String dept, String agency, String tku){
    	return appointmentDao.findTkuName(dept, agency, tku);
    }
    
    /**
     * This method is used to get the appointment information for an employee
     * with the given employee Id and appointments search date. The appointment
     * information is populated into instances of EmployeeListBean class and
     * returned back to the caller
     * 
     * @param empId
     * @param apptSearchDate
     * @return list of Appointments
     */
    public List<AppointmentListBean> getAppointmentsByEmpIdAndDept(int empId,
	    String dept, String agency, String tku, String loggedInUserId, String moduleId, Date apptSearchDate,String requestId) {
	List<AppointmentListBean> apptList = null;
    
	if(requestId!=null && requestId.trim().length()==0)
	{
	if (empId == 0 || dept == null || "".equals(dept)) {
	    return apptList;
	}
	}
	
	agency = (agency == null) ? "" : agency;
	 
	if(requestId!=null && requestId.trim().length()>0)
	{
		List <AppointmentHistory> aphsList = appointmentDao.getAppointmentIdentifier(requestId,moduleId);
		apptList = appointmentDao.findAppointmentsByRequestIdEmpIdAndDeptAgencyTku(empId, dept, agency, tku, 
				(apptSearchDate == null) ? new Date() : apptSearchDate, loggedInUserId, moduleId,requestId,aphsList); 
	}	 
	else if ("".equals(agency) && "".equals(tku)&&!"".equals(dept)){
		// only department
	apptList = appointmentDao.findAppointmentsByEmpIdAndDept(empId, dept,
		(apptSearchDate == null) ? new Date() : apptSearchDate, loggedInUserId, moduleId );
	} else if ("".equals(tku)){
		// department and agency
		apptList = appointmentDao.findAppointmentsByEmpIdAndDeptAgency(empId, dept, agency, 
				(apptSearchDate == null) ? new Date() : apptSearchDate, loggedInUserId, moduleId);
	} else{
		// department, agency and tku
		apptList = appointmentDao.findAppointmentsByEmpIdAndDeptAgencyTku(empId, dept, agency, tku, 
				(apptSearchDate == null) ? new Date() : apptSearchDate, loggedInUserId, moduleId);
	}	
	
	// check exp/adv presence
	populateTransactionPresence(apptList, moduleId);
	
	return apptList;
    }

    public List<MyEmployeesListBean> findAppointmentsByMyemployees(int empIdentifier, Date apptSearchDate) {
    	List<MyEmployeesListBean> apptList = null;
    	apptList = appointmentDao.findAppointmentsByMyemployees(empIdentifier,(apptSearchDate == null) ? new Date() : apptSearchDate);
    	return apptList;
        }    
    
    /**
     * This method is used to get the appointment information for an employee
     * with the given employee Id and appointments search date. If no
     * appointment search date is provided, current date is used as default. The
     * appointment information is populated into instances of EmployeeListBean
     * class and returned back to the caller
     * 
     * @param dept
     * @param agency
     * @param tku
     * @param lastName
     * @param apptSearchDate
     * @return list of Appointments
     */
    public List<AppointmentListBean> getAppointmentsByLastNameInDeptAgencyTku(String dept, String agency, 
    		String tku, String lastName, String loggedInUserId, String moduleId, Date apptSearchDate,String requestId) {
	List<AppointmentListBean> apptList = null;

	// initialize dept, agency and tku if NULL
	requestId = (requestId == null) ?"" :requestId;
	dept = (dept == null) ? "" : dept;
	agency = (agency == null) ? "" : agency;
	tku = (tku == null) ? "" : tku;
	lastName = (lastName == null) ? "" : lastName;

	Date searchDate = (apptSearchDate == null) ? new Date()
		: apptSearchDate;

	// without lastname
	if ("".equals(lastName)) {
		
		if(requestId.length()>0)
		{			
			List <AppointmentHistory> aphsList = appointmentDao.getAppointmentIdentifier(requestId,moduleId);
			if(aphsList.size() > 0 )
				apptList = appointmentDao.findAppointmentsRequestIdByDeptAgencyTku(dept,
					agency, tku, loggedInUserId, moduleId, searchDate,aphsList,lastName);
		}
	    // dept, agency, tku
		else if (agency.length() > 0 && tku.length() > 0)
	    	apptList = appointmentDao.findAppointmentsByDeptAgencyTku(dept,
									agency, tku, loggedInUserId, moduleId, searchDate);
	    // dept, agency
	    else if (agency.length() > 0 && tku.length() == 0)
	    	apptList = appointmentDao.findAppointmentsByDeptAgency(dept,
									agency, loggedInUserId, moduleId, searchDate);
	    // dept
	    else if (agency.length() == 0 && tku.length() == 0)
	    	apptList = appointmentDao.findAppointmentsByDept(dept, loggedInUserId, moduleId, searchDate);

	    // with lastname
	} else {
		if(requestId.length()>0)
		{			
			List <AppointmentHistory> aphsList = appointmentDao.getAppointmentIdentifier(requestId,moduleId);
			if(aphsList.size() > 0 )
				apptList = appointmentDao.findAppointmentsRequestIdByDeptAgencyTku(dept,
					agency, tku, loggedInUserId, moduleId, searchDate,aphsList,lastName);
		}
	    // dept, agency, tku
		else if (agency.length() > 0 && tku.length() > 0)
	    	apptList = appointmentDao.findAppointmentsByLastNameInDeptAgencyTku(dept,
				agency, tku, lastName, loggedInUserId, moduleId, searchDate);
	    // dept, agency
	    else if (agency.length() > 0 && tku.length() == 0)
	    	apptList = appointmentDao.findAppointmentsByLastNameInDeptAgency(dept, agency,
				lastName, loggedInUserId, moduleId, searchDate);
	    // dept
	    else if (agency.length() == 0 && tku.length() == 0)
	    	apptList = appointmentDao.findAppointmentsByLastNameInDept(dept, lastName, loggedInUserId, 
	    								moduleId, searchDate);
	}
	
	// check exp/adv presence
	if(apptList != null &&  apptList.size() > 0) populateTransactionPresence(apptList, moduleId);
	
	return apptList;
    }

    private void populateTransactionPresence(List<AppointmentListBean> appointments, String moduleId) {
    	List<Integer> apptIdList = prepareAppointmentIdList(appointments);
    	List<Integer> apptIdsHavingTrnx = null;
    	
    	if(IConstants.ADVANCE_MANAGER.equalsIgnoreCase(moduleId) || IConstants.ADVANCE_STATEWIDE.equalsIgnoreCase(moduleId)){
        	//verifyAdvanceExistence(appts);
    		apptIdsHavingTrnx = appointmentDao.findAppointmentIdsHavingAdvances(apptIdList);
    	}else if(IConstants.EXPENSE_MANAGER.equalsIgnoreCase(moduleId) || IConstants.EXPENSE_STATEWIDE.equalsIgnoreCase(moduleId)){
    		//verifyExpenseExistence(appts);
    		apptIdsHavingTrnx = appointmentDao.findAppointmentIdsHavingExpenses(apptIdList);
    	} else if(IConstants.TRAVEL_REQUISITIONS_MANAGER.equalsIgnoreCase(moduleId) || IConstants.TRAVEL_REQUISITIONS_STATEWIDE.equalsIgnoreCase(moduleId)){
    		apptIdsHavingTrnx = appointmentDao.findAppointmentIdsHavingTravelRequisitions(apptIdList);
    	}

    	for(AppointmentListBean appt: appointments){
    		appt.setTransactionPresent(apptIdsHavingTrnx.contains(appt.getAppointmentId()));
    	}
    }

    private List<Integer> prepareAppointmentIdList(List<AppointmentListBean> appointments) {
    	List<Integer> apptIdList = new ArrayList<Integer>(appointments.size());
    	
    	for(AppointmentListBean appt: appointments){
    		apptIdList.add(appt.getAppointmentId());
    	}
    	
		return apptIdList;
	}

	public void verifyExpenseExistence(List<AppointmentListBean> appointments){
    	
    	
    }    
    
    
	/**
     * Finds <code>AppointmentHistory</code> for an appointment for a given date.
     * 
     * @param appointment date
     * @author chaudharyM
     */
    public AppointmentHistory getAppointmentHistory(Integer apptId, Date searchDate) {
    	return appointmentDao.findAppointmentHistory(apptId, searchDate);
    }
    
    /**
     * Gets Appoitnments 
     * @param apptId
     * @return Appointments
     */
    public Appointments getAppointments(int apptIdentifier) {

	
	return appointmentDao.findAppointment(apptIdentifier);

    }

    /**
     * Finds an appointment for a given appointment Id.
     * 
     * @param apptId - Appointment Id
     * @return Appointment
     */
    public Appointment findAppointment(int apptId){
    	return appointmentDao.findAppointmentById(apptId);
    }
    
      /**
     * This method returns Employee Header Info By appt Id
     * 
     * @param apptId
     * @return List<EmployeeHeaderBean>
     */
    public List<EmployeeHeaderBean> getEmployeeHeaderInfo(int apptId) {
	List<EmployeeHeaderBean> empHeaderList = null;

	empHeaderList = appointmentDao.findEmployeeHeaderInfo(apptId);

	return empHeaderList;

    }

    /**
     * This method returns Employee Header Info by Employee Id
     * 
     * @param empId
     * @return List<EmployeeHeaderBean>
     */
    public List<EmployeeHeaderBean> getEmployeeHeaderInfoByEmpId(int empId) {
	List<EmployeeHeaderBean> empHeaderList = null;

	empHeaderList = appointmentDao.findEmployeeHeaderInfoByEmpId(empId);

	if (empHeaderList.size() > 1)
	    return null;
	else
	    return empHeaderList;

    }
    
    /**
     * This method returns Employee Header Info by Employee Id
     * 
     * @param empId
     * @return List<EmployeeHeaderBean>
     */
    public List<EmployeeHeaderBean> getEmployeeHeaderInfoByApptId(int apptId, Date searchDate) {
	List<EmployeeHeaderBean> empHeaderList = null;

	empHeaderList = appointmentDao.findEmployeeHeaderInfoByApptId(apptId, searchDate);

	if (empHeaderList.size() > 1)
	    return null;
	else
	    return empHeaderList;

    }

      
    /**
     * This method returns Employee General Information for Employee
     * 
     * @param apptId
     * @param effectiveDate
     * @return EmployeeGeneralInformation
     */
    
   public EmployeeGeneralInformation getEmployeeGeneralInfo(int apptId, Date effectiveDate) {
	 EmployeeGeneralInformation empInfo  = null;
	empInfo = (EmployeeGeneralInformation) appointmentDao.findEmployeeGeneralInfo(apptId, effectiveDate);
	  setClassTypeAvgHrs(empInfo, apptId, effectiveDate);
		
	return empInfo;
   }
    
   /**
    * This method returns Employee General Information for manager/statewide
    * 
    * @param apptId
    * @param effectiveDate
    * @param userId
    * @return EmployeeGeneralInformation
    */
   
  public EmployeeGeneralInformation getEmployeeGeneralInfo(int apptId, Date effectiveDate,String userId) {
	 EmployeeGeneralInformation empInfo  = null;
	empInfo = (EmployeeGeneralInformation) appointmentDao.findEmployeeGeneralInfoManagerStatewide(apptId, effectiveDate,userId);
     setClassTypeAvgHrs(empInfo, apptId, effectiveDate);
	
	
	return empInfo;
  } 
  
  /**
 * @param empInfo
 * @param apptId
 * @param effectiveDate
 */
private void setClassTypeAvgHrs(EmployeeGeneralInformation empInfo, int apptId, Date effectiveDate){
	  if(empInfo != null){
			if (empInfo.getClassType().equalsIgnoreCase("X")) {
				empInfo.setClassType("Classified");
			}
			else if (empInfo.getClassType().equalsIgnoreCase("U")) {
				empInfo.setClassType("Unclassified");
			}
			else if (empInfo.getClassType().equalsIgnoreCase("D")) {
				empInfo.setClassType("Per Diem");
			}
			else if (empInfo.getClassType().equalsIgnoreCase("C")) {
				empInfo.setClassType("Contractual");
			}
		
		    int averageHrs = appointmentDao.findAverageHrs(apptId, effectiveDate);
		    empInfo.setAverageHrs(averageHrs);
		}
	  
  }


    
     /**
	 * This Method Returns the expense profiles for an appointment.
	 * 
	 * @param apptId
	 * @param effectiveDate
	 * @return List<ExpenseProfile>
	 */
	public List<ExpenseProfile> getEmployeeExpenseProfiles(int apptId,Date effectiveDate) {
		List<ExpenseProfile> expProfileList = appointmentDao.findExpenseProfiles(apptId, effectiveDate);
		return expProfileList;
	}
    
    /** This method finds the employee work location
     * @param apptId
     * @param effectiveDate
     * @return Location
     */
    public Location getEmployeeWorkLocation(int apptId, Date effectiveDate) {

	return (Location) appointmentDao
		.findWorkLocation(apptId, effectiveDate);
    }

    /** This methods finds the employee home location.
     * @param empId
     * @param effectiveDate
     * @return Location
     */
    public Location getEmployeeHomeLocation(int empId, Date effectiveDate) {

	return (Location) appointmentDao.findHomeLocation(empId, effectiveDate);
    }



    /** This method finds the distribution start date.
     * @param effectiveDate
     * @return distribution start date.
     */
    public Date getDistributionStartDate(Date effectiveDate){
	
	return appointmentDao.findDistStartDate(effectiveDate);
		
    }
    
        
    /** This methods gets the List of Standard Distribution.
     * @param empId
     * @param effectiveDate
     * @return List<StandardDistCoding>
     */
    public List<StandardDistCoding> getStandardDistribution(int apptId, Date effectiveDate, String userId, Date distStartDate) {
             
	return (List<StandardDistCoding>) appointmentDao.findDefaultDistributionsExpense(apptId, effectiveDate, userId, distStartDate); }

    
    

    /** This methods finds the effective date.
     * @param apptId
     * @return effectiveDate
     */
    public Date getEffectiveDate(int apptId) {
	return appointmentDao.findEffectiveDate(apptId);
	
    }

    
    public AppointmentDAO getAppointmentDao() {
	return appointmentDao;
    }

    public void setAppointmentDao(AppointmentDAO dao) {
	this.appointmentDao = dao;
    }
    
    /**
     * Convenience method to get check if employee has appointments eligible for new requests 
     * @param expenseStartDate
     * @param expenseEndDate
     * @param moduleId
     * @param empId
     * @param userId
     * @return
     */
    
    public List<AppointmentsBean> getActiveAppointmentsByExpDatesEmployee(
    	    Date expenseStartDate, Date expenseEndDate, String moduleId,
    	    long empId, String userId) {
    	
    	return appointmentDao.findActiveAppointmentsByExpDatesEmployee (expenseStartDate, expenseEndDate, 
    			moduleId, empId, userId);
    }

    /**
     * gets the facs agency
     * @param fromDate
     * @param toDate
     * @param appointmentId
     * @return facs agency
     */
	public String getFacsAgency(Date fromDate, Date toDate, long appointmentId) {
		List<AppointmentsBean> listFacsAgencies = appointmentDao
				.findFacsAgencyByExpenseDatesByEmployee(fromDate, toDate,
						appointmentId);
		if (!listFacsAgencies.isEmpty())
			return listFacsAgencies.get(0).getFacs_agy();
		else
			return "";
	}

    /**
     * Find active appointment start and end date range
     */
	public AppointmentsBean findActiveAppointmentDateSpan(long appointmentId) {
		return appointmentDao.findActiveAppointmentDateSpan(appointmentId);
	}

    /**
     * Finds appointments for the given employee and search date.
     * 
     * @param searchDate
     * @return <code>AppointmentHistory</code>
     */
    public List<AppointmentsBean> findAppointmentHistory(int empId, Date searchDate){
    	return appointmentDao.findAppointmentHistory(empId, searchDate);
    }
    /**
     * Tests the current status of an employee
     * @param empId
     * @return
     * AI 19898 Employee active/inactive status checks.
     */
    
    public boolean isEmployeeActive (int empId){
    	boolean isActive = false;
    	if (appointmentDao.findLatestActiveAppointmentHistoryEmployee(empId) != null)
    		isActive = true;
    	return isActive;
    }
    
    /**
     * Does the appointment belong to the employee?
     * @param empId
     * @param apptId
     * @return
     */
    
    public boolean isEmployeeAppointment (int empId, int apptId){
    	boolean isEmployeeAppointment = false;
    	Integer employeeId = appointmentDao.findEmployeeByAppointmentId(apptId);
    	if (employeeId != null && employeeId.intValue() == empId)
    		isEmployeeAppointment = true;
    	return isEmployeeAppointment;
    }
    
    /**
     * Checks for an inactive status for an employee for a specific date
     * @param appointmentId
     * @param expenseToDate
     * @return
     */
    
    public boolean getInactiveStatusForExpenseToDate (int appointmentId, Date expenseToDate){
    	boolean result = true;
    	if (appointmentDao.findInactiveStatusForExpenseToDateLeave(appointmentId, expenseToDate) == 0){
    		// no valid date range found in leave of absence. Now try terminated
    		if (appointmentDao.findInactiveStatusForExpenseToDateTerminated(appointmentId, expenseToDate) == 0){
    			// still no valid date range found
    			result = false;
    		}
    	}
    		return result;
    }
    
    /**
     * Retrieves appointments without a search date
     * @param empId
     * @param dept
     * @param agency
     * @param tku
     * @param loggedInUserId
     * @param moduleId
     * @return
     */
    
    public List<AppointmentListBean> getAppointmentsByEmpIdAndDeptNoSearchDate (int empId,
		    String dept, String agency, String tku, String loggedInUserId, String moduleId) {
		List<AppointmentListBean> apptList = null;

		if (empId == 0 || dept == null || "".equals(dept)) {
		    return apptList;
		}
		
		agency = (agency == null) ? "" : agency;
		
		if ("".equals(agency) && "".equals(tku)){
			// only department
		apptList = appointmentDao.findAppointmentsByEmpIdAndDeptNoSearchDate(empId, dept,
			loggedInUserId, moduleId);
		} else if ("".equals(tku)){
			// department and agency
			apptList = appointmentDao.findAppointmentsByEmpIdAndDeptAgencyNoSearchDate(empId, dept, agency, 
					loggedInUserId, moduleId);
		} else{
			// department, agency and tku
			apptList = appointmentDao.findAppointmentsByEmpIdAndDeptAgencyTkuNoSearchDate(empId, dept, agency, tku, 
					loggedInUserId, moduleId);
		}	
	
	// check exp/adv presence
	populateTransactionPresence(apptList, moduleId);
	
	return apptList;
    }
    
    public AppointmentHistory getAppointmentHistory(Integer apptId) {
    	return appointmentDao.findAppointmentHistory(apptId);
    }

    public String getEmptyStrElementJson() {
		return "{code:\"\",display:\"\"}";
	}

    public String  getTravelExpense()
    {    	
    	StringBuilder buff = new StringBuilder();   
    	buff.append("[");
		buff.append(getEmptyStrElementJson());
		buff.append(",");
		buff.append(getTravelExpenseValues()); 
		buff.append("]"); 
		return buff.toString(); 
    }   
   
	public String getTravelExpenseValues() {
		StringBuilder buff = new StringBuilder();
		
		buff.append("{code:\"");
		buff.append("Y");
		buff.append("\",display:\"");
		buff.append("Travel");
		buff.append("\"},");
		buff.append("{code:\"");
		buff.append("N");
		buff.append("\",display:\"");
		buff.append("Non Travel");
		buff.append("\"},");
		buff.append("{code:\"");
		buff.append("B");
		buff.append("\",display:\"");
		buff.append("Both");
		buff.append("\"}");
		
		return buff.toString();

	}
	
    public String  getExpenseTypeState()
    {    	
    	StringBuilder buff = new StringBuilder();   
    	buff.append("[");
		buff.append(getEmptyStrElementJson());
		buff.append(",");
		buff.append(getExpenseTypeStateValues()); 
		buff.append("]"); 
		return buff.toString(); 
    }   
   
	public String getExpenseTypeStateValues() {
		StringBuilder buff = new StringBuilder();
		
		buff.append("{code:\"");
		buff.append("N");
		buff.append("\",display:\"");
		buff.append("In State");
		buff.append("\"},");
		buff.append("{code:\"");
		buff.append("Y");
		buff.append("\",display:\"");
		buff.append("Out Of State");
		buff.append("\"}");
		
		return buff.toString();

	}

}
