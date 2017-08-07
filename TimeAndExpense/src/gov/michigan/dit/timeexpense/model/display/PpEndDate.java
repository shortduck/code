package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;
import java.util.Date;

public class PpEndDate implements Serializable {

private static final long serialVersionUID = 4845844216167403690L;

private Date ppEndDate;
private String processDay;
private Date currPpEndDate;

public void setPpEndDate(Date ppEndDate) {
	this.ppEndDate = ppEndDate;
}
public Date getPpEndDate() {
	return ppEndDate;
}
public void setProcessDay(String processDay) {
	this.processDay = processDay;
}
public String getProcessDay() {
	return processDay;
}
public void setCurrPpEndDate(Date currPpEndDate) {
	this.currPpEndDate = currPpEndDate;
}
public Date getCurrPpEndDate() {
	return currPpEndDate;
}
}
