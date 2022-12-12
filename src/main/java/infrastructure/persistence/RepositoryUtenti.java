package infrastructure.persistence;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import domain.entities.Utente;

public class RepositoryUtenti extends AbstractRepository<Map<String,Utente>> {

	public RepositoryUtenti(String filename) throws ClassNotFoundException, IOException {
		super(filename);
	}

	@Override
	protected Map<String, Utente> build() {
		return new HashMap<>();
	}
	
	public Map<String, Utente> getUtenti(){
		return getT();
	}
}
