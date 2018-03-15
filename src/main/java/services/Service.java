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
    
    public static void inscription(Person p)
    {
        JpaUtil.createEntityManager();
        JpaUtil.beginTransaction();
        PersonDAO.create(p);
        try 
        {
            JpaUtil.commitTransaction();            
        } 
        catch(RollbackException e) 
        {
            JpaUtil.rollbackTransaction(); 
        }
        finally
        {
            JpaUtil.closeEntityManager();
        }
        
    }        
}
