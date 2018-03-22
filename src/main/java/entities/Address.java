package entities;

import com.google.maps.model.LatLng;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author tcadet
 */
@Embeddable
public class Address implements Serializable {

    private String address1;
    private String address2;
    private String zipCode;
    private String city;
    private String country;
    private LatLng geoCoords;
    
    public Address() {}
    
    public Address(String address1, String address2, String zipCode, String city, String country) {
        this.address1 = address1;
        this.address2 = address2;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public String getFullAddress()
    {
        return address1 + ", " + zipCode + " " + city + ", " + country;
    }        
    
    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public LatLng getGeoCoords() {
        return geoCoords;
    }

    public void setGeoCoords(LatLng geoCoords) {
        this.geoCoords = geoCoords;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.address1);
        hash = 47 * hash + Objects.hashCode(this.address2);
        hash = 47 * hash + Objects.hashCode(this.zipCode);
        hash = 47 * hash + Objects.hashCode(this.city);
        hash = 47 * hash + Objects.hashCode(this.country);
        hash = 47 * hash + Objects.hashCode(this.geoCoords);
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
        final Address other = (Address) obj;
        if (!Objects.equals(this.address1, other.address1)) {
            return false;
        }
        if (!Objects.equals(this.address2, other.address2)) {
            return false;
        }
        if (!Objects.equals(this.zipCode, other.zipCode)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.geoCoords, other.geoCoords)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Address{" + "address1=" + address1 + ", address2=" + address2 + ", zipCode=" + zipCode + ", city=" + city + ", country=" + country + ", geoCoords=" + geoCoords + '}';
    }
    
}
