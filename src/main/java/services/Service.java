package services;

import com.google.maps.model.LatLng;
import dao.EmployeeDAO;
import dao.InterventionDAO;
import dao.JpaUtil;
import dao.PersonDAO;
import entities.Client;
import entities.Employee;
import entities.Intervention;
import entities.Person;
import java.nio.charset.Charset;
import java.util.List;
import javax.persistence.RollbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.util.GeoService;
import services.util.PasswordUtil;

/**
 *
 * @author tcadet
 */
public final class Service {
    private final static Logger logger = LoggerFactory.getLogger(Service.class);
    public static Charset CHARSET = Charset.forName("UTF-8");
    
    private Service() {}
    
    public static boolean register(Person p, char[] password) {       
        try 
        {
            JpaUtil.createEntityManager();
            JpaUtil.beginTransaction();
            PersonDAO.create(p);
            p.setPasswordHash(PasswordUtil.hash(password, CHARSET));
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
    
    public static boolean createAndAssignIntervention(Intervention intervention, long clientId) 
    {
        try 
        {
            JpaUtil.createEntityManager();
            JpaUtil.beginTransaction();

            Client client = (Client) PersonDAO.findById(clientId);
            
            List<Employee> availableEmployees = EmployeeDAO.getAllAvailable();
            if(availableEmployees.isEmpty())
            {   //Aucun employé disponible
                logger.warn("Aucun employé n'est disponible pour {}", intervention);
                return false;
            }

            LatLng clientCoords = client.getAddress().getGeoCoords();
            Employee closest = null;
            double distanceMin = Double.MAX_VALUE;

            for (Employee employee : availableEmployees) {
                double currentDistance = GeoService.getTripDurationByBicycleInKm(employee.getAddress().getGeoCoords(), clientCoords);
                if(distanceMin > currentDistance)
                {
                    distanceMin = currentDistance;
                    closest = employee; 
                }    
            }
            
            intervention.setClient(client);
            intervention.setEmployee(closest);
            
            InterventionDAO.create(intervention);

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
