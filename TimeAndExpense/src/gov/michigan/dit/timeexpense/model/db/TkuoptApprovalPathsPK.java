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
public class TkuoptApprovalPathsPK implements Serializable {
    
    @Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "AGENCY")
    private String agency;
    
    @Column(name = "TKU")
    private String tku;
    
    @Column(name = "DATA_CATEGORY")
    private String dataCategory;

    public TkuoptApprovalPathsPK() {
    }

    public TkuoptApprovalPathsPK(String department, String agency, String tku, String dataCategory) {
        this.department = department;
        this.agency = agency;
        this.tku = tku;
        this.dataCategory = dataCategory;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getTku() {
        return tku;
    }

    public void setTku(String tku) {
        this.tku = tku;
    }

    public String getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(String dataCategory) {
        this.dataCategory = dataCategory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (department != null ? department.hashCode() : 0);
        hash += (agency != null ? agency.hashCode() : 0);
        hash += (tku != null ? tku.hashCode() : 0);
        hash += (dataCategory != null ? dataCategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TkuoptApprovalPathsPK)) {
            return false;
        }
        TkuoptApprovalPathsPK other = (TkuoptApprovalPathsPK) object;
        if ((this.department == null && other.department != null) || (this.department != null && !this.department.equals(other.department))) {
            return false;
        }
        if ((this.agency == null && other.agency != null) || (this.agency != null && !this.agency.equals(other.agency))) {
            return false;
        }
        if ((this.tku == null && other.tku != null) || (this.tku != null && !this.tku.equals(other.tku))) {
            return false;
        }
        if ((this.dataCategory == null && other.dataCategory != null) || (this.dataCategory != null && !this.dataCategory.equals(other.dataCategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.TkuoptApprovalPathsPK[department=" + department + ", agency=" + agency + ", tku=" + tku + ", dataCategory=" + dataCategory + "]";
    }

}
