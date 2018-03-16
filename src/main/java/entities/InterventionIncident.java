/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author Azergante
 */
@Entity
public class InterventionIncident extends Intervention {

    public InterventionIncident() {}

    public InterventionIncident(Long id, Long version, String description, Date startDate, Date endDate, String comment, boolean success, Client client, Employee employee) {
        super(id, version, description, startDate, endDate, comment, success, client, employee);
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
        if (!(object instanceof InterventionIncident)) {
            return false;
        }
        InterventionIncident other = (InterventionIncident) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.InterventionIncident[ id=" + id + " ]";
    }
    
}
