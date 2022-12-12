package domain.entities;

import java.io.Serializable;

public abstract class Utente implements Serializable {
	private final String username;
	private final String password;

	public Utente(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public boolean checkCredentials(String username, String password) {
		return this.username.equals(username) && this.password.equals(password);
	}

	public abstract boolean isAdmin();

	public String getUsername() {
		return username;
	}

}