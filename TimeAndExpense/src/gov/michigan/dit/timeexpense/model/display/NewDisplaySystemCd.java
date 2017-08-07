package gov.michigan.dit.timeexpense.model.display;
/**
 * This class is used to display the System Code attributes 
 * There are additional attributes used for security access and date parameters
 * @author kaws1
 */
import gov.michigan.dit.timeexpense.model.core.SystemCodes;

import java.util.Date;
import java.util.List;

public class NewDisplaySystemCd {

	private String systemCode;
	private Date startDate;

	private Date endDate;
	private String description;
	private String value;
	private String modifiedUserId;
	private Date modifiedDate;
	private String edit;
	private String dateCurrent;
	private boolean writeAccess;

	// Added by Smriti

	private List<SystemCodes> systemCodes;

	/**
	 * @return List<SystemCodes>
	 */
	public List<SystemCodes> getSystemCodes() {
		return systemCodes;
	}

	/**
	 * @param systemCodes
	 */
	public void setSystemCodes(List<SystemCodes> systemCodes) {
		this.systemCodes = systemCodes;
	}

	/**
	 * @return writeAccess True/false
	 */
	public boolean getWriteAccess() {
		return writeAccess;
	}

	/**
	 * @param writeAccess
	 */
	public void setWriteAccess(boolean writeAccess) {
		this.writeAccess = writeAccess;
	}

	/**
	 * @return String dateCurrent
	 */
	public String getDateCurrent() {
		return dateCurrent;
	}

	/**
	 * @param dateCurrent
	 */
	public void setDateCurrent(String dateCurrent) {
		this.dateCurrent = dateCurrent;
	}

	/**
	 * @return String edit
	 */
	public String getEdit() {
		return edit;
	}

	/**
	 * @param edit
	 */
	public void setEdit(String edit) {
		this.edit = edit;
	}

	/**
	 * @return systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @param systemCode
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return modifiedUserId
	 */
	public String getModifiedUserId() {
		return modifiedUserId;
	}

	/**
	 * @param modifiedUserId
	 */
	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	/**
	 * @return modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * New method added to get Json string for system Codes
	 * This method packages the code and description of the System Code
	 * for the drop down Select Box
	 * @return String buff
	 */
	public String getSystemCodesJsonWithoutArray() {
		StringBuilder buff = new StringBuilder();

		if (systemCodes == null) {
			return "";
		}

		for (SystemCodes sysCode : systemCodes) {
			buff.append("{code:\"");
			buff.append(sysCode.getSystemCodesPK().getSystemCode());
			buff.append("\",display:\"");
			if (sysCode.getDescription().length() > 40) {
				buff.append(sysCode.getDescription().substring(0, 40));
			} else {
				buff.append(sysCode.getDescription());
			}
			buff.append("\"},");
		}

		removeLastCommaCharacter(buff);
		return buff.toString();
	}

	/**
	 * Req #13 Method to display the drop down list for System Codes
	 * 
	 * @return String buff
	 */
	public String getSystemCodeMinusAllScopeJsonWithoutArray() {
		StringBuilder buff = new StringBuilder();

		if (systemCodes == null) {
			return "";
		}
		int count = 0;
		for (SystemCodes syscode : systemCodes) {
			if ("ALL".equalsIgnoreCase(syscode.getSystemCodesPK()
					.getSystemCode())) {
				continue;
			}
			count++;
			buff.append("{code:\"");
			buff.append(syscode.getSystemCodesPK().getSystemCode());
			buff.append("\",display:\"");
			buff.append(syscode.getSystemCodesPK().getSystemCode());
			buff.append(" ");
			if (syscode.getDescription().length() > 40) {
				buff.append(syscode.getDescription().substring(0, 40));
			} else {
				buff.append(syscode.getDescription());
			}
			buff.append("\"},");

		}

		removeLastCommaCharacter(buff);
		return buff.toString();

	}

	/**
	 * The display of the Drop down with empty elements in the String
	 * @return buff
	 */
	public String getCombinedSystemCodeNameWithEmptyElementWithoutAllScopeJson() {
		StringBuilder buff = new StringBuilder();

		buff.append("[");
		buff.append(getEmptyElementJson());
		buff.append(",");
		buff.append(getSystemCodeMinusAllScopeJsonWithoutArray());
		// remove trailing comma if no depts present!
		removeLastCommaCharacter(buff);
		buff.append("]");

		return buff.toString();
	}

	/**
	 * Get System Code List 
	 * @return String
	 */
	public String getFirstSystemCodeDisplayValue() {
		if (systemCodes == null || systemCodes.size() < 1)
			return "";

		return systemCodes.get(0).getSystemCodesPK().getSystemCode() + " "
				+ systemCodes.get(0).getDescription();
	}

	/**
	 * This method removes the comma from the last entry of the 
	 * Json string
	 * @param buff
	 */
	private void removeLastCommaCharacter(StringBuilder buff) {
		if (buff.length() > 0 && ',' == buff.charAt(buff.length() - 1))
			buff.deleteCharAt(buff.length() - 1);
	}

	/**
	 * If the list os empty then the empty String with code 
	 * and display attributes is selected
	 * @return String
	 */
	private String getEmptyElementJson() {
		return "{code:\"\",display:\"\"}";
	}

}
