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
@Table(name = "TKU_ACTIONS")
@NamedQueries({@NamedQuery(name = "TkuActions.findAll", query = "SELECT t FROM TkuActions t"), @NamedQuery(name = "TkuActions.findByTkactIdentifier", query = "SELECT t FROM TkuActions t WHERE t.tkactIdentifier = :tkactIdentifier"), @NamedQuery(name = "TkuActions.findByModifiedDate", query = "SELECT t FROM TkuActions t WHERE t.modifiedDate = :modifiedDate"), @NamedQuery(name = "TkuActions.findByComments", query = "SELECT t FROM TkuActions t WHERE t.comments = :comments"), @NamedQuery(name = "TkuActions.findByModifiedUserId", query = "SELECT t FROM TkuActions t WHERE t.modifiedUserId = :modifiedUserId")})
public class TkuActions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "TKACT_IDENTIFIER")
    private Integer tkactIdentifier;
    
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @JoinColumn(name = "ACTION_CODE", referencedColumnName = "ACTION_CODE")
    @ManyToOne(optional = false)
    private Actions actionCode;
    @JoinColumn(name = "PP_END_DATE", referencedColumnName = "PP_END_DATE")
    @ManyToOne(optional = false)
    private PayPeriods ppEndDate;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT"), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY"), @JoinColumn(name = "TKU", referencedColumnName = "TKU")})
    @ManyToOne(optional = false)
    private Tkus tkus;

    public TkuActions() {
    }

    public TkuActions(Integer tkactIdentifier) {
        this.tkactIdentifier = tkactIdentifier;
    }

    public TkuActions(Integer tkactIdentifier, Date modifiedDate) {
        this.tkactIdentifier = tkactIdentifier;
        this.modifiedDate = modifiedDate;
    }

    public Integer getTkactIdentifier() {
        return tkactIdentifier;
    }

    public void setTkactIdentifier(Integer tkactIdentifier) {
        this.tkactIdentifier = tkactIdentifier;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
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

    public Actions getActionCode() {
        return actionCode;
    }

    public void setActionCode(Actions actionCode) {
        this.actionCode = actionCode;
    }

    public PayPeriods getPpEndDate() {
        return ppEndDate;
    }

    public void setPpEndDate(PayPeriods ppEndDate) {
        this.ppEndDate = ppEndDate;
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
        hash += (tkactIdentifier != null ? tkactIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TkuActions)) {
            return false;
        }
        TkuActions other = (TkuActions) object;
        if ((this.tkactIdentifier == null && other.tkactIdentifier != null) || (this.tkactIdentifier != null && !this.tkactIdentifier.equals(other.tkactIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.TkuActions[tkactIdentifier=" + tkactIdentifier + "]";
    }

}
