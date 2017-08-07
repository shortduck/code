package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="COMMON_MILEAGES")
public class CommonMileages implements Serializable {
	@EmbeddedId
	private CommonMileagesPK pk;

	private BigDecimal mileage;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;

	private static final long serialVersionUID = 1L;

	public CommonMileages() {
		super();
	}

	public CommonMileagesPK getPk() {
		return this.pk;
	}

	public void setPk(CommonMileagesPK pk) {
		this.pk = pk;
	}

	public BigDecimal getMileage() {
		return this.mileage;
	}

	public void setMileage(BigDecimal mileage) {
		this.mileage = mileage;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedUserId() {
		return this.modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

}
