package entities;

import com.google.maps.model.LatLng;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-09T15:45:33")
@StaticMetamodel(Address.class)
public class Address_ { 

    public static volatile SingularAttribute<Address, String> zip;
    public static volatile SingularAttribute<Address, String> country;
    public static volatile SingularAttribute<Address, String> address2;
    public static volatile SingularAttribute<Address, String> city;
    public static volatile SingularAttribute<Address, LatLng> gpsCoords;
    public static volatile SingularAttribute<Address, String> address1;

}