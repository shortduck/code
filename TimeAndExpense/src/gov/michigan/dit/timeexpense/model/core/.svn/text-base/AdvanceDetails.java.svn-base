/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "ADVANCE_DETAILS")
@NamedQueries({@NamedQuery(name = "AdvanceDetails.findAll", query = "SELECT a FROM AdvanceDetails a"), @NamedQuery(name = "AdvanceDetails.findByAdvdIdentifier", query = "SELECT a FROM AdvanceDetails a WHERE a.advdIdentifier = :advdIdentifier"), @NamedQuery(name = "AdvanceDetails.findByDollarAmount", query = "SELECT a FROM AdvanceDetails a WHERE a.dollarAmount = :dollarAmount"), @NamedQuery(name = "AdvanceDetails.findByModifiedUserId", query = "SELECT a FROM AdvanceDetails a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "AdvanceDetails.findByModifiedDate", query = "SELECT a FROM AdvanceDetails a WHERE a.modifiedDate = :modifiedDate")})
public class AdvanceDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name = "ADVANCE_DETAILS_GENERATOR", sequenceName = "ADVD_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADVANCE_DETAILS_GENERATOR")
    
    @Column(name = "ADVD_IDENTIFIER")
    private Integer advdIdentifier;
    
    @Column(name = "DOLLAR_AMOUNT")
    private double dollarAmount;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;
    @JoinColumn(name = "ADVM_IDENTIFIER", referencedColumnName = "ADVM_IDENTIFIER")
    @ManyToOne(optional = false)
    private AdvanceMasters advmIdentifier;
    /*@JoinColumn(name = "PYTP_IDENTIFIER", referencedColumnName = "PYTP_IDENTIFIER")
    @ManyToOne(optional = false)*/
    @Column(name = "PYTP_IDENTIFIER")
    private Integer pytpIdentifier;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advdIdentifier")
    
    private List<AdvanceDetailCodingBlocks> advanceDetailCodingBlocksCollection;
    
    @Version
	@Column(name = "VERSION")
	private Integer version;

    public AdvanceDetails() {
    }

    public AdvanceDetails(Integer advdIdentifier) {
        this.advdIdentifier = advdIdentifier;
    }

    public AdvanceDetails(Integer advdIdentifier, double dollarAmount) {
        this.advdIdentifier = advdIdentifier;
        this.dollarAmount = dollarAmount;
    }

    public Integer getAdvdIdentifier() {
        return advdIdentifier;
    }

    public void setAdvdIdentifier(Integer advdIdentifier) {
        this.advdIdentifier = advdIdentifier;
    }

    public double getDollarAmount() {
        return dollarAmount;
    }

    public void setDollarAmount(double dollarAmount) {
        this.dollarAmount = dollarAmount;
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

    public AdvanceMasters getAdvmIdentifier() {
        return advmIdentifier;
    }

    public void setAdvmIdentifier(AdvanceMasters advmIdentifier) {
        this.advmIdentifier = advmIdentifier;
    }

    public Integer getPytpIdentifier() {
        return pytpIdentifier;
    }

    public void setPytpIdentifier(Integer pytpIdentifier) {
        this.pytpIdentifier = pytpIdentifier;
    }

    public List<AdvanceDetailCodingBlocks> getAdvanceDetailCodingBlocksCollection() {
        return advanceDetailCodingBlocksCollection;
    }

    public void setAdvanceDetailCodingBlocksCollection(List<AdvanceDetailCodingBlocks> advanceDetailCodingBlocksCollection) {
        this.advanceDetailCodingBlocksCollection = advanceDetailCodingBlocksCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (advdIdentifier != null ? advdIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvanceDetails)) {
            return false;
        }
        AdvanceDetails other = (AdvanceDetails) object;
        if ((this.advdIdentifier == null && other.advdIdentifier != null) || (this.advdIdentifier != null && !this.advdIdentifier.equals(other.advdIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AdvanceDetails[advdIdentifier=" + advdIdentifier + "]";
    }

}
