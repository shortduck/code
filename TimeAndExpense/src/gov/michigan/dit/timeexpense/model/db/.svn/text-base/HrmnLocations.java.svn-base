/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "HRMN_LOCATIONS")
@NamedQueries({@NamedQuery(name = "HrmnLocations.findAll", query = "SELECT h FROM HrmnLocations h"), @NamedQuery(name = "HrmnLocations.findByLocation", query = "SELECT h FROM HrmnLocations h WHERE h.location = :location"), @NamedQuery(name = "HrmnLocations.findByStartDate", query = "SELECT h FROM HrmnLocations h WHERE h.startDate = :startDate"), @NamedQuery(name = "HrmnLocations.findByEndDate", query = "SELECT h FROM HrmnLocations h WHERE h.endDate = :endDate"), @NamedQuery(name = "HrmnLocations.findByDescription", query = "SELECT h FROM HrmnLocations h WHERE h.description = :description"), @NamedQuery(name = "HrmnLocations.findByStatus", query = "SELECT h FROM HrmnLocations h WHERE h.status = :status"), @NamedQuery(name = "HrmnLocations.findByAddress1", query = "SELECT h FROM HrmnLocations h WHERE h.address1 = :address1"), @NamedQuery(name = "HrmnLocations.findByAddress2", query = "SELECT h FROM HrmnLocations h WHERE h.address2 = :address2"), @NamedQuery(name = "HrmnLocations.findByAddress3", query = "SELECT h FROM HrmnLocations h WHERE h.address3 = :address3"), @NamedQuery(name = "HrmnLocations.findByAddress4", query = "SELECT h FROM HrmnLocations h WHERE h.address4 = :address4"), @NamedQuery(name = "HrmnLocations.findByCity", query = "SELECT h FROM HrmnLocations h WHERE h.city = :city"), @NamedQuery(name = "HrmnLocations.findByState", query = "SELECT h FROM HrmnLocations h WHERE h.state = :state"), @NamedQuery(name = "HrmnLocations.findByZip", query = "SELECT h FROM HrmnLocations h WHERE h.zip = :zip"), @NamedQuery(name = "HrmnLocations.findByCounty", query = "SELECT h FROM HrmnLocations h WHERE h.county = :county"), @NamedQuery(name = "HrmnLocations.findByCountryCode", query = "SELECT h FROM HrmnLocations h WHERE h.countryCode = :countryCode"), @NamedQuery(name = "HrmnLocations.findByModifiedUserId", query = "SELECT h FROM HrmnLocations h WHERE h.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "HrmnLocations.findByModifiedDate", query = "SELECT h FROM HrmnLocations h WHERE h.modifiedDate = :modifiedDate")})
public class HrmnLocations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "LOCATION")
    private String location;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "STATUS")
    private String status;
    @Column(name = "ADDRESS_1")
    private String address1;
    @Column(name = "ADDRESS_2")
    private String address2;
    @Column(name = "ADDRESS_3")
    private String address3;
    @Column(name = "ADDRESS_4")
    private String address4;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "ZIP")
    private String zip;
    @Column(name = "COUNTY")
    private String county;
    @Column(name = "COUNTRY_CODE")
    private String countryCode;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(mappedBy = "location")
    private Collection<AppointmentHistories> appointmentHistoriesCollection;

    public HrmnLocations() {
    }

    public HrmnLocations(String location) {
        this.location = location;
    }

    public HrmnLocations(String location, Date startDate, Date endDate, String description, String status) {
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public Collection<AppointmentHistories> getAppointmentHistoriesCollection() {
        return appointmentHistoriesCollection;
    }

    public void setAppointmentHistoriesCollection(Collection<AppointmentHistories> appointmentHistoriesCollection) {
        this.appointmentHistoriesCollection = appointmentHistoriesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (location != null ? location.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HrmnLocations)) {
            return false;
        }
        HrmnLocations other = (HrmnLocations) object;
        if ((this.location == null && other.location != null) || (this.location != null && !this.location.equals(other.location))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.HrmnLocations[location=" + location + "]";
    }

}
