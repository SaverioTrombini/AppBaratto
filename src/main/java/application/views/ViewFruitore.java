package application.views;

import baratto.myLib.MyMenu;

public class ViewFruitore {

	public final static String[] MENU_FRUITORE= {"Visualizza categorie","Visualizza parametri",
			"Gestisci articoli","Gestisci baratti"};
	
	private static MyMenu menuFruitore = new MyMenu("Menu fruitore", MENU_FRUITORE);
	
	public int scelta() {
		return menuFruitore.scegli();
	}
}
