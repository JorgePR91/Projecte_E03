package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DificultatControllerJDLV implements Initializable {
	@FXML
	private BorderPane root_dificultad;

	private String tamany;

	public BorderPane getRoot_dificultad() {
		return root_dificultad;
	}

	public void setRoot_dificultad(BorderPane root_dificultad) {
		this.root_dificultad = root_dificultad;
	}

	public String getDificultat() {
		return tamany;
	}

	public void setDificultat(String tamany) {
		this.tamany = tamany;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> { // espera que l'inicialització siga completa.
			Window window = (Stage) root_dificultad.getScene().getWindow();
		});
	}
	
	@FXML
	public void tamany(ActionEvent e) throws IOException {

		Button b = (Button) e.getSource();
		tamany = b.getText();

		DadesSingleton dada = DadesSingleton.getInstancia();

		switch (tamany) {
		case "Xicotet" -> {
			dada.setTamanyCompartit(20);
			dada.setCadenaCompartida(tamany);
		}
		case "Mitjà" -> {
			dada.setTamanyCompartit(40);
			dada.setCadenaCompartida(tamany);
		}
		case "Gran" -> {
			dada.setTamanyCompartit(60);
			dada.setCadenaCompartida(tamany);
		}
		default-> {
			dada.setTamanyCompartit(40);
			dada.setCadenaCompartida("Normal");
		}}

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaJocDeLaVida.fxml"));

			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			// PescaminesController controller = new PescaminesController();

			// loader.setController(controller);
			Parent root = loader.load();
			Scene escena2 = new Scene(root);

			escena2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(escena2);
			window.setTitle("Joc de la Vida");
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
