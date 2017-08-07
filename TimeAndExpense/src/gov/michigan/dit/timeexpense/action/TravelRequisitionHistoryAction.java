package gov.michigan.dit.timeexpense.action;

import java.util.List;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import gov.michigan.dit.timeexpense.model.core.TravelReqHistory;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.service.TravelRequisitionDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

/**
 * Action class for the expense History tab
 * @author kurtzj
 */
public class TravelRequisitionHistoryAction extends AbstractAction implements	ServletRequestAware {
	private static final long serialVersionUID = 8357771470600623493L;
	private TravelRequisitionDSP treqService = null;
	private List<TravelReqHistory> treqHistoryList = null;
	private TravelReqMasters treqMaster;
	private String status = "";
	private static final Logger logger = Logger.getLogger(TravelRequisitionHistoryAction.class);

	@Override
	public void prepare() {
		treqService = new TravelRequisitionDSP(entityManager);
	}

	/**
	 * Gets the Expenses History.
	 * @return success:Find mapping in struts.xml
	 */
	public String getTravelRequisitionHistoryList() {
		// default method invoked when this action is called - struts framework rule
		if (logger.isDebugEnabled())
			logger.debug("inside expense history action");
		String status = "";
		treqMaster = (TravelReqMasters) session.get(IConstants.TRAVEL_REQUISITION_SESSION_DATA);

		if (treqMaster == null) {
			super.setJsonResponse(jsonParser.toJson(treqHistoryList));
		} else {
			treqHistoryList = treqService.getTravelRequisitionHistory(treqMaster.getTreqeIdentifier().getTreqeIdentifier());
			super.setJsonResponse(jsonParser.toJson(treqHistoryList));
			status = treqMaster.getStatus();
			String nextActionCode = null;
			nextActionCode = treqService.getNextActionCode(treqMaster);
			if (nextActionCode != null) {
				setStatus(treqService.getRemainingApprovalPaths(
						treqMaster.getTreqmIdentifier(), getUserSubject()));
			}			
			else
				if (status != null) {
					if (status.equals(IConstants.APPROVED)
							|| status.equals(IConstants.EXTRACTED)
							|| status.equals(IConstants.HOURS_ADJUSTMENT_SENT)) {
						setStatus(IConstants.HISTORYMSG_APPROVED_EXTRACTED_HOURS_ADJUSTMENT_SENT);
					} else if (status.equals(IConstants.PROCESSED)) {
						setStatus(IConstants.HISTORYMSG_PROCESSED);
					} else if (status.equals(IConstants.REJECTED)) {
						setStatus(IConstants.HISTORYMSG_REJECTED);
					}
				}
				else{
					setStatus(IConstants.HISTORYMSG_NEEDSTOBESUBMITTED);
				}	
		}
		
		return IConstants.SUCCESS;
	}

	public TravelReqMasters getTreqMaster() {
		return treqMaster;
	}

	public void setTreqMaster(TravelReqMasters treqMaster) {
		this.treqMaster = treqMaster;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
