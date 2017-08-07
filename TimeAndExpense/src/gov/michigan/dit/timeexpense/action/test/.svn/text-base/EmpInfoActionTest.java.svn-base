package gov.michigan.dit.timeexpense.action.test;

import gov.michigan.dit.timeexpense.action.EmpInfoAction;
import gov.michigan.dit.timeexpense.action.SelectEmployeeAction;
import gov.michigan.dit.timeexpense.dao.AppointmentDAO;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.service.AppointmentDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmpInfoActionTest {
    private static EntityManagerFactory emf;
    private EntityManager em;

    private EmpInfoAction action;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
	emf.close();
    }

    @Before
    public void setUp() throws Exception {
	em = emf.createEntityManager();

	action = new EmpInfoAction();
	action.setEntityManager(em);
	em.getTransaction().begin();
    }

    @After
    public void tearDown() throws Exception {
	em.getTransaction().commit();
    }

    @Test
    public void testExecute() throws Exception {
	{
	    action.execute();
	    String result = action.execute();

	    Assert.assertEquals("success", result);
	}
    }

}
