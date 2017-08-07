package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

public class AdvanceHistory implements Serializable {

	private static final long serialVersionUID = 8602741779475322257L;
	
	private int revisionNumber = 0;
	private String actionCode = " ";
	private String comments = " ";
	private String modifiedUserId = "";
	private Date modifiedDate;
	private String modifiedDateDisplay = "";
	
	public int getRevisionNumber() {
		return revisionNumber;
	}
	public void setRevisionNumber(int revisionNumber) {
		this.revisionNumber = revisionNumber;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getModifiedUserId() {
		return modifiedUserId;
	}
	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public void setModifiedDateDisplay(String modifiedDateDisplay) {
		this.modifiedDateDisplay = modifiedDateDisplay;
	}
	public String getModifiedDateDisplay() {
		return modifiedDateDisplay;
	}
}
