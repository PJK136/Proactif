package test;

import com.google.maps.model.LatLng;
import dao.JpaUtil;
import entities.Address;
import entities.Client;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import services.Service;

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
        LatLng geolocalisation = new LatLng();
        Address addr1 = new Address("ligne addresse1", "ligne addresse1", "75000", "Paris", "France", geolocalisation);
        Client c1 = new Client("Mr.", "Tristan", "Cadet", d1, "02 99 XX XX XX", "xxx@xxx.xx", "unHash", addr1); 
        System.out.println(c1.toString());
        Service.register(c1);
        
        JpaUtil.destroy();
    }
    
}
