/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import gov.michigan.dit.timeexpense.util.IConstants;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "ERROR_MESSAGES")
@NamedQueries({@NamedQuery(name = "ErrorMessages.findAll", query = "SELECT e FROM ErrorMessages e"), @NamedQuery(name = "ErrorMessages.findByErrorCode", query = "SELECT e FROM ErrorMessages e WHERE e.errorCode = :errorCode"), @NamedQuery(name = "ErrorMessages.findByOptions", query = "SELECT e FROM ErrorMessages e WHERE e.options = :options"), @NamedQuery(name = "ErrorMessages.findByDefaultOptions", query = "SELECT e FROM ErrorMessages e WHERE e.defaultOptions = :defaultOptions"), @NamedQuery(name = "ErrorMessages.findByErrorTitle", query = "SELECT e FROM ErrorMessages e WHERE e.errorTitle = :errorTitle"), @NamedQuery(name = "ErrorMessages.findByErrorText", query = "SELECT e FROM ErrorMessages e WHERE e.errorText = :errorText"), @NamedQuery(name = "ErrorMessages.findByDescription", query = "SELECT e FROM ErrorMessages e WHERE e.description = :description"), @NamedQuery(name = "ErrorMessages.findByIcon", query = "SELECT e FROM ErrorMessages e WHERE e.icon = :icon"), @NamedQuery(name = "ErrorMessages.findByLog", query = "SELECT e FROM ErrorMessages e WHERE e.log = :log"), @NamedQuery(name = "ErrorMessages.findByObjectName", query = "SELECT e FROM ErrorMessages e WHERE e.objectName = :objectName"), @NamedQuery(name = "ErrorMessages.findByModifiedUserId", query = "SELECT e FROM ErrorMessages e WHERE e.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "ErrorMessages.findByModifiedDate", query = "SELECT e FROM ErrorMessages e WHERE e.modifiedDate = :modifiedDate")})
public class ErrorMessages implements Serializable {
    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name = "EXPENSE_MESSAGES_GENERATOR", sequenceName = "EXPENSE_MESSAGES_SEQ")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPENSE_MESSAGES_GENERATOR")
        
    @Column(name = "ERROR_CODE")
    private String errorCode;
    
    @Column(name = "OPTIONS")
    private short options;
    
    @Column(name = "DEFAULT_OPTIONS")
    private short defaultOptions;
    
    @Column(name = "ERROR_TITLE")
    private String errorTitle;
    
    @Column(name = "ERROR_TEXT")
    private String errorText;
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "ICON")
    private short icon;
    
    @Column(name = "LOG")
    private String log;
    @Column(name = "OBJECT_NAME")
    private String objectName;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
/*    @OneToMany(cascade = CascadeType.ALL, mappedBy = "errorCode")
    private Collection<ExpenseErrors> expenseErrorsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "errorCode")
    private Collection<AdvanceErrors> advanceErrorsCollection;
*/
    public ErrorMessages() {
    }

    public ErrorMessages(String errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorMessages(String errorCode, short options, short defaultOptions, String errorTitle, String errorText, short icon, String log) {
        this.errorCode = errorCode;
        this.options = options;
        this.defaultOptions = defaultOptions;
        this.errorTitle = errorTitle;
        this.errorText = errorText;
        this.icon = icon;
        this.log = log;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public short getOptions() {
        return options;
    }

    public void setOptions(short options) {
        this.options = options;
    }

    public short getDefaultOptions() {
        return defaultOptions;
    }

    public void setDefaultOptions(short defaultOptions) {
        this.defaultOptions = defaultOptions;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getIcon() {
        return icon;
    }

    public void setIcon(short icon) {
        this.icon = icon;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
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
    
    /**
     * Override to display characters rather than integers for warnings/errors
     * @return
     */
    
    public String getSeverity (){
    	if (icon == 1)
    		return IConstants.STRING_WARNING;
    	else if (icon == 2)
    		return IConstants.STRING_ERROR;
    	return IConstants.STRING_BLANK;
    }

/*    public Collection<ExpenseErrors> getExpenseErrorsCollection() {
        return expenseErrorsCollection;
    }

    public void setExpenseErrorsCollection(Collection<ExpenseErrors> expenseErrorsCollection) {
        this.expenseErrorsCollection = expenseErrorsCollection;
    }

    public Collection<AdvanceErrors> getAdvanceErrorsCollection() {
        return advanceErrorsCollection;
    }

    public void setAdvanceErrorsCollection(Collection<AdvanceErrors> advanceErrorsCollection) {
        this.advanceErrorsCollection = advanceErrorsCollection;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (errorCode != null ? errorCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ErrorMessages)) {
            return false;
        }
        ErrorMessages other = (ErrorMessages) object;
        if ((this.errorCode == null && other.errorCode != null) || (this.errorCode != null && !this.errorCode.equals(other.errorCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ErrorMessages[errorCode=" + errorCode + "]";
    }

}
