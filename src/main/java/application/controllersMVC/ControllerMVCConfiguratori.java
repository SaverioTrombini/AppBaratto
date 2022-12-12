package application.controllersMVC;

import application.views.ViewConfiguratore;
import domain.entities.Utente;
import infrastructure.persistence.IDatabase;

public class ControllerMVCConfiguratori {

	private final ViewConfiguratore viewConfiguratore;
	private final ControllerMVCCategoria controllerMVCCategoria;
	private final ControllerMVCParametri controllerMVCParametri;
	private final ControllerMVCArticoli controllerMVCArticoli;

	public ControllerMVCConfiguratori(IDatabase salvataggi) {
		viewConfiguratore = new ViewConfiguratore();
		controllerMVCCategoria = new ControllerMVCCategoria(salvataggi);
		controllerMVCParametri = new ControllerMVCParametri(salvataggi);
		controllerMVCArticoli = new ControllerMVCArticoli(salvataggi);
	}

	public void execute(Utente u) {
		int scelta;
		do {
			scelta = viewConfiguratore.scelta();
			switch (scelta) {
			case 1 -> controllerMVCCategoria.execute();
			case 2 -> controllerMVCParametri.execute();
			case 3 -> controllerMVCArticoli.stampaArticoliTramiteCategoria(u);
			}
		} while (scelta != 0);
	}
}
