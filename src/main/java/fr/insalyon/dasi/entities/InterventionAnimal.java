package fr.insalyon.dasi.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;

/**
 * @author Tristan Cadet
 * @author Paul Du
 */
@Entity
public class InterventionAnimal extends Intervention {

    protected String animal;

    public InterventionAnimal() {}

    public InterventionAnimal(String animal, String description, Date startDate) {
        super(description, startDate);
        this.animal = animal;
    }

    @Override
    public String getType() {
        return "Animal";
    }

    public String getAnimal() {
        return animal;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 29 * hash + Objects.hashCode(this.animal);
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
        final InterventionAnimal other = (InterventionAnimal) obj;
        if (!Objects.equals(this.animal, other.animal)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InterventionAnimal{" + super.toString() + ", animal=" + animal + '}';
    }
    
}
