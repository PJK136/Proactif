package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cette classe fournit des méthodes statiques utiles pour accéder aux
 * fonctionnalités de JPA (Entity Manager, Entity Transaction). Le nom de
 * l'unité de persistance (PERSISTENCE_UNIT_NAME) doit être conforme à la
 * configuration indiquée dans le fichier persistence.xml du projet.
 *
 * @author DASI Team
 * @author Tristan Cadet
 * @author Paul Du
 */
public class JpaUtil {
    private final static Logger logger = LoggerFactory.getLogger(JpaUtil.class);
            
    // *************************************************************************************
    // * TODO: IMPORTANT -- Adapter le nom de l'Unité de Persistance (cf. persistence.xml) *
    // *************************************************************************************
    /**
     * Nom de l'unité de persistance utilisée par la Factory de Entity Manager.
     * <br><strong>Vérifier le nom de l'unité de persistance
     * (cf.&nbsp;persistence.xml)</strong>
     */
    public static final String PERSISTENCE_UNIT_NAME = "fr.insalyon.dasi_proactifJava_jar_1.0-SNAPSHOTPU";
    /**
     * Factory de Entity Manager liée à l'unité de persistance.
     * <br/><strong>Vérifier le nom de l'unité de persistance indiquée dans
     * l'attribut statique PERSISTENCE_UNIT_NAME
     * (cf.&nbsp;persistence.xml)</strong>
     */
    private static EntityManagerFactory entityManagerFactory = null;
    /**
     * Gère les instances courantes de Entity Manager liées aux Threads.
     * L'utilisation de ThreadLocal garantie une unique instance courante par
     * Thread.
     */
    private static final ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<EntityManager>() {

        @Override
        protected EntityManager initialValue() {
            return null;
        }
    };

    /**
     * Initialise la Factory de Entity Manager.
     * <br><strong>À utiliser uniquement au début de la méthode main() [projet
     * Java Application] ou dans la méthode init() de la Servlet Contrôleur
     * (ActionServlet) [projet Web Application].</strong>
     */
    public static synchronized void init() {
        logger.info("Initialisation de la factory de contexte de persistance");
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    /**
     * Libère la Factory de Entity Manager.
     * <br><strong>À utiliser uniquement à la fin de la méthode main() [projet
     * Java Application] ou dans la méthode destroy() de la Servlet Contrôleur
     * (ActionServlet) [projet Web Application].</strong>
     */
    public static synchronized void destroy() {
        logger.info("Libération de la factory de contexte de persistance");
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }

    /**
     * Créée l'instance courante de Entity Manager (liée à ce Thread).
     * <br><strong>À utiliser uniquement au niveau Service.</strong>
     */
    public static void createEntityManager() {
        logger.info("Création du contexte de persistance");
        threadLocalEntityManager.set(entityManagerFactory.createEntityManager());
    }

    /**
     * Ferme l'instance courante de Entity Manager (liée à ce Thread).
     * <br><strong>À utiliser uniquement au niveau Service.</strong>
     */
    public static void closeEntityManager() {
        logger.info("Fermeture du contexte de persistance");
        EntityManager em = threadLocalEntityManager.get();
        em.close();
        threadLocalEntityManager.set(null);
    }

    /**
     * Démarre une transaction sur l'instance courante de Entity Manager.
     * <br><strong>À utiliser uniquement au niveau Service.</strong>
     */
    public static void beginTransaction() {
        logger.info("Ouverture de la transaction (begin)");
        try {
            EntityManager em = threadLocalEntityManager.get();
            em.getTransaction().begin();
        } catch (Exception ex) {
            logger.error("Erreur lors de l'ouverture de la transaction");
            throw ex;
        }
    }

    /**
     * Valide la transaction courante sur l'instance courante de Entity Manager.
     * <br><strong>À utiliser uniquement au niveau Service.</strong>
     *
     * @exception RollbackException lorsque le <em>commit</em> n'a pas réussi.
     */
    public static void commitTransaction() throws RollbackException {
        logger.info("Validation de la transaction (commit)");
        try {
            EntityManager em = threadLocalEntityManager.get();
            em.getTransaction().commit();
        } catch (Exception ex) {
            logger.error("Erreur lors de la validation (commit) de la transaction");
            throw ex;
        }
    }

    /**
     * Annule la transaction courante sur l'instance courante de Entity Manager.
     * Si la transaction courante n'est pas démarrée, cette méthode n'effectue
     * aucune opération.
     * <br><strong>À utiliser uniquement au niveau Service.</strong>
     */
    public static void rollbackTransaction() {
        try {
            logger.info("Annulation de la transaction (rollback)");

            EntityManager em = threadLocalEntityManager.get();
            if (em.getTransaction().isActive()) {
                logger.info("Annulation effective de la transaction (rollback d'une transaction active)");
                em.getTransaction().rollback();
            }

        } catch (Exception ex) {
            logger.error("Erreur lors de l'annulation (rollback) de la transaction");
            throw ex;
        }
    }

    /**
     * Retourne l'instance courante de Entity Manager.
     * <br><strong>À utiliser uniquement au niveau DAO.</strong>
     *
     * @return instance de Entity Manager
     */
    protected static EntityManager getEntityManager() {
        logger.info("Obtention du contexte de persistance");
        return threadLocalEntityManager.get();
    }
}
