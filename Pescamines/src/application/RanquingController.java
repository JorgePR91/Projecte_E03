package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RanquingController implements Initializable {
	@FXML
	private TableView taulaRanquing;
	@FXML
	private TableColumn rPossicio;
	@FXML
	private TableColumn rUsuari;
	@FXML
	private TableColumn rDificultad;
	@FXML
	private TableColumn rTemps;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> { // espera que l'inicialització siga completa.
			ranquing();
		});
	}

	@FXML
	public void tornar(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaDificultad.fxml"));

			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			// DificultadController controller ;

			// loader.setController( new DificultadController());
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
	public void ranquing() {
		try {
			if (!ConnexioBD.connectarBD("ProjecteProg")) {
				ConnexioBD.connectarScriptBD("./BD/script.sql");
				ConnexioBD.connectarBD("ProjecteProg");
			}
			String[] camps = { "usuari", "dificultat", "temps" };
			String[] ranquing = ConnexioBD.ranquing("ranking_pescamines", camps, "temps");
			
			System.out.println(Arrays.toString(ranquing));
//https://es.stackoverflow.com/questions/212713/agregar-elementos-a-tableview-desde-una-lista-en-javafx
// https://stackoverflow.com/questions/41304198/javafx-add-data-to-a-table
			
			
			ConnexioBD.tancarBD();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
