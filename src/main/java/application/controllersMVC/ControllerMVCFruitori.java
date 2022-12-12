package application.controllersMVC;

import application.views.ViewFruitore;
import domain.entities.Utente;
import infrastructure.persistence.IDatabase;

public class ControllerMVCFruitori {

	private final ControllerMVCCategoria controllerMVCCategoria;
	private final ControllerMVCParametri controllerMVCParametri;
	private final ControllerMVCArticoli controllerMVCArticoli;
	private final ControllerMVCBaratti controllerMVCBaratti;
	private final ViewFruitore viewFruitore;

	public ControllerMVCFruitori(IDatabase salvataggi) {
		this.controllerMVCCategoria = new ControllerMVCCategoria(salvataggi);
		this.controllerMVCParametri = new ControllerMVCParametri(salvataggi);
		this.controllerMVCArticoli = new ControllerMVCArticoli(salvataggi);
		this.controllerMVCBaratti = new ControllerMVCBaratti(salvataggi);
		this.viewFruitore = new ViewFruitore();
	}

	public void execute(Utente u) {
		int scelta;
		do {
			scelta = viewFruitore.scelta();
			switch (scelta) {
			case 1 -> controllerMVCCategoria.toShortString();
			case 2 -> controllerMVCParametri.parametriToString();
			case 3 -> controllerMVCArticoli.execute(u);
			case 4 -> controllerMVCBaratti.execute(u);
			}
		} while (scelta != 0);
	}
}
