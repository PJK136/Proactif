package entities;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author tcadet
 */
@Entity
public class Client extends Person {
    
    @OneToMany(mappedBy="client")
    private List<Intervention> interventions;

    public List<Intervention> getInterventions() {
        return interventions;
    }
    
    public Client() {}
    
    public Client(String honorific, String firstname, String lastname, Date birthDate, String phoneNumber, String email, Address address) {
        super(honorific, firstname, lastname, birthDate, phoneNumber, email, address);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 11 * hash + Objects.hashCode(this.interventions);
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
        if (!super.equals(obj)) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.interventions, other.interventions)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Client{" + super.toString() + ", interventions=" + interventions + '}';
    }
}
