package infrastructure.persistence;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import domain.entities.Articolo;

public class RepositoryArticoli extends AbstractRepository<Map<Integer,Articolo>>{

	public RepositoryArticoli(String filename) throws ClassNotFoundException, IOException {
		super(filename);
	}

	
	protected Map<Integer, Articolo> build() {
		return new HashMap<>();
	}

	protected Map<Integer, Articolo> getArticoli() {
		return getT();
	}
}
