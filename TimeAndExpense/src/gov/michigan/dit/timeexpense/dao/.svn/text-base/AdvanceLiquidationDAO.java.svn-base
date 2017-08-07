package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.core.AdvanceLiquidations;
import gov.michigan.dit.timeexpense.model.core.AdvanceMasters;
import gov.michigan.dit.timeexpense.model.display.AdvanceLiqEditBean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Data access layer for <code>AdvanceLiquidations</code>.
 * 
 * @author chaudharym
 */
public class AdvanceLiquidationDAO extends AbstractDAO{
	private String FIND_LIQ_BY_ADV_EXP = "select * from ADVANCE_LIQUIDATIONS liq where liq.ADVM_IDENTIFIER = ?1 and liq.EXPM_IDENTIFIER = ?2";
	
	
	
	public AdvanceLiquidationDAO(EntityManager em){
		super(em);
	}
	public AdvanceLiquidationDAO()
	{
		
	}
	/** 
	 * Finds an <code>AdvanceLiquidations</code> record for the given
	 * advanceId and expenseId.
	 * 
	 * @param advmIdentifier
	 * @param expmIdentifier
	 * @return <code>AdvanceLiquidations</code>
	 */
	public AdvanceLiquidations findLiquidationByAdvanceAndExpenseId(int advmIdentifier,
			Integer expmIdentifier) {
		
		List<AdvanceLiquidations> liquidation = entityManager.createNativeQuery(
				FIND_LIQ_BY_ADV_EXP, AdvanceLiquidations.class)
		.setParameter(1, advmIdentifier).setParameter(2, expmIdentifier).getResultList();
		
		return liquidation.size() < 1 ? null : liquidation.get(0);
	}
	
	/**
	 * Finds liquidation done by the expense with the given expense master Id. 
	 * 
	 * @param expense master Id
	 * @return liquidations
	 */
	public List<AdvanceLiquidations> findLiquidationsByExpenseId(Integer expmId){
		return entityManager.createQuery("select al from AdvanceLiquidations al where al.expenseMasterId=:expmId")
						.setParameter("expmId", expmId).getResultList();
	}
	
	/**
	 * Finds liquidation done by the expense with the given expense master Id. 
	 * 
	 * @param expense master Id
	 * @return liquidations
	 */
	public List<AdvanceLiqEditBean> findLiquidationsByExpenseIdForEdit(Integer expmId){
		
		String finderQuery = "SELECT am2.adev_identifier advanceEventId, am2.advm_identifier advanceMasterId, am2.status status, am2.current_ind currentInd" +
		  " FROM advance_masterS am2," +
		   " (SELECT adev_identifier, MAX (am.advm_identifier) maxadvm " +
			"		FROM advance_liquidations al, advance_masters am " +
			"	  WHERE al.advm_identifier = am.advm_identifier " +
			"			  AND al.expm_identifier = ?1" +
			"  GROUP BY adev_identifier) temp " +
	        " WHERE temp.maxadvm = am2.advm_identifier";

		Query query = entityManager.createNativeQuery(finderQuery,
				AdvanceLiqEditBean.class);
		query.setParameter(1, expmId);

		List<AdvanceLiqEditBean> liqList = query.getResultList();

		return liqList;
	}
	
	/**
	 * Persists the provided <code>AdvanceLiquidations</code>. If it's a new object, it's
	 * persisted, otherwise it updated. In both the cases, the new saved entity is returned
	 * back. 
	 * 
	 * @param liquidation
	 * @return saved liquidation
	 */
	public AdvanceLiquidations saveAdvanceLiquidation(AdvanceLiquidations liquidation){
		AdvanceLiquidations savedLiquidation = null;
		
		if(liquidation.getLiqdIdentifier() == null){
			entityManager.persist(liquidation);
			savedLiquidation = liquidation;
		}else{
			savedLiquidation = entityManager.merge(liquidation);
		}
		return savedLiquidation;
	}
	
	/**
	 * Persists the provided <code>AdvanceLiquidations</code>. If it's a new object, it's
	 * persisted, otherwise it updated. In both the cases, the new saved entity is returned
	 * back. 
	 * 
	 * @param liquidation
	 * @return saved liquidation
	 */
	public List<AdvanceLiquidations> checkAdvanceLiquidation(int expmId, AdvanceMasters advance){
		AdvanceLiquidations savedLiquidation = null;
		String query = "select al from AdvanceLiquidations al where al.expenseMasterId = :expmId " + 
			//	"  and a1.amount = "+ liquidation.getAmount() +
		" and al.advanceMaster = :advance "; 
		List<AdvanceLiquidations> liquidationSum = entityManager.createQuery(query)
		.setParameter("expmId", expmId).setParameter("advance", advance)
				.getResultList();
		return liquidationSum;
	}
	
	/**
	 * Finds total sum of all liquidations done by the given expense master.
	 * 
	 * @param ExpenseMasters Id
	 * @returns total amount
	 */
	public double findTotalLiquidationAmountByExpense(Integer expmId){
		double result = 0;
		
		String query = "select sum(al.amount) from AdvanceLiquidations al where al.expenseMasterId = :expmId";
		Double liquidationSum = (Double)entityManager.createQuery(query)
												.setParameter("expmId", expmId)
												.getResultList().get(0);
		
		if(liquidationSum != null){
			result = liquidationSum.doubleValue();
		}
		
		return result;
	}
	
	/**
	 * Finds total sum of all liquidations done by the given expense master from the given advance.
	 * 
	 * @param ExpenseMasters Id
	 * @param AdvanceMasters
	 * @returns total liquidation by expense from advance
	 */
	public double findTotalLiquidationAmountByExpenseFromAdvance(Integer expmId, AdvanceMasters advance){
		double result = 0;
		
		String query = "select sum(al.amount) from AdvanceLiquidations al where al.expenseMasterId = :expmId and al.advanceMaster = :advance"; 
		
		Double liquidationSum = (Double)entityManager.createQuery(query)
												.setParameter("expmId", expmId)
												.setParameter("advance", advance)
												.getResultList().get(0);
		
		if(liquidationSum != null){
			result = liquidationSum.doubleValue();
		}
		
		return result;
	}	

	/**
	 * Deletes the given liquidation. 
	 * 
	 * @param liquidation
	 * @return
	 */
	public void deleteLiquidation(AdvanceLiquidations liquidation){
		entityManager.remove(liquidation);
	}
	
	/**
	 * Fetches liquidations for a previously processed expense
	 * @param expmIdentifier
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public AdvanceLiquidations findLiquidationByExpenseId(Integer expmIdentifier) {
		
		List<AdvanceLiquidations> liquidation = entityManager.createNativeQuery(
				"select * from ADVANCE_LIQUIDATIONS liq where liq.EXPM_IDENTIFIER = ?2", AdvanceLiquidations.class)
		.setParameter(1, expmIdentifier).getResultList();
		
		return liquidation.size() < 1 ? null : liquidation.get(0);
	}
}
