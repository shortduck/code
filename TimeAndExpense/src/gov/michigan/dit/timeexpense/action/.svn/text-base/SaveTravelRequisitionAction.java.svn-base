package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.exception.TimeAndExpenseException;
import gov.michigan.dit.timeexpense.model.core.AppointmentHistory;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetailCodingBlock;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetails;
import gov.michigan.dit.timeexpense.model.core.TravelReqErrors;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.TravelReqOutOfState;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.model.display.AppointmentsBean;
import gov.michigan.dit.timeexpense.model.display.DisplayAdvance;
import gov.michigan.dit.timeexpense.model.display.TimeAndExpenseError;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.TravelRequisitionDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;
import gov.michigan.dit.timeexpense.util.TravelRequisitionViewUtil;

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
 * Action responsible for saving an expense. The expense could be either a new
 * expense or an updated existing expense.
 * 
 * Validations are performed to ascertain the required input data required for
 * an expense. If any of the mandatory input fields is missing, the expense is
 * not saved and a validation message is returned.
 * 
 * As an expense can be saved with warnings and other permissible business error
 * conditions, the save operation is performed successfully in such a case. The
 * user is notified of any such warning conditions, though.
 * 
 */
public class SaveTravelRequisitionAction extends AbstractAction {

	private static final long serialVersionUID = 42322243823439L;
	private static Logger logger = Logger.getLogger(SaveTravelRequisitionAction.class);

	protected TravelReqMasters treqMaster;
	protected TravelReqDetails treqDetails;
	private List<Integer> selectedAuthCodes;

	private TravelRequisitionDSP treqService;
	private AppointmentDSP appointmentService;
	private gov.michigan.dit.timeexpense.service.SecurityManager securityService;
	private CommonDSP commonService;
	private CodingBlockDSP codingBlockService;
	// Used to track a prior inactive status for an active employee
	Boolean inactiveStatusForExpenseToDate;

	private DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);

	public SaveTravelRequisitionAction() {
		selectedAuthCodes = new ArrayList<Integer>();
	}

	/**
	 * @see BaseAction#prepare()
	 */
	public void prepare() {
		treqService = new TravelRequisitionDSP(entityManager);
		appointmentService = new AppointmentDSP(entityManager);
		securityService = new gov.michigan.dit.timeexpense.service.SecurityManager(entityManager);
		commonService = new CommonDSP(entityManager);
		codingBlockService = new CodingBlockDSP(entityManager);
	}

	@Override
	public String execute() throws Exception {
		TravelReqMasters originalTravelRequisitionBackup = null;
		TravelReqMasters savedTravelRequisition = null;
		try {
			// refresh
			TravelReqMasters treqm = (TravelReqMasters) session.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);
			if (treqm != null) {
				treqm = entityManager.merge(treqm);
				originalTravelRequisitionBackup = prepareTravelRequisitionOnlyData(treqm);
				session.put(IConstants.TRAVEL_REQUISITION_SESSION_DATA, treqm);

				// first delete all existing errors related to expense ID
				commonService.deleteTravelReqErrors(treqm, IConstants.EXP_ERR_SRC_ID_TAB);

				// delete cross tab errors relating to TripId tab
				// deleteCrossTabExpenseErrorsForTripId(treqm);

				// flush expense error changes
				entityManager.flush();
			}

			// merge new expense data into Expense object stored in session, if
			// applicable
			TravelReqMasters treqMasterInSession = mergeWithTravelRequisitionInSession();

			UserProfile userProfile = getLoggedInUser();

			// Get appointment information from UserSubject in session
			UserSubject userSubject = getUserSubject();

			if (userSubject == null || userSubject.getAppointmentId() == 0) {
				String errorMsg = "User subject information not found in session";
				logger.error(errorMsg);
				throw new TimeAndExpenseException(errorMsg);
			}

			entityManager.flush();
			if (treqMasterInSession.getRequestAdvance() != null
					&& treqMasterInSession.getRequestAdvance().equals("true")) {
				treqMasterInSession.setRequestAdvance("Y");
			}
			savedTravelRequisition = treqService.saveTravelRequisition(treqMasterInSession, treqDetails,
					userSubject.getAppointmentId(), getModuleIdFromSession(), getLoggedInUser());

			entityManager.flush();

			// TODO[mc]: If a new revision has been created from the current
			// expense, the new revision would contain
			// the updated new information. But the same information also got
			// saved to the current expense, while
			// getting expense data from view. So, undo the changes to the
			// current expense, if new revision created.
			
			  if(savedTravelRequisition.getRevisionNumber() >
			  treqMasterInSession.getRevisionNumber()){
			  updateCurrentExpenseWithOldInfoIfNewRevisionCreated
			  (treqMasterInSession, originalTravelRequisitionBackup); }
			 

			// write all the changes explicitly, to generate Ids
			// persistExpenseMasterState(savedTravelRequisition);

			// write all the changes explicitly, to generate Ids
			entityManager.flush();

			// push saved expense into session
			session.put(IConstants.TRAVEL_REQUISITION_SESSION_DATA, savedTravelRequisition);

			StringBuilder buff = new StringBuilder();
			buff.append("{treqEventId:'");
			buff.append(savedTravelRequisition.getTreqeIdentifier().getTreqeIdentifier());
			buff.append("',revisionNo:");
			buff.append(savedTravelRequisition.getRevisionNumber());
			buff.append("}");

			setJsonResponse(buff.toString());

			// propagate expense errors
			TravelRequisitionViewUtil util = new TravelRequisitionViewUtil();
			util.setJsonParser(jsonParser);
			util.setAppointmentService(appointmentService);
			util.setCodingBlockService(codingBlockService);
			addTimeAndExpenseErrors(util.prepareTimeAndExpenseErrors(savedTravelRequisition));
		} catch (OptimisticLockException e) {
			addFieldError(
					"errors",
					"The record you are attempting to change has already been modified by another user.  Please reopen the record before making any changes.");
		}
		return IConstants.SUCCESS;
	}

	/*
	 * private void deleteCrossTabTravelErrorsForTripId(TravelReqMasters
	 * expense) { List<String> expenseRelatedCrossTabErrorCodes = new
	 * ArrayList<String>(); expenseRelatedCrossTabErrorCodes.add(IConstants.
	 * EXPENSE_DETAILS_DATE_NOT_BETWEEN_EXPENSE_ID_DATE);
	 * 
	 * commonService.deleteExpenseErrors(expense,
	 * expenseRelatedCrossTabErrorCodes); }
	 */

	/**
	 * Explicitly saves all the collection entries in the expense and then
	 * flushes the changes to the DB. PS: This is just a workaround for the
	 * OpenJPA strange behavior.
	 */
	private void persistExpenseMasterState(TravelReqMasters master) {
		if (master.getTreqErrorsCollection() != null) {
			// persist errors
			for (TravelReqErrors error : master.getTreqErrorsCollection()) {
				if (error.getTreqerIdentifier() == null)
					entityManager.persist(error);
				else
					error = entityManager.merge(error);
				entityManager.flush();
			}
		}

		if (master.getTravelReqOutOfStateCollection() != null) {
			// persist oost
			for (TravelReqOutOfState oost : master.getTravelReqOutOfStateCollection()) {
				if (oost.getTreqOostIdentifier() == null)
					entityManager.persist(oost);
				else
					oost = entityManager.merge(oost);
				entityManager.flush();
			}
		}

		// entityManager.flush();
	}

	private TravelReqMasters prepareTravelRequisitionOnlyData(TravelReqMasters treqm) {
		TravelReqMasters master = new TravelReqMasters(treqm);
		master.setTreqmIdentifier(treqm.getTreqmIdentifier());
		master.setTreqeIdentifier(null);
		master.setTravelReqDetailsCollection(null);
		master.setTreqErrorsCollection(null);

		return master;
	}

	private void updateCurrentExpenseWithOldInfoIfNewRevisionCreated(TravelReqMasters treq, TravelReqMasters oldBackup) {
		// only undo data that could have been send by the view.
		treq.setTreqDateFrom(oldBackup.getTreqDateFrom());
		treq.setTreqDateTo(oldBackup.getTreqDateTo());
		treq.setNatureOfBusiness(oldBackup.getNatureOfBusiness());
		treq.setOutOfStateInd(oldBackup.getOutOfStateInd());
		treq.setOfficeProgram(oldBackup.getOfficeProgram());
		treq.setDestination(oldBackup.getDestination());
		treq.setRequestAdvance(oldBackup.getRequestAdvance());
		treq.setComments(oldBackup.getComments());
		treq.setTransportationVia(oldBackup.getTransportationVia());
		treq.setOfficeProgram(oldBackup.getOfficeProgram());
		treq.setDestination(oldBackup.getDestination());

		// If user chooses same Out Of State travel code after modification,
		// we need to explicitly delete those carried forwarded ones as we'll be
		// adding fresh ones below.
		List<TravelReqOutOfState> oostCodes = treq.getTravelReqOutOfStateCollection();
		for (TravelReqOutOfState oost : oostCodes) {
			// explicitly remove carry forwarded out of state travel codes
			if (oost.getTreqOostIdentifier() != null)
				entityManager.remove(oost);
		}
		// remove all existing codes.
		oostCodes.clear();
		entityManager.flush();

		for (TravelReqOutOfState oostDB : oldBackup.getTravelReqOutOfStateCollection()) {
			TravelReqOutOfState oost = new TravelReqOutOfState(oostDB);
			oostCodes.add(oost);
			oost.setTreqmIdentifier(treq);
			entityManager.persist(oost);
		}
	}

	private TravelReqMasters mergeWithTravelRequisitionInSession() {

		TravelReqMasters treqMasterInSession = (TravelReqMasters) session
				.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);

		treqMasterInSession = (treqMasterInSession == null) ? treqMaster : treqMasterInSession;

		treqMasterInSession.setTreqDateFrom(treqMaster.getTreqDateFrom());
		treqMasterInSession.setTreqDateTo(treqMaster.getTreqDateTo());
		treqMasterInSession.setNatureOfBusiness(treqMaster.getNatureOfBusiness());
		treqMasterInSession.setOutOfStateInd(treqMaster.getOutOfStateInd());
		treqMasterInSession.setOfficeProgram(treqMaster.getOfficeProgram());
		treqMasterInSession.setDestination(treqMaster.getDestination());
		treqMasterInSession.setRequestAdvance(treqMaster.getRequestAdvance());
		treqMasterInSession.setTransportationVia(treqMaster.getTransportationVia());
		treqMasterInSession.setComments(treqMaster.getComments());
		treqMasterInSession.setOfficeProgram(treqMaster.getOfficeProgram());
		treqMasterInSession.setDestination(treqMaster.getDestination());

		boolean outOfStateChecked = treqMaster.getOutOfStateInd() != null && "Y".equals(treqMaster.getOutOfStateInd()) ? true
				: false;

		if (!outOfStateChecked || selectedAuthCodes == null || selectedAuthCodes.size() == 0) {
			if (treqMasterInSession.getTravelReqOutOfStateCollection() != null) {
				for (TravelReqOutOfState oost : treqMasterInSession.getTravelReqOutOfStateCollection()) {
					entityManager.remove(oost);
					entityManager.flush();
				}
				treqMasterInSession.getTravelReqOutOfStateCollection().clear();
			}
		} else {
			if (treqMasterInSession.getTravelReqOutOfStateCollection() != null) {

				Map<Integer, Object> prevOOSTIdMap = new HashMap<Integer, Object>(treqMasterInSession
						.getTravelReqOutOfStateCollection().size() + 1, 1);

				for (TravelReqOutOfState oost : treqMasterInSession.getTravelReqOutOfStateCollection()) {
					prevOOSTIdMap.put(new Integer(oost.getStacIdentifier().intValue()), "");
				}

				// remove from original collection the ones that have been
				// unselected
				Iterator<TravelReqOutOfState> itr = treqMasterInSession.getTravelReqOutOfStateCollection().iterator();
				while (itr.hasNext()) {
					TravelReqOutOfState oost = itr.next();

					boolean existsAlready = false;
					for (Integer stacCode : selectedAuthCodes) {
						if (oost.getStacIdentifier().equals(stacCode)) {
							existsAlready = true;
							break;
						}
					}

					if (!existsAlready)
						itr.remove();
				}

				// add to collection the new items selected
				for (Integer stacCode : selectedAuthCodes) {
					if (!prevOOSTIdMap.containsKey(stacCode)) {
						TravelReqOutOfState newOutOfStateTravel = new TravelReqOutOfState();
						newOutOfStateTravel.setTreqmIdentifier(treqMasterInSession);
						newOutOfStateTravel.setStacIdentifier(stacCode);

						treqMasterInSession.getTravelReqOutOfStateCollection().add(newOutOfStateTravel);
					}
				}
			} else {
				List<TravelReqOutOfState> newOutofStateTravelList = new ArrayList<TravelReqOutOfState>();

				for (Integer stacCode : selectedAuthCodes) {
					TravelReqOutOfState newOutOfStateTravel = new TravelReqOutOfState();
					newOutOfStateTravel.setTreqmIdentifier(treqMasterInSession);
					newOutOfStateTravel.setStacIdentifier(stacCode);

					newOutofStateTravelList.add(newOutOfStateTravel);
				}

				treqMasterInSession.setTravelReqOutOfStateCollection(newOutofStateTravelList);
			}
		}

		return treqMasterInSession;
	}

	@Override
	public void validate() {
		if (treqMaster == null)
			return;

		if (treqMaster.getTreqDateFrom() == null)
			addFieldError("errors", "From date missing");
		if (treqMaster.getTreqDateTo() == null)
			addFieldError("errors", "To date missing");
		if (treqMaster.getTreqDateFrom() != null && treqMaster.getTreqDateTo() != null
				&& treqMaster.getTreqDateFrom().after(treqMaster.getTreqDateTo()))
			addFieldError("errors", "Expense TO date cannot be greater than expense FROM date");
		if (treqMaster.getNatureOfBusiness() == null || treqMaster.getNatureOfBusiness().trim().length() < 1)
			addFieldError("errors", "Nature of official business missing");
		if (treqMaster.getOutOfStateInd() != null && "Y".equals(treqMaster.getOutOfStateInd())
				&& selectedAuthCodes.size() < 1)
			addFieldError("errors", "Authorization code(s) required for out of state travel");
		if(treqMaster.getComments() != null && treqMaster.getComments().trim().length() > 255)
			addFieldError("errors", "Comments longer than allowed (255 characters). Please enter a shorter value.");
		
		//Validating travel req Request. Should be less than the system defined date.
		
		Date travelRequestDate = null;
		String strTravelRequestDate = commonService
				.getSystemCodeValue(IConstants.TRAVEL_REQ_END_DATE_SYSTEM_CODE);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try {
			travelRequestDate = sdf.parse(strTravelRequestDate);
			if (treqMaster.getTreqDateRequest() != null
					&& treqMaster.getTreqDateRequest().after(travelRequestDate)){
				
				String strErrorMessage = commonService.getErrorCode(IConstants.TREQ_FROM_DATE_AFTER_SYSTEM_PERMISSION_CLIENT_MESSAGE).getErrorText();
				//Replacing date place holder with date from DB. 
				strErrorMessage = strErrorMessage.replace("{0}", strTravelRequestDate);
				
				addFieldError("errors",strErrorMessage);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		String moduleId = getModuleIdFromSession();
		if (IConstants.TRAVEL_REQUISITIONS_MANAGER.equals(moduleId) || IConstants.TRAVEL_REQUISITIONS_STATEWIDE.equals(moduleId)) {
			// Check if subject has a valid active appointment i.e. not departed
			// or infinite leave etc.
			// for the expense TO date
			boolean result = validateSubjectActiveAppointment(moduleId);
			// If a problem already found and error for the same raised, then
			// just return.
			if (!result)
				return;

			// For manager & state wide access employee, check whether right
			// security exists to create
			// the expense on behalf of the user subject i.e. check if the
			// logged in user has access to the
			// Subject's dept, agency and tku for the entered expenseTo date.
			result = validateSupervisorSecurityScopeAccessOnExpenseToDate(moduleId);
			// If a problem already found and error for the same raised, then
			// just return.
			if (!result)
				return;
		}
		// employee creating expense for himself
		else if (IConstants.TRAVEL_REQUISITIONS_EMPLOYEE.equals(moduleId)) {
			UserSubject subject = getUserSubject();

			// 1) ASSERT SINGLE APPT
			// Check if multiple appointments exist for the provided expense
			// dates.
			// PS: This is not required if the user has explicitly selected the
			// appt.
			if (!subject.isSingleAppointmentChosen()) {
				boolean result = validateUniqueAppointmentForExpenseDates();
				// If a problem already found and error for the same raised,
				// then just return.
				if (!result)
					return;
			}

			// 2) ASSERT SECURITY FOR CREATING/MODIFYING EXPENSES FOR "THAT"
			// APPT
			// By now a single appointment for the employee would have been
			// decided. Let's check if
			// he has security to create expenses for this appointment.
			if (treqMaster.getRevisionNumber() == 0) {
				boolean userSecurityToCreateExpenseForSelf = securityService.checkModuleAccess(getLoggedInUser(),
						moduleId, subject.getDepartment(), subject.getAgency(), subject.getTku());
				if (!userSecurityToCreateExpenseForSelf) {
					addFieldError("errors", "You are not allowed to create requisions for these dates.");
					return;
				}
			}
		}
	}

	/*
	 * Verify the expense dates are completely within the Subject's appt dates
	 */
	private boolean validateSubjectActiveAppointment(String moduleId) {
		UserSubject subject = getUserSubject();
		boolean result = false;

		if (!treqMaster.getTreqDateFrom().before(subject.getAppointmentDate())
				&& !treqMaster.getTreqDateTo().after(subject.getAppointmentEnd())) {
			result = true;
		} else {
			// Expense dates not within the active date. Check for a prior
			// inactive status
			getInactiveStatusForExpenseToDate();
			if (inactiveStatusForExpenseToDate)
				result = true;
			else {
				StringBuilder buff = new StringBuilder("No unique active appointment for the chosen expense dates. ");

				// add date mismatch info to error
				if (treqMaster.getTreqDateFrom().before(subject.getAppointmentDate())) {
					buff.append("Appointment starts on " + df.format(subject.getAppointmentDate()));
				} else if (treqMaster.getTreqDateTo().after(subject.getAppointmentEnd())) {
					buff.append("Appointment ends on " + df.format(subject.getAppointmentEnd()));
				}

				addFieldError("errors", buff.toString());
				result = false;
			}
		}
		return result;
	}

	private boolean validateSupervisorSecurityScopeAccessOnExpenseToDate(String moduleId) {
		boolean supervisorAccessExists = false;
		if (!supervisorAccessExists()) {
			// No access for expense dates. Check access for a prior inactive
			// status
			if (inactiveStatusForExpenseToDate == null)
				getInactiveStatusForExpenseToDate();
			if (inactiveStatusForExpenseToDate != null && inactiveStatusForExpenseToDate)
				supervisorAccessExists = true;
		} else
			supervisorAccessExists = true;

		// raise error if no access!
		if (!supervisorAccessExists) {
			addFieldError("errors", "User does not have system access for the chosen expense dates.");
			return false;
		}

		return true;
	}

	/**
	 * Checks for a prior inactive status for an active employee
	 */

	private void getInactiveStatusForExpenseToDate() {
		UserSubject subject = getUserSubject();
		if (appointmentService.isEmployeeActive(subject.getEmployeeId())) {
			inactiveStatusForExpenseToDate = appointmentService.getInactiveStatusForExpenseToDate(
					subject.getAppointmentId(), treqMaster.getTreqDateTo());
		}

	}

	private boolean supervisorAccessExists() {
		Date expToDate = treqMaster.getTreqDateTo();
		UserSubject subject = getUserSubject();

		String scopedDept = "";
		String scopedAgency = "";
		String scopedTku = "";

		// if exp TO date not within selected appointment, find the dept,
		// agency, tku on exp TO date
		if (expToDate.before(subject.getAppointmentStart()) || expToDate.after(subject.getAppointmentEnd())) {

			// find the appointment, for the given exp TO date
			AppointmentHistory apptHistory = appointmentService.getAppointmentHistory(subject.getAppointmentId(),
					treqMaster.getTreqDateTo());

			if (apptHistory != null) {
				scopedDept = apptHistory.getDepartment();
				scopedAgency = apptHistory.getAgency();
				scopedTku = apptHistory.getTku();
			}
		} else {
			scopedDept = subject.getDepartment();
			scopedAgency = subject.getAgency();
			scopedTku = subject.getTku();
		}

		// check whether logged in user has access to the above found dept,
		// agency & tku
		return securityService.checkModuleAccess(getLoggedInUser(), getModuleIdFromSession(), scopedDept, scopedAgency,
				scopedTku);
	}

	public boolean validateUniqueAppointmentForExpenseDates() {
		List<AppointmentsBean> appts = appointmentService.findAppointmentHistory(getUserSubject().getEmployeeId(),
				treqMaster.getTreqDateTo());

		// if no appt info found for the given date, raise error
		if (appts == null || appts.isEmpty()) {
			addFieldError("errors", "No appointment exists for the expense dates");
			return false;
		} else if (appts.size() > 1) {
			addFieldError("errors", "Multiple active appointments exist for the entered expense dates");
			return false;
		}

		AppointmentsBean appt = appts.get(0);

		// update correct info in UserSubject
		updateUserSubjectWithCorrectAppointment(appt);
		// setupApptDates(appt.getAppt_identifier());

		if (!treqMaster.getTreqDateFrom().before(getUserSubject().getAppointmentDate())
				&& !treqMaster.getTreqDateTo().after(getUserSubject().getAppointmentEnd())) {
			// Check for leave of inactive scenarios before final validation
			if (!(appointmentService.isEmployeeActive(getUserSubject().getEmployeeId()))
					&& inactiveStatusForExpenseToDate == null)
				getInactiveStatusForExpenseToDate();
			if (inactiveStatusForExpenseToDate != null && !inactiveStatusForExpenseToDate) {
				addFieldError("errors", "No appointment exists for the expense dates");
				return false;
			}
			return true;
		} else {
			StringBuilder buff = new StringBuilder(
					"Multiple appointments found. Expense dates must be within an appointment. ");

			// add date mismatch info to error
			if (treqMaster.getTreqDateFrom().before(getUserSubject().getAppointmentDate())) {
				buff.append("Appointment starts on " + df.format(getUserSubject().getAppointmentDate()));
			} else if (treqMaster.getTreqDateTo().after(getUserSubject().getAppointmentEnd())) {
				buff.append("Appointment ends on " + df.format(getUserSubject().getAppointmentEnd()));
			}

			addFieldError("errors", buff.toString());
			return false;
		}
	}

	private void setupApptDates(long apptId) {
		AppointmentsBean apptBean = appointmentService.findActiveAppointmentDateSpan(apptId);

		if (apptBean.getDepartureDate() != null)
			getUserSubject().setAppointmentEnd(apptBean.getDepartureDate());
		else if (apptBean.getEnd_date() != null)
			getUserSubject().setAppointmentEnd(apptBean.getEnd_date());

		if (apptBean.getAppointment_date() != null)
			getUserSubject().setAppointmentDate(apptBean.getAppointment_date());
	}

	private void updateUserSubjectWithCorrectAppointment(AppointmentsBean appt) {
		UserSubject subject = getUserSubject();
		subject.setAppointmentId((int) appt.getAppt_identifier());
		subject.setAppointmentDate(appt.getAppointment_date());
		subject.setAppointmentStart(appt.getAppointment_date());
		subject.setAppointmentEnd(appt.getDepartureOrEndDate());
		subject.setPositionId(appt.getPosition_id());
		subject.setDepartment(appt.getDepartment());
		subject.setAgency(appt.getAgency());
		subject.setTku(appt.getTku());
		subject.setSingleAppointmentChosen(false);
	}

	@SuppressWarnings("unchecked")
	public String submitTravelRequisition() throws TimeAndExpenseException {

		String result = IConstants.SUCCESS;
		String approverComments = "";
		Map<String, String[]> paramMap = super.getRequest().getParameterMap();
		if (paramMap.containsKey("approverComments"))
			approverComments = paramMap.get("approverComments")[0];

		TravelReqMasters treqMmaster = (TravelReqMasters) session.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);
		if (treqMmaster == null) {
			// in case an advance was not saved in session beforehand due
			// validation errors
			addFieldError("errors", "Please Save requisition successfully prior to submission");
			return "failure";
		}
		treqMmaster = entityManager.merge(treqMmaster);

		try {
			/*
			 * if (canSubmitAdvanceWithErrors(advanceMaster, super
			 * .getUserSubject())) {
			 */
			treqMaster = treqService.submitTravelRequisition(treqMmaster, super.getUserSubject(),
					super.getLoggedInUser(), approverComments);
			//cn bug 147
			entityManager.flush();
            entityManager.refresh(treqMaster);
            //CN BUG 147
			treqService.emailNotification(treqMmaster, super.getUserSubject(), super.getLoggedInUser(), approverComments);
			if (treqMaster.getRequestAdvance() != null && treqMaster.getRequestAdvance().equals("Y")
					&& treqMaster.getTreqeIdentifier().getAdevIdentifier() == null) {
				/*
				 * List advSessionData = new ArrayList();
				 * advSessionData.add(treqMaster.getTreqDateRequest());
				 * advSessionData
				 * .add(treqMaster.getTravelReqDetailsCollection().
				 * get(0).getDollarAmount());
				 * advSessionData.add(treqMaster.getTreqDateFrom());
				 * advSessionData.add(treqMaster.getTreqDateTo());
				 * advSessionData.add(treqMaster.getNatureOfBusiness());
				 * session.put("ADVANCE_FROM_REQUISITION", advSessionData);
				 */
				DisplayAdvance adv = new DisplayAdvance();
				double airfareAmount=treqMaster.getTravelReqDetailsCollection().get(0).getAirfareAmount();
				adv.setRequestDate(TimeAndExpenseUtil.displayDate(treqMaster.getTreqDateRequest()));
				//Subract airfare amount from the total amount
				adv.setDollarAmount((treqMaster.getTravelReqDetailsCollection().get(0).getDollarAmount())- airfareAmount);
				adv.setDollarAmountForEditing(TimeAndExpenseUtil.displayAmountTwoDigits(treqMaster
						.getTravelReqDetailsCollection().get(0).getDollarAmount() -  airfareAmount));
				adv.setFromDate(TimeAndExpenseUtil.displayDate(treqMaster.getTreqDateFrom()));
				adv.setToDate(TimeAndExpenseUtil.displayDate(treqMaster.getTreqDateTo()));
				adv.setAdvanceReason(treqMaster.getNatureOfBusiness());
				adv.setRelatedTravelRequisition(treqMaster.getTreqeIdentifier());
				session.put("ADVANCE_FROM_REQUISITION", adv);
			}

			session.put(IConstants.TRAVEL_REQUISITION_SESSION_DATA, treqMmaster);
			String jsonResponse = "{status:'" + treqMmaster.getStatus() + "'" + ", revision:"
					+ treqMmaster.getRevisionNumber() + "}";
			super.setJsonResponse(jsonResponse);
		} catch (Exception e) {
			setJsonResponse("{msg:'Submission Failed - Please check grid below for errors', submitSuccess : false}");
		}
		// add any business errors
		TravelRequisitionViewUtil util = new TravelRequisitionViewUtil();
		util.setJsonParser(jsonParser);
		util.setAppointmentService(appointmentService);
		util.setCodingBlockService(codingBlockService);
		addTimeAndExpenseErrors(util.prepareTimeAndExpenseErrors(treqMmaster));
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public String approveTravelRequisition() throws TimeAndExpenseException {

		String result = IConstants.SUCCESS;
		UserSubject subject = super.getUserSubject();
		UserProfile profile = super.getLoggedInUser();
		String approverComments = "";

		treqMaster = (TravelReqMasters) session.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);

		// AI 19898 kp
		String jsonResponse = "";

		if (!appointmentService.isEmployeeActive(getUserSubject().getEmployeeId())) {
			jsonResponse = "{status:'" + "EMPLOYEE_STATUS_INVALID'" + "}";
			super.setJsonResponse(jsonResponse);
			return IConstants.FAILURE;
		} // kp

		/*
		 * advanceMaster = (AdvanceMasters) session
		 * .get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER); advanceMaster =
		 * entityManager.merge(advanceMaster);
		 * entityManager.refresh(advanceMaster);
		 */
		// check user security for next action code
		String nextActionCode = treqService.getLatestAction(treqMaster).get(0).getNextActionCode();
		String moduleAction = commonService.getRefCode(nextActionCode);
		if (this.checkSecurity(moduleAction) < IConstants.SECURITY_UPDATE_MODULE_ACCESS) {
			// user does not have approval access for next action code
			throw new TimeAndExpenseException("Approval security failure");
		}

		Map<String, String[]> paramMap = super.getRequest().getParameterMap();
		if (paramMap.containsKey("approverComments"))
			approverComments = paramMap.get("approverComments")[0];
		/*
		 * treqMasterInSession = (AdvanceMasters) session
		 * .get(IConstants.ADVANCE_CURRENT_ADVANCE_MASTER); advanceMaster =
		 * entityManager.merge(advanceMaster);
		 * entityManager.refresh(advanceMaster);
		 */
		try {
			treqMaster = treqService.approveTravelRequisition(treqMaster, subject, profile, approverComments);
	         //CN BUG 147 	if (treqMaster.getStatus().equals(IConstants.APPROVED)) {
		   		entityManager.flush();
	            entityManager.refresh(treqMaster);
	    		treqService.emailNotification(treqMaster, subject, profile, approverComments);
	    	 //CN BUG 147 
	    		
			// propagate expense errors
			TravelRequisitionViewUtil util = new TravelRequisitionViewUtil();
			util.setJsonParser(jsonParser);
			util.setAppointmentService(appointmentService);
			util.setCodingBlockService(codingBlockService);
			addTimeAndExpenseErrors(util.prepareTimeAndExpenseErrors(treqMaster));

			session.put(IConstants.TRAVEL_REQUISITION_SESSION_DATA, treqMaster);
			jsonResponse = "{status:'" + treqMaster.getStatus()
					+ "', expenseEventId:'"
					+ treqMaster.getTreqeIdentifier().getExpevIdentifier()
					+ "', moduleId:'"
					+ getModuleIdFromSession()
					+ "'}";
			super.setJsonResponse(jsonResponse);
		} catch (Exception e) {
			logger.error("Exception occured while trying to approve advance", e);
			throw new TimeAndExpenseException(e.getMessage());
		}

		return result;
	}

	private int checkSecurity(String moduleId) {
		if ("".equals(moduleId))
			moduleId = super.getModuleIdFromSession();
		UserProfile profile = super.getLoggedInUser();
		return securityService.getModuleAccessMode(profile, moduleId, super.getUserSubject().getDepartment(), super
				.getUserSubject().getAgency(), super.getUserSubject().getTku());
	}

	private void addErrors(TravelReqMasters treqMaster) {
		if (treqMaster.getTreqErrorsCollection() != null) {
			if (!treqMaster.getTreqErrorsCollection().isEmpty()) {
				for (TravelReqErrors treqError : treqMaster.getTreqErrorsCollection()) {
					String code = treqError.getErrorCode().getErrorCode();
					ErrorMessages error = commonService.getErrorCode(code);
					String description = error.getErrorText();
					String severity = error.getSeverity();
					Long errorIdentifier = 0L;
					if (treqError.getTreqerIdentifier() != null)
						errorIdentifier = treqError.getTreqerIdentifier().longValue();
					else
						errorIdentifier = System.nanoTime();
					TimeAndExpenseError displayError = new TimeAndExpenseError(errorIdentifier.intValue(), code,
							description, severity, treqError.getErrorSource());
					super.addTimeAndExpenseError(displayError);
				}
			}
		}

	}

	public String rejectTravelRequisition() throws TimeAndExpenseException {

		String result = IConstants.SUCCESS;
		UserSubject subject = super.getUserSubject();
		UserProfile profile = super.getLoggedInUser();
		// String approverComments =
		// super.getRequest().getParameter("approverComments");
		Map<String, String[]> paramMap = super.getRequest().getParameterMap();
		if (paramMap == null)
			paramMap = super.getRequest().getParameterMap();
		String approverComments = paramMap.get("approverComments")[0];
		TravelReqMasters treqMaster = (TravelReqMasters) session.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);
		treqMaster = entityManager.merge(treqMaster);
		try {
			treqMaster = treqService.rejectTravelRequisition(treqMaster, subject, profile, approverComments);
			treqService.emailNotification(treqMaster, subject, profile, approverComments);

			entityManager.flush();

			this.addErrors(treqMaster);
			session.put(IConstants.TRAVEL_REQUISITION_SESSION_DATA, treqMaster);
			// check to see if Modify button can be enabled after a rejection
			boolean allowModification = canModify();
			String jsonResponse = "{status:'" + treqMaster.getStatus() + "', " + "allowModification:'"
					+ allowModification + "'}";
			super.setJsonResponse(jsonResponse);
		} catch (Exception e) {
			logger.error("Exception occured while trying to reject advance", e);
			throw new TimeAndExpenseException(e.getMessage());
		}

		return result;
	}

	private boolean canModify() {
		boolean canModify = true;
		if (this.checkSecurity(getModuleIdFromSession()) < IConstants.SECURITY_UPDATE_MODULE_ACCESS) {
			canModify = false;
		}
		return canModify;
	}

	/**
	 * Creates a new expense request associated with the travel requisition
	 * 
	 * @return
	 */
	public String createExpense() {
		String result = IConstants.SUCCESS;
		UserSubject subject = super.getUserSubject();
		UserProfile profile = super.getLoggedInUser();
		treqMaster = (TravelReqMasters) session.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);
		treqMaster = entityManager.merge(treqMaster);
		// Get user appointments
		List<AppointmentsBean> appts = appointmentService.findAppointmentHistory(getUserSubject().getEmployeeId(), treqMaster.getTreqDateTo());
		if ((appts.size() == 0) || (appts.size() > 1) ||
				appts.get(0).getAppt_identifier() != treqMaster.getTreqeIdentifier().getApptIdentifier()){
			AppointmentHistory apptHistory = appointmentService.getAppointmentHistory(treqMaster.getTreqeIdentifier().getApptIdentifier());
			Date apptEndDate = apptHistory.getDepartureOrEndDate();
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String jsonResponse = "{status:'" + "No unique active appointment for the chosen expense dates. Appointment ends on " +
			 df.format(apptEndDate) + ". You must create the expense manually." 
			 + "', expenseEventId:'" + 0
			 + "'}";
			
			setJsonResponse(jsonResponse);
			return result;
		} 

		ExpenseMasters expenseMaster = treqService.createExpenseFromRequisition(appts.get(0).getAppt_identifier(), treqMaster, subject, profile);
		session.put(IConstants.CURR_EXPENSEMASTER_FROM_TREQ, expenseMaster);

		String jsonResponse = "{status:'" + treqMaster.getStatus() + "', expenseEventId:'"
				+ expenseMaster.getExpevIdentifier().getExpevIdentifier() + "'}";

		setJsonResponse(jsonResponse);

		session.put(IConstants.TRAVEL_REQUISITION_SESSION_DATA, treqMaster);

		return result;
	}

	public TravelReqMasters getTreqMaster() {
		return treqMaster;
	}

	public void setTreqMaster(TravelReqMasters treqMaster) {
		this.treqMaster = treqMaster;
	}

	public List<Integer> getSelectedAuthCodes() {
		return selectedAuthCodes;
	}

	public void setSelectedAuthCodes(List<Integer> selectedAuthCodes) {
		this.selectedAuthCodes = selectedAuthCodes;
	}

	public TravelReqDetails getTreqDetails() {
		return treqDetails;
	}

	public void setTreqDetails(TravelReqDetails treqDetails) {
		this.treqDetails = treqDetails;
	}
}
