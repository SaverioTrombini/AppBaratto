package domain.entities;

public class Configuratore extends Utente {
    public Configuratore(String username, String password) {
        super(username, password);
    }

    public boolean isAdmin() {
        return true;
    }
}