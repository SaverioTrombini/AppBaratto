package application.views;

import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.Set;

import application.views.renders.RendererBarattoResource;
import baratto.myLib.InputDati;
import baratto.myLib.MyMenu;
import domain.entities.Articolo;
import domain.entities.Baratto;
import domain.entities.Orologio;

public class ViewBaratto {
	public static final String[] MENU_BARATTO = { "Inizia un baratto", "Gestione proposte",
			"Visualizza baratti in scambio", "Gestione incontri proposti per baratto" };
	private static MyMenu menuBaratto = new MyMenu("Menu baratto", MENU_BARATTO);
	private final RendererBarattoResource catena;

	public ViewBaratto(RendererBarattoResource catena) {
		this.catena = catena;
	}

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
		return InputDati.leggiIntero("Inserire l'anno:", LocalDate.now().getYear(), LocalDate.now().getYear() + 1);
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

	public void stampaNonCiSonoArticoliInScambio() {
		System.out.println("Non ci sono articoli in scambio");
	}

	public void stampaBarattiInScambio(List<Baratto> articoliInScambio) {
		for (Baratto b : articoliInScambio) {
			System.out.println(catena.render(b));
		}
	}

	public void stampaNonCiSonoIncontriDaGestire() {
		System.out.println("Non sono presenti incontri da gestire");
	}

	public void stampaBaratto(Baratto baratto) {
		System.out.println(catena.render(baratto));
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

	public String richiediSelezioneProposta() {
		return "selezionare l'opzione associata all'articolo che si vuole proporre";
	}

	public String richiediSelezioneRichiesta() {
		return "selezionare l'opzione associata all'articolo a cui si e' interessati";
	}

	public String richiediQualeBarattoGestire() {
		return "selezionare l'opzione associata al baratto che si vuole gestire";
	}

	public String richiediOpzioneLuogo() {
		return "selezionare l'opzione associata al luogo che si vuole proporre";
	}

	public int stampaElencoEChiediLuogo(Map<Integer, String> elenco, String richiesta) {
		elenco.forEach((num, scelta) -> System.out.printf("%d -> %s\n", num, scelta));
		return InputDati.leggiIntero(richiesta, 1, elenco.size());
	}

	public int stampaElencoERitornaBarattoScelto(Map<Integer, Baratto> elenco, String richiesta) {
		int i = 1;
		for (Baratto b : elenco.values()) {
			System.out.printf("%d -> %s\n", i++, catena.render(b));
		}
		return InputDati.leggiIntero(richiesta, 1, elenco.size());
	}

	public int stampaElencoEChiediArticolo(Map<Integer, Articolo> elencoDisponibili, String richiesta) {
		int i = 1;
		for (Articolo a : elencoDisponibili.values()) {
			System.out.printf("%d -> %s\n", i++, catena.render(a));
		}
		return InputDati.leggiIntero(richiesta, 1, elencoDisponibili.size());
	}
}
