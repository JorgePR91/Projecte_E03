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

public class RanquingController implements Initializable {

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(()->{ // espera que l'inicialització siga completa.


		});
	}		
	@FXML
	public void tornar(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaDificultad.fxml"));

			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			//DificultadController controller ;

			//loader.setController( new DificultadController());
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

