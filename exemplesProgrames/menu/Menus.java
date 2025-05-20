package com.raul.altres;

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Menus extends Application {

	private BorderPane root;
	private MenuBar barraMenus;
	private Menu menuFitxer;
	private Menu menuEdita;
	private MenuItem itemObre;
	private MenuItem itemTanca;
	private MenuItem itemImprimeix;
	private MenuItem itemNou;
	private MenuItem itemRecent;
	private MenuItem itemRetalla;
	private MenuItem itemCopia;
	private MenuItem itemEnganxa;
	private MenuItem itemCerca;
	private MenuItem itemPreferencies;

	@Override
	public void start(Stage stage) {
		try {
			root = new BorderPane();

			// barra de menús
			barraMenus = new MenuBar();
			// menú fitxer i ítems
			menuFitxer = new Menu("Fitxer");
			itemNou = new MenuItem("Nou");
			// per modificar la longitud dels ítems del menú
			itemNou.setStyle("-fx-padding:0 80 0 0;");
			itemObre = new MenuItem("Obre");
			itemImprimeix = new MenuItem("Imprimeix...");
			itemRecent = new MenuItem("Recent");
			itemTanca = new MenuItem("Tanca");
			// icona per a ítem obre
			Image iconaObre = new Image(new FileInputStream("iconaObre.png"));
			ImageView obreView = new ImageView(iconaObre);
			// hem de redimensionar la imatge a mida d'icona
			obreView.setFitWidth(15);
			obreView.setFitHeight(15);
			itemObre.setGraphic(obreView);
			// Combinació de tecles
			itemObre.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
			// icona per a ítem obre
			Image iconaTanca = new Image(new FileInputStream("iconaTanca.png"));
			ImageView tancaView = new ImageView(iconaTanca);
			// hem de redimensionar la imatge a mida d'icona
			tancaView.setFitWidth(15);
			tancaView.setFitHeight(15);
			itemTanca.setGraphic(tancaView);
			// Combinació de tecles
			itemTanca.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

			// afegeim items a menú
			menuFitxer.getItems().addAll(itemNou, itemObre, itemRecent, new SeparatorMenuItem(), // separador de menú
					itemImprimeix, new SeparatorMenuItem(), // separador de menú
					itemTanca);
			// menú edita i ítem
			menuEdita = new Menu("Edita");

			itemRetalla = new MenuItem("Retalla");
			// per modificar la longitud dels ítems del menú
			itemRetalla.setStyle("-fx-padding:0 80 0 0;");
			itemCopia = new MenuItem("Copia");
			itemEnganxa = new MenuItem("Enganxa");
			// icona amb css: les imatges ja tenen mida 15x15
			itemCerca = new MenuItem("Cerca");
			itemCerca.setId("itemCerca");
			itemPreferencies = new MenuItem("Preferències...");
			itemPreferencies.setId("itemPreferencies");
			// afegim item al menú
			menuEdita.getItems().addAll(itemRetalla, itemCopia, itemEnganxa, new SeparatorMenuItem(), // separador de
																										// menú // menú
					itemCerca, new SeparatorMenuItem(), // separador de menú
					itemPreferencies);
			// afegim els menús a la barra de menús
			barraMenus.getMenus().addAll(menuFitxer, menuEdita);
			// afegim la barra de menús al contenidor
			root.setTop(barraMenus);
			// Exemples Esdeveniments
			itemNou.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("Nou fitxer");
				}
			});

			itemTanca.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Platform.exit();
				}
			});

			itemCopia.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("Copia");
				}
			});

			// creem escena i afegim a stage
			Scene escena = new Scene(root, 640, 500);
			escena.getStylesheets().add(getClass().getResource("estils.css").toExternalForm());
			
			//handler per a esdeveniments de teclat
			EventHandler<KeyEvent> handlerTeclat = new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if(event.isControlDown() && event.getText().toLowerCase().equals("o")) {
						System.out.println("Obrir Fitxer");
						event.consume(); //per prevenir alguna acció per defecte del sistema
					}
					if(event.isControlDown() && event.getText().toLowerCase().equals("q")) {						
						Platform.exit();
						event.consume();//per prevenir alguna acció per defecte del sistema
					}
				}
			};
			//l'establim com a filter per evitar possibles accions predefinides del sistema
			escena.addEventFilter(KeyEvent.KEY_PRESSED, handlerTeclat);

			stage.setScene(escena);
			stage.setTitle("Menús");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
