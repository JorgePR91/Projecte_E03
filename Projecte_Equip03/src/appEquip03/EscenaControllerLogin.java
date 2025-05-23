package appEquip03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EscenaControllerLogin {

    @FXML private TextField usuariField;
    @FXML private PasswordField contrasenyaField;
    @FXML private Button registrarBtn;
    @FXML private Button loginBtn;

    private Connection connectar() throws Exception {
        String url = "jdbc:mysql://localhost:3306/ProjecteProg"; // Nom de la BBDD
        String usuari = "root";
        String contrasenya = ""; // Canvia si tens contrasenya
        return DriverManager.getConnection(url, usuari, contrasenya);
    }

    @FXML
    private void registrarUsuari() {
        try (Connection conn = connectar()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO usuaris (usuari, contrasenya) VALUES (?, ?)");
            ps.setString(1, usuariField.getText());
            ps.setString(2, contrasenyaField.getText());
            ps.executeUpdate();
            mostrarMissatge("Usuari registrat correctament.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMissatge("Error al registrar: " + e.getMessage());
        }
    }

    @FXML
    private void validarLogin() {
        try (Connection conn = connectar()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuaris WHERE usuari=? AND contrasenya=?");
            ps.setString(1, usuariField.getText());
            ps.setString(2, contrasenyaField.getText());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaJocs.fxml"));
                Parent root = loader.load();
                Scene novaEscena = new Scene(root);
                MainWordle.canviarEscena(novaEscena);
            } else {
                mostrarMissatge("Credencials incorrectes.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMissatge("Error al validar: " + e.getMessage());
        }
    }

    private void mostrarMissatge(String missatge) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(missatge);
        alert.showAndWait();
    }
}
