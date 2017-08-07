package gov.michigan.dit.timeexpense.model.core;

import gov.michigan.dit.timeexpense.util.ExpenseComparator;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TravelReqHistory implements ExpenseComparator,Serializable {

	private static final long serialVersionUID = -3804856873790314275L;

	private int treqMasterId = 0;
	private int revisionNo = 0;
	private int treqaIdentifier = 0;
	private String treqActionCode = null;
	private String comments = null;
	private String modifiedUserId = null;
	private Date modifiedDate = null;
	private String modifiedDateDisplay = null;
	private String nextActionCode = null;
	private static String sortColumn = "";
	private static boolean sortAsc = true;
	
	

	public int getTreqaIdentifier() {
	    return treqaIdentifier;
	}
	public void setTreqaIdentifier(int treqaIdentifier) {
	    this.treqaIdentifier = treqaIdentifier;
	}
	public int getRevisionNo() {
		return revisionNo;
	}
	public void setRevisionNo(int revisionNo) {
		this.revisionNo = revisionNo;
	}	
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getTreqActionCode() {
		return treqActionCode;
	}
	public void setTreqActionCode(String treqActionCode) {
		this.treqActionCode = treqActionCode;
	}
	public int getTreqMasterId() {
		return treqMasterId;
	}
	public void setTreqMasterId(int treqMasterId) {
		this.treqMasterId = treqMasterId;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedUserId() {
		return modifiedUserId;
	}
	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}
	public String getNextActionCode() {
		return nextActionCode;
	}
	public void setNextActionCode(String nextActionCode) {
		this.nextActionCode = nextActionCode;
	}
	
	public int compareTo(Object obj_expenseData) {
		// TODO Auto-generated method stub
		int compareValue = 0;
		if(sortColumn.equalsIgnoreCase(IConstants.EXPENSE_HISTORY_LIST_MODIFIED_DATE_HEADER)){
			Date modifiedDate = this.getModifiedDate();
			if(modifiedDate!=null){
				if(((TravelReqHistory)obj_expenseData).getModifiedDate()!=null){
					compareValue = modifiedDate.compareTo(((TravelReqHistory)obj_expenseData).getModifiedDate());
					
				}
			}
		}
		
		if(sortColumn.equalsIgnoreCase(IConstants.EXPENSE_HISTORY_LIST_ACTION_CODE_HEADER)){
			String expenseActionCode = this.getTreqActionCode();
			if(expenseActionCode!=null){
				if(((TravelReqHistory)obj_expenseData).getTreqActionCode()!=null){
					compareValue = expenseActionCode.compareTo(((TravelReqHistory)obj_expenseData).getTreqActionCode());
					
				}
			}
		}
		
		if(sortColumn.equalsIgnoreCase(IConstants.EXPENSE_HISTORY_LIST_COMMENTS_HEADER)){
			String comments = this.getComments();
			if(comments!=null){
				if(((TravelReqHistory)obj_expenseData).getComments()!=null){
					compareValue = comments.compareTo(((TravelReqHistory)obj_expenseData).getComments());
					
				}
			}
		}
		
		if(sortColumn.equalsIgnoreCase(IConstants.EXPENSE_HISTORY_LIST_VERSION_HEADER)){
			Integer revisionNo = this.getRevisionNo();
			if(revisionNo!=null){
				if(((TravelReqHistory)obj_expenseData).getRevisionNo()!=0){
					compareValue = revisionNo.compareTo(((TravelReqHistory)obj_expenseData).getRevisionNo());
					
				}
			}
		}
		
		if(sortColumn.equalsIgnoreCase(IConstants.EXPENSE_HISTORY_LIST_MODIFIED_USER_ID_HEADER)){
			String modifierUserId = this.getModifiedUserId();
			if(modifierUserId!=null){
				if(((TravelReqHistory)obj_expenseData).getModifiedUserId()!=null){
					compareValue = modifierUserId.compareTo(((TravelReqHistory)obj_expenseData).getModifiedUserId());
					
				}
			}
		}
		
		if (sortAsc)
	           return compareValue;
	        else
	           return -compareValue;
	}
	
	public List sort(List obj_expenseList, String colName, boolean sortOrder) {
        if (colName==null || obj_expenseList==null || obj_expenseList.size()==0)
            return obj_expenseList;
        sortColumn=colName;
        this.sortAsc=sortOrder;
        Collections.sort(obj_expenseList);
		return obj_expenseList;
	}
	public void setModifiedDateDisplay(String modifiedDateDisplay) {
		this.modifiedDateDisplay = modifiedDateDisplay;
	}
	public String getModifiedDateDisplay() {
		return modifiedDateDisplay;
	}

}
