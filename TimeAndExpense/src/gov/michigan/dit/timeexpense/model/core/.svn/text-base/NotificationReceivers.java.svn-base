package gov.michigan.dit.timeexpense.model.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author chiduras
 */
@Entity
@Table(name = "NOTIFICATION_RECEIVERS")
public class NotificationReceivers implements Serializable {

	private static final long serialVersionUID = -3550793533605348668L;

	@Id
    @Column(name = "NTRC_IDENTIFIER")
    private Integer ntrcIdentifier;
    @Column(name = "RECEIVER")
    private String receiver;
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    
    @Column(name = "MESSAGE_ID")
    private String messageId;

    public NotificationReceivers() {
    }

    public NotificationReceivers(Integer ntrcIdentifier) {
        this.ntrcIdentifier = ntrcIdentifier;
    }

    public Integer getNtrcIdentifier() {
        return ntrcIdentifier;
    }

    public void setNtrcIdentifier(Integer ntrcIdentifier) {
        this.ntrcIdentifier = ntrcIdentifier;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ntrcIdentifier != null ? ntrcIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificationReceivers)) {
            return false;
        }
        NotificationReceivers other = (NotificationReceivers) object;
        if ((this.ntrcIdentifier == null && other.ntrcIdentifier != null) || (this.ntrcIdentifier != null && !this.ntrcIdentifier.equals(other.ntrcIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.db.NotificationReceivers[ntrcIdentifier=" + ntrcIdentifier + "]";
    }

    public void setMessageId(String messageId) {
	this.messageId = messageId;
    }

    public String getMessageId() {
	return messageId;
    }

}
