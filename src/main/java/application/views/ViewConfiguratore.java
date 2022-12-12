package application.views;

import infrastructure.persistence.IDatabase;
import myLib.MyMenu;

public class ViewConfiguratore {

	public final static String[] MENU_CONFIGURATORE = {"Gestisci categorie","Gestisci configurazione",
			"Visualizza offerte tramite categoria"};
	private static final MyMenu menuConfiguratore = new MyMenu("Menu Configuratore", MENU_CONFIGURATORE);
	public ViewConfiguratore(IDatabase salvataggi) {
		
	}

	public int scelta() {
		return menuConfiguratore.scegli();
	}

}
