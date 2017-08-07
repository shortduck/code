package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.core.Agency;
import gov.michigan.dit.timeexpense.model.core.Appointment;
import gov.michigan.dit.timeexpense.model.core.AppointmentHistory;
import gov.michigan.dit.timeexpense.model.core.AppointmentListBean;
import gov.michigan.dit.timeexpense.model.core.Department;
import gov.michigan.dit.timeexpense.model.core.Tku;
import gov.michigan.dit.timeexpense.model.core.User;
import gov.michigan.dit.timeexpense.model.db.Appointments;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.EmployeeGeneralInformation;
import gov.michigan.dit.timeexpense.model.display.EmployeeHeaderBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseProfile;
import gov.michigan.dit.timeexpense.model.display.Location;
import gov.michigan.dit.timeexpense.model.display.MyEmployeesListBean;
import gov.michigan.dit.timeexpense.model.display.StandardDistCoding;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * @author GhoshS
 * 
 */
@SuppressWarnings("unchecked")
public class AppointmentDAO extends AbstractDAO {

    Logger logger = Logger.getLogger(AppointmentDAO.class);

    private final String QUERY_AND_HISTORY_CONDITION = "select appt_info from AppointmentListBean appt_info where :apptSearchDate between appt_info.appointmentDate and "
			+ "appt_info.departureOrEndDate and appt_info.apptStatusCode not like 'X%' and "
			+ "appt_info.appointmentEnd = (select max(ap1.appointmentEnd) from AppointmentListBean ap1 "
			+ "where ap1.appointmentId = appt_info.appointmentId "
			+ "and ap1.appointmentEnd >= ap1.appointmentStart and :apptSearchDate between ap1.appointmentDate and "
			+ "ap1.departureOrEndDate)"; 
    
    private final String QUERY_AND_HISTORY_CONDITION_NO_SEARCH_DATE = "select appt_info from AppointmentListBean appt_info where " +
	"appt_info.appointmentEnd = (select max(ap1.appointmentEnd) from AppointmentListBean ap1 " +
	"where ap1.appointmentId = appt_info.appointmentId " +
	"and ap1.appointmentEnd >= ap1.appointmentStart)"; 
    
    private final String APPOINTMENTS_IN_DATE_RANGE_QUERY = "select appt_info from AppointmentListBean appt_info where :startDate <= appt_info.appointmentEnd and :endDate >= appt_info.appointmentDate";

    private final String LASTNAME_SUBQUERY_CONDITION = " and appt_info.lastName like :lastName ";
    private final String DEPT_SUBQUERY_CONDITION = " and appt_info.department = :dept";
    private final String AGENCY_SUBQUERY_CONDITION = " and appt_info.agency = :agency ";
    private final String TKU_SUBQUERY_CONDITION = " and appt_info.tku = :tku ";
    private final String LN_ORDERBY_SUBQUERY_CONDITION = " order by appt_info.lastName ";
    private final String EMPID_SUBQUERY_CONDITION = " and appt_info.employeeId = :empId ";
   
   
	private String SECURITY_SUB_SELECT_QUERY = " and exists (select security.id from Security security where security.user = :user "
		+ " and security.moduleId = :moduleId"
		+" AND (appt_info.department = security.department OR (security.department='AL'))"
		+" AND (appt_info.agency = security.agency OR (security.agency='AL'))"
		+" AND (appt_info.tku = security.tku OR (security.tku='AL'))) ";
    
    private final String APPT_BY_EMP_ID_DEPT_SECURITY_QUERY_ORDERBYLN = QUERY_AND_HISTORY_CONDITION
    + EMPID_SUBQUERY_CONDITION + DEPT_SUBQUERY_CONDITION + SECURITY_SUB_SELECT_QUERY + LN_ORDERBY_SUBQUERY_CONDITION;
    
    private final String APPT_BY_EMP_ID_DEPT_SECURITY_QUERY_ORDERBYLN_NO_SEARCH_DATE = QUERY_AND_HISTORY_CONDITION_NO_SEARCH_DATE
    + EMPID_SUBQUERY_CONDITION + DEPT_SUBQUERY_CONDITION + SECURITY_SUB_SELECT_QUERY + LN_ORDERBY_SUBQUERY_CONDITION;
    
    private final String APPT_BY_EMP_ID_DEPT_AGENCY_SECURITY_QUERY_ORDERBYLN = QUERY_AND_HISTORY_CONDITION
    + EMPID_SUBQUERY_CONDITION + DEPT_SUBQUERY_CONDITION + AGENCY_SUBQUERY_CONDITION 
    + SECURITY_SUB_SELECT_QUERY  
    + LN_ORDERBY_SUBQUERY_CONDITION;
    
    private final String APPT_BY_EMP_ID_DEPT_AGENCY_SECURITY_QUERY_ORDERBYLN_NO_SEARCH_DATE = QUERY_AND_HISTORY_CONDITION_NO_SEARCH_DATE
    + EMPID_SUBQUERY_CONDITION + DEPT_SUBQUERY_CONDITION + AGENCY_SUBQUERY_CONDITION 
    + SECURITY_SUB_SELECT_QUERY  
    + LN_ORDERBY_SUBQUERY_CONDITION;
    
   
    private final String APPT_BY_DEPT_QUERY = QUERY_AND_HISTORY_CONDITION
	    + DEPT_SUBQUERY_CONDITION;
    
    private final String APPT_BY_DEPT_SECURITY_QUERY_ORDERBYLN = QUERY_AND_HISTORY_CONDITION
    + DEPT_SUBQUERY_CONDITION + SECURITY_SUB_SELECT_QUERY + LN_ORDERBY_SUBQUERY_CONDITION;
    
    
    private final String APPT_BY_DEPT_AGENCY_QUERY = APPT_BY_DEPT_QUERY
	    + AGENCY_SUBQUERY_CONDITION;
    
    private final String APPT_BY_DEPT_AGENCY_SECURITY_QUERY_ORDERBYLN = APPT_BY_DEPT_QUERY
    + AGENCY_SUBQUERY_CONDITION + SECURITY_SUB_SELECT_QUERY + LN_ORDERBY_SUBQUERY_CONDITION;

    
    private final String APPT_BY_DEPT_AGENCY_TKU_QUERY = APPT_BY_DEPT_AGENCY_QUERY
    + TKU_SUBQUERY_CONDITION;
    
    private final String APPT_BY_EMP_ID_DEPT_AGENCY_TKU_SECURITY_QUERY_ORDERBYLN = QUERY_AND_HISTORY_CONDITION
    + EMPID_SUBQUERY_CONDITION + DEPT_SUBQUERY_CONDITION + AGENCY_SUBQUERY_CONDITION + TKU_SUBQUERY_CONDITION
    + SECURITY_SUB_SELECT_QUERY  
    + LN_ORDERBY_SUBQUERY_CONDITION;
    
    private final String APPT_BY_EMP_ID_DEPT_AGENCY_TKU_SECURITY_QUERY_ORDERBYLN_NO_SEARCH_DATE = QUERY_AND_HISTORY_CONDITION_NO_SEARCH_DATE
    + EMPID_SUBQUERY_CONDITION + DEPT_SUBQUERY_CONDITION + AGENCY_SUBQUERY_CONDITION + TKU_SUBQUERY_CONDITION
    + SECURITY_SUB_SELECT_QUERY  
    + LN_ORDERBY_SUBQUERY_CONDITION;
    
    private final String APPT_BY_DEPT_AGENCY_TKU_SECURITY_QUERY_ORDERBYLN = APPT_BY_DEPT_AGENCY_QUERY
    + TKU_SUBQUERY_CONDITION + SECURITY_SUB_SELECT_QUERY + LN_ORDERBY_SUBQUERY_CONDITION;
    
    
    private final String APPT_BY_LAST_NAME_DEPT_QUERY = APPT_BY_DEPT_QUERY
	    + LASTNAME_SUBQUERY_CONDITION;
    
    private final String APPT_BY_LAST_NAME_DEPT_SECURITY_QUERY_ORDERBYLN = APPT_BY_DEPT_QUERY
    + LASTNAME_SUBQUERY_CONDITION + SECURITY_SUB_SELECT_QUERY + LN_ORDERBY_SUBQUERY_CONDITION;
    
    private final String APPT_BY_LAST_NAME_DEPT_AGENCY_QUERY = APPT_BY_LAST_NAME_DEPT_QUERY
	    + AGENCY_SUBQUERY_CONDITION;
    
    private final String APPT_BY_LAST_NAME_DEPT_AGENCY_SECURITY_QUERY_ORDERBYLN = APPT_BY_LAST_NAME_DEPT_QUERY
    + AGENCY_SUBQUERY_CONDITION + SECURITY_SUB_SELECT_QUERY + LN_ORDERBY_SUBQUERY_CONDITION;
    
    
    private final String APPT_BY_LAST_NAME_DEPT_AGENCY_TKU_QUERY = APPT_BY_LAST_NAME_DEPT_AGENCY_QUERY
	    + TKU_SUBQUERY_CONDITION;
    
    private final String APPT_BY_LAST_NAME_DEPT_AGENCY_TKU_SECURITY_QUERY_ORDERBYLN = APPT_BY_LAST_NAME_DEPT_AGENCY_QUERY
    + TKU_SUBQUERY_CONDITION + SECURITY_SUB_SELECT_QUERY + LN_ORDERBY_SUBQUERY_CONDITION;
    
    
    private final String EMP_APPOINTMENTS_IN_DATE_RANGE_QUERY = APPOINTMENTS_IN_DATE_RANGE_QUERY
	    + EMPID_SUBQUERY_CONDITION;
    private final String EMP_APPOINTMENTS_IN_DATE_RANGE_QUERY_ORDERBYLN = APPOINTMENTS_IN_DATE_RANGE_QUERY
    + EMPID_SUBQUERY_CONDITION+LN_ORDERBY_SUBQUERY_CONDITION;    
    private final String EMP_GENERAL_INFO_SELECT = " SELECT    RTRIM (personnel_histories.last_name) || ', '"
	    + " || RTRIM (personnel_histories.first_name)    || ' ' "
	    + " || RTRIM (personnel_histories.middle_name)   || ' ' "
	    + " || RTRIM (personnel_histories.suffix)  employeeName,"
	    + " employees.ssn ssn,"
	    + " appointment_histories.department department,"
	    + " appointment_histories.agency agency, "
	    + " appointment_histories.tku tku,"
	    + " tkus.name tkuName,"
	    + " personnel_histories.fmla_expiration_date fmlaExpirationDate, "
	    + " personnel_histories.start_date phStartDate, "
	    + " personnel_histories.end_date phEndDate, "
	    + " appointment_histories.appt_identifier apptIdentifier,"
	    + " appointment_histories.move_date moveDate,"
	    + " appointment_histories.appt_type apptType,"
	    + " appointment_histories.appt_method apptMethod,"
	    + " appointment_histories.appt_duration apptDuration,"
	    + " appointment_histories.appointment_date appointmentDate,"
	    + " appointment_histories.start_date ahStartDate,"
	    + " appointment_histories.end_date ahEndDate,"
	    + " appointment_histories.departure_date departureDate,"
	    + " appointment_histories.on_call_code onCallCode,"
	    + " appointment_histories.appt_status_code apptStatusCode,"
	    + " appointment_histories.retirement_code retirementCode,"
	    + " appointment_histories.plan plan,"
	    + " appointment_histories.plan_hours planHours,"
	    + " appointment_histories.plan_expiration_date planExpirationDate,"
	    + " appointment_histories.position_number positionNumber,"
	    + " appointments.position_id positionId,"
	    + " f_class_type (appointment_histories.hjbcd_identifier) classType,"
	    + " appointment_histories.position_type positionType,"
	    + " appointment_histories.position_schedule positionSchedule,"
	    + " appointment_histories.position_duration positionDuration,"
	    + " appointment_histories.bargaining_unit bargainingUnit,"
	    + " appointment_histories.class_code classCode,"
	    + " appointment_histories.work_site workSite,"
	    + " appointment_histories.work_county workCounty,"
	    + " appointment_histories.pcel pcel,"
	    + " appointment_histories.flsa_code flsaCode,"
	    + " appointment_histories.flsa_expiration_date flsaExpirationDate,"
	    + " appointment_histories.standard_hours_reg standardHoursRegular,"
	    + " appointment_histories.standard_hours_shift2 standardHoursShift2,"
	    + " appointment_histories.standard_hours_shift3 standardHoursShift3,"
	    + " appointment_histories.salary_class salaryClass,"
	    + " hrmn_job_codes.job_code jobCode,"
	    + " appointment_histories.dept_code departmentCode,"
	    + " appointment_histories.exempt exempt,"
	    + " employees.emp_identifier employeeIdentifier,"
	    + " hrmn_dept_codes.name hrmnDeptName,"
	    + " apsc.description apscDescription,"
	    + " hrmn_supervisor_employees.emp_identifier supervisorEmployeeId,"
	    + " f_supervisor_name(hrmn_supervisor_employees.emp_identifier) supervisorName";

    private final String EMP_GENERAL_INFO_FROM_SECURITY = " FROM appointments,appointment_histories, personnel_histories, employees,"
	    + " hrmn_dept_codes, hrmn_job_codes,  appointment_status_codes apsc, tkus,  hrmn_supervisor_employees,  security";
   
   private final String EMP_GENERAL_INFO_WHERE_SECURITY = " WHERE (employees.emp_identifier = appointments.emp_identifier) "
	    + " AND (appointments.appt_identifier =   appointment_histories.appt_identifier)"
	    + " AND (employees.emp_identifier = personnel_histories.emp_identifier) "
	    + " AND (appointment_histories.department = hrmn_dept_codes.department) "
	    + " AND (appointment_histories.agency = hrmn_dept_codes.agency) "
	    + " AND (appointment_histories.dept_code = hrmn_dept_codes.dept_code)"
	    + " AND (appointment_histories.hjbcd_identifier =  hrmn_job_codes.hjbcd_identifier)"
	    + " AND appointment_histories.tku = tkus.tku "
	    + " AND appointment_histories.department = tkus.department"
	    + " AND appointment_histories.agency = tkus.agency "
	    + " AND (apsc.appt_status_code = appointment_histories.appt_status_code)"
	    + " AND (appointment_histories.supervisor =  hrmn_supervisor_employees.supervisor (+))"
	    + " AND SYSDATE BETWEEN hrmn_supervisor_employees.start_date (+) AND  hrmn_supervisor_employees.end_date (+)"
	    + " AND appointments.appt_identifier = ?1 "
	    + " AND ?2 BETWEEN appointment_histories.appointment_date"
	    + " AND appointment_histories.departure_or_end_date"
	    + " AND appointment_histories.end_date = (select max(ap1.end_date) from APPOINTMENT_HISTORIES ap1"
		+ " WHERE ap1.appt_identifier = appointment_histories.appt_identifier"
		+ " AND ap1.end_date >= ap1.start_date)"
	    + " AND ?2 BETWEEN personnel_histories.start_date "
	    + " AND  personnel_histories.end_date "
	    + " AND security.user_id = ?3 "
	    + " AND security.module_id = 'CMNW001' "
	    + " AND (appointment_histories.department BETWEEN DECODE (security.department, 'AL', '00', security.department) "
	    + " AND  DECODE (security.department, 'AL','99', security.department)) "
	    + " AND (appointment_histories.agency BETWEEN DECODE (security.agency, 'AL', '00', security.agency) "
	    + " AND  DECODE (security.agency, 'AL', '99', security.agency))"
	    + " AND (appointment_histories.tku BETWEEN DECODE (security.tku, 'AL', '00', security.tku)"
	    + " AND  DECODE (security.tku,'AL', '99', security.tku))";

   

   
   private final String EMP_GENERAL_INFO_FROM = " FROM appointments,appointment_histories, personnel_histories, employees,"
	    + " hrmn_dept_codes, hrmn_job_codes,  appointment_status_codes apsc, tkus,  hrmn_supervisor_employees";

    private final String EMP_GENERAL_INFO_WHERE = " WHERE (employees.emp_identifier = appointments.emp_identifier) "
	    + " AND (appointments.appt_identifier =   appointment_histories.appt_identifier)"
	    + " AND (employees.emp_identifier = personnel_histories.emp_identifier) "
	    + " AND (appointment_histories.department = hrmn_dept_codes.department) "
	    + " AND (appointment_histories.agency = hrmn_dept_codes.agency) "
	    + " AND (appointment_histories.dept_code = hrmn_dept_codes.dept_code)"
	    + " AND (appointment_histories.hjbcd_identifier =  hrmn_job_codes.hjbcd_identifier)"
	    + " AND appointment_histories.tku = tkus.tku "
	    + " AND appointment_histories.department = tkus.department"
	    + " AND appointment_histories.agency = tkus.agency "
	    + " AND (apsc.appt_status_code = appointment_histories.appt_status_code)"
	    + " AND (appointment_histories.supervisor =  hrmn_supervisor_employees.supervisor (+)) "
	    + " AND SYSDATE BETWEEN hrmn_supervisor_employees.start_date (+) " +
	    		" AND  hrmn_supervisor_employees.end_date (+) "
	    + " AND appointments.appt_identifier = ?1 "
	    + " AND ?2 BETWEEN appointment_histories.appointment_date"
	    + " AND appointment_histories.departure_or_end_date"
	    + " AND appointment_histories.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1"
		+ " WHERE ap1.appt_identifier = appointment_histories.appt_identifier"
		+ " AND ap1.end_date >= ap1.start_date) "
	    + " AND ?2 BETWEEN personnel_histories.start_date "
	    + " AND  personnel_histories.end_date ";
   		
   

    public AppointmentDAO() {}

    public AppointmentDAO(EntityManager em) {
    	super(em);
    }
    
    
    /**
     * This method retrieves the active appointments list for a given expense
     * date range
     * 
     * @param expenseStartDate
     * @param expenseEndDate
     * @param moduleId
     * @param empId
     * @param userId
     * @return list of appointments
     */

    public List<AppointmentsBean> findActiveAppointmentsByExpDatesEmployee(
    	    Date expenseStartDate, Date expenseEndDate, String moduleId,
    	    long empId, String userId) {

    	String finderQuery = "SELECT a.APPT_IDENTIFIER,a.EMP_IDENTIFIER,a.DEPARTMENT,a.AGENCY,a.TKU, "
    		+ "a.POSITION_ID, a.PROCESS_LEVEL,ah.START_DATE,ah.END_DATE, ah.APPOINTMENT_DATE, "
    		+ "a.DEPT_CODE, ah.DEPARTURE_DATE departureDate FROM "
    		+ "APPOINTMENT_HISTORIES AH, APPOINTMENTS A WHERE AH.APPT_IDENTIFIER = A.APPT_IDENTIFIER "
    		+ "AND A.EMP_IDENTIFIER = ?1 AND ?2 <= AH.DEPARTURE_OR_END_DATE AND ?3>= AH.APPOINTMENT_DATE "
    		+ "AND ah.APPT_STATUS_CODE not like 'X%' "
    		+ "and ah.START_DATE = (select max(START_DATE) from APPOINTMENT_HISTORIES where APPT_IDENTIFIER = AH.APPT_IDENTIFIER "
    		+ "AND ?4 <= END_DATE AND ?5 >= START_DATE)";

    	Query query = entityManager.createNativeQuery(finderQuery,
    		AppointmentsBean.class);
    	query.setParameter(1, empId);
    	query.setParameter(2, expenseStartDate);
    	query.setParameter(3, expenseEndDate);
    	query.setParameter(4, expenseStartDate);
    	query.setParameter(5, expenseEndDate);
    	

    	List<AppointmentsBean> appointmentList = query.getResultList();

    	return appointmentList;

        }

    /**
     * Finds appointment histories for the given search date.
     * 
     * @param searchDate
     * @return <code>AppointmentHistory</code>
     */
    /*public List<AppointmentsBean> findAppointmentHistory(int empId, Date searchDate) {
    	String query = "select ah.appt_identifier, a.emp_identifier, ah.start_date, ah.end_date, ah.department, ah.agency, ah.tku," +
		" ah.departure_date departureDate, ah.appointment_date, ah.departure_or_end_date departureOrEndDate, a.position_id from appointments a, appointment_histories ah " +
		" where a.appt_identifier = ah.appt_identifier and a.emp_identifier = ?1" +
		" and ?2 between ah.appointment_date and ah.departure_or_end_date" +
	    " AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1" +
		" WHERE ap1.appt_identifier = ah.appt_identifier" +
		" AND ap1.end_date >= ap1.start_date AND ?2 between ap1.appointment_date and ap1.departure_or_end_date)";
    	
    	return entityManager.createNativeQuery(query, AppointmentsBean.class)
    				 										  .setParameter(1, empId)
    				 										  .setParameter(2, searchDate)
    				 										  .getResultList();
    }*/
    
    /**
     * Finds appointment histories for the given search date.
     * 
     * @param searchDate
     * @return <code>AppointmentHistory</code>
     */
    public List<AppointmentsBean> findAppointmentHistory(int empId, Date searchDate) {
    	String query = "select ah.appt_identifier, a.emp_identifier, ah.start_date, ah.end_date, ah.department, ah.agency, ah.tku," +
		" ah.departure_date departureDate, ah.appointment_date, ah.departure_or_end_date departureOrEndDate, a.position_id," +
    	" ah.position_level from appointments a, appointment_histories ah " +
	    " WHERE a.appt_identifier = ah.appt_identifier AND a.emp_identifier = ?1" +
	    " AND ((?2 BETWEEN ah.appointment_date AND ah.departure_or_end_date" +
		" AND ah.end_date = '31-DEC-2222' AND (ah.appt_status_code like 'A%')" +
	    " OR (?2 BETWEEN ah.appointment_date AND ah.departure_date - 1 AND ah.appt_status_code like 'E%'" +
	    " AND ah.end_date <> '31-DEC-2222') OR  (?2 BETWEEN ah.appointment_date AND ah.departure_date" +
	    " AND ah.appt_status_code not like 'E%' AND ah.end_date = (select max(end_date) from appointment_histories where" +
	    " appt_identifier = ah.appt_identifier and ?2 BETWEEN appointment_date AND departure_date)) OR" +
	    "(?2 BETWEEN ah.appointment_date AND ah.departure_or_end_date" +
	    " AND ah.appt_status_code like 'Z%' AND ah.end_date = (select max(end_date) from appointment_histories where" +
	    " appt_identifier = ah.appt_identifier and ?2 BETWEEN appointment_date AND departure_or_end_date))))";
    	
    	return entityManager.createNativeQuery(query, AppointmentsBean.class)
    				 										  .setParameter(1, empId)
    				 										  .setParameter(2, searchDate)
    				 										  .getResultList();
    }
    
    
    /**
     * This method retrieves the list of active appointments for an employee
     * within a given expense date range
     * 
     * @param expenseStartDate
     * @param expenseEndDate
     * @param moduleId
     * @param empId
     * @param userId
     * @return list of appointments
     */

    public List<AppointmentsBean> findActiveAppointmentByExpenseDatesManagerStatewide(
	    Date expenseStartDate, Date expenseEndDate, String moduleId,
	    long empId, String userId) {
	String finderQuery = "SELECT a.APPT_IDENTIFIER,a.EMP_IDENTIFIER,ah.DEPARTMENT,ah.AGENCY,ah.TKU,ah.START_DATE,ah.END_DATE,ah.DEPT_CODE "
		+ ", AH.DEPARTMENT || AH.AGENCY as PROCESS_LEVEL, a.POSITION_ID, ah.APPOINTMENT_DATE, ah.DEPARTURE_DATE departureDate FROM "
		+ "APPOINTMENT_HISTORIES AH, APPOINTMENTS A WHERE AH.APPT_IDENTIFIER = A.APPT_IDENTIFIER "
		+ "AND A.EMP_IDENTIFIER = ?1 AND ?2 <= AH.DEPARTURE_OR_END_DATE AND ?3 >= AH.APPOINTMENT_DATE AND "
		+ "EXISTS (SELECT sec.user_id FROM SECURITY sec WHERE sec.user_id = ?4 AND sec.module_id = ?5 AND "
		+ "(ah.department BETWEEN DECODE(sec.department,'AL','00',sec.department) AND DECODE(sec.department,'AL','99',sec.department)) AND "
		+ "(ah.agency BETWEEN DECODE(sec.agency,'AL','00',sec.agency) AND DECODE(sec.agency,'AL','99',sec.agency)) AND "
		+ "(ah.tku BETWEEN DECODE(sec.tku,'AL','000',sec.tku) AND DECODE(sec.tku,'AL','999',sec.tku)))" 
		+ " and ah.APPOINTMENT_DATE = (select max(APPOINTMENT_DATE) from APPOINTMENT_HISTORIES where APPT_IDENTIFIER = AH.APPT_IDENTIFIER"
		+ " AND ?6 <= END_DATE AND ?7 >= APPOINTMENT_DATE"
	    + "	AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1"
		+ " WHERE ap1.appt_identifier = ah.appt_identifier"
		+ " AND ap1.end_date >= ap1.start_date))";

	Query query = entityManager.createNativeQuery(finderQuery,
		AppointmentsBean.class);
	query.setParameter(1, empId);
	query.setParameter(2, expenseStartDate);
	query.setParameter(3, expenseEndDate);
	query.setParameter(4, userId);
	query.setParameter(5, moduleId);
	query.setParameter(6, expenseStartDate);
	query.setParameter(7, expenseEndDate);

	List<AppointmentsBean> appointmentList = query.getResultList();

	return appointmentList;
    }

    /**
     * This method retrieves the list of facs agencies associated to an
     * appointment
     * 
     * @param expenseStartDate
     * @param expenseEndDate
     * @param apptId
     * @return list of FacsAgencies
     */

    public List<AppointmentsBean> findFacsAgencyByExpenseDatesByEmployee(
	    Date expenseStartDate, Date expenseEndDate, long apptId) {
	String finderQuery = "SELECT DISTINCT ah.APPT_IDENTIFIER,ag.FACS_AGY, ah.DEPARTMENT, ah.AGENCY FROM AGENCY_FACS_AGENCIES ag, APPOINTMENT_HISTORIES ah " +
			"WHERE ag.DEPARTMENT=ah.DEPARTMENT AND ag.AGENCY=ah.AGENCY AND " +
			"?1= ah.APPT_IDENTIFIER AND "  +
			"?2<=ag.END_DATE AND ?3>=ag.START_DATE AND " +
			"?2<=ah.DEPARTURE_OR_END_DATE AND ?2>=ah.APPOINTMENT_DATE ";

	Query query = entityManager.createNativeQuery(finderQuery,
		AppointmentsBean.class);
	query.setParameter(1, apptId);
	query.setParameter(2, expenseEndDate);
	query.setParameter(3, expenseStartDate);

	List<AppointmentsBean> appointmentsList = query.getResultList();

	return appointmentsList;

    }

    /**
     * This method retrieves all the departments that the user has security
     * access to.
     * 
     * @param userId
     * @param moduleId
     * @return list of accessible departments
     */
    public List<Department> findDepartments(String userId, String moduleId) {
	List<Department> result = null;

	if (userId == null || "".equals(userId.trim()) || moduleId == null
		|| "".equals(moduleId.trim())) {
	    return result;
	}

	String query = "SELECT d.department, d.name FROM departments d WHERE d.department > '00'"
		+ " AND (EXISTS (SELECT s.user_id  FROM security s WHERE (s.user_id = ?1)"
		+ " AND (s.module_id = ?2)" 
		+ " AND (d.department BETWEEN"
		+ " DECODE(s.department,'AL', '00', s.department)"
		+ " AND DECODE(s.department, 'AL', '99',s.department))))"
		+ " ORDER BY d.department";

	result = entityManager.createNativeQuery(query, Department.class)
		.setParameter(1, userId).setParameter(2, moduleId)
		.getResultList();

	return result;
    }

    /**
     * This method retrieves all the agencies that the user has security access
     * to.
     * 
     * @param userId
     * @param moduleId
     * @param dept
     * @return list of accessible agencies
     */
    public List<Agency> findAgencies(String userId, String moduleId, String dept) {
	List<Agency> result = null;

	if (userId == null || "".equals(userId.trim()) || moduleId == null
		|| "".equals(moduleId.trim()) || dept == null
		|| "".equals(dept.trim())) {
	    return result;
	}

	String query = "SELECT a.agency, a.name, a.department FROM agencies a WHERE"
		+ " a.status='A' AND a.department = ?1 AND a.dcds_start_date < a.end_date"
		+ " AND EXISTS (SELECT s.user_id FROM security s WHERE s.user_id = ?2"
		+ " AND s.module_id = ?3 " 
		+ " AND a.department BETWEEN DECODE(s.department,'AL', '00',s.department)"
		+ " AND DECODE (s.department, 'AL', '99', s.department) AND a.agency BETWEEN DECODE (s.agency, 'AL','00',s.agency)"
		+ " AND DECODE (s.agency, 'AL', '99', s.agency)) ORDER BY a.agency";

	result = entityManager.createNativeQuery(query, Agency.class)
		.setParameter(1, dept).setParameter(2, userId).setParameter(3,
			moduleId).getResultList();

	return result;
    }

    /**
     * This method retrieves all the TKU that the user has security access to.
     * 
     * @param userId
     * @param moduleId
     * @param dept
     * @param agency
     * @return list of accessible tkus
     */
    public List<Tku> findTKUs(String userId, String moduleId, String dept,
	    String agency) {
	List<Tku> result = null;

	if (userId == null || "".equals(userId.trim()) || moduleId == null
		|| "".equals(moduleId.trim()) || dept == null
		|| "".equals(dept.trim()) || agency == null
		|| "".equals(agency.trim())) {
	    return result;
	}

	String query = "SELECT tkus.department, tkus.agency, tkus.tku, tkus.name FROM tkus, agencies"
		+ " WHERE tkus.status='A' and tkus.department = agencies.department"
		+ " AND tkus.agency = agencies.agency AND tkus.end_date > agencies.dcds_start_date"
		+ " AND tkus.department = ?1 AND tkus.agency = ?2 AND EXISTS ("
		+ " SELECT sec.user_id FROM security sec"
		+ " WHERE sec.user_id = ?3 AND sec.module_id = ?4" 
		+ " AND tkus.department BETWEEN DECODE (sec.department, 'AL', '00',sec.department)"
		+ " AND DECODE (sec.department, 'AL', '99', sec.department)"
		+ " AND tkus.agency BETWEEN DECODE (sec.agency, 'AL', '00',sec.agency)"
		+ " AND DECODE (sec.agency,'AL', '99',sec.agency)"
		+ " AND tkus.tku BETWEEN DECODE (sec.tku, 'AL', '000', sec.tku)"
		+ " AND DECODE (sec.tku, 'AL', '999', sec.tku)"
		+ " ) ORDER BY tkus.tku";

	result = entityManager.createNativeQuery(query, Tku.class)
		.setParameter(1, dept).setParameter(2, agency).setParameter(3,
			userId).setParameter(4, moduleId).getResultList();

	return result;
    }

    /**
     * Finds TKU name using TKU code.
     * 
     * @param tku code
     * @return tku name
     */
    public String findTkuName(String dept, String agency, String tku){
    	String tkuName = "";
    	
    	String query = "select t from Tku t where t.tkuPK.department=:dept and t.tkuPK.agency=:agency and t.tkuPK.tku=:tku";
    	
    	List<Tku> tkus = entityManager.createQuery(query)
    									.setParameter("dept", dept)
    									.setParameter("agency", agency)
    									.setParameter("tku", tku)
    									.getResultList();
    	
    	if(!tkus.isEmpty()){
    		tkuName = tkus.get(0).getName();
    	}
    	
    	return tkuName;
    }    
     
    /**
     * Finds the appointment history for the given appointment on the 
     * specified search date. PS: The validity of an appointment history 
     * record is determined not only by the start and end dates but also 
     * by the departure date. If a record has a departure date, then that
     * departure date is effectively treated as the end date for that appointment.
     * Thia method takes care of that and ensures only those records get returned
     * that either have no departure date present or if present, then it must NOT
     * be less than the search date.   
     * 
     * <p>If no record found, NULL is returned!</p>
     * 
     * @param appointment Id
     * @param search date for appointment history
     * @return AppointmentHistory
     */
    public AppointmentHistory findAppointmentHistory(Integer apptId, Date searchDate){
    	String query ="SELECT ah from AppointmentHistory ah where ah.appointmentId = :apptId and :searchDate between ah.apptDate and ah.departureOrEndDate" +
    	              " AND ah.endDate = (select max(ap1.endDate) from AppointmentHistory ap1" +
 		              " WHERE ap1.appointmentId = ah.appointmentId" +
 		              " AND ap1.endDate >= ap1.startDate and :searchDate between ap1.apptDate and ap1.departureOrEndDate)";
    	List<AppointmentHistory> apptHistories = entityManager.createQuery(query)
    													.setParameter("apptId", apptId)
    													.setParameter("searchDate", searchDate)
    													.getResultList();
    	
    	// this would always contain unique result or no result
    	return (apptHistories.size()>0) ? apptHistories.get(0) : null;
    }
    
    
    /**
     * This method retrieves the employee header information for the associated
     * with the employee's current appointment and within the current date
     * 
     * @param appointmentId
     * @param positionLevel
     * @return list of appointments for an employee
     */

    public List<EmployeeHeaderBean> findEmployeeHeaderInfo(int appointmentId) {
	String finderQuery = "SELECT  RTRIM (PERSONNEL_HISTORIES.last_name) || ', ' || RTRIM (PERSONNEL_HISTORIES.first_name) || ' '  || RTRIM (PERSONNEL_HISTORIES.middle_name) "
		+ " || ' '  || RTRIM (PERSONNEL_HISTORIES.suffix)      empName, "
		+ " APPOINTMENTS.process_level processLevel,APPOINTMENTS.dept_code deptCode,HRMN_DEPT_CODES.name deptName,APPOINTMENTS.emp_identifier empId, APPOINTMENTS.appt_identifier apptId "
		+ "FROM APPOINTMENTS,APPOINTMENT_HISTORIES,PERSONNEL_HISTORIES,HRMN_DEPT_CODES,EMPLOYEES WHERE "
		+ "EMPLOYEES.emp_identifier = APPOINTMENTS.emp_identifier "
		+ "AND APPOINTMENTS.appt_identifier = APPOINTMENT_HISTORIES.appt_identifier "
		+ "AND EMPLOYEES.emp_identifier = PERSONNEL_HISTORIES.emp_identifier "
		+ "AND APPOINTMENT_HISTORIES.department = HRMN_DEPT_CODES.department "
		+ "AND APPOINTMENT_HISTORIES.agency = HRMN_DEPT_CODES.agency "
		+ "AND APPOINTMENT_HISTORIES.dept_code = HRMN_DEPT_CODES.dept_code "
		+ "AND APPOINTMENTS.appt_identifier = ?1 "
		+ "AND SYSDATE BETWEEN APPOINTMENT_HISTORIES.appointment_date "
		+ "AND APPOINTMENT_HISTORIES.departure_or_end_date "
	    + "AND APPOINTMENT_HISTORIES.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1 "
		+ "WHERE ap1.appt_identifier = APPOINTMENT_HISTORIES.appt_identifier "
		+ "AND ap1.end_date >= ap1.start_date) "
		+ "AND SYSDATE BETWEEN PERSONNEL_HISTORIES.start_date "
		+ "AND PERSONNEL_HISTORIES.end_date "
		+ "AND APPOINTMENT_HISTORIES.POSITION_LEVEL = 1";

	Query query = entityManager.createNativeQuery(finderQuery,
		EmployeeHeaderBean.class);
	query.setParameter(1, appointmentId);

	List<EmployeeHeaderBean> empHeaderList = query.getResultList();

	return empHeaderList;
    }

    /**
     * This method retrieves the employee header information for the associated
     * with the employeeId and the appointment that is valid within the given
     * date
     * 
     * @param appointmentId
     * @param positionLevel
     * @return list of appointments for an employee
     */

    public List<EmployeeHeaderBean> findEmployeeHeaderInfo(int appointmentId,
	    Date requestDate) {
	String finderQuery = "SELECT  RTRIM (PERSONNEL_HISTORIES.last_name) || ', ' || RTRIM (PERSONNEL_HISTORIES.first_name) || ' '  || RTRIM (PERSONNEL_HISTORIES.middle_name) "
		+ " || ' '  || RTRIM (PERSONNEL_HISTORIES.suffix)      empName, "
		+ " APPOINTMENTS.process_level processLevel,APPOINTMENTS.dept_code deptCode,HRMN_DEPT_CODES.name deptName,APPOINTMENTS.emp_identifier empId, APPOINTMENTS.appt_identifier apptId "
		+ "FROM APPOINTMENTS,APPOINTMENT_HISTORIES,PERSONNEL_HISTORIES,HRMN_DEPT_CODES,EMPLOYEES WHERE "
		+ "EMPLOYEES.emp_identifier = APPOINTMENTS.emp_identifier "
		+ "AND APPOINTMENTS.appt_identifier = APPOINTMENT_HISTORIES.appt_identifier "
		+ "AND EMPLOYEES.emp_identifier = PERSONNEL_HISTORIES.emp_identifier "
		+ "AND APPOINTMENT_HISTORIES.department = HRMN_DEPT_CODES.department "
		+ "AND APPOINTMENT_HISTORIES.agency = HRMN_DEPT_CODES.agency "
		+ "AND APPOINTMENT_HISTORIES.dept_code = HRMN_DEPT_CODES.dept_code "
		+ "AND APPOINTMENTS.appt_identifier = ?1 "
		+ "AND ?2 BETWEEN APPOINTMENT_HISTORIES.appointment_date "
		+ "AND APPOINTMENT_HISTORIES.departure_or_end_date "
	    + "AND APPOINTMENT_HISTORIES.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1 "
		+ "WHERE ap1.appt_identifier = APPOINTMENT_HISTORIES.appt_identifier "
		+ "AND ap1.end_date >= ap1.start_date) "
		+ "AND ?3 BETWEEN PERSONNEL_HISTORIES.start_date "
		+ "AND PERSONNEL_HISTORIES.end_date ";

	Query query = entityManager.createNativeQuery(finderQuery,
		EmployeeHeaderBean.class);
	query.setParameter(1, appointmentId);
	query.setParameter(2, requestDate);
	query.setParameter(3, requestDate);

	List<EmployeeHeaderBean> empHeaderList = query.getResultList();

	return empHeaderList;
    }
    
    
    /**
     * This method retrieves the employee header information for the associated
     * with the employee's current appointment and within the current date
     * 
     * @param appointmentId
     * @param positionLevel
     * @return list of appointments for an employee
     */

    public List<EmployeeHeaderBean> findEmployeeHeaderInfoByEmpId(int empId) {
    	String finderQuery = "SELECT  RTRIM (PERSONNEL_HISTORIES.last_name) || ', ' || RTRIM (PERSONNEL_HISTORIES.first_name) || ' '  || RTRIM (PERSONNEL_HISTORIES.middle_name) "
    		+ " || ' '  || RTRIM (PERSONNEL_HISTORIES.suffix)      empName, "
    		+ " APPOINTMENT_HISTORIES.BARGAINING_UNIT|| ' ' ||BARGAINING_UNITS.NAME  bargUnit,"//AI-29299 Added bargaining unit
    		+ " APPOINTMENTS.process_level processLevel,APPOINTMENTS.dept_code deptCode,HRMN_DEPT_CODES.name deptName,APPOINTMENTS.emp_identifier empId, APPOINTMENTS.appt_identifier apptId "
    		+ "FROM APPOINTMENTS,APPOINTMENT_HISTORIES,PERSONNEL_HISTORIES,HRMN_DEPT_CODES,EMPLOYEES,BARGAINING_UNITS WHERE "
    		+ "EMPLOYEES.emp_identifier = APPOINTMENTS.emp_identifier "
    		+ "AND APPOINTMENT_HISTORIES.BARGAINING_UNIT=BARGAINING_UNITS.BARGAINING_UNIT "
    		+ "AND APPOINTMENTS.appt_identifier = APPOINTMENT_HISTORIES.appt_identifier "
    		+ "AND EMPLOYEES.emp_identifier = PERSONNEL_HISTORIES.emp_identifier "
    		+ "AND APPOINTMENT_HISTORIES.department = HRMN_DEPT_CODES.department "
    		+ "AND APPOINTMENT_HISTORIES.agency = HRMN_DEPT_CODES.agency "
    		+ "AND APPOINTMENT_HISTORIES.dept_code = HRMN_DEPT_CODES.dept_code "
    		+ "AND APPOINTMENTS.emp_identifier = ?1 "
    		+ "AND APPOINTMENT_HISTORIES.APHS_IDENTIFIER = (select max(ah1.APHS_IDENTIFIER) from "
	        + "APPOINTMENT_HISTORIES ah1, APPOINTMENTS a1 where a1.APPT_IDENTIFIER = ah1.APPT_IDENTIFIER and a1.emp_identifier = ?1 "
	        + "AND ah1.end_date = '31-DEC-2222' AND ah1.POSITION_LEVEL = 1) "
    		+ "AND SYSDATE BETWEEN PERSONNEL_HISTORIES.start_date "
    		+ "AND PERSONNEL_HISTORIES.end_date";


    	Query query = entityManager.createNativeQuery(finderQuery,
    		EmployeeHeaderBean.class);
    	query.setParameter(1, empId);

    	List<EmployeeHeaderBean> empHeaderList = query.getResultList();

    	return empHeaderList;
        }
    
    /**
     * This method retrieves the employee header information for the associated
     * with the employee's selected appointment
     * 
     * @param appointmentId
     * @param positionLevel
     * @return list of appointments for an employee
     */

    public List<EmployeeHeaderBean> findEmployeeHeaderInfoByApptId(int apptId, Date searchDate) {
    	String finderQuery = "SELECT  RTRIM (PERSONNEL_HISTORIES.last_name) || ', ' || RTRIM (PERSONNEL_HISTORIES.first_name) || ' '  || RTRIM (PERSONNEL_HISTORIES.middle_name) "
    		+ " || ' '  || RTRIM (PERSONNEL_HISTORIES.suffix)      empName, "
    		+ " APPOINTMENT_HISTORIES.BARGAINING_UNIT|| ' ' ||BARGAINING_UNITS.NAME  bargUnit,"//AI-29299 Added bargaining unit
    		+ " APPOINTMENTS.process_level processLevel,APPOINTMENTS.dept_code deptCode,HRMN_DEPT_CODES.name deptName,APPOINTMENTS.emp_identifier empId, APPOINTMENTS.appt_identifier apptId "
    		+ "FROM APPOINTMENTS,APPOINTMENT_HISTORIES,PERSONNEL_HISTORIES,HRMN_DEPT_CODES,EMPLOYEES,BARGAINING_UNITS WHERE "
    		+ "EMPLOYEES.emp_identifier = APPOINTMENTS.emp_identifier "
    		+ "AND APPOINTMENT_HISTORIES.BARGAINING_UNIT=BARGAINING_UNITS.BARGAINING_UNIT "
    		+ "AND APPOINTMENTS.appt_identifier = APPOINTMENT_HISTORIES.appt_identifier "
    		+ "AND EMPLOYEES.emp_identifier = PERSONNEL_HISTORIES.emp_identifier "
    		+ "AND APPOINTMENT_HISTORIES.department = HRMN_DEPT_CODES.department "
    		+ "AND APPOINTMENT_HISTORIES.agency = HRMN_DEPT_CODES.agency "
    		+ "AND APPOINTMENT_HISTORIES.dept_code = HRMN_DEPT_CODES.dept_code "
    		+ "AND APPOINTMENT_HISTORIES.APHS_IDENTIFIER = (select max(ah1.APHS_IDENTIFIER) from "
	        + "APPOINTMENT_HISTORIES ah1 where ah1.APPT_IDENTIFIER = ?2) "
    		+ "AND ?1 BETWEEN PERSONNEL_HISTORIES.start_date "
    		+ "AND PERSONNEL_HISTORIES.end_date "
    		+ "AND APPOINTMENTS.appt_identifier = ?2";
    	
    	if (searchDate == null){
    		// employee access may not have a search date
    		searchDate = Calendar.getInstance().getTime();
    	}

    	Query query = entityManager.createNativeQuery(finderQuery,
    		EmployeeHeaderBean.class);
    	query.setParameter(1, searchDate);
    	query.setParameter(2, apptId);

    	List<EmployeeHeaderBean> empHeaderList = query.getResultList();

    	return empHeaderList;
        }

    
    /**
     * This method retrieves the appointments for an employee for a given search
     * date.
     * 
     * @param empId
     * @param apptSearchDate
     * @return list of AppointmentListBean
     */
    public List<AppointmentListBean> findAppointmentsByEmpIdAndDept(int empId,
	    String dept, Date apptSearchDate, String loggedInUserId, String moduleId) {
    	
    	User loggedInUser = entityManager.find(User.class, loggedInUserId);
    	
    	return entityManager.createQuery(APPT_BY_EMP_ID_DEPT_SECURITY_QUERY_ORDERBYLN)
				.setParameter("empId", empId).setParameter("dept", dept)
				.setParameter("apptSearchDate", apptSearchDate)
				.setParameter("user", loggedInUser)
				.setParameter("moduleId", moduleId)
				.getResultList();
    }
    
    /**
     * Retrieves appointments without a Search date
     * 
     * @param empId
     * @param dept
     * @param loggedInUserId
     * @param moduleId
     * @return
     */
    
    public List<AppointmentListBean> findAppointmentsByEmpIdAndDeptNoSearchDate(int empId,
    	    String dept, String loggedInUserId, String moduleId) {
        	
        	User loggedInUser = entityManager.find(User.class, loggedInUserId);
        	
        	return entityManager.createQuery(APPT_BY_EMP_ID_DEPT_SECURITY_QUERY_ORDERBYLN_NO_SEARCH_DATE)
    				.setParameter("empId", empId).setParameter("dept", dept)
    				.setParameter("user", loggedInUser)
    				.setParameter("moduleId", moduleId)
    				.getResultList();
        }

    
    /**
     * This method retrieves the appointments for an employee for a given search
     * date.
     * 
     * @param empId
     * @param apptSearchDate
     * @return list of AppointmentListBean
     */
    public List<AppointmentListBean> findAppointmentsByEmpIdAndDeptAgency(int empId,
	    String dept, String agency, Date apptSearchDate, String loggedInUserId, String moduleId) {
    	
    	User loggedInUser = entityManager.find(User.class, loggedInUserId);
    	
    	return entityManager.createQuery(APPT_BY_EMP_ID_DEPT_AGENCY_SECURITY_QUERY_ORDERBYLN)
				.setParameter("empId", empId).setParameter("dept", dept)
				.setParameter("agency", agency)
				.setParameter("apptSearchDate", apptSearchDate)
				.setParameter("user", loggedInUser)
				.setParameter("moduleId", moduleId)
				.getResultList();
    }
    
    public List<AppointmentListBean> findAppointmentsByEmpIdAndDeptAgencyNoSearchDate(int empId,
    	    String dept, String agency, String loggedInUserId, String moduleId) {
        	
        	User loggedInUser = entityManager.find(User.class, loggedInUserId);
        	
        	return entityManager.createQuery(APPT_BY_EMP_ID_DEPT_AGENCY_SECURITY_QUERY_ORDERBYLN_NO_SEARCH_DATE)
    				.setParameter("empId", empId).setParameter("dept", dept)
    				.setParameter("agency", agency)
    				.setParameter("user", loggedInUser)
    				.setParameter("moduleId", moduleId)
    				.getResultList();
        }
    
    /**
     * 
     * @param requestId
     * @param moduleId
     * @return List<AppointmentHistories>
     */
    
    public List<AppointmentHistory> getAppointmentIdentifier(String requestId,String moduleId)
    {
    	List <AppointmentHistory> aphsList = new ArrayList<AppointmentHistory>();
     	if(moduleId.toUpperCase().equals(IConstants.ADVANCE_MANAGER)|| moduleId.toUpperCase().equals(IConstants.ADVANCE_STATEWIDE))
    	{
    	String queryFirst =" Select distinct AH.APHS_IDENTIFIER from  ADVANCE_EVENTS ae, APPOINTMENTS a,"+
    						" APPOINTMENT_HISTORIES ah, ADVANCE_MASTERS am, PERSONNEL_HISTORIES ph "+
    						" where a.appt_identifier = ae.appt_identifier " +
    						" and ae.adev_identifier ="+requestId+
    						" and a.appt_identifier = ah.appt_identifier "+
    						" and am.adev_identifier = ae.adev_identifier "+
    						" and ph.emp_identifier = A.EMP_IDENTIFIER "+
    						" and sysdate between ph.start_date and ph.end_date "+
    						" and am.request_date between ah.start_date and AH.END_DATE  ";
    	     aphsList  =  entityManager.createNativeQuery(queryFirst).getResultList();
    	
    	}
    	else if(moduleId.toUpperCase().equals(IConstants.EXPENSE_MANAGER)||moduleId.toUpperCase().equals(IConstants.EXPENSE_STATEWIDE))
    	 	{
    	 		String queryFirst =" select distinct AH.APHS_IDENTIFIER  from "+
    	 		" expense_events ee, appointments a, appointment_histories ah, expense_masters em, personnel_histories ph"+
    	 		" where a.appt_identifier = ee.appt_identifier "+
    	 		" and ee.expev_identifier = " +requestId+
    	 		" and a.appt_identifier = ah.appt_identifier " +
    	 		" and em.expev_identifier = ee.expev_identifier "+ 
    	 		" and ph.emp_identifier = A.EMP_IDENTIFIER "+
    	 		" and sysdate between ph.start_date and ph.end_date "+
    	 		" and em.exp_date_to between ah.appointment_date " +
    	 		" and ah.departure_or_end_date " +
    	 		" and ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1 "
    			+ " WHERE ap1.appt_identifier = ah.appt_identifier "
    			+ " AND ap1.end_date >= ap1.start_date)";
    	 		
    	 		
    	 	
    	 		
           aphsList  =  entityManager.createNativeQuery(queryFirst).getResultList();
    	 	}
    	return aphsList;
    }
    
   /**
    * 
    * @param empId
    * @param dept
    * @param agency
    * @param tku
    * @param apptSearchDate
    * @param loggedInUserId
    * @param moduleId
    * @return List<AppointmentListBean>
    */
    public List<AppointmentListBean> findAppointmentsByRequestIdEmpIdAndDeptAgencyTku(int empId,
	    String dept, String agency, String tku, Date apptSearchDate, String loggedInUserId, String moduleId, String requestId,List<AppointmentHistory> aphsList) {
    	User loggedInUser = entityManager.find(User.class, loggedInUserId);
    	List<AppointmentListBean> appLApps =new  ArrayList<AppointmentListBean>();  
    	 
    	try
    	{
     
    	StringBuffer newQuery = new StringBuffer();
    	
    	newQuery.append(QUERY_AND_HISTORY_CONDITION);
    	newQuery.append(EMPID_SUBQUERY_CONDITION); 
    	 StringBuffer  APHS_ID_CONDITION= new StringBuffer();
    	 if(aphsList!=null)
    	 {
    		 newQuery.append(" and appt_info.apptHistoryId in ( ");
	    	 for(int i=0;i<aphsList.size();i++)
	    	 {
	    		 Object o= aphsList.get(i);
	    		 
	    	     newQuery.append(o.toString());
	    		 if(i+1<aphsList.size())
	    		 {
	    		 newQuery.append(",");
	    		 }
	    	 }
	    	 newQuery.append(" ) ");
    	 }
    	 
        
    	if(dept!=null && dept.trim()!="")
    	{
    		newQuery.append(" and appt_info.department = '"+dept+"'");
    	}
    	if(agency!=null && agency.trim()!="")
    	{
    		newQuery.append("and appt_info.agency = '"+agency+"'");
    	}
    	if(tku!=null && tku.trim()!="")
    	{
    		newQuery.append("and appt_info.tku = '"+tku+"'");
    	}
    	 
    		newQuery.append(SECURITY_SUB_SELECT_QUERY);
    		newQuery.append(LN_ORDERBY_SUBQUERY_CONDITION);
    	 
    		appLApps=  entityManager.createQuery(newQuery.toString()) 
    		.setParameter("apptSearchDate", apptSearchDate)
    		    .setParameter("empId", empId)				
				.setParameter("user", loggedInUser)
				.setParameter("moduleId", moduleId)
				.getResultList();
    	}catch(Exception e)
    	{
    		System.out.println(" \n The error is "+e.getMessage());
    		e.printStackTrace();
    	}
    	return appLApps;
    }

    
    
    
    /**
     * This method retrieves the appointments for an employee for a given search
     * date.
     * 
     * @param empId
     * @param apptSearchDate
     * @return list of AppointmentListBean
     */
    public List<AppointmentListBean> findAppointmentsByEmpIdAndDeptAgencyTku(int empId,
	    String dept, String agency, String tku, Date apptSearchDate, String loggedInUserId, String moduleId) {
    	
    	User loggedInUser = entityManager.find(User.class, loggedInUserId);
    	
    	return entityManager.createQuery(APPT_BY_EMP_ID_DEPT_AGENCY_TKU_SECURITY_QUERY_ORDERBYLN)
				.setParameter("empId", empId).setParameter("dept", dept)
				.setParameter("agency", agency).setParameter("tku", tku)
				.setParameter("apptSearchDate", apptSearchDate)
				.setParameter("user", loggedInUser)
				.setParameter("moduleId", moduleId)
				.getResultList();
    }
    

    public List<AppointmentListBean> findAppointmentsByEmpIdAndDeptAgencyTkuNoSearchDate(int empId,
    	    String dept, String agency, String tku, String loggedInUserId, String moduleId) {
        	
        	User loggedInUser = entityManager.find(User.class, loggedInUserId);
        	
        	return entityManager.createQuery(APPT_BY_EMP_ID_DEPT_AGENCY_TKU_SECURITY_QUERY_ORDERBYLN_NO_SEARCH_DATE)
    				.setParameter("empId", empId).setParameter("dept", dept)
    				.setParameter("agency", agency).setParameter("tku", tku)
    				.setParameter("user", loggedInUser)
    				.setParameter("moduleId", moduleId)
    				.getResultList();
        }

    
    public List<MyEmployeesListBean> findAppointmentsByMyemployees(int empIdentifier, Date apptSearchDate) {
    	String finderQuery = "SELECT e.emp_identifier employeeId,a.appt_identifier appointmentId,ah.aphs_identifier apptHistoryId," +
				" ah.start_date appointmentStart,ah.end_date appointmentEnd,ah.department department,ah.agency agency," +
				" ah.tku tku,ph.first_name firstName,ph.middle_name middleName,ph.last_name lastName,ph.suffix nameSuffix,a.position_id positionId ," +
				" ah.appointment_date appointmentDate, ah.departure_date departureDate "+ 
				" FROM appointments a,appointment_histories ah,employees e,personnel_histories ph," +
				" hrmn_positions hp,hrmn_supervisor_employees hse " +
				" WHERE SYSDATE BETWEEN ph.start_date AND ph.end_date AND e.emp_identifier = a.emp_identifier " +
				" AND a.appt_identifier = ah.appt_identifier AND e.emp_identifier = ph.emp_identifier " +
				" AND hse.supervisor = hp.supervisor AND a.position_id = hp.position_id AND SYSDATE BETWEEN hp.start_date " +
				" AND hp.end_date AND SYSDATE BETWEEN hse.start_date AND hse.end_date AND hse.emp_identifier = ?1 " +
				" AND ?2 between ah.appointment_date and ah.departure_or_end_date" +
			    " AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1" +
				" WHERE ap1.appt_identifier = ah.appt_identifier " +
				" AND ap1.end_date >= ap1.start_date)";
    	
    	Query query = entityManager.createNativeQuery(finderQuery,MyEmployeesListBean.class);
    		query.setParameter(1, empIdentifier);
    		query.setParameter(2, apptSearchDate);

		List<MyEmployeesListBean> appointmentList = query.getResultList();
		return appointmentList;
	}    
    
    /**
     * This method retrieves the appointments for an employee with the provided
     * last name, in a department for a given search date.
     * 
     * @param deptCode
     * @param lastName
     * @param apptSearchDate
     * @return list of AppointmentListBean
     */
    public List<AppointmentListBean> findAppointmentsByLastNameInDept(
	    String dept, String lastName, String loggedInUserId, String moduleId, Date apptSearchDate) {
    	
    	User loggedInUser = entityManager.find(User.class, loggedInUserId);
    	
    	return entityManager.createQuery(APPT_BY_LAST_NAME_DEPT_SECURITY_QUERY_ORDERBYLN)
							.setParameter("dept", dept)
							.setParameter("lastName",lastName.toUpperCase() + "%")
							.setParameter("apptSearchDate",apptSearchDate)
							.setParameter("user", loggedInUser).setParameter("moduleId", moduleId)
							.getResultList();
    }

    /**
     * This method retrieves the appointments for an employee with the provided
     * last name, in a department and agency for a given search date.
     * 
     * @param dept
     * @param agency
     * @param lastName
     * @param apptSearchDate
     * @return list of AppointmentListBean
     */
    public List<AppointmentListBean> findAppointmentsByLastNameInDeptAgency(String dept, String agency, 
    		String lastName, String loggedInUserId, String moduleId, Date apptSearchDate) {
    	
    	User loggedInUser = entityManager.find(User.class, loggedInUserId);
    	
    	return entityManager.createQuery(APPT_BY_LAST_NAME_DEPT_AGENCY_SECURITY_QUERY_ORDERBYLN)
							.setParameter("dept", dept).setParameter("agency", agency)
							.setParameter("lastName", lastName.toUpperCase() + "%")
							.setParameter("apptSearchDate", apptSearchDate)
							.setParameter("user", loggedInUser).setParameter("moduleId", moduleId)
							.getResultList();
    }

    /**
     * This method retrieves the appointments for an employee with the provided
     * last name, in a department, agency and tku for a given search date.
     * 
     * @param dept
     * @param agency
     * @param tku
     * @param lastName
     * @param apptSearchDate
     * @return list of AppointmentListBean
     */
    public List<AppointmentListBean> findAppointmentsByLastNameInDeptAgencyTku(String dept, String agency, 
    		String tku, String lastName, String loggedInUserId, String moduleId, Date apptSearchDate) {
	
    	User loggedInUser = entityManager.find(User.class, loggedInUserId);
    	
    	return entityManager.createQuery(APPT_BY_LAST_NAME_DEPT_AGENCY_TKU_SECURITY_QUERY_ORDERBYLN)
    						.setParameter("dept",dept).setParameter("agency", agency)
    						.setParameter("tku", tku).setParameter("lastName", lastName.toUpperCase() + "%")
    						.setParameter("apptSearchDate", apptSearchDate)
    						.setParameter("user", loggedInUser).setParameter("moduleId", moduleId)
    						.getResultList();
    }

    /**
     * This method retrieves the appointments for an employee in a department
     * for a given search date.
     * 
     * @param dept
     * @param apptSearchDate
     * @return list of AppointmentListBean
     */
    public List<AppointmentListBean> findAppointmentsByDept(String dept, 
    					String loggedInUserId, String moduleId, Date apptSearchDate) {
	
    	User loggedInUser = entityManager.find(User.class, loggedInUserId);

    	return entityManager.createQuery(APPT_BY_DEPT_SECURITY_QUERY_ORDERBYLN)
    						.setParameter("dept", dept).setParameter("apptSearchDate", apptSearchDate)
    						.setParameter("user", loggedInUser).setParameter("moduleId", moduleId)
    						.getResultList();
    }

    /**
     * This method retrieves the appointments for an employee in a department
     * and agency for a given search date.
     * 
     * @param dept
     * @param agency
     * @param apptSearchDate
     * @return list of AppointmentListBean
     */
    public List<AppointmentListBean> findAppointmentsByDeptAgency(String dept,
	    String agency, String loggedInUserId, String moduleId, Date apptSearchDate) {
	
    	User loggedInUser = entityManager.find(User.class, loggedInUserId);
    	
    	return entityManager.createQuery(APPT_BY_DEPT_AGENCY_SECURITY_QUERY_ORDERBYLN)
							.setParameter("dept", dept).setParameter("agency", agency)
							.setParameter("apptSearchDate", apptSearchDate)
							.setParameter("user", loggedInUser).setParameter("moduleId", moduleId)
							.getResultList();
    }

    
     /**
      * 
      * @param dept
      * @param agency
      * @param tku
      * @param loggedInUserId
      * @param moduleId
      * @param apptSearchDate
      * @return
      */
    public List<AppointmentListBean> findAppointmentsRequestIdByDeptAgencyTku(String dept, String agency, 
    		String tku, String loggedInUserId, String moduleId, Date apptSearchDate, List<AppointmentHistory> listRequests,String lastName) {
	
    	User loggedInUser = entityManager.find(User.class, loggedInUserId);
    	StringBuffer newQuery = new StringBuffer();
    	List<AppointmentListBean> aapl = new ArrayList<AppointmentListBean>();
    	try
    	{
    	newQuery.append(QUERY_AND_HISTORY_CONDITION);
    	
    	//if List is 0 length, then it is still NOT NULL.    	
         if(listRequests !=null && listRequests.size() > 0)
         {
        	 newQuery.append(" and appt_info.apptHistoryId in ( ");
	    	 for(int i=0;i<listRequests.size();i++)
	    	 {
	    		 Object o = listRequests.get(i); 
	    	     newQuery.append(o.toString());
	    		 if(i+1<listRequests.size())
	    		 {
	    		  newQuery.append(",");
	    		 }
	    	 }
	    	 newQuery.append(" ) ");
           }
         if(dept!=null && dept.trim()!="")
     	{
     		newQuery.append(" and appt_info.department = '"+dept+"'");
     	}
     	if(agency!=null && agency.trim()!="")
     	{
     		newQuery.append("and appt_info.agency = '"+agency+"'");
     	}
     	if(tku!=null && tku.trim()!="")
     	{
     		newQuery.append("and appt_info.tku = '"+tku+"'");
     	}
     	if(lastName !=null && lastName.trim().length()>0 ){
     		newQuery.append(" and appt_info.lastName like '"+lastName.toUpperCase()+"%' " );
     	}
     	newQuery.append(SECURITY_SUB_SELECT_QUERY );
     	newQuery.append(LN_ORDERBY_SUBQUERY_CONDITION);
     	aapl= entityManager.createQuery(newQuery.toString())
						    .setParameter("apptSearchDate",apptSearchDate)
							.setParameter("user", loggedInUser).setParameter("moduleId", moduleId)
							.getResultList();
    	}catch(Exception e)
    	{
    		System.out.println("\n Error is "+e.getMessage());
    		e.printStackTrace();
    	}
    	return aapl;
    }
    
    
    /**
     * This method retrieves the appointments for an employee in a department,
     * agency and tku for a given search date.
     * 
     * @param dept
     * @param agency
     * @param tku
     * @param apptSearchDate
     * @return list of AppointmentListBean
     */
    public List<AppointmentListBean> findAppointmentsByDeptAgencyTku(String dept, String agency, 
    		String tku, String loggedInUserId, String moduleId, Date apptSearchDate) {
	
    	User loggedInUser = entityManager.find(User.class, loggedInUserId);

    	return entityManager.createQuery(APPT_BY_DEPT_AGENCY_TKU_SECURITY_QUERY_ORDERBYLN)
							.setParameter("dept", dept).setParameter("agency", agency)
							.setParameter("tku", tku).setParameter("apptSearchDate",apptSearchDate)
							.setParameter("user", loggedInUser).setParameter("moduleId", moduleId)
							.getResultList();
    }

    /**
     * This method finds all the appointments for an employee within a date
     * range.
     * 
     * @param empId
     * @param startDate
     * @param endDate
     * @return list of AppointmentListBean
     */
    public List<AppointmentListBean> findEmployeeAppointmentsInDateRange(
	    int empId, Date startDate, Date endDate) {
	return entityManager.createQuery(EMP_APPOINTMENTS_IN_DATE_RANGE_QUERY_ORDERBYLN)
		.setParameter("empId", empId).setParameter("startDate",
			startDate).setParameter("endDate", endDate)
		.getResultList();
    }

    /**
     * This method finds the employees work location. Work location information
     * comes from the hrmn-locations table.
     * 
     * @param apptId
     * @param Date effectiveDate
     * @return list Location
     */

    public Location findWorkLocation(int apptId, Date effectiveDate) {
	logger
		.debug("Enter method : findWorkLocation(int apptId, Date effectiveDate)");
	String finderQuery = " SELECT hl.LOCATION locationCode, hl.address_1 addressLine1,"
		+ " hl.address_2 addressLine2, hl.city city, hl.state state,"
		+ "hl.zip zip, hl.county county"
		+ " FROM appointments a,hrmn_locations hl, appointment_histories ah"
		+ " WHERE a.appt_identifier = ah.appt_identifier"
		+ " AND ah.LOCATION = hl.LOCATION"
		+ " AND ?2 between ah.appointment_date and ah.departure_or_end_date"
	    + " AND ah.end_date = (select max(end_date) from APPOINTMENT_HISTORIES ap1"
		+ " WHERE ap1.appt_identifier = ah.appt_identifier"
		+ " AND ap1.end_date >= ap1.start_date) "
		+ " And a.appt_identifier = ?1";

	Query query = entityManager.createNativeQuery(finderQuery,
		Location.class);
	query.setParameter(1, apptId);
	query.setParameter(2, effectiveDate);

	List<Location> result = query.getResultList();
	logger
		.debug("Exit method : findWorkLocation(int apptId, Date effectiveDate)");
	return (result.size()>0) ? result.get(0) : null;

    }

    /**This method finds the employees home location. Home location information
     * comes from the Personnel_histories table.
     * @param empId
     * @param effectiveDate
     * @return list Location
     */
    public Location findHomeLocation(int empId, Date effectiveDate) {
	logger
		.debug("Enter method : findHomeLocation(int empId, Date effectiveDate)");

	String finderQuery = "SELECT ph.address_1 addressLine1,ph.address_2 addressLine2,"
		+ " ph.city city, ph.state state, ph.zip zip"
		+ " FROM personnel_histories ph,  appointments a"
		+ " WHERE ph.emp_identifier = a.emp_identifier  AND a.emp_identifier = ?1 "
		+ " AND ?2 between ph.start_date AND ph.end_date";

	Query query = entityManager.createNativeQuery(finderQuery,
		Location.class);
	query.setParameter(1, empId);
	query.setParameter(2, effectiveDate);
	List<Location> result = query.getResultList();
	logger
		.debug("Exit method : findHomeLocation(int empId, Date effectiveDate)");
	return (result.size()>0) ? result.get(0) : null;

    }

    /**
     * Finds an appointment by appointment Id.
     * 
     * @param apptId - Appointment Id.
     * @return Appointment
     */
    public Appointment findAppointmentById(int apptId){
    	return entityManager.find(Appointment.class, apptId);
    }
    
     /**
     * @param apptIdentifier
     * @return
     */
    public Appointments findAppointment(int apptIdentifier) {
	Appointments appt = entityManager.find(Appointments.class,
		apptIdentifier);
	return appt;
    }

    /***
     * This method retrieves the ExpenseProfile valid at the current date given
     * an Appointment.
     * 
     * @param apptId
     * @return ExpenseProfiles
     */
    
    public List<ExpenseProfile> findExpenseProfiles(int apptId, Date effectiveDate) {
	logger
	.debug("Enter method : findExpenseProfiles(int apptId, Date effectiveDate)");
	
	String finderQuery = "SELECT   expense_profile_rules.VALUE ruleValue, "
	                     + "(SELECT rules.description "
	                	+ " FROM rules WHERE rules.rule_identifier = expense_profile_rules.rule_identifier)"
	                	+ " ruleName "
	                	+ " FROM expense_profiles, expense_profile_rules"
	                	+ " WHERE (expense_profiles.expf_identifier = expense_profile_rules.expf_identifier) "
	                	+ " and EXPENSE_PROFILES.APPT_IDENTIFIER = ?1"
	                	+ " and ?2 between EXPENSE_PROFILES.START_DATE and EXPENSE_PROFILES.END_DATE";
	
	List<ExpenseProfile> expProfileList = entityManager.createNativeQuery(
		finderQuery,ExpenseProfile.class).setParameter(1, apptId).setParameter(
		2, effectiveDate).getResultList();

	
	
	logger
	.debug("Exit method : findExpenseProfiles(int apptId, Date effectiveDate)");
	return expProfileList;
    }
    
    
    
   
    
    
    /**This method finds the effective date of the appointment
     * 
     * @param apptId
     * @return effectiveDate
     */
    public Date findEffectiveDate(int apptId) {
	logger.debug("Enter method : findEffectiveDate(int apptId");
	String finderQuery = "SELECT max(end_date) from appointment_histories ah where"
		+ " ah.appt_identifier = ?1 ";

	Date effectiveDate = null;
	Date returnedDate = null;
	Query query = entityManager.createNativeQuery(finderQuery);
	query.setParameter(1, apptId);

	try {
	    returnedDate = (Date) query.getSingleResult();
	} catch (NoResultException e) {
	    returnedDate = new Date();
	}
	
	if (returnedDate == null){
	    effectiveDate  = new Date();
	}
	else if (returnedDate.before(new Date(2222, 11, 30))) {
	     effectiveDate = returnedDate;
	}
	else
	    effectiveDate  = new Date();
	       

	return effectiveDate;
    }

    /** This method finds default distribution start date
     * @param effectiveDate
     * @return
     */
    public Date findDistStartDate(Date effectiveDate) {
	logger.debug("Enter method : findStartDistDate(Date effectiveDate)");
	String finderQuery = "SELECT f_dist_start_date(?1) from dual";
	Date distStartDate = (Date) entityManager
		.createNativeQuery(finderQuery).setParameter(1, effectiveDate)
		.getSingleResult();

	return distStartDate;

    }
    
    /**This method finds employee general info when a manageer or statewide users clicks on the employee
     * @param apptId
     * @param effectiveDate
     * @return EmployeeGeneralInformation list
     */
    public EmployeeGeneralInformation findEmployeeGeneralInfo(int apptId, Date effectiveDate) {
	
	
	
	String finderQuery =  EMP_GENERAL_INFO_SELECT +  EMP_GENERAL_INFO_FROM +  EMP_GENERAL_INFO_WHERE;
	
	Query query = entityManager.createNativeQuery(finderQuery, EmployeeGeneralInformation.class);
	query.setParameter(1, apptId);
	query.setParameter(2, effectiveDate);
	List<EmployeeGeneralInformation> empInfo = (List<EmployeeGeneralInformation>) query.getResultList();
        if (empInfo.size() > 0)	
	return empInfo.get(0);
        else 
            return null;

    }
    
    
    /**This method finds employee general info when a manageer or statewide users clicks on the employee information hyperlink
     * @param apptId
     * @param effectiveDate
     * @return EmployeeGeneralInformation list
     */
    public EmployeeGeneralInformation findEmployeeGeneralInfoManagerStatewide(int apptId, Date effectiveDate, String userId) {
	
	if (logger.isDebugEnabled()) {
	    logger
		    .debug("Enter method :: findEmployeeGeneralInfoManagerStatewide(int apptId, Date effectiveDate, String userId)");
	}
	
	
	String finderQuery =  EMP_GENERAL_INFO_SELECT +  EMP_GENERAL_INFO_FROM_SECURITY +  EMP_GENERAL_INFO_WHERE_SECURITY;
	
	Query query = entityManager.createNativeQuery(finderQuery, EmployeeGeneralInformation.class);
	query.setParameter(1, apptId);
	query.setParameter(2, effectiveDate);
	query.setParameter(3, userId);
	List<EmployeeGeneralInformation> empInfo = (List<EmployeeGeneralInformation>) query.getResultList();
        if (empInfo.size() > 0)	
	return empInfo.get(0);
        else 
            return null;

    }
    
    
    /**This method finds calculates the average hours for the appointment id
     * @param apptId
     * @param 
     * @return Average Hours
     */
    public int findAverageHrs(int apptId, Date effectiveDate) {
	if (logger.isDebugEnabled()) {
	    logger.debug("Enter method :: findAverageHrs(int apptId, Date effectiveDate)");
	}
	
	String finderQuery = "Select f_avg_hrs(?1,Calendar.pp_end_date) averageHrs FROM Calendar WHERE Calendar.Calendar_date = ?2";		
	Query query = entityManager.createNativeQuery(finderQuery);
	query.setParameter(1, apptId);
	query.setParameter(2, effectiveDate);

	int averageHrs = 0;
	BigDecimal averageHours = null;
	
	try {
	    averageHours =  (BigDecimal) query.getSingleResult();
	    averageHrs = averageHours.intValue();	
	} catch (NoResultException e) {
	    averageHrs = 0;
	}
	
	if (logger.isDebugEnabled()) {
	    logger
		    .debug("Exit method :: findAverageHrs(int apptId, Date effectiveDate)");
	}
	
	return averageHrs;

    }


    
    /**This method finds Default Distributions for expense.
     * @param apptId
     * @param effectiveDate
     * @return
     */
    public List<StandardDistCoding> findDefaultDistributionsExpense(int apptId,
	    Date effectiveDate, String userId, Date distStartDate) {

	if (logger.isDebugEnabled()) {
	    logger
		    .debug("Enter method :: findDefaultDistributionsExpense(int apptId, Date effectiveDate, String userId, Date distStartDate)");
	}
	// TODO: change back the module id
	String finderQuery = "SELECT  dd.dfdse_identifier dfdseIdentifier, "
		+ " dd.appropriation_year appropriationYear, "
		+ " dd.index_code indexCode,"
		+ " dd.pca pca,"
		+ " dd.grant_number grantNumber,"
		+ " dd.grant_phase grantPhase,"
		+ " dd.agency_code_1 agencyCode1,"
		+ " dd.project_number projectNumber,"
		+ " dd.project_phase projectPhase,"
		+ " dd.agency_code_2 agencyCode2, "
		+ " dd.agency_code_3 agencyCode3,"
		+ " dd.multipurpose_code multipurposeCode, "
		+ " dd.PERCENT percent,"
		+ " dd.start_date startDate,"
		+ " dd.end_date endDate, "
		+ " 'EXPN' source"
		+ " FROM default_distributions_expense dd, appointment_histories ah, security sec "
		+ " WHERE dd.appt_identifier = ah.appt_identifier "
		+ " and dd.appt_identifier = ?1"
		+ " and sec.user_id = ?3"
		+ " and sec.module_id = 'CMNW001'"
		+ " and ((dd.start_date between ah.appointment_date and ah.departure_or_end_date AND ah.end_date = (select max(ah1.end_date) from APPOINTMENT_HISTORIES ah1"
		+ " WHERE ah1.appt_identifier = ah.appt_identifier"
		+ " AND ah1.end_date >= ah1.start_date)) OR"
		+ " (dd.end_date between ah.appointment_date and ah.departure_or_end_date AND ah.end_date = (select max(ah1.end_date) from APPOINTMENT_HISTORIES ah1"
		+ " WHERE ah1.appt_identifier = ah.appt_identifier"
		+ " AND ah1.end_date >= ah1.start_date)))"
		+ " and ?2 between ah.appointment_date and ah.departure_or_end_date AND ah.end_date = (select max(ah1.end_date) from APPOINTMENT_HISTORIES ah1"
		+ " WHERE ah1.appt_identifier = ah.appt_identifier"
		+ " AND ah1.end_date >= ah1.start_date) "
		+ " and (ah.department between decode(sec.department,'AL','00',sec.department)"
		+ " and decode(sec.department,'AL','99',sec.department))"
		+ " and (ah.agency between decode(sec.agency,'AL','00',sec.agency)"
		+ " and decode(sec.agency,'AL','99',sec.agency))"
		+ " and (ah.tku between decode(sec.tku,'AL','00',sec.tku)"
		+ " and decode(sec.tku,'AL','99',sec.tku))"
		+ " and dd.end_date > ?4"
		+ " union"
		+ " SELECT  dd.dfds_identifier dfdseIdentifier, "
		+ " dd.appropriation_year appropriationYear, "
		+ " dd.index_code indexCode,"
		+ " dd.pca pca,"
		+ " dd.grant_number grantNumber,"
		+ " dd.grant_phase grantPhase,"
		+ " dd.agency_code_1 agencyCode1,"
		+ " dd.project_number projectNumber,"
		+ " dd.project_phase projectPhase,"
		+ " dd.agency_code_2 agencyCode2, "
		+ " dd.agency_code_3 agencyCode3,"
		+ " dd.multipurpose_code multipurposeCode, "
		+ " dd.PERCENT percent,"
		+ " dd.start_date startDate,"
		+ " dd.end_date endDate, "
		+ " 'DCDS' source"
		+ " FROM default_distributions dd, appointment_histories ah, security sec "
		+ " WHERE dd.appt_identifier = ah.appt_identifier "
		+ " and dd.appt_identifier = ?1"
		+ " and sec.user_id = ?3"
		+ " and sec.module_id = 'CMNW001'"
		+ " and ((dd.start_date between ah.appointment_date and ah.departure_or_end_date AND ah.end_date = (select max(ah1.end_date) from APPOINTMENT_HISTORIES ah1"
		+ " WHERE ah1.appt_identifier = ah.appt_identifier"
		+ " AND ah1.end_date >= ah1.start_date)) OR "
		+ " (dd.end_date between ah.appointment_date and ah.departure_or_end_date AND ah.end_date = (select max(ah1.end_date) from APPOINTMENT_HISTORIES ah1"
		+ " WHERE ah1.appt_identifier = ah.appt_identifier"
		+ " AND ah1.end_date >= ah1.start_date)))"
		+ " and ?2 between ah.appointment_date and ah.departure_or_end_date AND ah.end_date = (select max(ah1.end_date) from APPOINTMENT_HISTORIES ah1"
		+ " WHERE ah1.appt_identifier = ah.appt_identifier"
		+ " AND ah1.end_date >= ah1.start_date) "
		+ " and (ah.department between decode(sec.department,'AL','00',sec.department)"
		+ " and decode(sec.department,'AL','99',sec.department))"
		+ " and (ah.agency between decode(sec.agency,'AL','00',sec.agency)"
		+ " and decode(sec.agency,'AL','99',sec.agency))"
		+ " and (ah.tku between decode(sec.tku,'AL','00',sec.tku)"
		+ " and decode(sec.tku,'AL','99',sec.tku))"
		+ " and dd.end_date > ?4" + " order by 14, 13";

	Query query = entityManager.createNativeQuery(finderQuery,
		StandardDistCoding.class);
	query.setParameter(1, apptId);
	query.setParameter(2, effectiveDate);
	query.setParameter(3, userId);
	query.setParameter(4, distStartDate);
	List<StandardDistCoding> defCoding = (List<StandardDistCoding>) query
		.getResultList();
	if (logger.isDebugEnabled()) {
	    logger
		    .debug("Exit method :: findDefaultDistributionsExpense(int apptId, Date effectiveDate, String userId, Date distStartDate)");
	}
	return defCoding;

    }

    /**
     * Find active appointment start and end date range
     */
	public AppointmentsBean findActiveAppointmentDateSpan(long appointmentId) {
		
		String queryStr = "SELECT ah.appointment_date, ah.end_date, ah.departure_date departureDate from Appointments a, APPOINTMENT_HISTORIES ah" +
				" WHERE ah.Appt_Identifier = a.Appt_Identifier" +
				" and a.Appt_Identifier = ?1 and" +
				" ah.end_Date = (SELECT max(End_Date) max_end_date FROM APPOINTMENT_HISTORIES ah2 WHERE ah2.Appt_Identifier = a.Appt_Identifier and ah2.Appt_Identifier = ah.Appt_Identifier)";

		List<AppointmentsBean> appts = entityManager.createNativeQuery(queryStr, AppointmentsBean.class)
													.setParameter(1, appointmentId)
													.getResultList();
		
		if(appts.size() <1) return null;
		return appts.get(0);
	}
    
	/**
	 * Finds appointment IDs for appointments that have at least one expense associated to them.
	 * The returned list is always a subset(/same set) of the provided list.  
	 * 
	 * @param appointmentIds - list of all appointment IDs from which to select the one that have
	 * 					at least one expense associated.
	 * @return - list of appointment IDs from the list provided that have at least one expense 
	 * 				associated to them.  
	 */
	public List<Integer> findAppointmentIdsHavingExpenses(List<Integer> appointmentIds){
		List<String> batchedIntValues = prepareBatchedCommaSeperatedIntValues(appointmentIds);

		if(batchedIntValues.isEmpty()) return new ArrayList<Integer>();
		
		StringBuilder buff = new StringBuilder();
		for(String str: batchedIntValues){
			buff.append("APPT_IDENTIFIER IN (");
			buff.append(str);
			buff.append(") OR ");
		}
		String queryINclause = buff.substring(0, buff.length()-4);
		
		String query = "select distinct(APPT_IDENTIFIER) from EXPENSE_EVENTS where " + queryINclause; 
		
		List<BigDecimal> apptIdsWithExpenses = entityManager.createNativeQuery(query).getResultList();
		
		// convert from BigDecimal to Integer types
		List<Integer> appointmentsWithAtleastOneExpense = new ArrayList<Integer>();
		for(BigDecimal apptId : apptIdsWithExpenses){
			appointmentsWithAtleastOneExpense.add(apptId.intValue());
		}
		
		return appointmentsWithAtleastOneExpense;
	}
	public List<Integer> findAppointmentIdsHavingTravelRequisitions(List<Integer> appointmentIds){
		List<String> batchedIntValues = prepareBatchedCommaSeperatedIntValues(appointmentIds);

		if(batchedIntValues.isEmpty()) return new ArrayList<Integer>();
		
		StringBuilder buff = new StringBuilder();
		for(String str: batchedIntValues){
			buff.append("APPT_IDENTIFIER IN (");
			buff.append(str);
			buff.append(") OR ");
		}
		String queryINclause = buff.substring(0, buff.length()-4);
		
		String query = "select distinct(APPT_IDENTIFIER) from TRAVEL_REQ_EVENTS where " + queryINclause; 
		
		List<BigDecimal> apptIdsWithExpenses = entityManager.createNativeQuery(query).getResultList();
		
		// convert from BigDecimal to Integer types
		List<Integer> appointmentsWithAtleastOneTravelRequisition = new ArrayList<Integer>();
		for(BigDecimal apptId : apptIdsWithExpenses){
			appointmentsWithAtleastOneTravelRequisition.add(apptId.intValue());
		}
		
		return appointmentsWithAtleastOneTravelRequisition;
	}


	/**
	 * Finds appointment IDs for appointments that have at least one advance associated to them.
	 * The returned list is always a subset(/same set) of the provided list.  
	 * 
	 * @param appointmentIds - list of all appointment IDs from which to select the one that have
	 * 					at least one advance associated.
	 * @return - list of appointment IDs from the list provided that have at least one advance 
	 * 				associated to them.  
	 */
	public List<Integer> findAppointmentIdsHavingAdvances(List<Integer> appointmentIds){
		List<String> batchedIntValues = prepareBatchedCommaSeperatedIntValues(appointmentIds);

		if(batchedIntValues.isEmpty()) return new ArrayList<Integer>();
		
		StringBuilder buff = new StringBuilder();
		for(String str: batchedIntValues){
			buff.append("APPT_IDENTIFIER IN (");
			buff.append(str);
			buff.append(") OR ");
		}
		
		String queryINclause = buff.substring(0, buff.length()-4);
		
		String query = "select distinct(APPT_IDENTIFIER) from ADVANCE_EVENTS where " + queryINclause; 
		
		List<BigDecimal> apptIdsWithAdvances = entityManager.createNativeQuery(query).getResultList();
		
		// convert from BigDecimal to Integer types
		List<Integer> appointmentsWithAtleastOneAdvance = new ArrayList<Integer>();
		for(BigDecimal apptId : apptIdsWithAdvances){
			appointmentsWithAtleastOneAdvance.add(apptId.intValue());
		}
		
		return appointmentsWithAtleastOneAdvance;
	}
	
	/**
	 * Prepares comma separated string by joining the values in the provided list. It
	 * does so in batches of 1000 elements per string. E.g. If there are 1001 items 
	 * in the list provide as input then this method would return 2 strings back, 
	 * one containing 1000 elments and the other containing 1. 
	 * 
	 * @param apptIds
	 * @return array of string; with each string containing 1000 comma separated values
	 */
	public List<String> prepareBatchedCommaSeperatedIntValues(List<Integer> apptIds){
		List<String> batchedIntValues = new ArrayList<String>();
		
		if(apptIds == null || apptIds.isEmpty()) return batchedIntValues;
		
		StringBuilder buff = new StringBuilder(1000);
		for(int i=0; i<apptIds.size() ; i++){
			if(i!= 0 && i%1000==0){
				batchedIntValues.add(buff.substring(0, buff.length()-1));
				buff = new StringBuilder(1000);
			}
			buff.append(apptIds.get(i)+",");
		}

		batchedIntValues.add(buff.substring(0, buff.length()-1));
		
		return batchedIntValues;
	}
	
/**
 * Check for active/inactive status for the employee
 * @param empId
 * @return
 */
    public String findLatestActiveAppointmentHistoryEmployee(int empId){
    	String query ="SELECT ah.apptStatusCode from AppointmentHistory ah, Appointment a where a.id = ah.appointmentId" +
    	              " AND a.empId = :empId" +
 		              " and (ah.apptStatusCode < 'EA' OR ah.apptStatusCode like 'Z%')" + 
 		             " and ah.apptHistoryId = (select max(ah1.apptHistoryId) from" +
 		             " AppointmentHistory ah1, Appointment a1 where a1.id = ah1.appointmentId and a1.empId = :empId and ah1.positionLevel = 1 and ah1.endDate = '31-DEC-2222')";
    	List<String> apptHistories = entityManager.createQuery(query)
    													.setParameter("empId", empId)
    													.getResultList();
    	
    	// this would always contain unique result or no result
    	return (apptHistories.size()>0) ? apptHistories.get(0) : null;
    }
    
    /**
     * Fetches the employee id associated with an appointment
     * @param apptId
     * @return
     */
    
    public Integer findEmployeeByAppointmentId(int apptId){
    	String query ="select a.empId from Appointment a where a.id =: apptId";
    	List<Integer> empIdList = entityManager.createQuery(query)
    													.setParameter("apptId", apptId)
    													.getResultList();
    	return (empIdList.size()>0) ? empIdList.get(0) : null;
    }
    
    /**
     * Checks for an inactive status for an employee for a specific date if an employee is in an "E" status
     * @param appointmentId
     * @param expenseToDate
     * @return
     */
    
    public int findInactiveStatusForExpenseToDateLeave (int appointmentId, Date expenseToDate){
    	java.sql.Date expDate = new java.sql.Date (expenseToDate.getTime());
    	String finderQuery = "Select count(*) From Appointment_Histories Where appt_identifier =?1 "
			+ "and ?2 between Appointment_Date and (Departure_Date - 1) and Appt_Status_Code like 'E%'";

    	BigDecimal count = (BigDecimal) entityManager.createNativeQuery(finderQuery)
							.setParameter(1,appointmentId)
							.setParameter(2,expDate).getSingleResult();
				

    	// this would always contain unique result or no result
    	return count.intValue();
    }
    
    /**
     * Checks for an inactive status for an employee for a specific date if an employee is in an "G" status
     * @param appointmentId
     * @param expenseToDate
     * @return
     */
    
    public int findInactiveStatusForExpenseToDateTerminated (int appointmentId, Date expenseToDate){
    	String finderQuery = "Select count(*) From Appointment_Histories Where appt_identifier =?1 "
			+ "and ?2 between Appointment_Date and Departure_Date and Appt_Status_Code like 'G%'";
    	
    	java.sql.Date expDate = new java.sql.Date (expenseToDate.getTime());

    	BigDecimal count = (BigDecimal) entityManager.createNativeQuery(finderQuery)
							.setParameter(1,appointmentId)
							.setParameter(2,expDate).getSingleResult();
				

    	// this would always contain unique result or no result
    	return count.intValue();
    }
    
    /**
     * Fetches the latest appointment using a FACS agency's department and agency
     * @param empId
     * @param Dept
     * @param agency
     * @return
     */
    public int findLatestAppointmentForFacsAgency (int empId, String Dept, String agency){
    	String finderQuery = "SELECT MAX (appt_identifier) FROM appointments WHERE emp_identifier = ? " +
    						 "and department = ? and agency = ?";

    	List<BigDecimal> apptList = (List<BigDecimal>) entityManager.createNativeQuery(finderQuery)
							.setParameter(1,empId)
    						.setParameter(2,Dept)
    						.setParameter(3,agency).getResultList();

    	return apptList.size() > 0 ? apptList.get(0).intValue() : null;
    }
    
    public AppointmentHistory findAppointmentHistory(Integer apptId){
    	String query ="SELECT ah from AppointmentHistory ah where ah.appointmentId = :apptId" +
    	              " AND ah.endDate = (select max(ap1.endDate) from AppointmentHistory ap1" +
 		              " WHERE ap1.appointmentId = ah.appointmentId" +
 		              " AND ap1.endDate >= ap1.startDate)";
    	List<AppointmentHistory> apptHistories = entityManager.createQuery(query)
    													.setParameter("apptId", apptId)
    													.getResultList();
    	
    	// this would always contain unique result or no result
    	return (apptHistories.size()>0) ? apptHistories.get(0) : null;
    }
    
}
