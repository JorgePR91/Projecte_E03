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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DificultadController implements Initializable {
	@FXML
	private VBox root_dificultad;

	private String dificultat;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> { // espera que l'inicialització siga completa.
			Window window = (Stage) root_dificultad.getScene().getWindow();

		});
	}

	public void dificultad(ActionEvent e) throws IOException {

		Button b = (Button) e.getSource();
		dificultat = b.getText();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaPescamines.fxml"));

			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			PescaminesController controller = new PescaminesController();

			loader.setController(controller);
			Parent root = loader.load();
			Scene escena2 = new Scene(root);

			escena2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(escena2);
			window.setTitle("Pescamines");
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	public void carregarPartida(ActionEvent e) throws IOException {
		System.out.println(e.toString()+"  DESSERIALIZAR");
	}

	@FXML
	public void ranquing(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaRanquing.fxml"));

			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			//RanquingController controller = ;

			loader.setController(new RanquingController());
			Parent root = loader.load();
			Scene escena2 = new Scene(root);

			escena2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(escena2);
			window.setTitle("Pescamines");
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
