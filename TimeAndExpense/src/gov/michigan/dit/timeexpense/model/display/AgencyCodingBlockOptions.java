/**
 * 
 */
package gov.michigan.dit.timeexpense.model.display;

/**
 * @author khilgip
 * Model object to handle department, agency, CB error flag,
 * and to add to session object. kp
 *
 */
public class AgencyCodingBlockOptions {

	private String departments;
	
	private String agency; 
	
	private boolean canSubmiteCodingBlock ;
	
	public AgencyCodingBlockOptions(){
		super(); 		
	}
	
	
	public AgencyCodingBlockOptions(String department, String agency, boolean canSubmit){
		
		
		
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public boolean getCanSubmitCodingBlock() {
		return canSubmiteCodingBlock;
	}

	public void setCanSubmitCodingBlock(boolean canSubmiteCodingBlock) {
		this.canSubmiteCodingBlock = canSubmiteCodingBlock;
	} 
	
		
	
}
