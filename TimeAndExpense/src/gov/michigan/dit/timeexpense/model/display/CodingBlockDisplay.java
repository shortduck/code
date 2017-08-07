/**
 * 
 */
package gov.michigan.dit.timeexpense.model.display;


import java.io.Serializable;
import java.util.List;


/**
 * @author GhoshS
 *
 */
public class CodingBlockDisplay implements Serializable {

	private static final long serialVersionUID = 8955754106737608737L;

	List<String> lineItemId;
	List<CodingBlockSummaryBean> codingBlocks;
	public List<String> getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(List<String> lineItemId) {
		this.lineItemId = lineItemId;
	}
	public List<CodingBlockSummaryBean> getCodingBlocks() {
		return codingBlocks;
	}
	public void setCodingBlocks(List<CodingBlockSummaryBean> codingBlocks) {
		this.codingBlocks = codingBlocks;
	}
	
	

}
