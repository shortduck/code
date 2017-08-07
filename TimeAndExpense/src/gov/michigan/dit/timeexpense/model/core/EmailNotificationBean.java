package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;



/**
 * Class to contain information about the parameters required to call the send_dcds_notification
 * for sending email notification
 *  
 * @author chiduras
 */
public class EmailNotificationBean implements Serializable {

	private static final long serialVersionUID = -8118548883614480154L;

	private String toUser;
	private String fromUser;
	private String msgId;
	private String addlText;
	private String dept;
	private String agy;
	private String tku;
	private int   sesNum;
		
	
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getAddlText() {
		return addlText;
	}
	public void setAddlText(String addlText) {
		this.addlText = addlText;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getAgy() {
		return agy;
	}
	public void setAgy(String agy) {
		this.agy = agy;
	}
	public String getTku() {
		return tku;
	}
	public void setTku(String tku) {
		this.tku = tku;
	}
	public int getSesNum() {
		return sesNum;
	}
	public void setSesNum(int sesNum) {
		this.sesNum = sesNum;
	}
	
	
	
	
}
