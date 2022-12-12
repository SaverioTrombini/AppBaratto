package domain.controllersGrasp;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonParseException;

import domain.entities.Campo;
import domain.entities.Categoria;
import infrastructure.batchImport.JsonParser;
import infrastructure.persistence.IDatabase;

public class ControllerGraspCategoria {
	private Map<String, Campo> campi;
	private final Map<String, Categoria> gerarchia;

	public ControllerGraspCategoria(IDatabase salvataggi) {
		this.gerarchia = salvataggi.getGerarchia();
		this.campi = new HashMap<String, Campo>();
	}

	public boolean existsRoot(String nome) {
		return gerarchia.get(nome) != null;
	}

	public void makeRootCategory(String nome, String descrizione, Map<String, Campo> newFields) {
		Categoria radice = new Categoria(nome, descrizione, campi);
		gerarchia.put(nome, radice);
	}

	public Map<String, Campo> getCampi() {
		return campi;
	}

	public void addCampo(boolean b, String nome) {
		Campo c = new Campo(b, nome);
		campi.put(c.getNome(), c);
	}

	public Map<String, Categoria> getGerarchia() {
		return gerarchia;
	}

	public String toShortString() {
		StringBuilder builder = new StringBuilder();
		for (Categoria c : gerarchia.values()) {
			builder.append(c.toShortString(0));
		}
		return builder.toString();
	}

	public boolean exists(String radice, String padre) {
		return searchByName(radice, padre) != null;
	}

	Categoria searchByName(String radice, String padre) {
		Categoria cRadice = gerarchia.get(radice);
		if (radice.equals(padre)) {
			return cRadice;
		}
		return searchByCategory(cRadice, padre);
	}

	private Categoria searchByCategory(Categoria cRadice, String padre) {
		for (Map.Entry<String, Categoria> figlia : cRadice.getFigli().entrySet()) {
			if (figlia.getValue().getNome().equalsIgnoreCase(padre)) {
				return figlia.getValue();
			} else if (!figlia.getValue().isLeaf()) {
				if (searchByCategory(figlia.getValue(), padre) != null) {
					return searchByCategory(figlia.getValue(), padre);
				}
			}
		}
		return null;
	}

	public void addParentFields(String radice, String padre) {
		Categoria c = searchByName(radice, padre);
		campi.putAll(c.getCampi());
	}

	public void makeChildCategory(String radice, String padre, String figlia, String descrizione,
			Map<String, Campo> campi) {

		Categoria cPadre = searchByName(radice, padre);
		cPadre.addChildCategory(new Categoria(figlia, descrizione, campi));
	}

	public Map<String, Campo> getCampi(String radice, String nome) {
		return searchByName(radice, nome).getCampi();
	}

	public void importFromBatch(String filePath) throws FileNotFoundException, JsonParseException {
		JsonParser jsonParser = new JsonParser();
		Arrays.stream(jsonParser.importCategoria(filePath))
				.forEach(categoria -> gerarchia.put(categoria.getNome(), categoria));
	}
}
