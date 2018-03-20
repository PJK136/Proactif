package services.util;

import entities.Client;
import entities.Intervention;
import entities.Person;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author paul
 */
public class NotificationSender {
    private final static Logger logger = LoggerFactory.getLogger(NotificationSender.class);
    
    public static boolean send(Person to, String message) {
        logger.info("Notification sent :\nDate : {}\nTo : {} {} (#{})\nMessage : {}",
                new Date(), to.getFirstName(), to.getLastName(), to.getId(), message);
        return true;
    }
    
    public static boolean sendInterventionNeeded(Intervention intervention) {
        Client client = intervention.getClient();
        return send(intervention.getEmployee(),
                    "Intervention " + intervention.getType()
                    + " demandée le " + intervention.getStartDate()
                    + " pour " + client.getFirstName() + " " + client.getLastName()
                    + " (#" + client.getId() + "), " + client.getAddress().getFullAddress()
                    + " (" + Math.round(intervention.getDistance()*10)/10. + " km) : " + intervention.getDescription());
    }
}
