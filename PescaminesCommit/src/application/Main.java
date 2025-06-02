package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaDificultat.fxml"));	


				Parent root_dificultad = FXMLLoader.load(getClass().getResource("EscenaDificultat.fxml"));	
				Scene scene = new Scene(root_dificultad);
				// afegim l'objecte cotxe com a dades adjuntes a la finestra
				// les recuperarem en el controler de l'altra escena
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Pescamines");
				primaryStage.show();

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
