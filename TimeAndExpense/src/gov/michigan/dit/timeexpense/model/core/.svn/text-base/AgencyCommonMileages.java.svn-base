package gov.michigan.dit.timeexpense.model.core;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="AGENCY_COMMON_MILEAGES")
public class AgencyCommonMileages implements Serializable {
	@Id
	@SequenceGenerator(name = "AGYCM_IDENTIFIER_GENERATOR", sequenceName = "AGYCM_IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AGYCM_IDENTIFIER_GENERATOR")
	@Column(name="AGYCM_IDENTIFIER")
	private long agycmIdentifier;

	@Column(name="TO_ELOC_IDENTIFIER")
	private BigDecimal toElocIdentifier;

	@Column(name="FROM_ELOC_IDENTIFIER")
	private BigDecimal fromElocIdentifier;

	@Column(name="AGY_MILEAGE")
	private BigDecimal agyMileage;

	private String department;

	private String agency;

	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;

	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	private static final long serialVersionUID = 1L;

	public AgencyCommonMileages() {
		super();
	}

	public long getAgycmIdentifier() {
		return this.agycmIdentifier;
	}

	public void setAgycmIdentifier(long agycmIdentifier) {
		this.agycmIdentifier = agycmIdentifier;
	}

	public BigDecimal getToElocIdentifier() {
		return this.toElocIdentifier;
	}

	public void setToElocIdentifier(BigDecimal toElocIdentifier) {
		this.toElocIdentifier = toElocIdentifier;
	}

	public BigDecimal getFromElocIdentifier() {
		return this.fromElocIdentifier;
	}

	public void setFromElocIdentifier(BigDecimal fromElocIdentifier) {
		this.fromElocIdentifier = fromElocIdentifier;
	}

	public BigDecimal getAgyMileage() {
		return this.agyMileage;
	}

	public void setAgyMileage(BigDecimal agyMileage) {
		this.agyMileage = agyMileage;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAgency() {
		return this.agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getModifiedUserId() {
		return this.modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
