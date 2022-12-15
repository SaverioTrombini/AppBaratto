package domain.entities;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Orologio extends OggettoBase implements Comparable<Orologio>, Serializable {

	private final LocalTime inizio;
	private final LocalTime fine;




	public static final LocalTime ORARIO_MAX = LocalTime.of(23, 30);

	public Orologio(int ora_inizio, int minuti_inizio, int ora_fine, int minuti_fine) {
		inizio = LocalTime.of(ora_inizio, minuti_inizio);
		fine = LocalTime.of(ora_fine, minuti_fine);
	}

	public Orologio(LocalTime inizio, LocalTime fine) {
		this.inizio = inizio;
		this.fine = fine;
	}

	@Override
	public int compareTo(Orologio o) {
		if (inizio.isBefore(o.inizio)) {
			return -1;
		} else if (inizio.equals(o.inizio)) {
			return 0;
		} else {
			return 1;
		}
	}

	public boolean contains(LocalTime orario) {
		return orario.equals(inizio) || orario.equals(fine) || (orario.isAfter(inizio) && orario.isBefore(fine));
	}

	public List<String> allowedTimes() {
		ArrayList<String> times = new ArrayList<>();
		times.add(inizio.toString());
		LocalTime orariConcessi = inizio;
		while (orariConcessi.isBefore(fine)) {
			orariConcessi = orariConcessi.plusMinutes(30);
			times.add(orariConcessi.toString());
		}
		return times;
	}

	public static Set<Integer> allowedMinutes() {
		Set<Integer> minsAllowed = new HashSet<>();
		minsAllowed.add(0);
		minsAllowed.add(30);
		return minsAllowed;
	}

	public String stampaOrariConcessi() {
		return allowedTimes().toString();
	}

	public LocalTime getInizio() {
		return inizio;
	}

	public LocalTime getFine() {
		return fine;
	}
}
