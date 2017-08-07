package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "EXPENSE_TYPE_RULES")

public class ExpenseTypeRules implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "EXTPR_IDENTIFIER")
    private Integer extprIdentifier;
    
    @Column(name = "VALUE")
    private String value;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    
    @JoinColumn(name = "EXP_TYPE_CODE", referencedColumnName = "EXP_TYPE_CODE")
    @ManyToOne(optional = false)
    private ExpenseTypes expTypeCode;
    
    //@JoinColumn(name = "RULE_IDENTIFIER", referencedColumnName = "RULE_IDENTIFIER")
   // @ManyToOne(optional = false)
    
    @Column(name = "RULE_IDENTIFIER")
    private Integer ruleIdentifier;

    public ExpenseTypeRules() {
    }

    public ExpenseTypeRules(Integer extprIdentifier) {
        this.extprIdentifier = extprIdentifier;
    }

    public ExpenseTypeRules(Integer extprIdentifier, String value) {
        this.extprIdentifier = extprIdentifier;
        this.value = value;
    }

    public Integer getExtprIdentifier() {
        return extprIdentifier;
    }

    public void setExtprIdentifier(Integer extprIdentifier) {
        this.extprIdentifier = extprIdentifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public ExpenseTypes getExpTypeCode() {
        return expTypeCode;
    }

    public void setExpTypeCode(ExpenseTypes expTypeCode) {
        this.expTypeCode = expTypeCode;
    }

    public Integer getRuleIdentifier() {
        return ruleIdentifier;
    }

    public void setRuleIdentifier(Integer ruleIdentifier) {
        this.ruleIdentifier = ruleIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (extprIdentifier != null ? extprIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExpenseTypeRules)) {
            return false;
        }
        ExpenseTypeRules other = (ExpenseTypeRules) object;
        if ((this.extprIdentifier == null && other.extprIdentifier != null) || (this.extprIdentifier != null && !this.extprIdentifier.equals(other.extprIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.ExpenseTypeRules[extprIdentifier=" + extprIdentifier + "]";
    }

}
