package infrastructure.persistence;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import domain.entities.Categoria;

public class RepositoryCategorie extends AbstractRepository <Map<String, Categoria>>{

	public RepositoryCategorie(String filename) throws ClassNotFoundException, IOException {
		super(filename);
	}

	@Override
	protected Map<String, Categoria> build() {
		return new HashMap<>();
	}

	public Map<String, Categoria> getGerarchia(){
		return getT();
	}
}
