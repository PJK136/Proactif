package entities;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author tcadet
 */
@Entity
public class Client extends Person {
    
    @OneToMany(mappedBy="client", cascade=CascadeType.ALL)
    protected List<Intervention> interventions;

    public List<Intervention> getInterventions() {
        return Collections.unmodifiableList(interventions);
    }
    
    public Client() {}
    
    public Client(String honorific, String firstname, String lastname, Date birthDate, String phoneNumber, String email, Address address) {
        super(honorific, firstname, lastname, birthDate, phoneNumber, email, address);
    }

    public void addIntervention(Intervention intervention) {
        interventions.add(intervention);
        intervention.setClient(this);
    }
    
    @Override
    public String toString() {
        return "Client{" + super.toString() + ", interventions=" + interventions + '}';
    }
}
