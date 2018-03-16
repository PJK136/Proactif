package dao;

import entities.Person;
import javax.persistence.Query;

/**
 *
 * @author tcadet
 */
public class PersonDAO {
    
    public static void create(Person p) {
        JpaUtil.getEntityManager().persist(p);
    }
    
    public static Person findByEmail(String email) {
        Query query = JpaUtil.getEntityManager().createQuery("SELECT p FROM Person p WHERE p.email = :email")
                .setParameter("email", email);
        return (Person) query.getSingleResult();
    }
}
