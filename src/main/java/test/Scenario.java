package test;

import dao.JpaUtil;
import entities.Address;
import entities.Client;
import entities.Employee;
import entities.Intervention;
import entities.InterventionAnimal;
import entities.InterventionIncident;
import entities.InterventionLivraison;
import entities.Person;
import java.util.Date;
import java.util.List;
import services.Service;
import services.util.ConsoleIO;

/**
 *
 * @author paul
 */
public class Scenario {
   
    private static abstract class Action {
        public String getName() {
            return getClass().getSimpleName();
        }
        
        public abstract Action run();
    }
    
    private static Action showMenu(String name, Action[] choices) {
        ConsoleIO.printMessageBox(name);
        ConsoleIO.println();
        
        for (int i = 0; i < choices.length; i++) {
            ConsoleIO.println(String.valueOf(i+1) + ". " + choices[i].getName());
        }
        
        ConsoleIO.println();
                
        return choices[ConsoleIO.askNumber("Votre choix :", 1, choices.length)-1];
    }
    
    private static class Accueil extends Action {
        @Override
        public Action run() {
            return showMenu("Proact'IF", new Action[] {new Inscription(), new Connexion(), new Quitter()});
        }
    }
    
    private static class Inscription extends Action {
        @Override
        public Action run() {
            return showMenu(getName(), new Action[] {new InscriptionClient(), new InscriptionEmploye(), new Accueil()});
        }
    }
    
    private static class InscriptionClient extends Action {
        @Override
        public String getName() {
            return "Inscription client";
        }
                
        @Override
        public Action run() {
            Client client = new Client(ConsoleIO.ask("Civilité :"),
                                       ConsoleIO.ask("Prénom :"),
                                       ConsoleIO.ask("Nom :"),
                                       ConsoleIO.askDate("Date de naissance :"),
                                       ConsoleIO.ask("Numéro de téléphone :"),
                                       ConsoleIO.ask("Email :"),
                                       null);
            Address address = new Address(ConsoleIO.ask("Adresse 1 :"),
                                          ConsoleIO.ask("Adresse 2 :"),
                                          ConsoleIO.ask("Code postal :"),
                                          ConsoleIO.ask("Ville :"),
                                          ConsoleIO.ask("Pays :"));
            client.setAddress(address);
            if (Service.register(client, ConsoleIO.ask("Mot de passe :").toCharArray())) {
                ConsoleIO.println("Vous vous êtes bien inscrit " + client.getFirstName() + ". Vous pouvez désormais vous connecter.");
                return new Connexion();
            } else {
                ConsoleIO.println("Une erreur est survenue pendant votre inscription. Veuillez réessayer ultérieurement.");
                return new Accueil();
            }
        }
    }
    
    private static class InscriptionEmploye extends Action {
        @Override
        public String getName() {
            return "Inscription employé";
        }
                
        @Override
        public Action run() {
            Employee employee = new Employee(ConsoleIO.ask("Civilité :"),
                                             ConsoleIO.ask("Prénom :"),
                                             ConsoleIO.ask("Nom :"),
                                             ConsoleIO.askDate("Date de naissance :"),
                                             ConsoleIO.ask("Numéro de téléphone :"),
                                             ConsoleIO.ask("Email :"),
                                             null,
                                             ConsoleIO.askTime("Heure début de travail : "),
                                             ConsoleIO.askTime("Heure fin de travail : "));

            Address address = new Address(ConsoleIO.ask("Adresse 1 :"),
                                          ConsoleIO.ask("Adresse 2 :"),
                                          ConsoleIO.ask("Code postal :"),
                                          ConsoleIO.ask("Ville :"),
                                          ConsoleIO.ask("Pays :"));
            employee.setAddress(address);
            
            if (Service.register(employee, ConsoleIO.ask("Mot de passe :").toCharArray())) {
                ConsoleIO.println("Vous vous êtes bien inscrit " + employee.getFirstName() + ". Vous pouvez désormais vous connecter.");
                return new Connexion();
            } else {
                ConsoleIO.println("Une erreur est survenue pendant votre inscription. Veuillez réessayer ultérieurement.");
                return new Accueil();
            }
        }
    }
        
    private static class Connexion extends Action {               
        @Override
        public Action run() {
            ConsoleIO.printMessageBox(getName());
            ConsoleIO.println();
            
            Person person = Service.login(ConsoleIO.ask("Email :"), ConsoleIO.ask("Mot de passe :").toCharArray());
            if (person == null) {
                ConsoleIO.println("Identifiants incorrects !");
                return new Accueil();
            } else if (person instanceof Client) {
                ConsoleIO.println("Bonjour " + person.getFirstName() + ".");
                return new AccueilClient((Client) person);
            } else {
                ConsoleIO.println("Bonjour " + person.getFirstName() + ".");
                return null;
            }
        }
    }

    private static class AccueilClient extends Action {
        private Client client;
        
        public AccueilClient(Client client) {
            this.client = client;
        }
        
        @Override
        public String getName() {
            return "Accueil Client";
        }
        
        @Override
        public Action run() {
            return showMenu(getName(), new Action[] {
                new DemandeIntervention(client),
                new ListeInterventions(client),
                new Accueil() {
                    @Override
                    public String getName() {
                        return "Se déconnecter";
                    }
                }
            });
        }
    }
    
    private static class DemandeIntervention extends Action {
        private final Client client;
        private Intervention intervention;
        
        public DemandeIntervention(Client client) {
            this.client = client;
        }

        @Override
        public String getName() {
            return "Demande Intervention";
        }
        
        @Override
        public Action run() {
            showMenu(getName(), new Action[] {
                new Action() {
                    @Override
                    public String getName() {
                        return "Incident";
                    }
                    
                    @Override
                    public Action run() {
                        intervention = new InterventionIncident(ConsoleIO.ask("Description :"), new Date());
                        return null;
                    }
                },
                
                new Action() {
                    @Override
                    public String getName() {
                        return "Livraison";
                    }
                    
                    @Override
                    public Action run() {
                        intervention = new InterventionLivraison(ConsoleIO.ask("Objet :"), ConsoleIO.ask("Entreprise :"), ConsoleIO.ask("Description :"), new Date());
                        return null;
                    }
                },
                
                new Action() {
                    @Override
                    public String getName() {
                        return "Animal";
                    }
                    
                    @Override
                    public Action run() {
                        intervention = new InterventionAnimal(ConsoleIO.ask("Animal :"), ConsoleIO.ask("Description :"), new Date());
                        return null;
                    }
                }
            }).run();
            
            if (Service.createAndAssignIntervention(intervention, client.getId())) {
                ConsoleIO.println("Votre demande d'intervention a été prise en compte et sera réalisée dans les plus bref délais.");
            } else {
                ConsoleIO.println("Aucun employé n'est disponible pour votre demande d'intervention. Je vous prie de ressayer ultérieurement");
            }

            return new AccueilClient(client);
        }
    }
    
    private static class ListeInterventions extends Action {
        private final Client client;
        
        public ListeInterventions(Client client) {
            this.client = client;
        }
        
        @Override
        public String getName() {
            return "Liste des interventions";
        }
        
        @Override
        public Action run() {
            List<Intervention> interventions = Service.getInterventionsByClient(client.getId());
            for (Intervention intervention : interventions) {
                ConsoleIO.println(intervention.toString());
            }
            
            return new AccueilClient(client);
        }
        
    }
            
    private static class Quitter extends Action {
        @Override
        public Action run() {
            return null;
        }
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JpaUtil.init();
        Action action = new Accueil();
        while (action != null) {
            action = action.run();
            ConsoleIO.println();
        }
        JpaUtil.destroy();
    }
    
}
