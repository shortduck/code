package gov.michigan.dit.timeexpense.model.core;

import gov.michigan.dit.timeexpense.model.db.ErrorMessages;

import java.io.Serializable;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "TRAVEL_REQ_ERRORS")
public class TravelReqErrors implements Serializable {
	private static final long serialVersionUID = 6768641595009446801L;

	@SequenceGenerator(name = "TRAVEL_REQ_ERRORS_GENERATOR", sequenceName = "TREQER_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAVEL_REQ_ERRORS_GENERATOR")
	@Column(name = "TREQER_IDENTIFIER")
	private Integer treqerIdentifier;
	
	@Column(name = "ERROR_SOURCE")
	private String errorSource;
	
	@Column(name = "MODIFIED_USER_ID")
	private String modifiedUserId;
	
	@ManyToOne(optional = false, cascade={CascadeType.MERGE})
	@JoinColumn(name = "ERROR_CODE")
	private ErrorMessages errorCode;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "TREQM_IDENTIFIER")
	private TravelReqMasters treqmIdentifier;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;

	public TravelReqErrors() {
	}

	public TravelReqErrors(Integer experIdentifier) {
		this.treqerIdentifier = experIdentifier;
	}

	/** Copy constructor to <b>deep</b> copy all properties except errorCode.
	 *  and any parent references (e.g. ExpenseMasters reference is not set and left to the caller to associate)
	 */
	public TravelReqErrors(TravelReqErrors oldError) {
		errorCode = oldError.errorCode;
		errorSource = oldError.errorSource;
		modifiedUserId = oldError.modifiedUserId;
	}	
	
	public Integer getTreqerIdentifier() {
		return treqerIdentifier;
	}

	public void setTreqerIdentifier(Integer treqerIdentifier) {
		this.treqerIdentifier = treqerIdentifier;
	}

	public String getErrorSource() {
		return errorSource;
	}

	public void setErrorSource(String errorSource) {
		this.errorSource = errorSource;
	}

	public String getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public ErrorMessages getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorMessages errorCode) {
		this.errorCode = errorCode;
	}

	public TravelReqMasters getTreqmIdentifier() {
		return treqmIdentifier;
	}

	public void setTreqmIdentifier(TravelReqMasters treqmIdentifier) {
		this.treqmIdentifier = treqmIdentifier;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (treqerIdentifier != null ? treqerIdentifier.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof TravelReqErrors)) {
			return false;
		}
		TravelReqErrors other = (TravelReqErrors) object;
		if ((this.treqerIdentifier == null && other.treqerIdentifier != null)
				|| (this.treqerIdentifier != null && !this.treqerIdentifier
						.equals(other.treqerIdentifier))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ExpenseErrors[experIdentifier="+treqerIdentifier+"]";
	}

}
