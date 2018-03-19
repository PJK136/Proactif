package services.util;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author paul
 */
public final class EmailSender {
    private final static Logger logger = LoggerFactory.getLogger(EmailSender.class);
    
    public static boolean send(String from, String to, String subject, String message) {
        logger.info("Email sent :\nDate : {}\nFrom : {}\nTo : {}\nSubject : {}\nMessage : {}", new Date(), from, to, subject, message);
        return true;
    }
}
