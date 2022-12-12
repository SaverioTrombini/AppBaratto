package domain.controllersGrasp;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import domain.entities.Articolo;
import domain.entities.Baratto;
import domain.entities.Utente;
import infrastructure.persistence.IDatabase;

public class ControllerGraspBaratti {

	private final ControllerGraspParametri controllerGraspParametri;
	private final ControllerGraspArticoli controllerGraspArticoli;
	private final List<Baratto> elencoBaratti;

	public ControllerGraspBaratti(IDatabase salvataggi) {
		this.controllerGraspParametri = new ControllerGraspParametri(salvataggi);
		this.controllerGraspArticoli = new ControllerGraspArticoli(salvataggi);
		this.elencoBaratti = salvataggi.getBaratti();
	}

	public void addBarattoEAggiornaStato(int id_proposta, int id_richiesta) {
		Articolo proposta = controllerGraspArticoli.getArticolo(id_proposta);
		Articolo richiesta = controllerGraspArticoli.getArticolo(id_richiesta);
		Baratto baratto = new Baratto(proposta, richiesta);
		elencoBaratti.add(baratto);
		baratto.setStatoPropostaAccoppiataRichiestaSelezionata();
	}

	public List<Baratto> getBarattiDaConfermare(Utente utente) {
		return elencoBaratti.stream().filter(baratto -> baratto.inAttesaDi(utente) && baratto.propostaInCorso())
				.toList();
	}

	public List<Baratto> getBarattiScambioInCorso(Utente utente) {
		return elencoBaratti.stream().filter(baratto -> baratto.presente(utente) && baratto.scambioInCorso()).toList();
	}

	public void setStatoOfferteInScambio(Baratto baratto) {
		elencoBaratti.remove(baratto);
		baratto.setStatoOfferteInScambio();
		elencoBaratti.add(baratto);
	}

	public void setStatoOfferteAperte(Baratto baratto) {
		baratto.setStatoOfferteAperte();
		elencoBaratti.remove(baratto);
	}

	public void setStatoOfferteChiuse(Baratto baratto) {
		elencoBaratti.remove(baratto);
		baratto.setStatoOfferteChiuse();
		elencoBaratti.add(baratto);
	}

	public Map<Integer, String> gestisciGiorno(int year, int month) {
		Map<Integer, String> giorni = new TreeMap<>();
		YearMonth yM = YearMonth.of(year, month);

		for (int giorno = 1; giorno < yM.lengthOfMonth(); giorno++) {
			DayOfWeek dayOfWeek = yM.atDay(giorno).getDayOfWeek();
			if (controllerGraspParametri.isValid(dayOfWeek)) {
				giorni.put(giorno, dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ITALIAN));
			}
		}
		return giorni;
	}

	public void aggiornaStatoBaratti() {
		int scadenza = controllerGraspParametri.getScadenza();
		elencoBaratti.removeIf(baratto -> baratto.aggiornaStati(scadenza, ChronoUnit.DAYS));
	}

	public List<Baratto> getBarattiInAttesa(Utente utente) {
		return elencoBaratti.stream().filter(baratto -> baratto.inAttesaDi(utente)).filter(Baratto::scambioInCorso)
				.toList();
	}

	public void setLuogoEOrario(String luogo, LocalDateTime data, Baratto baratto) {
		baratto.setLuogoEOrario(luogo, data);
	}

	public <T> Map<Integer, T> creaMappa(Collection<T> collection) {
		Map<Integer, T> elenco = new HashMap<>();
		for (T oggetto : collection) {
			elenco.put(elenco.size() + 1, oggetto);
		}
		return elenco;
	}

}
