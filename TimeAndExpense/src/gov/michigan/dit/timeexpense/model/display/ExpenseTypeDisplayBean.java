package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

/**
 * Class to capture display related properties for expense types.
 * 
 * @author chaudharym
 */
public class ExpenseTypeDisplayBean implements Serializable {

	private static final long serialVersionUID = -6218614616975115391L;

	private String expTypeCode;
    
    private String description;
    
    private short displayOrder;
    
    private String mileageIndicator;
    
    private String mealIndicator;

	public String getExpTypeCode() {
		return expTypeCode;
	}

	public void setExpTypeCode(String expTypeCode) {
		this.expTypeCode = expTypeCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(short displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getMileageIndicator() {
		return mileageIndicator;
	}

	public void setMileageIndicator(String mileageIndicator) {
		this.mileageIndicator = mileageIndicator;
	}
	
	public boolean isMileageRelated(){
		return "Y".equalsIgnoreCase(mileageIndicator);
	}

	public String getMealIndicator() {
		return mealIndicator;
	}

	public void setMealIndicator(String mealIndicator) {
		this.mealIndicator = mealIndicator;
	}
	
	public boolean isMealRelated(){
		return "Y".equalsIgnoreCase(mealIndicator);
	}	
}
