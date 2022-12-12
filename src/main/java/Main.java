import java.io.FileInputStream;
import java.io.IOException;

import application.controllersMVC.Controller;
import infrastructure.persistence.Database;
import infrastructure.persistence.IDatabase;

public class Main {

	public static void main(String[] args) {
	
		try {
			System.getProperties().load(new FileInputStream("./src/main/resources/properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		IDatabase salvataggio = new Database();
		Controller controller = new Controller(salvataggio);
		controller.execute();
		salvataggio.save();
	}

}
