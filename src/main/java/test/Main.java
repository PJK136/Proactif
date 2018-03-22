package test;

import dao.JpaUtil;
import entities.Address;
import entities.Client;
import entities.Employee;
import entities.Intervention;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.Service;
import services.util.EmailSender;

/**
 *
 * @author tcadet
 */
public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);
    
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
            logger.error("", ex);
        }
        //LatLng geolocalisation = new LatLng();
        Address addr1 = new Address("61 Avenue Roger Salengro", "", "69100", "Villeurbanne", "France");
        //addr1.setGeoCoords(GeoService.getLatLng(addr1.getFullAddress()));
        Client c1 = new Client("Mr.", "Tristan", "Cadet", d1, "02 99 XX XX XX", "xxx@xxx.xx", addr1); 
        
        Service.register(c1, new char[]{'m', 'o', 'n', 'm', 'd', 'p'});
        Service.register(c1, new char[]{'m', 'o', 'n', 'm', 'd', 'p'});
        
        Address addr2 = new Address("7 Avenue Jean Capelle Ouest", "", "69100", "Villeurbanne", "France");
        //addr2.setGeoCoords(GeoService.getLatLng(addr2.getFullAddress()));
        
        SimpleDateFormat hours = new SimpleDateFormat("HH:mm:ss");
        Date workStart = null;
        Date workEnd = null;
        try {
            workStart = hours.parse("0:00:00");
            workEnd = hours.parse("23:59:59");
        } catch (ParseException ex) {
            logger.error("", ex);
        }
        Employee e1 = new Employee("Mr.", "Tristan2", "Cadet", d1, "02 99 XX XX XX", "monmail@xxx.xx", addr2, workStart, workEnd); 
        
        Service.register(e1, new char[]{'m', 'o', 'n', 'm', 'd', 'p'});
        
        logger.info(c1.toString());
        logger.info(e1.toString());
        
        logger.info(Service.login("xxx@xxx.xx", "aeilrfjsdlkj".toCharArray()) != null ? "Logged !" : "Can't login");
        logger.info(Service.login("xxx@xxx.xx", "monmdp".toCharArray()) != null ? "Logged !" : "Can't login");
        
        
        //JpaUtil.createEntityManager();
        //c1 = (Client) PersonDAO.findById(1L);
        //JpaUtil.closeEntityManager();
        
        logger.info("--------------INTERVENTION------------------");
        Intervention intervention = new Intervention("Une description", new Date());
        if(!Service.createAndAssignIntervention(intervention, c1.getId()))
        {
            logger.info("RETURN FALSE");
        }    
        logger.info(intervention.toString());
        
        
        logger.info("--------------fillAttestation------------------");
        intervention.setSuccess(true);
        intervention.setComment("Fini sans problèmes !");
        try {
            Service.fillAttestation(intervention);
        } catch (Exception ex) {
            logger.error("{}", ex);
        }
        logger.info(intervention.toString());
        
        
        logger.info("{}", Service.getInterventionsByClient(c1.getId()));
        logger.info("{}", Service.getInterventionToDoByEmployee(e1.getId()));
        logger.info("{}", Service.getInterventionsByDay(new Date()));
        logger.info("{}", Service.resetPassword("xxx@xxx.xx"));
        
        EmailSender.send("me@google.com", "you@facebook.com", "Test", "Hello !\nHow are you ?");
        JpaUtil.destroy();
    }
    
}
