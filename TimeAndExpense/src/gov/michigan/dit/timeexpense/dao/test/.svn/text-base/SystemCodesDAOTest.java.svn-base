package gov.michigan.dit.timeexpense.dao.test;

import gov.michigan.dit.timeexpense.dao.AbstractDAO;
import gov.michigan.dit.timeexpense.dao.CommonDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.dao.SystemCodeDAO;
import gov.michigan.dit.timeexpense.model.core.ExpenseErrors;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.ExpenseProfiles;
import gov.michigan.dit.timeexpense.model.core.ExpenseTypes;
import gov.michigan.dit.timeexpense.model.core.SystemCodes;
import gov.michigan.dit.timeexpense.model.db.Appointments;
import gov.michigan.dit.timeexpense.model.db.ErrorMessages;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class SystemCodesDAOTest extends AbstractDAOTest{

	private SystemCodeDAO dao = new SystemCodeDAO();
	//private SystemCodeDAO dao ;
 
	public SystemCodesDAOTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Before
	public void startTransaction(){
		super.beginTransaction();
		//dao = new SystemCodeDAO(em);
		dao.setEntityManager(em);
	
			}
	

	@Test
	public void testFindMaxCodingBlockErrorCode(){

		 List<SystemCodes>  arr=  dao.findAllSysCodes();
		Assert.assertTrue(arr.size()>0);
	}	
	
	@Test
	public void testNOTFindMaxCodingBlockErrorCode(){

		 List<SystemCodes>  arr=  dao.findAllSysCodes();
		Assert.assertFalse(arr==null);
	}	
}
