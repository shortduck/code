package gov.michigan.dit.timeexpense.listener;

import gov.michigan.dit.timeexpense.model.core.DriverReimbExpTypeCbs;
import gov.michigan.dit.timeexpense.service.CodingBlockDSP;
import gov.michigan.dit.timeexpense.service.ExpenseLineItemDSP;
import gov.michigan.dit.timeexpense.util.IConstants;
import gov.michigan.dit.timeexpense.util.JPAUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.businessobjects.samples.CrystalReportsParms;
import com.businessobjects.samples.JRCHelperSample;

/**
 * Used to initialize the application context and setup the application cache to
 * cache static data.
 * 
 * @author chaudharym
 * 
 */
public class ApplicationContextListener implements ServletContextListener {

	private Logger log = Logger.getLogger(ApplicationContextListener.class);
	private EntityManager em;

	private ExpenseLineItemDSP detailsService;
	private CodingBlockDSP codingBlockService;

	/**
	 * Context initialization event handler. Application scoped cache is created
	 * and static data cached.
	 */
	public void contextInitialized(ServletContextEvent event) {
		Map<String, Object> applicationCache = new HashMap<String, Object>();
		event.getServletContext().setAttribute(IConstants.APPLICATION_CACHE,
				applicationCache);

		setupApplicationCache(applicationCache);
	}

	/**
	 * Context destruction event handler. Application scoped cache is cleared.
	 */
	public void contextDestroyed(ServletContextEvent event) {
		event.getServletContext().removeAttribute(IConstants.APPLICATION_CACHE);
	}

	/**
	 * Sets the application scoped cache. Throws a runtime error if cache
	 * creation is unsuccessful.
	 * 
	 * @param Cache
	 */
	private void setupApplicationCache(Map<String, Object> cache) {
		log.debug("Trying to setup application cache");
		boolean cacheSetupSuccess = true;

		try {
			// initialize entity manager
			em = JPAUtil.getEntityManagerFactory().createEntityManager();
			
			//setup cache
			prepareAppStartupData(cache);
			
		} catch (Exception ex) {
			cacheSetupSuccess = false;
			log.error("Application cache setup failed. Reason: " + ex.getMessage());
		} finally {
			if (em != null)
				em.close();
		}

		if (cacheSetupSuccess) log.debug("Application cache setup successfully");
	}

	private void prepareAppStartupData(Map<String, Object> cache) throws Exception{
		detailsService = new ExpenseLineItemDSP(em);
		codingBlockService = new CodingBlockDSP(em);
		// Invoke methods to fetch startup data
		initStates(cache);
		initCrystalReportsParms(cache);
		initDriverReimbursementCodingBlocks(cache);
	}

	/**
	 * Fetches states information and stores them in the cache.
	 * 
	 * @param cache
	 */
	private void initStates(Map<String, Object> cache) {
		List<Object> states = detailsService.findTravelAuthorizedStates();
		cache.put(IConstants.STATES, states);
	}

	/**
	 * Fetches city information and stores them in the cache.
	 * 
	 * @param cache
	 */
	/*
	 * This is not being used now. This has been replaced with Session version, instead of Cache
	private void initCities(Map<String, Object> cache) {
		List<String> cities = detailsService.findCities();
		cache.put(IConstants.CITIES, cities);
	}
	*/

	/**
	 * Fetches Crystal Reports credentials and stores them in the cache.
	 * 
	 * @param cache
	 */
	private void initCrystalReportsParms(Map<String, Object> cache) throws Exception{
		CrystalReportsParms cr = JRCHelperSample.getCrystalReportParms();
		cache.put(IConstants.CrystalReportsParms, cr);
	}
	/**
	 * fetch driver reimbursement coding blocks
	 * @param cache
	 * @throws Exception
	 */
	private void initDriverReimbursementCodingBlocks(Map<String, Object> cache) throws Exception{
		List<DriverReimbExpTypeCbs> cbs = codingBlockService.getDriverReimbExpTypeCbs();
		cache.put("DRIVER_REIMB_CODING_BLOCKS", cbs);
	}
}
