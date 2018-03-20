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
    
    public Client() {}
    
    public Client(String honorific, String firstname, String lastname, Date birthDate, String phoneNumber, String email, Address address) {
        super(honorific, firstname, lastname, birthDate, phoneNumber, email, address);
    }
    
    @Override
    public String toString() {
        return "Client{" + super.toString() + '}';
    }
}
