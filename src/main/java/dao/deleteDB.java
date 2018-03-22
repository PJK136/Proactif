/*
 
 */
package dao;

import javax.persistence.Query;


public class deleteDB {
    
    private static void executeQueries(String... queries) {
        JpaUtil.createEntityManager();
        JpaUtil.beginTransaction();
        for(String q: queries) {
            JpaUtil.getEntityManager().createNativeQuery(q).executeUpdate();   
        }
        JpaUtil.commitTransaction();
        JpaUtil.closeEntityManager();
    }
    
    public static void dropEmployeeTable() {
        executeQueries("DELETE FROM Employee");
    }
    
    public static void dropClientTable() {
        executeQueries("DELETE FROM Client");
    }
    
    public static void dropPersonTable() {
        dropEmployeeTable();
        dropClientTable();
        executeQueries("DELETE FROM Person");
    }
    
    public static void dropInterventionAnimalTable() {
        executeQueries("DELETE FROM InterventionAnimal");
    }
    
    public static void dropInterventionIncidentTable() {
        executeQueries("DELETE FROM InterventionIncident");
    }
    
    public static void dropInterventionLivraisonTable() {
        executeQueries("DELETE FROM InterventionLivraison");
    }
    
    public static void dropInterventionTable() {
        dropInterventionAnimalTable();
        dropInterventionIncidentTable();
        dropInterventionLivraisonTable();
        executeQueries("DELETE FROM Intervention");
    }
    
    public static void dropAllTable() {
        dropInterventionTable();
        dropPersonTable();
    }
}
