/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="STATE_AUTH_CODES")
public class StateAuthCodes implements Serializable {
	
	private static final long serialVersionUID = -4573588091606843719L;

	@Id
	@Column(name = "STAC_IDENTIFIER")
	private Integer stacIdentifier;

	@Basic(optional = false)
	private String description;
	
	@Column(name = "STATUS_CODE")
	private String statusCode;

	public StateAuthCodes() {}

	public StateAuthCodes(Integer stacIdentifier) {
		this.stacIdentifier = stacIdentifier;
	}

	public StateAuthCodes(Integer stacIdentifier, String description) {
		this.stacIdentifier = stacIdentifier;
		this.description = description;
	}

	public Integer getStacIdentifier() {
		return stacIdentifier;
	}

	public void setStacIdentifier(Integer stacIdentifier) {
		this.stacIdentifier = stacIdentifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (stacIdentifier != null ? stacIdentifier.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof StateAuthCodes)) {
			return false;
		}
		StateAuthCodes other = (StateAuthCodes) object;
		if ((this.stacIdentifier == null && other.stacIdentifier != null)
				|| (this.stacIdentifier != null && !this.stacIdentifier
						.equals(other.stacIdentifier))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "StateAuthCodes[stacIdentifier="+ stacIdentifier + "]";
	}

}
