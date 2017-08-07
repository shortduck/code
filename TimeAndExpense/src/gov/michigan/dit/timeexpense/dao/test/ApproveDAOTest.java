package gov.michigan.dit.timeexpense.dao.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.michigan.dit.timeexpense.dao.ApproveDAO;
import gov.michigan.dit.timeexpense.model.core.VAdvApprovalList;
import gov.michigan.dit.timeexpense.model.core.VExpApprovalList;
import gov.michigan.dit.timeexpense.model.core.VTreqApprovalList;
import gov.michigan.dit.timeexpense.model.display.ApprovalTransactionBean;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ApproveDAOTest extends AbstractDAOTest {
    private ApproveDAO approveDAO = new ApproveDAO();

   // private ExpenseDAO expenseDAO = new ExpenseDAO();

    public ApproveDAOTest() {
	
    }

    @Before
    public void startTransaction() {
	super.beginTransaction();
	approveDAO.setEntityManager(em);
	//expenseDAO.setEntityManager(em);
    }
    
    @Test
    public void testFindAdvancesAwaitingApproval() {
//134874
	List<VAdvApprovalList> advances = approveDAO
		.findAdvancesAwaitingApproval(133039);

	assertNotNull(advances);

	System.out.println(advances.size());
	System.out.println(advances.get(0).getApptIdentifier());
    }

    @Test
    public void testFindAdvancesAwaitingApproval_Invalid() {

	List<VAdvApprovalList> advances = approveDAO
		.findAdvancesAwaitingApproval(999999);

	assertTrue(advances.isEmpty());


	
    }
    
    @Test
	public void testFindAllAdvancesAwaitingApprovalAllEmployees(){
		List<ApprovalTransactionBean> advances = approveDAO.findAllAdvancesAwaitingApprovalAllEmployees("T_HRMND99");
		Assert.assertTrue(!advances.isEmpty());
	}
    
    
   /* @Test
	public void testFindAdjustedAdvancesAwaitingApprovalAllEmployees(){
		List<ApprovalTransactionBean> advances = approveDAO.findAdjustedAdvancesAwaitingApprovalAllEmployees("T_HRMND99");
		Assert.assertTrue(!advances.isEmpty());
	}*/

    
    @Test
    public void testFindAdjustedAdvancesAwaitingApproval() {

	List<VAdvApprovalList> advances = approveDAO
		.findAdjustedAdvancesAwaitingApproval(133039);

	//System.out.println(advances.get(0).getApptIdentifier());
	assertNotNull(advances);

    }

    @Test
    public void testFindNonAdjustedAdvancesAwaitingApproval() {

	List<VAdvApprovalList> advances = approveDAO
		.findNonAdjustedAdvancesAwaitingApproval(135365);
	//System.out.println(advances.get(0).getApptIdentifier());
	assertNotNull(advances);

	
    }

    /*@Test
    public void testFindSwAdvancesAwaitingApproval() {
	List<ApprovalTransactionBean> advances = approveDAO
		.findSwAdvancesAwaitingApproval("T_HRMND99");

	assertNotNull(advances);

    }*/

    @Test
    public void testFindSwAdvancesAwaitingApproval_WithDept() {

	List<ApprovalTransactionBean> advances = approveDAO
		.findSwAdvancesAwaitingApproval("T_HRMND99", "47");

	assertNotNull(advances);
	System.out.println(advances.get(0).getEmpIdentifier());

    }

    @Test
    public void testFindSwAdvancesAwaitingApproval_WithDeptAgy() {

	List<ApprovalTransactionBean> advances = approveDAO
		.findSwAdvancesAwaitingApproval("T_HRMND99", "47", "02");

	assertNotNull(advances);
	System.out.println(advances.get(0).getEmpIdentifier());

    }

    @Test
    public void testFindSwAdvancesAwaitingApproval_WithDeptAgyTku() {

	List<ApprovalTransactionBean> advances = approveDAO
		.findSwAdvancesAwaitingApproval("T_HRMND99", "47", "02", "810");

	assertNotNull(advances);
	System.out.println(advances.get(0).getEmpIdentifier());

    }

    @Test
    public void testFindSwAdvancesAwaitingApproval_WithEmpID() {

	List<ApprovalTransactionBean> advances = approveDAO
		.findSwAdvancesAwaitingApprovalEmp("T_HRMND99", "000134874", "47");

	assertNotNull(advances);
	System.out.println(advances.get(0).getEmpIdentifier());

    }

    @Test
    public void testFindSwAdvancesAwaitingApproval_WithDeptAgyTkuLastName() {

	List<ApprovalTransactionBean> advances = approveDAO
		.findSwAdvancesAwaitingApproval("T_HRMND99", "47", "02", "810",
			"A%");

	assertNotNull(advances);
	System.out.println(advances.get(0).getFirstName());

    }
    
    @Test
    public void testFindSwAdvancesAwaitingApproval_WithDeptAgyTkuLastName2() {

	List<ApprovalTransactionBean> advances = approveDAO
		.findSwAdvancesAwaitingApproval("T_DEPT99", "59", "01", "100",
			"COBB");

	assertNotNull(advances);
	System.out.println(advances.get(0).getFirstName());

    }
    @Test
	public void testFindExpensesAwaitingApproval_supervisorEmpId() {

		List<VExpApprovalList> approvalListBean = null;
		int supervisorEmpId = 133509;

		approvalListBean = approveDAO.findExpensesAwaitingApproval(supervisorEmpId);
		assertNotNull(approvalListBean);
		Assert.assertTrue(approvalListBean.size()>0);

	}
	
    /******************************************** Statewide Expenses Awaiting Approval ******************************************************************************************/
	@Test
	public void testFindExpensesAwaitingApproval_DeptAgencyTkuLastname() {

		List<ApprovalTransactionBean> approvalListBean = null;
		String userId = "T_HRMND99";
		String dept = "47";
		String agency = "19";
		String tku = "230";
		String lastname = "KRASNICKI";

		approvalListBean = approveDAO.findStateWideExpensesAwaitingApproval(dept,agency, tku, lastname, userId);
		assertNotNull(approvalListBean);
		//System.out.println(approvalListBean.get(0).getApptIdentifier());
	}

	@Test
	public void testFindStateWideExpensesAwaitingApproval_userIdDeptAgency() {

		List<ApprovalTransactionBean> approvalListBean = null;
		String userId = "T_HRMND99";
		String dept = "47";
		String agency = "19";

		approvalListBean = approveDAO.findStateWideExpensesAwaitingApproval(userId,dept,agency);
		assertNotNull(approvalListBean);
	}
	
	@Test
	public void testFindStateWideExpensesAwaitingApproval_userIdDeptAgencyTku() {

		List<ApprovalTransactionBean> approvalListBean = null;
		String userId = "T_HRMND99";
		String dept = "47";
		String agency = "19";
		String tku = "230";

		approvalListBean = approveDAO.findStateWideExpensesAwaitingApproval(userId,dept,agency,tku);
		assertNotNull(approvalListBean);
		System.out.println(approvalListBean.get(0).getEmpIdentifier());
	}
	
	@Test
	public void testFindStateWideExpensesAwaitingApproval_userIdDeptAgencyTkuAllAL() {

		List<ApprovalTransactionBean> approvalListBean = null;
		String userId = "T_HRMND99";
		approvalListBean = approveDAO.findStateWideExpensesAwaitingApproval(userId);
		assertNotNull(approvalListBean);
	}
	
	@Test
	public void testFindStateWideExpensesAwaitingApproval_userIdDeptAgencyTkuAL() {

		List<ApprovalTransactionBean> approvalListBean = null;
		String userId = "T_HRMND99";
		String dept = "47";
		String agency = "19";

		approvalListBean = approveDAO.findStateWideExpensesAwaitingApproval(userId,dept,agency);
		assertNotNull(approvalListBean);
	}
	
	@Test
	public void testFindStateWideExpensesAwaitingApproval_userIdDeptAgencyALtkuAL() {

		List<ApprovalTransactionBean> approvalListBean = null;
		String userId = "T_HRMND99";
		String dept = "47";

		approvalListBean = approveDAO.findStateWideExpensesAwaitingApproval(userId,dept);
		assertNotNull(approvalListBean);
	}
	
	@Test
	public void testFindStateWideExpensesAwaitingApproval_userIdEmpId() {

		List<ApprovalTransactionBean> approvalListBean = null;
		String userId = "T_HRMND99";
		String empId = "133342";

		approvalListBean = approveDAO.findStateWideExpensesAwaitingApprovalEmployee(userId,empId, "47");
		assertNotNull(approvalListBean);
	}

	

	
	@Test
	public void testFindAllExpensesAwaitingApprovalAllEmployees(){
		List<ApprovalTransactionBean> expenseApprovalList = approveDAO.findAllExpensesAwaitingApprovalAllEmployees("T_HRMND99");
		Assert.assertTrue(!expenseApprovalList.isEmpty());
	}

	@Test
	public void testFindAdjustedExpensesAwaitingApprovalAllEmployees(){
		List<ApprovalTransactionBean> expenseApprovalList = approveDAO.findAdjustedExpensesAwaitingApprovalAllEmployees("T_HRMND99");
		Assert.assertNotNull(expenseApprovalList);
	}

	@Test
	public void testFindNonAdjustedExpensesAwaitingApprovalAllEmployees(){
		List<ApprovalTransactionBean> expenseApprovalList = approveDAO.findNonAdjustedExpensesAwaitingApprovalAllEmployees("T_HRMND99");
		Assert.assertTrue(!expenseApprovalList.isEmpty());
	}

	@Test
	public void testIsExpenseInApprovalPathAfterFirstLevelApproval(){
		Assert.assertTrue(approveDAO.isExpenseInApprovalPathAfterFirstLevelApproval("APPR"));
	}
	@Test
	public void testFindAllTravelRequisitionsAwaitingApprovalAllEmployees(){
		List<ApprovalTransactionBean> treqApprovalList = approveDAO.findAllTravelRequisitionsAwaitingApprovalAllEmployees("CHIDURAS");
		Assert.assertTrue(!treqApprovalList.isEmpty());
	}

	@Test
	public void testFindStateWideTravelRequisitionsAwaitingApproval_userIdDeptAgencyALtkuAL() {

		List<ApprovalTransactionBean> approvalListBean = null;
		String userId = "CHIDURAS";
		String dept = "08";

		approvalListBean = approveDAO.findStateWideTravelRequisitionsAwaitingApproval(userId,dept);
		Assert.assertFalse(approvalListBean.isEmpty());
	}
	
    @Test
	public void testFindTravelRequisitionsAwaitingApproval_supervisorEmpId() {

		List<VTreqApprovalList> approvalListBean = null;
		int supervisorEmpId = 161136;

		approvalListBean = approveDAO.findTravelRequisitionsAwaitingApproval(supervisorEmpId);
		Assert.assertTrue(approvalListBean.size()>0);

	}
}
