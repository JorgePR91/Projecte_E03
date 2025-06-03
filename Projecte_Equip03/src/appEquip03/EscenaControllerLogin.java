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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EscenaControllerLogin {

    @FXML private TextField usuariField;
    @FXML private PasswordField contrasenyaField;
    @FXML private Button registrarBtn;
    @FXML private Button loginBtn;
    @FXML private ImageView loginImage;
    
    @FXML
    public void initialize() {
        Image image = new Image(getClass().getResource("/login.png").toExternalForm());
        loginImage.setImage(image);
    }

    private Connection connectar() throws Exception {
        String url = "jdbc:mysql://localhost:3306/ProjecteProg"; // Ruta BBDD
        String usuari = "root";
        String contrasenya = "";
        return DriverManager.getConnection(url, usuari, contrasenya);
    }

    @FXML
    private void registrarUsuari() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaRegistre.fxml"));
            Parent root = loader.load();
            Scene escenaRegistre = new Scene(root);
            MainWordle.canviarEscena(escenaRegistre);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMissatge("Error al obrir la finestra de registre: " + e.getMessage());
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
                mostrarMissatge("Aquest usuari no esta registrat.");
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
