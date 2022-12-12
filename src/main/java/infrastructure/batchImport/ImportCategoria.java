package infrastructure.batchImport;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import domain.entities.Campo;
import domain.entities.Categoria;

public class ImportCategoria implements JsonDeserializer<Categoria> {

	/*deserializzazione delle categorie da json*/
    @Override
    public Categoria deserialize(JsonElement jsonElement, Type t, JsonDeserializationContext jsonDC) 
    		throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        
        Categoria root = new Categoria(object.get("nome").getAsString(),object.get("descrizione").getAsString(),
                getCampi(object.getAsJsonArray("campi")));
        addFigli(root, object.getAsJsonArray("figli"));
        return root;
    }

    /**
     * Deserializza i campi
     * @param array di campi nel file json
     * @return Mappa dei campi
     */
    private Map<String, Campo> getCampi(JsonArray campi) {
        Map<String, Campo> campiImportati = new HashMap<>();
        campi.forEach(campo -> {
            JsonObject c = campo.getAsJsonObject();
            String nome = c.get("nome").getAsString();
            campiImportati.put(nome, new Campo(c.get("obbligatorio").getAsBoolean(), nome));
        });
        return campiImportati;
    }
    
    /**
     * Deserializza le categorie
     * La categoria padre serve per associare 
     * l'array di figli nel file json alla categoria corretta
     */
    private void addFigli(Categoria padre, JsonArray figli) {
        figli.forEach(categoria -> {
        	Categoria figlia = new Categoria(categoria.getAsJsonObject().get("nome").getAsString(),
                    categoria.getAsJsonObject().get("descrizione").getAsString(),
                    getCampi(categoria.getAsJsonObject().getAsJsonArray("campi")));
            padre.addChildCategory(figlia);
            addFigli(figlia, categoria.getAsJsonObject().getAsJsonArray("figli"));
        });
    }
}
