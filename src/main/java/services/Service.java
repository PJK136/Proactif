package services;

import dao.JpaUtil;
import dao.PersonDAO;
import entities.Person;
import java.nio.charset.Charset;
import javax.persistence.RollbackException;
import services.util.PasswordUtil;

/**
 *
 * @author tcadet
 */
public final class Service {
    public static Charset CHARSET = Charset.forName("UTF-8");
    
    private Service() {}
    
    public static boolean register(Person p, char[] password) {
        p.setPasswordHash(PasswordUtil.hash(password, CHARSET));
        
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
    
    public static Person login(String email, char[] password) {
        JpaUtil.createEntityManager();
        try {
            Person person = PersonDAO.findByEmail(email);
            if (person != null) {
                if (PasswordUtil.verify(person.getPasswordHash(), password, CHARSET))
                    return person;
            }
        }
        finally
        {
            JpaUtil.closeEntityManager();
        }
        
        return null;
    }
}
