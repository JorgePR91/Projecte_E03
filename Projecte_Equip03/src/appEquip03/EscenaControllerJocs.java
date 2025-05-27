package appEquip03;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EscenaControllerJocs {

    private String nomUsuari;

    @FXML private Label nomUsuariLabel;
    @FXML private Button logoutBtn;

    private void obrirEscena(String fxml, String titol) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // Intentem passar el nom d'usuari al controlador, si el mètode existeix
            Object controller = loader.getController();
            try {
                controller.getClass()
                    .getMethod("setNomUsuari", String.class)
                    .invoke(controller, nomUsuari);
            } catch (Exception ignored) {
                // Si no té el mètode, no fem res
            }

            Scene escena = new Scene(root, 600, 500);
            escena.getStylesheets().add(getClass().getResource("applicationWordle.css").toExternalForm());

            // Reutilitza l'Stage principal
            Stage stagePrincipal = MainWordle.getStagePrincipal();
            if (stagePrincipal != null) {
                stagePrincipal.setTitle(titol);
                stagePrincipal.setScene(escena);
                stagePrincipal.show();
            } else {
                // Si no hi ha stage principal, crea un nou stage (cas excepcional)
                Stage stage = new Stage();
                stage.setTitle(titol);
                stage.setScene(escena);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void anarWordle() {
        obrirEscena("EscenaWordle.fxml", "Wordle");
    }

    @FXML
    private void anarPescamines() {
        obrirEscena("EscenaPescamines.fxml", "Pescamines");
    }

    @FXML
    private void anarJocVida() {
        obrirEscena("EscenaJocVida.fxml", "Joc de la Vida");
    }

    @FXML
    private void anarPixelArt() {
        obrirEscena("EscenaPixelArt.fxml", "Pixel Art");
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
        if (nomUsuariLabel != null) {
            nomUsuariLabel.setText("Usuari: " + nomUsuari);
        }
    }

    @FXML
    private void tornarInici() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaInici.fxml"));
            Parent root = loader.load();
            Scene novaEscena = new Scene(root, 600, 500);
            novaEscena.getStylesheets().add(getClass().getResource("applicationWordle.css").toExternalForm());

            MainWordle.canviarEscena(novaEscena);

            // No cal tancar l'escena actual si estàs reutilitzant el stage principal

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
