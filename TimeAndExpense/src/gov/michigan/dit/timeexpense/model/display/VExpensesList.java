/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.display;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "V_EXPENSES_LIST")
@NamedQueries({@NamedQuery(name = "VExpensesList.findAll", query = "SELECT v FROM VExpensesList v"), @NamedQuery(name = "VExpensesList.findByApptIdentifier", query = "SELECT v FROM VExpensesList v WHERE v.apptIdentifier = :apptIdentifier"), @NamedQuery(name = "VExpensesList.findByEmpIdentifier", query = "SELECT v FROM VExpensesList v WHERE v.empIdentifier = :empIdentifier"), @NamedQuery(name = "VExpensesList.findByExpevIdentifier", query = "SELECT v FROM VExpensesList v WHERE v.expevIdentifier = :expevIdentifier"), @NamedQuery(name = "VExpensesList.findByExpmIdentifier", query = "SELECT v FROM VExpensesList v WHERE v.expmIdentifier = :expmIdentifier"), @NamedQuery(name = "VExpensesList.findByExpDateFrom", query = "SELECT v FROM VExpensesList v WHERE v.expDateFrom = :expDateFrom"), @NamedQuery(name = "VExpensesList.findByExpDateTo", query = "SELECT v FROM VExpensesList v WHERE v.expDateTo = :expDateTo"), @NamedQuery(name = "VExpensesList.findByComments", query = "SELECT v FROM VExpensesList v WHERE v.comments = :comments"), @NamedQuery(name = "VExpensesList.findByTravelInd", query = "SELECT v FROM VExpensesList v WHERE v.travelInd = :travelInd"), @NamedQuery(name = "VExpensesList.findByOutOfStateInd", query = "SELECT v FROM VExpensesList v WHERE v.outOfStateInd = :outOfStateInd"), @NamedQuery(name = "VExpensesList.findByRevisionNumber", query = "SELECT v FROM VExpensesList v WHERE v.revisionNumber = :revisionNumber"), @NamedQuery(name = "VExpensesList.findByActionCode", query = "SELECT v FROM VExpensesList v WHERE v.actionCode = :actionCode"), @NamedQuery(name = "VExpensesList.findByAdjIdentifier", query = "SELECT v FROM VExpensesList v WHERE v.adjIdentifier = :adjIdentifier")})
public class VExpensesList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "APPT_IDENTIFIER")
    private Integer apptIdentifier;
    @Column(name = "EMP_IDENTIFIER")
    private Integer empIdentifier;
    @Column(name = "EXPEV_IDENTIFIER")
    private Integer expevIdentifier;
    
    @Id
    @Column(name = "EXPM_IDENTIFIER")
    private Integer expmIdentifier;
    @Column(name = "EXP_DATE_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expDateFrom;
    @Column(name = "EXP_DATE_TO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expDateTo;
    @Column(name = "COMMENTS")
    private String comments;
    @Column(name = "TRAVEL_IND")
    private String travelInd;
    @Column(name = "OUT_OF_STATE_IND")
    private String outOfStateInd;
    @Column(name = "REVISION_NUMBER")
    private Short revisionNumber;
    @Column(name = "ACTION_CODE")
    private Integer actionCode;
    @Column(name = "ADJ_IDENTIFIER")
    private String adjIdentifier;

    public VExpensesList() {
    }

    public Integer getApptIdentifier() {
        return apptIdentifier;
    }

    public void setApptIdentifier(Integer apptIdentifier) {
        this.apptIdentifier = apptIdentifier;
    }

    public Integer getEmpIdentifier() {
        return empIdentifier;
    }

    public void setEmpIdentifier(Integer empIdentifier) {
        this.empIdentifier = empIdentifier;
    }

    public Integer getExpevIdentifier() {
        return expevIdentifier;
    }

    public void setExpevIdentifier(Integer expevIdentifier) {
        this.expevIdentifier = expevIdentifier;
    }

    public Integer getExpmIdentifier() {
        return expmIdentifier;
    }

    public void setExpmIdentifier(Integer expmIdentifier) {
        this.expmIdentifier = expmIdentifier;
    }

    public Date getExpDateFrom() {
        return expDateFrom;
    }

    public void setExpDateFrom(Date expDateFrom) {
        this.expDateFrom = expDateFrom;
    }

    public Date getExpDateTo() {
        return expDateTo;
    }

    public void setExpDateTo(Date expDateTo) {
        this.expDateTo = expDateTo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTravelInd() {
        return travelInd;
    }

    public void setTravelInd(String travelInd) {
        this.travelInd = travelInd;
    }

    public String getOutOfStateInd() {
        return outOfStateInd;
    }

    public void setOutOfStateInd(String outOfStateInd) {
        this.outOfStateInd = outOfStateInd;
    }

    public Short getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Short revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public Integer getActionCode() {
        return actionCode;
    }

    public void setActionCode(Integer actionCode) {
        this.actionCode = actionCode;
    }

    public String getAdjIdentifier() {
        return adjIdentifier;
    }

    public void setAdjIdentifier(String adjIdentifier) {
        this.adjIdentifier = adjIdentifier;
    }

}
