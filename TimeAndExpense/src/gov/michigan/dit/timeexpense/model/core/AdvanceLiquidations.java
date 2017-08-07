package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author chiduras
 */
@Entity
@Table(name = "ADVANCE_LIQUIDATIONS")
public class AdvanceLiquidations implements Serializable {
    private static final long serialVersionUID = 172573267856L;
    
    @SequenceGenerator(name = "LIQD_IDENTIFIER_GENERATOR", sequenceName = "LIQD_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIQD_IDENTIFIER_GENERATOR")
    @Column(name = "LIQD_IDENTIFIER")
    private Integer liqdIdentifier;

    @Column(name = "EXPM_IDENTIFIER")
    private Integer expenseMasterId;
    
    @Column(name = "AMOUNT")
    private double amount;
    
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @JoinColumn(name = "ADVM_IDENTIFIER", referencedColumnName = "ADVM_IDENTIFIER")
    @ManyToOne(optional = false)
    private AdvanceMasters advanceMaster;
    
    @Version
	@Column(name = "VERSION")
	private Integer version;

	public AdvanceLiquidations() {}

	/**
	 * Copy constructor.
	 * @param _old
	 */
	public AdvanceLiquidations(AdvanceLiquidations _old) {
		expenseMasterId = _old.expenseMasterId;
		amount = _old.amount;
		modifiedUserId = _old.modifiedUserId;
		modifiedDate = _old.modifiedDate;
		advanceMaster = _old.getAdvanceMaster();
    }

	
    public AdvanceLiquidations(Integer liqdIdentifier) {
        this.liqdIdentifier = liqdIdentifier;
    }

    public AdvanceLiquidations(Integer liqdIdentifier, double amount) {
        this.liqdIdentifier = liqdIdentifier;
        this.amount = amount;
    }

    public Integer getLiqdIdentifier() {
        return liqdIdentifier;
    }

    public void setLiqdIdentifier(Integer liqdIdentifier) {
        this.liqdIdentifier = liqdIdentifier;
    }

	public Integer getExpenseMasterId() {
		return expenseMasterId;
	}

	public void setExpenseMasterId(Integer expenseMasterId) {
		this.expenseMasterId = expenseMasterId;
	}
    
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
    
    public AdvanceMasters getAdvanceMaster() {
		return advanceMaster;
	}

	public void setAdvanceMaster(AdvanceMasters advanceMaster) {
		this.advanceMaster = advanceMaster;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (liqdIdentifier != null ? liqdIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvanceLiquidations)) {
            return false;
        }
        AdvanceLiquidations other = (AdvanceLiquidations) object;
        if ((this.liqdIdentifier == null && other.liqdIdentifier != null) || (this.liqdIdentifier != null && !this.liqdIdentifier.equals(other.liqdIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AdvanceLiquidations[liqdIdentifier=" + liqdIdentifier + "]";
    }

}
