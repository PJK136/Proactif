package services.util;

import entities.Address;
import entities.Client;
import entities.Employee;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


public class csvConvert {
    public static List<Employee> loadEmployeeWithAddress(String employeeFile, String addressFile) {
        List<Employee> employees = loadEmployee(employeeFile);
        List<Address> addresses = loadAddress(addressFile);
        if(employees.size()!=addresses.size()) {
            System.err.println("Échec, le nombre d'employés (="+employees.size()+") doit être égal au nombre d'adresses (="+addresses.size()+").");
            return null;
        }
        for(int i = 0; i<employees.size(); i++) {
            employees.get(i).setAddress(addresses.get(i));
        }
        return employees;
    }
    
    public static List<Client> loadClientWithAddress(String clientFile, String addressFile) {
        List<Client> clients = loadClient(clientFile);
        List<Address> addresses = loadAddress(addressFile);
        if(clients.size()!=addresses.size()) {
            System.err.println("Échec, le nombre de clients (="+clients.size()+") doit être égal au nombre d'adresses (="+addresses.size()+").");
            return null;
        }
        for(int i = 0; i<clients.size(); i++) {
            clients.get(i).setAddress(addresses.get(i));
        }
        return clients;
    }
    
    public static List<Employee> loadEmployee(String employeeFile) {
        List<Employee> employees = new LinkedList<>();   
        Iterable<CSVRecord> records = null;
        try {
            records = CSVParser.parse(Paths.get(employeeFile), StandardCharsets.UTF_8, CSVFormat.TDF.withFirstRecordAsHeader());
        } catch (IOException ex) {
            Logger.getLogger(csvConvert.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static List<Client> loadClient(String clientFile) {
        List<Client> clients = new LinkedList<>();   
        Iterable<CSVRecord> records = null;
        try {
            records = CSVParser.parse(Paths.get(clientFile), StandardCharsets.UTF_8, CSVFormat.TDF.withFirstRecordAsHeader());
        } catch (IOException ex) {
            Logger.getLogger(csvConvert.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static List<Address> loadAddress(String addressFile) {        
        List<Address> addresses = new LinkedList<>();   
        Iterable<CSVRecord> records = null;
        try {
            records = CSVParser.parse(Paths.get(addressFile), StandardCharsets.UTF_8, CSVFormat.TDF.withFirstRecordAsHeader());
        } catch (IOException ex) {
            Logger.getLogger(csvConvert.class.getName()).log(Level.SEVERE, null, ex);
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
                    GeoTest.getLatLng(address.getFullAddress()):
                    GeoTest.stringToLatLng(geoCoords)
            );
            addresses.add(address);                    
        }
        return addresses;
    }
    
    public static void main(String[] args) {
        final String dataPath = "src/main/resources/mockData/";
       
        /*System.out.println("------EMPLOYEE ADRESSES------");
        for(Address address: loadAddress(dataPath + "employee_address.csv")) {
            System.out.println(address);
        }
        System.out.println("------CLIENT ADRESSES------");
        for(Address address: loadAddress(dataPath + "client_address.csv")) {
            System.out.println(address);
        }*/
        
        /*System.out.println("------CLIENTS------");
        for(Client client: loadClient(dataPath + "client.csv")) {
            System.out.println(client);
        }*/
        
        /*System.out.println("------EMPLOYEES------");
        for(Employee employee: loadEmployee(dataPath + "employee.csv")) {
            System.out.println(employee);
        }*/
        
        /*System.out.println("------CLIENTS WITH ADDRESSES------");
        for(Client client: loadClientWithAddress(dataPath + "client.csv", dataPath + "client_address.csv")) {
            System.out.println(client);
        }*/
        
        System.out.println("------EMPLOYEES WITH ADDRESSES------");
        for(Employee employee: loadEmployeeWithAddress(dataPath + "employee.csv", dataPath + "employee_address.csv")) {
            System.out.println(employee);
        }
        
        
        System.out.println("------END------");
    }
}
