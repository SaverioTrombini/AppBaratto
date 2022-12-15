import java.io.FileInputStream;
import java.io.IOException;

import application.controllersMVC.Controller;
import application.views.renders.RendererArticolo;
import application.views.renders.RendererBaratto;
import application.views.renders.RendererBarattoResource;
import application.views.renders.RendererCategoria;
import application.views.renders.RendererDefault;
import application.views.renders.RendererParametri;
import infrastructure.persistence.Database;
import infrastructure.persistence.IDatabase;

public class Main {

	public static void main(String[] args) {
	
		try {
			System.getProperties().load(new FileInputStream("./src/main/resources/properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		RendererBarattoResource catena= new RendererBaratto(new RendererArticolo(new RendererCategoria
				(new RendererParametri(new RendererDefault()))));
		IDatabase salvataggio = new Database();
		Controller controller = new Controller(salvataggio,catena);
		controller.execute();
		salvataggio.save();
	}

}
