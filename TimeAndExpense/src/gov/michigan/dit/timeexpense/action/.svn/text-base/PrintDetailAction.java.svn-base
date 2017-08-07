package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.ExpenseDetailCodingBlocks;
import gov.michigan.dit.timeexpense.model.core.ExpenseDetails;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.model.display.ExpenseDetailAndCodingBlockBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseDetailDisplayBean;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 * This is the Action class which handles the Print Detail Report.
 * 
 * @author chiduras
 * @author chaudharym
 */
public class PrintDetailAction extends AbstractAction implements ServletRequestAware {
	private static final long serialVersionUID = 1831518973738159840L;

    private AppointmentDSP apptService;
    private HttpServletRequest request;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
	SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
	DecimalFormat decimalFormatter = new DecimalFormat("####0.00");
    
    public void prepare() {
    	this.apptService = new AppointmentDSP(entityManager);
    }

    public String execute() throws Exception {
    	// find TKU NAME
    	UserSubject subject = getUserSubject();
    	String tkuName = apptService.findTkuName(subject.getDepartment(), subject.getAgency(), subject.getTku());
    	request.setAttribute("TKU", subject.getTku());
    	request.setAttribute("TKU_NAME", tkuName);
    	
    	// As report needs details and CB lists, associate detail and coding block lists if not already fetched from DB
    	ExpenseMasters expense = (ExpenseMasters) session.get(IConstants.EXPENSE_SESSION_DATA);
    	if(expense == null) return IConstants.FAILURE;
    	
    	expense = entityManager.merge(expense);
    	
		// prepare display bean from expense details data
		List<ExpenseDetailDisplayBean> detailDisplayBeanList = new ArrayList<ExpenseDetailDisplayBean>(expense.getExpenseDetailsCollection().size());
		// prepare display bean from coding block & expense details data
		List<ExpenseDetailAndCodingBlockBean> CBDetailBeanList = new ArrayList<ExpenseDetailAndCodingBlockBean>();
		
		for(ExpenseDetails detail : expense.getExpenseDetailsCollection()){
			ExpenseDetailDisplayBean display = new ExpenseDetailDisplayBean();
			display.setExpenseDate(detail.getExpDate());
			display.setExpenseDateString(dateFormatter.format(detail.getExpDate()));
			display.setFromCity(detail.getFromElocCity());
			display.setFromState(detail.getFromElocStProv());
			display.setToCity(detail.getToElocCity());
			display.setToState(detail.getToElocStProv());
			display.setDepartureTime(detail.getDepartTime());
			display.setDepartTime(detail.getDepartTime() == null ? "" : timeFormatter.format(detail.getDepartTime()));
			display.setReturnTime(detail.getReturnTime() == null ? "" : timeFormatter.format(detail.getReturnTime()));
			display.setExpenseType(detail.getExpTypeCode().getDescription());
			display.setAmount(detail.getDollarAmount());
			display.setMiles(((Double)detail.getMileage()).intValue());
			display.setVicinityMiles(((Double)detail.getVicinityMileage()).intValue());
			display.setCommonMiles("Y".equalsIgnoreCase(detail.getMileOverrideInd()));
			display.setRoundTrip(detail.isRoundTrip());
			
			// pick only 254 chars from comments and split them
			if(detail.getComments() == null){
				display.setCommentsSplit_1("");
				display.setCommentsSplit_2("");
			}else if(detail.getComments().length() <= 127){
				display.setCommentsSplit_1(detail.getComments());
				display.setCommentsSplit_2("");
			}else if(detail.getComments().length() > 127 && detail.getComments().length() <= 254){
				display.setCommentsSplit_1(detail.getComments().substring(0, 127));
				display.setCommentsSplit_2(detail.getComments().substring(127));
			}else{
				display.setCommentsSplit_1(detail.getComments().substring(0,127));
				display.setCommentsSplit_2(detail.getComments().substring(127, 254));
			}
			
			detailDisplayBeanList.add(display);
			
			//While here, get coding block info also 
			for(ExpenseDetailCodingBlocks cb: detail.getExpenseDetailCodingBlocksCollection()){
				ExpenseDetailAndCodingBlockBean detailCBDisplay = new ExpenseDetailAndCodingBlockBean(
					dateFormatter.format(detail.getExpDate()),detail.getExpTypeCode().getDescription(),
					cb.getDollarAmount(), cb.getAppropriationYear(), cb.getIndexCode(), cb.getPca(),
					cb.getGrantNumber(), cb.getGrantPhase(), cb.getAgencyCode1(), cb.getProjectNumber(),
					cb.getProjectPhase(), cb.getAgencyCode2(), cb.getAgencyCode3(), cb.getMultipurposeCode(),
					"Y".equalsIgnoreCase(cb.getStandardInd()));				
				CBDetailBeanList.add(detailCBDisplay);
			}			
		}
		// sort both Detail and CB-Detail collections for the report
		sortCollections (detailDisplayBeanList, CBDetailBeanList);
		
    	request.setAttribute("DETAILS", detailDisplayBeanList);
    	request.setAttribute("DETAILS_BY_CB", CBDetailBeanList);
    	
    	session.put(IConstants.EXPENSE_SESSION_DATA, expense);
    	
    	return IConstants.SUCCESS;
    }
    
    private void sortCollections(List<ExpenseDetailDisplayBean> detailDisplayBeanList, List<ExpenseDetailAndCodingBlockBean> CBDetailBeanList) {
		// sort collections for subreports
		Collections.sort(detailDisplayBeanList, new ExpenseDetailComparer());
		// add a display sequence number for expense details
		int size = detailDisplayBeanList.size();
		for (int i = 0; i < size; i++) {
			detailDisplayBeanList.get(i).setDisplaySequenceNumber(i);
		}
		
		// Coding Block details server side sorting not used at this time.
		// Crystal report sorting is used instead.
		/*Collections.sort(CBDetailBeanList,
		new ExpenseCodingBlockDetailComparer());

		// add a display sequence number for coding block details
		size = CBDetailBeanList.size();
		for (int i = 0; i < size; i++) {
			if (StringUtils.isEmpty(CBDetailBeanList.get(i).getAppropriationYear())){
			CBDetailBeanList.get(i).setDisplaySequenceNumber(size);
			} else {
				CBDetailBeanList.get(i).setDisplaySequenceNumber(i);
			}
		}*/
	}

    public void setServletRequest(HttpServletRequest request) {
    	this.request = request;
    }
    
    /**
     * Comparator implementation for Expense Details subreport
     * @author hussainz
     *
     */
    
    class ExpenseDetailComparer implements Comparator<ExpenseDetailDisplayBean> {
        
    	public int compare(ExpenseDetailDisplayBean detail1, ExpenseDetailDisplayBean detail2)
            {
    		// Compare expense dates
    		int result = detail1.getExpenseDate().compareTo(detail2.getExpenseDate());
    		if (result == 0) {
    			// Both departure times are null
    			if (detail1.getDepartureTime() == null && detail2.getDepartureTime() == null) return 0;
    		    // At least one departure time is null. Show nulls at the bottom of the page 
    		    if (detail1.getDepartureTime() != null && detail2.getDepartureTime() == null) return -1;
    		    if (detail1.getDepartureTime() == null && detail2.getDepartureTime() != null) return 1; 
    			// Neither departure time is null 
    		      result = detail1.getDepartureTime().compareTo(detail2.getDepartureTime());
    		    }
    		return result;            	
            }
    }
    
    /**
     * Comparator implementation for Coding Block details subreport
     * @author hussainz
     *
     */
    

	class ExpenseCodingBlockDetailComparer implements
			Comparator<ExpenseDetailAndCodingBlockBean> {

		public int compare(ExpenseDetailAndCodingBlockBean cb1,
				ExpenseDetailAndCodingBlockBean cb2) {
			// Compare appropriation year 
			int result = cb1.getAppropriationYear().compareTo(
					cb2.getAppropriationYear());
			if (result != 0)
				return result;
			else {
				// If AY same, compare expense dates
				result = cb1.getExpenseDate().compareTo(cb2.getExpenseDate());
				if (result != 0)
					return result;
				else {
					// Compare expense types if same AY and dates
					result = cb1.getExpenseTypeDesc().compareTo(
							cb2.getExpenseTypeDesc());
					if (result != 0)
						return result;
					else {
						// Lastly, compare amounts
						result = ((Double) cb1.getExpenseAmount())
								.compareTo(((Double) cb2.getExpenseAmount()));
					}
				}
			}
			return result;
		}
	}
}
