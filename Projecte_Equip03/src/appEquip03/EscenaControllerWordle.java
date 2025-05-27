package appEquip03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EscenaControllerWordle {

    private List<String> paraules;
    private String paraulaObjectiu;
    private int intentsFets = 0;
    private String nomUsuari;


    @FXML private VBox root;
    @FXML private TextField entrada;
    @FXML private Button enviar;
    @FXML private Label pista;
    @FXML private Button reiniciar;
    @FXML private Label nomUsuariLabel;
    @FXML private Button logoutBtn;


    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
        nomUsuariLabel.setText("Usuari: " + nomUsuari);
    }
    
    @FXML
    private void tornarInici() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaInici.fxml"));
            Parent root = loader.load();
            Scene novaEscena = new Scene(root, 600, 500);

            MainWordle.canviarEscena(novaEscena);
            
            // Tanca l'escena actual
            Stage actual = (Stage) logoutBtn.getScene().getWindow();
            actual.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void initialize() {
        try {
            carregarParaules();
            paraulaObjectiu = paraules.get(new Random().nextInt(paraules.size()));
        } catch (IOException e) {
            mostrarError("No s'ha pogut carregar el fitxer de paraules.");
        }
    }

    @FXML
    private void reiniciarJoc() {
        entrada.setDisable(false);
        enviar.setDisable(false);
        root.getChildren().clear();
        intentsFets = 0;
        pista.setText("Introdueix una paraula de 5 lletres:");
        paraulaObjectiu = paraules.get(new Random().nextInt(paraules.size()));

        HBox controls = new HBox(10, entrada, enviar);
        controls.setAlignment(Pos.CENTER);
        root.getChildren().addAll(pista, controls);
    }

    @FXML
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
        filaIntent.setAlignment(Pos.CENTER);
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
            pista.setText("🎉 Enhorabona! Has encertat la paraula!");
            entrada.setDisable(true);
            enviar.setDisable(true);
        } else if (intentsFets >= 5) {
            pista.setText("❌ Has esgotat els intents! La paraula era: " + paraulaObjectiu.toUpperCase());
            entrada.setDisable(true);
            enviar.setDisable(true);
        } else {
            pista.setText("Intent " + intentsFets + " de 5");
        }
    }

    private void carregarParaules() throws IOException {
        InputStreamReader isr = new InputStreamReader(getClass().getResourceAsStream("/paraules.txt"));
        BufferedReader br = new BufferedReader(isr);
        paraules = br.lines()
                .map(String::toLowerCase)
                .filter(p -> p.length() == 5)
                .collect(Collectors.toList());
        br.close();
    }

    private String eliminarAccents(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    private void mostrarError(String missatge) {
        Alert alert = new Alert(Alert.AlertType.ERROR, missatge, ButtonType.OK);
        alert.showAndWait();
        System.exit(1);
    }
}