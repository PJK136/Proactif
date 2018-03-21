package services;

import dao.JpaUtil;
import entities.Client;
import entities.Intervention;
import entities.Person;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.Assert.*;
import services.util.CsvConvert;
import services.util.NotificationSender;

public class ServiceNGTest {
    
    final static String DATA_PATH = "src/main/resources/testData/";
    private final static Logger logger = LoggerFactory.getLogger(NotificationSender.class);
    
    public ServiceNGTest() {
    }

    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception {
        JpaUtil.init();        
    }

    @org.testng.annotations.AfterClass
    public static void tearDownClass() throws Exception {
        JpaUtil.destroy();
    }

    @org.testng.annotations.BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @org.testng.annotations.AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of register method, of class Service.
     */
    @org.testng.annotations.Test
    public void testRegister() {
        
        List<Client> clients = CsvConvert.loadClientsWithAddresses(Paths.get(DATA_PATH + "register/clients.csv"), Paths.get(DATA_PATH + "register/adresses.csv"));
        boolean expResult[] = {true, false, true};
        boolean result[] = new boolean[clients.size()];
        
        logger.info("-----REGISTER-----");
        logger.info("Cas négatif : l'email est déjà utilisé.");
        logger.info("Jeu de données : {}", Arrays.toString(clients.toArray()));
        logger.info("Résultat attendu : {}", Arrays.toString(expResult));
        
        for(int i = 0; i<clients.size(); i++) {
            result[i] = Service.register(clients.get(i), UUID.randomUUID().toString().toCharArray());
        } 
        
        logger.info("Résultat obtenu : {}", Arrays.toString(result));
        assertEquals(result, expResult);
    }

    /**
     * Test of login method, of class Service.
     */
    @org.testng.annotations.Test
    public void testLogin() {
        System.out.println("login");
        String email = "";
        char[] password = null;
        Person expResult = null;
        Person result = Service.login(email, password);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetPassword method, of class Service.
     */
    @org.testng.annotations.Test
    public void testResetPassword() {
        System.out.println("resetPassword");
        String email = "";
        boolean expResult = false;
        boolean result = Service.resetPassword(email);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createAndAssignIntervention method, of class Service.
     */
    @org.testng.annotations.Test
    public void testCreateAndAssignIntervention() {
        System.out.println("createAndAssignIntervention");
        Intervention intervention = null;
        Long clientId = null;
        boolean expResult = false;
        boolean result = Service.createAndAssignIntervention(intervention, clientId);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInterventionsByClient method, of class Service.
     */
    @org.testng.annotations.Test
    public void testGetInterventionsByClient() {
        System.out.println("getInterventionsByClient");
        Long clientId = null;
        List expResult = null;
        List result = Service.getInterventionsByClient(clientId);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInterventionToDoByEmployee method, of class Service.
     */
    @org.testng.annotations.Test
    public void testGetInterventionToDoByEmployee() {
        System.out.println("getInterventionToDoByEmployee");
        Long employeeId = null;
        Intervention expResult = null;
        Intervention result = Service.getInterventionToDoByEmployee(employeeId);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFinishedInterventionsByEmployee method, of class Service.
     */
    @org.testng.annotations.Test
    public void testGetFinishedInterventionsByEmployee() {
        System.out.println("getFinishedInterventionsByEmployee");
        Long employeeId = null;
        List expResult = null;
        List result = Service.getFinishedInterventionsByEmployee(employeeId);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInterventionsByDay method, of class Service.
     */
    @org.testng.annotations.Test
    public void testGetInterventionsByDay() {
        System.out.println("getInterventionsByDay");
        Date day = null;
        List expResult = null;
        List result = Service.getInterventionsByDay(day);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillAttestation method, of class Service.
     */
    @org.testng.annotations.Test
    public void testFillAttestation() throws Exception {
        System.out.println("fillAttestation");
        Intervention intervention = null;
        Service.fillAttestation(intervention);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
