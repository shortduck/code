/**
 * 
 */
package gov.michigan.dit.timeexpense.service.test;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import gov.michigan.dit.timeexpense.dao.AppointmentDAO;
import gov.michigan.dit.timeexpense.dao.EmailNotificationDAO;
import gov.michigan.dit.timeexpense.dao.test.AbstractDAOTest;
import gov.michigan.dit.timeexpense.model.core.UserSubject;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.service.EmailNotificationDSP;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author chiduras
 *
 */
public class EmailNotificationDSPTest {
    
   
    EmailNotificationDSP emailService;
	
        private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (emf != null && emf.isOpen())
			emf.close();
	}

	@Before
	public void setUp() throws Exception {
		em = emf.createEntityManager();

		emailService = new EmailNotificationDSP(em);
	        
	     //   AppointmentDSP apptDsp = new AppointmentDSP(em);
	     //   AppointmentDAO apptDao = new AppointmentDAO();
	     //   apptDao.setEntityManager(em);
	     //   apptDsp.setAppointmentDao(apptDao);
	        
	        EmailNotificationDAO dao = new EmailNotificationDAO();
		dao.setEntityManager(em);

		emailService.setEmailDAO(dao);
		em.getTransaction().begin();
		
	//	emailService.setApptDSP(apptDsp);
	}

	@After
	public void tearDown() throws Exception {
		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testProcessEmailNotifications(){
		UserSubject subject = new UserSubject();
	    int apptId = 107192;
	    int empId = 133509;
	    subject.setAppointmentId(apptId);
	    String userID = "T_HRMND99";
	    String msgId = "050";				
	    Date effectiveDate = new Date("01/01/2008");
	  int result = emailService.processEmailNotifications(subject, empId, userID, msgId, effectiveDate, "123456");
	  Assert.assertEquals(0, result);
	    
	}

	@Test
	public void testProcessEmailNotification_InvalidParameters (){
		UserSubject subject = new UserSubject();
	    int apptId = 0;
	    int empId = 0;
	    subject.setAppointmentId(apptId);
	    String userID = "T_HRMND99";
	    String msgId = "050";				
	    Date effectiveDate = new Date("01/01/2008");
	  int result = emailService.processEmailNotifications(subject, empId, userID, msgId, effectiveDate, "123456");
	  Assert.assertEquals(10550, result);
	    
	}

	@Test
	public void testProcessEmailNotification_InvalidParametersUserId (){
		UserSubject subject = new UserSubject();
	    int apptId = 107192;
	    int empId = 133509;
	    subject.setAppointmentId(apptId);
	    String userID = "";
	    String msgId = "050";				
	    Date effectiveDate = new Date("01/01/2008");
	  int result = emailService.processEmailNotifications(subject, empId, userID, msgId, effectiveDate, "123456");
	  Assert.assertEquals(10550, result);
	    
	}

	@Test
	public void testProcessEmailNotification_InvalidParametersMsId (){
		UserSubject subject = new UserSubject(); 
	    int apptId = 107192;
	    int empId = 133509;
	    subject.setAppointmentId(apptId);
	    String userID = "T_HRMND99";
	    String msgId = "";				
	    Date effectiveDate = new Date("01/01/2008");
	  int result = emailService.processEmailNotifications(subject, empId, userID, msgId, effectiveDate, "123456");
	  Assert.assertEquals(10550, result);
	    
	}

	@Test
	public void testProcessEmailNotification_InvalidParameterstiveDate (){
		UserSubject subject = new UserSubject(); 
	    int apptId = 107192;
	    int empId = 133509;
	    subject.setAppointmentId(apptId);
	    String userID = "T_HRMND99";
	    String msgId = "050";				
	    Date effectiveDate = null;
	  int result = emailService.processEmailNotifications(subject, empId, userID, msgId, effectiveDate, "123456");
	  Assert.assertEquals(10550, result);
	    
	}
	
	@Test
	public void testCheckEmailOptions_True (){
		 
	    int apptId = 107192;
	    String msgId = "050";
	    Date effectiveDate = new Date("01/01/2008");
	Assert.assertEquals(true, emailService.checkEmailOptions(apptId, effectiveDate, msgId));
	    
	}

	@Test
	public void testCheckEmailOptions_False(){
		
	    int apptId = 1;
	    String msgId = "000";	
	    Date effectiveDate = new Date("01/01/2008");
	Assert.assertEquals(false,emailService.checkEmailOptions(apptId, effectiveDate, msgId));
	    
	}
	

	public EmailNotificationDSP getEmailService() {
	    return emailService;
	}



	public void setEmailService(EmailNotificationDSP emailService) {
	    this.emailService = emailService;
	}
	
	    
	

	
    
    
    
}
