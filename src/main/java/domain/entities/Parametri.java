package domain.entities;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public class Parametri implements Serializable {

	private String piazza;
	private Set<String> luoghi;
	private Set<DayOfWeek> giorni;
	private Set<Orologio> orario;
	private int scadenza;

	public Parametri(String piazza, Set<String> luoghi, Set<DayOfWeek> giorni, Set<Orologio> orario, int scadenza) {
		this.piazza = piazza;
		this.luoghi = luoghi;
		this.giorni = giorni;
		this.orario = orario;
		this.scadenza = scadenza;
	}

	public Parametri(Set<String> luoghi, Set<DayOfWeek> giorni, Set<Orologio> orario) {
		this(null, luoghi, giorni, orario, 100);
	}

	public void addLuogo(String luogo) {
		luoghi.add(luogo);
	}

	public void addDay(DayOfWeek giorno) {
		giorni.add(giorno);
	}

	public void addTimeInterval(LocalTime inizio, LocalTime fine) {
		orario.add(new Orologio(inizio, fine));
	}

	public boolean contains(int ora, int minuti) {
		for (Orologio timeInterval : orario) {
			if (timeInterval.contains(LocalTime.of(ora, minuti))) {
				return true;
			}
		}
		return false;
	}

	public boolean isMaxTime(int ora, int minuti) {
		return Orologio.ORARIO_MAX.equals(LocalTime.of(ora, minuti));
	}

	public String toString() {
		return "Parametri:" + "\npiazza=" + piazza + "\nluoghi=" + luoghi + "\ngiorni=" + giorni + "\nintervalli orari="
				+ orario + "\nscadenza=" + scadenza;
	}

	public String getPiazza() {
		return piazza;
	}

	public void setPiazza(String piazza) {
		this.piazza = piazza;
	}

	public Set<String> getLuoghi() {
		return luoghi;
	}

	public void setLuoghi(Set<String> luoghi) {
		this.luoghi = luoghi;
	}

	public Set<DayOfWeek> getGiorni() {
		return giorni;
	}

	public void setGiorni(Set<DayOfWeek> giorni) {
		this.giorni = giorni;
	}

	public Set<Orologio> getOrario() {
		return orario;
	}

	public void setOrario(Set<Orologio> orario) {
		this.orario = orario;
	}

	public int getScadenza() {
		return scadenza;
	}

	public void setScadenza(int scadenza) {
		this.scadenza = scadenza;
	}

	public LocalTime getOrarioMax(LocalTime inizio) {
		for (Orologio timeInterval : orario) {
			if (timeInterval.getInizio().isAfter(inizio)) {
				return timeInterval.getInizio().minusMinutes(30);
			}
		}
		return Orologio.ORARIO_MAX;
	}

	public void setParametri(Parametri nParametri) {
		setPiazza(nParametri.getPiazza());
		setLuoghi(nParametri.getLuoghi());
		setGiorni(nParametri.getGiorni());
		setOrario(nParametri.getOrario());
		setScadenza(nParametri.getScadenza());
	}

}
