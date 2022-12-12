package domain.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;



public class Categoria  implements Serializable {
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

	public String prefix(int n) {
		return "	".repeat(n);
	}
	public String toShortString(int initialPrefixNumber) {
		StringBuilder built = new StringBuilder();
		built.append(String.format("%s%s %s%n", prefix(initialPrefixNumber), nome, campi));
		for (Categoria figlia : figli.values()) {
			built.append(figlia.toShortString(initialPrefixNumber + 1));
		}
		return built.toString();
	}

	public boolean isLeaf() {
		return figli.size() == 0;
	}

	public void addChildCategory(Categoria figlia) {
		this.figli.put(figlia.nome, figlia);
		figlia.setPadre(this);
	}
	
	}
	
	
	

