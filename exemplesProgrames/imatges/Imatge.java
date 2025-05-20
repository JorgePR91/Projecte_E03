package com.raul.altres;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Imatge extends Application {

	private VBox root;
	private HBox hboxImatges;
	private FlowPane botons;
	private Button botoImatge01;
	private Button botoImatge02;
	private Image imatge;
	private ImageView imageView;

	@Override
	public void start(Stage stage) {
		try {
			//contenidors
			root = new VBox(20);
			root.setAlignment(Pos.CENTER);
			root.setPadding(new Insets(20,20,20,20));
			hboxImatges = new HBox(20);
			hboxImatges.setPrefSize(600, 400);
			hboxImatges.setAlignment(Pos.CENTER);
			botons = new FlowPane(20,20);
			botons.setAlignment(Pos.CENTER);			
			//botons i esdeveniments
			botoImatge01 = new Button("Imatge 01");
			botoImatge01.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle (ActionEvent e) {
					try {
						imatge = new Image(new FileInputStream("./imatges/platja.jpg"));
						imageView.setImage(imatge);
						
					}catch (Exception ex) {
						System.out.println(ex);
					}
				}
			});
			
			botoImatge02 = new Button("Imatge 02");
			botoImatge02.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle (ActionEvent e) {
					try {
						imatge = new Image(new FileInputStream("./imatges/muntanya.jpg"));
						imageView.setImage(imatge);
						
					}catch (Exception ex) {
						System.out.println(ex);
					}
				}
			});
			//inicialitzem imageView
			imageView = new ImageView();
			imageView.setFitWidth(600);
			imageView.setPreserveRatio(true);
			//afegim elements a les caixes
			hboxImatges.getChildren().add(imageView);
			botons.getChildren().addAll(botoImatge01,botoImatge02);
			root.getChildren().addAll(hboxImatges,botons);				
			// creem escena i afegim a stage
			Scene escena = new Scene(root, 700, 500);
			//escena.getStylesheets().add(getClass().getResource("estils.css").toExternalForm());

			stage.setScene(escena);
			stage.setTitle("Imatges");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
