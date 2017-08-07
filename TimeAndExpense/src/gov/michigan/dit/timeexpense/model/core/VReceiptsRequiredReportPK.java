package gov.michigan.dit.timeexpense.model.core;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VReceiptsRequiredReportPK {
	
	@Column(name="EMP_IDENTIFIER")
	private Long empIdentifier;

	@Column(name="EXPEV_IDENTIFIER")
	private Long expevIdentifier;
	
	@Column(name="SUM_DOLLAR_AMOUNT")
	private Double sumDollarAmount;
	
	public VReceiptsRequiredReportPK() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empIdentifier == null) ? 0 : empIdentifier.hashCode());
		result = prime * result + ((expevIdentifier == null) ? 0 : expevIdentifier.hashCode());
		result = prime * result + ((sumDollarAmount == null) ? 0 : sumDollarAmount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VReceiptsRequiredReportPK other = (VReceiptsRequiredReportPK) obj;
		if (empIdentifier == null) {
			if (other.empIdentifier != null)
				return false;
		} else if (!empIdentifier.equals(other.empIdentifier))
			return false;
		if (expevIdentifier == null) {
			if (other.expevIdentifier != null)
				return false;
		} else if (!expevIdentifier.equals(other.expevIdentifier))
			return false;
		if (sumDollarAmount == null) {
			if (other.sumDollarAmount != null)
				return false;
		} else if (!sumDollarAmount.equals(other.sumDollarAmount))
			return false;
		return true;
	}
	
	
}