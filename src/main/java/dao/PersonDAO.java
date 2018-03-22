package dao;

import entities.Person;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author tcadet
 */
public class PersonDAO {
    public static void create(Person p) {
        JpaUtil.getEntityManager().persist(p);
    }

    public static Person update(Person p) {
        return JpaUtil.getEntityManager().merge(p);
    }
        
    public static Person findByEmail(String email) {
        Query query = JpaUtil.getEntityManager().createQuery("SELECT p FROM Person p WHERE p.email = :email")
                .setParameter("email", email);
        List<Person> interventions = (List<Person>) query.getResultList();
        return interventions.isEmpty()?null:interventions.get(0);
    }
    
    public static Person findById(Long id) {
        return (Person) JpaUtil.getEntityManager().find(Person.class, id);
    }
}
