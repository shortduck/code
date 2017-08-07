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
@Table(name = "APPROVAL_CATEGORIES")
@NamedQueries({@NamedQuery(name = "ApprovalCategories.findAll", query = "SELECT a FROM ApprovalCategories a"), @NamedQuery(name = "ApprovalCategories.findByDataCategory", query = "SELECT a FROM ApprovalCategories a WHERE a.dataCategory = :dataCategory"), @NamedQuery(name = "ApprovalCategories.findByDescription", query = "SELECT a FROM ApprovalCategories a WHERE a.description = :description"), @NamedQuery(name = "ApprovalCategories.findByModifiedUserId", query = "SELECT a FROM ApprovalCategories a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "ApprovalCategories.findByModifiedDate", query = "SELECT a FROM ApprovalCategories a WHERE a.modifiedDate = :modifiedDate")})
public class ApprovalCategories implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "DATA_CATEGORY")
    private String dataCategory;
    
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approvalCategories")
    private Collection<TkuoptApprovalPaths> tkuoptApprovalPathsCollection;

    public ApprovalCategories() {
    }

    public ApprovalCategories(String dataCategory) {
        this.dataCategory = dataCategory;
    }

    public ApprovalCategories(String dataCategory, String description) {
        this.dataCategory = dataCategory;
        this.description = description;
    }

    public String getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(String dataCategory) {
        this.dataCategory = dataCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Collection<TkuoptApprovalPaths> getTkuoptApprovalPathsCollection() {
        return tkuoptApprovalPathsCollection;
    }

    public void setTkuoptApprovalPathsCollection(Collection<TkuoptApprovalPaths> tkuoptApprovalPathsCollection) {
        this.tkuoptApprovalPathsCollection = tkuoptApprovalPathsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataCategory != null ? dataCategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApprovalCategories)) {
            return false;
        }
        ApprovalCategories other = (ApprovalCategories) object;
        if ((this.dataCategory == null && other.dataCategory != null) || (this.dataCategory != null && !this.dataCategory.equals(other.dataCategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.ApprovalCategories[dataCategory=" + dataCategory + "]";
    }

}
