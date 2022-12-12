package domain.entities;

public class Fruitore extends Utente {
    public Fruitore(String username, String password) {
        super(username, password);
    }

    public boolean isAdmin() {
        return false;
    }
}
