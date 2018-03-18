package services.util;

import entities.Address;
import entities.Client;
import entities.Employee;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.LoggerFactory;


public class CsvConvert {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(CsvConvert.class);
    
    public static List<Employee> loadEmployeesWithAddresses(Path employeeFile, Path addressFile) {
        List<Employee> employees = loadEmployees(employeeFile);
        List<Address> addresses = loadAddresses(addressFile);
        if(employees.size()!=addresses.size()) {
            logger.error("Échec, le nombre d'employés (="+employees.size()+") doit être égal au nombre d'adresses (="+addresses.size()+").");
            return null;
        }
        for(int i = 0; i<employees.size(); i++) {
            employees.get(i).setAddress(addresses.get(i));
        }
        return employees;
    }
    
    public static List<Client> loadClientsWithAddresses(Path clientFile, Path addressFile) {
        List<Client> clients = loadClients(clientFile);
        List<Address> addresses = loadAddresses(addressFile);
        if(clients.size()!=addresses.size()) {
            logger.error("Échec, le nombre de clients (="+clients.size()+") doit être égal au nombre d'adresses (="+addresses.size()+").");
            return null;
        }
        for(int i = 0; i<clients.size(); i++) {
            clients.get(i).setAddress(addresses.get(i));
        }
        return clients;
    }
    
    public static List<Employee> loadEmployees(Path employeeFile) {
        List<Employee> employees = new LinkedList<>();   
        Iterable<CSVRecord> records = null;
        try {
            records = CSVParser.parse(employeeFile, StandardCharsets.UTF_8, CSVFormat.TDF.withFirstRecordAsHeader());
        } catch (IOException ex) {
            logger.error("", ex);
        }
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (CSVRecord record : records) {
            Employee employee = new Employee(
                Boolean.parseBoolean(record.get("available")),
                hourFormat.parse(record.get("workStart"), new ParsePosition(0)),
                hourFormat.parse(record.get("workEnd"), new ParsePosition(0)),
                record.get("honorific"),
                record.get("firstName"),
                record.get("lastName"),
                dateFormat.parse(record.get("birthdate"), new ParsePosition(0)),
                record.get("phoneNumber"),
                record.get("email"),
                null
            );
            employees.add(employee);                    
        }
        return employees; 
    }
    
    public static List<Client> loadClients(Path clientFile) {
        List<Client> clients = new LinkedList<>();   
        Iterable<CSVRecord> records = null;
        try {
            records = CSVParser.parse(clientFile, StandardCharsets.UTF_8, CSVFormat.TDF.withFirstRecordAsHeader());
        } catch (IOException ex) {
            logger.error("", ex);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (CSVRecord record : records) {
            Client client = new Client(
                record.get("honorific"),
                record.get("firstName"),
                record.get("lastName"),
                dateFormat.parse(record.get("birthdate"), new ParsePosition(0)),
                record.get("phoneNumber"),
                record.get("email"),
                null
            );
            clients.add(client);                    
        }
        return clients; 
    }
    
    public static List<Address> loadAddresses(Path addressFile) {        
        List<Address> addresses = new LinkedList<>();   
        Iterable<CSVRecord> records = null;
        try {
            records = CSVParser.parse(addressFile, StandardCharsets.UTF_8, CSVFormat.TDF.withFirstRecordAsHeader());
        } catch (IOException ex) {
            logger.error("", ex);
        }
        for (CSVRecord record : records) {
            Address address = new Address(
                record.get("address1"),
                record.get("address2"),
                record.get("zipCode"),
                record.get("city"),
                record.get("country")
            );
            final String geoCoords = record.get("geoCoords");
            address.setGeoCoords(
                    geoCoords.isEmpty()?
                    GeoService.getLatLng(address.getFullAddress()):
                    GeoService.stringToLatLng(geoCoords)
            );
            addresses.add(address);                    
        }
        return addresses;
    }
    
    public static void main(String[] args) {
        final String dataPath = "src/main/resources/mockData/";
       
        /*logger.info("------EMPLOYEE ADRESSES------");
        for(Address address: loadAddresses(dataPath + "employee_address.csv")) {
            logger.info(address);
        }
        logger.info("------CLIENT ADRESSES------");
        for(Address address: loadAddresses(dataPath + "client_address.csv")) {
            logger.info(address);
        }
        
        logger.info("------CLIENTS------");
        for(Client client: loadClients(dataPath + "client.csv")) {
            logger.info(client);
        }
        
        logger.info("------EMPLOYEES------");
        for(Employee employee: loadEmployees(dataPath + "employee.csv")) {
            logger.info(employee);
        }
        
        logger.info("------CLIENTS WITH ADDRESSES------");
        for(Client client: loadClientsWithAddresses(dataPath + "client.csv", dataPath + "client_address.csv")) {
            logger.info(client);
        }*/
        
        logger.info("------EMPLOYEES WITH ADDRESSES------");
        for(Employee employee: loadEmployeesWithAddresses(Paths.get(dataPath + "employee.csv"), Paths.get(dataPath + "employee_address.csv"))) {
            logger.info(employee.toString());
        }
        
        
        logger.info("------END------");
    }
}
