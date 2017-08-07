/**
 * This class defines the behavior of the User Interface such as enable or disable buttons etc. In addition,
 * this class will also hold derived properties needed for display purposes. the only methods in this class
 * are getters and setters.
 * 
 * ZH - 02/18/2009
 */

package gov.michigan.dit.timeexpense.model.display;

import gov.michigan.dit.timeexpense.model.core.TravelReqEvents;

import java.io.Serializable;

public class DisplayAdvance implements Serializable {
	
	private static final long serialVersionUID = -3193928606218562873L;

	private Integer advmIdentifier;
	private int revisionNumber;
	private TravelReqEvents relatedTravelRequisition;
	
	private String requestDate = null;
	private double dollarAmount;
	private double liquidatedByExpense = 0d;
	private double liquidatedByDeposit = 0d;
	private double amountOutstanding = 0d;
	private String dollarAmountForEditing = "";
	private String fromDate = null;
	private String toDate = null;
	
	private String advanceReason = "";
	private String permanentAdvInd = "";
	private String manualWarrantIssdInd = "N";
	private String manualDepositDocNum = "";
	private String manualWarrantDocNum = "";
	private String manualDepositAmount = "";
	private String approverString = "";
	
	private boolean disablePreviousButton = true;
	private boolean disableNextButton = true;
	private boolean disableSaveButton = false;
	private boolean disableSubmitButton = false;
	private boolean disableModifyButton = true;
	private boolean disableApproveButton = true;
	private boolean disableApproveNextButton = true;
	private boolean disableApproveSkipButton = true;
	private boolean disableApproveWithCommentsButton = true;
	private boolean disableRejectButton = true;
	private boolean disableManualWarrantIssdInd = true;
	private String displaySaveButton = "inline";
	private String displaySubmitButton = "inline";
	private String displayApproveButton = "none";
	private String displayApproveWithCommentsButton = "none";
	private String displayApproveNextButton = "none";
	private String displayApproveSkipButton = "none";
	private String displayRejectButton = "none";
	private String moduleId = "";
	private String viewMode = "";

	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public double getDollarAmount() {
		return dollarAmount;
	}
	public void setDollarAmount(double dollarAmount) {
		this.dollarAmount = dollarAmount;
	}
	public double getLiquidatedByExpense() {
		return liquidatedByExpense;
	}
	public void setLiquidatedByExpense(double liquidatedByExpense) {
		this.liquidatedByExpense = liquidatedByExpense;
	}
	public double getLiquidatedByDeposit() {
		return liquidatedByDeposit;
	}
	public void setLiquidatedByDeposit(double liquidatedByDeposit) {
		this.liquidatedByDeposit = liquidatedByDeposit;
	}

	public String getAdvanceReason() {
		return advanceReason;
	}
	public void setAdvanceReason(String advanceReason) {
		this.advanceReason = advanceReason;
	}
	public String getPermanentAdvInd() {
		return permanentAdvInd;
	}
	public void setPermanentAdvInd(String permanentAdvInd) {
		this.permanentAdvInd = permanentAdvInd;
	}
	public String getManualWarrantIssdInd() {
		return manualWarrantIssdInd;
	}
	public void setManualWarrantIssdInd(String manualWarrantIssdInd) {
		this.manualWarrantIssdInd = manualWarrantIssdInd;
	}
	public String getManualDepositDocNum() {
		return manualDepositDocNum;
	}
	public void setManualDepositDocNum(String manualDepositDocNum) {
		this.manualDepositDocNum = manualDepositDocNum;
	}
	public String getManualWarrantDocNum() {
		return manualWarrantDocNum;
	}
	public void setManualWarrantDocNum(String manualWarrantDocNum) {
		this.manualWarrantDocNum = manualWarrantDocNum;
	}
	public String getManualDepositAmount() {
		return manualDepositAmount;
	}
	public void setManualDepositAmount(String manualDepositAmount) {
		this.manualDepositAmount = manualDepositAmount;
	}

	public boolean isDisablePreviousButton() {
		return disablePreviousButton;
	}
	public void setDisablePreviousButton(boolean disablePreviousButton) {
		this.disablePreviousButton = disablePreviousButton;
	}
	public boolean isDisableNextButton() {
		return disableNextButton;
	}
	public void setDisableNextButton(boolean disableNextButton) {
		this.disableNextButton = disableNextButton;
	}
	public boolean isDisableSaveButton() {
		return disableSaveButton;
	}
	public void setDisableSaveButton(boolean disableSaveButton) {
		this.disableSaveButton = disableSaveButton;
	}
	public boolean isDisableModifyButton() {
		return disableModifyButton;
	}
	public void setDisableModifyButton(boolean disableModifyButton) {
		this.disableModifyButton = disableModifyButton;
	}
	public boolean isDisableSubmitButton() {
		return disableSubmitButton;
	}
	public void setDisableSubmitButton(boolean disableSubmitButton) {
		this.disableSubmitButton = disableSubmitButton;
	}
	public boolean isDisableApproveButton() {
		return disableApproveButton;
	}
	public void setDisableApproveButton(boolean disableApproveButton) {
		this.disableApproveButton = disableApproveButton;
	}
	public void setAdvmIdentifier(Integer advmIdentifier) {
		this.advmIdentifier = advmIdentifier;
	}
	public Integer getAdvmIdentifier() {
		return advmIdentifier;
	}
	public void setRevisionNumber(int revisionNumber) {
		this.revisionNumber = revisionNumber;
	}
	public int getRevisionNumber() {
		return revisionNumber;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setAmountOutstanding(double amountOutstanding) {
		this.amountOutstanding = amountOutstanding;
	}
	public double getAmountOutstanding() {
		return amountOutstanding;
	}
	
	public String getDisplayApproveButton() {
		return displayApproveButton;
	}
	public void setDisplayApproveButton(String displayApproveButton) {
		this.displayApproveButton = displayApproveButton;
	}
	public String getDisplayApproveWithCommentsButton() {
		return displayApproveWithCommentsButton;
	}
	public void setDisplayApproveWithCommentsButton(String displayApproveWithCommentsButton){
		this.displayApproveWithCommentsButton = displayApproveWithCommentsButton;
	}
	public String getDisplayRejectButton() {
		return displayRejectButton;
	}
	public void setDisplayRejectButton(String displayRejectButton) {
		this.displayRejectButton = displayRejectButton;
	}
	public void setDisplaySaveButton(String displaySaveButton) {
		this.displaySaveButton = displaySaveButton;
	}
	public String getDisplaySaveButton() {
		return displaySaveButton;
	}
	public void setDisplaySubmitButton(String displaySubmitButton) {
		this.displaySubmitButton = displaySubmitButton;
	}
	public String getDisplaySubmitButton() {
		return displaySubmitButton;
	}
	public void setApproverString(String approverString) {
		this.approverString = approverString;
	}
	public String getApproverString() {
		return approverString;
	}
	public void setDisableManualWarrantIssdInd(boolean disableManualWarrantIssdInd) {
		this.disableManualWarrantIssdInd = disableManualWarrantIssdInd;
	}
	public boolean isDisableManualWarrantIssdInd() {
		return disableManualWarrantIssdInd;
	}
	public void setDisableApproveWithCommentsButton(
			boolean disableApproveWithCommentsButton) {
		this.disableApproveWithCommentsButton = disableApproveWithCommentsButton;
	}
	public boolean isDisableApproveWithCommentsButton() {
		return disableApproveWithCommentsButton;
	}
	public void setDisableRejectButton(boolean disableRejectButton) {
		this.disableRejectButton = disableRejectButton;
	}
	public boolean isDisableRejectButton() {
		return disableRejectButton;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}
	public String getViewMode() {
		return viewMode;
	}
	public TravelReqEvents getRelatedTravelRequisition() {
		return relatedTravelRequisition;
	}
	public void setRelatedTravelRequisition(TravelReqEvents relatedTravelRequisition) {
		this.relatedTravelRequisition = relatedTravelRequisition;
	}
	public void setDollarAmountForEditing(String dollarAmountForEditing) {
		this.dollarAmountForEditing = dollarAmountForEditing;
	}
	public String getDollarAmountForEditing() {
		return dollarAmountForEditing;
	}
	public boolean isDisableApproveNextButton() {
		return disableApproveNextButton;
	}
	public void setDisableApproveNextButton(boolean disableApproveNextButton) {
		this.disableApproveNextButton = disableApproveNextButton;
	}
	public boolean isDisableApproveSkipButton() {
		return disableApproveSkipButton;
	}
	public void setDisableApproveSkipButton(boolean disableApproveSkipButton) {
		this.disableApproveSkipButton = disableApproveSkipButton;
	}
	public String getDisplayApproveNextButton() {
		return displayApproveNextButton;
	}
	public void setDisplayApproveNextButton(String displayApproveNextButton) {
		this.displayApproveNextButton = displayApproveNextButton;
	}
	public String getDisplayApproveSkipButton() {
		return displayApproveSkipButton;
	}
	public void setDisplayApproveSkipButton(String displayApproveSkipButton) {
		this.displayApproveSkipButton = displayApproveSkipButton;
	}	
}
