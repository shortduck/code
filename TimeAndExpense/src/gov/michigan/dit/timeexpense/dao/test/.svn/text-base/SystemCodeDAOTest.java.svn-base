package gov.michigan.dit.timeexpense.dao.test;
import static org.junit.Assert.assertTrue;
import gov.michigan.dit.timeexpense.dao.ExpenseActionsDAO;
import gov.michigan.dit.timeexpense.dao.ExpenseDAO;
import gov.michigan.dit.timeexpense.dao.SystemCodeDAO;
import gov.michigan.dit.timeexpense.model.core.ExpenseActions;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;
import gov.michigan.dit.timeexpense.model.core.SystemCodes;
import gov.michigan.dit.timeexpense.model.core.SystemCodesPK;
import gov.michigan.dit.timeexpense.service.SystemCodesDSP;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SystemCodeDAOTest extends AbstractDAOTest {
	
	
	private SystemCodeDAO systemCodeDao;
	SystemCodes abc = new SystemCodes();
	@Before
	public void startTransaction() {
		super.beginTransaction();
		 
		systemCodeDao = new SystemCodeDAO(em);
	}
	
	@Test
	public void  findAllSysCodes() 
	{
		
		List<SystemCodes> listCodes  = systemCodeDao.findAllSysCodes();
		
		 
		assertTrue(listCodes.size()>0);
	}

	
	@Test
	public void createSysCdData() throws Exception
	{  
		SystemCodes sysObj= new SystemCodes();
		SystemCodesPK sysTest = new SystemCodesPK();
		sysTest.setStartDate(getCurrentDateTs());
		sysTest.setSystemCode("TEMP");
		sysObj.setSystemCodesPK(sysTest);
		sysObj.setDescription("cdsf");
		sysObj.setEndDate(getCurrentDateTs());
		sysObj.setValue("xcx");
		sysObj.setModifiedDate(getCurrentDateTs());
		sysObj.setModifiedUserId("user");
		systemCodeDao.createSysCdData(sysObj); 
		 
	}
	@Test
	public void deleteSystemCode()
	{
		List<SystemCodes> abc = systemCodeDao.findSelectedCode("TEMP");
		systemCodeDao.deleteSystemCode(abc.get(0));
	}
	@Test
	public void updateSysCdData()
	{
		List<SystemCodes> abc = systemCodeDao.findSelectedCode("DC02");
		systemCodeDao.updateSysCdData(abc.get(0)) ;
	}
	@Test
	public void findSelectedCode()
	{
		systemCodeDao.findSelectedCode("DC02");
		
	}
	public Date getCurrentDateTs() throws Exception
	{
		Date date = new Date();
		 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		 DateFormat formatter ;
		 formatter = new SimpleDateFormat("MM/dd/yyyy");
		 String retDate = dateFormat.format(date).toString(); 
		 date = (Date)formatter.parse(retDate); 
		 
		 Date date1 = new Date();
		 date1 = (Date)formatter.parse(dateFormat.format(date)); 
		 return date1;
	}

}
