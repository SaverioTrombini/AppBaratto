package application.controllersMVC;

import java.io.FileNotFoundException;

import com.google.gson.JsonParseException;

import application.views.ViewCategoria;
import application.views.renders.RendererBarattoResource;
import domain.controllersGrasp.ControllerGraspArticoli;
import domain.controllersGrasp.ControllerGraspCategoria;
import infrastructure.persistence.IDatabase;

public class ControllerMVCCategoria {

	private final ControllerGraspCategoria controllerGraspCategoria;
	private final ControllerGraspArticoli controllerGraspArticoli;
	private final ViewCategoria viewCategoria;

	public ControllerMVCCategoria(IDatabase salvataggi, RendererBarattoResource catena) {
		this.controllerGraspCategoria = new ControllerGraspCategoria(salvataggi);
		this.controllerGraspArticoli = new ControllerGraspArticoli(salvataggi);
		this.viewCategoria = new ViewCategoria(catena);
	}

	void execute() {
		int scelta;
		do {
			scelta = viewCategoria.scegli();
			switch (scelta) {
			case 1 -> insertRoot();
			case 2 -> insertChild();
			case 3 -> stampaGerarchia();
			case 4 -> importFromBatch();
			}
		} while (scelta != 0);
	}

	private void insertRoot() {
		String nome, descrizione;
		do {
			nome = viewCategoria.richiestaInserimentoNomeRadice();
			if (controllerGraspCategoria.existsRoot(nome)) {
				viewCategoria.stampaNomeGiaPresente();
			}
		} while (controllerGraspCategoria.existsRoot(nome));
		descrizione = viewCategoria.richiestaInserimentoDescrizione();
		askFields();
		controllerGraspCategoria.makeRootCategory(nome, descrizione, controllerGraspCategoria.getCampi());
	}

	private void insertChild() {
		stampaGerarchia();
		String radice = inputRoot();
		String padre = inputParent(radice);
		String nome = inputCategoriaNonPresente(radice);
		String descrizione = viewCategoria.richiestaInserimentoDescrizione();
		controllerGraspCategoria.addParentFields(radice, padre);
		askFields();
		controllerGraspCategoria.makeChildCategory(radice, padre, nome, descrizione,
				controllerGraspCategoria.getCampi());
	}

	String inputRoot() {
		String nome_radice;
		do {
			nome_radice = viewCategoria.richiestaInserimentoNomeRadice();
			if (!controllerGraspCategoria.existsRoot(nome_radice)) {
				viewCategoria.stampaDatoNonPresente(nome_radice);
			}
		} while (!controllerGraspCategoria.existsRoot(nome_radice));
		return nome_radice;
	}

	private String inputParent(String radice) {
		String padre;
		do {
			padre = viewCategoria.richiestaInserimentoNomePadreCategoria();
			if (!controllerGraspCategoria.exists(radice, padre)) {
				viewCategoria.stampaDatoNonPresente(padre);
			}
		} while (!controllerGraspCategoria.exists(radice, padre));
		return padre;
	}

	private String inputCategoriaNonPresente(String radice) {
		String nome;
		do {
			nome = viewCategoria.richiestaInserimentoNomeCategoria();
			if (controllerGraspCategoria.exists(radice, nome)) {
				viewCategoria.stampaDatoGiaPresente(nome);
			}
		} while (controllerGraspCategoria.exists(radice, nome));
		return nome;
	}

	private void askFields() {
		boolean scelta;
		do {
			scelta = viewCategoria.richiestaSeInserireNuovoCampo();
			if (scelta) {
				String nuovo = viewCategoria.richiestaInserimentoNomeCampo();
				boolean required = viewCategoria.richiestaSeCampoObbligatorio();
				controllerGraspCategoria.addCampo(required, nuovo);
			}
		} while (scelta);
	}

	private void importFromBatch() {
		try {
			if (controllerGraspArticoli.getArticoli().isEmpty()) {
				controllerGraspCategoria.importFromBatch(viewCategoria.richiestaInserimentoPercorsoAssoluto());
				viewCategoria.stampaCaricamentoCategorieRiuscito();
			} else {
				viewCategoria.stampaArticoliGiaPresenti();
			}
		} catch (FileNotFoundException | JsonParseException e) {
			viewCategoria.stampaErroriCaricamentoFile(e);
		}
	}

	void stampaGerarchia() {
		viewCategoria.stampaSpiegazioneVisualizzazioneGerarchia();
		viewCategoria.stampaGerarchia(controllerGraspCategoria.getGerarchia());
	}

	String inputCategoriaPresente(String radice) {
		String nome;
		do {
			nome = viewCategoria.richiestaInserimentoNomeCategoria();
			if (!controllerGraspCategoria.exists(radice, nome)) {
				viewCategoria.stampaDatoNonPresente(nome);
			}
		} while (!controllerGraspCategoria.exists(radice, nome));

		return nome;
	}
}
