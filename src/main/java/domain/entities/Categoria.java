package domain.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Categoria extends OggettoBase implements Serializable {
	private final String nome;
	private final String descrizione;
	private final Map<String, Campo> campi;
	private final Map<String, Categoria> figli;
	private Categoria padre = null;

	public Categoria(String nome, String descrizione, Map<String, Campo> campi) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.campi = new HashMap<>();
		this.campi.putAll(getDefaultFields());
		this.campi.putAll(campi);
		figli = new HashMap<>();
	}

	public static Map<String, Campo> getDefaultFields() {
		Map<String, Campo> campiObbl = new HashMap<>();
		campiObbl.put("Stato di conservazione", new Campo(true, "Stato di conservazione"));
		campiObbl.put("Descrizione libera", new Campo(false, "Descrizione libera"));
		return campiObbl;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Categoria category = (Categoria) o;
		return nome.equals(category.nome);
	}



	public boolean isLeaf() {
		return figli.size() == 0;
	}

	public void addChildCategory(Categoria figlia) {
		this.figli.put(figlia.nome, figlia);
		figlia.setPadre(this);
	}

	public Categoria getPadre() {
		return padre;
	}

	public void setPadre(Categoria padre) {
		this.padre = padre;
	}

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public Map<String, Campo> getCampi() {
		return campi;
	}

	public Map<String, Categoria> getFigli() {
		return figli;
	}

}
