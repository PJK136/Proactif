package entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Azergante
 */
@Entity
public class Employee extends Person {

    boolean available;
    @Temporal(TemporalType.TIME)
    Date workStart;
    @Temporal(TemporalType.TIME)
    Date workEnd;

    public Employee(){}
    
    public Employee(boolean available, Date workStart, Date workEnd, String honorific, String firstName, String lastName, Date birthdate, String phoneNumber, String email, Address address) {
        super(honorific, firstName, lastName, birthdate, phoneNumber, email, address);
        this.available = available;
        this.workStart = workStart;
        this.workEnd = workEnd;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Date getWorkStart() {
        return workStart;
    }

    public void setWorkStart(Date workStart) {
        this.workStart = workStart;
    }

    public Date getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(Date workEnd) {
        this.workEnd = workEnd;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.available ? 1 : 0);
        hash = 83 * hash + Objects.hashCode(this.workStart);
        hash = 83 * hash + Objects.hashCode(this.workEnd);
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
        final Employee other = (Employee) obj;
        if (this.available != other.available) {
            return false;
        }
        if (!Objects.equals(this.workStart, other.workStart)) {
            return false;
        }
        if (!Objects.equals(this.workEnd, other.workEnd)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employee{" + super.toString() + ", available=" + available + ", workStart=" + workStart + ", workEnd=" + workEnd + '}';
    }
    
}
