package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.exception.TimeAndExpenseException;
import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AppointmentHistory;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.OutOfStateTravel;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.service.AdvanceLiquidationDSP;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.util.ExpenseViewUtil;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import org.apache.log4j.Logger;

/**
 * Action responsible for saving an expense. The expense could
 * be either a new expense or an updated existing expense.
 * 
 * Validations are performed to ascertain the required input data
 * required for an expense. If any of the mandatory input fields
 * is missing, the expense is not saved and a validation message 
 * is returned.
 * 
 * As an expense can be saved with warnings and other permissible
 * business error conditions, the save operation is performed 
 * successfully in such a case. The user is notified of any such
 * warning conditions, though.  
 * 
 * @author chaudharym
 */
public class SaveExpenseAction extends BaseAction {

	private static final long serialVersionUID = 42322243823439L;
	private static Logger logger = Logger.getLogger(SaveExpenseAction.class);

	protected ExpenseMasters expenseMaster;
	private List<Integer> selectedAuthCodes;

	private ExpenseDSP expenseService;
	private ExpenseLineItemDSP expenseDetailService;
	private AppointmentDSP appointmentService;
	private AdvanceLiquidationDSP liquidationService;
	private gov.michigan.dit.timeexpense.service.SecurityManager securityService;
	private CommonDSP commonService;
	private CodingBlockDSP codingBlockService;
	// Used to track a prior inactive status for an active employee
	Boolean inactiveStatusForExpenseToDate;
	
	private DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
	
	public SaveExpenseAction() {
		selectedAuthCodes = new ArrayList<Integer>();
	}
	
	/**
	 * @see BaseAction#prepare()
	 */
	public void prepare(){
		expenseService = new ExpenseDSP(entityManager);
		expenseDetailService = new ExpenseLineItemDSP(entityManager);
		appointmentService = new AppointmentDSP(entityManager);
		liquidationService = new AdvanceLiquidationDSP(entityManager);
		securityService = new gov.michigan.dit.timeexpense.service.SecurityManager(entityManager);
		commonService = new CommonDSP(entityManager);
		codingBlockService = new CodingBlockDSP(entityManager);
	}

	@Override
	public String execute() throws Exception {
		ExpenseMasters originalExpenseBackup = null;
		ExpenseMasters savedExpense = null;
		ExpenseViewUtil util = new ExpenseViewUtil();
		util.setJsonParser(jsonParser);
		util.setCodingBlockService(codingBlockService);
		util.setAppointmentService(appointmentService);
		try {
			// refresh
			ExpenseMasters expm = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);
			if(expm != null){
				expm = entityManager.merge(expm);
				originalExpenseBackup = prepareExpenseOnlyData(expm);
				session.put(IConstants.EXPENSE_SESSION_DATA, expm);
				
				// first delete all existing errors related to expense ID
				commonService.deleteExpenseErrors(expm, IConstants.EXP_ERR_SRC_ID_TAB);
				
				// delete cross tab errors relating to TripId tab
				deleteCrossTabExpenseErrorsForTripId(expm);
				
				// flush expense error changes
				entityManager.flush();
			}
			
			// merge new expense data into Expense object stored in session, if applicable
			ExpenseMasters expenseMasterInSession = mergeWithExpenseInSession();
			
			UserProfile userProfile = getLoggedInUser();
			// perform biz validations and populate errors, if any
			expenseService.validateExpenseID(expenseMasterInSession, getUserSubject(), userProfile);
			
			// perform cross tab validations
			expenseDetailService.validateExpenseDetailsDate(expenseMasterInSession,userProfile);
			
			// Get appointment information from UserSubject in session
			UserSubject userSubject = getUserSubject();
			if(userSubject == null || userSubject.getAppointmentId() == 0){
				String errorMsg = "User subject information not found in session";
				logger.error(errorMsg);
				throw new TimeAndExpenseException(errorMsg);
			}
			
			
			savedExpense = expenseService.saveExpense(expenseMasterInSession, userSubject, getModuleIdFromSession(), getLoggedInUser());
			
			//TODO[mc]: If a new revision has been created from the current expense, the new revision would contain
			// the updated new information. But the same information also got saved to the current expense, while
			// getting expense data from view. So, undo the changes to the current expense, if new revision created.
			if(savedExpense.getRevisionNumber() > expenseMasterInSession.getRevisionNumber()){
				updateCurrentExpenseWithOldInfoIfNewRevisionCreated(expenseMasterInSession, originalExpenseBackup);
			}
	
			// write all the changes explicitly, to generate Ids
			persistExpenseMasterState(savedExpense);
			
			// If new revision created, copy over the attached liquidations too.
			//PS: This is being done after flush(), because the liquidations require expense master Id.
			if(savedExpense.getRevisionNumber() > expenseMasterInSession.getRevisionNumber()){
				copyLiquidationsFromOldExpenseRevision(savedExpense, originalExpenseBackup);
			}
	
			// write all the changes explicitly, to generate Ids
			entityManager.flush();
			// push saved expense into session
			session.put(IConstants.EXPENSE_SESSION_DATA, savedExpense);
			StringBuilder buff = new StringBuilder();
			buff.append("{expenseEventId:'");
			buff.append(savedExpense.getExpevIdentifier().getExpevIdentifier());
			buff.append("',revisionNo:");
			buff.append(savedExpense.getRevisionNumber());
			buff.append("}");
			
			setJsonResponse(buff.toString());
			// propagate expense errors
			addTimeAndExpenseErrors(util.prepareTimeAndExpenseErrors(savedExpense));
		} catch (OptimisticLockException e) {
			addFieldError("errors", "The record you are attempting to change has already been modified by another user.  Please reopen the record before making any changes.");
		}
		
		return IConstants.SUCCESS;
	}
	
	private void deleteCrossTabExpenseErrorsForTripId(ExpenseMasters expense) {
		List<String> expenseRelatedCrossTabErrorCodes = new ArrayList<String>();
		expenseRelatedCrossTabErrorCodes.add(IConstants.EXPENSE_DETAILS_DATE_NOT_BETWEEN_EXPENSE_ID_DATE);
		
		commonService.deleteExpenseErrors(expense, expenseRelatedCrossTabErrorCodes);
	}

	/** Explicitly saves all the collection entries in the expense 
	 * and then flushes the changes to the DB.
	 * PS: This is just a workaround for the OpenJPA strange behavior.
	 */
	private void persistExpenseMasterState(ExpenseMasters master) {
		if(master.getExpenseErrorsCollection() != null){
			// persist errors
			for(ExpenseErrors error : master.getExpenseErrorsCollection()){
				if(error.getExperIdentifier() == null)
					entityManager.persist(error);
				else
					error = entityManager.merge(error);
			}
		}

		if(master.getOutOfStateTravelList() != null){
			// persist oost
			for(OutOfStateTravel oost : master.getOutOfStateTravelList()){
				if(oost.getOostIdentifier() == null)
					entityManager.persist(oost);
				else
					oost = entityManager.merge(oost);
			}
		}
		
		entityManager.flush();
	}
	
	private void copyLiquidationsFromOldExpenseRevision(ExpenseMasters savedExpense, ExpenseMasters originalExpenseBackup) {
		List<AdvanceLiquidations> oldLiquidations = liquidationService.findLiquidationsByExpenseId(originalExpenseBackup.getExpmIdentifier());
		
		for(AdvanceLiquidations oldLiq : oldLiquidations){
			// [mc - 2/5/2010] Only copy those liquidations that are attached to the current advance.
			// Becos, if the advance is not current then it implies a newer version of it exists.
			// And that version would automatically carry over the required liquidations upon
			// being finally approved. If for some reason, the new version gets rejected then this prev.
			// is made current. So, we can safely assume the current advance to be the one to find out
			// active liquidations.
			if(!oldLiq.getAdvanceMaster().getCurrentInd().equals("Y")) continue;
			
			AdvanceLiquidations newLiq = new AdvanceLiquidations(oldLiq);
			newLiq.setExpenseMasterId(savedExpense.getExpmIdentifier());
			newLiq.setModifiedUserId(getLoggedInUser().getUserId());
			entityManager.persist(newLiq);
		}
	}

	private ExpenseMasters prepareExpenseOnlyData(ExpenseMasters expm) {
		ExpenseMasters master = new ExpenseMasters(expm);
		master.setExpmIdentifier(expm.getExpmIdentifier());
		master.setExpevIdentifier(null);
		master.setExpenseDetailsCollection(null);
		master.setExpenseErrorsCollection(null);
		
		return master;
	}

	private void updateCurrentExpenseWithOldInfoIfNewRevisionCreated(ExpenseMasters expense, ExpenseMasters oldBackup) {
		// only undo data that could have been send by the view.
		expense.setComments(oldBackup.getComments());
		expense.setExpDateFrom(oldBackup.getExpDateFrom());
		expense.setExpDateTo(oldBackup.getExpDateTo());
		expense.setNatureOfBusiness(oldBackup.getNatureOfBusiness());
		expense.setOutOfStateInd(oldBackup.getOutOfStateInd());
		expense.setTravelInd(oldBackup.getTravelInd());
		expense.setPdfInd(oldBackup.getPdfInd());
		expense.setOrigPaidDate(oldBackup.getOrigPaidDate());
		
		// If user chooses same Out Of State travel code after modification, 
		// we need to explicitly delete those carried forwarded ones as we'll be adding fresh ones below.
		List<OutOfStateTravel> oostCodes = expense.getOutOfStateTravelList();
		for(OutOfStateTravel oost : oostCodes){
			// explicitly remove carry forwarded out of state travel codes
			if(oost.getOostIdentifier() != null) entityManager.remove(oost);
		}
		// remove all existing codes.
		oostCodes.clear();
		entityManager.flush();
		
		for(OutOfStateTravel oostDB: oldBackup.getOutOfStateTravelList()){
			OutOfStateTravel oost = new OutOfStateTravel(oostDB);
			oostCodes.add(oost);
			oost.setExpmIdentifier(expense);
			entityManager.persist(oost);
		}
	}

	private ExpenseMasters mergeWithExpenseInSession() {
		
		ExpenseMasters expenseMasterInSession = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);
		
		expenseMasterInSession = (expenseMasterInSession == null) ? expenseMaster : expenseMasterInSession;
		
		expenseMasterInSession.setExpDateFrom(expenseMaster.getExpDateFrom());
		expenseMasterInSession.setExpDateTo(expenseMaster.getExpDateTo());
		expenseMasterInSession.setComments(expenseMaster.getComments());
		expenseMasterInSession.setNatureOfBusiness(expenseMaster.getNatureOfBusiness());
		expenseMasterInSession.setTravelInd(expenseMaster.getTravelInd());
		expenseMasterInSession.setOutOfStateInd(expenseMaster.getOutOfStateInd());
		expenseMasterInSession.setPdfInd(expenseMaster.getPdfInd());
		
		//set superReviewedReceiptsInd to false if not present already
		if(expenseMasterInSession.getSuperReviewedReceiptsInd() == null)
			expenseMasterInSession.setSuperReviewedReceiptsInd("N");
		
		
		boolean outOfStateChecked = 
			expenseMaster.getOutOfStateInd() != null && "Y".equals(expenseMaster.getOutOfStateInd())
			? true : false;
		
		if(!outOfStateChecked || selectedAuthCodes == null || selectedAuthCodes.size() == 0){
			if(expenseMasterInSession.getOutOfStateTravelList() != null){
				expenseMasterInSession.getOutOfStateTravelList().clear();
				for(OutOfStateTravel oost : expenseMasterInSession.getOutOfStateTravelList()) entityManager.remove(oost);
				entityManager.flush();
			}
			
		}else{
			if(expenseMasterInSession.getOutOfStateTravelList() != null){
				
				Map<Integer, Object> prevOOSTIdMap = 
					new HashMap<Integer, Object>(expenseMasterInSession.getOutOfStateTravelList().size()+1, 1);
				
				for(OutOfStateTravel oost : expenseMasterInSession.getOutOfStateTravelList()){
					prevOOSTIdMap.put(new Integer(oost.getStacIdentifier().intValue()), "");
				}
				
				// remove from original collection the ones that have been unselected
				Iterator<OutOfStateTravel> itr = expenseMasterInSession.getOutOfStateTravelList().iterator();
				while(itr.hasNext()){
					OutOfStateTravel oost = itr.next();
						
						boolean existsAlready = false;
						for(Integer stacCode : selectedAuthCodes){
							if(oost.getStacIdentifier().equals(stacCode)){
								existsAlready = true;
								break;
							}
						}
					
						if(!existsAlready) itr.remove();
				}
				
				// add to collection the new items selected
				for(Integer stacCode : selectedAuthCodes){
					if(!prevOOSTIdMap.containsKey(stacCode)){
						OutOfStateTravel newOutOfStateTravel = new OutOfStateTravel();
						newOutOfStateTravel.setExpmIdentifier(expenseMasterInSession);
						newOutOfStateTravel.setStacIdentifier(stacCode);
						
						expenseMasterInSession.getOutOfStateTravelList().add(newOutOfStateTravel);
					}
				}
			}else{
				List<OutOfStateTravel> newOutofStateTravelList = new ArrayList<OutOfStateTravel>();
				
				for(Integer stacCode : selectedAuthCodes){
					OutOfStateTravel newOutOfStateTravel = new OutOfStateTravel();
					newOutOfStateTravel.setExpmIdentifier(expenseMasterInSession);
					newOutOfStateTravel.setStacIdentifier(stacCode);
					
					newOutofStateTravelList.add(newOutOfStateTravel);
				}
				
				expenseMasterInSession.setOutOfStateTravelList(newOutofStateTravelList);
			}
		}

		return expenseMasterInSession;
	}

	@Override
	public void validate() {
		if(expenseMaster ==null) return;
		
		if(expenseMaster.getExpDateFrom() == null) addFieldError("errors", "Expense from date missing");
		if(expenseMaster.getExpDateTo() == null) addFieldError("errors", "Expense to date missing");
		if(expenseMaster.getExpDateFrom() != null && expenseMaster.getExpDateTo() != null 
				&& expenseMaster.getExpDateFrom().after(expenseMaster.getExpDateTo())) 
			addFieldError("errors", "Expense TO date cannot be greater than expense FROM date");
		if(expenseMaster.getNatureOfBusiness() == null || expenseMaster.getNatureOfBusiness().trim().length()<1)
			addFieldError("errors", "Nature of official business missing");
		if(expenseMaster.getTravelInd() == null || expenseMaster.getTravelInd().trim().length()<1)
			expenseMaster.setTravelInd("N");
		//	addFieldError("errors", "Expense type missing");
		if(expenseMaster.getOutOfStateInd() != null && "Y".equals(expenseMaster.getOutOfStateInd())
				&& selectedAuthCodes.size()<1)
			addFieldError("errors", "Authorization code(s) required for out of state travel");
		if(expenseMaster.getComments() != null && expenseMaster.getComments().trim().length() > 255)
			addFieldError("errors", "Comments longer than allowed (255 characters). Please enter a shorter value.");
		
		//Validating expense From date. Should be less than the system defined date.		
		Date expenseEndDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		String strExpenseEndDate = commonService
				.getSystemCodeValue(IConstants.EXPENSE_END_DATE_SYSTEM_CODE);
		
		try {
			expenseEndDate = sdf.parse(strExpenseEndDate);
			if (expenseMaster.getExpDateFrom() != null
					&& expenseMaster.getExpDateFrom().after(expenseEndDate)){
				
			String strErrorMessage = commonService.getErrorCode(IConstants.EXPENSE_FROM_DATE_AFTER_SYSTEM_PERMISSION_CLIENT_MESSAGE).getErrorText();
			//Replacing date place holder with date from DB. 
			strErrorMessage = strErrorMessage.replace("{0}", strExpenseEndDate);
			
				addFieldError("errors",strErrorMessage);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		String moduleId = getModuleIdFromSession();
		
		// 1) ASSERT SINGLE APPT
		// Check if multiple appointments exist for the provided expense dates.
		// PS: This is not required if the user has explicitly selected the appt.
		if(!getUserSubject().isSingleAppointmentChosen()){
			boolean result = validateUniqueAppointmentForExpenseDates();
			// If a problem already found and error for the same raised, then just return.
			if(!result) return;
		}
		
		if(IConstants.EXPENSE_MANAGER.equals(moduleId) || 
				IConstants.EXPENSE_STATEWIDE.equals(moduleId) ||
				IConstants.APPROVE_WEB_MANAGER.equals(moduleId) ||
				IConstants.APPROVE_WEB_STATEWIDE.equals(moduleId)){
			// Check if subject has a valid active appointment i.e. not departed or infinite leave etc.
			// for the expense TO date
			boolean result = validateSubjectActiveAppointment(moduleId);
			// If a problem already found and error for the same raised, then just return.
			if(!result) return;
			
			// For manager & state wide access employee, check whether right security exists to create
			// the expense on behalf of the user subject i.e. check if the logged in user has access to the 
			// Subject's dept, agency and tku for the entered expenseTo date.
			result = validateSupervisorSecurityScopeAccessOnExpenseToDate(moduleId);
			// If a problem already found and error for the same raised, then just return.
			if(!result) return;
			
			
			//PDF roles are only allowed to create PDF expense, they can't create regular expenses.
			//If user has the following roles ('TRAVL COORD EXP', 'SUPERVIS EXP', 'REGION APPR EXP', 'PERSON APPR EXP','FINAN APPR1 EXP', 'FINAN APPR2 EXP')
			//, allow them to create regular expenses, else fire an error.
			
			if (!expenseMaster.isPdfInd()
					&& expenseService.isUserWithPDFRole(getLoggedInUser()
							.getUserId(), getUserSubject().getDepartment(),
							getUserSubject().getAgency(), getUserSubject()
									.getTku())) {
				if (!expenseService
						.doesUserHasStatewideRoles(getLoggedInUser()
								.getUserId(), getUserSubject().getDepartment(),
								getUserSubject().getAgency(), getUserSubject()
										.getTku(), getModuleIdFromSession())) {
					addFieldError("errors", "You can only create PDF expenses");
				}
			}
		}
		// employee creating expense for himself
		else if(IConstants.EXPENSE_EMPLOYEE.equals(moduleId)){
			UserSubject subject = getUserSubject();

			// 2) ASSERT SECURITY FOR CREATING/MODIFYING EXPENSES FOR "THAT" APPT
			// By now a single appointment for the employee would have been decided. Let's check if
			// he has security to create expenses for this appointment.
			if(expenseMaster.getRevisionNumber() == 0){
				boolean userSecurityToCreateExpenseForSelf = securityService.checkModuleAccess(getLoggedInUser(), 
																moduleId, subject.getDepartment(), 
																subject.getAgency(), subject.getTku());
				if(!userSecurityToCreateExpenseForSelf){
					addFieldError("errors", "You are not allowed to create expenses for these expense dates.");
					return;
				}
			}
		}
		
		//validate user options and valid expense details.
		//Pass this validation in case of super user.		
		ExpenseMasters expm = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);
		//if this is the first time expm will be null. 
		if (expm == null)
				return;
		
		UserProfile userProfile = getLoggedInUser();
		if(!CommonDSP.isUserSuperUser(userProfile)){
			
			String strReturnErrors = expenseService.isUserOptionExpenseValid(expm.getExpmIdentifier() , expenseMaster.getTravelInd(), expenseMaster.getPdfInd(), expenseMaster.getOutOfStateInd());
			
			if (strReturnErrors.length() > 0 )
			{
				addFieldError("errors", strReturnErrors);
				return;
			}
		}	
	}

	/*
	 * Verify the expense dates are completely within the Subject's appt dates
	 */
	private boolean validateSubjectActiveAppointment(String moduleId) {
		UserSubject subject = getUserSubject();
		boolean result = false;
		
		if(!expenseMaster.getExpDateFrom().before(subject.getAppointmentDate()) 
				&& !expenseMaster.getExpDateTo().after(subject.getAppointmentEnd())){
			result = true;
		}else{
				// Expense dates not within the active date. Check for a prior inactive status 
				getInactiveStatusForExpenseToDate ();
				if (inactiveStatusForExpenseToDate)
					result = true;
				else {
				StringBuilder buff = new StringBuilder("No unique active appointment for the chosen expense dates. ");
				
				// add date mismatch info to error
				if(expenseMaster.getExpDateFrom().before(subject.getAppointmentDate())){
					buff.append("Appointment starts on "+df.format(subject.getAppointmentDate()));
				}else if(expenseMaster.getExpDateTo().after(subject.getAppointmentEnd())){
					buff.append("Appointment ends on "+df.format(subject.getAppointmentEnd()));
				}
				
				addFieldError("errors", buff.toString());
				result = false;
				}
		}
		return result;
	}

	private boolean validateSupervisorSecurityScopeAccessOnExpenseToDate(
			String moduleId) {
		boolean supervisorAccessExists = false;
		if (!supervisorAccessExists()) {
			// No access for expense dates. Check access for a prior inactive status
			if (inactiveStatusForExpenseToDate == null)
				getInactiveStatusForExpenseToDate ();
			if (inactiveStatusForExpenseToDate != null && inactiveStatusForExpenseToDate)
				supervisorAccessExists = true;
			} else
				supervisorAccessExists = true;

		// raise error if no access!
		if (!supervisorAccessExists) {
			addFieldError("errors",
					"User does not have system access for the chosen expense dates.");
			return false;
		}

		return true;
	}
	
	/**
	 * Checks for a prior inactive status for an active employee
	 */
	
	private void getInactiveStatusForExpenseToDate (){
		UserSubject subject = getUserSubject();
		if (appointmentService.isEmployeeActive(subject.getEmployeeId())) {
			inactiveStatusForExpenseToDate = appointmentService
					.getInactiveStatusForExpenseToDate(subject
							.getAppointmentId(), expenseMaster
							.getExpDateTo());
		}

	}
	
	private boolean supervisorAccessExists(){
		Date expToDate = expenseMaster.getExpDateTo();
		UserSubject subject = getUserSubject();
		
		String scopedDept = "";
		String scopedAgency = "";
		String scopedTku = "";
		
		// if exp TO date not within selected appointment, find the dept, agency, tku on exp TO date
		if(expToDate.before(subject.getAppointmentStart()) || expToDate.after(subject.getAppointmentEnd())){

			// find the appointment, for the given exp TO date
			AppointmentHistory apptHistory = appointmentService.getAppointmentHistory(subject.getAppointmentId(), expenseMaster.getExpDateTo());
			
			if(apptHistory!=null){
				scopedDept = apptHistory.getDepartment();
				scopedAgency = apptHistory.getAgency();
				scopedTku = apptHistory.getTku();
			}
		}else{
			scopedDept = subject.getDepartment();
			scopedAgency = subject.getAgency();
			scopedTku = subject.getTku();
		}
		
		// check whether logged in user has access to the above found dept, agency & tku
		return securityService.checkModuleAccess(getLoggedInUser(), getModuleIdFromSession(), 
																	scopedDept, scopedAgency, scopedTku);
	}

	public boolean validateUniqueAppointmentForExpenseDates(){
		List<AppointmentsBean> appts = appointmentService.findAppointmentHistory(getUserSubject().getEmployeeId(), expenseMaster.getExpDateTo());
		boolean trueMultiple = isTrueMultiple(appts);
		
		// if no appt info found for the given date, raise error
		if (appts == null || appts.isEmpty()) {
			addFieldError("errors",
					"No appointment exists for the expense dates");
			return false;
		} else if (appts.size() > 1) {
			if (getModuleIdFromSession().equals(IConstants.EXPENSE_EMPLOYEE) || !trueMultiple) {
				addFieldError("errors",
						"Multiple active appointments exist for the entered expense dates");
				return false;
			}
		}

		AppointmentsBean appt = appts.get(0);
		
		// update correct info in UserSubject
		if (((expenseMaster.getRevisionNumber() == 0)
				|| (expenseService.getPrevExpenseMasterInProcStatus(
						expenseMaster.getExpevIdentifier(), expenseMaster
								.getRevisionNumber()) == null)) && !trueMultiple) {
			updateUserSubjectWithCorrectAppointment(appt);
		}
		//setupApptDates(appt.getAppt_identifier());
		
		if(!expenseMaster.getExpDateFrom().before(getUserSubject().getAppointmentDate()) 
				&& !expenseMaster.getExpDateTo().after(getUserSubject().getAppointmentEnd())){
			// Check for leave of inactive scenarios before final validation
			if (!(appointmentService.isEmployeeActive(getUserSubject().getEmployeeId())) && inactiveStatusForExpenseToDate == null)
				getInactiveStatusForExpenseToDate ();
				if (inactiveStatusForExpenseToDate != null && !inactiveStatusForExpenseToDate){
					addFieldError("errors", "No appointment exists for the expense dates");
					return false;
				}
			return true;
		}else{
			StringBuilder buff = new StringBuilder("Multiple appointments found. Expense dates must be within an appointment. ");
			
			// add date mismatch info to error
			if(expenseMaster.getExpDateFrom().before(getUserSubject().getAppointmentDate())){
				buff.append("Appointment starts on "+df.format(getUserSubject().getAppointmentDate()));
			}else if(expenseMaster.getExpDateTo().after(getUserSubject().getAppointmentEnd())){
				buff.append("Appointment ends on "+df.format(getUserSubject().getAppointmentEnd()));
			}
			
			addFieldError("errors", buff.toString());
			return false;
		}
	}
	
	private void setupApptDates(long apptId) {
		AppointmentsBean apptBean = appointmentService.findActiveAppointmentDateSpan(apptId);
		
		if(apptBean.getDepartureDate() != null) getUserSubject().setAppointmentEnd(apptBean.getDepartureDate());
		else if(apptBean.getEnd_date() != null) getUserSubject().setAppointmentEnd(apptBean.getEnd_date());
		
		if(apptBean.getAppointment_date() != null) getUserSubject().setAppointmentDate(apptBean.getAppointment_date());
	}

	private void updateUserSubjectWithCorrectAppointment(AppointmentsBean appt) {
		UserSubject subject = getUserSubject();
		subject.setAppointmentId((int)appt.getAppt_identifier());
		subject.setAppointmentDate(appt.getAppointment_date());
		subject.setAppointmentStart(appt.getAppointment_date());
		subject.setAppointmentEnd(appt.getDepartureOrEndDate());
		subject.setPositionId(appt.getPosition_id());
		subject.setDepartment(appt.getDepartment());
		subject.setAgency(appt.getAgency());
		subject.setTku(appt.getTku());
		subject.setSingleAppointmentChosen(false);
	}
	
	private boolean isTrueMultiple (List<AppointmentsBean> appts){
		boolean retValue = false;
		for (AppointmentsBean appt: appts){
			if (appt.getPosition_level() > 1){
				retValue = true;
				break;
		}
		}
		return retValue;
	}
	
	public ExpenseMasters getExpenseMaster() {
		return expenseMaster;
	}

	public void setExpenseMaster(ExpenseMasters expenseMaster) {
		this.expenseMaster = expenseMaster;
	}

	public List<Integer> getSelectedAuthCodes() {
		return selectedAuthCodes;
	}

	public void setSelectedAuthCodes(List<Integer> selectedAuthCodes) {
		this.selectedAuthCodes = selectedAuthCodes;
	}

}
