package application.controllersMVC;

import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

import com.google.gson.JsonParseException;

import application.views.ViewParametri;
import domain.controllersGrasp.ControllerGraspParametri;
import infrastructure.persistence.IDatabase;

public class ControllerMVCParametri {
	private final ControllerGraspParametri controllerGraspParametri;
	private final ViewParametri viewParametri;

	public ControllerMVCParametri(IDatabase salvataggi) {
		this.controllerGraspParametri = new ControllerGraspParametri(salvataggi);
		this.viewParametri = new ViewParametri();
	}

	void execute() {
		int scelta;
		do {
			scelta = viewParametri.scelta();
			switch (scelta) {
			case 1 -> parametriToString();
			case 2 -> editParametri();
			case 3 -> importFromBatch();
			}
		} while (scelta != 0);
	}

	void parametriToString() {
		viewParametri.stampaParametri(controllerGraspParametri.getParametri());
	}

	private void editParametri() {
		if (!controllerGraspParametri.existsDefaultValues()) {
			inserisciPiazza();
			inserisciLuoghi();
			inserisciGiorni();
			inserisciIntervalliOrari();
			inserisciScadenza();
		} else {
			updateParametri();
		}
	}

	private void updateParametri() {
		if (viewParametri.richiestaModificaLuoghi())
			inserisciLuoghi();
		if (viewParametri.richiestaModificaGironi())
			inserisciGiorni();
		if (viewParametri.richiestaModificaIntervalliOrari())
			inserisciIntervalliOrari();
		if (viewParametri.richiediModificaScadenza())
			inserisciScadenza();
	}

	private void inserisciScadenza() {
		controllerGraspParametri.setScadenza(viewParametri.richiediInsermientoScadenza());
	}

	private void inserisciIntervalliOrari() {
		boolean continua;
		do {
			LocalTime orarioInizio = inserisciInizio();
			LocalTime orarioFine = inserisciFine(orarioInizio);
			controllerGraspParametri.addHourlyInterval(orarioInizio, orarioFine);
			continua = viewParametri.richiestaSeInserireIntervalliOrari();
		} while (continua);
	}

	private LocalTime inserisciInizio() {
		int oraIniziale, minutoIniziale;
		do {
			oraIniziale = viewParametri.richiestaInserimentoOraIniziale();
			minutoIniziale = viewParametri.richiediInserimentoMinutoIniziale(controllerGraspParametri.allowedMinutes());

			if (!controllerGraspParametri.isValid(oraIniziale, minutoIniziale)) {
				viewParametri.stampaOrarioNonValido();
			}
		} while (!controllerGraspParametri.isValid(oraIniziale, minutoIniziale));

		return LocalTime.of(oraIniziale, minutoIniziale);
	}

	private LocalTime inserisciFine(LocalTime orarioIniziale) {
		int oraFinale, minutoFinale;
		LocalTime stopLimitFor = controllerGraspParametri.getStopLimitFor(orarioIniziale);
		do {
			oraFinale = viewParametri.richiestaInserimentoOraFinale(orarioIniziale);
			minutoFinale = viewParametri.richiediInserimentoMinutoFinale(controllerGraspParametri.allowedMinutes());
			if (LocalTime.of(oraFinale, minutoFinale).isAfter(stopLimitFor)) {
				viewParametri.stampaOrarioNonValido(stopLimitFor);
			}
		} while (LocalTime.of(oraFinale, minutoFinale).isAfter(stopLimitFor));

		return LocalTime.of(oraFinale, minutoFinale);
	}

	private void inserisciGiorni() {
		controllerGraspParametri.getGiorni().forEach(
				dayOfWeek -> System.out.printf("%s ", dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ITALIAN)));
		boolean continua = true;
		do {
			DayOfWeek day = viewParametri.richiestaInserimentoGiorni();
			if (!controllerGraspParametri.exists(day)) {
				controllerGraspParametri.addDay(day);
				continua = viewParametri.richiestaSeInserireAltroGiorno();
			} else {
				viewParametri.stampaGiornoGiaInserito();
			}
		} while (continua);
	}

	private void inserisciLuoghi() {
		boolean continua = true;
		do {
			String luogo = viewParametri.richiestaInserimentoLuogo();
			if (!controllerGraspParametri.exists(luogo)) {
				controllerGraspParametri.addLuogo(luogo);
				continua = viewParametri.richiestaInserimentoAltroLuogo();
			} else {
				viewParametri.stampaLuogoGiaInserito();
			}
		} while (continua);

	}

	private void inserisciPiazza() {
		controllerGraspParametri.setPiazza(viewParametri.richiestaInserimentoPiazza());
	}

	private void importFromBatch() {
		if (controllerGraspParametri.existsDefaultValues()) {
			viewParametri.stampaPiazzaGiaInserita();
		}
		try {
			String filePath = viewParametri.richiestaInserimentoPercorsoAssoluto();
			controllerGraspParametri.importFromBatch(filePath);
			viewParametri.stampaCaricamentoParametriRiuscito();
		} catch (FileNotFoundException | JsonParseException e) {
			viewParametri.stampaErroriCaricamentoFile(e);
		}
	}

}
