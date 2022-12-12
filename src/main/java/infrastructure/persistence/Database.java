package infrastructure.persistence;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import domain.entities.Articolo;
import domain.entities.Baratto;
import domain.entities.Categoria;
import domain.entities.Parametri;
import domain.entities.Utente;

public class Database implements IDatabase {

	private RepositoryUtenti repositoryUtenti;
	private RepositoryCategorie repositoryCategorie;
	private RepositoryParametri repositoryParametri;
	private RepositoryArticoli repositoryArticoli;
	private RepositoryBaratti repositoryBaratti;

	public Database() {
		try {
			repositoryUtenti = new RepositoryUtenti(System.getProperty(RepositoryUtenti.class.getName()));
			repositoryCategorie = new RepositoryCategorie(System.getProperty(RepositoryCategorie.class.getName()));
			repositoryParametri = new RepositoryParametri(System.getProperty(RepositoryParametri.class.getName()));
			repositoryArticoli = new RepositoryArticoli(System.getProperty(RepositoryArticoli.class.getName()));
			repositoryBaratti = new RepositoryBaratti(System.getProperty(RepositoryBaratti.class.getName()));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save() {
		repositoryUtenti.save();
		repositoryCategorie.save();
		repositoryParametri.save();
		repositoryArticoli.save();
		repositoryBaratti.save();
	}

	@Override
	public Map<String, Utente> getUtenti() {
		return repositoryUtenti.getUtenti();
	}

	@Override
	public Map<String, Categoria> getGerarchia() {
		return repositoryCategorie.getGerarchia();
	}

	@Override
	public Parametri getParametri() {
		return repositoryParametri.getParametri();
	}

	@Override
	public String getPiazza() {
		return repositoryParametri.getParametri().getPiazza();
	}

	@Override
	public Map<Integer, Articolo> getArticoli() {
		return repositoryArticoli.getArticoli();
	}

	@Override
	public List<Baratto> getBaratti() {
		return repositoryBaratti.getBaratti();
	}

}
