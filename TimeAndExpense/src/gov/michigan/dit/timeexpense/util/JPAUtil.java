package gov.michigan.dit.timeexpense.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JPAUtil {
	private static EntityManagerFactory emf;
	
	static {
		try {
			emf = Persistence.createEntityManagerFactory("TimeAndExpensePU");
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	public static void shutdown() {
		if(emf != null){
			getEntityManagerFactory().close();
		}
	}
}
