package infrastructure.batchImport;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import domain.entities.Orologio;

public class ImportOrologio implements JsonDeserializer<Orologio> {
	/*deserializzazione dell'orologio e controllo su inserimento orari concessi*/
    @Override
    public Orologio deserialize(JsonElement jsonElement, Type t, JsonDeserializationContext jDC) 
    		throws JsonParseException {
        JsonObject o = jsonElement.getAsJsonObject();
        LocalTime inizio, fine;
        Set<Integer> allowedMinutes = Orologio.allowedMinutes();
        try {
            inizio = LocalTime.parse(o.get("inizio").getAsString());
            fine = LocalTime.parse(o.get("fine").getAsString());
            if (!allowedMinutes.contains(inizio.getMinute()) || !allowedMinutes.contains(fine.getMinute())) {
                throw new JsonParseException("valori orologio non permessi");
            }
        } catch (DateTimeParseException parseException) {
            throw new JsonParseException(parseException.getMessage());
        }
        return new Orologio(inizio, fine);
    }
    
}