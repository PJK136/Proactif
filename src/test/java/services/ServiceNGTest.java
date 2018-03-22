package services;

import dao.JpaUtil;
import entities.Address;
import entities.Client;
import entities.Employee;
import entities.Intervention;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import services.util.CsvConvert;

public class ServiceNGTest {
    
    final static String DATA_PATH = "src/main/resources/mockData/";
    private final static Logger logger = LoggerFactory.getLogger(ServiceNGTest.class);
    private static Stack<Client> clients = new Stack<>();
    private static Stack<Employee> employees = new Stack<>();
    private static Stack<Address> addresses = new Stack<>();
    private static Stack<Address> invalid_addresses = new Stack<>();
    private static int clientsSize;
    private static int employeesSize;
    public ServiceNGTest() {

    }

    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception {
        JpaUtil.init();  
        
        clients.addAll(CsvConvert.loadClients(Paths.get(DATA_PATH + "clients.csv")));
        addresses.addAll(CsvConvert.loadAddresses(Paths.get(DATA_PATH + "addresses.csv")));
        invalid_addresses.addAll(CsvConvert.loadAddresses(Paths.get(DATA_PATH + "invalid_addresses.csv")));
        employees.addAll(CsvConvert.loadEmployeesWithAddresses(Paths.get(DATA_PATH + "employees.csv"), Paths.get(DATA_PATH + "employees_addresses.csv")));
        
        Collections.shuffle(clients);
        Collections.shuffle(addresses);
        Collections.shuffle(invalid_addresses);
        Collections.shuffle(employees);
        
        clientsSize = clients.size();
        employeesSize = employees.size();
        assertEquals(clientsSize, addresses.size(), invalid_addresses.size());
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
    @Test(enabled=false)
    public void testRegister() {
        final int REPEAT = 20;
        boolean expResult[] = {true, false, true, false};
        
        logger.info("-----REGISTER-----");
        logger.info("Cas négatif 1 : l'email est déjà utilisé.");
        logger.info("Cas négatif 2 : l'adresse n'est pas géolocalisable.");
        logger.info("Test effectué {} fois avec des clients et adresses tirées au hasard dans un jeu de taille {}.", REPEAT, clientsSize);
        logger.info("Résultat attendu : {}", Arrays.toString(expResult));
        
        for(int i = 0; i<REPEAT; i++) {
            Client registerTwice = clients.pop();
            registerTwice.setAddress(addresses.pop());

            Client defaultCase = clients.pop();
            defaultCase.setAddress(addresses.pop());

            Client geolocFail = clients.pop();
            geolocFail.setAddress(invalid_addresses.pop());
            
            boolean result[] = {
                Service.register(registerTwice, UUID.randomUUID().toString().toCharArray()),
                Service.register(registerTwice, UUID.randomUUID().toString().toCharArray()),
                Service.register(defaultCase, UUID.randomUUID().toString().toCharArray()),
                Service.register(geolocFail, UUID.randomUUID().toString().toCharArray())
            };
            logger.info("Résultat obtenu à N={} : {}", i, Arrays.toString(result));
            assertEquals(result, expResult);
        }    
    }

    /**
     * Test of login method, of class Service.
     */
    @org.testng.annotations.Test(enabled=false)
    public void testLogin() {
        final int REPEAT = 20;
        boolean expResult[] = {false, true, false, true};
        
        logger.info("-----LOGIN-----");
        logger.info("Cas négatif 1 : l'email n'existe pas dans la BDD.");
        logger.info("Cas négatif 2 : le mot de passe ne correspond pas.");
        logger.info("Test effectué {} fois avec des clients et adresses tirées au hasard dans un jeu de taille {}.", REPEAT, clientsSize);
        logger.info("Résultat attendu : {}", Arrays.toString(expResult));
        
        for(int i = 0; i<REPEAT; i++) {
            Client notRegistered = clients.pop();
            notRegistered.setAddress(addresses.pop());
            String nrPassword = UUID.randomUUID().toString();

            Client invalidPassword = clients.pop();
            invalidPassword.setAddress(addresses.pop());
            String invPassword = UUID.randomUUID().toString();

            Client defaultCase = clients.pop();
            defaultCase.setAddress(addresses.pop());
            String dcPassword = UUID.randomUUID().toString();

            boolean result[] = new boolean[expResult.length];
            result[0] = Service.login(notRegistered.getEmail(), nrPassword.toCharArray()) != null;

            assert(Service.register(notRegistered, nrPassword.toCharArray()));
            result[1] = Service.login(notRegistered.getEmail(), nrPassword.toCharArray()) != null;

            assert(Service.register(invalidPassword, invPassword.substring(1).toCharArray()));
            result[2] = Service.login(invalidPassword.getEmail(), invPassword.toCharArray()) != null;

            assert(Service.register(defaultCase, dcPassword.toCharArray()));
            result[3] = Service.login(defaultCase.getEmail(), dcPassword.toCharArray()) != null;
            
            logger.info("Résultat obtenu à N={} : {}", i, Arrays.toString(result));
            assertEquals(result, expResult);
        }    
    }

    /**
     * Test of resetPassword method, of class Service.
     */
    @org.testng.annotations.Test
    public void testResetPassword() {
        final int REPEAT = 20;
        boolean expResult[] = {false, true, true};
        
        logger.info("-----RESET PASSWORD-----");
        logger.info("Cas négatif 1 : l'email n'existe pas dans la BDD.");
        logger.info("Test effectué {} fois avec des clients et adresses tirées au hasard dans un jeu de taille {}.", REPEAT, clientsSize);
        logger.info("Résultat attendu : {}", Arrays.toString(expResult));
        
        for(int i = 0; i<REPEAT; i++) {
            Client notRegistered = clients.pop();
            notRegistered.setAddress(addresses.pop());

            Client defaultCase = clients.pop();
            defaultCase.setAddress(addresses.pop());

            boolean result[] = new boolean[expResult.length];
            result[0] = Service.resetPassword(notRegistered.getEmail());

            assert(Service.register(notRegistered, UUID.randomUUID().toString().toCharArray()));
            result[1] = Service.resetPassword(notRegistered.getEmail());

            assert(Service.register(defaultCase, UUID.randomUUID().toString().toCharArray()));
            result[2] = Service.resetPassword(defaultCase.getEmail());
            
            logger.info("Résultat obtenu à N={} : {}", i, Arrays.toString(result));
            assertEquals(result, expResult);
        }  
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
