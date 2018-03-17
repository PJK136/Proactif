package services.util;

import entities.Address;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


public class csvConvert {
    /*public static List<Employee> loadEmployeeWithAddress(String employeeFile, String addressFile) {
        
    }
    
    public static List<Client> loadClientWithAddress(String clientFile, String addressFile) {
        
    }
    
    public static List<Employee> loadEmployee(String employeeFile) {
        
    }
    
    public static List<Client> loadClient(String clientFile) {
        
    }*/
    
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
        System.out.println("------EMPLOYEE ADRESSES------");
        for(Address address: loadAddress(dataPath + "employee_address.csv")) {
            System.out.println(address);
        }
        System.out.println("------CLIENT ADRESSES------");
        for(Address address: loadAddress(dataPath + "client_address.csv")) {
            System.out.println(address);
        }
        System.out.println("------END------");
    }
}
