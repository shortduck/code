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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Class to represent out of state travel information. 
 * 
 * @author chaudharym
 */
@Entity
@Table(name = "OUT_OF_STATE_TRAVEL")
public class OutOfStateTravel implements Serializable {

	private static final long serialVersionUID = -4535645441L;

	@Id
	@SequenceGenerator(name = "OOST_IDENTIFIER_GENERATOR", sequenceName = "OOST_IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OOST_IDENTIFIER_GENERATOR")
	@Column(name = "OOST_IDENTIFIER", nullable = false)
	private Integer oostIdentifier;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name = "MODIFIED_USER_ID")
	private String modifiedUserId;
	
	@JoinColumn(name = "EXPM_IDENTIFIER")
	@ManyToOne(optional = false)
	private ExpenseMasters expmIdentifier;
	
	@Column(name = "STAC_IDENTIFIER")
	private Integer stacIdentifier;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;

	public OutOfStateTravel() {
	}

	public OutOfStateTravel(OutOfStateTravel old) {
		stacIdentifier = new Integer(old.stacIdentifier);
		expmIdentifier = old.expmIdentifier;
		modifiedUserId = old.modifiedUserId;
		modifiedDate = new Date();
	}
	
	public OutOfStateTravel(Integer oostIdentifier) {
		this.oostIdentifier = oostIdentifier;
	}

	public Integer getOostIdentifier() {
		return oostIdentifier;
	}

	public void setOostIdentifier(Integer oostIdentifier) {
		this.oostIdentifier = oostIdentifier;
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

	public ExpenseMasters getExpmIdentifier() {
		return expmIdentifier;
	}

	public void setExpmIdentifier(ExpenseMasters expmIdentifier) {
		this.expmIdentifier = expmIdentifier;
	}

	public Integer getStacIdentifier() {
		return stacIdentifier;
	}

	public void setStacIdentifier(Integer stacIdentifier) {
		this.stacIdentifier = stacIdentifier;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (oostIdentifier != null ? oostIdentifier.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof OutOfStateTravel)) {
			return false;
		}
		OutOfStateTravel other = (OutOfStateTravel) object;
		if ((this.oostIdentifier == null && other.oostIdentifier != null)
				|| (this.oostIdentifier != null && !this.oostIdentifier
						.equals(other.oostIdentifier))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "OutOfStateTravel[oostIdentifier="+ oostIdentifier + "]";
	}

}
