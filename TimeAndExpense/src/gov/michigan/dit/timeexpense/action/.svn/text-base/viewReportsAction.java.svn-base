package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.WebReportParams;
import gov.michigan.dit.timeexpense.service.ReportsDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.businessobjects.samples.CrystalReportsParms;

@SuppressWarnings("serial")
public class viewReportsAction extends AbstractAction {
	
	private List<WebReportParams> reportsList;
	private ReportsDSP reportsService;
	
	@Override
	public void prepare() {
		reportsService = new ReportsDSP(entityManager);
		
	}

	@Override
	public String execute() throws Exception {

		reportsList = reportsService.getCompletegReports(getLoggedInUser().getUserId());
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		ServletContext context = ServletActionContext.getServletContext();
		File v = new File(context.getRealPath("/"));
	    String text3 = "\n Virtual Root Directory: "+v.getAbsolutePath();
	    System.out.println(text3);
		Map<String, Object> cache = (Map<String, Object>) context.getAttribute(IConstants.APPLICATION_CACHE);
		CrystalReportsParms crystalParms = (CrystalReportsParms) cache.get(IConstants.CrystalReportsParms);
		FileHelper.fileRead(crystalParms.getReportsLocation(), v.getAbsolutePath(), getLoggedInUser().getUserId());
	//	return df.format(requestDate);

		for (WebReportParams item: reportsList){
			item.setRequesDateTime(df.format(item.getRequestDate()));
			if (item.getReportFilePath() != null){
				if (item.getModifiedDate() != null){
					item.setCompletedDateTime(df.format(item.getModifiedDate()));
				}
				else{
					item.setCompletedDateTime(df.format(item.getModifiedDate()));
				}
			}
		}
		return "success";
	}

	public List<WebReportParams> getReportsList() {
		return reportsList;
	}

	public void setReportsList(List<WebReportParams> reportsList) {
		this.reportsList = reportsList;
	}	
	
}
