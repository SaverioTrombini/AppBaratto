package application.views;

import myLib.MyMenu;

public class ViewConfiguratore {

	public final static String[] MENU_CONFIGURATORE = { "Gestisci categorie", "Gestisci configurazione",
			"Visualizza offerte tramite categoria" };

	private static final MyMenu menuConfiguratore = new MyMenu("Menu Configuratore", MENU_CONFIGURATORE);

	public int scelta() {
		return menuConfiguratore.scegli();
	}
}
