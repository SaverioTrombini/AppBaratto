package application.controllersMVC;

import application.views.ViewLogin;
import domain.controllersGrasp.ControllerGraspUtente;
import domain.entities.Utente;
import infrastructure.persistence.IDatabase;

public class ControllerMVCLogin {
	private final ControllerGraspUtente controllerGraspUtente;
	private final ViewLogin viewLogin;
	private Utente utente;

	public ControllerMVCLogin(IDatabase salvataggi) {
		this.controllerGraspUtente = new ControllerGraspUtente(salvataggi);
		this.viewLogin = new ViewLogin();
	}

	void execute() {
		switch (viewLogin.scelta()) {
		case 1 -> {
			startRegister(false);
			utente = loginFruitore();
		}
		case 2 -> utente = login();
		}
	}

	private Utente login() {
		String username, password;

		do {
			username = viewLogin.richiediInserimentoUsername();
			password = viewLogin.richiediInserimentoPassword();
			if (controllerGraspUtente.checkDefaultCredentials(username, password)) {
				viewLogin.avvisaInserimentoCredenzialiDefault();
				startRegister(true);
			} else if (!controllerGraspUtente.login(username, password)) {
				viewLogin.avvisaCredenzialiNonValide();
			}
		} while (!controllerGraspUtente.login(username, password));
		return controllerGraspUtente.getUtente(username);
	}

	private Utente loginFruitore() {
		String username, password;
		do {
			username = viewLogin.richiediInserimentoUsername();
			password = viewLogin.richiediInserimentoPassword();
			if (controllerGraspUtente.checkDefaultCredentials(username, password)
					|| !controllerGraspUtente.login(username, password)) {
				viewLogin.avvisaCredenzialiNonValide();
			}
		} while (!controllerGraspUtente.login(username, password));
		return controllerGraspUtente.getUtente(username);
	}

	private void startRegister(boolean b) {
		String username, password;
		do {
			do {
				username = viewLogin.richiediNuovoUtente();
				if (controllerGraspUtente.existsUsername(username)) {
					viewLogin.avvisaNomeGiaPresente();
				}
			} while (controllerGraspUtente.existsUsername(username));

			password = viewLogin.richiediNuovaPassword();
			if (controllerGraspUtente.checkDefaultCredentials(username, password)) {
				viewLogin.avvisaCredenzialiNonValide();
			}
		} while (controllerGraspUtente.checkDefaultCredentials(username, password));
		controllerGraspUtente.registration(username, password, b);
		viewLogin.richiediDiInserireCredenziali();
	}

	Utente getUtente() {
		return utente;
	}

}
