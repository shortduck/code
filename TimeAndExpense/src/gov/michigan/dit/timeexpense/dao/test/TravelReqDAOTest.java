package gov.michigan.dit.timeexpense.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import gov.michigan.dit.timeexpense.dao.TravelRequisitionDAO;
import gov.michigan.dit.timeexpense.model.core.AdvanceActions;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseActions;
import gov.michigan.dit.timeexpense.model.core.ExpenseEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseHistory;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.TravelReqActions;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetailCodingBlock;
import gov.michigan.dit.timeexpense.model.core.TravelReqDetails;
import gov.michigan.dit.timeexpense.model.core.TravelReqEvents;
import gov.michigan.dit.timeexpense.model.core.TravelReqHistory;
import gov.michigan.dit.timeexpense.model.core.TravelReqMasters;
import gov.michigan.dit.timeexpense.model.core.TravelReqOutOfState;
import gov.michigan.dit.timeexpense.model.core.VTravelReqList;
import gov.michigan.dit.timeexpense.model.display.TravelReqListBean;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TravelReqDAOTest extends AbstractDAOTest {

	private TravelRequisitionDAO treqDAO = new TravelRequisitionDAO();

	public TravelReqDAOTest() {
		// TODO Auto-generated constructor stub
	}

	@Before
	public void startTransaction() {
		super.beginTransaction();
		treqDAO.setEntityManager(em);

	}

	@Test
	public void testFindTravelReqsByEmployeeId() {

		int employeeId = 1077335;
		List<VTravelReqList> expenseListBean = treqDAO
				.findTravelReqsByEmployeeId(employeeId);

		assertFalse(expenseListBean.isEmpty());
		System.out.println(expenseListBean.size());
	}
	
	@Test
	public void testFindTravelReqsByAppointmentId() {
		List<TravelReqListBean> treqListBean = treqDAO.findTravelReqsByAppointmentId(1139330, "CHIDURAS", "EXPW002");
		Assert.assertTrue(treqListBean.size()>0);
	}
	
	@Test
	public void testSaveExpense() {
		TravelReqEvents treqEvent = new TravelReqEvents();
		treqEvent.setApptIdentifier(1139330);

		TravelReqMasters treqMaster = new TravelReqMasters();
		List<TravelReqMasters> treqMasterList = new ArrayList<TravelReqMasters>();

		treqMaster.setTreqDateFrom(Calendar.getInstance().getTime());
		treqMaster.setTreqDateTo(Calendar.getInstance().getTime());
		treqMaster.setCurrentInd("Y");
		treqMaster.setOutOfStateInd("N");
		treqMaster.setRevisionNumber(0);
		treqMaster.setTransportationVia("car");
		treqMaster.setTreqeIdentifier(treqEvent);
				
		TravelReqOutOfState ofsTravel = treqDAO.getEntityManager().find(TravelReqOutOfState.class, 1);
		
		List<TravelReqOutOfState> outofStateList = new ArrayList<TravelReqOutOfState>();
		outofStateList.add(ofsTravel);
		
		treqMaster.setTravelReqOutOfStateCollection(outofStateList);
		//treqMaster.setExpenseErrorsCollection(new ArrayList<ExpenseErrors>());

		
		treqMasterList.add(treqMaster);
		treqEvent.setTravelReqMastersCollection(treqMasterList);

		TravelReqEvents treqEventSaved  = treqDAO.saveTravelRequisition(treqEvent);

		Assert.assertNotNull(treqEventSaved);
	}
	
	@Test
	public void testDeleteRequisition() {
		
		TravelReqEvents treqEvent = treqDAO.findTravelRequisitionEvent(9999999);
		boolean deleted = treqDAO.deleteTravelRequisition(treqEvent.getTreqeIdentifier());
		Assert.assertEquals(true, deleted);

	}
	
	@Test
	public void testFindTravelRequisitonByMasterId() {
		TravelReqMasters master = treqDAO.findTravelRequisitonByMasterId(154);
		assertNotNull(master);
	}
	
	@Test
	public void testFindTravelRequisitionByEventId() {
		TravelReqMasters master = treqDAO.findTravelRequisitionByEventId(9999999, 0);
		assertNotNull(master);
	}
	
	@Test
	public void testFindTravelRequisitionActions() {
		TravelReqMasters master = treqDAO.findTravelRequisitonByMasterId(9999999);
		List<TravelReqActions> actions = treqDAO.findTravelRequisitionActions(master);
		assertTrue(actions.size() == 0);
	}
	
	@Test
	public void testFindMaxRevisionNo() {
		int revision = treqDAO.findMaxRevisionNo(9999999);
		assertTrue(revision == 0);
	}
	
	@Test
	public void testFindTravelRequisitionHistory() {
		List<TravelReqHistory> treqHistoryList= treqDAO.findTravelRequisionHistory(150);
		Assert.assertTrue(!treqHistoryList.isEmpty());
		
	}
	
    @Test
    public void testGetLatestAction (){

    	TravelReqMasters treqMaster = treqDAO.findTravelRequisitonByMasterId(96);
    	List<TravelReqActions> treqActionsList =  treqDAO.findLatestAction(treqMaster);
    	String lastAction = treqActionsList.get(0).getActionCode();
    	assertEquals(lastAction, "SUBM");
    }
    
    @Test
    public void testFindNextActionCode() {
    TravelReqMasters treqMaster = treqDAO.findTravelRequisitonByMasterId(96);
    List<TravelReqActions> actionsList = treqDAO.findNextAction(treqMaster);
	assertFalse(actionsList.isEmpty());

    }
    
    @Test
    public void testFindTravelRequisitionCount() {
    long count = treqDAO.findTravelRequisitionCount(233033);
	assertTrue(count > 0);

    }
    
    @Test
    public void testFindTravelRequisitionCbs() {
    	
    	TravelReqDetails treqDetail =em.find(TravelReqDetails.class, 67);
    	List<TravelReqDetailCodingBlock> cbList = treqDAO.findCodingBlocks(treqDetail);
	assertTrue(cbList.size() > 1);

    }
    @Test
    public void testFindLatestTreqAction() {
    	
    	TravelReqMasters treqMaster =em.find(TravelReqMasters.class, 392);
    	TravelReqActions latestAction = treqDAO.findLatestTreqAction(treqMaster);
	assertTrue(latestAction.getActionCode().equals("AOSW"));
    }
    
	@Test
	public void testFindPrevTreqInStatus(){
		TravelReqEvents treqEvents = treqDAO.findTravelRequisition(268);
		TravelReqMasters treqMaster = treqDAO.findPrevTreqInStatus(treqEvents, IConstants.APPROVED, 5);
		Assert.assertNotNull(treqMaster);
	}
	
	@Test
	public void testFindTravelRequisitionCurrent(){
		TravelReqEvents treqEvents = treqDAO.findTravelRequisition(295768);
		TravelReqMasters treqMaster = treqDAO.findTravelRequisitionCurrent(treqEvents.getTreqeIdentifier());
		Assert.assertEquals(treqMaster.getTreqmIdentifier(), 482);
	}
	
	@Test
	public void testFindPrevTreqApprovalAction(){
		TravelReqMasters treqMaster = treqDAO.findTravelRequisitonByMasterId(401);
		List<TravelReqActions> actionsList = treqDAO.findPrevTreqApprovalAction(treqMaster.getTreqeIdentifier(), treqMaster.getRevisionNumber());
		Assert.assertFalse(actionsList.isEmpty());
	}
}
