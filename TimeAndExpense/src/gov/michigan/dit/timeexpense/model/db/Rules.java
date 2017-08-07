/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import gov.michigan.dit.timeexpense.model.core.ExpenseProfileRules;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypeRules;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "RULES")
@NamedQueries({@NamedQuery(name = "Rules.findAll", query = "SELECT r FROM Rules r"), @NamedQuery(name = "Rules.findByRuleIdentifier", query = "SELECT r FROM Rules r WHERE r.ruleIdentifier = :ruleIdentifier"), @NamedQuery(name = "Rules.findByRuleType", query = "SELECT r FROM Rules r WHERE r.ruleType = :ruleType"), @NamedQuery(name = "Rules.findByDescription", query = "SELECT r FROM Rules r WHERE r.description = :description"), @NamedQuery(name = "Rules.findByModifiedUserId", query = "SELECT r FROM Rules r WHERE r.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "Rules.findByModifiedDate", query = "SELECT r FROM Rules r WHERE r.modifiedDate = :modifiedDate")})
public class Rules implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "RULE_IDENTIFIER")
    private Integer ruleIdentifier;
    
    @Column(name = "RULE_TYPE")
    private String ruleType;
    
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ruleIdentifier")
    private List<ExpenseTypeRules> expenseTypeRulesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ruleIdentifier")
    private List<ExpenseProfileRules> expenseProfileRulesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ruleIdentifier")
    private List<RuleValues> ruleValuesList;

    public Rules() {
    }

    public Rules(Integer ruleIdentifier) {
        this.ruleIdentifier = ruleIdentifier;
    }

    public Rules(Integer ruleIdentifier, String ruleType, String description) {
        this.ruleIdentifier = ruleIdentifier;
        this.ruleType = ruleType;
        this.description = description;
    }

    public Integer getRuleIdentifier() {
        return ruleIdentifier;
    }

    public void setRuleIdentifier(Integer ruleIdentifier) {
        this.ruleIdentifier = ruleIdentifier;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<ExpenseTypeRules> getExpenseTypeRulesList() {
        return expenseTypeRulesList;
    }

    public void setExpenseTypeRulesList(List<ExpenseTypeRules> expenseTypeRulesList) {
        this.expenseTypeRulesList = expenseTypeRulesList;
    }

    public List<ExpenseProfileRules> getExpenseProfileRulesList() {
        return expenseProfileRulesList;
    }

    public void setExpenseProfileRulesList(List<ExpenseProfileRules> expenseProfileRulesList) {
        this.expenseProfileRulesList = expenseProfileRulesList;
    }

    public List<RuleValues> getRuleValuesList() {
        return ruleValuesList;
    }

    public void setRuleValuesList(List<RuleValues> ruleValuesList) {
        this.ruleValuesList = ruleValuesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruleIdentifier != null ? ruleIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rules)) {
            return false;
        }
        Rules other = (Rules) object;
        if ((this.ruleIdentifier == null && other.ruleIdentifier != null) || (this.ruleIdentifier != null && !this.ruleIdentifier.equals(other.ruleIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Rules[ruleIdentifier=" + ruleIdentifier + "]";
    }

}
