package gov.michigan.dit.timeexpense.dao.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import gov.michigan.dit.timeexpense.dao.AdvanceLiquidationDAO;
import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;

import org.junit.Before;
import org.junit.Test;

public class AdvanceLiquidationDAOTest extends AbstractDAOTest {
   private AdvanceLiquidationDAO advanceLiquidDAO = new AdvanceLiquidationDAO();

      public AdvanceLiquidationDAOTest() {
	
    }
    
    @Before
    public void startTransaction() {
	super.beginTransaction();
	advanceLiquidDAO.setEntityManager(em);
	 
    } 
    
	@Test
	public void testFindAllModulesForExpenseAction()
	{		
		AdvanceLiquidations liquidation = new AdvanceLiquidations();
		liquidation.setExpenseMasterId(331422);
		liquidation.setAmount(100);
		AdvanceMasters  advMaster = new   AdvanceMasters();
		advMaster.setAdvmIdentifier(20443);
		liquidation.setAdvanceMaster(advMaster);
		List<AdvanceLiquidations> liquidation1  = advanceLiquidDAO.checkAdvanceLiquidation(331422,advMaster);
		
		assertTrue(liquidation1.size()>0);
	}
}