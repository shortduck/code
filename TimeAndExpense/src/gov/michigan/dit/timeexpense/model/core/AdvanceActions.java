/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "ADVANCE_ACTIONS")
@NamedQueries({@NamedQuery(name = "AdvanceActions.findAll", query = "SELECT a FROM AdvanceActions a"), @NamedQuery(name = "AdvanceActions.findByAdvaIdentifier", query = "SELECT a FROM AdvanceActions a WHERE a.advaIdentifier = :advaIdentifier"), @NamedQuery(name = "AdvanceActions.findByNextActionCode", query = "SELECT a FROM AdvanceActions a WHERE a.nextActionCode = :nextActionCode"), @NamedQuery(name = "AdvanceActions.findByComments", query = "SELECT a FROM AdvanceActions a WHERE a.comments = :comments"), @NamedQuery(name = "AdvanceActions.findByModifiedDate", query = "SELECT a FROM AdvanceActions a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "AdvanceActions.findByModifiedUserId", query = "SELECT a FROM AdvanceActions a WHERE a.modifiedUserId = :modifiedUserId")})
public class AdvanceActions implements Serializable {
    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name = "ADVANCE_ACTIONS_GENERATOR", sequenceName = "ADVA_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADVANCE_ACTIONS_GENERATOR")
    
    @Column(name = "ADVA_IDENTIFIER")
    private Integer advaIdentifier;
    @Column(name = "NEXT_ACTION_CODE")
    private String nextActionCode;
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    /*@JoinColumn(name = "ACTION_CODE", referencedColumnName = "ACTION_CODE")
    @ManyToOne(optional = false)*/
    @Column(name = "ACTION_CODE")
    private String actionCode;
    @JoinColumn(name = "ADVM_IDENTIFIER", referencedColumnName = "ADVM_IDENTIFIER")
    @ManyToOne(optional = false)
    private AdvanceMasters advmIdentifier;
    @Version
	@Column(name = "VERSION")
	private Integer version;
    

    public AdvanceActions() {
    }

    public AdvanceActions(Integer advaIdentifier) {
        this.advaIdentifier = advaIdentifier;
    }

    public Integer getAdvaIdentifier() {
        return advaIdentifier;
    }

    public void setAdvaIdentifier(Integer advaIdentifier) {
        this.advaIdentifier = advaIdentifier;
    }

    public String getNextActionCode() {
        return nextActionCode;
    }

    public void setNextActionCode(String nextActionCode) {
        this.nextActionCode = nextActionCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public AdvanceMasters getAdvmIdentifier() {
        return advmIdentifier;
    }

    public void setAdvmIdentifier(AdvanceMasters advmIdentifier) {
        this.advmIdentifier = advmIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (advaIdentifier != null ? advaIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvanceActions)) {
            return false;
        }
        AdvanceActions other = (AdvanceActions) object;
        if ((this.advaIdentifier == null && other.advaIdentifier != null) || (this.advaIdentifier != null && !this.advaIdentifier.equals(other.advaIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AdvanceActions[advaIdentifier=" + advaIdentifier + "]";
    }

}
