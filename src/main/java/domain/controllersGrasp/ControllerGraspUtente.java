package domain.controllersGrasp;

import java.util.Map;

import domain.entities.Configuratore;
import domain.entities.Fruitore;
import domain.entities.Utente;
import infrastructure.persistence.IDatabase;


public class ControllerGraspUtente {
    private final Map<String, Utente> utenti;
    

    public ControllerGraspUtente(IDatabase salvataggi) {     
        this.utenti = salvataggi.getUtenti();
    }

    public boolean existsUsername(String username) {
        return utenti.containsKey(username);
    }

    public boolean login(String username, String password) {
          if (utenti.get(username) == null) {
            return false;
        }
          return utenti.get(username).checkCredentials(username, password);
    }


    public boolean checkDefaultCredentials(String username, String password) {
        return (username.equals("admin") && password.equals("admin"));
    }


    public void registration(String username, String password, boolean isAdmin) {
        if (isAdmin)
            utenti.put(username, new Configuratore(username, password));
        else
        	utenti.put(username, new Fruitore(username, password));
    }

    public Utente getUtente(String username) {
        return utenti.get(username);
    }
}

