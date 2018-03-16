package test;

import dao.JpaUtil;
import entities.Address;
import entities.Client;
import entities.Employee;
import entities.Intervention;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import javafx.util.Pair;
import services.Service;
import services.util.GeoTest;

/**
 *
 * @author tcadet
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JpaUtil.init();
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = null;
        try {
            d1 = sf.parse("04/11/1997");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        //LatLng geolocalisation = new LatLng();
        Address addr1 = new Address("61 Avenue Roger Salengro", "", "69100", "Villeurbanne", "France");
        addr1.setGeoCoords(GeoTest.getLatLng(addr1.getFullAddress()));
        Client c1 = new Client("Mr.", "Tristan", "Cadet", d1, "02 99 XX XX XX", "xxx@xxx.xx", addr1); 
        
        Service.register(c1, new char[]{'m', 'o', 'n', 'm', 'd', 'p'});
        
        Address addr2 = new Address("7 Avenue Jean Capelle Ouest", "", "69100", "Villeurbanne", "France");
        addr2.setGeoCoords(GeoTest.getLatLng(addr2.getFullAddress()));
        
        SimpleDateFormat hours = new SimpleDateFormat("HH:mm:ss");
        Date workStart = null;
        Date workEnd = null;
        try {
            workStart = hours.parse("10:00:00");
            workEnd = hours.parse("20:00:00");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        Employee e1 = new Employee(true, workStart, workEnd, "Mr.", "Tristan2", "Cadet", d1, "02 99 XX XX XX", "monmail@xxx.xx", addr2); 
        
        Service.register(e1, new char[]{'m', 'o', 'n', 'm', 'd', 'p'});
        
        System.out.println(c1);
        
        System.out.println(Service.login("xxx@xxx.xx", "aeilrfjsdlkj".toCharArray()));
        System.out.println(Service.login("xxx@xxx.xx", "monmdp".toCharArray()));
        
        
        System.out.println("--------------INTERVENTION------------------");
        Intervention intervention = new Intervention("Une description", new Date(), c1);
        if(!Service.createAndAssignIntervention(intervention))
        {
            System.out.println("RETURN FALSE");
        }    
        System.out.println(intervention);
        
        JpaUtil.destroy();
    }
    
}
