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
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.RollbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.util.EmailSender;
import services.util.GeoService;
import services.util.NotificationSender;
import services.util.PasswordUtil;

/**
 * @author Tristan Cadet
 * @author Paul Du
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
    
    public static boolean resetPassword(String email) {
        JpaUtil.createEntityManager();
        try {
            Person person = PersonDAO.findByEmail(email);
            if (person != null) {
                String resetPassword = UUID.randomUUID().toString();
                char[] password = resetPassword.toCharArray();
                person.setPasswordHash(PasswordUtil.hash(password, CHARSET));
                
                JpaUtil.beginTransaction();
                PersonDAO.update(person);
                JpaUtil.commitTransaction();
                
                EmailSender.sendResetPassword(person, resetPassword);
                return true;
            }
        }catch(RollbackException e) 
        {
            JpaUtil.rollbackTransaction();
        }
        finally
        {
            JpaUtil.closeEntityManager();
        }
        return false;
    }
    
    public static boolean createAndAssignIntervention(Intervention intervention, Long clientId) 
    {
        try 
        {
            JpaUtil.createEntityManager();
            JpaUtil.beginTransaction();

            Client client = (Client) PersonDAO.findById(clientId);
            if(client == null) { // le client n'existe pas
                logger.warn("Le client (id = {}) n'existe pas pour l'intervention {}", clientId, intervention);
                return false;
            }
            
            List<Employee> availableEmployees = EmployeeDAO.getAllAvailable();
            if(availableEmployees.isEmpty())
            {   //pas d'employé disponible
                logger.warn("Aucun employé n'est disponible pour {}", intervention);
                return false;
            }

            LatLng clientCoords = client.getAddress().getGeoCoords();
            Employee closest = null;
            double distanceMin = Double.MAX_VALUE;

            for (Employee employee : availableEmployees) {
                Double currentDistance = GeoService.getTripDurationByBicycleInKm(employee.getAddress().getGeoCoords(), clientCoords);
                if (currentDistance != null)
                {
                    if(distanceMin > currentDistance)
                    {
                        distanceMin = currentDistance;
                        closest = employee; 
                    }
                }
            }
            
            if (closest == null) {
                logger.error("Impossible de trouver l'employé le plus proche pour {}", intervention);
                return false;
            }
            
            intervention.setClient(client);
            intervention.setEmployee(closest);
            intervention.setDistance(distanceMin);
            
            InterventionDAO.create(intervention);
            closest.setAvailable(false);
            PersonDAO.update(closest);
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
    
    public static List<Intervention> getInterventionsByClient(Long clientId) {
        JpaUtil.createEntityManager();
        try {
            return InterventionDAO.findInterventionsByClient(clientId);
        }
        finally
        {
            JpaUtil.closeEntityManager();
        }
    }

    public static Intervention getInterventionToDoByEmployee(Long employeeId) {
        JpaUtil.createEntityManager();
        try {
            return InterventionDAO.findInterventionToDoByEmployee(employeeId);
        }
        finally
        {
            JpaUtil.closeEntityManager();
        }
    }
    
    public static List<Intervention> getFinishedInterventionsByEmployee(Long employeeId) {
        JpaUtil.createEntityManager();
        try {
            return InterventionDAO.findFinishedInterventionsByEmployee(employeeId);
        }
        finally
        {
            JpaUtil.closeEntityManager();
        }
    }
    
    public static List<Intervention> getInterventionsByDay(Date day) {
        JpaUtil.createEntityManager();
        try {
            return InterventionDAO.findInterventionsByDay(day);
        }
        finally
        {
            JpaUtil.closeEntityManager();
        }
    }

    public static boolean fillAttestation(Intervention intervention) {
        if(intervention.getEndDate() == null) {
            intervention.setEndDate(new Date());
        }
        try {
            JpaUtil.createEntityManager();
            try {
                if(InterventionDAO.find(intervention.getId())==null || PersonDAO.findById(intervention.getEmployee().getId())==null) {
                    return false;
                }
            } catch(IllegalArgumentException ex) {
                return false;
            }
            JpaUtil.beginTransaction();
            InterventionDAO.update(intervention); 
            intervention.getEmployee().setAvailable(true);
            PersonDAO.update(intervention.getEmployee());
            JpaUtil.commitTransaction();
            NotificationSender.sendAttestationFilled(intervention);
        } catch (RollbackException ex) {
            JpaUtil.rollbackTransaction();
            return false;
        } finally {
            JpaUtil.closeEntityManager();
        }
        return true;
    }
}
