/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author GhoshS
 */
@Entity
@Table(name = "STATE_AUTH_CODES")
@NamedQueries( {
		@NamedQuery(name = "StateAuthCodes.findAll", query = "SELECT s FROM StateAuthCodes s"),
		@NamedQuery(name = "StateAuthCodes.findByStacIdentifier", query = "SELECT s FROM StateAuthCodes s WHERE s.stacIdentifier = :stacIdentifier"),
		@NamedQuery(name = "StateAuthCodes.findByDescription", query = "SELECT s FROM StateAuthCodes s WHERE s.description = :description"),
		@NamedQuery(name = "StateAuthCodes.findByStatusCode", query = "SELECT s FROM StateAuthCodes s WHERE s.statusCode = :statusCode"),
		@NamedQuery(name = "StateAuthCodes.findByModifiedUserId", query = "SELECT s FROM StateAuthCodes s WHERE s.modifiedUserId = :modifiedUserId"),
		@NamedQuery(name = "StateAuthCodes.findByModifiedDate", query = "SELECT s FROM StateAuthCodes s WHERE s.modifiedDate = :modifiedDate") })
public class StateAuthCodes implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "STAC_IDENTIFIER")
	private Integer stacIdentifier;
	@Basic(optional = false)
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "STATUS_CODE")
	private String statusCode;
	@Column(name = "MODIFIED_USER_ID")
	private String modifiedUserId;
	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	public StateAuthCodes() {
	}

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
		return "gov.michigan.dit.timeexpense.model.StateAuthCodes[stacIdentifier="
				+ stacIdentifier + "]";
	}

}
