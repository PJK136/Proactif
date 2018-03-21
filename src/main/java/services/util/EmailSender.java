package services.util;

import entities.Person;
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
    
    public static boolean sendRegistrationConfirmation(Person person) {
        return send("contact@proact.if", person.getEmail(),
                    "Bienvenue chez Proact'IF",
                    "Bonjour " + person.getFirstName() + ",\n"
                    + "Nous vous confirmons votre inscription au service Proact'IF. "
                    + "Votre numéro d'utilisateur est : " + person.getId() + ".");
    }
    
    public static boolean sendRegistrationFailure(Person person) {
        return send("contact@proact.if", person.getEmail(),
                    "Erreur d'inscription chez Proact'IF",
                    "Bonjour " + person.getFirstName() + ",\n"
                    + "Votre inscription au service Proact'IF a malencontreusement échoué… "
                    + "Merci de recommencer ultérieusement.");
    }
    
    public static boolean sendResetPassword(Person person, String password) {
        return send("contact@proact.if", person.getEmail(),
                    "Votre nouveau mot de passe chez Proact'IF",
                    "Bonjour " + person.getFirstName() + ",\n"
                    + "Votre mot de passe a été réinitialisé à la valeur " + password
                    + "\nVous pouvez dès à présent l'utiliser pour vous connecter.");
    }
    
    
}
