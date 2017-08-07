/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "NOTIFICATION_MESSAGES")
@NamedQueries({@NamedQuery(name = "NotificationMessages.findAll", query = "SELECT n FROM NotificationMessages n"), @NamedQuery(name = "NotificationMessages.findByMessageId", query = "SELECT n FROM NotificationMessages n WHERE n.messageId = :messageId"), @NamedQuery(name = "NotificationMessages.findBySubject", query = "SELECT n FROM NotificationMessages n WHERE n.subject = :subject"), @NamedQuery(name = "NotificationMessages.findByText", query = "SELECT n FROM NotificationMessages n WHERE n.text = :text"), @NamedQuery(name = "NotificationMessages.findByModifiedUserId", query = "SELECT n FROM NotificationMessages n WHERE n.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "NotificationMessages.findByModifiedDate", query = "SELECT n FROM NotificationMessages n WHERE n.modifiedDate = :modifiedDate")})
public class NotificationMessages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "MESSAGE_ID")
    private String messageId;
    
    @Column(name = "SUBJECT")
    private String subject;
    
    @Column(name = "TEXT")
    private String text;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notificationMessages")
    private Collection<TkuoptNotifications> tkuoptNotificationsCollection;

    public NotificationMessages() {
    }

    public NotificationMessages(String messageId) {
        this.messageId = messageId;
    }

    public NotificationMessages(String messageId, String subject, String text) {
        this.messageId = messageId;
        this.subject = subject;
        this.text = text;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Collection<TkuoptNotifications> getTkuoptNotificationsCollection() {
        return tkuoptNotificationsCollection;
    }

    public void setTkuoptNotificationsCollection(Collection<TkuoptNotifications> tkuoptNotificationsCollection) {
        this.tkuoptNotificationsCollection = tkuoptNotificationsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificationMessages)) {
            return false;
        }
        NotificationMessages other = (NotificationMessages) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.NotificationMessages[messageId=" + messageId + "]";
    }

}
