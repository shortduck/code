package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name = "WEB_REPORT_PARAMS")

public class WebReportParams implements Serializable {

	@Id
    @SequenceGenerator(name = "WEB_REPORT_PARAMS_GENERATOR", sequenceName = "REPT_IDENTIFIER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WEB_REPORT_PARAMS_GENERATOR")
    @Column(name = "REPT_IDENTIFIER")
    private Integer reptIdentifier;
    
    @Column(name = "USER_ID")
    private String userId;

	@Column(name = "REQUEST_DATE")
	private Date requestDate;
	
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "AGENCY")
    private String agency;
    
    @Column(name = "TKU")
    private String tku;
    
    @Column(name = "EMP_IDENTIFIER")
    private String empIdentifier;
    
    @Column(name = "SUP_EMP_IDENTIFIER")
    private Integer supEmpIdentifier;
    
	@Column(name = "PMT_DATE_FROM")
	private Date pmtDateFrom;
	
	@Column(name = "PMT_DATE_TO")
	private Date pmtDateTo;
	
	@Column(name = "EXP_DATE_FROM")
	private Date expDateFrom;
	
	@Column(name = "EXP_DATE_TO")
	private Date expDateTo;
	
    @Column(name = "REPORT")
    private String report;
	
    @Column(name = "REPORT_TYPE")
    private String reportType;
	
    @Column(name = "LONG_TERM_ADV_IND")
    private String longtermAdvInd;
    
    @Column(name = "ADJ_IND")
    private String adjInd;
    
    @Column(name = "UNPAID_EXP_IND")
    private String unpaidExpInd;
    
    @Column(name = "RUNNING_IND")
    private String runningInd;
    
    @Column(name = "REPORT_FILE_PATH")
    private String reportFilePath;
    
    @Column(name = "REPORT_VIEWED_IND")
    private String reportViewedInd;
    
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	
	@Transient
	private String requesDateTime;
	
	@Transient
	private String completedDateTime;
	
	@Column(name = "REPORT_NAME")
	private String reportName;
	
    public WebReportParams() {
    }
    
	public WebReportParams(String userId, Date requestDate,
			String department, String agency, String tku,
			String empIdentifier, Integer supEmpIdentifier,
			Date pmtDateFrom, Date pmtDateTo, Date expDateFrom, Date expDateTo, String report,
			String reportType, String longtermAdvInd, String adjInd, String unpaidExpInd, String reportName) {
		this.userId = userId;
		this.requestDate = requestDate;
		this.department = department;
		this.agency = agency;
		this.tku = tku;
		this.empIdentifier = empIdentifier;
		this.supEmpIdentifier = supEmpIdentifier;
		this.pmtDateFrom = pmtDateFrom;
		this.pmtDateTo = expDateTo;
		this.expDateFrom = expDateFrom;
		this.expDateTo = expDateTo;
		this.report = report;
		this.reportType = reportType;
		this.longtermAdvInd = longtermAdvInd;
		this.adjInd = adjInd;
		this.unpaidExpInd = unpaidExpInd;
		this.reportName = reportName;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public Integer getReptIdentifier() {
		return reptIdentifier;
	}

	public void setReptIdentifier(Integer reptIdentifier) {
		this.reptIdentifier = reptIdentifier;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getTku() {
		return tku;
	}

	public void setTku(String tku) {
		this.tku = tku;
	}

	public String getEmpIdentifier() {
		return empIdentifier;
	}

	public void setEmpIdentifier(String empIdentifier) {
		this.empIdentifier = empIdentifier;
	}

	public Integer getSupEmpIdentifier() {
		return supEmpIdentifier;
	}

	public void setSupEmpIdentifier(Integer supEmpIdentifier) {
		this.supEmpIdentifier = supEmpIdentifier;
	}

	public Date getPmtDateFrom() {
		return pmtDateFrom;
	}

	public void setPmtDateFrom(Date pmtDateFrom) {
		this.pmtDateFrom = pmtDateFrom;
	}

	public Date getPmtDateTo() {
		return pmtDateTo;
	}

	public void setPmtDateTo(Date pmtDateTo) {
		this.pmtDateTo = pmtDateTo;
	}

	public Date getExpDateFrom() {
		return expDateFrom;
	}

	public void setExpDateFrom(Date expDateFrom) {
		this.expDateFrom = expDateFrom;
	}

	public Date getExpDateTo() {
		return expDateTo;
	}

	public void setExpDateTo(Date expDateTo) {
		this.expDateTo = expDateTo;
	}

	public String getLongtermAdvInd() {
		return longtermAdvInd;
	}

	public void setLongtermAdvInd(String longtermAdvInd) {
		this.longtermAdvInd = longtermAdvInd;
	}

	public String getAdjInd() {
		return adjInd;
	}

	public void setAdjInd(String adjInd) {
		this.adjInd = adjInd;
	}

	public String getUnpaidExpInd() {
		return unpaidExpInd;
	}

	public void setUnpaidExpInd(String unpaidExpInd) {
		this.unpaidExpInd = unpaidExpInd;
	}

	public String getRunningInd() {
		return runningInd;
	}

	public void setRunningInd(String runningInd) {
		this.runningInd = runningInd;
	}

	public String getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(String reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public String getReportViewedInd() {
		return reportViewedInd;
	}

	public void setReportViewedInd(String reportViewedInd) {
		this.reportViewedInd = reportViewedInd;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getRequesDateTime() {
		return requesDateTime;
	}

	public void setRequesDateTime(String requesDateTime) {
		this.requesDateTime = requesDateTime;
	}

	public String getCompletedDateTime() {
		return completedDateTime;
	}

	public void setCompletedDateTime(String completedDateTime) {
		this.completedDateTime = completedDateTime;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
}
