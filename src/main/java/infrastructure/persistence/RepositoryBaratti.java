package infrastructure.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.entities.Baratto;

public class RepositoryBaratti extends AbstractRepository<List<Baratto>> {

	public RepositoryBaratti(String filename) throws ClassNotFoundException, IOException {
		super(filename);
	}

	@Override
	protected List<Baratto> build() {
		return new ArrayList<>();
	}

	public List<Baratto> getBaratti() {
		return getT();
	}

}
