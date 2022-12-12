package application.controllersMVC;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import java.util.List;
import java.util.Map;

import application.views.ViewBaratto;
import domain.controllersGrasp.ControllerGraspArticoli;
import domain.controllersGrasp.ControllerGraspBaratti;
import domain.controllersGrasp.ControllerGraspParametri;
import domain.entities.Articolo;
import domain.entities.Baratto;
import domain.entities.Utente;
import infrastructure.persistence.IDatabase;

public class ControllerMVCBaratti {

	private final ControllerGraspBaratti controllerGraspBaratti;
	private final ControllerGraspParametri controllerGraspParametri;
	private final ControllerGraspArticoli controllerGraspArticoli;
	private final ViewBaratto viewBaratto;

	public ControllerMVCBaratti(IDatabase salvataggi) {
		this.controllerGraspBaratti = new ControllerGraspBaratti(salvataggi);
		this.controllerGraspParametri = new ControllerGraspParametri(salvataggi);
		this.controllerGraspArticoli = new ControllerGraspArticoli(salvataggi);
		this.viewBaratto = new ViewBaratto();
	}

	public void execute(Utente u) {
		int scelta;
		do {
			scelta = viewBaratto.scelta();
			switch (scelta) {
			case 1 -> iniziaBaratto(u);
			case 2 -> gestioneOfferte(u);
			case 3 -> stampaBarattiInScambio(u);
			case 4 -> gestioneIncontriProposteBaratto(u);
			}
		} while (scelta != 0);
	}

	private void gestioneIncontriProposteBaratto(Utente utente) {
		if (controllerGraspBaratti.getBarattiInAttesa(utente).isEmpty()) {
			viewBaratto.stampaNonCiSonoIncontriDaGestire();
			return;
		}
		gestioneIncontro(stampaElencoERitornaOpzioneSelezionata(controllerGraspBaratti.getBarattiInAttesa(utente)));
	}

	private void gestioneIncontro(Baratto baratto) {
		viewBaratto.stampaBaratto(baratto);
		if (viewBaratto.richiediSeAccettareLuogoEOrario()) {
			controllerGraspBaratti.setStatoOfferteChiuse(baratto);
			controllerGraspArticoli.setStatoChiuso(baratto.getProposta(),baratto.getRichiesta());
					
		} else {
			proponiLuogoEdOrario(baratto);
		}
	}

	private void stampaBarattiInScambio(Utente utente) {
		if (controllerGraspBaratti.getBarattiScambioInCorso(utente).isEmpty()) {
			viewBaratto.stampaNonCiSonoArticoliInScambio();
		}
		viewBaratto.stampaBarattiInScambio(controllerGraspBaratti.getBarattiScambioInCorso(utente));
	}

	private void gestioneOfferte(Utente utente) {
		if (controllerGraspBaratti.getBarattiDaConfermare(utente).isEmpty()) {
			viewBaratto.stampaNonCiSonoBarattiDaConfermare();
			return;
		}
		Baratto baratto = stampaElencoERitornaOpzioneSelezionata(controllerGraspBaratti.getBarattiDaConfermare(utente));
		viewBaratto.stampaBarattoScelto(baratto);
		if (viewBaratto.richiestaAccettazioneBarattoProposto()) {
			controllerGraspBaratti.setStatoOfferteInScambio(baratto);
			controllerGraspArticoli.setStatoInScambio(baratto.getProposta(),baratto.getRichiesta());
				viewBaratto.richiestaInserimentoLuogoEdOrario();
			proponiLuogoEdOrario(baratto);
		} else {
			controllerGraspBaratti.setStatoOfferteAperte(baratto);
			controllerGraspArticoli.setStatoAperto(baratto.getProposta(),baratto.getRichiesta());
		}
	}

	private void proponiLuogoEdOrario(Baratto baratto) {
		String luogo = stampaElencoERitornaOpzioneSelezionata(controllerGraspParametri.getLuoghi());
		LocalDateTime orario = gestioneOrario();
		controllerGraspBaratti.setLuogoEOrario(luogo, orario, baratto);
	}

	private LocalDateTime gestioneOrario() {
		LocalTime ora = gestioneOra();
		LocalDate data = gestioneData();
		return LocalDateTime.of(data, ora);
	}

	private LocalTime gestioneOra() {
		LocalTime proposta;
		do {
			int ora = viewBaratto.richiestaInserimentoOra();
			int minuti = viewBaratto.richiestaInserimentoMinuti(controllerGraspParametri.allowedMinutes());
			proposta = LocalTime.of(ora, minuti);
			if (!controllerGraspParametri.isValid(proposta)) {
				viewBaratto.stampaOrarioNonValido();
				viewBaratto.stampaOrariValidi(controllerGraspParametri.getOrario());
			}
		} while (!controllerGraspParametri.isValid(proposta));
		return proposta;
	}

	private LocalDate gestioneData() {
		LocalDate propsta;
		do {
			int year = viewBaratto.richiestaInserimentoAnno();
			int month = viewBaratto.richiestaInserimentoMese();
			int day = viewBaratto.stampaRichiestaGiorni(controllerGraspBaratti.gestisciGiorno(year, month));
			propsta = LocalDate.of(year, month, day);
			if (!propsta.isAfter(LocalDate.now())) {
				viewBaratto.stampaValoriPassatiNonValidi();
			}
		} while (!propsta.isAfter(LocalDate.now()));
		return propsta;
	}

	private <T> T stampaElencoERitornaOpzioneSelezionata(Collection<T> collection) {
		Map<Integer, T> elenco = controllerGraspBaratti.creaMappa(collection);
		int scelta = viewBaratto.stampaElencoEChiediOpzione(elenco);
		return elenco.get(scelta);
	}
	private void iniziaBaratto(Utente utente) {
		List<Articolo> articoliDisponibili = controllerGraspArticoli.getListaOfferteAperte(utente.getUsername());
		if (articoliDisponibili.isEmpty()) {
			viewBaratto.stampaNonHaiArticoliDisponibili();
			return;
		}

		Articolo proposta = stampaElencoERitornaOpzioneSelezionata(articoliDisponibili);
		List<Articolo> articoliSimili = controllerGraspArticoli.getListaArticoliDisponibili(utente,proposta.getCategoria());
		if (articoliSimili.isEmpty()) {
			viewBaratto.stampaNonCiSonoArticoliSimili();
			return;
		}

		Articolo richiesta = stampaElencoERitornaOpzioneSelezionata(articoliSimili);
		controllerGraspBaratti.addBarattoEAggiornaStato(proposta.getCodice_prodotto(), richiesta.getCodice_prodotto());
	}

}
