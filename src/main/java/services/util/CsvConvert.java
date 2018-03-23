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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class CsvConvert {
    private final static Logger logger = LoggerFactory.getLogger(CsvConvert.class);
    
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
                record.get("honorific"),
                record.get("firstName"),
                record.get("lastName"),
                dateFormat.parse(record.get("birthdate"), new ParsePosition(0)),
                record.get("phoneNumber"),
                record.get("email"),
                null,
                hourFormat.parse(record.get("workStart"), new ParsePosition(0)),
                hourFormat.parse(record.get("workEnd"), new ParsePosition(0))
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
            if (!geoCoords.isEmpty()) {
                address.setGeoCoords(GeoService.stringToLatLng(geoCoords));
            }
            addresses.add(address);                    
        }
        return addresses;
    }
    
    public static void main(String[] args) {
        final String dataPath = "src/main/resources/mockData/";
       
        /*logger.info("------EMPLOYEE ADRESSES------");
        for(Address address: loadAddresses(Paths.get(dataPath + "employees_addresses.csv"))) {
            logger.info(address.toString());
        }
        logger.info("------CLIENT ADRESSES------");
        for(Address address: loadAddresses(Paths.get(dataPath + "clients_addresses.csv"))) {
            logger.info(address.toString());
        }
        
        logger.info("------CLIENTS------");
        for(Client client: loadClients(Paths.get(dataPath + "clients.csv"))) {
            logger.info(client.toString());
        }
        
        logger.info("------EMPLOYEES------");
        for(Employee employee: loadEmployees(Paths.get(dataPath + "employees.csv"))) {
            logger.info(employee.toString());
        }
        
        logger.info("------CLIENTS WITH ADDRESSES------");
        for(Client client: loadClientsWithAddresses(Paths.get(dataPath + "clients.csv"), Paths.get(dataPath + "clients_addresses.csv"))) {
            logger.info(client.toString());
        }*/
        
        logger.info("------EMPLOYEES WITH ADDRESSES------");
        for(Employee employee: loadEmployeesWithAddresses(Paths.get(dataPath + "employees.csv"), Paths.get(dataPath + "employees_addresses.csv"))) {
            logger.info(employee.toString());
        }
        
        logger.info("------END------");
    }
}
