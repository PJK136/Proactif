/*
 
 */
package dao;

import javax.persistence.Query;


public class deleteDB {
    public static void dropEmployeeTable() {
        JpaUtil.createEntityManager();
        JpaUtil.beginTransaction();
        Query query = JpaUtil.getEntityManager().createNativeQuery("DELETE FROM Employee");
        query.executeUpdate();
        JpaUtil.commitTransaction();
        JpaUtil.closeEntityManager();
    }
    
    public static void dropClientTable() {
        JpaUtil.createEntityManager();
        JpaUtil.beginTransaction();
        Query query = JpaUtil.getEntityManager().createNativeQuery("DELETE FROM Client");
        query.executeUpdate();
        JpaUtil.commitTransaction();
        JpaUtil.closeEntityManager();
    }
    
    public static void dropPersonTable() {
        JpaUtil.createEntityManager();
        JpaUtil.beginTransaction();
        Query query = JpaUtil.getEntityManager().createNativeQuery("DELETE FROM Person");
        query.executeUpdate();
        JpaUtil.commitTransaction();
        JpaUtil.closeEntityManager();
    }
    
    public static void dropInterventionAnimalTable() {
        JpaUtil.createEntityManager();
        JpaUtil.beginTransaction();
        Query query = JpaUtil.getEntityManager().createNativeQuery("DELETE FROM InterventionAnimal");
        query.executeUpdate();
        JpaUtil.commitTransaction();
        JpaUtil.closeEntityManager();
    }
    
    public static void dropInterventionIncidentTable() {
        JpaUtil.createEntityManager();
        JpaUtil.beginTransaction();
        Query query = JpaUtil.getEntityManager().createNativeQuery("DELETE FROM InterventionIncident");
        query.executeUpdate();
        JpaUtil.commitTransaction();
        JpaUtil.closeEntityManager();
    }
    
    public static void dropInterventionLivraisonTable() {
        JpaUtil.createEntityManager();
        JpaUtil.beginTransaction();
        Query query = JpaUtil.getEntityManager().createNativeQuery("DELETE FROM InterventionLivraison");
        query.executeUpdate();
        JpaUtil.commitTransaction();
        JpaUtil.closeEntityManager();
    }
    
    public static void dropInterventionTable() {
        JpaUtil.createEntityManager();
        JpaUtil.beginTransaction();
        Query query = JpaUtil.getEntityManager().createNativeQuery("DELETE FROM Intervention");
        query.executeUpdate();
        JpaUtil.commitTransaction();
        JpaUtil.closeEntityManager();
    }
    
    public static void dropAllTable() {
        dropInterventionAnimalTable();
        dropInterventionIncidentTable();
        dropInterventionLivraisonTable();
        dropInterventionTable();
        dropClientTable();
        dropEmployeeTable();
        dropPersonTable();
    }
}
