package appEquip03;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EscenaControllerJocs implements Initializable {

	private String nomUsuari;
	private static Stage finestraWordle = null;

	public String getNomUsuari() {
		return nomUsuari;
	}

	@FXML
	private Label nomUsuariLabel;
	@FXML
	private Button logoutBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DadesSingleton dada = DadesSingleton.getInstancia();
		setNomUsuari(dada.getUsuari());
		nomUsuariLabel.setText("Usuari: " + nomUsuari);
	}

	private void obrirEscena(String fxml, String titol) {

		try {
			// Evita obrir una altra finestra si Wordle ja està obert
			if (fxml.equals("EscenaWordle.fxml") && finestraWordle != null && finestraWordle.isShowing()) {
				finestraWordle.toFront(); // Porta al davant
				return;
			}

			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			Parent root = loader.load();

			Object controller = loader.getController();
			/*
			 * try { controller.getClass() .getMethod("setNomUsuari", String.class)
			 * .invoke(controller, nomUsuari); } catch (Exception ignored) {}
			 */

			Scene escena = new Scene(root, 700, 600); // <- Mida fixa aquí
			escena.getStylesheets().add(getClass().getResource("applicationWordle.css").toExternalForm());

			Stage novaFinestra = new Stage();
			novaFinestra.setTitle(titol);
			novaFinestra.setScene(escena);

			// Guarda la finestra si és Wordle
			if (fxml.equals("EscenaWordle.fxml")) {
				finestraWordle = novaFinestra;
				novaFinestra.setOnCloseRequest(e -> finestraWordle = null); // Allibera la referència en tancar
			}

			novaFinestra.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void anarWordle() {
		obrirEscena("EscenaWordle.fxml", "Wordle");
	}

	@FXML
	private void anarPescamines() {
		obrirEscena("EscenaDificultatPescamines.fxml", "Pescamines");
	}

	@FXML
	private void anarJocVida() {
		obrirEscena("EscenaDificultatJocVida.fxml", "Joc de la Vida");
	}

	@FXML
	private void anarPixelArt() {
		obrirEscena("EscenaDificultatPixelArt.fxml", "Pixel Art");
	}

	public void setNomUsuari(String nomUsuari) {
		this.nomUsuari = nomUsuari;
		if (nomUsuariLabel != null) {
			nomUsuariLabel.setText("Usuari: " + nomUsuari);
		}
	}

	@FXML
	private void tornarInici() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaInici.fxml"));
			Parent root = loader.load();
			Scene novaEscena = new Scene(root, 700, 600); // <- També aquí!
			novaEscena.getStylesheets().add(getClass().getResource("applicationWordle.css").toExternalForm());

			Main.canviarEscena(novaEscena);

			// No cal tancar l'escena actual si estàs reutilitzant el stage principal

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
