package appEquip03;
	
import java.io.IOException;
import java.util.Random;

import appEquip03.pescamines.Tauler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
		
		
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("./pescamines/EscenaPescamines.fxml"));
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
	
	
	
}
