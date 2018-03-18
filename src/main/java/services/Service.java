package services;

import com.google.maps.model.LatLng;
import dao.EmployeeDAO;
import dao.InterventionDAO;
import dao.JpaUtil;
import dao.PersonDAO;
import entities.Employee;
import entities.Intervention;
import entities.Person;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ListIterator;
import javax.persistence.RollbackException;
import services.util.GeoService;
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
    
    public static boolean createAndAssignIntervention(Intervention intervention) 
    {
        JpaUtil.createEntityManager();
        List<Employee> availableEmployees = EmployeeDAO.getAllAvailable();
        if(availableEmployees.isEmpty())
        {   //Aucun employé disponible
            JpaUtil.closeEntityManager();
            return false;
        } 
        
        ListIterator<Employee> it = availableEmployees.listIterator();
        LatLng clientCoords = intervention.getClient().getAddress().getGeoCoords();
        Employee closest = null;
        double distanceMin = GeoService.getTripDurationByBicycleInKm(it.next().getAddress().getGeoCoords(), clientCoords);
        while(it.hasNext())
        {
           Employee cur = it.next(); 
           double currentDistance = GeoService.getTripDurationByBicycleInKm(cur.getAddress().getGeoCoords(), clientCoords);
           if(distanceMin > currentDistance)
           {
               distanceMin = currentDistance;
               closest = cur; 
           }    
        }
        
        intervention.setEmployee(closest);
        JpaUtil.beginTransaction();
        InterventionDAO.create(intervention);
      
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
