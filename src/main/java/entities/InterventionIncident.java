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

    public InterventionIncident(String description, Date startDate, Client client) {
        super(description, startDate, client);
    }
    
    @Override
    public String toString() {
        return "InterventionIncident{"  + super.toString() + '}';
    }
}
