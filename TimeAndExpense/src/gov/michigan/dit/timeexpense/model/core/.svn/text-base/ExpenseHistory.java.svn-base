package gov.michigan.dit.timeexpense.model.core;

import gov.michigan.dit.timeexpense.util.ExpenseComparator;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ExpenseHistory implements ExpenseComparator,Serializable {

	private static final long serialVersionUID = -3804856873790314275L;

	private int expenseMasterId = 0;
	private int revisionNo = 0;
	private int expaIdentifier = 0;
	private String expActionCode = null;
	private String comments = null;
	private String modifiedUserId = null;
	private Date modifiedDate = null;
	private String modifiedDateDisplay = null;
	private String nextActionCode = null;
	private static String sortColumn = "";
	private static boolean sortAsc = true;
	
	

	public int getExpaIdentifier() {
	    return expaIdentifier;
	}
	public void setExpaIdentifier(int expaIdentifier) {
	    this.expaIdentifier = expaIdentifier;
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
	public String getExpActionCode() {
		return expActionCode;
	}
	public void setExpActionCode(String expActionCode) {
		this.expActionCode = expActionCode;
	}
	public int getExpenseMasterId() {
		return expenseMasterId;
	}
	public void setExpenseMasterId(int expenseMasterId) {
		this.expenseMasterId = expenseMasterId;
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
				if(((ExpenseHistory)obj_expenseData).getModifiedDate()!=null){
					compareValue = modifiedDate.compareTo(((ExpenseHistory)obj_expenseData).getModifiedDate());
					
				}
			}
		}
		
		if(sortColumn.equalsIgnoreCase(IConstants.EXPENSE_HISTORY_LIST_ACTION_CODE_HEADER)){
			String expenseActionCode = this.getExpActionCode();
			if(expenseActionCode!=null){
				if(((ExpenseHistory)obj_expenseData).getExpActionCode()!=null){
					compareValue = expenseActionCode.compareTo(((ExpenseHistory)obj_expenseData).getExpActionCode());
					
				}
			}
		}
		
		if(sortColumn.equalsIgnoreCase(IConstants.EXPENSE_HISTORY_LIST_COMMENTS_HEADER)){
			String comments = this.getComments();
			if(comments!=null){
				if(((ExpenseHistory)obj_expenseData).getComments()!=null){
					compareValue = comments.compareTo(((ExpenseHistory)obj_expenseData).getComments());
					
				}
			}
		}
		
		if(sortColumn.equalsIgnoreCase(IConstants.EXPENSE_HISTORY_LIST_VERSION_HEADER)){
			Integer revisionNo = this.getRevisionNo();
			if(revisionNo!=null){
				if(((ExpenseHistory)obj_expenseData).getRevisionNo()!=0){
					compareValue = revisionNo.compareTo(((ExpenseHistory)obj_expenseData).getRevisionNo());
					
				}
			}
		}
		
		if(sortColumn.equalsIgnoreCase(IConstants.EXPENSE_HISTORY_LIST_MODIFIED_USER_ID_HEADER)){
			String modifierUserId = this.getModifiedUserId();
			if(modifierUserId!=null){
				if(((ExpenseHistory)obj_expenseData).getModifiedUserId()!=null){
					compareValue = modifierUserId.compareTo(((ExpenseHistory)obj_expenseData).getModifiedUserId());
					
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
