/**
 * Actions for the home page are included in this class. The class includes actions to
 * display the home page as well as populating the news item grid (Ajax).
 * 
 * ZH - 01/30/2009
 */

package gov.michigan.dit.timeexpense.action;

import gov.michigan.dit.timeexpense.model.core.RefCodes;
import gov.michigan.dit.timeexpense.model.core.SystemCodes;
import gov.michigan.dit.timeexpense.model.display.NewsBulletinItem;
import gov.michigan.dit.timeexpense.service.CommonDSP;
import gov.michigan.dit.timeexpense.util.IConstants;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class HomePageAction extends AbstractAction {

	private CommonDSP commonService;
	Logger logger = Logger.getLogger(HomePageAction.class);
	
	/**
	 * Setup services used and the associated DAO instances
	 */
	
	public HomePageAction (){
		commonService = new CommonDSP (entityManager);
		//commonService.setCommonDao(new CommonDAO());	
		
	}
	
	/**
	 * Action for the home page display
	 */
	
	// ZH - URLCNT now refers to "Helpful Tips", per defect # 1011. Only the menu option name has been changed
	// in the main template.
	@SuppressWarnings("unchecked")
	public String execute(){
		logger.info("Home Page Action invoked");
		if (session.get(IConstants.URLCNT) == null || session.get(IConstants.URLTRNG) == null || session.get(IConstants.URLFAQ) == null) {
			this.getHelpUrls();
		}
		String result = IConstants.SUCCESS;
		super.initialize();
		logger.info("User security initialization completed");
		return result;
	}	
	
	/**
	 * Used to get the Help related urls.
	 */
	private void getHelpUrls() {
		List<RefCodes> list = commonService.getHelpUrls();
		for (RefCodes rc : list) {
			session.put(rc.getRefCode(), rc.getDescription());
		}
	}

	/**
	 * Ajax action called to display news bulletin items in the grid
	 * 
	 * @return
	 */
	
	// TODO ZH _ Date conversion may need to be changed.
	public String getNewsBulletinItems(){
		logger.info("Ajax action - getNewsBulletinItems");
		String result = IConstants.SUCCESS;
		//commonService.getCommonDao().setEntityManager(entityManager);
		List<SystemCodes> newsItemsList = commonService.getNewsItemList();
		List<NewsBulletinItem> modifiedNewsList = new ArrayList<NewsBulletinItem> ();
		
		/* iterate through the list and copy items into the display bean. The display bean 
		 * is used to extract start date from primary key class and setup for display
		*/
		
		for (SystemCodes systemCodes: newsItemsList){
			NewsBulletinItem newsItem = new NewsBulletinItem ();
			newsItem.setStartDate(systemCodes.getSystemCodesPK().getStartDate());
			newsItem.setDescription(systemCodes.getDescription());
			modifiedNewsList.add(newsItem);
		}

		
		StringBuilder buffer = new StringBuilder("{");
		String firstTimeFlag = session.get(IConstants.FIRST_TIME) == null?"false": session.get(IConstants.FIRST_TIME).toString();
		
		buffer.append("newList:" + jsonParser.toJson(modifiedNewsList) + ", firstTimeFlag:"
		+ "\"" + firstTimeFlag + "\"");
		buffer.append("}");
		super.setJsonResponse(jsonParser.toJson(buffer.toString()));

		if(session.get(IConstants.FIRST_TIME) != null && ((String)session.get(IConstants.FIRST_TIME)).equalsIgnoreCase("true"))
		{
			session.remove(IConstants.FIRST_TIME);
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see gov.michigan.dit.timeexpense.action.BaseAction#prepare()
	 */
	@Override
	public void prepare() {
		commonService = new CommonDSP (entityManager);
		
	}
}
