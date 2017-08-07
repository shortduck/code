/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "AGENCY_PAY_TYPES")
@NamedQueries({@NamedQuery(name = "AgencyPayTypes.findAll", query = "SELECT a FROM AgencyPayTypes a"), @NamedQuery(name = "AgencyPayTypes.findByAgptIdentifier", query = "SELECT a FROM AgencyPayTypes a WHERE a.agptIdentifier = :agptIdentifier"), @NamedQuery(name = "AgencyPayTypes.findByStartDate", query = "SELECT a FROM AgencyPayTypes a WHERE a.startDate = :startDate"), @NamedQuery(name = "AgencyPayTypes.findByBenefitEarningsInd", query = "SELECT a FROM AgencyPayTypes a WHERE a.benefitEarningsInd = :benefitEarningsInd"), @NamedQuery(name = "AgencyPayTypes.findByBenefitDistMethod", query = "SELECT a FROM AgencyPayTypes a WHERE a.benefitDistMethod = :benefitDistMethod"), @NamedQuery(name = "AgencyPayTypes.findByEndDate", query = "SELECT a FROM AgencyPayTypes a WHERE a.endDate = :endDate"), @NamedQuery(name = "AgencyPayTypes.findByModifiedUserId", query = "SELECT a FROM AgencyPayTypes a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "AgencyPayTypes.findByModifiedDate", query = "SELECT a FROM AgencyPayTypes a WHERE a.modifiedDate = :modifiedDate")})
public class AgencyPayTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "AGPT_IDENTIFIER")
    private Integer agptIdentifier;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "BENEFIT_EARNINGS_IND")
    private String benefitEarningsInd;
    @Column(name = "BENEFIT_DIST_METHOD")
    private String benefitDistMethod;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT"), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY")})
    @ManyToOne(optional = false)
    private Agencies agencies;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT"), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY"), @JoinColumn(name = "PAY_TYPE_GROUP", referencedColumnName = "PAY_TYPE_GROUP")})
    @ManyToOne(optional = false)
    private AgencyPayTypeGroups agencyPayTypeGroups;
    @JoinColumn(name = "CLASS_TYPE", referencedColumnName = "CLASS_TYPE")
    @ManyToOne(optional = false)
    private ClassType classType;
    @JoinColumn(name = "PYTP_IDENTIFIER", referencedColumnName = "PYTP_IDENTIFIER")
    @ManyToOne(optional = false)
    private PayTypes pytpIdentifier;

    public AgencyPayTypes() {
    }

    public AgencyPayTypes(Integer agptIdentifier) {
        this.agptIdentifier = agptIdentifier;
    }

    public AgencyPayTypes(Integer agptIdentifier, Date startDate, String benefitEarningsInd, Date endDate) {
        this.agptIdentifier = agptIdentifier;
        this.startDate = startDate;
        this.benefitEarningsInd = benefitEarningsInd;
        this.endDate = endDate;
    }

    public Integer getAgptIdentifier() {
        return agptIdentifier;
    }

    public void setAgptIdentifier(Integer agptIdentifier) {
        this.agptIdentifier = agptIdentifier;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getBenefitEarningsInd() {
        return benefitEarningsInd;
    }

    public void setBenefitEarningsInd(String benefitEarningsInd) {
        this.benefitEarningsInd = benefitEarningsInd;
    }

    public String getBenefitDistMethod() {
        return benefitDistMethod;
    }

    public void setBenefitDistMethod(String benefitDistMethod) {
        this.benefitDistMethod = benefitDistMethod;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Agencies getAgencies() {
        return agencies;
    }

    public void setAgencies(Agencies agencies) {
        this.agencies = agencies;
    }

    public AgencyPayTypeGroups getAgencyPayTypeGroups() {
        return agencyPayTypeGroups;
    }

    public void setAgencyPayTypeGroups(AgencyPayTypeGroups agencyPayTypeGroups) {
        this.agencyPayTypeGroups = agencyPayTypeGroups;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public PayTypes getPytpIdentifier() {
        return pytpIdentifier;
    }

    public void setPytpIdentifier(PayTypes pytpIdentifier) {
        this.pytpIdentifier = pytpIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agptIdentifier != null ? agptIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgencyPayTypes)) {
            return false;
        }
        AgencyPayTypes other = (AgencyPayTypes) object;
        if ((this.agptIdentifier == null && other.agptIdentifier != null) || (this.agptIdentifier != null && !this.agptIdentifier.equals(other.agptIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AgencyPayTypes[agptIdentifier=" + agptIdentifier + "]";
    }

}
