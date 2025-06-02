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

public class DificultatController implements Initializable {
	@FXML
	private VBox root_dificultad;

	private String dificultat;

		
	public VBox getRoot_dificultad() {
		return root_dificultad;
	}

	public void setRoot_dificultad(VBox root_dificultad) {
		this.root_dificultad = root_dificultad;
	}

	public String getDificultat() {
		return dificultat;
	}

	public void setDificultat(String dificultat) {
		this.dificultat = dificultat;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> { // espera que l'inicialització siga completa.
			Window window = (Stage) root_dificultad.getScene().getWindow();

		});
	}

	public void dificultad(ActionEvent e) throws IOException {

		Button b = (Button) e.getSource();
		dificultat = b.getText();
		
		DadesSingleton dada = DadesSingleton.getInstancia();
		dada.setCadenaCompartida(dificultat);
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaPescamines.fxml"));

			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			//PescaminesController controller = new PescaminesController();

			//loader.setController(controller);
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
		System.out.println(e.toString()+"  DESERIALIZAR");
		//Primer: crear una File amb la direcció que ens han donat
		//Crear un nou tauler amb el context deserialitzar
		//tot comprovant si no és null
		//associar el nou tauler al carregat?
		//associar el Gp a les casselles del nou?
		//deixar la pantalla d'inici no vista i el ratolí transparent
		//encetar el temps a play
		
		//https://acodigo.blogspot.com/2014/12/file-chooser-javafx-abrir-archivos.html
		
				
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("./Partides"));
        fileChooser.setTitle("Buscar partida guardada");

        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Tots els arxius", "*.*"),
                new FileChooser.ExtensionFilter("DAT", "*.dat")
        );

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
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaPescamines.fxml"));
    			Context context = Context.desserialitzacioTauler(f);
    			if(context == null)
    				throw new IOException("L'arxiu no conté res deserialitzable");
    			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
    			//PescaminesController controller = new PescaminesController();

    			//loader.setController(controller);
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

	@FXML
	public void ranquing(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaRanquing.fxml"));

			Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
			//RanquingController controller = ;

			//loader.setController(new RanquingController());
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
