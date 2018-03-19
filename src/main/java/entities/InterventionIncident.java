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

    public InterventionIncident(String description, Date startDate) {
        super(description, startDate);
    }
    
    @Override
    public String toString() {
        return "InterventionIncident{"  + super.toString() + '}';
    }
}
