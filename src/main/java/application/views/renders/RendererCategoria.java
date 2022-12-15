package application.views.renders;

import java.util.Map;

import domain.entities.Campo;
import domain.entities.Categoria;
import domain.entities.OggettoBase;

public class RendererCategoria implements RendererBarattoResource {

	private RendererBarattoResource next;

	public RendererCategoria(RendererBarattoResource next) {
		this.next=next;
	}
	
	@Override
	public String render(OggettoBase risorsa) {
		if (risorsa instanceof Categoria) {
			Categoria r = (Categoria) risorsa;
			return toShortString(r,0);
		}
		return next.render(risorsa);
	}
	
	public String toShortString(Categoria r, int initialPrefixNumber) {
		StringBuilder built = new StringBuilder();
		built.append(String.format("%s%s %s%n", prefix(initialPrefixNumber), r.getNome(), estraiCampi(r.getCampi())));
		for (Categoria figlia : r.getFigli().values()) {
			built.append(toShortString(figlia, initialPrefixNumber + 1));
		}
		return built.toString();
	}
	
	public String prefix(int n) {
		return "	".repeat(n);
	}
	
	public String estraiCampi(Map<String,Campo> campi) {
		StringBuilder built = new StringBuilder();
		for(Campo c : campi.values()) {
			built.append(String.format(" %s (%s)", c.getNome(), c.obbligatorio()));
		}
		return built.toString();
	}
}
