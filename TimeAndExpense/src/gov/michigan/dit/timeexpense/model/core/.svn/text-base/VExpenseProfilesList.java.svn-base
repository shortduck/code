/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author chiduras
 */
@Entity
@Table(name = "V_EXPENSE_PROFILES_LIST")
public class VExpenseProfilesList implements Serializable {
    private static final long serialVersionUID = 1L;
   @Id
    @Column(name = "APPT_IDENTIFIER")
    private int apptIdentifier;    
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @Column(name = "VALUE")
    private String value;
    @Column(name = "DESCRIPTION")
    private String description;

    public VExpenseProfilesList() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getApptIdentifier() {
        return apptIdentifier;
    }

    public void setApptIdentifier(int apptIdentifier) {
        this.apptIdentifier = apptIdentifier;
    }

}
