package entities;

import java.util.Date;
import java.util.List;
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
    
    public Client() {}
    
    public Client(String honorific, String firstname, String lastname, Date birthDate, String phoneNumber, String email, String passwordHash, Address address) {
        super(honorific, firstname, lastname, birthDate, phoneNumber, email, passwordHash, address);
    }

}
