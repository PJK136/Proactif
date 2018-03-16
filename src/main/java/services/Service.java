package services;

import dao.JpaUtil;
import dao.PersonDAO;
import entities.Person;
import javax.persistence.RollbackException;

/**
 *
 * @author tcadet
 */
public class Service {
    public static boolean register(Person p) {
        JpaUtil.createEntityManager();
        JpaUtil.beginTransaction();
        PersonDAO.create(p);
        try 
        {
            JpaUtil.commitTransaction();
            return true;
        } 
        catch(RollbackException e) 
        {
            JpaUtil.rollbackTransaction(); 
            return false;
        }
        finally
        {
            JpaUtil.closeEntityManager();
        }
    }
}
