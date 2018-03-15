package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;

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
    protected String honorific;
    protected String surname;
    protected String forename;
    @Temporal(DATE)
    protected Date birthdate;
    protected String telephone;
    protected String mail;
    protected String passwordHash;
    @Embedded
    protected Address address;

    public Person() {}
    
    public Person(String honorific, String surname, String forename, Date birthdate, String telephone, String mail, String passwordHash, Address address) {
        this.honorific = honorific;
        this.surname = surname;
        this.forename = forename;
        this.birthdate = birthdate;
        this.telephone = telephone;
        this.mail = mail;
        this.passwordHash = passwordHash;
        this.address = address;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public String getHonorific() {
        return honorific;
    }

    public void setHonorific(String honorific) {
        this.honorific = honorific;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
        hash = 31 * hash + Objects.hashCode(this.surname);
        hash = 31 * hash + Objects.hashCode(this.forename);
        hash = 31 * hash + Objects.hashCode(this.birthdate);
        hash = 31 * hash + Objects.hashCode(this.telephone);
        hash = 31 * hash + Objects.hashCode(this.mail);
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
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.forename, other.forename)) {
            return false;
        }
        if (!Objects.equals(this.telephone, other.telephone)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.passwordHash, other.passwordHash)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.birthdate, other.birthdate)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", honorific=" + honorific + ", surname=" + surname + ", forename=" + forename + ", birthdate=" + birthdate + ", telephone=" + telephone + ", mail=" + mail + ", passwordHash=" + passwordHash + ", address=" + address + '}';
    }
    
    
    
    
}
