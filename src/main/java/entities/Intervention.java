package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import javax.persistence.Version;

/**
 *
 * @author tcadet
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public class Intervention implements Serializable {

    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Version
    protected Long version;
    protected String description;
    @Temporal(DATE)
    protected Date startDate;
    @Temporal(DATE)
    protected Date endDate;
    protected String comment;
    protected boolean success;
    @ManyToOne(fetch = FetchType.LAZY)
    protected Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    protected Employee employee;
    protected Double distance;
    
    public Intervention() {}
    
    public Intervention(String description, Date startDate) {
        this.description = description;
        this.startDate = startDate;
    }
    
    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }
    
    public String getType() {
        return "Générique";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.version);
        hash = 11 * hash + Objects.hashCode(this.description);
        hash = 11 * hash + Objects.hashCode(this.startDate);
        hash = 11 * hash + Objects.hashCode(this.endDate);
        hash = 11 * hash + Objects.hashCode(this.comment);
        hash = 11 * hash + (this.success ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Intervention other = (Intervention) obj;
        if (this.success != other.success) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.comment, other.comment)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.version, other.version)) {
            return false;
        }
        if (!Objects.equals(this.startDate, other.startDate)) {
            return false;
        }
        if (!Objects.equals(this.endDate, other.endDate)) {
            return false;
        }
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (!Objects.equals(this.employee, other.employee)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Intervention{" + "id=" + id + ", version=" + version + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", comment=" + comment + ", success=" + success + ", client=" + client.getId() + ", employee=" + employee.getId() + '}';
    }

}
