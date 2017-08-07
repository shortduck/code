package gov.michigan.dit.timeexpense.action.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import gov.michigan.dit.timeexpense.action.AbstractAction;
import gov.michigan.dit.timeexpense.action.BaseAction;
import gov.michigan.dit.timeexpense.model.core.UserProfile;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.TimeAndExpenseUtil;

import org.junit.BeforeClass;
import org.junit.Test;


public class AbstractActionTest {
	
	private static AbstractAction action;
	
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void init() {
		Map session = new HashMap<String, Object>();
		session.put(IConstants.USER_PROFILE_SESSION_KEY_NAME, createUserProfile());
		action.setSession(session);
		
	}
	@Test
	public void testInitialize() {
		action.initialize();
		Map<String, Object> session = 
			(Map<String, Object>)
			TimeAndExpenseUtil.getPrivateField(action, BaseAction.class, "session");
		assertFalse(session.isEmpty());
	}
	
    private static UserProfile createUserProfile() {
    	UserProfile profile = new UserProfile ("SMITHM69");
    	Set<String> modules = new HashSet<String> ();
        modules.add(IConstants.ADVANCE_EMPLOYEE);
        modules.add(IConstants.EXPENSE_EMPLOYEE);
        modules.add(IConstants.ADVANCE_MANAGER);
        modules.add(IConstants.APPROVE_WEB_MANAGER);
        modules.add(IConstants.EXPENSE_MANAGER);
        modules.add(IConstants.ADVANCE_STATEWIDE);
        modules.add(IConstants.APPROVE_WEB_STATEWIDE);
        modules.add(IConstants.EXPENSE_STATEWIDE);
        profile.setModules(modules);
        return profile;
   
    }

}
