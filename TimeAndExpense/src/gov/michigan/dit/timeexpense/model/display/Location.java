package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

/**
 * @author chiduras
 *
 */
public class Location implements Serializable {
   
	private static final long serialVersionUID = -5375954872689331793L;

	private String locationCode;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;
	private String county;
	
	
	public Location (){
	    
	}
	
	/**
	 * @param addressLine1
	 * @param addressLine2
	 * @param city
	 * @param county
	 * @param locationCode
	 * @param state
	 * @param zip
	 */
	public Location(String addressLine1, String addressLine2, String city,
		String county, String locationCode, String state, String zip) {
	    super();
	    this.addressLine1 = addressLine1;
	    this.addressLine2 = addressLine2;
	    this.city = city;
	    this.county = county;
	    this.locationCode = locationCode;
	    this.state = state;
	    this.zip = zip;
	}
	
	
	/**
	 * @return locationCode
	 */
	public String getLocationCode() {
	    return locationCode;
	}
	/**
	 * @param locationCode
	 */
	public void setLocationCode(String locationCode) {
	    this.locationCode = locationCode;
	}
	/**
	 * @return addressLine1
	 */
	public String getAddressLine1() {
	    return addressLine1;
	}
	/**
	 * @param addressLine1
	 */
	public void setAddressLine1(String addressLine1) {
	    this.addressLine1 = addressLine1;
	}
	/**
	 * @return addressLine2
	 */
	public String getAddressLine2() {
	    return addressLine2;
	}
	/**
	 * @param addressLine2
	 */
	public void setAddressLine2(String addressLine2) {
	    this.addressLine2 = addressLine2;
	}
	/**
	 * @return city
	 */
	public String getCity() {
	    return city;
	}
	/**
	 * @param city
	 */
	public void setCity(String city) {
	    this.city = city;
	}
	/**
	 * @return state
	 */
	public String getState() {
	    return state;
	}
	/**
	 * @param 
	 * sets the state
	 */
	public void setState(String state) {
	    this.state = state;
	}
	/**
	 * @return zip
	 */
	public String getZip() {
	    return zip;
	}
	/**
	 * @param 
	 * Set the zip
	 */
	public void setZip(String zip) {
	    this.zip = zip;
	}
	/**
	 * @return the county
	 */
	public String getCounty() {
	    return county;
	}
	
	
	/**
	 * @param
	 * Set the county
	 */
	public void setCounty(String county) {
	    this.county = county;
	}    
}
