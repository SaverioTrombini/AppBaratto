package infrastructure.persistence;

import java.io.IOException;
import java.util.HashSet;

import domain.entities.Parametri;

public class RepositoryParametri extends AbstractRepository<Parametri> {

	public RepositoryParametri(String filename) throws ClassNotFoundException, IOException {
		super(filename);
	}

	@Override
	protected Parametri build() {
		return new Parametri(new HashSet<>(), new HashSet<>(), new HashSet<>());
	}

	public Parametri getParametri() {
		return getT();
	}

}
