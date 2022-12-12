package application.views;

import baratto.myLib.InputDati;
import myLib.MyMenu;

public class ViewCategoria {
	public final static String[] MENU_CONFIGURATORE = { "Aggiungi nuova categoria",
			"Aggiungi nuova sotto categoria ad una categoria esistente", "Visualizza gerarchia",
			"Importa categorie in modalita batch" };
	private static MyMenu menuConfiguratore = new MyMenu("Menu configuratore", MENU_CONFIGURATORE);

	public int scegli() {
		return menuConfiguratore.scegli();
	}

	public String richiestaInserimentoNomeRadice() {
		return InputDati.leggiStringaNonVuota("Inserisci il nome della radice: ");
	}

	public void stampaNomeGiaPresente() {
		System.out.println("Nome gia presente");
	}

	public String richiestaInserimentoDescrizione() {
		return InputDati.leggiStringaNonVuota("Inserisci la descrizione: ");
	}

	public boolean richiestaSeInserireNuovoCampo() {
		return InputDati.yesOrNo("Vuoi aggiungere un nuovo campo?");
	}

	public String richiestaInserimentoNomeCampo() {
		return InputDati.leggiStringaNonVuota("Inserisci il nome del campo: ");
	}

	public boolean richiestaSeCampoObbligatorio() {
		return InputDati.yesOrNo("Il campo inserito e' obbligatorio?");
	}

	public void stampaSpiegazioneVisualizzazioneGerarchia() {
		System.out.println(
				"I figli vengono mostrati sotto ai padri con un tab di indentazione\nNOME - {descrizione= obbligo}");
	}

	public void stampaGerarchia(String shortString) {
		System.out.println(shortString);
	}

	public void stampaDatoNonPresente(String dato) {
		System.out.println(dato + " non presente nel sistema");
	}

	public String richiestaInserimentoNomePadreCategoria() {
		return InputDati.leggiStringaNonVuota("Inserisci il nome della categoria padre: ");
	}

	public String richiestaInserimentoNomeCategoria() {
		return InputDati.leggiStringaNonVuota("Inserisci il nome della categoria: ");
	}

	public void stampaDatoGiaPresente(String nome) {
		System.out.println(nome + " gia presente nel sistema");
	}

	public String richiestaInserimentoPercorsoAssoluto() {
		return InputDati.leggiStringaNonVuota("Inserisci il percorso assoluto del file: ");
	}

	public void stampaCaricamentoCategorieRiuscito() {
		System.out.println("Le categorie presenti nel file indicato sono state caricate");
	}

	public void stampaArticoliGiaPresenti() {
		System.out.println("Articoli già presenti");
	}

	public void stampaErroriCaricamentoFile(Exception e) {
		System.out.println("File not found / jsonparse error" + e);
	}

}
