package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

public class ExpenseProfile implements Serializable {

	private static final long serialVersionUID = 1818789816069388542L;
	
	private String ruleName;
    private String ruleValue;
    private String yes = "Y";
    private String no = "N";
    public ExpenseProfile(){
	
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
	if (yes.equalsIgnoreCase(ruleValue.trim())){
	 ruleValue = "Yes";	     
	}
	else {
	    if (no.equalsIgnoreCase(ruleValue.trim())){
		 ruleValue = "No";	     
		}
	}	
	
        this.ruleValue = ruleValue;
    }

    
    
}
