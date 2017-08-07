package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommonMileagesPK implements Serializable {
	@Column(name="TO_ELOC_IDENTIFIER")
	private long toElocIdentifier;

	@Column(name="FROM_ELOC_IDENTIFIER")
	private long fromElocIdentifier;

	private static final long serialVersionUID = 1L;

	public CommonMileagesPK() {
		super();
	}

	public long getToElocIdentifier() {
		return this.toElocIdentifier;
	}

	public void setToElocIdentifier(long toElocIdentifier) {
		this.toElocIdentifier = toElocIdentifier;
	}

	public long getFromElocIdentifier() {
		return this.fromElocIdentifier;
	}

	public void setFromElocIdentifier(long fromElocIdentifier) {
		this.fromElocIdentifier = fromElocIdentifier;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ( ! (o instanceof CommonMileagesPK)) {
			return false;
		}
		CommonMileagesPK other = (CommonMileagesPK) o;
		return (this.toElocIdentifier == other.toElocIdentifier)
			&& (this.fromElocIdentifier == other.fromElocIdentifier);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.toElocIdentifier ^ (this.toElocIdentifier >>> 32)));
		hash = hash * prime + ((int) (this.fromElocIdentifier ^ (this.fromElocIdentifier >>> 32)));
		return hash;
	}

}
