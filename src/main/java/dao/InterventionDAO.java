package dao;

import entities.Intervention;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Azergante
 */
public class InterventionDAO {
    public static void create(Intervention intervention) {
        JpaUtil.getEntityManager().persist(intervention);
    }
    
    public static void update(Intervention intervention) {
        JpaUtil.getEntityManager().merge(intervention);            
    }
    
    public static Intervention find(Long id) {
        return JpaUtil.getEntityManager().find(Intervention.class, id);
    }
    
    public static List<Intervention> findInterventionsByClient(Long clientId) {
        Query query = JpaUtil.getEntityManager().createQuery("SELECT i FROM Intervention i WHERE i.client.id = :id")
        .setParameter("id", clientId);
        return (List<Intervention>) query.getResultList();
    }

    public static Intervention findInterventionToDoByEmployee(Long employeeId) {
        Query query = JpaUtil.getEntityManager().createQuery("SELECT i FROM Intervention i WHERE i.endDate = NULL AND i.employee.id = :id")
        .setParameter("id", employeeId);
        List<Intervention> interventions = (List<Intervention>) query.getResultList();
        return interventions.isEmpty()?null:interventions.get(0);
    }

    public static List<Intervention> findFinishedInterventionsByEmployee(Long employeeId) {
        Query query = JpaUtil.getEntityManager().createQuery("SELECT i FROM Intervention i WHERE i.endDate != NULL AND i.employee.id = :employeeId")
                .setParameter("employeeId", employeeId);
        return (List<Intervention>) query.getResultList();
    }
    
    public static List<Intervention> findInterventionsByDay(Date day) {
        Query query = JpaUtil.getEntityManager().createQuery("SELECT i FROM Intervention i WHERE i.startDate = :day")
                .setParameter("day", day, TemporalType.DATE);
        return (List<Intervention>) query.getResultList();
    }
}
