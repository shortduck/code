package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

public class ExpenseProfiles implements Serializable {

	private static final long serialVersionUID = -4725998144031184232L;

	private String ruleName;
    private String ruleValue;
    
    public ExpenseProfiles() {
	
    }

    /**
     * @param ruleName
     * @param ruleValue
     */
    public ExpenseProfiles(String ruleName, String ruleValue) {
	super();
	this.ruleName = ruleName;
	this.ruleValue = ruleValue;
    }

    /**
     * @return ruleName
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * @param ruleName
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * @return ruleValue
     */
    public String getRuleValue() {
        return ruleValue;
    }

    /**
     * @param ruleValue
     */
    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    
}
