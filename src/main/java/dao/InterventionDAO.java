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
    
    public static void merge(Intervention intervention) {
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

    public static List<Intervention> getFinishedInterventionsByEmployee(Long employeeId) {
        Query query = JpaUtil.getEntityManager().createQuery("SELECT i FROM Intervention i WHERE i.employee.id = :employeeId")
                .setParameter("employeeId", employeeId);
        return (List<Intervention>) query.getResultList();
    }
}
