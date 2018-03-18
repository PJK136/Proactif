package dao;

import entities.Intervention;

/**
 *
 * @author Azergante
 */
public class InterventionDAO {
    public static void create(Intervention intervention) {
        JpaUtil.getEntityManager().persist(intervention);
    }
}
