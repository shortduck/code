/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "TKUOPT_NOTIFICATIONS")
@NamedQueries({@NamedQuery(name = "TkuoptNotifications.findAll", query = "SELECT t FROM TkuoptNotifications t"), @NamedQuery(name = "TkuoptNotifications.findByDepartment", query = "SELECT t FROM TkuoptNotifications t WHERE t.tkuoptNotificationsPK.department = :department"), @NamedQuery(name = "TkuoptNotifications.findByAgency", query = "SELECT t FROM TkuoptNotifications t WHERE t.tkuoptNotificationsPK.agency = :agency"), @NamedQuery(name = "TkuoptNotifications.findByTku", query = "SELECT t FROM TkuoptNotifications t WHERE t.tkuoptNotificationsPK.tku = :tku"), @NamedQuery(name = "TkuoptNotifications.findByMessageId", query = "SELECT t FROM TkuoptNotifications t WHERE t.tkuoptNotificationsPK.messageId = :messageId"), @NamedQuery(name = "TkuoptNotifications.findByModifiedUserId", query = "SELECT t FROM TkuoptNotifications t WHERE t.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "TkuoptNotifications.findByModifiedDate", query = "SELECT t FROM TkuoptNotifications t WHERE t.modifiedDate = :modifiedDate")})
public class TkuoptNotifications implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TkuoptNotificationsPK tkuoptNotificationsPK;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "MESSAGE_ID", referencedColumnName = "MESSAGE_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NotificationMessages notificationMessages;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false), @JoinColumn(name = "TKU", referencedColumnName = "TKU", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Tkus tkus;

    public TkuoptNotifications() {
    }

    public TkuoptNotifications(TkuoptNotificationsPK tkuoptNotificationsPK) {
        this.tkuoptNotificationsPK = tkuoptNotificationsPK;
    }

    public TkuoptNotifications(String department, String agency, String tku, String messageId) {
        this.tkuoptNotificationsPK = new TkuoptNotificationsPK(department, agency, tku, messageId);
    }

    public TkuoptNotificationsPK getTkuoptNotificationsPK() {
        return tkuoptNotificationsPK;
    }

    public void setTkuoptNotificationsPK(TkuoptNotificationsPK tkuoptNotificationsPK) {
        this.tkuoptNotificationsPK = tkuoptNotificationsPK;
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

    public NotificationMessages getNotificationMessages() {
        return notificationMessages;
    }

    public void setNotificationMessages(NotificationMessages notificationMessages) {
        this.notificationMessages = notificationMessages;
    }

    public Tkus getTkus() {
        return tkus;
    }

    public void setTkus(Tkus tkus) {
        this.tkus = tkus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tkuoptNotificationsPK != null ? tkuoptNotificationsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TkuoptNotifications)) {
            return false;
        }
        TkuoptNotifications other = (TkuoptNotifications) object;
        if ((this.tkuoptNotificationsPK == null && other.tkuoptNotificationsPK != null) || (this.tkuoptNotificationsPK != null && !this.tkuoptNotificationsPK.equals(other.tkuoptNotificationsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.TkuoptNotifications[tkuoptNotificationsPK=" + tkuoptNotificationsPK + "]";
    }

}
