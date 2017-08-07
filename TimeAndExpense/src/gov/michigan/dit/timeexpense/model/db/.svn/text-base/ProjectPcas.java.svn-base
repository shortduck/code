/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.michigan.dit.timeexpense.model.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "PROJECT_PCAS")
@NamedQueries({@NamedQuery(name = "ProjectPcas.findAll", query = "SELECT p FROM ProjectPcas p"), @NamedQuery(name = "ProjectPcas.findByPrpcIdentifier", query = "SELECT p FROM ProjectPcas p WHERE p.prpcIdentifier = :prpcIdentifier"), @NamedQuery(name = "ProjectPcas.findByProjectNumber", query = "SELECT p FROM ProjectPcas p WHERE p.projectNumber = :projectNumber"), @NamedQuery(name = "ProjectPcas.findByProjectPhase", query = "SELECT p FROM ProjectPcas p WHERE p.projectPhase = :projectPhase"), @NamedQuery(name = "ProjectPcas.findByModifiedUserId", query = "SELECT p FROM ProjectPcas p WHERE p.modifiedUserId = :modifiedUserId"), @NamedQuery(name = "ProjectPcas.findByModifiedDate", query = "SELECT p FROM ProjectPcas p WHERE p.modifiedDate = :modifiedDate")})
public class ProjectPcas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    
    @Column(name = "PRPC_IDENTIFIER")
    private Integer prpcIdentifier;
    
    @Column(name = "PROJECT_NUMBER")
    private String projectNumber;
    @Column(name = "PROJECT_PHASE")
    private String projectPhase;
    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId;
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumns({@JoinColumn(name = "FACS_AGY", referencedColumnName = "FACS_AGY"), @JoinColumn(name = "APPROPRIATION_YEAR", referencedColumnName = "APPROPRIATION_YEAR"), @JoinColumn(name = "PCA", referencedColumnName = "PCA")})
    @ManyToOne(optional = false)
    private Pcas pcas;

    public ProjectPcas() {
    }

    public ProjectPcas(Integer prpcIdentifier) {
        this.prpcIdentifier = prpcIdentifier;
    }

    public ProjectPcas(Integer prpcIdentifier, String projectNumber) {
        this.prpcIdentifier = prpcIdentifier;
        this.projectNumber = projectNumber;
    }

    public Integer getPrpcIdentifier() {
        return prpcIdentifier;
    }

    public void setPrpcIdentifier(Integer prpcIdentifier) {
        this.prpcIdentifier = prpcIdentifier;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getProjectPhase() {
        return projectPhase;
    }

    public void setProjectPhase(String projectPhase) {
        this.projectPhase = projectPhase;
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

    public Pcas getPcas() {
        return pcas;
    }

    public void setPcas(Pcas pcas) {
        this.pcas = pcas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prpcIdentifier != null ? prpcIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectPcas)) {
            return false;
        }
        ProjectPcas other = (ProjectPcas) object;
        if ((this.prpcIdentifier == null && other.prpcIdentifier != null) || (this.prpcIdentifier != null && !this.prpcIdentifier.equals(other.prpcIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.michigan.dit.timeexpense.model.ProjectPcas[prpcIdentifier=" + prpcIdentifier + "]";
    }

}
