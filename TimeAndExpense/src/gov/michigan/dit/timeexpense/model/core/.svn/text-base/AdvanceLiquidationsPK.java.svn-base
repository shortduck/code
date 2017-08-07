/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author GhoshS
 */
@Embeddable
public class AdvanceLiquidationsPK implements Serializable {
	
	private static final long serialVersionUID = 6055216112928481892L;

	@Column(name = "ADVM_IDENTIFIER")
    private int advmIdentifier;
    
    @Column(name = "EXPM_IDENTIFIER")
    private int expmIdentifier;

    public AdvanceLiquidationsPK() {
    }

    public AdvanceLiquidationsPK(int advmIdentifier, int expmIdentifier) {
        this.advmIdentifier = advmIdentifier;
        this.expmIdentifier = expmIdentifier;
    }

    public int getAdvmIdentifier() {
        return advmIdentifier;
    }

    public void setAdvmIdentifier(int advmIdentifier) {
        this.advmIdentifier = advmIdentifier;
    }

    public int getExpmIdentifier() {
        return expmIdentifier;
    }

    public void setExpmIdentifier(int expmIdentifier) {
        this.expmIdentifier = expmIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) advmIdentifier;
        hash += (int) expmIdentifier;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvanceLiquidationsPK)) {
            return false;
        }
        AdvanceLiquidationsPK other = (AdvanceLiquidationsPK) object;
        if (this.advmIdentifier != other.advmIdentifier) {
            return false;
        }
        if (this.expmIdentifier != other.expmIdentifier) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.AdvanceLiquidationsPK[advmIdentifier=" + advmIdentifier + ", expmIdentifier=" + expmIdentifier + "]";
    }

}
