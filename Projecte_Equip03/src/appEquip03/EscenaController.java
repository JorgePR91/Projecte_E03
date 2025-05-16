package appEquip03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EscenaController {
	 private List<String> paraules;          // Llista de paraules de 5 lletres
	    private String paraulaObjectiu;          // Paraula a endevinar
	    private int intentsFets = 0;             // Comptador d'intents

	    private VBox root;                       // Contenidor principal
	    private TextField entrada;               // Camp per escriure la paraula
	    private Button enviar;                   // Botó per enviar l'intent
	    private Button reiniciar;                // Botó per reiniciar el joc
	    private Label pista;                     // Missatges i pistes per l'usuari

	    public static void main(String[] args) {
	        launch(args);                       // Inicia l'aplicació JavaFX
	    }

	    private static void launch(String[] args) {
			// TODO Auto-generated method stub
			
		}

		public void start(Stage primaryStage) {
	        primaryStage.setTitle("WordleFX - Endevina la paraula");

	        try {
	            carregarParaules();              // Carrega paraules del fitxer
	        } catch (IOException e) {
	            mostrarError("No s'ha pogut carregar el fitxer de paraules.");
	            return;
	        }

	        inicialitzarJoc();                  // Configura la UI i variables per començar

	        Scene scene = new Scene(root, 400, 500);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }

	    // Carrega paraules de 5 lletres del fitxer 'paraules.txt'
	    private void carregarParaules() throws IOException {
	        InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/paraules.txt"));
	        BufferedReader br = new BufferedReader(isr);
	        paraules = br.lines()
	                .map(String::toLowerCase)
	                .filter(p -> p.length() == 5)
	                .collect(Collectors.toList());
	        br.close();
	    }

	    // Prepara la partida nova, escull paraula aleatòria i crea la interfície
	    private void inicialitzarJoc() {
	        paraulaObjectiu = paraules.get(new Random().nextInt(paraules.size()));
	        intentsFets = 0;

	        root = new VBox(10);
	        root.setPadding(new Insets(15));

	        pista = new Label("Introdueix una paraula de 5 lletres:");
	        entrada = new TextField();
	        entrada.setPromptText("Escriu ací...");
	        enviar = new Button("Enviar");
	        reiniciar = new Button("Reiniciar");

	        enviar.setOnAction(e -> gestionarIntent());
	        reiniciar.setOnAction(e -> start((Stage) root.getScene().getWindow()));

	        HBox controls = new HBox(10, entrada, enviar);
	        root.getChildren().addAll(pista, controls);
	    }

	    // Comprova l'intent de l'usuari i mostra colors segons l'encert
	    private void gestionarIntent() {
	        String intent = entrada.getText().toLowerCase();
	        entrada.clear();

	        if (intent.length() != 5) {
	            pista.setText("La paraula ha de tindre exactament 5 lletres.");
	            return;
	        }

	        String normalObjectiu = eliminarAccents(paraulaObjectiu);
	        String normalIntent = eliminarAccents(intent);

	        HBox filaIntent = new HBox(5);
	        filaIntent.setPadding(new Insets(5));

	        boolean encertTotal = true;

	        for (int i = 0; i < 5; i++) {
	            char lletra = intent.charAt(i);
	            Label casella = new Label(String.valueOf(Character.toUpperCase(lletra)));
	            casella.setMinSize(40, 40);
	            casella.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-alignment: center; -fx-border-color: black;");

	            char lletraNormal = normalIntent.charAt(i);
	            if (normalObjectiu.charAt(i) == lletraNormal) {
	                casella.setStyle(casella.getStyle() + " -fx-background-color: green; -fx-text-fill: white;");
	            } else if (normalObjectiu.contains(String.valueOf(lletraNormal))) {
	                casella.setStyle(casella.getStyle() + " -fx-background-color: gold; -fx-text-fill: black;");
	                encertTotal = false;
	            } else {
	                casella.setStyle(casella.getStyle() + " -fx-background-color: gray; -fx-text-fill: white;");
	                encertTotal = false;
	            }
	            filaIntent.getChildren().add(casella);
	        }

	        root.getChildren().add(filaIntent);
	        intentsFets++;

	        if (encertTotal) {
	            pista.setText("Enhorabona! Has encertat la paraula!");
	            entrada.setDisable(true);
	            enviar.setDisable(true);
	            root.getChildren().add(reiniciar);
	        } else if (intentsFets >= 5) {
	            pista.setText("Has esgotat els intents! La paraula era: " + paraulaObjectiu.toUpperCase());
	            entrada.setDisable(true);
	            enviar.setDisable(true);
	            root.getChildren().add(reiniciar);
	        } else {
	            pista.setText("Intent " + intentsFets + " de 5");
	        }
	    }

	    // Elimina accents d'una cadena per comparar lletres sense diferenciar accents
	    private String eliminarAccents(String text) {
	        return Normalizer.normalize(text, Normalizer.Form.NFD)
	                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	    }

	    // Mostra un missatge d'error i tanca l'aplicació
	    private void mostrarError(String missatge) {
	        Alert alert = new Alert(Alert.AlertType.ERROR, missatge, ButtonType.OK);
	        alert.showAndWait();
	        System.exit(1);
	    }
	}
