package application.views.renders;

import domain.entities.OggettoBase;

public class RendererDefault implements RendererBarattoResource{

	@Override
	public String render(OggettoBase risorsa) {
		return risorsa.toString();
	}

}
