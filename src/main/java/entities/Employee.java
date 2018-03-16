/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import javafx.util.Pair;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author Azergante
 */
@Entity
public class Employee extends Person {

    boolean available;
    Pair<LocalTime, LocalTime> workHours;
    @OneToMany(mappedBy="employee")
    List<Intervention> interventions;

    public Employee(){}
    
    public Employee(boolean available, Pair<LocalTime, LocalTime> workHours, List<Intervention> interventions, String honorific, String firstName, String lastName, Date birthdate, String phoneNumber, String email, String passwordHash, Address address) {
        super(honorific, firstName, lastName, birthdate, phoneNumber, email, passwordHash, address);
        this.available = available;
        this.workHours = workHours;
        this.interventions = interventions;
    }
    
    

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Pair<LocalTime, LocalTime> getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Pair<LocalTime, LocalTime> workHours) {
        this.workHours = workHours;
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
        return "entities.Employee[ id=" + id + " ]";
    }
    
}
