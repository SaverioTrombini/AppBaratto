package infrastructure.batchImport;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.DayOfWeek;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import domain.entities.Categoria;
import domain.entities.Orologio;
import domain.entities.Parametri;

public class JsonParser {

	private final Gson gson;

	public JsonParser() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Categoria.class, new ImportCategoria());
		builder.registerTypeAdapter(DayOfWeek.class, new ImportDayOfWeek());
		builder.registerTypeAdapter(Orologio.class, new ImportOrologio());

		gson = builder.create();
	}

	/**
	 * Importa la gerarchia da ./categoria.json
	 * 
	 * @param filePath percorso assoluto file
	 * @return gerarchia letta da ./categoria.json
	 */
	public Categoria[] importCategoria(String filePath) throws FileNotFoundException, JsonParseException {
		return gson.fromJson(new JsonReader(new FileReader(filePath)), Categoria[].class);
	}

	/**
	 * Importa la configurazione da ./parametri.json
	 * 
	 * @param filePath percorso assoluto file
	 * @return La configurazione letta dal file
	 */
	public Parametri importParametri(String filePath) throws FileNotFoundException, JsonParseException {
		return gson.fromJson(new JsonReader(new FileReader(filePath)), Parametri.class);
	}

}
