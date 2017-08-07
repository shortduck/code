/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "TA_ACTIONS")
@NamedQueries({@NamedQuery(name = "TaActions.findAll", query = "SELECT t FROM TaActions t"), @NamedQuery(name = "TaActions.findByTactIdentifier", query = "SELECT t FROM TaActions t WHERE t.tactIdentifier = :tactIdentifier"), @NamedQuery(name = "TaActions.findByComments", query = "SELECT t FROM TaActions t WHERE t.comments = :comments"), @NamedQuery(name = "TaActions.findByModifiedUserId", query = "SELECT t FROM TaActions t WHERE t.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "TaActions.findByModifiedDate", query = "SELECT t FROM TaActions t WHERE t.modifiedDate = :modifiedDate")})
public class TaActions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "TACT_IDENTIFIER")
    private Integer tactIdentifier;
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "ACTION_CODE", referencedColumnName = "ACTION_CODE")
    @ManyToOne(optional = false)
    private Actions actionCode;
    @JoinColumn(name = "NEXT_ACTION_CODE", referencedColumnName = "ACTION_CODE")
    @ManyToOne
    private Actions nextActionCode;
    @JoinColumn(name = "TA_IDENTIFIER", referencedColumnName = "TA_IDENTIFIER")
    @ManyToOne(optional = false)
    private Tas taIdentifier;

    public TaActions() {
    }

    public TaActions(Integer tactIdentifier) {
        this.tactIdentifier = tactIdentifier;
    }

    public TaActions(Integer tactIdentifier, Date modifiedDate) {
        this.tactIdentifier = tactIdentifier;
        this.modifiedDate = modifiedDate;
    }

    public Integer getTactIdentifier() {
        return tactIdentifier;
    }

    public void setTactIdentifier(Integer tactIdentifier) {
        this.tactIdentifier = tactIdentifier;
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

    public Actions getActionCode() {
        return actionCode;
    }

    public void setActionCode(Actions actionCode) {
        this.actionCode = actionCode;
    }

    public Actions getNextActionCode() {
        return nextActionCode;
    }

    public void setNextActionCode(Actions nextActionCode) {
        this.nextActionCode = nextActionCode;
    }

    public Tas getTaIdentifier() {
        return taIdentifier;
    }

    public void setTaIdentifier(Tas taIdentifier) {
        this.taIdentifier = taIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tactIdentifier != null ? tactIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TaActions)) {
            return false;
        }
        TaActions other = (TaActions) object;
        if ((this.tactIdentifier == null && other.tactIdentifier != null) || (this.tactIdentifier != null && !this.tactIdentifier.equals(other.tactIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.TaActions[tactIdentifier=" + tactIdentifier + "]";
    }

}
