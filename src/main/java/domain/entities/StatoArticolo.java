package domain.entities;

import java.io.Serializable;

public enum StatoArticolo implements Serializable {
	
	 OFFERTA_APERTA("Aperta"),OFFERTA_RITIRATA("Ritirata"),
	 OFFERTA_ACCOPPIATA("Accoppiata"),OFFERTA_SELEZIONATA("Selezionata"),
	 OFFERTA_IN_SCAMBIO("In scambio"),OFFERTA_CHIUSA("Chiusa");
	private String stato;

	StatoArticolo(String stato) {
		this.stato = stato;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
}
