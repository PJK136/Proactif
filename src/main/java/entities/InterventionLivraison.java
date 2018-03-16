/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;

/**
 *
 * @author Azergante
 */
@Entity
public class InterventionLivraison extends Intervention {

    private String object;
    private String company;
    
    public InterventionLivraison() {}

    public InterventionLivraison(String object, String company, String description, Date startDate, Client client) {
        super(description, startDate, client);
        this.object = object;
        this.company = company;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 79 * hash + Objects.hashCode(this.object);
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
        if (!Objects.equals(this.object, other.object)) {
            return false;
        }
        if (!Objects.equals(this.company, other.company)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InterventionLivraison{" + super.toString() + ", object=" + object + ", company=" + company + '}';
    }
}
