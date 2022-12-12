package infrastructure.persistence;

import java.io.IOException;
import java.util.HashSet;

import domain.entities.Parametri;

public class RepositoryParametri extends AbstractRepository<Parametri> {
	private final Parametri parametri;
	public RepositoryParametri(String filename) throws ClassNotFoundException, IOException {
		super(filename);
		parametri = getT();
	}

	@Override
	protected Parametri build() {
		return new Parametri(new HashSet<>(), new HashSet<>(), new HashSet<>());
	}

	public Parametri getParametri() {
		return parametri;
	}

	public void setParametri(Parametri nParametri) {
       	parametri.setPiazza(nParametri.getPiazza());
        parametri.setLuoghi(nParametri.getLuoghi());
        parametri.setGiorni(nParametri.getGiorni());
        parametri.setOrario(nParametri.getOrario());
        parametri.setScadenza(nParametri.getScadenza());
    }

}
