package application;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PADificultatController implements Initializable {
	@FXML
	private VBox root_dificultad;

	private String tamany;

	public VBox getRoot_dificultad() {
		return root_dificultad;
	}

	public void setRoot_dificultad(VBox root_dificultad) {
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaPixelArt.fxml"));

			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			// PescaminesController controller = new PescaminesController();

			// loader.setController(controller);
			Parent root = loader.load();
			Scene escena2 = new Scene(root);

			escena2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(escena2);
			window.setTitle("PixelArt");
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	public void carregarPartida(ActionEvent e) throws IOException {
		System.out.println(e.toString() + "  DESERIALIZAR");
		// Primer: crear una File amb la direcció que ens han donat
		// Crear un nou tauler amb el context deserialitzar
		// tot comprovant si no és null
		// associar el nou tauler al carregat?
		// associar el Gp a les casselles del nou?
		// deixar la pantalla d'inici no vista i el ratolí transparent
		// encetar el temps a play

		// https://acodigo.blogspot.com/2014/12/file-chooser-javafx-abrir-archivos.html

		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("./Llenços"));
		fileChooser.setTitle("Buscar partida guardada");

		// Agregar filtros para facilitar la busqueda
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Tots els arxius", "*.*"),
				new FileChooser.ExtensionFilter("DAT", "*.dat"));

		// Obtener la imagen seleccionada
		File f = fileChooser.showOpenDialog(root_dificultad.getScene().getWindow());

		// Mostar la imagen
		if (f != null) {
			File partida = new File(f.getAbsolutePath());
// Crear un tauler nou amb el context deserialitzar, fent referència a l'arxiu seleccionat amb el nom .getName()
//Si no és null es fa una funció inicialització de components amb el tauler(sols el temps amb setTemps new Timeline()), es carrega el nou tauler en this.nouTauler(ell ho ha fet tot en un però crec que puc fer-ho amb el traspàs de dades Singleton), s'apliquen les caselles i es fa la GP, i s0inicialitza el temps.play

			DadesSingleton dada = DadesSingleton.getInstancia();
			dada.setPartidaCompartida(partida);

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaPixelArt.fxml"));

				Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
				// PescaminesController controller = new PescaminesController();

				// loader.setController(controller);
				Parent root = loader.load();
				Scene escena2 = new Scene(root);

				escena2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				window.setScene(escena2);
				window.setTitle("Pixel Art");
				window.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

}
