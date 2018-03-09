package entities;

import entities.Address;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-09T15:45:33")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, String> forename;
    public static volatile SingularAttribute<Person, Date> birthdate;
    public static volatile SingularAttribute<Person, Address> address;
    public static volatile SingularAttribute<Person, String> mail;
    public static volatile SingularAttribute<Person, String> surname;
    public static volatile SingularAttribute<Person, String> telephone;
    public static volatile SingularAttribute<Person, Long> id;
    public static volatile SingularAttribute<Person, String> passwordHash;
    public static volatile SingularAttribute<Person, String> honorific;

}