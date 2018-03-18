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
