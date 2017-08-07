/**
 * This class is used to hold display items for the news bulletin grid shown on
 * the home page. The display class is used rather than the core db class becuase the
 * core db class includes other fields not needed for the grid. In addition, the start
 * date for the grid is included in the primary key that is part of a separate class.   
 * 
 * ZH - 03/31/2009
 */

package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author hussainz
 */

public class NewsBulletinItem implements Serializable {
    
    private Date startDate;
	private String description;
    
    public NewsBulletinItem() {
    }
    
    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
