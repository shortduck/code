/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "TKUOPT_APPROVAL_PATHS")
@NamedQueries({@NamedQuery(name = "TkuoptApprovalPaths.findAll", query = "SELECT t FROM TkuoptApprovalPaths t"), @NamedQuery(name = "TkuoptApprovalPaths.findByDepartment", query = "SELECT t FROM TkuoptApprovalPaths t WHERE t.tkuoptApprovalPathsPK.department = :department"), @NamedQuery(name = "TkuoptApprovalPaths.findByAgency", query = "SELECT t FROM TkuoptApprovalPaths t WHERE t.tkuoptApprovalPathsPK.agency = :agency"), @NamedQuery(name = "TkuoptApprovalPaths.findByTku", query = "SELECT t FROM TkuoptApprovalPaths t WHERE t.tkuoptApprovalPathsPK.tku = :tku"), @NamedQuery(name = "TkuoptApprovalPaths.findByDataCategory", query = "SELECT t FROM TkuoptApprovalPaths t WHERE t.tkuoptApprovalPathsPK.dataCategory = :dataCategory"), @NamedQuery(name = "TkuoptApprovalPaths.findByModifiedUserId", query = "SELECT t FROM TkuoptApprovalPaths t WHERE t.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "TkuoptApprovalPaths.findByModifiedDate", query = "SELECT t FROM TkuoptApprovalPaths t WHERE t.modifiedDate = :modifiedDate")})
public class TkuoptApprovalPaths implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TkuoptApprovalPathsPK tkuoptApprovalPathsPK;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "APPROVAL_STEP_5", referencedColumnName = "ACTION_CODE")
    @ManyToOne
    private Actions approvalStep5;
    @JoinColumn(name = "APPROVAL_STEP_4", referencedColumnName = "ACTION_CODE")
    @ManyToOne
    private Actions approvalStep4;
    @JoinColumn(name = "APPROVAL_STEP_3", referencedColumnName = "ACTION_CODE")
    @ManyToOne
    private Actions approvalStep3;
    @JoinColumn(name = "APPROVAL_STEP_2", referencedColumnName = "ACTION_CODE")
    @ManyToOne
    private Actions approvalStep2;
    @JoinColumn(name = "APPROVAL_STEP_1", referencedColumnName = "ACTION_CODE")
    @ManyToOne
    private Actions approvalStep1;
    @JoinColumn(name = "DATA_CATEGORY", referencedColumnName = "DATA_CATEGORY", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ApprovalCategories approvalCategories;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false), @JoinColumn(name = "TKU", referencedColumnName = "TKU", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Tkus tkus;

    public TkuoptApprovalPaths() {
    }

    public TkuoptApprovalPaths(TkuoptApprovalPathsPK tkuoptApprovalPathsPK) {
        this.tkuoptApprovalPathsPK = tkuoptApprovalPathsPK;
    }

    public TkuoptApprovalPaths(String department, String agency, String tku, String dataCategory) {
        this.tkuoptApprovalPathsPK = new TkuoptApprovalPathsPK(department, agency, tku, dataCategory);
    }

    public TkuoptApprovalPathsPK getTkuoptApprovalPathsPK() {
        return tkuoptApprovalPathsPK;
    }

    public void setTkuoptApprovalPathsPK(TkuoptApprovalPathsPK tkuoptApprovalPathsPK) {
        this.tkuoptApprovalPathsPK = tkuoptApprovalPathsPK;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Actions getApprovalStep5() {
        return approvalStep5;
    }

    public void setApprovalStep5(Actions approvalStep5) {
        this.approvalStep5 = approvalStep5;
    }

    public Actions getApprovalStep4() {
        return approvalStep4;
    }

    public void setApprovalStep4(Actions approvalStep4) {
        this.approvalStep4 = approvalStep4;
    }

    public Actions getApprovalStep3() {
        return approvalStep3;
    }

    public void setApprovalStep3(Actions approvalStep3) {
        this.approvalStep3 = approvalStep3;
    }

    public Actions getApprovalStep2() {
        return approvalStep2;
    }

    public void setApprovalStep2(Actions approvalStep2) {
        this.approvalStep2 = approvalStep2;
    }

    public Actions getApprovalStep1() {
        return approvalStep1;
    }

    public void setApprovalStep1(Actions approvalStep1) {
        this.approvalStep1 = approvalStep1;
    }

    public ApprovalCategories getApprovalCategories() {
        return approvalCategories;
    }

    public void setApprovalCategories(ApprovalCategories approvalCategories) {
        this.approvalCategories = approvalCategories;
    }

    public Tkus getTkus() {
        return tkus;
    }

    public void setTkus(Tkus tkus) {
        this.tkus = tkus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tkuoptApprovalPathsPK != null ? tkuoptApprovalPathsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TkuoptApprovalPaths)) {
            return false;
        }
        TkuoptApprovalPaths other = (TkuoptApprovalPaths) object;
        if ((this.tkuoptApprovalPathsPK == null && other.tkuoptApprovalPathsPK != null) || (this.tkuoptApprovalPathsPK != null && !this.tkuoptApprovalPathsPK.equals(other.tkuoptApprovalPathsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.TkuoptApprovalPaths[tkuoptApprovalPathsPK=" + tkuoptApprovalPathsPK + "]";
    }

}
