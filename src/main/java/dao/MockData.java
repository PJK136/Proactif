package dao;

import entities.Client;
import entities.Employee;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import services.util.CsvConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockData {
   private final static Logger logger = LoggerFactory.getLogger(MockData.class);
    
   final static String DATA_PATH = "src/main/resources/mockData/";
   final static int FLUSH_THRESHOLD = 1000;
   
   public static boolean[] insertAll() {
       return new boolean[] {insertEmployees(), insertClients()};
   }
   
   public static boolean insertEmployees() {
        JpaUtil.createEntityManager();
        EntityManager em = JpaUtil.getEntityManager();
        List<Employee> employees = CsvConvert.loadEmployeesWithAddresses(DATA_PATH + "employee.csv", DATA_PATH + "employee_address.csv");
   
        JpaUtil.beginTransaction();
        for (int i = 0; i < employees.size(); i++) {
            em.persist(employees.get(i));
            if ((i % FLUSH_THRESHOLD) == 0) {
                em.flush();
                em.clear();
            }
        }
        
        try {
            JpaUtil.commitTransaction();
        } catch(RollbackException e) {
            JpaUtil.rollbackTransaction(); 
            return false;
        } finally {
            JpaUtil.closeEntityManager();
        }
        return true;
   } 
   
   public static boolean insertClients() {
        JpaUtil.createEntityManager();
        EntityManager em = JpaUtil.getEntityManager();
        List<Client> clients = CsvConvert.loadClientsWithAddresses(DATA_PATH + "client.csv", DATA_PATH + "client_address.csv");
   
        JpaUtil.beginTransaction();
        for (int i = 0; i < clients.size(); i++) {
            em.persist(clients.get(i));
            if ((i % FLUSH_THRESHOLD) == 0) {
                em.flush();
                em.clear();
            }
        }
        
        try {
            JpaUtil.commitTransaction();
        } catch(RollbackException e) {
            JpaUtil.rollbackTransaction(); 
            return false;
        } finally {
            JpaUtil.closeEntityManager();
        }
        return true;
   }
   
   public static void main(String[] args) {
       JpaUtil.init();
       logger.info("-----MOCK DATA START-----");
       
       /*logger.info("-----INSERT EMPLOYEES-----");
       logger.info(insertEmployees());
       
       logger.info("-----INSERT CLIENTS-----");
       logger.info(insertClients());*/
       
       logger.info("-----INSERT ALL-----");
       logger.info(Arrays.toString(insertAll()));
       
       logger.info("-----MOCK DATA END-----");
       JpaUtil.destroy();
   }
}
