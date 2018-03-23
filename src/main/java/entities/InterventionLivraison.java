package entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;

/**
 * @author Tristan Cadet
 * @author Paul Du
 */
@Entity
public class InterventionLivraison extends Intervention {

    private String subject;
    private String company;
    
    public InterventionLivraison() {}

    public InterventionLivraison(String object, String company, String description, Date startDate) {
        super(description, startDate);
        this.subject = object;
        this.company = company;
    }

    @Override
    public String getType() {
        return "Livraison";
    }

    public String getSubject() {
        return subject;
    }

    public String getCompany() {
        return company;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 79 * hash + Objects.hashCode(this.subject);
        hash = 79 * hash + Objects.hashCode(this.company);
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
        final InterventionLivraison other = (InterventionLivraison) obj;
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        if (!Objects.equals(this.company, other.company)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InterventionLivraison{" + super.toString() + ", subject=" + subject + ", company=" + company + '}';
    }
}
