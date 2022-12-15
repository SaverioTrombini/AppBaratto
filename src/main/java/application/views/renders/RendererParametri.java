package application.views.renders;

import java.util.Set;

import domain.entities.OggettoBase;
import domain.entities.Orologio;
import domain.entities.Parametri;

public class RendererParametri implements RendererBarattoResource {

	private RendererBarattoResource next;

	public RendererParametri(RendererBarattoResource next) {
		this.next=next;
	}
	
	@Override
	public String render(OggettoBase risorsa) {
		if (risorsa instanceof Parametri) {
			Parametri r = (Parametri) risorsa;
			return String.format("Parametri: \npiazza= %s\nluoghi= %s\ngiorni= %s\nintervalli orari= %s\nscadenza= %s",
					r.getPiazza(), r.getLuoghi(), r.getGiorni(), estraiOrari(r.getOrario()), r.getScadenza());
		}
		return next.render(risorsa);
	}

	private Object estraiOrari(Set<Orologio> orario) {
		StringBuilder built = new StringBuilder();
		for(Orologio o : orario) {
			built.append(String.format("dalle %s alle %s",o.getInizio(),o.getFine()));
		}
		return built;
	}
}
