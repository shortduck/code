package gov.michigan.dit.timeexpense.dao.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Base class for DAO test cases providing <code>EntityManagerFactory</code>
 * and <code>EntityManager</code> instances.
 * 
 * @author chaudharym
 */
public class AbstractDAOTest {

	public static EntityManagerFactory emf;
	protected EntityManager em;

	@BeforeClass
	public static void init(){
		emf = Persistence.createEntityManagerFactory("TimeAndExpensePU_Test");
	}
	
	@AfterClass
	public static void destroy(){
		emf.close();
	}
	
	public void beginTransaction() {
		em = emf.createEntityManager();
		em.getTransaction().begin();
	}

	@After
	public void closeTransaction() {
		em.flush();
		em.getTransaction().commit();
		em.close();
	}
}
