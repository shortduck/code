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
@Table(name = "RULE_VALUES")
@NamedQueries({@NamedQuery(name = "RuleValues.findAll", query = "SELECT r FROM RuleValues r"), @NamedQuery(name = "RuleValues.findByRlvalIdentifier", query = "SELECT r FROM RuleValues r WHERE r.rlvalIdentifier = :rlvalIdentifier"), @NamedQuery(name = "RuleValues.findByValue", query = "SELECT r FROM RuleValues r WHERE r.value = :value"), @NamedQuery(name = "RuleValues.findByDefaultInd", query = "SELECT r FROM RuleValues r WHERE r.defaultInd = :defaultInd"), @NamedQuery(name = "RuleValues.findByModifiedUserId", query = "SELECT r FROM RuleValues r WHERE r.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "RuleValues.findByModifiedDate", query = "SELECT r FROM RuleValues r WHERE r.modifiedDate = :modifiedDate")})
public class RuleValues implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "RLVAL_IDENTIFIER")
    private Integer rlvalIdentifier;
    
    @Column(name = "VALUE")
    private String value;
    
    @Column(name = "DEFAULT_IND")
    private String defaultInd;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "RULE_IDENTIFIER", referencedColumnName = "RULE_IDENTIFIER")
    @ManyToOne(optional = false)
    private Rules ruleIdentifier;

    public RuleValues() {
    }

    public RuleValues(Integer rlvalIdentifier) {
        this.rlvalIdentifier = rlvalIdentifier;
    }

    public RuleValues(Integer rlvalIdentifier, String value, String defaultInd) {
        this.rlvalIdentifier = rlvalIdentifier;
        this.value = value;
        this.defaultInd = defaultInd;
    }

    public Integer getRlvalIdentifier() {
        return rlvalIdentifier;
    }

    public void setRlvalIdentifier(Integer rlvalIdentifier) {
        this.rlvalIdentifier = rlvalIdentifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefaultInd() {
        return defaultInd;
    }

    public void setDefaultInd(String defaultInd) {
        this.defaultInd = defaultInd;
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

    public Rules getRuleIdentifier() {
        return ruleIdentifier;
    }

    public void setRuleIdentifier(Rules ruleIdentifier) {
        this.ruleIdentifier = ruleIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rlvalIdentifier != null ? rlvalIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RuleValues)) {
            return false;
        }
        RuleValues other = (RuleValues) object;
        if ((this.rlvalIdentifier == null && other.rlvalIdentifier != null) || (this.rlvalIdentifier != null && !this.rlvalIdentifier.equals(other.rlvalIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.RuleValues[rlvalIdentifier=" + rlvalIdentifier + "]";
    }

}
