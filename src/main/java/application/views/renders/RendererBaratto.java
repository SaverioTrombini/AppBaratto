package application.views.renders;


import domain.entities.Articolo;
import domain.entities.Baratto;
import domain.entities.Campo;
import domain.entities.OggettoBase;

public class RendererBaratto implements RendererBarattoResource {

	private RendererBarattoResource next;

	public RendererBaratto(RendererBarattoResource next) {
		this.next=next;
	}
	
	@Override
	public String render(OggettoBase risorsa) {
		if (risorsa instanceof Baratto) {
			Baratto b = (Baratto) risorsa;
			return String.format("%s ha proposto il seguente baratto:\n%sin cambio di\n%s-----",b.getProposta().getUsername(),
					 estraiArticolo(b.getRichiesta()), estraiArticolo(b.getProposta()));
		}
		return next.render(risorsa);
	}
	
	public String estraiArticolo(Articolo a){
		StringBuilder sb = new StringBuilder();
		sb.append( String.format("  %d: %s (%s)\n", a.getCodice_prodotto(), a.getCategoria().getNome(), a.getStato()));
		for (Campo campo : a.getCampi().values()) {
			sb.append(String.format("  %s  %s\n", campo.getNome(), campo.getDescrizione()));
		}
		return sb.toString();
	}

}
