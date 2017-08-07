/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "DRIVER_REIMB_EXP_TYPE_CBS")
public class DriverReimbExpTypeCbs implements Serializable {

	@Id
	@Column(name = "DRETC_IDENTIFIER")
    private Integer dretcIdentifier;
    	
	@Column(name = "DEPARTMENT")
    private String department;
    
    @Column(name = "AGENCY")
    private String agency;
    
    @Column(name = "EXP_TYPE_CODE")
    private String expTypeCode;
    
    @Column(name = "INDEX_CODE")
    private String indexCode;
    
	public DriverReimbExpTypeCbs() {
    }

	public Integer getDretcIdentifier() {
		return dretcIdentifier;
	}

	public void setDretcIdentifier(Integer dretcIdentifier) {
		this.dretcIdentifier = dretcIdentifier;
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

	public String getExpTypeCode() {
		return expTypeCode;
	}

	public void setExpTypeCode(String expTypeCode) {
		this.expTypeCode = expTypeCode;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}
}
