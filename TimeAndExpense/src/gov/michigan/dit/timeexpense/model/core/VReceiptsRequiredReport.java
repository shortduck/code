package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="V_RECEIPTS_REQUIRED_REPORT")
public class VReceiptsRequiredReport implements Serializable {
	
	private String name;
	
	@EmbeddedId
	private VReceiptsRequiredReportPK id;
	
	@Column(name="EMP_IDENTIFIER")
	private Long empIdentifier;

	

	@Column(name="EXPEV_IDENTIFIER")
	private Long expevIdentifier;

	@Column(name="EXP_DATE_FROM")
	private Date expDateFrom;

	@Column(name="EXP_DATE_TO")
	private Date expDateTo;

	@Column(name="NATURE_OF_BUSINESS")
	private String natureOfBusiness;

	private String status;

	private String department;

	@Column(name="DEPT_NAME")
	private String deptName;

	private String agency;

	@Column(name="AGY_NAME")
	private String agyName;

	private String tku;

	@Column(name="TKU_NAME")
	private String tkuName;

	private String supervisor;

	@Column(name="SUP_NAME")
	private String supName;

	private Date paydate;

	@Column(name="TRAVEL_TYPE")
	private String travelType;

	@Column(name="PRE_AUDIT_REQ")
	private String preAuditReq;

	@Column(name="SUM_DOLLAR_AMOUNT")
	private Double sumDollarAmount;

	@Column(name="SUPEREMPID")
	private Long superempid;

	private String groupfield;

	@Column(name="TRAVEL_IND")
	private String travelInd;

	@Column(name="OUT_OF_STATE_IND")
	private String outOfStateInd;

	@Column(name="EXPENSE_TYPE")
	private String expenseType;
   
	@Transient
	private BigDecimal amtGreater;
	@Transient
	private BigDecimal amtLesser;
	   
	@Transient
	private Date payAmtFrm;
	
	@Transient
	private Date payAmtTo;
	
	@Transient
	private float randPer = 100;
	 
	private static final long serialVersionUID = 1L;

	public VReceiptsRequiredReport() {
		super();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VReceiptsRequiredReport other = (VReceiptsRequiredReport) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getEmpIdentifier() {
		return this.empIdentifier;
	}

	public void setEmpIdentifier(Long empIdentifier) {
		this.empIdentifier = empIdentifier;
	}

	public Long getExpevIdentifier() {
		return this.expevIdentifier;
	}

	public void setExpevIdentifier(Long expevIdentifier) {
		this.expevIdentifier = expevIdentifier;
	}

	public Date getExpDateFrom() {
		return this.expDateFrom;
	}

	public void setExpDateFrom(Date expDateFrom) {
		this.expDateFrom = expDateFrom;
	}

	public Date getExpDateTo() {
		return this.expDateTo;
	}

	public void setExpDateTo(Date expDateTo) {
		this.expDateTo = expDateTo;
	}

	public String getNatureOfBusiness() {
		return this.natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAgency() {
		return this.agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getAgyName() {
		return this.agyName;
	}

	public void setAgyName(String agyName) {
		this.agyName = agyName;
	}

	public String getTku() {
		return this.tku;
	}

	public void setTku(String tku) {
		this.tku = tku;
	}

	public String getTkuName() {
		return this.tkuName;
	}

	public void setTkuName(String tkuName) {
		this.tkuName = tkuName;
	}

	public String getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getSupName() {
		return this.supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public Date getPaydate() {
		return this.paydate;
	}

	public void setPaydate(Date paydate) {
		this.paydate = paydate;
	}

	public String getTravelType() {
		return this.travelType;
	}

	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}

	public String getPreAuditReq() {
		return this.preAuditReq;
	}

	public void setPreAuditReq(String preAuditReq) {
		this.preAuditReq = preAuditReq;
	}

	public Double getSumDollarAmount() {
		return this.sumDollarAmount;
	}

	public void setSumDollarAmount(Double sumDollarAmount) {
		this.sumDollarAmount = sumDollarAmount;
	}

	public Long getSuperempid() {
		return this.superempid;
	}

	public void setSuperempid(Long superempid) {
		this.superempid = superempid;
	}

	public String getGroupfield() {
		return this.groupfield;
	}

	public void setGroupfield(String groupfield) {
		this.groupfield = groupfield;
	}

	public String getTravelInd() {
		return this.travelInd;
	}

	public void setTravelInd(String travelInd) {
		this.travelInd = travelInd;
	}

	public String getOutOfStateInd() {
		return this.outOfStateInd;
	}

	public void setOutOfStateInd(String outOfStateInd) {
		this.outOfStateInd = outOfStateInd;
	}

	public String getExpenseType() {
		return this.expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public BigDecimal getAmtGreater() {
		return amtGreater;
	}

	public void setAmtGreater(BigDecimal amtGreater) {
		this.amtGreater = amtGreater;
	}

	public BigDecimal getAmtLesser() {
		return amtLesser;
	}

	public void setAmtLesser(BigDecimal amtLesser) {
		this.amtLesser = amtLesser;
	}

	public Date getPayAmtFrm() {
		return payAmtFrm;
	}

	public void setPayAmtFrm(Date payAmtFrm) {
		this.payAmtFrm = payAmtFrm;
	}

	public Date getPayAmtTo() {
		return payAmtTo;
	}

	public void setPayAmtTo(Date payAmtTo) {
		this.payAmtTo = payAmtTo;
	}

	public float getRandPer() {
		return randPer;
	}

	public void setRandPer(float randPer) {
		this.randPer = randPer;
	}

}
