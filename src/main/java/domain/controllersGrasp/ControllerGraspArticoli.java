package domain.controllersGrasp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import domain.entities.Articolo;
import domain.entities.Campo;
import domain.entities.Categoria;
import domain.entities.StatoArticolo;
import domain.entities.Utente;
import infrastructure.persistence.IDatabase;

public class ControllerGraspArticoli {
	private final Map<Integer, Articolo> articoli;
	private final ControllerGraspCategoria controllerGraspCategoria;
	private final ControllerGraspUtente controllerGraspUtente;

	public ControllerGraspArticoli(IDatabase salvataggi) {
		this.articoli = salvataggi.getArticoli();
		this.controllerGraspCategoria = new ControllerGraspCategoria(salvataggi);
		this.controllerGraspUtente = new ControllerGraspUtente(salvataggi);
	}

	public List<Articolo> getArticoliCategoria(String radice, String nomeCategoria, boolean admin) {
		return articoli.values().stream().filter(
				article -> article.getCategoria().equals(controllerGraspCategoria.searchByName(radice, nomeCategoria)))
				.filter(article -> {
					StatoArticolo state = article.getStato();
					if (admin) {
						return state.equals(StatoArticolo.OFFERTA_APERTA)
								|| state.equals(StatoArticolo.OFFERTA_IN_SCAMBIO)
								|| state.equals(StatoArticolo.OFFERTA_CHIUSA);
					} else {
						return state.equals(StatoArticolo.OFFERTA_APERTA);
					}
				}).toList();
	}

	public List<Articolo> getArticoliUtente(String username) {
		return articoli.values().stream().filter(article -> article.getUsername().equals(username)).toList();
	}

	public void addArticle(String username, String radice, String nomeCategoria, StatoArticolo stato,
			Map<String, String> descCampi) {
		Utente utente = controllerGraspUtente.getUtente(username);
		Categoria categoria = controllerGraspCategoria.searchByName(radice, nomeCategoria);
		Map<String, Campo> campi = new HashMap<>(categoria.getCampi());
		for (String nomeCampo : campi.keySet()) {
			campi.get(nomeCampo).setDescrizione(descCampi.get(nomeCampo));
		}
		articoli.put(articoli.size() + 1, new Articolo(articoli.size() + 1, utente, categoria, stato, campi));
	}

	public boolean exists(int codice_prodotto) {
		return articoli.containsKey(codice_prodotto);
	}

	public Articolo getArticolo(int codice_prodotto) {
		return articoli.get(codice_prodotto);
	}

	public void setStatoChiuso(Articolo proposta, Articolo richiesta) {
		setStato(proposta.getCodice_prodotto(), StatoArticolo.OFFERTA_CHIUSA);
		setStato(richiesta.getCodice_prodotto(), StatoArticolo.OFFERTA_CHIUSA);
	}

	public void setStatoInScambio(Articolo proposta, Articolo richiesta) {
		setStato(proposta.getCodice_prodotto(), StatoArticolo.OFFERTA_IN_SCAMBIO);
		setStato(richiesta.getCodice_prodotto(), StatoArticolo.OFFERTA_IN_SCAMBIO);
	}

	public void setStatoAperto(Articolo proposta, Articolo richiesta) {
		setStato(proposta.getCodice_prodotto(), StatoArticolo.OFFERTA_APERTA);
		setStato(richiesta.getCodice_prodotto(), StatoArticolo.OFFERTA_APERTA);
	}

	public void setStato(int codiceArticolo, StatoArticolo stato) {
		articoli.get(codiceArticolo).setStato(stato);
	}

	public StatoArticolo ritirareOfferta(boolean scelta) {
		return scelta ? StatoArticolo.OFFERTA_RITIRATA : StatoArticolo.OFFERTA_APERTA;
	}

	public StatoArticolo pubblicareOfferta(boolean scelta) {
		return scelta ? StatoArticolo.OFFERTA_APERTA : StatoArticolo.OFFERTA_RITIRATA;
	}

	public Map<Integer, Articolo> getArticoli() {
		return articoli;
	}

	public List<Articolo> getListaArticoli(String username) {
		return getStreamArticoli(username).toList();
	}

	public Stream<Articolo> getStreamArticoli(String username) {
		return articoli.values().stream().filter(articolo -> articolo.getUtente().getUsername().equals(username));
	}

	public List<Articolo> getListaOfferteAperte(String username) {
		return getStreamArticoli(username).filter(Articolo::aperta).toList();
	}

	public List<Articolo> getListaArticoliDisponibili(Utente utente, Categoria categoria) {
		return articoli.values().stream().filter(articolo -> articolo.getCategoria().equals(categoria)
				&& articolo.aperta() && !articolo.getUtente().equals(utente)).toList();
	}
}
