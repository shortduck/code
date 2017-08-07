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
@Table(name = "TKUOPT_HOURS_TYPES")
@NamedQueries({@NamedQuery(name = "TkuoptHoursTypes.findAll", query = "SELECT t FROM TkuoptHoursTypes t"), @NamedQuery(name = "TkuoptHoursTypes.findByDepartment", query = "SELECT t FROM TkuoptHoursTypes t WHERE t.tkuoptHoursTypesPK.department = :department"), @NamedQuery(name = "TkuoptHoursTypes.findByAgency", query = "SELECT t FROM TkuoptHoursTypes t WHERE t.tkuoptHoursTypesPK.agency = :agency"), @NamedQuery(name = "TkuoptHoursTypes.findByTku", query = "SELECT t FROM TkuoptHoursTypes t WHERE t.tkuoptHoursTypesPK.tku = :tku"), @NamedQuery(name = "TkuoptHoursTypes.findByHoursType", query = "SELECT t FROM TkuoptHoursTypes t WHERE t.tkuoptHoursTypesPK.hoursType = :hoursType"), @NamedQuery(name = "TkuoptHoursTypes.findByModifiedUserId", query = "SELECT t FROM TkuoptHoursTypes t WHERE t.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "TkuoptHoursTypes.findByModifiedDate", query = "SELECT t FROM TkuoptHoursTypes t WHERE t.modifiedDate = :modifiedDate")})
public class TkuoptHoursTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TkuoptHoursTypesPK tkuoptHoursTypesPK;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "HOURS_TYPE", referencedColumnName = "HOURS_TYPE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private HoursTypes hoursTypes;
    @JoinColumns({@JoinColumn(name = "DEPARTMENT", referencedColumnName = "DEPARTMENT", insertable = false, updatable = false), @JoinColumn(name = "AGENCY", referencedColumnName = "AGENCY", insertable = false, updatable = false), @JoinColumn(name = "TKU", referencedColumnName = "TKU", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Tkus tkus;

    public TkuoptHoursTypes() {
    }

    public TkuoptHoursTypes(TkuoptHoursTypesPK tkuoptHoursTypesPK) {
        this.tkuoptHoursTypesPK = tkuoptHoursTypesPK;
    }

    public TkuoptHoursTypes(String department, String agency, String tku, String hoursType) {
        this.tkuoptHoursTypesPK = new TkuoptHoursTypesPK(department, agency, tku, hoursType);
    }

    public TkuoptHoursTypesPK getTkuoptHoursTypesPK() {
        return tkuoptHoursTypesPK;
    }

    public void setTkuoptHoursTypesPK(TkuoptHoursTypesPK tkuoptHoursTypesPK) {
        this.tkuoptHoursTypesPK = tkuoptHoursTypesPK;
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

    public HoursTypes getHoursTypes() {
        return hoursTypes;
    }

    public void setHoursTypes(HoursTypes hoursTypes) {
        this.hoursTypes = hoursTypes;
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
        hash += (tkuoptHoursTypesPK != null ? tkuoptHoursTypesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TkuoptHoursTypes)) {
            return false;
        }
        TkuoptHoursTypes other = (TkuoptHoursTypes) object;
        if ((this.tkuoptHoursTypesPK == null && other.tkuoptHoursTypesPK != null) || (this.tkuoptHoursTypesPK != null && !this.tkuoptHoursTypesPK.equals(other.tkuoptHoursTypesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.TkuoptHoursTypes[tkuoptHoursTypesPK=" + tkuoptHoursTypesPK + "]";
    }

}
