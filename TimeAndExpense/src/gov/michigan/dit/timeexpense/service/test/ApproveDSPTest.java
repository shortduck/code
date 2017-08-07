/**
 * 
 */
package gov.michigan.dit.timeexpense.service.test;

import static org.junit.Assert.assertTrue;
import gov.michigan.dit.timeexpense.dao.ApproveDAO;
import gov.michigan.dit.timeexpense.model.core.VAdvApprovalList;
import gov.michigan.dit.timeexpense.model.core.VExpApprovalList;
import gov.michigan.dit.timeexpense.model.display.AdvApprovalTransaction;
import gov.michigan.dit.timeexpense.model.display.ApprovalTransactionBean;
import gov.michigan.dit.timeexpense.model.display.ExpenseApprovalTransactionBean;
import gov.michigan.dit.timeexpense.service.ApproveDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author chiduras
 *
 */
public class ApproveDSPTest {
    

	private ApproveDSP approveDSP;
	private static EntityManagerFactory emf;
	private EntityManager em;
	private ApproveDAO approveDao = new ApproveDAO();
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if(emf != null && emf.isOpen())
			emf.close();
	}
	
	@Before
	public void setUp() throws Exception {
		
		
		em = emf.createEntityManager();
		approveDSP = new ApproveDSP (em);
		em.getTransaction().begin();		
	}

	@After
	public void tearDown() throws Exception {
		if (em.getTransaction().isActive())
			em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorMyEmployees() {
		List<VAdvApprovalList> advances = approveDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);
		assertTrue(!advances.isEmpty());
	}	
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorMyEmployeesAdjusted() {
		List<VAdvApprovalList> advances = approveDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_ADJUSTED_ONLY);
		assertTrue(!advances.isEmpty());
	}	
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorMyEmployeesNonAdjusted() {
		List<VAdvApprovalList> advances = approveDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY);
		assertTrue(!advances.isEmpty());
	}	
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorAllEmployees() {
		List<VAdvApprovalList> advances = approveDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED);
		assertTrue(!advances.isEmpty());
	}
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorAllEmployeesAdjusted() {
		List<VAdvApprovalList> advances = approveDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_ADJUSTED_ONLY);
		assertTrue(!advances.isEmpty());
	}	
	
	@Test
	public void testGetAdvancesAwaitingApprovalSupervisorAllEmployeesNonAdjusted() {
		List<VAdvApprovalList> advances = approveDSP.getAdvancesAwaitingApprovalSupervisorMyEmployees(133039, IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY);
		assertTrue(!advances.isEmpty());
	}
	
	@Test
	public void testGetAllSwAdvancesAwaitingApprovalDeptAgencyTku() {
		
		String requestType = IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED;
		String empId = "";
		String userId = "T_HRMND99";
		String moduleId = "ADVW002";
		String lastName = "";
		String dept = "47";
		String agency = "02";
		String tku = "810";

		List<ApprovalTransactionBean> advances = approveDSP
			.getAllStateWideAdvancesAwaitingApproval(dept, agency, tku, lastName, requestType, userId,  empId);

		assertTrue(!advances.isEmpty());

	    }
	
	@Test
	public void testGetAllSwAdvancesAwaitingApprovalDeptAgencyTkuNonAdjusted() {
		
		String requestType = IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY;
		String empId = "";
		String userId = "T_HRMND99";
		String moduleId = "ADVW002";
		String lastName = "";
		String dept = "47";
		String agency = "02";
		String tku = "810";

		List<ApprovalTransactionBean> advances = approveDSP
			.getAllStateWideAdvancesAwaitingApproval(dept, agency, tku, lastName, requestType, userId, empId);

		assertTrue(!advances.isEmpty());

	    }
	
	@Test
	public void testGetAllSwAdvancesAwaitingApprovalDeptAgencyTkuAdjusted() {
		
		String requestType = IConstants.TRANSACTION_LIST_ADJUSTED_ONLY;
		String empId = "";
		String userId = "T_HRMND99";
		String moduleId = "ADVW002";
		String lastName = "";
		String dept = "47";
		String agency = "02";
		String tku = "810";

		List<ApprovalTransactionBean> advances = approveDSP
			.getAllStateWideAdvancesAwaitingApproval(dept, agency, tku, lastName, requestType, userId, empId);

		assertTrue(!advances.isEmpty());

	    }

	
	
	@Test
	public void testGetExpensesAwaitingApproval() {
		int supervisorEmpId = 1;
		String requestType = IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED;
		approveDSP.getExpensesAwaitingApproval(supervisorEmpId, requestType);
	}
	
	@Test
	public void testGetExpensesAwaitingApproval_Adjusted() {

		int supervisorEmpId =134720;
		String requestType = IConstants.TRANSACTION_LIST_ADJUSTED_ONLY;
		List<VExpApprovalList> expenseApprovalList = approveDSP.getExpensesAwaitingApproval(supervisorEmpId, requestType);
		Assert.assertNotNull(expenseApprovalList);
	}
	
	
	@Test
	public void testGetExpensesAwaitingApproval_NonAdjusted() {

		int supervisorEmpId = 134720;
		String requestType = IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY;
		
		List<VExpApprovalList> expenseApprovalList = approveDSP.getExpensesAwaitingApproval(supervisorEmpId, requestType);
		Assert.assertNotNull(expenseApprovalList);
		//expenseDSP.getExpensesAwaitingApproval(empId, requestType1, userId,moduleId);
	}
	
	
	@Test
	public void testGetAllSwExpensesAwaitingApproval_empId() {

		String requestType = IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED;
		String empId = "133342";
		String userId = "T_HRMND99";
		String moduleId = "PRMW007";
		String lastName = "";
		String dept = "";
		String agency = "";
		String tku = "";

		List<ApprovalTransactionBean> expApproveList = new ArrayList<ApprovalTransactionBean>();
		expApproveList =   approveDSP.getAllSwExpensesAwaitingApproval(dept, agency, tku, lastName, requestType, userId, empId);
		Assert.assertTrue(!expApproveList.isEmpty());
	}
	
	@Test
	public void testGetAllSwExpensesAwaitingApproval_DeptAgencyTkuAllAL() {

		String requestType = IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED;
		String empId = "";
		String userId = "T_HRMND99";
		String moduleId = "PRMW027";
		String lastName = "";
		String dept = "AL";
		String agency = "AL";
		String tku = "AL";

		List<ApprovalTransactionBean> expApproveList =   approveDSP.getAllSwExpensesAwaitingApproval(dept, agency, tku, lastName, requestType, userId,  empId);
		Assert.assertTrue(!expApproveList.isEmpty());
	}
	
	@Test
	public void testGetAllSwExpensesAwaitingApproval_DeptAgencyTku() {

		String requestType = IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED;
		String empId = "";
		String userId = "T_HRMND99";
		String moduleId = "PRMW027";
		String lastName = "";
		String dept = "47";
		String agency = "19";
		String tku = "230";

		List<ApprovalTransactionBean> expApproveList =   approveDSP.getAllSwExpensesAwaitingApproval(dept, agency, tku, lastName, requestType, userId, empId);
		Assert.assertTrue(!expApproveList.isEmpty());
	}
	
	@Test
	public void testGetAllSwExpensesAwaitingApproval_DeptAgencyTkuAdjusted() {

		String requestType = IConstants.TRANSACTION_LIST_ADJUSTED_ONLY;
		String empId = "";
		String userId = "T_HRMND99";
		String moduleId = "PRMW027";
		String lastName = "";
		String dept = "47";
		String agency = "19";
		String tku = "230";

		List<ApprovalTransactionBean> expApproveList =   approveDSP.getAllSwExpensesAwaitingApproval(dept, agency, tku, lastName, requestType, userId, empId);
		Assert.assertTrue(!expApproveList.isEmpty());
	}
	
	@Test
	public void testGetAllSwExpensesAwaitingApproval_DeptAgencyTkuNonAdjusted() {

		String requestType = IConstants.TRANSACTION_LIST_NON_ADJUSTED_ONLY;
		String empId = "";
		String userId = "T_HRMND99";
		String moduleId = "PRMW027";
		String lastName = "";
		String dept = "03";
		String agency = "14";
		String tku = "001";

		List<ApprovalTransactionBean> expApproveList =   approveDSP.getAllSwExpensesAwaitingApproval(dept, agency, tku, lastName, requestType, userId, empId);
		Assert.assertTrue(!expApproveList.isEmpty());
	}
	
	
	
	@Test
	public void testGetAllSwExpensesAwaitingApproval_DeptAgencyTkuAL() {

		String requestType = IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED;
		String empId = "";
		String userId = "T_HRMND99";
		String moduleId = "PRMW027";
		String lastName = "";
		String dept = "47";
		String agency = "19";
		String tku = "AL";

		List<ApprovalTransactionBean> expApproveList =   approveDSP.getAllSwExpensesAwaitingApproval(dept, agency, tku, lastName, requestType, userId,  empId);
		Assert.assertTrue(!expApproveList.isEmpty());
	}

	
	@Test
	public void testGetAllSwExpensesAwaitingApproval_DeptAgencyALTkuAL() {

		String requestType = IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED;
		String empId = "";
		String userId = "T_HRMND99";
		String moduleId = "PRMW027";
		String lastName = "";
		String dept = "47";
		String agency = "AL";
		String tku = "AL";

		List<ApprovalTransactionBean> expApproveList =   approveDSP.getAllSwExpensesAwaitingApproval(dept, agency, tku, lastName, requestType, userId, empId);
		Assert.assertTrue(!expApproveList.isEmpty());
	}
	
	@Test
	public void testGetAllSwExpensesAwaitingApproval_DeptAgencyTkuLastName() {

		String requestType = IConstants.TRANSACTION_LIST_BOTH_ADJUSTED_AND_NON_ADJUSTED;
		String empId = "";
		String userId = "T_HRMND99";
		String moduleId = "PRMW027";
		String lastName = "KRASNICKI";
		String dept = "47";
		String agency = "19";
		String tku = "230";

		List<ApprovalTransactionBean> expApproveList =   approveDSP.getAllSwExpensesAwaitingApproval(dept, agency, tku, lastName, requestType, userId,empId);
		Assert.assertTrue(!expApproveList.isEmpty());
	}

		

}
