package appEquip03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EscenaControllerRegistre {

    @FXML private TextField usuariField;
    @FXML private TextField nomField;
    @FXML private TextField cognomsField;
    @FXML private TextField poblacioField;
    @FXML private TextField correuField;
    @FXML private TextField imatgeField;
    @FXML private PasswordField contrasenyaField;
    @FXML private ImageView formImage;

    @FXML
    public void initialize() {
        Image image = new Image(getClass().getResource("/form.png").toExternalForm());
        formImage.setImage(image);
    }

    private Connection connectar() throws Exception {
        String url = "jdbc:mysql://localhost:3306/ProjecteProg";
        String usuari = "root";
        String contrasenya = "";
        return DriverManager.getConnection(url, usuari, contrasenya);
    }

    @FXML
    private void processarRegistre() {
        try (Connection conn = connectar()) {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO usuaris (usuari, nom, cognoms, imatge, poblacio, correu, contrasenya) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, usuariField.getText());
            ps.setString(2, nomField.getText());
            ps.setString(3, cognomsField.getText());
            ps.setString(4, imatgeField.getText());
            ps.setString(5, poblacioField.getText());
            ps.setString(6, correuField.getText());
            ps.setString(7, contrasenyaField.getText());
            ps.executeUpdate();
            mostrarMissatge("Usuari registrat correctament.");
            tornarEnrere();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMissatge("Error al registrar: " + e.getMessage());
        }
    }

    @FXML
    private void tornarEnrere() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaLogin.fxml"));
            Parent root = loader.load();
            Scene escenaLogin = new Scene(root);
            MainWordle.canviarEscena(escenaLogin);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMissatge("No s'ha pogut tornar a l'inici.");
        }
    }

    private void mostrarMissatge(String missatge) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(missatge);
        alert.showAndWait();
    }
}
