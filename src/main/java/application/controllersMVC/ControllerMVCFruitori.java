package application.controllersMVC;

import application.views.ViewFruitore;
import application.views.renders.RendererBarattoResource;
import domain.entities.Utente;
import infrastructure.persistence.IDatabase;

public class ControllerMVCFruitori {

	private final ControllerMVCCategoria controllerMVCCategoria;
	private final ControllerMVCParametri controllerMVCParametri;
	private final ControllerMVCArticoli controllerMVCArticoli;
	private final ControllerMVCBaratti controllerMVCBaratti;
	private final ViewFruitore viewFruitore;

	public ControllerMVCFruitori(IDatabase salvataggi, RendererBarattoResource catena) {
		this.controllerMVCCategoria = new ControllerMVCCategoria(salvataggi, catena);
		this.controllerMVCParametri = new ControllerMVCParametri(salvataggi, catena);
		this.controllerMVCArticoli = new ControllerMVCArticoli(salvataggi, catena);
		this.controllerMVCBaratti = new ControllerMVCBaratti(salvataggi, catena);
		this.viewFruitore = new ViewFruitore();
	}

	public void execute(Utente u) {
		int scelta;
		do {
			scelta = viewFruitore.scelta();
			switch (scelta) {
			case 1 -> controllerMVCCategoria.stampaGerarchia();
			case 2 -> controllerMVCParametri.stampaParametri();
			case 3 -> controllerMVCArticoli.execute(u);
			case 4 -> controllerMVCBaratti.execute(u);
			}
		} while (scelta != 0);
	}
}
