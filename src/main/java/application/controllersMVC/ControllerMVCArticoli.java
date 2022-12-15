package application.controllersMVC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.views.ViewArticoli;
import application.views.renders.RendererBarattoResource;
import domain.entities.Articolo;
import domain.entities.Campo;
import domain.entities.Utente;
import domain.entities.StatoArticolo;
import domain.controllersGrasp.ControllerGraspArticoli;
import domain.controllersGrasp.ControllerGraspCategoria;
import infrastructure.persistence.IDatabase;

public class ControllerMVCArticoli {
	private final ControllerGraspArticoli controllerGraspArticoli;
	private final ControllerGraspCategoria controllerGraspCategoria;
	private final ViewArticoli viewArticoli;
	private final ControllerMVCCategoria controllerMVCCategoria;

	public ControllerMVCArticoli(IDatabase salvataggi, RendererBarattoResource catena) {
		this.controllerGraspArticoli = new ControllerGraspArticoli(salvataggi);
		this.controllerGraspCategoria = new ControllerGraspCategoria(salvataggi);
		this.viewArticoli = new ViewArticoli(catena);
		this.controllerMVCCategoria = new ControllerMVCCategoria(salvataggi, catena);

	}

	void execute(Utente u) {
		int scelta;
		do {
			scelta = viewArticoli.scelta();
			switch (scelta) {
			case 1 -> stampaElencoArticoliUtente(u);
			case 2 -> stampaArticoliTramiteCategoria(u);
			case 3 -> aggiungiArticolo(u);
			case 4 -> setStatoArticolo(u);
			}
		} while (scelta != 0);
	}

	private void stampaElencoArticoliUtente(Utente utente) {
		if (controllerGraspArticoli.getArticoliUtente(utente.getUsername()).isEmpty()) {
			viewArticoli.stampaNonCiSonoArticoliNelTuoElenco();
			return;
		}
		viewArticoli.stampaElencoArticoli(controllerGraspArticoli.getArticoliUtente(utente.getUsername()));
	}

	void stampaArticoliTramiteCategoria(Utente u) {
		controllerMVCCategoria.stampaGerarchia();
		String radice = controllerMVCCategoria.inputRoot();
		String categoria = controllerMVCCategoria.inputCategoriaPresente(radice);
		viewArticoli.stampaElencoArticoli(controllerGraspArticoli.getArticoliCategoria(radice, categoria, u.isAdmin()));
	}

	private void aggiungiArticolo(Utente utente) {
		controllerMVCCategoria.stampaGerarchia();
		String radice = controllerMVCCategoria.inputRoot();
		String categoria = controllerMVCCategoria.inputCategoriaPresente(radice);
		Map<String, String> campi = getCampiDesc(controllerGraspCategoria.getCampi(radice, categoria));
		controllerGraspArticoli.addArticle(utente.getUsername(), radice, categoria, StatoArticolo.OFFERTA_APERTA,
				campi);
	}

	private Map<String, String> getCampiDesc(Map<String, Campo> campiCategoria) {
		Map<String, String> descCampi = new HashMap<>();
		for (Map.Entry<String, Campo> campo : campiCategoria.entrySet()) {
			String value;
			if (campo.getValue().obbligatorio()) {
				value = viewArticoli.richiestaInserimentoCampoObbligatorio(campo);
			} else {
				value = viewArticoli.richiestaInserimentoCampoFacoltativo(campo);
			}
			descCampi.put(campo.getKey(), value);
		}
		return descCampi;
	}

	private void setStatoArticolo(Utente utente) {
		stampaElencoArticoliUtente(utente);
		int codiceArticolo;
		do {
			codiceArticolo = viewArticoli.richiestaInserimentoCodiceArticolo();
			if (!controllerGraspArticoli.exists(codiceArticolo)) {
				viewArticoli.stampaCodiceArticoloNonPresente();
			}
		} while (!controllerGraspArticoli.exists(codiceArticolo));
		viewArticoli.stampaArticolo(controllerGraspArticoli.getArticolo(codiceArticolo));
		controllerGraspArticoli.setStato(codiceArticolo,
				statoArticolo(controllerGraspArticoli.getArticolo(codiceArticolo).disponibile()));
	}

	private StatoArticolo statoArticolo(boolean disponibile) {
		if (disponibile) {
			boolean scelta = viewArticoli.richiestaRitiroArticolo();
			return controllerGraspArticoli.ritirareOfferta(scelta);
		} else {
			boolean scelta = viewArticoli.richiestaPubblicazioneArticolo();
			return controllerGraspArticoli.pubblicareOfferta(scelta);
		}
	}

	List<Articolo> getListaOfferteAperte(String username) {
		return controllerGraspArticoli.getListaOfferteAperte(username);
	}

}
