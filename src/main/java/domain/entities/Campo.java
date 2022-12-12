package domain.entities;

import java.io.Serializable;

public class Campo implements Serializable {
    private final boolean obbligatorio;
    private final String nome;
    private String descrizione;
    
    public Campo(boolean obbligatorio, String nome, String descrizione) {
        this.obbligatorio = obbligatorio;
        this.nome = nome;
        this.descrizione = descrizione;
    }
    
    public Campo(boolean obbligatorio, String nome) {
        this(obbligatorio, nome, null);
    }

    public boolean obbligatorio() {
		return obbligatorio;
	}
    
    public String toString() {
        return String.format(" %s", obbligatorio);
    }
    //getters&setters
	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}
    
	public void setDescrizione(String descrizione) {
		this.descrizione=descrizione;
	}
}