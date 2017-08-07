/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import gov.michigan.dit.timeexpense.model.core.AdvanceEvents;
import gov.michigan.dit.timeexpense.model.core.ExpenseProfiles;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author GhoshS
 */
@Entity
@Table(name = "APPOINTMENTS")
@NamedQueries({@NamedQuery(name = "Appointments.findAll", query = "SELECT a FROM Appointments a"), @NamedQuery(name = "Appointments.findByApptIdentifier", query = "SELECT a FROM Appointments a WHERE a.apptIdentifier = :apptIdentifier"), @NamedQuery(name = "Appointments.findByPositionId", query = "SELECT a FROM Appointments a WHERE a.positionId = :positionId"), @NamedQuery(name = "Appointments.findByOrganizationNumber", query = "SELECT a FROM Appointments a WHERE a.organizationNumber = :organizationNumber"), @NamedQuery(name = "Appointments.findByDeleteFlag", query = "SELECT a FROM Appointments a WHERE a.deleteFlag = :deleteFlag"), @NamedQuery(name = "Appointments.findByModifiedDate", query = "SELECT a FROM Appointments a WHERE a.modifiedDate = :modifiedDate"), @NamedQuery(name = "Appointments.findByModifiedUserId", query = "SELECT a FROM Appointments a WHERE a.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "Appointments.findByProcessLevel", query = "SELECT a FROM Appointments a WHERE a.processLevel = :processLevel"), @NamedQuery(name = "Appointments.findByPprismPositionId", query = "SELECT a FROM Appointments a WHERE a.pprismPositionId = :pprismPositionId"), @NamedQuery(name = "Appointments.findByTransferType", query = "SELECT a FROM Appointments a WHERE a.transferType = :transferType"), @NamedQuery(name = "Appointments.findByTransferDate", query = "SELECT a FROM Appointments a WHERE a.transferDate = :transferDate"), @NamedQuery(name = "Appointments.findBySequenceNumber", query = "SELECT a FROM Appointments a WHERE a.sequenceNumber = :sequenceNumber")})
public class Appointments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "APPT_IDENTIFIER")
    private Integer apptIdentifier;
    
    @Column(name = "POSITION_ID")
    private String positionId;
    @Column(name = "ORGANIZATION_NUMBER")
    private String organizationNumber;
    @Column(name = "DELETE_FLAG")
    private String deleteFlag;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "PROCESS_LEVEL")
    private String processLevel;
    @Column(name = "PPRISM_POSITION_ID")
    private String pprismPositionId;
    @Column(name = "TRANSFER_TYPE")
    private String transferType;
    @Column(name = "TRANSFER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transferDate;
    @Column(name = "SEQUENCE_NUMBER")
    private Integer sequenceNumber;
/*    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apptIdentifier")
    private Collection<AdvanceEvents> advanceEventsCollection;*/
/*    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apptIdentifier")
    private Collection<ExpenseEvents> expenseEventsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apptIdentifier")
    private Collection<ExpenseProfiles> expenseProfilesCollection;
    @OneToMany(mappedBy = "apptIdentifier")
    private Collection<Tas> tasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apptIdentifier")
    private Collection<AppointmentHistories> appointmentHistoriesCollection;
    @OneToMany(mappedBy = "apptIdentifier")
    private Collection<Approver> approverCollection;
    @JoinColumn(name = "EMP_IDENTIFIER", referencedColumnName = "EMP_IDENTIFIER")
    @ManyToOne(optional = false)
    private Employees empIdentifier;*/
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT"), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY"), @JoinColumn(name = "DEPT_CODE", referencedColumnName = "DEPT_CODE")})
    @ManyToOne
    private HrmnDeptCodes hrmnDeptCodes;
    /*@JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT"), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY"), @JoinColumn(name = "TKU", referencedColumnName = "TKU")})
    @ManyToOne(optional = false)
    private Tkus tkus;*/

    public Appointments() {
    }

    public Appointments(Integer apptIdentifier) {
        this.apptIdentifier = apptIdentifier;
    }

    public Appointments(Integer apptIdentifier, String positionId) {
        this.apptIdentifier = apptIdentifier;
        this.positionId = positionId;
    }

    public Integer getApptIdentifier() {
        return apptIdentifier;
    }

    public void setApptIdentifier(Integer apptIdentifier) {
        this.apptIdentifier = apptIdentifier;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(String organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(String modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public String getProcessLevel() {
        return processLevel;
    }

    public void setProcessLevel(String processLevel) {
        this.processLevel = processLevel;
    }

    public String getPprismPositionId() {
        return pprismPositionId;
    }

    public void setPprismPositionId(String pprismPositionId) {
        this.pprismPositionId = pprismPositionId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

/*    public Collection<AdvanceEvents> getAdvanceEventsCollection() {
        return advanceEventsCollection;
    }

    public void setAdvanceEventsCollection(Collection<AdvanceEvents> advanceEventsCollection) {
        this.advanceEventsCollection = advanceEventsCollection;
    }
*/
/*    public Collection<ExpenseEvents> getExpenseEventsCollection() {
        return expenseEventsCollection;
    }

    public void setExpenseEventsCollection(Collection<ExpenseEvents> expenseEventsCollection) {
        this.expenseEventsCollection = expenseEventsCollection;
    }

    public Collection<ExpenseProfiles> getExpenseProfilesCollection() {
        return expenseProfilesCollection;
    }

    public void setExpenseProfilesCollection(Collection<ExpenseProfiles> expenseProfilesCollection) {
        this.expenseProfilesCollection = expenseProfilesCollection;
    }

    public Collection<Tas> getTasCollection() {
        return tasCollection;
    }

    public void setTasCollection(Collection<Tas> tasCollection) {
        this.tasCollection = tasCollection;
    }

    public Collection<AppointmentHistories> getAppointmentHistoriesCollection() {
        return appointmentHistoriesCollection;
    }

    public void setAppointmentHistoriesCollection(Collection<AppointmentHistories> appointmentHistoriesCollection) {
        this.appointmentHistoriesCollection = appointmentHistoriesCollection;
    }

    public Collection<Approver> getApproverCollection() {
        return approverCollection;
    }

    public void setApproverCollection(Collection<Approver> approverCollection) {
        this.approverCollection = approverCollection;
    }

    public Employees getEmpIdentifier() {
        return empIdentifier;
    }

    public void setEmpIdentifier(Employees empIdentifier) {
        this.empIdentifier = empIdentifier;
    }*/

    public HrmnDeptCodes getHrmnDeptCodes() {
        return hrmnDeptCodes;
    }

    public void setHrmnDeptCodes(HrmnDeptCodes hrmnDeptCodes) {
        this.hrmnDeptCodes = hrmnDeptCodes;
    }

    /*public Tkus getTkus() {
        return tkus;
    }

    public void setTkus(Tkus tkus) {
        this.tkus = tkus;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (apptIdentifier != null ? apptIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appointments)) {
            return false;
        }
        Appointments other = (Appointments) object;
        if ((this.apptIdentifier == null && other.apptIdentifier != null) || (this.apptIdentifier != null && !this.apptIdentifier.equals(other.apptIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.Appointments[apptIdentifier=" + apptIdentifier + "]";
    }

}
