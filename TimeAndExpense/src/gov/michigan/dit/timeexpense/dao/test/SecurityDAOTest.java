package gov.michigan.dit.timeexpense.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gov.michigan.dit.timeexpense.dao.SecurityDAO;
import gov.michigan.dit.timeexpense.exception.MultipleUsersException;
import gov.michigan.dit.timeexpense.model.core.User;
import gov.michigan.dit.timeexpense.util.IConstants;

import org.junit.Before;
import org.junit.Test;

public class SecurityDAOTest extends AbstractDAOTest{

	private SecurityDAO dao = new SecurityDAO();
	
	private final int MULTIPLE_USER_EMP_ID = 999999998;
	private final int SINGLE_USER_EMP_ID = 999999999;
	private final String SINGLE_USER_ID = "Mohnish003";
	
	
	@Before
	public void startTransaction(){
		beginTransaction();
		dao.setEntityManager(em);
	}
	
	
	@Test
	public void testFindUserByEmpId_ValidUser() throws Exception {
		User validUser = dao.findUserByEmpId(SINGLE_USER_EMP_ID);
		assertNotNull(validUser);
	}
	
	@Test
	public void testFindUserByEmpId_InvalidUser() throws Exception {
		int testEmpId = -999999;
		User validUser = dao.findUserByEmpId(testEmpId);
		
		assertNull(validUser);
	}
	
	@Test
	public void testFindUserByEmpId_MultipleUsers() {
		User validUser = null;
		
		try{
			validUser = dao.findUserByEmpId(MULTIPLE_USER_EMP_ID);
		}catch(MultipleUsersException mue){
			assertSame(MultipleUsersException.class, mue.getClass());
			assertTrue(mue.getMessage().startsWith("Multiple"));
		}

		assertNull(validUser);
	}
	
	@Test
	public void testFindSecurityScope_InvalidScope(){
		assertNull(dao.findSecurityScope(SINGLE_USER_ID,"ZA09402", "08", "08", "09"));
	}
/*
	@Test
	public void testFindSecurityScope_ValidScope(){
		String fullScope = IConstants.SECURITY_SCOPE_ALL;
		assertNotNull(dao.findSecurityScope(SINGLE_USER_ID,"TEXW001", fullScope, fullScope, fullScope));
	}
*/	
	@Test
	public void testFindSecurityScope_ValidLimitedScope(){
		String fullScope = IConstants.SECURITY_SCOPE_ALL;
		String result = dao.findSecurityScope("T_DEPT99","ADVW001", "59", "01", "001");
		assertEquals(result, "Y");
	}
   @Test
   public void findSecurityScope()
   {
	   String result = dao.findSecurityScope("CHIDURAS","PRMW079");
	   System.out.println(" result "+result);
   }
	
}
