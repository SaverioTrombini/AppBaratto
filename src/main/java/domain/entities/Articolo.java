package domain.entities;

import java.io.Serializable;
import java.util.Map;


public class Articolo implements Serializable {
	
	private final int codice_prodotto;
	private final Utente utente;
	private final Categoria categoria;
	private StatoArticolo stato;
	private final Map<String, Campo> campi;
	
	public Articolo(int codice_prodotto, Utente utente, Categoria categoria, StatoArticolo stato,
			Map<String, Campo> campi) {
		this.codice_prodotto = codice_prodotto;
		this.utente = utente;
		this.categoria = categoria;
		this.stato = stato;
		this.campi = campi;
	}

	public StatoArticolo getStato() {
		return stato;
	}

	public void setStato(StatoArticolo stato) {
		this.stato = stato;
	}

	public int getCodice_prodotto() {
		return codice_prodotto;
	}

	public Utente getUtente() {
		return utente;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public Map<String, Campo> getCampi() {
		return campi;
	}

	public String getUsername() {
		return utente.getUsername();
	}

	 public boolean disponibile(){
        return stato == StatoArticolo.OFFERTA_APERTA || stato == StatoArticolo.OFFERTA_RITIRATA;
    }

	   public boolean aperta() {
	        return stato == StatoArticolo.OFFERTA_APERTA;
	    }
	
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("%d: %s (%s)\n", codice_prodotto, categoria.getNome(), stato));
			for (Campo campo : campi.values()) {
				sb.append(String.format("  %s  %s\n", campo.getNome(), campo.getDescrizione()));
			}
			return sb.toString();
		}
	
}
