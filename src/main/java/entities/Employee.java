/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import static javax.persistence.TemporalType.TIME;

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
    @OneToMany(mappedBy="employee")
    List<Intervention> interventions;

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

    

    

    public List<Intervention> getInterventions() {
        return interventions;
    }

    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employee{" + super.toString() + "available=" + available + ", workStart=" + workStart + ", workEnd=" + workEnd + '}';
    }

    
    
}
