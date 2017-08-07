package gov.michigan.dit.timeexpense.model.display;

import gov.michigan.dit.timeexpense.model.core.AgencyCommonMileages;
import gov.michigan.dit.timeexpense.model.core.CommonMileages;
import gov.michigan.dit.timeexpense.model.core.ExpenseLocations;
import gov.michigan.dit.timeexpense.model.core.States;
import gov.michigan.dit.timeexpense.model.core.VExpenseLocations;

import java.util.ArrayList;
import java.util.List;

public class DisplayDeptAgyLoc {

	String department;
	String agency;
	String agencyName;
	List<ExpenseLocations> agyLocations;
	List<VExpenseLocations> stateLocations;
	// List
	List<States> statesUs;
	String newLocation;
	boolean writeAccess;

	List<CommonMileages> commMiles;
	List<AgencyCommonMileages> agyCommMiles;
	String[] agyLocationsDisplay;
	String distanceCityMile;
	boolean stateAccessUpd;

	List<String> arrSt;

	/**
	 * Setter agyLocationsDisplay
	 * 
	 * @param agyLocationsDisplay
	 */
	public void setAgyLocationsDisplay(String[] agyLocationsDisplay) {
		this.agyLocationsDisplay = agyLocationsDisplay;
	}

	public String[] getAgyLocationsDisplay() {
		return agyLocationsDisplay;
	}

	/**getter writeAccess
	 * @return writeAccess True/false
	 */
	public boolean getWriteAccess() {
		return writeAccess;
	}

	/**Setter for writeAccess
	 * @param writeAccess
	 */
	public void setWriteAccess(boolean writeAccess) {
		this.writeAccess = writeAccess;
	}
/**
 * Setter newLocation
 * @param newLocation
 */
	public void setNewLocation(String newLocation) {
		this.newLocation = newLocation;
	}
/**
 * getter newLocation
 * @return newLocation
 */
	public String getNewLocation() {
		return newLocation;
	}
/**
 * Get Us states
 * @param statesUs
 */
	public void setStatesUs(List<States> statesUs) {
		this.statesUs = statesUs;
	}
/**
 * Set US states
 * @return statesUs
 */
	public List<States> getStatesUs() {
		return statesUs;
	}
/**
 *  Setter for agyLocations
 * @param agyLocations
 */
	public void setAgyLocations(List<ExpenseLocations> agyLocations) {
		this.agyLocations = agyLocations;
	}

	/**
	 * public String[] getAgyLocations() { return agyLocations; }
	 */
	public List<ExpenseLocations> getAgyLocations() {
		return agyLocations;
	}
/**
 * Setter for department
 * @param department
 */
	public void setDepartment(String department) {
		this.department = department;
	}
/**
 * Getter for department
 * @return department
 */
	public String getDepartment() {
		return department;
	}
/**
 * Setter for agency
 * @param agency
 */
	public void setAgency(String agency) {
		this.agency = agency;
	}
/**
 * Getter for agency
 * @return agency
 */
	public String getAgency() {
		return agency;
	}
/**
 * Getter for arrSt
 * @return arrSt
 */
	public List<String> getArrSt() {
		return arrSt;
	}
/**
 * Setter for arrSt
 * @param arrSt
 */
	public void setArrSt(List<String> arrSt) {
		this.arrSt = arrSt;
	}
/**
 * getter for agencyName
 * @return agencyName
 */
	public String getAgencyName() {
		return agencyName;
	}
/**
 * Setter for agencyName
 * @param agencyName
 */
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
/**
 * Setter for commMiles
 * @return commMiles
 */
	public List<CommonMileages> getCommMiles() {
		return commMiles;
	}
/**
 * Setter for commMiles
 * @param commMiles
 */
	public void setCommMiles(List<CommonMileages> commMiles) {
		this.commMiles = commMiles;
	}
/**
 * getter for agyCommMiles
 * @return agyCommMiles
 */
	public List<AgencyCommonMileages> getAgyCommMiles() {
		return agyCommMiles;
	}
/**
 * Setter for agyCommMiles
 * @param agyCommMiles
 */
	public void setAgyCommMiles(List<AgencyCommonMileages> agyCommMiles) {
		this.agyCommMiles = agyCommMiles;
	}
/**
 * getter for distanceCityMile
 * @return distanceCityMile
 */
	public String getDistanceCityMile() {
		return distanceCityMile;
	}
/**
 * Setter for distanceCityMile
 * @param distanceCityMile
 */
	public void setDistanceCityMile(String distanceCityMile) {
		this.distanceCityMile = distanceCityMile;
	}

public List<VExpenseLocations> getStateLocations() {
	return stateLocations;
}

public void setStateLocations(List<VExpenseLocations> stateLocations) {
	this.stateLocations = stateLocations;
}

public boolean isStateAccessUpd() {
	return stateAccessUpd;
}

public void setStateAccessUpd(boolean stateAccessUpd) {
	this.stateAccessUpd = stateAccessUpd;
}

}
