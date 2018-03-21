package dao;

import entities.Intervention;
import java.util.List;
import javax.persistence.Query;

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
    
    public static List<Intervention> findInterventionsByClient(long clientId) {
        Query query = JpaUtil.getEntityManager().createQuery("SELECT i FROM Intervention i WHERE i.client.id = :id")
        .setParameter("id", clientId);
        return (List<Intervention>) query.getResultList();
    }

    public static Intervention findInterventionToDoByEmployee(long employeeId) {
        Query query = JpaUtil.getEntityManager().createQuery("SELECT i FROM Intervention i WHERE i.endDate = NULL AND i.employee.id = :id")
        .setParameter("id", employeeId);
        return (Intervention) query.getSingleResult();
    }

    public static List<Intervention> findFinishedInterventionsByEmployee(Long employeeId) {
        Query query = JpaUtil.getEntityManager().createQuery("SELECT i FROM Intervention i WHERE i.endDate != NULL AND i.employee.id = :employeeId")
                .setParameter("employeeId", employeeId);
        return (List<Intervention>) query.getResultList();
    }
}
