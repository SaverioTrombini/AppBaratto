package application.views.renders;

import domain.entities.Articolo;
import domain.entities.Campo;
import domain.entities.OggettoBase;


public class RendererArticolo implements RendererBarattoResource {

	private RendererBarattoResource next;

	public RendererArticolo(RendererBarattoResource next) {
		this.next=next;
	}
	
	@Override
	public String render(OggettoBase risorsa) {
		if (risorsa instanceof Articolo) {
			Articolo a = (Articolo) risorsa;
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("%d: %s (%s)\n", a.getCodice_prodotto(), a.getCategoria().getNome(), a.getStato()));
			for (Campo campo : a.getCampi().values()) {
				sb.append(String.format("  %s  %s\n", campo.getNome(), campo.getDescrizione()));
			}
			return sb.toString();
		}
		return next.render(risorsa);
	}
	

}
