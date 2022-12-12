package application.controllersMVC;

import domain.controllersGrasp.ControllerGraspBaratti;
import domain.entities.Utente;
import infrastructure.persistence.IDatabase;

public class Controller {
	private final ControllerMVCLogin controllerMVCLogin;
	private final ControllerMVCConfiguratori controllerMVCConfiguratori;
	private final ControllerMVCFruitori controllerMVCFruitori;
	private final ControllerGraspBaratti controllerGraspBaratti;

	public Controller(IDatabase salvataggi) {
		this.controllerMVCLogin = new ControllerMVCLogin(salvataggi);
		this.controllerMVCConfiguratori = new ControllerMVCConfiguratori(salvataggi);
		this.controllerMVCFruitori = new ControllerMVCFruitori(salvataggi);
		this.controllerGraspBaratti = new ControllerGraspBaratti(salvataggi);

	}

	public void execute() {
		controllerGraspBaratti.aggiornaStatoBaratti();
		controllerMVCLogin.execute();
		Utente utente = controllerMVCLogin.getUtente();
		if (utente != null && utente.isAdmin()) {
			controllerMVCConfiguratori.execute(utente);
		} else {
			controllerMVCFruitori.execute(utente);
		}
	}

}
