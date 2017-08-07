package gov.michigan.dit.timeexpense.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import gov.michigan.dit.timeexpense.dao.AdvanceDAO;
import gov.michigan.dit.timeexpense.dao.EmailNotificationDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.dao.SecurityDAO;
import gov.michigan.dit.timeexpense.model.core.EmailNotificationBean;
import gov.michigan.dit.timeexpense.model.core.NotificationReceivers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmailNotificationDAOTest extends AbstractDAOTest {

    private EmailNotificationDAO emailDAO = new EmailNotificationDAO();

    public EmailNotificationDAOTest() {

    }

    @Before
    public void startTransaction() {
	super.beginTransaction();
	emailDAO.setEntityManager(em);

    }

    @Test
    public void testFindEmailReceivers_ValidMessageID() {
       List<NotificationReceivers> receivers = emailDAO.findEmailReceivers("050");
	assertNotNull(receivers);
	
    }

    @Test
    public void testFindEmailReceivers_InValidMessageID() {
       List<NotificationReceivers> receivers = emailDAO.findEmailReceivers("999");
       Assert.assertTrue(receivers.isEmpty());
	
    }
    
   

    @Test
    public void testcheckTkuOptions() {

	// assertTrue(dao.checkTkuOptions("08", "01", "AL", "001"));
	assertFalse(emailDAO.checkTkuOptions("08", "01", "999", "001"));
	assertTrue(emailDAO.checkTkuOptions("03", "01", "999", "002"));
	assertTrue(emailDAO.checkTkuOptions("03", "01", "001", "002"));

    }

    @Test
    public void testsendEmailNotification() {
	EmailNotificationBean emailBean = null;
	emailBean = new EmailNotificationBean();
	emailBean.setToUser("CHIDURAS");
	emailBean.setFromUser("CHIDURAS");
	emailBean.setMsgId("001");
	emailBean.setAddlText("Testing");
	emailBean.setSesNum(123);
	emailBean.setDept("08");
	emailBean.setAgy("01");
	emailBean.setTku("AL");

	assertNotNull(emailDAO.sendEmailNotification(emailBean));

	
    }
   
    
   /* DECLARE 
    RetVal NUMBER;
    TO_USER VARCHAR2(32767);
    FROM_USER VARCHAR2(32767);
    MSG_ID VARCHAR2(32767);
    ADDL_TEXT VARCHAR2(32767);
    DEPT VARCHAR2(32767);
    AGNCY VARCHAR2(32767);
    L_TKU VARCHAR2(32767);
    SESNUM NUMBER;
    IN_DO_COMMIT VARCHAR2(32767);

  BEGIN 
    TO_USER := 'T_HRMND99';
    FROM_USER := 'T_HRMND99';
    MSG_ID := '001';
    ADDL_TEXT := 'TESTING';
    DEPT := '08';
    AGNCY := '01';
    L_TKU := '867';
    SESNUM := 123;
    IN_DO_COMMIT := 'Y';

    RetVal := HRMNDCD.SEND_DCDS_NOTIFICATION ( TO_USER, FROM_USER, MSG_ID, ADDL_TEXT, DEPT, AGNCY, L_TKU, SESNUM, IN_DO_COMMIT );

    DBMS_OUTPUT.Put_Line('RetVal = ' || TO_CHAR(RetVal));

    DBMS_OUTPUT.Put_Line('');

    COMMIT; 
  END;*/
    
    @Test
    public void testsendEmailNotification_Valid_Returns_0() {
	EmailNotificationBean emailBean = null;	
	emailBean = new EmailNotificationBean();	 
	emailBean.setToUser("T_HRMND99");	
	emailBean.setFromUser("T_HRMND99");
	emailBean.setMsgId("001");
	emailBean.setAddlText("TESTING");
	emailBean.setSesNum(123);
	emailBean.setDept("08");
	emailBean.setAgy("01");
	emailBean.setTku("867");
      
	Assert.assertEquals(emailDAO.sendEmailNotification(emailBean), 0);

	
    }
    
    @Test
    public void testsendEmailNotification_Returns_1_MessageNotFound() {
	EmailNotificationBean emailBean = null;	
	emailBean = new EmailNotificationBean();	 
	emailBean.setToUser("T_HRMND99");	
	emailBean.setFromUser("T_HRMND99");
	emailBean.setMsgId("050");
	emailBean.setAddlText("TESTING");
	emailBean.setSesNum(123);
	emailBean.setDept("03");
	emailBean.setAgy("01");
	emailBean.setTku("900");  
	
	Assert.assertEquals(emailDAO.sendEmailNotification(emailBean), 1);
	
    }
    
    
    @Test
	public void testFindSupervisorEmailId_ValidEmail() {
		int apptId = 108887;
		String emailId = emailDAO.findSupervisorEmailId(apptId);
		System.out.println(emailId);
		Assert.assertNotNull(emailId);
	}
	

	
    @Test
	public void testFindSupervisorEmailId_NoEmailIdFound_ReturnsUserID() {
		int apptId = 106980;
		String emailId = emailDAO.findSupervisorEmailId(apptId);
		System.out.println(emailId);
		Assert.assertNotNull(emailId);
	}
	@Test
	public void testFindSupervisorEmailId_NoEmailIdFound() {
		int apptId = 1234;
		String emailId = emailDAO.findSupervisorEmailId(apptId);
		System.out.println(emailId);
		Assert.assertNull(emailId);
	}
	//TODO: test for negative
	
	@Test
	public void testFindFromUserMailId_InValid() {
		String userId = "CHIDURA";
		String emailId = emailDAO.findFromUserMailId(userId);
		System.out.println(emailId);
		Assert.assertNotNull(emailId);
	}
	
	
	
	@Test
	public void testFindFromUserMailId_Valid() {
		String userId = "T_HRMND99";
		String emailId = emailDAO.findFromUserMailId(userId);
		System.out.println(emailId);
		Assert.assertNotNull(emailId);
	}

	@Test
	public void testFindFromUserMailId_ReturnsNull() {
		String userId = "Satish9";
		String emailId = emailDAO.findFromUserMailId(userId);
		System.out.println(emailId);
		Assert.assertNull(emailId);
	}
	
	
	@Test
	public void testFindToUserMailId_Valid() {
		int apptId = 108904;
		String emailId = emailDAO.findToUserMailId(apptId);
		System.out.println(emailId);
		Assert.assertNotNull(emailId);
	}
	
	@Test
	public void testFindToUserMailId_ReturnsNull() {
	        int apptId = 10899;
		String emailId = emailDAO.findToUserMailId(apptId);
		System.out.println(emailId);
		Assert.assertNull(emailId);
	}
	
	
	
	@Test
	public void testFindByUserName_Valid() {
		int empId = 132448;
		String name = emailDAO.findByUserName(empId);
		System.out.println(name);
		Assert.assertNotNull(name);
	}
	
	@Test
	public void testFindByUserName_ReturnsNull() {
		int empId = 999998;
		String name = emailDAO.findByUserName(empId);
		System.out.println(name);
		Assert.assertNull(name);
	}
	
	
	@Test
	public void testFindForUserName_Valid() {
		int apptId = 108904;
		String name = emailDAO.findForUserName(apptId);
		System.out.println(name);
		Assert.assertNotNull(name);
	}
	
	
	@Test
	public void testFindForUserName_ReturnsNull() {
		int apptId = 999998;
		String name = emailDAO.findForUserName(apptId);
		System.out.println(name);
		Assert.assertNull(name);
	}
	
	
    
   
}


