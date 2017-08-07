/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "NON_EMP")
@NamedQueries({@NamedQuery(name = "NonEmp.findAll", query = "SELECT n FROM NonEmp n"), @NamedQuery(name = "NonEmp.findByNempIdentifier", query = "SELECT n FROM NonEmp n WHERE n.nempIdentifier = :nempIdentifier"), @NamedQuery(name = "NonEmp.findBySsn", query = "SELECT n FROM NonEmp n WHERE n.ssn = :ssn"), @NamedQuery(name = "NonEmp.findByOrganizationNumber", query = "SELECT n FROM NonEmp n WHERE n.organizationNumber = :organizationNumber"), @NamedQuery(name = "NonEmp.findByLastName", query = "SELECT n FROM NonEmp n WHERE n.lastName = :lastName"), @NamedQuery(name = "NonEmp.findByFirstName", query = "SELECT n FROM NonEmp n WHERE n.firstName = :firstName"), @NamedQuery(name = "NonEmp.findByMiddleName", query = "SELECT n FROM NonEmp n WHERE n.middleName = :middleName"), @NamedQuery(name = "NonEmp.findBySuffix", query = "SELECT n FROM NonEmp n WHERE n.suffix = :suffix"), @NamedQuery(name = "NonEmp.findByPcel", query = "SELECT n FROM NonEmp n WHERE n.pcel = :pcel"), @NamedQuery(name = "NonEmp.findByFlsaCode", query = "SELECT n FROM NonEmp n WHERE n.flsaCode = :flsaCode"), @NamedQuery(name = "NonEmp.findByClassType", query = "SELECT n FROM NonEmp n WHERE n.classType = :classType"), @NamedQuery(name = "NonEmp.findByWorkStatusCode", query = "SELECT n FROM NonEmp n WHERE n.workStatusCode = :workStatusCode"), @NamedQuery(name = "NonEmp.findByStartDate", query = "SELECT n FROM NonEmp n WHERE n.startDate = :startDate"), @NamedQuery(name = "NonEmp.findByEndDate", query = "SELECT n FROM NonEmp n WHERE n.endDate = :endDate"), @NamedQuery(name = "NonEmp.findByHourlyRate", query = "SELECT n FROM NonEmp n WHERE n.hourlyRate = :hourlyRate"), @NamedQuery(name = "NonEmp.findByFirmNumber", query = "SELECT n FROM NonEmp n WHERE n.firmNumber = :firmNumber"), @NamedQuery(name = "NonEmp.findByContractNumber", query = "SELECT n FROM NonEmp n WHERE n.contractNumber = :contractNumber"), @NamedQuery(name = "NonEmp.findByVendorNumber", query = "SELECT n FROM NonEmp n WHERE n.vendorNumber = :vendorNumber"), @NamedQuery(name = "NonEmp.findByModifiedUserId", query = "SELECT n FROM NonEmp n WHERE n.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "NonEmp.findByModifiedDate", query = "SELECT n FROM NonEmp n WHERE n.modifiedDate = :modifiedDate"), @NamedQuery(name = "NonEmp.findByDeptCode", query = "SELECT n FROM NonEmp n WHERE n.deptCode = :deptCode"), @NamedQuery(name = "NonEmp.findBySupervisor", query = "SELECT n FROM NonEmp n WHERE n.supervisor = :supervisor"), @NamedQuery(name = "NonEmp.findByLocation", query = "SELECT n FROM NonEmp n WHERE n.location = :location"), @NamedQuery(name = "NonEmp.findByExempt", query = "SELECT n FROM NonEmp n WHERE n.exempt = :exempt"), @NamedQuery(name = "NonEmp.findByHjbcdIdentifier", query = "SELECT n FROM NonEmp n WHERE n.hjbcdIdentifier = :hjbcdIdentifier"), @NamedQuery(name = "NonEmp.findByProcessLevel", query = "SELECT n FROM NonEmp n WHERE n.processLevel = :processLevel"), @NamedQuery(name = "NonEmp.findBySsnReal", query = "SELECT n FROM NonEmp n WHERE n.ssnReal = :ssnReal")})
public class NonEmp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "NEMP_IDENTIFIER")
    private Integer nempIdentifier;
    
    @Column(name = "SSN")
    private String ssn;
    @Column(name = "ORGANIZATION_NUMBER")
    private String organizationNumber;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    @Column(name = "SUFFIX")
    private String suffix;
    @Column(name = "PCEL")
    private String pcel;
    @Column(name = "FLSA_CODE")
    private String flsaCode;
    @Column(name = "CLASS_TYPE")
    private String classType;
    @Column(name = "WORK_STATUS_CODE")
    private String workStatusCode;
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "HOURLY_RATE")
    private BigDecimal hourlyRate;
    @Column(name = "FIRM_NUMBER")
    private String firmNumber;
    @Column(name = "CONTRACT_NUMBER")
    private String contractNumber;
    @Column(name = "VENDOR_NUMBER")
    private String vendorNumber;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "DEPT_CODE")
    private String deptCode;
    @Column(name = "SUPERVISOR")
    private String supervisor;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "EXEMPT")
    private String exempt;
    @Column(name = "HJBCD_IDENTIFIER")
    private Integer hjbcdIdentifier;
    @Column(name = "PROCESS_LEVEL")
    private String processLevel;
    @Column(name = "SSN_REAL")
    private String ssnReal;
    @OneToMany(mappedBy = "nempIdentifier")
    private Collection<UserIds> userIdsCollection;
    @OneToMany(mappedBy = "nempIdentifier")
    private Collection<Tas> tasCollection;
    @JoinColumn(name = "BARGAINING_UNIT", referencedColumnName = "BARGAINING_UNIT")
    @ManyToOne
    private BargainingUnits bargainingUnit;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT"), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY"), @JoinColumn(name = "TKU", referencedColumnName = "TKU")})
    @ManyToOne(optional = false)
    private Tkus tkus;
    @OneToMany(mappedBy = "nempIdentifier")
    private Collection<Approver> approverCollection;

    public NonEmp() {
    }

    public NonEmp(Integer nempIdentifier) {
        this.nempIdentifier = nempIdentifier;
    }

    public NonEmp(Integer nempIdentifier, String ssn) {
        this.nempIdentifier = nempIdentifier;
        this.ssn = ssn;
    }

    public Integer getNempIdentifier() {
        return nempIdentifier;
    }

    public void setNempIdentifier(Integer nempIdentifier) {
        this.nempIdentifier = nempIdentifier;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(String organizationNumber) {
        this.organizationNumber = organizationNumber;
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

    public String getPcel() {
        return pcel;
    }

    public void setPcel(String pcel) {
        this.pcel = pcel;
    }

    public String getFlsaCode() {
        return flsaCode;
    }

    public void setFlsaCode(String flsaCode) {
        this.flsaCode = flsaCode;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getWorkStatusCode() {
        return workStatusCode;
    }

    public void setWorkStatusCode(String workStatusCode) {
        this.workStatusCode = workStatusCode;
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

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getFirmNumber() {
        return firmNumber;
    }

    public void setFirmNumber(String firmNumber) {
        this.firmNumber = firmNumber;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getVendorNumber() {
        return vendorNumber;
    }

    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
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

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExempt() {
        return exempt;
    }

    public void setExempt(String exempt) {
        this.exempt = exempt;
    }

    public Integer getHjbcdIdentifier() {
        return hjbcdIdentifier;
    }

    public void setHjbcdIdentifier(Integer hjbcdIdentifier) {
        this.hjbcdIdentifier = hjbcdIdentifier;
    }

    public String getProcessLevel() {
        return processLevel;
    }

    public void setProcessLevel(String processLevel) {
        this.processLevel = processLevel;
    }

    public String getSsnReal() {
        return ssnReal;
    }

    public void setSsnReal(String ssnReal) {
        this.ssnReal = ssnReal;
    }

    public Collection<UserIds> getUserIdsCollection() {
        return userIdsCollection;
    }

    public void setUserIdsCollection(Collection<UserIds> userIdsCollection) {
        this.userIdsCollection = userIdsCollection;
    }

    public Collection<Tas> getTasCollection() {
        return tasCollection;
    }

    public void setTasCollection(Collection<Tas> tasCollection) {
        this.tasCollection = tasCollection;
    }

    public BargainingUnits getBargainingUnit() {
        return bargainingUnit;
    }

    public void setBargainingUnit(BargainingUnits bargainingUnit) {
        this.bargainingUnit = bargainingUnit;
    }

    public Tkus getTkus() {
        return tkus;
    }

    public void setTkus(Tkus tkus) {
        this.tkus = tkus;
    }

    public Collection<Approver> getApproverCollection() {
        return approverCollection;
    }

    public void setApproverCollection(Collection<Approver> approverCollection) {
        this.approverCollection = approverCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nempIdentifier != null ? nempIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NonEmp)) {
            return false;
        }
        NonEmp other = (NonEmp) object;
        if ((this.nempIdentifier == null && other.nempIdentifier != null) || (this.nempIdentifier != null && !this.nempIdentifier.equals(other.nempIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.NonEmp[nempIdentifier=" + nempIdentifier + "]";
    }

}
