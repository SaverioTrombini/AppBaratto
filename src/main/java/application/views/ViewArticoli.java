package application.views;

import java.util.List;
import java.util.Map.Entry;

import application.views.renders.RendererBarattoResource;
import baratto.myLib.InputDati;
import myLib.MyMenu;
import domain.entities.Articolo;
import domain.entities.Campo;

public class ViewArticoli {
	public static final String[] MENU_ARTICOLI = { "Visualizza l'elenco dei propri articoli",
			"Visualizza l'elenco offerte aperte tramite categoria", "Aggiungi un articolo",
			"Modifica lo stato di un articolo" };
	private static MyMenu menuArticoli = new MyMenu("Menu articoli", MENU_ARTICOLI);
	private final RendererBarattoResource catena;

	public ViewArticoli(RendererBarattoResource catena) {
		this.catena=catena;
	}

	public int scelta() {
		return menuArticoli.scegli();
	}

	
	public void stampaElencoArticoli(List<Articolo> articoliCategoria) {
		for(Articolo a : articoliCategoria) {
			System.out.println(catena.render(a));
		}
	}

	public String richiestaInserimentoCampoObbligatorio(Entry<String, Campo> campo) {
		return InputDati.leggiStringaNonVuota(String.format("%s obbligatorio:", campo.getKey()));
	}

	public String richiestaInserimentoCampoFacoltativo(Entry<String, Campo> campo) {
		return InputDati.leggiStringa(String.format("%s facoltativo: ", campo.getKey()));
	}

	public int richiestaInserimentoCodiceArticolo() {
		return InputDati.leggiInteroConMinimo("inserire codice articolo: ", 0);
	}

	public void stampaCodiceArticoloNonPresente() {
		System.out.println("codice articolo non presente");
	}

	public void stampaArticolo(Articolo articolo) {
		System.out.println(catena.render(articolo));
	}

	public boolean richiestaRitiroArticolo() {
		return InputDati.yesOrNo("Ritirare l'articolo?");
	}

	public boolean richiestaPubblicazioneArticolo() {
		return InputDati.yesOrNo("Rendere l'articolo disponibile?");
	}

	public void stampaNonCiSonoArticoliNelTuoElenco() {
		System.out.println("Non sono presenti articoli nel tuo elenco");
	}
}
