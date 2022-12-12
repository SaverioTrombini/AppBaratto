package infrastructure.batchImport;

import java.lang.reflect.Type;
import java.time.DayOfWeek;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ImportDayOfWeek  implements JsonDeserializer<DayOfWeek> {
	
	/*deserializzazione dei giorni della settimana e conversione in int*/
    @Override
    public DayOfWeek deserialize(JsonElement jsonElement, Type t, JsonDeserializationContext jDC) 
    		throws JsonParseException {
        return DayOfWeek.of(jsonElement.getAsInt());
    }
}