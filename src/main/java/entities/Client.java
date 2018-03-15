/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public Client(String honorific, String surname, String forename, Date birthdate, String telephone, String mail, String passwordHash, Address address) {
        super(honorific, surname, forename, birthdate, telephone, mail, passwordHash, address);
    }

    
    
    

    
    
}
