package entities;

import java.util.Date;
import javax.persistence.Entity;

/**
 * @author Tristan Cadet
 * @author Paul Du
 */
@Entity
public class InterventionIncident extends Intervention {

    public InterventionIncident() {}

    public InterventionIncident(String description, Date startDate) {
        super(description, startDate);
    }

    @Override
    public String getType() {
        return "Incident";
    }

    @Override
    public String toString() {
        return "InterventionIncident{"  + super.toString() + '}';
    }
}
