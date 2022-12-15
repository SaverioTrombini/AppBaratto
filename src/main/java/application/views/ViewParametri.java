package application.views;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import application.views.renders.RendererBarattoResource;
import baratto.myLib.InputDati;
import domain.entities.Parametri;
import myLib.MyMenu;

public class ViewParametri {
	public static final int MAX_HOUR = 23;
	public static final int MIN_HOUR = 0;
	public static final int MIN_SCADENZA = 1;
	public static final int MAX_SCADENZA = 7;
	public static final String INSERISCI_GIORNO = String.format("Inserisci un giorno della settimana [%d-%d]: ",
			MIN_SCADENZA, MAX_SCADENZA);
	public static final String[] MENU_CONFIGURAZIONE = { "Visualizza Configurazione", "Modifica Configurazione",
			"Importa parametri in modalita batch" };
	private static final MyMenu menuConfigurazione = new MyMenu("Menu Configurazione", MENU_CONFIGURAZIONE);
	private final RendererBarattoResource catena;

	public ViewParametri(RendererBarattoResource catena) {
		this.catena=catena;
	}
	
	public int scelta() {
		return menuConfigurazione.scegli();
	}

	public void stampaParametri(Parametri parametri) {
		System.out.println(catena.render(parametri));
	}

	public String richiestaInserimentoPiazza() {
		return InputDati
				.leggiStringaNonVuota("Inserisci piazza di scambio (ATTENZIONE, QUESTO VALORE NON PUO' CAMBIARE): ");
	}

	public String richiestaInserimentoLuogo() {
		return InputDati.leggiStringaNonVuota("Inserire luogo di scambio: ");
	}

	public boolean richiestaInserimentoAltroLuogo() {
		return InputDati.yesOrNo("Inserire un altro luogo?");
	}

	public void stampaLuogoGiaInserito() {
		System.out.println("Luogo gia' inserito");
	}

	public DayOfWeek richiestaInserimentoGiorni() {
		return DayOfWeek.of(InputDati.leggiIntero(INSERISCI_GIORNO, MIN_SCADENZA, MAX_SCADENZA));
	}

	public boolean richiestaSeInserireAltroGiorno() {
		return InputDati.yesOrNo("Inserire un altro giorno? ");
	}

	public void stampaGiornoGiaInserito() {
		System.out.println("Giorno gia' inserito");
	}

	public int richiestaInserimentoOraIniziale() {
		return InputDati.leggiIntero("Inserisci l'ora iniziale: ", MIN_HOUR, MAX_HOUR);
	}

	public int richiediInserimentoMinutoIniziale(Set<Integer> allowedMinutes) {
		return InputDati.leggiInteroDaSet("Inserisci il minuto iniziale: ", allowedMinutes);
	}

	public void stampaOrarioNonValido() {
		System.out.println("Orario non valido");
	}

	public int richiestaInserimentoOraFinale(LocalTime orarioIniziale) {
		return InputDati.leggiIntero("Inserisci l'ora in cui terminano gli scambi: ", orarioIniziale.getHour(),
				MAX_HOUR);
	}

	public int richiediInserimentoMinutoFinale(Set<Integer> allowedMinutes) {
		return InputDati.leggiInteroDaSet("Inserisci i minuti in cui terminano gli scambi: ", allowedMinutes);
	}

	public void stampaOrarioNonValido(LocalTime stopLimitFor) {
		System.out.println("L'orario non e' valido: ");
		System.out.printf("Inserisci un orario < di %s", stopLimitFor);
	}

	public int richiediInsermientoScadenza() {
		return InputDati.leggiInteroConMinimo("Inserisci i giorni che mancano alla scadenza", 1);
	}

	public boolean richiestaModificaLuoghi() {
		return InputDati.yesOrNo("Modificare i luoghi inseriti? ");
	}

	public boolean richiestaModificaGironi() {
		return InputDati.yesOrNo("Modificare i giorni inseriti?");
	}

	public boolean richiestaModificaIntervalliOrari() {
		return InputDati.yesOrNo("Modificare gli intervalli orari inseriti? ");
	}

	public boolean richiediModificaScadenza() {
		return InputDati.yesOrNo("Modificare la scadenza predefinita di baratto?");
	}

	public boolean richiestaSeInserireIntervalliOrari() {
		return InputDati.yesOrNo("Inserire un altro intervallo?");
	}

	public void stampaPiazzaGiaInserita() {
		System.out.println("Piazza gia' inserita e non può essere modificata");
	}

	public String richiestaInserimentoPercorsoAssoluto() {
		return InputDati.leggiStringaNonVuota("Inserisci il percorso assoluto del file: ");
	}

	public void stampaCaricamentoParametriRiuscito() {
		System.out.println("I parametri presenti nel file indicato sono stati caricati");
	}

	public void stampaErroriCaricamentoFile(Exception e) {
		System.out.println("File not found / jsonparse error" + e);
	}

}
