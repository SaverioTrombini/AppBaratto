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

		private final IDatabase salvataggi;

	public ControllerGraspParametri(IDatabase salvataggi) {
		this.salvataggi = salvataggi;
	}


	public void addDay(DayOfWeek giorno) {
		salvataggi.getParametri().addDay(giorno);
	}

	public void addLuogo(String luogo) {
		salvataggi.getParametri().addLuogo(luogo);
	}

	public void addHourlyInterval(LocalTime inizio, LocalTime fine) {
		salvataggi.getParametri().addTimeInterval(inizio, fine);
	}	

	public boolean exists(String luogo) {
		return salvataggi.getParametri().getLuoghi().contains(luogo);
	}

	public boolean exists(DayOfWeek giorno) {
		return salvataggi.getParametri().getGiorni().contains(giorno);
	}	


	public boolean isValid(int inizio, int fine) {
		return !salvataggi.getParametri().contains(inizio, fine)
				|| salvataggi.getParametri().isMaxTime(inizio, fine);
	}
	
	public boolean isValid(DayOfWeek dayOfWeek) {
        return getGiorni().contains(dayOfWeek);
    }
	
	 public boolean isValid(LocalTime orario) {
	        for (Orologio intervallo : getOrario()) {
	            if(intervallo.contains(orario))
	                return true;
	        }
	        return false;
	    }

	public Set<Integer> allowedMinutes() {
		return Orologio.allowedMinutes();
	}
	

	
	// Setters&getters
	public LocalTime getStopLimitFor(LocalTime inizio) {
		return salvataggi.getParametri().getOrarioMax(inizio);
	}

	public int getScadenza() {
		return salvataggi.getParametri().getScadenza();
	}
	public void setScadenza(int scadenza) {
		salvataggi.getParametri().setScadenza(scadenza);
	}

	public Set<DayOfWeek> getGiorni() {
		return salvataggi.getParametri().getGiorni();
	}

	public String getParametri() {
		return salvataggi.getParametri().toString();
	}

	public void setPiazza(String piazza) {
		salvataggi.getParametri().setPiazza(piazza);
	}

	public Set<String> getLuoghi(){
        return salvataggi.getParametri().getLuoghi();
    }
	
	public Set<Orologio> getOrario(){
        return salvataggi.getParametri().getOrario();    
    }

	public boolean existsDefaultValues() {
		return salvataggi.getPiazza()!=null;
	}

	public void importFromBatch(String filePath) 
			throws FileNotFoundException, JsonParseException {
        JsonParser jsonParser = new JsonParser();
        Parametri parametri = jsonParser.importParametri(filePath);
        salvataggi.setParametri(parametri);
    }
	

}
