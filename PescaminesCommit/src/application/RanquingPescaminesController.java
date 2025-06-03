package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class RanquingPescaminesController implements Initializable {
	@FXML
	private TableView<PescaminesPartidaRanquing> taulaRanquing;
	@FXML
	private TableColumn<PescaminesPartidaRanquing, Integer> rPossicio;
	@FXML
	private TableColumn<PescaminesPartidaRanquing, String> rUsuari;
	@FXML
	private TableColumn<PescaminesPartidaRanquing, String> rDificultat;
	@FXML
	private TableColumn<PescaminesPartidaRanquing, Time> rTemps;
	
	private ObservableList<PescaminesPartidaRanquing> partides = FXCollections.observableArrayList();;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> { // espera que l'inicialització siga completa.
			//https://www.youtube.com/watch?v=SwYczt6K_Q0
			//https://code.makery.ch/es/library/javafx-tutorial/part2/
			 
			ArrayList<PescaminesPartidaRanquing> arrParRan = ranquing();
			
			if(arrParRan.size() > 0)
	        for (int i = 0; i < arrParRan.size(); i++) {
	        	partides.add(arrParRan.get(i));
	        }
			
			this.rPossicio.setCellValueFactory(new PropertyValueFactory<>("possicio"));
			this.rUsuari.setCellValueFactory(new PropertyValueFactory<>("usuari"));
			this.rDificultat.setCellValueFactory(new PropertyValueFactory("dificultat"));
			this.rTemps.setCellValueFactory(new PropertyValueFactory("temps"));

			taulaRanquing.setItems(partides);

		});
	}
	
	@FXML
	public void tornar(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaPescaminesDificultat.fxml"));

			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

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
	public ArrayList<PescaminesPartidaRanquing> ranquing() {
		ArrayList<PescaminesPartidaRanquing> ranquing = null;
		try {
			if (!ConnexioBD.connectarBD("ProjecteProg")) {
				ConnexioBD.connectarScriptBD("./BD/script.sql");
				ConnexioBD.connectarBD("ProjecteProg");
			}
			String[] camps = { "usuari", "dificultat", "temps" };
			ranquing = ConnexioBD.ranquingPescamines("ranking_pescamines", camps);
			
//https://es.stackoverflow.com/questions/212713/agregar-elementos-a-tableview-desde-una-lista-en-javafx
// https://stackoverflow.com/questions/41304198/javafx-add-data-to-a-table
			ConnexioBD.tancarBD();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<PescaminesPartidaRanquing>();
		}
		return ranquing;

	}
}
