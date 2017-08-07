/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "ADVANCE_EVENTS")
@NamedQueries({@NamedQuery(name = "AdvanceEvents.findAll", query = "SELECT a FROM AdvanceEvents a"), @NamedQuery(name = "AdvanceEvents.findByAdevIdentifier", query = "SELECT a FROM AdvanceEvents a WHERE a.adevIdentifier = :adevIdentifier"), @NamedQuery(name = "AdvanceEvents.findByModifiedUserId", query = "SELECT a FROM AdvanceEvents a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "AdvanceEvents.findByModifiedDate", query = "SELECT a FROM AdvanceEvents a WHERE a.modifiedDate = :modifiedDate")})
public class AdvanceEvents implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @SequenceGenerator(name = "ADVANCE_EVENTS_GENERATOR", sequenceName = "ADEV_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADVANCE_EVENTS_GENERATOR")
    
 
    
    @Column(name = "ADEV_IDENTIFIER")
    private Integer adevIdentifier;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    /*@JoinColumn(name = "APPT_IDENTIFIER", referencedColumnName = "APPT_IDENTIFIER")
    @ManyToOne(optional = false)*/
    @Column(name = "APPT_IDENTIFIER")
    private Integer apptIdentifier;
    @Column(name = "OUTSTANDING_AMOUNT")
    private double outstandingAmount = 0;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adevIdentifier", fetch = EAGER)
    private List<AdvanceMasters> advanceMastersCollection;
    @Version
	@Column(name = "VERSION")
	private Integer version;

    public AdvanceEvents() {
    }

    public AdvanceEvents(Integer adevIdentifier) {
        this.adevIdentifier = adevIdentifier;
    }

    public Integer getAdevIdentifier() {
        return adevIdentifier;
    }

    public void setAdevIdentifier(Integer adevIdentifier) {
        this.adevIdentifier = adevIdentifier;
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

    public Integer getApptIdentifier() {
        return apptIdentifier;
    }

    public void setApptIdentifier(Integer apptIdentifier) {
        this.apptIdentifier = apptIdentifier;
    }

    public List<AdvanceMasters> getAdvanceMastersCollection() {
        return advanceMastersCollection;
    }

    public void setAdvanceMastersCollection(List<AdvanceMasters> advanceMastersCollection) {
        this.advanceMastersCollection = advanceMastersCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adevIdentifier != null ? adevIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvanceEvents)) {
            return false;
        }
        AdvanceEvents other = (AdvanceEvents) object;
        if ((this.adevIdentifier == null && other.adevIdentifier != null) || (this.adevIdentifier != null && !this.adevIdentifier.equals(other.adevIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AdvanceEvents[adevIdentifier=" + adevIdentifier + "]";
    }

	public void setOutstandingAmount(double outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public double getOutstandingAmount() {
		return outstandingAmount;
	}

}
