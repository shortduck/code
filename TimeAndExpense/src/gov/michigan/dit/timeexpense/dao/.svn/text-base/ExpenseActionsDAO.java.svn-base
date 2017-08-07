package gov.michigan.dit.timeexpense.dao;

import gov.michigan.dit.timeexpense.model.core.ExpenseActions;
import gov.michigan.dit.timeexpense.model.core.ExpenseMasters;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * Persistence layer for <code>ExpenseActions</code> core class.
 * 
 * @author chaudharym
 */
@SuppressWarnings("unchecked")
public class ExpenseActionsDAO extends AbstractDAO{

	public ExpenseActionsDAO(EntityManager em){
		super(em);
	}
 	
	/**
	 * Finds all the actions taken on the expense in the order of occurrence.
	 * 
	 * @param expense
	 * @return list of actions in ascending order of modified date.
	 */
	public List<ExpenseActions> findExpenseActions(ExpenseMasters expense){
		String query = "select ea from ExpenseActions ea where ea.expmIdentifier=:expense order by ea.modifiedDate";
		
		List<ExpenseActions> actions = entityManager.createQuery(query).setParameter("expense", expense).getResultList();
		
		return actions;
	}
	
	public ExpenseActions findLatestExpenseActionExcludingActions(ExpenseMasters expense){
		String query = "select ea from ExpenseActions ea where ea.expmIdentifier=:expense and ea.actionCode <> 'AUDT' " +
				"and ea.actionCode <> 'SPLT' and ea.actionCode <> 'ECRT' AND ea.modifiedDate = " +
				"(select max(_ea.modifiedDate) from ExpenseActions _ea where _ea.expmIdentifier=:expense and _ea.actionCode <> 'AUDT' and _ea.actionCode <> 'SPLT' and _ea.actionCode <> 'ECRT')";
		
		List<ExpenseActions> actions = entityManager.createQuery(query).setParameter("expense", expense).getResultList();
		
		return actions.size() == 0 ? null: actions.get(0);
		
		//return actions;
	}
	
	/**
	 * Finds the security modules associated to a given expense action.
	 * PS: Ideally there should be only one security module for an 
	 * expense action, but as this is not enforced this method returns 
	 * a list back.
	 * 
	 * @param action
	 * @return list of security modules associated to the expense action
	 */
	public List<String> findAllModulesForExpenseAction(String action){
		String query = "select rc.REF_CODE from REF_CODES rc where rc.value = ?1";
		
		List<String> modules = entityManager.createNativeQuery(query).setParameter(1, action).getResultList();
		
		return modules;
	}
	
	public ExpenseActions findLatestExpenseActionExcludingAuditCertify(ExpenseMasters expense){
		String query = "select ea from ExpenseActions ea where ea.expmIdentifier=:expense and ea.actionCode <> 'AUDT' " +
				"and ea.actionCode <> 'ECRT' and ea.modifiedDate = (select max(_ea.modifiedDate) from ExpenseActions _ea " + 
				"where _ea.expmIdentifier=:expense and _ea.actionCode <> 'AUDT' and _ea.actionCode <> 'ECRT')";
		
		List<ExpenseActions> actions = entityManager.createQuery(query).setParameter("expense", expense).getResultList();
		
		return actions.size() == 0 ? null: actions.get(0);
		
		//return actions;
	}
	
}
