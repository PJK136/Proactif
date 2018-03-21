package test;

import dao.JpaUtil;
import entities.Person;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.Service;
import services.util.CsvConvert;

public final class MockData {
    private final static Logger logger = LoggerFactory.getLogger(MockData.class);
    
    final static String DATA_PATH = "src/main/resources/mockData/";
   
    public static boolean[] insertAll() {
        return new boolean[] {insertEmployees(), insertClients()};
    }
      
    public static boolean insertEmployees() {
        return insertPersons(CsvConvert.loadEmployeesWithAddresses(Paths.get(DATA_PATH + "employee.csv"), Paths.get(DATA_PATH + "employee_address.csv")));
    } 
   
    public static boolean insertClients() {
        return insertPersons(CsvConvert.loadClientsWithAddresses(Paths.get(DATA_PATH + "client.csv"), Paths.get(DATA_PATH + "client_address.csv")));
    }
     
    public static boolean insertPersons(List<? extends Person> persons) {
        for (Person person : persons) {
            if (person.getAddress().getGeoCoords() == null) {
                logger.error("Does not have geocoordinates !");
                continue;
            }
            Service.register(person, UUID.randomUUID().toString().toCharArray());
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
