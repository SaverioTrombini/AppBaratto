package application.controllersMVC;

import application.views.renders.RendererBarattoResource;
import domain.controllersGrasp.ControllerGraspBaratti;
import domain.entities.Utente;
import infrastructure.persistence.IDatabase;

public class Controller {
	private final ControllerMVCLogin controllerMVCLogin;
	private final ControllerMVCConfiguratori controllerMVCConfiguratori;
	private final ControllerMVCFruitori controllerMVCFruitori;
	private final ControllerGraspBaratti controllerGraspBaratti;
	
	public Controller(IDatabase salvataggi, RendererBarattoResource catena) {
		this.controllerMVCLogin = new ControllerMVCLogin(salvataggi);
		this.controllerMVCConfiguratori = new ControllerMVCConfiguratori(salvataggi,catena);
		this.controllerMVCFruitori = new ControllerMVCFruitori(salvataggi,catena);
		this.controllerGraspBaratti = new ControllerGraspBaratti(salvataggi,catena);

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
