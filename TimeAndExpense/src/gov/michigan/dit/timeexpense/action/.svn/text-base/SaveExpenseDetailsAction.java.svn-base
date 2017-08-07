package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.DriverReimbExpTypeCbs;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetailCodingBlock;
import gov.michigan.dit.timeexpense.model.core.TravelReqEvents;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.ExpenseDetailDisplayBean;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.service.TravelRequisitionDSP;
import gov.michigan.dit.timeexpense.util.ExpenseViewUtil;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.apache.log4j.Logger;

import com.google.gson.reflect.TypeToken;

/**
 * Action responsible for saving expense details. It expects an
 * <code>ExpenseMasters</code> to be present in session to perform
 * the save operation. The entered details are attached to this 
 * existing <code>ExpenseMasters</code> and the same updated in the
 * session to reflect the most recent valid state.
 *  
 * As an expense can be saved with warnings and other permissible
 * business error conditions, the save operation is performed 
 * successfully in such a case. The user is notified of any such
 * warning conditions, though.  
 * 
 * @author chaudharym
 */
public class SaveExpenseDetailsAction extends ExpenseDetailsAction {

	private static final long serialVersionUID = 4026167L;

	private Logger log = Logger.getLogger(SaveExpenseDetailsAction.class);
	
	private String expenseDetailsJson;
	
	private ExpenseLineItemDSP detailsService;
	private TravelRequisitionDSP treqService;
	private List<ExpenseDetailDisplayBean> detailsDisplayList;
	private ExpenseViewUtil viewUtil;
	
	private CommonDSP commonService;
	private CodingBlockDSP codingBlockService;
	private AppointmentDSP appointmentService;
	
	@Override
	public void prepare() {
		commonService = new CommonDSP(entityManager);
		detailsService = new ExpenseLineItemDSP(entityManager);
		codingBlockService = new CodingBlockDSP(entityManager);
		treqService = new TravelRequisitionDSP(entityManager);
		appointmentService = new AppointmentDSP(entityManager);
		viewUtil = new ExpenseViewUtil();
		viewUtil.setJsonParser(jsonParser);
		viewUtil.setCodingBlockService(codingBlockService);
		viewUtil.setAppointmentService(appointmentService);
		
	}
	
	@Override
	public String execute() throws Exception {
		if(log.isDebugEnabled()) log.debug("Validation successful.");
		ExpenseMasters master = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);
		try {

			// refresh master state, to enable child fetch operations
			if(master != null){ 
				master = entityManager.merge(master);
				
				// first delete all existing errors related to expense details
				commonService.deleteExpenseErrors(master, IConstants.EXP_ERR_SRC_DTL_TAB);
				
				// flush expense error changes
				entityManager.flush();
	
				// get changes done by user into expense master's details collection.
				updateExpenseDetails(master, detailsDisplayList);
				UserProfile userProfile = getLoggedInUser();
				//get UserSubject
				UserSubject userSubject = (UserSubject) session.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME);
				
				// validate expense details
				detailsService.validateExpenseLineItem(master, userProfile, userSubject, getModuleIdFromSession());			
				
				for(ExpenseDetails detail : master.getExpenseDetailsCollection()){
					if(log.isDebugEnabled()) log.debug("Detail line "+detail.getLineItem()+" depart time: "+detail.getDepartTime());
					if(log.isDebugEnabled()) log.debug("Detail line "+detail.getLineItem()+" return time: "+detail.getReturnTime());
				}
				
				// update scenario, only check for id in first line. If new lines, 
				//the application of Std Coding Block has been done beforehand
				if (master.getExpenseDetailsCollection() != null &&
						!master.getExpenseDetailsCollection().isEmpty() &&
						 master.getExpenseDetailsCollection().get(0).getExpdIdentifier() != null)				
					codingBlockService.updateExpenseDetailAmounts(master, getLoggedInUser());
				
				// flush the changes explicitly to the db, to get updated Ids
				//[mc] OpenJPA behaves in a weird way by only persisting the first error in the errors
				// collection! All other errors in the error collection are not persisted! Ideally all
				// the errors should have been persisted, upon expense master save operation.
				// Therefore, we persist the errors explicitly below.
				persistExpenseMasterState(master);
	
				// update ExpenseMaster in session
				session.put(IConstants.EXPENSE_SESSION_DATA, master);		
			}
	
			//prepare details json and send back to client
			String detailsJson = "{expenseDetails:{"+viewUtil.prepareDetailsJson(master, entityManager, CommonDSP.isUserSuperUser(getLoggedInUser()) )+"}}";
			
			setJsonResponse(detailsJson);
			addTimeAndExpenseErrors(viewUtil.prepareTimeAndExpenseErrors(master));
		} catch (OptimisticLockException e) {
			addFieldError("errors", "The record you are attempting to change has already been modified by another user.  Please reopen the record before making any changes.");
		}
		return "success";
	}

	/** Explicitly saves all the expense errors and then flushes the changes to the db */
	private void persistExpenseMasterState(ExpenseMasters master) {
		// persist errors
		for(ExpenseErrors error : master.getExpenseErrorsCollection()){
			if(error.getExperIdentifier() == null)
				entityManager.persist(error);
			else
				error = entityManager.merge(error);
		}
		
		// persist details
		for(ExpenseDetails detail : master.getExpenseDetailsCollection()){
			if(detail.getExpdIdentifier() == null)
				entityManager.persist(detail);
			else
				detail = entityManager.merge(detail);
			
			for(ExpenseDetailCodingBlocks cb : detail.getExpenseDetailCodingBlocksCollection()){
				if(cb.getExpdcIdentifier() == null)
					entityManager.persist(cb);
				else
					cb = entityManager.merge(cb);
			}
		}
		
		entityManager.flush();
	}

	public void updateExpenseDetails(ExpenseMasters expenseMaster, List<ExpenseDetailDisplayBean> expenseDetailDisplayList) {
		List<ExpenseDetails> details = expenseMaster.getExpenseDetailsCollection();
		
		List<ExpenseDetails> newDetails = new ArrayList<ExpenseDetails>();

		// remove deleted elements from details list
		for(Iterator<ExpenseDetails> itr = details.iterator(); itr.hasNext();){
			ExpenseDetails detail = itr.next();
			boolean missing = true;
			
			for(ExpenseDetailDisplayBean display: expenseDetailDisplayList){
				// no need to check new elements in display list
				if(display.getExpdId() == 0)
					continue;
				
				if(display.getExpdId() == detail.getExpdIdentifier().intValue()){
					missing = false;
					break;
				}
			}
			
			if(missing) {
				int lineId = detail.getLineItem();
				itr.remove();
		// Remove the coding block errors from the Error grid. kp 
		 commonService.deleteExpenseErrors(expenseMaster, IConstants.EXP_ERR_SRC_CB_TAB_PREFIX + " " + lineId) ;  
		
			}
		}

		// handle elements from view
		for(Iterator<ExpenseDetailDisplayBean> itr = expenseDetailDisplayList.iterator(); itr.hasNext();){
			ExpenseDetailDisplayBean display = itr.next();
			
			// if new add directly
			if(display.getExpdId() == 0){
				newDetails.add(updateOrNewExpenseDetailWithViewDate(null, display, expenseMaster));
				continue;
			}
			
			// if old then search & update
			for(ExpenseDetails detail: details){
				if(display.getExpdId() == detail.getExpdIdentifier().intValue()){
					updateOrNewExpenseDetailWithViewDate(detail, display, expenseMaster);
					break;
				}
			}
		}

		//add collected new expense details
		details.addAll(newDetails);
		
	}

	private ExpenseDetails updateOrNewExpenseDetailWithViewDate(ExpenseDetails detail,
			ExpenseDetailDisplayBean display, ExpenseMasters expenseMaster) {
		
		if(detail == null){
			detail = new ExpenseDetails();
			detail.setExpmIdentifier(expenseMaster);
		}
		
		detail.setLineItem(display.getLineItemNo());
		detail.setExpDate(display.getExpenseDate());
		detail.setDepartTime(TimeAndExpenseUtil.constructDateFromDateAndTime(display.getExpenseDate(), display.getDepartTime()));
		detail.setReturnTime(TimeAndExpenseUtil.constructDateFromDateAndTime(display.getExpenseDate(), display.getReturnTime()));
		
		if(log.isDebugEnabled()) log.debug("Departure time conversion => "+detail.getDepartTime());
		if(log.isDebugEnabled()) log.debug("Return time conversion => "+detail.getReturnTime());
		
		detail.setMileage(display.getMiles());
		detail.setMileOverrideInd(display.isCommonMiles()?"Y":"N");
		detail.setVicinityMileage(display.getVicinityMiles());
		detail.setFromElocCity(display.getFromCity());
		detail.setFromElocStProv(display.getFromState());
		detail.setToElocCity(display.getToCity());
		detail.setToElocStProv(display.getToState());
		detail.setRoundTripInd(display.isRoundTrip()?"Y":"N");
		detail.setDollarAmount(display.getAmount());
		if(display.getComments() != null) detail.setComments(display.getComments().trim());
		detail.setExpTypeCode(detailsService.getExpenseTypes(display.getExpenseType()));
		detail.setModifiedUserId(getLoggedInUser().getUserId());
		
		//overnight
		if("yes".equalsIgnoreCase(display.getOvernightInd()))
			detail.setOvernightInd("Y");
		else if("no".equalsIgnoreCase(display.getOvernightInd()))
			detail.setOvernightInd("N");
		
		// apply a standard coding block to every new detail
		applyCodingBlock(detail);
		
		return detail;
	}

	/**
	 * Applies a standard coding block to the provided expense detail.
	 * 
	 * @param <code>ExpenseDetails</code>
	 */
	protected void applyCodingBlock(ExpenseDetails detail) {
		if (detail == null)
			return;

		// apply coding block if new detail
		if (detail.getExpdIdentifier() == null) {
			List<ExpenseDetailCodingBlocks> cbs = new ArrayList<ExpenseDetailCodingBlocks>();
			DriverReimbExpTypeCbs dretc = ActionHelper.getDriverReimbExpTypeCb(
					getApplicationCache(), getUserSubject(), detail
							.getExpTypeCode().getExpTypeCode());
			if (dretc != null) {
				// driver reimbursement
				addDriverReimbCodingBlock(dretc, cbs, detail);
			} else {
				// check for travel requisition
				int expenseEventId = detail.getExpmIdentifier()
						.getExpevIdentifier().getExpevIdentifier();
				TravelReqEvents treqEvent = treqService
						.getTravelRequisitionRelatedWithExpense(expenseEventId);
				if (treqEvent != null) {
					// requisition exists
					int maxRevision = treqService.getMaxRevisionNo(treqEvent
							.getTreqeIdentifier());
					TravelReqMasters treqMaster = treqService
							.getTravelRequisitionByEventId(treqEvent
									.getTreqeIdentifier(), maxRevision);
					// add travel requisition coding block
					List<TravelReqDetailCodingBlock> treqCbs = treqMaster
							.getTravelReqDetailsCollection().get(0)
							.getTravelReqDetailCodingBlockCollection();
					for (TravelReqDetailCodingBlock item : treqCbs) {
						ExpenseDetailCodingBlocks expenseCb = new ExpenseDetailCodingBlocks();
						expenseCb.setExpdIdentifier(detail);
						expenseCb.setCodingBlock(item.getCodingBlock());
						expenseCb.setFacsAgy(item.getFacsAgy());
						expenseCb.setAppropriationYear(item
								.getAppropriationYear());
						expenseCb.setIndexCode(item.getIndexCode());
						expenseCb.setPca(item.getPca());
						expenseCb.setGrantNumber(item.getGrantNumber());
						expenseCb.setGrantPhase(item.getGrantPhase());
						expenseCb.setAgencyCode1(item.getAgencyCode1());
						expenseCb.setAgencyCode2(item.getAgencyCode2());
						expenseCb.setAgencyCode3(item.getAgencyCode3());
						expenseCb.setProjectNumber(item.getProjectNumber());
						expenseCb.setProjectPhase(item.getProjectPhase());
						expenseCb.setMultipurposeCode(item
								.getMultipurposeCode());
						expenseCb.setMultipurposeCode(item
								.getMultipurposeCode());
						expenseCb.setStandardInd(item.getStandardInd());
						expenseCb.setDollarAmount(item.getDollarAmount());
						expenseCb.setPercent(item.getPercent());
						cbs.add(expenseCb);
					}
				} else {
					// standard
					ExpenseDetailCodingBlocks standardCB = new ExpenseDetailCodingBlocks();
					standardCB.setExpdIdentifier(detail);
					standardCB.setStandardInd("Y");
					standardCB.setDollarAmount(detail.getDollarAmount());
					standardCB.setPercent(1);
					standardCB.setModifiedUserId(getLoggedInUser().getUserId());
					cbs.add(standardCB);
				}
			}
			detail.setExpenseDetailCodingBlocksCollection(cbs);
		} else {
			// update if driver reimbursement
			DriverReimbExpTypeCbs dretc = ActionHelper.getDriverReimbExpTypeCb(
					getApplicationCache(), getUserSubject(), detail
							.getExpTypeCode().getExpTypeCode());
			if (dretc != null) {
				detail.getExpenseDetailCodingBlocksCollection().clear();
				entityManager.flush();
				addDriverReimbCodingBlock(dretc, detail
						.getExpenseDetailCodingBlocksCollection(), detail);
			}
		}
	}

	@Override
	public void validate() {
		if(log.isDebugEnabled()) log.debug("Validating user input...");
		
		try{
			detailsDisplayList = getExpenseDetailDisplayBeanList();
		}catch(Exception ex){
			ex.printStackTrace();
			addFieldError("errors", "Required fields missing");
			return;
		}
		
		for(ExpenseDetailDisplayBean detail: detailsDisplayList){
			if(detail.getExpenseDate()==null){
				addFieldError("errors", "Invalid expense detail date");
				return;
			}
			if(detail.getFromCity() == null || detail.getFromState() == null
					|| "".equals(detail.getFromCity()) || "".equals(detail.getFromState())){
				addFieldError("errors", "Invalid FROM destination");
				return;
			}
			if(detail.getToCity() == null || detail.getToState() == null
					|| "".equals(detail.getToCity()) || "".equals(detail.getToState())){
				addFieldError("errors", "Invalid TO destination");
				return;
			}
			
			if(detail.getDepartTime() != null && !"".equals(detail.getDepartTime()) && 
					TimeAndExpenseUtil.constructDateFromDateAndTime(detail.getExpenseDate(), detail.getDepartTime()) == null){
				addFieldError("errors", "Invalid departure time");
				return;
			}

			if(detail.getReturnTime() != null && !"".equals(detail.getReturnTime()) &&
					TimeAndExpenseUtil.constructDateFromDateAndTime(detail.getExpenseDate(), detail.getReturnTime()) ==null){
				addFieldError("errors", "Invalid return time");
				return;
			}
			if((detail.getReturnTime() != null && detail.getDepartTime()==null) ||
					(!"".equals(detail.getReturnTime()) && "".equals(detail.getDepartTime()))	
					|| (detail.getDepartTime() != null && detail.getReturnTime() == null)
					|| (!"".equals(detail.getDepartTime()) && "".equals(detail.getReturnTime()))){
				addFieldError("errors", "Either none or both departure and return required ");
				return;
			}
			if(detail.getDepartTime() != null && !"".equals(detail.getDepartTime()) &&
					detail.getReturnTime() != null && !"".equals(detail.getReturnTime()) &&
					!TimeAndExpenseUtil.constructDateFromDateAndTime(detail.getExpenseDate(), detail.getReturnTime()).after(
					TimeAndExpenseUtil.constructDateFromDateAndTime(detail.getExpenseDate(), detail.getDepartTime()))){
				addFieldError("errors", "Return time must be greater than departure time");
				return;
			}
			if(detail.getExpenseType()==null || "".equals(detail.getExpenseType())){
				addFieldError("errors", "Expense type missing");
				return;
			}
			if(detail.getAmount() <= 0){
				addFieldError("errors", "Reimbursement amount missing");
				return;
			}
			if(detail.getComments() != null && detail.getComments().trim().length() > 255)
				addFieldError("errors", "Comments longer than allowed (255 characters). Please enter a shorter value.");
			
			if(detail.getIsMealRelated()){				
				if (detail.getDepartTime().length() == 0 || detail.getReturnTime().length() == 0){
					addFieldError("errors", "Departure and Return time is required for meal type expenses.");					
				}				
			}
		}
		
		// ENSURE expense is in modifiable state.  
		ExpenseMasters exp = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);
		if(exp.getStatus() != null && !"".equals(exp.getStatus()))
				addFieldError("errors", "Save failed as expense in unmodifiable state. Please SAVE on ID tab and then try again.");
	}
	
	/**
	 * Create a new CB element for driver reimbursement
	 * @param dretc
	 * @param cbs
	 * @param detail
	 */
	
	private void addDriverReimbCodingBlock (DriverReimbExpTypeCbs dretc, List<ExpenseDetailCodingBlocks> cbs, ExpenseDetails detail){
			ExpenseDetailCodingBlocks dreCb = new ExpenseDetailCodingBlocks();
			dreCb.setExpdIdentifier(detail);
			dreCb.setAppropriationYear(commonService.getCurrentFiscalYear(detail.getExpmIdentifier().getExpDateTo()));
			dreCb.setIndexCode(dretc.getIndexCode());
			dreCb.setStandardInd("N");
			dreCb.setDollarAmount(detail.getDollarAmount());
			dreCb.setPercent(1);
			dreCb.setModifiedUserId(getLoggedInUser().getUserId());
			cbs.add(dreCb);
	}
	
	private List<ExpenseDetailDisplayBean> getExpenseDetailDisplayBeanList() {
		Type expDtlDisplayListType = new TypeToken<List<ExpenseDetailDisplayBean>>() {}.getType();
		return jsonParser.fromJson(expenseDetailsJson, expDtlDisplayListType);
	}

	public String getExpenseDetailsJson() {
		return expenseDetailsJson;
	}

	public void setExpenseDetailsJson(String expenseDetailsJson) {
		this.expenseDetailsJson = expenseDetailsJson;
	}
	
}
