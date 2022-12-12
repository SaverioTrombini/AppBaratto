package domain.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

public class Baratto implements Serializable {

	private Utente fruitore;

	private final Articolo proposta;
	private final Articolo richiesta;

	private String luogo;
	private LocalDate data;
	private LocalDateTime orario;

	public Baratto(Articolo proposta, Articolo richiesta) {
		this.fruitore = richiesta.getUtente();
		this.proposta = proposta;
		this.richiesta = richiesta;
		this.data = LocalDate.now();
	}

	public boolean inAttesaDi(Utente utente) {
		return fruitore.getUsername().equals(utente.getUsername());
	}

	public boolean propostaInCorso() {
		boolean artProposti = false;
		artProposti = richiesta.getStato().equals(StatoArticolo.OFFERTA_SELEZIONATA)
				&& proposta.getStato().equals(StatoArticolo.OFFERTA_ACCOPPIATA);
		return artProposti;
	}

	public boolean presente(Utente utente) {
		return proposta.getUtente().getUsername().equals(utente.getUsername())
				|| richiesta.getUtente().getUsername().equals(utente.getUsername());
	}

	public boolean scambioInCorso() {
		boolean artInScambio = false;
		artInScambio = richiesta.getStato().equals(StatoArticolo.OFFERTA_IN_SCAMBIO)
				&& proposta.getStato().equals(StatoArticolo.OFFERTA_IN_SCAMBIO);
		return artInScambio;
	}

	public boolean aggiornaStati(int scadenza, TemporalUnit tu) {
		if (getData().plus(scadenza, tu).isBefore(LocalDate.now())) {
			setStatoOfferteAperte();
			return true;
		}
		return false;
	}

	public void setLuogoEOrario(String luogo, LocalDateTime orario) {
		this.luogo = luogo;
		this.orario = orario;
		aggiornaData();
	}

	public void aggiornaData() {
		this.fruitore = fruitore.equals(proposta.getUtente()) ? richiesta.getUtente() : proposta.getUtente();
		data = LocalDate.now();
	}

	public Articolo getProposta() {
		return proposta;
	}

	public Articolo getRichiesta() {
		return richiesta;
	}

	public LocalDate getData() {
		return data;
	}

	public void setStatoOfferteInScambio() {
		proposta.setStato(StatoArticolo.OFFERTA_IN_SCAMBIO);
		richiesta.setStato(StatoArticolo.OFFERTA_IN_SCAMBIO);
		data = LocalDate.now();
	}

	public void setStatoPropostaAccoppiataRichiestaSelezionata() {
		proposta.setStato(StatoArticolo.OFFERTA_ACCOPPIATA);
		richiesta.setStato(StatoArticolo.OFFERTA_SELEZIONATA);
	}

	public void setStatoOfferteChiuse() {
		proposta.setStato(StatoArticolo.OFFERTA_CHIUSA);
		richiesta.setStato(StatoArticolo.OFFERTA_CHIUSA);
	}

	public void setStatoOfferteAperte() {
		proposta.setStato(StatoArticolo.OFFERTA_APERTA);
		richiesta.setStato(StatoArticolo.OFFERTA_APERTA);
	}
	
	@Override
	public String toString() {
		String s = scambioInCorso() ? String.format(" [luogo: %s orario: %s]", luogo, orario) : "";
		return String.format("%s ha proposto il seguente baratto" + s + ":\n %s\n %s\n", proposta.getUsername(),
				richiesta.toString(), proposta.toString());
	}

}
