package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.ComponentViewState;
import gov.michigan.dit.timeexpense.model.display.ExpenseDetailView;
import gov.michigan.dit.timeexpense.model.display.ExpenseTypeDisplayBean;
import gov.michigan.dit.timeexpense.service.AdvanceDSP;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.service.ExpenseDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.service.SecurityManager;
import gov.michigan.dit.timeexpense.util.ExpenseViewUtil;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import java.util.Date;
import java.util.List;


/**
 * Action class providing services for expense details.
 * 
 * @author chaudharym
 */
public class ExpenseDetailsAction extends BaseAction {

	private static final long serialVersionUID = -5296133474998471011L;
	
	private ExpenseMasters expenseMaster;
	private ExpenseLineItemDSP detailsService ;
	private AdvanceDSP advanceService ;
	private ExpenseDSP expenseService;
	private SecurityManager security;
	
	private ExpenseViewUtil viewUtil;
	
	private String isOvernight;
	
	@Override
	public void prepare() {
		security = new SecurityManager(entityManager);
		
		viewUtil = new ExpenseViewUtil();
		viewUtil.setJsonParser(jsonParser);

		detailsService = new ExpenseLineItemDSP(entityManager);
		advanceService = new AdvanceDSP(entityManager);
		expenseService = new ExpenseDSP(entityManager);

		ExpenseMasters master = (ExpenseMasters)session.get(IConstants.EXPENSE_SESSION_DATA);
		if(master != null)
			expenseMaster = entityManager.merge(master);
	}

	@Override
	public String execute() throws Exception {
		StringBuilder buff = new StringBuilder();
		buff.append("{");

		if(expenseMaster != null) buff.append("expenseDetails:{"+viewUtil.prepareDetailsJson(expenseMaster, entityManager,CommonDSP.isUserSuperUser(getLoggedInUser()))+"},");
		
		buff.append("outstandingAdvance:"+ getFormattedOutstandingAdvance()+",");
		buff.append("expenseTypes:" + constructExpenseTypesJson()+",");
		buff.append("state:"+constructStateJson() +",");
		buff.append("cities:"+constructCityJson() +",");
		buff.append("viewState:"+constructViewJson() +",");

		boolean supervisorReceiptsReviewed = expenseMaster == null || expenseMaster.getSuperReviewedReceiptsInd() == null 
												|| "".equals(expenseMaster.getSuperReviewedReceiptsInd())
												|| "N".equalsIgnoreCase(expenseMaster.getSuperReviewedReceiptsInd())? false : true;
		
		buff.append("supervisorReceiptsReviewed:"+ supervisorReceiptsReviewed);
		buff.append("}");
		
		setJsonResponse(buff.toString());
		
		return IConstants.SUCCESS;
	}
	
	private String constructViewJson() {
		ExpenseDetailView view = prepareView();
		return jsonParser.toJson(view);
	}

	/**
	 * Prepares the view state for audit and documents review.
	 */
	private ExpenseDetailView prepareView(){
		ExpenseDetailView view = new ExpenseDetailView();

		//prepare audit complete view state
		view.setAudit(prepareAuditCompleteViewState());
		
		//prepare supervisor reviewed receipts view state
		view.setSupervisorReview(prepareSupervisorDocumentsReviewedViewState());
		
		return view;
	}
	
	private ComponentViewState prepareAuditCompleteViewState() {
		ComponentViewState viewState = null;
		
		if(isUserInManagerRole() || isUserInStatewideRole()){
			if(expenseMaster == null){
				viewState = ComponentViewState.DISABLED;
			}
			// is authorized to perform audit and not already marked to be audit complete
			// and is not viewing his/her own record
			else if("Y".equalsIgnoreCase(expenseMaster.getCurrentInd()) 
					&& expenseMaster.getStatus() != null && !"".equals(expenseMaster.getStatus())
					&& security.checkModuleAccess(getLoggedInUser(), IConstants.AUDIT_COMPLETE_AUTHORIZATION_CODE)
					&& !expenseService.isExpenseMarkedAuditComplete(expenseMaster)
					&& getLoggedInUser().getEmpIdentifier() != getUserSubject().getEmployeeId()){
				viewState = ComponentViewState.ENABLED;
			}else{
				viewState = ComponentViewState.DISABLED;
			}
		}else{
			viewState = ComponentViewState.HIDDEN;
		}
		
		return viewState;
	}

	private ComponentViewState prepareSupervisorDocumentsReviewedViewState() {
		if(expenseMaster == null) return ComponentViewState.DISABLED;
		
		String module = getModuleIdFromSession();
		
		// only editable if manager & in SUBM status
		return "Y".equalsIgnoreCase(expenseMaster.getCurrentInd()) && 
				IConstants.APPROVE_WEB_MANAGER.equals(module) 
				&& IConstants.SUBMIT.equalsIgnoreCase(expenseMaster.getStatus()) 
				? ComponentViewState.ENABLED : ComponentViewState.DISABLED;
	}

	private String getFormattedOutstandingAdvance() {
		double outstandingAdvance = 0;
		
		if(session.containsKey(IConstants.EXPENSE_TOTAL_OUTSTANDING_ADVANCE_AMOUNT)){
			outstandingAdvance = (Double)session.get(IConstants.EXPENSE_TOTAL_OUTSTANDING_ADVANCE_AMOUNT);
		}else{
			outstandingAdvance = advanceService.getTotalAdvanceOutstandingAmount(
										getUserSubject().getEmployeeId()); 
		// store in session for reuse
		session.put(IConstants.EXPENSE_TOTAL_OUTSTANDING_ADVANCE_AMOUNT, outstandingAdvance);
		}
		
		return TimeAndExpenseUtil.displayAmountTwoDigits(outstandingAdvance);
	}

	public String constructStateJson() {
		List<Object> states = (List<Object>)getApplicationCache().get(IConstants.STATES);
		
		StringBuilder buff = new StringBuilder();
		buff.append("[{code:\"\",name:\"\"}");
		buff.append(",{code:\"NA\",name:\"N/A\"}");
		
		for(Object obj : states){
			Object[] state = (Object[])obj;

			String stateCode = (String)state[0];
			// replace whitespace between words with blank space
			String stateName = ((String)state[1]).replaceAll("\\s", " ");
			buff.append(",{code:\""); buff.append(stateCode);buff.append("\"");
			buff.append(",name:\""); buff.append(stateName);buff.append("\"}");
		}
		
		return buff.append("]").toString();
	}

	public String constructCityJson() {
		StringBuilder buff = new StringBuilder();
		buff.append("[");
		buff.append("{name:\"\"},{name:\"N/A\"}");

		
		List<String> cities = (List)session.get(IConstants.CITIES);		
		if (cities == null)
			cities  = detailsService.findCities(getUserSubject().getDepartment(), getUserSubject().getAgency());

		for(String city: cities){
			buff.append(",{name:\""+city+"\"}");
		}
		
		buff.append("]");

		return buff.toString();
	}

	public String constructExpenseTypesJson() {
		
		Date effectiveDate = (expenseMaster != null) ? expenseMaster.getExpDateTo() : new Date();
		
		//Flags, to get correct expense type list.
		String isOutOfState =  (expenseMaster != null) ? expenseMaster.getOutOfStateInd() : "N";
		String isTravel =  (expenseMaster != null) ? expenseMaster.getTravelInd() : "N";
		boolean isPDF = (expenseMaster != null) ? ("Y".equalsIgnoreCase(expenseMaster.getPdfInd())  ? true : false ) : false;
				
		if (isOvernight == null || isOvernight.length() == 0)  
			isOvernight = null;
		else if ("no".equalsIgnoreCase(isOvernight))
			isOvernight=  "N";
		else if ( "yes".equalsIgnoreCase(isOvernight))
			isOvernight=  "Y";
		
		String department = "";
		String agency = "";
		String tku = "";
		
		UserSubject userSubject = (UserSubject) session.get(IConstants.USER_SUBJECT_SESSION_KEY_NAME);
		if (userSubject != null){
			department = userSubject.getDepartment();
			agency = userSubject.getAgency();
			tku = userSubject.getTku();
		}
		
		UserProfile userProfile = (UserProfile) session.get(IConstants.USER_PROFILE_SESSION_KEY_NAME);
		
		List<ExpenseTypeDisplayBean>  expenseTypes = null;
		
		if (CommonDSP.isUserSuperUser(userProfile))
			expenseTypes = detailsService
					.findAllExpenseTypesSuperUser(effectiveDate);
		else
		
			expenseTypes = detailsService
					.findAllExpenseTypesWithMileageIndicator(effectiveDate,
							isOutOfState, isTravel, isOvernight, isPDF,
							department, agency, tku);
		
		StringBuilder buff = new StringBuilder();
		buff.append("[");
		buff.append(getBlankExpenseTypeJson());

		for(ExpenseTypeDisplayBean expenseType : expenseTypes){
			buff.append(",{code:\""+ expenseType.getExpTypeCode() +"\",");
			buff.append("desc:\""+ expenseType.getDescription() +"\",");
			buff.append("isMileageRelated:"+ expenseType.isMileageRelated() + "," );
			buff.append("isMealRelated:"+ expenseType.isMealRelated() +"}");
		}
		
		buff.append("]");
		
		return buff.toString();
	}
	
	/*
	 * Creates blank <code>ExpenseTypes</code> element JSON.
	 */
	private String getBlankExpenseTypeJson() {
		return "{code:\"\", desc:\"\", isMileageRelated: false, isMealRelated: false}";
	}

	public String getIsOvernight() {
		return isOvernight;
	}

	public void setIsOvernight(String isOvernight) {
		this.isOvernight = isOvernight;
	}
}
