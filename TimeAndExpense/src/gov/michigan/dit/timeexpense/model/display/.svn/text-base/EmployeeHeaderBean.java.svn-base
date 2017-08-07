package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;

public class EmployeeHeaderBean implements Serializable {
	
	private static final long serialVersionUID = -210966246465713985L;

	private String empName;
	private String processLevel;
	private String deptCode;
	private String deptName;
	private int empId;
	private int apptId;
	private String bargUnit;//AI-29299 Added bargaining unit 
	public EmployeeHeaderBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmployeeHeaderBean(String deptCode, String deptName, int empId,
			String empName, String processLevel) {
		super();
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.empId = empId;
		this.empName = empName;
		this.processLevel = processLevel;
		this.bargUnit = bargUnit;//AI-29299 Added bargaining unit 
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getProcessLevel() {
		return processLevel;
	}
	public void setProcessLevel(String processLevel) {
		this.processLevel = processLevel;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	// used for employee header
	public String getDisplayName(){
		return deptCode + " " + deptName;
	}
	public void setApptId(int apptId) {
	    this.apptId = apptId;
	}
	public int getApptId() {
	    return apptId;
	}
	//AI-29299 Added bargaining unit 
	public String getBargUnit() {
		return bargUnit;
	}
	//AI-29299 Added bargaining unit 
	public void setBargUnit(String bargUnit) {
		this.bargUnit = bargUnit;
	}
	
}
