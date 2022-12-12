package application.views;

import myLib.InputDati;
import myLib.MyMenu;

public class ViewLogin {

	private static final String BENVENUTO = "Benvenuto nella applicazione per il baratto";
	private static final String[] VOCI = { "Nuovo fruitore", "Accedi" };
	private static final MyMenu loginRegisterMenu = new MyMenu(BENVENUTO, VOCI);

	public int scelta() {
		return loginRegisterMenu.scegliSenzaUscita();
	}

	public String richiediNuovoUtente() {
		return InputDati.leggiStringaNonVuota("Inserisci le nuove credenziali\nUsername: ");
	}

	public void avvisaNomeGiaPresente() {
		System.out.println("Username gia' presente, scegline un altro");
	}

	public String richiediNuovaPassword() {
		return InputDati.leggiStringaNonVuota("Password: ");
	}

	public void avvisaCredenzialiNonValide() {
		System.out.println("Credenziali non valide");
	}

	public String richiediInserimentoUsername() {
		return InputDati.leggiStringaNonVuota("Inserisci lo username: ");
	}

	public String richiediInserimentoPassword() {
		return InputDati.leggiStringaNonVuota("Inserisci la password: ");
	}

	public void avvisaInserimentoCredenzialiDefault() {
		System.out.println("Hai inserito le credenziali di default, procedi con l'inserimento dei nuovi dati");
	}

	public void richiediDiInserireCredenziali() {
		System.out.println("Inserisci nuovamente le credenziali per accedere al sistema");
	}

}
