/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import gov.michigan.dit.timeexpense.model.db.ErrorMessages;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "ADVANCE_ERRORS")
@NamedQueries({@NamedQuery(name = "AdvanceErrors.findAll", query = "SELECT a FROM AdvanceErrors a"), @NamedQuery(name = "AdvanceErrors.findByAdveIdentifier", query = "SELECT a FROM AdvanceErrors a WHERE a.adveIdentifier = :adveIdentifier"), @NamedQuery(name = "AdvanceErrors.findByModifiedDate", query = "SELECT a FROM AdvanceErrors a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "AdvanceErrors.findByModifiedUserId", query = "SELECT a FROM AdvanceErrors a WHERE a.modifiedUserId = :modifiedUserId")})
public class AdvanceErrors implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @SequenceGenerator(name = "ADVANCE_ERRORS_GENERATOR", sequenceName = "ADVE_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADVANCE_ERRORS_GENERATOR")

    @Column(name = "ADVE_IDENTIFIER")
    private Integer adveIdentifier;
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @JoinColumn(name = "ADVM_IDENTIFIER", referencedColumnName = "ADVM_IDENTIFIER")
    @ManyToOne(optional = false)
    private AdvanceMasters advmIdentifier;
    @JoinColumn(name = "ERROR_CODE", referencedColumnName = "ERROR_CODE")
    @ManyToOne(optional = false)
    private ErrorMessages errorCode;
    
    @Column(name = "ERROR_SOURCE")
    private String errorSource;
    @Version
	@Column(name = "VERSION")
	private Integer version;

    public AdvanceErrors() {
    }

    public AdvanceErrors(Integer adveIdentifier) {
        this.adveIdentifier = adveIdentifier;
    }

    public Integer getAdveIdentifier() {
        return adveIdentifier;
    }

    public void setAdveIdentifier(Integer adveIdentifier) {
        this.adveIdentifier = adveIdentifier;
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

    public AdvanceMasters getAdvmIdentifier() {
        return advmIdentifier;
    }

    public void setAdvmIdentifier(AdvanceMasters advmIdentifier) {
        this.advmIdentifier = advmIdentifier;
    }

    public ErrorMessages getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorMessages errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adveIdentifier != null ? adveIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvanceErrors)) {
            return false;
        }
        AdvanceErrors other = (AdvanceErrors) object;
        if ((this.adveIdentifier == null && other.adveIdentifier != null) || (this.adveIdentifier != null && !this.adveIdentifier.equals(other.adveIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AdvanceErrors[adveIdentifier=" + adveIdentifier + "]";
    }

	public void setErrorSource(String errorSource) {
		this.errorSource = errorSource;
	}

	public String getErrorSource() {
		return errorSource;
	}

}
