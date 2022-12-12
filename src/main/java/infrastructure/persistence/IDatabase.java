package infrastructure.persistence;

import java.util.List;
import java.util.Map;

import domain.entities.Articolo;
import domain.entities.Baratto;
import domain.entities.Categoria;
import domain.entities.Parametri;
import domain.entities.Utente;

public interface IDatabase {

	void save();
	
	Map<String,Utente> getUtenti();

	Map<String,Categoria> getGerarchia();

	Map<Integer, Articolo> getArticoli();

	List<Baratto> getBaratti();
	
	Parametri getParametri();

	String getPiazza();

	void setParametri(Parametri parametri);
}
