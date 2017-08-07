/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "PAY_TYPES")
@NamedQueries({@NamedQuery(name = "PayTypes.findAll", query = "SELECT p FROM PayTypes p"), @NamedQuery(name = "PayTypes.findByPytpIdentifier", query = "SELECT p FROM PayTypes p WHERE p.pytpIdentifier = :pytpIdentifier"), @NamedQuery(name = "PayTypes.findByPayType", query = "SELECT p FROM PayTypes p WHERE p.payType = :payType"), @NamedQuery(name = "PayTypes.findByStartDate", query = "SELECT p FROM PayTypes p WHERE p.startDate = :startDate"), @NamedQuery(name = "PayTypes.findByEndDate", query = "SELECT p FROM PayTypes p WHERE p.endDate = :endDate"), @NamedQuery(name = "PayTypes.findByPayTypeCategory", query = "SELECT p FROM PayTypes p WHERE p.payTypeCategory = :payTypeCategory"), @NamedQuery(name = "PayTypes.findByModifiedDate", query = "SELECT p FROM PayTypes p WHERE p.modifiedDate = :modifiedDate"), @NamedQuery(name = "PayTypes.findByModifiedUserId", query = "SELECT p FROM PayTypes p WHERE p.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "PayTypes.findByDescription", query = "SELECT p FROM PayTypes p WHERE p.description = :description"), @NamedQuery(name = "PayTypes.findBySourceInd", query = "SELECT p FROM PayTypes p WHERE p.sourceInd = :sourceInd"), @NamedQuery(name = "PayTypes.findByStatus", query = "SELECT p FROM PayTypes p WHERE p.status = :status")})
public class PayTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "PYTP_IDENTIFIER")
    private Integer pytpIdentifier;
    
    @Column(name = "PAY_TYPE")
    private String payType;
    
    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(name = "PAY_TYPE_CATEGORY")
    private String payTypeCategory;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "SOURCE_IND")
    private String sourceInd;
    
    @Column(name = "STATUS")
    private String status;
   /* @OneToMany(cascade = CascadeType.ALL, mappedBy = "pytpIdentifier")
    private Collection<AdvanceDetails> advanceDetailsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pytpIdentifier")
    private Collection<ExpenseTypes> expenseTypesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pytpIdentifier")
    private Collection<AgencyPayTypes> agencyPayTypesCollection;
*/
    public PayTypes() {
    }

    public PayTypes(Integer pytpIdentifier) {
        this.pytpIdentifier = pytpIdentifier;
    }

    public PayTypes(Integer pytpIdentifier, String payType, Date startDate, Date endDate, String payTypeCategory, String status) {
        this.pytpIdentifier = pytpIdentifier;
        this.payType = payType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.payTypeCategory = payTypeCategory;
        this.status = status;
    }

    public Integer getPytpIdentifier() {
        return pytpIdentifier;
    }

    public void setPytpIdentifier(Integer pytpIdentifier) {
        this.pytpIdentifier = pytpIdentifier;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public String getPayTypeCategory() {
        return payTypeCategory;
    }

    public void setPayTypeCategory(String payTypeCategory) {
        this.payTypeCategory = payTypeCategory;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceInd() {
        return sourceInd;
    }

    public void setSourceInd(String sourceInd) {
        this.sourceInd = sourceInd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

/*    public Collection<AdvanceDetails> getAdvanceDetailsCollection() {
        return advanceDetailsCollection;
    }

    public void setAdvanceDetailsCollection(Collection<AdvanceDetails> advanceDetailsCollection) {
        this.advanceDetailsCollection = advanceDetailsCollection;
    }

    public Collection<ExpenseTypes> getExpenseTypesCollection() {
        return expenseTypesCollection;
    }

    public void setExpenseTypesCollection(Collection<ExpenseTypes> expenseTypesCollection) {
        this.expenseTypesCollection = expenseTypesCollection;
    }

    public Collection<AgencyPayTypes> getAgencyPayTypesCollection() {
        return agencyPayTypesCollection;
    }

    public void setAgencyPayTypesCollection(Collection<AgencyPayTypes> agencyPayTypesCollection) {
        this.agencyPayTypesCollection = agencyPayTypesCollection;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pytpIdentifier != null ? pytpIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PayTypes)) {
            return false;
        }
        PayTypes other = (PayTypes) object;
        if ((this.pytpIdentifier == null && other.pytpIdentifier != null) || (this.pytpIdentifier != null && !this.pytpIdentifier.equals(other.pytpIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.PayTypes[pytpIdentifier=" + pytpIdentifier + "]";
    }

}
