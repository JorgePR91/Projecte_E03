package appEquip03;
	
import java.io.IOException;
import java.util.Random;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	Random alea = new Random();
	
	
	
	@Override
	public void init() throws Exception {
		super.init();
		//CARREGAR BD
		//CARREGAR ARXIUS EXTERNS I RECURSOS
		
	}

		@Override
		public void start(Stage primaryStage) {
			
				
				try {
					Parent root = FXMLLoader.load(getClass().getResource("EscenaPescamines.fxml"));
					Scene scene = new Scene(root,800,800);
					scene.getStylesheets().add(getClass().getResource("applicationPescamines.css").toExternalForm());
					primaryStage.setTitle("Pescamines");
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
	
		}
	
	public static void main(String[] args) {
		launch(args);
	}
	//CREAR SEGONS DIFICULTAT
	public Tauler crearTauler(String dificultat) {
	int tamany = 0;
	
	switch(dificultat) {
	case "f" -> tamany = 9;
	case "n" -> tamany = 10;
	case "d" -> tamany = 12;
	default -> {dificultat = "n"; tamany = 10;}
	}
	return new Tauler(tamany, tamany);
	}
	
	
	//MONTAR BOTONS I CLAVAR-LOS AL ROOT
}
