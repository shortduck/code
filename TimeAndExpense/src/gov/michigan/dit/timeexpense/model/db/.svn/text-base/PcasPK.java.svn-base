/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author GhoshS
 */
@Embeddable
public class PcasPK implements Serializable {
    
    @Column(name = "FACS_AGY")
    private String facsAgy;
    
    @Column(name = "APPROPRIATION_YEAR")
    private String appropriationYear;
    
    @Column(name = "PCA")
    private String pca;

    public PcasPK() {
    }

    public PcasPK(String facsAgy, String appropriationYear, String pca) {
        this.facsAgy = facsAgy;
        this.appropriationYear = appropriationYear;
        this.pca = pca;
    }

    public String getFacsAgy() {
        return facsAgy;
    }

    public void setFacsAgy(String facsAgy) {
        this.facsAgy = facsAgy;
    }

    public String getAppropriationYear() {
        return appropriationYear;
    }

    public void setAppropriationYear(String appropriationYear) {
        this.appropriationYear = appropriationYear;
    }

    public String getPca() {
        return pca;
    }

    public void setPca(String pca) {
        this.pca = pca;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facsAgy != null ? facsAgy.hashCode() : 0);
        hash += (appropriationYear != null ? appropriationYear.hashCode() : 0);
        hash += (pca != null ? pca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PcasPK)) {
            return false;
        }
        PcasPK other = (PcasPK) object;
        if ((this.facsAgy == null && other.facsAgy != null) || (this.facsAgy != null && !this.facsAgy.equals(other.facsAgy))) {
            return false;
        }
        if ((this.appropriationYear == null && other.appropriationYear != null) || (this.appropriationYear != null && !this.appropriationYear.equals(other.appropriationYear))) {
            return false;
        }
        if ((this.pca == null && other.pca != null) || (this.pca != null && !this.pca.equals(other.pca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.PcasPK[facsAgy=" + facsAgy + ", appropriationYear=" + appropriationYear + ", pca=" + pca + "]";
    }

}
