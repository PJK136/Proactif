package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import javax.persistence.Version;

/**
 *
 * @author tcadet
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public abstract class Person implements Serializable {

    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Version
    protected Long version;
    protected String honorific;
    protected String firstName;
    protected String lastName;
    @Temporal(DATE)
    protected Date birthDate;
    protected String phoneNumber;
    @Column(unique = true)
    protected String email;
    protected String passwordHash;
    @Embedded
    protected Address address;

    public Person() {}
    
    public Person(String honorific, String firstName, String lastName, Date birthdate, String phoneNumber, String email, Address address) {
        this.honorific = honorific;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthdate;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getHonorific() {
        return honorific;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.honorific);
        hash = 31 * hash + Objects.hashCode(this.firstName);
        hash = 31 * hash + Objects.hashCode(this.lastName);
        hash = 31 * hash + Objects.hashCode(this.birthDate);
        hash = 31 * hash + Objects.hashCode(this.phoneNumber);
        hash = 31 * hash + Objects.hashCode(this.email);
        hash = 31 * hash + Objects.hashCode(this.passwordHash);
        hash = 31 * hash + Objects.hashCode(this.address);
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
        final Person other = (Person) obj;
        if (!Objects.equals(this.honorific, other.honorific)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.passwordHash, other.passwordHash)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.birthDate, other.birthDate)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", version=" + version + ", honorific=" + honorific + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", phoneNumber=" + phoneNumber + ", email=" + email + ", passwordHash=" + passwordHash + ", address=" + address + '}';
    }

}
