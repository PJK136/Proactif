package services;

import com.google.maps.model.LatLng;
import dao.EmployeeDAO;
import dao.InterventionDAO;
import dao.JpaUtil;
import dao.PersonDAO;
import dao.exceptions.NonexistentEntityException;
import entities.Client;
import entities.Employee;
import entities.Intervention;
import entities.Person;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import javax.persistence.RollbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.util.EmailSender;
import services.util.GeoService;
import services.util.NotificationSender;
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
            p.setPasswordHash(PasswordUtil.hash(password, CHARSET));
            
            if (p.getAddress().getGeoCoords() == null) {
                LatLng coords = GeoService.getLatLng(p.getAddress().getFullAddress());
            
                if (coords != null)
                    p.getAddress().setGeoCoords(coords);
                else {
                    logger.error("Impossible de géolocaliser {}", p);
                    return false;
                }
            }
            
            PersonDAO.create(p);
            JpaUtil.commitTransaction();
            EmailSender.sendRegistrationConfirmation(p);
            return true;
        }
        catch(RollbackException e) 
        {
            JpaUtil.rollbackTransaction(); 
            EmailSender.sendRegistrationFailure(p);
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
            intervention.setDistance(distanceMin);
            
            InterventionDAO.create(intervention);
            JpaUtil.commitTransaction();
            NotificationSender.sendInterventionNeeded(intervention);
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
    
    public static List<Intervention> getInterventionsByClient(long clientId) {
        JpaUtil.createEntityManager();
        try {
            return InterventionDAO.findInterventionsByClient(clientId);
        }
        finally
        {
            JpaUtil.closeEntityManager();
        }
    }

    public static void fillAttestation(Intervention intervention) throws NonexistentEntityException, Exception {
        if(intervention.getEndDate() == null) {
            intervention.setEndDate(new Date());
        }
        try {
            JpaUtil.createEntityManager();
            JpaUtil.beginTransaction();
            InterventionDAO.merge(intervention);
            JpaUtil.commitTransaction();
        } catch (RollbackException ex) {
            JpaUtil.rollbackTransaction();
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = intervention.getId();
                if (InterventionDAO.find(id) == null) {
                    throw new NonexistentEntityException("The intervention with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            JpaUtil.closeEntityManager();
        } 
    }        
}
