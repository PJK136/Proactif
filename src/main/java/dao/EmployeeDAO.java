package dao;

import entities.Employee;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Azergante
 */
public class EmployeeDAO {
    public static List<Employee> getAllAvailable() {
        Query query = JpaUtil.getEntityManager().createQuery("SELECT e FROM Employee e WHERE e.available = TRUE AND e.workStart <= :now AND :now <= e.workEnd").setParameter("now", new Date(), TemporalType.TIME);
        return (List<Employee>) query.getResultList();
    }
    
    
    
}
