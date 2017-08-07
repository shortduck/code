package gov.michigan.dit.timeexpense.model.core;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PERSONNEL_HISTORIES")
public class PersonnelHistories {
	
	@Id
	@Column(name="PRHI_IDENTIFIER")
	private Integer prhiIdentifier;
	
	@Column(name="EMP_IDENTIFIER")
	private int empIdentifier;
	
	@Column(name="START_DATE")
	private Date startDate;
	
	@Column(name="END_DATE")
	private Date endDate;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="MIDDLE_NAME")
	private String middleName;
	
	@Column(name="SUFFIX")
	private String suffix;
	
	@Column(name="FMLA_EXPIRATION_DATE")
	private Date fmlaExpirationDate;
	
	@Column(name="BIRTHDATE")
	private Date birthdate;
	
	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;
	
	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;
	
	@Column(name="ADDRESS_1")
	private String address1;
	
	@Column(name="ADDRESS_2")
	private String address2;
	
	@Column(name="ADDRESS_3")
	private String address3;
	
	@Column(name="ADDRESS_4")
	private String address4;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="STATE")
	private String state;
	
	@Column(name="ZIP")
	private String zip;
	
	@Column(name="COUNTRY")
	private String country;
	
	public PersonnelHistories() {
	}

	public Integer getPrhiIdentifier() {
		return prhiIdentifier;
	}

	public void setPrhiIdentifier(Integer prhiIdentifier) {
		this.prhiIdentifier = prhiIdentifier;
	}

	public int getEmpIdentifier() {
		return empIdentifier;
	}

	public void setEmpIdentifier(int empIdentifier) {
		this.empIdentifier = empIdentifier;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Date getFmlaExpirationDate() {
		return fmlaExpirationDate;
	}

	public void setFmlaExpirationDate(Date fmlaExpirationDate) {
		this.fmlaExpirationDate = fmlaExpirationDate;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
