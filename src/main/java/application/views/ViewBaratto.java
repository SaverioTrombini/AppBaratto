package application.views;

import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.Set;

import baratto.myLib.InputDati;
import baratto.myLib.MyMenu;
import domain.entities.Baratto;
import domain.entities.Orologio;


public class ViewBaratto {
	 public static final String[] MENU_BARATTO = {"Inizia un baratto","Gestione proposte","Visualizza baratti in scambio",
		"Gestione incontri proposti per baratto"};
	 private static MyMenu menuBaratto = new MyMenu("Menu baratto", MENU_BARATTO);
	 
	 public int scelta() {
			return menuBaratto.scegli();
		}


	


	public int richiestaInserimentoOra() {
		return InputDati.leggiIntero("Inserire l'ora:", 0, 23);
	}

	public int richiestaInserimentoMinuti(Set<Integer> allowedMinutes) {
		InputDati.leggiInteroDaSet("Inserire i minuti:", allowedMinutes);
		return 0;
	}

	public void stampaOrarioNonValido() {
		System.out.println("Orario non valido");
		
	}

	public void stampaOrariValidi(Set<Orologio> set) {
		set.forEach(timeInterval -> System.out.println(timeInterval.allowedTimes()));
		
	}

	public int richiestaInserimentoAnno() {
		return InputDati.leggiIntero("Inserire l'anno:", LocalDate.now().getYear(),
				LocalDate.now().getYear() + 1);
	}

	public int richiestaInserimentoMese() {
		return InputDati.leggiIntero("Inserire il mese[1-12]:", 1, 12);
	}

	public void stampaValoriPassatiNonValidi() {
		System.out.println("Si possono inserire solo valori futuri");
		
	}

	public int stampaRichiestaGiorni(Map<Integer, String> giorni) {
		giorni.forEach((day, dayOfWeek) -> System.out.println(" " + dayOfWeek + " " + day));
		return InputDati.leggiInteroDaSet("Inserire il giorno:", giorni.keySet());
		
	}

	public void stampaNonCiSonoBarattiDaConfermare() {
		System.out.println("Non ci sono al momento proposte di baratto");
		
	}

	public boolean richiestaAccettazioneBarattoProposto() {
		return InputDati.yesOrNo("Desideri accettare il baratto proposto?");
	}

	public void richiestaInserimentoLuogoEdOrario() {
		System.out.println("Inserisci luogo ed orario per lo scambio");
		
	}

	public void stampaBarattoScelto(Baratto baratto) {
		System.out.println(baratto);
		
	}

	public void stampaNonCiSonoArticoliInScambio() {
		System.out.println("Non ci sono articoli in scambio");
	}

	public void stampaBarattiInScambio(List<Baratto> articoliInScambio) {
		articoliInScambio.forEach(System.out::println);
		
	}

	public void stampaNonCiSonoIncontriDaGestire() {
		System.out.println("Non sono presenti incontri da gestire");
		
	}

	public void stampaBaratto(Baratto baratto) {
		System.out.println(baratto);
	}

	public boolean richiediSeAccettareLuogoEOrario() {
		return InputDati.yesOrNo("Accettare luogo ed orario proposti?");
	}

	public void stampaNonHaiArticoliDisponibili() {
		System.out.println("Lista articoli vuota, aggiungi un articolo per iniziare a barattare");
		
	}

	public void stampaNonCiSonoArticoliSimili() {
		System.out.println("Lista articoli vuota, attendi che vengano inseriti");
		
	}

	public <T> int stampaElencoEChiediOpzione(Map<Integer, T> elenco) {
		elenco.forEach((num, scelta) -> System.out.printf("%d -> %s\n", num, scelta));
		return InputDati.leggiIntero("Inserire il numero dell'opzione desiderata", 1, elenco.size());
	}
}
