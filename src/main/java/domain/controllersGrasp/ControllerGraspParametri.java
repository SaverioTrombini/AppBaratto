package domain.controllersGrasp;

import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

import com.google.gson.JsonParseException;

import domain.entities.Orologio;
import domain.entities.Parametri;
import infrastructure.batchImport.JsonParser;
import infrastructure.persistence.IDatabase;

public class ControllerGraspParametri {

	private final Parametri parametri;

	public ControllerGraspParametri(IDatabase salvataggi) {
		this.parametri = salvataggi.getParametri();
	}

	public void addDay(DayOfWeek giorno) {
		parametri.addDay(giorno);
	}

	public void addLuogo(String luogo) {
		parametri.addLuogo(luogo);
	}

	public void addHourlyInterval(LocalTime inizio, LocalTime fine) {
		parametri.addTimeInterval(inizio, fine);
	}

	public void importFromBatch(String filePath) throws FileNotFoundException, JsonParseException {
		JsonParser jsonParser = new JsonParser();
		Parametri p = jsonParser.importParametri(filePath);
		parametri.setParametri(p);
	}
	
	public boolean exists(String luogo) {
		return parametri.getLuoghi().contains(luogo);
	}

	public boolean exists(DayOfWeek giorno) {
		return parametri.getGiorni().contains(giorno);
	}

	public boolean isValid(int inizio, int fine) {
		return !parametri.contains(inizio, fine) || parametri.isMaxTime(inizio, fine);
	}

	public boolean isValid(DayOfWeek dayOfWeek) {
		return getGiorni().contains(dayOfWeek);
	}

	public boolean isValid(LocalTime orario) {
		for (Orologio intervallo : getOrario()) {
			if (intervallo.contains(orario))
				return true;
		}
		return false;
	}

	public Set<Integer> allowedMinutes() {
		return Orologio.allowedMinutes();
	}

	public LocalTime getStopLimitFor(LocalTime inizio) {
		return parametri.getOrarioMax(inizio);
	}

	public int getScadenza() {
		return parametri.getScadenza();
	}

	public void setScadenza(int scadenza) {
		parametri.setScadenza(scadenza);
	}

	public Set<DayOfWeek> getGiorni() {
		return parametri.getGiorni();
	}

	public Parametri getParametri() {
		return parametri;
	}

	public void setPiazza(String piazza) {
		parametri.setPiazza(piazza);
	}

	public Set<String> getLuoghi() {
		return parametri.getLuoghi();
	}

	public Set<Orologio> getOrario() {
		return parametri.getOrario();
	}

	public boolean existsDefaultValues() {
		return parametri.getPiazza() != null;
	}

}
