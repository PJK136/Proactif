/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private String zip;
    private String city;
    private String country;
    private LatLng gpsCoords;
    
    public Address() {}
    
    public Address(String address1, String address2, String zip, String city, String country, LatLng gpsCoords) {
        this.address1 = address1;
        this.address2 = address2;
        this.zip = zip;
        this.city = city;
        this.country = country;
        this.gpsCoords = gpsCoords;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LatLng getGpsCoords() {
        return gpsCoords;
    }

    public void setGpsCoords(LatLng gpsCoords) {
        this.gpsCoords = gpsCoords;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.address1);
        hash = 47 * hash + Objects.hashCode(this.address2);
        hash = 47 * hash + Objects.hashCode(this.zip);
        hash = 47 * hash + Objects.hashCode(this.city);
        hash = 47 * hash + Objects.hashCode(this.country);
        hash = 47 * hash + Objects.hashCode(this.gpsCoords);
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
        if (!Objects.equals(this.zip, other.zip)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.gpsCoords, other.gpsCoords)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Address{" + "address1=" + address1 + ", address2=" + address2 + ", zip=" + zip + ", city=" + city + ", country=" + country + ", gpsCoords=" + gpsCoords + '}';
    }

    

    

    
    
}
