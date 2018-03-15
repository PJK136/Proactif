package dao;

import entities.Person;

/**
 *
 * @author tcadet
 */
public class PersonDAO {
    
    public static void create(Person p)
    {
        JpaUtil.getEntityManager().persist(p);
    }        
}
