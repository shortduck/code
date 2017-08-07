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
@Table(name = "EXPENSE_ERRORS")
public class ExpenseErrors implements Serializable {
	private static final long serialVersionUID = 6768641595009446801L;

	@SequenceGenerator(name = "EXPENSE_ERRORS_GENERATOR", sequenceName = "EXPER_IDENTIFIER")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPENSE_ERRORS_GENERATOR")
	@Column(name = "EXPER_IDENTIFIER")
	private Integer experIdentifier;
	
	@Column(name = "ERROR_SOURCE")
	private String errorSource;
	
	@Column(name = "MODIFIED_USER_ID")
	private String modifiedUserId;
	
	@ManyToOne(optional = false, cascade={CascadeType.MERGE})
	@JoinColumn(name = "ERROR_CODE")
	private ErrorMessages errorCode;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "EXPM_IDENTIFIER")
	private ExpenseMasters expmIdentifier;
	
	@Version
	@Column(name = "VERSION")
	private Integer version;

	public ExpenseErrors() {
	}

	public ExpenseErrors(Integer experIdentifier) {
		this.experIdentifier = experIdentifier;
	}

	/** Copy constructor to <b>deep</b> copy all properties except errorCode.
	 *  and any parent references (e.g. ExpenseMasters reference is not set and left to the caller to associate)
	 */
	public ExpenseErrors(ExpenseErrors oldError) {
		errorCode = oldError.errorCode;
		errorSource = oldError.errorSource;
		modifiedUserId = oldError.modifiedUserId;
	}	
	
	public Integer getExperIdentifier() {
		return experIdentifier;
	}

	public void setExperIdentifier(Integer experIdentifier) {
		this.experIdentifier = experIdentifier;
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

	public ExpenseMasters getExpmIdentifier() {
		return expmIdentifier;
	}

	public void setExpmIdentifier(ExpenseMasters expmIdentifier) {
		this.expmIdentifier = expmIdentifier;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (experIdentifier != null ? experIdentifier.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ExpenseErrors)) {
			return false;
		}
		ExpenseErrors other = (ExpenseErrors) object;
		if ((this.experIdentifier == null && other.experIdentifier != null)
				|| (this.experIdentifier != null && !this.experIdentifier
						.equals(other.experIdentifier))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ExpenseErrors[experIdentifier="+experIdentifier+"]";
	}

}
